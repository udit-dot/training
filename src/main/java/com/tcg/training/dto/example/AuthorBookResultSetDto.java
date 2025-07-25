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
	
	public AuthorBookResultSetDto(String authorName, String bookTitle, String publisher) {
		this.authorName = authorName;
		this.bookTitle = bookTitle;
		this.publisher = publisher;
	}
}
