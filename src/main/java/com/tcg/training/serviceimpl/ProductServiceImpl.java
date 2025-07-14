package com.tcg.training.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcg.training.entity.Inventory;
import com.tcg.training.entity.Product;
import com.tcg.training.exception.ProductNotFoundException;
import com.tcg.training.repository.ProductRepository;
import com.tcg.training.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product createProduct(Product product) {
		if (product.getInventories() != null) {
	        for (Inventory inventory : product.getInventories()) {
	            inventory.setProduct(product);
	        }
	    }
		return productRepository.save(product);
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product getProductById(Integer id) {
		return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
	}

	@Override
	public Product updateProduct(Integer id, Product product) {
		Optional<Product> existingProduct = productRepository.findById(id);
		if (existingProduct.isPresent()) {
			product.setProductId(id);
			return productRepository.save(product);
		} else {
			throw new ProductNotFoundException("Product not found");
		}
	}

	@Override
	public void deleteProduct(Integer id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ProductNotFoundException("Product not found"));
		productRepository.delete(product);
	}

	@Override
	public int getTotalQuantityForProduct(Integer productId) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ProductNotFoundException("Product not found"));
		return product.getInventories().stream().mapToInt(Inventory::getQuantity).sum();
	}
}