package com.gurpreet.monocept.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gurpreet.monocept.entity.Employee;

public interface EmployeeRepository  extends JpaRepository<Employee, Integer>{

}
