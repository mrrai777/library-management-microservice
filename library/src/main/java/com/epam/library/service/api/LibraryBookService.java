package com.epam.library.service.api;

import java.util.List;

import com.epam.library.dto.BookDTO;

public interface LibraryBookService {

	List<BookDTO> getBooks(Integer pageNumber, Integer pageSize);

	BookDTO getBook(Integer id);

	BookDTO createBook(BookDTO bookDto);

	BookDTO updateBook(Integer id, BookDTO bookDto);

	Void deleteBook(Integer id);

}
