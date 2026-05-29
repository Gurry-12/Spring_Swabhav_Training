package com.swabhav.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StudentRequestDto {

	@JsonProperty("full_name")
	@NotBlank(message = "name can't be empty")
	private String fullName;

	@NotNull(message = "age can't be null")
	@Min(value = 1, message = "age can not be smaller than 1 ")
	private Integer age;

	@Valid
	@NotNull(message = "profile can not be null")
	private StudentProfileRequestDto profile;
	

}
