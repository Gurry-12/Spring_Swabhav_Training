package com.gurpreet.monocept.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gurpreet.monocept.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}
