package com.devwithahammer.cut;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.devwithahammer.cut.Cut;
import com.devwithahammer.cut.testclasses.FailOnStaticInitialiseNoInterface;
import com.devwithahammer.cut.testclasses.FailOnStaticInitialiseWithInterface;
import com.devwithahammer.cut.testclasses.FailOnStaticVariableInitialiseNoInterface;
import com.devwithahammer.cut.testclasses.FailOnStaticVariableInitialiseWithInterface;
import com.devwithahammer.cut.testclasses.IReturnValue;

public class StaticInitialisationTest {

	@Test
	public void testWithInterfaceCreate() throws Exception {
		IReturnValue rv = Cut.create(FailOnStaticInitialiseWithInterface.class, IReturnValue.class);
		assertEquals(rv.getReturnValue(), 0);
	}
	
	@Test
	public void testWithInterfaceAssign() throws Exception {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("returnValue", 3);
		values.put("returnStringValue", "stringValue");
		
		IReturnValue rv = Cut.create(FailOnStaticInitialiseWithInterface.class, IReturnValue.class, values);
		assertEquals(rv.getReturnValue(), 3);
		assertEquals(rv.getReturnStringValue(), "stringValue");
	}
	
	@Test
	public void testNoInterfaceCreate() throws Exception {
		IReturnValue rv = Cut.create(FailOnStaticInitialiseNoInterface.class, IReturnValue.class);
		assertEquals(rv.getReturnValue(), 0);
	}
	
	@Test
	public void testNoInterfaceAssign() throws Exception {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("returnValue", 3);
		values.put("returnStringValue", "stringValue");
		
		IReturnValue rv = Cut.create(FailOnStaticInitialiseNoInterface.class, IReturnValue.class, values);
		assertEquals(rv.getReturnValue(), 3);
		assertEquals(rv.getReturnStringValue(), "stringValue");
	}
	
	@Test
	public void testWithInterfaceStaticVariableCreate() throws Exception {
		IReturnValue rv = Cut.create(FailOnStaticVariableInitialiseWithInterface.class, IReturnValue.class);
		assertEquals(rv.getReturnValue(), 0);
	}
	
	@Test
	public void testWithInterfaceStaticVariableAssign() throws Exception {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("returnValue", 3);
		values.put("returnStringValue", "stringValue");
		
		IReturnValue rv = Cut.create(FailOnStaticVariableInitialiseWithInterface.class, IReturnValue.class, values);
		assertEquals(rv.getReturnValue(), 3);
		assertEquals(rv.getReturnStringValue(), "stringValue");
	}
	
	@Test
	public void testNoInterfaceStaticVariableCreate() throws Exception {
		IReturnValue rv = Cut.create(FailOnStaticVariableInitialiseNoInterface.class, IReturnValue.class);
		assertEquals(rv.getReturnValue(), 0);
	}
	
	@Test
	public void testNoInterfaceStaticVariableAssign() throws Exception {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("returnValue", 3);
		values.put("returnStringValue", "stringValue");
		
		IReturnValue rv = Cut.create(FailOnStaticVariableInitialiseNoInterface.class, IReturnValue.class, values);
		assertEquals(rv.getReturnValue(), 3);
		assertEquals(rv.getReturnStringValue(), "stringValue");
	}
	
}
