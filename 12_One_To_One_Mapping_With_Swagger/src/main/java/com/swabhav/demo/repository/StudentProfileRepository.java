package com.swabhav.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swabhav.demo.model.StudentProfile;

public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {

	boolean existsByEmail(String email);
	boolean existsByEmailAndIdNot(String email, Long id);
}
