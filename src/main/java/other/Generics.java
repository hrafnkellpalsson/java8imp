package other;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

// Arrays are reified (Google defines as "make something more concrete or real") which means that
// arrays know and enforce their element types at runtime.
// Generics are generally non-reifiable (intuitively, according to Bloch, means runtime
// representation contains less information than compile time information) because of type-erasure,
// though there are obscure exceptions, see method pedanticStuff.
// So arrays and generics usually don't play well together.

// There really are 4 different types: "Normal" types (String), generic types (List<E>),
// parameterized types (List<String>, List<Integer> etc.) and raw types (List).
// Each generic type defines a set of parameterized types.

// Generic types also come in 4 different flavors, normal or wildcard and bounded or unbounded.
// Type 1
// Generic type: List<E> - No bound on the parameterized types.
// Bloch: There is a duality between type parameters and wildcards, and many method can be declared
// using one or the other.
// Type 2
// Bounded generic type: List<E extends String>
// A special subtype is recursively bounded generic type. The most common is the following (and the
// type bound may be read as "for every type E that can be compared to itself"):
// List<E extends Comparable<E>>
// Type 3
// Unbounded wildcard type: List<?> - If you want to use a generic type but you don't know or care
// what the actual type parameter is. To realize the difference between List<?> and List<E> look at
// method numCommon. Also, while List<Object> is parameterized type representing a list that can
// contain objects of any type, List<?> is a wildcard type.
// Bloch rule: If a type parameter appears only once in a method declaration, replace it with a
// wildcard.
// representing a set that can contain only objects of some unknown type.
// Type 4
// Bounded wildcard type: List<? extends String> or List<? super String> - For covariance and
// contra-variance, see classes MyStack and MyStackTest.
class Generics {
  static int numCommon(Set<?> s1, Set<?> s2) {
    int result = 0;
    for (Object o1 : s1) {
      if (s2.contains(o1)) {
        result++;
      }
    }
    return result;
  }
  // If the signature for numCommon would be like this then we couldn't use two different actual
  // type parameters.
  // public static <E> int numCommon(Set<E> s1, Set<E> s2)
  // This signature works but the wildcard version is more elegant.
  // public static <E, F> int numCommon(Set<E> s1, Set<F> s2)

  // An example of where generics and arrays do play together.
  public <E> E[] toArray() {
    throw new UnsupportedOperationException();
  }

  // Knowing how to create a generic array is useful for creating generic collections backed by an
  // array.
  static <E> E[] createGenericArray(E[] arr) {
    // Creating a generic array by new-ing it is not allowed (compare with C# ": where new"
    // construct) return new E[];
    // We can however create a generic array by casting.
    Object[] brr = Arrays.copyOf(arr, arr.length);
    // The elements of this array will only contain E instances since the input parameter is of
    // type E[].
    @SuppressWarnings("unchecked")
    E[] crr = (E[]) brr; // Cast to 'create' (or rather transform to) generic array.
    return crr;
  }

  // Bloch rule: Do not use wildcard types as return types
  // Bloch rule: If the user of a class has to think about wildcard types, there is probably something wrong
  // with the class' API
  // This sums up to say that client code should not have to use wildcards.
  static void usingWildcardsInCode(Set<?> s) {
    // We can not use wildcards on their own like we can with a vanilla generic type parameter.
    // <?> obj = null; - Illegal!
    // ? obj = null; - Illegal!
    // We can however use wildcards for parameterized types.
    List<? extends String> li = null;
    CompletableFuture<?> fut = null;
  }

  public static <E extends String> void boundedGeneric(E elem) {
    System.out.println("elem = [" + elem + "]");
  }

  // Method signature could be improved, see method comparableGenerics, but the point here is to
  // show a recursive bound.
  public static <E extends Comparable<E>> void recursivelyBoundedGeneric(E elem) {
    System.out.println("elem = [" + elem + "]");
  }

  // Comparable is a consumer of E elements and so by the PECS rule we should use <? super E> in
  // preference to <E>.
  // Same goes for Comparator.
  public static <E extends Comparable<? super E>> int comparableGenerics(E e1, E e2) {
    return e1.compareTo(e2);
  }

  // On duality between type parameters and wildcards. A method that swaps two elements in a list
  // can be declared in either of following ways
  // public static void swap(List<?> list, int i, int j) or
  // public static <E> void swap(List<E> list, int i, int j)
  // Bloch deems first one as slightly better because there is no type parameter to worry about, but
  // it's also slightly harder to implement, see Bloch. Also, this declaration falls under Bloch
  // rule about a type parameterthat appears only once.

  void usingRawTypes(Object o) {
    // There are only two exceptions to using raw types in new code.
    // First.
    // You must use raw types in class literals. List<String>.class is forbidden so we must use
    // List.class (note that String.class, String[].class and int.class are all legal).
    // Second.
    // Concerns instanceof operator. Because of type erasure at runtime it is illegal to use
    // instanceof operator on parameterized types except on unbounded wildcard types, because that's
    // the obscure exception where type erasure does not happen!
    // if (o instanceof Set<String>) - Illegal!
    // if (o instanceof Set<?>) - Bloch considers the "<?>" to be unnecessary noise here and prefers
    // dropping it
    if (o instanceof Set) {
      Set<?> s = (Set<?>) o;
    }
  }

  // For generics and classes see class ClassStuff.

  void pedanticStuff() {
    // While this is legal
    List<String>[] arr = null;
    // we see again, like in method createGenericArray, that new-ing a generic array is illegal
    // arr = new List<String>[1];
    // because if this were allowed then generics would no longer provide perfect type safety.

    // It's actually legal to create arrays of unbounded wildcard types though this is rarely useful
    // according to Bloch.
    List<?>[] brr = new List<?>[1];
    // This is the obscure exception about reifiable generics mentioned above!
  }
}
