package excercises;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
                .filter(w -> w.length() > 4) // Let's make the criteria for a 'long' word low so we get less output.
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
     * Suppose you have an array int[] values = { 1, 4, 9, 16 }. What is Stream.of(values)? How do you get a stream of
     * int instead?
     */
    public void ex4() {
        
    }
}