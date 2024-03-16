package com.epam.users.dto;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record ErrorResponseDTO(String message, LocalDateTime timestamp) {

}
