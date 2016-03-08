package excercises;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ArrayList2<E> extends ArrayList<E> implements Collection2<E> {
    @Override
    public void forEachIf(Consumer<E> action, Predicate<E> filter) {
        // We can't use an old school for loop, because we can't call a lambda directly (the only thing we can do with
        // a lambda is convert it to a functional interface)
//        for (E elem : this) {
//            if (filter(elem)) { // Syntax error!
//
//            }
//        }

        this.stream().filter(filter).forEach(action);
    }
}
