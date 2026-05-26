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
	public Student createStudent(@RequestBody Student student) {
		return studentService.createStudent(student);
	}

	@PostMapping("/createMultiple")
	public List<Student> createBulkStudents(@RequestBody List<Student> students) {
		return studentService.createBulkStudents(students);
	}

	@GetMapping("/{id}")
	public Student getStudentById(@PathVariable int id) {
		return studentService.getStudentById(id);
	}

	@GetMapping("/getStudents")
	public List<Student> getAllStudents() {
		return studentService.getAllStudents();
	}

	@PutMapping("/{id}")
	public Student updateStudent(@PathVariable int id, @RequestBody Student updateStudent) {
		return studentService.updateStudent(id, updateStudent);

	}

	@PatchMapping("/{id}")
	public Student updateStudentPartially(@PathVariable int id, @RequestBody Map<String, Object> updateData) {
		return studentService.updateStudentPartially(id, updateData);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteStudent(@PathVariable int id) {
		studentService.deleteStudent(id);
		return ResponseEntity.noContent().build();
	}

}
