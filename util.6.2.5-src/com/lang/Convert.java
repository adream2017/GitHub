/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.lang;

import com.lang.enums.OutRange;
import com.reflect.ReflectUtil;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Convert {
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private static final SimpleDateFormat SHORT_DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");

	private static Map<Field, Object> fileldsMap = null;

	private static ReflectConvert<?> convert = null;

	public static Boolean toBoolean(Object obj) {
		if (obj == null)
			return null;
		if (obj instanceof Boolean)
			return ((Boolean) obj);
		if (obj instanceof Number) {
			if (((Number) obj).intValue() == 1)
				return Boolean.valueOf(true);
			return Boolean.valueOf(false);
		}
		String str = obj;
		if (str.isEmpty())
			return null;
		if ("true".equalsIgnoreCase(str))
			return Boolean.valueOf(true);
		if ("false".equalsIgnoreCase(str))
			return Boolean.valueOf(false);
		Double d = toDouble(obj);
		if ((d == null) || (d.doubleValue() == 0.0D))
			return Boolean.valueOf(false);
		return Boolean.valueOf(true);
	}

	public static Boolean[] toBoolean(Object[] obj) {
		if (obj == null)
			return null;
		if (obj instanceof Boolean[])
			return ((Boolean[]) obj);
		Boolean[] arr = new Boolean[obj.length];
		for (int i = 0; i < obj.length; ++i)
			arr[i] = toBoolean(obj[i]);
		return arr;
	}

	public static Boolean[] toBoolean(boolean[] arr) {
		if (arr == null)
			return null;
		Boolean[] bool = new Boolean[arr.length];
		for (int i = 0; i < arr.length; ++i)
			bool[i] = Boolean.valueOf(arr[i]);
		return bool;
	}

	public static boolean toBooleanValue(Object obj) {
		Boolean bool = toBoolean(obj);

		return ((bool != null) && (bool.booleanValue()));
	}

	public static boolean[] toBooleanValue(Object[] obj) {
		Boolean[] bool = toBoolean(obj);
		if (bool == null)
			return null;
		boolean[] arr = new boolean[bool.length];
		for (int i = 0; i < bool.length; ++i)
			arr[i] = (((bool[i] == null) || (!(bool[i].booleanValue()))) ? 0
					: true);
		return arr;
	}

	public static Byte toByte(Object obj) {
		return toByte(obj, null);
	}

	public static Byte toByte(Object obj, OutRange outRange) {
		Long l = toLong(obj, outRange, -128L, 127L);
		return ((l == null) ? null : Byte.valueOf(l.byteValue()));
	}

	public static Byte[] toByte(Object[] obj) {
		return toByte(obj, null);
	}

	public static Byte[] toByte(Object[] obj, OutRange outRange) {
		if (obj == null)
			return null;
		if (obj instanceof Byte[])
			return ((Byte[]) obj);
		Byte[] arr = new Byte[obj.length];
		for (int i = 0; i < obj.length; ++i)
			arr[i] = toByte(obj[i], outRange);
		return arr;
	}

	public static Byte[] toByte(byte[] arr) {
		try {
			return ((Byte[]) toArray(arr, Byte.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static Byte[] toByte(short[] arr) {
		return toByte(arr, null);
	}

	public static Byte[] toByte(short[] arr, OutRange outRange) {
		try {
			return ((Byte[]) toArray(arr, outRange, Byte.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static Byte[] toByte(int[] arr) {
		return toByte(arr, null);
	}

	public static Byte[] toByte(int[] arr, OutRange outRange) {
		try {
			return ((Byte[]) toArray(arr, outRange, Byte.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static Byte[] toByte(long[] arr) {
		return toByte(arr, null);
	}

	public static Byte[] toByte(long[] arr, OutRange outRange) {
		try {
			return ((Byte[]) toArray(arr, outRange, Byte.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static Byte[] toByte(float[] arr) {
		return toByte(arr, null);
	}

	public static Byte[] toByte(float[] arr, OutRange outRange) {
		try {
			return ((Byte[]) toArray(arr, outRange, Byte.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static Byte[] toByte(double[] arr) {
		return toByte(arr, null);
	}

	public static Byte[] toByte(double[] arr, OutRange outRange) {
		try {
			return ((Byte[]) toArray(arr, outRange, Byte.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static byte toByteValue(Object obj) {
		return toByteValue(obj, false);
	}

	public static byte toByteValue(Object obj, boolean outRangeAsZero) {
		Byte b = toByte(obj, (outRangeAsZero) ? OutRange.ASZERO : null);
		return ((b == null) ? 0 : b.byteValue());
	}

	public static byte[] toByteValue(Object[] obj) {
		return toByteValue(obj, false);
	}

	public static byte[] toByteValue(Object[] obj, boolean outRangeAsZero)
  {
    if (obj == null)
      return null;
    byte[] arr = new byte[obj.length];
    if (obj.length == 0)
      return arr;
    if (obj instanceof Number[]) {
      Number[] ns = (Number[])obj;
      int i = 0; break label122:
      while (true) { if (ns[i] != null) {
          Double d = Double.valueOf(ns[i].doubleValue());
          if (d != null) if (!(d.isNaN()))
            {
              if (((!(d.isInfinite())) && (d.doubleValue() >= -128.0D) && 
                (d.doubleValue() <= 127.0D)) || 
                (!(outRangeAsZero)))
              {
                arr[i] = d.byteValue();
              }
            }
        }
        ++i; if (i >= ns.length)
        {
          label122: return arr; } }
    }
    OutRange outRange = (outRangeAsZero) ? OutRange.ASZERO : null;
    for (int i = 0; i < obj.length; ++i) {
      Byte b = toByte(obj[i], outRange);
      if (b != null)
        arr[i] = b.byteValue();
    }
    return arr;
  }

	public static byte[] toByteValue(short[] arr) {
		return toByteValue(arr, false);
	}

	public static byte[] toByteValue(short[] arr, boolean outRangeAsZero) {
		if (arr == null)
			return null;
		byte[] b = new byte[arr.length];
		for (int i = 0; i < arr.length; ++i)
			b[i] = (((outRangeAsZero) && (((arr[i] < -128) || (arr[i] > 127)))) ? 0
					: (byte) arr[i]);
		return b;
	}

	public static byte[] toByteValue(int[] arr) {
		return toByteValue(arr, false);
	}

	public static byte[] toByteValue(int[] arr, boolean outRangeAsZero) {
		if (arr == null)
			return null;
		byte[] b = new byte[arr.length];
		for (int i = 0; i < arr.length; ++i)
			b[i] = (((outRangeAsZero) && (((arr[i] < -128) || (arr[i] > 127)))) ? 0
					: (byte) arr[i]);
		return b;
	}

	public static byte[] toByteValue(long[] arr) {
		return toByteValue(arr, false);
	}

	public static byte[] toByteValue(long[] arr, boolean outRangeAsZero) {
		if (arr == null)
			return null;
		byte[] b = new byte[arr.length];
		for (int i = 0; i < arr.length; ++i)
			b[i] = (((outRangeAsZero) && (((arr[i] < -128L) || (arr[i] > 127L)))) ? 0
					: (byte) (int) arr[i]);
		return b;
	}

	public static byte[] toByteValue(float[] arr) {
		return toByteValue(arr, false);
	}

	public static byte[] toByteValue(float[] arr, boolean outRangeAsZero) {
		if (arr == null)
			return null;
		byte[] b = new byte[arr.length];
		for (int i = 0; i < arr.length; ++i)
			b[i] = (((outRangeAsZero) && (((arr[i] < -128.0F) || (arr[i] > 127.0F)))) ? 0
					: (byte) (int) arr[i]);
		return b;
	}

	public static byte[] toByteValue(double[] arr) {
		return toByteValue(arr, false);
	}

	public static byte[] toByteValue(double[] arr, boolean outRangeAsZero) {
		if (arr == null)
			return null;
		byte[] b = new byte[arr.length];
		for (int i = 0; i < arr.length; ++i)
			b[i] = (((outRangeAsZero) && (((arr[i] < -128.0D) || (arr[i] > 127.0D)))) ? 0
					: (byte) (int) arr[i]);
		return b;
	}

	public static Character toCharacter(Object obj) {
		if (obj == null)
			return null;
		if (obj instanceof Character)
			return ((Character) obj);
		if (obj instanceof Number)
			return Character.valueOf((char) (int) ((Number) obj).doubleValue());
		if (obj instanceof BigDecimal)
			return Character.valueOf((char) (int) ((BigDecimal) obj)
					.doubleValue());
		if (obj instanceof BigInteger)
			return Character.valueOf((char) (int) ((BigInteger) obj)
					.doubleValue());
		try {
			Double d = toDouble(obj);
			if (d == null)
				break label98;
			label98: return Character.valueOf((char) (int) d.doubleValue());
		} catch (Exception str) {
			String str = obj.toString();
			if (str.isEmpty())
				return null;
			if ((obj instanceof String) || (obj instanceof StringBuffer)
					|| (obj instanceof StringBuilder))
				return Character.valueOf(str.charAt(0));
		}
		return null;
	}

	public static Character[] toCharacter(Object[] obj) {
		if (obj == null)
			return null;
		if (obj instanceof Character[])
			return ((Character[]) obj);
		Character[] arr = new Character[obj.length];
		for (int i = 0; i < obj.length; ++i)
			arr[i] = toCharacter(obj[i]);
		return arr;
	}

	public static Character[] toCharacter(char[] arr) {
		if (arr == null)
			return null;
		Character[] ch = new Character[arr.length];
		for (int i = 0; i < arr.length; ++i)
			ch[i] = Character.valueOf(arr[i]);
		return ch;
	}

	public static char toCharValue(Object obj) {
		Character c = toCharacter(obj);
		return ((c == null) ? '\0' : c.charValue());
	}

	public static char[] toCharValue(Object[] obj) {
		Character[] c = toCharacter(obj);
		if (c == null)
			return null;
		char[] arr = new char[c.length];
		for (int i = 0; i < c.length; ++i)
			arr[i] = ((c[i] == null) ? 0 : c[i].charValue());
		return arr;
	}

	public static Short toShort(Object obj) {
		return toShort(obj, null);
	}

	public static Short toShort(Object obj, OutRange outRange) {
		Long l = toLong(obj, outRange, -32768L, 32767L);
		return ((l == null) ? null : Short.valueOf(l.shortValue()));
	}

	public static Short[] toShort(Object[] obj) {
		return toShort(obj, null);
	}

	public static Short[] toShort(Object[] obj, OutRange outRange) {
		if (obj == null)
			return null;
		if (obj instanceof Short[])
			return ((Short[]) obj);
		Short[] arr = new Short[obj.length];
		for (int i = 0; i < obj.length; ++i)
			arr[i] = toShort(obj[i], outRange);
		return arr;
	}

	public static Short[] toShort(byte[] arr) {
		try {
			return ((Short[]) toArray(arr, Short.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static Short[] toShort(short[] arr) {
		try {
			return ((Short[]) toArray(arr, null, Short.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static Short[] toShort(int[] arr) {
		return toShort(arr, null);
	}

	public static Short[] toShort(int[] arr, OutRange outRange) {
		try {
			return ((Short[]) toArray(arr, outRange, Short.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static Short[] toShort(long[] arr) {
		return toShort(arr, null);
	}

	public static Short[] toShort(long[] arr, OutRange outRange) {
		try {
			return ((Short[]) toArray(arr, outRange, Short.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static Short[] toShort(float[] arr) {
		return toShort(arr, null);
	}

	public static Short[] toShort(float[] arr, OutRange outRange) {
		try {
			return ((Short[]) toArray(arr, outRange, Short.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static Short[] toShort(double[] arr) {
		return toShort(arr, null);
	}

	public static Short[] toShort(double[] arr, OutRange outRange) {
		try {
			return ((Short[]) toArray(arr, outRange, Short.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static short toShortValue(Object obj) {
		return toShortValue(obj, false);
	}

	public static short toShortValue(Object obj, boolean outRangeAsZero) {
		Short b = toShort(obj, (outRangeAsZero) ? OutRange.ASZERO : null);
		return ((b == null) ? 0 : b.shortValue());
	}

	public static short[] toShortValue(Object[] obj) {
		return toShortValue(obj, false);
	}

	public static short[] toShortValue(Object[] obj, boolean outRangeAsZero)
  {
    if (obj == null)
      return null;
    short[] arr = new short[obj.length];
    if (obj.length == 0)
      return arr;
    if (obj instanceof Number[]) {
      Number[] ns = (Number[])obj;
      int i = 0; break label122:
      while (true) { if (ns[i] != null) {
          Double d = Double.valueOf(ns[i].doubleValue());
          if (d != null) if (!(d.isNaN()))
            {
              if (((!(d.isInfinite())) && (d.doubleValue() >= -32768.0D) && 
                (d.doubleValue() <= 32767.0D)) || 
                (!(outRangeAsZero)))
              {
                arr[i] = d.shortValue();
              }
            }
        }
        ++i; if (i >= ns.length)
        {
          label122: return arr; } }
    }
    OutRange outRange = (outRangeAsZero) ? OutRange.ASZERO : null;
    for (int i = 0; i < obj.length; ++i) {
      Short b = toShort(obj[i], outRange);
      if (b != null)
        arr[i] = b.shortValue();
    }
    return arr;
  }

	public static short[] toShortValue(byte[] arr) {
		if (arr == null)
			return null;
		short[] b = new short[arr.length];
		for (int i = 0; i < arr.length; ++i)
			b[i] = arr[i];
		return b;
	}

	public static short[] toShortValue(int[] arr) {
		return toShortValue(arr, false);
	}

	public static short[] toShortValue(int[] arr, boolean outRangeAsZero) {
		if (arr == null)
			return null;
		short[] b = new short[arr.length];
		for (int i = 0; i < arr.length; ++i)
			b[i] = (((outRangeAsZero) && (((arr[i] < -32768) || (arr[i] > 32767)))) ? 0
					: (short) arr[i]);
		return b;
	}

	public static short[] toShortValue(long[] arr) {
		return toShortValue(arr, false);
	}

	public static short[] toShortValue(long[] arr, boolean outRangeAsZero) {
		if (arr == null)
			return null;
		short[] b = new short[arr.length];
		for (int i = 0; i < arr.length; ++i)
			b[i] = (((outRangeAsZero) && (((arr[i] < -32768L) || (arr[i] > 32767L)))) ? 0
					: (short) (int) arr[i]);
		return b;
	}

	public static short[] toShortValue(float[] arr) {
		return toShortValue(arr, false);
	}

	public static short[] toShortValue(float[] arr, boolean outRangeAsZero) {
		if (arr == null)
			return null;
		short[] b = new short[arr.length];
		for (int i = 0; i < arr.length; ++i)
			b[i] = (((outRangeAsZero) && (((arr[i] < -32768.0F) || (arr[i] > 32767.0F)))) ? 0
					: (short) (int) arr[i]);
		return b;
	}

	public static short[] toShortValue(double[] arr) {
		return toShortValue(arr, false);
	}

	public static short[] toShortValue(double[] arr, boolean outRangeAsZero) {
		if (arr == null)
			return null;
		short[] b = new short[arr.length];
		for (int i = 0; i < arr.length; ++i)
			b[i] = (((outRangeAsZero) && (((arr[i] < -32768.0D) || (arr[i] > 32767.0D)))) ? 0
					: (short) (int) arr[i]);
		return b;
	}

	public static Integer toInteger(Object obj) {
		return toInteger(obj, null);
	}

	public static Integer toInteger(Object obj, OutRange outRange) {
		Long l = toLong(obj, outRange, -2147483648L, 2147483647L);
		return ((l == null) ? null : Integer.valueOf(l.intValue()));
	}

	public static Integer[] toInteger(Object[] obj) {
		return toInteger(obj, null);
	}

	public static Integer[] toInteger(Object[] obj, OutRange outRange) {
		if (obj == null)
			return null;
		if (obj instanceof Integer[])
			return ((Integer[]) obj);
		Integer[] arr = new Integer[obj.length];
		for (int i = 0; i < obj.length; ++i)
			arr[i] = toInteger(obj[i], outRange);
		return arr;
	}

	public static Integer[] toInteger(byte[] arr) {
		try {
			return ((Integer[]) toArray(arr, Integer.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static Integer[] toInteger(short[] arr) {
		try {
			return ((Integer[]) toArray(arr, null, Integer.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static Integer[] toInteger(int[] arr) {
		try {
			return ((Integer[]) toArray(arr, null, Integer.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static Integer[] toInteger(long[] arr) {
		return toInteger(arr, null);
	}

	public static Integer[] toInteger(long[] arr, OutRange outRange) {
		try {
			return ((Integer[]) toArray(arr, outRange, Integer.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static Integer[] toInteger(float[] arr) {
		return toInteger(arr, null);
	}

	public static Integer[] toInteger(float[] arr, OutRange outRange) {
		try {
			return ((Integer[]) toArray(arr, outRange, Integer.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static Integer[] toInteger(double[] arr) {
		return toInteger(arr, null);
	}

	public static Integer[] toInteger(double[] arr, OutRange outRange) {
		try {
			return ((Integer[]) toArray(arr, outRange, Integer.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static int toIntValue(Object obj) {
		return toIntValue(obj, false);
	}

	public static int toIntValue(Object obj, boolean outRangeAsZero) {
		Integer b = toInteger(obj, (outRangeAsZero) ? OutRange.ASZERO : null);
		return ((b == null) ? 0 : b.intValue());
	}

	public static int[] toIntValue(Object[] obj) {
		return toIntValue(obj, false);
	}

	public static int[] toIntValue(Object[] obj, boolean outRangeAsZero)
  {
    if (obj == null)
      return null;
    int[] arr = new int[obj.length];
    if (obj.length == 0)
      return arr;
    if (obj instanceof Number[]) {
      Number[] ns = (Number[])obj;
      int i = 0; break label122:
      while (true) { if (ns[i] != null) {
          Double d = Double.valueOf(ns[i].doubleValue());
          if (d != null) if (!(d.isNaN()))
            {
              if (((!(d.isInfinite())) && (d.doubleValue() >= -2147483648.0D) && 
                (d.doubleValue() <= 2147483647.0D)) || 
                (!(outRangeAsZero)))
              {
                arr[i] = d.intValue();
              }
            }
        }
        ++i; if (i >= ns.length)
        {
          label122: return arr; } }
    }
    OutRange outRange = (outRangeAsZero) ? OutRange.ASZERO : null;
    for (int i = 0; i < obj.length; ++i) {
      Integer b = toInteger(obj[i], outRange);
      if (b != null)
        arr[i] = b.intValue();
    }
    return arr;
  }

	public static int[] toIntValue(byte[] arr) {
		if (arr == null)
			return null;
		int[] b = new int[arr.length];
		for (int i = 0; i < arr.length; ++i)
			b[i] = arr[i];
		return b;
	}

	public static int[] toIntValue(short[] arr) {
		if (arr == null)
			return null;
		int[] b = new int[arr.length];
		for (int i = 0; i < arr.length; ++i)
			b[i] = arr[i];
		return b;
	}

	public static int[] toIntValue(long[] arr) {
		return toIntValue(arr, false);
	}

	public static int[] toIntValue(long[] arr, boolean outRangeAsZero) {
		if (arr == null)
			return null;
		int[] b = new int[arr.length];
		for (int i = 0; i < arr.length; ++i)
			b[i] = (((outRangeAsZero) && (((arr[i] < -2147483648L) || (arr[i] > 2147483647L)))) ? 0
					: (int) arr[i]);
		return b;
	}

	public static int[] toIntValue(float[] arr) {
		return toIntValue(arr, false);
	}

	public static int[] toIntValue(float[] arr, boolean outRangeAsZero) {
		if (arr == null)
			return null;
		int[] b = new int[arr.length];
		for (int i = 0; i < arr.length; ++i)
			b[i] = (((outRangeAsZero) && (((arr[i] < -2.147484E+009F) || (arr[i] > 2.147484E+009F)))) ? 0
					: (int) arr[i]);
		return b;
	}

	public static int[] toIntValue(double[] arr) {
		return toIntValue(arr, false);
	}

	public static int[] toIntValue(double[] arr, boolean outRangeAsZero) {
		if (arr == null)
			return null;
		int[] b = new int[arr.length];
		for (int i = 0; i < arr.length; ++i)
			b[i] = (((outRangeAsZero) && (((arr[i] < -2147483648.0D) || (arr[i] > 2147483647.0D)))) ? 0
					: (int) arr[i]);
		return b;
	}

	public static Long toLong(Object obj) {
		return toLong(obj, null);
	}

	public static Long toLong(Object obj, OutRange outRange) {
		Long l = toLong(obj, outRange, -9223372036854775808L,
				9223372036854775807L);
		return ((l == null) ? null : Long.valueOf(l.longValue()));
	}

	public static Long[] toLong(Object[] obj) {
		return toLong(obj, null);
	}

	public static Long[] toLong(Object[] obj, OutRange outRange) {
		if (obj == null)
			return null;
		if (obj instanceof Long[])
			return ((Long[]) obj);
		Long[] arr = new Long[obj.length];
		for (int i = 0; i < obj.length; ++i)
			arr[i] = toLong(obj[i], outRange);
		return arr;
	}

	public static Long[] toLong(byte[] arr) {
		try {
			return ((Long[]) toArray(arr, Long.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static Long[] toLong(short[] arr) {
		try {
			return ((Long[]) toArray(arr, null, Long.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static Long[] toLong(int[] arr) {
		try {
			return ((Long[]) toArray(arr, null, Long.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static Long[] toLong(long[] arr) {
		try {
			return ((Long[]) toArray(arr, null, Long.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static Long[] toLong(float[] arr) {
		return toLong(arr, null);
	}

	public static Long[] toLong(float[] arr, OutRange outRange) {
		try {
			return ((Long[]) toArray(arr, outRange, Long.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static Long[] toLong(double[] arr) {
		return toLong(arr, null);
	}

	public static Long[] toLong(double[] arr, OutRange outRange) {
		try {
			return ((Long[]) toArray(arr, outRange, Long.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static long toLongValue(Object obj) {
		return toLongValue(obj, false);
	}

	public static long toLongValue(Object obj, boolean outRangeAsZero) {
		Long b = toLong(obj, (outRangeAsZero) ? OutRange.ASZERO : null);
		return ((b == null) ? 0L : b.longValue());
	}

	public static long[] toLongValue(Object[] obj) {
		return toLongValue(obj, false);
	}

	public static long[] toLongValue(Object[] obj, boolean outRangeAsZero)
  {
    if (obj == null)
      return null;
    long[] arr = new long[obj.length];
    if (obj.length == 0)
      return arr;
    if (obj instanceof Number[]) {
      Number[] ns = (Number[])obj;
      int i = 0; break label122:
      while (true) { if (ns[i] != null) {
          Double d = Double.valueOf(ns[i].doubleValue());
          if (d != null) if (!(d.isNaN()))
            {
              if (((!(d.isInfinite())) && (d.doubleValue() >= -9.223372036854776E+018D) && 
                (d.doubleValue() <= 9.223372036854776E+018D)) || 
                (!(outRangeAsZero)))
              {
                arr[i] = d.longValue();
              }
            }
        }
        ++i; if (i >= ns.length)
        {
          label122: return arr; } }
    }
    OutRange outRange = (outRangeAsZero) ? OutRange.ASZERO : null;
    for (int i = 0; i < obj.length; ++i) {
      Long b = toLong(obj[i], outRange);
      if (b != null)
        arr[i] = b.longValue();
    }
    return arr;
  }

	public static long[] toLongValue(byte[] arr) {
		if (arr == null)
			return null;
		long[] b = new long[arr.length];
		for (int i = 0; i < arr.length; ++i)
			b[i] = arr[i];
		return b;
	}

	public static long[] toLongValue(short[] arr) {
		if (arr == null)
			return null;
		long[] b = new long[arr.length];
		for (int i = 0; i < arr.length; ++i)
			b[i] = arr[i];
		return b;
	}

	public static long[] toLongValue(int[] arr) {
		if (arr == null)
			return null;
		long[] b = new long[arr.length];
		for (int i = 0; i < arr.length; ++i)
			b[i] = arr[i];
		return b;
	}

	public static long[] toLongValue(float[] arr) {
		return toLongValue(arr, false);
	}

	public static long[] toLongValue(float[] arr, boolean outRangeAsZero)
  {
    if (arr == null)
      return null;
    long[] b = new long[arr.length];
    for (int i = 0; i < arr.length; ++i)
      b[i] = (((outRangeAsZero) && ((
        (arr[i] < -9.223372E+018F) || (arr[i] > 9.223372E+018F)))) ? 
        0L : 
        ()arr[i]);
    return b;
  }

	public static long[] toLongValue(double[] arr) {
		return toLongValue(arr, false);
	}

	public static long[] toLongValue(double[] arr, boolean outRangeAsZero)
  {
    if (arr == null)
      return null;
    long[] b = new long[arr.length];
    for (int i = 0; i < arr.length; ++i)
      b[i] = (((outRangeAsZero) && ((
        (arr[i] < -9.223372036854776E+018D) || (arr[i] > 9.223372036854776E+018D)))) ? 
        0L : 
        ()arr[i]);
    return b;
  }

	private static Long toLong(Object obj, OutRange outRange, long MIN_VALUE,
			long MAX_VALUE) {
		ExceptionUtil
				.gtThrowException(Long.valueOf(MIN_VALUE),
						Long.valueOf(MAX_VALUE),
						"the min's value must be less than or equal to the max's value");
		if ((obj == null) || (obj.getClass().isArray()))
			return null;
		if (obj instanceof Byte)
			return Long.valueOf(((Byte) obj).longValue());
		if (obj instanceof Short)
			return Long.valueOf(((Short) obj).longValue());
		if (obj instanceof Integer)
			return Long.valueOf(((Integer) obj).longValue());
		if (obj instanceof Long)
			return ((Long) obj);
		Double d = null;
		if (obj instanceof Float)
			d = Double.valueOf(((Float) obj).doubleValue());
		else if (obj instanceof Double)
			d = (Double) obj;
		else if (obj instanceof Number)
			d = Double.valueOf(((Number) obj).doubleValue());
		else {
			try {
				d = Double.valueOf(obj.toString());
			} catch (Exception e) {
				return null;
			}
		}
		if ((d == null) || (d.isNaN()))
			return null;
		if (outRange == null)
			outRange = OutRange.NORMAL;
		if ((d.isInfinite()) || (d.doubleValue() < MIN_VALUE)
				|| (d.doubleValue() > MAX_VALUE)) {
			switch ($SWITCH_TABLE$com$lang$enums$OutRange()[outRange.ordinal()]) {
			case 1:
				return null;
			case 2:
				return Long.valueOf(0L);
			}
			return Long.valueOf(d.longValue());
		}

		return Long.valueOf(d.longValue());
	}

	public static Float toFloat(Object obj) {
		return toFloat(obj, true);
	}

	public static Float toFloat(Object obj, boolean outRangeAsNull) {
		Double db = toDouble(obj, outRangeAsNull);
		if (db == null)
			return null;
		if ((db.doubleValue() < 1.401298464324817E-045D)
				|| (db.doubleValue() > 3.402823466385289E+038D))
			return ((outRangeAsNull) ? null : Float.valueOf(0.0F));
		return Float.valueOf(db.floatValue());
	}

	public static Float toFloat(Object obj, int scale) {
		return toFloat(obj, scale, com.lang.enums.RoundingMode.ROUND);
	}

	public static Float toFloat(Object obj,
			com.lang.enums.RoundingMode roundingMode) {
		return toFloat(obj, 0, roundingMode);
	}

	public static Float toFloat(Object obj, int scale,
			com.lang.enums.RoundingMode roundingMode) {
		BigDecimal big = toBigDecimal(obj, scale, roundingMode);
		if (big == null)
			return null;
		Float f = Float.valueOf(big.floatValue());
		return Float.valueOf(((f.isNaN()) || (f.isInfinite())) ? 0.0F : f
				.floatValue());
	}

	public static Float[] toFloat(Object[] obj) {
		return toFloat(obj, true);
	}

	public static Float[] toFloat(Object[] obj, boolean outRangeAsNull) {
		if (obj == null)
			return null;
		if (obj instanceof Float[])
			return ((Float[]) obj);
		Float[] arr = new Float[obj.length];
		for (int i = 0; i < obj.length; ++i)
			arr[i] = toFloat(obj[i], outRangeAsNull);
		return arr;
	}

	public static Float[] toFloat(byte[] arr) {
		try {
			return ((Float[]) toArray(arr, Float.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static Float[] toFloat(short[] arr) {
		try {
			return ((Float[]) toArray(arr, null, Float.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static Float[] toFloat(int[] arr) {
		try {
			return ((Float[]) toArray(arr, null, Float.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static Float[] toFloat(long[] arr) {
		try {
			return ((Float[]) toArray(arr, null, Float.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static Float[] toFloat(float[] arr) {
		try {
			return ((Float[]) toArray(arr, null, Float.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static Float[] toFloat(double[] arr) {
		return toFloat(arr, true);
	}

	public static Float[] toFloat(double[] arr, boolean outRangeAsZero) {
		try {
			return ((Float[]) toArray(arr, (outRangeAsZero) ? OutRange.ASZERO
					: OutRange.ASNULL, Float.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static float toFloatValue(Object obj) {
		Float d = toFloat(obj);
		return ((d == null) ? 0.0F : d.floatValue());
	}

	public static float[] toFloatValue(Object[] obj) {
		if (obj == null)
			return null;
		float[] arr = new float[obj.length];
		if (obj.length == 0)
			return arr;
		for (int i = 0; i < obj.length; ++i) {
			Float d = toFloat(obj[i]);
			arr[i] = ((d == null) ? 0.0F : d.floatValue());
		}
		return arr;
	}

	public static float[] toFloatValue(byte[] arr) {
		if (arr == null)
			return null;
		float[] b = new float[arr.length];
		for (int i = 0; i < arr.length; ++i)
			b[i] = arr[i];
		return b;
	}

	public static float[] toFloatValue(short[] arr) {
		if (arr == null)
			return null;
		float[] b = new float[arr.length];
		for (int i = 0; i < arr.length; ++i)
			b[i] = arr[i];
		return b;
	}

	public static float[] toFloatValue(int[] arr) {
		if (arr == null)
			return null;
		float[] b = new float[arr.length];
		for (int i = 0; i < arr.length; ++i)
			b[i] = arr[i];
		return b;
	}

	public static float[] toFloatValue(long[] arr) {
		if (arr == null)
			return null;
		float[] b = new float[arr.length];
		for (int i = 0; i < arr.length; ++i)
			b[i] = (float) arr[i];
		return b;
	}

	public static float[] toFloatValue(float[] arr) {
		if (arr == null)
			return null;
		float[] f = new float[arr.length];
		for (int i = 0; i < arr.length; ++i)
			f[i] = (((Float.isNaN(arr[i])) || (Float.isInfinite(arr[i]))) ? 0.0F
					: arr[i]);
		return f;
	}

	public static float[] toFloatValue(double[] arr) {
		if (arr == null)
			return null;
		float[] f = new float[arr.length];
		for (int i = 0; i < arr.length; ++i)
			f[i] = (((Double.isNaN(arr[i])) || (Double.isInfinite(arr[i]))
					|| (arr[i] < 4.9E-324D) || (arr[i] > 1.7976931348623157E+308D)) ? 0.0F
					: (float) arr[i]);
		return f;
	}

	public static Double toDouble(Object obj) {
		return toDouble(obj, true);
	}

	public static Double toDouble(Object obj, boolean outRangeAsNull) {
		if ((obj == null) || (obj.getClass().isArray()))
			return null;
		if (obj instanceof Byte)
			return Double.valueOf(((Byte) obj).doubleValue());
		if (obj instanceof Short)
			return Double.valueOf(((Short) obj).doubleValue());
		if (obj instanceof Integer)
			return Double.valueOf(((Integer) obj).doubleValue());
		if (obj instanceof Long)
			return Double.valueOf(((Long) obj).doubleValue());
		Double d = null;
		if (obj instanceof Float)
			d = Double.valueOf(((Float) obj).doubleValue());
		else if (obj instanceof Double)
			d = (Double) obj;
		else if (obj instanceof Number)
			d = Double.valueOf(((Number) obj).doubleValue());
		else {
			try {
				d = Double.valueOf(obj.toString());
			} catch (Exception e) {
				return null;
			}
		}
		if ((d == null) || (d.isNaN()))
			return null;
		return Double.valueOf((d.isInfinite()) ? ((outRangeAsNull) ? null
				: Integer.valueOf(0)).intValue() : d.doubleValue());
	}

	public static Double toDouble(Object obj, int scale) {
		return toDouble(obj, scale, com.lang.enums.RoundingMode.ROUND);
	}

	public static Double toDouble(Object obj,
			com.lang.enums.RoundingMode roundingMode) {
		return toDouble(obj, 0, roundingMode);
	}

	public static Double toDouble(Object obj, int scale,
			com.lang.enums.RoundingMode roundingMode) {
		BigDecimal big = toBigDecimal(obj, scale, roundingMode);
		if (big == null)
			return null;
		Double d = Double.valueOf(big.doubleValue());
		return Double.valueOf(((d.isNaN()) || (d.isInfinite())) ? 0.0D : d
				.doubleValue());
	}

	public static Double[] toDouble(Object[] obj) {
		return toDouble(obj, true);
	}

	public static Double[] toDouble(Object[] obj, boolean outRangeAsNull) {
		if (obj == null)
			return null;
		if (obj instanceof Double[])
			return ((Double[]) obj);
		Double[] arr = new Double[obj.length];
		for (int i = 0; i < obj.length; ++i)
			arr[i] = toDouble(obj[i], outRangeAsNull);
		return arr;
	}

	public static Double[] toDouble(byte[] arr) {
		try {
			return ((Double[]) toArray(arr, Double.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static Double[] toDouble(short[] arr) {
		try {
			return ((Double[]) toArray(arr, null, Double.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static Double[] toDouble(int[] arr) {
		try {
			return ((Double[]) toArray(arr, null, Double.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static Double[] toDouble(long[] arr) {
		try {
			return ((Double[]) toArray(arr, null, Double.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static Double[] toDouble(float[] arr) {
		try {
			return ((Double[]) toArray(arr, null, Double.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static Double[] toDouble(double[] arr) {
		try {
			return ((Double[]) toArray(arr, null, Double.class));
		} catch (Exception e) {
		}
		return null;
	}

	public static double toDoubleValue(Object obj) {
		Double d = toDouble(obj);
		return ((d == null) ? 0.0D : d.doubleValue());
	}

	public static double[] toDoubleValue(Object[] obj) {
		if (obj == null)
			return null;
		double[] arr = new double[obj.length];
		if (obj.length == 0)
			return arr;
		for (int i = 0; i < obj.length; ++i) {
			Double d = toDouble(obj[i]);
			arr[i] = ((d == null) ? 0.0D : d.doubleValue());
		}
		return arr;
	}

	public static double[] toDoubleValue(byte[] arr) {
		if (arr == null)
			return null;
		double[] b = new double[arr.length];
		for (int i = 0; i < arr.length; ++i)
			b[i] = arr[i];
		return b;
	}

	public static double[] toDoubleValue(short[] arr) {
		if (arr == null)
			return null;
		double[] b = new double[arr.length];
		for (int i = 0; i < arr.length; ++i)
			b[i] = arr[i];
		return b;
	}

	public static double[] toDoubleValue(int[] arr) {
		if (arr == null)
			return null;
		double[] b = new double[arr.length];
		for (int i = 0; i < arr.length; ++i)
			b[i] = arr[i];
		return b;
	}

	public static double[] toDoubleValue(long[] arr) {
		if (arr == null)
			return null;
		double[] b = new double[arr.length];
		for (int i = 0; i < arr.length; ++i)
			b[i] = arr[i];
		return b;
	}

	public static double[] toDoubleValue(float[] arr) {
		if (arr == null)
			return null;
		double[] f = new double[arr.length];
		for (int i = 0; i < arr.length; ++i)
			f[i] = (((Float.isNaN(arr[i])) || (Float.isInfinite(arr[i]))) ? 0.0F
					: arr[i]);
		return f;
	}

	public static double[] toDoubleValue(double[] arr) {
		if (arr == null)
			return null;
		double[] f = new double[arr.length];
		for (int i = 0; i < arr.length; ++i)
			f[i] = (((Double.isNaN(arr[i])) || (Double.isInfinite(arr[i]))) ? 0.0D
					: arr[i]);
		return f;
	}

	public static BigDecimal toBigDecimal(Object obj) {
		if ((obj == null) || (obj.getClass().isArray()))
			return null;
		if (obj instanceof BigDecimal)
			return ((BigDecimal) obj);
		if (obj instanceof Boolean)
			return new BigDecimal((((Boolean) obj).booleanValue()) ? 1 : 0);
		String str = obj.toString();
		if (str.isEmpty())
			return null;
		try {
			return new BigDecimal(str);
		} catch (Exception e) {
		}
		return null;
	}

	public static BigDecimal toBigDecimal(Object obj, int scale) {
		return toBigDecimal(obj, scale, com.lang.enums.RoundingMode.ROUND);
	}

	public static BigDecimal toBigDecimal(Object obj,
			com.lang.enums.RoundingMode roundingMode) {
		return toBigDecimal(obj, 0, roundingMode);
	}

	public static BigDecimal toBigDecimal(Object obj, int scale,
			com.lang.enums.RoundingMode roundingMode) {
		if (roundingMode == null)
			throw new NullPointerException("the roundingMode must not be null");
		if (scale == -1) {
			if (roundingMode.ordinal() < 4)
				throw new IllegalArgumentException(
						"when the scale equal -1，the roundingMode must be ABS or OPPOSITE or UNNECESSARY");
		} else {
			if (roundingMode.ordinal() >= 4)
				throw new IllegalArgumentException(
						"when roundingMode is ABS or OPPOSITE or UNNECESSARY，the scale must equal -1");
			if ((scale < 0) || (scale > 99))
				throw new IllegalArgumentException(
						"the scale must be between 0 and 99");
		}
		BigDecimal bd = toBigDecimal(obj);
		if (bd == null)
			return null;
		java.math.RoundingMode mode = null;
		if (roundingMode == com.lang.enums.RoundingMode.ABS)
			bd = bd.abs();
		else if (roundingMode == com.lang.enums.RoundingMode.OPPOSITE)
			bd = bd.negate();
		switch ($SWITCH_TABLE$com$lang$enums$RoundingMode()[roundingMode
				.ordinal()]) {
		case 1:
			mode = java.math.RoundingMode.CEILING;
			break;
		case 2:
			mode = java.math.RoundingMode.FLOOR;
			break;
		case 4:
			mode = java.math.RoundingMode.DOWN;
			break;
		case 3:
			mode = java.math.RoundingMode.HALF_UP;
			break;
		default:
			return bd;
		}
		return bd.setScale(scale, mode);
	}

	public static Number toNumber(Object obj) {
		return ((obj instanceof Number) ? (Number) obj : toDouble(obj));
	}

	public static Number[] toNumber(Object[] obj) {
		return ((obj instanceof Number[]) ? (Number[]) obj : toDouble(obj));
	}

	public static Number[] toNumber(byte[] arr) {
		if (arr == null)
			return null;
		Number[] num = new Number[arr.length];
		for (int i = 0; i < arr.length; num[i] = Byte.valueOf(arr[(i++)]))
			;
		return num;
	}

	public static Number[] toNumber(short[] arr) {
		if (arr == null)
			return null;
		Number[] num = new Number[arr.length];
		for (int i = 0; i < arr.length; num[i] = Short.valueOf(arr[(i++)]))
			;
		return num;
	}

	public static Number[] toNumber(int[] arr) {
		if (arr == null)
			return null;
		Number[] num = new Number[arr.length];
		for (int i = 0; i < arr.length; num[i] = Integer.valueOf(arr[(i++)]))
			;
		return num;
	}

	public static Number[] toNumber(long[] arr) {
		if (arr == null)
			return null;
		Number[] num = new Number[arr.length];
		for (int i = 0; i < arr.length; num[i] = Long.valueOf(arr[(i++)]))
			;
		return num;
	}

	public static Number[] toNumber(float[] arr) {
		if (arr == null)
			return null;
		Number[] num = new Number[arr.length];
		for (int i = 0; i < arr.length; num[i] = Float.valueOf(arr[(i++)]))
			;
		return num;
	}

	public static Number[] toNumber(double[] arr) {
		if (arr == null)
			return null;
		Number[] num = new Number[arr.length];
		for (int i = 0; i < arr.length; num[i] = Double.valueOf(arr[(i++)]))
			;
		return num;
	}

	public static BigDecimal[] toBigDecimal(Object[] obj) {
		if (obj == null)
			return null;
		if (obj instanceof BigDecimal[])
			return ((BigDecimal[]) obj);
		BigDecimal[] arr = new BigDecimal[obj.length];
		for (int i = 0; i < obj.length; ++i)
			arr[i] = toBigDecimal(obj[i]);
		return arr;
	}

	public static BigInteger toBigInteger(Object obj) {
		if (obj == null)
			return null;
		if (obj instanceof BigInteger)
			return ((BigInteger) obj);
		if (obj instanceof BigDecimal)
			return ((BigDecimal) obj).toBigInteger();
		BigDecimal big = toBigDecimal(obj);
		return ((big == null) ? null : big.toBigInteger());
	}

	public static BigInteger toBigInteger(Object obj, int scale) {
		return toBigInteger(obj, scale, com.lang.enums.RoundingMode.ROUND);
	}

	public static BigInteger toBigInteger(Object obj,
			com.lang.enums.RoundingMode roundingMode) {
		return toBigInteger(obj, 0, roundingMode);
	}

	public static BigInteger toBigInteger(Object obj, int scale,
			com.lang.enums.RoundingMode roundingMode) {
		BigDecimal big = toBigDecimal(obj, scale, roundingMode);
		return ((big == null) ? null : big.toBigInteger());
	}

	public static BigInteger[] toBigInteger(Object[] obj) {
		if (obj == null)
			return null;
		if (obj instanceof BigInteger[])
			return ((BigInteger[]) obj);
		BigInteger[] arr = new BigInteger[obj.length];
		for (int i = 0; i < obj.length; ++i)
			arr[i] = toBigInteger(obj[i]);
		return arr;
	}

	public static java.util.Date toDate(String value) {
		try {
			if (StringUtil.isNullOrWhiteSpace(value))
				return null;
			value = value.trim();
			ArrayList list1 = StringUtil.allMatchesToArrayList("[0-9]+", value);
			if (list1.isEmpty()) {
				return null;
			}
			ArrayList list2 = toArrayList(value.split("[0-9]+"));
			if (list1.size() == 1)
				list2.add("");
			String[] strArr = { "yyyy", "MM", "dd", "HH", "mm", "ss", "SSS" };
			StringBuilder date = new StringBuilder();
			StringBuilder simpleFormat = new StringBuilder();
			for (int i = 0; i < strArr.length; ++i) {
				if (i >= list1.size())
					break;
				if (i >= list2.size())
					break;
				StringBuilder sb = new StringBuilder((String) list2.get(i));
				if ((sb.length() <= ((i == 0) ? 0 : 1))
						|| (sb.charAt(sb.length() - 1) != '-'))
					sb = new StringBuilder((String) list1.get(i));
				else
					sb = new StringBuilder("-" + ((String) list1.get(i)));
				date.append(((String) list2.get(i)) + sb);
				simpleFormat.append(((String) list2.get(i)) + strArr[i]);
			}
			return new SimpleDateFormat(simpleFormat.toString()).parse(date
					.toString());
		} catch (ParseException localParseException) {
		}
		return null;
	}

	public static java.util.Date toDate(String value, String format) {
		try {
			if ((StringUtil.isNullOrWhiteSpace(value))
					|| (StringUtil.isNullOrWhiteSpace(format)))
				return null;
			value = value.trim();
			format = format.trim();
			if ("yyyy-MM-dd HH:mm:ss".equals(format))
				return DATE_FORMAT.parse(value);
			if ("yyyy-MM-dd".equals(format))
				return SHORT_DATE_FORMAT.parse(value);
			return new SimpleDateFormat(format).parse(value);
		} catch (Exception localParseException) {
			try {
				if ((StringUtil.isNullOrWhiteSpace(value)) || (format == null))
					return null;
				value = value.trim();
				format = format.trim();
				ArrayList list1 = StringUtil.allMatchesToArrayList("[0-9]+",
						value);
				if (list1.isEmpty()) {
					return null;
				}
				ArrayList list2 = toArrayList(value.split("[0-9]+"));
				ArrayList list3 = StringUtil.allMatchesToArrayList("[A-Za-z]+",
						format);

				ArrayList list4 = toArrayList(format.split("[A-Za-z]+"));
				if (list1.size() == 1)
					list2.add("");
				String[] strArr = { "yyyy", "MM", "dd", "HH", "mm", "ss", "SSS" };
				StringBuilder date = new StringBuilder();
				StringBuilder simpleFormat = new StringBuilder();
				if (list2.size() > list3.size()) {
					for (int i = 0; i < strArr.length; ++i) {
						boolean isExists = false;
						for (int j = 0; j < list3.size(); ++j) {
							if (i >= list3.size())
								break;
							String s = (String) list3.get(i);
							if (StringUtil.isMatch("y", s, true))
								isExists = true;
							else if (s.indexOf("M") >= 0)
								isExists = true;
							else if (StringUtil.isMatch("d", s, true))
								isExists = true;
							else if (StringUtil.isMatch("h", s, true))
								isExists = true;
							else if (s.indexOf("m") >= 0)
								isExists = true;
							else if (s.indexOf("s") >= 0)
								isExists = true;
							else if (s.indexOf("S") >= 0)
								isExists = true;
						}
						if (!(isExists))
							list3.add(strArr[i]);
					}
				}
				for (int i = 0; i < list4.size(); ++i) {
					if (i >= list1.size())
						break;
					if (i >= list2.size())
						break;
					StringBuilder sb = new StringBuilder((String) list2.get(i));
					if ((sb.length() <= ((i == 0) ? 0 : 1))
							|| (sb.charAt(sb.length() - 1) != '-')
							|| (sb.toString().equals(list4.get(i))))
						sb = new StringBuilder((String) list1.get(i));
					else
						sb = new StringBuilder("-" + ((String) list1.get(i)));
					date.append(((String) list2.get(i)) + sb);
					simpleFormat.append(((String) list2.get(i)) + strArr[i]);
				}
				return new SimpleDateFormat(simpleFormat.toString()).parse(date
						.toString());
			} catch (ParseException localParseException) {
			}
		}
		return null;
	}

	public static String toDateString(java.util.Date value) {
		if (value == null)
			return null;
		try {
			return DATE_FORMAT.format(value);
		} catch (Exception e) {
		}
		return null;
	}

	public static String toDateString(java.util.Date value, String format) {
		if ((value == null) || (StringUtil.isNullOrWhiteSpace(format)))
			return null;
		try {
			return new SimpleDateFormat(format).format(value);
		} catch (Exception e) {
		}
		return null;
	}

	public static java.util.Date toShortDate(String value) {
		return toSqlDate(value);
	}

	public static String toShortDateString(java.util.Date value) {
		if (value == null)
			return null;
		try {
			return SHORT_DATE_FORMAT.format(value);
		} catch (Exception e) {
		}
		return null;
	}

	public static java.sql.Date toSqlDate(String value) {
		java.util.Date date = toDate(value, "yyyy-MM-dd");
		return new java.sql.Date(date.getTime());
	}

	public static String toSqlDateString(java.util.Date value) {
		return toShortDateString(value);
	}

	public static String toString(Object obj) {
		if (obj == null)
			return null;
		if (obj instanceof Number)
			return toPlainString(obj);
		if (obj instanceof java.sql.Date)
			return toSqlDateString((java.sql.Date) obj);
		if (obj instanceof java.util.Date)
			return toDateString((java.util.Date) obj);
		return obj.toString();
	}

	public static StringBuffer toStringBuffer(Object obj) {
		String str = toString(obj);
		return new StringBuffer(str);
	}

	public static StringBuilder toStringBuilder(Object obj) {
		String str = toString(obj);
		return new StringBuilder(str);
	}

	public static String toPlainString(Object obj) {
		return toPlainString(obj, -1, com.lang.enums.RoundingMode.UNNECESSARY);
	}

	public static String toPlainString(Object obj, int scale) {
		return toPlainString(obj, scale, com.lang.enums.RoundingMode.ROUND);
	}

	public static String toPlainString(Object obj,
			com.lang.enums.RoundingMode roundingMode) {
		return toPlainString(obj, 0, roundingMode);
	}

	public static String toPlainString(Object obj, int scale,
			com.lang.enums.RoundingMode roundingMode) {
		BigDecimal big = toBigDecimal(obj, scale, roundingMode);
		return ((big == null) ? "NaN" : big.toPlainString());
	}

	public static String toScienceString(Object obj) {
		return toScienceString(obj, -1, com.lang.enums.RoundingMode.UNNECESSARY);
	}

	public static String toScienceString(Object obj, int num) {
		return toScienceString(obj, num, com.lang.enums.RoundingMode.ROUND);
	}

	public static String toScienceString(Object obj,
			com.lang.enums.RoundingMode roundingMode) {
		return toScienceString(obj, 0, roundingMode);
	}

	public static String toScienceString(Object obj, int num,
			com.lang.enums.RoundingMode roundingMode) {
		if (roundingMode == null)
			throw new NullPointerException("roundingMode should not be null");
		if (num == -1) {
			if (roundingMode.ordinal() < 4)
				throw new IllegalArgumentException(
						"when the valid number equal -1，the roundingMode must be ABS or OPPOSITE or UNNECESSARY");
		} else {
			if (roundingMode.ordinal() >= 4)
				throw new IllegalArgumentException(
						"when roundingMode is ABS or OPPOSITE or UNNECESSARY，the valid number must equal -1");
			if ((num < 0) || (num > 9))
				throw new IllegalArgumentException(
						"the valid number must be between 0 and 9");
		}
		Double d = toDouble(obj);
		if (d == null)
			return "NaN";
		if (roundingMode == com.lang.enums.RoundingMode.ABS)
			d = Double.valueOf(Math.abs(d.doubleValue()));
		java.math.RoundingMode mode = null;
		BigDecimal bd = null;
		switch ($SWITCH_TABLE$com$lang$enums$RoundingMode()[roundingMode
				.ordinal()]) {
		case 5:
			d = Double.valueOf(Math.abs(d.doubleValue()));
		case 6:
			d = Double.valueOf(-d.doubleValue());
		case 7:
			bd = new BigDecimal(d.toString());
			break;
		case 1:
			mode = java.math.RoundingMode.CEILING;
		case 2:
			mode = java.math.RoundingMode.FLOOR;
		case 4:
			mode = java.math.RoundingMode.DOWN;
		case 3:
			mode = java.math.RoundingMode.HALF_UP;
		default:
			bd = new BigDecimal(d.toString()).setScale(num, mode);
		}
		if (((d.doubleValue() == d.intValue()) && (d.doubleValue() > -10.0D) && (d
				.doubleValue() < 10.0D))
				|| ((d.doubleValue() > -10.0D) && (d.doubleValue() < -1.0D))
				|| ((d.doubleValue() > 1.0D) && (d.doubleValue() < 10.0D)))
			return bd.toPlainString();
		StringBuilder sb = new StringBuilder(d.toString().replaceFirst(
				"\\.0+$", ""));
		int e = sb.lastIndexOf("E");
		if (e >= 0) {
			d = Double.valueOf(toDoubleValue(sb.substring(0, e)));
			switch ($SWITCH_TABLE$com$lang$enums$RoundingMode()[roundingMode
					.ordinal()]) {
			case 5:
			case 6:
			case 7:
				bd = new BigDecimal(d.toString());
				break;
			default:
				bd = new BigDecimal(d.toString()).setScale(num, mode);
			}
			return sb.replace(0, e, bd.toPlainString()).toString();
		}
		String symbol = (d.doubleValue() < 0.0D) ? "-" : "";
		d = Double.valueOf(Math.abs(d.doubleValue()));
		sb = new StringBuilder(symbol);
		if (d.doubleValue() >= 10.0D) {
			int count = 0;
			for (; d.doubleValue() >= 10.0D; ++count)
				d = Double.valueOf(d.doubleValue() / 10.0D);
			switch ($SWITCH_TABLE$com$lang$enums$RoundingMode()[roundingMode
					.ordinal()]) {
			case 5:
			case 6:
			case 7:
				bd = new BigDecimal(d.toString());
				break;
			default:
				bd = new BigDecimal(d.toString()).setScale(num, mode);
			}
			return bd.toPlainString() + "E" + count;
		}

		int count = 0;
		for (; d.doubleValue() < 1.0D; ++count)
			d = Double.valueOf(d.doubleValue() * 10.0D);
		switch ($SWITCH_TABLE$com$lang$enums$RoundingMode()[roundingMode
				.ordinal()]) {
		case 5:
		case 6:
		case 7:
			bd = new BigDecimal(d.toString());
			break;
		default:
			bd = new BigDecimal(d.toString()).setScale(num, mode);
		}

		return bd.toPlainString() + "E-" + count;
	}

	public static <T> T[] toArray(Collection<T> c, Class<? super T> cls) {
		if ((c == null) || (cls == null)) {
			return null;
		}
		Object[] ts = (Object[]) Array.newInstance(cls, 0);
		return c.toArray(ts);
	}

	public static <T> T[] toArray(byte[] arr, Class<? extends Number> cls)
			throws Exception {
		if (arr == null)
			return null;
		String methodName = null;
		if (cls == Byte.class)
			methodName = "byteValue";
		else if (cls == Short.class)
			methodName = "shortValue";
		else if (cls == Integer.class)
			methodName = "intValue";
		else if (cls == Long.class)
			methodName = "longValue";
		else if (cls == Float.class)
			methodName = "floatValue";
		else if (cls == Double.class)
			methodName = "doubleValue";
		else
			return null;
		Object[] ts = (Object[]) Array.newInstance(cls, arr.length);
		if (arr.length == 0)
			return ts;
		Method m = Byte.class.getMethod(methodName, new Class[0]);
		for (int i = 0; i < arr.length; ++i)
			ts[i] = m.invoke(Byte.valueOf(arr[i]), new Object[0]);
		return ts;
	}

	public static <T> T[] toArray(short[] arr, Class<? extends Number> cls)
			throws Exception {
		return toArray(arr, null, cls);
	}

	public static <T> T[] toArray(short[] arr, OutRange outRange,
			Class<? extends Number> cls) throws Exception {
		if (arr == null)
			return null;
		double min = 0.0D;
		double max = 0.0D;
		String methodName = null;
		boolean isOutRange = false;
		if (cls == Byte.class) {
			min = -128.0D;
			max = 127.0D;
			methodName = "byteValue";
			isOutRange = true;
		} else if (cls == Short.class) {
			methodName = "shortValue";
		} else if (cls == Integer.class) {
			methodName = "intValue";
		} else if (cls == Long.class) {
			methodName = "longValue";
		} else if (cls == Float.class) {
			methodName = "floatValue";
		} else if (cls == Double.class) {
			methodName = "doubleValue";
		} else {
			return null;
		}
		Object[] ts = (Object[]) Array.newInstance(cls, arr.length);
		if (arr.length == 0)
			return ts;
		Method m = Short.class.getMethod(methodName, new Class[0]);
		for (int i = 0; i < arr.length; ++i) {
			if ((!(isOutRange)) || (outRange == null)
					|| (outRange == OutRange.NORMAL)
					|| ((arr[i] >= min) && (arr[i] <= max)))
				ts[i] = m.invoke(Short.valueOf(arr[i]), new Object[0]);
			else if (outRange == OutRange.ASZERO)
				ts[i] = m.invoke(Integer.valueOf(0), new Object[0]);
		}
		return ts;
	}

	public static <T> T[] toArray(int[] arr, Class<? extends Number> cls)
			throws Exception {
		return toArray(arr, null, cls);
	}

	public static <T> T[] toArray(int[] arr, OutRange outRange,
			Class<? extends Number> cls) throws Exception {
		if (arr == null)
			return null;
		int min = 0;
		int max = 0;
		String methodName = null;
		boolean isOutRange = false;
		if (cls == Byte.class) {
			min = -128;
			max = 127;
			methodName = "byteValue";
			isOutRange = true;
		} else if (cls == Short.class) {
			min = -32768;
			max = 32767;
			methodName = "shortValue";
			isOutRange = true;
		} else if (cls == Integer.class) {
			methodName = "intValue";
		} else if (cls == Long.class) {
			methodName = "longValue";
		} else if (cls == Float.class) {
			methodName = "floatValue";
		} else if (cls == Double.class) {
			methodName = "doubleValue";
		} else {
			return null;
		}
		Object[] ts = (Object[]) Array.newInstance(cls, arr.length);
		if (arr.length == 0)
			return ts;
		Method m = Integer.class.getMethod(methodName, new Class[0]);
		for (int i = 0; i < arr.length; ++i) {
			if ((!(isOutRange)) || (outRange == null)
					|| (outRange == OutRange.NORMAL)
					|| ((arr[i] >= min) && (arr[i] <= max)))
				ts[i] = m.invoke(Integer.valueOf(arr[i]), new Object[0]);
			else if (outRange == OutRange.ASZERO)
				ts[i] = m.invoke(Integer.valueOf(0), new Object[0]);
		}
		return ts;
	}

	public static <T> T[] toArray(long[] arr, Class<? extends Number> cls)
			throws Exception {
		return toArray(arr, null, cls);
	}

	public static <T> T[] toArray(long[] arr, OutRange outRange,
			Class<? extends Number> cls) throws Exception {
		if (arr == null)
			return null;
		long min = 0L;
		long max = 0L;
		String methodName = null;
		boolean isOutRange = false;
		if (cls == Byte.class) {
			min = -128L;
			max = 127L;
			methodName = "byteValue";
			isOutRange = true;
		} else if (cls == Short.class) {
			min = -32768L;
			max = 32767L;
			methodName = "shortValue";
			isOutRange = true;
		} else if (cls == Integer.class) {
			min = -2147483648L;
			max = 2147483647L;
			methodName = "intValue";
			isOutRange = true;
		} else if (cls == Long.class) {
			methodName = "longValue";
		} else if (cls == Float.class) {
			methodName = "floatValue";
		} else if (cls == Double.class) {
			methodName = "doubleValue";
		} else {
			return null;
		}
		Object[] ts = (Object[]) Array.newInstance(cls, arr.length);
		if (arr.length == 0)
			return ts;
		Method m = Long.class.getMethod(methodName, new Class[0]);
		for (int i = 0; i < arr.length; ++i) {
			if ((!(isOutRange)) || (outRange == null)
					|| (outRange == OutRange.NORMAL)
					|| ((arr[i] >= min) && (arr[i] <= max)))
				ts[i] = m.invoke(Long.valueOf(arr[i]), new Object[0]);
			else if (outRange == OutRange.ASZERO)
				ts[i] = m.invoke(Integer.valueOf(0), new Object[0]);
		}
		return ts;
	}

	public static <T> T[] toArray(float[] arr, Class<? extends Number> cls)
			throws Exception {
		return toArray(arr, null, cls);
	}

	public static <T> T[] toArray(float[] arr, OutRange outRange,
			Class<? extends Number> cls) throws Exception {
		if (arr == null)
			return null;
		float min = 0.0F;
		float max = 0.0F;
		String methodName = null;
		boolean isOutRange = false;
		if (cls == Byte.class) {
			min = -128.0F;
			max = 127.0F;
			methodName = "byteValue";
			isOutRange = true;
		} else if (cls == Short.class) {
			min = -32768.0F;
			max = 32767.0F;
			methodName = "shortValue";
			isOutRange = true;
		} else if (cls == Integer.class) {
			min = -2.147484E+009F;
			max = 2.147484E+009F;
			methodName = "intValue";
			isOutRange = true;
		} else if (cls == Long.class) {
			min = -9.223372E+018F;
			max = 9.223372E+018F;
			methodName = "longValue";
			isOutRange = true;
		} else if (cls == Float.class) {
			methodName = "floatValue";
		} else if (cls == Double.class) {
			methodName = "doubleValue";
		} else {
			return null;
		}
		Object[] ts = (Object[]) Array.newInstance(cls, arr.length);
		if (arr.length == 0)
			return ts;
		Method m = Float.class.getMethod(methodName, new Class[0]);
		for (int i = 0; i < arr.length; ++i) {
			if (Float.isNaN(arr[i]))
				continue;
			if (Float.isInfinite(arr[i]))
				continue;
			if ((!(isOutRange)) || (outRange == null)
					|| (outRange == OutRange.NORMAL)
					|| ((arr[i] >= min) && (arr[i] <= max)))
				ts[i] = m.invoke(Float.valueOf(arr[i]), new Object[0]);
			else if (outRange == OutRange.ASZERO)
				ts[i] = m.invoke(Integer.valueOf(0), new Object[0]);
		}
		return ts;
	}

	public static <T> T[] toArray(double[] arr, Class<? extends Number> cls)
			throws Exception {
		return toArray(arr, null, cls);
	}

	public static <T> T[] toArray(double[] arr, OutRange outRange,
			Class<? extends Number> cls) throws Exception {
		if (arr == null)
			return null;
		double min = 0.0D;
		double max = 0.0D;
		String methodName = null;
		boolean isOutRange = false;
		if (cls == Byte.class) {
			min = -128.0D;
			max = 127.0D;
			methodName = "byteValue";
			isOutRange = true;
		} else if (cls == Short.class) {
			min = -32768.0D;
			max = 32767.0D;
			methodName = "shortValue";
			isOutRange = true;
		} else if (cls == Integer.class) {
			min = -2147483648.0D;
			max = 2147483647.0D;
			methodName = "intValue";
			isOutRange = true;
		} else if (cls == Long.class) {
			min = -9.223372036854776E+018D;
			max = 9.223372036854776E+018D;
			methodName = "longValue";
			isOutRange = true;
		} else if (cls == Float.class) {
			min = 1.401298464324817E-045D;
			max = 3.402823466385289E+038D;
			methodName = "floatValue";
			isOutRange = true;
		} else if (cls == Double.class) {
			methodName = "doubleValue";
		} else {
			return null;
		}
		Object[] ts = (Object[]) Array.newInstance(cls, arr.length);
		if (arr.length == 0)
			return ts;
		Method m = Double.class.getMethod(methodName, new Class[0]);
		for (int i = 0; i < arr.length; ++i) {
			if (Double.isNaN(arr[i]))
				continue;
			if (Double.isInfinite(arr[i]))
				continue;
			if ((!(isOutRange)) || (outRange == null)
					|| (outRange == OutRange.NORMAL)
					|| ((arr[i] >= min) && (arr[i] <= max)))
				ts[i] = m.invoke(Double.valueOf(arr[i]), new Object[0]);
			else if (outRange == OutRange.ASZERO)
				ts[i] = m.invoke(Integer.valueOf(0), new Object[0]);
		}
		return ts;
	}

	public static <K, V> V[] toArray(Map<K, V> map, Class<? super V> cls) {
		if (map == null)
			return null;
		if (map.isEmpty())
			return ((Object[]) Array.newInstance(cls, 0));
		Object[] vs = (Object[]) Array.newInstance(cls, map.size());
		int c = 0;
		for (Iterator i = map.entrySet().iterator(); i.hasNext();)
			vs[(c++)] = ((Map.Entry) i.next()).getValue();

		return vs;
	}

	public static <T> List<? super T> toList(T[] ts) {
		return ((ts == null) ? null : Arrays.asList(ts));
	}

	public static <T> ArrayList<? super T> toArrayList(T[] ts) {
		if (ts == null)
			return null;
		ArrayList list = new ArrayList();
		for (int i = 0; i < ts.length; list.add(ts[(i++)]))
			;
		return list;
	}

	public static <T> ArrayList<? super T> toArrayList(Collection<T> c) {
		if (c == null)
			return null;
		ArrayList arrayList = new ArrayList();
		if (c instanceof ArrayList)
			return ((ArrayList) c);
		if (c.isEmpty())
			return arrayList;
		if (c instanceof List) {
			List list = (List) c;
			for (int i = 0; i < list.size(); list.add(list.get(i++)))
				;
		} else {
			for (Iterator i = c.iterator(); i.hasNext(); arrayList
					.add(i.next()))
				;
		}
		return arrayList;
	}

	public static <K, V> ArrayList<? super V> toArrayList(Map<K, V> map) {
		if (map == null)
			return null;
		ArrayList arrayList = new ArrayList();
		if (map.isEmpty())
			return arrayList;
		for (Iterator i = map.entrySet().iterator(); i.hasNext();)
			arrayList.add(((Map.Entry) i.next()).getValue());

		return arrayList;
	}

	public static <T> ArrayList<T> toArrayList(Class<? super T> cls,
			Object[][] arr) throws Exception {
		convert = new ReflectConvert(cls);
		com.lang.annotation.Convert con = null;
		if ((arr == null)
				|| ((con = (com.lang.annotation.Convert) cls
						.getAnnotation(com.lang.annotation.Convert.class)) == null))
			return ReflectConvert.dispose(convert, fileldsMap, null);
		ArrayList lists = new ArrayList();
		int maxIndex = arr.length - 1;
		if (maxIndex < 0)
			return ReflectConvert.dispose(convert, fileldsMap, lists);
		int index = con.index();
		ExceptionUtil.lessZeroThrowIndexOutOfBoundsException(index, "索引必须为非负数");
		ExceptionUtil.gtThrowIndexOutOfBoundsException(index, maxIndex,
				"索引必须在0-" + maxIndex + "之间");
		for (int i = index; i <= maxIndex; lists.add(convert.readFileds(cls,
				arr[(i++)], fileldsMap)))
			;
		return ReflectConvert.dispose(convert, fileldsMap, lists);
	}

	public static <T> ArrayList<T> toArrayList(Class<? super T> cls,
			List<Object[]> list) throws Exception {
		convert = new ReflectConvert(cls);
		com.lang.annotation.Convert con = null;
		if ((list == null)
				|| ((con = (com.lang.annotation.Convert) cls
						.getAnnotation(com.lang.annotation.Convert.class)) == null))
			return ReflectConvert.dispose(convert, fileldsMap, null);
		ArrayList lists = new ArrayList();
		int maxIndex = list.size() - 1;
		if (maxIndex < 0)
			return ReflectConvert.dispose(convert, fileldsMap, lists);
		int index = con.index();
		ExceptionUtil.lessZeroThrowIndexOutOfBoundsException(index, "索引必须为非负数");
		ExceptionUtil.gtThrowIndexOutOfBoundsException(index, maxIndex,
				"索引必须在0-" + maxIndex + "之间");
		for (int i = index; i <= maxIndex; lists.add(convert.readFileds(cls,
				(Object[]) list.get(i++), fileldsMap)))
			;
		return ReflectConvert.dispose(convert, fileldsMap, lists);
	}

	public static <T> LinkedList<? super T> toLinkedList(T[] ts) {
		if (ts == null)
			return null;
		LinkedList list = new LinkedList();
		for (int i = 0; i < ts.length; list.add(ts[(i++)]))
			;
		return list;
	}

	public static <T> LinkedList<? super T> toLinkedList(Collection<T> c) {
		if (c == null)
			return null;
		LinkedList linkedList = new LinkedList();
		if (c instanceof LinkedList)
			return ((LinkedList) c);
		if (c.isEmpty())
			return linkedList;
		if (c instanceof List) {
			List list = (List) c;
			for (int i = 0; i < list.size(); list.add(list.get(i++)))
				;
		} else {
			for (Iterator i = c.iterator(); i.hasNext(); linkedList.add(i
					.next()))
				;
		}
		return linkedList;
	}

	public static <K, V> LinkedList<? super V> toLinkedList(Map<K, V> map) {
		if (map == null)
			return null;
		LinkedList linkedList = new LinkedList();
		if (map.isEmpty())
			return linkedList;
		for (Iterator i = map.entrySet().iterator(); i.hasNext();)
			linkedList.add(((Map.Entry) i.next()).getValue());

		return linkedList;
	}

	public static <T> HashSet<? super T> toHashSet(T[] ts) {
		if (ts == null)
			return null;
		HashSet set = new HashSet();
		for (int i = 0; i < ts.length; set.add(ts[(i++)]))
			;
		return set;
	}

	public static <T> HashSet<? super T> toHashSet(Collection<T> c) {
		if (c == null)
			return null;
		if (c instanceof HashSet)
			return ((HashSet) c);
		HashSet hashSet = new HashSet();
		if (c.isEmpty())
			return hashSet;
		for (Iterator i = c.iterator(); i.hasNext(); hashSet.add(i.next()))
			;
		return hashSet;
	}

	public static <K, V> HashSet<? super V> toHashSet(Map<K, V> map) {
		if (map == null)
			return null;
		HashSet hashSet = new HashSet();
		if (map.isEmpty())
			return hashSet;
		for (Iterator i = map.entrySet().iterator(); i.hasNext();)
			hashSet.add(((Map.Entry) i.next()).getValue());

		return hashSet;
	}

	public static <T> TreeSet<? super T> toTreeSet(T[] ts) {
		if (ts == null)
			return null;
		TreeSet set = new TreeSet();
		for (int i = 0; i < ts.length; set.add(ts[(i++)]))
			;
		return set;
	}

	public static <T> TreeSet<? super T> toTreeSet(Collection<T> c) {
		if (c == null)
			return null;
		if (c instanceof TreeSet)
			return ((TreeSet) c);
		TreeSet treeSet = new TreeSet();
		if (c.isEmpty())
			return treeSet;
		for (Iterator i = c.iterator(); i.hasNext(); treeSet.add(i.next()))
			;
		return treeSet;
	}

	public static <K, V> TreeSet<? super V> toTreeSet(Map<K, V> map) {
		if (map == null)
			return null;
		TreeSet treeSet = new TreeSet();
		if (map.isEmpty())
			return treeSet;
		for (Iterator i = map.entrySet().iterator(); i.hasNext();)
			treeSet.add(((Map.Entry) i.next()).getValue());

		return treeSet;
	}

	public static <T> HashMap<? extends Number, ? super T> toHashMap(T[] ts) {
		if (ts == null)
			return null;
		HashMap hashMap = new HashMap();
		int count = 0;
		for (int i = 0; i < ts.length; hashMap.put(Integer.valueOf(++count),
				ts[(i++)]))
			;
		return hashMap;
	}

	public static <T> HashMap<? extends Number, ? super T> toHashMap(
			Collection<T> c) {
		if (c == null)
			return null;
		HashMap hashMap = new HashMap();
		if (c.isEmpty())
			return hashMap;
		int count = 0;
		for (Iterator i = c.iterator(); i.hasNext(); hashMap.put(
				Integer.valueOf(++count), i.next()))
			;
		return hashMap;
	}

	public static <K, V> HashMap<? super K, ? super V> toHashMap(Map<K, V> map) {
		if (map == null)
			return null;
		if (map instanceof HashMap)
			return ((HashMap) map);
		HashMap hashMap = new HashMap();
		if (map.isEmpty())
			return hashMap;
		for (Iterator i = map.entrySet().iterator(); i.hasNext();) {
			Map.Entry entry = (Map.Entry) i.next();
			hashMap.put(entry.getKey(), entry.getValue());
		}
		return hashMap;
	}

	public static <T> HashMap<? super String, ? super T> toHashMapString(T[] ts) {
		if (ts == null)
			return null;
		HashMap hashMap = new HashMap();
		int count = 0;
		for (int i = 0; i < ts.length; hashMap.put(String.valueOf(++count),
				ts[(i++)]))
			;
		return hashMap;
	}

	public static <T> HashMap<? super String, ? super T> toHashMapString(
			Collection<T> c) {
		if (c == null)
			return null;
		HashMap hashMap = new HashMap();
		if (c.isEmpty())
			return hashMap;
		int count = 0;
		for (Iterator i = c.iterator(); i.hasNext(); hashMap.put(
				String.valueOf(++count), i.next()))
			;
		return hashMap;
	}

	public static <T> TreeMap<? extends Number, ? super T> toTreeMap(T[] ts) {
		if (ts == null)
			return null;
		TreeMap treeMap = new TreeMap();
		int count = 0;
		for (int i = 0; i < ts.length; treeMap.put(Integer.valueOf(++count),
				ts[(i++)]))
			;
		return treeMap;
	}

	public static <T> TreeMap<? extends Number, ? super T> toTreeMap(
			Collection<T> c) {
		if (c == null)
			return null;
		TreeMap treeMap = new TreeMap();
		if (c.isEmpty())
			return treeMap;
		int count = 0;
		for (Iterator i = c.iterator(); i.hasNext(); treeMap.put(
				Integer.valueOf(++count), i.next()))
			;
		return treeMap;
	}

	public static <K, V> TreeMap<? super K, ? super V> toTreeMap(Map<K, V> map) {
		if (map == null)
			return null;
		if (map instanceof TreeMap)
			return ((TreeMap) map);
		TreeMap treeMap = new TreeMap();
		if (map.isEmpty())
			return treeMap;
		for (Iterator i = map.entrySet().iterator(); i.hasNext();) {
			Map.Entry entry = (Map.Entry) i.next();
			treeMap.put(entry.getKey(), entry.getValue());
		}
		return treeMap;
	}

	public static <T> TreeMap<? super String, ? super T> toTreeMapString(T[] ts) {
		if (ts == null)
			return null;
		TreeMap treeMap = new TreeMap();
		int count = 0;
		for (int i = 0; i < ts.length; treeMap.put(String.valueOf(++count),
				ts[(i++)]))
			;
		return treeMap;
	}

	public static <T> TreeMap<? super String, ? super T> toTreeMapString(
			Collection<T> c) {
		if (c == null)
			return null;
		TreeMap treeMap = new TreeMap();
		if (c.isEmpty())
			return treeMap;
		int count = 0;
		for (Iterator i = c.iterator(); i.hasNext(); treeMap.put(
				String.valueOf(++count), i.next()))
			;
		return treeMap;
	}

	private static class ReflectConvert<T> extends ReflectUtil<T> {
		public ReflectConvert(Class<? super T> cls)
				throws NoSuchFieldException, NoSuchMethodException {
			super(cls);
			Convert.fileldsMap = this.fileldsMap;
		}

		public Object readFileds(Class<?> cls, Object[] arr,
				Map<Field, Object> fileldsMap) throws Exception {
			return super.readFileds(cls, arr, fileldsMap);
		}

		public static <T> ArrayList<T> dispose(ReflectConvert<?> convert,
				Map<Field, Object> fileldsMap, ArrayList<T> list) {
			return ReflectUtil.dispose(convert, fileldsMap, list);
		}
	}
}