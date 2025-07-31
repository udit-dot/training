package com.tcg.training.dto.example;

import lombok.Data;

@Data
public class BookDto {
  private String title;
  private Double price;
  private String publisher;
//  private String newData;
  
  //book.getAuthor().getName();
//  private String authorName;
  private Long authorId;
}