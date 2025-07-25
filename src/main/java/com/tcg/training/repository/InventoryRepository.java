package com.tcg.training.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tcg.training.dto.InventoryReportDTO;
import com.tcg.training.dto.InventorySummaryDTO;
import com.tcg.training.entity.Inventory;
import com.tcg.training.projection.LocQuanAndProdNameInvProjection;

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

	@Query("SELECT i.location FROM Inventory i WHERE i.quantity BETWEEN :minQty AND :maxQty")
	List<String> findlocbyQty(@Param("minQty") Integer minQty, @Param("maxQty") Integer maxQty);

	// DTO projection using JPQL constructor expression
	@Query("SELECT new com.tcg.training.dto.InventorySummaryDTO(i.product.productName, i.location, i.quantity) FROM Inventory i WHERE i.product.productId = :productId")
	List<InventorySummaryDTO> findInventorySummaryByProductId(@Param("productId") Integer productId);

	@Query("SELECT new com.tcg.training.dto.InventorySummaryDTO(i.product.productName, i.location, i.quantity) FROM Inventory i WHERE i.quantity < :threshold")
	List<InventorySummaryDTO> findLowStockItems(@Param("threshold") Integer threshold);

	// Native SQL queries
	@Query(value = "SELECT * FROM inventory WHERE quantity > :minQty", nativeQuery = true)
	List<Inventory> findInventoriesWithMinQuantity(@Param("minQty") int minQty);

	// Native query with @SqlResultSetMapping
//	@SqlResultSetMapping(name = "InventoryReportMapping")
	@Query(value = "SELECT p.product_name, i.location, i.quantity, p.product_price, (i.quantity * p.product_price) as total_value FROM inventory i JOIN product p ON i.product_id = p.product_id WHERE i.quantity > :minQty", nativeQuery = true)
	List<InventoryReportDTO> findInventoryReportWithMinQuantity(@Param("minQty") int minQty);

	// JPQL query returns the select field. Return type is object[] because we fetch
	// the selected fields(location and quantity)
	@Query("SELECT i.location, i.quantity FROM Inventory i WHERE i.product.id = :id")
	List<Object[]> getLocationAndQuantity(@Param("id") Long id);

	@Query("SELECT i.location as location, i.quantity as quantity, i.product.productName as productName FROM Inventory i WHERE i.product.id = :id")
	List<LocQuanAndProdNameInvProjection> getLocationQuantityAndProdName(@Param("id") Long id);

//	@Query(value = "SELECT i.location as location, i.quantity as quantity, p.product_name as productName FROM Inventory i join product p ON i.product_id = p.product_id WHERE i.product_id = :id", nativeQuery = true)
//	List<LocQuanAndProdNameInvProjection> getLocationQuantityAndProdName(@Param("id") Long id);
}