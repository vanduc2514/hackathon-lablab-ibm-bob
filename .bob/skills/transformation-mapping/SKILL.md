---
name: transformation-mapping
description: Define migration strategy per component using the 7Rs framework. Maps each component's transformation approach, dependencies, sequence, and rollback procedures.
---

# Transformation Mapping

You are defining how each component will be transformed during migration. Apply the 7Rs framework to determine the optimal migration strategy per component.

## Objective

Document the migration approach for every component, including strategy selection, dependency analysis, migration sequence, and rollback procedures.

## The 7Rs Framework

- **Rehost**: Lift-and-shift with minimal changes (fastest, least optimization)
- **Replatform**: Optimize for target platform without major refactoring
- **Refactor**: Restructure for cloud-native patterns and modern architecture
- **Repurchase**: Replace with SaaS or managed service
- **Retire**: Decommission if no longer needed
- **Retain**: Keep as-is if not ready to migrate
- **Relocate**: Move to different infrastructure (hypervisor-level)

## Required Inputs

- `design_target_architecture.md`: Target state definition
- Assessment Report: Component inventory, dependencies, complexity scores
- Migration Plan: Timeline, resource constraints, risk tolerance

## Output Document

Create `design_component_strategies.md` containing:

**Component Inventory**: List of all components with current technology and purpose

**Strategy Matrix**: Table with columns: Component | Current Stack | Target Stack | 7Rs Strategy | Rationale | Risk Level

**Migration Sequence**: Ordered list of components with dependencies and parallel opportunities

**Dependency Graph**: Visual or textual representation of component dependencies

**Rollback Procedures**: Per-component rollback strategy and validation checkpoints

## Strategy Selection Process

For each component:

1. **Assess Complexity**: Review code structure, dependencies, business criticality
2. **Evaluate Options**: Consider all 7Rs strategies against constraints
3. **Select Strategy**: Choose based on risk, effort, and target architecture alignment
4. **Document Rationale**: Explain why this strategy over alternatives
5. **Identify Dependencies**: Map what must migrate before/after this component
6. **Define Rollback**: Specify how to safely revert if migration fails

## Example: E-commerce Application

```
Component: User Authentication Service
- Current: Custom JWT implementation in Java 11
- Target: Spring Security 6 with OAuth2 in Java 21
- Strategy: Replatform
- Rationale: Leverage Spring Security's mature OAuth2 support, reduce custom code
- Dependencies: Must migrate after database layer, before API gateway
- Risk: Medium (authentication is critical but well-tested patterns exist)
- Rollback: Keep old service running, route traffic back via feature flag

Component: Legacy Reporting Module
- Current: Perl scripts generating PDF reports
- Target: N/A
- Strategy: Retire
- Rationale: Usage analytics show <1% utilization, functionality replaced by BI tool
- Dependencies: None (isolated module)
- Risk: Low (minimal usage, alternative exists)
- Rollback: Archive scripts, document alternative in BI tool
```

## Migration Sequencing Guidelines

- **Foundation First**: Migrate infrastructure, databases, core services before dependent components
- **Parallel Opportunities**: Identify independent components that can migrate simultaneously
- **Risk Distribution**: Don't migrate all high-risk components in same phase
- **Validation Points**: Define checkpoints between component migrations
- **Incremental Value**: Prioritize components that deliver business value early

## Validation Checklist

- [ ] All components assigned a 7Rs strategy
- [ ] Rationale documented for each strategy decision
- [ ] Dependencies identified and mapped
- [ ] Migration sequence is logical and respects dependencies
- [ ] Rollback procedure defined per component
- [ ] Risk levels assessed and distributed across phases
- [ ] Parallel migration opportunities identified

## Guardrails

**Strategy over tactics**: Define what to do, not how to implement (that's for Execution Mode)

**Dependency accuracy**: Verify dependencies through code analysis, not assumptions

**Realistic sequencing**: Ensure migration order is achievable within timeline constraints