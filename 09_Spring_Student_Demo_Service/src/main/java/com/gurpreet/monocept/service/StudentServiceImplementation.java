package com.gurpreet.monocept.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gurpreet.monocept.dto.StudentRequestDto;
import com.gurpreet.monocept.dto.StudentResponseDto;
import com.gurpreet.monocept.entity.Student;
import com.gurpreet.monocept.exception.StudentNotFoundException;
import com.gurpreet.monocept.repository.StudentRepository;

@Service
public class StudentServiceImplementation implements StudentService {

	private StudentRepository studentRepository;

	@Autowired
	public StudentServiceImplementation(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	@Override
	public StudentResponseDto createStudent(StudentRequestDto studentRequestDto) {

		Student student = convertToEntity(studentRequestDto);

		Student retrivedStudent = studentRepository.save(student);

		StudentResponseDto studentResponseDto = convertToStudentResponseDto(retrivedStudent);

		return studentResponseDto;

	}

	@Override
	public List<StudentResponseDto> createBulkStudents(List<StudentRequestDto> studentRequestDtos) {

		List<Student> students = new ArrayList<Student>();
		for (StudentRequestDto dto : studentRequestDtos) {
			students.add(convertToEntity(dto));
		}

		List<Student> retrivedStudents = studentRepository.saveAll(students);

		List<StudentResponseDto> studentResponseDtos = new ArrayList<StudentResponseDto>();
		for (Student student : retrivedStudents) {
			studentResponseDtos.add(convertToStudentResponseDto(student));
		}

		return studentResponseDtos;
	}

	@Override
	public StudentResponseDto getStudentById(int id) {
		Student foundStudent = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));

		StudentResponseDto foundStudentResponseDto = convertToStudentResponseDto(foundStudent);

		return foundStudentResponseDto;
	}

	@Override
	public List<StudentResponseDto> getAllStudents() {

		List<Student> retrivedStudents = studentRepository.findAll();

		List<StudentResponseDto> studentResponseDtos = new ArrayList<StudentResponseDto>();
		for (Student student : retrivedStudents) {
			studentResponseDtos.add(convertToStudentResponseDto(student));
		}

		return studentResponseDtos;
	}

	@Override
	public StudentResponseDto updateStudent(int id, StudentRequestDto updateStudentRequestDto) {

		Student updateStudent = convertToEntity(updateStudentRequestDto);
		Student existingStudent = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));

		existingStudent.setFullName(updateStudent.getFullName());
		existingStudent.setAge(updateStudent.getAge());
		existingStudent.setDepartment(updateStudent.getDepartment());

		Student updatedStudent = studentRepository.save(existingStudent);

		StudentResponseDto studentResponseDto = convertToStudentResponseDto(updatedStudent);
		return studentResponseDto;
	}

	@Override
	public StudentResponseDto updateStudentPartially(int id, Map<String, Object> updateData) {
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

		StudentResponseDto studentResponseDto = convertToStudentResponseDto(updatedStudent);
		return studentResponseDto;
	}

	@Override
	public void deleteStudent(int id) {
		Student existingStudent = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));

		studentRepository.delete(existingStudent);

	}

	// helper methods
	private Student convertToEntity(StudentRequestDto studentRequestDto) {

		Student student = new Student();

		// seting values
		student.setFullName(studentRequestDto.getFullName());
		student.setAge(studentRequestDto.getAge());
		student.setDepartment(studentRequestDto.getDepartment());

		return student;
	}

	private StudentResponseDto convertToStudentResponseDto(Student student) {

		StudentResponseDto studentResponseDto = new StudentResponseDto();

		// seting values
		studentResponseDto.setFullName(student.getFullName());
		studentResponseDto.setAge(student.getAge());
		//studentResponseDto.setDepartment(student.getDepartment());

		return studentResponseDto;
	}

}
