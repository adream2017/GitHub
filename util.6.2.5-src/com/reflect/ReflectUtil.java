/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.reflect;

import com.lang.Convert;
import com.lang.ExceptionUtil;
import com.lang.StringUtil;
import com.lang.annotation.ReadField;
import com.lang.annotation.Transient;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ReflectUtil<T> {
	protected Class<? super T> cls = null;
	protected Map<Field, Object> fileldsMap = new HashMap();

	public ReflectUtil(Class<? super T> cls) throws NoSuchMethodException {
		ExceptionUtil.nullThrowException(cls, "cls is null");
		if ((cls.getClassLoader() == null) || (cls.isEnum())
				|| (cls.isInterface())
				|| (cls.getDeclaredConstructors().length == 0))
			throw new IllegalArgumentException(
					"the type of cls param must be a custom class type,it can't be a interface or annotation or enum and so on");
		if (Modifier.isAbstract(cls.getModifiers()))
			throw new IllegalArgumentException("the class of " + cls.getName()
					+ " can't be abstract");
		try {
			cls.newInstance();
			readFileds(cls, this.fileldsMap);
			this.cls = cls;
		} catch (Exception e) {
			throw new NoSuchMethodException("the 'public "
					+ cls.getSimpleName()
					+ "()' constructor is not found in the " + cls.getName()
					+ " class");
		}
	}

	protected Map<Field, Object> readFileds(Class<?> cls, Map<Field, Object> map)
			throws NoSuchFieldException, NoSuchMethodException {
		ExceptionUtil.nullThrowException(cls, "cls is null");
		try {
			Field[] fields = cls.getDeclaredFields();
			if (fields.length == 0)
				return null;
			for (int i = 0; i < fields.length; ++i) {
				Field field = fields[i];
				int m = field.getModifiers();
				if (Modifier.isStatic(m))
					continue;
				if (Modifier.isFinal(m))
					continue;
				Class c = field.getType();
				boolean classloader = c.getClassLoader() != null;
				if ((!(classloader))
						&& (field.getAnnotation(ReadField.class) == null))
					continue;
				if ((classloader)
						&& (field.isAnnotationPresent(Transient.class)))
					continue;
				Method method = null;
				if ((!(Modifier.isPublic(m)))
						&& ((method = getMethod(cls,
								StringUtil.getSetterMethodName(field), c)) == null))
					continue;
				if (classloader)
					map.put(field, new AbstractMap.SimpleEntry(method,
							readFileds(c, new HashMap())));
				else
					map.put(field, method);
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	protected Object readFileds(Class<?> cls, Object[] arr,
			Map<Field, Object> fileldsMap) throws Exception {
		if ((arr == null) || (arr.length == 0) || (fileldsMap == null)
				|| (fileldsMap.isEmpty()))
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
				Object res = readFileds(f.getType(), arr,
						(Map) simpleEntry.getValue());
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
				StringBuilder sb = new StringBuilder(cls.getName()).append(".")
						.append(f.getName()).append("：");
				int index = readField.index();
				int length = arr.length;
				ExceptionUtil.lessZeroThrowIndexOutOfBoundsException(index,
						sb.toString() + "索引必须为非负数");
				ExceptionUtil.gtThrowIndexOutOfBoundsException(index, length,
						(length == 0) ? "索引只能为0" : new StringBuilder("索引必须在0-")
								.append(length).append("之间").toString());
				Class c = f.getType();
				Object o = getObject(arr[index], c);
				if (Modifier.isPublic(f.getModifiers()))
					f.set(obj, o);
				else if (value instanceof Method)
					((Method) value).invoke(obj, new Object[] { o });
			}
		}
		return obj;
	}

	protected Method getMethod(Class<?> cls, String methodName, Class<?> c) {
		ExceptionUtil.nullThrowException(cls, "cls is null");
		ExceptionUtil.nullOrWhiteSpaceThrowException(methodName,
				"methodName is null");
		ExceptionUtil.nullThrowException(c, "c is null");
		try {
			Method method = cls.getMethod(methodName, new Class[] { c });
			return ((Modifier.isStatic(method.getModifiers())) ? null : method);
		} catch (NoSuchMethodException localNoSuchMethodException1) {
			try {
				Class cl = getClass(c);
				if (cl == null)
					return null;
				Method method = cls.getMethod(methodName, new Class[] { cl });
				return ((Modifier.isStatic(method.getModifiers())) ? null
						: method);
			} catch (NoSuchMethodException localNoSuchMethodException1) {
			}
		}
		return null;
	}

	protected Class<?> getClass(Class<?> c) {
		if (c == null)
			return null;
		if (c == Boolean.TYPE)
			c = Boolean.class;
		else if (c == Boolean.class)
			c = Boolean.TYPE;
		else if (c == Character.TYPE)
			c = Character.class;
		else if (c == Character.class)
			c = Character.TYPE;
		else if (c == Byte.TYPE)
			c = Byte.class;
		else if (c == Byte.class)
			c = Byte.TYPE;
		else if (c == Short.TYPE)
			c = Short.class;
		else if (c == Short.class)
			c = Short.TYPE;
		else if (c == Integer.TYPE)
			c = Integer.class;
		else if (c == Integer.class)
			c = Integer.TYPE;
		else if (c == Long.TYPE)
			c = Long.class;
		else if (c == Long.class)
			c = Long.TYPE;
		else if (c == Float.TYPE)
			c = Float.class;
		else if (c == Float.class)
			c = Float.TYPE;
		else if (c == Double.TYPE)
			c = Double.class;
		else if (c == Double.class)
			c = Double.TYPE;
		else
			c = null;
		return c;
	}

	protected Object getObject(Object value, Class<?> c) {
		ExceptionUtil.nullThrowException(value, "value is null");
		ExceptionUtil.nullThrowException(c, "c is null");
		if (c == Object.class)
			return value;
		if (c == Void.class)
			return null;
		if (c == Boolean.TYPE)
			return Boolean.valueOf(Convert.toBooleanValue(value));
		if (c == Boolean.class)
			return Convert.toBoolean(value);
		if (c == Character.TYPE)
			return Character.valueOf(Convert.toCharValue(value));
		if (c == Character.class)
			return Convert.toCharacter(value);
		if (c == Byte.TYPE)
			return Byte.valueOf(Convert.toByteValue(value));
		if (c == Byte.class)
			return Convert.toByte(value);
		if (c == Short.TYPE)
			return Short.valueOf(Convert.toShortValue(value));
		if (c == Short.class)
			return Convert.toShort(value);
		if (c == Integer.TYPE)
			return Integer.valueOf(Convert.toIntValue(value));
		if (c == Integer.class)
			return Convert.toInteger(value);
		if (c == Long.TYPE)
			return Long.valueOf(Convert.toLongValue(value));
		if (c == Long.class)
			return Convert.toLong(value);
		if (c == Float.TYPE)
			return Float.valueOf(Convert.toFloatValue(value));
		if (c == Float.class)
			return Convert.toFloat(value);
		if (c == Double.TYPE)
			return Double.valueOf(Convert.toDoubleValue(value));
		if (c == Double.class)
			return Convert.toDouble(value);
		if (c == Number.class)
			return Convert.toNumber(value);
		if (c == BigInteger.class)
			return Convert.toBigInteger(value);
		if (c == BigDecimal.class)
			return Convert.toBigDecimal(value);
		if (c == java.sql.Date.class) {
			if (value instanceof java.util.Date)
				return new java.sql.Date(((java.util.Date) value).getTime());
			return Convert.toSqlDate(value.toString());
		}
		if (c == java.util.Date.class)
			return ((value instanceof java.util.Date) ? value : Convert
					.toDate(value.toString()));
		if (c == String.class)
			return Convert.toString(value);
		if (c == StringBuffer.class)
			return Convert.toStringBuffer(value);
		if (c == StringBuilder.class)
			return Convert.toStringBuilder(value);
		return null;
	}

	public static <T> List<Class<?>> getGenericParameters(Class<T> baseClass,
			Class<? extends T> childClass) {
		Map resolvedTypes = new HashMap();
		Type type = childClass;
		while (!(getClass(type).equals(baseClass)))
			if (type instanceof Class) {
				type = ((Class) type).getGenericSuperclass();
			} else {
				ParameterizedType parameterizedType = (ParameterizedType) type;
				Class rawType = (Class) parameterizedType.getRawType();
				Type[] actualTypeArguments = parameterizedType
						.getActualTypeArguments();
				typeParameters = rawType.getTypeParameters();
				for (i = 0; i < actualTypeArguments.length; resolvedTypes.put(
						typeParameters[i], actualTypeArguments[(i++)]))
					;
				if (!(rawType.equals(baseClass)))
					type = rawType.getGenericSuperclass();
			}
		Type[] actualTypeArguments;
		Type[] actualTypeArguments;
		if (type instanceof Class)
			actualTypeArguments = ((Class) type).getTypeParameters();
		else
			actualTypeArguments = ((ParameterizedType) type)
					.getActualTypeArguments();
		List typeArgumentsAsClasses = new ArrayList();
		Type[] arrayOfType1;
		int i = (arrayOfType1 = actualTypeArguments).length;
		for (TypeVariable[] typeParameters = 0; typeParameters < i; ++typeParameters) {
			Type baseType = arrayOfType1[typeParameters];
			while (resolvedTypes.containsKey(baseType)) {
				baseType = (Type) resolvedTypes.get(baseType);
			}
			typeArgumentsAsClasses.add(getClass(baseType));
		}
		return typeArgumentsAsClasses;
	}

	private static Class<?> getClass(Type type) {
		if (type instanceof Class)
			return ((Class) type);
		if (type instanceof ParameterizedType)
			return getClass(((ParameterizedType) type).getRawType());
		if (type instanceof GenericArrayType) {
			Type componentType = ((GenericArrayType) type)
					.getGenericComponentType();
			Class componentClass = getClass(componentType);
			if (componentClass != null)
				return Array.newInstance(componentClass, 0).getClass();
		}
		return null;
	}

	protected static <T> ArrayList<T> dispose(ReflectUtil<?> reflect,
			Map<Field, Object> fileldsMap, ArrayList<T> list) {
		reflect = null;
		fileldsMap = null;
		return list;
	}
}