# Migration Planning Skill
# Language-agnostic: works for any migration type that passed Gate 1.
# Self-contained: no external config.yaml required.

---

## Built-in Defaults
# These values are the single source of truth for Planning mode.
# To change a threshold, edit this file — nowhere else.

| Setting | Value | Notes |
|---|---|---|
| Planning report path | `reports/planning/migration-plan.md` | Created if missing |
| Comms plan path | `reports/planning/comms-plan.md` | Separate file, always generated |
| JIRA stories stub path | `reports/planning/jira-stories-stub.md` | Used when JIRA stub = true |
| JIRA stub mode | **true** | false = call real JIRA MCP |
| JIRA project key | **MIGR** | Match value from assessment skill |
| Timeline format | **Mermaid gantt** | Rendered in migration-plan.md |
| Rollback Manager MCP | **absent** → markdown runbook fallback | One runbook per phase |
| Resource matrix | **ask user** | Never assume team size or capacity |
| Success criteria scope | **per-phase** | One criteria block per migration phase |
| Buffer requirement | **20%** minimum | Applied to all timeline estimates |
| Assessment report path | `reports/assessment/assessment-report.md` | Read-only input |

---

## Entry Criteria (Gate 1 → Planning)

- `reports/assessment/assessment-report.md` exists
- Gate Decision in report is `✅ PROCEED TO PLANNING MODE`
- Readiness score ≥ 65 (threshold from assessment skill)
- **Ask Mode** (read-only) for steps 1–4
- **Code Mode** (write) only for steps 5–7

---

## 8-Step Workflow

### Step 1 — Validate entry and parse assessment report
**Mode:** Ask | **Input:** `reports/assessment/assessment-report.md`

1. Confirm file exists. If missing → stop:
   _"No assessment report found at `reports/assessment/assessment-report.md`. Run Migration Assessment Mode first."_
2. Read Gate Decision section. If not `✅ PROCEED TO PLANNING MODE` → stop:
   _"Assessment gate has not passed (score: X/100). Resolve blocking issues before planning."_
3. Extract and hold in memory:

| Field | Source in assessment report |
|---|---|
| `project_name` | Header `**Project:**` |
| `migration_label` | Header `**Migration:**` |
| `migration_type` | Header `**Migration Type:**` |
| `readiness_score` | Header `**Readiness Score:**` |
| `effort_classification` | Section 8/9 `**Overall Classification:**` |
| `critical_path` | Section 9 `**Critical Path:**` |
| `7rs_components` | Section 8 — full 7Rs Strategy Matrix (all rows, all columns) |
| `risk_matrix` | Section 9 — Risk Matrix table |
| `warnings` | Gate Decision `**Warnings:**` list |
| `success_criteria` | Section 9 / Gate Decision `**Success Criteria:**` list |
| `blocking_issues` | Gate Decision `**Blocking Issues:**` |
| `recommended_next_steps` | Gate Decision `**Recommended Next Steps:**` list |
| `effort_table` | Section 9 — effort table (phase | tasks | effort | dependencies) |

4. Confirm to the user:
   > "Assessment loaded: **{project_name}** — {migration_label} | Score: {readiness_score} | Effort: {effort_classification} | Components: {count} | Warnings: {count}"

---

### Step 2 — Gather team capacity
**Mode:** Ask | **Input:** User response

Ask the user exactly these three questions. Do not proceed until all three are answered.

```
Before generating the resource matrix and timeline, I need a few details:

1. How many developers will work on this migration, and what are their rough roles?
   (e.g. "2 mid-level devs, 1 senior, 1 QA")

2. What is each person's availability for migration work?
   (e.g. "2 devs at 50% for 2 weeks, senior at 25% throughout")

3. Are there any hard schedule constraints?
   (e.g. release freeze dates, sprint boundaries, on-call rotations, public holidays)
```

Record as:
- `team_roster` — list of (role, count, availability %)
- `total_capacity_days` — calculate from roster × availability × calendar days
- `schedule_constraints` — list of hard dates/windows from user

If the user gives vague capacity (e.g. "a couple of devs"), ask one follow-up to get a number.
Never assume capacity — always confirm.

---

### Step 3 — Build task breakdown
**Mode:** Ask | **Input:** `7rs_components`, `effort_table`, `warnings` from step 1

1. Derive migration tasks from the 7Rs matrix:
   - Each component with strategy **Rehost** → 1 task (config/build update)
   - Each component with strategy **Replatform** → 1–2 tasks (update + verify)
   - Each component with strategy **Refactor** → 2–3 tasks (design + implement + review)
   - Each component with strategy **Repurchase** → 2 tasks (replace + migrate data/logic)
   - Each component with strategy **Retire** → 1 task (remove + confirm no downstream impact)
   - Each component with strategy **Retain** → 0 tasks (document decision only)
   - Each component with strategy **Relocate** → 1 task (infra move)

2. Add standard non-component tasks per migration phase:
   - **Pre-migration:** test baseline establishment (always, if test coverage < High)
   - **Pre-migration:** security remediation tasks (for every Critical/High finding in assessment)
   - **Migration:** build verification after each batch
   - **Validation:** full test suite run · performance benchmark · security rescan
   - **Optimization:** configuration tuning · documentation update
   - **Hypercare:** monitoring setup · rollback readiness check

3. Sequence tasks using dependencies from `effort_table` `critical_path`.

4. Apply effort in days:
   - S (Small) = 0.25–0.5 days
   - M (Medium) = 0.5–1 day
   - L (Large) = 1–2 days
   - XL (Extra Large) = 3–5 days

5. Add 20% buffer to total.

**Output (internal):** task list (id | title | 7R | effort_days | depends_on | phase | assignee_role)

---

### Step 4 — Derive per-phase success criteria
**Mode:** Ask | **Input:** `success_criteria` from assessment + task list from step 3

For each of the 5 active migration phases (Pre-migration, Migration, Validation, Optimization,
Hypercare), define 3–6 measurable success criteria.

Rules:
- Carry forward assessment-level success criteria into the appropriate phase
- Every criterion must be binary (pass/fail) or have a numeric threshold
- Validation phase must always include: test pass rate, performance variance (±10%), security scan result
- Pre-migration phase must include test coverage target if assessment flagged test gap
- Hypercare phase must include uptime target and MTTR

**Output (internal):** phase → criteria list mapping

---

### Step 5 — Write migration plan
**Mode:** Code | **Output:** `reports/planning/migration-plan.md`

Fill every section. No "TBD", no placeholders. Use data from steps 1–4.

```markdown
# Migration Plan

**Project:** {project_name}
**Migration:** {migration_label}
**Migration Type:** {migration_type}
**Date:** {today}
**Effort Classification:** {effort_classification}
**Planning Status:** Draft — Pending Approval

---

## 1. Executive Summary

<3–4 sentences: what is being migrated, why, overall approach, total effort with buffer>

---

## 2. Scope & Component Strategy

<Reproduce the 7Rs matrix from assessment verbatim — do not reinterpret>

---

## 3. Task Breakdown

| ID | Task | Phase | 7R | Effort (days) | Depends On | Assignee Role |
|---|---|---|---|---|---|---|
<one row per task from step 3>

**Total effort (raw):** X days
**Buffer (20%):** Y days
**Total effort (with buffer):** Z days

---

## 4. Timeline

<Mermaid gantt diagram — rules below>

```mermaid
gantt
    title {project_name} — {migration_label}
    dateFormat  YYYY-MM-DD
    excludes    <weekends + any schedule_constraints from step 2>

    section Pre-Migration
    <one bar per pre-migration task, id matches task table>

    section Migration
    <one bar per migration task>

    section Validation
    <one bar per validation task>

    section Optimization
    <one bar per optimization task>

    section Hypercare
    <one bar per hypercare task>
```

Timeline rules:
- Start date = today + 1 business day (allow plan review)
- Each task bar uses the effort_days from step 3
- Parallel tasks (no dependency on each other) run concurrently
- Sequential tasks (depends_on set) start after dependency ends
- Apply schedule_constraints as `milestone` markers or `excludes`
- Add a `Plan Approved` milestone at day 0 before Pre-Migration section

---

## 5. Resource Matrix

| Role | Count | Availability | Capacity (days) | Assigned Phases | Assigned Tasks |
|---|---|---|---|---|---|
<one row per role from step 2 team_roster>
<final row: Total — — {total_capacity_days} days — —>

**Capacity check:** Total capacity ({total_capacity_days} days) vs total effort with buffer ({Z} days)
<If capacity < effort: flag gap and recommend options (extend timeline, reduce scope, add resource)>
<If capacity ≥ effort: confirm feasible>

---

## 6. Risk Mitigation Plan

<Reproduce risk matrix from assessment, add a "Planning-Phase Mitigation" column>

| Risk | Probability | Impact | Assessment Mitigation | Planning Action | Owner Role |
|---|---|---|---|---|---|
<one row per risk, add any new risks identified during planning>

---

## 7. Per-Phase Success Criteria

### Pre-Migration
<criteria list from step 4>

### Migration
<criteria list from step 4>

### Validation
<criteria list from step 4>

### Optimization
<criteria list from step 4>

### Hypercare
<criteria list from step 4>

---

## 8. Rollback Procedures

<One subsection per phase — see rollback runbook rules below>

### Pre-Migration Rollback
**Trigger:** <what condition requires rollback>
**Steps:**
1. <manual step>
2. <manual step>
...
**Recovery time estimate:** <N hours/days>
**Owner:** <role>

### Migration Rollback
...

### Validation Rollback
...

### Optimization Rollback
...

### Hypercare Rollback
...

---

## Gate Decision

### Planning Outcome
<✅ PROCEED TO DESIGN MODE or ❌ BLOCKED — reason>

**Gate Criteria Status:**
- <list each criterion with ✅ or ❌>

**Blocking Issues:** <None or list>

**Recommended Next Steps:**
1. Review and approve this plan
2. Confirm team availability and task assignments
3. Proceed to Migration Design Mode
```

Rollback runbook rules (Rollback Manager MCP absent):
- Each phase rollback is a standalone markdown subsection
- Steps must be manual, concrete, executable without tooling
- Migration rollback must include: revert command / restore-from-backup instruction
- Always include a recovery time estimate and an owner role
- Never use vague language ("undo changes", "restore previous state") — be specific

---

### Step 6 — Write JIRA stories stub
**Mode:** Code | **Output:** `reports/planning/jira-stories-stub.md`

Generate one story per task from the step 3 task list. No boilerplate stories.

```markdown
# JIRA Stories Stub — Migration Planning
**Project:** {project_name} | **Epic:** MIGR-{n} (from assessment epic stub) | **Sprint:** TBD

---

## Stories

### {MIGR-nn} {Task title}
**Phase:** {phase}
**7R Strategy:** {7R}
**Story Points:** {effort_days × 2, rounded to nearest Fibonacci: 1/2/3/5/8}
**Depends On:** {MIGR-xx or None}
**Assignee Role:** {role}

**Description:**
<2–3 sentences: what needs to be done and why, referencing the component from the 7Rs matrix>

**Acceptance Criteria:**
- [ ] <binary criterion 1>
- [ ] <binary criterion 2>
- [ ] <binary criterion 3>

**Labels:** migration, {migration_type}, {phase}, {7R-strategy-lowercase}

---
```

Repeat for every task. Link to parent epic from assessment epic stub.
If JIRA stub = false: call JIRA MCP to create real stories and link to epic.

---

### Step 7 — Write communications plan
**Mode:** Code | **Output:** `reports/planning/comms-plan.md`

```markdown
# Communications Plan

**Project:** {project_name}
**Migration:** {migration_label}
**Date:** {today}

---

## 1. Stakeholder Map

| Stakeholder | Role | Interest | Communication Need |
|---|---|---|---|
| Technical Lead | Approver | Architecture decisions | Gate approvals, blockers |
| Developers | Executors | Task assignments, blockers | Daily standups, task updates |
| QA / Validation | Validator | Test results, Go/No-Go | Validation reports |
| Product Owner | Approver | Timeline, risk | Weekly status, gate outcomes |
| Operations | Support | Deployment, monitoring | Hypercare dashboard, incidents |

<Add or remove rows based on team_roster from step 2>

---

## 2. Communication Schedule

| Event | Frequency | Audience | Channel | Owner | Template |
|---|---|---|---|---|---|
| Kickoff meeting | Once (before Pre-Migration) | All stakeholders | Meeting | Tech Lead | See §4 |
| Daily standup | Daily (Migration phase) | Dev team | Standup | Dev Lead | 3 questions |
| Weekly status | Weekly (all phases) | Tech Lead + PO | Async report | Bob | See §4 |
| Gate review | Per gate (×5) | Tech Lead + PO | Meeting | Tech Lead | See §4 |
| Incident alert | As needed | Tech Lead + Ops | Immediate | On-call | See §4 |
| Post-migration review | Once (end of Hypercare) | All stakeholders | Meeting | Tech Lead | See §4 |

---

## 3. Escalation Path

| Situation | Escalate To | Within | Method |
|---|---|---|---|
| Blocker unresolved > 1 day | Tech Lead | 24 hours | Direct message |
| Gate blocked | Product Owner | Same day | Sync meeting |
| Security finding (Critical) | Tech Lead + Security | Immediate | Alert |
| Rollback triggered | Tech Lead + Ops | Immediate | Incident channel |
| Timeline slip > 20% | Product Owner | Same day | Written update |

---

## 4. Message Templates

### Kickoff
> **Subject:** Migration Kickoff — {migration_label}
> We are proceeding with {migration_label} for {project_name}. Assessment readiness score: {readiness_score}/100.
> Plan approved. Migration starts {start_date}. Key risks: {top 2 warnings from assessment}.
> Gate reviews scheduled at end of each phase. Questions to Tech Lead.

### Weekly Status
> **{project_name} Migration — Week N Status**
> Phase: {current_phase} | Progress: {X of Y tasks complete}
> On track: {Yes/No} | Blockers: {list or None}
> Next week: {planned tasks}

### Gate Outcome
> **Gate {N} — {Phase} → {Next Phase}**
> Result: {✅ PASSED / ❌ BLOCKED}
> Criteria met: {list} | Criteria failed: {list or None}
> Next action: {proceed / remediate X by date}

### Rollback Alert
> **⚠️ ROLLBACK TRIGGERED — {project_name}**
> Phase: {phase} | Trigger: {reason}
> Action taken: {rollback step summary}
> Status: {in progress / complete} | ETA: {time}
> Owner: {role}

---

## 5. Warnings from Assessment (carry forward)

<List all warnings from assessment Gate Decision — these are standing communication items
that must be referenced in kickoff and gate reviews>
```

---

## Gate Exit Criteria (Planning → Design)

| Criterion | Required |
|---|---|
| Assessment report parsed and gate confirmed | ✅ |
| Team capacity gathered and confirmed | ✅ |
| All 7Rs components have tasks | ✅ |
| Total effort calculated with 20% buffer | ✅ |
| Capacity ≥ effort (or gap flagged with options) | ✅ |
| Mermaid timeline covers all tasks | ✅ |
| Per-phase success criteria defined | ✅ |
| Rollback runbook per phase written | ✅ |
| Migration plan written | ✅ |
| JIRA stories stub written | ✅ |
| Communications plan written | ✅ |

Pass → `✅ GATE 2 PASSED | Report: reports/planning/migration-plan.md | Ready for: Design Mode`
Fail → `❌ BLOCKED — {criterion} — {fix}`

---

## Coin Rules
- Steps 1–4: single Ask Mode session, no writes
- Steps 5–7: Code Mode, exactly 3 files
- Load only: `@reports/assessment/assessment-report.md`
- Never load: source code, vendor dirs, build artefacts
- Expected cost: ~2–3 Bobcoins
