package com.devwithahammer.cut.testclasses;

public class FailOnStaticVariableInitialiseNoInterfaceConstructor {
	
	private int returnValue;
	private String returnStringValue;
	
	public FailOnStaticVariableInitialiseNoInterfaceConstructor(String value) {}

	static {
		if (true) throw new RuntimeException();
	}

	public int getReturnValue() {
		return returnValue;
	}

	public String getReturnStringValue() {
		return returnStringValue;
	}
}
