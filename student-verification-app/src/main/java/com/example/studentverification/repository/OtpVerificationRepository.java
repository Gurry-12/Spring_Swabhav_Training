package com.example.studentverification.repository;

import com.example.studentverification.entity.OtpVerification;
import com.example.studentverification.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpVerificationRepository extends JpaRepository<OtpVerification, Long> {
    Optional<OtpVerification> findTopByStudentAndUsedFalseOrderByCreatedAtDesc(Student student);
}
