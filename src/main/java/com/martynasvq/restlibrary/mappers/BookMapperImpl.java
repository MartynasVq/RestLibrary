package com.martynasvq.restlibrary.mappers;

import com.martynasvq.restlibrary.model.Book;
import com.martynasvq.restlibrary.model.BookDto;
import org.springframework.stereotype.Component;

@Component
public class BookMapperImpl implements BookMapper {
    public BookMapperImpl() {
    }

    public BookDto BookToBookDto(Book book) {
        if (book == null) {
            return null;
        } else {
            BookDto bookDto = new BookDto();
            bookDto.setName(book.getName());
            bookDto.setAuthor(book.getAuthor());
            bookDto.setCategory(book.getCategory());
            bookDto.setLanguage(book.getLanguage());
            bookDto.setPublicationDate(book.getPublicationDate());
            bookDto.setIsbn(book.getIsbn());
            bookDto.setId(book.getId());
            bookDto.setAvailable(book.isAvailable());
            bookDto.setBorrowedBy(book.getBorrowedBy());
            bookDto.setBorrowedUntil(book.getBorrowedUntil());
            return bookDto;
        }
    }

    public Book BookDtoToBook(BookDto book) {
        if (book == null) {
            return null;
        } else {
            Book book1 = new Book();
            book1.setName(book.getName());
            book1.setAuthor(book.getAuthor());
            book1.setCategory(book.getCategory());
            book1.setLanguage(book.getLanguage());
            book1.setPublicationDate(book.getPublicationDate());
            book1.setIsbn(book.getIsbn());
            book1.setId(book.getId());
            book1.setAvailable(book.isAvailable());
            book1.setBorrowedBy(book.getBorrowedBy());
            book1.setBorrowedUntil(book.getBorrowedUntil());
            return book1;
        }
    }
}

