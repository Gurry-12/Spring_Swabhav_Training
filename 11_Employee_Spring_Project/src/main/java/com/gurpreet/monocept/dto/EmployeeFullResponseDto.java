package com.gurpreet.monocept.dto;

import java.time.LocalDateTime;

public class EmployeeFullResponseDto {

	private String employeeName;
	private String employeeCity;
	private String cityCode;
	private double employeeSalary;
	private int employeeAge;
	private String employeeEmail;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public EmployeeFullResponseDto(String employeeName, String employeeCity, String cityCode, double employeeSalary,
			int employeeAge, String employeeEmail, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.employeeName = employeeName;
		this.employeeCity = employeeCity;
		this.cityCode = cityCode;
		this.employeeSalary = employeeSalary;
		this.employeeAge = employeeAge;
		this.employeeEmail = employeeEmail;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public EmployeeFullResponseDto() {
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
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

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

}
