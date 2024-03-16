package com.epam.books.dto;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record ErrorDTO(String message, LocalDateTime timestamp) {

}
