---
name: context-engineering
description: Guide for writing effective custom modes and skills using context engineering best practices. Use when creating or refining system prompts, custom modes, or skills to ensure optimal balance between specificity and flexibility.
---

# Context Engineering for Custom Modes & Skills

You are tasked with creating or refining a custom mode or skill. Apply these context engineering principles to achieve the optimal balance between specificity and flexibility.

## Core Principles

When creating a custom mode or skill:

1. Define the core objective clearly
2. Identify the right altitude for instructions
3. Structure content into logical sections
4. Provide 2-3 canonical examples
5. Design token-efficient tools (if applicable)
6. Test and iterate based on failure modes
7. Validate against the checklist above

Your custom mode or skill should be **clear, concise, and capable** - giving the model just enough guidance to succeed without constraining its intelligence.

### 1. Find the Right Altitude
Your instructions should exist in the "Goldilocks zone":
- **Too Low (Brittle)**: Hardcoded if-else logic, overly prescriptive rules that break easily
- **Too High (Vague)**: Generic guidance that assumes shared context or lacks concrete signals
- **Just Right**: Specific enough to guide behavior, flexible enough to provide strong heuristics

**Example of Right Altitude:**
```
❌ Too Low: "If the user asks about Java, respond with 'Java is a programming language.' If they ask about Python, respond with 'Python is a programming language.'"

❌ Too High: "Help the user with programming questions."

✅ Just Right: "You are a programming assistant. When users ask about languages, provide context-appropriate explanations that match their expertise level. Include practical examples and highlight key differences when comparing technologies."
```

### 2. Minimize Token Count, Maximize Signal
- Use the **smallest possible set** of high-signal tokens
- Remove redundant information and edge cases
- Focus on canonical, diverse examples rather than exhaustive lists

### 3. Structure for Clarity
Organize prompts into distinct sections using Markdown headers:

For example:

- `## Background`
- `## Instructions`
- `## Tool Guidance`
- `## Output Format`
- `## Examples`

## Writing Instruction Best Practices

### General Rule

1. **Expertise Definition**: Applicable when writing instruction for custom mode. What is their expertise?
2. **Core Objective**: What is the primary goal or task?
3. **Key Constraints**: What are the critical boundaries or limitations?
4. **Behavioral Heuristics**: What principles guide decision-making?

### Anti-Patterns to Avoid
- ❌ Listing every possible edge case
- ❌ Hardcoding complex branching logic
- ❌ Assuming the model knows your domain context
- ❌ Mixing multiple concerns without clear separation
- ❌ Using vague language like "be helpful" or "do your best"

### Best Practices
- ✅ Use simple, direct language
- ✅ Provide 2-3 diverse, canonical examples
- ✅ State explicit success criteria
- ✅ Include clear error handling guidance
- ✅ Specify output format expectations

## Examples and Few-Shot Prompting

### Quality Over Quantity
- Provide **2-5 diverse, canonical examples**
- Each example should demonstrate a different aspect of desired behavior
- Avoid redundant examples that show the same pattern
- Example should be minimal and is not too much verbose

### Example Structure
```
<example>
<scenario>Brief description of the situation</scenario>
<input>What the user provides</input>
<reasoning>Key decision points (optional)</reasoning>
<output>Expected response or action</output>
</example>
```

## Context Management Strategies

### For Long-Horizon Tasks

Choose the appropriate instruction strategy based on task characteristics:

**Sub-Task Architectures**: Specialized sub-tasks for focused tasks
   - Best for: Complex research/analysis with parallel exploration
   - Implementation: Main task coordinates, sub-tasks handle deep work

### Just-In-Time Context Retrieval
- Maintain lightweight identifiers (paths, queries, links)
- Load data dynamically at runtime using tools
- Leverage metadata for efficient navigation
- Enable progressive disclosure through exploration
