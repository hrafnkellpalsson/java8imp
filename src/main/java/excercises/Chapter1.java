package excercises;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class Chapter1 {
  /**
   * Using the listFiles(FileFilter) and isDirectory methods of the java.io.File class, write a
   * method that returns all subdirectories of a given directory. Use a lambda expression instead of
   * a FileFilter object. Repeat with a method reference.
   */
  void ex2() {
    // Recursive lambdas are not supported, so that's not an option.
    File file = new File("/Users/hrafnkellpalsson/Downloads");
    go(file);
  }

  private void go(File file) {
    // File[] dirs = file.listFiles(f -> f.isDirectory());
    File[] dirs = file.listFiles(File::isDirectory);
    for (File dir : dirs) {
      System.out.println(dir);
      go(dir);
    }
  }

  /**
   * Given an array of File objects, sort it so that the directories come before the files, and
   * within each group, elements are sorted by path name. Use a lambda expression, not a
   * Comparator.
   */
  void ex4() {
    final String base = "/Users/hrafnkellpalsson/Downloads";
    File[] files = new File[9];
    files[0] = new File(base + "/what");
    files[1] = new File(base + "/what/romans.txt");
    files[2] = new File(base + "/what/greek.txt");
    files[3] = new File(base + "/what/who");
    files[4] = new File(base + "/what/who/pericles.txt");
    files[5] = new File(base + "/what/who/cato.txt");
    files[6] = new File(base + "/what/when");
    files[7] = new File(base + "/what/when/BCE.txt");
    files[8] = new File(base + "/what/when/CE.txt");

    // We could have converted the array to a list and used the sort() method defined on the list
    // interface.
    // List<File> fs = Arrays.asList(files);

    Comparator<? super File> comparator = (f1, f2) -> {
      if (f1.isDirectory() && f2.isDirectory()) {
        return f1.compareTo(f2);
      }

      if (f1.isDirectory()) {
        return -1;
      }

      if (f2.isDirectory()) {
        return 1;
      }

      return f1.compareTo(f2);
    };

    Arrays.sort(files, comparator);

    for (File f : files) {
      System.out.println(f);
    }
  }

  /**
   * Form a sub-interface Collection2 from Collection and add a default method void
   * forEachIf(Consumer<T> action, Predicate<T> filter) that applies action to each element for
   * which filter returns true. How could you use it?
   */
  void ex9() {
    Collection2<String> li = new ArrayList2<>();
    li.add("Basketball");
    li.add("Baseball");
    li.add("Football");

    li.forEachIf(System.out::println, e -> e.startsWith("B"));
  }

  /**
   * Go through the method of the Collections class. If you were king for a day, into which
   * interface would you place each method? Would it be a default method or a static method?
   */
  public void ex10() {
    // Factory methods would be static methods on a given interface. Example:
    // public static final <T> Set<T> emptySet() in Collections helper class would be
    // static <T> Set<T> emptySet() in Set interface

    // Methods that naturally operate on an instance would become default methods on a given interface. Example:
    // public static void shuffle(List<?> list) in Collections helper class would be
    // default void shuffle(List<?> list)
  }

  /**
   * In the past, you were told that it's bad form to add method to an interface because it would
   * break existing code. Now you are told that it's okay to add new methods, provided you also
   * supply a default implementation. How safe is that? Describe a scenario where the new stream
   * method of the Collection interface causes legacy code to fail compilation. What about binary
   * compatibility? Will legacy code from a JAR file still run?
   */
  public void ex12() {
    // A case where legacy code would fail compilation:
    // A custom object implements the Collection interface and a custom interface that has a method with the same
    // signature as the stream() method of the Collection interface.

    // Binary compatibility
    // Don't know.
  }
}
