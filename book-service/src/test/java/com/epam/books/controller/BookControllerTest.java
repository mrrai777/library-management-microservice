package com.epam.books.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.books.dto.BookDTO;
import com.epam.books.exception.BookException;
import com.epam.books.service.BookServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

	@MockBean
	private BookServiceImpl bookService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	private BookDTO bookDto;

	@BeforeEach
	void setUp() {
		bookDto = BookDTO.builder().bookName("Let Us C").bookAuthor("Yashwant K").publisher("BPB publications").build();
	}

	@Test
	void validGetBooks() throws Exception {
		when(bookService.findAllBooks(0, 5)).thenReturn(List.of(bookDto));
		mockMvc.perform(get("/books").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void validGetBook() throws Exception {
		when(bookService.findBookById(anyInt())).thenReturn(bookDto);
		mockMvc.perform(get("/books/1").accept(MediaType.APPLICATION_JSON)).andExpectAll(status().isOk(),
				jsonPath("$.bookName").value("Let Us C"));
	}

	@Test
	void validCreateBook() throws Exception {
		when(bookService.createBook(any())).thenReturn(bookDto);
		mockMvc.perform(
				post("/books").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(bookDto)))
				.andExpect(status().isCreated());
	}

	@Test
	void validUpdateBook() throws Exception {
		when(bookService.updateBook(anyInt(), any())).thenReturn(bookDto);
		mockMvc.perform(
				put("/books/1").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(bookDto)))
				.andExpectAll(status().isOk(), jsonPath("$.publisher").value("BPB publications"));
	}

	@Test
	void validDeleteBook() throws Exception {
		doNothing().when(bookService).deleteBook(anyInt());
		mockMvc.perform(delete("/books/1")).andExpect(status().isNoContent());
	}

	@Test
	void invalidGetBook() throws Exception {
		when(bookService.findBookById(anyInt())).thenThrow(new BookException("Book not found"));
		mockMvc.perform(get("/books/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}

	@Test
	void invalidCreateBook() throws Exception {
		BookDTO bookDTO = BookDTO.builder().bookName("Let Us C++").bookAuthor("Y.K.").build();
		when(bookService.createBook(any())).thenReturn(bookDto);
		mockMvc.perform(
				post("/books").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(bookDTO)))
				.andExpectAll(status().isBadRequest(),
						jsonPath("$.message").value("[Publisher information is necessary]"));
	}

	@Test
	void invalidUpdateBook() throws Exception {
		when(bookService.updateBook(anyInt(), any())).thenThrow(new NullPointerException());
		mockMvc.perform(
				put("/books/1").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(bookDto)))
				.andExpect(status().isInternalServerError());
	}

}
