package com.epam.library.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(value = Include.NON_NULL)
public class BookDTO {
	
	private Integer bookId;
	@NotNull(message = "Book name is mandatory")
	private String bookName;
	@NotNull(message = "Publisher information is necessary")
	private String publisher;
	@NotNull(message = "Book Author is mandatory")
	private String bookAuthor;
	private Integer bookQuantity;
	
}

