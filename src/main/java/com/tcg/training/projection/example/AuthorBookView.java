package com.tcg.training.projection.example;

public interface AuthorBookView {
	//Method names must match the aliases or field names in the JPQL query.
	String getAuthorName();
	String getBookTitle();
	String getBookPublisher();
	Double getBookPrice();
}
