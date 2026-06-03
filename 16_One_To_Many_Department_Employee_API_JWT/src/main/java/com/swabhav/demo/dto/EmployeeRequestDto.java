package com.swabhav.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDto {

	@JsonProperty("employee_name")
	@NotBlank(message = "Employee name is required")
	private String employeeName;
	
	@NotBlank(message = "Employee email is required")
	@Email(message = "Employee email is not in required format")
	private String email;
	
	@Min(value = 0 , message = "salary can't be negative")
	private Double salary;
	
}
