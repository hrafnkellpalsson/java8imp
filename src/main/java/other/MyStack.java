package other;

import java.util.Arrays;
import java.util.Set;

// Working on PECS rule - Producer extends, consumer super
public class MyStack {
    public void push(Number number){
        // Take 'number' and push it on to the stack
    }

    public void push(Set<? extends Number> numbers){
        for (Number n : numbers) {
            // Take 'n' and push it on to the stack
        }
    }

    public Number pop(){
        // return the topmost Number from the stack
        throw new UnsupportedOperationException();
    }

    public Set<? super Number> pop(Set<? super Number> numbers, int n){
        // return a list of numbers from top of stack
        // Go fetch those numbers and shove them into collection
        throw new UnsupportedOperationException();
    }
}
