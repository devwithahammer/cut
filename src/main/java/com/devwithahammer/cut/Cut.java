package com.devwithahammer.cut;

import java.util.HashMap;
import java.util.Map;

import com.devwithahammer.cut.asm.AsmCut;
import com.devwithahammer.cut.objensis.ObjensisCut;

/**
 * Cut library is for the sole purpose of creating classes-under-test in legacy code. Only to be used where
 * there is an impediment to creating the class-under-test, such as resource creation in the constructor
 * or during static initialisation.
 * 
 * <p>Cut deals with two specific situations: Exceptions in constructors and exceptions in static initialisation</p>
 * 
 * <h1>Exceptions in constructors</h1>
 * 
 * This is a simple case of passing the type and getting an instance (created using objensis).
 * 
 * Say we have the following class
 * 
 * <pre class="code"><code class="java">
 * public class Foo {
 *     private Resource resource;
 * 
 *     public Foo() {
 * 	       Resource resource = createResource(); //Under test circumstances this throws an Exception.
 *     }
 * }
 * </code></pre>
 * 
 * Then, to create a test instance of Foo you do the following 
 * <pre class="code"><code class="java">
 * Foo testFoo = Cut.create(Foo.class);
 * </code></pre>
 * 
 * If, in addition, you want to initialise some of the fields, you pass in a map of field names to values. 
 * So in the above example you would do something like
 * <pre class="code"><code class="java">
 * Resource mockResource = new MockResource();
 * 
 * Map<String, Object> values = new HashMap<String, Object>();
 * values.put("resource", mockResource);
 * 
 * Foo testFoo = Cut.create(Foo.class, values);
 * </code></pre>
 * 
 * <h1>Exceptions in Static Initialisation</h1>
 * 
 * Here you need to pass in the interface of the instance you want returned.
 * 
 * So, say you had the following class
 * 
 * <pre class="code"><code class="java">
 * public class Foo {
 *     private static Resource resource = createResource(); //Under test circumstances this throws an Exception.
 * 
 *     public ResourceResult useResource() {
 *         //use the resource in some way
 *         
 *         return resource.getResult();
 *     }
 * }
 * </code></pre>
 * 
 * and you wanted to test the <pre class="code"><code class="java">useResource()</code></pre> method you 
 * would create the following interface
 * <pre class="code"><code class="java">
 * interface TestFoo {
 *     ResourceResult useResource();
 * }
 * </code></pre>
 * 
 * and create an instance as follows
 * <pre class="code"><code class="java">
 * TestFoo testFoo = Cut.create(Foo.class, TestFoo.class);
 * </code></pre>
 * 
 * If the class already implements the interface that you want to use for test purposes then you can also
 * pass that in. So, say you had.
 * <pre class="code"><code class="java">
 * 
 * public interface IFoo {
 *     ResourceResult useResource();
 * }
 * 
 * public class Foo implements IFoo {
 *     private static Resource resource = createResource(); //Under test circumstances this throws an Exception.
 * 
 *     public ResourceResult useResource() {
 *         //use the resource in some way
 *         
 *         return resource.getResult();
 *     }
 * }
 * </code></pre>
 * 
 * Then you could create an instance as follows
 * <pre class="code"><code class="java">
 * IFoo testFoo = Cut.create(Foo.class, IFoo.class);
 * </code></pre>
 * 
 * Also, as with the constructor issues instantiation, you can pass in a map of fields that you want initialised.
 */
public class Cut {
	
	private static final ObjensisCut objensis = new ObjensisCut();
	
	private static final AsmCut asm = new AsmCut();
	
	/**
     * Use this method when you want to create a class that throws exceptions in its constructor.
     *
     * @param cut The class under test to be created
     * @return instance of class
     */
	public static <T> T create(Class<T> cut) {
		return objensis.create(cut);
	}

	/**
     * Use this method when you want to create a class that throws exceptions in its constructor and
     * initialise some or all of the fields in that class.
     *
     * @param cut The class under test to be created
     * @param values Map of field name to required values
     * @return instance of class
     */
	public static <T> T create(Class<T> cut, Map<String, Object> values) {
		return objensis.create(cut, values);
	}
	
	/**
     * Use this method when you want to create a class that throws exceptions during static initialisation.
     * You pass in an interface to be used as the return type.
     *
     * @param cutType The class under test with errors in static initialisation
     * @param returnType The interface that will actually be used for testing
     * @param values Map of field name to required values
     * @return instance of passed in interface
     */
	public static <T> T create(Class<?> cutType, Class<T> returnType) {	
		return asm.create(cutType, returnType, new HashMap<String, Object>());
	}

	/**
     * Use this method when you want to create a class that throws exceptions during static initialisation
     * and initialise some or all of the fields in that class. You pass in an interface to be used as the 
     * return type.
     *
     * @param cutType The class under test with errors in static initialisation
     * @param returnType The interface that will actually be used for testing
     * @param values Map of field name to required values
     * @return instance of passed in interface
     */
	public static <T> T create(Class<?> cutType, Class<T> returnType, Map<String, Object> values) {
		return asm.create(cutType, returnType, values);
	}
}
