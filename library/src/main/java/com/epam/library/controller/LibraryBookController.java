package com.epam.library.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.library.dto.BookDTO;
import com.epam.library.service.api.LibraryBookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/library/books")
@RequiredArgsConstructor
public class LibraryBookController {

	private final LibraryBookService libraryBookService;

	@GetMapping
	public ResponseEntity<List<BookDTO>> getBooks(@RequestParam(defaultValue = "0") Integer pageNumber,
			@RequestParam(defaultValue = "5") Integer pageSize) {
		return ResponseEntity.ok(libraryBookService.getBooks(pageNumber, pageSize));
	}

	@GetMapping("/{id}")
	public ResponseEntity<BookDTO> getBook(@PathVariable Integer id) {
		return ResponseEntity.ok(libraryBookService.getBook(id));
	}

	@PostMapping
	public ResponseEntity<BookDTO> createBook(@RequestBody @Valid BookDTO bookDto) {
		return new ResponseEntity<>(libraryBookService.createBook(bookDto), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<BookDTO> updateBook(@PathVariable Integer id, @RequestBody @Valid BookDTO bookDto) {
		return new ResponseEntity<>(libraryBookService.updateBook(id, bookDto), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
		libraryBookService.deleteBook(id);
		return ResponseEntity.noContent().build();
	}
}
