package com.gurpreet.monocept.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "students")
public class Student {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "fullName", nullable = false, length = 100)
	@NotBlank(message = "Name can't be empty")
	@Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
	private String fullName;

	@Column(name = "age", nullable = false)
	@NotNull(message = "Age can't be null")
	@Min(value = 1, message = "Age must be at least 1")
	@Max(value = 100, message = "Age must be at most 100")
	private int age;

	@Column(name = "department", nullable = false, length = 50)
	@NotBlank(message = "Department can't be empty")
	@Size(max = 50, message = "Department name can't exceed 50 characters")
	private String department;

	public Student(String fullName, int age, String department) {
		this.fullName = fullName;
		this.age = age;
		this.department = department;
	}

	public Student() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "Student [Id=" + id + ", FullName=" + fullName + ", Age=" + age + ", Department=" + department + "]";
	}

}