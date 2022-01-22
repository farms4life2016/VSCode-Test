package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Table {
	
	private String[][] data;
	private String[] headers;
	private int[] rowHeights, columnHeights;
	private int headerFontSize, dataFontSize;
	private Color outlineCol, databackground, headerbackground;
	private Rectangle dimensions; 
	
	//TODO row selection
	Color selOutline, selDataBg, selHeadBg;
	int selRow, selCol;
	boolean isSelecting;
	
	public static final int OUTLINE_WIDTH = 2;
	
	private static final String 
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
	public Table(String[] headers, String[][] data) {
		this.data = data;
		this.headers = headers;
		
		//init rest of variables
		
	}
	
	public void drawSelf(Graphics2D g, StringDrawer s) {
		
		g.setColor(Color.BLACK);
		g.drawRect(dimensions.x, dimensions.y, dimensions.width, dimensions.height);
		
		
	}

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
	}

	public int[] getColumnHeights() {
		return columnHeights;
	}

	public void setColumnHeights(int[] columnHeights) {
		this.columnHeights = columnHeights;
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
