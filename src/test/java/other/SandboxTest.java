package other;

import org.testng.annotations.Test;
import java.io.IOException;
import java.io.PrintStream;

public class SandboxTest {
    @Test
    public final void testSandbox() throws IOException {
        PrintStream out = System.out;
    }
}
