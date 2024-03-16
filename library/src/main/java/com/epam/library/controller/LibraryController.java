package com.epam.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.library.dto.LibraryDTO;
import com.epam.library.exception.LibraryException;
import com.epam.library.service.api.LibraryService;

@RestController
@RequestMapping("/library/users/{username}/books/{bookId}")
public class LibraryController {

	@Autowired
	private LibraryService libraryService;

	@PostMapping
	public ResponseEntity<LibraryDTO> issueBook(@PathVariable String username, @PathVariable Integer bookId)
			throws LibraryException {
		return new ResponseEntity<>(libraryService.issueBook(username, bookId), HttpStatus.CREATED);
	}

	@DeleteMapping
	public ResponseEntity<Void> releaseBook(@PathVariable String username, @PathVariable Integer bookId) {
		libraryService.releaseBook(username, bookId);
		return ResponseEntity.noContent().build();
	}

}
