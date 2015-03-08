Cut library is for the sole purpose of creating classes-under-test in legacy code. Only to be used where
there is an impediment to creating the class-under-test such as resource creation in the constructor
or during static initialisation.

Cut deals with two specific situations: Exceptions in constructors and exceptions in static initialisation

## Exceptions in constructors
 
This is a simple case of passing the type and getting an instance (created using objensis).
 
Say we have the following class  

```java
public class Foo {
    private Resource resource;
 
    public Foo() {
 	       Resource resource = createResource(); //Under test circumstances this throws an Exception.
    }
}
```

Then, to create a test instance of Foo you do the following 

 ```java
Foo testFoo = Cut.create(Foo.class);
```

If, in addition, you want to initialise some of the fields, you pass in a map of field names to values. 
So in the above example you would do something like
```java
Resource mockResource = new MockResource();

Map<String, Object> values = new HashMap<String, Object>();
values.put("resource", mockResource);

Foo testFoo = Cut.create(Foo.class, values);
```
## Exceptions in Static Initialisation

Here you need to pass in the interface of the instance you want returned.

So, say you had the following class

```java
public class Foo {
     private static Resource resource = createResource(); //Under test circumstances this throws an Exception.
 
    public ResourceResult useResource() {
        //use the resource in some way
         
         return resource.getResult();
     }
 }
```

and you wanted to test the <pre class="code"><code class="java">useResource()</code></pre> method you 
would create the following interface
```java
interface TestFoo {
     ResourceResult useResource();
}
```

and create an instance as follows
```java
TestFoo testFoo = Cut.create(Foo.class, TestFoo.class);
```

If the class already implements the interface that you want to use for test purposes then you can also
pass that in. So, say you had.
```java
public interface IFoo {
    ResourceResult useResource();
}

public class Foo implements IFoo {
    private static Resource resource = createResource(); //Under test circumstances this throws an Exception.

    public ResourceResult useResource() {
       //use the resource in some way
       
       return resource.getResult();
    }
}
```
 
Then you could create an instance as follows
```java
IFoo testFoo = Cut.create(Foo.class, IFoo.class);
```

Also, as with the constructor issues instantiation, you can pass in a map of fields that you want initialised.
