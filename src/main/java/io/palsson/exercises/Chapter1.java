package io.palsson.exercises;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Chapter1 {
  private final static PrintStream out = System.out;

  /*
  Is the comparator code in the Arrays.sort method called in the same thread as the call to
  sort or a different thread?
   */
  private static Set<Long> ex1Helper(BiConsumer<String[], Comparator<String>> consumer) {
    Set<Long> threadIds = new CopyOnWriteArraySet<>();
    long mainId = Thread.currentThread().getId();
    threadIds.add(mainId);

    String[] sample = Helper.getBookWords("alice.txt").toArray(new String[0]);
    Comparator<String> comparator = (i, j) -> {
      long lambdaId = Thread.currentThread().getId();
      threadIds.add(lambdaId);
      return i.compareTo(j);
    };
    consumer.accept(sample, comparator);

    return threadIds;
  }

  static Set<Long> ex1Sort() {
    BiConsumer<String[], Comparator<String>> consumer = Arrays::sort;
    return ex1Helper(consumer);
  }

  static Set<Long> ex1ParallelSort() {
    BiConsumer<String[], Comparator<String>> consumer = Arrays::parallelSort;
    return ex1Helper(consumer);
  }

  /*
  Using the listFiles(FileFilter) and isDirectory methods of the java.io.File class, write a
  method that returns all subdirectories of a given directory. Use a lambda expression instead of
  a FileFilter object. Repeat with a method reference.
   */
  static List<File> ex2() {
    // Recursive lambdas are not supported, so that's not an option.
    File file = new File("./src");
    List<File> allDirs = new ArrayList<>();
    go(file, allDirs);
    return allDirs;
  }

  private static void go(File file, List<File> allDirs) {
    // File[] dirs = file.listFiles(f -> f.isDirectory()); // Using a lambda expression
    File[] dirs = file.listFiles(File::isDirectory); // Using a method reference
    for (File dir : dirs) {
      // out.println(dir);
      allDirs.add(dir);
      go(dir, allDirs);
    }
  }

  /*
  Using the list(FilenameFilter) method of the java.io.File cass, write a method that return all
  files in a given directory with a given extension. Use a lambda expression, not a
  FilenameFilter. Which variables from the enclosing scope does it capture?
   */
  static File[] ex3(String suffix) {
    File file = new File(".");
    FilenameFilter filter = (dir, name) -> {
      boolean isFile = !Paths.get(dir.getName(), name).toFile().isDirectory();
      return name.endsWith(suffix) && isFile;
    };
    return file.listFiles(filter);
  }

  /*
  Given an array of File objects, sort it so that the directories come before the files, and
  within each group, elements are sorted by path name. Use a lambda expression, not a
  Comparator.
   */
  static void ex4(File[] files) {
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
  }

  /*
  Take a file from one of your projects that contains a number of ActionListener, Runnable, or
  the like. Replace them with lambda expressions. How many lines did it save? Was the code easier
  to read? Were you able to use method references?
   */
  static void ex5() {
    // I can haz cheezburger?
  }

  /*
  Didn't you always hate it that you had to deal with checked exceptions in a Runnable? Write a
  method uncheck that catches all checked exceptions and turns them into unchecked exceptions.
  For example,
  new Thread(uncheck(
    () -> { System.out.println("Zzz"); Thread.sleep(1000); })).start();
      // Look, no catch (InterruptedException)!
  Hint: Define an interface RunnableEx whose run method may throw any exceptions. Then implement
  public static Runnable uncheck(RunnableEx runner). Use a lambda expression inside the uncheck
  method.
   */
  static void ex6() throws InterruptedException {
    Runnable r = uncheck(() -> {
      out.println("Zzz");
      Thread.sleep(100);
    });

    ExecutorService executor = Executors.newSingleThreadExecutor();
    executor.execute(r);

    // Sleep to allow ExecutorService thread to finish before current thread returns from method.
    Thread.sleep(200);
  }

  static Runnable uncheck(RunnableEx runner) {
    Runnable r = () -> {
      try {
        runner.run();
      } catch (Exception e) {
        // Ignore
      }
    };

    return r;
  }

  static void ex6Pre() throws InterruptedException {
    ExecutorService executor = Executors.newSingleThreadExecutor();

    // If our runnable doesn't have to deal with checked exceptions, everything is fine and dandy.
    Runnable runnerNoChecked = () -> { out.println("I fart in your general direction!"); };

    // This runnable throws a checked exception (IOException thrown by Files.readAllBytes) and we
    // have to catch it.
    // If a runnable were allowed to throw a checked exception, what would catch it?
    // There's no way to enclose that run() call in a handler, since we don't write the code that
    // invokes it.
    // Knowing that we have to catch the exception, how do we relate the error information back
    // to the main thread? Well, threads communicate through shared memory, so we have to write the
    // error information somewhere in memory the main thread can access it.
    Runnable runnerWithChecked = () -> {
      try {
        Files.readAllBytes(Paths.get("README.md"));
        out.println("Succeeded in opening file.");
      } catch (IOException e) {
        // Proper handling of exception would require writing error to shared memory.
        out.println(e.getMessage());
      }
    };

    executor.execute(runnerNoChecked);
    executor.execute(runnerWithChecked);

    // Sleep to allow ExecutorService thread to finish before current thread returns from method.
    Thread.sleep(200);
  }

  /*
  Write a static method andThen that takes as parameters two Runnable instances and returns a
  Runnable that runs the first, then the second. In the main method, pass two lambda expressions
  into a call to andThen, and run the returned instance.
   */
  static void ex7() throws InterruptedException {
    Runnable first = () -> { out.println("I fart in your "); };
    Runnable second = () -> { out.println("general direction!"); };
    Runnable combined = andThen(first, second);

    ExecutorService executor = Executors.newSingleThreadExecutor();
    executor.execute(combined);

    // Sleep to allow ExecutorService thread to finish before current thread returns from method.
    Thread.sleep(200);
  }

  private static Runnable andThen(Runnable first, Runnable second) {
    return () -> {
      first.run();
      second.run();
    };
  }

  /*
  What happens when a lambda expression captures values in an enhanced for loop such as this one?
  String[] names = { "Peter", "Paul", "Mary" };
  List<Runnable> runners = new ArrayList<>();
  for (String name : names)
    runners.add(() -> System.out.println(name));
  Is it legal? Does each lambda expression capture a different value, or do they all get the last
  value? What happens if you use a traditional loop
  for (int i = 0; i < names.length; i++)?
   */
  static void ex8() throws InterruptedException {
    // See methods ex8Pre1 and ex8Pre2.
    // In both instances each lambda expression captures a different value.

    // TODO Is the following explanation correct?
    // I'm guessing that the way variable binding works means that each thread gets a hold of an
    // actual string object ("Peter" etc.) rather than a 'name' token which would bind to a value
    // at the time of running (which would be after the for loop, so it would bind to "Mary").

    // Note that when using a traditional for loop we had to define a local variable name rather
    // and use that in the lambda rather than use names[i] which causes a compiler error.
    // That is expected since names is a local variable and the compiler reports error when using
    // not effectively final local variables - see p. 12.
  }

  static void ex8Pre1() throws InterruptedException {
    String[] names = { "Peter", "Paul", "Mary" };
    List<Runnable> runners = new ArrayList<>();
    for (String name : names) {
      runners.add(() -> out.println(name));
    }

    ExecutorService executor = Executors.newSingleThreadExecutor();
    for (Runnable runner : runners) {
      executor.execute(runner);
    }

    // Sleep to allow ExecutorService thread to finish before current thread returns from method.
    Thread.sleep(200);
  }

  static void ex8Pre2() throws InterruptedException {
    String[] names = { "Peter", "Paul", "Mary" };
    List<Runnable> runners = new ArrayList<>();
    for (int i = 0; i < names.length; i++) {
      String name = names[i];
      runners.add(() -> out.println(name));
    }

    ExecutorService executor = Executors.newSingleThreadExecutor();
    for (Runnable runner : runners) {
      executor.execute(runner);
    }

    // Sleep to allow ExecutorService thread to finish before current thread returns from method.
    Thread.sleep(200);
  }

  /*
  Form a sub-interface Collection2 from Collection and add a default method void
  forEachIf(Consumer<T> action, Predicate<T> filter) that applies action to each element for
  which filter returns true. How could you use it?
   */
  static void ex9() {
    Collection2<String> li = new ArrayList2<>();
    li.add("Basketball");
    li.add("Baseball");
    li.add("Football");

    li.forEachIf(out::println, e -> e.startsWith("B"));
  }

  /*
  Go through the method of the Collections class. If you were king for a day, into which
  interface would you place each method? Would it be a default method or a static method?
   */
  static void ex10() {
    // Factory methods would be static methods on a given interface. Example:
    // public static final <T> Set<T> emptySet() in Collections helper class would be
    // static <T> Set<T> emptySet() in Set interface

    // Methods that naturally operate on an instance would become default methods on a given
    // interface. Example:
    // public static void shuffle(List<?> list) in Collections helper class would be
    // default void shuffle(List<?> list)
  }

  /*
  Suppose you have a class that implements two interfaces I and J, each of which has a method
  void f(). Exactly what happens if f is an abstract, default, or static method of I and an
  abstract, default, or static method of J? Repeat where a class extends a superclass S and
  implements an interface I, each of which has a method void f().
   */
  static void ex11() {
    // See method ex11Interface and ex11InterfaceAndSuperclass
  }

  static void ex11Interfaces() {
    class Inheritor implements I, J {
      public void f() {
        System.out.println("I'm method f on Inheritor");
      }

      public void g() {
        System.out.println("I'm method g on Inheritor");
        I.super.g();
      }
    }

    // As we can see from runtime results, it doesn't matter whether we define the compile-time
    // type to be I, J, or Inheritor.
    I inheritorI = new Inheritor();
    inheritorI.f();
    inheritorI.g();
    out.println();

    J inheritorJ = new Inheritor();
    inheritorJ.f();
    inheritorJ.g();
    out.println();

    Inheritor inheritor = new Inheritor();
    inheritor.f();
    inheritor.g();
    out.println();

    out.println("Static methods don't cause conflict since they must be called on the interface,"
        + "they can't be called on the object implementing the interface.");
    I.h();
    J.h();
  }

  static void ex11InterfaceAndSuperclass() {
    class Inheritor extends S implements I {



    }

    I inheritorI = new Inheritor();
    inheritorI.f();
    inheritorI.g();
    out.println();

  }

  /*
  In the past, you were told that it's bad form to add method to an interface because it would
  break existing code. Now you are told that it's okay to add new methods, provided you also
  supply a default implementation. How safe is that? Describe a scenario where the new stream
  method of the Collection interface causes legacy code to fail compilation. What about binary
  compatibility? Will legacy code from a JAR file still run?
   */
  static void ex12() {
    // A case where legacy code would fail compilation:
    // A custom object implements the Collection interface and a custom interface that has a method with the same
    // signature as the stream() method of the Collection interface.

    // TODO Binary compatibility
  }
}
