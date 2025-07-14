package com.tcg.training.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventorySummaryDTO {
	private String productName;
	private String location;
	private Integer quantity;

}