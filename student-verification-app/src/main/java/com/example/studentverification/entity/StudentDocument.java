package com.example.studentverification.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "student_documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originalFileName;

    private String contentType;

    private Long sizeInBytes;

    @Column(nullable = false)
    private String cloudinaryPublicId;

    @Column(nullable = false, length = 1000)
    private String cloudinaryUrl;

    private String resourceType;

    private LocalDateTime uploadedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @PrePersist
    public void onCreate() {
        uploadedAt = LocalDateTime.now();
    }
}
