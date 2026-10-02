package org.example;

public class MinHeap {
    private int[] heap;
    private int size;
    private final Metrics metrics;

    public MinHeap(Metrics metrics) {
        this.heap = new int[10];
        this.size = 0;
        this.metrics = metrics;
    }

    public int size() {
        return size;
    }

    private void ensureCapacity() {
        if (size == heap.length) {
            int[] newHeap = new int[heap.length * 2];
            for (int i = 0; i < size; i++) {
                metrics.incrementSteps();
                metrics.incrementMoves();
                newHeap[i] = heap[i];
            }
            heap = newHeap;
        }
    }

    public void insert(int element) {
        ensureCapacity();
        metrics.incrementMoves();
        heap[size] = element;
        size++;
        heapifyUp(size - 1);
    }

    public int extractMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        metrics.incrementSteps();
        int min = heap[0];
        metrics.incrementSteps();
        metrics.incrementMoves();
        heap[0] = heap[size - 1];
        size--;
        heapifyDown(0);
        return min;
    }

    public int peek() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        metrics.incrementSteps();
        return heap[0];
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            metrics.incrementSteps(); // чтение текущего элемента
            metrics.incrementSteps(); // чтение родителя
            metrics.incrementComparisons();
            if (heap[index] < heap[parentIndex]) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    private void heapifyDown(int index) {
        while (index < size) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int smallest = index;

            if (leftChild < size) {
                metrics.incrementSteps();
                metrics.incrementSteps();
                metrics.incrementComparisons();
                if (heap[leftChild] < heap[smallest]) {
                    smallest = leftChild;
                }
            }

            if (rightChild < size) {
                metrics.incrementSteps();
                metrics.incrementSteps();
                metrics.incrementComparisons();
                if (heap[rightChild] < heap[smallest]) {
                    smallest = rightChild;
                }
            }

            if (smallest != index) {
                swap(index, smallest);
                index = smallest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        metrics.incrementSteps();
        metrics.incrementSteps();
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
        metrics.incrementMoves();
        metrics.incrementMoves();
    }
}