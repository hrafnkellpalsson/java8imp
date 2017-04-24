package other;

// Testing how the different types of inner classes work.
// J. Bloch: A nested class should exist only to serve its enclosing class.
public class OuterClass {

  public void PlainInstanceMethod() {
    System.out.println("Can call enclosing class' PlainInstanceMethod");
  }

  public static void PlainStaticMethod() {
    System.out.println("Can call enclosing class' PlainStaticMethod");
  }

  // J. Bloch paraphrased: Despite syntactic similarity static member classes are very different from nonstatic
  // member classes.
  // J. Bloch: If you declare a member class that does not require access to an enclosing instance, always put the
  // static modifier in its declaration.
  // Like any other static method, a static member class has access to all static methods of the enclosing class.
  // J. Bloch: One common us eof a static member class is as a public helper class, useful only in conjunction with
  // its outer class.
  // J. Bloch: A common use of private static member classes is to represent components of the object represented by
  // their enclosing lass.
  static class StaticClass {

    void meth() {
      System.out.println("In StaticClass.meth");
      PlainStaticMethod(); // We can call a static method of the enclosing class, but not an instance method.
    }
  }

  // A member class is instance specific and has access to any and all methods and members, even the parent's this
  // reference.
  // J. Bloch paraphrased: Nonstatic member classes are used as Adapters and to implement iterators.
  class Clazz {

    void meth() {
      System.out.println("In Clazz.meth");
      PlainInstanceMethod();
      PlainStaticMethod();
      System.out.println(
          "Acceessing name of class through .this reference: " + this.getClass().getName());
    }
  }

  public class PublicClass {

    void meth() {
      System.out.println("In PublicClass.meth");
      PlainInstanceMethod();
      PlainStaticMethod();
      System.out.println(
          "Acceessing name of class through .this reference: " + this.getClass().getName());
    }
  }

  private class PrivateClass {

    void meth() {
      System.out.println("In PrivateClass.meth");
      PlainInstanceMethod();
      PlainStaticMethod();
      System.out.println(
          "Acceessing name of class through .this reference: " + this.getClass().getName());
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
