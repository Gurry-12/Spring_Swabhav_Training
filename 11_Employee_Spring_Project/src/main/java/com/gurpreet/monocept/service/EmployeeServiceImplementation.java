package com.gurpreet.monocept.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gurpreet.monocept.dto.EmployeeFullResponseDto;
import com.gurpreet.monocept.dto.EmployeeRequestDto;
import com.gurpreet.monocept.dto.EmployeeSummaryResponseDto;
import com.gurpreet.monocept.entity.Employee;
import com.gurpreet.monocept.exception.EmployeeNotFoundException;
import com.gurpreet.monocept.repository.EmployeeRepository;

@Service
public class EmployeeServiceImplementation implements EmployeeService {

	private EmployeeRepository employeeRepository;
	private ModelMapper modelMapper;

	@Autowired
	public EmployeeServiceImplementation(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
		this.employeeRepository = employeeRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public EmployeeSummaryResponseDto createEmployee(EmployeeRequestDto employeeRequestDto) {

		Employee employee = modelMapper.map(employeeRequestDto, Employee.class);

		Employee retrivedEmployee = employeeRepository.save(employee);

		return modelMapper.map(retrivedEmployee, EmployeeSummaryResponseDto.class);
	}

	@Override
	public EmployeeSummaryResponseDto readEmployeeSummaryById(int id) {

		Employee employee = readById(id);

		return modelMapper.map(employee, EmployeeSummaryResponseDto.class);
	}

	@Override
	public EmployeeFullResponseDto readEmployeeById(int id) {
		Employee employee = readById(id);

		return modelMapper.map(employee, EmployeeFullResponseDto.class);
	}

	private Employee readById(int id) {
		return employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
	}

}
