package com.devwithahammer.cut.asm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class InterfaceUpdater {
	
	private InterfaceUpdater(){};

	String[] getInterfaces(String[] interfaces) {
		return interfaces;
	}

	static InterfaceUpdater getUpdater(Class<?> inputClass, final Class<?> returnClass) {
		
		if (returnClass.isAssignableFrom(inputClass)) {
			return new InterfaceUpdater();
		}
		
		return new InterfaceUpdater() {
			public String[] getInterfaces(String[] interfaces) {
				List<String> newInterfaces = new ArrayList<String>(Arrays.asList(interfaces));
				newInterfaces.add(returnClass.getName().replace(".", "/"));
				return newInterfaces.toArray(new String[newInterfaces.size()]);
			}
		};
	}

}
