package com.spaceOptimizer.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.spaceOptimizer.models.CutPiece;
import com.spaceOptimizer.models.OptimizationResult;
import com.spaceOptimizer.models.StockSheet;

public class CuttingPlanPdfGenerator {
	
	// Method to generate PDF document with cutting plan visualization
	public void generateCuttingPlanPdf(OptimizationResult result, String filePath) throws Exception {
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(filePath));
		Document doc = new Document(pdfDoc);

		// Iterate over stock sheets and draw each piece on the stock sheet
		StockSheet sheet = result.getStockSheets();
		doc.add(new Paragraph("Stock Sheet: " + sheet.getWidth() + " x " + sheet.getHeight()).setFontSize(14)
				.setTextAlignment(TextAlignment.CENTER));

		// Track the next available position on the stock sheet
		float x = 0;
		float y = 0;
		float maxHeightInRow = 0;

		// Iterate over the cut pieces and draw them on the sheet
		for (CutPiece piece : sheet.getCutPieces()) {
			if (x + piece.getWidth() > sheet.getWidth()) {
				// Move to the next row if the piece doesn't fit horizontally
				x = 0; // Reset x to the left
				y += maxHeightInRow; // Move y to the next row
				maxHeightInRow = 0; // Reset maxHeightInRow for the next row
			}

			// Now we have space to place the piece at (x, y)
			piece.setX(x);
			piece.setY(y);

			// Draw the piece on the canvas
			drawCutPiece(pdfDoc, piece);

			// Update the position for the next piece
			x += piece.getWidth(); // Move x to the right after placing the piece

			// Update the maxHeightInRow to ensure proper row spacing
			maxHeightInRow = Math.max(maxHeightInRow, piece.getHeight());
		}

		doc.close();
	}

	// Method to draw a single cut piece on the PDF with custom color border and
	// fill
	private void drawCutPiece(PdfDocument pdfDoc, CutPiece piece) throws Exception {
		float x = piece.getX(); // X-coordinate of the cut piece
		float y = piece.getY(); // Y-coordinate of the cut piece
		float width = piece.getWidth();
		float height = piece.getHeight();

		// Get the canvas for drawing on the first page
		PdfCanvas canvas = new PdfCanvas(pdfDoc.getFirstPage());

		// Set the fill color to blue (RGB) for the interior
		canvas.setFillColor(new DeviceRgb(0, 0, 255)); // Blue color for filling

		// Set the stroke (border) color to red (RGB)
		canvas.setStrokeColor(new DeviceRgb(255, 0, 0)); // Red color for border

		// Set the line dash to dotted
		canvas.setLineDash(3, 2); // Dotted line pattern (3 units on, 2 units off)

		// Draw the rectangle representing the cut piece (only the border, no fill)
		canvas.rectangle(x, y, width, height); // Define the rectangle

		// Apply the dotted border (stroke the rectangle)
		canvas.stroke();

		// Now, fill the rectangle with the blue color
		canvas.setFillColor(new DeviceRgb(0, 0, 255)); // Re-apply blue fill color
		canvas.fill();

		// Optionally add a label with the dimensions of the piece (inside the
		// rectangle)
		canvas.beginText();
		canvas.setFontAndSize(PdfFontFactory.createFont(), 10);
		canvas.moveText(x + 2, y + 2); // Move text slightly inside the rectangle
		canvas.showText(piece.getWidth() + " x " + piece.getHeight()); // Show text inside the rectangle
		canvas.endText();
	}
	
	
	public String createFile() {
		String outputPdfPath = "output/cutting_plan.pdf";
		Path outputPath = Paths.get(outputPdfPath).getParent();

		if (outputPath != null) {
			// Check if the directory exists, and create it if it doesn't
			File directory = outputPath.toFile();
			if (!directory.exists()) {
				directory.mkdirs(); // Create the directory and any necessary parent directories
			}
		}
		return outputPdfPath;
	}
}
