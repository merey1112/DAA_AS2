package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Random;

public class Benchmark {

    private static final int[] SIZES = {100, 1000, 10000, 100000};

    public static void main(String[] args) {
        File resultsDir = new File("results");
        if (!resultsDir.exists()) {
            resultsDir.mkdirs();
        }

        File csvFile = new File(resultsDir, "results.csv");

        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFile))) {
            writer.println("DataStructure,Operation,Size,TimeMs,Steps,Comparisons");

            for (int size : SIZES) {
                System.out.println("Running benchmarks for size n = " + size + "...");
                runDynamicArrayBenchmarks(writer, size);
                runLinkedListBenchmarks(writer, size);
                runMinHeapBenchmarks(writer, size);
            }

            System.out.println("Benchmark finished! Results written to results/results.csv");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void runDynamicArrayBenchmarks(PrintWriter writer, int size) {
        Random random = new Random(42);

        // 1. Benchmark Add
        Metrics metricsAdd = new Metrics();
        DynamicArray arrayAdd = new DynamicArray(metricsAdd);
        long startAdd = System.nanoTime();
        for (int i = 0; i < size; i++) {
            arrayAdd.add(random.nextInt());
        }
        long endAdd = System.nanoTime();
        double timeAddMs = (endAdd - startAdd) / 1_000_000.0;
        writeRecord(writer, "DynamicArray", "Add", size, timeAddMs, metricsAdd);

        // 2. Benchmark Get
        Metrics metricsGet = new Metrics();
        DynamicArray arrayGet = new DynamicArray(metricsGet);
        for (int i = 0; i < size; i++) arrayGet.add(random.nextInt());

        long startGet = System.nanoTime();
        for (int i = 0; i < size; i++) {
            int randomIndex = random.nextInt(size);
            arrayGet.get(randomIndex);
        }
        long endGet = System.nanoTime();
        double timeGetMs = (endGet - startGet) / 1_000_000.0;
        writeRecord(writer, "DynamicArray", "Get", size, timeGetMs, metricsGet);

        // 3. Benchmark Remove
        Metrics metricsRemove = new Metrics();
        DynamicArray arrayRemove = new DynamicArray(metricsRemove);
        for (int i = 0; i < size; i++) arrayRemove.add(random.nextInt());

        long startRemove = System.nanoTime();
        for (int i = 0; i < Math.min(100, size); i++) {
            int currentSize = arrayRemove.size();
            if (currentSize > 0) {
                int removeIndex = random.nextInt(currentSize);
                arrayRemove.remove(removeIndex);
            }
        }
        long endRemove = System.nanoTime();
        double timeRemoveMs = (endRemove - startRemove) / 1_000_000.0;
        writeRecord(writer, "DynamicArray", "Remove", size, timeRemoveMs, metricsRemove);
    }

    private static void runLinkedListBenchmarks(PrintWriter writer, int size) {
        Random random = new Random(42);

        // 1. Benchmark Add
        Metrics metricsAdd = new Metrics();
        MyLinkedList listAdd = new MyLinkedList(metricsAdd);
        long startAdd = System.nanoTime();
        for (int i = 0; i < size; i++) {
            listAdd.add(random.nextInt());
        }
        long endAdd = System.nanoTime();
        double timeAddMs = (endAdd - startAdd) / 1_000_000.0;
        writeRecord(writer, "MyLinkedList", "Add", size, timeAddMs, metricsAdd);

        // 2. Benchmark Get
        Metrics metricsGet = new Metrics();
        MyLinkedList listGet = new MyLinkedList(metricsGet);
        for (int i = 0; i < size; i++) listGet.add(random.nextInt());

        long startGet = System.nanoTime();
        int sampleCount = Math.min(1000, size);
        for (int i = 0; i < sampleCount; i++) {
            int randomIndex = random.nextInt(size);
            listGet.get(randomIndex);
        }
        long endGet = System.nanoTime();
        double timeGetMs = (endGet - startGet) / 1_000_000.0;
        writeRecord(writer, "MyLinkedList", "Get", size, timeGetMs, metricsGet);

        // 3. Benchmark Remove
        Metrics metricsRemove = new Metrics();
        MyLinkedList listRemove = new MyLinkedList(metricsRemove);
        for (int i = 0; i < size; i++) listRemove.add(random.nextInt());

        long startRemove = System.nanoTime();
        for (int i = 0; i < Math.min(100, size); i++) {
            int currentSize = listRemove.size();
            if (currentSize > 0) {
                int removeIndex = random.nextInt(currentSize);
                listRemove.remove(removeIndex);
            }
        }
        long endRemove = System.nanoTime();
        double timeRemoveMs = (endRemove - startRemove) / 1_000_000.0;
        writeRecord(writer, "MyLinkedList", "Remove", size, timeRemoveMs, metricsRemove);
    }

    private static void runMinHeapBenchmarks(PrintWriter writer, int size) {
        Random random = new Random(42);

        // 1. Benchmark Insert
        Metrics metricsInsert = new Metrics();
        MinHeap heapInsert = new MinHeap(metricsInsert);
        long startInsert = System.nanoTime();
        for (int i = 0; i < size; i++) {
            heapInsert.insert(random.nextInt());
        }
        long endInsert = System.nanoTime();
        double timeInsertMs = (endInsert - startInsert) / 1_000_000.0;
        writeRecord(writer, "MinHeap", "Insert", size, timeInsertMs, metricsInsert);

        // 2. Benchmark ExtractMin
        Metrics metricsExtract = new Metrics();
        MinHeap heapExtract = new MinHeap(metricsExtract);
        for (int i = 0; i < size; i++) heapExtract.insert(random.nextInt());

        long startExtract = System.nanoTime();
        for (int i = 0; i < size; i++) {
            if (heapExtract.size() > 0) {
                heapExtract.extractMin();
            }
        }
        long endExtract = System.nanoTime();
        double timeExtractMs = (endExtract - startExtract) / 1_000_000.0;
        writeRecord(writer, "MinHeap", "ExtractMin", size, timeExtractMs, metricsExtract);
    }

    private static void writeRecord(PrintWriter writer, String structName, String operation, int size, double timeMs, Metrics metrics) {
        String formattedTime = String.format(Locale.US, "%.4f", timeMs);
        long steps = metrics != null ? metrics.getSteps() : 0;
        long comparisons = metrics != null ? metrics.getComparisons() : 0;
        writer.println(structName + "," + operation + "," + size + "," + formattedTime + "," + steps + "," + comparisons);
    }
}