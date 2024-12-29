package com.spaceOptimizer.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CutPiece {
    private int width;   // Width of the cut piece
    private int height;  // Height of the cut piece
    private int quantity; // Number of pieces needed
    private float x;  // X coordinate on the sheet
    private float y;  // Y coordinate on the sheet
}
