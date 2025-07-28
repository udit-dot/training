package com.tcg.training.repository;

import com.tcg.training.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

  List<Document> findByCategory(String category);

  List<Document> findByUploadedBy(String uploadedBy);

  List<Document> findByStatus(String status);

  List<Document> findByContentType(String contentType);
}