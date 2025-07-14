package com.tcg.training.service;

import java.util.List;

import com.tcg.training.entity.Product;

public interface ProductService {
	Product createProduct(Product product);

	List<Product> getAllProducts();

	Product getProductById(Integer id);

	Product updateProduct(Integer id, Product product);

	void deleteProduct(Integer id);

	int getTotalQuantityForProduct(Integer productId);
}