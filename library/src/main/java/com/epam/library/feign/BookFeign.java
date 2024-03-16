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
import org.springframework.web.bind.annotation.RequestParam;

import com.epam.library.dto.BookDTO;

import jakarta.validation.Valid;

@FeignClient(name = "bookservice")
public interface BookFeign {

	@GetMapping("/books")
	public ResponseEntity<List<BookDTO>> getBooks(@RequestParam(defaultValue = "0") Integer pageNumber,
			@RequestParam(defaultValue = "5") Integer pageSize);

	@GetMapping("/books/{id}")
	public ResponseEntity<BookDTO> getBook(@PathVariable Integer id);

	@PostMapping("/books")
	public ResponseEntity<BookDTO> createBook(@RequestBody @Valid BookDTO bookDto);

	@PutMapping("/books/{id}")
	public ResponseEntity<BookDTO> updateBook(@PathVariable Integer id, @RequestBody @Valid BookDTO bookDto);

	@DeleteMapping("/books/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable Integer id);

}
