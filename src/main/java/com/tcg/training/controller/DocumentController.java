package com.tcg.training.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcg.training.dto.DocumentUploadRequest;
import com.tcg.training.entity.Document;
import com.tcg.training.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@Tag(name = "Document Controller", description = "APIs for document management including PDF uploads")
public class DocumentController {

	@Autowired
	private DocumentService documentService;

	@Autowired
	private ObjectMapper objectMapper;

	@Operation(summary = "Test multipart", description = "Simple test endpoint")
	@PostMapping("/test")
	public ResponseEntity<String> testMultipart(@RequestParam("file") MultipartFile file) {
		return ResponseEntity.ok("File received: " + file.getOriginalFilename() + ", Size: " + file.getSize());
	}

	@Operation(summary = "Upload PDF document", description = "Uploads a PDF document along with metadata")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Document uploaded successfully", content = @Content(schema = @Schema(implementation = Document.class))),
			@ApiResponse(responseCode = "400", description = "Invalid file or parameters"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PostMapping("/upload")
	public ResponseEntity<Document> uploadDocument(
			@Parameter(description = "PDF file to upload", required = true) @RequestPart("file") MultipartFile file,
			@Parameter(description = "Document description") @RequestParam(value = "description", required = false) String description,
			@Parameter(description = "Document category") @RequestParam(value = "category", required = false) String category,
			@Parameter(description = "User who uploaded the document", required = true) @RequestParam("uploadedBy") String uploadedBy,
			@Parameter(description = "Document status") @RequestParam(value = "status", required = false) String status) {

		Document uploadedDocument = documentService.uploadDocument(file, description, category, uploadedBy, status);
		return ResponseEntity.status(201).body(uploadedDocument);
	}

	@Operation(summary = "Upload PDF with JSON metadata", description = "Uploads a PDF document with stringified JSON metadata")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Document uploaded successfully", content = @Content(schema = @Schema(implementation = Document.class))),
			@ApiResponse(responseCode = "400", description = "Invalid file or JSON parameters"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PostMapping("/upload-json")
	public ResponseEntity<Document> uploadDocumentWithJson(
			@Parameter(description = "PDF file to upload", required = true) @RequestPart("file") MultipartFile file,
			@Parameter(description = "Stringified JSON metadata", required = true) @RequestPart("metadata") String jsonMetadata) {

		try {
			// Parse the JSON string to extract metadata
			DocumentUploadRequest metadata = objectMapper.readValue(jsonMetadata, DocumentUploadRequest.class);

			Document uploadedDocument = documentService.uploadDocument(file, metadata.getDescription(),
					metadata.getCategory(), metadata.getUploadedBy(), metadata.getStatus());
			return ResponseEntity.status(201).body(uploadedDocument);

		} catch (Exception e) {
			throw new RuntimeException("Failed to parse JSON metadata: " + e.getMessage());
		}
	}

	@Operation(summary = "Get all documents", description = "Retrieves all uploaded documents")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Documents retrieved successfully", content = @Content(schema = @Schema(implementation = Document.class))) })
	@GetMapping
	public ResponseEntity<List<Document>> getAllDocuments() {
		List<Document> documents = documentService.getAllDocuments();
		return ResponseEntity.ok(documents);
	}

	@Operation(summary = "Get documents by category", description = "Retrieves documents filtered by category")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Documents retrieved successfully", content = @Content(schema = @Schema(implementation = Document.class))) })
	@GetMapping("/category/{category}")
	public ResponseEntity<List<Document>> getDocumentsByCategory(
			@Parameter(description = "Document category", required = true) @PathVariable String category) {
		List<Document> documents = documentService.getDocumentsByCategory(category);
		return ResponseEntity.ok(documents);
	}

	@Operation(summary = "Get documents by uploader", description = "Retrieves documents uploaded by a specific user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Documents retrieved successfully", content = @Content(schema = @Schema(implementation = Document.class))) })
	@GetMapping("/uploader/{uploadedBy}")
	public ResponseEntity<List<Document>> getDocumentsByUploader(
			@Parameter(description = "Uploader username", required = true) @PathVariable String uploadedBy) {
		List<Document> documents = documentService.getDocumentsByUploader(uploadedBy);
		return ResponseEntity.ok(documents);
	}

	@Operation(summary = "Download document file", description = "Downloads the PDF file from database")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "File downloaded successfully"),
			@ApiResponse(responseCode = "404", description = "Document not found") })
	@GetMapping("/{id}/download")
	public ResponseEntity<byte[]> downloadDocument(
			@Parameter(description = "Document ID", required = true) @PathVariable Long id) {
		Document document = documentService.getDocument(id);
		byte[] fileData = documentService.getDocumentFile(id);

		return ResponseEntity.ok()
				.header("Content-Disposition", "attachment; filename=\"" + document.getOriginalFileName() + "\"")
				.header("Content-Type", document.getContentType()).body(fileData);
	}

	@Operation(summary = "Get document by ID", description = "Retrieves a specific document by its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Document found", content = @Content(schema = @Schema(implementation = Document.class))),
			@ApiResponse(responseCode = "404", description = "Document not found") })
	@GetMapping("/{id}")
	public ResponseEntity<Document> getDocumentById(
			@Parameter(description = "Document ID", required = true) @PathVariable Long id) {
		Document document = documentService.getDocument(id);
		return ResponseEntity.ok(document);
	}

	@Operation(summary = "Update document metadata", description = "Updates document description, category, and status")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Document updated successfully", content = @Content(schema = @Schema(implementation = Document.class))),
			@ApiResponse(responseCode = "404", description = "Document not found") })
	@PutMapping("/{id}")
	public ResponseEntity<Document> updateDocument(
			@Parameter(description = "Document ID", required = true) @PathVariable Long id,
			@Parameter(description = "Document metadata to update") @RequestBody DocumentUploadRequest metadata) {

		Document updatedDocument = documentService.updateDocument(id, metadata.getDescription(), metadata.getCategory(),
				metadata.getStatus());
		return ResponseEntity.ok(updatedDocument);
	}

	@Operation(summary = "Delete document", description = "Deletes a document and its associated file")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Document deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Document not found") })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteDocument(
			@Parameter(description = "Document ID", required = true) @PathVariable Long id) {
		documentService.deleteDocument(id);
		return ResponseEntity.noContent().build();
	}
}