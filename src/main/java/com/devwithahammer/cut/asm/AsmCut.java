package com.devwithahammer.cut.asm;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objenesis.ObjenesisStd;

import com.devwithahammer.cut.objensis.ObjensisCut;
import com.devwithahammer.cut.reflect.Populator;

public class AsmCut {
	
	private final Populator populator = new Populator();
	private final ObjensisCut objensis = new ObjensisCut();
	
	public <T> T create(Class<?> inputClass, Class<T> returnClass) {	
		return create(inputClass, returnClass, new HashMap<String, Object>());
	}

	public <T> T create(Class<?> inputClass, Class<T> returnClass, Map<String, Object> values) {
		
		byte[] newClassBytes = getNewClassBytesFromAsm(inputClass, returnClass);
		
		Class<?> newClass = getNewClass(inputClass, newClassBytes);
		
		Object instance = objensis.create(newClass);
		
		populator.populate(newClass, newClass.cast(instance), values);
		
		T out = returnClass.cast(instance);
		
		return out;
	}

	private <T> byte[] getNewClassBytesFromAsm(Class<?> inputClass, Class<T> returnClass) {
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		
		ClassVisitor cl = new CutClassVisitor(Opcodes.ASM4, cw, InterfaceUpdater.getUpdater(inputClass, returnClass));
		
		ClassReader classReader = getClassReader(inputClass);
		
		classReader.accept(cl, 0);
		
		byte [] newClassBytes = cw.toByteArray();
		
		return newClassBytes;
	}

	private ClassReader getClassReader(Class<?> inputClass) {
		String className = inputClass.getName();
		String classAsPath = className.replace('.', '/') + ".class";
		InputStream in = inputClass.getClassLoader().getResourceAsStream(classAsPath);
		
		ClassReader classReader;
		try {
			classReader = new ClassReader(in);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return classReader;
	}
	
	private Class<?> getNewClass(Class<?> inputClass, byte[] newClassBytes) {
		ClassLoader newClassLoader = new CutClassLoader(newClassBytes, inputClass.getName());
		
		Class<?> newClass;
		try {
			newClass = newClassLoader.loadClass(inputClass.getName());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		return newClass;
	}
	
	private final static class CutClassLoader extends ClassLoader {
		private final byte [] newClass;
		private final String className;
		
		private CutClassLoader(byte [] newClass, String className) {
			super(AsmCut.class.getClassLoader());
			
			this.newClass = newClass;
			this.className = className;
		}
		
		public Class<?> loadClass(String name) throws ClassNotFoundException {
			if(!className.equals(name))
	            return super.loadClass(name);
			return defineClass(name, newClass, 0, newClass.length);
			
		}
	}
}
