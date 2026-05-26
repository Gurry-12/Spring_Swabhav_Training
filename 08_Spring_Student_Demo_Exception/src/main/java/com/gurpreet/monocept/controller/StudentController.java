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
import com.gurpreet.monocept.repository.StudentRespository;

@RestController
@RequestMapping("/api/students")
public class StudentController {

	private StudentRespository studentRepository;

	@Autowired
	public StudentController(StudentRespository studentRepository) {
		this.studentRepository = studentRepository;
	}

	@PostMapping("/create")
	public Student createStudent(@RequestBody Student student) {
		return studentRepository.save(student);
	}

	@PostMapping("/createMultiple")
	public List<Student> createMultipleStudents(@RequestBody List<Student> students) {
		return studentRepository.saveAll(students);
	}

	@GetMapping("/{id}")
	public Student readStudentById(@PathVariable int id) {
		Student foundStudent = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
		;
		return foundStudent;
	}

	@GetMapping("/getStudents")
	public List<Student> readAllStudents() {
		return studentRepository.findAll();
	}

	@PutMapping("/{id}")
	public Student updateExistingStudent(@PathVariable int id, @RequestBody Student updateStudent) {

		Student existingStudent = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));

		existingStudent.setFullName(updateStudent.getFullName());
		existingStudent.setAge(updateStudent.getAge());
		existingStudent.setDepartment(updateStudent.getDepartment());

		return studentRepository.save(existingStudent);

	}

	@PatchMapping("/{id}")
	public Student partiallyUpdateStudent(@PathVariable int id, @RequestBody Map<String, Object> updateData) {

		Student existingStudent = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));

		if (updateData.containsKey("fullName")) {
			existingStudent.setFullName((String) updateData.get("fullName"));
		}
		if (updateData.containsKey("age")) {
			existingStudent.setAge((Integer) updateData.get("age"));
		}
		if (updateData.containsKey("department")) {
			existingStudent.setDepartment((String) updateData.get("department"));
		}

		return studentRepository.save(existingStudent);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteExistingStudent(@PathVariable int id) {
		Student existingStudent = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));

		studentRepository.delete(existingStudent);
		return ResponseEntity.noContent().build();
	}

}
