package com.tcg.training.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentResponseDTO {

	private Long id;
	private String fileName;
	private String originalFileName;
	private String contentType;
	private Long fileSize;
	private String description;
	private String uploadedBy;
	private LocalDateTime uploadedAt;
	private String category;
	private String status;
}