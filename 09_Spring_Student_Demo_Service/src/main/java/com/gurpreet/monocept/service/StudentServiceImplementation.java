package com.gurpreet.monocept.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public Student createStudent(Student student) {
		return studentRepository.save(student);
	}

	@Override
	public List<Student> createBulkStudents(List<Student> students) {
		return studentRepository.saveAll(students);
	}

	@Override
	public Student getStudentById(int id) {
		Student foundStudent = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
		;
		return foundStudent;
	}

	@Override
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	@Override
	public Student updateStudent(int id, Student updateStudent) {
		Student existingStudent = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));

		existingStudent.setFullName(updateStudent.getFullName());
		existingStudent.setAge(updateStudent.getAge());
		existingStudent.setDepartment(updateStudent.getDepartment());

		return studentRepository.save(existingStudent);
	}

	@Override
	public Student updateStudentPartially(int id, Map<String, Object> updateData) {
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

		return studentRepository.save(existingStudent);
	}

	@Override
	public void deleteStudent(int id) {
		Student existingStudent = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));

		studentRepository.delete(existingStudent);

	}

}
