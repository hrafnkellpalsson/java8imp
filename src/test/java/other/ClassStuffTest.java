package other;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ClassStuffTest {
    @Test
    public void testStuff() {
        Class<String> strClassFromLiteral = String.class;
        // Are there other ways to construct class types than using class literals?
        // Yes, through reflection!
        // TODO Can we only construct a class through reflection after having created its through reflection?
        try {
            // Type erasure forces a wildcard return type
            Class<?> strClass = Class.forName("java.lang.String");
            // but the object is still very useful
            Assert.assertEquals("java.lang.String", strClass.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // TODO Any other ways to create variables of the Class type?
        // Not by constructor, it's private (plus it needs an argument of type ClassLoader)
        // Class<String> sc = new Class<String>(); - Illegal!

        try {
            Class<String> sc = String.class;
            String s = sc.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
