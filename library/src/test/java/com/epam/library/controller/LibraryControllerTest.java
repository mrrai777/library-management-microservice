package com.epam.library.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.library.dto.LibraryDTO;
import com.epam.library.service.api.LibraryService;

@WebMvcTest(LibraryController.class)
@AutoConfigureMockMvc
class LibraryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private LibraryService libraryService;

	@Test
	void testIssueBook() throws Exception {
		when(libraryService.issueBook(anyString(), anyInt())).thenReturn(mock(LibraryDTO.class));

		mockMvc.perform(post("/library/users/username/books/1")).andExpect(status().isCreated());

		verify(libraryService).issueBook(eq("username"), eq(1));
	}

	@Test
	void testReleaseBook() throws Exception {
		mockMvc.perform(delete("/library/users/username/books/1")).andExpect(status().isNoContent());

		verify(libraryService).releaseBook(eq("username"), eq(1));
	}
}
