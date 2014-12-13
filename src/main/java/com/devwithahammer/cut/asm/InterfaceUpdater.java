package com.devwithahammer.cut.asm;

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
				String [] newInterfaces = new String[interfaces.length + 1];
				
				for (int i = 0; i < interfaces.length; i++) {
					newInterfaces[i] = interfaces[i];
				}
				newInterfaces[interfaces.length] = returnClass.getName().replace(".", "/");
				return newInterfaces;
			}
		};
	}

}
