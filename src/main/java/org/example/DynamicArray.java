package org.example;

public class DynamicArray {
    private int[] data;
    private int size;
    private final Metrics metrics;

    public DynamicArray(Metrics metrics) {
        this.data = new int[10];
        this.size = 0;
        this.metrics = metrics;
    }

    public int size() {
        return size;
    }

    private void ensureCapacity() {
        if (size == data.length) {
            int[] newData = new int[data.length * 2];
            for (int i = 0; i < size; i++) {
                metrics.incrementSteps();
                metrics.incrementMoves();
                newData[i] = data[i];
            }
            data = newData;
        }
    }

    public void add(int element) {
        ensureCapacity();
        metrics.incrementMoves();
        data[size++] = element;
    }

    public void add(int index, int element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        ensureCapacity();
        for (int i = size; i > index; i--) {
            metrics.incrementSteps();
            metrics.incrementMoves();
            data[i] = data[i - 1];
        }
        data[index] = element;
        metrics.incrementMoves();
        size++;
    }

    public int remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        metrics.incrementSteps();
        int removed = data[index];
        for (int i = index; i < size - 1; i++) {
            metrics.incrementSteps();
            metrics.incrementMoves();
            data[i] = data[i + 1];
        }
        size--;
        return removed;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
        metrics.incrementSteps();
        return data[index];
    }

    public boolean contains(int element) {
        for (int i = 0; i < size; i++) {
            metrics.incrementSteps();
            metrics.incrementComparisons();
            if (data[i] == element) {
                return true;
            }
        }
        return false;
    }
}
