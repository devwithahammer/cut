package com.devwithahammer.cut.objensis;

import java.util.HashMap;
import java.util.Map;

import org.objenesis.ObjenesisStd;

import com.devwithahammer.cut.reflect.Populator;

public class ObjensisCut {
	
	private final Populator populator = new Populator();
	
	private final ObjenesisStd objenesis = new ObjenesisStd(false);

	public <T> T create(Class<T> clazz) {
		return create(clazz, new HashMap<String, Object>());
	}

	public <T> T create(Class<T> clazz, Map<String, Object> values) {
		T instance = objenesis.newInstance(clazz);
		
		populator.populate(clazz, instance, values);
		
		return instance;
	}
}
