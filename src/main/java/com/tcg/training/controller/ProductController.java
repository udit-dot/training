package com.tcg.training.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcg.training.entity.Product;
import com.tcg.training.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/products")
@Tag(name = "Product Controller", description = "Manages CRUD operations of Product Entity")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Operation(summary = "Create a new product", description = "Creates a new product with the provided details")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Product created successfully", content = @Content(schema = @Schema(implementation = Product.class))) })
	@PostMapping(value = "/addProduct", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> createProduct(
			@Parameter(name = "product", description = "Product object to be created", required = true) @RequestBody Product product) {
		Product createdProduct = productService.createProduct(product);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
	}

	@GetMapping("/getAllProducts")
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> products = productService.getAllProducts();
		return ResponseEntity.status(200).body(products);
	}

	@GetMapping("/getProduct/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
		Product product = productService.getProductById(id);
		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
		headers.add("new-header", "application/json");
		if (product != null) {
			return ResponseEntity.ok().headers(headers).body(product);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/editProduct/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestBody Product product) {
		Product updatedProduct = productService.updateProduct(id, product);
		if (updatedProduct != null) {
			return ResponseEntity.ok(updatedProduct);
		} else {
			return ResponseEntity.status(404).body("Product Id not found!!");
		}
	}

	@DeleteMapping("/deleteProduct/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
		productService.deleteProduct(id);
		return ResponseEntity.status(HttpStatus.OK).body("Deletion request accepted.");
	}
}