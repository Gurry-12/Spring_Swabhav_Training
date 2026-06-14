package com.example.studentverification.controller;

import com.example.studentverification.dto.DocumentResponse;
import com.example.studentverification.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/students/documents")
@RequiredArgsConstructor
public class StudentDocumentController {

    private final DocumentService documentService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentResponse> uploadDocument(@RequestParam("file") MultipartFile file,
                                                           @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(documentService.uploadDocument(file, userDetails.getUsername()));
    }

    @GetMapping
    public ResponseEntity<List<DocumentResponse>> getMyDocuments(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(documentService.getMyDocuments(userDetails.getUsername()));
    }
}
