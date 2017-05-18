/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.lang;

import com.lang.enums.RoundingMode;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	public static String format(String format, Object[] args)
			throws FormatException {
		if (format == null)
			return null;
		StringBuilder sb = new StringBuilder(format);
		for (Matcher m = Pattern.compile("(?i)\\{[^{}\\\\]*\\}")
				.matcher(format); m.find();) {
			String str = m.group();
			String s = str.substring(1, str.length() - 1);
			int beginIndex = m.start();
			int endIndex = m.end() - 1;
			if ((beginIndex >= 1) && (format.charAt(beginIndex - 1) == '\\'))
				continue;
			if (!(isMatch("^\\{[+-]?\\d+[^{}]*\\}$", str)))
				throw new FormatException("参数format的格式不正确，在索引位置" + beginIndex
						+ "-" + endIndex + "之间的表达式：" + str);
			Integer index = Convert.toInteger(matchesString("^[+-]?\\d+", s));
			if ((index == null) || (index.intValue() < 0)
					|| (index.intValue() >= args.length))
				throw new FormatException("索引必须从零开始，且小于参数列表的大小，在索引位置"
						+ beginIndex + "-" + endIndex + "之间的表达式：" + str);
			String pattern = "\\{" + s + "\\}";
			Object obj = (args[index.intValue()] == null) ? "" : args[index
					.intValue()];
			if (isMatch("^[+]?\\d+$", s)) {
				sb = new StringBuilder(sb.toString().replaceAll(pattern,
						obj.toString()));
			} else if (isMatch(
					"^[+]?\\d+[：:，,](([cfrt])|(ceil)|(floor)|(round)|(trunc))([+-]?\\d+)?([：:，,](([tfn])|(true)|(false)|(null)))?$",
					s, true)) {
				Double d = Convert.toDouble(obj);
				if (d == null) {
					sb = new StringBuilder(sb.toString().replaceAll(pattern,
							obj.toString()));
				} else {
					Boolean sc = null;
					if (isMatch("(t)|(true)$", s, true))
						sc = Boolean.valueOf(true);
					else if (isMatch("(f)|(false)$", s, true))
						sc = Boolean.valueOf(false);
					String[] arr = allMatchesToArray("[+-]?\\d+", s);
					int scale = 2;
					if (arr.length > 1)
						scale = Convert.toIntValue(arr[1]);
					RoundingMode mode = null;
					if (isMatch("^[+]?\\d+[：:，,]((c)|(ceil))", s, true))
						mode = RoundingMode.CEIL;
					else if (isMatch("^[+]?\\d+[：:，,]((f)|(floor))", s, true))
						mode = RoundingMode.FLOOR;
					else if (isMatch("^[+]?\\d+[：:，,]((r)|(round))", s, true))
						mode = RoundingMode.ROUND;
					else if (isMatch("^[+]?\\d+[：:，,]((t)|(trunc))", s, true))
						mode = RoundingMode.TRUNC;
					else
						throw new FormatException("参数format的格式不正确，在索引位置"
								+ beginIndex + "-" + endIndex + "之间的表达式：" + str);
					if (sc == Boolean.TRUE) {
						if ((scale < 0) || (scale > 9))
							throw new FormatException("有效数字必须在0-9之间，在索引位置"
									+ beginIndex + "-" + endIndex + "之间的表达式："
									+ str);
						sb = new StringBuilder(sb.toString().replaceAll(
								pattern,
								Convert.toScienceString(obj, scale, mode)));
					} else if (sc == Boolean.FALSE) {
						if ((scale < 0) || (scale > 99))
							throw new FormatException("小数位数必须在0-99之间，在索引位置"
									+ beginIndex + "-" + endIndex + "之间的表达式："
									+ str);
						sb = new StringBuilder(sb.toString().replaceAll(
								pattern,
								Convert.toPlainString(obj, scale, mode)));
					} else {
						sb = new StringBuilder(sb.toString().replaceAll(
								pattern,
								Convert.toDouble(d, scale, mode).toString()));
					}
				}
			} else if (isMatch(
					"^[+]?\\d+[：:，,](([aou])|(abs)|(opposite)|(unnecessary))([：:，,](([tfn])|(true)|(false)|(null)))?$",
					s, true)) {
				Double d = Convert.toDouble(obj);
				if (d == null) {
					sb = new StringBuilder(sb.toString().replaceAll(pattern,
							obj.toString()));
				} else {
					Boolean sc = null;
					if (isMatch("(t)|(true)$", s, true))
						sc = Boolean.valueOf(true);
					else if (isMatch("(f)|(false)$", s, true))
						sc = Boolean.valueOf(false);
					RoundingMode mode = null;
					if (isMatch("^[+]?\\d+[：:，,]((a)|(abs))", s, true))
						mode = RoundingMode.ABS;
					else if (isMatch("^[+]?\\d+[：:，,]((o)|(opposite))", s, true))
						mode = RoundingMode.OPPOSITE;
					else if (isMatch("^[+]?\\d+[：:，,]((u)|(unnecessary))", s,
							true))
						mode = RoundingMode.UNNECESSARY;
					else
						throw new FormatException("参数format的格式不正确，在索引位置"
								+ beginIndex + "-" + endIndex + "之间的表达式：" + str);
					if (sc == Boolean.TRUE)
						sb = new StringBuilder(sb.toString()
								.replaceAll(pattern,
										Convert.toScienceString(obj, -1, mode)));
					else if (sc == Boolean.FALSE)
						sb = new StringBuilder(sb.toString().replaceAll(
								pattern, Convert.toPlainString(obj, -1, mode)));
					else
						sb = new StringBuilder(sb.toString().replaceAll(
								pattern,
								Convert.toDouble(d, -1, mode).toString()));
				}
			} else {
				if (isMatch(
						"^[+]?\\d+[：:，,](([us]?d)|(utildate)|(shd)|(shortdate)|(sqldate))$",
						s, true)) {
					if (!(obj instanceof Date)) {
						sb = new StringBuilder(sb.toString().replaceAll(
								pattern, obj.toString()));
						continue;
					}
					if (isMatch("^[+]?\\d+[：:，,](([u]?d)|(utildate))", s, true)) {
						sb = new StringBuilder(sb.toString().replaceAll(
								pattern, Convert.toDateString((Date) obj)));
						continue;
					}
					if (isMatch(
							"^[+]?\\d+[：:，,]((s[h]?d)|(shortdate)|(sqldate))",
							s, true)) {
						sb = new StringBuilder(sb.toString().replaceAll(
								pattern, Convert.toShortDateString((Date) obj)));
						continue;
					}
					throw new FormatException("参数format的格式不正确，在索引位置"
							+ beginIndex + "-" + endIndex + "之间的表达式：" + str);
				}
				if (isMatch("^[+]?\\d+[：:，,]((fd)|(formatdate))[：:，,][^a|a]+$",
						s, true)) {
					if (obj instanceof Date) {
						String fmt = s.replaceFirst(
								"(?i)^[+]?\\d+[：:，,]((fd)|(formatdate))[：:，,]",
								"");
						obj = Convert.toDateString((Date) obj, fmt);
						if (obj == null)
							throw new FormatException("无效的日期表达式，在索引位置"
									+ beginIndex + "-" + endIndex + "之间的表达式："
									+ str);
					}
					sb = new StringBuilder(sb.toString().replaceAll(pattern,
							obj.toString()));
				} else {
					throw new FormatException("参数format的格式不正确，在索引位置"
							+ beginIndex + "-" + endIndex + "之间的表达式：" + str);
				}
			}
		}
		return sb.toString().replaceAll("\\\\\\{", "{")
				.replaceAll("\\\\\\}", "}");
	}

	public static boolean isMatch(String pattern, String str) {
		return isMatch(pattern, str, false);
	}

	public static boolean isMatch(String pattern, String str,
			boolean isIgnoreCase) {
		ExceptionUtil.nullThrowException(pattern, "pattern is null");
		if (str == null)
			return false;
		Matcher m = Pattern.compile(((isIgnoreCase) ? "(?i)" : "") + pattern)
				.matcher(str);
		return m.find();
	}

	public static AbstractMap.SimpleEntry<Integer, Integer> matchesIndex(
			String pattern, String str) {
		return matchesIndex(pattern, str, false);
	}

	public static AbstractMap.SimpleEntry<Integer, Integer> matchesIndex(
			String pattern, String str, boolean isIgnoreCase) {
		ExceptionUtil.nullThrowException(pattern, "pattern is null");
		if (str == null)
			return null;
		Matcher m = Pattern.compile(((isIgnoreCase) ? "(?i)" : "") + pattern)
				.matcher(str);
		if (m.find())
			return new AbstractMap.SimpleEntry(Integer.valueOf(m.start()),
					Integer.valueOf(m.end() - 1));
		return null;
	}

	public static AbstractMap.SimpleEntry<Integer, Integer> lastMatchesIndex(
			String pattern, String str) {
		return lastMatchesIndex(pattern, str, false);
	}

	public static AbstractMap.SimpleEntry<Integer, Integer> lastMatchesIndex(
			String pattern, String str, boolean isIgnoreCase) {
		ExceptionUtil.nullThrowException(pattern, "pattern is null");
		if (str == null)
			return null;
		AbstractMap.SimpleEntry simpleEntry = null;
		for (Matcher m = Pattern.compile(
				((isIgnoreCase) ? "(?i)" : "") + pattern).matcher(str); m
				.find();) {
			simpleEntry = new AbstractMap.SimpleEntry(
					Integer.valueOf(m.start()), Integer.valueOf(m.end() - 1));
			if (m.find())
				simpleEntry = new AbstractMap.SimpleEntry(Integer.valueOf(m
						.start()), Integer.valueOf(m.end() - 1));
			else
				return simpleEntry;
		}
		return simpleEntry;
	}

	public static String matchesString(String pattern, String str) {
		return matchesString(pattern, str, false);
	}

	public static String matchesString(String pattern, String str,
			boolean isIgnoreCase) {
		ExceptionUtil.nullThrowException(pattern, "pattern is null");
		if (str == null)
			return null;
		Matcher m = Pattern.compile(((isIgnoreCase) ? "(?i)" : "") + pattern)
				.matcher(str);
		if (m.find())
			return m.group();
		return null;
	}

	public static AbstractMap.SimpleEntry<AbstractMap.SimpleEntry<Integer, Integer>, String> matches(
			String pattern, String str) {
		return matches(pattern, str, false);
	}

	public static AbstractMap.SimpleEntry<AbstractMap.SimpleEntry<Integer, Integer>, String> matches(
			String pattern, String str, boolean isIgnoreCase) {
		ExceptionUtil.nullThrowException(pattern, "pattern is null");
		if (str == null)
			return null;
		Matcher m = Pattern.compile(((isIgnoreCase) ? "(?i)" : "") + pattern)
				.matcher(str);
		if (m.find())
			return new AbstractMap.SimpleEntry(new AbstractMap.SimpleEntry(
					Integer.valueOf(m.start()), Integer.valueOf(m.end() - 1)),
					m.group());
		return null;
	}

	public static AbstractMap.SimpleEntry<AbstractMap.SimpleEntry<Integer, Integer>, String> lastMatches(
			String pattern, String str) {
		return lastMatches(pattern, str, false);
	}

	public static AbstractMap.SimpleEntry<AbstractMap.SimpleEntry<Integer, Integer>, String> lastMatches(
			String pattern, String str, boolean isIgnoreCase) {
		ExceptionUtil.nullThrowException(pattern, "pattern is null");
		if (str == null)
			return null;
		AbstractMap.SimpleEntry simpleEntry = null;
		for (Matcher m = Pattern.compile(
				((isIgnoreCase) ? "(?i)" : "") + pattern).matcher(str); m
				.find();) {
			simpleEntry = new AbstractMap.SimpleEntry(
					new AbstractMap.SimpleEntry(Integer.valueOf(m.start()),
							Integer.valueOf(m.end() - 1)), m.group());
			if (m.find())
				simpleEntry = new AbstractMap.SimpleEntry(
						new AbstractMap.SimpleEntry(Integer.valueOf(m.start()),
								Integer.valueOf(m.end() - 1)), m.group());
			else
				return simpleEntry;
		}
		return simpleEntry;
	}

	public static int allMatchesCount(String pattern, String str) {
		return allMatchesCount(pattern, str, false);
	}

	public static int allMatchesCount(String pattern, String str,
			boolean isIgnoreCase) {
		ExceptionUtil.nullThrowException(pattern, "pattern is null");
		if (str == null)
			return 0;
		int count = 0;
		for (Matcher m = Pattern.compile(
				((isIgnoreCase) ? "(?i)" : "") + pattern).matcher(str); m
				.find(); ++count)
			;
		return count;
	}

	public static String[] allMatchesToArray(String pattern, String str) {
		return allMatchesToArray(pattern, str, false);
	}

	public static String[] allMatchesToArray(String pattern, String str,
			boolean isIgnoreCase) {
		return ((String[]) Convert
				.toArray(allMatchesToArrayList(pattern, str, isIgnoreCase),
						String.class));
	}

	public static ArrayList<String> allMatchesToArrayList(String pattern,
			String str) {
		return allMatchesToArrayList(pattern, str, false);
	}

	public static ArrayList<String> allMatchesToArrayList(String pattern,
			String str, boolean isIgnoreCase) {
		ExceptionUtil.nullThrowException(pattern, "pattern is null");
		ArrayList arrayList = new ArrayList();
		if (str == null)
			return arrayList;
		for (Matcher m = Pattern.compile(
				((isIgnoreCase) ? "(?i)" : "") + pattern).matcher(str); m
				.find(); arrayList.add(m.group()))
			;
		return arrayList;
	}

	public static LinkedList<String> allMatchesToLinkedList(String pattern,
			String str) {
		return allMatchesToLinkedList(pattern, str, false);
	}

	public static LinkedList<String> allMatchesToLinkedList(String pattern,
			String str, boolean isIgnoreCase) {
		ExceptionUtil.nullThrowException(pattern, "pattern is null");
		LinkedList linkedList = new LinkedList();
		if (str == null)
			return linkedList;
		for (Matcher m = Pattern.compile(
				((isIgnoreCase) ? "(?i)" : "") + pattern).matcher(str); m
				.find(); linkedList.add(m.group()))
			;
		return linkedList;
	}

	public static HashSet<String> allMatchesToHashSet(String pattern, String str) {
		return allMatchesToHashSet(pattern, str, false);
	}

	public static HashSet<String> allMatchesToHashSet(String pattern,
			String str, boolean isIgnoreCase) {
		ExceptionUtil.nullThrowException(pattern, "pattern is null");
		HashSet hashSet = new HashSet();
		if (str == null)
			return hashSet;
		for (Matcher m = Pattern.compile(
				((isIgnoreCase) ? "(?i)" : "") + pattern).matcher(str); m
				.find(); hashSet.add(m.group()))
			;
		return hashSet;
	}

	public static TreeSet<String> allMatchesToTreeSet(String pattern, String str) {
		return allMatchesToTreeSet(pattern, str, false);
	}

	public static TreeSet<String> allMatchesToTreeSet(String pattern,
			String str, boolean isIgnoreCase) {
		ExceptionUtil.nullThrowException(pattern, "pattern is null");
		TreeSet treeSet = new TreeSet();
		if (str == null)
			return treeSet;
		for (Matcher m = Pattern.compile(
				((isIgnoreCase) ? "(?i)" : "") + pattern).matcher(str); m
				.find(); treeSet.add(m.group()))
			;
		return treeSet;
	}

	public static HashMap<AbstractMap.SimpleEntry<Integer, Integer>, String> allMatchesToHashMap(
			String pattern, String str) {
		return allMatchesToHashMap(pattern, str, false);
	}

	public static HashMap<AbstractMap.SimpleEntry<Integer, Integer>, String> allMatchesToHashMap(
			String pattern, String str, boolean isIgnoreCase) {
		ExceptionUtil.nullThrowException(pattern, "pattern is null");
		HashMap hashMap = new HashMap();
		if (str == null)
			return hashMap;
		for (Matcher m = Pattern.compile(
				((isIgnoreCase) ? "(?i)" : "") + pattern).matcher(str); m
				.find();)
			hashMap.put(new AbstractMap.SimpleEntry(Integer.valueOf(m.start()),
					Integer.valueOf(m.end() - 1)), m.group());
		return hashMap;
	}

	public static TreeMap<AbstractMap.SimpleEntry<Integer, Integer>, String> allMatchesToTreeMap(
			String pattern, String str) {
		return allMatchesToTreeMap(pattern, str, false);
	}

	public static TreeMap<AbstractMap.SimpleEntry<Integer, Integer>, String> allMatchesToTreeMap(
			String pattern, String str, boolean isIgnoreCase) {
		ExceptionUtil.nullThrowException(pattern, "pattern is null");
		TreeMap treeMap = new TreeMap();
		if (str == null)
			return treeMap;
		for (Matcher m = Pattern.compile(
				((isIgnoreCase) ? "(?i)" : "") + pattern).matcher(str); m
				.find();)
			treeMap.put(new AbstractMap.SimpleEntry(Integer.valueOf(m.start()),
					Integer.valueOf(m.end() - 1)), m.group());
		return treeMap;
	}

	public static boolean equals(String str1, String str2) {
		return ((str1 == null) ? false
				: ((str1 == null) && (str2 == null)) ? true : str1.equals(str2));
	}

	public static boolean equalsIgnoreCase(String str1, String str2) {
		return ((str1 == null) ? false
				: ((str1 == null) && (str2 == null)) ? true : str1
						.equalsIgnoreCase(str2));
	}

	public static <T> String join(boolean[] arr) {
		return join(arr, ",");
	}

	public static <T> String join(boolean[] arr, boolean newline) {
		return join(arr, (newline) ? "\r\n" : ",");
	}

	public static <T> String join(boolean[] arr, String s) {
		if (arr == null)
			return null;
		if (arr.length == 0)
			return "";
		if (s == null)
			s = "";
		StringBuilder sb = new StringBuilder(String.valueOf(arr[0]));
		for (int i = 1; i < arr.length; ++i)
			sb.append(s).append(arr[i]);
		return sb.toString();
	}

	public static <T> String join(char[] arr) {
		return join(arr, ",");
	}

	public static <T> String join(char[] arr, boolean newline) {
		return join(arr, (newline) ? "\r\n" : ",");
	}

	public static <T> String join(char[] arr, String s) {
		if (arr == null)
			return null;
		if (arr.length == 0)
			return "";
		if (s == null)
			s = "";
		StringBuilder sb = new StringBuilder(String.valueOf(arr[0]));
		for (int i = 1; i < arr.length; ++i)
			sb.append(s).append(arr[i]);
		return sb.toString();
	}

	public static <T> String join(byte[] arr) {
		return join(arr, ",");
	}

	public static <T> String join(byte[] arr, boolean newline) {
		return join(arr, (newline) ? "\r\n" : ",");
	}

	public static <T> String join(byte[] arr, String s) {
		if (arr == null)
			return null;
		if (arr.length == 0)
			return "";
		if (s == null)
			s = "";
		StringBuilder sb = new StringBuilder(String.valueOf(arr[0]));
		for (int i = 1; i < arr.length; ++i)
			sb.append(s).append(arr[i]);
		return sb.toString();
	}

	public static <T> String join(short[] arr) {
		return join(arr, ",");
	}

	public static <T> String join(short[] arr, boolean newline) {
		return join(arr, (newline) ? "\r\n" : ",");
	}

	public static <T> String join(short[] arr, String s) {
		if (arr == null)
			return null;
		if (arr.length == 0)
			return "";
		if (s == null)
			s = "";
		StringBuilder sb = new StringBuilder(String.valueOf(arr[0]));
		for (int i = 1; i < arr.length; ++i)
			sb.append(s).append(arr[i]);
		return sb.toString();
	}

	public static <T> String join(int[] arr) {
		return join(arr, ",");
	}

	public static <T> String join(int[] arr, boolean newline) {
		return join(arr, (newline) ? "\r\n" : ",");
	}

	public static <T> String join(int[] arr, String s) {
		if (arr == null)
			return null;
		if (arr.length == 0)
			return "";
		if (s == null)
			s = "";
		StringBuilder sb = new StringBuilder(String.valueOf(arr[0]));
		for (int i = 1; i < arr.length; ++i)
			sb.append(s).append(arr[i]);
		return sb.toString();
	}

	public static <T> String join(long[] arr) {
		return join(arr, ",");
	}

	public static <T> String join(long[] arr, boolean newline) {
		return join(arr, (newline) ? "\r\n" : ",");
	}

	public static <T> String join(long[] arr, String s) {
		if (arr == null)
			return null;
		if (arr.length == 0)
			return "";
		if (s == null)
			s = "";
		StringBuilder sb = new StringBuilder(String.valueOf(arr[0]));
		for (int i = 1; i < arr.length; ++i)
			sb.append(s).append(arr[i]);
		return sb.toString();
	}

	public static <T> String join(float[] arr) {
		return join(arr, ",");
	}

	public static <T> String join(float[] arr, boolean newline) {
		return join(arr, (newline) ? "\r\n" : ",");
	}

	public static <T> String join(float[] arr, String s) {
		if (arr == null)
			return null;
		if (arr.length == 0)
			return "";
		if (s == null)
			s = "";
		StringBuilder sb = new StringBuilder(String.valueOf(arr[0]));
		for (int i = 1; i < arr.length; ++i)
			sb.append(s).append(arr[i]);
		return sb.toString();
	}

	public static <T> String join(double[] arr) {
		return join(arr, ",");
	}

	public static <T> String join(double[] arr, boolean newline) {
		return join(arr, (newline) ? "\r\n" : ",");
	}

	public static <T> String join(double[] arr, String s) {
		if (arr == null)
			return null;
		if (arr.length == 0)
			return "";
		if (s == null)
			s = "";
		StringBuilder sb = new StringBuilder(String.valueOf(arr[0]));
		for (int i = 1; i < arr.length; ++i)
			sb.append(s).append(arr[i]);
		return sb.toString();
	}

	public static <T> String join(T[] ts) {
		return join(ts, ",");
	}

	public static <T> String join(T[] ts, boolean newline) {
		return join(ts, (newline) ? "\r\n" : ",");
	}

	public static <T> String join(T[] ts, String s) {
		if (ts == null)
			return null;
		if (ts.length == 0)
			return "";
		if (s == null)
			s = "";
		StringBuilder sb = new StringBuilder(String.valueOf(ts[0]));
		for (int i = 1; i < ts.length; ++i)
			sb.append(s).append(ts[i]);
		return sb.toString();
	}

	public static <T> String join(Collection<T> c) {
		return join(c, ",");
	}

	public static <T> String join(Collection<T> c, boolean newline) {
		return join(c, (newline) ? "\r\n" : ",");
	}

	public static <T> String join(Collection<T> c, String s) {
		if (c == null)
			return null;
		if (c.isEmpty())
			return "";
		if (s == null)
			s = "";
		StringBuilder sb = new StringBuilder();
		if (c instanceof List) {
			List list = (List) c;
			sb.append(list.get(0));
			for (int i = 1; i < list.size(); sb.append(s).append(list.get(i++)))
				;
		} else {
			Iterator i = c.iterator();
			if (i.hasNext())
				sb.append(i.next());
			for (; i.hasNext(); sb.append(s).append(i.next()))
				;
		}
		return sb.toString();
	}

	public static <K, V> String join(Map<K, V> map) {
		return join(map, ",");
	}

	public static <K, V> String join(Map<K, V> map, boolean newline) {
		return join(map, (newline) ? "\r\n" : ",");
	}

	public static <K, V> String join(Map<K, V> map, String s) {
		if (map == null)
			return null;
		if (map.isEmpty())
			return "";
		if (s == null)
			s = "";
		StringBuilder sb = new StringBuilder();
		Iterator i = map.entrySet().iterator();
		if (i.hasNext())
			sb.append(i.next());
		for (; i.hasNext(); sb.append(s).append(i.next()))
			;
		return sb.toString();
	}

	public static boolean isEmpty(String value) {
		return ((value != null) && (value.isEmpty()));
	}

	public static boolean isBlank(String value) {
		return ((!(isNullOrEmpty(value))) && (value.trim().isEmpty()));
	}

	public static boolean isWhiteSpace(String value) {
		return ((value != null) && (value.trim().isEmpty()));
	}

	public static boolean isNullOrEmpty(String value) {
		return ((value == null) || (value.isEmpty()));
	}

	public static boolean isNullOrBlank(String value) {
		return ((value == null) || (isBlank(value)));
	}

	public static boolean isNullOrWhiteSpace(String value) {
		return ((value == null) || (value.trim().isEmpty()));
	}

	public static Object getNulllObject(Object obj1, Object obj2) {
		return getNullObject(obj1, obj2, obj1);
	}

	public static Object getNullObject(Object obj1, Object obj2, Object obj3) {
		return ((obj1 == null) ? obj2 : obj3);
	}

	public static String getNulllString(Object obj) {
		return getNulllString(obj, "", obj);
	}

	public static String getNulllString(Object obj1, Object obj2) {
		return getNulllString(obj1, obj2, obj1);
	}

	public static String getNulllString(Object obj1, Object obj2, Object obj3) {
		return obj3;
	}

	public static String getNullOrEmptyString(String str, Object obj) {
		return getNullOrEmptyString(str, obj, str);
	}

	public static String getNullOrEmptyString(String str, Object obj1,
			Object obj2) {
		return obj2;
	}

	public static String getNullOrWhiteSpaceString(String str, Object obj) {
		return getNullOrWhiteSpaceString(str, obj, str);
	}

	public static String getNullOrWhiteSpaceString(String str, Object obj1,
			Object obj2) {
		return obj2;
	}

	public static String distinctString(String str, char separator) {
		String sep = String.valueOf(separator);
		String[] strArr = str.replaceAll("(^" + sep + "+)|(" + sep + "+$)", "")
				.replaceAll(sep + "+", sep).split(sep);
		List list = new ArrayList();
		for (String s : strArr) {
			if (!(list.contains(s)))
				list.add(s);
		}
		return join(list, String.valueOf(separator));
	}

	public static String distinctString(String str, String pattern) {
		return distinctString(str, pattern, false, true);
	}

	public static String distinctString(String str, String pattern,
			boolean isIgnoreCase) {
		return distinctString(str, pattern, isIgnoreCase, true);
	}

	public static String distinctString(String str, String pattern,
			boolean isIgnoreCase, boolean isMatchWholeWord) {
		if (isNullOrEmpty(str))
			return str;
		ExceptionUtil.nullThrowException(pattern, "pattern is null");
		if (pattern.isEmpty())
			return str;
		Matcher m = Pattern.compile(pattern).matcher(str);
		if (!(m.find()))
			return str;
		String s = m.group();
		int index = str.indexOf(s);
		if (index < 0)
			return str;
		StringBuilder pat = new StringBuilder(pattern);
		if (isMatchWholeWord)
			pat = new StringBuilder("\\b").append(pattern).append("\\b");
		if (isIgnoreCase)
			pat = new StringBuilder("(?i)").append(pat);
		str = str.substring(0, index + s.length())
				+ str.substring(index + s.length()).replaceAll(pat.toString(),
						"");
		return str;
	}

	public static String firstLetterToUpper(String str) {
		if ((str == null) || (str.length() == 0))
			return str;
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	public static String firstLetterToLower(String str) {
		if ((str == null) || (str.length() == 0))
			return str;
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}

	public static String getMethodName(Field field, String prefix) {
		if ((field == null) || (isNullOrWhiteSpace(prefix)))
			return null;
		return prefix + firstLetterToUpper(field.getName());
	}

	public static String getGetterMethodName(Field field) {
		return getMethodName(field, "get");
	}

	public static String getSetterMethodName(Field field) {
		return getMethodName(field, "set");
	}

	public static String getIsMethodName(Field field) {
		return getMethodName(field, "is");
	}

	public static String getResourcePathAtClasspath() {
		return getResourcePathAtClasspath("");
	}

	public static String getResourcePathAtClasspath(String fileName) {
		return Thread.currentThread().getContextClassLoader()
				.getResource((fileName == null) ? "" : fileName).getPath();
	}

	public static String getResourcePathAtClasspath(Class<?> cls) {
		return getResourcePathAtClasspath(cls, "");
	}

	public static String getResourcePathAtClasspath(Class<?> cls,
			String fileName) {
		if (cls == null)
			return null;
		return cls.getClassLoader()
				.getResource((fileName == null) ? "" : fileName).getPath();
	}

	public static String getResourcePath(Class<?> cls) {
		return getResourcePath(cls, "");
	}

	public static String getResourcePath(Class<?> cls, String fileName) {
		if ((cls == null) || (fileName == null))
			return null;
		try {
			return cls.getResource(fileName).toURI().getPath();
		} catch (URISyntaxException e) {
		}
		return null;
	}

	public static String getDecodePath(String path) {
		return getDecodePath(path, "utf-8");
	}

	public static String getDecodePath(String path, String encode) {
		if ((isNullOrEmpty(path)) || (isNullOrEmpty(encode)))
			return null;
		try {
			return URLDecoder.decode(path, encode);
		} catch (UnsupportedEncodingException e) {
		}
		return path;
	}

	public static String getEncodeString(String str) {
		return getEncodeString(str, "utf-8");
	}

	public static String getEncodeString(String str, String target) {
		return getEncodeString(str, "ISO-8859-1", target);
	}

	public static String getEncodeString(String str, String source,
			String target) {
		try {
			if ((isNullOrWhiteSpace(str)) || (isNullOrWhiteSpace(source))
					|| (isNullOrWhiteSpace(target)))
				return str;
			return new String(str.getBytes(source), target);
		} catch (Exception e) {
		}
		return null;
	}

	public static String subString(String str, int byteLength) {
		ExceptionUtil.ltThrowException(Integer.valueOf(byteLength),
				Integer.valueOf(0), "指定的字节长度必须大于0");
		if ((str == null) || (str.getBytes().length <= byteLength))
			return str;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); ++i) {
			String tmp = String.valueOf(str.charAt(i));
			if (sb.toString().getBytes().length + tmp.getBytes().length > byteLength)
				break;
			sb.append(tmp);
		}
		return sb.toString();
	}

	public static String omitString(String str, int byteLength) {
		if ((str == null) || (str.getBytes().length <= byteLength))
			return str;
		StringBuffer sb = new StringBuffer(subString(str, byteLength));
		for (int i = sb.toString().getBytes().length; i < byteLength; ++i)
			sb.append(".");
		sb.append("...");
		return sb.toString();
	}
}