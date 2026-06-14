---
title: Spring AI and Agentic Systems
description: Comprehensive guide to AI integration in Java — Spring AI, LangChain4j, RAG techniques, agent patterns, MCP, LLM architecture, and practical projects.
---

# Spring AI and Agentic Systems

A comprehensive guide to building AI-powered Java applications. Covers foundational concepts, frameworks, and production patterns.

<span class="badge badge-advanced">Advanced</span> · **4-6 weeks**

!!! info "Prerequisites"
    Complete Java Basics, OOP, and Java Backend modules. Familiarity with REST APIs and Spring Boot is assumed.

---

## Part 1 — Understanding LLMs

### What is a Large Language Model?

A Large Language Model (LLM) is a neural network trained on massive text datasets to predict the next token (word/subword) in a sequence. Through this simple objective, LLMs learn grammar, facts, reasoning patterns, and coding ability.

**Key concepts:**

| Concept | Description |
|---------|-------------|
| Transformer | The neural network architecture behind all modern LLMs (GPT, Claude, Llama) |
| Token | A subword unit. "Hello world" → ["Hello", " world"] (2 tokens). ~4 chars per token on average |
| Context window | Maximum number of tokens the model can process at once (e.g., GPT-4: 128K tokens) |
| Temperature | Controls randomness. 0 = deterministic, 1 = creative/random |
| Top-p (Nucleus Sampling) | Only consider tokens whose cumulative probability exceeds p |
| System prompt | Instructions that define the model's behavior/persona |
| Embeddings | Dense vector representations of text, enabling semantic similarity search |

### How a Transformer Works (Simplified)

1. **Input text** is split into tokens
2. Each token gets an **embedding vector** (numerical representation)
3. **Self-attention** lets each token "look at" every other token to understand context
4. Multiple attention layers (12-96 layers) refine the understanding
5. Final layer predicts the probability distribution for the **next token**
6. A token is sampled from this distribution, appended, and the process repeats

This is why LLMs "generate" text one token at a time — they are fundamentally **next-token predictors**.

---

## Part 2 — Spring AI Framework

### Overview

Spring AI provides a unified, Spring-native API for integrating AI models. It abstracts provider-specific implementations so you can swap between OpenAI, Anthropic, Ollama, etc. with configuration changes only.

### Setup

```xml
<!-- pom.xml -->
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-openai-spring-boot-starter</artifactId>
    <version>0.8.0</version>
</dependency>

<!-- For local models via Ollama -->
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-ollama-spring-boot-starter</artifactId>
    <version>0.8.0</version>
</dependency>
```

### Configuration

```yaml
# application.yml
spring:
  ai:
    openai:
      api-key: ${OPENAI_API_KEY}
      chat:
        options:
          model: gpt-4o-mini
          temperature: 0.7
          max-tokens: 2048

    # Alternative: Ollama (local, no API key)
    ollama:
      base-url: http://localhost:11434
      chat:
        options:
          model: llama3
```

### Simple Chat Service

```java
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
public class AIChatService {
    private final ChatClient chatClient;

    public AIChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String chat(String userInput) {
        Prompt prompt = new Prompt(List.of(
            new SystemMessage("You are a helpful Java programming assistant."),
            new UserMessage(userInput)
        ));
        return chatClient.call(prompt).getResult().getOutput().getContent();
    }
}

// REST Controller
@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final AIChatService chatService;

    public ChatController(AIChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public Map<String, String> chat(@RequestBody Map<String, String> request) {
        String response = chatService.chat(request.get("message"));
        return Map.of("response", response);
    }
}
```

---

## Part 3 — LangChain4j

### What is LangChain4j?

LangChain4j is the Java port of the LangChain ecosystem. It provides abstractions for building AI-powered applications with:

- Model interactions (chat, completion, embedding)
- Memory management (conversation history)
- Chains (composable pipelines)
- Agents (autonomous tool-using systems)
- RAG (retrieval-augmented generation)

### Setup and Basic Usage

```xml
<dependency>
    <groupId>dev.langchain4j</groupId>
    <artifactId>langchain4j</artifactId>
    <version>0.28.0</version>
</dependency>
<dependency>
    <groupId>dev.langchain4j</groupId>
    <artifactId>langchain4j-open-ai</artifactId>
    <version>0.28.0</version>
</dependency>
```

```java
ChatLanguageModel model = OpenAiChatModel.builder()
    .apiKey(System.getenv("OPENAI_API_KEY"))
    .modelName("gpt-4o-mini")
    .build();

String response = model.generate("Explain Java Streams in 3 sentences.");
System.out.println(response);
```

### Chat with Memory

```java
List<ChatMessage> messages = new ArrayList<>();
messages.add(new SystemMessage("You are a Java tutor."));
messages.add(new UserMessage("What is a HashMap?"));

ChatResponse response1 = model.generate(messages);
messages.add(response1.content());

// Follow-up (model remembers context)
messages.add(new UserMessage("How does it handle collisions?"));
ChatResponse response2 = model.generate(messages);
```

---

## Part 4 — RAG (Retrieval-Augmented Generation)

### What is RAG?

RAG augments an LLM's knowledge by retrieving relevant documents from a knowledge base before generating a response. This solves two key problems:

1. **Knowledge cutoff:** LLMs don't know about events after training
2. **Hallucination:** By grounding responses in actual documents, hallucinations are reduced

### RAG Pipeline

```
User Query
    ↓
1. Embed the query → vector
    ↓
2. Search vector store → top-k relevant documents
    ↓
3. Construct prompt = system instructions + retrieved docs + user query
    ↓
4. Send to LLM → generate response grounded in retrieved context
```

### RAG Techniques Overview

This table is adapted from the [AI Engineering Academy RAG module](https://github.com/adithya-s-k/AI-Engineering.academy), which provides in-depth implementations of each technique.

| Technique | Description | When to Use |
|-----------|-------------|-------------|
| Simple RAG | Encode documents → vector store → retrieve top-k | Starting point, small knowledge bases |
| BM25 RAG | Keyword-based retrieval (TF-IDF variant) | When exact keyword matching matters |
| Hybrid RAG | Combine dense (embedding) + sparse (BM25) retrieval | Best of both worlds, production systems |
| ReRanker RAG | Initial retrieval → re-rank with a cross-encoder | Improve precision of top results |
| Sentence Window | Retrieve sentence + surrounding context | Fine-grained retrieval |
| Auto Merging | Merge overlapping/redundant retrieved chunks | Reduce noise in context |
| HyDE | Generate hypothetical answer → use it as query | Abstract or vague queries |
| Query Transformation | Rewrite/expand the query before retrieval | Complex or ambiguous queries |
| Self Query | Model generates structured filters from natural language | Metadata-filtered retrieval |
| RAG Fusion | Multiple retrievals → merge and re-rank results | Comprehensive coverage |
| RAPTOR | Hierarchical summarization for multi-level retrieval | Large document collections |
| ColBERT | Token-level dense retrieval | High-precision search |
| Graph RAG | Knowledge graph-based retrieval | Relationship-heavy data |
| Agentic RAG | Agent decides when and how to retrieve | Complex multi-step reasoning |
| Vision RAG | Multi-modal retrieval (text + images) | Documents with diagrams/charts |
| CAG | Cache-augmented generation | Repeated similar queries |

### Complete RAG Implementation

```java
public class RAGSystem {
    private final ChatLanguageModel model;
    private final EmbeddingModel embeddingModel;
    private final InMemoryEmbeddingStore<TextSegment> store;

    // Index documents
    public void indexDocuments(List<String> documents) {
        for (String doc : documents) {
            Embedding emb = embeddingModel.embed(doc).content();
            store.add(emb, new TextSegment(doc, null));
        }
    }

    // Query with RAG
    public String query(String question) {
        // 1. Embed the question
        Embedding queryEmb = embeddingModel.embed(question).content();

        // 2. Retrieve relevant documents
        List<EmbeddingMatch<TextSegment>> matches = store.findRelevant(queryEmb, 3);

        // 3. Build context
        String context = matches.stream()
            .map(m -> m.embedded().text())
            .collect(Collectors.joining("\n---\n"));

        // 4. Generate grounded response
        String prompt = "Based on the following context, answer the question.\n\n" +
                        "Context:\n" + context + "\n\n" +
                        "Question: " + question + "\n\nAnswer:";
        return model.generate(prompt);
    }
}
```

### Chunking Strategies

How you split documents affects retrieval quality:

| Strategy | Description | Best For |
|----------|-------------|----------|
| Fixed size | Split every N characters/tokens | Simple, predictable |
| Sentence-based | Split on sentence boundaries | Preserving meaning |
| Paragraph-based | Split on paragraph breaks | Structured documents |
| Semantic chunking | Split when topic changes (using embeddings) | High-quality retrieval |
| Recursive | Try largest split first, fall back to smaller | General purpose |

---

## Part 5 — AI Agents

### What is an Agent?

An agent is an AI system that can autonomously decide **which actions to take** to accomplish a goal. Unlike simple chat, agents can:

1. Reason about a problem
2. Select and use tools
3. Process tool results
4. Iterate until the task is complete

### Agent Patterns

These patterns are based on the [AI Engineering Academy Agent module](https://github.com/adithya-s-k/AI-Engineering.academy).

#### 1. Reflection Pattern

The agent evaluates its own output and iteratively improves it.

```java
public class ReflectionAgent {
    private final ChatLanguageModel model;

    public String generateWithReflection(String task) {
        // Step 1: Generate initial response
        String draft = model.generate("Complete this task: " + task);

        // Step 2: Self-critique
        String critique = model.generate(
            "Review this response for errors and improvements:\n" + draft
        );

        // Step 3: Refine based on critique
        String refined = model.generate(
            "Original: " + draft + "\nCritique: " + critique +
            "\nGenerate an improved version."
        );

        return refined;
    }
}
```

#### 2. Tool Use Pattern

The agent decides which tools to call based on the user's request.

```java
import dev.langchain4j.agent.tool.Tool;

public class DeveloperTools {

    @Tool("Search for Java documentation")
    public String searchDocs(String query) {
        // Call documentation API
        return "Documentation result for: " + query;
    }

    @Tool("Execute a SQL query against the database")
    public String executeQuery(String sql) {
        // Execute and return results
        return "Query results: [...]";
    }

    @Tool("Get current system metrics")
    public String getMetrics() {
        Runtime rt = Runtime.getRuntime();
        return String.format("Memory: %dMB / %dMB, Processors: %d",
            rt.totalMemory() / 1024 / 1024,
            rt.maxMemory() / 1024 / 1024,
            rt.availableProcessors());
    }
}
```

#### 3. Planning Pattern

The agent breaks a complex task into steps before executing.

```java
public class PlanningAgent {
    private final ChatLanguageModel model;

    public String solve(String goal) {
        // Step 1: Create plan
        String plan = model.generate(
            "Break this goal into numbered steps: " + goal
        );

        // Step 2: Execute each step
        StringBuilder results = new StringBuilder();
        for (String step : plan.split("\n")) {
            String result = model.generate("Execute this step: " + step);
            results.append(step).append("\nResult: ").append(result).append("\n\n");
        }

        // Step 3: Synthesize
        return model.generate("Summarize these results:\n" + results);
    }
}
```

#### 4. Multi-Agent Pattern

Multiple specialized agents collaborate to solve complex problems.

```java
public class MultiAgentSystem {
    private final ChatLanguageModel researcher;
    private final ChatLanguageModel coder;
    private final ChatLanguageModel reviewer;

    public String buildFeature(String requirement) {
        // Agent 1: Research
        String research = researcher.generate(
            "Research the best approach for: " + requirement
        );

        // Agent 2: Implement
        String code = coder.generate(
            "Based on this research, write Java code:\n" + research
        );

        // Agent 3: Review
        String review = reviewer.generate(
            "Review this code for bugs, security issues, and improvements:\n" + code
        );

        return "Code:\n" + code + "\n\nReview:\n" + review;
    }
}
```

---

## Part 6 — MCP (Model Context Protocol)

### What is MCP?

The Model Context Protocol is an open standard for connecting AI models to external data sources and tools. It defines a client-server architecture where:

- **MCP Client:** The AI application (your Spring Boot app)
- **MCP Server:** Exposes tools, resources, and prompts to the client

### Why MCP Matters

Without MCP, every AI integration requires custom code. MCP standardizes how models access external capabilities — similar to how HTTP standardized web communication.

### Building an MCP Server (Conceptual)

```java
// An MCP server exposes tools that AI models can call
@McpServer
public class JavaDocsServer {

    @McpTool(description = "Search Java API documentation")
    public String searchJavaDocs(String className) {
        // Return documentation for the given class
        return "Documentation for " + className + ": ...";
    }

    @McpTool(description = "Get method signatures for a Java class")
    public String getMethods(String className) {
        // Use reflection or documentation API
        return "Methods for " + className + ": ...";
    }

    @McpResource(uri = "docs://java/tutorials")
    public String getTutorials() {
        // Return available tutorial resources
        return "Available tutorials: Streams, Collections, Concurrency...";
    }
}
```

---

## Part 7 — Practical Projects

### Project 1: Document Q&A System

Build a system where users upload documents and ask questions about them.

```java
@Service
public class DocumentQAService {
    private final ChatLanguageModel model;
    private final EmbeddingModel embeddingModel;
    private final InMemoryEmbeddingStore<TextSegment> store;

    public void ingestDocument(String content) {
        // Chunk the document
        List<String> chunks = splitIntoChunks(content, 500);

        // Embed and store each chunk
        for (String chunk : chunks) {
            Embedding emb = embeddingModel.embed(chunk).content();
            store.add(emb, new TextSegment(chunk, null));
        }
    }

    public String askQuestion(String question) {
        Embedding queryEmb = embeddingModel.embed(question).content();
        List<EmbeddingMatch<TextSegment>> relevant = store.findRelevant(queryEmb, 3);

        String context = relevant.stream()
            .map(m -> m.embedded().text())
            .collect(Collectors.joining("\n\n"));

        return model.generate(
            "Answer based on context only. If not found, say 'not found'.\n\n" +
            "Context:\n" + context + "\n\nQuestion: " + question
        );
    }

    private List<String> splitIntoChunks(String text, int chunkSize) {
        List<String> chunks = new ArrayList<>();
        for (int i = 0; i < text.length(); i += chunkSize) {
            chunks.add(text.substring(i, Math.min(i + chunkSize, text.length())));
        }
        return chunks;
    }
}
```

### Project 2: Code Review Agent

An AI agent that reviews Java code for bugs, security issues, and best practices.

```java
@Service
public class CodeReviewAgent {
    private final ChatLanguageModel model;

    public CodeReviewResult review(String code) {
        String review = model.generate(
            "You are a senior Java developer. Review this code for:\n" +
            "1. Bugs and logical errors\n" +
            "2. Security vulnerabilities (OWASP Top 10)\n" +
            "3. Performance issues\n" +
            "4. Best practice violations\n" +
            "5. Suggestions for improvement\n\n" +
            "Code:\n```java\n" + code + "\n```\n\n" +
            "Format: For each issue, provide [SEVERITY] Description and Fix."
        );

        return new CodeReviewResult(review);
    }
}
```

---

## Resources

### Official Documentation

- [Spring AI Documentation](https://docs.spring.io/spring-ai/docs/current/reference/html/){ target=_blank }
- [LangChain4j Docs](https://docs.langchain4j.dev/){ target=_blank }
- [AI Engineering Academy](https://github.com/adithya-s-k/AI-Engineering.academy){ target=_blank } — Comprehensive RAG and Agent tutorials
- [Agentscope](https://modelscope.cn/org/agentscope){ target=_blank }

### APIs and Tools

- [OpenAI Platform](https://platform.openai.com/){ target=_blank }
- [Anthropic Claude](https://www.anthropic.com/){ target=_blank }
- [Ollama](https://ollama.ai/){ target=_blank } — Run LLMs locally
- [Spring AI Project](https://spring.io/projects/spring-ai){ target=_blank }

### Learning Resources

- [LangChain4j Examples](https://github.com/langchain4j/langchain4j-examples){ target=_blank }
- [DeepLearning.AI](https://www.deeplearning.ai/){ target=_blank }
- [AI Engineering Academy RAG Module](https://github.com/adithya-s-k/AI-Engineering.academy/tree/main/RAG){ target=_blank }
