package com.gurpreet.monocept.model;

import java.util.List;

public interface StudentDAO {

	void save(Student student);
	
	Student findStudentById(Integer id);
	
	
	List<Student> findAllStudent();
}
