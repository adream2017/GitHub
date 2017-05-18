/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.excel;

import com.excel.annotation.ReadExcel.Excel;
import com.lang.ExceptionUtil;
import com.lang.annotation.ReadField;
import com.reflect.ReflectUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ExcelInfo {
	private static Map<Field, Object> fileldsMap = null;
	private static Class<?> cls_DateUtil = null;

	private static Excel<?> excel = null;

	private static Object getCellValue(Object cell) throws Exception {
		ExceptionUtil.nullThrowException(cell, "cell must not be null");
		Class cls_cell = cell.getClass();
		Object cellType = cls_cell.getMethod("getCellType", new Class[0])
				.invoke(cell, new Object[0]);
		if (cellType.equals(cls_cell.getField("CELL_TYPE_BOOLEAN").get(null)))
			return cls_cell.getMethod("getBooleanCellValue", new Class[0])
					.invoke(cell, new Object[0]);
		if (cellType.equals(cls_cell.getField("CELL_TYPE_STRING").get(null)))
			return cls_cell.getMethod("getStringCellValue", new Class[0])
					.invoke(cell, new Object[0]);
		if (cellType.equals(cls_cell.getField("CELL_TYPE_NUMERIC").get(null))) {
			if (Boolean.TRUE.equals(cls_DateUtil.getMethod(
					"isCellDateFormatted",
					new Class[] { cls_cell.getInterfaces()[0] }).invoke(null,
					new Object[] { cell }))) {
				return cls_cell.getMethod("getDateCellValue", new Class[0])
						.invoke(cell, new Object[0]);
			}
			return cls_cell.getMethod("getNumericCellValue", new Class[0])
					.invoke(cell, new Object[0]);
		}
		return cell;
	}

	public static <T> ArrayList<T> readExcel(Class<? super T> cls, String path)
			throws Exception {
		ExceptionUtil.nullOrWhiteSpaceThrowException(path, "文件路径不能为空");
		return readExcel(cls, new File(path));
	}

	public static <T> ArrayList<T> readExcel(Class<? super T> cls, File file)
			throws Exception {
		excel = new Excel(cls);
		ArrayList list = new ArrayList();
		com.excel.annotation.ReadExcel readExcel = (com.excel.annotation.ReadExcel) cls
				.getAnnotation(com.excel.annotation.ReadExcel.class);
		if (readExcel == null)
			return Excel.dispose(excel, fileldsMap, list);
		ExceptionUtil.nullThrowException(file, "文件不能为空");
		if (!(file.exists()))
			throw new FileNotFoundException("文件\"" + file.getAbsolutePath()
					+ "\"不存在");
		if (!(file.isFile()))
			throw new IOException("无效的excel文件");
		Class cls_workbook = null;
		try {
			cls_workbook = Class
					.forName("org.apache.poi.xssf.usermodel.XSSFWorkbook");
		} catch (Exception e) {
			throw new ClassNotFoundException(
					"can't find the class of org.apache.poi.xssf.usermodel.XSSFWorkbook");
		}
		try {
			cls_DateUtil = Class
					.forName("org.apache.poi.ss.usermodel.DateUtil");
		} catch (Exception e) {
			throw new ClassNotFoundException(
					"can't find the class of org.apache.poi.ss.usermodel.DateUtil");
		}
		Object workbook = null;
		Object sheet = null;
		Method getNumberOfSheets = null;
		try {
			workbook = cls_workbook.getConstructor(new Class[] { File.class })
					.newInstance(new Object[] { file });
			if (readExcel.readWay() == ReadExcel.Excel.SHEET_INDEX)
				getNumberOfSheets = cls_workbook.getMethod("getNumberOfSheets",
						new Class[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (readExcel.readWay() == ReadExcel.Excel.SHEET_INDEX) {
			if (getNumberOfSheets == null)
				return Excel.dispose(excel, fileldsMap, list);
			int maxSheetIndex = ((Integer) getNumberOfSheets.invoke(workbook,
					new Object[0])).intValue() - 1;
			int index = readExcel.sheetIndex();
			ExceptionUtil.lessZeroThrowIndexOutOfBoundsException(index,
					"索引必须为非负数");
			ExceptionUtil.gtThrowIndexOutOfBoundsException(index,
					maxSheetIndex, "索引必须在0-" + maxSheetIndex + "之间");
			try {
				Method getSheetAt = cls_workbook.getMethod("getSheetAt",
						new Class[] { Integer.TYPE });
				sheet = getSheetAt.invoke(workbook,
						new Object[] { Integer.valueOf(index) });
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			String sheetName = readExcel.sheetName();
			ExceptionUtil
					.nullOrWhiteSpaceThrowException(sheetName, "工作簿名字不能为空");
			Method getSheet = cls_workbook.getMethod("getSheet",
					new Class[] { String.class });
			sheet = getSheet.invoke(workbook, new Object[] { sheetName });
		}
		if (sheet == null)
			return Excel.dispose(excel, fileldsMap, list);
		Method getLastRowNum = sheet.getClass().getMethod("getLastRowNum",
				new Class[0]);
		int lastRowNum = ((Integer) getLastRowNum.invoke(sheet, new Object[0]))
				.intValue();
		int rowindex = readExcel.rowIndex();
		ExceptionUtil.lessZeroThrowIndexOutOfBoundsException(rowindex,
				"索引必须为非负数");
		ExceptionUtil.gtThrowIndexOutOfBoundsException(rowindex, lastRowNum,
				"索引必须在0-" + lastRowNum + "之间");
		for (int i = rowindex; i <= lastRowNum; ++i) {
			Method getRow = sheet.getClass().getMethod("getRow",
					new Class[] { Integer.TYPE });
			Object row = getRow.invoke(sheet,
					new Object[] { Integer.valueOf(i) });
			ExceptionUtil.nullThrowException(row, "工作簿不存在或无数据");
			Class cls_row = row.getClass();
			Method getLastCellNum = cls_row.getMethod("getLastCellNum",
					new Class[0]);
			short lastCellNum = ((Short) getLastCellNum.invoke(row,
					new Object[0])).shortValue();
			Method getCell = cls_row.getMethod("getCell",
					new Class[] { Integer.TYPE });
			list.add(excel.readFileds(cls, row, getCell,
					lastCellNum = (short) (lastCellNum - 1), fileldsMap));
		}
		cls_workbook.getMethod("close", new Class[0]).invoke(workbook,
				new Object[0]);
		return Excel.dispose(excel, fileldsMap, list);
	}

	public static ArrayList<Object[]> readExcel(ReadExcel readExcel, String path)
			throws Exception {
		ExceptionUtil.nullOrWhiteSpaceThrowException(path, "文件路径不能为空");
		return readExcel(readExcel, new File(path));
	}

	public static ArrayList<Object[]> readExcel(ReadExcel readExcel, File file)
			throws Exception {
		ExceptionUtil.nullThrowException(readExcel, "readExcel is null");
		ReadExcel.Excel excel = readExcel.getReadWay();
		ExceptionUtil.nullThrowException(excel,
				"readExcel.getReadWay() is null");
		ExceptionUtil.nullThrowException(file, "文件不能为空");
		if (!(file.exists()))
			throw new FileNotFoundException("文件\"" + file.getAbsolutePath()
					+ "\"不存在");
		if (!(file.isFile()))
			throw new IOException("无效的excel文件");
		Class cls_workbook = null;
		try {
			cls_workbook = Class
					.forName("org.apache.poi.xssf.usermodel.XSSFWorkbook");
		} catch (Exception e) {
			throw new ClassNotFoundException(
					"can't find the class of org.apache.poi.xssf.usermodel.XSSFWorkbook");
		}
		Object workbook = null;
		Object sheet = null;
		Method getNumberOfSheets = null;
		try {
			workbook = cls_workbook.getConstructor(new Class[] { File.class })
					.newInstance(new Object[] { file });
			if (excel == ReadExcel.Excel.SHEET_INDEX)
				getNumberOfSheets = cls_workbook.getMethod("getNumberOfSheets",
						new Class[0]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ArrayList list = new ArrayList();
		if (excel == ReadExcel.Excel.SHEET_INDEX) {
			if (getNumberOfSheets == null)
				return list;
			int maxSheetIndex = ((Integer) getNumberOfSheets.invoke(workbook,
					new Object[0])).intValue() - 1;
			int index = readExcel.getSheetIndex();
			ExceptionUtil.lessZeroThrowIndexOutOfBoundsException(index,
					"索引必须为非负数");
			ExceptionUtil.gtThrowIndexOutOfBoundsException(index,
					maxSheetIndex, "索引必须在0-" + maxSheetIndex + "之间");
			try {
				Method getSheetAt = cls_workbook.getMethod("getSheetAt",
						new Class[] { Integer.TYPE });
				sheet = getSheetAt.invoke(workbook,
						new Object[] { Integer.valueOf(index) });
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			String sheetName = readExcel.getSheetName();
			ExceptionUtil
					.nullOrWhiteSpaceThrowException(sheetName, "工作簿名字不能为空");
			Method getSheet = cls_workbook.getMethod("getSheet",
					new Class[] { String.class });
			sheet = getSheet.invoke(workbook, new Object[] { sheetName });
		}
		if (sheet == null)
			return list;
		Method getLastRowNum = sheet.getClass().getMethod("getLastRowNum",
				new Class[0]);
		int lastRowNum = ((Integer) getLastRowNum.invoke(sheet, new Object[0]))
				.intValue();
		int rowindex = readExcel.getRowIndex();
		ExceptionUtil.lessZeroThrowIndexOutOfBoundsException(rowindex,
				"索引必须为非负数");
		ExceptionUtil.gtThrowIndexOutOfBoundsException(rowindex, lastRowNum,
				"索引必须在0-" + lastRowNum + "之间");
		for (int i = rowindex; i <= lastRowNum; ++i) {
			Method getRow = sheet.getClass().getMethod("getRow",
					new Class[] { Integer.TYPE });
			Object row = getRow.invoke(sheet,
					new Object[] { Integer.valueOf(i) });
			ExceptionUtil.nullThrowException(row, "工作簿不存在或无数据");
			Class cls_row = row.getClass();
			Method getLastCellNum = cls_row.getMethod("getLastCellNum",
					new Class[0]);
			short lastCellNum = ((Short) getLastCellNum.invoke(row,
					new Object[0])).shortValue();
			Method getCell = cls_row.getMethod("getCell",
					new Class[] { Integer.TYPE });
			Object[] objArr = new Object[lastCellNum];
			for (int j = 0; j < lastCellNum; ++j)
				objArr[j] = getCellValue(getCell.invoke(row,
						new Object[] { Integer.valueOf(j) }));
			list.add(objArr);
		}
		cls_workbook.getMethod("close", new Class[0]).invoke(workbook,
				new Object[0]);
		return list;
	}

	private static class Excel<T> extends ReflectUtil<T> {
		public Excel(Class<? super T> cls) throws NoSuchFieldException,
				NoSuchMethodException {
			super(cls);
			ExcelInfo.fileldsMap = this.fileldsMap;
		}

		public Object readFileds(Class<?> cls, Object row, Method getCell,
				short lastCellNum, Map<Field, Object> fileldsMap)
				throws Exception {
			if ((row == null) || (getCell == null) || (lastCellNum < 0)
					|| (fileldsMap == null) || (fileldsMap.isEmpty()))
				return null;
			Object obj = cls.newInstance();
			for (Iterator i = fileldsMap.entrySet().iterator(); i.hasNext();) {
				Map.Entry entry = (Map.Entry) i.next();
				Field f = (Field) entry.getKey();
				Object value = entry.getValue();
				if (value instanceof AbstractMap.SimpleEntry) {
					AbstractMap.SimpleEntry simpleEntry = (AbstractMap.SimpleEntry) value;
					if (!(simpleEntry.getValue() instanceof HashMap)) {
						continue;
					}
					Object res = readFileds(f.getType(), row, getCell,
							lastCellNum, (Map) simpleEntry.getValue());
					if (Modifier.isPublic(f.getModifiers()))
						f.set(obj, res);
					else if (simpleEntry.getKey() instanceof Method) {
						((Method) simpleEntry.getKey()).invoke(obj,
								new Object[] { res });
					}
				} else {
					ReadField readField = (ReadField) f
							.getAnnotation(ReadField.class);
					if (readField == null)
						continue;
					StringBuilder sb = new StringBuilder(cls.getName())
							.append(".").append(f.getName()).append("：");
					int index = readField.index();
					ExceptionUtil.lessZeroThrowIndexOutOfBoundsException(index,
							sb.toString() + "索引必须为非负数");
					ExceptionUtil.gtThrowIndexOutOfBoundsException(index,
							lastCellNum,
							(lastCellNum == 0) ? "索引只能为0" : new StringBuilder(
									"索引必须在0-").append(lastCellNum).append("之间")
									.toString());
					Object cell = getCell.invoke(row,
							new Object[] { Integer.valueOf(index) });
					Object cellValue = getObject(ExcelInfo.access$2(cell),
							f.getType());
					if (Modifier.isPublic(f.getModifiers()))
						f.set(obj, cellValue);
					else if (value instanceof Method)
						((Method) value)
								.invoke(obj, new Object[] { cellValue });
				}
			}
			return obj;
		}

		public static <T> ArrayList<T> dispose(Excel<?> excel,
				Map<Field, Object> fileldsMap, ArrayList<T> list) {
			return ReflectUtil.dispose(excel, fileldsMap, list);
		}
	}
}