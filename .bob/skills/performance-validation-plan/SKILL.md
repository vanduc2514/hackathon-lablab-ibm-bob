---
name: performance-validation-plan
description: Create comprehensive performance validation plan for hypercare phase. Defines metrics to track, validation procedures, comparison baselines, and reporting templates for production performance.
---

# Performance Validation Plan

You are creating a comprehensive performance validation plan for the hypercare phase. Your goal is to define specific procedures for validating that the migrated system meets performance targets under real production load.

## Objective

Produce detailed validation procedures that enable the team to systematically compare production performance against baselines, identify degradation early, and ensure the system meets all performance requirements before transitioning to standard operations.

## Required Inputs

- `optimization_report.md`: Performance baselines, optimization results, target metrics
- `design_testing_strategy.md`: Performance targets, SLA requirements
- `hypercare_monitoring_setup.md`: Monitoring dashboards and metrics
- Production environment: access to metrics, logs, APM tools
- Business requirements: acceptable performance thresholds

## Output Document

Create `hypercare_performance_validation_plan.md` containing:

**Validation Strategy**: Overall approach to performance validation during hypercare

**Baseline Metrics**: Pre-production performance baselines for comparison

**Validation Schedule**: Daily/weekly validation procedures and responsibilities

**Performance Metrics**: Specific metrics to track with acceptance criteria

**Comparison Procedures**: How to compare production vs. baseline metrics

**Degradation Response**: What to do when performance degrades

**Reporting Templates**: Daily/weekly performance reports for stakeholders

## Performance Validation Categories

### 1. Throughput Validation
- Requests per second / Transactions per minute
- Message processing rate
- Batch job completion times
- Concurrent user capacity

### 2. Latency Validation
- P50, P95, P99 response times
- End-to-end transaction times
- API endpoint latencies
- Database query performance

### 3. Resource Utilization
- CPU usage patterns
- Memory consumption and GC behavior
- Disk I/O and storage usage
- Network bandwidth utilization

### 4. Reliability Metrics
- Error rates and types
- Availability and uptime
- Circuit breaker trips
- Retry and timeout occurrences

### 5. Scalability Validation
- Performance under varying load
- Auto-scaling behavior
- Resource efficiency at scale
- Breaking point identification

## Example: Performance Validation Plan

```markdown
# Performance Validation Plan: E-Commerce Platform

## Validation Strategy

**Objective**: Validate that production performance meets or exceeds optimization targets under real user load, and identify any degradation early.

**Approach**:
- Compare production metrics to optimization baselines daily
- Track trends over hypercare period (4 weeks)
- Validate under different load patterns (normal, peak, off-peak)
- Document any deviations and investigate root causes
- Report findings to stakeholders weekly

**Success Criteria**:
- All metrics within 10% of optimization targets
- No sustained degradation over 2-week period
- Performance stable under peak load
- No critical performance issues

---

## Baseline Metrics (From Optimization Phase)

### Application Performance Baselines
| Metric | Optimization Baseline | Production Target | Acceptable Range |
|--------|----------------------|-------------------|------------------|
| Throughput | 500 req/sec | ≥ 400 req/sec | 400-600 req/sec |
| P50 Latency | 85ms | < 100ms | 70-100ms |
| P95 Latency | 247ms | < 300ms | 200-300ms |
| P99 Latency | 580ms | < 800ms | 500-800ms |
| Error Rate | 0.2% | < 0.5% | 0-0.5% |
| Availability | 99.95% | ≥ 99.9% | 99.9-100% |

### Infrastructure Baselines
| Metric | Optimization Baseline | Production Target | Acceptable Range |
|--------|----------------------|-------------------|------------------|
| CPU Usage | 45% avg | < 70% avg | 40-70% |
| Memory Usage | 1.8GB | < 2.5GB | 1.5-2.5GB |
| GC Pause Time | 95ms avg | < 150ms avg | 80-150ms |
| DB Connections | 8 avg | < 15 avg | 5-15 |

### Database Baselines
| Metric | Optimization Baseline | Production Target | Acceptable Range |
|--------|----------------------|-------------------|------------------|
| Query Time (P95) | 35ms | < 50ms | 30-50ms |
| Slow Queries | 5/min | < 20/min | 0-20/min |
| Connection Pool | 40% utilized | < 80% utilized | 30-80% |
| Replication Lag | 0.5s | < 2s | 0-2s |

---

## Validation Schedule

### Daily Validation (All Weeks)

**Time**: 9:00 AM daily
**Duration**: 30 minutes
**Responsible**: On-call engineer + Team lead
**Procedure**:

1. **Review 24-Hour Metrics** (10 minutes)
   - Open performance dashboard
   - Compare yesterday's metrics to baseline
   - Note any anomalies or trends
   - Check for alerts or incidents

2. **Analyze Key Metrics** (15 minutes)
   - Throughput: Compare to baseline and target
   - Latency: Check P50, P95, P99 percentiles
   - Error Rate: Identify error types and patterns
   - Resource Usage: CPU, memory, database connections

3. **Document Findings** (5 minutes)
   - Update daily performance log
   - Flag any concerns for investigation
   - Prepare summary for stakeholders

**Output**: Daily performance log entry

### Weekly Validation (All Weeks)

**Time**: Friday 2:00 PM
**Duration**: 1 hour
**Responsible**: Full team + Stakeholders
**Procedure**:

1. **Trend Analysis** (20 minutes)
   - Review 7-day performance trends
   - Compare week-over-week changes
   - Identify patterns (day-of-week, time-of-day)
   - Assess progress toward exit criteria

2. **Deep Dive on Issues** (20 minutes)
   - Review all incidents and degradations
   - Analyze root causes
   - Evaluate effectiveness of mitigations
   - Plan improvements if needed

3. **Stakeholder Report** (20 minutes)
   - Present weekly performance summary
   - Highlight achievements and concerns
   - Discuss any needed actions
   - Update exit criteria progress

**Output**: Weekly performance report

### Load Pattern Validation (Week 2)

**Time**: Mid-week, during peak hours
**Duration**: 4 hours
**Responsible**: Performance engineer + On-call
**Procedure**:

1. **Baseline Normal Load** (1 hour)
   - Capture metrics during typical load
   - Verify performance within targets

2. **Observe Peak Load** (2 hours)
   - Monitor during highest traffic period
   - Validate auto-scaling behavior
   - Check for any degradation
   - Verify resource headroom

3. **Analyze Results** (1 hour)
   - Compare peak vs. normal performance
   - Assess scalability and efficiency
   - Document any issues or concerns

**Output**: Load pattern validation report

---

## Validation Procedures

### Procedure 1: Daily Throughput Validation

**Objective**: Verify system handles expected load

**Steps**:
1. Open monitoring dashboard
2. Navigate to throughput metrics (last 24 hours)
3. Calculate average and peak throughput
4. Compare to baseline (500 req/sec avg, 650 req/sec peak)
5. Check for any significant drops or spikes

**Queries**:
```promql
# Average throughput (last 24h)
avg_over_time(rate(http_requests_total[5m])[24h])

# Peak throughput (last 24h)
max_over_time(rate(http_requests_total[5m])[24h])
```

**Acceptance Criteria**:
- ✅ Average throughput ≥ 400 req/sec
- ✅ Peak throughput handled without errors
- ✅ No sustained drops > 20% from baseline

**If Failed**:
- Investigate cause of throughput drop
- Check for errors, resource constraints, or external issues
- Document in daily log and escalate if persistent

---

### Procedure 2: Daily Latency Validation

**Objective**: Verify response times meet SLA

**Steps**:
1. Open APM dashboard
2. Review latency percentiles (P50, P95, P99) for last 24 hours
3. Compare to baseline (85ms, 247ms, 580ms)
4. Identify slowest endpoints
5. Check for latency spikes or trends

**Queries**:
```promql
# P50 latency (last 24h)
histogram_quantile(0.50, rate(http_request_duration_seconds_bucket[24h]))

# P95 latency (last 24h)
histogram_quantile(0.95, rate(http_request_duration_seconds_bucket[24h]))

# P99 latency (last 24h)
histogram_quantile(0.99, rate(http_request_duration_seconds_bucket[24h]))
```

**Acceptance Criteria**:
- ✅ P50 < 100ms
- ✅ P95 < 300ms
- ✅ P99 < 800ms
- ✅ No endpoints consistently > 2x baseline

**If Failed**:
- Identify slow endpoints or queries
- Check database performance
- Review recent changes
- Create performance improvement ticket

---

### Procedure 3: Error Rate Validation

**Objective**: Ensure error rate within acceptable limits

**Steps**:
1. Open error tracking dashboard
2. Calculate 24-hour error rate
3. Compare to baseline (0.2%) and target (< 0.5%)
4. Categorize errors by type (5xx, 4xx, timeouts)
5. Identify error patterns or trends

**Queries**:
```promql
# Error rate (last 24h)
sum(rate(http_requests_total{status=~"5.."}[24h])) 
/ sum(rate(http_requests_total[24h]))

# Errors by status code
sum by (status) (rate(http_requests_total{status=~"[45].."}[24h]))
```

**Acceptance Criteria**:
- ✅ Overall error rate < 0.5%
- ✅ No single error type > 0.2%
- ✅ No new error types introduced
- ✅ Error rate trending stable or down

**If Failed**:
- Investigate error root causes
- Check logs for error details
- Assess if errors are user-caused or system issues
- Escalate if error rate > 1%

---

### Procedure 4: Resource Utilization Validation

**Objective**: Verify efficient resource usage

**Steps**:
1. Open infrastructure monitoring
2. Review CPU, memory, disk, network metrics
3. Compare to baseline and capacity limits
4. Check for resource exhaustion or leaks
5. Validate auto-scaling behavior

**Queries**:
```promql
# CPU usage (last 24h)
avg_over_time(100 - (avg by (instance) (rate(node_cpu_seconds_total{mode="idle"}[5m])) * 100)[24h])

# Memory usage (last 24h)
avg_over_time(node_memory_MemTotal_bytes - node_memory_MemAvailable_bytes)[24h]

# GC pause time
rate(jvm_gc_pause_seconds_sum[24h]) / rate(jvm_gc_pause_seconds_count[24h])
```

**Acceptance Criteria**:
- ✅ CPU usage < 70% average
- ✅ Memory usage < 80% of allocated
- ✅ GC pause time < 150ms average
- ✅ No resource exhaustion events

**If Failed**:
- Identify resource bottlenecks
- Check for memory leaks or CPU hotspots
- Review auto-scaling configuration
- Consider resource optimization

---

### Procedure 5: Database Performance Validation

**Objective**: Ensure database performance optimal

**Steps**:
1. Connect to database monitoring
2. Review query performance metrics
3. Check connection pool utilization
4. Identify slow queries
5. Validate replication lag (if applicable)

**Queries**:
```sql
-- Slow queries (last 24h)
SELECT query, mean_exec_time, calls, total_exec_time
FROM pg_stat_statements
WHERE mean_exec_time > 50
ORDER BY mean_exec_time DESC
LIMIT 20;

-- Connection pool stats
SELECT count(*), state 
FROM pg_stat_activity 
GROUP BY state;

-- Replication lag
SELECT EXTRACT(EPOCH FROM (now() - pg_last_xact_replay_timestamp())) as lag_seconds;
```

**Acceptance Criteria**:
- ✅ P95 query time < 50ms
- ✅ Connection pool < 80% utilized
- ✅ Slow queries < 20/min
- ✅ Replication lag < 2 seconds

**If Failed**:
- Optimize slow queries
- Add missing indexes
- Adjust connection pool size
- Investigate replication issues

---

## Degradation Response Procedures

### Minor Degradation (10-20% from baseline)

**Actions**:
1. Document degradation in daily log
2. Monitor for 24 hours to confirm trend
3. Investigate potential causes
4. Plan optimization if persistent
5. Update stakeholders in weekly report

**Example**: P95 latency increases from 247ms to 280ms

### Moderate Degradation (20-50% from baseline)

**Actions**:
1. Create incident ticket (P2)
2. Begin immediate investigation
3. Notify team lead
4. Implement mitigation if cause identified
5. Daily updates to stakeholders
6. Plan fix for next deployment

**Example**: Throughput drops from 500 to 350 req/sec

### Severe Degradation (>50% from baseline)

**Actions**:
1. Create incident ticket (P1)
2. Engage full team for investigation
3. Notify engineering manager and stakeholders
4. Implement immediate mitigation
5. Consider rollback if not resolved quickly
6. Hourly updates to stakeholders
7. Post-incident review required

**Example**: Error rate increases from 0.2% to 3%

---

## Reporting Templates

### Daily Performance Log Entry

```markdown
## Performance Log: [Date]

**Summary**: [One-line status: Green/Yellow/Red]

### Key Metrics (24h average)
- Throughput: [value] req/sec (Target: ≥400, Baseline: 500)
- P95 Latency: [value]ms (Target: <300, Baseline: 247)
- Error Rate: [value]% (Target: <0.5, Baseline: 0.2)
- CPU Usage: [value]% (Target: <70, Baseline: 45)

### Observations
- [Notable patterns or changes]
- [Any alerts or incidents]
- [Comparison to previous day]

### Actions Taken
- [Any investigations or fixes]
- [None if all metrics normal]

### Concerns
- [Any degradation or trends to watch]
- [None if all metrics healthy]

**Status**: ✅ All metrics within targets | ⚠️ Minor concerns | 🚨 Action required
```

### Weekly Performance Report

```markdown
# Weekly Performance Report: Week [N] ([Date Range])

## Executive Summary
[2-3 sentences on overall performance status and key findings]

## Performance Trends

### Throughput
- **Average**: [value] req/sec (Baseline: 500)
- **Peak**: [value] req/sec
- **Trend**: [Stable/Improving/Degrading]
- **Status**: ✅/⚠️/🚨

### Latency
- **P50**: [value]ms (Baseline: 85ms)
- **P95**: [value]ms (Baseline: 247ms)
- **P99**: [value]ms (Baseline: 580ms)
- **Trend**: [Stable/Improving/Degrading]
- **Status**: ✅/⚠️/🚨

### Reliability
- **Error Rate**: [value]% (Baseline: 0.2%)
- **Availability**: [value]% (Target: 99.9%)
- **Incidents**: [count] ([P0/P1/P2 breakdown])
- **Status**: ✅/⚠️/🚨

### Resource Efficiency
- **CPU**: [value]% avg (Baseline: 45%)
- **Memory**: [value]GB avg (Baseline: 1.8GB)
- **Database**: [value] connections avg (Baseline: 8)
- **Status**: ✅/⚠️/🚨

## Incidents and Issues
| Date | Severity | Issue | Resolution | Status |
|------|----------|-------|------------|--------|
| [date] | P1 | [description] | [resolution] | Resolved |

## Achievements
- [Positive findings or improvements]
- [Milestones reached]

## Concerns
- [Any ongoing issues or degradation]
- [Risks to monitor]

## Next Week Focus
- [Planned validations or tests]
- [Areas requiring attention]

## Exit Criteria Progress
- [ ] Availability ≥ 99.9% for 2 weeks: [current status]
- [ ] Error rate < 0.5%: [current status]
- [ ] No P0 incidents for 1 week: [current status]
- [ ] Performance meets targets: [current status]

**Overall Status**: [On Track / At Risk / Blocked]
```

---

## Validation Checklist

### Daily Validation
- [ ] Throughput compared to baseline
- [ ] Latency percentiles within targets
- [ ] Error rate acceptable
- [ ] Resource utilization healthy
- [ ] Database performance optimal
- [ ] Daily log entry completed
- [ ] Concerns flagged for investigation

### Weekly Validation
- [ ] 7-day trends analyzed
- [ ] Week-over-week comparison done
- [ ] All incidents reviewed
- [ ] Stakeholder report prepared
- [ ] Exit criteria progress assessed
- [ ] Next week priorities identified

### Load Pattern Validation
- [ ] Normal load performance validated
- [ ] Peak load performance validated
- [ ] Auto-scaling behavior verified
- [ ] Resource headroom confirmed
- [ ] Load pattern report completed
```

## Validation Checklist

- [ ] Baseline metrics clearly documented with sources
- [ ] Validation schedule specifies who, what, when
- [ ] Procedures include specific queries and commands
- [ ] Acceptance criteria are measurable and specific
- [ ] Degradation response procedures are actionable
- [ ] Reporting templates are ready to use
- [ ] All critical performance aspects covered
- [ ] Procedures reference actual monitoring tools

## Guardrails

**Baseline-Driven**: All comparisons reference actual optimization baselines

**Measurable Criteria**: Use specific thresholds, not subjective assessments

**Actionable Procedures**: Include exact queries, commands, and steps

**Regular Cadence**: Daily and weekly validation ensures early detection

**Documented Findings**: All validations logged for trend analysis and learning