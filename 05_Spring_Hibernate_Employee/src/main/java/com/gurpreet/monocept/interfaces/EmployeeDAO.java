package com.gurpreet.monocept.interfaces;

import java.util.List;

import com.gurpreet.monocept.model.Employee;

public interface EmployeeDAO {

//	Read all employee
	List<Employee> readAllEmployee();

//	read by id
	Employee readEmployeeById(int id);

//	read by name
	List<Employee> readEmployeeByName(String name);

//	read by age
	List<Employee> readEmployeeByAge(int age);

//	read by city_code
	List<Employee> readEmployeeByCityCode(String cityCode);

//	employee where salary is more than 10000
	List<Employee> readEmployeeSalaryMoreThan10000(double salary);

// create employee
	void createEmployee(Employee employee);

}
