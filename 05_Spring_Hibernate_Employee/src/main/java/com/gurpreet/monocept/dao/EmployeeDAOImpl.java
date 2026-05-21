package com.gurpreet.monocept.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gurpreet.monocept.interfaces.EmployeeDAO;
import com.gurpreet.monocept.model.Employee;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class EmployeeDAOImpl implements EmployeeDAO {

	private EntityManager entityManager;

	@Autowired
	public EmployeeDAOImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Employee> readAllEmployee() {
		return entityManager.createQuery("from Employee", Employee.class).getResultList();
	}

	@Override
	public Employee readEmployeeById(int id) {
		return entityManager.find(Employee.class, id);
	}

	@Override
	public List<Employee> readEmployeeByName(String name) {
		return entityManager.createQuery("FROM Employee WHERE empName = :name", Employee.class)
				.setParameter("name", name).getResultList();
	}

	@Override
	public List<Employee> readEmployeeByAge(int age) {
		return entityManager.createQuery("FROM Employee WHERE empAge = :age", Employee.class).setParameter("age", age)
				.getResultList();
	}

	@Override
	public List<Employee> readEmployeeByCityCode(String cityCode) {
		return entityManager.createQuery("FROM Employee WHERE cityCode = :cityCode", Employee.class)
				.setParameter("cityCode", cityCode).getResultList();
	}

	@Override
	public List<Employee> readEmployeeSalaryMoreThan10000(double salary) {
		return entityManager.createQuery("FROM Employee WHERE empSalary > :salary", Employee.class)
				.setParameter("salary", salary).getResultList();
	}

	@Override
	public void createEmployee(Employee employee) {
		entityManager.persist(employee);

	}

	@Override
	public void updateEmployeeName(int id, String name) {
		entityManager.createQuery("update Employee set empName = :name where empId = :id").setParameter("name", name)
				.setParameter("id", id).executeUpdate();
	}

	@Override
	@Transactional
	public Employee updateEmployee(Employee employee) {
		return entityManager.merge(employee);
	}

	@Override
	public void deleteEmployee(int id) {
		Employee employee = readEmployeeById(id);
		if (employee != null) {
			entityManager.remove(employee);
		}
	}
	
	

}
