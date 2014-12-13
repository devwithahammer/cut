package com.devwithahammer.cut.testclasses;

public class FailOnStaticVariableInitialiseWithInterface implements IReturnValue {
	private static int returnValue = initialiseReturnValue();
	private static String returnStringValue = initialiseStringReturnValue();

	public int getReturnValue() {
		return returnValue;
	}

	private static String initialiseStringReturnValue() {
		if (true) throw new RuntimeException();
		return null;
	}

	private static int initialiseReturnValue() {
		if (true) throw new RuntimeException();
		return 0;
	}

	public String getReturnStringValue() {
		return returnStringValue;
	}
}