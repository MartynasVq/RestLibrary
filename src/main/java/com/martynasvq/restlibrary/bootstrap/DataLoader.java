package com.martynasvq.restlibrary.bootstrap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.martynasvq.restlibrary.mappers.BookMapper;
import com.martynasvq.restlibrary.model.Book;
import com.martynasvq.restlibrary.model.BookDto;
import com.martynasvq.restlibrary.services.BookService;
import com.martynasvq.restlibrary.services.LendingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    LendingService lendingService;
    @Autowired
    BookService bookService;
    @Autowired
    BookMapper bookMapper;

    @Override
    public void run(String... args) throws Exception {
        if(loadBookData()) {
            log.info("Book data initialized.");
        }
        else {
            log.info("No books have been loaded.");
            loadMockData();
        }
    }

    private void loadMockData() {
        BookDto bookDto1 = new BookDto();
        bookDto1.setId(UUID.randomUUID());
        bookDto1.setCategory("Horror");
        bookDto1.setAuthor("John Johnson");
        bookDto1.setIsbn("12313221321");
        bookDto1.setLanguage("English");
        bookDto1.setPublicationDate(LocalDate.of(1999,10,11));
        bookService.saveBook(bookDto1);

        BookDto bookDto2 = new BookDto();
        bookDto2.setId(UUID.randomUUID());
        bookDto2.setCategory("Thriller");
        bookDto2.setAuthor("John James");
        bookDto2.setIsbn("12313555551");
        bookDto2.setLanguage("English");
        bookDto2.setPublicationDate(LocalDate.of(1999,10,11));
        bookService.saveBook(bookDto2);

    }

    private boolean loadBookData() {
        Gson gson = new Gson();
        try {
            Map<UUID, Book> books = gson.fromJson(new FileReader("data.json"), new TypeToken<Map<UUID, Book>>(){}.getType());
            if(books != null) {
                bookService.setBookList(books);
                books.values().forEach(b -> {
                    if(!b.isAvailable()) {
                        lendingService.borrow(b.getId(), b.getBorrowedBy());
                    }
                });
            }
        } catch (FileNotFoundException e) {
             return false;
        }

        return true;
    }
}
