package com.gurpreet.monocept.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gurpreet.monocept.dto.StudentRequestDto;
import com.gurpreet.monocept.dto.StudentResponseDto;
import com.gurpreet.monocept.entity.Student;
import com.gurpreet.monocept.exception.StudentNotFoundException;
import com.gurpreet.monocept.repository.StudentRepository;
import com.gurpreet.monocept.service.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {

	private StudentService studentService;

	@Autowired
	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@PostMapping("/create")
	public StudentResponseDto createStudent(@RequestBody StudentRequestDto studentRequestDto) {
		return studentService.createStudent(studentRequestDto);
	}

	@PostMapping("/createMultiple")
	public List<StudentResponseDto> createBulkStudents(@RequestBody List<StudentRequestDto> studentRequestDtos) {
		return studentService.createBulkStudents(studentRequestDtos);
	}

	@GetMapping("/{id}")
	public StudentResponseDto getStudentById(@PathVariable int id) {
		return studentService.getStudentById(id);
	}

	@GetMapping("/getStudents")
	public List<StudentResponseDto> getAllStudents() {
		return studentService.getAllStudents();
	}

	@PutMapping("/{id}")
	public StudentResponseDto updateStudent(@PathVariable int id, @RequestBody StudentRequestDto updateStudentRequestDto) {
		return studentService.updateStudent(id, updateStudentRequestDto);

	}

	@PatchMapping("/{id}")
	public StudentResponseDto updateStudentPartially(@PathVariable int id, @RequestBody Map<String, Object> updateData) {
		return studentService.updateStudentPartially(id, updateData);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteStudent(@PathVariable int id) {
		studentService.deleteStudent(id);
		return ResponseEntity.noContent().build();
	}

}
