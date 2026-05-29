package com.gurpreet.monocept.service;

import java.util.List;
import java.util.Map;

import com.gurpreet.monocept.dto.EmployeeFullResponseDto;
import com.gurpreet.monocept.dto.EmployeeRequestDto;
import com.gurpreet.monocept.dto.EmployeeSummaryResponseDto;
import com.gurpreet.monocept.dto.PageResponseDto;

public interface EmployeeService {
	
	EmployeeSummaryResponseDto createEmployee(EmployeeRequestDto employeeRequestDto);
	
	EmployeeSummaryResponseDto readEmployeeSummaryById(int id);
	
	EmployeeFullResponseDto readEmployeeById(int id);
	
	List<EmployeeSummaryResponseDto> createMultipleEmployees(List<EmployeeRequestDto> employeeRequestDtos);
	
	List<EmployeeFullResponseDto> readAllEmployees();
	
	EmployeeSummaryResponseDto updateEmployee(int id, EmployeeRequestDto employeeRequestDto);
	
	EmployeeSummaryResponseDto updateEmployeePartially( int id , Map<String, Object> updateData);
	
	void deleteEmployee(int id);
	
	PageResponseDto<EmployeeSummaryResponseDto> getEmployeeSummaryPagination(int pageNumber, int pageSize);
	
	PageResponseDto<EmployeeFullResponseDto> getEmployeeFullPagination(int pageNumber, int pageSize);
	
	
	
	

}
