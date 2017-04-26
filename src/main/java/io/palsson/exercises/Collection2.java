package io.palsson.exercises;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;

interface Collection2<E> extends Collection<E> {
  void forEachIf(Consumer<E> action, Predicate<E> filter);
}
