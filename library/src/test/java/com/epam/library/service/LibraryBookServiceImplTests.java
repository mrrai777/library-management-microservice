package com.epam.library.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.epam.library.dto.BookDTO;
import com.epam.library.feign.BookFeign;
import com.epam.library.feign.UserFeign;
import com.epam.library.repository.LibraryRepository;

@SpringBootTest
class LibraryBookServiceImplTests {

	@InjectMocks
	private LibraryBookServiceImpl libraryBookService;

	@Mock
	private BookFeign bookFeign;

	@Mock
	private UserFeign userFeign;

	@Mock
	private LibraryRepository libraryRepository;

	@Test
	void testGetBooks() {
		List<BookDTO> expectedBooks = Arrays.asList(new BookDTO(), new BookDTO());
		when(bookFeign.getBooks(anyInt(), anyInt())).thenReturn(ResponseEntity.ok(expectedBooks));

		List<BookDTO> books = libraryBookService.getBooks(1, 2);

		Assertions.assertEquals(expectedBooks, books);
	}

	@Test
	void testGetBook() {
		BookDTO expectedBook = new BookDTO();
		when(bookFeign.getBook(anyInt())).thenReturn(ResponseEntity.ok(expectedBook));

		BookDTO book = libraryBookService.getBook(1);

		Assertions.assertEquals(expectedBook, book);
	}

	@Test
	void testCreateBook() {
		BookDTO newBook = new BookDTO();
		BookDTO expectedBook = new BookDTO();
		when(bookFeign.createBook(any())).thenReturn(ResponseEntity.ok(expectedBook));

		BookDTO book = libraryBookService.createBook(newBook);

		Assertions.assertEquals(expectedBook, book);
	}

	@Test
	void testUpdateBook() {
		BookDTO bookUpdate = new BookDTO();
		BookDTO expectedBook = new BookDTO();
		when(bookFeign.updateBook(anyInt(), any())).thenReturn(ResponseEntity.ok(expectedBook));

		BookDTO book = libraryBookService.updateBook(1, bookUpdate);

		Assertions.assertEquals(expectedBook, book);
	}

	@Test
	void testDeleteBook() {
		List<String> users = Arrays.asList("Alice", "Bob");
		when(libraryRepository.findAllUsernameByBookId(anyInt())).thenReturn(users);
		when(bookFeign.deleteBook(anyInt())).thenReturn(ResponseEntity.noContent().build());

		libraryBookService.deleteBook(1);

		verify(userFeign).decrementUsersBookCount(users);
		verify(libraryRepository).deleteAllByBookId(1);
		verify(bookFeign).deleteBook(1);
	}

}