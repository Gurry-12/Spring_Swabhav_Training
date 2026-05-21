package com.gurpreet.monocept.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gurpreet.monocept.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	List<Employee> findByEmpName(String empName);
	
	List<Employee> findByEmpAge(int age);
	
	List<Employee> findByCityCode(String code);
	
	List<Employee> findByEmpSalaryGreaterThan(double salary);
	
	List<Employee> findByEmpSalaryLessThan(double salary);

	List<Employee> findByEmpAgeBetween(int initialAge, int endAge);
	
	
	
}

