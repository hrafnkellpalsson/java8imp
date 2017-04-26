package io.palsson.exercises;

final class ImmutableAverager {

  private final double total;
  private final int count;

  ImmutableAverager(double total, int count) {
    this.total = total;
    this.count = count;
  }

  double average() {
    return this.count > 0 ? total / count : 0D;
  }

  ImmutableAverager accumulator(Double d) {
    return new ImmutableAverager(total + d, count + 1);
  }

  ImmutableAverager combiner(ImmutableAverager other) {
    return new ImmutableAverager(total + other.total, count + other.count);
  }
}
