package io.palsson.exercises;

public interface I {
  void f();
  default void g() {
    System.out.println("I'm default method g on I");
  }
  static void h() {
    System.out.println("I'm static method h on I");
  }
}
