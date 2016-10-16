package other;

import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

public class MyStackTest {
    @Test
    public final void testSandbox1() {
        MyStack myStack = new MyStack();

        Set<Number> mySet = new HashSet<>();
        mySet.add(3);
        mySet.add(5);
        myStack.push(mySet);

        Set<Integer> myIntSet = new HashSet<>();
        myIntSet.add(7);
        myIntSet.add(8);
        myStack.push(myIntSet);

        Set<Number> empty = new HashSet<>();
        Set<? super Number> popped = myStack.pop(empty, 4);

        Set<Object> emptyObj = new HashSet<>();
        Set<? super Number> poppedObj = myStack.pop(emptyObj, 4);
    }
}
