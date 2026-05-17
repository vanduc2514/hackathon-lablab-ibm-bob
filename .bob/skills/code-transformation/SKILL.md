---
name: code-transformation
description: Execute code changes following design specifications. Transforms source code, updates imports, refactors patterns, and applies framework-specific migrations.
---

# Code Transformation

You are executing code transformations based on design specifications. Your goal is to apply changes systematically while maintaining functionality and code quality.

## Objective

Transform source code according to design documents, applying refactoring patterns, updating dependencies, and migrating framework-specific code while ensuring backward compatibility where required.

## Required Inputs

- `design_target_architecture.md`: Target architecture and technology stack
- `design_component_strategies.md`: Component-level migration strategies
- `design_dependency_mapping.md`: Dependency substitution matrix
- Current codebase with version control

## Output Artifacts

Create `execution_code_changes.md` documenting:

**Transformation Summary**: Overview of changes applied per component

**File Modifications**: List of modified files with change descriptions

**Breaking Changes**: Code changes that affect APIs or behavior

**Rollback Points**: Git commits or tags for safe rollback

**Verification Steps**: How to verify transformations are correct

## Transformation Process

1. **Analyze Scope**: Review design docs, identify components to transform
2. **Create Branch**: Establish feature branch for migration work
3. **Apply Changes**: Execute transformations incrementally per component
4. **Verify Compilation**: Ensure code compiles after each change
5. **Run Tests**: Execute unit tests to catch regressions
6. **Document Changes**: Record what was changed and why

## Transformation Patterns

### Dependency Updates

```java
// Before: Spring Boot 2.7
import javax.validation.constraints.NotNull;
import javax.persistence.Entity;

// After: Spring Boot 3.2
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Entity;
```

### Framework Migration

```java
// Before: Quarkus Panache
@Entity
public class User extends PanacheEntity {
    public static List<User> findByEmail(String email) {
        return find("email", email).list();
    }
}

// After: Spring Data JPA
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Repository handles queries
}

// New UserRepository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByEmail(String email);
}
```

### Configuration Migration

```yaml
# Before: application.properties (Quarkus)
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost/db
quarkus.hibernate-orm.database.generation=update

# After: application.yml (Spring Boot)
spring:
  datasource:
    url: jdbc:postgresql://localhost/db
  jpa:
    hibernate:
      ddl-auto: update
```

## Incremental Transformation Strategy

**Component-by-Component**: Transform one component fully before moving to next

**Layer-by-Layer**: Transform all components in one layer (e.g., repositories) before next layer

**Feature-by-Feature**: Transform complete features end-to-end

Choose strategy based on:
- Dependency relationships
- Testing requirements
- Rollback complexity
- Team coordination needs

## Example: Spring Boot 2.7 → 3.2 Migration

```
Component: UserService

Transformations Applied:
1. Updated imports: javax.* → jakarta.*
2. Replaced deprecated methods:
   - WebSecurityConfigurerAdapter → SecurityFilterChain
   - authorizeRequests() → authorizeHttpRequests()
3. Updated Spring Data queries for Hibernate 6 compatibility
4. Migrated configuration properties to new format

Files Modified:
- src/main/java/com/example/service/UserService.java
- src/main/java/com/example/config/SecurityConfig.java
- src/main/java/com/example/repository/UserRepository.java
- src/main/resources/application.yml

Verification:
✅ Code compiles without errors
✅ All unit tests pass (127/127)
✅ Integration tests pass (45/45)
✅ No deprecated API warnings

Rollback Point: commit abc123f
```

## Validation Checklist

- [ ] All design specifications implemented
- [ ] Code compiles without errors
- [ ] No new compiler warnings introduced
- [ ] Existing tests updated for new framework
- [ ] New tests added for changed behavior
- [ ] Breaking changes documented
- [ ] Rollback points established
- [ ] Code review completed

## Guardrails

**Incremental changes**: Apply transformations in small, verifiable steps

**Test after each change**: Don't accumulate untested changes

**Preserve functionality**: Ensure behavior remains consistent unless intentionally changed

**Document deviations**: If implementation differs from design, document why

**Use design as guide**: Design docs inform approach but use your expertise for implementation details