package com.swabhav.demo.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentRequestDto {

	@JsonProperty("department_name")
	@NotBlank(message = "Department name is required")
	private String departmentName;
	
	@NotBlank(message = "Location is required")
	private String location;
	
	@Valid
	private List<EmployeeRequestDto> employees;
}
