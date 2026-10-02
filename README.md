# Assignment 2: Empirical Analysis of Fundamental Data Structures

**Student:** Abdymanap Merey  
**Group:** SE-2516

---

## Overview
This project presents an empirical performance analysis of three fundamental data structures:
1. **Dynamic Array** (`DynamicArray`)
2. **Singly Linked List** (`MyLinkedList`)
3. **Min-Heap** (`MinHeap`)

Each structure was evaluated across various operations using a custom instrumentation metric class (`Metrics`) that tracks operation steps, element comparisons, and element moves.

---

## Theoretical vs Empirical Complexity

| Data Structure | Operation | Theoretical Complexity | Empirical Observation |
| :--- | :--- | :--- | :--- |
| **Dynamic Array** | Add (Append) | $O(1)$ amortized | Fast, steps grow linearly with array reallocations |
| **Dynamic Array** | Get (by index) | $O(1)$ | Constant number of steps regardless of $N$ |
| **Dynamic Array** | Remove (at head)| $O(N)$ | Steps and moves scale linearly $O(N)$ due to element shifting |
| **MyLinkedList** | Add (Append) | $O(N)$ | Requires full traversal to reach tail ($O(N)$ steps) |
| **MyLinkedList** | Get (by index) | $O(N)$ | Traverses nodes sequentially ($O(N)$ steps) |
| **MyLinkedList** | Remove (at head)| $O(1)$ | Direct pointer reassignment ($O(1)$ steps) |
| **MinHeap** | Insert | $O(\log N)$ | Logarithmic comparisons and moves during heapify-up |
| **MinHeap** | ExtractMin | $O(\log N)$ | Logarithmic comparisons and moves during heapify-down |

---

## Benchmark Results Analysis

1. **Dynamic Array:**
    - **Access ($O(1)$):** Extremely efficient due to direct memory indexing.
    - **Insertion ($O(1)$ Amortized):** Fast overall, with periodic spikes when resizing array buffer.
    - **Deletion at Head ($O(N)$):** High number of element moves due to left-shifting contiguous memory.

2. **Singly Linked List:**
    - **Access ($O(N)$):** Slow for large $N$ because each access requires pointer traversal from `head`.
    - **Deletion at Head ($O(1)$):** Outperforms Dynamic Array significantly as no elements need shifting.

3. **Min-Heap:**
    - **Insertion & Extraction ($O(\log N)$):** Maintains strict heap invariant efficiently. Steps and comparisons scale logarithmically as data size increases up to $100,000$.

---

## How to Run
1. **Run Unit Tests:**
   ```bash
   mvn test