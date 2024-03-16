package com.epam.library.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.epam.library.dto.UserDTO;

import jakarta.validation.Valid;

@FeignClient(name = "userservice")
public interface UserFeign {

	@GetMapping("/users")
	public ResponseEntity<List<UserDTO>> getUsers();

	@GetMapping("/users/{username}")
	public ResponseEntity<UserDTO> getUser(@PathVariable String username);

	@PostMapping("/users")
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDto);

	@PutMapping("/users/{username}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable String username, @Valid @RequestBody UserDTO userDto);

	@DeleteMapping("/users/{username}")
	public ResponseEntity<Void> deleteUser(@PathVariable String username);

	@PutMapping("/users/decrementBookCount")
	public ResponseEntity<Void> decrementUsersBookCount(List<String> users);

}
