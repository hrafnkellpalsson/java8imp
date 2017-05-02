package io.palsson.exercises;

import java.io.File;
import java.io.PrintStream;
import java.util.List;
import static org.junit.Assert.*;

import java.util.Set;
import org.junit.Test;

public class Chapter1Test {
  private static final PrintStream out = System.out;

  @Test
  public void testEx1Sort() {
    Set<Long> threadIds = Chapter1.ex1Sort();
    assertEquals(1, threadIds.size());
  }

  @Test
  public void testEx1ParallelSort() {
    Set<Long> threadIds = Chapter1.ex1ParallelSort();
    assertTrue(threadIds.size() > 1);
  }

  @Test
  public void testEx2() {
    List<File> allDirs = Chapter1.ex2();
    // Test for existence of a couple of subdirectories.
    assertTrue(allDirs.contains(new File("./src/main")));
    assertTrue(allDirs.contains(new File("./src/main/java/io/palsson/exercises")));
    assertTrue(allDirs.contains(new File("./src/test/java/io")));
  }

  @Test
  public void testEx3() {

  }

  @Test
  public void testEx4() {
    String[] paths = new String[9];
    paths[0] = "./src";
    paths[1] = "./src/main";
    paths[2] = "./src/main/java/io/palsson/exercises";
    paths[3] = "./src/main/java/io/palsson/exercises/Chapter1.java";
    paths[4] = "./src/main/java/io/palsson/exercises/Chapter2.java";
    paths[5] = "./src/test";
    paths[6] = "./src/test/java/io/palsson/exercises";
    paths[7] = "./src/test/java/io/palsson/exercises/Chapter1Test.java";
    paths[8] = "./src/test/java/io/palsson/exercises/Chapter2Test.java";

    File[] files = new File[9];
    files[0] = new File(paths[0]);
    files[1] = new File(paths[1]);
    files[2] = new File(paths[2]);
    files[3] = new File(paths[3]);
    files[4] = new File(paths[4]);
    files[5] = new File(paths[5]);
    files[6] = new File(paths[6]);
    files[7] = new File(paths[7]);
    files[8] = new File(paths[8]);
    Chapter1.ex4(files);

    File[] expected = new File[9];
    expected[0] = new File(paths[0]);
    expected[1] = new File(paths[1]);
    expected[2] = new File(paths[2]);
    expected[3] = new File(paths[5]);
    expected[4] = new File(paths[6]);
    expected[5] = new File(paths[3]);
    expected[6] = new File(paths[4]);
    expected[7] = new File(paths[7]);
    expected[8] = new File(paths[8]);

    assertArrayEquals(expected, files);
  }

  @Test
  public void testEx9() {
    Chapter1.ex9();
  }
}
