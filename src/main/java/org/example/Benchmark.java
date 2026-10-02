package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class Benchmark {

    public static void main(String[] args) {
        int[] sizes = {100, 1000, 10000, 100000};
        String csvFileName = "results.csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFileName))) {
            writer.println("DataStructure,Operation,Size,TimeMs,Steps,Comparisons,Moves");

            Random random = new Random(42);

            for (int n : sizes) {
                runDynamicArrayBenchmark(writer, n, random);
                runLinkedListBenchmark(writer, n, random);
                runMinHeapBenchmark(writer, n, random);
            }

            System.out.println("Benchmark completed! Results saved to " + csvFileName);

        } catch (IOException e) {
            System.err.println("Error writing to CSV: " + e.getMessage());
        }
    }

    private static void runDynamicArrayBenchmark(PrintWriter writer, int n, Random random) {
        Metrics metrics = new Metrics();
        DynamicArray arr = new DynamicArray(metrics);

        // Add
        long startTime = System.nanoTime();
        for (int i = 0; i < n; i++) {
            arr.add(random.nextInt(n));
        }
        long endTime = System.nanoTime();
        writeMetrics(writer, "DynamicArray", "Add", n, (endTime - startTime) / 1e6, metrics);

        // Access (Get)
        metrics.reset();
        startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            arr.get(random.nextInt(n));
        }
        endTime = System.nanoTime();
        writeMetrics(writer, "DynamicArray", "Get", n, (endTime - startTime) / 1e6, metrics);

        // Remove
        metrics.reset();
        startTime = System.nanoTime();
        for (int i = 0; i < Math.min(n, 100); i++) {
            arr.remove(0);
        }
        endTime = System.nanoTime();
        writeMetrics(writer, "DynamicArray", "Remove", n, (endTime - startTime) / 1e6, metrics);
    }

    private static void runLinkedListBenchmark(PrintWriter writer, int n, Random random) {
        Metrics metrics = new Metrics();
        MyLinkedList list = new MyLinkedList(metrics);

        // Add
        long startTime = System.nanoTime();
        for (int i = 0; i < n; i++) {
            list.add(random.nextInt(n));
        }
        long endTime = System.nanoTime();
        writeMetrics(writer, "MyLinkedList", "Add", n, (endTime - startTime) / 1e6, metrics);

        // Access (Get)
        metrics.reset();
        startTime = System.nanoTime();
        for (int i = 0; i < Math.min(n, 100); i++) {
            list.get(random.nextInt(n));
        }
        endTime = System.nanoTime();
        writeMetrics(writer, "MyLinkedList", "Get", n, (endTime - startTime) / 1e6, metrics);

        // Remove
        metrics.reset();
        startTime = System.nanoTime();
        for (int i = 0; i < Math.min(n, 100); i++) {
            list.remove(0);
        }
        endTime = System.nanoTime();
        writeMetrics(writer, "MyLinkedList", "Remove", n, (endTime - startTime) / 1e6, metrics);
    }

    private static void runMinHeapBenchmark(PrintWriter writer, int n, Random random) {
        Metrics metrics = new Metrics();
        MinHeap heap = new MinHeap(metrics);

        // Insert
        long startTime = System.nanoTime();
        for (int i = 0; i < n; i++) {
            heap.insert(random.nextInt(n));
        }
        long endTime = System.nanoTime();
        writeMetrics(writer, "MinHeap", "Insert", n, (endTime - startTime) / 1e6, metrics);

        // ExtractMin
        metrics.reset();
        startTime = System.nanoTime();
        for (int i = 0; i < Math.min(n, 100); i++) {
            heap.extractMin();
        }
        endTime = System.nanoTime();
        writeMetrics(writer, "MinHeap", "ExtractMin", n, (endTime - startTime) / 1e6, metrics);
    }

    private static void writeMetrics(PrintWriter writer, String ds, String op, int size, double timeMs, Metrics metrics) {
        writer.printf("%s,%s,%d,%.4f,%d,%d,%d%n",
                ds, op, size, timeMs, metrics.getSteps(), metrics.getComparisons(), metrics.getMoves());
    }
}