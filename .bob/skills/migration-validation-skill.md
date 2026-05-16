---
name: migration-validation
description: Run complete validation suite after migration execution. Generates behavior/integration/performance tests, runs them, detects drift and gaps, and produces Go/No-Go report.
---

# Migration Validation Skill

## Purpose

This skill executes a comprehensive validation workflow after migration code changes are complete. It ensures the migrated system meets all quality gates before proceeding to the Optimization phase.

## Trigger Conditions

Activate this skill when:
- User explicitly says "validate the migration" or "run validation"
- Migration Orchestrator transitions from Migration Execution phase to Validation phase
- After Gate 4 criteria are confirmed met (all migration tasks complete, build passing, unit tests passing)
- User requests "check if migration is ready for optimization"

## Required Inputs

Before running this skill, ensure these inputs are available:

1. **migrated_code_path**: Path to the migrated source code repository
   - Example: `/path/to/project` or current workspace directory
   
2. **design_doc_path**: Path to Phase 3 design document
   - Default: `hackathon-ideas/migration-technical-specifications.md`
   - Contains: planned architecture, technology choices, API specifications
   
3. **migration_type**: Type of migration being validated
   - Options: `java_version`, `framework`, `cloud`, `database`, `language`
   - Example: `java_version` for Java 11→21 migration
   
4. **pre_migration_baseline**: Performance baseline from before migration (optional but recommended)
   - Format: JSON file with p50/p95/p99 latency, throughput, CPU, memory metrics
   - Location: `reports/assessment/performance-baseline.json`
   - If missing: current metrics will become the new baseline
   
5. **test_framework**: Testing framework used in the project
   - Auto-detect from: `pom.xml` (JUnit), `package.json` (Jest/Mocha), `pytest.ini` (pytest)
   - Options: `junit5`, `junit4`, `jest`, `mocha`, `pytest`, `go-test`

## Step-by-Step Instructions

### Step 1: Verify Entry Conditions

**Action:** Check that all Gate 4 prerequisites are met before proceeding.

**Checks to perform:**
```bash
# Check if code is committed
git status --porcelain
# Should return empty or only untracked files

# Check if build passes
# Java: mvn clean compile
# Node: npm run build
# Python: python -m py_compile src/**/*.py

# Check if basic tests pass
# Java: mvn test
# Node: npm test
# Python: pytest
```

**Expected outcome:**
- Git working directory is clean (no uncommitted changes to source code)
- Build completes successfully with no errors
- Existing unit tests pass (at least 80% pass rate)

**If checks fail:**
- STOP immediately
- Report which prerequisite failed
- Provide guidance: "Migration Execution phase is not complete. Please ensure all code is committed, build passes, and unit tests are passing before validation."
- Do NOT proceed to Step 2

### Step 2: Context Gathering

**Action:** Read and understand what was planned vs what was implemented.

**Read the design document:**
```
Use read_file on: hackathon-ideas/migration-technical-specifications.md
Extract:
- Technology choices (e.g., "use Java 21 Records", "migrate to jakarta.*")
- Architecture patterns (e.g., "implement virtual threads for I/O")
- API specifications (e.g., "UserService.findById() throws UserNotFoundException")
- Non-functional requirements (e.g., "p99 latency < 300ms", "handle 500 req/s")
```

**Inventory the migrated codebase:**
```
Use list_files recursively on: src/
Identify:
- Source code directories (src/main/java, src/, lib/)
- Test directories (src/test/java, tests/, __tests__/)
- Configuration files (application.yml, package.json, pom.xml)
- Build files (Dockerfile, .github/workflows/)
```

**Analyze code structure:**
```
Use list_code_definition_names on key directories
Extract:
- Class names and method signatures
- Public APIs that should match design spec
- New classes/methods added during migration
```

**Build mental model:**
- Create a checklist of design requirements
- For each requirement, note where to look in code to verify it
- Identify critical paths that need behavior tests

### Step 3: Generate Behavior Tests

**Action:** Generate unit tests that verify WHAT the system does (behavior), not HOW it does it (implementation).

**MCP Call (if available):**
```
Use Test Generator MCP → generate_tests
Parameters:
  test_types: ["unit"]
  focus: "behavior"
  framework: [detected from Step 1]
  source_path: "src/main" or "src/" or "lib/"
  output_path: "src/test" or "tests/" or "__tests__/"
  coverage_target: 100  # 100% of public API behaviors
```

**Fallback (if MCP unavailable):**
```
1. Identify all public APIs from list_code_definition_names
2. For each public method/function:
   a. Read the design spec to understand expected behavior
   b. Write test cases covering:
      - Happy path (normal input → expected output)
      - Edge cases (boundary values, empty inputs)
      - Error cases (invalid input → expected exception)
   c. Save test file with naming convention: *Test.java, *.test.js, *_test.py
3. Mark as "FALLBACK-GENERATED" in report
```

**Example output (Java 11→21):**
```java
// File: src/test/java/com/example/UserServiceBehaviorTest.java
@Test
void findById_returnsCompleteUser_whenUserExists() {
    // Given: user with ID 1 exists in database
    User user = new User(1L, "John Doe", "john@example.com");
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    
    // When: finding user by ID
    User result = userService.findById(1L);
    
    // Then: returns user with all fields populated
    assertThat(result.getId()).isEqualTo(1L);
    assertThat(result.getName()).isEqualTo("John Doe");
    assertThat(result.getEmail()).isEqualTo("john@example.com");
    assertThat(result.getCreatedAt()).isNotNull(); // Java 21 Record field
}

@Test
void findById_throwsUserNotFoundException_whenUserDoesNotExist() {
    // Given: user with ID 999 does not exist
    when(userRepository.findById(999L)).thenReturn(Optional.empty());
    
    // When/Then: throws UserNotFoundException
    assertThrows(UserNotFoundException.class, 
        () -> userService.findById(999L));
}
```

**Success criteria:**
- At least one behavior test per public API method
- Tests verify behavior from design spec, not implementation details
- Tests are independent (can run in any order)
- Test files saved to appropriate test directory

### Step 4: Generate Integration Tests

**Action:** Generate tests that verify multiple components work together correctly.

**MCP Call (if available):**
```
Use Test Generator MCP → generate_tests
Parameters:
  test_types: ["integration"]
  include_external_dependencies: true
  framework: [detected from Step 1]
  test_containers: true  # Use Testcontainers for databases
```

**Fallback (if MCP unavailable):**
```
1. Identify integration points:
   - Service → Repository → Database
   - Controller → Service → External API
   - Message Producer → Queue → Consumer
2. For each integration point, write tests that:
   - Start real dependencies (database, message queue) using Testcontainers
   - Exercise the full flow from entry point to exit point
   - Verify data persists correctly
   - Verify error handling across boundaries
3. Mark as "FALLBACK-GENERATED" in report
```

**Example output (Java 11→21):**
```java
// File: src/test/java/com/example/OrderUserIntegrationTest.java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class OrderUserIntegrationTest {
    
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
        .withDatabaseName("testdb")
        .withUsername("test")
        .withPassword("test");
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    void createOrder_fetchesUserFromDatabase_andPersistsOrder() {
        // Given: user exists in database
        User user = new User(1L, "John Doe", "john@example.com");
        // ... insert user into test database
        
        // When: creating order via REST API
        OrderRequest request = new OrderRequest(1L, "ITEM-001", 2);
        ResponseEntity<Order> response = restTemplate.postForEntity(
            "/api/orders", request, Order.class);
        
        // Then: order is created with user information
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getUserId()).isEqualTo(1L);
        assertThat(response.getBody().getStatus()).isEqualTo("PENDING");
        
        // And: order is persisted in database
        // ... verify order exists in database
    }
}
```

**Success criteria:**
- Integration tests cover critical user flows
- Tests use real dependencies (databases, queues) via Testcontainers
- Tests verify data persistence and cross-component communication
- At least 10 integration tests for typical application

### Step 5: Generate Performance Tests

**Action:** Create load test scripts that validate performance requirements from design spec.

**MCP Call (if available):**
```
Use Performance Profiler MCP → profile_application
Parameters:
  include_load_test: true
  target_rps: [from design spec, e.g., 500]
  duration_seconds: 90
  ramp_up_seconds: 30
  p99_threshold_ms: [from design spec, e.g., 300]
```

**Fallback (if MCP unavailable):**
```
1. Read design spec for performance requirements:
   - Target throughput (requests per second)
   - Latency thresholds (p50, p95, p99)
   - Concurrent users
2. Generate k6 load test script (JavaScript)
3. Save to: tests/performance/load-test.js
4. Mark as "FALLBACK-GENERATED" in report
```

**Example output (k6 script):**
```javascript
// File: tests/performance/load-test.js
import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  stages: [
    { duration: '30s', target: 100 },   // Ramp up to 100 users
    { duration: '1m',  target: 500 },   // Stay at 500 users (target load)
    { duration: '30s', target: 0 },     // Ramp down
  ],
  thresholds: {
    http_req_duration: ['p(99)<300'],    // p99 < 300ms (from design)
    http_req_failed:   ['rate<0.01'],    // Error rate < 1%
    http_reqs:         ['rate>500'],     // 500 req/s minimum
  },
};

export default function () {
  const res = http.get('http://localhost:8080/api/users/1');
  check(res, {
    'status is 200': (r) => r.status === 200,
    'response time < 300ms': (r) => r.timings.duration < 300,
  });
  sleep(0.1);
}
```

**Success criteria:**
- Load test script matches design spec requirements
- Thresholds defined for p50, p95, p99 latency
- Throughput target specified
- Error rate threshold set

### Step 6: Execute All Tests

**Action:** Run all generated tests and collect results.

**MCP Call (if available):**
```
Use Test Generator MCP → execute_tests
Parameters:
  test_suite: "all"
  parallel: false  # Run sequentially: unit → integration → performance
  collect_coverage: true
  output_format: "json"
  output_file: "reports/validation/test-results-raw.json"
```

**Fallback (if MCP unavailable):**
```bash
# Create reports directory
mkdir -p reports/validation

# Run unit tests
# Java:
mvn test -Dtest="*BehaviorTest" > reports/validation/unit-tests.log 2>&1
# Node:
npm test -- --testPathPattern=".*\\.test\\.js$" --json --outputFile=reports/validation/unit-tests.json
# Python:
pytest tests/ -v --json-report --json-report-file=reports/validation/unit-tests.json

# Run integration tests
# Java:
mvn verify -Dtest="*IntegrationTest" > reports/validation/integration-tests.log 2>&1
# Node:
npm test -- --testPathPattern=".*\\.integration\\.test\\.js$"

# Run performance tests (if k6 installed)
k6 run tests/performance/load-test.js --out json=reports/validation/performance-results.json

# Collect coverage
# Java:
mvn jacoco:report
# Node:
npm test -- --coverage --coverageReporters=json
```

**Parse results and extract:**
- Total tests run
- Tests passed
- Tests failed (with failure details)
- Tests skipped
- Execution duration
- Code coverage percentage
- Failed test names and error messages

**Save to:** `reports/validation/test-results-raw.json`

**Format:**
```json
{
  "unit_tests": {
    "total": 47,
    "passed": 47,
    "failed": 0,
    "skipped": 0,
    "duration_seconds": 42,
    "coverage_percentage": 85.3,
    "failures": []
  },
  "integration_tests": {
    "total": 12,
    "passed": 11,
    "failed": 1,
    "duration_seconds": 156,
    "failures": [
      {
        "test_name": "OrderUserIntegrationTest.createOrder_withTimeout",
        "error_message": "Timeout after 5s, expected 2s",
        "stack_trace": "..."
      }
    ]
  },
  "performance_tests": {
    "p50_latency_ms": 38,
    "p95_latency_ms": 128,
    "p99_latency_ms": 310,
    "throughput_rps": 520,
    "error_rate_pct": 0.15,
    "duration_seconds": 120
  }
}
```

**Success criteria:**
- All test suites executed
- Results captured in structured format
- Failures documented with details
- Coverage data collected

### Step 7: Security Scan

**Action:** Scan for vulnerabilities in dependencies and code.

**MCP Call (if available):**
```
Use Security Scanner MCP → scan_vulnerabilities
Parameters:
  scan_type: "comprehensive"
  severity_threshold: "medium"
  include_dependencies: true
  include_code: true
  include_config: true

Use Security Scanner MCP → check_compliance
Parameters:
  standards: ["OWASP"]
```

**Fallback (if MCP unavailable):**
```bash
# Java: OWASP Dependency Check
mvn dependency-check:check -DfailBuildOnCVSS=7
# Output: target/dependency-check-report.json

# Node: npm audit
npm audit --json > reports/validation/npm-audit.json

# Python: pip-audit
pip-audit --format json --output reports/validation/pip-audit.json

# Parse output and classify by severity
```

**Extract and save:**
- Critical vulnerabilities (CVSS ≥ 9.0)
- High vulnerabilities (CVSS 7.0-8.9)
- Medium vulnerabilities (CVSS 4.0-6.9)
- Low vulnerabilities (CVSS < 4.0)
- CVE IDs and descriptions
- Affected components and versions
- Remediation recommendations

**Save to:** `reports/validation/security-scan.json`

**Success criteria:**
- Security scan completed
- All vulnerabilities classified by severity
- Critical count = 0 (required for gate pass)
- High count ≤ 3 (acceptable with JIRA tickets)

### Step 8: Performance Baseline Comparison

**Action:** Compare current performance against pre-migration baseline.

**MCP Call (if available):**
```
Use Performance Profiler MCP → compare_metrics
Parameters:
  baseline_file: "reports/assessment/performance-baseline.json"
  current_file: "reports/validation/performance-results.json"
  metrics_to_compare: ["response_time", "throughput", "cpu_usage", "memory_usage"]
  threshold: 0.10  # ±10% acceptable
```

**Fallback (if MCP unavailable):**
```javascript
// Manual comparison logic
const baseline = JSON.parse(fs.readFileSync('reports/assessment/performance-baseline.json'));
const current = JSON.parse(fs.readFileSync('reports/validation/performance-results.json'));

const comparison = {
  p50_latency: {
    baseline: baseline.p50_latency_ms,
    current: current.p50_latency_ms,
    change_pct: ((current.p50_latency_ms - baseline.p50_latency_ms) / baseline.p50_latency_ms) * 100,
    status: Math.abs(change_pct) <= 10 ? "PASS" : "FAIL"
  },
  // ... repeat for p95, p99, throughput, cpu, memory
};
```

**If no baseline exists:**
- Document: "No pre-migration baseline found"
- Use current metrics as new baseline
- Save to: `reports/validation/performance-baseline.json`
- Mark all performance checks as "BASELINE_ESTABLISHED" instead of PASS/FAIL

**Save to:** `reports/validation/perf-comparison.json`

**Success criteria:**
- Comparison completed for all metrics
- Changes within ±10% threshold
- Regressions identified and documented

### Step 9: Data Integrity Check (if applicable)

**Action:** Verify data was migrated correctly (only for migrations involving data).

**Determine if applicable:**
- Check migration_type: if `database` or `data`, this step is required
- Otherwise, skip and document: "Data integrity check: SKIPPED — this migration does not involve data changes"

**MCP Call (if available):**
```
Use Data Quality Analyzer MCP → detect_issues
Parameters:
  issue_types: ["duplicates", "nulls", "referential_integrity", "format_violations"]
  data_source: [connection string from config]
```

**Fallback (if MCP unavailable):**
```sql
-- Compare row counts
SELECT 'users' as table_name, COUNT(*) as row_count FROM users
UNION ALL
SELECT 'orders', COUNT(*) FROM orders;
-- Compare with pre-migration counts

-- Check referential integrity
SELECT COUNT(*) as fk_violations 
FROM orders o 
LEFT JOIN users u ON o.user_id = u.id 
WHERE u.id IS NULL;

-- Check for nulls in required fields
SELECT COUNT(*) as null_violations 
FROM users 
WHERE email IS NULL OR name IS NULL;
```

**Save to:** `reports/validation/data-integrity.json`

**Success criteria:**
- Row counts match pre-migration (if applicable)
- No referential integrity violations
- No unexpected null values
- Data format consistent

### Step 10: Implementation Drift Detection

**Action:** Invoke the drift-gap-detection skill to compare design vs implementation.

**Invoke skill:**
```
Use skill: drift-gap-detection-skill.md
Inputs:
  - design_doc_path: hackathon-ideas/migration-technical-specifications.md
  - implementation_path: src/
  - migration_type: [from Step 1]
Outputs:
  - reports/validation/drift-report.md
```

**The drift-gap-detection skill will:**
1. Parse design document for all requirements
2. Search code for each requirement
3. Classify findings as: MATCH, DRIFT, or GAP
4. Classify drift severity: HIGH, MEDIUM, LOW
5. Generate structured drift report

**Success criteria:**
- Drift detection completed
- All drifts classified by severity
- HIGH drifts = 0 (required for gate pass)
- MEDIUM drifts ≤ 5 (acceptable with tracking)

### Step 11: Implementation Gap Detection

**Action:** This is part of the drift-gap-detection skill (Step 10).

**The skill will identify gaps:**
- Requirements in design that are NOT found in code
- Classify gap severity: BLOCKING or NON-BLOCKING
- Generate structured gap report

**Output:** `reports/validation/gap-report.md`

**Success criteria:**
- Gap detection completed
- All gaps classified by severity
- BLOCKING gaps = 0 (required for gate pass)
- NON-BLOCKING gaps ≤ 10 (acceptable with JIRA tickets)

### Step 12: Generate Validation Report

**Action:** Aggregate all results into a comprehensive validation report.

**Collect data from:**
- Step 6: Test results (unit, integration, performance)
- Step 7: Security scan results
- Step 8: Performance comparison
- Step 9: Data integrity (if applicable)
- Step 10: Drift report
- Step 11: Gap report

**Generate report:** `reports/validation/validation-report.md`

**Use the exact format from Section G of customInstructions:**
- Executive Summary (overall status, key metrics)
- Test Results (behavior, integration, performance)
- Security Scan Results
- Data Integrity Results (if applicable)
- Implementation Drift Report
- Implementation Gap Report
- Quality Gate Summary
- Go/No-Go Recommendation
- Generated Artifacts

**Success criteria:**
- Report generated with all 8 sections
- All metrics calculated correctly
- All thresholds evaluated
- Clear recommendation provided

### Step 13: Go/No-Go Recommendation

**Action:** Make explicit recommendation based on quality gate criteria.

**Evaluate criteria:**

| Criterion | Threshold | Status |
|-----------|-----------|--------|
| Unit test pass rate | 100% | Check |
| Integration test pass rate | ≥95% | Check |
| Code coverage | ≥80% | Check |
| Critical vulnerabilities | 0 | Check |
| Performance regression | ≤+10% | Check |
| Blocking gaps | 0 | Check |
| High severity drifts | 0 | Check |

**Decision logic:**

**✅ PROCEED** if ALL of:
- Unit test pass rate = 100%
- Integration test pass rate ≥ 95%
- Code coverage ≥ 80%
- Critical vulnerabilities = 0
- Performance within ±10% of baseline
- Blocking gaps = 0
- High severity drifts = 0

**⚠️ CONDITIONAL** if:
- Minor issues present (1-2 flaky integration tests, medium drifts, non-blocking gaps)
- All critical criteria met
- Issues can be tracked and addressed in Optimization phase

**❌ BLOCKED** if ANY of:
- Unit test pass rate < 100%
- Critical vulnerabilities > 0
- Performance regression > 10%
- Blocking gaps > 0
- High severity drifts > 0

**Document recommendation in report:**
- State decision clearly: PROCEED / CONDITIONAL / BLOCKED
- List conditions (if CONDITIONAL)
- List blockers (if BLOCKED)
- Specify approval required: QA Lead + Technical Lead

## MCP Fallback Instructions

If MCPs are not available in the hackathon environment, use these fallbacks:

### Test Generator MCP → Fallback
```bash
# Generate tests manually or use existing test templates
# Run tests with native tools:
mvn test          # Java
npm test          # Node.js
pytest            # Python
go test ./...     # Go
```

### Performance Profiler MCP → Fallback
```bash
# Use k6 for load testing
k6 run tests/performance/load-test.js --out json=results.json

# Or use Apache JMeter
jmeter -n -t load-test.jmx -l results.jtl
```

### Security Scanner MCP → Fallback
```bash
# Java: OWASP Dependency Check
mvn dependency-check:check

# Node: npm audit
npm audit --json

# Python: pip-audit
pip-audit --format json
```

### Data Quality Analyzer MCP → Fallback
```bash
# Run SQL queries directly
psql -d database -f data-integrity-checks.sql

# Or use Python script
python scripts/check_data_integrity.py
```

**Important:** When using fallbacks, always:
1. State clearly in output: "MCP [name] not available — using fallback"
2. Parse command output and format as JSON
3. Mark results as "FALLBACK" in the validation report

## Success Criteria

Validation is successful when:

1. **All 13 steps completed** without errors
2. **Validation report generated** at `reports/validation/validation-report.md`
3. **Go/No-Go recommendation made** with clear justification
4. **All artifacts saved** to `reports/validation/` directory
5. **Quality gates evaluated** against thresholds
6. **Handoff data prepared** for Optimization Mode (if PROCEED or CONDITIONAL)

## Output Files

This skill generates these files:

| File | Purpose |
|------|---------|
| `reports/validation/test-results-raw.json` | Raw test execution results |
| `reports/validation/security-scan.json` | Security vulnerability scan |
| `reports/validation/perf-comparison.json` | Performance baseline comparison |
| `reports/validation/data-integrity.json` | Data integrity check results |
| `reports/validation/drift-report.md` | Implementation drift analysis |
| `reports/validation/gap-report.md` | Implementation gap analysis |
| `reports/validation/validation-report.md` | **Main validation report** |
| `src/test/**/*BehaviorTest.*` | Generated behavior tests |
| `src/test/**/*IntegrationTest.*` | Generated integration tests |
| `tests/performance/load-test.js` | Generated performance test script |

## Integration with Other Phases

**Receives from Migration Execution (Phase 4):**
- Migrated source code
- Updated test files
- Build configuration
- Change logs

**Provides to Optimization (Phase 6):**
- Performance baseline metrics
- Optimization targets (bottlenecks, regressions)
- Deferred drift/gap items
- Validation report URL

**Handoff data format:**
```json
{
  "performance_baseline": {
    "p50_ms": 38,
    "p95_ms": 128,
    "p99_ms": 310,
    "throughput_rps": 520
  },
  "optimization_targets": [
    "N+1 query in OrderService.getOrderWithItems()",
    "p99 latency exceeds threshold by 0.7%"
  ],
  "drift_items_deferred": ["D1: UserDTO Records", "D3: JSON logging"],
  "gap_items_deferred": ["G1: @Deprecated annotation", "G2: README update"],
  "validation_report_url": "reports/validation/validation-report.md"
}
```

## Notes

- This skill is designed for hackathon use — prioritize working flow over perfect implementation
- If MCPs are unavailable, fallbacks ensure the skill still works
- All reports are markdown for easy reading and version control
- The skill is idempotent — can be run multiple times safely
- Failed validation can be re-run after fixes without starting from scratch