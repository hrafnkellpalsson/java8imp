package io.palsson.exercises;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

class ArrayList2<E> extends ArrayList<E> implements Collection2<E> {

  @Override
  public void forEachIf(Consumer<E> action, Predicate<E> filter) {
    this.stream().filter(filter).forEach(action);

    // We also could have done this with an old school loop
    // for (E elem : this) {
    //   if (filter.test(elem)) {
    //     action.accept(elem);
    //   }
    // }
  }
}
