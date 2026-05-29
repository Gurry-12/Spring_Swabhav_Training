package com.swabhav.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.swabhav.demo.dto.PageResponseDto;
import com.swabhav.demo.dto.StudentRequestDto;
import com.swabhav.demo.dto.StudentResponseDto;
import com.swabhav.demo.service.StudentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@SecurityRequirement(name = "basicAuth")
@Tag(name = "Student API", description = "Manage student crud role based ")
public class StudentController {

	private final StudentService studentService;

	@PostMapping
	@ResponseStatus(HttpStatus.ACCEPTED)
	@Operation(summary = "Create Student", description = "Returns a created student .")
	public StudentResponseDto createStudent(@Valid @RequestBody StudentRequestDto studentRequestDto) {
		return studentService.createStudent(studentRequestDto);
	}

	@GetMapping
	@Operation(summary = "Get all Students", description = "Returns a list of student objects.")
	public List<StudentResponseDto> getAllStudents() {
		return studentService.getAllStudents();
	}

	@GetMapping("/page")
	@Operation(summary = "Get students by pagination", description = "Returns list of students with pagination data.")
	public PageResponseDto<StudentResponseDto> getAllStudentsWithPagination(@RequestParam(defaultValue = "0") int pageNumber,
			@RequestParam(defaultValue = "5") int pageSize) {
		return studentService.getAllStudentsWithPagination(pageNumber, pageSize);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get student by ID", description = "Returns a single student object.")
	public StudentResponseDto getStudentById(@PathVariable Long id) {
		return studentService.getStudentById(id);
	}

	@PutMapping("/{id}")
	@Operation(summary = "update student by ID", description = "Returns a updated student object.")
	public StudentResponseDto updateStudent(@PathVariable Long id,
			@Valid @RequestBody StudentRequestDto studentRequestDto) {
		return studentService.updateStudent(id, studentRequestDto);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "delete student by ID", description = "Returns nothing only 203 status code .")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteStudent(@PathVariable Long id) {
		studentService.deleteStudent(id);
	}

}
