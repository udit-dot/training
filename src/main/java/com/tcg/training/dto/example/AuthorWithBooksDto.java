package com.tcg.training.dto.example;

import lombok.Data;
import java.util.List;

@Data
public class AuthorWithBooksDto {
	private String name;
	private String nationality;
	private String birthDate;
	private String biography;
	private List<BookSimpleDto> books;
}