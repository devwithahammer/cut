package com.devwithahammer.cut.asm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.devwithahammer.cut.asm.InterfaceUpdater;
import com.devwithahammer.cut.testclasses.FailOnStaticInitialiseNoInterface;
import com.devwithahammer.cut.testclasses.FailOnStaticInitialiseWithInterface;
import com.devwithahammer.cut.testclasses.IReturnValue;

public class InterfaceUpdaterTest {
	
	@Test
	public void testNotImplemented() {
		InterfaceUpdater updater = 
				InterfaceUpdater.getUpdater(FailOnStaticInitialiseNoInterface.class, IReturnValue.class);
		
		String [] interfaces = updater.getInterfaces(new String[]{});
		assertEquals(1, interfaces.length);
		assertEquals("com/devwithahammer/cut/testclasses/IReturnValue", interfaces[0]);
	}
	
	@Test
	public void testImplemented() {
		InterfaceUpdater updater = 
				InterfaceUpdater.getUpdater(FailOnStaticInitialiseWithInterface.class, IReturnValue.class);
		
		String [] interfaces = updater.getInterfaces(new String[]{});
		assertEquals(0, interfaces.length);
	}

}
