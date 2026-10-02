package org.example;

public class MyLinkedList {
    private static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;
    private int size;
    private final Metrics metrics;

    public MyLinkedList(Metrics metrics) {
        this.head = null;
        this.size = 0;
        this.metrics = metrics;
    }

    public int size() {
        return size;
    }

    public void add(int element) {
        Node newNode = new Node(element);
        metrics.incrementMoves();

        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            metrics.incrementSteps();
            while (current.next != null) {
                metrics.incrementSteps();
                current = current.next;
            }
            current.next = newNode;
            metrics.incrementMoves();
        }
        size++;
    }

    public void add(int index, int element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        Node newNode = new Node(element);
        metrics.incrementMoves();

        if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node current = head;
            metrics.incrementSteps();
            for (int i = 0; i < index - 1; i++) {
                metrics.incrementSteps();
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
            metrics.incrementMoves();
        }
        size++;
    }

    public int remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        int removedData;
        if (index == 0) {
            metrics.incrementSteps();
            removedData = head.data;
            head = head.next;
        } else {
            Node current = head;
            metrics.incrementSteps();
            for (int i = 0; i < index - 1; i++) {
                metrics.incrementSteps();
                current = current.next;
            }
            metrics.incrementSteps();
            removedData = current.next.data;
            current.next = current.next.next;
        }
        size--;
        return removedData;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }

        Node current = head;
        metrics.incrementSteps();
        for (int i = 0; i < index; i++) {
            metrics.incrementSteps();
            current = current.next;
        }
        return current.data;
    }

    public boolean contains(int element) {
        Node current = head;
        while (current != null) {
            metrics.incrementSteps();
            metrics.incrementComparisons();
            if (current.data == element) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
}