/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.excel.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ java.lang.annotation.ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ReadExcel {
	public abstract int rowIndex();

	public abstract Excel readWay();

	public abstract int sheetIndex();

	public abstract String sheetName();

	public static enum Excel {
		SHEET_INDEX, SHEET_NAME;
	}
}