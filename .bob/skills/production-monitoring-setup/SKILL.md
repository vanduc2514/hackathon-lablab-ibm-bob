---
name: production-monitoring-setup
description: Create comprehensive monitoring setup documentation for hypercare phase. Defines dashboards, alerts, metrics, thresholds, and escalation procedures for intensive post-deployment monitoring.
---

# Production Monitoring Setup

You are creating comprehensive monitoring documentation for the hypercare phase. Your goal is to define specific dashboards, alerts, metrics, thresholds, and procedures that enable the support team to proactively monitor the migrated system.

## Objective

Produce detailed monitoring setup documentation that specifies exactly what to monitor, how to configure alerts, what thresholds to use, and how to respond to monitoring signals during the critical hypercare period.

## Required Inputs

- `optimization_report.md`: Performance baselines, resource utilization patterns
- `design_target_architecture.md`: System components and architecture
- `design_testing_strategy.md`: Performance targets and SLAs
- Team structure: on-call rotation, escalation contacts
- Monitoring tools: available platforms (Datadog, New Relic, Prometheus, CloudWatch, etc.)

## Output Document

Create `hypercare_monitoring_setup.md` containing:

**Monitoring Overview**: Hypercare monitoring strategy and intensity levels by week

**Critical Metrics Dashboard**: Key metrics to track with specific thresholds and alert conditions

**Alert Configuration**: Detailed alert rules with severity levels, thresholds, and notification channels

**Monitoring Schedule**: When to review metrics (hourly, daily, weekly) and who is responsible

**Escalation Procedures**: When and how to escalate based on monitoring signals

**Tool Configuration**: Specific dashboard URLs, query examples, and setup instructions

## Monitoring Categories

### 1. Application Performance Metrics
- **Throughput**: Requests/sec, transactions/min, message processing rate
- **Latency**: P50, P95, P99 response times for critical endpoints
- **Error Rates**: HTTP 5xx errors, exceptions, timeouts
- **Availability**: Uptime percentage, health check status

### 2. Infrastructure Metrics
- **CPU Utilization**: Average, peak, per-instance
- **Memory Usage**: Heap usage, GC activity, memory leaks
- **Disk I/O**: Read/write operations, disk space
- **Network**: Bandwidth, connection counts, packet loss

### 3. Database Metrics
- **Query Performance**: Slow queries, execution time
- **Connection Pool**: Active connections, wait time, exhaustion events
- **Database Load**: CPU, memory, disk usage
- **Replication Lag**: For read replicas

### 4. External Dependencies
- **API Latency**: Third-party service response times
- **Circuit Breaker**: Trip events, failure rates
- **Message Queue**: Queue depth, processing lag, dead letters
- **Cache**: Hit rate, eviction rate, memory usage

### 5. Business Metrics
- **User Activity**: Active users, session counts
- **Transaction Success**: Successful vs. failed transactions
- **Critical Workflows**: Key business process completion rates

## Alert Severity Levels

**P0 (Critical)**: System down or major functionality broken
- Response: Immediate (< 15 minutes)
- Notification: Page on-call engineer, notify leadership
- Examples: Service unavailable, data loss, security breach

**P1 (High)**: Significant degradation affecting users
- Response: Within 30 minutes
- Notification: Alert on-call engineer, notify team lead
- Examples: High error rate (>5%), severe latency (>2x baseline)

**P2 (Medium)**: Performance degradation or minor issues
- Response: Within 2 hours
- Notification: Alert on-call engineer
- Examples: Elevated error rate (1-5%), moderate latency increase

**P3 (Low)**: Warning signals or trends
- Response: Next business day
- Notification: Log for review
- Examples: Resource utilization trending up, cache hit rate declining

## Example: Spring Boot Application Monitoring

```markdown
# Hypercare Monitoring Setup: E-Commerce Platform

## Monitoring Strategy

**Week 1 (Critical Period)**
- 24/7 monitoring with 15-minute alert response SLA
- Hourly metric reviews by on-call engineer
- Daily team sync to review trends
- Real-time dashboard displayed in team area

**Week 2-4 (Stabilization)**
- Extended hours monitoring (6am-10pm)
- 4-hour metric reviews
- Twice-daily team syncs
- Weekly trend analysis

**Week 4+ (Transition)**
- Business hours monitoring
- Daily metric reviews
- Daily team syncs
- Bi-weekly trend analysis

---

## Critical Metrics Dashboard

### Application Performance
| Metric | Target | Warning Threshold | Critical Threshold | Alert |
|--------|--------|-------------------|-------------------|-------|
| Throughput | 400 req/sec | < 300 req/sec | < 200 req/sec | P1 |
| P95 Latency | < 300ms | > 400ms | > 600ms | P1 |
| P99 Latency | < 800ms | > 1000ms | > 1500ms | P2 |
| Error Rate | < 0.5% | > 1% | > 5% | P0 |
| Availability | 99.9% | < 99.5% | < 99% | P0 |

### Infrastructure
| Metric | Target | Warning Threshold | Critical Threshold | Alert |
|--------|--------|-------------------|-------------------|-------|
| CPU Usage | < 70% | > 80% | > 90% | P1 |
| Memory Usage | < 75% | > 85% | > 95% | P0 |
| Disk Space | > 30% free | < 20% free | < 10% free | P1 |
| GC Pause Time | < 100ms | > 200ms | > 500ms | P2 |

### Database
| Metric | Target | Warning Threshold | Critical Threshold | Alert |
|--------|--------|-------------------|-------------------|-------|
| Query Time (P95) | < 50ms | > 100ms | > 200ms | P1 |
| Connection Pool | < 80% | > 90% | 100% (exhausted) | P0 |
| Slow Queries | < 10/min | > 50/min | > 100/min | P2 |
| Replication Lag | < 1s | > 5s | > 30s | P1 |

---

## Alert Configuration

### Datadog Alert Rules

**Critical: Service Unavailable**
```
Alert: avg(last_5m):avg:service.health{env:prod} < 1
Message: @pagerduty-critical @slack-incidents
Service health check failing for 5 minutes
Runbook: https://wiki.company.com/runbooks/service-down
```

**High: Error Rate Spike**
```
Alert: avg(last_10m):sum:http.errors{status:5xx,env:prod}.as_rate() > 0.05
Message: @pagerduty-high @slack-alerts
Error rate above 5% for 10 minutes
Current: {{value}}%
Runbook: https://wiki.company.com/runbooks/error-spike
```

**High: Latency Degradation**
```
Alert: avg(last_15m):p95:http.request.duration{env:prod} > 600
Message: @pagerduty-high @slack-alerts
P95 latency above 600ms for 15 minutes
Current: {{value}}ms
Runbook: https://wiki.company.com/runbooks/latency-issues
```

**Critical: Database Connection Pool Exhausted**
```
Alert: avg(last_5m):avg:db.pool.active{env:prod} / avg:db.pool.max{env:prod} >= 1
Message: @pagerduty-critical @slack-incidents
Database connection pool exhausted
Runbook: https://wiki.company.com/runbooks/db-pool-exhaustion
```

---

## Monitoring Schedule

### Hourly Reviews (Week 1)
**Who**: On-call engineer
**What**: Check critical metrics dashboard
**Action**: 
- Verify all metrics within normal ranges
- Note any trends or anomalies
- Update incident log if issues detected

### Daily Reviews (All Weeks)
**Who**: On-call engineer + team lead
**What**: Review 24-hour trends
**Action**:
- Compare to previous day and baseline
- Identify patterns or degradation
- Update stakeholders on status
- Plan any needed interventions

### Weekly Reviews (Week 2+)
**Who**: Full team + stakeholders
**What**: Comprehensive trend analysis
**Action**:
- Review all incidents and resolutions
- Analyze performance trends
- Assess progress toward exit criteria
- Adjust monitoring or procedures as needed

---

## Escalation Procedures

### Level 1: On-Call Engineer
**Triggers**: P2, P3 alerts
**Response**: Investigate and resolve or escalate
**Timeframe**: Within 2 hours

### Level 2: Team Lead
**Triggers**: P1 alerts, unresolved P2 after 2 hours
**Response**: Coordinate resolution, engage additional resources
**Timeframe**: Within 30 minutes

### Level 3: Engineering Manager + Product Owner
**Triggers**: P0 alerts, multiple P1 incidents, rollback consideration
**Response**: Make go/no-go decisions, authorize rollback
**Timeframe**: Within 15 minutes

### Level 4: Executive Leadership
**Triggers**: Extended outage (>1 hour), data loss, security incident
**Response**: Business continuity decisions, external communication
**Timeframe**: Immediate notification

---

## Tool Configuration

### Datadog Dashboard
**URL**: https://app.datadoghq.com/dashboard/abc-123-hypercare
**Widgets**:
- Timeseries: Request rate, error rate, latency (P50/P95/P99)
- Heatmap: Response time distribution
- Query value: Current error rate, availability
- Toplist: Slowest endpoints, most common errors
- Logs stream: Recent errors and warnings

### Prometheus Queries
```promql
# Request rate
rate(http_requests_total{job="app",env="prod"}[5m])

# Error rate
rate(http_requests_total{job="app",env="prod",status=~"5.."}[5m]) 
/ rate(http_requests_total{job="app",env="prod"}[5m])

# P95 latency
histogram_quantile(0.95, rate(http_request_duration_seconds_bucket[5m]))

# CPU usage
100 - (avg by (instance) (rate(node_cpu_seconds_total{mode="idle"}[5m])) * 100)
```

### CloudWatch Alarms
- **HighErrorRate**: ErrorCount > 50 for 2 datapoints within 10 minutes
- **HighLatency**: P95Latency > 600ms for 3 datapoints within 15 minutes
- **LowAvailability**: HealthCheckStatus < 1 for 2 datapoints within 5 minutes

---

## Monitoring Checklist

- [ ] All dashboards created and accessible to team
- [ ] Alert rules configured with correct thresholds
- [ ] Notification channels tested (PagerDuty, Slack, email)
- [ ] On-call rotation schedule published
- [ ] Escalation contacts verified and available
- [ ] Runbook links added to all alerts
- [ ] Team trained on dashboard interpretation
- [ ] Baseline metrics documented for comparison
- [ ] Monitoring schedule communicated to all stakeholders
```

## Validation Checklist

- [ ] Specific metrics defined with exact thresholds
- [ ] Alert severity levels clearly defined
- [ ] Escalation procedures include contacts and timeframes
- [ ] Monitoring schedule specifies who, what, when
- [ ] Tool-specific configuration provided (queries, dashboards)
- [ ] All critical system components covered
- [ ] Baseline metrics from optimization phase referenced
- [ ] Runbook links or procedures included for each alert

## Guardrails

**Specific not Generic**: Include actual metric names, thresholds, tool configurations

**Actionable not Theoretical**: Provide exact queries, dashboard URLs, alert rules

**Complete Coverage**: Monitor all critical paths including dependencies

**Realistic Thresholds**: Base on actual baselines from optimization phase, not arbitrary numbers