package com.gurpreet.monocept;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.gurpreet.monocept.model.Student;
import com.gurpreet.monocept.model.StudentDAO;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);

	}

	@Bean
	public CommandLineRunner commandLineRunner(StudentDAO studentDAO) {

		return runner -> {
			// createStudent(studentDAO);

			// readStudent(studentDAO);

			readAllStudent(studentDAO);
		};
	}

	private void readAllStudent(StudentDAO studentDAO) {
		System.out.println("Retriving all the students");
		List<Student> studentList = studentDAO.findAllStudent();

		for (Student student : studentList) {
			System.out.println(student);
		}

	}

	private void readStudent(StudentDAO studentDAO) {
		System.out.println("Retriving data from database based on id");
		Student student = studentDAO.findStudentById(1);
		System.out.println("Found Student is : " + student);

	}

	private void createStudent(StudentDAO studentDAO) {
		System.out.println("Hi I am in main method");

		Student student = new Student(5, "Jay");
		studentDAO.save(student);
		System.out.println("saved");

	}

}
