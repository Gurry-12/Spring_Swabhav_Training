package com.gurpreet.monocept.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gurpreet.monocept.entity.Student;

public interface StudentRespository extends JpaRepository<Student, Integer> {

}
