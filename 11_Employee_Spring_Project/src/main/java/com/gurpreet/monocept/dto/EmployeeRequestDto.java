package com.gurpreet.monocept.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDto {

	@NotBlank(message = "Employee name cannot be blank")
	@Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
	@Pattern(regexp = "^[A-Za-z\\s.]+$", message = "Name can only contain alphabets, spaces, and dots")
	@JsonProperty("name")
	private String employeeName;

	@NotBlank(message = "City cannot be blank")
	@Size(min = 2, max = 100, message = "City name must be between 2 and 100 characters")
	@JsonProperty("city")
	private String employeeCity;

	@NotBlank(message = "City code cannot be blank")
	@Pattern(regexp = "^[A-Z]{2,4}\\d{2,4}$", message = "City code must match pattern (e.g., NYC01, MUM400)")
	@JsonProperty("city_code")
	private String cityCode;

	@Positive(message = "Salary must be greater than zero")
	@Min(value = 10000, message = "Minimum salary should be 10,000")
	@JsonProperty("salary")
	private double employeeSalary;

	@Min(value = 18, message = "Employee must be at least 18 years old")
	@Max(value = 65, message = "Employee age cannot exceed 65 years")
	@JsonProperty("age")
	private int employeeAge;

	@NotBlank(message = "Email cannot be blank")
	@Email(message = "Please provide a valid email address")
	@JsonProperty("email")
	private String employeeEmail;

	
}
