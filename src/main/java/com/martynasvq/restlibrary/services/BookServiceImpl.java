package com.martynasvq.restlibrary.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.martynasvq.restlibrary.mappers.BookMapper;
import com.martynasvq.restlibrary.model.Book;
import com.martynasvq.restlibrary.model.BookDto;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static Map<UUID, Book> bookList;
    private final BookMapper bookMapper;
    private final LendingService lendingService;

    public BookServiceImpl(BookMapper bookMapper, LendingService lendingService) {
        this.bookMapper = bookMapper;
        this.lendingService = lendingService;
        if(this.bookList == null) {
            bookList = new HashMap<>();
        }
    }

    @Override
    public BookDto findById(UUID uuid) {
        Book book = bookList.get(uuid);
        if(book != null)
            return bookMapper.BookToBookDto(book);
        else
            return null;
    }

    @Override
    public void deleteById(UUID uuid) {
            if(bookList.remove(uuid) != null)
                saveDataToFile();
    }

    @Override
    public Book saveBook(BookDto bookDto) {
        Book book = bookList.putIfAbsent(bookDto.getId(), bookMapper.BookDtoToBook(bookDto));
        if(book == null)
            saveDataToFile();

        return book;
    }

    @Override
    public List<BookDto> listBooks() {
        ArrayList<BookDto> bookDtos = new ArrayList<>();
        bookList.values().forEach(b -> bookDtos.add(bookMapper.BookToBookDto(b)));
        return bookDtos;
    }

    @Override
    public List<BookDto> listBooksByAuthor(String author) {
        return listBooks().stream().filter(b -> b.getAuthor().equalsIgnoreCase(author)).collect(Collectors.toList());
    }

    @Override
    public List<BookDto> listBooksByCategory(String category) {
        return listBooks().stream().filter(b -> b.getCategory().equalsIgnoreCase(category)).collect(Collectors.toList());
    }

    @Override
    public List<BookDto> listBooksByLanguage(String language) {
        return listBooks().stream().filter(b -> b.getLanguage().equalsIgnoreCase(language)).collect(Collectors.toList());
    }

    @Override
    public List<BookDto> listBooksByISBN(String isbn) {
        return listBooks().stream().filter(b -> b.getIsbn().equalsIgnoreCase(isbn)).collect(Collectors.toList());
    }

    @Override
    public List<BookDto> listBooksByName(String name) {
        return listBooks().stream().filter(b -> b.getName().equalsIgnoreCase(name)).collect(Collectors.toList());
    }

    @Override
    public List<BookDto> listBooksByAvailability(Boolean isAvailable) {
        return listBooks().stream().filter(b -> b.isAvailable() == isAvailable).collect(Collectors.toList());
    }

    @Override
    public void saveDataToFile() {
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        try (Writer writer = new FileWriter("data.json")) {
            gson.toJson(bookList, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BookDto borrowBook(UUID uuid, String personName, LocalDate until) {
        Book book = bookList.get(uuid);
        if(book == null || !book.isAvailable()) {
            return null;
        }
        LocalDate localDate = LocalDate.now().plusMonths(3);
        if(localDate.isBefore(until) || until.isBefore(LocalDate.now())) {
            return null;
        }
        if(!lendingService.canBorrowMore(personName)) {
            return null;
        }

        lendingService.borrow(uuid, personName);
        book.setAvailable(false);
        book.setBorrowedBy(personName);
        book.setBorrowedUntil(until);
        saveDataToFile();
        return bookMapper.BookToBookDto(book);
    }

    public void setBookList(Map<UUID, Book> bookList) {
        this.bookList = bookList;
    }


}
