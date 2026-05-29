package com.swabhav.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class StudentResponseDto {

	private Long id;
	
	@JsonProperty("full_name")
	private String fullName;

	private Integer age;

	private StudentProfileResponseDto profile;
}
