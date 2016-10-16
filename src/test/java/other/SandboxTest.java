package other;

import org.testng.annotations.Test;

import java.util.*;

public class SandboxTest {
    @Test
    public final void testSandbox2() {
        Object[] ar = new Number[]{}; // Legal because arrays are covariant
        // List<Object> li = new LinkedList<Number>(); // Illegal because lists (along with other collections) are NOT covariant.
    }
}
