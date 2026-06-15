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

## Java Core (continued)

### Q7: What is the difference between checked and unchecked exceptions?

Short answer: Checked exceptions must be declared or caught at compile time (IOException, SQLException). Unchecked exceptions are runtime errors that the compiler does not force you to handle (NullPointerException, ArrayIndexOutOfBoundsException).

Detailed explanation: Checked exceptions extend Exception (but not RuntimeException) and represent recoverable conditions external to the program, like a missing file. Unchecked exceptions extend RuntimeException and usually signal programming bugs. Error and its subclasses (OutOfMemoryError, StackOverflowError) are unchecked too, and you generally should not try to catch these since they indicate the JVM itself is in trouble.

Follow up: Should you create custom checked exceptions? Most modern Java codebases avoid them because they force every caller up the chain to handle or rethrow, which clutters method signatures. Unchecked custom exceptions with clear messages are more common now.

---

### Q8: What is the difference between String, StringBuilder, and StringBuffer?

String is immutable, so every concatenation creates a new object. StringBuilder is mutable and not thread safe, fast for single threaded string building. StringBuffer is mutable and thread safe because its methods are synchronized, but that makes it slower.

```java
String s = "a";
s += "b"; // creates a new String object, old "a" becomes garbage

StringBuilder sb = new StringBuilder();
sb.append("a").append("b"); // modifies the same object
```

Rule of thumb: use StringBuilder unless you specifically need thread safety, in which case StringBuffer or external synchronization works.

---

### Q9: Explain method overloading vs overriding.

Overloading happens at compile time, same method name with different parameter lists in the same class. Overriding happens at runtime, a subclass provides a specific implementation of a method already defined in its parent with the same signature.

Overloading is resolved by the compiler based on argument types (static binding). Overriding is resolved at runtime based on the actual object type (dynamic binding), which is why polymorphism works.

```java
class Animal {
    void sound() { System.out.println("generic sound"); }
}
class Dog extends Animal {
    @Override
    void sound() { System.out.println("bark"); } // overriding
}

class Calculator {
    int add(int a, int b) { return a + b; }
    double add(double a, double b) { return a + b; } // overloading
}
```

---

### Q10: What is the difference between final, finally, and finalize?

final is a keyword for constants, methods that cannot be overridden, or classes that cannot be extended. finally is a block that always executes after try/catch regardless of whether an exception occurred, commonly used for cleanup. finalize is a method called by the garbage collector before an object is destroyed, but it is deprecated and unreliable, so avoid relying on it. Use try with resources or explicit close methods instead.

---

### Q11: What are Java Streams and how do they differ from collections?

A collection is an in memory data structure. A stream is a sequence of elements that supports functional style operations like map, filter, and reduce, computed lazily and often in a pipeline.

```java
List<String> names = List.of("Alice", "Bob", "Charlie", "Dave");
List<String> result = names.stream()
    .filter(n -> n.length() > 3)
    .map(String::toUpperCase)
    .sorted()
    .collect(Collectors.toList());
// [ALICE, CHARLIE, DAVE]
```

Streams do not store data, they describe a computation. Intermediate operations (filter, map) are lazy and only run when a terminal operation (collect, forEach, reduce) is called. Streams can be sequential or parallel via parallelStream(), but parallel streams only help for CPU heavy work on large datasets, not for small collections or IO bound tasks.

---

## DSA Interview Patterns (continued)

### Pattern 5: Fast and Slow Pointers (Cycle Detection)

When to use: Detecting cycles in linked lists, finding the middle of a list, palindrome linked list checks.

```java
public boolean hasCycle(ListNode head) {
    ListNode slow = head, fast = head;
    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow == fast) return true;
    }
    return false;
}
// Time: O(n), Space: O(1)
```

This is Floyd's cycle detection algorithm. If there is a cycle, the fast pointer will eventually lap the slow pointer.

---

### Pattern 6: Merge Intervals

When to use: Overlapping ranges, scheduling problems, calendar conflicts.

```java
public int[][] merge(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
    List<int[]> result = new ArrayList<>();
    for (int[] interval : intervals) {
        if (result.isEmpty() || result.get(result.size() - 1)[1] < interval[0]) {
            result.add(interval);
        } else {
            result.get(result.size() - 1)[1] = Math.max(result.get(result.size() - 1)[1], interval[1]);
        }
    }
    return result.toArray(new int[result.size()][]);
}
// Time: O(n log n) for the sort, Space: O(n)
```

The key step is sorting by start time first. After that, a single pass is enough since overlaps can only happen with adjacent intervals once sorted.

---

### Pattern 7: Backtracking

When to use: Generating permutations, combinations, subsets, solving puzzles like N Queens or Sudoku.

```java
public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(result, new ArrayList<>(), nums);
    return result;
}

private void backtrack(List<List<Integer>> result, List<Integer> current, int[] nums) {
    if (current.size() == nums.length) {
        result.add(new ArrayList<>(current));
        return;
    }
    for (int num : nums) {
        if (current.contains(num)) continue;
        current.add(num);
        backtrack(result, current, nums);
        current.remove(current.size() - 1);
    }
}
```

Company frequency: Amazon, Google, Microsoft for permutations and subsets, Meta for combination sum variants.

---

### Pattern 8: Binary Search on Answer

When to use: When you are asked to minimize or maximize some value, and you can check in O(n) whether a given candidate value works.

Example: Given an array, split it into k subarrays minimizing the largest subarray sum. Instead of trying every split, binary search on the answer (the maximum subarray sum), and check feasibility for each candidate.

```java
public int splitArray(int[] nums, int k) {
    int lo = 0, hi = 0;
    for (int n : nums) { lo = Math.max(lo, n); hi += n; }
    while (lo < hi) {
        int mid = lo + (hi - lo) / 2;
        if (canSplit(nums, k, mid)) hi = mid;
        else lo = mid + 1;
    }
    return lo;
}

private boolean canSplit(int[] nums, int k, int maxSum) {
    int pieces = 1, currentSum = 0;
    for (int n : nums) {
        if (currentSum + n > maxSum) { pieces++; currentSum = n; }
        else currentSum += n;
    }
    return pieces <= k;
}
```

This pattern shows up whenever you see "minimize the maximum" or "maximize the minimum" framing.

---

## System Design (continued)

### Load Balancing

Requirements: Distribute incoming traffic across multiple servers to avoid overload on any single machine and improve availability.

Common algorithms:

| Algorithm | How it works | Best for |
|-----------|--------------|----------|
| Round robin | Requests sent to servers in rotation | Servers with similar capacity |
| Weighted round robin | Servers with more capacity get more requests | Mixed hardware |
| Least connections | New request goes to server with fewest active connections | Long lived connections |
| IP hash | Client IP determines which server handles request | Session stickiness without shared session store |

Layer 4 load balancers operate on IP and TCP/UDP info, faster but less flexible. Layer 7 load balancers inspect HTTP headers and can route based on URL path or cookies, more flexible but slower.

---

### Designing a Chat Application

Requirements: Real time one to one and group messaging, message delivery guarantees, online presence.

High level design:

```
Client <-> WebSocket Gateway <-> Message Service -> Database (messages)
                                                   -> Message Queue (for offline delivery)
                                                   -> Presence Service (Redis)
```

Key decisions:

WebSockets or long polling for real time delivery. A message queue like Kafka or RabbitMQ buffers messages for offline users. Message ordering matters, often handled with a sequence number per conversation. For group chats with many members, fan out on write (push to each member's inbox) works for small groups, while fan out on read (pull on demand) works better for very large groups or channels.

Storage: NoSQL databases like Cassandra are common for chat history because of high write throughput and easy partitioning by conversation ID.

---

### Designing a News Feed

Requirements: Users see posts from people they follow, ranked roughly by recency or relevance.

Two main approaches:

Fan out on write: when a user posts, the post is pushed to the feed of every follower immediately. Fast reads, but expensive for users with millions of followers (the celebrity problem).

Fan out on read: when a user opens their feed, the system queries posts from everyone they follow and merges them on the fly. Avoids the celebrity problem but reads become expensive as follow counts grow.

Most real systems use a hybrid: fan out on write for regular users, fan out on read for celebrities, with results merged at read time.

---

## Spring Boot (continued)

### Q4: What is the difference between @Component, @Service, and @Repository?

All three are specializations of @Component and get registered as Spring beans. The difference is mainly semantic, helping with readability and enabling specific behavior.

@Component is the generic annotation for any Spring managed bean. @Service marks business logic classes. @Repository marks data access classes and additionally enables automatic exception translation, converting persistence specific exceptions into Spring's DataAccessException hierarchy.

---

### Q5: Explain @Transactional and common pitfalls.

@Transactional wraps a method in a database transaction, committing on success and rolling back on a runtime exception (by default, checked exceptions do not trigger rollback unless configured).

Common pitfalls:

Self invocation does not work. If a method inside the same class calls another @Transactional method on this, the proxy is bypassed and the annotation has no effect, because Spring's transaction management relies on proxies wrapping the bean.

Private methods cannot be proxied, so @Transactional on a private method is silently ignored.

Read only transactions (@Transactional(readOnly = true)) can be a performance optimization hint for some databases and ORMs, but do not enforce read only behavior at the database level.

---

### Q6: What is the difference between @RequestParam, @PathVariable, and @RequestBody?

@RequestParam extracts query parameters, like /search?q=java extracting q. @PathVariable extracts values from the URL path itself, like /users/{id} extracting id. @RequestBody deserializes the request body, typically JSON, into a Java object, used for POST and PUT requests.

```java
@GetMapping("/users/{id}")
public User getUser(@PathVariable Long id, @RequestParam(required = false) String fields) {
    // id comes from path, fields from query string
}

@PostMapping("/users")
public User createUser(@RequestBody UserDto dto) {
    // dto is deserialized from JSON body
}
```

---

## DBMS (continued)

### Q4: What is the difference between clustered and non clustered indexes?

A clustered index determines the physical order of data in the table, so a table can have only one clustered index (usually the primary key). A non clustered index is a separate structure that stores pointers back to the actual rows, and a table can have many of these.

Reading via a clustered index is faster for range queries because the data is physically sorted. Non clustered indexes add overhead on every write since they must be updated separately from the data.

---

### Q5: Explain ACID properties.

Atomicity: a transaction either fully completes or fully rolls back, no partial updates.

Consistency: a transaction brings the database from one valid state to another, respecting constraints and rules.

Isolation: concurrent transactions do not interfere with each other's intermediate states.

Durability: once a transaction commits, the changes survive even a crash, typically because they are written to a transaction log on disk before the commit is acknowledged.

---

### Q6: What are the different types of joins?

INNER JOIN returns only rows with matching values in both tables. LEFT JOIN returns all rows from the left table plus matched rows from the right, with nulls where there is no match. RIGHT JOIN is the mirror of LEFT JOIN. FULL OUTER JOIN returns all rows from both tables, with nulls where there is no match on either side. CROSS JOIN returns the cartesian product of both tables, every row from one combined with every row from the other.

```sql
SELECT e.name, d.department_name
FROM employees e
LEFT JOIN departments d ON e.dept_id = d.id;
-- includes employees with no department, dept_name will be NULL
```

---

### Q7: What is database sharding?

Sharding splits a large database into smaller pieces called shards, each stored on a different server, usually based on a key like user ID or geographic region.

Common sharding strategies: range based (sort key ranges across shards, simple but can create hot spots), hash based (hash the shard key to distribute evenly, harder to do range queries), and directory based (a lookup table maps keys to shards, flexible but the directory itself becomes a bottleneck if not handled carefully).

The hardest part of sharding is usually resharding when a shard grows too large, and handling queries that need data from multiple shards, like joins or aggregations across the whole dataset.

---

## OS and Networks (continued)

### Q4: What is the difference between a process and a thread?

A process is an independent program in execution with its own memory space. A thread is a lightweight unit of execution within a process, sharing the process's memory and resources with other threads in the same process.

Context switching between threads of the same process is cheaper than between processes, because the memory mapping does not need to change. But shared memory means threads need synchronization (locks, semaphores) to avoid race conditions, which is not a concern between separate processes unless they explicitly set up shared memory.

---

### Q5: Explain virtual memory and paging.

Virtual memory gives each process the illusion of a large, contiguous, private address space, even though physical memory is limited and shared. The OS maps virtual addresses to physical addresses using page tables.

Paging divides memory into fixed size blocks called pages (commonly 4KB). When a process accesses a page not currently in physical memory, a page fault occurs, and the OS loads the page from disk (the swap space), possibly evicting another page first using a replacement policy like LRU (least recently used).

Benefits: processes are isolated from each other, programs can be larger than physical memory, and memory can be allocated non contiguously while still appearing contiguous to the process.

---

### Q6: What is the difference between TCP and UDP?

TCP is connection oriented, with a handshake before data transfer, guarantees ordered and reliable delivery via acknowledgments and retransmission, and includes flow control and congestion control. Used for HTTP, file transfer, email.

UDP is connectionless, sends packets (datagrams) without guarantees on order or delivery, and has much lower overhead. Used for video streaming, DNS, online gaming, where occasional packet loss is acceptable but latency matters more than perfect reliability.

---

### Q7: What is DNS and how does it work at a high level?

DNS (Domain Name System) translates human readable domain names into IP addresses.

Resolution flow: the browser checks its local cache first. If not found, it queries a recursive resolver (often run by the ISP). The resolver queries a root server, which points to a TLD server (for .com, .org, etc), which points to the authoritative name server for the specific domain, which finally returns the IP address. The result is cached at multiple levels (browser, OS, resolver) according to a TTL (time to live) value to reduce repeated lookups.

---

### Q8: What is the difference between HTTP and HTTPS?

HTTP sends data in plain text, so anyone intercepting the traffic can read it. HTTPS adds a TLS (Transport Layer Security) layer that encrypts the data, verifies the server's identity via certificates, and protects against tampering.

The TLS handshake involves the client and server agreeing on a cipher suite, the server presenting a certificate signed by a trusted certificate authority, and both sides deriving a shared symmetric key used to encrypt the actual data, since symmetric encryption is much faster than asymmetric encryption for bulk data.

---

## Behavioral Questions (continued)

### "Why do you want to work here?"

Avoid generic answers about company size or reputation. Mention something specific, a product you have used, a technical blog post or talk from someone on the team, or a problem space that genuinely interests you and connects to your background.

### "Tell me about a time you had to learn something quickly."

Pick an example with a real deadline and a concrete outcome. Mention how you approached learning (docs, asking a teammate, building a small prototype first) rather than just saying "I read the documentation."

### "How do you prioritize when everything feels urgent?"

Good answers usually mention a framework, even an informal one: separating what is urgent versus important, communicating tradeoffs to stakeholders rather than silently dropping things, and giving an example where you pushed back on a deadline with a reason.

### Tips for Remote and Virtual Interviews

Test your setup (camera, mic, screen share) at least ten minutes before. Keep a notepad and pen nearby for system design or quick math, since typing while talking can be distracting. If using a shared coding editor, narrate what you are typing as you type it, since the interviewer cannot always see your cursor in real time.
