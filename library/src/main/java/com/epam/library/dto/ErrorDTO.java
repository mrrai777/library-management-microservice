package com.epam.library.dto;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record ErrorDTO(String message, LocalDateTime timestamp) {

}
