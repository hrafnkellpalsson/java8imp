package io.palsson.exercises;

import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.Test;
import org.junit.Assert;

// We sometimes call methods in tests twice to allow the JVM to warm up and thereby get more
// realistic runtimes.
public class Chapter2Test {
  private static PrintStream out = System.out;

  @Test
  public void testEx1() throws ExecutionException, InterruptedException {
    final String filename = "warAndPeace.txt";
    long actual, expected;
    actual = Chapter2.ex1(filename);
    actual = Chapter2.ex1(filename);
    expected = Chapter2.singleThreadedCounting(filename);
    expected = Chapter2.singleThreadedCounting(filename);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testEx2() {
    Chapter2.ex2();
  }

  @Test
  public void testEx3() throws IOException {
    Chapter2.ex3();
    Chapter2.ex3();
  }

  @Test
  public void testEx5() throws IOException {
    Chapter2.ex5();
  }

  @Test
  public void testEx6() throws IOException {
    Chapter2.ex6();
  }

  @Test
  public void testEx9_1() {
    Chapter2.ex9_1();
  }

  @Test
  public void testEx9_2() {
    ArrayList<LocalDate> janDates = IntStream.range(1, 3)
        .mapToObj(i -> LocalDate.of(2016, Month.JANUARY, i))
        .collect(Collectors.toCollection(ArrayList::new));

    ArrayList<LocalDate> febDates = IntStream.range(1, 3)
        .mapToObj(i -> LocalDate.of(2016, Month.FEBRUARY, i))
        .collect(Collectors.toCollection(ArrayList::new));

    ArrayList<LocalDate> marDates = IntStream.range(1, 3)
        .mapToObj(i -> LocalDate.of(2016, Month.MARCH, i))
        .collect(Collectors.toCollection(ArrayList::new));

    Stream<ArrayList<LocalDate>> streamDates = Stream.of(janDates, febDates, marDates);

    ArrayList<LocalDate> listDates = Chapter2.ex9_2(streamDates);
    listDates.forEach(out::println);
  }

  @Test
  public void testEx10() {
    Chapter2.ex10();
  }
}
