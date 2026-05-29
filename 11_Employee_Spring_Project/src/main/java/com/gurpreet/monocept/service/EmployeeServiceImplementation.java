package com.gurpreet.monocept.service;

import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gurpreet.monocept.dto.EmployeeFullResponseDto;
import com.gurpreet.monocept.dto.EmployeeRequestDto;
import com.gurpreet.monocept.dto.EmployeeSummaryResponseDto;
import com.gurpreet.monocept.dto.PageResponseDto;
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

	@Override
	public List<EmployeeSummaryResponseDto> createMultipleEmployees(List<EmployeeRequestDto> employeeRequestDtos) {

		return employeeRepository
				.saveAll(employeeRequestDtos.stream().map(dto -> modelMapper.map(dto, Employee.class)).toList())
				.stream().map(emp -> modelMapper.map(emp, EmployeeSummaryResponseDto.class)).toList();

	}

	@Override
	public List<EmployeeFullResponseDto> readAllEmployees() {

		return employeeRepository.findAll().stream().map(emp -> modelMapper.map(emp, EmployeeFullResponseDto.class))
				.toList();
	}

	@Override
	public EmployeeSummaryResponseDto updateEmployee(int id, EmployeeRequestDto employeeRequestDto) {
		Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));

		modelMapper.map(employeeRequestDto, employee);

		Employee updatedEmployee = employeeRepository.save(employee);

		return modelMapper.map(updatedEmployee, EmployeeSummaryResponseDto.class);
	}

	@Override
	public EmployeeSummaryResponseDto updateEmployeePartially(int id, Map<String, Object> updateData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteEmployee(int id) {
		// TODO Auto-generated method stub

	}

	@Override
	public PageResponseDto<EmployeeSummaryResponseDto> getEmployeeSummaryPagination(int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageResponseDto<EmployeeFullResponseDto> getEmployeeFullPagination(int pageNumber, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

}
