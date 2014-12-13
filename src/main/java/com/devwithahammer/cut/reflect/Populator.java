package com.devwithahammer.cut.reflect;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

public class Populator {
	
	public void populate(Class<?> clazz, Object instance, Map<String, Object> values) {
		for (Entry<String, Object> entry : values.entrySet()) {
			assignValue(clazz, instance, entry.getKey(), entry.getValue());
		}
	}
	
	private static void assignValue(Class<?> clazz, Object instance, String key, Object value) {
		assignValue(clazz.getSimpleName(), clazz, instance, key, value);
	}

	private static void assignValue(String className, Class<?> clazz, Object instance, String key, Object value) {
		Field f;
		try {
			f = clazz.getDeclaredField(key);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchFieldException e) {
			if (clazz.getSuperclass() == null) 
				throw new RuntimeException(key + " is not a field in class " + className);
			assignValue(className, clazz.getSuperclass(), instance, key, value);
			return;
		}
		
		boolean accessible = f.isAccessible();
		
		f.setAccessible(true);
		
		try {
			f.set(instance, value);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		
		f.setAccessible(accessible);
	}
}
