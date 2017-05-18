/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.excel;

import com.excel.annotation.ReadExcel.Excel;

public final class ReadExcel {
	private int rowIndex;
	private ReadExcel.Excel readWay;
	private int sheetIndex;
	private String sheetName;

	public ReadExcel() {
		this.readWay = ReadExcel.Excel.SHEET_INDEX;

		this.sheetName = "Sheet1";
	}

	public ReadExcel(int sheetIndex) {
		this.readWay = ReadExcel.Excel.SHEET_INDEX;

		this.sheetName = "Sheet1";

		this.sheetIndex = sheetIndex;
	}

	public ReadExcel(String sheetName) {
		this(0, ReadExcel.Excel.SHEET_NAME, sheetName);
	}

	public ReadExcel(ReadExcel.Excel readWay) {
		this.readWay = ReadExcel.Excel.SHEET_INDEX;

		this.sheetName = "Sheet1";

		this.readWay = readWay;
	}

	public ReadExcel(int rowIndex, int sheetIndex) {
		this(rowIndex, ReadExcel.Excel.SHEET_INDEX, sheetIndex);
	}

	public ReadExcel(int rowIndex, String sheetName) {
		this(rowIndex, ReadExcel.Excel.SHEET_NAME, sheetName);
	}

	public ReadExcel(int rowIndex, ReadExcel.Excel readWay, int sheetIndex) {
		this.readWay = ReadExcel.Excel.SHEET_INDEX;

		this.sheetName = "Sheet1";

		this.rowIndex = rowIndex;
		this.readWay = readWay;
		this.sheetIndex = sheetIndex;
	}

	public ReadExcel(int rowIndex, ReadExcel.Excel readWay, String sheetName) {
		this.readWay = ReadExcel.Excel.SHEET_INDEX;

		this.sheetName = "Sheet1";

		this.rowIndex = rowIndex;
		this.readWay = readWay;
		this.sheetName = sheetName;
	}

	int getRowIndex() {
		return this.rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	ReadExcel.Excel getReadWay() {
		return this.readWay;
	}

	public void setReadWay(ReadExcel.Excel readWay) {
		this.readWay = readWay;
	}

	int getSheetIndex() {
		return this.sheetIndex;
	}

	public void setSheetIndex(int sheetIndex) {
		this.sheetIndex = sheetIndex;
	}

	String getSheetName() {
		return this.sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
}