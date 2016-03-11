package excercises;

public final class ImmutableAverager {
    private final double total;
    private final int count;

    public ImmutableAverager(double total, int count) {
        this.total = total;
        this.count = count;
    }

    public double average() {
        return this.count > 0 ? total/count : 0D;
    }

    public ImmutableAverager accumulator(Double d) {
        return new ImmutableAverager(total + d, count + 1);
    }

    public ImmutableAverager combiner(ImmutableAverager other) {
        return new ImmutableAverager(total + other.total, count + other.count);
    }
}
