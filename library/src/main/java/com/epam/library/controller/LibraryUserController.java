package com.epam.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.library.dto.UserDTO;
import com.epam.library.service.api.LibraryUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/library/users")
public class LibraryUserController {

	@Autowired
	private LibraryUserService libraryUserService;
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> getUsers() {
		return ResponseEntity.ok(libraryUserService.getUsers());
	}

	@GetMapping("/{username}")
	public ResponseEntity<UserDTO> getUser(@PathVariable String username) {
		return ResponseEntity.ok(libraryUserService.getUser(username));
	}

	@PostMapping
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDto) {
		return new ResponseEntity<>(libraryUserService.createUser(userDto), HttpStatus.CREATED);
	}

	@PutMapping("/{username}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable String username, @Valid @RequestBody UserDTO userDto) {
		return ResponseEntity.ok(libraryUserService.updateUser(username, userDto));
	}

	@DeleteMapping("/{username}")
	public ResponseEntity<Void> deleteUser(@PathVariable String username) {
		libraryUserService.deleteUser(username);
		return ResponseEntity.noContent().build();
	}
}
