package com.epam.books.service.api;

import java.util.List;

import com.epam.books.dto.BookDTO;
import com.epam.books.exception.BookException;

public interface BookService {

	List<BookDTO> findAllBooks(Integer pageNumber, Integer pageSize);

	BookDTO findBookById(Integer id) throws BookException;

	BookDTO createBook(BookDTO bookDto);

	void deleteBook(Integer id);

	BookDTO updateBook(Integer id, BookDTO bookDto);
	
}
