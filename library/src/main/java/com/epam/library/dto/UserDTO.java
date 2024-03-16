package com.epam.library.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(value = Include.NON_NULL)
public class UserDTO {

	@NotNull(message = "username is mandatory")
	private String username;
	
	@NotNull(message = "email is mandatory")
	private String email;
	
	@NotNull(message = "name is mandatory")
	private String name;
	
	private Integer bookBorrowed;
	
}