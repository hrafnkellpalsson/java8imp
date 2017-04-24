package other;

import org.testng.annotations.Test;
import java.io.IOException;
import java.io.PrintStream;

public class SandboxTest {

  private static PrintStream out = System.out;

  @Test
  public final void testSandbox() throws IOException {
    out.println(7 / 2);
    out.println(Math.floorDiv(7, 2));
    out.println(-7 / 2);
    out.println(Math.floorDiv(-7, 2));
  }
}
