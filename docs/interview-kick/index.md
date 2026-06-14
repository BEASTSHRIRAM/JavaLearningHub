---
title: InterviewKick
description: Curated interview questions and answers from big tech companies — Java Core, DSA patterns, System Design, Spring Boot, DBMS, OS, Networks, and behavioral questions.
---

# InterviewKick

Curated interview questions and detailed answers from companies like Google, Amazon, Microsoft, Meta, and top startups. Each answer includes a short response (for quick recall) and a detailed explanation.

---

## Java Core

### Q1: What is the difference between `==` and `.equals()` in Java?

**Short answer:** `==` compares references (memory addresses). `.equals()` compares values (content).

**Detailed explanation:** For primitive types, `==` compares values directly. For objects, `==` checks if both variables point to the same object in memory. The `.equals()` method (from `Object`) can be overridden to compare content. `String`, `Integer`, and most wrapper classes override `.equals()` to compare values. If you create a custom class, you must override both `.equals()` and `.hashCode()` to get correct behavior in collections.

```java
String a = new String("hello");
String b = new String("hello");
System.out.println(a == b);       // false (different objects)
System.out.println(a.equals(b));  // true (same content)

String c = "hello";
String d = "hello";
System.out.println(c == d);       // true (string pool — same reference)
```

**Follow-up:** Why must you override `hashCode()` when you override `equals()`? Because `HashMap`/`HashSet` use `hashCode()` first to find the bucket, then `equals()` to find the exact match. If two equal objects have different hash codes, lookups fail.

---

### Q2: Explain the difference between ArrayList and LinkedList.

**Short answer:** `ArrayList` uses a dynamic array (fast random access, slow insertion in the middle). `LinkedList` uses a doubly-linked list (fast insertion/deletion, slow random access).

| Operation | ArrayList | LinkedList |
|-----------|-----------|------------|
| `get(index)` | O(1) | O(n) |
| `add(end)` | O(1) amortized | O(1) |
| `add(middle)` | O(n) | O(1)* |
| `remove(middle)` | O(n) | O(1)* |
| Memory overhead | Low | High (node + 2 pointers) |
| Cache performance | Excellent | Poor |

*O(1) only if you already have a reference to the node. Finding the node is O(n).

**When to use which:** Use `ArrayList` in 95% of cases. Use `LinkedList` only when you need frequent insertion/deletion at the beginning or have a `Deque` use case.

---

### Q3: What is the difference between HashMap and ConcurrentHashMap?

**Short answer:** `HashMap` is not thread-safe. `ConcurrentHashMap` allows concurrent reads and segment-level locking for writes.

**Detailed explanation:** `HashMap` can throw `ConcurrentModificationException` or corrupt data when accessed from multiple threads. `ConcurrentHashMap` divides the map into segments (Java 7) or uses CAS operations with node-level locking (Java 8+), allowing multiple threads to read and write concurrently without external synchronization. It does not allow null keys or values (unlike `HashMap`).

---

### Q4: Explain Java's Garbage Collection.

**Short answer:** The JVM automatically reclaims memory from objects that are no longer reachable. It uses generational collection (Young Gen → Old Gen) and different algorithms (G1, ZGC, Shenandoah).

**Key concepts:**

- **Young Generation:** Short-lived objects. Minor GC collects here (fast, frequent)
- **Old Generation:** Long-lived objects promoted from Young Gen. Major GC collects here (slower)
- **G1 GC (default in Java 9+):** Divides heap into regions, collects regions with most garbage first
- **ZGC:** Sub-millisecond pause times, suitable for low-latency applications

**Follow-up:** How do you diagnose memory leaks? Use `-Xmx` and `-Xms` flags, enable GC logging with `-Xlog:gc`, use profilers like VisualVM or JProfiler, take heap dumps with `jmap`.

---

### Q5: What is the volatile keyword?

**Short answer:** `volatile` guarantees visibility — changes to a volatile variable are immediately visible to all threads. It prevents caching of the variable in CPU registers.

**Important:** `volatile` does NOT guarantee atomicity. `count++` on a volatile variable is still not thread-safe (it's a read-modify-write operation). Use `AtomicInteger` for atomic operations.

---

### Q6: Explain the difference between abstract class and interface (Java 8+).

| Feature | Abstract Class | Interface |
|---------|---------------|-----------|
| Methods | Abstract and concrete | Abstract, default, static |
| Variables | Any type | Only `public static final` |
| Constructor | Yes | No |
| Inheritance | Single (extends) | Multiple (implements) |
| Access modifiers | Any | Public only (methods) |
| When to use | Shared state + behavior | Contract / capability |

**Rule of thumb:** Use interface to define *what* a class can do. Use abstract class to define *what* a class *is* and share common implementation.

---

## DSA Interview Patterns

### Pattern 1: Two Pointers

**When to use:** Sorted arrays, finding pairs, palindromes, removing duplicates.

**Example:** Given a sorted array, find two numbers that add up to a target.

```java
public int[] twoSum(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    while (left < right) {
        int sum = nums[left] + nums[right];
        if (sum == target) return new int[]{left, right};
        else if (sum < target) left++;
        else right--;
    }
    return new int[]{-1, -1};
}
// Time: O(n), Space: O(1)
```

**Company frequency:** Amazon, Google, Microsoft — almost every interview.

---

### Pattern 2: Sliding Window

**When to use:** Subarray/substring problems, finding max/min in a window, contiguous sequences.

**Example:** Find the maximum sum subarray of size k.

```java
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

---

### Pattern 3: BFS/DFS

**When to use:** Trees, graphs, connected components, shortest path (BFS), exhaustive search (DFS).

**Company-specific problems:**

| Company | Problem | Technique |
|---------|---------|-----------|
| Google | Word Ladder | BFS shortest path |
| Amazon | Number of Islands | DFS/BFS grid |
| Meta | Clone Graph | BFS + HashMap |
| Microsoft | Course Schedule | Topological sort (DFS) |

---

### Pattern 4: Dynamic Programming

**When to use:** Optimization problems with overlapping subproblems. Look for "minimum cost", "maximum profit", "number of ways".

**Steps to solve DP:**
1. Define the state: What does `dp[i]` represent?
2. Find the recurrence: How does `dp[i]` relate to previous states?
3. Identify the base case
4. Determine the iteration order
5. Optimize space if possible

---

## System Design (Simplified)

### URL Shortener (TinyURL)

**Requirements:** Convert long URL to short URL, redirect short URL to original.

**High-level design:**

```
Client → API Gateway → URL Service → Database
                                   → Cache (Redis)
```

**Key decisions:**

- **ID generation:** Base62 encoding of auto-increment ID (62^7 = 3.5 trillion URLs)
- **Storage:** PostgreSQL for persistence, Redis for caching hot URLs
- **Read-heavy:** 100:1 read-to-write ratio → cache extensively
- **API:** `POST /api/shorten` (create), `GET /{shortCode}` (redirect with 301)

---

### Rate Limiter

**Problem:** Limit API requests to N per time window per user.

**Algorithms:**

| Algorithm | How It Works | Pros | Cons |
|-----------|-------------|------|------|
| Fixed Window | Count requests in fixed time slots | Simple | Burst at window edges |
| Sliding Window Log | Store timestamp of each request | Accurate | Memory-heavy |
| Token Bucket | Tokens added at fixed rate, consumed per request | Smooth, bursty-friendly | Slightly complex |
| Sliding Window Counter | Weighted average of current + previous window | Good balance | Approximate |

---

## Spring Boot Interview Questions

### Q1: Explain Dependency Injection in Spring.

**Short answer:** DI is a design pattern where objects receive their dependencies from an external source (the Spring container) rather than creating them. This enables loose coupling, testability, and modularity.

**Three injection types:**

```java
// 1. Constructor injection (RECOMMENDED)
@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {  // Injected by Spring
        this.repo = repo;
    }
}

// 2. Setter injection
@Autowired
public void setRepo(UserRepository repo) { this.repo = repo; }

// 3. Field injection (DISCOURAGED — untestable)
@Autowired
private UserRepository repo;
```

### Q2: What is the Spring Bean lifecycle?

1. **Instantiation** — Spring creates the bean object
2. **Populate properties** — Inject dependencies
3. **BeanNameAware** — Set bean name
4. **BeanFactoryAware** — Set bean factory
5. **Pre-initialization** — `@PostConstruct` or `afterPropertiesSet()`
6. **Custom init** — `@Bean(initMethod = "init")`
7. **Ready for use**
8. **Pre-destroy** — `@PreDestroy`
9. **Custom destroy** — `@Bean(destroyMethod = "cleanup")`

### Q3: Difference between @Controller and @RestController?

`@RestController` = `@Controller` + `@ResponseBody`. It automatically serializes return values to JSON/XML. `@Controller` returns view names (for server-side rendering with Thymeleaf, JSP).

---

## DBMS Interview Questions

### Q1: What is normalization? Explain up to 3NF.

- **1NF:** Atomic values, no repeating groups. Each cell has one value.
- **2NF:** 1NF + no partial dependency. Every non-key column depends on the entire primary key.
- **3NF:** 2NF + no transitive dependency. Non-key columns don't depend on other non-key columns.

### Q2: What is an index? When should you NOT use one?

An index is a B-tree (or hash) structure that speeds up SELECT queries. Don't index:

- Small tables (full scan is faster)
- Columns with low cardinality (e.g., boolean flags)
- Tables with heavy INSERT/UPDATE (index maintenance overhead)
- Columns rarely used in WHERE/JOIN/ORDER BY

### Q3: Write a SQL query to find the second highest salary.

```sql
-- Method 1: Subquery
SELECT MAX(salary) FROM employees
WHERE salary < (SELECT MAX(salary) FROM employees);

-- Method 2: DENSE_RANK (handles ties)
SELECT salary FROM (
    SELECT salary, DENSE_RANK() OVER (ORDER BY salary DESC) AS rank
    FROM employees
) ranked
WHERE rank = 2;
```

---

## OS and Networks Questions

### Q1: What is a deadlock? How do you prevent it?

A deadlock occurs when two or more processes wait indefinitely for resources held by each other. Four conditions must hold simultaneously: mutual exclusion, hold and wait, no preemption, circular wait.

**Prevention:** Break any one condition. Most practical: impose a global ordering on resource acquisition (all threads lock resources in the same order).

### Q2: Explain the TCP 3-way handshake.

1. **SYN:** Client sends a SYN packet with initial sequence number
2. **SYN-ACK:** Server acknowledges with SYN-ACK and its own sequence number
3. **ACK:** Client sends ACK, connection established

**Follow-up:** Why 3 steps? Both sides need to confirm they can send AND receive. Two steps would only confirm one direction.

### Q3: What happens when you type google.com in a browser?

1. Browser checks cache for DNS record
2. DNS resolution: recursive resolver → root → TLD → authoritative server → IP address
3. TCP connection established (3-way handshake)
4. TLS handshake (for HTTPS)
5. HTTP GET request sent
6. Server processes request, returns HTML
7. Browser parses HTML, fetches CSS/JS/images
8. Browser renders the page (DOM + CSSOM → render tree → layout → paint)

---

## Behavioral Questions

### STAR Method

Structure your answers as: **Situation → Task → Action → Result**

### Common Questions

**"Tell me about a challenging project."**
Focus on: technical complexity, what you learned, measurable outcome.

**"How do you handle disagreements with teammates?"**
Focus on: listen first, data-driven arguments, compromise, team goal.

**"Describe a time you failed."**
Focus on: what went wrong, what you learned, how you changed your approach.

### Tips for Big Tech Interviews

1. **Think out loud.** Interviewers evaluate your thought process, not just the answer.
2. **Clarify before coding.** Ask about edge cases, constraints, expected input size.
3. **Start with brute force.** Then optimize. This shows structured thinking.
4. **Test your solution.** Walk through with a small example before saying "done."
5. **Know your resume.** Be ready to discuss every project and technology listed.
6. **Practice mock interviews.** Use platforms like Pramp, Interviewing.io.
