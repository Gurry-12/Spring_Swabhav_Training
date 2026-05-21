package com.gurpreet.monocept;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.gurpreet.monocept.model.Employee;
import com.gurpreet.monocept.repository.EmployeeRepository;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(EmployeeRepository employeeRepository) {

		return runner -> {

//			 createEmployee(employeeRepository);

			// readAllEmployees(employeeRepository);

			// readEmployeeById(employeeRepository);

			// readEmployeeByName(employeeRepository);

			// readEmployeeByAge(employeeRepository);

			// readEmployeeByCode(employeeRepository);

			// readEmployeeBySalaryGreaterThanGiven(employeeRepository);

			// readEmployeeBySalarySmallerThanGiven(employeeRepository);

			// updateEmployee(employeeRepository);

			// deleteEmployee(employeeRepository);

			 filterEmplyeeBeteenAge(employeeRepository, 20, 40 );

		};
	}

	private void filterEmplyeeBeteenAge(EmployeeRepository employeeRepository, int startAge, int endAge) {
		List<Employee> employees = employeeRepository.findByEmpAgeBetween(startAge, endAge);

		for (Employee employee : employees) {
			System.out.println(employee);
		}

	}

	private void deleteEmployee(EmployeeRepository employeeRepository) {
		employeeRepository.deleteById(2);

	}

	private void updateEmployee(EmployeeRepository employeeRepository) {

		Optional<Employee> employee = employeeRepository.findById(1);
		if (employee != null) {
			Employee emp1 = new Employee(1, "Aarav Sharma", "Mumbai", "MH01", 12000.0, 34, "aarav.sharma@example.in");

			employeeRepository.save(emp1);
		}

	}

	private void readEmployeeBySalarySmallerThanGiven(EmployeeRepository employeeRepository) {
		List<Employee> employees = employeeRepository.findByEmpSalaryLessThan(100000);

		for (Employee employee : employees) {
			System.out.println(employee);
		}

	}

	private void readEmployeeBySalaryGreaterThanGiven(EmployeeRepository employeeRepository) {
		List<Employee> employees = employeeRepository.findByEmpSalaryGreaterThan(10000);

		for (Employee employee : employees) {
			System.out.println(employee);
		}

	}

	private void readEmployeeByCode(EmployeeRepository employeeRepository) {
		List<Employee> employees = employeeRepository.findByCityCode("MH01");

		for (Employee employee : employees) {
			System.out.println(employee);
		}

	}

	private void readEmployeeByAge(EmployeeRepository employeeRepository) {
		List<Employee> employees = employeeRepository.findByEmpAge(34);

		for (Employee employee : employees) {
			System.out.println(employee);
		}

	}

	private void readEmployeeByName(EmployeeRepository employeeRepository) {
		List<Employee> employees = employeeRepository.findByEmpName("Aarav Sharma");

		for (Employee employee : employees) {
			System.out.println(employee);
		}

	}

	private void readEmployeeById(EmployeeRepository employeeRepository) {
		Optional<Employee> employee = employeeRepository.findById(1);

		System.out.println(employee);

	}

	private void readAllEmployees(EmployeeRepository employeeRepository) {
		List<Employee> employees = employeeRepository.findAll();

		for (Employee employee : employees) {
			System.out.println(employee);
		}

	}

	private void createEmployee(EmployeeRepository employeeRepository) {

		System.out.println("Creating employee data ");

		Employee emp1 = new Employee(1, "Aarav Sharma", "Mumbai", "MH01", 125000.0, 34, "aarav.sharma@company.in");
		Employee emp2 = new Employee(2, "Ananya Iyer", "Bengaluru", "KA03", 98000.0, 28, "ananya.iyer@company.in");
		Employee emp3 = new Employee(3, "Vihaan Verma", "Delhi", "DL02", 85000.0, 41, "vihaan.verma@company.in");
		Employee emp4 = new Employee(4, "Diya Reddy", "Hyderabad", "TS09", 45000.0, 22, "diya.reddy@company.in");
		Employee emp5 = new Employee(5, "Arjun Nair", "Kochi", "KL07", 92000.0, 31, "arjun.nair@company.in");

		employeeRepository.save(emp1);
	}

}
