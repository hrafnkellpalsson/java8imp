package other;

import java.util.List;
import java.util.Set;

// Arrays are reified (Google defines as "make something more concrete or real") which means that arrays know
// and enforce their element types at runtime.
// Generics are generally non-reifiable (intuitively, according to Bloch, means runtime representation contains less
// information than compile time information) because of type-erasure, though there are obscure exceptions, see
// method pedanticStuff.
// So arrays and generics usually don't play well together.

// There really are 4 different types: "Normal" types (String), generic types (List<E>),
// parameterized types (List<String>) and raw types (List).
// Each generic type defines a set of parameterized types.

// Generic types also come in 4 different flavors, normal or wildcard and bounded or unbounded.
// Type 1
// Generic type: List<E> - ?
// Type 2
// Recursively bounded generic type : List<E extends Comparable<E>> or List<E super Comparable<E>> - ?
// Type 3
// Unbounded wildcard type: List<?> - If you want to use a generic type but you don't know or care what the actual type
// parameter is. To realize the difference between List<?> and List<E> look at method nCommon. Also, while List<Object>
// is parameterized type representing a list that can contain objects of any type, List<?> is a wildcard type
// representing a set that can contain only objects of some unknown type.
// Type 4
// Bounded wildcard type: List<? extends String> or List<? super String> - ?
public class Generics {
    public static int nCommon(Set<?> s1, Set<?> s2) {
        int result = 0;
        for (Object o1: s1) {
            if (s2.contains(o1)) {
                result++;
            }
        }
        return result;
    }
    // If the signature for nCommon would be like this then we couldn't use two different actual type parameters.
    // public static <E> int nCommon(Set<E> s1, Set<E> s2)
    // This signature for nCommon would allow different type parameters but is less elegant than using wildcards.
    // public static <E, F> int nCommon(Set<E> s1, Set<F> s2)

    // An example of where generics and arrays do play together.
    public <T> T[] toArray() {
        throw new UnsupportedOperationException();
    }

    public <T> T[] toArrayBroken() {
        throw new UnsupportedOperationException();
        // return new T[]; // Not allowed, compare with C# ": where new" construct
    }

    public void pedanticStuff() {
        // While this is legal
        List<String>[] genericArray1 = null;
        // actually creating the array is illegal
        // genericArray = new List<String>[1]; - Illegal!
        // because if this were allowed then generics would no longer provide perfect type safety.

        // It's actually legal to create arrays of unbounded wildcard types though this is rarely useful according to Bloch.
        List<?>[] genericArray2 = new List<?>[1];
        // This is the obscure exception about reifiable generics mentioned above!
    }
}
