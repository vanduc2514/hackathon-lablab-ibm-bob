---
name: migration-validation
description: Validate migrated code against design spec. Detect drift and gaps, generate tests, produce Go/No-Go report.
---

# Migration Validation Skill

## Purpose

Ensure migrated code matches its approved design specification before proceeding to Optimization phase.

## Core Objective

Compare design intent vs actual implementation to detect:
- **Drift**: Wrong approach (designed X, implemented Y)
- **Gaps**: Missing features (designed X, not implemented)
- **Quality issues**: Test coverage, security, performance

## Input Contract

- **Primary**: `design_xxx.md` from Phase 3 (source of truth)
- **Context**: Migrated source code in repository
- **Config**: `.bob/config.yaml` for thresholds

## Workflow

Execute these steps sequentially:

### 1. Parse Design Spec
Extract requirements from `design_xxx.md`:
- Technology choices (Java 21, Spring Boot 3.2)
- API contracts (method signatures, exceptions)
- Behaviors (what system should do)
- Infrastructure (Docker, CI/CD)
- Security requirements

Create internal checklist: requirement ID, text, category, priority, expected evidence.

### 2. Inventory Migrated Code
Scan repository to map what exists:
- File structure (`list_files`)
- API signatures (`list_code_definition_names`)
- Configurations (pom.xml, Dockerfile)
- Existing tests

### 3. Generate Behavior Tests
For each public API in design, write tests verifying WHAT system does:
- Happy path: normal input → expected output
- Error cases: invalid input → expected exception

Write to `src/test/` using project's test framework (JUnit, Jest, pytest).

**Example:**
```java
@Test
void findById_returnsUser_whenExists() {
    User user = service.findById(1L);
    assertNotNull(user);
}

@Test
void findById_throwsException_whenNotFound() {
    assertThrows(UserNotFoundException.class, 
        () -> service.findById(999L));
}
```

### 4. Generate Integration Tests
For each component boundary, write tests verifying end-to-end flow:
- Controller → Service → Repository → Database
- Service → External API
- Message Producer → Queue → Consumer

Use test containers or mocks for external dependencies.

### 5. Document Performance Test Plan
Extract NFRs from design (latency, throughput, resource usage).
Write plan to `reports/validation/performance-test-plan.md`:
- Metrics to measure (p50, p95, p99 latency)
- Target values from config (`performance_variance_threshold_pct`)
- Test scenarios (normal load, peak load)
- Mark execution as "deferred" (run in Optimization phase)

### 6. Detect Implementation Drift
For each requirement, compare design vs code:
- **HIGH drift**: Breaks behavior or violates contract (must fix)
- **MEDIUM drift**: Different approach, same outcome (track)
- **LOW drift**: Style difference only (optional)

Use `validation-examples.md` for classification criteria.

### 7. Detect Implementation Gaps
For each requirement, verify it exists in code:
- **BLOCKING gap**: Required feature missing (must fix)
- **NON-BLOCKING gap**: Nice-to-have missing (track)

Search code using `search_files` and `read_file`.

### 8. Evaluate Security Posture
Search for vulnerability patterns using `search_files`:
- Hardcoded secrets: `password\s*=\s*["']`
- Missing auth: `@RestController` without `@PreAuthorize`
- Stale dependencies: check pom.xml, package.json

Classify findings:
- **CRITICAL**: Exploitable vulnerability, exposed secrets
- **HIGH**: Known CVE, missing authentication
- **MEDIUM**: Weak config, outdated dependency
- **LOW**: Best practice violation

### 9. Apply Metrics & Thresholds
Read thresholds from `.bob/config.yaml`. Evaluate:

| Metric | Threshold | Status Logic |
|--------|-----------|--------------|
| Unit test pass rate | 100% | ✅ if = 100%, ❌ if < 100% |
| Integration test pass rate | ≥95% | ✅ if ≥95%, ⚠️ if close, ❌ if far |
| Code coverage | ≥80% | ✅ if ≥80%, ❌ if <80% |
| Blocking gaps | 0 | ✅ if = 0, ❌ if > 0 |
| High drifts | 0 | ✅ if = 0, ❌ if > 0 |
| Critical security | 0 | ✅ if = 0, ❌ if > 0 |

### 10. Write Validation Report
Write to path from config (`migration.output.validation_report`).

**Required sections:**
1. Executive Summary (overall status, key numbers)
2. Test Results (unit, integration, performance plan)
3. Security Findings (severity, count, details)
4. Drift Report (table with severity)
5. Gap Report (table with severity)
6. Quality Gate Summary (all metrics vs thresholds)
7. Go/No-Go Decision (PROCEED/CONDITIONAL/BLOCKED)

**Decision logic:**
- **PROCEED**: All thresholds met, 0 blockers
- **CONDITIONAL**: Non-blocking issues only, tracked in JIRA
- **BLOCKED**: Any threshold failed OR blocker present

## MCP Fallback Strategy

All MCPs `present: false` in hackathon environment:
- **test_generator**: Use native reasoning to write tests
- **security_scanner**: Use `search_files` with regex patterns
- **performance_profiler**: Document plan, skip execution
- **data_quality_analyzer**: Use native reasoning

## Success Criteria

- [ ] All 10 steps executed
- [ ] Test files written to `src/test/`
- [ ] Drift/gap tables complete
- [ ] Security findings documented
- [ ] Validation report written with explicit Go/No-Go
- [ ] Issues tracked if CONDITIONAL or BLOCKED

## Integration

**Receives from Phase 4 (Execution):**
- Migrated source code
- Build configuration
- Existing tests

**Provides to Phase 6 (Optimization):**
- Validation report
- Performance baseline (documented)
- Optimization targets (bottlenecks, medium drifts)
- Deferred items (non-blocking gaps)