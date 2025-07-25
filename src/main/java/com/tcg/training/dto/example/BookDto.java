package com.tcg.training.dto.example;

import lombok.Data;

@Data
public class BookDto {
  private String title;
  private Double price;
  private String publisher;
  private Long authorId;
}