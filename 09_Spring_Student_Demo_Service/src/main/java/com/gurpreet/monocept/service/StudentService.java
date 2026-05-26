package com.gurpreet.monocept.service;

import java.util.List;
import java.util.Map;

import com.gurpreet.monocept.entity.Student;

public interface StudentService {

	Student createStudent(StudentRequestDto student);
	
	List<Student> createBulkStudents(List<Student> students);
	
	Student getStudentById(int id);
	
	List<Student> getAllStudents();
	
	Student updateStudent(int id, Student updateStudent);
	
	Student updateStudentPartially(int id, Map<String,Object> updateData );
	
	void deleteStudent(int id);
}
