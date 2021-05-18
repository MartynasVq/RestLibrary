package com.martynasvq.restlibrary.controllers;

import com.martynasvq.restlibrary.model.Book;
import com.martynasvq.restlibrary.model.BookDto;
import com.martynasvq.restlibrary.services.BookService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
public class BookController {

    BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/list")
    ResponseEntity<List<BookDto>> listAllBooks(@RequestParam(value = "author", required = false) String author,
                                               @RequestParam(value = "category", required = false) String category,
                                               @RequestParam(value = "language", required = false) String language,
                                               @RequestParam(value = "isbn", required = false) String isbn,
                                               @RequestParam(value = "name", required = false) String name,
                                               @RequestParam(value = "available", required = false) Boolean available) {

        List<BookDto> bookDtoList;
        if(author != null) {
            bookDtoList = bookService.listBooksByAuthor(author);
        } else if(category != null) {
            bookDtoList = bookService.listBooksByCategory(category);
        } else if(language != null) {
            bookDtoList = bookService.listBooksByLanguage(language);
        } else if(isbn != null) {
            bookDtoList = bookService.listBooksByISBN(isbn);
        } else if(name != null) {
            bookDtoList = bookService.listBooksByName(name);
        } else if(available != null) {
            bookDtoList = bookService.listBooksByAvailability(available);
        } else {
            bookDtoList = bookService.listBooks();
        }

        return new ResponseEntity<>(bookDtoList, HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}/delete")
    ResponseEntity delete(@PathVariable UUID uuid) {
        bookService.deleteById(uuid);

        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{uuid}")
    ResponseEntity<BookDto> getBookById(@PathVariable UUID uuid) {
        BookDto bookDto = bookService.findById(uuid);
        if(bookDto == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }

    @PostMapping("/new")
    ResponseEntity newBook(@RequestBody BookDto bookDto) {
        Book book = bookService.saveBook(bookDto);
        if(book == null)
            return new ResponseEntity(HttpStatus.CREATED);
        else
            return new ResponseEntity(HttpStatus.CONFLICT);
    }

    @GetMapping("/{uuid}/borrow")
    ResponseEntity borrow(@PathVariable UUID uuid, @RequestParam(value = "until", required = true)  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                          @RequestParam(value = "personName", required = true) String personName) {
        BookDto bookDto = bookService.borrowBook(uuid, personName, date);
        if(bookDto == null)
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);

        return new ResponseEntity<>(bookDto, HttpStatus.OK);
    }
}
