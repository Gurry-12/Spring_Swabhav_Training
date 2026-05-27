package com.gurpreet.monocept.dto;

public class StudentResponseDto {

	private String fullName;
	private int age;
	private String department;

	public StudentResponseDto(String fullName, int age, String department) {
		this.fullName = fullName;
		this.age = age;
		this.department = department;
	}

	public StudentResponseDto() {
	}

	public String getFullName() {
		return fullName;
	}

	public int getAge() {
		return age;
	}

	public String getDepartment() {
		return department;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "StudentResponseDto [fullName=" + fullName + ", age=" + age + "]";
	}

	public void setDepartment(String department) {
		this.department = department;
	}

}
