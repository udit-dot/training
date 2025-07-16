package com.tcg.training.controller;

import com.tcg.training.entity.Inventory;
import com.tcg.training.projection.LocQuanAndProdNameInvProjection;
import com.tcg.training.service.InventoryService;
import com.tcg.training.dto.InventorySummaryDTO;
import com.tcg.training.dto.InventoryReportDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventories")
@Tag(name = "Inventory Controller", description = "Manages CRUD operations and custom queries for Inventory Entity")
public class InventoryController {
	@Autowired
	private InventoryService inventoryService;

	@Operation(summary = "Create a new inventory", description = "Creates a new inventory record with the provided details")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Inventory created successfully", content = @Content(schema = @Schema(implementation = Inventory.class))) })
	@PostMapping("/addInventory")
	public ResponseEntity<Inventory> createInventory(
			@Parameter(name = "inventory", description = "Inventory object to be created", required = true) @RequestBody Inventory inventory) {
		Inventory saved = inventoryService.createInventory(inventory);
		return ResponseEntity.status(201).body(saved);
	}

	@Operation(summary = "Update an inventory", description = "Updates an existing inventory record by ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Inventory updated successfully", content = @Content(schema = @Schema(implementation = Inventory.class))) })
	@PutMapping("/editInventory/{id}")
	public ResponseEntity<Inventory> updateInventory(
			@Parameter(description = "ID of the inventory to update", required = true) @PathVariable Long id,
			@Parameter(description = "Updated inventory object", required = true) @RequestBody Inventory inventory) {
		Inventory updated = inventoryService.updateInventory(id, inventory);
		return ResponseEntity.ok(updated);
	}

	@Operation(summary = "Get inventory by ID", description = "Retrieves an inventory record by its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Inventory found", content = @Content(schema = @Schema(implementation = Inventory.class))) })
	@GetMapping("/getInventory/{id}")
	public ResponseEntity<Inventory> getInventory(
			@Parameter(description = "ID of the inventory to retrieve", required = true) @PathVariable Long id) {
		Inventory inventory = inventoryService.getInventory(id);
		return ResponseEntity.ok(inventory);
	}

	@Operation(summary = "Get inventories by product ID", description = "Retrieves all inventories for a given product ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Inventories found", content = @Content(schema = @Schema(implementation = Inventory.class))) })
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Inventory>> getInventoriesByProductId(
			@Parameter(description = "Product ID to filter inventories", required = true) @PathVariable Integer productId) {
		List<Inventory> inventories = inventoryService.getInventoriesByProductId(productId);
		return ResponseEntity.ok(inventories);
	}

	@Operation(summary = "Delete inventory by ID", description = "Deletes an inventory record by its ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Inventory deleted successfully") })
	@DeleteMapping("/deleteInventory/{id}")
	public ResponseEntity<Void> deleteInventory(
			@Parameter(description = "ID of the inventory to delete", required = true) @PathVariable Long id) {
		inventoryService.deleteInventory(id);
		return ResponseEntity.noContent().build();
	}

	// Custom endpoints
	@Operation(summary = "Get inventories by location", description = "Retrieves all inventories for a given location")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Inventories found", content = @Content(schema = @Schema(implementation = Inventory.class))) })
	@GetMapping("/location/{location}")
	public ResponseEntity<List<Inventory>> getInventoriesByLocation(
			@Parameter(description = "Location to filter inventories", required = true) @PathVariable String location) {
		List<Inventory> inventories = inventoryService.getInventoriesByLocation(location);
		return ResponseEntity.ok(inventories);
	}

	@Operation(summary = "Get inventory by product and location", description = "Retrieves inventory for a given product and location")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Inventory found", content = @Content(schema = @Schema(implementation = Inventory.class))) })
	@GetMapping("/product/{productId}/location/{location}")
	public ResponseEntity<Inventory> getInventoryByProductAndLocation(
			@Parameter(description = "Product ID", required = true) @PathVariable Integer productId,
			@Parameter(description = "Location", required = true) @PathVariable String location) {
		Inventory inventory = inventoryService.getInventoryByProductAndLocation(productId, location);
		return ResponseEntity.ok(inventory);
	}

	@Operation(summary = "Get inventories by quantity range", description = "Retrieves all inventories with quantity between minQty and maxQty")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Inventories found", content = @Content(schema = @Schema(implementation = Inventory.class))) })
	@GetMapping("/quantity-range")
	public ResponseEntity<List<String>> getInventoriesByQuantityRange(
			@Parameter(description = "Minimum quantity", required = true) @RequestParam Integer minQty,
			@Parameter(description = "Maximum quantity", required = true) @RequestParam Integer maxQty) {
		List<String> inventories = inventoryService.getInventoriesByQuantityRange(minQty, maxQty);
		return ResponseEntity.ok(inventories);
	}

	@Operation(summary = "Get low stock inventories", description = "Retrieves all inventories with quantity below the given threshold")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Low stock inventories found", content = @Content(schema = @Schema(implementation = Inventory.class))) })
	@GetMapping("/low-stock/{threshold}")
	public ResponseEntity<List<Inventory>> getLowStockInventories(
			@Parameter(description = "Threshold for low stock", required = true) @PathVariable Integer threshold) {
		List<Inventory> inventories = inventoryService.getLowStockInventories(threshold);
		return ResponseEntity.ok(inventories);
	}

	@Operation(summary = "Get inventory summary by product ID", description = "Retrieves inventory summary for a given product ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Inventory summaries found", content = @Content(schema = @Schema(implementation = InventorySummaryDTO.class))) })
	@GetMapping("/summary/product/{productId}")
	public ResponseEntity<List<InventorySummaryDTO>> getInventorySummaryByProductId(
			@Parameter(description = "Product ID", required = true) @PathVariable Integer productId) {
		List<InventorySummaryDTO> summaries = inventoryService.getInventorySummaryByProductId(productId);
		return ResponseEntity.ok(summaries);
	}

	@Operation(summary = "Get low stock items", description = "Retrieves inventory summary for items below the given threshold")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Low stock items found", content = @Content(schema = @Schema(implementation = InventorySummaryDTO.class))) })
	@GetMapping("/low-stock-items/{threshold}")
	public ResponseEntity<List<InventorySummaryDTO>> getLowStockItems(
			@Parameter(description = "Threshold for low stock items", required = true) @PathVariable Integer threshold) {
		List<InventorySummaryDTO> items = inventoryService.getLowStockItems(threshold);
		return ResponseEntity.ok(items);
	}

	@Operation(summary = "Get inventories with minimum quantity", description = "Retrieves all inventories with at least the given minimum quantity")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Inventories found", content = @Content(schema = @Schema(implementation = Inventory.class))) })
	@GetMapping("/min-quantity/{minQty}")
	public ResponseEntity<List<Inventory>> getInventoriesWithMinQuantity(
			@Parameter(description = "Minimum quantity", required = true) @PathVariable int minQty) {
		List<Inventory> inventories = inventoryService.getInventoriesWithMinQuantity(minQty);
		return ResponseEntity.ok(inventories);
	}

	@Operation(summary = "Get inventory report with minimum quantity", description = "Retrieves inventory report for items with at least the given minimum quantity")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Inventory report found", content = @Content(schema = @Schema(implementation = InventoryReportDTO.class))) })
	@GetMapping("/report/min-quantity/{minQty}")
	public ResponseEntity<List<InventoryReportDTO>> getInventoryReportWithMinQuantity(
			@Parameter(description = "Minimum quantity", required = true) @PathVariable int minQty) {
		List<InventoryReportDTO> report = inventoryService.getInventoryReportWithMinQuantity(minQty);
		return ResponseEntity.ok(report);
	}
	
	@Operation(summary = "Get inventory report only with location and quantity by product id", description = "Retrieves inventory report for items with only location and quantity By Product Id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Inventory report found", content = @Content(schema = @Schema(implementation = Inventory.class))) })
	@GetMapping("/report/location-quantity/{id}")
	public ResponseEntity<List<Inventory>> getInventoryReportWithOnlyLocationAndQuantity(
			@Parameter(description = "Minimum quantity", required = true) @PathVariable Long id) {
		List<Inventory> report = inventoryService.getInventoryLocAndQuanByProductId(id);
		return ResponseEntity.ok(report);
	}
	
	@Operation(summary = "Get inventory report only with location, quantity and product name By Product Id", description = "Retrieves inventory report for items with only location, quantity and product name by product id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Inventory report found", content = @Content(schema = @Schema(implementation = LocQuanAndProdNameInvProjection.class))) })
	@GetMapping("/report/location-quantity-name/{id}")
	public ResponseEntity<List<LocQuanAndProdNameInvProjection>> getInventoryReportWithOnlyLocQuanAndProdName(
			@Parameter(description = "Minimum quantity", required = true) @PathVariable Long id) {
		List<LocQuanAndProdNameInvProjection> report = inventoryService.getInventoryLocQuanAndProdNameByProdId(id);
		return ResponseEntity.ok(report);
	}
}