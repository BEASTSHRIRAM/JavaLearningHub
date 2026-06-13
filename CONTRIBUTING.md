# Contributing to Java Learning Hub

Thank you for wanting to contribute! Here's how you can help us build the best Java learning resource.

## Contribution Guidelines

### Before You Start
- Check existing issues/PRs to avoid duplicates
- For DSA problems, link to LeetCode challenges
- Follow Java naming conventions and best practices
- Add comments explaining complex concepts

### Types of Contributions

#### 1. **Adding a New Lesson**

**For Java Basics/OOP:**
```markdown
---
title: "Understanding Inheritance"
topic: "OOP"
difficulty: "Medium"
time: "15 mins"
prerequisites: ["Classes & Objects", "Encapsulation"]
---

## Concept Explanation
[Your explanation here with examples]

## Code Example
[Example Java code]

## Key Takeaways
- Point 1
- Point 2

## Practice Problems
- [LeetCode Link]
```

**For DSA:**
```markdown
---
title: "Two Sum"
problem_id: "1"
difficulty: "Easy"
leetcode: "https://leetcode.com/problems/two-sum/"
tags: ["Array", "HashMap"]
---

## Problem Statement
[Description]

## Solution
[Code with explanation]

## Time Complexity
O(n), O(n) space
```

#### 2. **Adding Code Examples**

Place in appropriate `examples/` folder:
- Use meaningful class/file names
- Add Javadoc comments
- Include a main() method demonstrating usage

Example structure:
```java
/**
 * Demonstrates the concept of [Topic]
 * Author: Your Name
 * Date: 2026-06-13
 */
public class ExampleName {
    
    public static void main(String[] args) {
        // Example code here
    }
}
```

#### 3. **Updating DSA Problems**

If adding from the DSA repository (https://github.com/BEASTSHRIRAM/DSA-Java):
1. Reference the original problem
2. Add LeetCode link
3. Explain the approach
4. Add multiple solutions if available
5. Note time/space complexity

### Submission Process

1. **Fork** the repository
2. **Branch** from main:
   ```bash
   git checkout -b feature/topic-name
   ```
3. **Commit** with clear message:
   ```bash
   git commit -m "Add: Inheritance lesson with examples"
   ```
4. **Push** to your fork:
   ```bash
   git push origin feature/topic-name
   ```
5. **Create PR** with description of changes

### PR Template

```markdown
## Description
What are you adding/fixing?

## Type of Change
- [ ] New lesson
- [ ] Code example
- [ ] Bug fix
- [ ] Documentation update

## Topics Covered
- Topic 1
- Topic 2

## Related Issues
Fixes #(issue number)
```

### Review Process

- Maintainers will review within 3-5 days
- Feedback may be requested
- Once approved, your PR will be merged
- Your contribution appears on GitHub Pages automatically

---

## Style Guide

### Java Code
- Follow **Google Java Style Guide**
- Use meaningful variable names
- Add comments for complex logic
- Test your code before submitting

### Documentation
- Use clear, simple English
- Use "you" and "we" for engagement
- Add code examples
- Explain the "why" not just the "what"

### File Naming
- Classes: `PascalCase` (e.g., `LinkedListExample.java`)
- Files: `kebab-case` (e.g., `array-basics.md`)
- Folders: `kebab-case` (e.g., `data-structures`)

---

## Reporting Issues

Found a bug or have an idea?

1. Check existing issues first
2. Click **Issues** → **New Issue**
3. Use clear title and description
4. Add labels (bug, enhancement, documentation)
5. Include code snippets if relevant

---

## Checklists Before Submitting

- [ ] Code compiles and runs without errors
- [ ] Follows Java naming conventions
- [ ] Includes comments/documentation
- [ ] LeetCode links tested and valid
- [ ] PR template completed
- [ ] No duplicate content
- [ ] README or docs updated if needed

---

## Community Guidelines

- Be respectful and inclusive
- Help others learn
- No spam or promotional content
- Ask questions if you're stuck
- Share knowledge generously

---

## Questions?

- Open an **Issue** with `question` label
- Start a **Discussion** thread
- Reach out on GitHub

**Thank you for contributing!**


