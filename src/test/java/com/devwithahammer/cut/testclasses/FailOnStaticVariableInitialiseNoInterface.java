package com.devwithahammer.cut.testclasses;

public class FailOnStaticVariableInitialiseNoInterface {
	
	private int returnValue;
	private String returnStringValue;

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