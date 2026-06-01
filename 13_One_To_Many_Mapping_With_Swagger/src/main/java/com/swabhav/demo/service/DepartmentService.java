package com.swabhav.demo.service;

import java.util.List;

import com.swabhav.demo.dto.DepartmentRequestDto;
import com.swabhav.demo.dto.DepartmentResponseDto;
import com.swabhav.demo.dto.PageResponseDto;

public interface DepartmentService {
	
	DepartmentResponseDto createDepartment(DepartmentRequestDto departmentRequestDto);
	
	List<DepartmentResponseDto> getAllDepartments();
	
	PageResponseDto<DepartmentResponseDto> getAllDepartmentsWithPagination(int pageNumber, int pageSize);
	
	DepartmentResponseDto getDepartmentById(Long id);
	
	DepartmentResponseDto updateDepartment(Long id, DepartmentRequestDto departmentRequestDto);
	
	void deleteDepartment(Long id);

}
