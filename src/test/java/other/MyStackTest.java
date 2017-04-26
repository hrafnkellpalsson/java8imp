package other;

import org.junit.Test;

import java.util.*;

public class MyStackTest {
  @Test
  public final void testPushAll() {
    MyStack myStack = new MyStack();

    Set<Number> numSet = new HashSet<>();
    numSet.add(3);
    numSet.add(5);
    myStack.pushAll(numSet); // Obviously we can push a set of Numbers

    Set<Integer> intSet = new HashSet<>();
    intSet.add(7);
    intSet.add(8);
    myStack.pushAll(intSet); // We can also push a set of Integers because we implemented PECS!
  }

  @Test
  public final void testPopAll() {
    MyStack myStack = new MyStack();

    Set<Number> numSet = new HashSet<>();
    myStack.popAll(numSet, 4); // Obviously we can pop into a set of Numbers

    Set<Object> objSet = new HashSet<>();
    // We can also pop into a set of Objects because we implemented PECS!
    myStack.popAll(objSet, 4);
  }
}
