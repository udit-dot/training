package com.tcg.training.service;

import com.tcg.training.entity.Inventory;
import com.tcg.training.projection.LocQuanAndProdNameInvProjection;
import com.tcg.training.dto.InventorySummaryDTO;
import com.tcg.training.dto.InventoryReportDTO;
import java.util.List;

public interface InventoryService {
	Inventory createInventory(Inventory inventory);

	Inventory updateInventory(Long id, Inventory inventory);

	Inventory getInventory(Long id);

	List<Inventory> getInventoriesByProductId(Integer productId);

	void deleteInventory(Long id);

	// Custom methods using repository
	List<Inventory> getInventoriesByLocation(String location);

	Inventory getInventoryByProductAndLocation(Integer productId, String location);

	List<String> getInventoriesByQuantityRange(Integer minQty, Integer maxQty);

	List<Inventory> getLowStockInventories(Integer threshold);

	List<InventorySummaryDTO> getInventorySummaryByProductId(Integer productId);

	List<InventorySummaryDTO> getLowStockItems(Integer threshold);

	List<Inventory> getInventoriesWithMinQuantity(int minQty);

	List<InventoryReportDTO> getInventoryReportWithMinQuantity(int minQty);
	
	List<Inventory> getInventoryLocAndQuanByProductId(Long id);
	
	List<LocQuanAndProdNameInvProjection> getInventoryLocQuanAndProdNameByProdId(Long prodId);
}