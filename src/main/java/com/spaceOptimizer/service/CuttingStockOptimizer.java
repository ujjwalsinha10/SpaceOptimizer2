package com.spaceOptimizer.service;
import java.util.ArrayList;
import java.util.List;

import com.spaceOptimizer.models.CutPiece;
import com.spaceOptimizer.models.OptimizationResult;
import com.spaceOptimizer.models.StockSheet;

public class CuttingStockOptimizer {

    // Method to optimize cutting stock
    public OptimizationResult optimize(List<CutPiece> requiredPieces, int stockWidth, int stockHeight) {
        // Sort pieces by area (width * height) in descending order
        requiredPieces.sort((p1, p2) -> (p2.getWidth() * p2.getHeight()) - (p1.getWidth() * p1.getHeight()));

        List<StockSheet> stockSheets = new ArrayList<>();
        List<CutPiece> cutPieces= new ArrayList<>();
//        int cutPieceCount=0;
        /*
        for (CutPiece cutPiece : requiredPieces) {
            for (int i = 0; i < cutPiece.getQuantity(); i++) {
                // Try to fit the current piece into an existing sheet
                boolean placed = false;
                for (StockSheet sheet : stockSheets) {
                    if (canPlacePiece(sheet, cutPiece)) {
                        CutPiece piece=placePiece(sheet, cutPiece);
                        placed = true;
                        cutPieces.add(piece);
                        break;
                    }
                    sheet.setCutPieces(cutPieces);
                }

                // If the piece couldn't fit in any existing sheet, create a new sheet
                if (!placed) {
                    StockSheet newSheet = new StockSheet(stockWidth, stockHeight);
                    CutPiece piece=placePiece(newSheet, cutPiece);
                    cutPieces.add(piece);
                    newSheet.setCutPieces(cutPieces);
                    stockSheets.add(newSheet);
                    
                }
            }
        }
        */
        
        StockSheet newSheet = new StockSheet(stockWidth, stockHeight);
		for (CutPiece cutPiece : requiredPieces) {
			for (int i = 0; i < cutPiece.getQuantity(); i++) {
				// Try to fit the current piece into an existing sheet
				if (canPlacePiece(newSheet, cutPiece)) {
					CutPiece piece = placePiece(newSheet, cutPiece);
					cutPieces.add(piece);
					newSheet.setCutPieces(cutPieces);
				}else {
					
				}
			}
		}

        // Calculate total waste
        int totalWaste = calculateTotalWaste(newSheet, stockWidth, stockHeight);

        OptimizationResult optimizationResult = new OptimizationResult(newSheet, newSheet.getRemainingArea());
		return optimizationResult;
    }

	private boolean canPlacePiece(StockSheet sheet, CutPiece piece) {
		// Check if piece can fit into the remaining area of the stock sheet
		// You can add more sophisticated placement logic (e.g., using a packing
		// algorithm)
		int totalArea = (sheet.getWidth() * sheet.getHeight()) - sheet.getUsedArea()
				- (piece.getHeight() * piece.getWidth());
		return totalArea > 0 ? true : false;
	}

	private CutPiece placePiece(StockSheet sheet, CutPiece piece) {
		// Place the piece on the sheet and update used area
		int pieceArea = piece.getWidth() * piece.getHeight();
		sheet.setUsedArea(sheet.getUsedArea() + pieceArea);
		sheet.setRemainingArea(
				(sheet.getWidth() * sheet.getHeight()) - sheet.getUsedArea());
		return piece;
	}

//    private int calculateTotalWaste(List<StockSheet> stockSheets, int stockWidth, int stockHeight) {
//        int totalArea = stockWidth * stockHeight * stockSheets.size();
//        int usedArea = 0;
//        for (StockSheet sheet : stockSheets) {
//            usedArea += sheet.getUsedArea();
//        }
//        return totalArea - usedArea;
//    }
	
	private int calculateTotalWaste(StockSheet stockSheet, int stockWidth, int stockHeight) {
        int totalArea = stockWidth * stockHeight;
        int usedArea = 0;
//        for (StockSheet sheet : stockSheets) {
            usedArea += stockSheet.getUsedArea();
//        }
        return totalArea - usedArea;
    }
}
