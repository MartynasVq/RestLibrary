package com.martynasvq.restlibrary.services;

import com.martynasvq.restlibrary.model.BookDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = LendingServiceImpl.class)
class LendingServiceImplTest {

    @Autowired
    LendingService lendingService;

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

    @Test
    void canBorrowMore() {
        assertTrue(lendingService.canBorrowMore("John"));
    }

    @Test
    void canBorrowMoreThanThree() {
        lendingService.borrow(book1.getId(), "John");
        lendingService.borrow(book2.getId(), "John");
        lendingService.borrow(book3.getId(), "John");

        assertFalse(lendingService.canBorrowMore("John"));
    }
}