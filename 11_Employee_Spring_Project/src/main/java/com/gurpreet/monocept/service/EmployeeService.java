package com.gurpreet.monocept.service;

import com.gurpreet.monocept.dto.EmployeeFullResponseDto;
import com.gurpreet.monocept.dto.EmployeeRequestDto;
import com.gurpreet.monocept.dto.EmployeeSummaryResponseDto;

public interface EmployeeService {
	
	EmployeeSummaryResponseDto createEmployee(EmployeeRequestDto employeeRequestDto);
	
	EmployeeSummaryResponseDto readEmployeeSummaryById(int id);
	
	EmployeeFullResponseDto readEmployeeById(int id);
	
	

}
