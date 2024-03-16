package com.epam.books.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.epam.books.dto.ErrorDTO;
import com.epam.books.exception.BookException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class BookExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorDTO> handleRuntimeException(RuntimeException e) {
		log.info("BookExceptionHandler (RuntimeException) : {}", e.getMessage());
		ErrorDTO response = ErrorDTO.builder()
				.message(e.getMessage())
				.timestamp(LocalDateTime.now())
				.build();
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		List<String> info = e.getBindingResult()
				.getFieldErrors().stream()
				.map(x -> x.getDefaultMessage())
				.toList();
		
		log.info("BookExceptionHandler (MethodArgumentNotValidException) : {}", info);
		ErrorDTO response = ErrorDTO.builder()
				.message(info.toString())
				.timestamp(LocalDateTime.now())
				.build();
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(BookException.class)
	public ResponseEntity<ErrorDTO> handleRuntimeException(BookException e) {
		log.info("BookExceptionHandler (BookException) : {}", e.getMessage());
		ErrorDTO response = ErrorDTO.builder()
				.message(e.getMessage())
				.timestamp(LocalDateTime.now())
				.build();
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

}
