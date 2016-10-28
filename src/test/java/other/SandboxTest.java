package other;

import org.testng.annotations.Test;

import java.util.*;

public class SandboxTest {
    @Test
    public final void testSandbox() {
        // TODO Why are both of these syntaxes allowed?
        Object[] o1 = new Object[]{};
        Object[] o2 = new Object[1];

        // What are clas literals?
        Class<String> s = String.class;
        Class<List> ls = List.class;
        Class<?> c = Class.class;
    }
}
