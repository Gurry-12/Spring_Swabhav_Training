package com.swabhav.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data 
public class StudentProfileRequestDto {

	@NotBlank(message = "email can't be empty. ")
	@Email(message = "Invalid email format")
	private String email;
	
	@NotBlank(message = "phone can't be empty.")
	private String phone;
	
	@NotBlank(message = "city can't be empty.")
	private String city;
}

