---
title: Master AI — Complete AI Engineering Guide
description: Comprehensive guide to AI integration in Java — LLM architecture, prompt engineering, Spring AI, LangChain4j, RAG techniques, agent patterns, MCP, fine-tuning, evaluation, and production deployment.
---

# Master AI — Complete AI Engineering Guide

A comprehensive, interview-ready guide to building AI-powered Java applications. Covers foundational theory, frameworks, production patterns, and hands-on projects.

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
| **Transformer** | The neural network architecture behind all modern LLMs (GPT, Claude, Llama, Gemini) |
| **Token** | A subword unit. "Hello world" → ["Hello", " world"] (2 tokens). ~4 chars per token on average |
| **Context window** | Maximum tokens the model can process at once (e.g., GPT-4o: 128K, Claude 3.5: 200K) |
| **Temperature** | Controls randomness. 0 = deterministic, 1 = creative/random |
| **Top-p (Nucleus Sampling)** | Only consider tokens whose cumulative probability exceeds `p` |
| **Top-k** | Only consider the `k` most probable next tokens |
| **System prompt** | Instructions that define the model's behavior/persona |
| **Embeddings** | Dense vector representations of text enabling semantic similarity search |
| **Inference** | The process of generating output from a trained model |

### How a Transformer Works (Simplified)

```
Input Text
    ↓
1. Tokenization (BPE / SentencePiece)
    ↓  "Hello world" → [15496, 995]
2. Token Embedding + Positional Encoding
    ↓  Each token → dense vector (d=768 to 12288)
3. Self-Attention (Multi-Head)
    ↓  Each token attends to every other token
    ↓  Q×K^T / √d_k → softmax → × V
4. Feed-Forward Network
    ↓  Per-token non-linear transformation
5. Repeat layers 2-4 (12 to 96+ times)
    ↓
6. Final Linear + Softmax → probability distribution
    ↓
7. Sample next token → append → repeat
```

**Tokenization methods:**

| Method | Description | Used By |
|--------|-------------|---------|
| **BPE (Byte Pair Encoding)** | Iteratively merges most frequent character pairs | GPT, Llama |
| **SentencePiece** | Language-agnostic subword tokenizer | T5, Gemini |
| **WordPiece** | Similar to BPE but uses likelihood instead of frequency | BERT |

### Self-Attention Mechanism

Self-attention allows each token to "look at" every other token in the sequence to understand context.

```
For each token:
  Q (Query)  = token × W_Q    "What am I looking for?"
  K (Key)    = token × W_K    "What do I contain?"
  V (Value)  = token × W_V    "What do I provide?"

Attention(Q, K, V) = softmax(Q × K^T / √d_k) × V
```

**Multi-head attention** runs multiple attention computations in parallel (e.g., 12-96 heads), each learning different relationships (syntax, semantics, coreference, etc.).

### Model Comparison

| Model | Provider | Context | Strengths | API Cost (approx) |
|-------|----------|---------|-----------|-------------------|
| **GPT-4o** | OpenAI | 128K | General reasoning, coding, multimodal | $2.50/$10 per 1M tokens (in/out) |
| **GPT-4o-mini** | OpenAI | 128K | Cost-effective, fast | $0.15/$0.60 per 1M tokens |
| **Claude 3.5 Sonnet** | Anthropic | 200K | Long-context, nuanced writing, coding | $3/$15 per 1M tokens |
| **Gemini 2.0 Flash** | Google | 1M | Massive context, multimodal, fast | $0.10/$0.40 per 1M tokens |
| **Llama 3 (70B)** | Meta | 128K | Open-source, self-hosted, no API cost | Free (compute cost only) |
| **Mistral Large** | Mistral | 128K | Open-weight, strong reasoning | $2/$6 per 1M tokens |

### Inference Optimization

| Technique | Description | Benefit |
|-----------|-------------|---------|
| **Quantization** | Reduce weight precision (FP32 → INT8/INT4) | 2-4× smaller model, faster inference |
| **KV Cache** | Cache key-value pairs from previous tokens | Avoids redundant computation during generation |
| **Speculative Decoding** | Small model drafts, large model verifies | 2-3× faster generation |
| **Batching** | Process multiple requests simultaneously | Higher throughput |
| **Distillation** | Train a smaller model to mimic a larger one | Smaller, faster model with similar quality |

---

## Part 2 — Prompt Engineering

### Why Prompt Engineering Matters

The same model can produce vastly different outputs based on how you prompt it. Prompt engineering is the skill of crafting inputs to get optimal outputs — it's the highest-leverage skill in AI engineering.

### Prompting Techniques

| Technique | Description | When to Use |
|-----------|-------------|-------------|
| **Zero-shot** | No examples, just the instruction | Simple tasks the model already understands |
| **Few-shot** | Provide 2-5 examples before the question | Pattern-following, classification, formatting |
| **Chain-of-Thought (CoT)** | Ask model to "think step by step" | Math, logic, multi-step reasoning |
| **Self-Consistency** | Generate multiple CoT paths, take majority vote | Improved accuracy on reasoning tasks |
| **ReAct** | Reason + Act — interleave thinking with tool use | Agent-based systems |
| **Tree-of-Thought** | Explore multiple reasoning branches | Complex problem solving |

### Prompt Templates in Spring AI

```java
// Using Spring AI's PromptTemplate
@Service
public class StructuredPromptService {
    private final ChatClient chatClient;

    // Few-shot prompt template
    private static final String FEW_SHOT_TEMPLATE = """
        Classify the following customer message into one of these categories:
        - billing
        - technical
        - general

        Examples:
        Message: "My payment failed" → Category: billing
        Message: "App crashes on login" → Category: technical
        Message: "What are your hours?" → Category: general

        Message: "{userMessage}" → Category:
        """;

    public String classifyMessage(String userMessage) {
        PromptTemplate template = new PromptTemplate(FEW_SHOT_TEMPLATE);
        Prompt prompt = template.create(Map.of("userMessage", userMessage));
        return chatClient.call(prompt).getResult().getOutput().getContent();
    }

    // Chain-of-Thought prompt
    private static final String COT_TEMPLATE = """
        You are a senior Java developer. Analyze the following code for bugs.

        Think step by step:
        1. First, understand what the code is trying to do
        2. Check for null pointer risks
        3. Check for concurrency issues
        4. Check for resource leaks
        5. Provide your final assessment

        Code:
        ```java
        {code}
        ```

        Analysis:
        """;

    public String analyzeCode(String code) {
        PromptTemplate template = new PromptTemplate(COT_TEMPLATE);
        Prompt prompt = template.create(Map.of("code", code));
        return chatClient.call(prompt).getResult().getOutput().getContent();
    }
}
```

### System Prompt Best Practices

| Principle | Example |
|-----------|---------|
| **Be specific about role** | "You are a senior Java backend engineer with 10 years of experience" |
| **Define output format** | "Respond in JSON with fields: category, confidence, explanation" |
| **Set constraints** | "Only use information from the provided context. If unsure, say 'I don't know'" |
| **Provide examples** | Include 2-3 examples of desired input/output pairs |
| **Specify what NOT to do** | "Do not make up information. Do not include code you haven't verified" |

---

## Part 3 — Spring AI Framework

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

    # Retry configuration
    retry:
      max-attempts: 3
      backoff:
        initial-interval: 1000
        multiplier: 2
        max-interval: 10000
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

### Structured Output Parsing

```java
// Define your output structure
public record MovieRecommendation(
    String title,
    int year,
    String genre,
    double rating,
    String reason
) {}

@Service
public class MovieService {
    private final ChatClient chatClient;

    public List<MovieRecommendation> getRecommendations(String preferences) {
        String prompt = """
            Based on these preferences: %s
            Recommend 3 movies. For each, provide:
            - title, year, genre, rating (out of 10), and reason.
            Respond as a JSON array.
            """.formatted(preferences);

        String response = chatClient.call(new Prompt(prompt))
            .getResult().getOutput().getContent();

        // Parse JSON response into typed objects
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response,
            new TypeReference<List<MovieRecommendation>>() {});
    }
}
```

### Streaming Responses

```java
@Service
public class StreamingChatService {
    private final ChatClient chatClient;

    // For real-time streaming (SSE)
    public Flux<String> streamChat(String userInput) {
        Prompt prompt = new Prompt(List.of(
            new SystemMessage("You are a helpful assistant."),
            new UserMessage(userInput)
        ));

        return chatClient.stream(prompt)
            .map(response -> response.getResult().getOutput().getContent())
            .filter(Objects::nonNull);
    }
}

@RestController
public class StreamController {
    private final StreamingChatService streamService;

    @GetMapping(value = "/api/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamChat(@RequestParam String message) {
        return streamService.streamChat(message);
    }
}
```

---

## Part 4 — LangChain4j

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

### AI Services (Declarative Interface)

```java
// Define your AI service as a Java interface
interface JavaTutor {

    @SystemMessage("You are a patient Java tutor. Explain concepts simply with examples.")
    String explain(@UserMessage String concept);

    @SystemMessage("You are a code reviewer. Be constructive.")
    String review(@UserMessage String code);
}

// LangChain4j generates the implementation
JavaTutor tutor = AiServices.builder(JavaTutor.class)
    .chatLanguageModel(model)
    .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
    .build();

String explanation = tutor.explain("What are Java generics?");
String codeReview = tutor.review("public void process(List list) { ... }");
```

---

## Part 5 — RAG (Retrieval-Augmented Generation)

### What is RAG?

RAG augments an LLM's knowledge by retrieving relevant documents from a knowledge base before generating a response. This solves two key problems:

1. **Knowledge cutoff:** LLMs don't know about events after training
2. **Hallucination:** By grounding responses in actual documents, hallucinations are reduced

### RAG Pipeline

```
User Query
    ↓
1. Embed the query → vector (e.g., 1536 dimensions)
    ↓
2. Search vector store → top-k relevant documents
    ↓
3. Construct prompt = system instructions + retrieved docs + user query
    ↓
4. Send to LLM → generate response grounded in retrieved context
    ↓
5. (Optional) Cite sources in the response
```

### RAG Techniques Overview

| Technique | Description | When to Use |
|-----------|-------------|-------------|
| **Simple RAG** | Encode documents → vector store → retrieve top-k | Starting point, small knowledge bases |
| **BM25 RAG** | Keyword-based retrieval (TF-IDF variant) | When exact keyword matching matters |
| **Hybrid RAG** | Combine dense (embedding) + sparse (BM25) retrieval | Best of both worlds, production systems |
| **ReRanker RAG** | Initial retrieval → re-rank with a cross-encoder | Improve precision of top results |
| **Sentence Window** | Retrieve sentence + surrounding context | Fine-grained retrieval |
| **Auto Merging** | Merge overlapping/redundant retrieved chunks | Reduce noise in context |
| **HyDE** | Generate hypothetical answer → use it as query | Abstract or vague queries |
| **Query Transformation** | Rewrite/expand the query before retrieval | Complex or ambiguous queries |
| **Self Query** | Model generates structured filters from natural language | Metadata-filtered retrieval |
| **RAG Fusion** | Multiple retrievals → merge and re-rank results | Comprehensive coverage |
| **RAPTOR** | Hierarchical summarization for multi-level retrieval | Large document collections |
| **ColBERT** | Token-level dense retrieval | High-precision search |
| **Graph RAG** | Knowledge graph-based retrieval | Relationship-heavy data |
| **Agentic RAG** | Agent decides when and how to retrieve | Complex multi-step reasoning |
| **Vision RAG** | Multi-modal retrieval (text + images) | Documents with diagrams/charts |
| **CAG** | Cache-augmented generation | Repeated similar queries |

### Embedding Models Comparison

| Model | Dimensions | Strengths | Cost |
|-------|-----------|-----------|------|
| **OpenAI text-embedding-3-small** | 1536 | Good general purpose, low cost | $0.02 / 1M tokens |
| **OpenAI text-embedding-3-large** | 3072 | Higher quality | $0.13 / 1M tokens |
| **Cohere embed-v3** | 1024 | Multilingual, search-optimized | $0.10 / 1M tokens |
| **BGE-large-en** | 1024 | Open-source, high quality | Free (self-hosted) |
| **all-MiniLM-L6-v2** | 384 | Fast, lightweight, open-source | Free (self-hosted) |

### Vector Database Options

| Database | Type | Key Feature | Best For |
|----------|------|-------------|----------|
| **pgvector** | PostgreSQL extension | No new infra; lives in your existing DB | Teams already on PostgreSQL |
| **Pinecone** | Managed cloud | Fully managed, scalable | Production with minimal ops |
| **Weaviate** | Open-source | GraphQL API, hybrid search | Flexible self-hosted |
| **Chroma** | Open-source | Simple API, easy to start | Prototyping, small projects |
| **Milvus** | Open-source | High performance, GPU support | Large-scale production |
| **Qdrant** | Open-source | Rust-based, fast filtering | Performance-critical apps |

### Complete RAG Implementation

```java
public class RAGSystem {
    private final ChatLanguageModel model;
    private final EmbeddingModel embeddingModel;
    private final InMemoryEmbeddingStore<TextSegment> store;

    // Index documents
    public void indexDocuments(List<String> documents) {
        for (String doc : documents) {
            // Chunk the document first
            List<String> chunks = splitIntoChunks(doc, 500, 50); // size=500, overlap=50
            for (String chunk : chunks) {
                Embedding emb = embeddingModel.embed(chunk).content();
                store.add(emb, new TextSegment(chunk, null));
            }
        }
    }

    // Query with RAG
    public String query(String question) {
        // 1. Embed the question
        Embedding queryEmb = embeddingModel.embed(question).content();

        // 2. Retrieve relevant documents
        List<EmbeddingMatch<TextSegment>> matches = store.findRelevant(queryEmb, 3);

        // 3. Filter by relevance score
        String context = matches.stream()
            .filter(m -> m.score() > 0.7) // Only high-relevance matches
            .map(m -> m.embedded().text())
            .collect(Collectors.joining("\n---\n"));

        if (context.isEmpty()) {
            return "I don't have enough information to answer that question.";
        }

        // 4. Generate grounded response
        String prompt = """
            Based on the following context, answer the question.
            If the answer is not in the context, say "I don't have that information."

            Context:
            %s

            Question: %s

            Answer:
            """.formatted(context, question);

        return model.generate(prompt);
    }

    private List<String> splitIntoChunks(String text, int chunkSize, int overlap) {
        List<String> chunks = new ArrayList<>();
        for (int i = 0; i < text.length(); i += (chunkSize - overlap)) {
            chunks.add(text.substring(i, Math.min(i + chunkSize, text.length())));
        }
        return chunks;
    }
}
```

### Chunking Strategies

| Strategy | Description | Best For | Chunk Overlap |
|----------|-------------|----------|---------------|
| **Fixed size** | Split every N characters/tokens | Simple, predictable | 10-20% overlap recommended |
| **Sentence-based** | Split on sentence boundaries | Preserving meaning | 1-2 sentence overlap |
| **Paragraph-based** | Split on paragraph breaks | Structured documents | No overlap needed |
| **Semantic chunking** | Split when topic changes (using embeddings) | High-quality retrieval | Automatic |
| **Recursive** | Try largest split first, fall back to smaller | General purpose | Configurable |

### RAG Evaluation Metrics

| Metric | What It Measures | How to Compute |
|--------|-----------------|----------------|
| **Faithfulness** | Is the answer supported by retrieved context? | LLM-as-judge against context |
| **Answer Relevance** | Does the answer address the question? | Semantic similarity (question ↔ answer) |
| **Context Precision** | Are the retrieved docs relevant to the question? | Ratio of relevant docs in top-k |
| **Context Recall** | Did we retrieve all necessary information? | Coverage of ground-truth answer |

---

## Part 6 — AI Agents

### What is an Agent?

An agent is an AI system that can autonomously decide **which actions to take** to accomplish a goal. Unlike simple chat, agents can:

1. **Reason** about a problem (observe → think)
2. **Select and use tools** (act)
3. **Process tool results** (observe)
4. **Iterate** until the task is complete

### Agent Patterns

#### 1. Reflection Pattern

The agent evaluates its own output and iteratively improves it.

```java
public class ReflectionAgent {
    private final ChatLanguageModel model;

    public String generateWithReflection(String task, int maxIterations) {
        String draft = model.generate("Complete this task: " + task);

        for (int i = 0; i < maxIterations; i++) {
            // Self-critique
            String critique = model.generate(
                "Review this response for errors, missing details, and improvements:\n" + draft
            );

            // Check if good enough
            if (critique.toLowerCase().contains("no issues") ||
                critique.toLowerCase().contains("looks good")) {
                break;
            }

            // Refine based on critique
            draft = model.generate(
                "Original: " + draft + "\nCritique: " + critique +
                "\nGenerate an improved version addressing the critique."
            );
        }

        return draft;
    }
}
```

#### 2. ReAct Pattern (Reason + Act)

The agent interleaves reasoning steps with tool calls.

```
Thought: I need to find the user's order status. Let me query the database.
Action: queryDatabase("SELECT status FROM orders WHERE user_id = 123")
Observation: [{status: "shipped", tracking: "1Z999AA10123456784"}]
Thought: The order is shipped. I should provide the tracking number.
Answer: Your order has been shipped! Tracking: 1Z999AA10123456784
```

```java
public class ReActAgent {
    private final ChatLanguageModel model;
    private final Map<String, Function<String, String>> tools;

    public String solve(String question) {
        StringBuilder scratchpad = new StringBuilder();
        String systemPrompt = """
            You are a helpful agent. Use the following format:
            Thought: your reasoning
            Action: toolName(argument)
            ... (wait for Observation)
            Thought: reasoning about observation
            Answer: final answer to the user

            Available tools: %s
            """.formatted(tools.keySet());

        for (int step = 0; step < 5; step++) {
            String response = model.generate(
                systemPrompt + "\nQuestion: " + question + "\n" + scratchpad
            );

            // Parse action from response
            if (response.contains("Answer:")) {
                return response.substring(response.indexOf("Answer:") + 8).trim();
            }

            if (response.contains("Action:")) {
                String action = parseAction(response);
                String toolName = action.split("\\(")[0].trim();
                String arg = action.substring(action.indexOf('(') + 1, action.lastIndexOf(')'));

                String observation = tools.get(toolName).apply(arg);
                scratchpad.append(response)
                    .append("\nObservation: ").append(observation).append("\n");
            }
        }
        return "I couldn't find an answer within the step limit.";
    }
}
```

#### 3. Tool Use Pattern

```java
import dev.langchain4j.agent.tool.Tool;

public class DeveloperTools {

    @Tool("Search for Java documentation")
    public String searchDocs(String query) {
        return "Documentation result for: " + query;
    }

    @Tool("Execute a SQL query against the database")
    public String executeQuery(String sql) {
        // Validate SQL (prevent injection!)
        if (sql.toLowerCase().contains("drop") || sql.toLowerCase().contains("delete")) {
            return "Error: Destructive queries are not allowed.";
        }
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

    @Tool("Send an HTTP GET request to a URL")
    public String httpGet(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url)).GET().build();
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
```

#### 4. Planning Pattern

```java
public class PlanningAgent {
    private final ChatLanguageModel model;

    public String solve(String goal) {
        // Step 1: Create plan
        String plan = model.generate(
            "Break this goal into numbered steps (max 5): " + goal
        );

        // Step 2: Execute each step
        StringBuilder results = new StringBuilder();
        for (String step : plan.split("\n")) {
            if (step.trim().isEmpty()) continue;
            String result = model.generate("Execute this step: " + step);
            results.append(step).append("\nResult: ").append(result).append("\n\n");
        }

        // Step 3: Synthesize
        return model.generate("Summarize these results into a coherent answer:\n" + results);
    }
}
```

#### 5. Multi-Agent Pattern

Multiple specialized agents collaborate to solve complex problems.

```java
public class MultiAgentSystem {
    private final ChatLanguageModel researcher;
    private final ChatLanguageModel coder;
    private final ChatLanguageModel reviewer;

    public String buildFeature(String requirement) {
        // Agent 1: Research
        String research = researcher.generate(
            "Research the best approach for: " + requirement +
            "\nConsider: design patterns, performance, edge cases."
        );

        // Agent 2: Implement
        String code = coder.generate(
            "Based on this research, write production-quality Java code:\n" + research +
            "\nInclude error handling, logging, and javadoc."
        );

        // Agent 3: Review
        String review = reviewer.generate(
            "Review this code for bugs, security issues (OWASP), and improvements:\n" + code
        );

        // Agent 4: Iterate if needed
        if (review.toLowerCase().contains("critical") || review.toLowerCase().contains("bug")) {
            code = coder.generate(
                "Fix these issues in the code:\n" + review + "\n\nOriginal code:\n" + code
            );
        }

        return "Code:\n" + code + "\n\nReview:\n" + review;
    }
}
```

### Agent Memory Types

| Memory Type | Description | Implementation |
|-------------|-------------|----------------|
| **Short-term** | Current conversation context | Message list / sliding window |
| **Long-term** | Facts learned across sessions | Vector store / database |
| **Episodic** | Past experiences and outcomes | Event log with embeddings |
| **Procedural** | Learned procedures and workflows | Tool descriptions, prompts |

---

## Part 7 — MCP (Model Context Protocol)

### What is MCP?

The Model Context Protocol is an open standard for connecting AI models to external data sources and tools. It defines a client-server architecture where:

- **MCP Client:** The AI application (your Spring Boot app)
- **MCP Server:** Exposes tools, resources, and prompts to the client

### Why MCP Matters

Without MCP, every AI integration requires custom code. MCP standardizes how models access external capabilities — similar to how HTTP standardized web communication.

| Without MCP | With MCP |
|-------------|----------|
| Custom integration per tool | Standard protocol for all tools |
| Tight coupling to AI provider | Provider-agnostic tool access |
| No discoverability | Tools self-describe their capabilities |
| Manual context management | Automatic context injection |

### Building an MCP Server

```java
@McpServer
public class JavaDocsServer {

    @McpTool(description = "Search Java API documentation for a class")
    public String searchJavaDocs(String className) {
        return "Documentation for " + className + ": ...";
    }

    @McpTool(description = "Get all method signatures for a Java class")
    public String getMethods(String className) {
        return "Methods for " + className + ": ...";
    }

    @McpTool(description = "Run a Java code snippet and return the output")
    public String executeJava(String code) {
        // Sandboxed execution
        return "Output: ...";
    }

    @McpResource(uri = "docs://java/tutorials")
    public String getTutorials() {
        return "Available tutorials: Streams, Collections, Concurrency...";
    }
}
```

---

## Part 8 — Fine-Tuning Concepts

### When to Fine-Tune vs RAG vs Prompt Engineering

```
┌─────────────────────────────────────────────────────────────┐
│ START: Can prompt engineering solve it?                      │
│   YES → Use prompt engineering (cheapest, fastest)           │
│   NO  → Does the model need external knowledge?             │
│           YES → Use RAG (retrieval-augmented generation)     │
│           NO  → Does the model need a new behavior/style?   │
│                   YES → Fine-tune                            │
│                   NO  → Combine RAG + better prompts         │
└─────────────────────────────────────────────────────────────┘
```

| Approach | When to Use | Cost | Latency |
|----------|-------------|------|---------|
| **Prompt Engineering** | Model knows how but needs guidance | Free (just tokens) | Same |
| **RAG** | Model needs external/updated knowledge | Storage + retrieval | +100-500ms |
| **Fine-Tuning** | Model needs new behavior, style, or domain expertise | Training compute | Faster inference |
| **RAG + Fine-Tuning** | Both new knowledge and new behavior | Highest | Variable |

### Fine-Tuning Methods

| Method | Description | Resource Needs |
|--------|-------------|----------------|
| **Full Fine-Tuning** | Update all model parameters | Very high (multiple GPUs) |
| **LoRA** | Low-Rank Adaptation — freeze base weights, train small adapter matrices | Low (single GPU) |
| **QLoRA** | LoRA with quantized base model (4-bit) | Very low (consumer GPU) |
| **Prefix Tuning** | Prepend learnable tokens to input | Low |

### Dataset Preparation

```json
// Training data format (OpenAI style)
{"messages": [
  {"role": "system", "content": "You are a Java code reviewer."},
  {"role": "user", "content": "Review this code: public void process(List items) {...}"},
  {"role": "assistant", "content": "Issues found:\n1. Raw type List..."}
]}
```

**Guidelines:**

- Minimum 50-100 examples (more = better, diminishing returns after ~1000)
- High quality > quantity — curate carefully
- Include edge cases and varied examples
- Validate with a held-out test set

---

## Part 9 — Evaluation and Observability

### LLM Evaluation Metrics

| Metric | What It Measures | Method |
|--------|-----------------|--------|
| **Accuracy** | Correct answers vs total | Exact match / fuzzy match |
| **Faithfulness** | Answer supported by context (no hallucination) | LLM-as-judge |
| **Relevance** | Answer addresses the question | Semantic similarity |
| **Toxicity** | Harmful or inappropriate content | Classifier / LLM-as-judge |
| **Latency** | Time to first token / total generation time | Instrumentation |
| **Cost** | Total token usage × price | Token counting |

### Hallucination Detection

```java
@Service
public class HallucinationDetector {
    private final ChatLanguageModel judge;

    public boolean isHallucination(String context, String answer) {
        String prompt = """
            Given the following context and answer, determine if the answer
            contains any claims NOT supported by the context.

            Context: %s

            Answer: %s

            Respond with only "FAITHFUL" or "HALLUCINATION" followed by explanation.
            """.formatted(context, answer);

        String verdict = judge.generate(prompt);
        return verdict.toUpperCase().contains("HALLUCINATION");
    }
}
```

### Token Usage and Cost Tracking

```java
@Component
public class TokenUsageTracker {
    private final AtomicLong totalInputTokens = new AtomicLong(0);
    private final AtomicLong totalOutputTokens = new AtomicLong(0);

    // Pricing per 1M tokens (example: GPT-4o-mini)
    private static final double INPUT_COST_PER_M = 0.15;
    private static final double OUTPUT_COST_PER_M = 0.60;

    public void track(ChatResponse response) {
        Usage usage = response.getMetadata().getUsage();
        totalInputTokens.addAndGet(usage.getInputTokens());
        totalOutputTokens.addAndGet(usage.getOutputTokens());
    }

    public double getTotalCost() {
        return (totalInputTokens.get() / 1_000_000.0) * INPUT_COST_PER_M +
               (totalOutputTokens.get() / 1_000_000.0) * OUTPUT_COST_PER_M;
    }

    public String getReport() {
        return String.format("Input: %d tokens | Output: %d tokens | Cost: $%.4f",
            totalInputTokens.get(), totalOutputTokens.get(), getTotalCost());
    }
}
```

---

## Part 10 — Production Patterns

### Rate Limiting

```java
@Component
public class AIRateLimiter {
    // Sliding window rate limiter
    private final Semaphore permits;
    private final ScheduledExecutorService scheduler;

    public AIRateLimiter(
        @Value("${ai.rate-limit.requests-per-minute:60}") int rpm
    ) {
        this.permits = new Semaphore(rpm);
        this.scheduler = Executors.newSingleThreadScheduledExecutor();

        // Replenish permits every minute
        scheduler.scheduleAtFixedRate(
            () -> permits.release(rpm - permits.availablePermits()),
            1, 1, TimeUnit.MINUTES
        );
    }

    public <T> T executeWithRateLimit(Supplier<T> aiCall) {
        if (!permits.tryAcquire(5, TimeUnit.SECONDS)) {
            throw new RateLimitExceededException("AI rate limit exceeded. Try again later.");
        }
        return aiCall.get();
    }
}
```

### Caching Strategies

```java
@Service
public class CachedAIService {
    private final ChatLanguageModel model;
    private final Cache<String, String> cache;

    public CachedAIService(ChatLanguageModel model) {
        this.model = model;
        this.cache = Caffeine.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(1, TimeUnit.HOURS)
            .build();
    }

    public String chat(String input) {
        String cacheKey = hashInput(input);
        return cache.get(cacheKey, k -> model.generate(input));
    }

    // For semantic caching: embed the query and check similarity
    // to cached queries before calling the model
    public String semanticCachedChat(String input) {
        Embedding queryEmb = embeddingModel.embed(input).content();
        // Check if similar query exists in cache
        Optional<CacheEntry> cached = findSimilar(queryEmb, 0.95);
        if (cached.isPresent()) return cached.get().response();

        String response = model.generate(input);
        cacheWithEmbedding(queryEmb, input, response);
        return response;
    }
}
```

### Fallback Chains

```java
@Service
public class ResilientAIService {
    private final ChatLanguageModel primary;   // GPT-4o
    private final ChatLanguageModel secondary; // Claude 3.5
    private final ChatLanguageModel fallback;  // Local Ollama

    public String generate(String prompt) {
        // Try primary
        try {
            return primary.generate(prompt);
        } catch (Exception e) {
            log.warn("Primary model failed: {}", e.getMessage());
        }

        // Try secondary
        try {
            return secondary.generate(prompt);
        } catch (Exception e) {
            log.warn("Secondary model failed: {}", e.getMessage());
        }

        // Fallback to local
        try {
            return fallback.generate(prompt);
        } catch (Exception e) {
            log.error("All models failed", e);
            throw new AIServiceUnavailableException("All AI providers are unavailable");
        }
    }
}
```

### Content Filtering / Guardrails

```java
@Service
public class AIGuardrails {

    // Input guardrails — validate before sending to model
    public String sanitizeInput(String userInput) {
        // 1. Check for prompt injection attempts
        if (containsPromptInjection(userInput)) {
            throw new SecurityException("Potential prompt injection detected");
        }

        // 2. Check length limits
        if (userInput.length() > 10_000) {
            throw new ValidationException("Input too long");
        }

        // 3. Remove PII (emails, phone numbers, SSN)
        return removePII(userInput);
    }

    // Output guardrails — validate before returning to user
    public String sanitizeOutput(String modelOutput) {
        // 1. Check for harmful content
        if (containsHarmfulContent(modelOutput)) {
            return "I'm unable to provide that information.";
        }

        // 2. Remove any leaked system prompt content
        modelOutput = removeSystemPromptLeaks(modelOutput);

        return modelOutput;
    }

    private boolean containsPromptInjection(String input) {
        String lower = input.toLowerCase();
        return lower.contains("ignore previous instructions") ||
               lower.contains("you are now") ||
               lower.contains("system prompt");
    }
}
```

---

## Part 11 — Practical Projects

### Project 1: Document Q&A System

Build a system where users upload documents and ask questions about them.

```java
@Service
public class DocumentQAService {
    private final ChatLanguageModel model;
    private final EmbeddingModel embeddingModel;
    private final InMemoryEmbeddingStore<TextSegment> store;

    public void ingestDocument(String content) {
        List<String> chunks = splitIntoChunks(content, 500);
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

### Project 3: Conversational Database Agent

An agent that translates natural language to SQL and queries your database.

```java
@Service
public class DatabaseAgent {
    private final ChatLanguageModel model;
    private final JdbcTemplate jdbc;

    @Tool("Execute a read-only SQL query and return results")
    public String queryDatabase(String sql) {
        // Safety: only allow SELECT
        if (!sql.trim().toUpperCase().startsWith("SELECT")) {
            return "Error: Only SELECT queries are allowed";
        }
        List<Map<String, Object>> results = jdbc.queryForList(sql);
        return results.toString();
    }

    public String ask(String question, String schema) {
        String prompt = """
            Given this database schema:
            %s

            Convert this natural language question to SQL:
            "%s"

            Rules:
            - Only use SELECT queries
            - Use proper JOINs
            - Add LIMIT 10 to prevent large result sets

            SQL:
            """.formatted(schema, question);

        String sql = model.generate(prompt).trim();
        String results = queryDatabase(sql);

        return model.generate(
            "Based on these query results, answer the user's question in natural language.\n" +
            "Question: " + question + "\nResults: " + results
        );
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
- [Google AI Studio](https://aistudio.google.com/){ target=_blank }
- [Ollama](https://ollama.ai/){ target=_blank } — Run LLMs locally
- [Spring AI Project](https://spring.io/projects/spring-ai){ target=_blank }

### Learning Resources

- [LangChain4j Examples](https://github.com/langchain4j/langchain4j-examples){ target=_blank }
- [DeepLearning.AI](https://www.deeplearning.ai/){ target=_blank }
- [AI Engineering Academy RAG Module](https://github.com/adithya-s-k/AI-Engineering.academy/tree/main/RAG){ target=_blank }
- [Prompt Engineering Guide](https://www.promptingguide.ai/){ target=_blank }
