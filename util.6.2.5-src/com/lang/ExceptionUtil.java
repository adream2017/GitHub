/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.lang;

import java.lang.reflect.Constructor;

public class ExceptionUtil {
	public static Exception getException(Class<? extends Exception> exception) {
		return getException(null, exception);
	}

	public static Exception getException(String msg,
			Class<? extends Exception> exception) {
		if (exception == null)
			return new Exception(msg);
		Constructor c = null;
		Exception ex = null;
		try {
			c = exception.getConstructor(new Class[] { String.class });
			ex = (Exception) c.newInstance(new Object[] { msg });
		} catch (Exception localException1) {
		}
		if (ex == null)
			try {
				c = exception.getConstructor(new Class[] { String.class,
						Throwable.class });
				ex = (Exception) c.newInstance(new Object[] { msg,
						new Throwable(msg) });
			} catch (Exception localException2) {
			}
		if (ex == null)
			try {
				c = exception.getConstructor(new Class[] { Throwable.class });
				ex = (Exception) c.newInstance(new Object[] { msg,
						new Throwable(msg) });
			} catch (Exception localException3) {
			}
		if (ex == null)
			try {
				ex = (Exception) exception.newInstance();
			} catch (Exception localException4) {
			}
		if (ex == null) {
			return new Exception(msg);
		}
		return ex;
	}

	public static void nullThrowException(Object obj) {
		nullThrowException(obj, null);
	}

	public static void nullThrowException(Object obj, String msg) {
		if (StringUtil.isNullOrWhiteSpace(msg))
			msg = "value不能为空";
		if (obj == null)
			throw new NullPointerException(msg);
	}

	public static void nullOrEmptyThrowException(String value) {
		nullOrEmptyThrowException(value, "");
	}

	public static void nullOrEmptyThrowException(String value, String msg) {
		if (StringUtil.isNullOrWhiteSpace(msg))
			msg = "value不能为空";
		if (value == null)
			throw new NullPointerException(msg);
		if (value.isEmpty())
			throw new IllegalArgumentException(msg);
	}

	public static void nullOrEmptyThrowException(String value,
			Exception exception) throws Exception {
		if (exception == null)
			exception = new Exception("value不能为空");
		if (value == null)
			throw new NullPointerException(exception.getMessage());
		if (value.isEmpty())
			throw exception;
	}

	public static void nullOrEmptyThrowException(String value, String msg,
			Class<? extends Exception> exception) throws Exception {
		if (StringUtil.isNullOrWhiteSpace(msg))
			msg = "value不能为空";
		if (value == null)
			throw new NullPointerException(msg);
		if (value.isEmpty())
			throw getException(msg, exception);
	}

	public static void nullOrWhiteSpaceThrowException(String value) {
		nullOrWhiteSpaceThrowException(value, "");
	}

	public static void nullOrWhiteSpaceThrowException(String value, String msg) {
		if (StringUtil.isNullOrWhiteSpace(msg))
			msg = "value不能为空";
		if (value == null)
			throw new NullPointerException(msg);
		if (value.trim().isEmpty())
			throw new IllegalArgumentException(msg);
	}

	public static void nullOrWhiteSpaceThrowException(String value,
			Exception exception) throws Exception {
		if (exception == null)
			exception = new Exception("value不能为空");
		if (value == null)
			throw new NullPointerException(exception.getMessage());
		if (value.trim().isEmpty())
			throw exception;
	}

	public static void nullOrWhiteSpaceThrowException(String value, String msg,
			Class<? extends Exception> exception) throws Exception {
		if (StringUtil.isNullOrWhiteSpace(msg))
			msg = "value不能为空";
		if (value == null)
			throw new NullPointerException(msg);
		if (value.trim().isEmpty())
			throw getException(msg, exception);
	}

	public static void lessZeroThrowIndexOutOfBoundsException(int index) {
		lessZeroThrowIndexOutOfBoundsException(index, "");
	}

	public static void lessZeroThrowIndexOutOfBoundsException(int index,
			String msg) {
		if (StringUtil.isNullOrWhiteSpace(msg))
			msg = "index must be non negative";
		if (index < 0)
			throw new IndexOutOfBoundsException(msg);
	}

	public static void gtThrowIndexOutOfBoundsException(int index, int max) {
		gtThrowIndexOutOfBoundsException(index, max, "");
	}

	public static void gtThrowIndexOutOfBoundsException(int index, int max,
			String msg) {
		lessZeroThrowIndexOutOfBoundsException(index);
		ltThrowException(Integer.valueOf(max), Integer.valueOf(0),
				"max must be non negative");
		if (StringUtil.isNullOrWhiteSpace(msg))
			msg = "index must be "
					+ ((max == 0) ? "equal to 0" : new StringBuilder(
							"between 0 and ").append(max).toString());
		if (index > max)
			throw new IndexOutOfBoundsException(msg);
	}

	public static void geThrowIndexOutOfBoundsException(int index, int max) {
		String msg = "index must be "
				+ ((max == 0) ? "equal to 0" : new StringBuilder(
						"between 0 and ").append(max).toString());
		gtThrowIndexOutOfBoundsException(index, --max, msg);
	}

	public static void geThrowIndexOutOfBoundsException(int index, int max,
			String msg) {
		if (StringUtil.isNullOrWhiteSpace(msg))
			msg = "index must be "
					+ ((max == 0) ? "equal to 0" : new StringBuilder(
							"between 0 and ").append(max).toString());
		gtThrowIndexOutOfBoundsException(index, --max, msg);
	}

	public static void geThrowException(Number num, Number max) {
		geThrowException(num, max, "");
	}

	public static void geThrowException(Number num, Number max, String msg) {
		if ((num == null) || (max == null))
			return;
		if (StringUtil.isNullOrWhiteSpace(msg))
			msg = "the value of num must be less than the max's value " + max;
		if (num.doubleValue() >= max.doubleValue())
			throw new IllegalArgumentException(msg);
	}

	public static void geThrowException(Number num, Number max,
			Exception exception) throws Exception {
		if ((num == null) || (max == null))
			return;
		if (exception == null)
			exception = new IllegalArgumentException(
					"the value of num must be less than the max's value " + max);
		if (num.doubleValue() >= max.doubleValue())
			throw exception;
	}

	public static void geThrowException(Number num, Number max, String msg,
			Class<? extends Exception> exception) throws Exception {
		if ((num == null) || (max == null))
			return;
		if (StringUtil.isNullOrWhiteSpace(msg))
			msg = "the value of num must be less than the max's value " + max;
		if (num.doubleValue() >= max.doubleValue())
			throw getException(msg, exception);
	}

	public static void leThrowException(Number num, Number min) {
		leThrowException(num, min, "");
	}

	public static void leThrowException(Number num, Number min, String msg) {
		if ((num == null) || (min == null))
			return;
		if (StringUtil.isNullOrWhiteSpace(msg))
			msg = "the value of num must be greater than min's value " + min;
		if (num.doubleValue() <= min.doubleValue())
			throw new IllegalArgumentException(msg);
	}

	public static void leThrowException(Number num, Number min,
			Exception exception) throws Exception {
		if ((num == null) || (min == null))
			return;
		if (exception == null)
			exception = new IllegalArgumentException(
					"num's value equal or less than min's value");
		if (num.doubleValue() <= min.doubleValue())
			throw exception;
	}

	public static void leThrowException(Number num, Number min, String msg,
			Class<? extends Exception> exception) throws Exception {
		if ((num == null) || (min == null))
			return;
		if (StringUtil.isNullOrWhiteSpace(msg))
			msg = "num's value equal or less than min's value";
		if (num.doubleValue() <= min.doubleValue())
			throw getException(msg, exception);
	}

	public static void geOrLeThrowException(Number num, Number min, Number max) {
		geOrLeThrowException(num, min, max, "");
	}

	public static void geOrLeThrowException(Number num, Number min, Number max,
			String msg) {
		if ((num == null) || ((min == null) && (max == null)))
			return;
		gtThrowException(min, max,
				"the min's value must be less than or equal to the max's value");
		if (StringUtil.isNullOrWhiteSpace(msg))
			msg = "the value of num must be greater than the min's value "
					+ min + " and less than the max' value " + max;
		if (((max != null) && (num.doubleValue() >= max.doubleValue()))
				|| ((min != null) && (num.doubleValue() <= min.doubleValue())))
			throw new IllegalArgumentException(msg);
	}

	public static void geOrLeThrowException(Number num, Number min, Number max,
			Exception exception) throws Exception {
		if ((num == null) || ((min == null) && (max == null)))
			return;
		gtThrowException(min, max,
				"the min's value must be less than or equal to the max's value");
		if (exception == null)
			exception = new IllegalArgumentException(
					"the value of num must be greater than the min's value "
							+ min + " and less than the max' value " + max);
		if (((max != null) && (num.doubleValue() >= max.doubleValue()))
				|| ((min != null) && (num.doubleValue() <= min.doubleValue())))
			throw exception;
	}

	public static void geOrLeThrowException(Number num, Number min, Number max,
			String msg, Class<? extends Exception> exception) throws Exception {
		if ((num == null) || ((min == null) && (max == null)))
			return;
		gtThrowException(min, max,
				"the min's value must be less than or equal to the max's value");
		if (StringUtil.isNullOrWhiteSpace(msg))
			msg = "the value of num must be greater than the min's value "
					+ min + " and less than the max' value " + max;
		if ((num.doubleValue() >= max.doubleValue())
				|| (num.doubleValue() <= min.doubleValue()))
			throw getException(msg, exception);
	}

	public static void gtOrLeThrowException(Number num, Number min, Number max) {
		gtOrLeThrowException(num, min, max, "");
	}

	public static void gtOrLeThrowException(Number num, Number min, Number max,
			String msg) {
		if ((num == null) || ((min == null) && (max == null)))
			return;
		gtThrowException(min, max,
				"the min's value must be less than or equal to the max's value");
		if (StringUtil.isNullOrWhiteSpace(msg))
			msg = "the value of num must be greater than the min's value "
					+ min + " and less than or equal to the max' value " + max;
		if ((num.doubleValue() > max.doubleValue())
				|| (num.doubleValue() <= min.doubleValue()))
			throw new IllegalArgumentException(msg);
	}

	public static void gtOrLeThrowException(Number num, Number min, Number max,
			Exception exception) throws Exception {
		if ((num == null) || ((min == null) && (max == null)))
			return;
		gtThrowException(min, max,
				"the min's value must be less than or equal to the max's value");
		if (exception == null)
			exception = new IllegalArgumentException(
					"the value of num must be greater than the min's value "
							+ min
							+ " and less than or equal to the max' value "
							+ max);
		if ((num.doubleValue() > max.doubleValue())
				|| (num.doubleValue() <= min.doubleValue()))
			throw exception;
	}

	public static void gtOrLeThrowException(Number num, Number min, Number max,
			String msg, Class<? extends Exception> exception) throws Exception {
		if ((num == null) || ((min == null) && (max == null)))
			return;
		gtThrowException(min, max,
				"the min's value must be less than or equal to the max's value");
		if (StringUtil.isNullOrWhiteSpace(msg))
			msg = "the value of num must be greater than the min's value "
					+ min + " and less than or equal to the max' value " + max;
		if ((num.doubleValue() > max.doubleValue())
				|| (num.doubleValue() <= min.doubleValue()))
			throw getException(msg, exception);
	}

	public static void gtThrowException(Number num, Number max) {
		gtThrowException(num, max, "");
	}

	public static void gtThrowException(Number num, Number max, String msg) {
		if ((num == null) || (max == null))
			return;
		if (StringUtil.isNullOrWhiteSpace(msg))
			msg = "the value of num must be less than or equal to the max's value "
					+ max;
		if (num.doubleValue() > max.doubleValue())
			throw new IllegalArgumentException(msg);
	}

	public static void gtThrowException(Number num, Number max,
			Exception exception) throws Exception {
		if ((num == null) || (max == null))
			return;
		if (exception == null)
			exception = new IllegalArgumentException(
					"the value of num must be less than or equal to the max's value "
							+ max);
		if (num.doubleValue() > max.doubleValue())
			throw exception;
	}

	public static void gtThrowException(Number num, Number max, String msg,
			Class<? extends Exception> exception) throws Exception {
		if ((num == null) || (max == null))
			return;
		if (StringUtil.isNullOrWhiteSpace(msg))
			msg = "the value of num must be less than or equal to the max's value "
					+ max;
		if (num.doubleValue() > max.doubleValue())
			throw getException(msg, exception);
	}

	public static void geOrLtThrowException(Number num, Number min, Number max) {
		geOrLtThrowException(num, min, max, "");
	}

	public static void geOrLtThrowException(Number num, Number min, Number max,
			String msg) {
		if ((num == null) || ((min == null) && (max == null)))
			return;
		gtThrowException(min, max,
				"the min's value must be less than or equal to the max's value");
		if (StringUtil.isNullOrWhiteSpace(msg))
			msg = "the value of num must be greater than or equal to the min's value "
					+ min + " and less than the max' value " + max;
		if (((max != null) && (num.doubleValue() >= max.doubleValue()))
				|| ((min != null) && (num.doubleValue() < min.doubleValue())))
			throw new IllegalArgumentException(msg);
	}

	public static void geOrLtThrowException(Number num, Number min, Number max,
			Exception exception) throws Exception {
		if ((num == null) || ((min == null) && (max == null)))
			return;
		gtThrowException(min, max,
				"the min's value must be less than or equal to the max's value");
		if (exception == null)
			exception = new IllegalArgumentException(
					"the value of num must be greater than or equal to the min's value "
							+ min + " and less than the max' value " + max);
		if (((max != null) && (num.doubleValue() >= max.doubleValue()))
				|| ((min != null) && (num.doubleValue() < min.doubleValue())))
			throw exception;
	}

	public static void geOrLtThrowException(Number num, Number min, Number max,
			String msg, Class<? extends Exception> exception) throws Exception {
		if ((num == null) || ((min == null) && (max == null)))
			return;
		gtThrowException(min, max,
				"the min's value must be less than or equal to the max's value");
		if (StringUtil.isNullOrWhiteSpace(msg))
			msg = "the value of num must be greater than or equal to the min's value "
					+ min + " and less than the max' value " + max;
		if (((max != null) && (num.doubleValue() >= max.doubleValue()))
				|| ((min != null) && (num.doubleValue() < min.doubleValue())))
			throw getException(msg, exception);
	}

	public static void gtOrLtThrowException(Number num, Number min, Number max) {
		gtOrLtThrowException(num, min, max, "");
	}

	public static void gtOrLtThrowException(Number num, Number min, Number max,
			String msg) {
		if ((num == null) || ((min == null) && (max == null)))
			return;
		gtThrowException(min, max,
				"the min's value must be less than or equal to the max's value");
		if (StringUtil.isNullOrWhiteSpace(msg))
			msg = "the value of num must be greater than or equal to the min's value "
					+ min + " and less than or equal to the max' value " + max;
		if ((num.doubleValue() > max.doubleValue())
				|| (num.doubleValue() < min.doubleValue()))
			throw new IllegalArgumentException(msg);
	}

	public static void gtOrLtThrowException(Number num, Number min, Number max,
			Exception exception) throws Exception {
		if ((num == null) || ((min == null) && (max == null)))
			return;
		gtThrowException(min, max,
				"the min's value must be less than or equal to the max's value");
		if (exception == null)
			exception = new IllegalArgumentException(
					"the value of num must be greater than or equal to the min's value "
							+ min
							+ " and less than or equal to the max' value "
							+ max);
		if ((num.doubleValue() > max.doubleValue())
				|| (num.doubleValue() < min.doubleValue()))
			throw exception;
	}

	public static void gtOrLtThrowException(Number num, Number min, Number max,
			String msg, Class<? extends Exception> exception) throws Exception {
		if ((num == null) || ((min == null) && (max == null)))
			return;
		gtThrowException(min, max,
				"the min's value must be less than or equal to the max's value");
		if (StringUtil.isNullOrWhiteSpace(msg))
			msg = "the value of num must be greater than or equal to the min's value "
					+ min + " and less than or equal to the max' value " + max;
		if ((num.doubleValue() > max.doubleValue())
				|| (num.doubleValue() < min.doubleValue()))
			throw getException(msg, exception);
	}

	public static void ltThrowException(Number num, Number min) {
		ltThrowException(num, min, "");
	}

	public static void ltThrowException(Number num, Number min, String msg) {
		if ((num == null) || (min == null))
			return;
		if (StringUtil.isNullOrWhiteSpace(msg))
			msg = "the value of num must be greater than or equal to min's value "
					+ min;
		if (num.doubleValue() < min.doubleValue())
			throw new IllegalArgumentException(msg);
	}

	public static void ltThrowException(Number num, Number min,
			Exception exception) throws Exception {
		if ((num == null) || (min == null))
			return;
		if (exception == null)
			exception = new IllegalArgumentException(
					"the value of num must be greater than or equal to min's value "
							+ min);
		if (num.doubleValue() < min.doubleValue())
			throw exception;
	}

	public static void ltThrowException(Number num, Number min, String msg,
			Class<? extends Exception> exception) throws Exception {
		if ((num == null) || (min == null))
			return;
		if (StringUtil.isNullOrWhiteSpace(msg))
			msg = "the value of num must be greater than or equal to min's value "
					+ min;
		if (num.doubleValue() < min.doubleValue())
			throw getException(msg, exception);
	}
}