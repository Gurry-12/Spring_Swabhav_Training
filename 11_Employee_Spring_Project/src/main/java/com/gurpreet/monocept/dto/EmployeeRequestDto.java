package com.gurpreet.monocept.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class EmployeeRequestDto {

	@NotBlank(message = "Employee name cannot be blank")
	@Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
	@Pattern(regexp = "^[A-Za-z\\s.]+$", message = "Name can only contain alphabets, spaces, and dots")
	private String employeeName;

	@NotBlank(message = "City cannot be blank")
	@Size(min = 2, max = 100, message = "City name must be between 2 and 100 characters")
	private String employeeCity;

	@NotBlank(message = "City code cannot be blank")
	@Pattern(regexp = "^[A-Z]{2,4}\\d{2,4}$", message = "City code must match pattern (e.g., NYC01, MUM400)")
	private String cityCode;

	@Positive(message = "Salary must be greater than zero")
	@Min(value = 10000, message = "Minimum salary should be 10,000")
	private double employeeSalary;

	@Min(value = 18, message = "Employee must be at least 18 years old")
	@Max(value = 65, message = "Employee age cannot exceed 65 years")
	private int employeeAge;

	@NotBlank(message = "Email cannot be blank")
	@Email(message = "Please provide a valid email address")
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Email format is invalid")
	private String employeeEmail;

	public EmployeeRequestDto(String employeeName, String employeeCity, String cityCode, double employeeSalary,
			int employeeAge, String employeeEmail) {
		this.employeeName = employeeName;
		this.employeeCity = employeeCity;
		this.cityCode = cityCode;
		this.employeeSalary = employeeSalary;
		this.employeeAge = employeeAge;
		this.employeeEmail = employeeEmail;
	}

	public EmployeeRequestDto() {
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public String getEmployeeCity() {
		return employeeCity;
	}

	public String getCityCode() {
		return cityCode;
	}

	public double getEmployeeSalary() {
		return employeeSalary;
	}

	public int getEmployeeAge() {
		return employeeAge;
	}

	public String getEmployeeEmail() {
		return employeeEmail;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public void setEmployeeCity(String employeeCity) {
		this.employeeCity = employeeCity;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public void setEmployeeSalary(double employeeSalary) {
		this.employeeSalary = employeeSalary;
	}

	public void setEmployeeAge(int employeeAge) {
		this.employeeAge = employeeAge;
	}

	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}

}
