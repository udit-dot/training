package com.tcg.training.dto.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorBookResultSetDto {

	private String authorName;
	private String bookTitle;
	private String publisher;
	private Double price;
}
