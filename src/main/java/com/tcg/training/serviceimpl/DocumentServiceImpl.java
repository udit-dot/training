package com.tcg.training.serviceimpl;

import com.tcg.training.entity.Document;
import com.tcg.training.exception.DocumentNotFoundException;
import com.tcg.training.repository.DocumentRepository;
import com.tcg.training.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentServiceImpl implements DocumentService {

	@Autowired
	private DocumentRepository documentRepository;

	@Override
	public Document uploadDocument(MultipartFile file, String description, String category, String uploadedBy,
			String status) {
		try {
			// Validate file
			if (file.isEmpty()) {
				throw new RuntimeException("File is empty");
			}

			// Validate file type
			String contentType = file.getContentType();
			if (contentType == null || !contentType.equals("application/pdf")) {
				throw new RuntimeException("Only PDF files are allowed");
			}

			// Generate unique filename
			String originalFileName = file.getOriginalFilename();
			String fileExtension = originalFileName != null
					? originalFileName.substring(originalFileName.lastIndexOf("."))
					: ".pdf";
			String fileName = UUID.randomUUID().toString() + fileExtension;

			// Read file data into byte array
			byte[] fileData = file.getBytes();

			// Create document entity
			Document document = new Document();
			document.setFileName(fileName);
			document.setOriginalFileName(originalFileName);
			document.setContentType(contentType);
			document.setFileSize(file.getSize());
			document.setFileData(fileData);
			document.setDescription(description);
			document.setUploadedBy(uploadedBy);
			document.setCategory(category);
			document.setStatus(status != null ? status : "ACTIVE");

			// Save to database
			return documentRepository.save(document);

		} catch (IOException e) {
			throw new RuntimeException("Failed to read file data", e);
		}
	}

	@Override
	public Document getDocument(Long id) {
		return documentRepository.findById(id).orElseThrow(() -> new DocumentNotFoundException(id));
	}

	@Override
	public byte[] getDocumentFile(Long id) {
		Document document = getDocument(id);
		return document.getFileData();
	}

	@Override
	public List<Document> getAllDocuments() {
		return documentRepository.findAll();
	}

	@Override
	public List<Document> getDocumentsByCategory(String category) {
		return documentRepository.findByCategory(category);
	}

	@Override
	public List<Document> getDocumentsByUploader(String uploadedBy) {
		return documentRepository.findByUploadedBy(uploadedBy);
	}

	@Override
	public void deleteDocument(Long id) {
		// Delete from database (file data is automatically removed)
		documentRepository.deleteById(id);
	}

	@Override
	public Document updateDocument(Long id, String description, String category, String status) {
		Document document = getDocument(id);

		if (description != null) {
			document.setDescription(description);
		}
		if (category != null) {
			document.setCategory(category);
		}
		if (status != null) {
			document.setStatus(status);
		}

		return documentRepository.save(document);
	}
}