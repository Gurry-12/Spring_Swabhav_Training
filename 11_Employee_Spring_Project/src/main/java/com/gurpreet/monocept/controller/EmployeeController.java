package com.gurpreet.monocept.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gurpreet.monocept.dto.EmployeeFullResponseDto;
import com.gurpreet.monocept.dto.EmployeeRequestDto;
import com.gurpreet.monocept.dto.EmployeeSummaryResponseDto;
import com.gurpreet.monocept.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	
	
	private EmployeeService employeeService;
	
	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public EmployeeSummaryResponseDto createEmployee(@Valid @RequestBody EmployeeRequestDto employeeRequestDto) {
		return employeeService.createEmployee(employeeRequestDto);
	}
	
	@GetMapping("/{id}")
	public EmployeeSummaryResponseDto readEmployeeSummaryById(@PathVariable int id) {
		return employeeService.readEmployeeSummaryById(id);
	}
	
	@GetMapping("/profile/{id}")
	public EmployeeFullResponseDto readEmployeeById(@PathVariable int id) {
		return employeeService.readEmployeeById(id);
	}
	

}
