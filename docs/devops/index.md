---
title: DevOps for Java Developers
description: Essential DevOps skills for Java developers — Linux commands, YAML configuration, Git workflow, Docker, CI/CD with GitHub Actions, Kubernetes basics, and monitoring.
---

# DevOps for Java Developers

The essential DevOps knowledge every Java backend developer needs. Practical, hands-on, and directly integrated with Java/Spring Boot applications.

<span class="badge badge-intermediate">Intermediate</span> · **3-4 weeks**

---

## Linux Essentials

Most servers run Linux. You need to be comfortable on the command line.

### File System Hierarchy

```
/               Root of everything
├── /home       User home directories
├── /etc        Configuration files
├── /var        Variable data (logs, databases)
│   └── /var/log    System and application logs
├── /opt        Optional/third-party software
├── /tmp        Temporary files (cleared on reboot)
├── /usr        User programs and libraries
│   ├── /usr/bin    User binaries
│   └── /usr/lib    Libraries
└── /proc       Virtual filesystem (process info)
```

### Essential Commands

**Navigation and files:**
```bash
pwd                        # Print Working Directory: shows exactly where you are right now
ls -la                     # List all files (including hidden ones) with details like permissions and size
cd /var/log                # Change Directory: moves you into the /var/log folder
mkdir -p app/src/main      # Make Directory: creates nested folders all at once (thanks to -p)
cp file.txt backup/        # Copy: duplicates file.txt into the backup folder
mv old.txt new.txt         # Move: renames old.txt to new.txt (or moves it to another folder)
rm -rf build/              # Remove: recursively (-r) and forcefully (-f) deletes the build folder. CAREFUL!
find / -name "*.log" -mtime -1   # Find: searches the whole system (/) for .log files modified in the last 1 day (-mtime -1)
```

**File content:**
```bash
cat application.yml        # Concatenate: prints the entire content of the file to the screen
head -n 20 server.log      # Head: prints only the first 20 lines of the file
tail -f server.log         # Tail: follows the file in real-time, printing new lines as they are added (crucial for live debugging)
grep -r "ERROR" /var/log/  # Global Regular Expression Print: searches recursively (-r) for the word "ERROR" in the /var/log/ folder
grep -i "exception" app.log | wc -l   # Finds "exception" case-insensitively (-i) and pipes (|) the result to Word Count (wc) to count the lines (-l)
```

**Process management:**
```bash
ps aux                     # Process Status: lists all running processes for all users
ps aux | grep java         # Finds processes, but filters only the ones containing the word "java"
top                        # Shows a real-time, dynamic view of running processes and CPU/RAM usage
htop                       # A better, colorful version of top (usually needs to be installed first)
kill -9 <PID>              # Sends signal 9 (SIGKILL) to forcefully terminate a process by its Process ID (PID)
nohup java -jar app.jar &  # No Hangup (nohup) keeps the app running after you log out. '&' puts it in the background
```

**Permissions:**
```bash
chmod 755 deploy.sh        # Change Mode: sets permissions to rwxr-xr-x (owner: read/write/execute, others: read/execute)
chmod +x script.sh         # Makes the file executable (+x)
chown appuser:appgroup /opt/myapp   # Change Owner: assigns the file to 'appuser' and group 'appgroup'
```

**Networking:**
```bash
curl -X GET http://localhost:8080/api/health    # Client URL (curl): makes an HTTP GET request to test an endpoint
curl -X POST -H "Content-Type: application/json" -d '{"name":"test"}' http://localhost:8080/api/users # Makes a POST request with JSON data
netstat -tlnp              # Network Statistics: shows listening TCP ports (-t), listening status (-l), numeric addresses (-n), and process IDs (-p)
ss -tlnp                   # Socket Statistics: a faster, modern alternative to netstat
ping google.com            # Sends ICMP packets to test if a server is reachable and how fast it responds
ssh user@server            # Secure Shell: logs you into a remote server securely
scp app.jar user@server:/opt/app/   # Secure Copy: securely transfers a file from your machine to a remote server
```

### Shell Scripting Basics

Shell scripts automate repetitive tasks. Here is a simple script to build and run a Java application with detailed explanations for each command:

```bash
#!/bin/bash
# The line above tells the OS to use the 'bash' shell to run this script

APP_NAME="myapp"
JAR_FILE="target/${APP_NAME}.jar"
LOG_FILE="/var/log/${APP_NAME}.log"

# 'echo' prints text to the screen (like System.out.println in Java)
echo "Building application..."

# 'mvn' runs Maven to compile and package the Java code into a JAR file
mvn clean package -DskipTests

# '$?' holds the exit code of the last command. 0 means success.
# '-ne' means "not equal". So this checks if Maven failed.
if [ $? -ne 0 ]; then
    echo "Build failed!"
    exit 1   # Stop the script with an error code (1)
fi

echo "Stopping existing instance..."
# 'pkill' finds running processes matching a name and kills them
# '2>/dev/null' hides error messages just in case the process wasn't running
pkill -f "${APP_NAME}.jar" 2>/dev/null

# 'sleep 2' pauses the script for 2 seconds to let the process fully stop
sleep 2

echo "Starting application..."
# 'nohup' prevents the app from stopping when you close your terminal
# '> "${LOG_FILE}" 2>&1' redirects all console output (logs and errors) into the log file
# '&' at the very end runs the process in the background
nohup java -jar "${JAR_FILE}" --spring.profiles.active=prod > "${LOG_FILE}" 2>&1 &

# '$!' gets the Process ID (PID) of the background job we just started
echo "Application started. PID: $!"
echo "Logs: tail -f ${LOG_FILE}"
```

---

## YAML and Configuration

YAML (YAML Ain't Markup Language) is the standard configuration format for Spring Boot, Docker Compose, Kubernetes, and GitHub Actions.

### YAML Syntax

```yaml
# Key-value pairs
server:
  port: 8080
  host: localhost

# Lists
fruits:
  - apple
  - banana
  - cherry

# Nested objects
database:
  primary:
    url: jdbc:postgresql://localhost:5432/mydb
    username: admin
    password: ${DB_PASSWORD}  # Environment variable reference

# Multiline strings
description: |
  This is a multiline
  string that preserves
  line breaks.

# Boolean and null
enabled: true
cache: null
```

### Spring Boot application.yml

```yaml
# application.yml — Main configuration
spring:
  application:
    name: java-learning-hub

  # Database
  datasource:
    url: jdbc:postgresql://localhost:5432/mydb
    username: ${DB_USERNAME:postgres}    # Default value if env var missing
    password: ${DB_PASSWORD:password}
    hikari:
      maximum-pool-size: 10
      minimum-idle: 5

  # JPA / Hibernate
  jpa:
    hibernate:
      ddl-auto: validate    # Never use 'update' or 'create' in production
    show-sql: false
    properties:
      hibernate:
        format_sql: true

  # Profiles
  profiles:
    active: ${SPRING_PROFILE:dev}

# Server
server:
  port: ${PORT:8080}
  servlet:
    context-path: /api

# Logging
logging:
  level:
    root: INFO
    com.myapp: DEBUG
    org.hibernate.SQL: WARN
  file:
    name: /var/log/myapp/application.log
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"

# Actuator (monitoring)
management:
  endpoints:
    web:
      exposure:
        include: health,metrics,info,prometheus
  endpoint:
    health:
      show-details: when_authorized
```

### Environment-Specific Profiles

```yaml
# application-dev.yml
spring:
  datasource:
    url: jdbc:h2:mem:devdb
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
logging:
  level:
    root: DEBUG

---

# application-prod.yml
spring:
  datasource:
    url: jdbc:postgresql://prod-db:5432/mydb
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false
logging:
  level:
    root: WARN
```

---

## Git Workflow

### Branching Strategy (Git Flow)

```
main ──────────────────────────────────────────► (production)
  │
  └── develop ─────────────────────────────────► (integration)
        │         │           │
        └── feature/user-auth  │
                  │            └── feature/payment
                  │
                  └── (merged back to develop)
```

### Essential Git Commands

```bash
# Daily workflow
git checkout -b feature/user-auth develop    # Create feature branch
git add .                                      # Stage changes
git commit -m "feat: add user authentication"  # Commit
git push origin feature/user-auth              # Push to remote

# Keeping up to date
git fetch origin
git rebase origin/develop                      # Rebase onto latest develop

# Pull request workflow
# 1. Push feature branch
# 2. Create PR on GitHub
# 3. Code review
# 4. Squash and merge

# Useful commands
git log --oneline -10                          # Recent commits
git stash                                      # Temporarily save changes
git stash pop                                  # Restore stashed changes
git diff                                       # View unstaged changes
git blame filename.java                        # Who changed what line
```

### .gitignore for Java/Spring

```gitignore
# Build output
target/
build/
*.jar
*.war

# IDE files
.idea/
*.iml
.vscode/
.settings/
.classpath
.project

# Environment
.env
application-local.yml

# OS files
.DS_Store
Thumbs.db

# Logs
*.log
logs/
```

---

## Docker

Docker packages your application and its dependencies into a portable container that runs consistently across any environment.

### Dockerfile for Spring Boot

```dockerfile
# Multi-stage build — keeps final image small
# Stage 1: Build
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline          # Cache dependencies
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Security: don't run as root
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Docker Compose — Java + PostgreSQL + Redis

```yaml
# docker-compose.yml
version: "3.9"

services:
  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILE=prod
      - DB_USERNAME=postgres
      - DB_PASSWORD=secret
      - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/mydb
      - SPRING_REDIS_HOST=redis
    depends_on:
      db:
        condition: service_healthy
      redis:
        condition: service_started

  db:
    image: postgres:16-alpine
    environment:
      POSTGRES_DB: mydb
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: secret
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U postgres"]
      interval: 5s
      timeout: 5s
      retries: 5

  redis:
    image: redis:7-alpine
    ports:
      - "6379:6379"

volumes:
  postgres_data:
```

### Docker Commands

```bash
# Reads the Dockerfile in the current directory ('.') and builds an image named 'myapp' with tag '1.0'
docker build -t myapp:1.0 .

# Runs the image as a container. '-p 8080:8080' maps port 8080 on your computer to 8080 in the container
docker run -p 8080:8080 myapp:1.0

# Starts all services defined in 'docker-compose.yml'. '-d' means detached (run in background)
docker compose up -d

# Stops and removes all containers, networks, and volumes created by 'up'
docker compose down

# Follows the live console output ('-f') for the service named 'app'
docker compose logs -f app

# Lists all currently running containers and their IDs
docker ps

# Opens an interactive terminal ('-it') inside the running container using the shell ('/bin/sh')
docker exec -it <container> /bin/sh

# Lists all downloaded and built Docker images on your machine
docker images

# Cleans up and deletes all stopped containers, unused networks, and dangling images
docker system prune -a
```

---

## CI/CD with GitHub Actions

### Java Build + Test + Deploy Pipeline

```yaml
# .github/workflows/ci.yml
name: Java CI/CD Pipeline

on:
  push:
    branches: [main, develop]
  pull_request:
    branches: [main]

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout code
        uses: actions/checkout@v4

      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with:
          java-version: "17"
          distribution: "temurin"
          cache: maven

      - name: Build and test
        run: mvn clean verify

      - name: Upload test results
        if: always()
        uses: actions/upload-artifact@v4
        with:
          name: test-results
          path: target/surefire-reports/

  docker:
    needs: build
    if: github.ref == 'refs/heads/main'
    runs-on: ubuntu-latest

    steps:
      - name: Checkout code
        uses: actions/checkout@v4

      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with:
          java-version: "17"
          distribution: "temurin"
          cache: maven

      - name: Build JAR
        run: mvn package -DskipTests

      - name: Build and push Docker image
        uses: docker/build-push-action@v5
        with:
          context: .
          push: true
          tags: ghcr.io/${{ github.repository }}:latest
```

---

## Kubernetes Basics

Kubernetes (K8s) orchestrates containers at scale. As a Java developer, you need to understand the core concepts.

### Key Concepts

| Concept | What It Is | Analogy |
|---------|-----------|---------|
| Pod | Smallest deployable unit (1+ containers) | A single running instance of your app |
| Deployment | Manages pod replicas, rolling updates | "Keep 3 copies of my app running" |
| Service | Stable network endpoint for pods | Load balancer / DNS name for your app |
| ConfigMap | External configuration (non-sensitive) | Your `application.yml` but stored in K8s |
| Secret | External sensitive data | DB passwords, API keys |
| Ingress | External HTTP routing | Nginx reverse proxy |

### Deployment Manifest

```yaml
# k8s/deployment.yml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: myapp
spec:
  replicas: 3
  selector:
    matchLabels:
      app: myapp
  template:
    metadata:
      labels:
        app: myapp
    spec:
      containers:
        - name: myapp
          image: ghcr.io/beastshriram/myapp:latest
          ports:
            - containerPort: 8080
          env:
            - name: SPRING_PROFILE
              value: prod
            - name: DB_PASSWORD
              valueFrom:
                secretKeyRef:
                  name: db-secret
                  key: password
          resources:
            requests:
              memory: "256Mi"
              cpu: "250m"
            limits:
              memory: "512Mi"
              cpu: "500m"
          readinessProbe:
            httpGet:
              path: /api/actuator/health
              port: 8080
            initialDelaySeconds: 30
            periodSeconds: 10
---
apiVersion: v1
kind: Service
metadata:
  name: myapp-service
spec:
  selector:
    app: myapp
  ports:
    - port: 80
      targetPort: 8080
  type: ClusterIP
```

---

## Monitoring and Observability

### Spring Boot Actuator

```xml
<!-- pom.xml -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

**Key endpoints:**

| Endpoint | Purpose |
|----------|---------|
| `/actuator/health` | Application health status |
| `/actuator/metrics` | JVM metrics, HTTP request stats |
| `/actuator/prometheus` | Prometheus-format metrics |
| `/actuator/info` | Application info (version, git commit) |

### Logging Best Practices

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public User findById(Long id) {
        log.info("Finding user by id={}", id);        // INFO: normal operations
        try {
            User user = userRepo.findById(id).orElseThrow();
            log.debug("Found user: {}", user.getName()); // DEBUG: detailed info
            return user;
        } catch (Exception e) {
            log.error("Failed to find user id={}", id, e); // ERROR: with stack trace
            throw e;
        }
    }
}
```

**Logback configuration (logback-spring.xml):**
```xml
<configuration>
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>/var/log/myapp/application.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>/var/log/myapp/application.%d{yyyy-MM-dd}.log</fileNamePattern>
            <maxHistory>30</maxHistory>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <root level="INFO">
        <appender-ref ref="CONSOLE" />
        <appender-ref ref="FILE" />
    </root>
</configuration>
```
