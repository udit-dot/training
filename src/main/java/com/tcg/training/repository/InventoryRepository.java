package com.tcg.training.repository;

import com.tcg.training.entity.Inventory;
import com.tcg.training.dto.InventorySummaryDTO;
import com.tcg.training.dto.InventoryReportDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

	// Method naming conventions
	List<Inventory> findByLocation(String location);

	List<Inventory> findByProductProductId(Integer productId);

	Inventory findByProductProductIdAndLocation(Integer productId, String location);

	List<Inventory> findByQuantityLessThan(Integer quantity);

	List<Inventory> findByQuantityGreaterThan(Integer quantity);

	// JPQL queries
	@Query("SELECT i FROM Inventory i WHERE i.product.productName = :name")
	List<Inventory> findInventoriesByProductName(@Param("name") String name);

	@Query("SELECT i FROM Inventory i WHERE i.quantity BETWEEN :minQty AND :maxQty")
	List<Inventory> findInventoriesByQuantityRange(@Param("minQty") Integer minQty, @Param("maxQty") Integer maxQty);

	// DTO projection using JPQL constructor expression
	@Query("SELECT new com.tcg.training.dto.InventorySummaryDTO(i.product.productName, i.location, i.quantity) FROM Inventory i WHERE i.product.productId = :productId")
	List<InventorySummaryDTO> findInventorySummaryByProductId(@Param("productId") Integer productId);

	@Query("SELECT new com.tcg.training.dto.InventorySummaryDTO(i.product.productName, i.location, i.quantity) FROM Inventory i WHERE i.quantity < :threshold")
	List<InventorySummaryDTO> findLowStockItems(@Param("threshold") Integer threshold);

	// Native SQL queries
	@Query(value = "SELECT * FROM inventory WHERE quantity > :minQty", nativeQuery = true)
	List<Inventory> findInventoriesWithMinQuantity(@Param("minQty") int minQty);

	// Native query with @SqlResultSetMapping
	@Query(value = "SELECT p.product_name, i.location, i.quantity, p.product_price, (i.quantity * p.product_price) as total_value FROM inventory i JOIN product p ON i.product_id = p.product_id WHERE i.quantity > :minQty", nativeQuery = true)
	List<InventoryReportDTO> findInventoryReportWithMinQuantity(@Param("minQty") int minQty);
}