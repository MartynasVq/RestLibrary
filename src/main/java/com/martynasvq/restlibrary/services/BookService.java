package com.martynasvq.restlibrary.services;

import com.martynasvq.restlibrary.model.Book;
import com.martynasvq.restlibrary.model.BookDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface BookService {

    BookDto findById(UUID uuid);

    void deleteById(UUID uuid);

    Book saveBook(BookDto bookDto);

    List<BookDto> listBooks();

    List<BookDto> listBooksByAuthor(String author);

    List<BookDto> listBooksByCategory(String category);

    List<BookDto> listBooksByLanguage(String language);

    List<BookDto> listBooksByISBN(String isbn);

    List<BookDto> listBooksByName(String name);

    List<BookDto> listBooksByAvailability(Boolean isAvailable);

    void saveDataToFile();

    BookDto borrowBook(UUID uuid, String personName, LocalDate until);

    void setBookList(Map<UUID, Book> bookList);
}
