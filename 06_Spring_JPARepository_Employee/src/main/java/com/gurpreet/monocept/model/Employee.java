package com.gurpreet.monocept.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee {

	@Id
	@Column(name = "emp_id")
	private int empId;

	@Column(name = "emp_name")
	private String empName;

	@Column(name = "emp_city")
	private String empCity;

	@Column(name = "city_code")
	private String cityCode;

	@Column(name = "emp_salary")
	private double empSalary;

	@Column(name = "emp_age")
	private int empAge;

	@Column(name = "emp_email")
	private String empEmail;

	// Default Constructor
	public Employee() {
	}

	// Parameterized Constructor
	public Employee(int empId, String empName, String empCity, String cityCode, double empSalary, int empAge,
			String empEmail) {
		this.empId = empId;
		this.empName = empName;
		this.empCity = empCity;
		this.cityCode = cityCode;
		this.empSalary = empSalary;
		this.empAge = empAge;
		this.empEmail = empEmail;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public void setEmpCity(String empCity) {
		this.empCity = empCity;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public void setEmpSalary(double empSalary) {
		this.empSalary = empSalary;
	}

	public void setEmpAge(int empAge) {
		this.empAge = empAge;
	}

	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}

	// Getters and Setters
	public int getEmpId() {
		return empId;
	}

	public String getEmpName() {
		return empName;
	}

	public String getEmpCity() {
		return empCity;
	}

	public String getCityCode() {
		return cityCode;
	}

	public double getEmpSalary() {
		return empSalary;
	}

	public int getEmpAge() {
		return empAge;
	}

	public String getEmpEmail() {
		return empEmail;
	}

	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", empName=" + empName 
				+ ", empCity=" + empCity + ", cityCode=" + cityCode
				+ ", empSalary=" + empSalary + ", empAge=" + empAge + 
				", empEmail=" + empEmail + "]";
	}

}