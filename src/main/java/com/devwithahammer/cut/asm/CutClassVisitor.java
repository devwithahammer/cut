package com.devwithahammer.cut.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class CutClassVisitor extends ClassVisitor {
	
	private final InterfaceUpdater interfaceUpdater;

	public CutClassVisitor(int api, ClassWriter cw, InterfaceUpdater interfaceUpdater) {
		super(api, cw);
		this.interfaceUpdater = interfaceUpdater;
	}
	
	@Override
	public void visit(int version, int access, String name, String signature, String superName, String [] interfaces){		
		interfaces = interfaceUpdater.getInterfaces(interfaces);	
		super.visit(version, access, name, signature, superName, interfaces);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		if (!name.startsWith("<clinit>")) {
			return super.visitMethod(access, name, desc, signature, exceptions);
		}
		return null;
	}
}
