package farms4life2016.gui.tables;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Arrays;

import farms4life2016.dataprocessing.Controller;
import farms4life2016.gui.StringDrawer;

public class LegacyTable {
	
	private String[][] data;
	private String[] headers;
	private int[] rowHeights, columnWidths;
	private int headerFontSize, dataFontSize;
	private Color outlineCol, databackground, headerbackground;
	private Rectangle dimensions; 
	
	//TODO row selection
	Color selOutline, selDataBg, selHeadBg;
	int selRow, selCol;
	boolean isSelecting;
	
	public static final int OUTLINE_WIDTH = 3;
	
	public static final String 
	 SAMPLE_HEAD[] = {"Item", "Cost", "Source", "Availability"},
	 SAMPLE_DATA[][] = {
			{"Fog Machine", "1 g", "Party Girl", "At night"},
			{"Bubble Machine", "1 g", "Party Girl", "During the day"},
			{"Balloon Machine", "5 g", "Party Girl", "Always available"}
	};
	
	/**
	 * Make a new table based on
	 * @param headers
	 * @param data
	 */
	public LegacyTable(String[] headers, String[][] data) {
		this.data = data;
		this.headers = headers;
		
		//init rest of variables
		dimensions = new Rectangle(50, 200, Controller.WINDOW_W-130, Controller.WINDOW_H-300);
		rowHeights = new int[data.length+1]; //add one for the headers
		columnWidths = new int[data[0].length];

		Arrays.fill(rowHeights, ( (Controller.WINDOW_H-300) / (data.length+1)) );
		Arrays.fill(columnWidths, 100);

		//just another check
		setColumnWidths(columnWidths);
		setRowHeights(rowHeights);

		headerFontSize = 16;
		dataFontSize = 14;

		headerbackground = Color.YELLOW;
		databackground = Color.WHITE;

		System.out.printf("%d   %d    %d    %d %n", 50, 200, Controller.WINDOW_W-130, Controller.WINDOW_H-300);
		System.out.println(dimensions);
		
		
	}
	
	public void drawSelf(Graphics2D g) {

		//g.setColor(Color.CYAN);
		
		//g.fillRect(dimensions.x + dimensions.width, dimensions.y, OUTLINE_WIDTH, dimensions.height);
		//g.fillRect(dimensions.x, dimensions.y + dimensions.height, dimensions.width, OUTLINE_WIDTH);

		//draw the grid
		g.setColor(Color.BLACK);
		g.fillRect(dimensions.x, dimensions.y, OUTLINE_WIDTH, Controller.WINDOW_H-300);
		g.fillRect(dimensions.x, dimensions.y, dimensions.width, OUTLINE_WIDTH);

		int pointer = dimensions.y;
		for (int i = 0; i < rowHeights.length; i++) {
			pointer += rowHeights[i];
			g.fillRect(dimensions.x, pointer, dimensions.width, OUTLINE_WIDTH);
		}

		pointer = dimensions.x;
		for (int i = 0; i < columnWidths.length; i++) {
			pointer += columnWidths[i];
			g.fillRect(pointer, dimensions.y, OUTLINE_WIDTH, Controller.WINDOW_H-300);
		}

		//draw the text
		Rectangle textbox = null;
		int pointerX = dimensions.x; //drawing headers
		for (int i = 0; i < headers.length; i++) {
			textbox = new Rectangle(pointerX, dimensions.y, columnWidths[i], rowHeights[0]);
			g.setColor(headerbackground);
			g.fill(textbox);
			
			g.setColor(Color.BLACK);
			StringDrawer.drawStringCenteredYLeftAligned(g, headers[i], textbox, Font.PLAIN, headerFontSize);
			pointerX += columnWidths[i];
		}

		int pointerY = dimensions.y + rowHeights[0];
		pointerX = dimensions.x; //drawing data, requires loop for 2D array
		for (int r = 0; r < data.length; r++) {
			for (int c = 0; c < data[r].length; c++) { //header counts as the 0-th row
				textbox = new Rectangle(pointerX, pointerY, columnWidths[c], rowHeights[r+1]);
				g.setColor(databackground);
				g.fill(textbox);

				g.setColor(Color.BLACK);
				//StringDrawer.drawStringCenteredYLeftAligned(g, data[r][c], new Rectangle(pointerX, pointerY, columnWidths[c], rowHeights[r+1]), dataFontSize);
				StringDrawer.drawStringSuperCentered(g, data[r][c], textbox, Font.PLAIN, dataFontSize);
				pointerX += columnWidths[c];
			}
			//bring x pointer back to left side and start new row
			pointerX = dimensions.x;
			pointerY += rowHeights[r+1];
			
		}

	}

	/*
	 * Setters and getters
	 */

	public String[][] getData() {
		return data;
	}

	public void setData(String[][] data) {
		this.data = data;
	}

	public String[] getHeaders() {
		return headers;
	}

	public void setHeaders(String[] headers) {
		this.headers = headers;
	}

	public int[] getRowHeights() {
		return rowHeights;
	}

	public void setRowHeights(int[] rowHeights) {
		this.rowHeights = rowHeights;

		//check that the sum of row heights don't exceed the table's own height
		int lastHeight = dimensions.height;
		for (int i = 0; i < rowHeights.length; i++) {

			//if row heights do exceed, then the remaining rows become height 0
			if (lastHeight <= 0 || lastHeight - rowHeights[i] < 0) {
				rowHeights[i] = Math.max(lastHeight, 0);
			} 

			lastHeight -= rowHeights[i];
			
		}

		//if there is still some space remaining
		if (lastHeight > 0) {

			//assign rest of the space to last row
			rowHeights[rowHeights.length-1] += lastHeight;
		}

	}

	public int[] getColumnWidths() {
		return columnWidths;
	}

	public void setColumnWidths(int[] columnWidths) {
		this.columnWidths = columnWidths;

		//check that the sum of col widths don't exceed the table's own height
		int lastWidth = dimensions.width;
		for (int i = 0; i < columnWidths.length; i++) {

			//if widths do exceed, then the remaining cols become w = 0
			if (lastWidth <= 0 || lastWidth - columnWidths[i] < 0) {
				columnWidths[i] = Math.max(lastWidth, 0);
			} 

			lastWidth -= columnWidths[i];
			
		}

		

		//if there is still some space remaining
		if (lastWidth > 0) {

			//assign rest of the space to last row
			columnWidths[columnWidths.length-1] += lastWidth;
		}

		System.out.println(lastWidth + "   " + columnWidths[columnWidths.length-1]);

	}

	public int getHeaderFontSize() {
		return headerFontSize;
	}

	public void setHeaderFontSize(int headerFontSize) {
		this.headerFontSize = headerFontSize;
	}

	public int getDataFontSize() {
		return dataFontSize;
	}

	public void setDataFontSize(int dataFontSize) {
		this.dataFontSize = dataFontSize;
	}

	public Color getOutlineCol() {
		return outlineCol;
	}

	public void setOutlineCol(Color outline) {
		this.outlineCol = outline;
	}

	public Color getDatabackground() {
		return databackground;
	}

	public void setDatabackground(Color databackground) {
		this.databackground = databackground;
	}

	public Color getHeaderbackground() {
		return headerbackground;
	}

	public void setHeaderbackground(Color headerbackground) {
		this.headerbackground = headerbackground;
	}

	public Color getSelOutline() {
		return selOutline;
	}

	public void setSelOutline(Color selOutline) {
		this.selOutline = selOutline;
	}

	public Color getSelDataBg() {
		return selDataBg;
	}

	public void setSelDataBg(Color selDataBg) {
		this.selDataBg = selDataBg;
	}

	public Color getSelHeadBg() {
		return selHeadBg;
	}

	public void setSelHeadBg(Color selHeadBg) {
		this.selHeadBg = selHeadBg;
	}

	public int getSelRow() {
		return selRow;
	}

	public void setSelRow(int selRow) {
		this.selRow = selRow;
	}

	public int getSelCol() {
		return selCol;
	}

	public void setSelCol(int selCol) {
		this.selCol = selCol;
	}

	public Rectangle getDimensions() {
		return dimensions;
	}

	public void setDimensions(Rectangle dimensions) {
		this.dimensions = dimensions;
	}
	
	
	
	

}
