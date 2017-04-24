package excercises;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface Collection2<E> extends Collection<E> {
  void forEachIf(Consumer<E> action, Predicate<E> filter);
}
