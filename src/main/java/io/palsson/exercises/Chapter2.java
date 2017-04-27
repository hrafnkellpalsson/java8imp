package io.palsson.exercises;

import com.google.common.base.Stopwatch;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.KeySetView;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

class Chapter2 {
  private final static PrintStream out = System.out;

  /**
   * Write a parallel version of the for loop in Section 2.1, "From Iteration to Stream Operations,"
   * on page 22. Obtain the number of processors. Make that many separate threads, each working on a
   * segment of the list, and total up the results as they come in. (You don't want the threads to
   * update a single counter. Why?)
   */
  static long ex1(String fileName) throws InterruptedException, ExecutionException {
    final int longWordLength = 13;
    final List<String> words = Helper.getBookWords(fileName);
    final int numCores = Runtime.getRuntime().availableProcessors();
    out.format("numCores=%s\n", numCores);

    Stopwatch watch = Stopwatch.createStarted();
    final int chunkSize = words.size() / numCores;
    List<List<String>> chunks = new ArrayList<>(numCores);
    int fromIndex, toIndex;
    for (int i = 0; i < numCores - 1; i++) {
      // Remember, fromIndex is inclusive, toIndex is exclusive
      fromIndex = i * chunkSize;
      toIndex = (i + 1) * chunkSize;
      chunks.add(words.subList(fromIndex, toIndex));
    }
    fromIndex = (numCores - 1) * chunkSize;
    toIndex = words.size();
    chunks.add(words.subList(fromIndex, toIndex));

    Set<Callable<Long>> tasks = new HashSet<>();
    ExecutorService pool = Executors.newFixedThreadPool(numCores);
    for (List<String> chunk : chunks) {
      Callable c = () -> chunk.stream()
          .filter(w -> w.length() >= longWordLength)
          .count();
      tasks.add(c);
    }
    List<Future<Long>> futures = pool.invokeAll(tasks);

    long count = 0;
    for (Future<Long> future : futures) {
      count += future.get();
    }
    watch.stop();

    pool.shutdown();
    pool.awaitTermination(5, TimeUnit.SECONDS);

    out.format("Parallel counting of long words took %sms\n",
        watch.elapsed(TimeUnit.MILLISECONDS));

    return count;

    // We don't want the threads to update a single counter because that would result in a race
    // condition.
  }

  static long singleThreadedCounting(String filename) {
    final int longWordLength = 13;
    final List<String> words = Helper.getBookWords(filename);

    Stopwatch watch = Stopwatch.createStarted();
    long count = words.stream()
        .filter(w -> w.length() >= longWordLength)
        .count();

    watch.stop();
    out.format("Single threaded counting of long words took %sms\n",
        watch.elapsed(TimeUnit.MILLISECONDS));

    return count;
  }
  /**
   * Verify that asking for the first five long words does not call the filter method once the fifth
   * long word has been found. Simply log each method call.
   */
  static void ex2() {
    final int longWordLength = 6;
    final int numWords = 5;
    List<String> longWords = Helper.getBookWords("alice.txt")
        .stream()
        .peek(out::println)
        .filter(w -> w.length() >= longWordLength)
        .limit(numWords)
        .collect(Collectors.toList());
  }

  /**
   * Measure the difference when counting long words with a parallelStream instead of a stream. Call
   * System.nanoTime before and after the call, and print the difference. Switch to a larger
   * document (such as War and Peace) if you have a fast computer.
   */
  static void ex3() throws IOException {
    List<String> words = Helper.getBookWords("warAndPeace.txt");
    final double nanoToMilli = 1000000;
    final int longWordLength = 13;
    long t1, t2, t3, t4;
    long numWords;

    t1 = System.nanoTime();
    numWords = words.stream().filter(w -> w.length() >= longWordLength).count();
    t2 = System.nanoTime();
    out.format("Time for single threaded operation: %.0fms\n", (t2 - t1) / nanoToMilli);

    t3 = System.nanoTime();
    numWords = words.stream().parallel().filter(w -> w.length() >= longWordLength).count();
    t4 = System.nanoTime();
    out.format("Time for parallel operation: %.0fms\n", (t4 - t3) / nanoToMilli);
  }

  /**
   * Suppose you have an array int[] values = { 1, 4, 9, 16 }. What is Stream.of(values)? How do you
   * get a stream of int instead?
   */
  static void ex4() {
    int[] values = {1, 4, 9, 16};
    Stream<Object> objStream = Stream.of(values);
    IntStream intStream = IntStream.of(values);
  }

  /**
   * Using Stream.iterate, make an infinite stream of random numbers - not by calling Math.random
   * but by directly implementing a linear congruential generator. In such a generator, you start
   * with x_0 = seed and then produce x_n+1 = (a * x_n + c) % m, for appropriate values of , c,
   * and m. You should implement a method with parameters a, c, m, and seed that yields a
   * Stream<Long>. Try out a = 25214903917, c = 11, and m = 2 ^ 48.
   */
  static void ex5() {
    long seed = 8;
    long a = 25214903917L;
    long c = 11;
    long m = 2 ^ 48;
    Stream<Long> stream = LongStream.iterate(seed, x -> (a * x + c) % m).boxed();
    stream.limit(5).forEach(out::println);
  }

  /**
   * The characterStream method in Section 2.3 on page 25 was a bit clumsy. Write a stream-based
   * one-liner instead.
   */
  static void ex6() {
    String s = "montypython";
    Stream<Character> stream;

    // Clumsy implementation from book.
    List<Character> result = new ArrayList<>();
    for (char c : s.toCharArray()) {
      result.add(c);
    }
    stream = result.stream();
    stream.forEach(c -> out.format(" %s", c));
    out.println();

    // Better implementation.
    stream = IntStream.range(0, s.length()).map(s::charAt).mapToObj(c -> (char) c);
    stream.forEach(c -> out.format(" %s", c));
    out.println();

    // Even better.
    stream = s.chars().mapToObj(c -> (char) c);
    stream.forEach(c -> out.format(" %s", c));
    out.println();
  }

  /**
   * Your manager asks you to write a method public static <T> boolean isFinite(Steam<T> stream).
   * Why isn't that such a good idea? Go ahead and write in anyway.
   */
  static void ex7() {
    // This is not a good idea because the method will never return if the stream is infinite.
    // So a test running this method would never return - hence, no test for this method :)
    Stream<Character> infiniteStream = Stream.generate(() -> 'a');
    boolean isFinite = isFinite(infiniteStream);
  }

  private static <T> boolean isFinite(Stream<T> stream) {
    stream.count();
    return true;
  }

  /**
   * Write a method public static <T> Stream<T> zip(Stream<T> first, Stream<T> second) that
   * alternates elements from the stream first and second, stopping when one of them runs out of
   * elements.
   */
  static void ex8() {
    // If we allow one or both streams to be infinite this seems to be somewhat involved.
    // We could copy implementation from protonpack library,
    // see http://stackoverflow.com/a/25668784/1728563
    // TODO Implement version for finite streams.
    throw new UnsupportedOperationException();
  }

  /**
   * Join all elements in a Stream<ArrayList<T>> to one ArrayList<T>. Show how to do this with the
   * three forms of reduce.
   */
  static void ex9_1() {
    // Let's first try a simple non generic implementation.
    ArrayList<LocalDate> janDates = IntStream.range(1, 3)
        .mapToObj(i -> LocalDate.of(2016, Month.JANUARY, i))
        .collect(Collectors.toCollection(ArrayList::new));

    ArrayList<LocalDate> febDates = IntStream.range(1, 3)
        .mapToObj(i -> LocalDate.of(2016, Month.FEBRUARY, i))
        .collect(Collectors.toCollection(ArrayList::new));

    ArrayList<LocalDate> marDates = IntStream.range(1, 3)
        .mapToObj(i -> LocalDate.of(2016, Month.MARCH, i))
        .collect(Collectors.toCollection(ArrayList::new));

    // Solution 1
    Stream<ArrayList<LocalDate>> streamDates1 = Stream.of(janDates, febDates, marDates);
    ArrayList<LocalDate> listDates1 = streamDates1
        .reduce((x, y) -> {
          x.addAll(y);
          return x;
        })
        .orElse(new ArrayList<>(Collections.emptyList()));
    // out.println("Solution 1");
    // listDates1.forEach(out::println);

    // Solution 2 - Use an identity so we don't need to deal with Optional
    Stream<ArrayList<LocalDate>> streamDates2 = Stream.of(janDates, febDates, marDates);
    ArrayList<LocalDate> listDates2 = streamDates2
        .reduce(new ArrayList<>(Collections.emptyList()), (x, y) -> {
          x.addAll(y);
          return x;
        });
    out.println("Solution 2");
    listDates2.forEach(out::println);

    // Solution 3 - The combiner function isn't needed here because the argument types are the same
    // as the result types. But we can use it nevertheless.
    Stream<ArrayList<LocalDate>> streamDates3 = Stream.of(janDates, febDates, marDates);
    ArrayList<LocalDate> listDates3 = streamDates3
        .reduce(new ArrayList<>(Collections.emptyList()), (x, y) -> {
          x.addAll(y);
          return x;
        }, (z, w) -> {
          z.addAll(w);
          return z;
        });
    // out.println("Solution 3");
    // listDates3.forEach(out::println);
  }

  // Now let's do the generic version, using only the best way, i.e. Solution 2, from the simplified
  // example above.
  static <T> ArrayList<T> ex9_2(Stream<ArrayList<T>> stream) {
    return stream.reduce(new ArrayList<>(Collections.emptyList()), (x, y) -> {
      x.addAll(y);
      return x;
    });
  }

  // The solution was stolen from here
  // http://stackoverflow.com/questions/23658956/finding-average-using-reduce-and-collect

  /**
   * Write a call to reduce that can be used to compute the average of a Stream<Double>. Why can't
   * you simply compute the sum and divide by count()?
   */
  static void ex10() {
    // First, remember that for a DoubleStream there is an average() method, but that method is
    // only available for primitive streams (as are sum(), max(), min() and summaryStatistics()).

    // So we could cheat and not use reduce()
    Stream<Double> stream1 = DoubleStream.of(1, 2, 3, 4, 5).boxed();
    Double av1 = stream1.mapToDouble(d -> d).average().orElse(0);
    out.println("By cheating we found the average: " + av1);

    // Now no cheating, let's use reduce() as requested.
    // We can't simply compute the sum and divide by count() because we can't consume the stream
    // twice. Note that the reduce we apply here is a terminal operation in the sense that the
    // reduced stream gets reduced to a single value, but nevertheless it's not the last operation
    // in our calculations.
    Stream<Double> stream2 = DoubleStream.of(1, 2, 3, 4, 5).boxed();
    double av2 = stream2.reduce(new ImmutableAverager(0D, 0),
        ImmutableAverager::accumulator, ImmutableAverager::combiner)
        .average();
    out.println("Not cheating we found the average: " + av2);
  }
}



































