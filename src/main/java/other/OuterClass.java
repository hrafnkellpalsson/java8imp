package other;

// Testing how the different types of inner classes work.
public class OuterClass {
    public void PlainInstanceMethod() {
        System.out.println("Can call enclosing class' PlainInstanceMethod");
    }

    public static void PlainStaticMethod() {
        System.out.println("Can call enclosing class' PlainStaticMethod");
    }

    // Like any other static method, a static member class has access to all static methods of the enclosing class.
    static class StaticClass {
        void meth() {
            System.out.println("In StaticClass.meth");
            PlainStaticMethod(); // We can call a static method of the enclosing class, but not an instance method.
        }
    }

    // A member class is instance specific and has access to any and all methods and members, even the parent's this
    // reference.
    class Clazz {
        void meth() {
            System.out.println("In Clazz.meth");
            PlainInstanceMethod();
            PlainStaticMethod();
            System.out.println("Acceessing name of class through .this reference: " + this.getClass().getName());
        }
    }

    public class PublicClass {
        void meth() {
            System.out.println("In PublicClass.meth");
            PlainInstanceMethod();
            PlainStaticMethod();
            System.out.println("Acceessing name of class through .this reference: " + this.getClass().getName());
        }
    }

    private class PrivateClass {
        void meth() {
            System.out.println("In PrivateClass.meth");
            PlainInstanceMethod();
            PlainStaticMethod();
            System.out.println("Acceessing name of class through .this reference: " + this.getClass().getName());
        }
    }

    public void methodLocalClassExample() {
        class MethodLocalClass {
            public void meth() {
                System.out.println("In MethodLocalClass.meth");
                PlainInstanceMethod();
                PlainStaticMethod();
            }
        }

        // We can only access a method local class in the enclosing method.
        MethodLocalClass mlc = new MethodLocalClass();
        mlc.meth();
    }

    public Clazz ClazzFactory() {
        return new Clazz();
    }
}
