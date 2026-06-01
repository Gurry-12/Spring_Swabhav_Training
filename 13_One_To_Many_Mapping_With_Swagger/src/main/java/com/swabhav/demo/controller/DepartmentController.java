package com.swabhav.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.swabhav.demo.dto.DepartmentRequestDto;
import com.swabhav.demo.dto.DepartmentResponseDto;
import com.swabhav.demo.dto.PageResponseDto;
import com.swabhav.demo.service.DepartmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@SecurityRequirement(name = "basicAuth")
@Tag(name = "Department API", description = "Manage Department crud role based ")
public class DepartmentController {

	private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);
	private final DepartmentService departmentService;

	@PostMapping
	@ResponseStatus(HttpStatus.ACCEPTED)
	@Operation(summary = "Create Department", description = "Returns a created department .")
	public DepartmentResponseDto createDepartment(@Valid @RequestBody DepartmentRequestDto departmentRequestDto) {
		logger.info("REST request to create Department with name: {}", departmentRequestDto.getDepartmentName());
		DepartmentResponseDto response = departmentService.createDepartment(departmentRequestDto);
		logger.info("Successfully created Department with ID: {}", response.getId());
		return response;
	}

	@GetMapping
	@Operation(summary = "Get all Departments", description = "Returns a list of department objects.")
	public List<DepartmentResponseDto> getAllDepartments() {
		List<DepartmentResponseDto> departmentResponseDtos = departmentService.getAllDepartments();
		logger.info("Successfully fetched {} departments", departmentResponseDtos.size());
		return departmentResponseDtos;
	}

	@GetMapping("/page")
	@Operation(summary = "Get deartments by pagination", description = "Returns list of departments with pagination data.")
	public PageResponseDto<DepartmentResponseDto> getAllDepartmentsWithPagination(
			@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "5") int pageSize) {
		PageResponseDto<DepartmentResponseDto> pageResponse = departmentService
				.getAllDepartmentsWithPagination(pageNumber, pageSize);
		logger.info("Fetched page metadata - Total Elements: {}, Total Pages: {}", pageResponse.getTotalElements(),
				pageResponse.getTotalPages());
		return pageResponse;
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get depatment by ID", description = "Returns a single department object.")
	public DepartmentResponseDto getDepartmentById(@PathVariable Long id) {
		logger.info("REST request to fetch Department by ID: {}", id);
		return departmentService.getDepartmentById(id);
	}

	@PutMapping("/{id}")
	@Operation(summary = "update department by ID", description = "Returns a updated department object.")
	public DepartmentResponseDto updateDepartment(@PathVariable Long id,
			@Valid @RequestBody DepartmentRequestDto departmentRequestDto) {
		logger.info("REST request to update Department ID: {}", id);
		DepartmentResponseDto response = departmentService.updateDepartment(id, departmentRequestDto);
		logger.info("Successfully updated Department ID: {}", id);
		return response;
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "delete department by ID", description = "Returns nothing only 203 status code .")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteDepartment(@PathVariable Long id) {
		logger.info("REST request to delete Department ID: {}", id);
		departmentService.deleteDepartment(id);
		logger.info("Successfully deleted Department ID: {}", id);
	}

}
