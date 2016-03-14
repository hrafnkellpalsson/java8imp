package other;

import org.testng.annotations.Test;

public class OuterClassTest {
    @Test
    public void testPackageLocalInner() {
        OuterClass oc = new OuterClass();
        OuterClass.Clazz Clazz;
        Clazz = oc.ClazzFactory(); // We can access the inner class through a factory method...
        Clazz = oc.new Clazz(); // ...or using this special new syntax. J. Bloch paraphrased: This way is rare.
        Clazz.meth();
    }

    // An inner class with the public modifier works just like an inner class with no modifier (package local).
    @Test
    public void testPublicInner() {
        OuterClass oc = new OuterClass();
        OuterClass.PublicClass PublicClass = oc.new PublicClass();
        PublicClass.meth();
    }

    @Test
    public void testStaticInner() {
        // Note that we don't need to instantiate the outer class here. This makes sense, since that is the case
        // for any kind of static member of a class.
        OuterClass.StaticClass StaticClass = new OuterClass.StaticClass();
        StaticClass.meth();
        //Clazz.meth();
    }

    // Obviously can't getSync a hold of the method local class here, because it's only available in the method that
    // defines it.
    @Test
    public void testMethodLocal() {
        OuterClass oc = new OuterClass();
        oc.methodLocalClassExample();
    }

    @Test
    public void testAnonymousInner() {
        // In case of anonymous inner classes, we declare and instantiate them at the same time. Generally they are
        // used whenever you need to override the method of a class or an interface.
        // Note, the right hand side of the expression could be passed directly to a method.
        // I find that you don't need to associate anonymous classes with inner classes, like this class is not defined
        // inside the OuterClass class. And if you pass it as an argument to a method right away (without storing
        // it in a variable first that is) then not thinking of them as inner classes is a simpler mental model I find.
        Object overObj = new Object(){
            @Override
            public boolean equals(Object object) {
                return true;
            }

            boolean siggi() {
                return true;
            }
        };
    }
}
