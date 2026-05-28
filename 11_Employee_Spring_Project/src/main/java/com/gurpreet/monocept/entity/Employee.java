package com.gurpreet.monocept.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "employees")
public class Employee {

	@Id
	@Column(name = "employee_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int employeeId;

	@Column(name = "employee_name", nullable = false, length = 100)
	@NotBlank(message = "Employee name cannot be blank")
	@Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
	@Pattern(regexp = "^[A-Za-z\\s.]+$", message = "Name can only contain alphabets, spaces, and dots")
	private String employeeName;

	@Column(name = "employee_city", nullable = false, length = 100)
	@NotBlank(message = "City cannot be blank")
	@Size(min = 2, max = 100, message = "City name must be between 2 and 100 characters")
	private String employeeCity;

	@Column(name = "city_code", nullable = false, length = 10)
	@NotBlank(message = "City code cannot be blank")
	@Pattern(regexp = "^[A-Z]{2,4}\\d{2,4}$", message = "City code must be alphanumeric (e.g., NYC01, MUM400)")
	private String cityCode;

	@Column(name = "employee_salary", nullable = false)
	@Positive(message = "Salary must be greater than zero")
	@Min(value = 10000, message = "Minimum salary should be 10,000")
	private double employeeSalary;

	@Column(name = "employee_age", nullable = false)
	@Min(value = 18, message = "Employee must be at least 18 years old")
	@Max(value = 65, message = "Employee age cannot exceed 65 years")
	private int employeeAge;

	@Column(name = "employee_email", nullable = false, unique = true)
	@NotBlank(message = "Email cannot be blank")
	@Email(message = "Please provide a valid email address")
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Email format is invalid")
	private String employeeEmail;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	public Employee(int employeeId, String employeeName, String employeeCity, String cityCode, double employeeSalary,
			int employeeAge, String employeeEmail, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.employeeCity = employeeCity;
		this.cityCode = cityCode;
		this.employeeSalary = employeeSalary;
		this.employeeAge = employeeAge;
		this.employeeEmail = employeeEmail;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public Employee() {
	}

	public int getEmployeeId() {
		return employeeId;
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

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
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

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", employeeName=" + employeeName + ", employeeCity="
				+ employeeCity + ", cityCode=" + cityCode + ", employeeSalary=" + employeeSalary + ", employeeAge="
				+ employeeAge + ", employeeEmail=" + employeeEmail + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + "]";
	}

}
