package org.example;

public class Metrics {
    private long steps = 0;
    private long moves = 0;
    private long comparisons = 0;

    public void incrementSteps() { steps++; }
    public void incrementMoves() { moves++; }
    public void incrementComparisons() { comparisons++; }

    public void reset() {
        steps = 0;
        moves = 0;
        comparisons = 0;
    }

    public long getSteps() { return steps; }
    public long getMoves() { return moves; }
    public long getComparisons() { return comparisons; }
}