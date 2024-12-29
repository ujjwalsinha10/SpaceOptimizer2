package com.spaceOptimizer.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockSheet {
    private int width;  // Width of the stock sheet
    private int height; // Height of the stock sheet
    private int usedArea; // Area used in the sheet so far
    private List<CutPiece> cutPieces; // List of cut pieces on this sheet
    private int remainingArea;
    
	public StockSheet(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}
    
	public void placePiece(CutPiece piece, float x, float y) {
        piece.setX(x);
        piece.setY(y);
        cutPieces.add(piece);
        usedArea += piece.getWidth() * piece.getHeight();
    }
    
}
