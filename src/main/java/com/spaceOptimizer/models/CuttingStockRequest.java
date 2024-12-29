package com.spaceOptimizer.models;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data

public class CuttingStockRequest {
	private int stockWidth;
	private int stockHeight;
	private List<CutPiece> requiredPieces;

	// Getters and setters
}
