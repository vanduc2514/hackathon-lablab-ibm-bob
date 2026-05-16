---
name: drift-gap-detection
description: Compare migration design specification against actual implementation. Detects implementation drift (wrong approach) and implementation gaps (missing features). Produces structured drift-report.md and gap-report.md.
---

# Drift & Gap Detection Skill

## Purpose

This skill performs a detailed comparison between what was planned in the design phase and what was actually implemented in the migration phase. It identifies two types of issues:

1. **Implementation Drift**: When the code does something DIFFERENT from what was designed (wrong approach)
2. **Implementation Gap**: When the code does NOT do something that was designed (missing functionality)

## Key Concepts

### What is Drift vs Gap?

**DRIFT** = Did something DIFFERENT from what was designed
- The requirement was implemented, but using a different approach than specified
- Example: Design says "use Java 21 Records", code uses Lombok @Data
- Impact: May work but doesn't follow design intent, could have hidden issues

**GAP** = Did NOT do something that was designed
- The requirement was not implemented at all
- Example: Design says "implement Redis caching", code has no caching
- Impact: Missing functionality, incomplete migration

### Example Scenario

**Design document says:**
1. "Use Java 21 Records for all DTOs"
2. "Implement Redis caching for user lookups"
3. "Update Docker base image to eclipse-temurin:21"

**Actual implementation:**
1. Developer used Lombok @Data instead of Records → **DRIFT** (LOW severity)
2. No caching implemented at all → **GAP** (BLOCKING severity)
3. Docker image not updated → **GAP** (BLOCKING severity)

## Trigger Conditions

This skill is invoked by the migration-validation-skill.md at Step 9 and Step 10.

Activate when:
- Validation workflow reaches drift/gap detection phase
- User explicitly requests "check for implementation drift"
- User asks "compare design vs implementation"

## Required Inputs

1. **design_doc_path**: Path to Phase 3 design document
   - Default: `hackathon-ideas/migration-technical-specifications.md`
   - Contains: architecture decisions, technology choices, API specs, requirements

2. **implementation_path**: Path to migrated source code
   - Default: `src/` or current workspace directory
   - Contains: actual implementation files

3. **migration_type**: Type of migration
   - Options: `java_version`, `framework`, `cloud`, `database`, `language`
   - Used to apply type-specific detection rules

## Detection Procedure

### Step 1: Parse the Design Document

**Action:** Extract all requirements, decisions, and specifications from the design document.

**What to look for:**
- Keywords indicating requirements: "must", "shall", "required", "should", "use", "implement", "migrate"
- Technology choices: "use Java 21 Records", "migrate to jakarta.*", "implement virtual threads"
- Architecture patterns: "use microservices", "implement event sourcing", "use REST API"
- API specifications: "UserService.findById() throws UserNotFoundException"
- Non-functional requirements: "p99 latency < 300ms", "handle 500 req/s"
- Configuration requirements: "update Docker image", "configure connection pool"

**Build a requirements checklist:**
```
Use read_file on: hackathon-ideas/migration-technical-specifications.md

For each requirement found, create entry:
{
  requirement_id: "REQ-001",
  requirement_text: "Use Java 21 Records for UserDTO",
  location_in_design: "Section 1.2, line 45",
  category: "technology_choice",
  priority: "should" | "must" | "required",
  expected_evidence: "class UserDTO should be a record",
  search_patterns: ["record UserDTO", "public record UserDTO"]
}
```

**Example requirements extracted:**

| ID | Requirement | Category | Priority | Expected Evidence |
|----|-------------|----------|----------|-------------------|
| REQ-001 | Use Java 21 Records for UserDTO | Technology | SHOULD | `public record UserDTO(...)` |
| REQ-002 | Migrate javax.* to jakarta.* | Breaking Change | MUST | No `import javax.*` in code |
| REQ-003 | UserService.findById() throws UserNotFoundException | API Contract | MUST | Method signature matches |
| REQ-004 | Implement Redis caching | Feature | MUST | Redis configuration + @Cacheable |
| REQ-005 | Update Docker image to eclipse-temurin:21 | Infrastructure | MUST | `FROM eclipse-temurin:21` in Dockerfile |
| REQ-006 | Add @Deprecated to legacy endpoints | Documentation | SHOULD | @Deprecated annotations present |

### Step 2: Search the Code for Each Requirement

**Action:** For each requirement in the checklist, search the actual implementation to verify it was implemented as designed.

**Search strategy:**

**For technology choices (e.g., "use Java 21 Records"):**
```
1. Use search_files with pattern: "record UserDTO"
2. If found: read the file to verify it's actually a record
3. If not found: search for "class UserDTO" to see if it exists differently
4. Compare: Does it match the design? (record vs class)
```

**For breaking changes (e.g., "migrate javax.* to jakarta.*"):**
```
1. Use search_files with pattern: "import javax\."
2. Count occurrences
3. If count > 0: DRIFT or GAP (should be 0)
4. Use search_files with pattern: "import jakarta\."
5. Verify jakarta imports exist
```

**For API contracts (e.g., "throws UserNotFoundException"):**
```
1. Use list_code_definition_names to find UserService
2. Use read_file to read UserService.java
3. Search for method signature: "findById"
4. Verify: Does it throw UserNotFoundException?
5. Compare: Does signature match design?
```

**For features (e.g., "implement Redis caching"):**
```
1. Use search_files with pattern: "Redis|@Cacheable|CacheManager"
2. If found: read files to verify proper implementation
3. If not found: GAP (feature missing)
4. Check configuration files: application.yml, pom.xml for Redis dependencies
```

**For infrastructure (e.g., "update Docker image"):**
```
1. Use read_file on: Dockerfile
2. Search for: "FROM eclipse-temurin:21"
3. If not found: check what image is used
4. Compare: Does it match design?
```

**Record findings:**
```
For each requirement:
{
  requirement_id: "REQ-001",
  found: true | false,
  how_implemented: "Used Lombok @Data instead of Record",
  matches_design: true | false,
  evidence_location: "src/main/java/com/example/dto/UserDTO.java:5",
  issue_type: "drift" | "gap" | "match"
}
```

### Step 3: Classify Each Finding

**Action:** Determine if each finding is a MATCH, DRIFT, or GAP.

**Classification logic:**

```
IF found = true AND matches_design = true:
  → NO ISSUE (requirement met correctly)

IF found = true AND matches_design = false:
  → DRIFT (requirement met but using different approach)
  → Proceed to Step 4 to classify drift severity

IF found = false:
  → GAP (requirement not implemented)
  → Proceed to Step 5 to classify gap severity
```

**Example classifications:**

| Requirement | Found? | Matches? | Classification |
|-------------|--------|----------|----------------|
| REQ-001: Use Records | Yes | No (uses Lombok) | DRIFT |
| REQ-002: Migrate javax→jakarta | Partial | No (still has javax) | DRIFT |
| REQ-003: throws UserNotFoundException | Yes | Yes | MATCH ✅ |
| REQ-004: Redis caching | No | N/A | GAP |
| REQ-005: Docker image | Yes | No (still openjdk:11) | GAP |
| REQ-006: @Deprecated | No | N/A | GAP |

### Step 4: Classify Drift Severity

**Action:** For each DRIFT, determine severity: HIGH, MEDIUM, or LOW.

**Severity criteria:**

**HIGH Severity Drift:**
- Changes behavior or violates hard constraints
- Will break functionality or cause runtime errors
- Violates security requirements
- Breaks API contracts

**Examples:**
- Design: "use JWT RS256", Code: "uses HS256" → Security issue
- Design: "throws UserNotFoundException", Code: "returns null" → Contract violation
- Design: "migrate javax.* to jakarta.*", Code: "still uses javax.*" → Will break on Java 21

**MEDIUM Severity Drift:**
- Changes approach but achieves same outcome
- Works correctly but bypasses framework features
- Makes code harder to maintain
- Deviates from best practices

**Examples:**
- Design: "use @Cacheable", Code: "manual ConcurrentHashMap" → Works but bypasses Spring
- Design: "use CompletableFuture", Code: "uses Thread pool directly" → Works but less elegant
- Design: "use Spring Data JPA", Code: "uses JDBC directly" → Works but more code

**LOW Severity Drift:**
- Cosmetic or stylistic differences
- No functional impact
- Optional improvements not implemented
- Different but equivalent approach

**Examples:**
- Design: "use Java 21 Records", Code: "uses Lombok @Data" → Works fine, just not modern
- Design: "use structured JSON logging", Code: "uses plain text" → Works, just harder to parse
- Design: "use var for local variables", Code: "uses explicit types" → Style preference

**Classification algorithm:**
```
For each drift:
  IF affects_behavior OR violates_contract OR causes_errors:
    severity = HIGH
  ELSE IF bypasses_framework OR harder_to_maintain:
    severity = MEDIUM
  ELSE:
    severity = LOW
```

### Step 5: Classify Gap Severity

**Action:** For each GAP, determine severity: BLOCKING or NON-BLOCKING.

**Severity criteria:**

**BLOCKING Gap:**
- Required functionality is missing
- Will cause failures or errors
- Prevents system from working correctly
- Violates must-have requirements

**Examples:**
- Design: "Update Docker image to Java 21", Code: "still uses Java 11" → Deployment will fail
- Design: "Implement authentication", Code: "no auth" → Security requirement missing
- Design: "Migrate database schema", Code: "schema not updated" → App won't start
- Design: "Update CI/CD to Java 21", Code: "still builds with Java 11" → Build will fail

**NON-BLOCKING Gap:**
- Nice-to-have features missing
- Documentation or comments missing
- Optional improvements not done
- Can be deferred to later phases

**Examples:**
- Design: "Add @Deprecated to legacy endpoints", Code: "not added" → Works but misleading
- Design: "Update README with Java 21 instructions", Code: "README not updated" → Docs incomplete
- Design: "Add Javadoc comments", Code: "no Javadoc" → Code works, just undocumented
- Design: "Implement metrics dashboard", Code: "no dashboard" → Nice-to-have

**Classification algorithm:**
```
For each gap:
  IF requirement_priority = "MUST" OR causes_failure:
    severity = BLOCKING
  ELSE IF requirement_priority = "SHOULD" OR is_documentation:
    severity = NON-BLOCKING
```

### Step 6: Generate Drift Report

**Action:** Create a structured markdown report of all drifts found.

**Output file:** `reports/validation/drift-report.md`

**Report format:**

```markdown
# Implementation Drift Report

**Migration ID:** [from JIRA or generate]
**Migration Type:** [Java 11→21 / etc.]
**Analysis Date:** [date]
**Analyzed By:** Bob (Drift-Gap Detection Skill)

---

## Executive Summary

| Metric | Count |
|--------|-------|
| Total Drifts Found | 5 |
| HIGH Severity | 1 |
| MEDIUM Severity | 2 |
| LOW Severity | 2 |
| **Overall Status** | ⚠️ CONDITIONAL (1 HIGH drift must be addressed) |

---

## Drift Details

### HIGH Severity Drifts (1)

#### D-001: javax.* imports not migrated to jakarta.*

**Requirement:** REQ-002 - Migrate all javax.* imports to jakarta.*  
**Design Location:** Section 1.2, Breaking Changes  
**Priority:** MUST

**What was designed:**
```
All javax.xml.bind imports must be changed to jakarta.xml.bind
All javax.persistence imports must be changed to jakarta.persistence
```

**What was implemented:**
```java
// Found in: src/main/java/com/example/service/XmlService.java:3
import javax.xml.bind.JAXBContext;  // ❌ Still using javax

// Found in: src/main/java/com/example/model/User.java:5
import javax.persistence.Entity;  // ❌ Still using javax
```

**Impact:**
- Will cause runtime errors on Java 21+ (javax.* packages removed)
- Application will fail to start
- Breaks compatibility with Jakarta EE 9+

**Severity Justification:** HIGH - Will break at runtime

**Remediation:**
1. Replace all `javax.xml.bind` with `jakarta.xml.bind`
2. Replace all `javax.persistence` with `jakarta.persistence`
3. Update dependencies in pom.xml to Jakarta versions
4. Re-run tests to verify

**Estimated Effort:** 2 hours

**JIRA Ticket:** [Create ticket: "Migrate remaining javax.* imports to jakarta.*"]

---

### MEDIUM Severity Drifts (2)

#### D-002: Manual caching instead of Spring @Cacheable

**Requirement:** REQ-007 - Use Spring Cache abstraction with @Cacheable  
**Design Location:** Section 2.3, Caching Strategy  
**Priority:** SHOULD

**What was designed:**
```java
@Cacheable(value = "users", key = "#id")
public User findById(Long id) {
    return userRepository.findById(id).orElseThrow();
}
```

**What was implemented:**
```java
// Found in: src/main/java/com/example/service/UserService.java:25
private final ConcurrentHashMap<Long, User> cache = new ConcurrentHashMap<>();

public User findById(Long id) {
    return cache.computeIfAbsent(id, k -> 
        userRepository.findById(k).orElseThrow());
}
```

**Impact:**
- Works correctly but bypasses Spring Cache management
- Cannot use cache eviction strategies
- Harder to configure cache TTL
- Cannot switch cache providers easily

**Severity Justification:** MEDIUM - Works but bypasses framework features

**Remediation:**
1. Remove manual ConcurrentHashMap
2. Add @EnableCaching to configuration
3. Use @Cacheable annotation
4. Configure cache provider in application.yml

**Estimated Effort:** 1 hour

**JIRA Ticket:** [Create ticket: "Refactor to use Spring @Cacheable"]

---

#### D-003: Plain text logging instead of structured JSON

**Requirement:** REQ-009 - Use structured JSON logging for production  
**Design Location:** Section 3.1, Observability  
**Priority:** SHOULD

**What was designed:**
```java
log.info("User {} logged in from IP {}", userId, ipAddress);
// Should output: {"timestamp":"2024-01-01T10:00:00Z","level":"INFO","message":"User logged in","userId":123,"ipAddress":"192.168.1.1"}
```

**What was implemented:**
```java
// Found in: src/main/java/com/example/controller/AuthController.java:45
log.info("User " + userId + " logged in from IP " + ipAddress);
// Outputs: "User 123 logged in from IP 192.168.1.1"
```

**Impact:**
- Logs work but harder to parse in production
- Cannot easily query logs by userId or ipAddress
- Log aggregation tools (ELK, Splunk) less effective

**Severity Justification:** MEDIUM - Works but harder to maintain in production

**Remediation:**
1. Add logback-json-classic dependency
2. Configure JSON encoder in logback.xml
3. Use structured logging with MDC
4. Update log statements to use placeholders

**Estimated Effort:** 3 hours

**JIRA Ticket:** [Create ticket: "Implement structured JSON logging"]

---

### LOW Severity Drifts (2)

#### D-004: Lombok @Data instead of Java 21 Records

**Requirement:** REQ-001 - Use Java 21 Records for all DTOs  
**Design Location:** Section 1.1, Modern Java Features  
**Priority:** SHOULD

**What was designed:**
```java
public record UserDTO(Long id, String name, String email) {}
```

**What was implemented:**
```java
// Found in: src/main/java/com/example/dto/UserDTO.java:5
@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
}
```

**Impact:**
- Works perfectly fine
- Just not using modern Java 21 feature
- Slightly more verbose

**Severity Justification:** LOW - Cosmetic, no functional impact

**Remediation:**
- Optional: Refactor to Records in Optimization phase
- Not blocking for validation gate

**JIRA Ticket:** [Optional: "Refactor DTOs to Java 21 Records"]

---

#### D-005: Explicit types instead of var

**Requirement:** REQ-010 - Use var for local variables where type is obvious  
**Design Location:** Section 1.1, Code Style  
**Priority:** OPTIONAL

**What was designed:**
```java
var user = userRepository.findById(id);
var orders = orderService.getOrdersForUser(userId);
```

**What was implemented:**
```java
// Found throughout codebase
Optional<User> user = userRepository.findById(id);
List<Order> orders = orderService.getOrdersForUser(userId);
```

**Impact:**
- No functional impact
- Slightly more verbose
- Style preference

**Severity Justification:** LOW - Style preference only

**Remediation:**
- Optional: Can be addressed in code review
- Not blocking

**JIRA Ticket:** [Not needed]

---

## Drift Summary by Category

| Category | HIGH | MEDIUM | LOW | Total |
|----------|------|--------|-----|-------|
| Breaking Changes | 1 | 0 | 0 | 1 |
| Framework Usage | 0 | 1 | 0 | 1 |
| Observability | 0 | 1 | 0 | 1 |
| Modern Features | 0 | 0 | 1 | 1 |
| Code Style | 0 | 0 | 1 | 1 |
| **Total** | **1** | **2** | **2** | **5** |

---

## Recommendations

### Immediate Actions (Before Gate 5)
1. ❌ **MUST FIX:** D-001 - Migrate javax.* to jakarta.* (HIGH severity, will break)

### Track for Optimization Phase
2. ⚠️ **SHOULD FIX:** D-002 - Implement Spring @Cacheable (MEDIUM severity)
3. ⚠️ **SHOULD FIX:** D-003 - Structured JSON logging (MEDIUM severity)

### Optional Improvements
4. ✅ **OPTIONAL:** D-004 - Refactor to Records (LOW severity)
5. ✅ **OPTIONAL:** D-005 - Use var (LOW severity)

---

## Gate 5 Impact

**Can proceed to Optimization?**
- ❌ **NO** - 1 HIGH severity drift must be fixed first
- After fixing D-001: ⚠️ **CONDITIONAL** - Can proceed with MEDIUM drifts tracked

**Approval Required:** Technical Lead must review D-001 fix before proceeding
```

### Step 7: Generate Gap Report

**Action:** Create a structured markdown report of all gaps found.

**Output file:** `reports/validation/gap-report.md`

**Report format:**

```markdown
# Implementation Gap Report

**Migration ID:** [from JIRA or generate]
**Migration Type:** [Java 11→21 / etc.]
**Analysis Date:** [date]
**Analyzed By:** Bob (Drift-Gap Detection Skill)

---

## Executive Summary

| Metric | Count |
|--------|-------|
| Total Gaps Found | 4 |
| BLOCKING Gaps | 2 |
| NON-BLOCKING Gaps | 2 |
| **Overall Status** | ❌ BLOCKED (2 BLOCKING gaps must be addressed) |

---

## Gap Details

### BLOCKING Gaps (2)

#### G-001: Docker base image not updated to Java 21

**Requirement:** REQ-005 - Update Docker base image to eclipse-temurin:21  
**Design Location:** Section 4.1, Infrastructure  
**Priority:** MUST

**What was designed:**
```dockerfile
FROM eclipse-temurin:21-jdk-alpine
```

**What was found:**
```dockerfile
# Found in: Dockerfile:1
FROM openjdk:11-jdk-slim  # ❌ Still using Java 11
```

**Impact:**
- Container will run with Java 11 instead of Java 21
- Migration will not actually use Java 21 features
- Deployment will fail if code uses Java 21 APIs

**Severity Justification:** BLOCKING - Defeats purpose of migration

**Remediation:**
1. Update Dockerfile: `FROM eclipse-temurin:21-jdk-alpine`
2. Update CI/CD pipeline to use Java 21
3. Test container build
4. Verify application starts correctly

**Estimated Effort:** 30 minutes

**JIRA Ticket:** [Create ticket: "Update Docker image to Java 21"]

---

#### G-002: CI/CD pipeline not updated to Java 21

**Requirement:** REQ-006 - Update CI/CD pipeline to build with Java 21  
**Design Location:** Section 4.2, Build Pipeline  
**Priority:** MUST

**What was designed:**
```yaml
# .github/workflows/build.yml
- uses: actions/setup-java@v3
  with:
    java-version: '21'
    distribution: 'temurin'
```

**What was found:**
```yaml
# Found in: .github/workflows/build.yml:15
- uses: actions/setup-java@v3
  with:
    java-version: '11'  # ❌ Still using Java 11
    distribution: 'adopt'
```

**Impact:**
- CI/CD builds with Java 11, not Java 21
- Tests run against wrong Java version
- Deployment artifacts built with wrong version

**Severity Justification:** BLOCKING - Build pipeline must match target version

**Remediation:**
1. Update .github/workflows/build.yml to Java 21
2. Update Jenkinsfile (if exists) to Java 21
3. Update any other CI scripts
4. Run full CI/CD pipeline to verify

**Estimated Effort:** 1 hour

**JIRA Ticket:** [Create ticket: "Update CI/CD to Java 21"]

---

### NON-BLOCKING Gaps (2)

#### G-003: @Deprecated annotations not added to legacy endpoints

**Requirement:** REQ-008 - Add @Deprecated to all legacy /v1/* endpoints  
**Design Location:** Section 2.2, API Versioning  
**Priority:** SHOULD

**What was designed:**
```java
@Deprecated(since = "2.0", forRemoval = true)
@GetMapping("/v1/users/{id}")
public User getUserV1(@PathVariable Long id) {
    // Legacy endpoint
}
```

**What was found:**
```java
// Found in: src/main/java/com/example/controller/UserController.java:25
@GetMapping("/v1/users/{id}")  // ❌ No @Deprecated annotation
public User getUserV1(@PathVariable Long id) {
    return userService.findById(id);
}
```

**Impact:**
- Legacy endpoints still work
- But consumers not warned they're deprecated
- May cause confusion about which API version to use

**Severity Justification:** NON-BLOCKING - Works but misleading

**Remediation:**
1. Add @Deprecated annotation to all /v1/* endpoints
2. Update API documentation
3. Add deprecation notice to response headers

**Estimated Effort:** 1 hour

**JIRA Ticket:** [Create ticket: "Add @Deprecated to legacy endpoints"]

---

#### G-004: README not updated with Java 21 setup instructions

**Requirement:** REQ-011 - Update README with Java 21 installation and setup  
**Design Location:** Section 5.1, Documentation  
**Priority:** SHOULD

**What was designed:**
```markdown
## Prerequisites
- Java 21 (eclipse-temurin recommended)
- Maven 3.9+
- Docker 24+

## Setup
1. Install Java 21: `sdk install java 21-tem`
2. Verify: `java -version` (should show 21.x.x)
3. Build: `mvn clean install`
```

**What was found:**
```markdown
# Found in: README.md:10
## Prerequisites
- Java 11  # ❌ Still says Java 11
- Maven 3.6+
- Docker 20+
```

**Impact:**
- Documentation outdated
- New developers may install wrong Java version
- Setup instructions incorrect

**Severity Justification:** NON-BLOCKING - Documentation issue

**Remediation:**
1. Update README.md with Java 21 requirements
2. Update setup instructions
3. Add troubleshooting section for Java 21

**Estimated Effort:** 30 minutes

**JIRA Ticket:** [Create ticket: "Update README for Java 21"]

---

## Gap Summary by Category

| Category | BLOCKING | NON-BLOCKING | Total |
|----------|----------|--------------|-------|
| Infrastructure | 2 | 0 | 2 |
| API Documentation | 0 | 1 | 1 |
| Project Documentation | 0 | 1 | 1 |
| **Total** | **2** | **2** | **4** |

---

## Recommendations

### Immediate Actions (Before Gate 5)
1. ❌ **MUST FIX:** G-001 - Update Docker image to Java 21 (BLOCKING)
2. ❌ **MUST FIX:** G-002 - Update CI/CD to Java 21 (BLOCKING)

### Track for Later
3. ⚠️ **SHOULD FIX:** G-003 - Add @Deprecated annotations (NON-BLOCKING)
4. ⚠️ **SHOULD FIX:** G-004 - Update README (NON-BLOCKING)

---

## Gate 5 Impact

**Can proceed to Optimization?**
- ❌ **NO** - 2 BLOCKING gaps must be fixed first
- After fixing G-001 and G-002: ⚠️ **CONDITIONAL** - Can proceed with NON-BLOCKING gaps tracked

**Approval Required:** Technical Lead + DevOps Lead must verify infrastructure fixes
```

## Success Criteria

This skill is successful when:

1. **All requirements extracted** from design document
2. **All requirements searched** in implementation
3. **All findings classified** as MATCH, DRIFT, or GAP
4. **All drifts classified** by severity (HIGH, MEDIUM, LOW)
5. **All gaps classified** by severity (BLOCKING, NON-BLOCKING)
6. **Drift report generated** at `reports/validation/drift-report.md`
7. **Gap report generated** at `reports/validation/gap-report.md`
8. **Clear recommendations** provided for each issue
9. **JIRA tickets identified** for tracking

## Output Files

| File | Purpose |
|------|---------|
| `reports/validation/drift-report.md` | Detailed drift analysis with severity and remediation |
| `reports/validation/gap-report.md` | Detailed gap analysis with severity and remediation |

## Integration with Validation Skill

This skill is invoked by `migration-validation-skill.md` at:
- **Step 9:** Implementation Drift Detection
- **Step 10:** Implementation Gap Detection

The outputs (drift-report.md and gap-report.md) are then incorporated into the main validation report.

## Concrete Examples by Migration Type

### Java Version Migration (Java 11→21)

**Common Drifts:**
- Using Lombok instead of Records (LOW)
- Not using virtual threads (MEDIUM)
- Still using javax.* imports (HIGH)

**Common Gaps:**
- Docker image not updated (BLOCKING)
- CI/CD not updated (BLOCKING)
- Documentation not updated (NON-BLOCKING)

### Framework Migration (Quarkus→Spring Boot)

**Common Drifts:**
- Using @Inject instead of @Autowired (LOW)
- Manual configuration instead of auto-configuration (MEDIUM)
- Different annotation names (HIGH if breaks functionality)

**Common Gaps:**
- Spring Boot starters not added (BLOCKING)
- Application properties not migrated (BLOCKING)
- Tests not updated to Spring Test (BLOCKING)

### Cloud Migration (On-Prem→AWS)

**Common Drifts:**
- Using EC2 instead of ECS (MEDIUM)
- Manual scaling instead of auto-scaling (MEDIUM)
- Self-managed database instead of RDS (HIGH)

**Common Gaps:**
- IAM roles not configured (BLOCKING)
- CloudWatch monitoring not set up (NON-BLOCKING)
- Cost alerts not configured (NON-BLOCKING)

## Notes

- This skill is designed to be thorough but practical for hackathon use
- Focus on HIGH severity drifts and BLOCKING gaps first
- All other issues can be tracked and addressed in Optimization phase
- The skill is idempotent — can be run multiple times safely
- Reports are markdown for easy reading and version control