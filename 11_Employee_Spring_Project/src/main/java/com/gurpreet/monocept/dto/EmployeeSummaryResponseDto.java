package com.gurpreet.monocept.dto;

import java.time.LocalDateTime;

public class EmployeeSummaryResponseDto {

	private String employeeName;
	private int employeeAge;
	private String employeeEmail;
	private LocalDateTime createdAt;
	public EmployeeSummaryResponseDto(String employeeName, int employeeAge, String employeeEmail,
			LocalDateTime createdAt) {
		this.employeeName = employeeName;
		this.employeeAge = employeeAge;
		this.employeeEmail = employeeEmail;
		this.createdAt = createdAt;
	}
	
	public EmployeeSummaryResponseDto() {
	}

	public String getEmployeeName() {
		return employeeName;
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

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
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
	
	
}

