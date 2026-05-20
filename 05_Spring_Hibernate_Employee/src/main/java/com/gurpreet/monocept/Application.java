package com.gurpreet.monocept;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.gurpreet.monocept.interfaces.EmployeeDAO;
import com.gurpreet.monocept.model.Employee;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(EmployeeDAO employeeDAO) {

		return runner -> {

			// createEmployee(employeeDAO);

			// read
			readAllEmployees(employeeDAO);
			readEmployeeById(employeeDAO, 1);
//            readEmployeesByName(employeeDAO, "Gurpreet Singh");
//            readEmployeesByAge(employeeDAO, 28);
//            readEmployeesByCityCode(employeeDAO, "JP");
//            readEmployeesWithSalaryMoreThan10000(employeeDAO, 10000.0);
		};
	}

	private void readEmployeesWithSalaryMoreThan10000(EmployeeDAO employeeDAO, double salary) {
		System.out.println("Employee with salary more than " + salary);
		List<Employee> employees = employeeDAO.readEmployeeSalaryMoreThan10000(salary);
		for (Employee employee : employees) {
			System.out.println(employee);
		}

	}

	private void readEmployeesByCityCode(EmployeeDAO employeeDAO, String cityCode) {
		System.out.println("Employee with city code " + cityCode);
		List<Employee> employees = employeeDAO.readEmployeeByCityCode(cityCode);
		for (Employee employee : employees) {
			System.out.println(employee);
		}

	}

	private void readEmployeesByAge(EmployeeDAO employeeDAO, int age) {
		System.out.println("Employee ith age " + age);
		List<Employee> employees = employeeDAO.readEmployeeByAge(age);
		for (Employee employee : employees) {
			System.out.println(employee);
		}

	}

	private void readEmployeesByName(EmployeeDAO employeeDAO, String name) {
		System.out.println("Employee with name " + name);
		List<Employee> employees = employeeDAO.readEmployeeByName(name);
		for (Employee employee : employees) {
			System.out.println(employee);
		}

	}

	private void readEmployeeById(EmployeeDAO employeeDAO, int id) {
		System.out.println("Employee with Id " + id);
		Employee employee = employeeDAO.readEmployeeById(id);
		System.out.println(employee);

	}

	private void readAllEmployees(EmployeeDAO employeeDAO) {
		System.out.println("All Employees :");
		List<Employee> employees = employeeDAO.readAllEmployee();
		for (Employee employee : employees) {
			System.out.println(employee);
		}

	}

	private void createEmployee(EmployeeDAO employeeDAO) {

		System.out.println("create employee.");
		Employee employee = new Employee(1, "Gurpreet Singh", "Jaipur", "JP", 75000.0, 28, "gurpreet@example.com");

		employeeDAO.createEmployee(employee);
		System.out.println("Employee created.");

	}

}
