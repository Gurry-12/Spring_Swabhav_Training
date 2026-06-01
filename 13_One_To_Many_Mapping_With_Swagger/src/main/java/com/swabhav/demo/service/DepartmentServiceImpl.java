package com.swabhav.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.swabhav.demo.controller.DepartmentController;
import com.swabhav.demo.dto.DepartmentRequestDto;
import com.swabhav.demo.dto.DepartmentResponseDto;
import com.swabhav.demo.dto.EmployeeRequestDto;
import com.swabhav.demo.dto.PageResponseDto;
import com.swabhav.demo.exception.DuplicateResourceException;
import com.swabhav.demo.exception.ResourceNotFoundException;
import com.swabhav.demo.model.Department;
import com.swabhav.demo.model.Employee;
import com.swabhav.demo.repository.DepartmentRepository;
import com.swabhav.demo.repository.EmployeeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

	private static final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);

	private final ModelMapper modelMapper;
	private final DepartmentRepository departmentRepository;
	private final EmployeeRepository employeeRepository;

	@Override
	@Transactional
	public DepartmentResponseDto createDepartment(DepartmentRequestDto departmentRequestDto) {

		logger.info("Service request to create a new department: {}", departmentRequestDto.getDepartmentName());

		// 1. Department name must be unique.
		if (departmentRepository.existsByDepartmentName(departmentRequestDto.getDepartmentName())) {
			logger.warn("Creation failed: Department name '{}' already exists",
					departmentRequestDto.getDepartmentName());
			throw new DuplicateResourceException(
					"Duplicate department name found. - " + departmentRequestDto.getDepartmentName());
		}

		// 2. At least one employee must be provided.
		if (departmentRequestDto.getEmployees() == null) {
			logger.warn("Creation failed: No employees provided for the new department");
			throw new ResourceNotFoundException("Atleast One employee exist. ");
		}

		// 4. Employee emails must be unique.
		validateEmployeeEmailsForCreate(departmentRequestDto.getEmployees());

		// 4. Each employee must be linked to the department.
		Department department = attachEmployeesToDepartment(departmentRequestDto);

		// 5. Department and employees must be saved together.
		Department savedDepartment = departmentRepository.save(department);

		logger.info("Successfully persisted new Department with ID: {}", savedDepartment.getId());
		// 6. Response must return department details with employee details.
		return modelMapper.map(savedDepartment, DepartmentResponseDto.class);
	}

	@Override
	public List<DepartmentResponseDto> getAllDepartments() {
		logger.info("Service request to retrieve all departments");
		List<Department> departments = departmentRepository.findAll();

		return departments.stream().map(department -> modelMapper.map(department, DepartmentResponseDto.class))
				.toList();
	}

	@Override
	public PageResponseDto<DepartmentResponseDto> getAllDepartmentsWithPagination(int pageNumber, int pageSize) {
		validatePagination(pageNumber, pageSize);

		// create page
		PageRequest pageable = PageRequest.of(pageNumber, pageSize);

		Page<Department> departmentPage = departmentRepository.findAll(pageable);

		List<Department> departments = departmentPage.getContent();

		List<DepartmentResponseDto> departmentResponses = new ArrayList<>();

		for (Department s : departments) {
			departmentResponses.add(modelMapper.map(s, DepartmentResponseDto.class));
		}

		PageResponseDto<DepartmentResponseDto> pageResponseDto = new PageResponseDto<>();

		pageResponseDto.setContent(departmentResponses);
		pageResponseDto.setPageNumber(departmentPage.getNumber());
		pageResponseDto.setPageSize(departmentPage.getSize());
		pageResponseDto.setTotalElements(departmentPage.getTotalElements());
		pageResponseDto.setTotalPages(departmentPage.getTotalPages());
		pageResponseDto.setLastPage(departmentPage.isLast());

		return pageResponseDto;

	}

	@Override
	public DepartmentResponseDto getDepartmentById(Long id) {

		Department department = findDepartmentById(id);

		return modelMapper.map(department, DepartmentResponseDto.class);
	}

	@Override
	@Transactional
	public DepartmentResponseDto updateDepartment(Long id, DepartmentRequestDto departmentRequestDto) {
		logger.info("Service request to update Department ID: {}", id);
		// 1. Department must exist.
		Department existingDepartment = findDepartmentById(id);

		// 2. New department name must not belong to another department.
		if (departmentRepository.existsByDepartmentNameAndIdNot(departmentRequestDto.getDepartmentName(), id)) {
			logger.warn("Update failed: Department name '{}' is already claimed by another department entity",
					departmentRequestDto.getDepartmentName());
			throw new DuplicateResourceException("Department name already exists in another department: "
					+ departmentRequestDto.getDepartmentName());
		}

		// 3. Employee emails must not duplicate existing employees outside the current
		// department.
		validateEmployeeEmailsForUpdate(departmentRequestDto.getEmployees(), id);

		// 4. Department name must be updated.
		existingDepartment.setDepartmentName(departmentRequestDto.getDepartmentName());

		// 5. Department location must be updated.
		existingDepartment.setLocation(departmentRequestDto.getLocation());

		// 6. Employee list must be updated.
		List<Employee> incomingEmployees = departmentRequestDto.getEmployees().stream().map(dto -> {
			Employee emp = modelMapper.map(dto, Employee.class);
			emp.setDepartment(existingDepartment); // 8. Link to the department
			return emp;
		}).collect(Collectors.toList());
		// 7. Old employees not present in the new list must be removed.
		existingDepartment.getEmployees().clear();
		existingDepartment.getEmployees().addAll(incomingEmployees);

		// 8. New employees must be linked to the same department.
		Department updatedDepartment = departmentRepository.save(existingDepartment);

		logger.info("Successfully updated Department records for ID: {}", id);
		// 9. Response must return updated department details.
		return modelMapper.map(updatedDepartment, DepartmentResponseDto.class);
	}

	@Override
	@Transactional
	public void deleteDepartment(Long id) {
		logger.info("Service request to delete Department ID: {}", id);
		Department department = findDepartmentById(id);

		departmentRepository.delete(department);
		logger.info("Successfully deleted Department with ID: {}", id);

	}

	private Department findDepartmentById(Long id) {
		return departmentRepository.findById(id).orElseThrow(() -> {
			logger.warn("Resource tracking error: Department not found with ID: {}", id);
			return new ResourceNotFoundException("Department not found with id - . " + id);
		});
	}

	private Department attachEmployeesToDepartment(DepartmentRequestDto departmentRequestDto) {
		Department department = modelMapper.map(departmentRequestDto, Department.class);

		if (department.getEmployees() != null) {
			for (Employee employee : department.getEmployees()) {
				employee.setDepartment(department);
			}
		}
		return department;
	}

	private void validateEmployeeEmailsForCreate(List<EmployeeRequestDto> employeeRequestDtos) {

		for (EmployeeRequestDto dto : employeeRequestDtos) {

			if (employeeRepository.existsByEmail(dto.getEmail())) {
				logger.warn("Email validation failure: Conflict found with registered email address '{}'",
						dto.getEmail());
				throw new DuplicateResourceException("Duplicate employee email exist. - " + dto.getEmail());
			}
		}
	}

	private void validateEmployeeEmailsForUpdate(List<EmployeeRequestDto> employeeRequestDtos,
			Long currentDepartmentId) {
		if (employeeRequestDtos == null)
			return;

		for (EmployeeRequestDto dto : employeeRequestDtos) {

			if (employeeRepository.existsByEmailAndIdNot(dto.getEmail(), currentDepartmentId)) {
				logger.warn(
						"Email validation failure: Email '{}' is actively registered to an employee in a different department",
						dto.getEmail());
				throw new DuplicateResourceException("Email is already taken by another department: " + dto.getEmail());
			}
		}
	}

	private void validatePagination(int pageNumber, int pageSize) {
		if (pageNumber < 0) {
			logger.warn("Pagination parameter violation: Received negative pageNumber value ({})", pageNumber);
			throw new IllegalArgumentException("Page number must not be negative");
		}

		if (pageSize <= 0) {
			logger.warn("Pagination parameter violation: Received zero or negative pageSize value ({})", pageSize);
			throw new IllegalArgumentException("Page size must be greater than zero");
		}

		if (pageSize > 100) {
			logger.warn(
					"Pagination parameter violation: Requested pageSize value ({}) exceeds safety limit threshold of 100",
					pageSize);
			throw new IllegalArgumentException("Page size must not exceed 100 ");
		}
	}
}
