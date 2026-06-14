package com.example.studentverification.service;

import com.example.studentverification.dto.*;
import com.example.studentverification.entity.Student;
import com.example.studentverification.entity.StudentStatus;
import com.example.studentverification.exception.BadRequestException;
import com.example.studentverification.exception.ResourceNotFoundException;
import com.example.studentverification.repository.StudentRepository;
import com.example.studentverification.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Transactional
    public ApiResponse register(RegisterRequest request) {
        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already registered");
        }

        if (studentRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new BadRequestException("Phone number already registered");
        }

        Student student = Student.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .emailVerified(false)
                .phoneVerified(false)
                .status(StudentStatus.PENDING)
                .build();

        Student savedStudent = studentRepository.save(student);
        otpService.createAndSendOtp(savedStudent);

        return new ApiResponse("OTP sent to email and phone. Verify both OTPs to complete registration.");
    }

    @Transactional
    public StudentResponse verifyOtp(VerifyOtpRequest request) {
        Student student = studentRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        if (student.getStatus() == StudentStatus.ACTIVE) {
            throw new BadRequestException("Student is already verified");
        }

        otpService.verifyOtp(student, request.getEmailOtp(), request.getPhoneOtp());

        student.setEmailVerified(true);
        student.setPhoneVerified(true);
        student.setStatus(StudentStatus.ACTIVE);

        return toStudentResponse(studentRepository.save(student));
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtService.generateToken(userDetails);

        Student student = studentRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        return new AuthResponse(token, toStudentResponse(student));
    }

    private StudentResponse toStudentResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .name(student.getName())
                .email(student.getEmail())
                .phoneNumber(student.getPhoneNumber())
                .emailVerified(student.isEmailVerified())
                .phoneVerified(student.isPhoneVerified())
                .status(student.getStatus())
                .build();
    }
}
