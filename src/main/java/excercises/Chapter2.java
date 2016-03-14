package excercises;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.*;

public class Chapter2 {
    /**
     * Write a parallel version of the for loop in Section 2.1, "From Iteration to Stream Operations," on page 22.
     * Obtain the number of processors. Make that many separate threads, each working on a segment of the list, and
     * total up the results as they come in. (You don't want the threads to update a single counter. Why?)
     */
    public void ex1() {
        int nProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("nProcessors=" + nProcessors);

        // TODO Implement rest of answer.
    }

    /**
     * Verify that asking for the first five long words does not call the filter method once the fifth long word has
     * been found. Simply log each method call.
     */
    public void ex2() {
        String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
                "incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation " +
                "ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
                "voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non " +
                "proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
        List<String> words = Arrays.asList(loremIpsum.split("[\\P{L}]+"));
        List<String> stream = words.stream()
                .peek(System.out::println)
                .filter(w -> w.length() > 4) // Let's make the criteria for a 'long' word low so we getSync less output.
                .limit(5)
                .collect(Collectors.toList());
    }

    /**
     * Measure the difference when counting long words with a parallelStream instead of a stream. Call System.nanoTime
     * before and after the call, and print the difference. Switch to a larger document (such as War and Peace) if you
     * have a fast computer.
     */
    public void ex3() throws IOException {
        Path path = Paths.get("/Users/hrafnkellpalsson/IdeaProjects/java8imp/src/main/java/excercises/WarAndPeace.txt");
        byte[] bytes = Files.readAllBytes(path);
        String content = new String(bytes, StandardCharsets.UTF_8);
        List<String> words = Arrays.asList(content.split("[\\P{L}]+"));

        double nanoToMilli = 1000000;

        long t1 = System.nanoTime();
        words.stream().filter(w -> w.length() > 12).count();
        long t2 = System.nanoTime();
        System.out.println("Time for single threaded operation: " + (t2 - t1)/nanoToMilli);

        long t3 = System.nanoTime();
        words.stream().parallel().filter(w -> w.length() > 12).count();
        long t4 = System.nanoTime();
        System.out.println("Time for parallel operation: " + (t4 - t3)/nanoToMilli);
    }

    /**
     * Suppose you have an array int[] values = { 1, 4, 9, 16 }. What is Stream.of(values)? How do you getSync a stream of
     * int instead?
     */
    public void ex4() {
        int[] values = { 1, 4 , 9, 16 };
        Stream<Object> objStream = Stream.of(values);
        IntStream intStream = IntStream.of(values); // Naturally IntStream is not a generic interface, since if applies only to ints!

    }

    /**
     * Using Stream.iterate, make an infinite stream of random numbers - not by calling Math.random but by directly
     * implementing a linear congruential generator...
     */
    public void ex5() {
        long seed = 8;
        long a = 25214903917L;
        long c = 11;
        long m = 2^48;
        Stream<Long> stream = LongStream.iterate(seed, x -> (a*x+c)%m).boxed();
        stream.limit(5).forEach(System.out::println);
    }

    /**
     * The characterStream method in Section 2.3 on page 25 was a bit clumsy. Write a stream-based one-liner instead.
     */
    public void ex6() {
        String s = "drumpf";
        Stream<Character> stream;

        // Clumsy implementation from book.
        List<Character> result = new ArrayList<>();
        for (char c : s.toCharArray()) {
            result.add(c);
        }
        stream = result.stream();
        stream.forEach(System.out::println);

        // Better implementation.
        stream = IntStream.range(0, s.length()-1).map(s::charAt).mapToObj(c -> (char) c);
        stream.forEach(System.out::println);
        // Even better
        stream = s.chars().mapToObj(c -> (char)c);
        stream.forEach(System.out::println);
    }

    /**
     * Your manager asks you to write a method public static <T> boolean isFinite(Steam<T> stream). Why isn't that
     * such a good idea? Go ahead and write in anyway.
     */
    public void ex7() {
        // This is not a good idea because the method will never return if the stream is infinite.
        boolean isFinite = isFinite(Stream.generate(() -> "a"));
    }

    public static <T> boolean isFinite(Stream<T> stream) {
        stream.count();
        return true;
    }

    /**
     * Write a method public static <T> Stream<T> zip(Stream<T> first, Stream<T> second) that alternates elements from
     * the stream first and second, stopping when one of them runs out of elements.
     */
    public void ex8() {
        // If we allow one or both streams to be infinite this seems to be somewhat involved.
        // We could copy implementation from potonpack library, see http://stackoverflow.com/a/25668784/1728563
    }

    /**
     * Join all elements in a Stream<ArrayList<T>> to one ArrayList<T>. Show how to do this with the three forms of
     * reduce.
     */
    public void ex9_1() {
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
        // System.out.println("Solution 1");
        // listDates1.forEach(System.out::println);

        // Solution 2 - Use an identity so we don't need to deal with Optional
        Stream<ArrayList<LocalDate>> streamDates2 = Stream.of(janDates, febDates, marDates);
        ArrayList<LocalDate> listDates2 = streamDates2
                .reduce(new ArrayList<>(Collections.emptyList()), (x, y) -> {
                    x.addAll(y);
                    return x;
                });
        System.out.println("Solution 2");
        listDates2.forEach(System.out::println);

        // Solution 3 - The combiner function isn't needed here because the argument types are the same as the result
        // types. But we can use it nevertheless.
        Stream<ArrayList<LocalDate>> streamDates3 = Stream.of(janDates, febDates, marDates);
        ArrayList<LocalDate> listDates3 = streamDates3
                .reduce(new ArrayList<>(Collections.emptyList()), (x, y) -> {
                    x.addAll(y);
                    return x;
                }, (z, w) -> {
                    z.addAll(w);
                    return z;
                });
        // System.out.println("Solution 3");
        // listDates3.forEach(System.out::println);
    }

    // Now let's do the generic version, using only the best way, i.e. Solution 2, from the simplified example above.
    public <T> ArrayList<T> ex9_2(Stream<ArrayList<T>> stream) {
        return stream.reduce(new ArrayList<>(Collections.emptyList()), (x, y) -> {
            x.addAll(y);
            return x;
        });
    }

    // TODO We could try more stuff here, such as not using an immutable Averager.
    // The solution was stolen from here http://stackoverflow.com/questions/23658956/finding-average-using-reduce-and-collect
    /**
     * Write a call to reduce that can be used to compute the average of a Stream<Double>. Why can't you simply compute
     * the sum and divide by count()?
     */
    public void ex10() {
        // Firstly, remember that for a DoubleStream there is an average() method, but that method is only available for
        // primitive streams (as are sum(), max(), min() and summaryStatistics()).

        // So we could cheat and not use reduce()
        Stream<Double> stream1 = DoubleStream.of(1, 2, 3, 4, 5).boxed();
//        DoubleStream dStream  = stream1.mapToDouble(d -> d);
//        OptionalDouble optAv = dStream.average();
        // Or as a one liner with an identity
        Double av1 = stream1.mapToDouble(d -> d).average().orElse(0);
        System.out.println("Cheating: " + av1);

        // Now no cheating, let's use reduce() as requested.
        // We can't simply compute the sum and divide by count() because we can't consume the stream twice.
        // Note that the reduce we apply here is a terminal operation in the sense that the reduced stream gets reduced
        // to a single value, but nevertheless it's not the last operation in our calculations.
        Stream<Double> stream2 = DoubleStream.of(1, 2, 3, 4, 5).boxed();
        double av2 = stream2.reduce(new ImmutableAverager(0D, 0), ImmutableAverager::accumulator, ImmutableAverager::combiner)
            .average();
        System.out.println("Not cheating: " + av2);
    }
}



































