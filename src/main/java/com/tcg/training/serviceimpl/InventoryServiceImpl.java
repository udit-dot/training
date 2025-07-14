package com.tcg.training.serviceimpl;

import com.tcg.training.entity.Inventory;
import com.tcg.training.entity.Product;
import com.tcg.training.repository.InventoryRepository;
import com.tcg.training.repository.ProductRepository;
import com.tcg.training.service.InventoryService;
import com.tcg.training.exception.ProductNotFoundException;
import com.tcg.training.exception.InventoryNotFoundException;
import com.tcg.training.dto.InventorySummaryDTO;
import com.tcg.training.dto.InventoryReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {
	@Autowired
	private InventoryRepository inventoryRepository;
	@Autowired
	private ProductRepository productRepository;

	@Override
	public Inventory createInventory(Inventory inventory) {
		// Ensure product exists
		Product product = productRepository.findById(inventory.getProduct().getProductId())
				.orElseThrow(() -> new ProductNotFoundException("Product not found"));
		inventory.setProduct(product);
		return inventoryRepository.save(inventory);
	}

	@Override
	public Inventory updateInventory(Long id, Inventory inventory) {
		Inventory existing = inventoryRepository.findById(id)
				.orElseThrow(() -> new InventoryNotFoundException("Inventory not found"));
		existing.setLocation(inventory.getLocation());
		existing.setQuantity(inventory.getQuantity());
		// Optionally update product
		if (inventory.getProduct() != null) {
			Product product = productRepository.findById(inventory.getProduct().getProductId())
					.orElseThrow(() -> new ProductNotFoundException("Product not found"));
			existing.setProduct(product);
		}
		return inventoryRepository.save(existing);
	}

	@Override
	public Inventory getInventory(Long id) {
		return inventoryRepository.findById(id)
				.orElseThrow(() -> new InventoryNotFoundException("Inventory not found"));
	}

	@Override
	public List<Inventory> getInventoriesByProductId(Integer productId) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ProductNotFoundException("Product not found"));
		return product.getInventories().stream().toList();
	}

	@Override
	public void deleteInventory(Long id) {
		inventoryRepository.deleteById(id);
	}

	// Custom methods implementation
	@Override
	public List<Inventory> getInventoriesByLocation(String location) {
		return inventoryRepository.findByLocation(location);
	}

	@Override
	public Inventory getInventoryByProductAndLocation(Integer productId, String location) {
		return inventoryRepository.findByProductProductIdAndLocation(productId, location);
	}

	@Override
	public List<Inventory> getInventoriesByQuantityRange(Integer minQty, Integer maxQty) {
		return inventoryRepository.findInventoriesByQuantityRange(minQty, maxQty);
	}

	@Override
	public List<Inventory> getLowStockInventories(Integer threshold) {
		return inventoryRepository.findByQuantityLessThan(threshold);
	}

	@Override
	public List<InventorySummaryDTO> getInventorySummaryByProductId(Integer productId) {
		return inventoryRepository.findInventorySummaryByProductId(productId);
	}

	@Override
	public List<InventorySummaryDTO> getLowStockItems(Integer threshold) {
		return inventoryRepository.findLowStockItems(threshold);
	}

	@Override
	public List<Inventory> getInventoriesWithMinQuantity(int minQty) {
		return inventoryRepository.findInventoriesWithMinQuantity(minQty);
	}

	@Override
	public List<InventoryReportDTO> getInventoryReportWithMinQuantity(int minQty) {
		return inventoryRepository.findInventoryReportWithMinQuantity(minQty);
	}
}