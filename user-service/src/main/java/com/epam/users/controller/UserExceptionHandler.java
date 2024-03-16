package com.epam.users.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.epam.users.dto.ErrorResponseDTO;
import com.epam.users.exception.BookException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class UserExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponseDTO> handleRuntimeException(RuntimeException e) {
		log.info("UserExceptionHandler (RuntimeException) : {}", e.getMessage());
		ErrorResponseDTO response = ErrorResponseDTO.builder().message(e.getMessage())
				.timestamp(LocalDateTime.now())
				.build();
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		List<String> message = e.getBindingResult().getFieldErrors()
				.stream()
				.map(x -> x.getDefaultMessage())
				.toList();

		log.info("UserExceptionHandler (MethodArgumentNotValidException) : ", message);
		ErrorResponseDTO response = ErrorResponseDTO.builder().message(message.toString())
				.timestamp(LocalDateTime.now())
				.build();

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(BookException.class)
	public ResponseEntity<ErrorResponseDTO> handleUserException(BookException e) {
		log.info("UserExceptionHandler (UserException)", e.getMessage());
		ErrorResponseDTO response = ErrorResponseDTO.builder().message(e.getMessage())
				.timestamp(LocalDateTime.now())
				.build();
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

}
