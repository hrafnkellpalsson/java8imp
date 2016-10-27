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
        s1. add(2);
        Set<Object> s2 = new HashSet<>(); // Different type parameter from s1!
        s2.add(2);
        s2.add(3);
        int num = Generics.nCommon(s1, s2);
        Assert.assertEquals(num, 1);
    }
}
