package com.gurpreet.monocept.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeFullResponseDto {

	@JsonProperty("name")
	private String employeeName;
	
	@JsonProperty("city")
	private String employeeCity;
	
	@JsonProperty("city_code")
	private String cityCode;
	
	@JsonProperty("salary")
	private double employeeSalary;
	
	@JsonProperty("age")
	private int employeeAge;
	
	@JsonProperty("email")
	private String employeeEmail;
	
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}
