package other;

// Testing how the different types of inner classes work.
// J. Bloch: A nested class should exist only to serve its enclosing class.
class OuterClass {

  private void plainInstanceMethod() {
    System.out.println("Can call enclosing class' plainInstanceMethod");
  }

  static void plainStaticMethod() {
    System.out.println("Can call enclosing class' plainStaticMethod");
  }

  // Bloch paraphrased: Despite syntactic similarity static member classes are very different
  // from non-static member classes.
  // Bloch: If you declare a member class that does not require access to an enclosing instance,
  // always put the static modifier in its declaration.
  // Like any other static method, a static member class has access to all static methods of the
  // enclosing class.
  // Bloch: One common us eof a static member class is as a public helper class, useful only in
  // conjunction with its outer class.
  // Bloch: A common use of private static member classes is to represent components of the object
  // represented by their enclosing lass.
  static class StaticClass {
    void method() {
      System.out.println("In StaticClass.method");
      // We can call a static method of the enclosing class, but not an instance method.
      plainStaticMethod();
    }
  }

  // A member class is instance specific and has access to any and all methods and members, even the
  // parent's this reference. Bloch paraphrased: Non-static member classes are used as Adapters and
  // to implement iterators.
  class Clazz {

    void method() {
      System.out.println("In Clazz.method");
      plainInstanceMethod();
      plainStaticMethod();
      System.out.println(
          "Accessing name of class through .this reference: " + this.getClass().getName());
    }
  }

  class PublicClass {
    void method() {
      System.out.println("In PublicClass.method");
      plainInstanceMethod();
      plainStaticMethod();
      System.out.println(
          "Accessing name of class through .this reference: " + this.getClass().getName());
    }
  }

  private class PrivateClass {
    void method() {
      System.out.println("In PrivateClass.method");
      plainInstanceMethod();
      plainStaticMethod();
      System.out.println(
          "Accessing name of class through .this reference: " + this.getClass().getName());
    }
  }

  void methodLocalClassExample() {
    class MethodLocalClass {
      private void method() {
        System.out.println("In MethodLocalClass.method");
        plainInstanceMethod();
        plainStaticMethod();
      }
    }

    // We can only access a method local class in the enclosing method.
    MethodLocalClass mlc = new MethodLocalClass();
    mlc.method();
  }

  Clazz clazzFactory() {
    return new Clazz();
  }
}
