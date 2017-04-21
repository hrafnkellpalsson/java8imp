package excercises;

import org.testng.annotations.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Chapter2Test {
    @Test
    public void testEx1() {
        Chapter2 ch2 = new Chapter2();
        ch2.ex1();
    }

    @Test
    public void testEx2() {
        Chapter2 ch2 = new Chapter2();
        ch2.ex2();
    }

    @Test
    public void testEx3() throws IOException {
        Chapter2 ch2 = new Chapter2();
        ch2.ex3();
    }

    @Test
    public void testEx5() throws IOException {
        Chapter2 ch2 = new Chapter2();
        ch2.ex5();
    }

    @Test
    public void testEx6() throws IOException {
        Chapter2 ch2 = new Chapter2();
        ch2.ex6();
    }

    @Test
    public void testEx7() throws IOException {
        Chapter2 ch2 = new Chapter2();
        ch2.ex7();
    }

    @Test
    public void testEx9_1() {
        Chapter2 ch2 = new Chapter2();
        ch2.ex9_1();
    }

    @Test
    public void testEx9_2() {
        Chapter2 ch2 = new Chapter2();

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

        ArrayList<LocalDate> listDates = ch2.ex9_2(streamDates);
        listDates.forEach(System.out::println);
    }

    @Test
    public void testEx10() {
        Chapter2 ch2 = new Chapter2();
        ch2.ex10();
    }
}