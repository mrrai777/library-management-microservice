package com.epam.library.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.epam.library.dto.ErrorDTO;
import com.epam.library.exception.LibraryException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class LibraryExceptionHandler {

	private final ObjectMapper mapper;

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorDTO> handleRuntimeException(RuntimeException ex) {
		log.info("LibraryExceptionHandler (RuntimeException) : {}", ex.getMessage());
		
		ErrorDTO response = ErrorDTO.builder()
							.message(ex.getMessage())
							.timestamp(LocalDateTime.now())
							.build();

		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		List<String> info = ex.getBindingResult()
							.getFieldErrors()
							.stream()
							.map(x -> x.getDefaultMessage())
							.toList();

		log.info("LibraryExceptionHandler (MethodArgumentNotValidException) : {}", info);
		
		ErrorDTO response = ErrorDTO.builder()
							.message(info.toString())
							.timestamp(LocalDateTime.now())
							.build();
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(LibraryException.class)
	public ResponseEntity<ErrorDTO> handleRuntimeException(LibraryException ex) {
		log.info("LibraryExceptionHandler (LibraryException) : {}", ex.getMessage());
		
		ErrorDTO response = ErrorDTO.builder()
							.message(ex.getMessage())
							.timestamp(LocalDateTime.now())
							.build();
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(FeignException.class)
	public ResponseEntity<ErrorDTO> handleFeignException(FeignException ex) {
		log.info("LibraryExceptionHandler (FeignException) : {}", ex.getMessage());
		
		ResponseEntity<ErrorDTO> responseResult;
		ErrorDTO response;
		try {
			ErrorDTO errorResponse = mapper.readValue(ex.contentUTF8(), ErrorDTO.class);

			response = ErrorDTO.builder()
						.message(errorResponse.message())
						.timestamp(errorResponse.timestamp())
						.build();

			responseResult = new ResponseEntity<>(response, HttpStatus.valueOf(ex.status()));
			
		} catch (JsonProcessingException jsonException) {
			
			response = ErrorDTO.builder()
						.message(ex.getMessage())
						.timestamp(LocalDateTime.now())
						.build();

			responseResult = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseResult;
	}
}
