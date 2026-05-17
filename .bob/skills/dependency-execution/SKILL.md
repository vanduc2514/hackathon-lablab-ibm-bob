---
name: dependency-execution
description: Execute dependency updates, resolve conflicts, and update build configurations. Applies dependency mapping from design phase to actual project files.
---

# Dependency Execution

You are executing dependency updates based on design specifications. Your goal is to update project dependencies, resolve conflicts, and ensure build stability.

## Objective

Apply dependency substitutions from design phase to project manifests, resolve version conflicts, update build tools, and verify successful compilation.

## Required Inputs

- `design_dependency_mapping.md`: Dependency substitution matrix
- `design_target_architecture.md`: Target technology versions
- Project dependency manifests (pom.xml, package.json, build.gradle, etc.)
- Build tool configurations

## Output Artifacts

Create `execution_dependency_updates.md` documenting:

**Applied Updates**: List of dependency changes with versions

**Conflict Resolutions**: How version conflicts were resolved

**Build Configuration Changes**: Updates to build tools and plugins

**Verification Results**: Build success confirmation and test results

**Rollback Instructions**: How to revert dependency changes

## Execution Process

1. **Backup Current State**: Commit or tag current dependency state
2. **Update Manifests**: Apply dependency changes from design mapping
3. **Resolve Conflicts**: Address version conflicts and transitive dependencies
4. **Update Build Tools**: Upgrade build plugins and configurations
5. **Verify Build**: Ensure project compiles successfully
6. **Run Tests**: Execute test suite to catch compatibility issues

## Example: Spring Boot 2.7 → 3.2 Dependency Update

```xml
<!-- pom.xml BEFORE -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.7.18</version>
</parent>

<dependencies>
    <dependency>
        <groupId>javax.validation</groupId>
        <artifactId>validation-api</artifactId>
        <version>2.0.1.Final</version>
    </dependency>
</dependencies>

<!-- pom.xml AFTER -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.2.0</version>
</parent>

<dependencies>
    <dependency>
        <groupId>jakarta.validation</groupId>
        <artifactId>jakarta.validation-api</artifactId>
        <!-- Version managed by Spring Boot BOM -->
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.11.0</version>
            <configuration>
                <source>21</source>
                <target>21</target>
            </configuration>
        </plugin>
    </plugins>
</build>
```

## Conflict Resolution Strategies

### Version Convergence

```xml
<!-- Multiple dependencies require different versions -->
<!-- Solution: Use dependency management to enforce version -->
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.15.3</version>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### Exclusions

```xml
<!-- Exclude conflicting transitive dependency -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

### BOM Management

```xml
<!-- Use Bill of Materials for consistent versions -->
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>2023.0.0</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

## Build Tool Updates

### Maven

```xml
<!-- Update Maven wrapper -->
<properties>
    <maven.version>3.9.5</maven.version>
</properties>

<!-- Update plugins -->
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <version>3.2.0</version>
</plugin>
```

### Gradle

```groovy
// build.gradle
plugins {
    id 'org.springframework.boot' version '3.2.0'
    id 'io.spring.dependency-management' version '1.1.4'
    id 'java'
}

java {
    sourceCompatibility = '21'
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'jakarta.validation:jakarta.validation-api'
}
```

## Verification Steps

1. **Clean Build**: `mvn clean install` or `gradle clean build`
2. **Dependency Tree**: Review resolved dependencies for conflicts
3. **Test Execution**: Run full test suite
4. **Security Scan**: Check for known vulnerabilities
5. **License Compliance**: Verify license compatibility

## Example Execution Report

```
Dependency Update Execution: Spring Boot 2.7 → 3.2

Applied Updates:
✅ spring-boot-starter-parent: 2.7.18 → 3.2.0
✅ javax.validation → jakarta.validation
✅ hibernate-core: 5.6.15 → 6.4.0 (transitive)
✅ maven-compiler-plugin: 3.8.1 → 3.11.0

Conflict Resolutions:
- jackson-databind: Enforced 2.15.3 via dependencyManagement
- logback-classic: Excluded from spring-boot-starter-web, added explicitly

Build Configuration:
- Java source/target: 11 → 21
- Maven: 3.6.3 → 3.9.5
- Added spring-boot-maven-plugin configuration for native compilation

Verification Results:
✅ Build successful: 0 errors, 0 warnings
✅ Unit tests: 127/127 passed
✅ Integration tests: 45/45 passed
✅ Security scan: 0 critical, 0 high vulnerabilities
✅ License check: All dependencies compatible

Rollback Instructions:
git checkout HEAD~1 pom.xml
mvn clean install
```

## Validation Checklist

- [ ] All dependencies from design mapping applied
- [ ] Version conflicts resolved
- [ ] Build completes successfully
- [ ] No new compiler warnings
- [ ] All tests pass
- [ ] Security vulnerabilities addressed
- [ ] Build tool versions updated
- [ ] Dependency tree reviewed for unexpected changes

## Guardrails

**Exact versions**: Use exact versions from design mapping, not ranges

**Test after updates**: Run tests immediately after dependency changes

**Incremental updates**: Update related dependencies together, not all at once

**Document deviations**: If actual versions differ from design, document why

**Security first**: Address known vulnerabilities before proceeding