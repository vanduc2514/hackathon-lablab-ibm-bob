# Migration Assessment Skill
Language-agnostic: 
- works for any source → target migration type. 
- works for any lower versioned programming language → higher versioned programming language

## Purpose
Perform a complete pre-migration assessment of a source codebase and produce
a structured Assessment Report with a readiness score and 7Rs recommendation
per component. Works with or without MCP servers (graceful fallback to native
Bob file-reading when MCPs are absent).

Supported migration types (non-exhaustive):
- Language version upgrades:  Java 11 → Java 21, Python 3.8 → 3.12, Node 16 → 20
- Cross-language:             Python → Rust, Java → Scala, Python → C++, COBOL → Java
- Notebook conversion:        .ipynb → .py
- Framework swap:             Spring MVC → Quarkus, Flask → FastAPI, JDBC → JPA/Hibernate
- Database:                   PostgreSQL → MongoDB, Oracle → PostgreSQL, MySQL → DynamoDB
- Platform:                   Monolith → Microservices, on-prem → cloud, VM → container
- Protocol:                   REST → gRPC, SOAP → REST
- Any combination of the above

## Configuration
Before starting, read `.bob/config.yaml` and extract:
- `migration.readiness_threshold`        → gate score 
- `migration.output.assessment_report`   → output file path
- `migration.jira.stub`                  → true = markdown stub, false = JIRA MCP call
- `migration.mcp.*`                      → which MCPs are live and which fallback to use

## Entry criteria (Gate 0 → Assessment)
- Source codebase is accessible
- Migration goal is stated as: `<source> → <target>`
  e.g. "Java 11 → Java 21", "Python → Rust", "PostgreSQL → MongoDB", ".ipynb → .py"
- Bob operates in **Ask Mode** (read-only) for steps 1–8
- Bob switches to **Code Mode** (write) only for steps 9–10

---

## Migration Type Classification

Classify the migration into one or more of these categories at the start.
All subsequent steps adapt their behaviour based on this classification.

| Category | Examples | Primary concern |
|---|---|---|
| `version-upgrade` | Java 11→21, Python 3.8→3.12 | Removed APIs, syntax changes, deprecations |
| `cross-language` | Python→Rust, Java→Scala, Python→C++ | Type system, runtime model, ecosystem gap |
| `notebook-conversion` | .ipynb→.py | Cell ordering, state, magic commands |
| `framework-swap` | JDBC→JPA, Flask→FastAPI, Spring→Quarkus | Config model, lifecycle, annotation changes |
| `database-migration` | PostgreSQL→MongoDB, Oracle→PG | Schema paradigm, query language, constraints |
| `platform-migration` | Monolith→Microservices, VM→container | Boundaries, data ownership, deployment |
| `protocol-migration` | REST→gRPC, SOAP→REST | Contract, serialisation, client impact |

Multiple categories may apply simultaneously.

---

## 10-Step Workflow

### Step 1 — Gather project context
**Mode:** Ask (read-only)
**Input:** Repo root path, migration goal string
**Actions:**
1. Read `README.md` if present
2. Auto-detect source language and version from:
   - File extensions: `.java`, `.py`, `.rs`, `.scala`, `.cpp`, `.go`, `.ts`, `.ipynb`, `.cs`, `.rb`, `.kt`
   - Version files: `.python-version`, `.nvmrc`, `.java-version`, `rust-toolchain.toml`
   - Build manifests: `pyproject.toml`, `pom.xml`, `build.gradle`, `Cargo.toml`,
     `package.json`, `go.mod`, `CMakeLists.txt`, `*.csproj`, `mix.exs`, `Gemfile`,
     `build.sbt`, `*.cabal`
3. Identify build/package system from the manifest found above
4. Classify migration type from the table above
5. Note runtime environment: local, containerised, cloud-native, embedded, serverless
6. For **database migrations**: locate schema files (`.sql`, migration scripts, ORM models)
7. For **notebook conversions**: count `.ipynb` files, note any execution-order dependencies

**Output:**
- Migration type classification (one or more categories)
- Source language/version + build system
- Project purpose (2–3 sentences)
- Runtime environment

**MCP fallback:** Always native for this step — no MCP required.

---

### Step 2 — Scan codebase structure and define scope
**Mode:** Ask (read-only)
**Input:** Project root
**Actions:**
1. List all top-level directories and files
2. Identify in-scope source directories by migration type:
   - `version-upgrade` / `cross-language` / `framework-swap`: `src/`, `lib/`, `app/`, `pkg/`, `core/`
   - `notebook-conversion`: every `.ipynb` file is IN scope
   - `database-migration`: schema files, migration scripts, ORM/repository classes
3. Apply these **universal exclusion rules** (unless the user overrides):
   - Compiled output and build artefacts (`.class`, `.o`, `.pyc`, `target/`, `dist/`, `build/`)
   - Binary model weights and large data files
   - Auto-generated code (marked `@Generated`, `# auto-generated`, `DO NOT EDIT`)
   - Vendored third-party code (`vendor/`, `node_modules/`, `.venv/`, `__pycache__/`)
   - Documentation-only dirs with no executable code
   - Demo, screenshot, and static asset directories
4. Count approximate LOC per language (in-scope files only)
5. Identify architectural layers: API, business logic, data access, ML/AI, frontend,
   infrastructure-as-code, tests

**Output:**
- File inventory table: directory | file count | LOC | in-scope Y/N | reason if excluded
- Architecture layer map
- Explicit scope statement:
  `IN SCOPE: <list>` and `OUT OF SCOPE: <list> — reason: <rationale>`

**Coin discipline:** Never load out-of-scope directories into context.

---

### Step 3 — Analyse dependencies and portability
**Mode:** Ask (read-only)
**Input:** Build manifest(s) identified in step 1

**If Dependency Scanner MCP is present:**
- Call `dependency_scanner.analyze(build_file)` → dependency tree JSON

**If MCP absent (fallback):**
- Read build manifest directly
- Parse each dependency name and version

**Classify every dependency with this universal portability framework:**

| Label | Meaning | Action |
|---|---|---|
| ✅ Direct equivalent | Same library or well-known port exists in target | Swap |
| ⚠️ Partial equivalent | Target library covers most functionality, with gaps | Use + workaround |
| 🔄 Rewrite required | No equivalent — functionality must be reimplemented | Estimate effort |
| 🔒 Boundary binding | Must remain in source language; call via API/FFI/subprocess | Design boundary |
| ❌ No viable path | Fundamental paradigm mismatch | Recommend Retain |

**Migration-type-specific classification rules:**

- `version-upgrade`: flag APIs removed or deprecated between source and target version
- `cross-language`: ask "does the target ecosystem have this capability?"
  Specialised runtimes (ML inference, GPU compute, OS syscalls) → `🔒` or `❌`
- `framework-swap` (JDBC→JPA): map JDBC patterns to JPA equivalents;
  flag native queries, stored procedures, batch ops as `🔄` or `⚠️`
- `database-migration` (PostgreSQL→MongoDB): classify SQL features:
  JOINs, transactions, stored procedures, triggers, full-text search → label each
- `notebook-conversion`: deps usually port fine (same Python);
  flag `%magic` commands and `IPython.display` calls as `🔄`

**Output:**
- Dependency compatibility table: name | version | label | target equivalent | notes
- Count per label type
- Any `❌` or `🔒` items flagged as potential migration scope blockers

---

### Step 4 — Identify breaking changes
**Mode:** Ask (read-only)
**Input:** In-scope source files only

Apply the detection rules matching the classified migration type(s):

**`version-upgrade`:**
- APIs removed or deprecated between source and target version
- Changed defaults (encoding, GC behaviour, type coercion, numeric precision)
- Required build tool / compiler version changes

**`cross-language`:**
- Paradigm gaps: dynamic vs static typing, GC vs ownership, OOP vs functional
- Concurrency model: threads / async / actors / goroutines / green threads
- Error handling: exceptions vs Result/Option/Either
- Reflection, metaprogramming, runtime `eval()` with no target equivalent
- Standard library coverage gaps

**`framework-swap`:**
- Lifecycle and dependency injection model differences
- Annotation/configuration model changes
- JDBC→JPA specifics: native queries, stored procedures, pessimistic locking,
  batch inserts, `ResultSet` manual mapping
- REST→framework: routing model, middleware chain, request/response lifecycle

**`database-migration`:**
- Relational→document: JOIN elimination, denormalisation, referential integrity loss,
  transaction scope reduction
- SQL→NoSQL: no GROUP BY, no arbitrary joins, different aggregation pipeline
- Index strategy changes
- Data type mapping gaps

**`notebook-conversion`:**
- Cell execution order dependencies (variable defined in cell N used in cell M < N)
- `display()`, `IPython.display`, widget/interactive calls
- Magic commands: `%matplotlib`, `%%time`, `!shell_command`, `%run`
- Implicit state from repeated cell re-execution

**Output:**
- Breaking-change catalogue: item | severity (Blocker/High/Medium/Low) | affected files | remediation hint
- Count per severity

---

### Step 5 — Assess test coverage
**Mode:** Ask (read-only)
**Input:** Test directories, CI configuration files

**Auto-detect test locations by language:**
- Java: `src/test/`, `*Test.java`, `*Spec.java`, JUnit/TestNG config
- Python: `tests/`, `test_*.py`, `*_test.py`, `conftest.py`
- JavaScript/TS: `*.test.js`, `*.spec.ts`, Jest/Mocha/Vitest config
- Rust: `#[cfg(test)]` blocks, `tests/` integration directory
- Scala: `src/test/scala/`, ScalaTest/Specs2
- Go: `*_test.go`
- C++: Google Test, Catch2 test files
- Notebooks: `assert` cells, separate test notebooks
- Database migrations: rollback scripts, data validation queries

**If Test Generator MCP present:** Call `test_generator.coverage_report(src_dir)`

**If MCP absent (fallback):**
- Count test files vs source files
- Read a sample of test files to identify type: unit / integration / e2e / none
- Check CI configs: `.github/workflows/`, `Jenkinsfile`, `Makefile`, `tox.ini`, `.travis.yml`

**Test portability by migration type:**
- `version-upgrade`: existing tests likely portable — verify they run on target version
- `cross-language`: tests must be **fully rewritten** in target language — flag as XL effort
- `framework-swap`: tests need partial rewrite — mocking layer and integration setup change
- `database-migration`: data integrity tests are critical and likely need new tooling
- `notebook-conversion`: tests rarely exist — always flag as high-risk gap

**Output:**
- Coverage level: High (>70%) / Medium (40–70%) / Low (<40%) / None
- Portability assessment for this specific migration type
- Test gaps that must be closed before validation phase

---

### Step 6 — Evaluate security posture
**Mode:** Ask (read-only)
**Input:** In-scope source code, config files, build manifest

**If Security Scanner MCP present:** Call `security_scanner.scan(repo_path)`

**If MCP absent (universal fallback checks):**
1. **Secrets exposure**: `.gitignore` coverage; grep for `API_KEY`, `SECRET`, `PASSWORD`,
   `TOKEN`, `PRIVATE_KEY`, `credentials` in non-env source files
2. **Dependency staleness**: flag dependencies not updated in >12 months
3. **Injection risks**: SQL string concatenation, `exec()`/`eval()` with user input,
   template injection, command injection via `subprocess`/`Runtime.exec()`
4. **Auth patterns**: hardcoded credentials, missing input validation, insecure defaults
5. **Data exposure**: PII in logs, unencrypted sensitive fields

**Migration-type additions:**
- `database-migration`: unparameterised queries, missing encryption at rest/transit
- `cross-language`: source-runtime-specific safety assumptions that don't transfer
  (e.g. Python GIL as implicit thread safety → invalid in Rust/C++/Go)
- `framework-swap`: target framework security defaults differ from source
  (CSRF, CORS, session management, input sanitisation)

**Output:**
- Finding list: severity (Critical/High/Medium/Low) | file reference | description
- Technical debt notes relevant to the migration

---

### Step 7 — Calculate readiness score
**Mode:** Ask (read-only)
**Input:** Outputs from steps 1–6

Score each dimension 0–100, then apply weights:

| Dimension | Weight | Scoring method |
|---|---|---|
| **Dependency portability** | 30% | (`✅` × 1.0 + `⚠️` × 0.5) / total_deps × 100 |
| **Breaking change density** | 25% | 100 − (Blockers×20 + High×10 + Medium×3 + Low×1), min 0 |
| **Code complexity** | 20% | Low=85, Medium=60, High=35, Very High=10 |
| **Test coverage** | 15% | High=90, Medium=60, Low=30, None=0 |
| **Security baseline** | 10% | 100 − (Critical×20 + High×10 + Medium×3), min 0 |

Show the dimension score and working for each before computing the final weighted sum.

Read `migration.readiness_threshold` from `.bob/config.yaml`.

- Score ≥ threshold → `✅ GATE PASSES` — continue to steps 8–10
- Score < threshold → `❌ GATE BLOCKED`:
  - Identify which dimensions are below their expected contribution
  - Give specific remediations per dimension
  - State whether reducing scope would push score above threshold
  - **Stop here and wait for user confirmation before writing any files**

**Output:** Score (0–100) with full dimension breakdown and gate decision.

---

### Step 8 — Recommend 7Rs per component
**Mode:** Ask (read-only)
**Input:** Component inventory (step 2), dependency labels (step 3), breaking changes (step 4)

Assign one 7R to each identified component and justify it.

**Universal 7R decision guide:**

| Strategy | Use when |
|---|---|
| **Retire** | Component is unused, redundant, or fully superseded in target |
| **Retain** | No viable migration path; keep as-is in source language/platform |
| **Relocate** | Move without any code changes (infrastructure only) |
| **Rehost** | Config/build updates only — logic unchanged |
| **Replatform** | Adopt target idioms with moderate rewrite; same core logic |
| **Repurchase** | Replace with a different library or managed service entirely |
| **Refactor** | Deep redesign — logic changes significantly for target paradigm |

**Migration-type decision rules (apply the matching set):**

`version-upgrade`:
- Uses removed/deprecated API → Replatform
- No deprecated usage → Rehost
- Can adopt new language features optionally → Refactor (optional improvement)

`cross-language`:
- Pure business logic, no ecosystem coupling → Refactor
- Tightly coupled to source runtime (ML inference, reflection, native bindings) → Retain
- Replaceable by a target-ecosystem library → Repurchase
- Out of language scope entirely (frontend, infra) → Retain

`framework-swap` (e.g. JDBC→JPA):
- Standard CRUD → Replatform
- Native SQL / stored procedures → Refactor (or Retain if complexity is high)
- Infrastructure concerns now managed by target framework → Repurchase

`database-migration` (e.g. relational→document):
- Simple entity with clean document shape → Replatform
- Heavily JOIN-dependent → Refactor (denormalise/embed)
- Stored procedures / triggers → Retire (move to app layer) or Retain
- Referential integrity constraints → Refactor (application-enforced)

`notebook-conversion`:
- Data exploration cells → Retire (not production code)
- Reusable pipeline logic → Refactor (extract to functions/classes with `if __name__ == "__main__"`)
- Training/eval cells → Retain as script or Refactor
- Visualisation cells → Retain as optional section or Retire

**Output:** 7Rs strategy matrix — one row per component:
component | current implementation | 7R | target implementation | rationale | effort (S/M/L/XL)

---

### Step 9 — Generate assessment report
**Mode:** Code (write)
**Input:** All prior step outputs
**Output path:** `migration.output.assessment_report` from `.bob/config.yaml`

Fill every section with real content — no placeholder text, no "TBD".

```markdown
# Migration Assessment Report

**Project:** <project name>
**Migration:** <source> → <target>
**Migration type:** <classification from step 1>
**Date:** <today>
**Readiness Score:** <X>/100 — ✅ PASS / ❌ BLOCKED (threshold: <Y>)

## 1. Executive Summary
## 2. Project Overview & Scope Boundary
## 3. Dependency Compatibility Analysis
## 4. Breaking Change Catalogue
## 5. Test Coverage Assessment
## 6. Security Posture
## 7. Readiness Score Breakdown
## 8. 7Rs Strategy Matrix
## 9. Effort Estimate & Risk Matrix
### Gate Decision
✅ PROCEED TO PLANNING MODE
— or —
❌ BLOCKED — <reason> — resolve by: <action>
```

Create output directory if it doesn't exist. Write the file.

---

### Step 10 — Create JIRA Epic (or stub)
**Mode:** Code (write)
**Input:** Assessment report + JIRA config from `.bob/config.yaml`

**If `jira.stub: true`:**
Generate child stories **dynamically from the step 8 7Rs matrix**.
Do not use boilerplate — every story title must name the actual component.

```markdown
# JIRA Epic Stub — Migration Assessment

**Epic title:** Migrate <project> from <source> to <target>
**Project key:** <from config>
**Labels:** migration, assessment, <source-slug>-to-<target-slug>
**Description:** <2-paragraph summary>

## Child stories (generated from 7Rs matrix)
| Story title | 7R | Effort | Notes |
|---|---|---|---|
| <one row per Replatform/Refactor/Repurchase/Rehost component> |
| Retain components: one tracking story each |
| Retire components: one decommission story each |

## Attachments
- <assessment report filename>
```

**If `jira.stub: false`:** Call JIRA MCP to create real epic and child issues.

---

## Gate exit criteria (Assessment → Planning)

| Criterion | Required |
|---|---|
| Migration type classified | ✅ |
| Readiness score ≥ `readiness_threshold` | ✅ |
| Scope boundary documented (IN / OUT with rationale) | ✅ |
| All in-scope components have a 7R assigned | ✅ |
| All Critical security findings have a mitigation noted | ✅ |
| Assessment report written to configured path | ✅ |
| JIRA epic or stub written | ✅ |

All pass → output:
```
✅ GATE 1 PASSED
   Score     : <X>/100  (threshold: <Y>)
   Report    : <path>
   JIRA stub : <path>
   Ready for : Migration Planning Mode
```

Any fail → output `❌ BLOCKED — <criterion> — <fix>`.

---

## Coin-efficiency rules
- Steps 1–8: single **Ask Mode** session, no file writes
- Steps 9–10: switch to **Code Mode** to write exactly 2 files
- Load only: `@<build-manifest>`, `@src/` (or equivalent in-scope dir), `@README.md`
- Never load: build artefacts, model weights, vendor dirs, auto-generated code
- Expected cost: ~2–3 Bobcoins for a small-medium repo
