package com.tcg.training.controller;

import com.tcg.training.entity.Inventory;
import com.tcg.training.service.InventoryService;
import com.tcg.training.dto.InventorySummaryDTO;
import com.tcg.training.dto.InventoryReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventories")
public class InventoryController {
	@Autowired
	private InventoryService inventoryService;

	@PostMapping("/addInventory")
	public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) {
		Inventory saved = inventoryService.createInventory(inventory);
		return ResponseEntity.status(201).body(saved);
	}

	@PutMapping("/editInventory/{id}")
	public ResponseEntity<Inventory> updateInventory(@PathVariable Long id, @RequestBody Inventory inventory) {
		Inventory updated = inventoryService.updateInventory(id, inventory);
		return ResponseEntity.ok(updated);
	}

	@GetMapping("/getInventory/{id}")
	public ResponseEntity<Inventory> getInventory(@PathVariable Long id) {
		Inventory inventory = inventoryService.getInventory(id);
		return ResponseEntity.ok(inventory);
	}

	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Inventory>> getInventoriesByProductId(@PathVariable Integer productId) {
		List<Inventory> inventories = inventoryService.getInventoriesByProductId(productId);
		return ResponseEntity.ok(inventories);
	}

	@DeleteMapping("/deleteInventory/{id}")
	public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
		inventoryService.deleteInventory(id);
		return ResponseEntity.noContent().build();
	}

	// Custom endpoints
	@GetMapping("/location/{location}")
	public ResponseEntity<List<Inventory>> getInventoriesByLocation(@PathVariable String location) {
		List<Inventory> inventories = inventoryService.getInventoriesByLocation(location);
		return ResponseEntity.ok(inventories);
	}

	@GetMapping("/product/{productId}/location/{location}")
	public ResponseEntity<Inventory> getInventoryByProductAndLocation(@PathVariable Integer productId,
			@PathVariable String location) {
		Inventory inventory = inventoryService.getInventoryByProductAndLocation(productId, location);
		return ResponseEntity.ok(inventory);
	}

	@GetMapping("/quantity-range")
	public ResponseEntity<List<Inventory>> getInventoriesByQuantityRange(@RequestParam Integer minQty,
			@RequestParam Integer maxQty) {
		List<Inventory> inventories = inventoryService.getInventoriesByQuantityRange(minQty, maxQty);
		return ResponseEntity.ok(inventories);
	}

	@GetMapping("/low-stock/{threshold}")
	public ResponseEntity<List<Inventory>> getLowStockInventories(@PathVariable Integer threshold) {
		List<Inventory> inventories = inventoryService.getLowStockInventories(threshold);
		return ResponseEntity.ok(inventories);
	}

	@GetMapping("/summary/product/{productId}")
	public ResponseEntity<List<InventorySummaryDTO>> getInventorySummaryByProductId(@PathVariable Integer productId) {
		List<InventorySummaryDTO> summaries = inventoryService.getInventorySummaryByProductId(productId);
		return ResponseEntity.ok(summaries);
	}

	@GetMapping("/low-stock-items/{threshold}")
	public ResponseEntity<List<InventorySummaryDTO>> getLowStockItems(@PathVariable Integer threshold) {
		List<InventorySummaryDTO> items = inventoryService.getLowStockItems(threshold);
		return ResponseEntity.ok(items);
	}

	@GetMapping("/min-quantity/{minQty}")
	public ResponseEntity<List<Inventory>> getInventoriesWithMinQuantity(@PathVariable int minQty) {
		List<Inventory> inventories = inventoryService.getInventoriesWithMinQuantity(minQty);
		return ResponseEntity.ok(inventories);
	}

	@GetMapping("/report/min-quantity/{minQty}")
	public ResponseEntity<List<InventoryReportDTO>> getInventoryReportWithMinQuantity(@PathVariable int minQty) {
		List<InventoryReportDTO> report = inventoryService.getInventoryReportWithMinQuantity(minQty);
		return ResponseEntity.ok(report);
	}
}