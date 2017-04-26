package other;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class ArrayAndListCreation {
  private static PrintStream out = System.out;

  static void arrayCreation() {
    // Initializing array and explicitly setting size is not allowed.
    // Object[] illegal = new Object[1]{1};

    // Initializing array right away and implicitly setting its size.
    Object[] cumbersome = new Object[]{1, 2};
    // An even better way is simply
    int[] sleek = {1, 2};

    // If we don't populate the initializer the size is set to zero right away.
    Object[] nothing = new Object[]{};
    // We can also explicitly set the array size to 0.
    Object[] zero = new Object[0];

    // If we set the array size but don't initialize it the array is filled with ?
    // Depends on the type. For int it's 0, for Integer it's null, etc.
    int[] emptyInt = new int[4];
    IntStream.of(emptyInt).forEach(e -> out.format("%s ", e));
    out.println();
    Integer[] emptyInteger = new Integer[4];
    Stream.of(emptyInteger).forEach(e -> out.format("%s ", e));
  }

  static void listCreation() {
    // For comparison, with lists
    List<String> places = Arrays.asList("Buenos Aires", "Córdoba", "La Plata");
    // Or even
    List<String> str = Stream.of("xyz", "abc").collect(Collectors.toList());
  }
}
