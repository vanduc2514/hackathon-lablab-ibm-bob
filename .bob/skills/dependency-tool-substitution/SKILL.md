---
name: dependency-tool-substitution
description: Map current dependencies and tools to target platform equivalents. Identifies version updates, replacements, and compatibility requirements for libraries, frameworks, and tooling.
---

# Dependency & Tool Substitution

You are mapping current dependencies to their target platform equivalents. Your goal is to identify what needs to be updated, replaced, or removed during migration.

## Objective

Create a comprehensive mapping of all dependencies, libraries, frameworks, and tools from current to target state, including version compatibility and substitution strategies.

## Required Inputs

- `design_target_architecture.md`: Target technology stack
- `design_component_strategies.md`: Component migration strategies
- Current dependency manifests (pom.xml, package.json, requirements.txt, etc.)
- Target platform compatibility matrix

## Output Document

Create `design_dependency_mapping.md` containing:

**Dependency Inventory**: Complete list of current dependencies with versions and usage

**Substitution Matrix**: Table with columns: Current Dependency | Version | Target Dependency | Version | Change Type | Compatibility Notes

**Breaking Changes**: Dependencies with incompatible APIs requiring code changes

**Deprecated Dependencies**: Libraries to remove with replacement recommendations

**New Dependencies**: Additional libraries needed for target platform

**Tool Updates**: Build tools, CI/CD, testing frameworks, and development tooling changes

## Change Types

- **Direct Update**: Same library, newer version (e.g., Spring Boot 2.7 → 3.2)
- **Equivalent Replacement**: Different library, same functionality (e.g., Log4j → Logback)
- **Platform Native**: Replace with platform-provided service (e.g., custom auth → AWS Cognito)
- **Removal**: Deprecated functionality no longer needed
- **Addition**: New dependency required by target platform

## Analysis Process

1. **Extract Dependencies**: Parse dependency manifests, analyze imports
2. **Check Compatibility**: Verify each dependency works with target platform
3. **Identify Conflicts**: Find version conflicts or incompatible combinations
4. **Research Alternatives**: For incompatible dependencies, find suitable replacements
5. **Document Changes**: Specify exact versions and migration notes
6. **Validate Completeness**: Ensure all transitive dependencies considered

## Example: Java Spring Boot Migration

```
Dependency Mapping: Java 11 → Java 21, Spring Boot 2.7 → 3.2

Direct Updates:
- spring-boot-starter-web: 2.7.18 → 3.2.0
  * Breaking: javax.* → jakarta.* namespace change
  * Action: Update all imports in code
  
- hibernate-core: 5.6.15 → 6.4.0
  * Breaking: Criteria API changes, deprecated methods removed
  * Action: Refactor query code, update entity mappings

Equivalent Replacements:
- javax.validation:validation-api → jakarta.validation:jakarta.validation-api
  * Reason: Jakarta EE namespace migration
  * Compatibility: API mostly unchanged, import updates only

Removals:
- jaxb-api (no longer needed, included in Java 11 but removed in 17+)
  * Action: Add explicit dependency if XML binding still needed
  * Alternative: Use Jackson for JSON, consider removing XML support

Additions:
- spring-boot-starter-actuator: 3.2.0 (new)
  * Reason: Enhanced monitoring for production
  * Configuration: Expose health and metrics endpoints

Tool Updates:
- Maven: 3.6.3 → 3.9.5
- maven-compiler-plugin: 3.8.1 → 3.11.0 (for Java 21 support)
- JUnit: 4.13.2 → 5.10.1 (Jupiter)
```

## Conflict Resolution Strategies

**Version Convergence**: When multiple dependencies require different versions of the same library, choose the highest compatible version

**Exclusions**: Exclude transitive dependencies that conflict, add explicit dependency with correct version

**BOM Management**: Use Bill of Materials (BOM) to manage related dependency versions consistently

**Compatibility Testing**: Validate that resolved versions work together through integration tests

## Validation Checklist

- [ ] All current dependencies inventoried with versions
- [ ] Target dependencies specified with exact versions
- [ ] Breaking changes identified with required code modifications
- [ ] Version conflicts resolved with clear strategy
- [ ] Transitive dependencies considered
- [ ] Build tool and plugin updates specified
- [ ] Testing framework updates documented
- [ ] CI/CD tool compatibility verified

## Guardrails

**Exact versions**: Always specify exact versions, not ranges (e.g., 3.2.0, not 3.2.x)

**Compatibility verification**: Don't assume compatibility—verify with target platform documentation

**Security considerations**: Check for known vulnerabilities in target versions, prefer latest stable releases