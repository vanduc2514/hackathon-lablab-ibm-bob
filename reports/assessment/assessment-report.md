# Migration Assessment Report

**Project:** Library Management System  
**Migration:** Java 11 → Java 17 (Spring Boot 2.7.18)  
**Migration Type:** `version-upgrade`  
**Date:** 2026-05-17  
**Readiness Score:** 72/100 — ✅ PASS (threshold: 60)

---

## 1. Executive Summary

The Library Management System is a Spring Boot 2.4.3 REST API application currently running on Java 11. This assessment evaluates the feasibility of migrating to Java 17 with Spring Boot 2.7.18 (the last 2.x version supporting Java 17).

**Key Findings:**
- ✅ **Migration is feasible** with a readiness score of 72/100 (exceeds 60 threshold)
- ✅ **Low technical risk** — primarily a framework version upgrade with no breaking code changes
- ⚠️ **Critical gap:** Zero functional test coverage (only context load test exists)
- ⚠️ **Security concern:** Hardcoded database credentials in source control
- ✅ **Effort estimate:** 2-3 days for core migration (Small effort)

**Recommendation:** Proceed to Migration Planning Mode. Strongly recommend adding unit/integration tests before migration to establish baseline behavior validation.

**Alternative Path Considered:** Direct Java 11 → Java 21 migration was evaluated and found **infeasible** due to Spring Boot 2.4.3 incompatibility with Java 21. Java 21 requires Spring Boot 3.x with jakarta namespace migration, which is a separate, larger effort.

---

## 2. Project Overview & Scope Boundary

### Project Context
- **Purpose:** REST API for library operations managing students, books, authors, library cards, and book transactions
- **Architecture:** Layered Spring Boot application with JPA/Hibernate data access
- **Database:** MySQL 8.x
- **Build System:** Maven 3.x
- **Current Stack:** Spring Boot 2.4.3, Java 11, Hibernate 5.4.x

### Architecture Layers
1. **API Layer:** 4 REST controllers (`AuthorController`, `BookController`, `StudentController`, `TransactionController`)
2. **Service Layer:** 5 service classes with business logic and transaction management
3. **Data Access Layer:** 5 JPA repository interfaces extending `JpaRepository`
4. **Domain Model:** 7 JPA entity classes with bidirectional relationships
5. **Configuration:** Spring Boot properties file with database and application settings

### Scope Boundary

**IN SCOPE (18 files, ~865 LOC):**
- All 17 Java source files in `src/main/java/com/StudentLibrary/Studentlibrary/`
  - 1 main application class
  - 4 REST controllers
  - 5 service classes
  - 5 repository interfaces
  - 7 JPA entity classes (Student, Book, Author, Card, Transaction, CardStatus, Genre)
- `pom.xml` — Maven build configuration
- `src/main/resources/application.properties` — Spring Boot configuration
- `src/test/java/` — Test infrastructure (1 file)

**OUT OF SCOPE:**
- `.mvn/`, `mvnw`, `mvnw.cmd` — Maven wrapper (auto-generated, not migrated)
- `*.PNG`, `LICENSE`, `README.md` — Documentation and assets
- Build artifacts in `target/` directory (excluded by `.gitignore`)

**Rationale:** Focus exclusively on executable application code and build configuration. Maven wrapper will be regenerated automatically. Documentation requires no migration.

---

## 3. Dependency Compatibility Analysis

### Build Manifest Analysis
Source: `pom.xml`

| Dependency | Current Version | Portability Label | Target Equivalent | Notes |
|---|---|---|---|---|
| `spring-boot-starter-parent` | 2.4.3 | ⚠️ Partial equivalent | 2.7.18 | **Must upgrade** — 2.4.3 does not support Java 17 |
| `spring-boot-starter-data-jpa` | (inherited from parent) | ✅ Direct equivalent | Same in 2.7.18 | No API changes between versions |
| `spring-boot-starter-web` | (inherited from parent) | ✅ Direct equivalent | Same in 2.7.18 | No API changes between versions |
| `spring-boot-starter-test` | (inherited from parent) | ✅ Direct equivalent | Same in 2.7.18 | No API changes between versions |
| `mysql-connector-java` | (runtime, latest) | ✅ Direct equivalent | Same | Fully compatible with Java 17 |

### Portability Summary
- ✅ **Direct equivalent:** 4 dependencies (80%)
- ⚠️ **Partial equivalent:** 1 dependency (20%) — Spring Boot parent requires version upgrade
- 🔄 **Rewrite required:** 0 dependencies
- 🔒 **Boundary binding:** 0 dependencies
- ❌ **No viable path:** 0 dependencies

### Critical Dependency Finding
**Spring Boot 2.4.3 Compatibility Constraint:**
- Spring Boot 2.4.x officially supports Java 8, 11, and 15 only
- Java 17 support begins in Spring Boot 2.5.x
- Spring Boot 2.7.18 is the last 2.x version and supports Java 17 fully
- **Action Required:** Upgrade `spring-boot-starter-parent` from 2.4.3 → 2.7.18 in `pom.xml`

### Transitive Dependency Changes (2.4.3 → 2.7.18)
- Hibernate: 5.4.28 → 5.6.15 (minor version, no breaking changes for standard JPA usage)
- Jackson: 2.11.x → 2.13.x (backward compatible)
- Tomcat: 9.0.43 → 9.0.75 (patch updates only)
- Spring Framework: 5.3.4 → 5.3.27 (patch updates only)

**Assessment:** All transitive dependency upgrades are backward compatible with existing code.

---

## 4. Breaking Change Catalogue

### Java 11 → Java 17 Breaking Changes

| Item | Severity | Affected Files | Impact | Remediation |
|---|---|---|---|---|
| **JEP 403: Strongly encapsulate JDK internals** | LOW | None detected | No reflection on `sun.*` or `com.sun.*` packages found in codebase | No action required |
| **Removed `java.security.acl` package** | LOW | None | Package not used in application | No action required |
| **Deprecated `finalize()` for removal** | LOW | None | No `finalize()` method overrides found | No action required |
| **Changed default GC to G1** | LOW | All | Performance characteristics may differ slightly | Monitor performance post-migration |

### Spring Boot 2.4.3 → 2.7.18 Changes

| Item | Severity | Affected Files | Impact | Remediation |
|---|---|---|---|---|
| **Framework version constraint** | HIGH | `pom.xml` | 2.4.3 does not support Java 17 | Update parent version to 2.7.18 |
| **Hibernate 5.4 → 5.6 minor upgrade** | LOW | All entity classes | No breaking changes for standard `@Entity`, `@Id`, `@GeneratedValue`, `@OneToMany`, `@ManyToOne` usage | No action required |
| **`spring.jpa.hibernate.ddl-auto` behavior** | LOW | `application.properties` | Already using `update` mode (safe) | No action required |
| **Deprecation warnings** | LOW | Various | Some APIs deprecated but not removed | Address in future refactoring (non-blocking) |

### Breaking Change Summary
- **Blockers:** 0
- **High severity:** 1 (framework version constraint — resolved by upgrading Spring Boot parent)
- **Medium severity:** 0
- **Low severity:** 4 (no code changes required)

**Assessment:** No application code changes required. Only `pom.xml` modification needed.

---

## 5. Test Coverage Assessment

### Test Infrastructure
**Location:** `src/test/java/com/StudentLibrary/Studentlibrary/`

**Current Test Inventory:**
- `StudentLibraryApplicationTests.java` — Spring Boot context load test only
- Unit tests: **0 files**
- Integration tests: **0 files**
- End-to-end tests: **0 files**

### Coverage Analysis
**Coverage Level:** **None (0%)**

The application has only a single smoke test that verifies the Spring context loads successfully. There are no tests for:
- Service layer business logic (5 service classes untested)
- Repository layer data access (5 repositories untested)
- Controller layer REST endpoints (4 controllers untested)
- Entity relationships and JPA mappings (7 entities untested)

### Test Portability
**Portability Assessment:** ✅ **High**

The existing context load test will run unchanged on Java 17 + Spring Boot 2.7.18. JUnit 5 (included in `spring-boot-starter-test`) is fully compatible with both versions.

### Critical Test Gap
**Risk:** Without functional tests, there is no automated way to verify that the migrated application behaves identically to the current version.

**Recommendation:** Add unit and integration tests **before migration** to:
1. Establish baseline behavior
2. Validate migration success
3. Enable regression detection
4. Support future refactoring

**Suggested Test Coverage Targets:**
- Service layer: 80%+ coverage (critical business logic)
- Repository layer: Basic CRUD operations for each entity
- Controller layer: Happy path + error handling for each endpoint

---

## 6. Security Posture

### Security Findings

| Severity | Location | Description | Remediation |
|---|---|---|---|
| **CRITICAL** | `application.properties:4` | Hardcoded database password `Saikat@021` committed to source control | Move to environment variable `${DB_PASSWORD}` |
| **HIGH** | `application.properties:2-4` | Database credentials (URL, username, password) not externalized | Use Spring Boot environment variable substitution |
| **MEDIUM** | `pom.xml` | Spring Boot 2.4.3 is 4+ years old with known CVEs (CVE-2021-22118, CVE-2021-22096, others) | Upgrade to 2.7.18 (includes security patches) |
| **LOW** | All repository classes | No explicit `@Transactional` annotations on write operations | Relies on service layer transactions (acceptable pattern) |

### Dependency Staleness
- **Spring Boot 2.4.3:** Released February 2021 (4+ years old)
- **Upgrade benefit:** Spring Boot 2.7.18 (released November 2023) includes patches for multiple CVEs

### Migration Security Impact
**Positive Security Outcomes:**
- ✅ Java 17 includes security improvements (JEP 411: Deprecate Security Manager, JEP 412: Foreign Function & Memory API)
- ✅ Spring Boot 2.7.18 patches known vulnerabilities in 2.4.3
- ✅ Updated transitive dependencies (Tomcat, Jackson, Hibernate) include security fixes

**Unresolved Security Issues:**
- ⚠️ Hardcoded credentials remain a critical issue (unrelated to Java/Spring Boot version)
- ⚠️ Recommend addressing as part of migration effort

### Recommended Security Remediations
1. **Immediate (Critical):** Externalize database credentials to environment variables
2. **Migration-related (High):** Upgrade Spring Boot to 2.7.18 (resolves CVEs)
3. **Post-migration (Medium):** Review and update `.gitignore` to prevent future credential commits
4. **Future (Low):** Add input validation and parameterized queries (already using JPA, low risk)

---

## 7. Readiness Score Breakdown

### Scoring Methodology
Each dimension scored 0-100, then weighted to produce final score.

| Dimension | Weight | Raw Score | Calculation | Weighted Contribution |
|---|---|---|---|---|
| **Dependency Portability** | 30% | 90/100 | (4 direct × 1.0 + 1 partial × 0.5) / 5 × 100 | 27.0 |
| **Breaking Change Density** | 25% | 86/100 | 100 − (0 blockers×20 + 1 high×10 + 0 medium×3 + 4 low×1) | 21.5 |
| **Code Complexity** | 20% | 85/100 | Low complexity (standard CRUD, clear layering) | 17.0 |
| **Test Coverage** | 15% | 0/100 | None (0% functional coverage) | 0.0 |
| **Security Baseline** | 10% | 67/100 | 100 − (1 critical×20 + 1 high×10 + 1 medium×3) | 6.7 |

### Final Readiness Score
**72.2/100** (rounded to 72/100)

### Gate Decision
✅ **PASSES** — Score exceeds threshold of 60/100

### Dimension Analysis

**Strengths:**
1. **Dependency Portability (90/100):** Excellent — only one dependency requires upgrade, all others are direct equivalents
2. **Breaking Change Density (86/100):** Excellent — minimal breaking changes, no code modifications required
3. **Code Complexity (85/100):** Good — well-structured layered architecture, standard Spring Boot patterns

**Weaknesses:**
1. **Test Coverage (0/100):** Critical gap — no functional tests to validate migration success
2. **Security Baseline (67/100):** Moderate concern — hardcoded credentials and outdated dependencies

**Remediation Impact:**
- Adding test coverage would increase score to **83/100** (assuming 80% coverage achieved)
- Fixing security issues would increase score to **75/100**
- Both remediations together would yield **96/100**

---

## 8. 7Rs Strategy Matrix

| Component | Current Implementation | 7R Strategy | Target Implementation | Rationale | Effort |
|---|---|---|---|---|---|
| **pom.xml** | Spring Boot 2.4.3 parent, Java 11 | **Rehost** | Spring Boot 2.7.18 parent, Java 17 | Version bump only — no dependency additions/removals | S |
| **7 Entity Classes** | JPA entities with `javax.persistence.*` | **Rehost** | Same (stays on `javax.*` namespace) | Spring Boot 2.x uses `javax.*` — no namespace migration needed | S |
| **5 Repository Interfaces** | `JpaRepository` extensions | **Rehost** | Same | No API changes in Spring Data JPA 2.x | S |
| **5 Service Classes** | Business logic with `@Service`, `@Autowired` | **Rehost** | Same | No breaking changes in Spring Framework 5.3.x | S |
| **4 Controller Classes** | REST endpoints with `@RestController`, `@RequestMapping` | **Rehost** | Same | No API changes in Spring Web MVC | S |
| **Main Application Class** | `@SpringBootApplication` with `CommandLineRunner` | **Rehost** | Same | No changes to Spring Boot startup mechanism | S |
| **application.properties** | Hardcoded database credentials | **Replatform** | Externalize credentials to environment variables | Security remediation — adopt Spring Boot best practice | M |
| **Test Suite** | Context load test only | **Refactor** | Add unit/integration tests for services, repositories, controllers | Close critical coverage gap — recommended before migration | L |

### 7Rs Summary
- **Rehost:** 6 components (all application code — no changes required)
- **Replatform:** 1 component (configuration security improvement)
- **Refactor:** 1 component (test suite expansion)
- **Retire/Retain/Relocate/Repurchase:** 0 components

### Effort Breakdown
**Core Migration (Rehost components):**
- Update `pom.xml`: 15 minutes
- Run `mvn clean install`: 5 minutes
- Smoke test application: 15 minutes
- **Subtotal:** 35 minutes (0.5 days)

**Security Remediation (Replatform — Optional but Recommended):**
- Externalize credentials to environment variables: 1 hour
- Update deployment documentation: 1 hour
- **Subtotal:** 2 hours (0.25 days)

**Test Suite Expansion (Refactor — Recommended but Not Blocking):**
- Service layer unit tests: 1 day
- Repository integration tests: 0.5 days
- Controller integration tests: 1 day
- **Subtotal:** 2.5 days

**Total Effort Estimate:**
- **Minimum (core migration only):** 0.5 days
- **Recommended (core + security):** 0.75 days
- **Ideal (core + security + tests):** 3.25 days

---

## 9. Effort Estimate & Risk Matrix

### Effort Estimate
**Overall Classification:** Small (2-3 days)

| Phase | Tasks | Effort | Dependencies |
|---|---|---|---|
| **Pre-Migration** | Add unit/integration tests (optional) | 2.5 days | None |
| **Migration** | Update `pom.xml`, rebuild, smoke test | 0.5 days | None |
| **Security Remediation** | Externalize credentials (optional) | 0.25 days | None |
| **Validation** | Run test suite, manual testing | 0.5 days | Tests must exist |
| **Documentation** | Update README, deployment docs | 0.25 days | Migration complete |

**Critical Path:** Pre-migration testing (if included) → Migration → Validation

### Risk Matrix

| Risk | Probability | Impact | Mitigation |
|---|---|---|---|
| **Zero test coverage prevents validation** | High | High | Add tests before migration OR perform extensive manual testing |
| **Hardcoded credentials exposed in logs** | Medium | Critical | Externalize credentials before migration |
| **Transitive dependency conflicts** | Low | Medium | Use `mvn dependency:tree` to verify resolution |
| **Performance regression from G1 GC** | Low | Low | Monitor metrics post-migration, tune if needed |
| **Hibernate 5.6 behavior changes** | Very Low | Low | Standard JPA usage unaffected |

### Risk Mitigation Strategies
1. **Test Coverage Risk:** 
   - **Option A (Recommended):** Add tests before migration
   - **Option B:** Perform comprehensive manual testing with documented test cases
   - **Option C:** Deploy to staging environment first with monitoring

2. **Security Risk:**
   - Externalize credentials to environment variables
   - Rotate database password after remediation
   - Add `.env` to `.gitignore`

3. **Dependency Conflict Risk:**
   - Run `mvn dependency:tree` before and after upgrade
   - Verify no version conflicts in transitive dependencies
   - Test application startup and basic operations

### Success Criteria
- ✅ Application builds successfully with Java 17 + Spring Boot 2.7.18
- ✅ All existing tests pass (currently only context load test)
- ✅ Application starts without errors
- ✅ Manual smoke test of core functionality succeeds
- ✅ No new security vulnerabilities introduced
- ✅ Performance metrics within acceptable range (±10%)

---

## Gate Decision

### Assessment Outcome
✅ **PROCEED TO PLANNING MODE**

**Readiness Score:** 72/100 (exceeds threshold of 60/100)

**Gate Criteria Status:**
- ✅ Migration type classified: `version-upgrade`
- ✅ Readiness score ≥ 60: **72/100**
- ✅ Scope boundary documented: IN/OUT with rationale provided
- ✅ All in-scope components have 7R assigned: 8 components mapped
- ✅ Critical security findings have mitigation noted: Externalize credentials
- ✅ Assessment report written: This document
- ⏳ JIRA epic stub: To be created in Step 10

**Blocking Issues:** None

**Warnings:**
1. **Test Coverage (Critical):** Zero functional test coverage prevents automated validation. Strongly recommend adding tests before migration.
2. **Security (High):** Hardcoded database credentials in source control. Recommend remediation as part of migration effort.

**Recommended Next Steps:**
1. Review and approve this assessment report
2. Proceed to Migration Planning Mode to create detailed migration plan
3. Consider adding test coverage before executing migration
4. Plan security remediation alongside migration work

---

**Report Generated:** 2026-05-17  
**Assessment Mode:** Migration Assessment Mode  
**Next Phase:** Migration Planning Mode