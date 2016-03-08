package excercises;

import java.io.File;
import java.util.*;

public class Chapter1 {
    /**
     * Using the listFiles(FileFilter) and isDirectory methods of the java.io.File class, meth a method that returns
     * all subdirectories of a given directory. Use a lambda expression instead of a FileFilter object. Repeat with a
     * method reference.
     */
    public void ex2() {
        File file = new File("/Users/hrafnkellpalsson/Downloads");
        ex2Node(file);
    }

    public void ex2Node(File file) {
//        File[] dirs = file.listFiles(f -> f.isDirectory());
        File[] dirs = file.listFiles(File::isDirectory);
        for (File dir : dirs) {
            System.out.println(dir);
            ex2Node(dir);
        }
    }

    public void ex4() {
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

        // We could have converted the array to a list and used the sort() method defined on the list interface.
        // List<File> fs = Arrays.asList(files);

        Arrays.sort(files, (f1, f2) -> {
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
        });

        for (File f : files) {
            System.out.println(f);
        }
    }

    public void ex9() {
        Collection2<String> li = new ArrayList2<>();
        li.add("Basketball");
        li.add("Baseball");
        li.add("Football");

        li.forEachIf(System.out::println, e -> e.startsWith("B"));
    }
}
