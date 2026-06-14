---
title: Java Backend Development
description: Build production-ready REST APIs using Spring Boot – covering setup, REST principles, JPA, security, JWT, testing, and Docker deployment.
---

# Java Backend Development

Build production-ready REST APIs using Spring Boot and modern best practices.

<span class="badge badge-advanced">Advanced</span> · **4-6 weeks**

!!! info "Prerequisites"
    Complete Java Basics, OOP, and have basic REST API knowledge.

---

## Backend Development Basics

### What is Backend?

Backend is the server-side of an application that handles:

- Processing business logic
- Database interactions
- API endpoints (REST, GraphQL, etc.)
- Authentication & Authorization
- External service integrations

### Backend Architecture

```
Client (Frontend)
     ↓
Request (HTTP/HTTPS)
     ↓
API Layer (REST Endpoints)
     ↓
Service Layer (Business Logic)
     ↓
DAO/Repository (Data Access)
     ↓
Database (MySQL, PostgreSQL, etc.)
```

### Why Spring Boot?

- ✅ Simplified Spring configuration
- ✅ Embedded server (Tomcat)
- ✅ Auto-configuration
- ✅ Extensive ecosystem
- ✅ Production-ready

---

## Spring Boot Introduction

### Setup Spring Boot Project

```xml
<!-- pom.xml -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.1.0</version>
</parent>

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

### Application Properties

```properties
# application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/mydb
spring.datasource.username=root
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

server.port=8080
server.servlet.context-path=/api
```

### Main Application Class

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

---

## Building REST APIs

### REST Principles

- **GET:** Retrieve data (safe, idempotent)
- **POST:** Create new resource
- **PUT:** Update entire resource
- **PATCH:** Partial update
- **DELETE:** Remove resource

### Complete REST API Example

```java
// Entity
import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}

// Repository
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    List<User> findByNameContaining(String name);
}

// Service
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            return userRepository.save(existingUser);
        }
        return null;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

// Controller
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updated = userService.updateUser(id, user);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
```

### API Testing

Test your endpoints using tools like **Postman**, **cURL**, or **Insomnia**.

```bash
# GET all users
curl http://localhost:8080/api/users

# GET user by ID
curl http://localhost:8080/api/users/1

# CREATE user
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"John","email":"john@example.com"}'

# UPDATE user
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Jane","email":"jane@example.com"}'

# DELETE user
curl -X DELETE http://localhost:8080/api/users/1
```

---

## Database Integration

### JPA & Hibernate

JPA (Java Persistence API) is an ORM (Object-Relational Mapping) framework.

### Relationships

```java
// One-to-Many
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees;
}

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}

// Many-to-Many
@Entity
public class Student {
    @Id
    private Long id;

    @ManyToMany
    @JoinTable(name = "student_course")
    private List<Course> courses;
}

@Entity
public class Course {
    @Id
    private Long id;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students;
}
```

### Query Methods

```java
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Derived query methods
    List<Employee> findByDepartmentId(Long id);
    List<Employee> findByNameContaining(String name);
    Employee findByEmail(String email);

    // Custom JPQL query
    @Query("SELECT e FROM Employee e WHERE e.department.id = ?1")
    List<Employee> getEmployeesByDept(Long deptId);

    // Native SQL query
    @Query(value = "SELECT * FROM employees WHERE salary > ?1",
           nativeQuery = true)
    List<Employee> getHighSalaryEmployees(double salary);
}
```

---

## Security & Authentication

### Spring Security Setup

```java
// pom.xml dependency
// <dependency>
//     <groupId>org.springframework.boot</groupId>
//     <artifactId>spring-boot-starter-security</artifactId>
// </dependency>

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/api/public/**").permitAll()
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            .and()
            .httpBasic();

        return http.build();
    }
}
```

### JWT Authentication

```java
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenProvider {
    private String secretKey = "my-secret-key";
    private long validityInMilliseconds = 3600000; // 1 hour

    public String generateToken(String username) {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + validityInMilliseconds))
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
```

---

## Testing

### Unit Testing with JUnit & Mockito

```java
@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testGetUserById() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setName("John");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        User result = userService.getUserById(1L);

        // Assert
        assertEquals("John", result.getName());
        verify(userRepository, times(1)).findById(1L);
    }
}
```

### Integration Testing

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetAllUsers() {
        ResponseEntity<User[]> response = restTemplate.getForEntity(
            "/api/users",
            User[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().length > 0);
    }
}
```

---

## Deployment

### Build JAR File

```bash
# Using PowerShell
mvn clean package

# Run the JAR
java -jar target/myapp-1.0.0.jar

# Or run directly
mvn spring-boot:run
```

### Deployment Options

- **Docker:** Containerize your app
- **Heroku:** Cloud platform
- **AWS:** EC2, Elastic Beanstalk
- **Azure:** App Service
- **Google Cloud:** Cloud Run, App Engine

### Docker Example

```dockerfile
FROM openjdk:11-jre-slim
COPY target/myapp-1.0.0.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

```bash
# Build and run
docker build -t my-java-app .
docker run -p 8080:8080 my-java-app
```

---

## Backend Best Practices

!!! tip "1. Use Dependency Injection"
    Let Spring manage object creation and injection, not manual instantiation.

!!! tip "2. Proper Exception Handling"
    Create custom exceptions, use @ExceptionHandler for consistent error responses.

!!! tip "3. Validation"
    Validate input using Spring's @Valid and custom validators.

!!! tip "4. Logging"
    Use SLF4J with Logback for proper logging instead of println.

!!! tip "5. Version Your API"
    Use versioning in URLs (`/api/v1/`, `/api/v2/`) for easy updates.

!!! tip "6. Document API"
    Use Swagger/SpringFox for API documentation.

!!! tip "7. Monitor Performance"
    Use Spring Boot Actuator and monitoring tools like Prometheus.

!!! tip "8. Secure Sensitive Data"
    Use environment variables for secrets, never hardcode credentials.

---

## Next Steps

Once you've mastered Backend, explore:

[Learn Spring AI & Agents :material-arrow-right:](../spring-ai/index.md){ .md-button .md-button--primary }
