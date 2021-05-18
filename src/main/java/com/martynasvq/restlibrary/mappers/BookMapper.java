package com.martynasvq.restlibrary.mappers;

import com.martynasvq.restlibrary.model.Book;
import com.martynasvq.restlibrary.model.BookDto;
import org.mapstruct.Mapper;


public interface BookMapper {

    BookDto BookToBookDto(Book book);

    Book BookDtoToBook(BookDto book);
}
