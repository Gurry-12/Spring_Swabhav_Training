package com.gurpreet.monocept.service;

import java.util.List;
import java.util.Map;

import com.gurpreet.monocept.dto.PageResponseDto;
import com.gurpreet.monocept.dto.StudentRequestDto;
import com.gurpreet.monocept.dto.StudentResponseDto;
import com.gurpreet.monocept.entity.Student;

public interface StudentService {

	StudentResponseDto createStudent(StudentRequestDto student);
	
	List<StudentResponseDto> createBulkStudents(List<StudentRequestDto> students);
	
	StudentResponseDto getStudentById(int id);
	
	List<StudentResponseDto> getAllStudents();
	
	StudentResponseDto updateStudent(int id, StudentRequestDto updateStudent);
	
	StudentResponseDto updateStudentPartially(int id, Map<String,Object> updateData );
	
	void deleteStudent(int id);
	
	PageResponseDto<StudentResponseDto> getAllStudentsWithPagination(int pageNumber, int pageSize);
}
