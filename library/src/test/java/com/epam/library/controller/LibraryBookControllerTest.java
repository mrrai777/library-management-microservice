package com.epam.library.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.library.dto.BookDTO;
import com.epam.library.service.api.LibraryBookService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(LibraryBookController.class)
@AutoConfigureMockMvc
class LibraryBookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private LibraryBookService libraryBookService;

	@Autowired
	private ObjectMapper mapper;

	private List<BookDTO> books;

	@BeforeEach
	void setUp() {
		books = Arrays.asList(BookDTO.builder()
				.bookId(1)
				.bookName("Book 1")
				.bookAuthor("bookAuthor 1")
				.build(),
				
				BookDTO.builder()
				.bookId(2)
				.bookName("Book 2")
				.bookAuthor("bookAuthor 2")
				.build());
	}

	@Test
	void testGetBooks() throws Exception {
		when(libraryBookService.getBooks(anyInt(), anyInt())).thenReturn(books);

		mockMvc.perform(get("/library/books"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].bookId").value(1))
				.andExpect(jsonPath("$[0].bookName").value("Book 1"))
				.andExpect(jsonPath("$[0].bookAuthor").value("bookAuthor 1"));

		verify(libraryBookService).getBooks(0, 5);
	}

	@Test
	void testGetBook() throws Exception {
		when(libraryBookService.getBook(1)).thenReturn(books.get(0));

		mockMvc.perform(get("/library/books/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.bookId").value(1))
				.andExpect(jsonPath("$.bookName").value("Book 1"))
				.andExpect(jsonPath("$.bookAuthor").value("bookAuthor 1"));

		verify(libraryBookService).getBook(1);
	}

	@Test
	void testCreateBook() throws Exception {
		BookDTO book = BookDTO.builder()
				.bookId(3)
				.bookName("Book 3")
				.bookAuthor("bookAuthor 3")
				.publisher("Geetha Press").build();
		
		when(libraryBookService.createBook(any(BookDTO.class))).thenReturn(book);

		mockMvc.perform(post("/library/books").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(book)))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.bookId").value(3))
				.andExpect(jsonPath("$.bookName").value("Book 3"))
				.andExpect(jsonPath("$.bookAuthor").value("bookAuthor 3"));

		verify(libraryBookService).createBook(any(BookDTO.class));
	}

	@Test
	void testUpdateBook() throws Exception {
		BookDTO book = BookDTO.builder()
						.bookId(3)
						.bookName("Updated Book")
						.bookAuthor("Updated bookAuthor")
						.publisher("BPB").build();
		
		when(libraryBookService.updateBook(eq(1), any(BookDTO.class))).thenReturn(book);

		mockMvc.perform(put("/library/books/1").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(book)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.bookId").value(3))
				.andExpect(jsonPath("$.bookName").value("Updated Book"))
				.andExpect(jsonPath("$.bookAuthor").value("Updated bookAuthor"));

		verify(libraryBookService).updateBook(eq(1), any(BookDTO.class));
	}

	@Test
	void testDeleteBook() throws Exception {
		mockMvc.perform(delete("/library/books/1"))
		.andExpect(status().isNoContent());

		verify(libraryBookService).deleteBook(1);
	}
	
	@Test
	void invalidGetBook() throws Exception {
		
		when(libraryBookService.getBook(anyInt())).thenThrow(new NullPointerException());

		mockMvc.perform(get("/library/books/11"))
				.andExpect(status().isInternalServerError());
	}
}
