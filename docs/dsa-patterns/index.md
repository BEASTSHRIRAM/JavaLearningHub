---
title: DSA Patterns
description: A comprehensive reference for identifying, understanding, and applying common DSA patterns in coding interviews.
---

# DSA Patterns — Complete Study Guide

> A comprehensive reference for identifying, understanding, and applying common DSA patterns in coding interviews.

---

## How to Identify & Apply Patterns

| Signal in the problem | Likely pattern |
|---|---|
| Sorted array, find pair/triplet | Two Pointers |
| Cycle detection in list/array | Fast & Slow Pointers |
| Contiguous subarray, substring of size k | Sliding Window |
| "Maximum/minimum subarray sum" | Kadane's Algorithm |
| Running sum, divisibility, range queries | Prefix Sum |
| Overlapping ranges, scheduling | Merge Intervals |
| Reverse part of a linked list | In-place Reversal |
| Top/bottom K elements, median | Heap / Priority Queue |
| "How many ways", overlapping subproblems | Dynamic Programming |
| Level-by-level tree traversal | BFS / Tree Level Order |
| All combinations / permutations / subsets | Backtracking |
| Binary search on a sorted or "monotone" space | Binary Search |
| Shortest path in unweighted graph | BFS on Graph |
| Shortest path in weighted graph | Dijkstra / Bellman-Ford |
| Detect cycle in directed graph | DFS + Topological Sort |
| Repeated lookups, anagram checks | HashMap / HashSet |
| Parentheses matching, next greater element | Stack |
| Prefix matching on strings | Trie |
| Connectivity / grouping / components | Union-Find |

---

## 1. Two Pointers

**When to use:** Sorted array or linked list. You need to find a pair, triplet, or partition elements with O(1) extra space.

**Core technique:** Start one pointer at the left (`i = 0`) and one at the right (`j = n-1`). Move them toward each other based on a comparison.

**Template:**
```python
left, right = 0, len(arr) - 1
while left < right:
    if condition(arr[left], arr[right]):
        # found answer
    elif arr[left] + arr[right] < target:
        left += 1
    else:
        right -= 1
```

| Question | Difficulty | Link |
|---|---|---|
| Pair with Target Sum | Easy | [LeetCode](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/description/) |
| Rearrange 0s and 1s | Easy | [GFG](https://www.geeksforgeeks.org/problems/segregate-0s-and-1s5106/1) |
| Remove Duplicates from Sorted Array | Easy | [LeetCode](https://leetcode.com/problems/remove-duplicates-from-sorted-array/description/) |
| Remove Duplicates II | Medium | [LeetCode](https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/) |
| Squaring a Sorted Array | Easy | [LeetCode](https://leetcode.com/problems/squares-of-a-sorted-array/) |
| Triplet Sum to Zero (3Sum) | Medium | [LeetCode](https://leetcode.com/problems/3sum/) |
| Triplet Sum Closest to Target | Medium | [LeetCode](https://leetcode.com/problems/3sum-closest/) |
| Triplets with Smaller Sum | Medium | [GFG](https://www.geeksforgeeks.org/problems/count-triplets-with-sum-smaller-than-x5549/1) |
| Subarray Product Less Than K | Medium | [LeetCode](https://leetcode.com/problems/subarray-product-less-than-k/) |
| Dutch National Flag (Sort Colors) | Medium | [LeetCode](https://leetcode.com/problems/sort-colors/description/) |
| Quadruple Sum to Target (4Sum) | Medium | [LeetCode](https://leetcode.com/problems/4sum/) |
| Backspace String Compare | Medium | [LeetCode](https://leetcode.com/problems/backspace-string-compare/) |
| Minimum Window Sort | Medium | [LeetCode](https://leetcode.com/problems/shortest-unsorted-continuous-subarray/) |

---

## 2. Fast & Slow Pointers

**When to use:** Cycle detection in linked lists or arrays. Finding the middle of a list. Problems involving "meeting point."

**Core technique:** `slow` moves 1 step at a time, `fast` moves 2. If they meet → cycle exists. After detecting a cycle, reset one pointer to head to find the cycle start.

**Template:**
```python
slow, fast = head, head
while fast and fast.next:
    slow = slow.next
    fast = fast.next.next
    if slow == fast:
        # cycle detected
```

| Question | Difficulty | Link |
|---|---|---|
| LinkedList Cycle | Easy | [LeetCode](https://leetcode.com/problems/linked-list-cycle/) |
| Start of LinkedList Cycle | Medium | [LeetCode](https://leetcode.com/problems/linked-list-cycle-ii/) |
| Happy Number | Medium | [LeetCode](https://leetcode.com/problems/happy-number/) |
| Find the Duplicate Number | Medium | [LeetCode](https://leetcode.com/problems/find-the-duplicate-number/description/) |
| Middle of the LinkedList | Easy | [LeetCode](https://leetcode.com/problems/middle-of-the-linked-list/) |
| Palindrome LinkedList | Medium | [LeetCode](https://leetcode.com/problems/palindrome-linked-list/) |
| Reorder List | Medium | [LeetCode](https://leetcode.com/problems/reorder-list/) |
| Circular Array Loop | Hard | [LeetCode](https://leetcode.com/problems/circular-array-loop/) |

---

## 3. Sliding Window

**When to use:** Contiguous subarray or substring problems. Asked for maximum/minimum/longest/shortest within a window. Key signal: "subarray of size k" or "at most k distinct."

**Core technique (variable window):**
```python
left = 0
for right in range(len(arr)):
    # expand window by including arr[right]
    while window_is_invalid:
        # shrink from left
        left += 1
    update_answer()
```

| Question | Difficulty | Link |
|---|---|---|
| Maximum Sum Subarray of Size K | Easy | [GFG](https://www.geeksforgeeks.org/problems/max-sum-subarray-of-size-k5313/1) |
| Smallest Subarray with Given Sum | Easy | [LeetCode](https://leetcode.com/problems/minimum-size-subarray-sum/) |
| Longest Substring with K Distinct Characters | Medium | [GFG](https://www.geeksforgeeks.org/problems/longest-k-unique-characters-substring0853/1) |
| Fruit Into Baskets | Medium | [LeetCode](https://leetcode.com/problems/fruit-into-baskets/) |
| Longest Substring Without Repeating Characters | Hard | [LeetCode](https://leetcode.com/problems/longest-substring-without-repeating-characters/) |
| Longest Repeating Character Replacement | Hard | [LeetCode](https://leetcode.com/problems/longest-repeating-character-replacement/) |
| Max Consecutive Ones III | Hard | [LeetCode](https://leetcode.com/problems/max-consecutive-ones-iii/) |
| Minimum Window Substring | Hard | [LeetCode](https://leetcode.com/problems/minimum-window-substring/description/) |
| Permutation in String | Hard | [LeetCode](https://leetcode.com/problems/permutation-in-string/) |
| Find All Anagrams in a String | Hard | [LeetCode](https://leetcode.com/problems/find-all-anagrams-in-a-string/) |
| Substring with Concatenation of All Words | Hard | [LeetCode](https://leetcode.com/problems/substring-with-concatenation-of-all-words/) |

---

## 4. Kadane's Algorithm

**When to use:** Maximum (or minimum) sum contiguous subarray. Circular subarray variants. Variants that allow one deletion.

**Core technique:** Track a running sum. If it goes negative, reset it. Update global max at every step.

**Template:**
```python
max_sum = arr[0]
current = arr[0]
for x in arr[1:]:
    current = max(x, current + x)
    max_sum = max(max_sum, current)
```

| Question | Difficulty | Link |
|---|---|---|
| Maximum Subarray Sum | Easy | [LeetCode](https://leetcode.com/problems/maximum-subarray/) |
| Minimum Subarray Sum | Easy | [GFG](https://www.geeksforgeeks.org/problems/smallest-sum-contiguous-subarray/1) |
| Maximum Product Subarray | Medium | [LeetCode](https://leetcode.com/problems/maximum-product-subarray/) |
| Maximum Subarray Sum with One Deletion | Medium | [LeetCode](https://leetcode.com/problems/maximum-subarray-sum-with-one-deletion/description/) |
| Maximum Absolute Sum of Any Subarray | Medium | [LeetCode](https://leetcode.com/problems/maximum-absolute-sum-of-any-subarray/) |
| Maximum Sum Circular Subarray | Medium | [LeetCode](https://leetcode.com/problems/maximum-sum-circular-subarray/) |

---

## 5. Prefix Sum

**When to use:** Range sum queries. Counting subarrays with a target sum. Divisibility of subarrays. "Number of subarrays where sum equals k."

**Core technique:** Build a running sum array. Use a hashmap to track how many times each prefix sum has been seen.

**Template:**
```python
prefix = 0
count = 0
seen = {0: 1}
for x in arr:
    prefix += x
    count += seen.get(prefix - target, 0)
    seen[prefix] = seen.get(prefix, 0) + 1
```

| Question | Difficulty | Link |
|---|---|---|
| Subarray Sum Equals K | Easy | [LeetCode](https://leetcode.com/problems/subarray-sum-equals-k/description/) |
| Find Pivot Index | Easy | [LeetCode](https://leetcode.com/problems/find-pivot-index/description/) |
| Subarray Sums Divisible by K | Medium | [LeetCode](https://leetcode.com/problems/subarray-sums-divisible-by-k/description/) |
| Contiguous Array (equal 0s and 1s) | Medium | [LeetCode](https://leetcode.com/problems/contiguous-array/description/) |
| Shortest Subarray with Sum at Least K | Hard | [LeetCode](https://leetcode.com/problems/shortest-subarray-with-sum-at-least-k/description/) |
| Count of Range Sum | Hard | [LeetCode](https://leetcode.com/problems/count-of-range-sum/description/) |

---

## 6. Merge Intervals

**When to use:** Problems involving ranges, schedules, or time intervals. "Merge overlapping," "find free time," "minimum rooms needed."

**Core technique:** Sort by start time. Compare current interval's end with next interval's start.

**Template:**
```python
intervals.sort(key=lambda x: x[0])
merged = [intervals[0]]
for start, end in intervals[1:]:
    if start <= merged[-1][1]:
        merged[-1][1] = max(merged[-1][1], end)
    else:
        merged.append([start, end])
```

| Question | Difficulty | Link |
|---|---|---|
| Merge Intervals | Medium | [LeetCode](https://leetcode.com/problems/merge-intervals/description/) |
| Insert Interval | Medium | [LeetCode](https://leetcode.com/problems/insert-interval/) |
| Interval List Intersections | Medium | [LeetCode](https://leetcode.com/problems/interval-list-intersections/description/) |
| Check Overlapping Intervals | Easy | [GFG](https://www.geeksforgeeks.org/check-if-any-two-intervals-overlap-among-a-given-set-of-intervals/) |
| Minimum Meeting Rooms | Hard | [GFG](https://www.geeksforgeeks.org/problems/attend-all-meetings-ii/1) |
| Maximum CPU Load | Hard | [GFG](https://www.geeksforgeeks.org/maximum-cpu-load-from-the-given-list-of-jobs/) |
| Employee Free Time | Hard | [CoderTrain](https://www.codertrain.co/employee-free-time) |
| Non-overlapping Intervals | Medium | [LeetCode](https://leetcode.com/problems/non-overlapping-intervals/) |
| Meeting Rooms I | Easy | [LeetCode](https://leetcode.com/problems/meeting-rooms/) |

---

## 7. In-place Reversal of a LinkedList

**When to use:** Reverse a portion (or all) of a linked list without extra space. "Reverse every k-group," "rotate list."

**Core technique:** Keep track of `prev`, `current`, and `next`. Redirect `current.next` to `prev`, then advance.

**Template:**
```python
prev = None
current = head
while current:
    next_node = current.next
    current.next = prev
    prev = current
    current = next_node
return prev
```

| Question | Difficulty | Link |
|---|---|---|
| Reverse a LinkedList | Easy | [LeetCode](https://leetcode.com/problems/reverse-linked-list/) |
| Reverse a Sub-list | Medium | [LeetCode](https://leetcode.com/problems/reverse-linked-list-ii/) |
| Reverse Every K-element Sub-list | Medium | [LeetCode](https://leetcode.com/problems/reverse-nodes-in-k-group/) |
| Rotate a LinkedList | Medium | [LeetCode](https://leetcode.com/problems/rotate-list/) |
| Reverse Nodes in Even Length Groups | Medium | [LeetCode](https://leetcode.com/problems/reverse-nodes-in-even-length-groups/) |

---

## 8. Heap / Top-K Elements

**When to use:** "Top K," "K closest," "K largest/smallest," "Kth largest," "Median from a data stream." Use a min-heap of size K to track top-K largest elements.

**Core technique:**
- Top K largest → min-heap of size K (pop when size > K)
- Top K smallest → max-heap of size K
- Median → two heaps: max-heap for lower half, min-heap for upper half

| Question | Difficulty | Link |
|---|---|---|
| Kth Largest Element in an Array | Medium | [LeetCode](https://leetcode.com/problems/kth-largest-element-in-an-array/) |
| K Closest Points to Origin | Medium | [LeetCode](https://leetcode.com/problems/k-closest-points-to-origin/) |
| Top K Frequent Elements | Medium | [LeetCode](https://leetcode.com/problems/top-k-frequent-elements/) |
| Sort Characters by Frequency | Medium | [LeetCode](https://leetcode.com/problems/sort-characters-by-frequency/) |
| Kth Largest Element in a Stream | Easy | [LeetCode](https://leetcode.com/problems/kth-largest-element-in-a-stream/) |
| Find Median from Data Stream | Hard | [LeetCode](https://leetcode.com/problems/find-median-from-data-stream/) |
| Sliding Window Median | Hard | [LeetCode](https://leetcode.com/problems/sliding-window-median/) |
| Task Scheduler | Medium | [LeetCode](https://leetcode.com/problems/task-scheduler/) |
| Reorganize String | Medium | [LeetCode](https://leetcode.com/problems/reorganize-string/) |

---

## 9. Binary Search

**When to use:** Sorted array. "Find minimum/maximum satisfying a condition." Search space that is monotone (answer is feasible on one side, not the other). Key signal: O(log n) expected complexity.

**Template (find leftmost condition):**
```python
lo, hi = 0, len(arr) - 1
while lo < hi:
    mid = (lo + hi) // 2
    if condition(mid):
        hi = mid
    else:
        lo = mid + 1
return lo
```

| Question | Difficulty | Link |
|---|---|---|
| Binary Search | Easy | [LeetCode](https://leetcode.com/problems/binary-search/) |
| Search in Rotated Sorted Array | Medium | [LeetCode](https://leetcode.com/problems/search-in-rotated-sorted-array/) |
| Find Minimum in Rotated Sorted Array | Medium | [LeetCode](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/) |
| Find Peak Element | Medium | [LeetCode](https://leetcode.com/problems/find-peak-element/) |
| Kth Smallest Element in a Sorted Matrix | Medium | [LeetCode](https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/) |
| Search a 2D Matrix | Medium | [LeetCode](https://leetcode.com/problems/search-a-2d-matrix/) |
| Median of Two Sorted Arrays | Hard | [LeetCode](https://leetcode.com/problems/median-of-two-sorted-arrays/) |
| Split Array Largest Sum | Hard | [LeetCode](https://leetcode.com/problems/split-array-largest-sum/) |
| Capacity to Ship Packages Within D Days | Medium | [LeetCode](https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/) |
| Koko Eating Bananas | Medium | [LeetCode](https://leetcode.com/problems/koko-eating-bananas/) |

---

## 10. Tree BFS (Level Order)

**When to use:** Level-by-level traversal. "Average of levels," "right side view," "zigzag traversal," "connect level nodes."

**Core technique:** Use a queue. At each level, process all nodes currently in the queue (that's one level).

**Template:**
```python
from collections import deque
queue = deque([root])
while queue:
    level_size = len(queue)
    for _ in range(level_size):
        node = queue.popleft()
        # process node
        if node.left:  queue.append(node.left)
        if node.right: queue.append(node.right)
```

| Question | Difficulty | Link |
|---|---|---|
| Binary Tree Level Order Traversal | Medium | [LeetCode](https://leetcode.com/problems/binary-tree-level-order-traversal/) |
| Binary Tree Zigzag Level Order Traversal | Medium | [LeetCode](https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/) |
| Average of Levels in Binary Tree | Easy | [LeetCode](https://leetcode.com/problems/average-of-levels-in-binary-tree/) |
| Binary Tree Right Side View | Medium | [LeetCode](https://leetcode.com/problems/binary-tree-right-side-view/) |
| Minimum Depth of Binary Tree | Easy | [LeetCode](https://leetcode.com/problems/minimum-depth-of-binary-tree/) |
| Populating Next Right Pointers | Medium | [LeetCode](https://leetcode.com/problems/populating-next-right-pointers-in-each-node/) |
| Word Ladder | Hard | [LeetCode](https://leetcode.com/problems/word-ladder/) |

---

## 11. Tree DFS

**When to use:** Path sum problems. "All root-to-leaf paths," "validate BST," "lowest common ancestor," diameter problems.

**Core technique:** Recursive pre/in/post-order traversal. Pass state down (e.g. current sum) and accumulate results up.

| Question | Difficulty | Link |
|---|---|---|
| Path Sum | Easy | [LeetCode](https://leetcode.com/problems/path-sum/) |
| Path Sum II (all paths) | Medium | [LeetCode](https://leetcode.com/problems/path-sum-ii/) |
| Sum Root to Leaf Numbers | Medium | [LeetCode](https://leetcode.com/problems/sum-root-to-leaf-numbers/) |
| Diameter of Binary Tree | Easy | [LeetCode](https://leetcode.com/problems/diameter-of-binary-tree/) |
| Binary Tree Maximum Path Sum | Hard | [LeetCode](https://leetcode.com/problems/binary-tree-maximum-path-sum/) |
| Lowest Common Ancestor | Medium | [LeetCode](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/) |
| Validate Binary Search Tree | Medium | [LeetCode](https://leetcode.com/problems/validate-binary-search-tree/) |
| Construct Binary Tree from Preorder and Inorder | Medium | [LeetCode](https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/) |
| Serialize and Deserialize Binary Tree | Hard | [LeetCode](https://leetcode.com/problems/serialize-and-deserialize-binary-tree/) |

---

## 12. Backtracking

**When to use:** All combinations / permutations / subsets. Constraint satisfaction (N-Queens, Sudoku). "Generate all valid…"

**Core technique:** Make a choice → recurse → undo the choice (backtrack).

**Template:**
```python
def backtrack(start, path):
    if is_solution(path):
        results.append(list(path))
        return
    for choice in choices(start):
        path.append(choice)
        backtrack(next_start, path)
        path.pop()  # undo
```

| Question | Difficulty | Link |
|---|---|---|
| Subsets | Medium | [LeetCode](https://leetcode.com/problems/subsets/) |
| Subsets II (with duplicates) | Medium | [LeetCode](https://leetcode.com/problems/subsets-ii/) |
| Permutations | Medium | [LeetCode](https://leetcode.com/problems/permutations/) |
| Permutations II (with duplicates) | Medium | [LeetCode](https://leetcode.com/problems/permutations-ii/) |
| Combination Sum | Medium | [LeetCode](https://leetcode.com/problems/combination-sum/) |
| Combination Sum II | Medium | [LeetCode](https://leetcode.com/problems/combination-sum-ii/) |
| Letter Combinations of a Phone Number | Medium | [LeetCode](https://leetcode.com/problems/letter-combinations-of-a-phone-number/) |
| Generate Parentheses | Medium | [LeetCode](https://leetcode.com/problems/generate-parentheses/) |
| N-Queens | Hard | [LeetCode](https://leetcode.com/problems/n-queens/) |
| Sudoku Solver | Hard | [LeetCode](https://leetcode.com/problems/sudoku-solver/) |
| Word Search | Medium | [LeetCode](https://leetcode.com/problems/word-search/) |
| Palindrome Partitioning | Medium | [LeetCode](https://leetcode.com/problems/palindrome-partitioning/) |

---

## 13. Dynamic Programming

**When to use:** "How many ways," "minimum cost," "maximum profit," overlapping subproblems where brute force recurses into the same state multiple times.

**Key sub-patterns:**

| Sub-pattern | Trigger phrase | Classic problem |
|---|---|---|
| 0/1 Knapsack | "pick items with weight/value constraint" | [0/1 Knapsack](https://www.geeksforgeeks.org/0-1-knapsack-problem-dp-10/) |
| Unbounded Knapsack | "unlimited copies of each item" | [Coin Change](https://leetcode.com/problems/coin-change/) |
| LCS | "longest common subsequence/substring" | [LCS](https://leetcode.com/problems/longest-common-subsequence/) |
| LIS | "longest increasing subsequence" | [LIS](https://leetcode.com/problems/longest-increasing-subsequence/) |
| Matrix DP | grid path, island counting | [Unique Paths](https://leetcode.com/problems/unique-paths/) |
| Interval DP | "burst balloons," "matrix chain" | [Burst Balloons](https://leetcode.com/problems/burst-balloons/) |
| State Machine DP | buy/sell stock with cooldown/fee | [Best Time to Buy and Sell Stock IV](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/) |

| Question | Difficulty | Link |
|---|---|---|
| Climbing Stairs | Easy | [LeetCode](https://leetcode.com/problems/climbing-stairs/) |
| House Robber | Medium | [LeetCode](https://leetcode.com/problems/house-robber/) |
| House Robber II | Medium | [LeetCode](https://leetcode.com/problems/house-robber-ii/) |
| Longest Common Subsequence | Medium | [LeetCode](https://leetcode.com/problems/longest-common-subsequence/) |
| Longest Increasing Subsequence | Medium | [LeetCode](https://leetcode.com/problems/longest-increasing-subsequence/) |
| Coin Change | Medium | [LeetCode](https://leetcode.com/problems/coin-change/) |
| 0/1 Knapsack | Medium | [GFG](https://www.geeksforgeeks.org/0-1-knapsack-problem-dp-10/) |
| Partition Equal Subset Sum | Medium | [LeetCode](https://leetcode.com/problems/partition-equal-subset-sum/) |
| Edit Distance | Medium | [LeetCode](https://leetcode.com/problems/edit-distance/) |
| Unique Paths | Medium | [LeetCode](https://leetcode.com/problems/unique-paths/) |
| Minimum Path Sum | Medium | [LeetCode](https://leetcode.com/problems/minimum-path-sum/) |
| Word Break | Medium | [LeetCode](https://leetcode.com/problems/word-break/) |
| Burst Balloons | Hard | [LeetCode](https://leetcode.com/problems/burst-balloons/) |
| Best Time to Buy and Sell Stock IV | Hard | [LeetCode](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/) |
| Regular Expression Matching | Hard | [LeetCode](https://leetcode.com/problems/regular-expression-matching/) |

---

## 14. Graph BFS / DFS

**When to use:** Connected components, shortest path in unweighted graph, "number of islands," "rotting oranges," reachability.

**Template (BFS):**
```python
from collections import deque
visited = set([start])
queue = deque([start])
while queue:
    node = queue.popleft()
    for neighbor in graph[node]:
        if neighbor not in visited:
            visited.add(neighbor)
            queue.append(neighbor)
```

| Question | Difficulty | Link |
|---|---|---|
| Number of Islands | Medium | [LeetCode](https://leetcode.com/problems/number-of-islands/) |
| Max Area of Island | Medium | [LeetCode](https://leetcode.com/problems/max-area-of-island/) |
| Clone Graph | Medium | [LeetCode](https://leetcode.com/problems/clone-graph/) |
| Rotting Oranges | Medium | [LeetCode](https://leetcode.com/problems/rotting-oranges/) |
| Pacific Atlantic Water Flow | Medium | [LeetCode](https://leetcode.com/problems/pacific-atlantic-water-flow/) |
| Course Schedule (Cycle Detection) | Medium | [LeetCode](https://leetcode.com/problems/course-schedule/) |
| Course Schedule II (Topological Sort) | Medium | [LeetCode](https://leetcode.com/problems/course-schedule-ii/) |
| Word Ladder | Hard | [LeetCode](https://leetcode.com/problems/word-ladder/) |
| Alien Dictionary | Hard | [LeetCode](https://leetcode.com/problems/alien-dictionary/) |

---

## 15. Topological Sort

**When to use:** Ordering tasks with dependencies. "Course prerequisites," "build order," "detect cycle in directed graph."

**Core technique (Kahn's BFS):** Find all nodes with in-degree 0, process them, reduce neighbors' in-degrees, repeat.

| Question | Difficulty | Link |
|---|---|---|
| Course Schedule | Medium | [LeetCode](https://leetcode.com/problems/course-schedule/) |
| Course Schedule II | Medium | [LeetCode](https://leetcode.com/problems/course-schedule-ii/) |
| Alien Dictionary | Hard | [LeetCode](https://leetcode.com/problems/alien-dictionary/) |
| Sequence Reconstruction | Medium | [LeetCode](https://leetcode.com/problems/sequence-reconstruction/) |
| Minimum Height Trees | Medium | [LeetCode](https://leetcode.com/problems/minimum-height-trees/) |

---

## 16. Union-Find (Disjoint Set Union)

**When to use:** Group/connect components dynamically. "Number of connected components," "detect cycle in undirected graph," "accounts merge."

**Template:**
```python
parent = list(range(n))
rank = [0] * n

def find(x):
    if parent[x] != x:
        parent[x] = find(parent[x])  # path compression
    return parent[x]

def union(x, y):
    px, py = find(x), find(y)
    if px == py: return False
    if rank[px] < rank[py]: px, py = py, px
    parent[py] = px
    if rank[px] == rank[py]: rank[px] += 1
    return True
```

| Question | Difficulty | Link |
|---|---|---|
| Number of Connected Components | Medium | [LeetCode](https://leetcode.com/problems/number-of-connected-components-in-an-undirected-graph/) |
| Redundant Connection | Medium | [LeetCode](https://leetcode.com/problems/redundant-connection/) |
| Accounts Merge | Medium | [LeetCode](https://leetcode.com/problems/accounts-merge/) |
| Most Stones Removed | Medium | [LeetCode](https://leetcode.com/problems/most-stones-removed-with-same-row-or-column/) |
| Number of Operations to Make Network Connected | Medium | [LeetCode](https://leetcode.com/problems/number-of-operations-to-make-network-connected/) |
| Satisfiability of Equality Equations | Medium | [LeetCode](https://leetcode.com/problems/satisfiability-of-equality-equations/) |

---

## 17. Monotonic Stack

**When to use:** "Next greater element," "next smaller element," "largest rectangle in histogram," "trapping rain water." The stack maintains a monotonically increasing or decreasing order.

**Template (next greater element):**
```python
stack = []
result = [-1] * len(arr)
for i, val in enumerate(arr):
    while stack and arr[stack[-1]] < val:
        result[stack.pop()] = val
    stack.append(i)
```

| Question | Difficulty | Link |
|---|---|---|
| Next Greater Element I | Easy | [LeetCode](https://leetcode.com/problems/next-greater-element-i/) |
| Next Greater Element II (circular) | Medium | [LeetCode](https://leetcode.com/problems/next-greater-element-ii/) |
| Daily Temperatures | Medium | [LeetCode](https://leetcode.com/problems/daily-temperatures/) |
| Largest Rectangle in Histogram | Hard | [LeetCode](https://leetcode.com/problems/largest-rectangle-in-histogram/) |
| Trapping Rain Water | Hard | [LeetCode](https://leetcode.com/problems/trapping-rain-water/) |
| Maximal Rectangle | Hard | [LeetCode](https://leetcode.com/problems/maximal-rectangle/) |
| Remove K Digits | Medium | [LeetCode](https://leetcode.com/problems/remove-k-digits/) |
| Sum of Subarray Minimums | Medium | [LeetCode](https://leetcode.com/problems/sum-of-subarray-minimums/) |

---

## 18. Trie

**When to use:** Prefix search, autocomplete, "word search in a board," "longest common prefix," problems involving a dictionary of words.

**Template:**
```python
class TrieNode:
    def __init__(self):
        self.children = {}
        self.is_end = False

class Trie:
    def __init__(self): self.root = TrieNode()
    def insert(self, word):
        node = self.root
        for ch in word:
            node = node.children.setdefault(ch, TrieNode())
        node.is_end = True
    def search(self, word):
        node = self.root
        for ch in word:
            if ch not in node.children: return False
            node = node.children[ch]
        return node.is_end
```

| Question | Difficulty | Link |
|---|---|---|
| Implement Trie | Medium | [LeetCode](https://leetcode.com/problems/implement-trie-prefix-tree/) |
| Word Search II | Hard | [LeetCode](https://leetcode.com/problems/word-search-ii/) |
| Design Add and Search Words Data Structure | Medium | [LeetCode](https://leetcode.com/problems/design-add-and-search-words-data-structure/) |
| Replace Words | Medium | [LeetCode](https://leetcode.com/problems/replace-words/) |
| Maximum XOR of Two Numbers in an Array | Medium | [LeetCode](https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array/) |
| Longest Word in Dictionary | Medium | [LeetCode](https://leetcode.com/problems/longest-word-in-dictionary/) |

---

## 19. HashMap / HashSet

**When to use:** O(1) lookups. Anagram detection, two-sum variants, frequency counting, "first duplicate," "group by."

| Question | Difficulty | Link |
|---|---|---|
| Two Sum | Easy | [LeetCode](https://leetcode.com/problems/two-sum/) |
| Group Anagrams | Medium | [LeetCode](https://leetcode.com/problems/group-anagrams/) |
| Valid Anagram | Easy | [LeetCode](https://leetcode.com/problems/valid-anagram/) |
| Top K Frequent Elements | Medium | [LeetCode](https://leetcode.com/problems/top-k-frequent-elements/) |
| Longest Consecutive Sequence | Medium | [LeetCode](https://leetcode.com/problems/longest-consecutive-sequence/) |
| Intersection of Two Arrays | Easy | [LeetCode](https://leetcode.com/problems/intersection-of-two-arrays/) |
| Contains Duplicate II | Easy | [LeetCode](https://leetcode.com/problems/contains-duplicate-ii/) |
| Isomorphic Strings | Easy | [LeetCode](https://leetcode.com/problems/isomorphic-strings/) |

---

## 20. Bit Manipulation

**When to use:** Problems involving XOR (finding unique elements), counting set bits, checking powers of 2, subsets via bitmask.

**Common tricks:**

| Trick | Code |
|---|---|
| Check if bit i is set | `(n >> i) & 1` |
| Set bit i | `n \| (1 << i)` |
| Clear bit i | `n & ~(1 << i)` |
| Toggle bit i | `n ^ (1 << i)` |
| Remove lowest set bit | `n & (n - 1)` |
| Isolate lowest set bit | `n & (-n)` |
| XOR duplicates cancel out | `a ^ a == 0` |

| Question | Difficulty | Link |
|---|---|---|
| Single Number | Easy | [LeetCode](https://leetcode.com/problems/single-number/) |
| Single Number II | Medium | [LeetCode](https://leetcode.com/problems/single-number-ii/) |
| Number of 1 Bits | Easy | [LeetCode](https://leetcode.com/problems/number-of-1-bits/) |
| Counting Bits | Easy | [LeetCode](https://leetcode.com/problems/counting-bits/) |
| Reverse Bits | Easy | [LeetCode](https://leetcode.com/problems/reverse-bits/) |
| Power of Two | Easy | [LeetCode](https://leetcode.com/problems/power-of-two/) |
| Sum of Two Integers (no + operator) | Medium | [LeetCode](https://leetcode.com/problems/sum-of-two-integers/) |
| Missing Number | Easy | [LeetCode](https://leetcode.com/problems/missing-number/) |
| XOR Queries of a Subarray | Medium | [LeetCode](https://leetcode.com/problems/xor-queries-of-a-subarray/) |

---

## Quick Pattern Decision Tree

```
Is the input sorted?
├── Yes → Two Pointers or Binary Search
│         └── Finding pairs/triplets → Two Pointers
│         └── Finding a value / min-max condition → Binary Search
└── No  → Continue below

Is it a subarray/substring problem?
├── Fixed size window → Sliding Window (fixed)
├── Variable size window → Sliding Window (variable) or Prefix Sum
└── Max/min sum subarray → Kadane's

Is it a tree?
├── Level-by-level → BFS
└── Path / depth / ancestor → DFS

Is it a graph?
├── Shortest path, unweighted → BFS
├── Shortest path, weighted → Dijkstra
├── Dependency ordering → Topological Sort
└── Connectivity / grouping → Union-Find

Is it "how many ways" or "min cost"?
└── Dynamic Programming

Is it "all combinations / all subsets"?
└── Backtracking

Does it involve ranges/intervals?
└── Merge Intervals (sort by start time)

Is it about cycles in a linked list?
└── Fast & Slow Pointers

Is it about top/bottom K elements?
└── Heap

Does it involve prefix/word search?
└── Trie
```

---

*Last updated: June 2026. All LeetCode links point to the problem description page.*
