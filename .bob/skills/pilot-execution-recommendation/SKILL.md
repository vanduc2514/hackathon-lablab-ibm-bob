---
name: pilot-execution-recommendation
description: Recommend Proof of Concept (PoC) scope to validate high-risk design decisions before full migration. Defines objectives, scope, success criteria, and resource requirements.
---

# Pilot Execution Recommendation

You are recommending a Proof of Concept (PoC) to validate critical design decisions before committing to full migration. Your goal is to identify what needs validation and define a focused pilot that reduces risk.

## Objective

Identify high-risk or uncertain aspects of the migration design and recommend a targeted PoC that validates feasibility, performance, and approach before full-scale execution.

## Required Inputs

- All design documents: target architecture, component strategies, interface contracts, dependencies, testing strategy
- Risk assessment from Assessment phase
- Technical uncertainties or unknowns
- Stakeholder concerns or questions

## Output Document

Create `design_poc_recommendations.md` containing:

**Validation Objectives**: What specific questions or risks the PoC will address

**Scope Definition**: Components, features, and scenarios to include (and explicitly exclude)

**Success Criteria**: Measurable outcomes that determine PoC success or failure

**Timeline & Resources**: Estimated duration, team members needed, infrastructure requirements

**Implementation Approach**: High-level steps to execute the PoC

**Decision Framework**: How PoC results will inform go/no-go decisions

## When to Recommend a PoC

Recommend a PoC when:
- **New Technology**: Team lacks experience with target platform or framework
- **Performance Uncertainty**: Unclear if target architecture meets performance requirements
- **Complex Integration**: Novel integration patterns or external system dependencies
- **Data Migration Risk**: Large-scale data transformation with potential data loss
- **Breaking Changes**: Significant API changes affecting many consumers
- **Cost Uncertainty**: Cloud migration with unclear cost implications

Skip PoC when:
- Well-understood technology with team expertise
- Low-risk, straightforward migration (e.g., minor version update)
- Time constraints don't allow for pilot phase

## PoC Design Process

1. **Identify Risks**: Review all design documents, highlight uncertainties
2. **Prioritize Validation**: Focus on highest-risk or highest-impact items
3. **Define Scope**: Select minimal components that validate key decisions
4. **Set Criteria**: Define specific, measurable success metrics
5. **Estimate Effort**: Calculate realistic timeline and resource needs
6. **Plan Execution**: Outline implementation steps and validation approach

## Example: Framework Migration PoC

```
PoC Recommendation: Quarkus → Spring Boot Migration

Validation Objectives:
1. Verify Spring Boot performance meets requirements (target: <200ms P95 latency)
2. Validate dependency injection migration approach (Quarkus CDI → Spring)
3. Confirm REST endpoint migration pattern works for all annotation types
4. Test data access layer migration (Panache → Spring Data JPA)
5. Validate build time and startup time acceptable for CI/CD pipeline

Scope:
Include:
- 2 representative REST controllers (simple CRUD + complex business logic)
- 1 service layer with dependency injection
- 1 repository with Panache queries → Spring Data JPA
- Integration tests for all endpoints
- Performance test simulating production load

Exclude:
- Authentication/authorization (use mock)
- Message queue integration (defer to later phase)
- All other 15 controllers (validate pattern, not exhaustive migration)
- UI/frontend changes

Success Criteria:
✅ All REST endpoints functional with equivalent behavior
✅ P95 latency ≤ 200ms under 100 req/sec load
✅ Unit test coverage ≥ 80%
✅ Integration tests pass with test containers
✅ Build time < 5 minutes
✅ Startup time < 30 seconds
✅ No critical security vulnerabilities in dependencies

Failure Criteria (triggers design revision):
❌ Performance >300ms P95 latency (unacceptable degradation)
❌ Data access layer requires major refactoring (pattern not viable)
❌ Build time >10 minutes (CI/CD impact too high)

Timeline: 2 weeks
- Week 1: Implement migration for scoped components
- Week 2: Testing, performance validation, documentation

Resources:
- 2 developers (full-time)
- 1 QA engineer (part-time for test design)
- AWS test environment (t3.medium instances)
- Budget: ~$500 for infrastructure

Decision Framework:
- All success criteria met → Proceed with full migration
- 1-2 criteria missed → Revise design, re-run PoC
- 3+ criteria missed or failure criteria hit → Reconsider migration approach
```

## PoC Execution Approach

1. **Setup Environment**: Provision infrastructure, configure tools
2. **Implement Scope**: Migrate selected components following design
3. **Run Tests**: Execute functional, integration, performance tests
4. **Measure Results**: Collect metrics against success criteria
5. **Document Findings**: Record what worked, what didn't, lessons learned
6. **Make Recommendation**: Go/no-go decision with supporting evidence

## Validation Checklist

- [ ] High-risk areas identified and prioritized
- [ ] PoC scope is minimal yet representative
- [ ] Success criteria are specific and measurable
- [ ] Failure criteria define when to stop and reconsider
- [ ] Timeline is realistic (typically 1-4 weeks)
- [ ] Resource requirements specified
- [ ] Decision framework clear for interpreting results
- [ ] PoC results will meaningfully reduce risk

## Guardrails

**Focused scope**: PoC should validate specific risks, not attempt full migration

**Time-boxed**: Set clear deadline to prevent scope creep

**Measurable outcomes**: Define objective criteria, not subjective assessments

**Disposable code**: PoC code may be throwaway—focus on learning, not production quality