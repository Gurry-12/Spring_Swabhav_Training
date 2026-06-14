package com.example.studentverification.service;

import com.cloudinary.Cloudinary;
import com.example.studentverification.dto.DocumentResponse;
import com.example.studentverification.entity.Student;
import com.example.studentverification.entity.StudentDocument;
import com.example.studentverification.exception.BadRequestException;
import com.example.studentverification.exception.ResourceNotFoundException;
import com.example.studentverification.repository.StudentDocumentRepository;
import com.example.studentverification.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentService {

	private final Cloudinary cloudinary;
	private final StudentRepository studentRepository;
	private final StudentDocumentRepository documentRepository;

	private static final List<String> ALLOWED_TYPES = List.of("application/pdf", "application/msword",
			"application/vnd.openxmlformats-officedocument.wordprocessingml.document", "image/jpeg", "image/png",
			"image/webp");

	@Transactional
	public DocumentResponse uploadDocument(MultipartFile file, String studentEmail) {
		validateFile(file);

		Student student = studentRepository.findByEmail(studentEmail)
				.orElseThrow(() -> new ResourceNotFoundException("Student not found"));

		try {
			String resourceType = getCloudinaryResourceType(file.getContentType());
			String publicId = buildPublicId(file.getOriginalFilename(), student.getId(), resourceType);

			Map<String, Object> options = new HashMap<>();
			options.put("resource_type", resourceType);
			options.put("public_id", publicId);

			Map uploadResult = cloudinary.uploader().upload(file.getBytes(), options);

			StudentDocument document = StudentDocument.builder().student(student)
					.originalFileName(file.getOriginalFilename()).contentType(file.getContentType())
					.sizeInBytes(file.getSize()).cloudinaryPublicId(String.valueOf(uploadResult.get("public_id")))
					.cloudinaryUrl(String.valueOf(uploadResult.get("secure_url")))
					.resourceType(String.valueOf(uploadResult.get("resource_type"))).build();

			return toDocumentResponse(documentRepository.save(document));

		} catch (IOException ex) {
			throw new BadRequestException("Unable to read uploaded file");
		}
	}

	public List<DocumentResponse> getMyDocuments(String studentEmail) {
		return documentRepository.findByStudentEmail(studentEmail).stream().map(this::toDocumentResponse).toList();
	}

	private void validateFile(MultipartFile file) {
		if (file == null || file.isEmpty()) {
			throw new BadRequestException("Please upload a file");
		}

		if (file.getSize() > 5 * 1024 * 1024) {
			throw new BadRequestException("File size must be less than 5 MB");
		}

		if (!ALLOWED_TYPES.contains(file.getContentType())) {
			throw new BadRequestException("Only PDF, Word, JPG, PNG and WEBP files are allowed");
		}
	}

	private String getCloudinaryResourceType(String contentType) {
		if (contentType != null && contentType.startsWith("image/")) {
			return "image";
		}

		return "raw";
	}

	private String buildPublicId(String originalFilename, Long studentId, String resourceType) {
		String safeName = Objects.requireNonNullElse(originalFilename, "document").replaceAll("[^a-zA-Z0-9._-]", "_");

		String extension = getFileExtension(safeName);

		String baseName = extension.isBlank() ? safeName
				: safeName.substring(0, safeName.length() - extension.length());

		String uniqueName = baseName + "-" + UUID.randomUUID();

		if ("raw".equals(resourceType)) {
			uniqueName = uniqueName + extension;
		}

		return "student-documents/student-" + studentId + "/" + uniqueName;
	}

	private String getFileExtension(String filename) {
		int lastDotIndex = filename.lastIndexOf('.');

		if (lastDotIndex == -1) {
			return "";
		}

		return filename.substring(lastDotIndex);
	}

	private DocumentResponse toDocumentResponse(StudentDocument document) {
		return DocumentResponse.builder().id(document.getId()).originalFileName(document.getOriginalFileName())
				.contentType(document.getContentType()).sizeInBytes(document.getSizeInBytes())
				.cloudinaryPublicId(document.getCloudinaryPublicId()).cloudinaryUrl(document.getCloudinaryUrl())
				.resourceType(document.getResourceType()).uploadedAt(document.getUploadedAt()).build();
	}
}