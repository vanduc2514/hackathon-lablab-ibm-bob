---
name: hypercare-exit-criteria
description: Define comprehensive exit criteria for transitioning from hypercare to standard operations. Specifies measurable thresholds, readiness assessments, and sign-off procedures.
---

# Hypercare Exit Criteria

You are defining comprehensive exit criteria for transitioning from intensive hypercare support to standard operations. Your goal is to establish clear, measurable criteria that ensure the system is stable and the operations team is ready before ending hypercare.

## Objective

Produce detailed exit criteria documentation that specifies exactly what must be achieved before transitioning to standard operations, including stability metrics, operational readiness, and stakeholder approval requirements.

## Required Inputs

- `hypercare_monitoring_setup.md`: Monitoring configuration and alert thresholds
- `hypercare_incident_response_procedures.md`: Incident handling procedures
- `hypercare_performance_validation_plan.md`: Performance validation procedures
- `hypercare_knowledge_transfer_plan.md`: Training and handoff plan
- `optimization_report.md`: Performance targets and baselines
- Business requirements: acceptable risk levels, SLA commitments

## Output Document

Create `hypercare_exit_criteria.md` containing:

**Exit Criteria Overview**: Summary of requirements for hypercare completion

**Stability Metrics**: Specific performance and reliability thresholds that must be met

**Operational Readiness**: Criteria for operations team capability and confidence

**Documentation Completeness**: Required documentation and knowledge base status

**Stakeholder Approval**: Sign-off requirements and approval process

**Transition Plan**: Steps for moving from hypercare to standard operations

**Contingency Plan**: What to do if exit criteria are not met

## Exit Criteria Categories

### 1. System Stability Metrics
- Availability and uptime requirements
- Error rate thresholds
- Performance metrics (latency, throughput)
- Incident frequency and severity
- Resource utilization patterns

### 2. Operational Readiness
- Operations team training completion
- Incident handling capability
- Tool proficiency
- Confidence assessment
- Escalation path clarity

### 3. Process Maturity
- Runbooks tested and validated
- Incident response proven effective
- Monitoring and alerting tuned
- Change management working
- Communication protocols established

### 4. Knowledge Transfer
- Documentation complete and accessible
- Training materials validated
- FAQ built from real issues
- Lessons learned captured
- Continuous improvement plan

### 5. Business Confidence
- Stakeholder satisfaction
- User feedback positive
- Cost projections validated
- Risk assessment acceptable
- Leadership approval obtained

## Example: Hypercare Exit Criteria

```markdown
# Hypercare Exit Criteria: E-Commerce Platform

## Executive Summary

This document defines the specific, measurable criteria that must be met before transitioning from intensive hypercare support to standard operations. All criteria must be satisfied for a minimum of 2 consecutive weeks before transition.

**Target Transition Date**: Week 4 of hypercare (flexible based on criteria achievement)

**Approval Required From**:
- Engineering Manager
- Operations Lead
- Product Owner
- CTO (for final sign-off)

---

## Stability Metrics Criteria

### Criterion 1: System Availability

**Requirement**: System availability ≥ 99.9% for 2 consecutive weeks

**Measurement**:
```promql
# Calculate availability over 2 weeks
(1 - (sum(rate(http_requests_total{status=~"5.."}[2w])) / 
      sum(rate(http_requests_total[2w])))) * 100
```

**Current Status**: [To be updated weekly]
- Week 1: 99.8% ❌
- Week 2: 99.95% ✅
- Week 3: 99.97% ✅
- Week 4: 99.98% ✅

**Status**: ✅ Met | ⚠️ At Risk | ❌ Not Met

**Notes**: [Any incidents or planned downtime that affected availability]

---

### Criterion 2: Error Rate

**Requirement**: Error rate < 0.5% for 2 consecutive weeks

**Measurement**:
```promql
# Calculate error rate over 2 weeks
sum(rate(http_requests_total{status=~"5.."}[2w])) / 
sum(rate(http_requests_total[2w])) * 100
```

**Current Status**:
- Week 1: 0.8% ❌
- Week 2: 0.4% ✅
- Week 3: 0.3% ✅
- Week 4: 0.2% ✅

**Status**: ✅ Met | ⚠️ At Risk | ❌ Not Met

**Notes**: [Error types, trends, any concerning patterns]

---

### Criterion 3: Performance Targets

**Requirement**: All performance metrics within target ranges for 2 consecutive weeks

| Metric | Target | Week 1 | Week 2 | Week 3 | Week 4 | Status |
|--------|--------|--------|--------|--------|--------|--------|
| Throughput | ≥ 400 req/sec | 380 ❌ | 420 ✅ | 450 ✅ | 465 ✅ | ✅ |
| P50 Latency | < 100ms | 95ms ✅ | 90ms ✅ | 85ms ✅ | 83ms ✅ | ✅ |
| P95 Latency | < 300ms | 285ms ✅ | 270ms ✅ | 250ms ✅ | 247ms ✅ | ✅ |
| P99 Latency | < 800ms | 920ms ❌ | 680ms ✅ | 610ms ✅ | 580ms ✅ | ✅ |

**Status**: ✅ Met | ⚠️ At Risk | ❌ Not Met

**Notes**: [Any performance issues, optimizations applied]

---

### Criterion 4: Incident Frequency

**Requirement**: No P0 incidents for 1 week, P1 incidents < 2 per week for 2 weeks

**Current Status**:

| Week | P0 | P1 | P2 | P3 | Status |
|------|----|----|----|----|--------|
| 1 | 0 ✅ | 3 ❌ | 9 | 15 | ❌ |
| 2 | 0 ✅ | 1 ✅ | 6 | 12 | ✅ |
| 3 | 0 ✅ | 0 ✅ | 4 | 8 | ✅ |
| 4 | 0 ✅ | 1 ✅ | 3 | 5 | ✅ |

**Status**: ✅ Met | ⚠️ At Risk | ❌ Not Met

**Notes**: [Description of any P0/P1 incidents, root causes addressed]

---

### Criterion 5: Resource Utilization

**Requirement**: Resource utilization stable and within capacity limits for 2 weeks

| Resource | Target | Week 1 | Week 2 | Week 3 | Week 4 | Status |
|----------|--------|--------|--------|--------|--------|--------|
| CPU | < 70% avg | 68% ✅ | 62% ✅ | 58% ✅ | 55% ✅ | ✅ |
| Memory | < 80% | 72% ✅ | 70% ✅ | 68% ✅ | 67% ✅ | ✅ |
| DB Connections | < 80% pool | 65% ✅ | 58% ✅ | 52% ✅ | 48% ✅ | ✅ |
| Disk Space | > 30% free | 45% ✅ | 43% ✅ | 42% ✅ | 41% ✅ | ✅ |

**Status**: ✅ Met | ⚠️ At Risk | ❌ Not Met

**Notes**: [Any resource trends, capacity planning updates]

---

## Operational Readiness Criteria

### Criterion 6: Operations Team Training

**Requirement**: All operations team members complete training and demonstrate competency

**Training Completion**:
- [ ] Week 1: Observation and shadowing - 100% complete
- [ ] Week 2: Hands-on training - 100% complete
- [ ] Week 3: Supervised practice - 100% complete
- [ ] Week 4: Independent operation - 100% complete

**Competency Verification**:
- [ ] Can navigate all monitoring dashboards
- [ ] Can interpret key metrics and alerts
- [ ] Can perform daily health checks
- [ ] Can execute deployment procedures
- [ ] Can perform rollback if needed
- [ ] Can diagnose and resolve P2/P3 incidents
- [ ] Knows when and how to escalate
- [ ] Comfortable with all operational tools

**Status**: ✅ Met | ⚠️ At Risk | ❌ Not Met

**Notes**: [Training completion dates, any gaps identified]

---

### Criterion 7: Incident Handling Capability

**Requirement**: Operations team successfully handles incidents independently

**Verification**:
- [ ] Handled at least 5 P2/P3 incidents independently
- [ ] Average resolution time within SLA
- [ ] Proper documentation for all incidents
- [ ] Appropriate escalation decisions made
- [ ] No incidents escalated unnecessarily
- [ ] Post-incident reviews completed

**Incident Handling Record**:
| Incident | Severity | Handled By | Resolution Time | Escalated | Outcome |
|----------|----------|------------|-----------------|-----------|---------|
| INC-001 | P2 | Ops Team | 45 min | No | ✅ Resolved |
| INC-002 | P3 | Ops Team | 2 hours | No | ✅ Resolved |
| INC-003 | P1 | Ops Team | 15 min | Yes (appropriate) | ✅ Resolved |
| INC-004 | P2 | Ops Team | 1 hour | No | ✅ Resolved |
| INC-005 | P2 | Ops Team | 30 min | No | ✅ Resolved |

**Status**: ✅ Met | ⚠️ At Risk | ❌ Not Met

**Notes**: [Team performance assessment, areas of strength/improvement]

---

### Criterion 8: Confidence Assessment

**Requirement**: Operations team and engineering leadership confident in readiness

**Operations Team Self-Assessment** (1-5 scale, target ≥ 4):
- System understanding: [4.5/5] ✅
- Monitoring proficiency: [4.2/5] ✅
- Incident response capability: [4.0/5] ✅
- Tool proficiency: [4.3/5] ✅
- Overall confidence: [4.2/5] ✅

**Engineering Leadership Assessment**:
- [ ] Engineering Manager approves handoff
- [ ] Operations Lead confirms team readiness
- [ ] No major concerns or knowledge gaps
- [ ] Team demonstrated capability in real scenarios

**Status**: ✅ Met | ⚠️ At Risk | ❌ Not Met

**Notes**: [Feedback from team and leadership]

---

## Process Maturity Criteria

### Criterion 9: Runbook Validation

**Requirement**: All operational runbooks tested and validated in production

**Runbook Status**:
- [ ] Daily health check - Tested 20+ times ✅
- [ ] Application deployment - Tested 4 times ✅
- [ ] Rollback procedure - Tested 1 time ✅
- [ ] Database maintenance - Tested 2 times ✅
- [ ] High error rate troubleshooting - Tested 3 times ✅
- [ ] High latency troubleshooting - Tested 2 times ✅
- [ ] Pod crash troubleshooting - Tested 2 times ✅

**Runbook Quality**:
- [ ] All procedures accurate and complete
- [ ] Commands and queries verified
- [ ] Edge cases documented
- [ ] Updated based on real incidents
- [ ] Accessible to all team members

**Status**: ✅ Met | ⚠️ At Risk | ❌ Not Met

**Notes**: [Any runbook updates needed, lessons learned]

---

### Criterion 10: Monitoring and Alerting

**Requirement**: Monitoring and alerting proven effective and tuned

**Alert Effectiveness**:
- [ ] No false positive alerts in past week
- [ ] All real issues detected by monitoring
- [ ] Alert thresholds appropriate (not too sensitive/insensitive)
- [ ] Escalation paths working correctly
- [ ] Notification channels reliable

**Monitoring Coverage**:
- [ ] All critical metrics monitored
- [ ] Dashboards provide actionable insights
- [ ] Log aggregation working effectively
- [ ] APM tracing available for troubleshooting
- [ ] No blind spots identified

**Status**: ✅ Met | ⚠️ At Risk | ❌ Not Met

**Notes**: [Any monitoring gaps, alert tuning performed]

---

## Documentation Criteria

### Criterion 11: Documentation Completeness

**Requirement**: All required documentation complete, accurate, and accessible

**Documentation Checklist**:
- [ ] Architecture documentation current
- [ ] API documentation up to date
- [ ] Runbook library complete
- [ ] Troubleshooting guide validated
- [ ] FAQ built from real issues (≥ 20 entries)
- [ ] Incident post-mortems documented
- [ ] Lessons learned captured
- [ ] Contact lists current

**Documentation Quality**:
- [ ] Accurate and tested
- [ ] Easy to find and navigate
- [ ] Regularly updated
- [ ] Accessible to all team members
- [ ] Version controlled

**Status**: ✅ Met | ⚠️ At Risk | ❌ Not Met

**Notes**: [Documentation location, update process]

---

### Criterion 12: Knowledge Base

**Requirement**: Comprehensive knowledge base built from hypercare experience

**Knowledge Base Contents**:
- [ ] All incidents documented with solutions
- [ ] Common issues and resolutions (≥ 15 entries)
- [ ] Performance optimization tips
- [ ] Configuration best practices
- [ ] Troubleshooting decision trees
- [ ] Tool usage guides

**Knowledge Base Usage**:
- [ ] Operations team uses KB as first resource
- [ ] KB reduces repeat questions
- [ ] KB updated continuously
- [ ] Search functionality effective

**Status**: ✅ Met | ⚠️ At Risk | ❌ Not Met

**Notes**: [KB location, contribution process]

---

## Business Criteria

### Criterion 13: Stakeholder Satisfaction

**Requirement**: Key stakeholders satisfied with system stability and support readiness

**Stakeholder Feedback**:
- [ ] Product Owner: Satisfied with system performance ✅
- [ ] Engineering Manager: Confident in operations team ✅
- [ ] Operations Lead: Team ready for standard operations ✅
- [ ] Business Users: No major concerns ✅
- [ ] Customer Support: Manageable issue volume ✅

**User Feedback**:
- User-reported issues: [Low/Acceptable/High]
- User satisfaction: [Positive/Neutral/Negative]
- Performance complaints: [None/Few/Many]

**Status**: ✅ Met | ⚠️ At Risk | ❌ Not Met

**Notes**: [Stakeholder comments, user feedback summary]

---

### Criterion 14: Cost Validation

**Requirement**: Actual costs align with projections (within 10%)

**Cost Comparison**:
| Category | Projected | Actual | Variance | Status |
|----------|-----------|--------|----------|--------|
| Compute | $225/mo | $235/mo | +4% | ✅ |
| Database | $360/mo | $345/mo | -4% | ✅ |
| Storage | $25/mo | $28/mo | +12% | ⚠️ |
| Network | $80/mo | $75/mo | -6% | ✅ |
| **Total** | **$690/mo** | **$683/mo** | **-1%** | ✅ |

**Status**: ✅ Met | ⚠️ At Risk | ❌ Not Met

**Notes**: [Cost trends, any unexpected expenses]

---

## Exit Criteria Summary

### Overall Status Dashboard

| Category | Criteria | Met | At Risk | Not Met | Overall |
|----------|----------|-----|---------|---------|---------|
| Stability | 5 | 5 | 0 | 0 | ✅ |
| Operational Readiness | 3 | 3 | 0 | 0 | ✅ |
| Process Maturity | 2 | 2 | 0 | 0 | ✅ |
| Documentation | 2 | 2 | 0 | 0 | ✅ |
| Business | 2 | 2 | 0 | 0 | ✅ |
| **Total** | **14** | **14** | **0** | **0** | **✅** |

**Minimum Requirements for Transition**:
- All stability criteria met for 2 consecutive weeks
- All operational readiness criteria met
- All process maturity criteria met
- All documentation criteria met
- All business criteria met
- No outstanding P0 or P1 issues
- All stakeholder approvals obtained

**Current Status**: ✅ Ready for Transition | ⚠️ Not Ready - Minor Issues | ❌ Not Ready - Major Issues

---

## Transition Plan

### Pre-Transition Activities (1 week before)

**Week Before Transition**:
1. **Final Validation** (Day 1-2)
   - Verify all exit criteria met
   - Review all documentation
   - Confirm operations team readiness
   - Check stakeholder approvals

2. **Transition Preparation** (Day 3-4)
   - Schedule transition meeting
   - Prepare handoff materials
   - Update on-call schedules
   - Notify all stakeholders

3. **Final Training** (Day 5)
   - Review any remaining questions
   - Walk through escalation scenarios
   - Confirm contact information
   - Address any last concerns

### Transition Day

**Activities**:
1. **Morning**: Final hypercare team meeting
2. **Midday**: Official handoff ceremony
3. **Afternoon**: Operations team takes primary responsibility
4. **Evening**: Engineering team on standby

**Communication**:
- Announce transition to all stakeholders
- Update status pages and documentation
- Adjust on-call schedules
- Send transition summary email

### Post-Transition Support (First 30 Days)

**Week 1-2 After Transition**:
- Engineering on-call available as escalation
- Daily 15-minute check-in calls
- Rapid response to questions
- Continue incident reviews

**Week 3-4 After Transition**:
- Engineering support as needed
- Weekly check-in calls
- Monthly operations review scheduled
- Continuous improvement planning

---

## Contingency Plan

### If Exit Criteria Not Met

**Assessment**:
1. Identify which criteria are not met
2. Determine root causes
3. Estimate time to meet criteria
4. Assess risk of extending hypercare

**Options**:

**Option 1: Extend Hypercare**
- Continue intensive support
- Address gaps systematically
- Re-evaluate weekly
- Set new target transition date

**Option 2: Partial Transition**
- Transition with engineering backup
- Extended on-call support
- More frequent check-ins
- Gradual responsibility transfer

**Option 3: Rollback Consideration**
- If stability cannot be achieved
- If costs exceed acceptable limits
- If risks too high for business
- Requires executive approval

**Decision Process**:
1. Engineering Manager assesses situation
2. Consult with Operations Lead and Product Owner
3. Present options to CTO
4. Make go/no-go decision
5. Communicate plan to all stakeholders

---

## Sign-Off

### Approval Requirements

**Technical Approval**:
- [ ] Engineering Manager: _________________ Date: _______
  - Confirms system stability and performance
  - Approves technical readiness

- [ ] Operations Lead: _________________ Date: _______
  - Confirms team readiness and confidence
  - Approves operational capability

**Business Approval**:
- [ ] Product Owner: _________________ Date: _______
  - Confirms business requirements met
  - Approves user experience

- [ ] CTO: _________________ Date: _______
  - Final approval for transition
  - Accepts residual risks

**Transition Authorization**:
☐ Approved - Proceed with transition on [Date]
☐ Conditional - Address [issues] then re-evaluate
☐ Denied - Extend hypercare, re-assess in [timeframe]

**Notes**: [Any conditions, concerns, or special instructions]
```

## Validation Checklist

- [ ] All criteria are specific and measurable
- [ ] Thresholds based on actual baselines and requirements
- [ ] Minimum duration specified (e.g., 2 consecutive weeks)
- [ ] Both quantitative and qualitative criteria included
- [ ] Operations team readiness thoroughly assessed
- [ ] Documentation completeness verified
- [ ] Stakeholder approval process defined
- [ ] Contingency plan addresses failure scenarios
- [ ] Sign-off template ready for use

## Guardrails

**Measurable not Subjective**: Use specific metrics and thresholds, not opinions

**Time-Based**: Require sustained achievement over time, not single snapshots

**Comprehensive**: Cover stability, readiness, process, documentation, and business

**Realistic**: Set achievable criteria based on actual system characteristics

**Flexible**: Allow for contingency plans if criteria cannot be met