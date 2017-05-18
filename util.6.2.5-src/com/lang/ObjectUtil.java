/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.lang;

import com.lang.annotation.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ObjectUtil {
	public static <T> T clone(T t) {
		if (t == null)
			return null;
		try {
			Class cls = t.getClass();
			if (Modifier.isAbstract(cls.getModifiers()))
				return null;
			Object tt = cls.newInstance();
			Field[] fields = cls.getDeclaredFields();
			if (fields.length == 0)
				return tt;
			for (int i = 0; i < fields.length; ++i) {
				Field field = fields[i];
				if (field.isAnnotationPresent(Transient.class))
					continue;
				field.setAccessible(true);
				int m = field.getModifiers();
				if (Modifier.isStatic(m))
					continue;
				if (Modifier.isFinal(m))
					continue;
				try {
					field.set(tt, field.get(t));
				} catch (Exception localException1) {
				}
			}
			return tt;
		} catch (Exception e) {
		}
		return null;
	}

	public static <T> T getFirst(Collection<T> c) {
		if (c == null)
			return null;
		if (c.isEmpty())
			return null;
		if (c instanceof List) {
			List list = (List) c;
			return list.get(0);
		}

		return c.iterator().next();
	}

	public static <K, V> Map.Entry<K, V> getFirst(Map<K, V> map) {
		return (((map == null) || (map.isEmpty())) ? null : (Map.Entry) map
				.entrySet().iterator().next());
	}
}