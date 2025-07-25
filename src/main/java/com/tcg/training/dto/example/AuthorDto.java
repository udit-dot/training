package com.tcg.training.dto.example;

import lombok.Data;
import java.util.List;

@Data
public class AuthorDto {
	private Long id;
	private String name;
	private List<String> bookTitles;
	private String nationality;
	private String birthDate;
	private String biography;
}