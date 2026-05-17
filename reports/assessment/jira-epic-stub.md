# JIRA Epic Stub — Migration Assessment

**Epic Title:** Migrate Library Management System from Java 11 to Java 17 (Spring Boot 2.7.18)  
**Project Key:** MIGR  
**Epic Type:** Migration  
**Labels:** migration, assessment, java11-to-java17, spring-boot-upgrade, version-upgrade  
**Priority:** Medium  
**Status:** Planning

---

## Epic Description

Migrate the Library Management System REST API from Java 11 + Spring Boot 2.4.3 to Java 17 + Spring Boot 2.7.18. This is a low-risk version upgrade migration with no application code changes required.

**Current State:**
- Java 11
- Spring Boot 2.4.3
- Hibernate 5.4.x
- Maven build system
- MySQL database

**Target State:**
- Java 17 (LTS)
- Spring Boot 2.7.18 (last 2.x version with Java 17 support)
- Hibernate 5.6.x
- Maven build system (unchanged)
- MySQL database (unchanged)

**Migration Type:** `version-upgrade`  
**Readiness Score:** 72/100 (✅ PASSED threshold of 60)  
**Effort Estimate:** 2-3 days (Small)  
**Risk Level:** Low

**Key Benefits:**
- Security patches from Spring Boot 2.7.18 (resolves CVEs in 2.4.3)
- Java 17 LTS support with extended maintenance window
- Performance improvements from Java 17 features
- Foundation for future Spring Boot 3.x migration (if needed)

**Key Risks:**
- Zero functional test coverage (only context load test exists)
- Hardcoded database credentials in source control

---

## Child Stories (Generated from 7Rs Strategy Matrix)

### Core Migration Stories (Rehost Strategy)

| Story ID | Story Title | 7R Strategy | Effort | Priority | Acceptance Criteria |
|---|---|---|---|---|---|
| MIGR-001 | Update pom.xml to Spring Boot 2.7.18 and Java 17 | Rehost | S | High | - `spring-boot-starter-parent` version updated to 2.7.18<br>- `<java.version>` property updated to 17<br>- `mvn clean install` succeeds<br>- No dependency conflicts reported |
| MIGR-002 | Verify 7 JPA Entity classes compile on Java 17 | Rehost | S | High | - All entity classes (Student, Book, Author, Card, Transaction, CardStatus, Genre) compile without errors<br>- No deprecation warnings related to JPA annotations<br>- Hibernate 5.6 compatibility confirmed |
| MIGR-003 | Verify 5 Repository interfaces compile on Java 17 | Rehost | S | High | - All repository interfaces compile without errors<br>- JpaRepository methods remain compatible<br>- No Spring Data JPA API changes detected |
| MIGR-004 | Verify 5 Service classes compile on Java 17 | Rehost | S | High | - All service classes compile without errors<br>- `@Service`, `@Autowired`, `@Transactional` annotations work correctly<br>- Business logic unchanged |
| MIGR-005 | Verify 4 REST Controller classes compile on Java 17 | Rehost | S | High | - All controller classes compile without errors<br>- `@RestController`, `@RequestMapping` annotations work correctly<br>- REST endpoints remain functional |
| MIGR-006 | Verify Main Application class starts on Java 17 | Rehost | S | High | - `StudentLibraryApplication` starts successfully<br>- Spring Boot context loads without errors<br>- `CommandLineRunner` executes (if enabled)<br>- Application listens on configured port |

### Security Remediation Story (Replatform Strategy)

| Story ID | Story Title | 7R Strategy | Effort | Priority | Acceptance Criteria |
|---|---|---|---|---|---|
| MIGR-007 | Externalize database credentials from application.properties | Replatform | M | High | - Database URL, username, password moved to environment variables<br>- `application.properties` uses `${DB_URL}`, `${DB_USERNAME}`, `${DB_PASSWORD}` syntax<br>- `.env.example` file created with placeholder values<br>- Deployment documentation updated<br>- Hardcoded password removed from git history (optional) |

### Test Coverage Story (Refactor Strategy)

| Story ID | Story Title | 7R Strategy | Effort | Priority | Acceptance Criteria |
|---|---|---|---|---|---|
| MIGR-008 | Add unit tests for 5 Service classes | Refactor | L | Medium | - Unit tests created for StudentService, BookService, AuthorService, CardService, TransactionService<br>- Minimum 80% code coverage for service layer<br>- Tests use mocked repositories<br>- All tests pass on Java 17 |
| MIGR-009 | Add integration tests for 5 Repository interfaces | Refactor | M | Medium | - Integration tests created for all repository interfaces<br>- Tests verify CRUD operations<br>- Tests use H2 in-memory database or Testcontainers MySQL<br>- All tests pass on Java 17 |
| MIGR-010 | Add integration tests for 4 REST Controllers | Refactor | L | Medium | - Integration tests created for all controller endpoints<br>- Tests verify request/response handling<br>- Tests use MockMvc or WebTestClient<br>- All tests pass on Java 17 |

### Validation & Documentation Stories

| Story ID | Story Title | 7R Strategy | Effort | Priority | Acceptance Criteria |
|---|---|---|---|---|---|
| MIGR-011 | Perform smoke testing on Java 17 environment | N/A | S | High | - Application starts successfully<br>- All REST endpoints respond correctly<br>- Database connectivity verified<br>- No runtime errors in logs<br>- Performance metrics within acceptable range (±10%) |
| MIGR-012 | Update project documentation for Java 17 | N/A | S | Medium | - README.md updated with Java 17 requirement<br>- Build instructions updated<br>- Deployment guide updated<br>- CHANGELOG.md entry added<br>- Migration notes documented |

---

## Epic Acceptance Criteria

### Functional Requirements
- ✅ Application builds successfully with Java 17 + Spring Boot 2.7.18
- ✅ All existing tests pass (currently only context load test)
- ✅ Application starts without errors
- ✅ All REST endpoints function correctly
- ✅ Database operations work as expected
- ✅ No regression in functionality

### Non-Functional Requirements
- ✅ Performance metrics within ±10% of baseline
- ✅ No new security vulnerabilities introduced
- ✅ Build time remains acceptable (<5 minutes)
- ✅ Application startup time remains acceptable (<30 seconds)

### Documentation Requirements
- ✅ Assessment report completed and approved
- ✅ Migration plan documented
- ✅ Deployment guide updated
- ✅ README.md reflects new Java version

### Quality Gates
- ✅ Readiness score ≥ 60 (achieved: 72/100)
- ✅ All critical security findings mitigated
- ✅ Zero blocker-level breaking changes
- ✅ Smoke tests pass in staging environment

---

## Dependencies & Blockers

### Prerequisites
- Java 17 JDK installed on development machines
- Maven 3.6+ (supports Java 17)
- CI/CD pipeline updated to use Java 17
- Staging environment provisioned with Java 17

### External Dependencies
- None (all dependencies are internal to the project)

### Known Blockers
- None identified

### Risks & Mitigations
| Risk | Impact | Probability | Mitigation |
|---|---|---|---|
| Zero test coverage prevents validation | High | High | Add tests before migration (MIGR-008, MIGR-009, MIGR-010) OR perform extensive manual testing |
| Hardcoded credentials in logs | Critical | Medium | Complete MIGR-007 before deployment |
| Performance regression | Medium | Low | Monitor metrics, tune JVM if needed |

---

## Story Sequencing & Phases

### Phase 1: Pre-Migration (Optional but Recommended)
**Duration:** 2.5 days  
**Stories:** MIGR-008, MIGR-009, MIGR-010  
**Goal:** Establish test coverage baseline

### Phase 2: Core Migration (Critical Path)
**Duration:** 0.5 days  
**Stories:** MIGR-001, MIGR-002, MIGR-003, MIGR-004, MIGR-005, MIGR-006  
**Goal:** Complete Java 17 + Spring Boot 2.7.18 upgrade

### Phase 3: Security Remediation (Recommended)
**Duration:** 0.25 days  
**Stories:** MIGR-007  
**Goal:** Externalize database credentials

### Phase 4: Validation & Documentation
**Duration:** 0.5 days  
**Stories:** MIGR-011, MIGR-012  
**Goal:** Verify migration success and update documentation

**Total Duration:** 3.75 days (with all optional work) OR 1.25 days (core migration only)

---

## Success Metrics

### Technical Metrics
- Build success rate: 100%
- Test pass rate: 100% (of existing tests)
- Application startup time: ≤30 seconds
- API response time: within ±10% of baseline
- Memory usage: within ±15% of baseline

### Business Metrics
- Zero production incidents related to migration
- Zero downtime during deployment
- All existing functionality preserved

### Quality Metrics
- Code coverage: ≥80% (if test stories completed)
- Security vulnerabilities: 0 critical, 0 high (after MIGR-007)
- Technical debt: Reduced (by upgrading Spring Boot)

---

## Attachments

- [Migration Assessment Report](./assessment-report.md) — Detailed analysis of readiness, risks, and recommendations
- [7Rs Strategy Matrix](./assessment-report.md#8-7rs-strategy-matrix) — Component-by-component migration strategy
- [Dependency Compatibility Analysis](./assessment-report.md#3-dependency-compatibility-analysis) — Full dependency portability assessment

---

## Notes

**Migration Strategy:** This is a **Rehost** migration for all application code — no code changes required. Only `pom.xml` needs modification. The 7Rs strategy assigns:
- **Rehost (6 components):** All Java source files migrate without changes
- **Replatform (1 component):** Configuration file security improvement
- **Refactor (1 component):** Test suite expansion (optional)

**Alternative Path Considered:** Direct Java 11 → Java 21 migration was evaluated and found infeasible due to Spring Boot 2.4.3 incompatibility. Java 21 requires Spring Boot 3.x with jakarta namespace migration, which is a separate, larger effort.

**Future Considerations:** After successful Java 17 migration, the team may consider:
1. Spring Boot 2.7.18 → 3.2.x (requires javax → jakarta namespace migration)
2. Java 17 → Java 21 (requires Spring Boot 3.2+)
3. MySQL connector upgrade to `mysql-connector-j` (new artifact ID in Spring Boot 3.x)

---

**Epic Created:** 2026-05-17  
**Assessment Mode:** Migration Assessment Mode  
**Next Phase:** Migration Planning Mode  
**Estimated Start Date:** TBD  
**Estimated Completion Date:** TBD