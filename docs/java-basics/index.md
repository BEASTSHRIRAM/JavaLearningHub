---
title: Java Basics
description: Master the fundamentals of Java programming – variables, data types, operators, control flow, methods, arrays, strings, and exception handling.
---

# Java Basics

Master the fundamentals of Java programming. Perfect for beginners!

<span class="badge badge-beginner">Beginner</span> · **2-3 weeks**

---

## Introduction to Java

Java is a versatile, object-oriented programming language that runs on the Java Virtual Machine (JVM). It's used for everything from web applications to Android apps and enterprise systems.

!!! note "Key Characteristics"
    - ✅ **Platform Independent:** Write once, run anywhere (WORA)
    - ✅ **Object-Oriented:** Everything is an object
    - ✅ **Statically Typed:** Type checking at compile time
    - ✅ **Garbage Collection:** Automatic memory management
    - ✅ **Rich Ecosystem:** Massive libraries and frameworks

---

## Setup & Environment

### Install Java Development Kit (JDK)

Download and install from: [Oracle JDK](https://www.oracle.com/java/technologies/downloads/){ target=_blank }

```sh
# Verify installation (Windows PowerShell)
java -version
javac -version
```

### Install IDE

Choose one:

- **IntelliJ IDEA:** Best overall (Community Edition is free)
- **Eclipse:** Lightweight, good for beginners
- **VS Code:** Minimal, customizable

### Create Your First Java Project

```java
// File: HelloWorld.java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

**Compile & Run:**

```sh
javac HelloWorld.java
java HelloWorld
```

---

## Variables & Data Types

### Primitive Data Types

| Type | Size | Range | Example |
|------|------|-------|---------|
| `byte` | 1 byte | -128 to 127 | `byte b = 10;` |
| `short` | 2 bytes | -32,768 to 32,767 | `short s = 1000;` |
| `int` | 4 bytes | -2^31 to 2^31-1 | `int i = 50000;` |
| `long` | 8 bytes | -2^63 to 2^63-1 | `long l = 999999999L;` |
| `float` | 4 bytes | ±3.4 × 10^38 | `float f = 3.14f;` |
| `double` | 8 bytes | ±1.7 × 10^308 | `double d = 3.14;` |
| `boolean` | 1 bit | true or false | `boolean flag = true;` |
| `char` | 2 bytes | Unicode 0-65535 | `char c = 'A';` |

### Variable Declaration

```java
// Syntax: dataType variableName = value;

int age = 25;
double salary = 50000.50;
String name = "John Doe";
boolean isStudent = true;
char grade = 'A';
```

### Reference Data Types (Objects)

```java
String message = "Hello Java";
int[] numbers = {1, 2, 3, 4, 5};
ArrayList<String> list = new ArrayList<>();
HashMap<String, Integer> map = new HashMap<>();
```

---

## Operators

### Arithmetic Operators

```java
int a = 10, b = 3;
System.out.println(a + b);     // 13 (Addition)
System.out.println(a - b);     // 7  (Subtraction)
System.out.println(a * b);     // 30 (Multiplication)
System.out.println(a / b);     // 3  (Division)
System.out.println(a % b);     // 1  (Modulus)
System.out.println(a++);       // 10 (Post-increment)
System.out.println(++a);       // 12 (Pre-increment)
```

### Comparison Operators

```java
int x = 5, y = 10;
System.out.println(x == y);    // false
System.out.println(x != y);    // true
System.out.println(x < y);     // true
System.out.println(x > y);     // false
System.out.println(x <= y);    // true
System.out.println(x >= y);    // false
```

### Logical Operators

```java
boolean p = true, q = false;
System.out.println(p && q);    // false (AND)
System.out.println(p || q);    // true  (OR)
System.out.println(!p);        // false (NOT)
```

### Assignment Operators

```java
int num = 10;
num += 5;   // num = 15
num -= 3;   // num = 12
num *= 2;   // num = 24
num /= 4;   // num = 6
num %= 5;   // num = 1
```

---

## Control Flow

### if-else Statement

```java
int age = 20;

if (age < 18) {
    System.out.println("Minor");
} else if (age < 65) {
    System.out.println("Adult");
} else {
    System.out.println("Senior");
}
```

### Switch Statement

```java
int day = 3;
String dayName;

switch(day) {
    case 1:
        dayName = "Monday";
        break;
    case 2:
        dayName = "Tuesday";
        break;
    case 3:
        dayName = "Wednesday";
        break;
    default:
        dayName = "Invalid";
}
```

### for Loop

```java
// Traditional for loop
for (int i = 0; i < 5; i++) {
    System.out.println(i);
}

// Enhanced for loop (for-each)
int[] arr = {1, 2, 3, 4, 5};
for (int num : arr) {
    System.out.println(num);
}
```

### while & do-while Loop

```java
// while loop
int count = 0;
while (count < 5) {
    System.out.println(count);
    count++;
}

// do-while loop (executes at least once)
int i = 0;
do {
    System.out.println(i);
    i++;
} while (i < 5);
```

---

## Methods & Functions

### Method Syntax

```java
// Syntax: accessModifier returnType methodName(parameters) {
//     method body
// }

public class Calculator {
    // Method with parameters and return type
    public int add(int a, int b) {
        return a + b;
    }

    // Method with no parameters
    public void greet() {
        System.out.println("Hello!");
    }

    // Method with no return type
    public void printMessage(String msg) {
        System.out.println(msg);
    }

    // Overloading - same method name, different parameters
    public int add(int a, int b, int c) {
        return a + b + c;
    }

    public double add(double a, double b) {
        return a + b;
    }
}
```

### Method Calling

```java
Calculator calc = new Calculator();
int result = calc.add(5, 10);           // 15
int result2 = calc.add(5, 10, 15);      // 30
double result3 = calc.add(3.5, 2.5);    // 6.0
calc.greet();                            // Hello!
```

---

## Java Collections Framework

The Java Collections Framework provides an architecture to store and manipulate a group of objects. It includes interfaces, implementations (classes), and algorithms.

### 1. Lists (Ordered, allows duplicates)

**ArrayList**: Resizable array. Fast for random access, slow for insertions/deletions in the middle.
```java
List<String> names = new ArrayList<>();
names.add("Alice");
names.add("Bob");
names.set(1, "Charlie"); // Updates element at index 1
System.out.println(names.get(0)); // Alice
```

**LinkedList**: Doubly-linked list. Fast for insertions/deletions, slow for random access.
```java
LinkedList<String> queue = new LinkedList<>();
queue.addFirst("First");
queue.addLast("Last");
```

### 2. Sets (Unordered, no duplicates)

**HashSet**: Backed by a Hash Table. O(1) constant time for basic operations. No ordering guarantees.
```java
Set<Integer> uniqueNumbers = new HashSet<>();
uniqueNumbers.add(10);
uniqueNumbers.add(10); // Ignored, duplicate
```

**TreeSet**: Backed by a Red-Black Tree. Elements are sorted in natural order. O(log n) time.
```java
Set<Integer> sortedNumbers = new TreeSet<>();
sortedNumbers.add(5);
sortedNumbers.add(1); // Internally sorted: [1, 5]
```

### 3. Maps (Key-Value pairs)

**HashMap**: Unordered key-value pairs. O(1) lookups.
```java
Map<String, Integer> ages = new HashMap<>();
ages.put("Alice", 25);
ages.put("Bob", 30);
System.out.println(ages.get("Alice")); // 25
```

**TreeMap**: Sorted by keys. O(log n) lookups.

---

## Multithreading & Concurrency

Java has built-in support for multithreading, allowing multiple parts of a program to run concurrently to maximize CPU utilization.

### 1. Creating Threads

**Method 1: Implementing Runnable (Recommended)**
```java
class MyTask implements Runnable {
    public void run() {
        System.out.println("Task is running on: " + Thread.currentThread().getName());
    }
}

public class Main {
    public static void main(String[] args) {
        Thread t1 = new Thread(new MyTask());
        t1.start(); // Starts the new thread
    }
}
```

**Method 2: Extending Thread Class**
```java
class MyThread extends Thread {
    public void run() {
        System.out.println("Thread running");
    }
}
```

### 2. Thread Synchronization
When multiple threads access shared resources, data corruption can occur. We use the `synchronized` keyword to lock the resource so only one thread can access it at a time.

```java
class Counter {
    private int count = 0;
    
    // Only one thread can execute this at a time
    public synchronized void increment() {
        count++;
    }
}
```

### 3. Executor Service (Modern Concurrency)
Instead of manually managing raw threads, modern Java applications use Thread Pools.

```java
ExecutorService executor = Executors.newFixedThreadPool(2);
executor.submit(() -> System.out.println("Task 1"));
executor.submit(() -> System.out.println("Task 2"));
executor.shutdown();
```

---

## Modern Java: Lambdas & Stream API

Introduced in Java 8, Lambdas and Streams revolutionized how we process collections, enabling functional, declarative-style programming.

### 1. Lambda Expressions
A concise way to represent anonymous functions.

```java
// Traditional way
Runnable r1 = new Runnable() {
    public void run() { System.out.println("Running"); }
};

// Lambda way
Runnable r2 = () -> System.out.println("Running");
```

### 2. Stream API
Streams allow declarative processing of collections (filtering, mapping, reducing) without mutating the original collection.

```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

// Filter names starting with 'C' or 'D', convert to uppercase, and print
names.stream()
     .filter(name -> name.startsWith("C") || name.startsWith("D"))
     .map(String::toUpperCase)
     .forEach(System.out::println);
// Output: CHARLIE
// Output: DAVID
```

**Common Stream Operations:**

- `filter(Predicate)`: Keeps elements that match a condition.
- `map(Function)`: Transforms elements.
- `collect(Collectors.toList())`: Gathers the stream back into a collection.
- `reduce(BinaryOperator)`: Combines elements into a single result.

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
int sum = numbers.stream().reduce(0, (a, b) -> a + b); // sum = 15
```

---

## Strings

### String Basics

```java
String str1 = "Hello";
String str2 = new String("World");

// Concatenation
String greeting = str1 + " " + str2;    // "Hello World"
String greeting2 = str1.concat(" ").concat(str2);

// String length
System.out.println(greeting.length());  // 11

// Character access
System.out.println(greeting.charAt(0)); // 'H'

// Substring
System.out.println(greeting.substring(0, 5));  // "Hello"
```

### String Methods

```java
String text = "  Hello World  ";

// Case conversion
System.out.println(text.toUpperCase());  // "  HELLO WORLD  "
System.out.println(text.toLowerCase());  // "  hello world  "

// Trimming & replacing
System.out.println(text.trim());             // "Hello World"
System.out.println(text.replace("World", "Java")); // "  Hello Java  "

// Checking
System.out.println(text.contains("World")); // true
System.out.println(text.startsWith("  H")); // true
System.out.println(text.endsWith("d  "));   // true

// Splitting
String[] words = "Hello World Java".split(" ");
// words = ["Hello", "World", "Java"]
```

---

## Exception Handling

```java
public class ExceptionExample {
    public static void main(String[] args) {
        try {
            // Code that might throw exception
            int result = 10 / 0;  // ArithmeticException
        } catch (ArithmeticException e) {
            System.out.println("Cannot divide by zero!");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("General exception occurred");
        } finally {
            System.out.println("This always executes");
        }
    }
}
```

### Common Exception Types

- `ArithmeticException` – Math errors (divide by zero)
- `NullPointerException` – Accessing null object
- `ArrayIndexOutOfBoundsException` – Invalid array index
- `StringIndexOutOfBoundsException` – Invalid string index
- `NumberFormatException` – Invalid number conversion
- `IOException` – Input/Output errors

### Multiple Catch Blocks

```java
try {
    String num = "abc";
    int value = Integer.parseInt(num);
} catch (NumberFormatException e) {
    System.out.println("Invalid number format");
} catch (Exception e) {
    System.out.println("General error occurred");
}
```

---

## Practice Problems

!!! tip "Problem 1: Simple Calculator"
    Create a program that takes two numbers and an operator (+, -, *, /) and performs the operation.

    **Hint:** Use Scanner class for user input and switch statement for operators.

!!! tip "Problem 2: Factorial Calculation"
    Write a method that calculates the factorial of a number using a loop.

    **Example:** `factorial(5) = 5 × 4 × 3 × 2 × 1 = 120`

!!! tip "Problem 3: Reverse a String"
    Create a method that reverses a given string without using built-in reverse method.

    **Example:** `"Hello"` → `"olleH"`

!!! tip "Problem 4: Count Vowels"
    Write a program that counts the number of vowels in a given string.

    **Hint:** Use a for-each loop or enhanced for loop.

!!! tip "Problem 5: Fibonacci Series"
    Generate the first n Fibonacci numbers and store them in an ArrayList.

    **Example:** First 7: `0, 1, 1, 2, 3, 5, 8`

---

## Next Steps

Once you've mastered Java Basics, move on to:

[Learn Object-Oriented Programming :material-arrow-right:](../oops/index.md){ .md-button .md-button--primary }
