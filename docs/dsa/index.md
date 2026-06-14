---
title: Data Structures & Algorithms
description: A theory-first approach to mastering DSA — understand what each data structure is, why it matters, where it's used, then practice with code and LeetCode problems.
---

# Data Structures & Algorithms

A structured, theory-first approach to mastering DSA. Each topic covers **what it is**, **why it matters**, **real-world applications**, **implementation**, and **practice problems**.

<span class="badge badge-intermediate">Intermediate – Advanced</span> · Ongoing Practice

---

## Introduction

Data Structures and Algorithms are the foundation of efficient software engineering. They determine how data is organized, stored, and manipulated — and how efficiently your programs run.

### Why DSA Matters

- Write code that scales from 100 to 100 million users
- Pass technical interviews at any company
- Understand the performance characteristics of your code
- Choose the right tool for the right problem
- Think systematically about problem-solving

### Big O Notation

Big O describes how an algorithm's runtime or space usage grows relative to input size. It captures the **worst-case** growth rate, ignoring constants and lower-order terms.

| Notation | Name | Example | Practical Impact |
|----------|------|---------|-----------------|
| O(1) | Constant | Array access by index, HashMap lookup | Instant, regardless of data size |
| O(log n) | Logarithmic | Binary Search | Halves the problem each step |
| O(n) | Linear | Iterating through an array | Proportional to data size |
| O(n log n) | Linearithmic | Merge Sort, Quick Sort (avg) | Optimal for comparison-based sorting |
| O(n^2) | Quadratic | Nested loops, Bubble Sort | Slows dramatically with large input |
| O(2^n) | Exponential | Recursive Fibonacci (naive) | Impractical for n > 30 |
| O(n!) | Factorial | Generating all permutations | Only feasible for tiny inputs |

**How to analyze:** Count the dominant operation (comparisons, swaps, iterations) as a function of input size `n`. Drop constants and lower-order terms.

---

## Arrays and Strings

### What is an Array?

An array is a **contiguous block of memory** that stores elements of the same type in sequential positions. Each element is accessed by its **index** (position number), starting from 0.

**Memory Layout:**
```
Index:    [0]   [1]   [2]   [3]   [4]
Value:    |  5  | 12  |  3  |  8  | 21  |
Address:  1000  1004  1008  1012  1016
          (each int = 4 bytes)
```

Because elements are stored contiguously, the address of any element can be computed directly:
`address = base_address + (index × element_size)` — this is why array access is O(1).

### Why Arrays Matter

- **Foundation of computing:** Most data structures (stacks, queues, heaps, hash tables) are built on arrays internally
- **Cache-friendly:** Contiguous memory means CPU cache lines load adjacent elements automatically, making iteration very fast
- **Fixed-size trade-off:** Arrays have fixed capacity (in most languages), which means no overhead for resizing, but you must know the size upfront

### Real-World Applications

- **Image processing:** A 1920×1080 image is a 2D array of pixel values
- **Database records:** Rows stored as arrays of column values
- **Audio/signal processing:** Sound waves as arrays of amplitude samples
- **Game boards:** Chess, Sudoku, Tic-Tac-Toe — all 2D arrays

### Strings in Java

A String in Java is internally a `char[]` array (or `byte[]` in Java 9+). Strings are **immutable** — once created, they cannot be changed. This has important implications:

- String concatenation in a loop creates O(n) new String objects — use `StringBuilder` instead
- String comparison with `==` checks reference equality, not value — use `.equals()`
- The String pool: Java interns string literals to save memory

### Core Operations and Complexity

| Operation | Array | ArrayList | String |
|-----------|-------|-----------|--------|
| Access by index | O(1) | O(1) | O(1) |
| Search (unsorted) | O(n) | O(n) | O(n) |
| Insert at end | O(1)* | O(1) amortized | O(n) (new string) |
| Insert at middle | O(n) | O(n) | O(n) |
| Delete | O(n) | O(n) | O(n) |

### Implementation

```java
// Array declaration and initialization
int[] numbers = {10, 20, 30, 40, 50};

// Access: O(1)
System.out.println(numbers[2]); // 30

// Search: O(n) — must scan linearly
int target = 40;
for (int i = 0; i < numbers.length; i++) {
    if (numbers[i] == target) {
        System.out.println("Found at index " + i);
        break;
    }
}

// Two-pointer technique (common pattern)
public boolean isPalindrome(String s) {
    int left = 0, right = s.length() - 1;
    while (left < right) {
        if (s.charAt(left) != s.charAt(right)) return false;
        left++;
        right--;
    }
    return true;
}

// Sliding window technique (common pattern)
public int maxSubarraySum(int[] arr, int k) {
    int windowSum = 0, maxSum = Integer.MIN_VALUE;
    for (int i = 0; i < arr.length; i++) {
        windowSum += arr[i];
        if (i >= k - 1) {
            maxSum = Math.max(maxSum, windowSum);
            windowSum -= arr[i - (k - 1)];
        }
    }
    return maxSum;
}
```

### Practice Problems

| Problem | Difficulty | Key Technique | Link |
|---------|-----------|---------------|------|
| Two Sum | Easy | HashMap lookup | [LeetCode #1](https://leetcode.com/problems/two-sum/){ target=_blank } |
| Best Time to Buy and Sell Stock | Easy | Single pass, track min | [LeetCode #121](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/){ target=_blank } |
| Container With Most Water | Medium | Two pointers | [LeetCode #11](https://leetcode.com/problems/container-with-most-water/){ target=_blank } |
| 3Sum | Medium | Sort + two pointers | [LeetCode #15](https://leetcode.com/problems/3sum/){ target=_blank } |
| Longest Substring Without Repeating | Medium | Sliding window | [LeetCode #3](https://leetcode.com/problems/longest-substring-without-repeating-characters/){ target=_blank } |
| Trapping Rain Water | Hard | Two pointers / stack | [LeetCode #42](https://leetcode.com/problems/trapping-rain-water/){ target=_blank } |

---

## Linked Lists

### What is a Linked List?

A linked list is a **linear data structure** where each element (node) contains two things: a **value** and a **pointer** (reference) to the next node. Unlike arrays, elements are **not stored contiguously** in memory — each node can be anywhere in the heap.

```
Head → [10 | →] → [20 | →] → [30 | →] → [40 | null]
        Node 1      Node 2      Node 3      Node 4
```

**Types:**

- **Singly Linked List:** Each node points to the next node only
- **Doubly Linked List:** Each node points to both next and previous
- **Circular Linked List:** The last node points back to the first

### Why Linked Lists Matter

- **Dynamic size:** No need to declare size upfront — grows and shrinks as needed
- **Efficient insertion/deletion:** Adding or removing from the beginning or middle is O(1) if you have a reference to the node (vs O(n) for arrays which must shift elements)
- **Trade-off:** No random access (must traverse from head), so access is O(n) instead of O(1)

### Arrays vs Linked Lists

| Feature | Array | Linked List |
|---------|-------|-------------|
| Memory layout | Contiguous | Scattered (heap) |
| Access by index | O(1) | O(n) |
| Insert at beginning | O(n) | O(1) |
| Insert at end | O(1) amortized | O(n) or O(1) with tail |
| Delete from beginning | O(n) | O(1) |
| Memory overhead | None | Extra pointer per node |
| Cache performance | Excellent | Poor |

### Real-World Applications

- **Music playlist:** Each song points to the next — easy to insert/remove songs
- **Browser history:** Back/forward navigation (doubly linked list)
- **Undo/Redo:** Each state points to the previous state
- **Memory allocation:** Free memory blocks managed as a linked list by the OS
- **Java's `LinkedList`:** Implements both `List` and `Deque` interfaces

### Implementation

```java
class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

class LinkedList {
    Node head;

    // Insert at beginning — O(1)
    void insertFirst(int data) {
        Node newNode = new Node(data);
        newNode.next = head;
        head = newNode;
    }

    // Insert at end — O(n)
    void insertLast(int data) {
        Node newNode = new Node(data);
        if (head == null) { head = newNode; return; }
        Node current = head;
        while (current.next != null) current = current.next;
        current.next = newNode;
    }

    // Delete by value — O(n)
    void delete(int data) {
        if (head == null) return;
        if (head.data == data) { head = head.next; return; }
        Node current = head;
        while (current.next != null && current.next.data != data) {
            current = current.next;
        }
        if (current.next != null) current.next = current.next.next;
    }

    // Reverse — O(n) time, O(1) space
    void reverse() {
        Node prev = null, current = head, next;
        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        head = prev;
    }

    // Detect cycle — Floyd's algorithm
    boolean hasCycle() {
        Node slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true;
        }
        return false;
    }
}
```

### Practice Problems

| Problem | Difficulty | Key Technique | Link |
|---------|-----------|---------------|------|
| Reverse Linked List | Easy | Iterative pointer swap | [LeetCode #206](https://leetcode.com/problems/reverse-linked-list/){ target=_blank } |
| Linked List Cycle | Easy | Floyd's slow/fast | [LeetCode #141](https://leetcode.com/problems/linked-list-cycle/){ target=_blank } |
| Merge Two Sorted Lists | Easy | Two-pointer merge | [LeetCode #21](https://leetcode.com/problems/merge-two-sorted-lists/){ target=_blank } |
| Remove Nth Node From End | Medium | Two pointers with gap | [LeetCode #19](https://leetcode.com/problems/remove-nth-node-from-end-of-list/){ target=_blank } |
| LRU Cache | Medium | HashMap + doubly linked list | [LeetCode #146](https://leetcode.com/problems/lru-cache/){ target=_blank } |

---

## Stacks and Queues

### What is a Stack?

A stack is a **Last-In-First-Out (LIFO)** data structure. Think of a stack of plates — you can only add to or remove from the top.

**Operations:** `push` (add to top), `pop` (remove from top), `peek` (look at top) — all O(1).

### What is a Queue?

A queue is a **First-In-First-Out (FIFO)** data structure. Think of a line at a ticket counter — first person in line gets served first.

**Operations:** `enqueue/add` (add to rear), `dequeue/remove` (remove from front), `peek` (look at front) — all O(1).

### Real-World Applications

**Stack applications:**

- **Function call stack:** Every method call pushes a frame; return pops it. This is why `StackOverflowError` happens with deep recursion.
- **Undo/Redo:** Each action pushed; undo pops the last action
- **Expression evaluation:** Parsing mathematical expressions, balancing parentheses
- **Browser back button:** Each visited page pushed to a stack
- **DFS traversal:** Implicitly uses a stack (recursive) or explicitly (iterative)

**Queue applications:**

- **Print queue:** Documents printed in order they arrive
- **Task scheduling:** OS CPU scheduler uses various queue types
- **BFS traversal:** Level-by-level graph exploration
- **Message queues:** Kafka, RabbitMQ — async communication between services
- **Rate limiting:** Request queues with time-based processing

### Implementation

```java
// Stack using Java's Deque (preferred over legacy Stack class)
Deque<Integer> stack = new ArrayDeque<>();
stack.push(10);    // Push
stack.push(20);
stack.peek();      // 20 (look without removing)
stack.pop();       // 20 (remove and return)

// Queue using Java's Queue interface
Queue<Integer> queue = new LinkedList<>();
queue.add(10);     // Enqueue
queue.add(20);
queue.peek();      // 10 (look without removing)
queue.remove();    // 10 (remove and return)

// Priority Queue (min-heap by default)
PriorityQueue<Integer> pq = new PriorityQueue<>();
pq.add(30);
pq.add(10);
pq.add(20);
pq.poll();         // 10 (smallest first)
```

### Practice Problems

| Problem | Difficulty | Key Technique | Link |
|---------|-----------|---------------|------|
| Valid Parentheses | Easy | Stack matching | [LeetCode #20](https://leetcode.com/problems/valid-parentheses/){ target=_blank } |
| Min Stack | Medium | Two stacks | [LeetCode #155](https://leetcode.com/problems/min-stack/){ target=_blank } |
| Implement Queue using Stacks | Easy | Two stacks | [LeetCode #232](https://leetcode.com/problems/implement-queue-using-stacks/){ target=_blank } |
| Daily Temperatures | Medium | Monotonic stack | [LeetCode #739](https://leetcode.com/problems/daily-temperatures/){ target=_blank } |
| Sliding Window Maximum | Hard | Monotonic deque | [LeetCode #239](https://leetcode.com/problems/sliding-window-maximum/){ target=_blank } |

---

## Trees and Binary Search Trees

### What is a Tree?

A tree is a **hierarchical data structure** consisting of nodes connected by edges, with one designated **root** node. Each node can have zero or more child nodes, but every node (except the root) has exactly one parent.

**Key terminology:**

- **Root:** The topmost node (has no parent)
- **Leaf:** A node with no children
- **Height:** The number of edges on the longest path from a node to a leaf
- **Depth:** The number of edges from the root to a node
- **Subtree:** A node and all its descendants
- **Balanced tree:** A tree where the height difference between left and right subtrees is at most 1

### Why Trees Matter

Trees model **hierarchical relationships** — one of the most common patterns in computing:

- **File systems:** Directories contain files and subdirectories
- **HTML DOM:** Nested elements form a tree
- **Database indexes:** B-trees and B+ trees enable O(log n) lookups in databases
- **Decision trees:** ML models for classification
- **Syntax trees:** Compilers parse code into Abstract Syntax Trees (AST)
- **Organization charts:** Reporting hierarchies

### Binary Search Tree (BST)

A BST is a binary tree where for every node: **all values in the left subtree < node value < all values in the right subtree**. This property enables O(log n) search, insert, and delete on average.

```
        8
       / \
      3   10
     / \    \
    1   6    14
       / \   /
      4   7 13
```

Searching for 7: Start at 8 → go left (7 < 8) → go right (7 > 3) → go right (7 > 6) → found.

### Implementation

```java
class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}

class BST {
    TreeNode root;

    // Insert — O(log n) average, O(n) worst
    TreeNode insert(TreeNode node, int val) {
        if (node == null) return new TreeNode(val);
        if (val < node.val) node.left = insert(node.left, val);
        else node.right = insert(node.right, val);
        return node;
    }

    // Search — O(log n) average
    boolean search(TreeNode node, int val) {
        if (node == null) return false;
        if (val == node.val) return true;
        return val < node.val ? search(node.left, val) : search(node.right, val);
    }
}
```

### Tree Traversals

Understanding traversal order is critical for interviews:

```java
// Inorder (Left → Root → Right) — gives SORTED order for BST
void inorder(TreeNode node) {
    if (node == null) return;
    inorder(node.left);
    System.out.print(node.val + " ");
    inorder(node.right);
}

// Preorder (Root → Left → Right) — useful for copying/serializing trees
void preorder(TreeNode node) {
    if (node == null) return;
    System.out.print(node.val + " ");
    preorder(node.left);
    preorder(node.right);
}

// Postorder (Left → Right → Root) — useful for deleting trees
void postorder(TreeNode node) {
    if (node == null) return;
    postorder(node.left);
    postorder(node.right);
    System.out.print(node.val + " ");
}

// Level Order (BFS) — processes level by level
void levelOrder(TreeNode root) {
    if (root == null) return;
    Queue<TreeNode> q = new LinkedList<>();
    q.add(root);
    while (!q.isEmpty()) {
        TreeNode node = q.poll();
        System.out.print(node.val + " ");
        if (node.left != null) q.add(node.left);
        if (node.right != null) q.add(node.right);
    }
}
```

### Practice Problems

| Problem | Difficulty | Key Technique | Link |
|---------|-----------|---------------|------|
| Maximum Depth of Binary Tree | Easy | DFS recursion | [LeetCode #104](https://leetcode.com/problems/maximum-depth-of-binary-tree/){ target=_blank } |
| Validate BST | Medium | Inorder or range checking | [LeetCode #98](https://leetcode.com/problems/validate-binary-search-tree/){ target=_blank } |
| Binary Tree Level Order Traversal | Medium | BFS with queue | [LeetCode #102](https://leetcode.com/problems/binary-tree-level-order-traversal/){ target=_blank } |
| Lowest Common Ancestor | Medium | Recursive path finding | [LeetCode #236](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/){ target=_blank } |
| Serialize and Deserialize Binary Tree | Hard | BFS/DFS + string encoding | [LeetCode #297](https://leetcode.com/problems/serialize-and-deserialize-binary-tree/){ target=_blank } |

---

## Graphs

### What is a Graph?

A graph is a collection of **vertices** (nodes) connected by **edges** (links). Unlike trees, graphs can have **cycles**, and there is no root — any node can connect to any other node.

**Types:**

- **Directed:** Edges have a direction (A → B does not imply B → A)
- **Undirected:** Edges are bidirectional
- **Weighted:** Edges carry a cost/distance value
- **Unweighted:** All edges are equal

### Why Graphs Matter

Graphs model **relationships and networks** — arguably the most versatile data structure:

- **Social networks:** Users are vertices, friendships are edges
- **Maps / GPS:** Intersections are vertices, roads are weighted edges (distance/time)
- **Internet:** Routers and connections form a graph (this is how packets are routed)
- **Dependency management:** Package managers (Maven, npm) resolve dependency graphs
- **Recommendation systems:** "Users who liked X also liked Y" — bipartite graphs
- **Web crawling:** Pages linked to each other form a directed graph (Google's PageRank)

### Graph Representations

**Adjacency Matrix** — O(V^2) space, O(1) edge lookup:
```
     A  B  C  D
A [  0  1  1  0 ]
B [  1  0  0  1 ]
C [  1  0  0  1 ]
D [  0  1  1  0 ]
```

**Adjacency List** — O(V + E) space, more memory-efficient for sparse graphs:
```
A → [B, C]
B → [A, D]
C → [A, D]
D → [B, C]
```

### Graph Traversal

```java
// DFS — Depth-First Search: O(V + E)
// Explores as deep as possible before backtracking
// Uses: cycle detection, topological sort, connected components
void dfs(int node, Map<Integer, List<Integer>> graph, Set<Integer> visited) {
    visited.add(node);
    System.out.print(node + " ");
    for (int neighbor : graph.getOrDefault(node, List.of())) {
        if (!visited.contains(neighbor)) {
            dfs(neighbor, graph, visited);
        }
    }
}

// BFS — Breadth-First Search: O(V + E)
// Explores level by level (all neighbors first)
// Uses: shortest path (unweighted), level-order traversal
void bfs(int start, Map<Integer, List<Integer>> graph) {
    Set<Integer> visited = new HashSet<>();
    Queue<Integer> queue = new LinkedList<>();
    queue.add(start);
    visited.add(start);

    while (!queue.isEmpty()) {
        int node = queue.poll();
        System.out.print(node + " ");
        for (int neighbor : graph.getOrDefault(node, List.of())) {
            if (!visited.contains(neighbor)) {
                visited.add(neighbor);
                queue.add(neighbor);
            }
        }
    }
}
```

### Practice Problems

| Problem | Difficulty | Key Technique | Link |
|---------|-----------|---------------|------|
| Number of Islands | Medium | DFS/BFS grid traversal | [LeetCode #200](https://leetcode.com/problems/number-of-islands/){ target=_blank } |
| Clone Graph | Medium | BFS + HashMap | [LeetCode #133](https://leetcode.com/problems/clone-graph/){ target=_blank } |
| Course Schedule | Medium | Topological sort / cycle detection | [LeetCode #207](https://leetcode.com/problems/course-schedule/){ target=_blank } |
| Word Ladder | Hard | BFS shortest path | [LeetCode #127](https://leetcode.com/problems/word-ladder/){ target=_blank } |
| Network Delay Time | Medium | Dijkstra's algorithm | [LeetCode #743](https://leetcode.com/problems/network-delay-time/){ target=_blank } |

---

## Sorting Algorithms

### Why Sorting Matters

Sorting is one of the most fundamental operations in computer science. A sorted dataset enables binary search (O(log n) instead of O(n)), simplifies duplicate detection, and is a prerequisite for many algorithms.

### Comparison of Algorithms

| Algorithm | Best | Average | Worst | Space | Stable | When to Use |
|-----------|------|---------|-------|-------|--------|-------------|
| Bubble Sort | O(n) | O(n^2) | O(n^2) | O(1) | Yes | Never in production. Educational only. |
| Selection Sort | O(n^2) | O(n^2) | O(n^2) | O(1) | No | Small datasets where swaps are expensive |
| Insertion Sort | O(n) | O(n^2) | O(n^2) | O(1) | Yes | Nearly sorted data, small arrays |
| Merge Sort | O(n log n) | O(n log n) | O(n log n) | O(n) | Yes | When stability is required, linked lists |
| Quick Sort | O(n log n) | O(n log n) | O(n^2) | O(log n) | No | General purpose, in-memory sorting |
| Heap Sort | O(n log n) | O(n log n) | O(n log n) | O(1) | No | When O(1) space is required |

**Stability** means equal elements maintain their original relative order. This matters when sorting by multiple keys (e.g., sort by name, then by age — stable sort preserves name order within same age).

**Java's built-in sorting:** `Arrays.sort()` uses Dual-Pivot Quicksort for primitives and TimSort (hybrid merge + insertion sort) for objects.

### Merge Sort — Divide and Conquer

```java
void mergeSort(int[] arr, int left, int right) {
    if (left >= right) return;
    int mid = left + (right - left) / 2;
    mergeSort(arr, left, mid);
    mergeSort(arr, mid + 1, right);
    merge(arr, left, mid, right);
}

void merge(int[] arr, int left, int mid, int right) {
    int[] temp = new int[right - left + 1];
    int i = left, j = mid + 1, k = 0;
    while (i <= mid && j <= right) {
        temp[k++] = arr[i] <= arr[j] ? arr[i++] : arr[j++];
    }
    while (i <= mid) temp[k++] = arr[i++];
    while (j <= right) temp[k++] = arr[j++];
    System.arraycopy(temp, 0, arr, left, temp.length);
}
```

---

## Dynamic Programming

### What is Dynamic Programming?

Dynamic Programming (DP) is a technique for solving problems that have two properties:

1. **Overlapping Subproblems:** The same smaller problems are solved repeatedly
2. **Optimal Substructure:** The optimal solution can be built from optimal solutions of subproblems

DP avoids redundant computation by **storing results of subproblems** (memoization or tabulation).

### Memoization vs Tabulation

**Memoization (Top-Down):** Start with the original problem, break it down recursively, and cache results.

**Tabulation (Bottom-Up):** Start with the smallest subproblems, solve them iteratively, and build up to the answer.

```java
// Fibonacci — Naive recursion: O(2^n) — exponential!
int fibNaive(int n) {
    if (n <= 1) return n;
    return fibNaive(n - 1) + fibNaive(n - 2); // recalculates same values
}

// Fibonacci — Memoization: O(n) time, O(n) space
Map<Integer, Long> memo = new HashMap<>();
long fibMemo(int n) {
    if (n <= 1) return n;
    if (memo.containsKey(n)) return memo.get(n);
    long result = fibMemo(n - 1) + fibMemo(n - 2);
    memo.put(n, result);
    return result;
}

// Fibonacci — Tabulation: O(n) time, O(n) space
long fibTab(int n) {
    if (n <= 1) return n;
    long[] dp = new long[n + 1];
    dp[0] = 0; dp[1] = 1;
    for (int i = 2; i <= n; i++) dp[i] = dp[i-1] + dp[i-2];
    return dp[n];
}

// Fibonacci — Space-optimized: O(n) time, O(1) space
long fibOptimal(int n) {
    if (n <= 1) return n;
    long prev2 = 0, prev1 = 1;
    for (int i = 2; i <= n; i++) {
        long curr = prev1 + prev2;
        prev2 = prev1;
        prev1 = curr;
    }
    return prev1;
}
```

### Common DP Patterns

| Pattern | Example Problem | Core Idea |
|---------|----------------|-----------|
| 1D DP | Climbing Stairs, House Robber | State depends on previous 1-2 states |
| 2D DP | Unique Paths, Edit Distance | State depends on two dimensions (i, j) |
| Knapsack | 0/1 Knapsack, Coin Change | Include/exclude items to optimize value |
| String DP | Longest Common Subsequence | Compare characters from two strings |
| Interval DP | Matrix Chain Multiplication | Optimal way to partition an interval |

### Practice Problems

| Problem | Difficulty | Pattern | Link |
|---------|-----------|---------|------|
| Climbing Stairs | Easy | 1D DP | [LeetCode #70](https://leetcode.com/problems/climbing-stairs/){ target=_blank } |
| House Robber | Medium | 1D DP | [LeetCode #198](https://leetcode.com/problems/house-robber/){ target=_blank } |
| Coin Change | Medium | Knapsack | [LeetCode #322](https://leetcode.com/problems/coin-change/){ target=_blank } |
| Longest Common Subsequence | Medium | String DP | [LeetCode #1143](https://leetcode.com/problems/longest-common-subsequence/){ target=_blank } |
| Edit Distance | Medium | String DP | [LeetCode #72](https://leetcode.com/problems/edit-distance/){ target=_blank } |
| Longest Increasing Subsequence | Medium | 1D DP + binary search | [LeetCode #300](https://leetcode.com/problems/longest-increasing-subsequence/){ target=_blank } |

---

## Practice Resources

- [LeetCode](https://leetcode.com/){ target=_blank } — Primary practice platform
- [BEASTSHRIRAM/DSA-Java](https://github.com/BEASTSHRIRAM/DSA-Java){ target=_blank } — Curated DSA solutions in Java
- [NeetCode Roadmap](https://neetcode.io/roadmap){ target=_blank } — Structured problem sets by pattern
- [InterviewBit](https://www.interviewbit.com/){ target=_blank } — Interview-focused practice
- [GeeksforGeeks](https://www.geeksforgeeks.org/){ target=_blank } — Detailed explanations and theory
