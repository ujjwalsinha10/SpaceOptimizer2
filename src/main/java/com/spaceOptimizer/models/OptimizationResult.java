package com.spaceOptimizer.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptimizationResult {
//    private List<StockSheet> stockSheets; // List of optimized stock sheets
	private StockSheet stockSheets; // List of optimized stock sheets
    private int totalWaste; // Total area wasted
}
