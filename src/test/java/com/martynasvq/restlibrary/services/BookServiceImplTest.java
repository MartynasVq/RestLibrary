package com.martynasvq.restlibrary.services;

import com.martynasvq.restlibrary.mappers.BookMapper;
import com.martynasvq.restlibrary.mappers.BookMapperImpl;
import com.martynasvq.restlibrary.model.BookDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {BookServiceImpl.class, BookMapperImpl.class, LendingServiceImpl.class})
class BookServiceImplTest {

    @Autowired
    LendingService lendingService;

    @Autowired
    BookMapper bookMapper;

    @Autowired
    BookService bookService;

    BookDto book1 = BookDto.builder().author("John Johnson").category("Thriller").isbn("111111115")
            .name("Thrills").language("English").publicationDate(LocalDate.of(2020, 10, 11))
            .id(UUID.fromString("407ece6c-19e0-4c56-b0e0-f27356c5cb2c")).build();
    BookDto book2 = BookDto.builder().author("Peter Peterson").category("Fantasy").isbn("111111114")
            .name("Dragons").language("English").publicationDate(LocalDate.of(2020, 5, 11))
            .id(UUID.fromString("407ece6c-19e0-4c56-b0e3-f27356c5cb2c")).build();
    BookDto book3 = BookDto.builder().author("John Reed").category("Horror").isbn("111111113")
            .name("Horror House").language("English").publicationDate(LocalDate.of(2020, 10, 11))
            .id(UUID.fromString("407ece6c-19e0-4c56-b0e2-f27356c5cb2c")).build();
    BookDto book4 = BookDto.builder().author("James Pike").category("Comedy").isbn("111111112")
            .name("Fun and Laughs!").language("English").publicationDate(LocalDate.of(2020, 6, 11))
            .id(UUID.fromString("407ece6c-19e0-4c56-b0e1-f27356c5cb2c")).build();

    @BeforeEach
    void init() {
        bookService.saveBook(book1);
        bookService.saveBook(book2);
        bookService.saveBook(book3);
    }

    @AfterEach
    void cleanUp() {
        try (Writer writer = new FileWriter("data.json")) {
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bookService.deleteById(book4.getId());
    }

    @Test
    void findById() {
        assertNotNull(bookService.findById(book1.getId()));

        assertNull(bookService.findById(book4.getId()));
    }

    @Test
    void deleteById() {
        bookService.deleteById(book1.getId());

        assertNull(bookService.findById(book1.getId()));
    }

    @Test
    void saveBook() {
        bookService.saveBook(book4);

        assertNotNull(bookService.findById(book4.getId()));
    }

    @Test
    void listBooks() {
        assertEquals(3, bookService.listBooks().size());
    }

    @Test
    void borrowBook() {
        assertFalse(bookService.borrowBook(book3.getId(), "John", LocalDate.of(2021,06,18)).isAvailable());
    }

}