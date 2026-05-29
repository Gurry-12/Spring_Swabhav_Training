package com.swabhav.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.swabhav.demo.dto.PageResponseDto;
import com.swabhav.demo.dto.StudentProfileRequestDto;
import com.swabhav.demo.dto.StudentRequestDto;
import com.swabhav.demo.dto.StudentResponseDto;
import com.swabhav.demo.exception.DuplicateResourceException;
import com.swabhav.demo.exception.ResourceNotFoundException;
import com.swabhav.demo.model.Student;
import com.swabhav.demo.model.StudentProfile;
import com.swabhav.demo.repository.StudentProfileRepository;
import com.swabhav.demo.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

	private final StudentRepository studentRepository;
	private final StudentProfileRepository studentProfileRepository;
	private final ModelMapper modelMapper;

	@Override
	public StudentResponseDto createStudent(StudentRequestDto requestDto) {

		// check email exist or not -
		if (studentProfileRepository.existsByEmail(requestDto.getProfile().getEmail())) {
			throw new DuplicateResourceException("Duplicate email Id exist");
		}

		// map request dto to entity
		Student student = modelMapper.map(requestDto, Student.class);

		StudentProfile studentProfile = modelMapper.map(requestDto.getProfile(), StudentProfile.class);

		student.setProfile(studentProfile);
		studentProfile.setStudent(student);
		// save to repo
		Student retriveStudent = studentRepository.save(student);
		StudentProfile retrivedProfile = studentProfileRepository.save(studentProfile);

		// map to dto
		return modelMapper.map(retriveStudent, StudentResponseDto.class);
	}

	@Override
	public List<StudentResponseDto> getAllStudents() {

		List<Student> students = studentRepository.findAll();

		List<StudentResponseDto> studentResponseDtos = new ArrayList<>();

		for (Student student : students) {
			studentResponseDtos.add(modelMapper.map(student, StudentResponseDto.class));
		}

		return studentResponseDtos;
	}

	@Override
	public PageResponseDto<StudentResponseDto> getAllStudentsWithPagination(int pageNumber, int pageSize) {
		validatePagination(pageNumber, pageSize);

		// create page
		PageRequest pageable = PageRequest.of(pageNumber, pageSize);

		Page<Student> studentPage = studentRepository.findAll(pageable);

		List<Student> students = studentPage.getContent();

		List<StudentResponseDto> studentResponses = new ArrayList<>();

		for (Student s : students) {
			studentResponses.add(modelMapper.map(s, StudentResponseDto.class));
		}

		PageResponseDto<StudentResponseDto> pageResponseDto = new PageResponseDto<>();

		pageResponseDto.setContent(studentResponses);
		pageResponseDto.setPageNumber(studentPage.getNumber());
		pageResponseDto.setPageSize(studentPage.getSize());
		pageResponseDto.setTotalElements(studentPage.getTotalElements());
		pageResponseDto.setTotalPages(studentPage.getTotalPages());
		pageResponseDto.setLastPage(studentPage.isLast());

		return pageResponseDto;
	}

	@Override
	public StudentResponseDto getStudentById(Long id) {
		Student student = findStudentById(id);

		return modelMapper.map(student, StudentResponseDto.class);
	}

	@Override
	public StudentResponseDto updateStudent(Long id, StudentRequestDto requestDto) {

		// find student
		Student student = findStudentById(id);

		// find profile
		StudentProfile profile = student.getProfile();

		// check email
		if (studentProfileRepository.existsByEmailAndIdNot(requestDto.getProfile().getEmail(), profile.getId())) {
			throw new DuplicateResourceException("Duplicate email Id exist");
		}

		// student profile dto
		StudentProfileRequestDto dto = requestDto.getProfile();

		// set data
		student.setFullName(requestDto.getFullName());
		student.setAge(requestDto.getAge());

		updateProfile(profile, dto);

		Student retrivedStudent = studentRepository.save(student);

		return modelMapper.map(retrivedStudent, StudentResponseDto.class);

	}

	@Override
	public void deleteStudent(Long id) {

		Student student = findStudentById(id);

		studentRepository.delete(student);

	}

	// helper methods

	private Student findStudentById(Long id) {
		Student student = studentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found with id : " + id));

		return student;
	}

	private void updateProfile(StudentProfile profile, StudentProfileRequestDto profileRequestDto) {

		profile.setEmail(profileRequestDto.getEmail());
		profile.setPhone(profileRequestDto.getPhone());
		profile.setCity(profileRequestDto.getCity());
	}

	private void validatePagination(int pageNumber, int pageSize) {

		if (pageNumber < 0) {
			throw new IllegalArgumentException("page number can not be negative");
		}

		if (pageSize <= 0) {
			throw new IllegalArgumentException("page size can not be smaller than 1 ");
		}

		if (pageSize > 100) {
			throw new IllegalArgumentException("page size can not be greater than 100 ");
		}
	}

}
