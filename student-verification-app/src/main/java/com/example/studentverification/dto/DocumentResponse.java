package com.example.studentverification.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class DocumentResponse {
    private Long id;
    private String originalFileName;
    private String contentType;
    private Long sizeInBytes;
    private String cloudinaryPublicId;
    private String cloudinaryUrl;
    private String resourceType;
    private LocalDateTime uploadedAt;
}
