package io.palsson.exercises.chapter1;

public interface J {
  void f();
  default void g() {
    System.out.println("I'm default method g on J");
  }
  static void h() {
    System.out.println("I'm static method h on J");
  }
}
