package com.gurpreet.monocept.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.gurpreet.monocept.dto.PageResponseDto;
import com.gurpreet.monocept.dto.StudentRequestDto;
import com.gurpreet.monocept.dto.StudentResponseDto;
import com.gurpreet.monocept.entity.Student;
import com.gurpreet.monocept.exception.StudentNotFoundException;
import com.gurpreet.monocept.repository.StudentRepository;

@Service
public class StudentServiceImplementation implements StudentService {

	private StudentRepository studentRepository;
	private ModelMapper modelMapper;

	@Autowired
	public StudentServiceImplementation(StudentRepository studentRepository, ModelMapper modelMapper) {
		this.studentRepository = studentRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public StudentResponseDto createStudent(StudentRequestDto studentRequestDto) {

		Student student = modelMapper.map(studentRequestDto, Student.class);

		Student retrivedStudent = studentRepository.save(student);

		return modelMapper.map(retrivedStudent, StudentResponseDto.class);

	}

	@Override
	public List<StudentResponseDto> createBulkStudents(List<StudentRequestDto> studentRequestDtos) {

		if(studentRequestDtos == null || studentRequestDtos.isEmpty()) {
			throw new IllegalArgumentException("Student List can't be empty");
		}
		
		List<Student> students = new ArrayList<Student>();
		for (StudentRequestDto dto : studentRequestDtos) {
			students.add(modelMapper.map(dto, Student.class));
		}

		List<Student> retrivedStudents = studentRepository.saveAll(students);

		List<StudentResponseDto> studentResponseDtos = new ArrayList<StudentResponseDto>();
		for (Student student : retrivedStudents) {
			studentResponseDtos.add(modelMapper.map(student, StudentResponseDto.class));
		}

		return studentResponseDtos;
	}

	@Override
	public StudentResponseDto getStudentById(int id) {
		
		if(id < 0) {
			throw new IllegalArgumentException("Id can't be negative");
		}
		Student foundStudent = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));

		return modelMapper.map(foundStudent, StudentResponseDto.class);
	}

	@Override
	public List<StudentResponseDto> getAllStudents() {

		List<Student> retrivedStudents = studentRepository.findAll();

		List<StudentResponseDto> studentResponseDtos = new ArrayList<StudentResponseDto>();
		for (Student student : retrivedStudents) {
			studentResponseDtos.add(modelMapper.map(student, StudentResponseDto.class));
		}

		return studentResponseDtos;
	}

	@Override
	public StudentResponseDto updateStudent(int id, StudentRequestDto updateStudentRequestDto) {

		if(id < 0) {
			throw new IllegalArgumentException("Id can't be negative");
		}
		
		
		Student updateStudent = modelMapper.map(updateStudentRequestDto, Student.class);
		
		Student existingStudent = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));

		existingStudent.setFullName(updateStudent.getFullName());
		existingStudent.setAge(updateStudent.getAge());
		existingStudent.setDepartment(updateStudent.getDepartment());

		Student updatedStudent = studentRepository.save(existingStudent);

		return modelMapper.map(updatedStudent, StudentResponseDto.class);
	}

	@Override
	public StudentResponseDto updateStudentPartially(int id, Map<String, Object> updateData) {
		
		if(id < 0) {
			throw new IllegalArgumentException("Id can't be negative");
		}
		
		if(updateData== null || updateData.isEmpty()) {
			throw new IllegalArgumentException("Data can't be empty");
		}
		
		Student existingStudent = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));

		if (updateData.containsKey("fullName")) {
			existingStudent.setFullName((String) updateData.get("fullName"));
		}
		if (updateData.containsKey("age")) {
			existingStudent.setAge((Integer) updateData.get("age"));
		}
		if (updateData.containsKey("department")) {
			existingStudent.setDepartment((String) updateData.get("department"));
		}

		Student updatedStudent = studentRepository.save(existingStudent);

		return modelMapper.map(updatedStudent, StudentResponseDto.class);
	}

	@Override
	public void deleteStudent(int id) {
		
		if(id < 0) {
			throw new IllegalArgumentException("Id can't be negative");
		}
		
		Student existingStudent = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));

		studentRepository.delete(existingStudent);

	}

	@Override
	public PageResponseDto<StudentResponseDto> getAllStudentsWithPagination(int pageNumber, int pageSize) {
	
		PageRequest pageable = PageRequest.of(pageNumber, pageSize);
		
		Page<Student> studentPage = studentRepository.findAll(pageable);
		
		List<Student> students = studentPage.getContent();
		
		List<StudentResponseDto> studentResponses = new ArrayList<>();
		
		for(Student s : students) {
			studentResponses.add(modelMapper.map(s, StudentResponseDto.class));
		}
		
		PageResponseDto<StudentResponseDto> pageResponseDto = new PageResponseDto<>();
		
		pageResponseDto.setContent(studentResponses);
		pageResponseDto.setPageNumber(studentPage.getNumber());
		pageResponseDto.setPageSize(studentPage.getSize());
		pageResponseDto.setTotalCount(studentPage.getTotalElements());
		pageResponseDto.setTotalPages(studentPage.getTotalPages());
		pageResponseDto.setLastPage(studentPage.isLast());
		
		return pageResponseDto;
//		return new PageResponseDto<StudentResponseDto>(
//				
//				studentResponses,
//				studentPage.getNumber(),
//				studentPage.getSize(),
//				studentPage.getTotalElements(),
//				studentPage.getTotalPages(),
//				studentPage.isLast()
//				);
	}


}
