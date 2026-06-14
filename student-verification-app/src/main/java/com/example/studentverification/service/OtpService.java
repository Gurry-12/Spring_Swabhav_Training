package com.example.studentverification.service;

import com.example.studentverification.entity.OtpVerification;
import com.example.studentverification.entity.Student;
import com.example.studentverification.exception.BadRequestException;
import com.example.studentverification.repository.OtpVerificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final OtpVerificationRepository otpRepository;
    private final EmailService emailService;
    private final SmsService smsService;
    private final SecureRandom secureRandom = new SecureRandom();

    @Value("${app.otp.expiry-minutes}")
    private long expiryMinutes;

    @Transactional
    public void createAndSendOtp(Student student) {
        String emailOtp = generateSixDigitOtp();
        String phoneOtp = generateSixDigitOtp();

        OtpVerification otpVerification = OtpVerification.builder()
                .student(student)
                .emailOtp(emailOtp)
                .phoneOtp(phoneOtp)
                .expiresAt(LocalDateTime.now().plusMinutes(expiryMinutes))
                .used(false)
                .build();

        otpRepository.save(otpVerification);
        emailService.sendOtp(student.getEmail(), emailOtp);
        smsService.sendOtp(student.getPhoneNumber(), phoneOtp);
    }

    @Transactional
    public void verifyOtp(Student student, String emailOtp, String phoneOtp) {
        OtpVerification latestOtp = otpRepository.findTopByStudentAndUsedFalseOrderByCreatedAtDesc(student)
                .orElseThrow(() -> new BadRequestException("No active OTP found. Please register again."));

        if (latestOtp.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("OTP expired. Please register again to get a new OTP.");
        }

        if (!latestOtp.getEmailOtp().equals(emailOtp)) {
            throw new BadRequestException("Invalid email OTP");
        }

        if (!latestOtp.getPhoneOtp().equals(phoneOtp)) {
            throw new BadRequestException("Invalid phone OTP");
        }

        latestOtp.setUsed(true);
        otpRepository.save(latestOtp);
    }

    private String generateSixDigitOtp() {
        int number = secureRandom.nextInt(900000) + 100000;
        return String.valueOf(number);
    }
}
