---
title: CS Fundamentals
description: Core computer science theory every developer needs — Operating Systems, Computer Networks, and DBMS — with practical Java backend integration.
---

# CS Fundamentals

Core computer science theory that every backend developer must know. Each topic covers the theory, then shows how it directly applies to Java development.

---

## Part 1 — Operating Systems

### Processes and Threads

A **process** is an independent program in execution with its own memory space. A **thread** is a lightweight unit of execution within a process that shares the process's memory.

| Feature | Process | Thread |
|---------|---------|--------|
| Memory | Separate address space | Shared memory within process |
| Creation cost | Expensive (fork) | Lightweight |
| Communication | IPC (pipes, sockets, shared memory) | Direct shared memory access |
| Crash impact | Isolated — one crash doesn't affect others | One thread crash can kill the process |
| Context switch | Expensive (TLB flush, page tables) | Cheaper (same address space) |

**Java integration:** Every Java program runs as a process on the JVM. The JVM manages threads internally.

```java
// Creating threads in Java
// Method 1: Extend Thread class
class MyThread extends Thread {
    public void run() {
        System.out.println("Thread: " + Thread.currentThread().getName());
    }
}

// Method 2: Implement Runnable (preferred)
Runnable task = () -> System.out.println("Running in: " + Thread.currentThread().getName());
new Thread(task).start();

// Method 3: ExecutorService (production-grade)
ExecutorService executor = Executors.newFixedThreadPool(4);
executor.submit(() -> {
    // Task runs in thread pool
    System.out.println("Pool thread: " + Thread.currentThread().getName());
});
executor.shutdown();
```

### CPU Scheduling

The OS scheduler decides which process/thread runs on the CPU and for how long.

| Algorithm | Description | Pros | Cons |
|-----------|------------|------|------|
| FCFS (First Come First Served) | Processes run in arrival order | Simple, fair | Convoy effect — short jobs wait behind long ones |
| SJF (Shortest Job First) | Shortest burst time runs first | Optimal average waiting time | Starvation of long processes, requires burst prediction |
| Round Robin | Each process gets a fixed time slice (quantum) | Fair, responsive | High context-switch overhead if quantum is too small |
| Priority Scheduling | Highest priority runs first | Important tasks prioritized | Starvation of low-priority (solved with aging) |
| Multilevel Queue | Separate queues for different process types | Organized, efficient | Inflexible without feedback |

### Deadlocks

A deadlock occurs when two or more processes are waiting for each other to release resources, creating a circular dependency where none can proceed.

**Four necessary conditions (all must hold):**

1. **Mutual Exclusion:** Resource held exclusively by one process
2. **Hold and Wait:** Process holds one resource while waiting for another
3. **No Preemption:** Resources cannot be forcibly taken away
4. **Circular Wait:** A circular chain of processes, each waiting for a resource held by the next

**Prevention strategies:** Break any one condition — e.g., impose a resource ordering (breaks circular wait), require all resources upfront (breaks hold and wait).

**Java example — deadlock:**
```java
Object lock1 = new Object(), lock2 = new Object();

// Thread 1: locks lock1, then lock2
new Thread(() -> {
    synchronized (lock1) {
        synchronized (lock2) { /* work */ }
    }
}).start();

// Thread 2: locks lock2, then lock1 — DEADLOCK!
new Thread(() -> {
    synchronized (lock2) {
        synchronized (lock1) { /* work */ }
    }
}).start();

// Fix: Always acquire locks in the same order (lock1 → lock2)
```

### Memory Management

**JVM Memory Areas:**

| Area | Purpose | Thread-shared? |
|------|---------|----------------|
| Heap | Object storage, garbage collected | Yes |
| Stack | Method frames, local variables | No (per thread) |
| Metaspace | Class metadata, method info | Yes |
| Program Counter | Current instruction address | No (per thread) |

**Paging:** The OS divides physical memory into fixed-size frames and virtual memory into pages. A **page table** maps virtual pages to physical frames.

**Page Replacement Algorithms:**

- **FIFO:** Replace the oldest page — simple but suffers from Belady's anomaly
- **LRU (Least Recently Used):** Replace the page not used for the longest time — good performance, used in practice
- **Optimal:** Replace the page that won't be used for the longest future time — theoretical best, impossible to implement

**Java connection:** The JVM's garbage collector (G1, ZGC, Shenandoah) manages heap memory. Understanding GC pauses is critical for low-latency applications.

---

## Part 2 — Computer Networks

### OSI Model (7 Layers)

| Layer | Name | Protocol Examples | What It Does |
|-------|------|-------------------|--------------|
| 7 | Application | HTTP, FTP, SMTP, DNS | User-facing services |
| 6 | Presentation | SSL/TLS, JPEG, JSON | Data format, encryption |
| 5 | Session | NetBIOS, RPC | Session management |
| 4 | Transport | TCP, UDP | Reliable delivery, flow control |
| 3 | Network | IP, ICMP, ARP | Routing, addressing |
| 2 | Data Link | Ethernet, Wi-Fi | Frame transmission |
| 1 | Physical | Cables, signals | Raw bit transmission |

### TCP vs UDP

| Feature | TCP | UDP |
|---------|-----|-----|
| Connection | Connection-oriented (3-way handshake) | Connectionless |
| Reliability | Guaranteed delivery, ordering | No guarantee |
| Speed | Slower (overhead) | Faster |
| Use cases | HTTP, email, file transfer | Video streaming, DNS, gaming |
| Flow control | Yes (sliding window) | No |

**TCP 3-Way Handshake:** `SYN → SYN-ACK → ACK` — establishes a connection before data transfer.

### HTTP Protocol

HTTP (Hypertext Transfer Protocol) is the foundation of web communication. Every REST API you build uses HTTP.

**Request structure:**
```
GET /api/users/1 HTTP/1.1
Host: localhost:8080
Accept: application/json
Authorization: Bearer <token>
```

**Common status codes:**

| Code | Meaning | When It's Used |
|------|---------|----------------|
| 200 | OK | Successful GET/PUT |
| 201 | Created | Successful POST |
| 204 | No Content | Successful DELETE |
| 400 | Bad Request | Invalid input/validation error |
| 401 | Unauthorized | Missing/invalid authentication |
| 403 | Forbidden | Authenticated but not authorized |
| 404 | Not Found | Resource doesn't exist |
| 500 | Internal Server Error | Unhandled server exception |

### DNS (Domain Name System)

DNS translates human-readable domain names (`google.com`) to IP addresses (`142.250.80.14`). The resolution process:

1. Browser checks local cache
2. OS checks its cache
3. Query goes to recursive DNS resolver (ISP or 8.8.8.8)
4. Resolver queries root server → TLD server → authoritative server
5. IP address returned and cached at each level

### Java Networking Integration

```java
// HTTP Client (Java 11+)
HttpClient client = HttpClient.newHttpClient();
HttpRequest request = HttpRequest.newBuilder()
    .uri(URI.create("https://api.example.com/users"))
    .header("Content-Type", "application/json")
    .GET()
    .build();
HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
System.out.println(response.statusCode()); // 200
System.out.println(response.body());

// Socket programming (TCP)
// Server
ServerSocket server = new ServerSocket(8080);
Socket clientSocket = server.accept(); // blocks until client connects
BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
String message = in.readLine();

// Spring Boot automatically handles HTTP for you via @RestController
@RestController
@RequestMapping("/api/users")
public class UserController {
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        // Spring handles HTTP parsing, serialization, status codes
        return ResponseEntity.ok(userService.findById(id));
    }
}
```

---

## Part 3 — Database Management Systems (DBMS)

### ER Diagrams and Schema Design

Entity-Relationship diagrams model real-world entities and their relationships before writing SQL.

**Key concepts:**

- **Entity:** A real-world object (User, Order, Product)
- **Attribute:** A property of an entity (name, email, price)
- **Primary Key:** Unique identifier for each record
- **Foreign Key:** Reference to a primary key in another table
- **Relationships:** One-to-One, One-to-Many, Many-to-Many

### Normalization

Normalization reduces data redundancy and prevents anomalies (insertion, update, deletion).

| Normal Form | Rule | Example Violation |
|-------------|------|-------------------|
| 1NF | No repeating groups, atomic values | `skills: "Java, Python, Go"` → should be separate rows |
| 2NF | 1NF + no partial dependencies | Non-key column depends on part of a composite key |
| 3NF | 2NF + no transitive dependencies | `zip_code → city` — city depends on zip, not on the primary key |
| BCNF | Every determinant is a candidate key | Stricter version of 3NF |

### ACID Properties

Every transaction in a relational database must satisfy:

| Property | Meaning | Example |
|----------|---------|---------|
| **Atomicity** | All or nothing — transaction either fully completes or fully rolls back | Bank transfer: debit AND credit both succeed, or neither does |
| **Consistency** | Database moves from one valid state to another | Balance cannot go negative if constrained |
| **Isolation** | Concurrent transactions don't interfere | Two users buying the last item — only one succeeds |
| **Durability** | Committed data survives crashes | Written to disk, not just memory |

### Indexing

An index is a data structure (typically B-tree or B+ tree) that speeds up data retrieval at the cost of extra storage and slower writes.

**Without index:** Full table scan — O(n)
**With index:** B-tree lookup — O(log n)

```sql
-- Create index on frequently queried column
CREATE INDEX idx_users_email ON users(email);

-- Composite index for multi-column queries
CREATE INDEX idx_orders_user_date ON orders(user_id, order_date);

-- When to index:
-- YES: columns in WHERE, JOIN, ORDER BY clauses
-- NO: small tables, columns with low cardinality, heavily updated columns
```

### SQL Joins

```sql
-- INNER JOIN: only matching rows from both tables
SELECT u.name, o.total
FROM users u INNER JOIN orders o ON u.id = o.user_id;

-- LEFT JOIN: all rows from left table, matching from right
SELECT u.name, o.total
FROM users u LEFT JOIN orders o ON u.id = o.user_id;

-- Common interview question: find users with no orders
SELECT u.name FROM users u
LEFT JOIN orders o ON u.id = o.user_id
WHERE o.id IS NULL;
```

### Java + DBMS Integration (JPA/Hibernate)

```java
// Entity mapping — Java class → database table
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    // One user has many orders
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;
}

// Transaction management in Spring
@Service
public class TransferService {

    @Transactional // Ensures ACID properties
    public void transfer(Long fromId, Long toId, BigDecimal amount) {
        Account from = accountRepo.findById(fromId).orElseThrow();
        Account to = accountRepo.findById(toId).orElseThrow();

        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));

        accountRepo.save(from);
        accountRepo.save(to);
        // If any step fails, everything rolls back
    }
}

// Connection pooling with HikariCP (Spring Boot default)
// application.yml
// spring:
//   datasource:
//     hikari:
//       maximum-pool-size: 10
//       minimum-idle: 5
//       connection-timeout: 30000
```

---

## Summary — How CS Fundamentals Connect to Java Backend

| CS Concept | Java Backend Application |
|-----------|-------------------------|
| Threads & concurrency | `ExecutorService`, `@Async`, reactive programming |
| Deadlocks | Synchronized blocks, lock ordering, `ReentrantLock` |
| Memory management | JVM heap/stack, GC tuning, memory leaks |
| CPU scheduling | Thread pools, `ForkJoinPool`, work-stealing |
| TCP/IP | Socket programming, HTTP clients |
| HTTP protocol | Spring `@RestController`, status codes, headers |
| DNS | Service discovery in microservices |
| ACID transactions | `@Transactional`, isolation levels |
| Indexing | JPA `@Index`, query optimization |
| Normalization | Entity design, relationship mapping |
