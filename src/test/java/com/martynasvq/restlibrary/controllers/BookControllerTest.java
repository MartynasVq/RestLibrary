package com.martynasvq.restlibrary.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.martynasvq.restlibrary.model.BookDto;
import com.martynasvq.restlibrary.services.BookService;
import com.martynasvq.restlibrary.services.LendingService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.util.UUID;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Autowired
    LendingService lendingService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    BookService bookService;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

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
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        try (Writer writer = new FileWriter("data.json")) {
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    }

    @Test
    void listAllBooks() throws Exception {
        mockMvc.perform(get("/list").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(bookService.listBooks().size())));

    }

    @Test
    void listAllBooksWithParam() throws Exception {
        mockMvc.perform(get("/list").param("author", "John Johnson").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(bookService.listBooksByAuthor("John Johnson").size())));

    }

    @Test
    void deleteBook() throws Exception {
            mockMvc.perform(delete("/{id}/delete", book1.getId())).andExpect(status().isNotFound());

            assertEquals(null, bookService.findById(book1.getId()));
    }

    @Test
    void getBookById() throws Exception {
        mockMvc.perform(get("/{uuid}", book1.getId())).andExpect(status().isOk()).
                    andExpect(jsonPath("$.name").value(book1.getName()));

        mockMvc.perform(get("/{uuid}", UUID.randomUUID())).andExpect(status().isNotFound());
    }

    @Test
    void newBook() throws Exception {
        int size = bookService.listBooks().size()+1;
        mockMvc.perform(post("/new").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(book4)))
                    .andExpect(status().isCreated());

        assertEquals(size, bookService.listBooks().size());
    }

    @Test
    void borrowOne() throws Exception {
            mockMvc.perform(get("/{uuid}/borrow", book1.getId())
                    .param("until", "2021-05-20")
                    .param("personName", "Johnathan")).andExpect(status().isOk());

            assertEquals(false, bookService.findById(book1.getId()).isAvailable());

        mockMvc.perform(get("/{uuid}/borrow", book1.getId())
                .param("until", "2021-05-20")
                .param("personName", "Johnathan")).andExpect(status().isNotAcceptable());
    }

    @Test
    void borrowWithDateException() throws Exception {
        mockMvc.perform(get("/{uuid}/borrow", book1.getId())
                .param("until", "2021-08-20")
                .param("personName", "Johnathan")).andExpect(status().isNotAcceptable());

        mockMvc.perform(get("/{uuid}/borrow", book1.getId())
                .param("until", "2021-04-20")
                .param("personName", "Johnathan")).andExpect(status().isNotAcceptable());
    }

    @Test
    void borrowWithMissingParam() throws Exception {
        mockMvc.perform(get("/{uuid}/borrow", book1.getId())
                .param("until", "2021-05-20")).andExpect(status().isBadRequest());
    }


}
