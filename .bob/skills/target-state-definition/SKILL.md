---
name: target-state-definition
description: Design target architecture and define the end state for migration projects. Creates architecture diagrams, component mappings, and design decisions aligned with platform best practices.
---

# Target State Definition

You are designing the target architecture for a migration project. Your goal is to create a clear, comprehensive vision of the end state that guides all subsequent migration work.

## Objective

Define what the migrated system will look like, including architecture patterns, component organization, technology stack, and key design decisions.

## Required Inputs

- Assessment Report: current architecture, technology stack, dependencies
- Migration Plan: scope, timeline, constraints
- Target Platform Requirements: best practices, compliance needs, performance targets

## Output Document

Create `design_target_architecture.md` containing:

**Executive Summary**: Migration type, scope, target platform, and key architectural decisions

**Target Architecture Diagram**: Visual representation showing components, layers, and relationships

**Component Mapping Matrix**: Table mapping current components to target architecture with transformation type

**Architectural Patterns**: Patterns to adopt (microservices, event-driven, CQRS, etc.) with rationale

**Technology Stack**: Target languages, frameworks, databases, infrastructure with version specifications

**Design Decisions Log**: Key decisions made, alternatives considered, rationale, and trade-offs

## Design Process

1. **Analyze Current State**: Use `list_files`, `list_code_definition_names`, `read_file` to understand existing architecture
2. **Research Target Platform**: Identify best practices, recommended patterns, and constraints
3. **Design Architecture**: Create target architecture aligned with platform standards
4. **Map Components**: Define how each current component maps to target architecture
5. **Document Decisions**: Record all key decisions with clear rationale
6. **Validate Alignment**: Ensure design meets scalability, performance, security requirements

## Key Considerations

- **Platform Best Practices**: Follow target platform's recommended patterns and conventions
- **Scalability**: Design for expected growth and load patterns
- **Security**: Incorporate security by design principles
- **Maintainability**: Favor simplicity and clear component boundaries
- **Migration Feasibility**: Ensure design is achievable within constraints

## Example: Java 11 → 21 Spring Boot Migration

```
Target Architecture:
- Java 21 with virtual threads for improved concurrency
- Spring Boot 3.2.x with native compilation support
- Modular architecture with clear domain boundaries
- RESTful APIs with OpenAPI 3.0 specifications
- PostgreSQL 15 with optimized connection pooling

Key Decisions:
1. Adopt virtual threads (Project Loom) for I/O-heavy operations
   - Rationale: Simplifies async code, improves throughput
   - Trade-off: Requires Java 21, learning curve for team
2. Enable Spring Boot native compilation
   - Rationale: Faster startup, lower memory footprint
   - Trade-off: Longer build times, some reflection limitations
```

## Validation Checklist

- [ ] Architecture diagram clearly shows all major components
- [ ] All current components mapped to target architecture
- [ ] Technology stack versions specified
- [ ] Architectural patterns identified with rationale
- [ ] Design decisions documented with alternatives and trade-offs
- [ ] Architecture aligns with target platform best practices
- [ ] Scalability and performance requirements addressed
- [ ] Security requirements incorporated

## Guardrails

**Read-only analysis**: Examine existing code and configurations but make no modifications

**Design artifacts only**: Create documentation and diagrams, not code changes

**Platform alignment**: Ensure design follows target platform's recommended practices