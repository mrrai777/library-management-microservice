package com.epam.users.controller;

import static org.mockito.ArgumentMatchers.any;
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

import com.epam.users.dto.UserDTO;
import com.epam.users.exception.BookException;
import com.epam.users.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
	@MockBean
	private UserServiceImpl userService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	private UserDTO userDto;

	@BeforeEach
	void setUp() {
		userDto = UserDTO.builder().username("abc321").email("xyz123@gmail.com").name("abc").build();
	}

	@Test
	void validGetBooks() throws Exception {
		when(userService.findAllUser()).thenReturn(List.of(userDto));
		mockMvc.perform(get("/users").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void validGetBook() throws Exception {
		when(userService.findUserByUsername(any())).thenReturn(userDto);
		mockMvc.perform(get("/users/1").accept(MediaType.APPLICATION_JSON)).andExpectAll(status().isOk(),
				jsonPath("$.username").value("abc321"));
	}

	@Test
	void validCreateBook() throws Exception {
		when(userService.createUser(any())).thenReturn(userDto);
		mockMvc.perform(
				post("/users").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(userDto)))
				.andExpect(status().isCreated());
	}

	@Test
	void validUpdateBook() throws Exception {
		when(userService.updateUser(any(), any())).thenReturn(userDto);
		mockMvc.perform(
				put("/users/1").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(userDto)))
				.andExpectAll(status().isOk(), jsonPath("$.name").value("abc"));
	}

	@Test
	void validDeleteBook() throws Exception {
		doNothing().when(userService).deleteUser(any());
		mockMvc.perform(delete("/users/1")).andExpect(status().isNoContent());
	}

	@Test
	void invalidGetBook() throws Exception {
		when(userService.findUserByUsername(any())).thenThrow(new BookException("Book not found"));
		mockMvc.perform(get("/users/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}

	@Test
	void invalidCreateBook() throws Exception {
		UserDTO userDTO = UserDTO.builder().username("abc321").name("abc").build();
		when(userService.createUser(any())).thenReturn(userDto);
		mockMvc.perform(
				post("/users").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(userDTO)))
				.andExpectAll(status().isBadRequest(),
						jsonPath("$.message").value("[email is mandatory]"));
	}

	@Test
	void invalidUpdateBook() throws Exception {
		when(userService.updateUser(any(), any())).thenThrow(new NullPointerException());
		mockMvc.perform(
				put("/users/1").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(userDto)))
				.andExpect(status().isInternalServerError());
	}
}
