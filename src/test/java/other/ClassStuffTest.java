package other;

import org.junit.Assert;
import org.junit.Test;

public class ClassStuffTest {

  @Test
  public void testStuff()
      throws ClassNotFoundException, IllegalAccessException, InstantiationException {
    // Ways to construct variables of class type.
    String fqn = "java.lang.String";

    // 1. Using a class literal
    Class<String> a = String.class;
    Assert.assertEquals(fqn, a.getName());

    // 2. Dynamically get runtime class of variable
    Object str = "mystring";
    Class<?> b0 = str.getClass(); // Type erasure forces a wildcard return type
    @SuppressWarnings("unchecked") // We know that the runtime type of object str is String.
        Class<String> b1 = (Class<String>) b0;
    Assert.assertEquals(fqn, b1.getName());

    // 3. Using reflection.
    // Uses the class loader of the calling code behind the scenes, also performs initialization on the retrieved
    // class. The more general form Class.forName(String, boolean, ClassLoader) allows us to choose whether
    // initialization occurs and what class loader to use.
    Class<?> c0 = Class.forName(fqn); // Type erasure forces a wildcard return type
    @SuppressWarnings("unchecked") // We know that c0 is of type Class<String> because of the way it was constructed
        Class<String> c1 = (Class<String>) c0;
    Assert.assertEquals(fqn, c1.getName());

    // 4. Using class loaders.
    // We have to specify a class loader, initialization not performed, instead it happen when the class is first used.
    Class<?> d0 = ClassLoader.getSystemClassLoader()
        .loadClass(fqn); // Type erasure forces a wildcard return type
    @SuppressWarnings("unchecked") // We know that d0 is of type Class<String> because of the way it was constructed
        Class<String> d1 = (Class<String>) d0;
    Assert.assertEquals(fqn, d1.getName());

    // We can't use the Class constructor to create Class objects, it's private (plus it needs an argument of type
    // ClassLoader). We could probably do it by using reflection to access its private constructor though.
    // Class<String> sc = new Class<String>(); - Illegal!

    Class<String> sc = String.class;
    String s = sc.newInstance();
  }
}
