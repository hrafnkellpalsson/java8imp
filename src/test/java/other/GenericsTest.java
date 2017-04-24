package other;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;

public class GenericsTest {

  @Test(expectedExceptions = ArrayStoreException.class)
  public void testCovariance() {
    // Legal because arrays are covariant
    Object[] x = new String[1];
    // But covariance of arrays is a problem because this is allowed and throws an ArrayStoreException at runtime
    x[0] = new Integer(0);

    // Illegal because collections are NOT covariant, they are invariant.
    // List<Object> li = new LinkedList<Number>();
    // Collections are covariant when using raw types but we shouldn't use raw types (they exist solely for
    // backwards compatibility) so just don't do it.
    List li = new LinkedList<Number>();
  }

  @Test(expectedExceptions = ClassCastException.class)
  public void testRawType() {
    // Using raw types we don't get type safety anymore, i.e. code can throw ClassCastException at runtime.
    List badList = new ArrayList<String>();
    badList.add(5); // Error not caught at compile time.
    // Type isn't known at compile time so we're forced to manually cast ourselves (we're still using an
    // enhanced for loop)
    for (Object str : badList) {
      String s = (String) str; // Will throw a ClassCastException!
    }
    // We don't need this casting when we properly paramtererize our generic type collections, because the compiler
    // guarantees we only have the correct type in our collections and can therefore do the cast behind the scenes
    // for us.
  }

  @Test
  public void testNCommon() {
    Set<Number> s1 = new HashSet<>();
    s1.add(1);
    s1.add(2);
    Set<Object> s2 = new HashSet<>(); // Different type parameter from s1!
    s2.add(2);
    s2.add(3);
    int num = Generics.nCommon(s1, s2);
    Assert.assertEquals(num, 1);
  }

  @Test
  public void testCreateGenericArray() {
    // Note that we don't explicitly have to state the type parameter for generic methods.
    // This is Java type inference which is pretty limited compared to say C#'s var or to Scala in general.
    String[] input = new String[]{"bla", "ble"};
    // String[] strArr = Generics.<String>createGenericArray(input); - Unnecessary to specify type argument
    String[] strArr = Generics.createGenericArray(input);
    // Sometimes even this limited type inference doesn't work so you have to specify the type parameter explicitly.

    // If the generic method is a constructor we can still use type inference! Which didn't use to be the case,
    // hence the factory methods for various collections in Google's Guava.
    // This feature was added in Java 7.
    Map<String, Integer> map = new HashMap<>();
    map.put("one", 1);
    map.put("two", 2);
    map.forEach((k, v) -> System.out.println(k + v));
  }
}
