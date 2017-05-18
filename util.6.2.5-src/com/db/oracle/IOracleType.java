/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.db.oracle;

public abstract interface IOracleType {
	public static final int STYLE_ARRAY_LENGTH = 0;
	public static final int STYLE_DATUM = 1;
	public static final int STYLE_JAVA = 2;
	public static final int STYLE_RAWBYTE = 3;
	public static final int STYLE_INT = 4;
	public static final int STYLE_DOUBLE = 5;
	public static final int STYLE_FLOAT = 6;
	public static final int STYLE_LONG = 7;
	public static final int STYLE_SHORT = 8;
	public static final int STYLE_SKIP = 9;
	public static final boolean TRACE = 0;
	public static final boolean PRIVATE_TRACE = 0;
}