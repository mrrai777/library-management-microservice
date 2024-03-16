package com.epam.books.utility;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import com.epam.books.dto.BookDTO;
import com.epam.books.entity.Book;

@Mapper
public interface BookMapper {
	
	BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

	BookDTO convertToBookDTO(Book book);

	Book convertToBook(BookDTO bookDTO);

	List<BookDTO> convertToBookDTOs(Page<Book> books);
}
