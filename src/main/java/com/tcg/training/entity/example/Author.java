package com.tcg.training.entity.example;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SqlResultSetMapping(name = "AuthorBookDataMapping", classes = @ConstructorResult(targetClass = com.tcg.training.dto.example.AuthorBookResultSetDto.class, 
columns = {
	@ColumnResult(name = "authorName", type = String.class),
	@ColumnResult(name = "bookTitle", type = String.class), 
	@ColumnResult(name = "publisher", type = String.class),
	@ColumnResult(name = "price", type = Double.class) }))
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@JsonManagedReference
	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Book> books = new ArrayList<>();
}