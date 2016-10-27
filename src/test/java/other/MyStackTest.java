package other;

import org.testng.annotations.Test;

import java.util.*;

public class MyStackTest {
    @Test
    public final void testPush() {
        MyStack myStack = new MyStack();

        Set<Number> numSet = new HashSet<>();
        numSet.add(3);
        numSet.add(5);
        myStack.push(numSet); // Obviously we can push a set of Numbers

        Set<Integer> intSet = new HashSet<>();
        intSet.add(7);
        intSet.add(8);
        myStack.push(intSet); // We can also push a set of Integers because we implemented PECS!
    }

    @Test
    public final void testPop() {
        MyStack myStack = new MyStack();

        Set<Number> numSet = new HashSet<>();
        Set<? super Number> popped = myStack.pop(numSet, 4); // Obviously we can pop into a set of Numbers

        Set<Object> objSet = new HashSet<>();
        Set<? super Number> poppedObj = myStack.pop(objSet, 4); // We can also pop into a set of Objects because we implemented PECS!
    }
}
