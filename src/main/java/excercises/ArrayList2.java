package excercises;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ArrayList2<E> extends ArrayList<E> implements Collection2<E> {

  @Override
  public void forEachIf(Consumer<E> action, Predicate<E> filter) {
    for (E elem : this) {
      if (filter.test(elem)) { // Syntax error!
        action.accept(elem);
      }
    }

    this.stream().filter(filter).forEach(action);
  }
}
