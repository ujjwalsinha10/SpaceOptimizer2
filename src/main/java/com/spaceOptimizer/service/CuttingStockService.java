package com.spaceOptimizer.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.spaceOptimizer.models.CutPiece;
import com.spaceOptimizer.models.OptimizationResult;

@Service
public class CuttingStockService {

    private final CuttingStockOptimizer optimizer = new CuttingStockOptimizer();
    private final CuttingPlanPdfGenerator pdfGenerator = new CuttingPlanPdfGenerator();

    public void generateCuttingPlanPdf(List<CutPiece> requiredPieces, int stockWidth, int stockHeight, String outputPdfPath) throws Exception {
        // Perform optimization
        OptimizationResult result = optimizer.optimize(requiredPieces, stockWidth, stockHeight);
        
        // Generate PDF from the optimization result
        pdfGenerator.generateCuttingPlanPdf(result, outputPdfPath);
    }

	public String createFile() {
		return pdfGenerator.createFile();
	}
}
