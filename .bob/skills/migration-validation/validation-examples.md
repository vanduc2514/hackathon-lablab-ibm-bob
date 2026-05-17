# Validation Examples — Classification Reference

Quick reference for classifying drift, gaps, and security findings. All thresholds come from `.bob/config.yaml`.

---

## Metric Formulas

| Metric | Formula | Example |
|--------|---------|---------|
| Unit test pass rate | `passed ÷ total × 100` | 47/47 = 100% ✅ |
| Integration test pass rate | `passed ÷ total × 100` | 11/12 = 91.7% ⚠️ |
| Code coverage | `lines_covered ÷ total_lines × 100` | 850/1000 = 85% ✅ |
| Performance variance | `(current − baseline) ÷ baseline × 100` | (310−280)÷280 = +10.7% ❌ |

---

## Drift Classification

### HIGH Severity
**When:** Breaks behavior, violates contract, causes runtime errors

| Scenario | Designed | Actual | Why HIGH |
|----------|----------|--------|----------|
| Exception handling | `throws UserNotFoundException` | `returns null` | Breaks API contract |
| Package migration | `import jakarta.*` | `import javax.*` | Runtime failure on Java 21+ |
| Security | `JWT with RS256` | `JWT with HS256` | Violates security requirement |

**Action:** Must fix before proceeding

### MEDIUM Severity
**When:** Different approach, same functional outcome

| Scenario | Designed | Actual | Why MEDIUM |
|----------|----------|--------|------------|
| Caching | `@Cacheable annotation` | `manual ConcurrentHashMap` | Works but bypasses framework |
| Async | `CompletableFuture` | `Thread pool directly` | Works but harder to maintain |

**Action:** Track and address in Optimization

### LOW Severity
**When:** Style difference, no functional impact

| Scenario | Designed | Actual | Why LOW |
|----------|----------|--------|---------|
| Modern features | `Java 21 Records` | `Lombok @Data` | Both work fine |
| Variable declaration | `var user = ...` | `User user = ...` | Style preference only |

**Action:** Optional, can defer

---

## Gap Classification

### BLOCKING Gap
**When:** Required functionality missing, causes failures

| Requirement | Status | Why BLOCKING |
|-------------|--------|--------------|
| Update Docker image to Java 21 | ❌ Missing | Container runs wrong version |
| Implement OAuth2 | ❌ Missing | Security requirement not met |
| Migrate database schema | ❌ Missing | App won't start |

**Action:** Must fix before proceeding

### NON-BLOCKING Gap
**When:** Nice-to-have missing, system works

| Requirement | Status | Why NON-BLOCKING |
|-------------|--------|------------------|
| Add @Deprecated annotations | ❌ Missing | Works but misleading |
| Update README | ❌ Missing | Docs outdated but app works |
| Add Prometheus metrics | ❌ Missing | Nice-to-have, not critical |

**Action:** Track and address later

---

## Security Classification

### CRITICAL Severity
**When:** Exploitable vulnerability, exposed secrets

| Finding | Why CRITICAL |
|---------|--------------|
| `password = "admin123"` in source | Exposed credential |
| SQL injection vulnerability | Exploitable |
| `/admin` with no auth | Unauthorized access |

**Action:** Must fix immediately

### HIGH Severity
**When:** Known CVE, missing authentication

| Finding | Why HIGH |
|---------|----------|
| `spring-web:6.1.0` with CVE-2024-XXXX | Known vulnerability |
| Public endpoint without auth | Security gap |
| MD5 hash for passwords | Cryptographically broken |

**Action:** Fix before proceeding or track in JIRA

### MEDIUM Severity
**When:** Weak config, outdated dependency (no CVE)

| Finding | Why MEDIUM |
|---------|------------|
| `lodash:4.17.15` (old but no CVE) | Should update |
| No `X-Frame-Options` header | Best practice violation |

**Action:** Track and address in Optimization

---

## Go/No-Go Decision Matrix

| Unit Tests | Integration | Coverage | Gaps | Drifts | Security | Decision |
|------------|-------------|----------|------|--------|----------|----------|
| 100% | ≥95% | ≥80% | 0 blocking | 0 high | 0 critical | ✅ **PROCEED** |
| 100% | ≥95% | ≥80% | 0 blocking | 0 high | 0 critical (has medium/low) | ⚠️ **CONDITIONAL** |
| <100% | <95% | <80% | >0 blocking | >0 high | >0 critical | ❌ **BLOCKED** |

**PROCEED:** All criteria met, move to Optimization immediately

**CONDITIONAL:** Non-blocking issues only, proceed with JIRA tracking and approval

**BLOCKED:** Critical issues present, must fix before proceeding

---

## Report Template Snippets

### Drift Table
```markdown
| # | Component | Designed | Actual | Severity | Action |
|---|-----------|----------|--------|----------|--------|
| D1 | UserDTO | Java 21 Record | Lombok @Data | LOW | Optional |
| D2 | Caching | @Cacheable | Manual Map | MEDIUM | Track JIRA |
| D3 | Exception | throws UserNotFoundException | returns null | HIGH | Must fix |

**Conclusion:** ❌ FAIL — 1 HIGH drift must be resolved
```

### Gap Table
```markdown
| # | Requirement | Status | Severity | JIRA |
|---|-------------|--------|----------|------|
| G1 | Update Docker to Java 21 | ❌ Missing | BLOCKING | MIGR-101 |
| G2 | Add @Deprecated | ❌ Missing | NON-BLOCKING | MIGR-102 |

**Conclusion:** ❌ FAIL — 1 BLOCKING gap must be resolved
```

### Quality Gate Summary
```markdown
| Criterion | Threshold | Actual | Status |
|-----------|-----------|--------|--------|
| Unit test pass rate | 100% | 100% | ✅ PASS |
| Integration test pass rate | ≥95% | 91.7% | ⚠️ WARN |
| Code coverage | ≥80% | 85% | ✅ PASS |
| Blocking gaps | 0 | 1 | ❌ FAIL |
| High drifts | 0 | 1 | ❌ FAIL |
| Critical security | 0 | 0 | ✅ PASS |
```

### Go/No-Go Decision
```markdown
**Decision: ❌ BLOCKED**

**Blockers:**
1. D3: Exception handling drift (HIGH) — Must throw UserNotFoundException
2. G1: Docker image gap (BLOCKING) — Update Dockerfile to Java 21
3. Performance variance (+10.7%) — Exceeds +10% threshold

**Action:** Fix blockers and re-run validation

**Approval Required:** QA Lead + Technical Lead
```

---

## Notes

- All thresholds from `.bob/config.yaml` — never hardcode
- When in doubt, classify as higher severity (safer)
- Document reasoning for each classification