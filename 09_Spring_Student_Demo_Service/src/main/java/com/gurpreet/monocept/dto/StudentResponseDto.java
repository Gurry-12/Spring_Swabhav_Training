package com.gurpreet.monocept.dto;

public class StudentResponseDto {
	
	private int id;
	private String fullName;
	private int age;
	private String department;

	

	public StudentResponseDto(int id, String fullName, int age, String department) {
		super();
		this.id = id;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
