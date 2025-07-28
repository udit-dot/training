package com.tcg.training.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Document {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String fileName;

	@Column(nullable = false)
	private String originalFileName;

	@Column(nullable = false)
	private String contentType;

	@Column(nullable = false)
	private Long fileSize;

	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] fileData;

	@Column(length = 1000)
	private String description;

	@Column(nullable = false)
	private String uploadedBy;

	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime uploadedAt;

	@Column(length = 100)
	private String category;

	@Column(length = 50)
	private String status;

}