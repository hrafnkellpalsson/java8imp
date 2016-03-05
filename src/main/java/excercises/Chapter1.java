package excercises;

import javaslang.collection.List;

import java.io.File;

public class Chapter1 {
    /**
     * Using the listFiles(FileFilter) and isDirectory methods of the java.io.File class, write a method that returns
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
}
