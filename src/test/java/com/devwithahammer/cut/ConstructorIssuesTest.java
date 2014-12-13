package com.devwithahammer.cut;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.devwithahammer.cut.Cut;

public class ConstructorIssuesTest {
	@Test
	public void testCreate() {
		FailOnCreate f = Cut.create(FailOnCreate.class);
		assertEquals(f.getReturnValue(), 0);
	}
	
	@Test
	public void testAssign() {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("returnValue", 3);
		values.put("returnStringValue", "stringValue");
		
		FailOnCreate f = Cut.create(FailOnCreate.class, values);
		assertEquals(f.getReturnValue(), 3);
		assertEquals(f.getReturnStringValue(), "stringValue");
	}
	
	@Test
	public void testProtected() {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("returnValue", 3);
		values.put("returnStringValue", "stringValue");
		
		FailOnCreateSubclass f = Cut.create(FailOnCreateSubclass.class, values);
		assertEquals(f.getReturnValue(), 3);
		assertEquals(f.getReturnStringValue(), "stringValue");
	}
	
	@Test
	public void testNotThere() {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("notThere", "notThere");
		
		FailOnCreateSubclass f;
		try {
			f = Cut.create(FailOnCreateSubclass.class, values);
		}
		catch (RuntimeException oe) {
			assertEquals(oe.getMessage(), "notThere is not a field in class FailOnCreateSubclass");
		}
	}
	
	
	
	private static class FailOnCreate {
		
		private final int returnValue;
		
		private final String returnStringValue;
		
		public FailOnCreate() {
			if (true) throw new RuntimeException();
		}
		
		public int getReturnValue() {return returnValue;}
		
		public String getReturnStringValue() {return returnStringValue;}
		
	}
	
	private static class FailOnCreateSubclass extends FailOnCreateSuperclass {
		
		public FailOnCreateSubclass() {
			if (true) throw new RuntimeException();
		}
		
		public int getReturnValue() {return returnValue;}
		
		public String getReturnStringValue() {return returnStringValue;}
		
	}
	
	private static class FailOnCreateSuperclass {
		
		protected final int returnValue;
		
		protected final String returnStringValue;
		
		public FailOnCreateSuperclass() {
			if (true) throw new RuntimeException();
		}
		
	}
}
