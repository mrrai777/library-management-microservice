package com.epam.books.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.epam.books.dto.BookDTO;
import com.epam.books.entity.Book;
import com.epam.books.exception.BookException;
import com.epam.books.repository.BookRepository;
import com.epam.books.utility.BookMapper;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class BookServiceTest {
	@Mock
	private BookRepository bookRepository;

//	@Mock
//	private Bookmapper mapper;

//	@Mock
//	private BookMapper mapper = Mappers.getMapper(BookMapper.class);

	@InjectMocks
	private BookServiceImpl service;

	@Test
	void validGetAllUser() {
		Pageable pageable = PageRequest.of(0, 3);
		Page<Book> page = mock(Page.class);
		when(bookRepository.findAll(pageable)).thenReturn(page);
//		when(mapper.convertToBookDTOs(any())).thenReturn(List.of(mock(BookDTO.class)));

		service.findAllBooks(0, 3);

		verify(bookRepository).findAll(pageable);
	}

	@Test
	void validGetByUsername() throws BookException {
		Book book = mock(Book.class);
		BookDTO bookDto = mock(BookDTO.class);
		when(bookRepository.findById(any())).thenReturn(Optional.of(book));
//		when(mapper.convertToBookDTO(any())).thenReturn(bookDto);
		service.findBookById(1);
		verify(bookRepository).findById(1);
//		verify(mapper).convertToBookDTO(book);
	}

	@Test
	void invalidGetByUsername() throws BookException {
		when(bookRepository.findById(any())).thenReturn(Optional.empty());
		Assertions.assertThrowsExactly(BookException.class, () -> service.findBookById(1));
	}

	@Test
	void validCreateUser() {
		Book book = mock(Book.class);
		BookDTO bookDto = mock(BookDTO.class);
		when(bookRepository.save(any())).thenReturn(book);
//		when(mapper.convertToBook(any())).thenReturn(book);
//		when(mapper.convertToBookDTO(any())).thenReturn(bookDto);

		service.createBook(bookDto);

		verify(bookRepository).save(book);
	}

	@Test
	void validUpdateUser() {
		Book book = mock(Book.class);
		BookDTO bookDto = mock(BookDTO.class);
		when(bookRepository.save(book)).thenReturn(book);
//		when(mapper.convertToBook(any())).thenReturn(book);
//		when(mapper.convertToBookDTO(any())).thenReturn(bookDto);

		service.updateBook(1, bookDto);

		verify(bookRepository).save(book);
	}

	@Test
	void validDeleteUser() {
		doNothing().when(bookRepository).deleteById(any());
		service.deleteBook(1);
		verify(bookRepository).deleteById(1);
	}
}
