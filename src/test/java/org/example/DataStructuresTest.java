package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DataStructuresTest {
    private Metrics metrics;

    @BeforeEach
    void setUp() {
        metrics = new Metrics();
    }

    @Test
    void testDynamicArray() {
        DynamicArray arr = new DynamicArray(metrics);
        arr.add(10);
        arr.add(20);
        arr.add(30);

        assertEquals(3, arr.size());
        assertEquals(20, arr.get(1));
        assertTrue(arr.contains(20));
        assertEquals(20, arr.remove(1));
        assertEquals(2, arr.size());
        assertTrue(metrics.getSteps() > 0);
    }

    @Test
    void testMyLinkedList() {
        MyLinkedList list = new MyLinkedList(metrics);
        list.add(100);
        list.add(200);

        assertEquals(2, list.size());
        assertEquals(100, list.get(0));
        assertTrue(list.contains(200));
        assertEquals(100, list.remove(0));
        assertEquals(1, list.size());
        assertTrue(metrics.getSteps() > 0);
    }

    @Test
    void testMinHeap() {
        MinHeap heap = new MinHeap(metrics);
        heap.insert(50);
        heap.insert(10);
        heap.insert(30);

        assertEquals(3, heap.size());
        assertEquals(10, heap.peek());
        assertEquals(10, heap.extractMin());
        assertEquals(30, heap.extractMin());
        assertEquals(1, heap.size());
        assertTrue(metrics.getComparisons() > 0);
    }
}