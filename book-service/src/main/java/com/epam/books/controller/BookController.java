package com.epam.books.controller;

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

import com.epam.books.dto.BookDTO;
import com.epam.books.exception.BookException;
import com.epam.books.service.api.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("books")
@RequiredArgsConstructor
public class BookController {

	private final BookService bookService;

	@GetMapping
	public ResponseEntity<List<BookDTO>> getBooks(@RequestParam(defaultValue = "0") Integer pageNumber,
			@RequestParam(defaultValue = "5") Integer pageSize) {
		log.info("Finding all books");
		return ResponseEntity.ok(bookService.findAllBooks(pageNumber, pageSize));
	}

	@GetMapping("{id}")
	public ResponseEntity<BookDTO> getBook(@PathVariable Integer id) throws BookException {
		log.info("Finding book by Id : {}", id);
		return ResponseEntity.ok(bookService.findBookById(id));
	}

	@PostMapping
	public ResponseEntity<BookDTO> createBook(@RequestBody @Valid BookDTO bookDto) {
		log.info("Save book : {}", bookDto);
		return new ResponseEntity<>(bookService.createBook(bookDto), HttpStatus.CREATED);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
		log.info("Deleting book, ID : {}", id);
		bookService.deleteBook(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("{id}")
	public ResponseEntity<BookDTO> updateBook(@PathVariable Integer id, @RequestBody @Valid BookDTO bookDto) {
		log.info("Updating book : {}", bookDto);
		return new ResponseEntity<>(bookService.updateBook(id, bookDto), HttpStatus.OK);
	}
}
