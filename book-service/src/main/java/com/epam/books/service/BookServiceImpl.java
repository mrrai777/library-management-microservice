package com.epam.books.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.epam.books.dto.BookDTO;
import com.epam.books.entity.Book;
import com.epam.books.exception.BookException;
import com.epam.books.repository.BookRepository;
import com.epam.books.service.api.BookService;
import com.epam.books.utility.BookMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;

	@Override
	public List<BookDTO> findAllBooks(Integer pageNumber, Integer pageSize) {
		log.info("BookServiceImpl (findAllBooks)");
		Pageable page = PageRequest.of(pageNumber, pageSize);
		return BookMapper.INSTANCE.convertToBookDTOs(bookRepository.findAll(page));
	}

	@Override
	public BookDTO findBookById(Integer id) throws BookException {
		log.info("BookServiceImpl (findBook) Id : {}", id);
		Book book = bookRepository.findById(id).orElseThrow(() -> new BookException("Book not found"));
		return BookMapper.INSTANCE.convertToBookDTO(book);
	}

	@Override
	public BookDTO createBook(BookDTO bookDto) {
		log.info("BookServiceImpl (createBook) : ", bookDto);
		Book book = bookRepository.save(BookMapper.INSTANCE.convertToBook(bookDto));
		return BookMapper.INSTANCE.convertToBookDTO(book);
	}

	@Override
	public void deleteBook(Integer id) {
		log.info("BookServiceImpl (deleteBook) Id : {}", id);
		bookRepository.deleteById(id);
	}

	@Override
	public BookDTO updateBook(Integer id, BookDTO bookDto) {
		bookDto.setBookId(id);
		log.info("BookServiceImpl (updateBook) : {}", bookDto);
		Book book = bookRepository.save(BookMapper.INSTANCE.convertToBook(bookDto));
		return BookMapper.INSTANCE.convertToBookDTO(book);
	}
}
