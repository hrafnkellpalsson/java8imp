package excercises;

import org.testng.annotations.Test;

import java.io.IOException;

public class Chapter2Test {
    @Test
    public void testEx1() {
        Chapter2 ch2 = new Chapter2();
        ch2.ex1();
    }

    @Test
    public void testEx2() {
        Chapter2 ch2 = new Chapter2();
        ch2.ex2();
    }

    @Test
    public void testEx3() throws IOException {
        Chapter2 ch2 = new Chapter2();
        ch2.ex3();
    }
}
