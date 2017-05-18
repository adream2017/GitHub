/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.lang;

public class FormatException extends Exception {
	private static final long serialVersionUID = 1L;

	public FormatException() {
	}

	public FormatException(String message) {
		super(message);
	}

	public FormatException(Throwable cause) {
		super(cause);
	}

	public FormatException(String message, Throwable cause) {
		super(message, cause);
	}
}