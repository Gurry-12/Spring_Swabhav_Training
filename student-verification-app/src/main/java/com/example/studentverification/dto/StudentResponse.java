package com.example.studentverification.dto;

import com.example.studentverification.entity.StudentStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudentResponse {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private boolean emailVerified;
    private boolean phoneVerified;
    private StudentStatus status;
}
