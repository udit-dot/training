package com.tcg.training.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcg.training.dto.DocumentResponseDTO;
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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/documents")
@Tag(name = "Document Controller", description = "APIs for document management including PDF uploads")
public class DocumentController {

	@Autowired
	private DocumentService documentService;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Convert Document entity to DocumentResponseDTO
	 */
	private DocumentResponseDTO toResponseDTO(Document document) {
		return modelMapper.map(document, DocumentResponseDTO.class);
	}

	/**
	 * Convert list of Document entities to list of DocumentResponseDTO
	 */
	private List<DocumentResponseDTO> toResponseDTOList(List<Document> documents) {
		return documents.stream().map(this::toResponseDTO).toList();
	}

	@Operation(summary = "Test multipart", description = "Simple test endpoint")
	@PostMapping("/test")
	public ResponseEntity<String> testMultipart(@RequestParam("file") MultipartFile file) {
		return ResponseEntity.ok("File received: " + file.getOriginalFilename() + ", Size: " + file.getSize());
	}

	@Operation(summary = "Upload PDF document", description = "Uploads a PDF document along with metadata")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Document uploaded successfully", content = @Content(schema = @Schema(implementation = DocumentResponseDTO.class))),
			@ApiResponse(responseCode = "400", description = "Invalid file or parameters"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PostMapping("/upload")
	public ResponseEntity<DocumentResponseDTO> uploadDocument(
			@Parameter(description = "PDF file to upload", required = true) @RequestParam(value = "file", required = true) MultipartFile file,
			@Parameter(description = "Document description") @RequestParam(value = "description", required = false) String description,
			@Parameter(description = "Document category") @RequestParam(value = "category", required = false) String category,
			@Parameter(description = "User who uploaded the document", required = true) @RequestParam("uploadedBy") String uploadedBy,
			@Parameter(description = "Document status") @RequestParam(value = "status", required = false) String status) {

		Document uploadedDocument = documentService.uploadDocument(file, description, category, uploadedBy, status);
		DocumentResponseDTO responseDTO = toResponseDTO(uploadedDocument);
		return ResponseEntity.status(201).body(responseDTO);
	}

	@Operation(summary = "Upload PDF document", description = "Uploads a PDF document along with metadata")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Document uploaded successfully", content = @Content(schema = @Schema(implementation = DocumentResponseDTO.class))),
			@ApiResponse(responseCode = "400", description = "Invalid file or parameters"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PostMapping("/upload-part")
	public ResponseEntity<DocumentResponseDTO> uploadDocumentWithPart(
			@Parameter(description = "PDF file to upload", required = true) @RequestPart("file") MultipartFile file,
			@Parameter(description = "Document metadata") @RequestPart("metadata") DocumentUploadRequest metadata) {

		Document uploadedDocument = documentService.uploadDocument(file, metadata.getDescription(),
				metadata.getCategory(), metadata.getUploadedBy(), metadata.getStatus());
		DocumentResponseDTO responseDTO = toResponseDTO(uploadedDocument);
		return ResponseEntity.status(201).body(responseDTO);
	}

	@Operation(summary = "Upload PDF with JSON metadata", description = "Uploads a PDF document with stringified JSON metadata")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Document uploaded successfully", content = @Content(schema = @Schema(implementation = DocumentResponseDTO.class))),
			@ApiResponse(responseCode = "400", description = "Invalid file or JSON parameters"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PostMapping("/upload-json")
	public ResponseEntity<DocumentResponseDTO> uploadDocumentWithJson(
			@Parameter(description = "PDF file to upload", required = true) @RequestPart("file") MultipartFile file,
			@Parameter(description = "Stringified JSON metadata", required = true) @RequestPart("metadata") String jsonMetadata) {

		try {
			// Parse the JSON string to extract metadata
			DocumentUploadRequest metadata = objectMapper.readValue(jsonMetadata, DocumentUploadRequest.class);

			Document uploadedDocument = documentService.uploadDocument(file, metadata.getDescription(),
					metadata.getCategory(), metadata.getUploadedBy(), metadata.getStatus());
			DocumentResponseDTO responseDTO = toResponseDTO(uploadedDocument);
			return ResponseEntity.status(201).body(responseDTO);

		} catch (Exception e) {
			throw new RuntimeException("Failed to parse JSON metadata: " + e.getMessage());
		}
	}

	@Operation(summary = "Upload multiple PDF documents", description = "Uploads multiple PDF documents with shared metadata")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Documents uploaded successfully", content = @Content(schema = @Schema(implementation = DocumentResponseDTO.class))),
			@ApiResponse(responseCode = "400", description = "Invalid files or parameters"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PostMapping("/upload-multiple")
	public ResponseEntity<List<DocumentResponseDTO>> uploadMultipleDocuments(
			@Parameter(description = "PDF files to upload", required = true) @RequestParam("files") List<MultipartFile> files,
			@Parameter(description = "Document metadata") @RequestPart("metadata") DocumentUploadRequest metadata) {

		List<Document> uploadedDocuments = documentService.uploadMultipleDocuments(files, metadata.getDescription(),
				metadata.getCategory(), metadata.getUploadedBy(), metadata.getStatus());
		List<DocumentResponseDTO> responseDTOs = toResponseDTOList(uploadedDocuments);
		return ResponseEntity.status(201).body(responseDTOs);
	}

	@Operation(summary = "Upload multiple PDF documents with JSON metadata", description = "Uploads multiple PDF documents with stringified JSON metadata")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Documents uploaded successfully", content = @Content(schema = @Schema(implementation = DocumentResponseDTO.class))),
			@ApiResponse(responseCode = "400", description = "Invalid files or JSON parameters"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PostMapping("/upload-multiple-json")
	public ResponseEntity<List<DocumentResponseDTO>> uploadMultipleDocumentsWithJson(
			@Parameter(description = "PDF files to upload", required = true) @RequestPart("files") List<MultipartFile> files,
			@Parameter(description = "Stringified JSON metadata", required = true) @RequestPart("metadata") String jsonMetadata) {

		try {
			// Parse the JSON string to extract metadata
			DocumentUploadRequest metadata = objectMapper.readValue(jsonMetadata, DocumentUploadRequest.class);

			List<Document> uploadedDocuments = documentService.uploadMultipleDocuments(files, metadata.getDescription(),
					metadata.getCategory(), metadata.getUploadedBy(), metadata.getStatus());
			List<DocumentResponseDTO> responseDTOs = toResponseDTOList(uploadedDocuments);
			return ResponseEntity.status(201).body(responseDTOs);

		} catch (Exception e) {
			throw new RuntimeException("Failed to parse JSON metadata: " + e.getMessage());
		}
	}

	@Operation(summary = "Get all documents", description = "Retrieves all uploaded documents")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Documents retrieved successfully", content = @Content(schema = @Schema(implementation = DocumentResponseDTO.class))) })
	@GetMapping
	public ResponseEntity<List<DocumentResponseDTO>> getAllDocuments() {
		List<Document> documents = documentService.getAllDocuments();
		List<DocumentResponseDTO> responseDTOs = toResponseDTOList(documents);
		return ResponseEntity.ok(responseDTOs);
	}

	@Operation(summary = "Get documents by category", description = "Retrieves documents filtered by category")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Documents retrieved successfully", content = @Content(schema = @Schema(implementation = DocumentResponseDTO.class))) })
	@GetMapping("/category/{category}")
	public ResponseEntity<List<DocumentResponseDTO>> getDocumentsByCategory(
			@Parameter(description = "Document category", required = true) @PathVariable String category) {
		List<Document> documents = documentService.getDocumentsByCategory(category);
		List<DocumentResponseDTO> responseDTOs = toResponseDTOList(documents);
		return ResponseEntity.ok(responseDTOs);
	}

	@Operation(summary = "Get documents by uploader", description = "Retrieves documents uploaded by a specific user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Documents retrieved successfully", content = @Content(schema = @Schema(implementation = DocumentResponseDTO.class))) })
	@GetMapping("/uploader/{uploadedBy}")
	public ResponseEntity<List<DocumentResponseDTO>> getDocumentsByUploader(
			@Parameter(description = "Uploader username", required = true) @PathVariable String uploadedBy) {
		List<Document> documents = documentService.getDocumentsByUploader(uploadedBy);
		List<DocumentResponseDTO> responseDTOs = toResponseDTOList(documents);
		return ResponseEntity.ok(responseDTOs);
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
			@ApiResponse(responseCode = "200", description = "Document found", content = @Content(schema = @Schema(implementation = DocumentResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "Document not found") })
	@GetMapping("/{id}")
	public ResponseEntity<DocumentResponseDTO> getDocumentById(
			@Parameter(description = "Document ID", required = true) @PathVariable Long id) {
		Document document = documentService.getDocument(id);
		DocumentResponseDTO responseDTO = toResponseDTO(document);
		return ResponseEntity.ok(responseDTO);
	}

	@Operation(summary = "Update document metadata", description = "Updates document description, category, and status")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Document updated successfully", content = @Content(schema = @Schema(implementation = DocumentResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "Document not found") })
	@PutMapping("/{id}")
	public ResponseEntity<DocumentResponseDTO> updateDocument(
			@Parameter(description = "Document ID", required = true) @PathVariable Long id,
			@Parameter(description = "Document metadata to update") @RequestBody DocumentUploadRequest metadata) {

		Document updatedDocument = documentService.updateDocument(id, metadata.getDescription(), metadata.getCategory(),
				metadata.getStatus());
		DocumentResponseDTO responseDTO = toResponseDTO(updatedDocument);
		return ResponseEntity.ok(responseDTO);
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