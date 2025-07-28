package com.tcg.training.service;

import com.tcg.training.entity.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {

  Document uploadDocument(MultipartFile file, String description, String category, String uploadedBy, String status);

  Document getDocument(Long id);

  byte[] getDocumentFile(Long id);

  List<Document> getAllDocuments();

  List<Document> getDocumentsByCategory(String category);

  List<Document> getDocumentsByUploader(String uploadedBy);

  void deleteDocument(Long id);

  Document updateDocument(Long id, String description, String category, String status);
}