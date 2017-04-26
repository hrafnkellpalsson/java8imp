package other;

import java.util.List;

class ClassStuff {
  void classTypes() {
    // Literals are the values of primitive types. Java also supports string literals.
    // These literals all have a type, e.g.
    int i = 0;
    char c = 'c';
    String str = "a";
    // Finally, Java also supports class literals and those of course also have a type.
    Class<String> sc = String.class;
    Class<List> cl = List.class;
    // Class<List<String>> cls = List<String>.class - Right hand side is illegal by JLS!
    Class<String[]> csa = String[].class;
    Class<Integer> ci0 = Integer.class;
    Class<Integer> ci1 = Integer.TYPE; // Same as Integer.class, available for any primitive type
    Class<Integer> cip = int.class; // Automatic boxing
    Class<Class> cc = Class.class; // Mind blown!
  }
}
