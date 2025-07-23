package com.tcg.training.dto.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorBookCollectionDto {

	private String authorName;
	private String bookTitle;
	private Double bookPrice;
	private String bookPublisher;

	public AuthorBookCollectionDto(String authorName, String bookTitle, Double bookPrice) {
		this.authorName = authorName;
		this.bookTitle = bookTitle;
		this.bookPrice = bookPrice;
	}
}
