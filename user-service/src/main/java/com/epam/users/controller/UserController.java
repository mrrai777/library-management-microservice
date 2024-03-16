package com.epam.users.controller;

import java.util.List;

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

import com.epam.users.dto.UserDTO;
import com.epam.users.exception.BookException;
import com.epam.users.service.api.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("users")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> getUsers() {
		log.info("UserController (getAllUser)");
		return ResponseEntity.ok(userService.findAllUser());
	}
	
	@GetMapping("{username}")
	public ResponseEntity<UserDTO> getUser(@PathVariable String username) throws BookException {
		log.info("UserController (getByUsername)");
		return ResponseEntity.ok(userService.findUserByUsername(username));
	}
	
	@PostMapping
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDto) {
		log.info("UserController (createUser)");
		return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
	}
	
	@DeleteMapping("{username}")
	public ResponseEntity<Void> deleteUser(@PathVariable String username) {
		log.info("UserController (deleteUser)");
		userService.deleteUser(username);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("{username}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable String username, @Valid @RequestBody UserDTO userDto) {
		log.info("UserController (updateUser)");
		return new ResponseEntity<>(userService.updateUser(username, userDto), HttpStatus.OK);
	}
	
	@PutMapping("/decrementBookCount")
	public void decrementBookCount(@RequestBody List<String> users) {
		userService.decrementBookCount(users);
	}

}
