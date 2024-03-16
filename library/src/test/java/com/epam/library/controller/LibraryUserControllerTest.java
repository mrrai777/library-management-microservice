package com.epam.library.controller;

import static org.mockito.ArgumentMatchers.any;
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

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.library.dto.UserDTO;
import com.epam.library.service.api.LibraryUserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class LibraryUserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private LibraryUserService libraryUserService;

	@Autowired
	private ObjectMapper mapper;

	@Test
	void testGetUsers() throws Exception {
		List<UserDTO> users = Arrays.asList(UserDTO.builder().username("user1").bookBorrowed(1).name("user1").build(),
				UserDTO.builder().username("user2").bookBorrowed(1).name("user2").build());
		when(libraryUserService.getUsers()).thenReturn(users);

		mockMvc.perform(get("/library/users")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].username").value("user1"))
				.andExpect(jsonPath("$[1].username").value("user2"));

		verify(libraryUserService).getUsers();
	}

	@Test
	void testGetUser() throws Exception {
		UserDTO user = UserDTO.builder().username("user1").bookBorrowed(1).name("user1").build();
		when(libraryUserService.getUser("user1")).thenReturn(user);

		mockMvc.perform(get("/library/users/user1")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.username").value("user1"));

		verify(libraryUserService).getUser("user1");
	}

	@Test
	void testCreateUser() throws Exception {
		UserDTO user = UserDTO.builder().username("user1").bookBorrowed(1).name("user1").email("user@gmail.com").build();
		
		when(libraryUserService.createUser(any(UserDTO.class))).thenReturn(user);

		mockMvc.perform(
				post("/library/users").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(user)))
				.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.username").value("user1"));

		verify(libraryUserService).createUser(any(UserDTO.class));
	}

	@Test
	void testUpdateUser() throws Exception {
		UserDTO user = UserDTO.builder().username("user1").bookBorrowed(1).name("user1").email("user123@gmail.com").build();
		when(libraryUserService.updateUser(any(), any(UserDTO.class))).thenReturn(user);

		mockMvc.perform(put("/library/users/user1").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(user))).andExpect(status().isOk())
				.andExpect(jsonPath("$.username").value("user1"));

		verify(libraryUserService).updateUser(eq("user1"), any(UserDTO.class));
	}

	@Test
	void testDeleteUser() throws Exception {
		mockMvc.perform(delete("/library/users/user1")).andExpect(status().isNoContent());

		verify(libraryUserService).deleteUser("user1");
	}
}
