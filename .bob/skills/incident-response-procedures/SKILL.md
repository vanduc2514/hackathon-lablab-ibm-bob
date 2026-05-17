---
name: incident-response-procedures
description: Create comprehensive incident response runbooks for hypercare phase. Defines incident classification, triage steps, diagnostic procedures, escalation paths, and resolution workflows.
---

# Incident Response Procedures

You are creating comprehensive incident response documentation for the hypercare phase. Your goal is to define clear procedures for classifying, triaging, diagnosing, and resolving incidents that occur during the critical post-deployment period.

## Objective

Produce detailed incident response runbooks that enable the support team to quickly and effectively handle production issues, minimize impact, and ensure proper documentation and learning from each incident.

## Required Inputs

- `optimization_report.md`: System characteristics, known issues, optimization changes
- `design_target_architecture.md`: System components, dependencies, integration points
- `hypercare_monitoring_setup.md`: Alert definitions and monitoring thresholds
- Deployment artifacts: rollback procedures, configuration management
- Team contacts: on-call rotation, escalation paths, vendor support

## Output Document

Create `hypercare_incident_response_procedures.md` containing:

**Incident Classification**: Severity levels (P0-P3) with clear definitions and examples

**Triage Procedures**: Initial assessment steps to classify and prioritize incidents

**Diagnostic Runbooks**: Step-by-step troubleshooting guides for common issues

**Escalation Matrix**: When and how to escalate based on severity and duration

**Communication Templates**: Status updates, incident notifications, post-mortem formats

**Rollback Procedures**: When and how to execute rollback to previous version

**Post-Incident Process**: Documentation requirements, root cause analysis, preventive actions

## Incident Severity Definitions

### P0 - Critical (System Down)
**Definition**: Complete service outage or data loss affecting all users
**Response Time**: Immediate (< 15 minutes)
**Examples**:
- Application completely unavailable
- Database corruption or data loss
- Security breach or data exposure
- Payment processing completely broken

**Actions**:
- Page on-call engineer immediately
- Notify team lead and engineering manager
- Start incident bridge/war room
- Consider immediate rollback
- Update status page every 15 minutes

### P1 - High (Major Degradation)
**Definition**: Significant functionality broken or severe performance degradation affecting many users
**Response Time**: Within 30 minutes
**Examples**:
- Critical feature unavailable (checkout, login)
- Error rate > 5%
- Latency > 3x baseline
- Database connection pool exhausted

**Actions**:
- Alert on-call engineer
- Notify team lead
- Begin investigation and mitigation
- Update stakeholders every 30 minutes
- Prepare rollback if not resolved in 1 hour

### P2 - Medium (Partial Degradation)
**Definition**: Non-critical functionality affected or moderate performance issues
**Response Time**: Within 2 hours
**Examples**:
- Error rate 1-5%
- Latency 1.5-3x baseline
- Non-critical feature broken
- Elevated resource utilization

**Actions**:
- Alert on-call engineer
- Investigate and document
- Update team lead hourly
- Plan fix for next deployment window

### P3 - Low (Warning/Trend)
**Definition**: Minor issues or concerning trends that don't immediately impact users
**Response Time**: Next business day
**Examples**:
- Error rate 0.5-1%
- Resource utilization trending up
- Cache hit rate declining
- Slow query count increasing

**Actions**:
- Log for review
- Monitor trend
- Plan optimization or fix
- Include in daily standup

## Example: Incident Response Runbooks

```markdown
# Incident Response Procedures: E-Commerce Platform

## Quick Reference

| Severity | Response Time | Notification | Rollback Threshold |
|----------|--------------|--------------|-------------------|
| P0 | < 15 min | Page + War Room | Consider immediately |
| P1 | < 30 min | Alert + Team Lead | If not resolved in 1 hour |
| P2 | < 2 hours | Alert | If pattern persists |
| P3 | Next day | Log | Not applicable |

---

## Incident Triage Process

### Step 1: Detect and Classify (5 minutes)
1. **Receive Alert**: Via PagerDuty, monitoring dashboard, or user report
2. **Verify Impact**: Check monitoring dashboards to confirm issue
3. **Assess Scope**: Determine how many users/transactions affected
4. **Classify Severity**: Use definitions above to assign P0-P3
5. **Create Incident Ticket**: Log in incident management system

### Step 2: Initial Response (10 minutes)
1. **Acknowledge Alert**: Confirm you're responding
2. **Notify Stakeholders**: Based on severity level
3. **Start Investigation**: Begin diagnostic procedures
4. **Update Status**: Initial communication to stakeholders
5. **Gather Context**: Recent deployments, changes, similar past incidents

### Step 3: Diagnose and Mitigate (varies)
1. **Follow Runbook**: Use specific runbook for issue type
2. **Collect Evidence**: Logs, metrics, traces, error messages
3. **Identify Root Cause**: Or at least proximate cause
4. **Implement Fix**: Apply mitigation or workaround
5. **Verify Resolution**: Confirm metrics return to normal

### Step 4: Post-Incident (within 24 hours)
1. **Document Incident**: Complete incident report
2. **Root Cause Analysis**: If not done during incident
3. **Identify Preventive Actions**: How to avoid recurrence
4. **Update Runbooks**: Add learnings to procedures
5. **Team Review**: Discuss in next standup or retrospective

---

## Diagnostic Runbooks

### Runbook 1: High Error Rate (5xx Errors)

**Symptoms**: Error rate > 1%, HTTP 5xx responses increasing

**Quick Checks** (5 minutes):
```bash
# Check application logs for errors
kubectl logs -n prod deployment/app --tail=100 | grep ERROR

# Check recent deployments
kubectl rollout history deployment/app -n prod

# Check resource utilization
kubectl top pods -n prod

# Check database connectivity
psql -h db.prod.company.com -U app -c "SELECT 1"
```

**Common Causes**:
1. **Database Connection Pool Exhausted**
   - Symptom: "Connection timeout" errors in logs
   - Check: `SELECT count(*) FROM pg_stat_activity WHERE state = 'active'`
   - Fix: Increase pool size or restart app to reset connections
   
2. **Memory Leak / OOM**
   - Symptom: Pods restarting, heap usage at 100%
   - Check: `kubectl describe pod <pod-name>` for OOMKilled
   - Fix: Increase memory limit or restart pods
   
3. **External Service Timeout**
   - Symptom: Timeout errors for specific API calls
   - Check: Circuit breaker metrics, external service status
   - Fix: Increase timeout or enable fallback behavior

**Escalation**: If not resolved in 30 minutes, escalate to P1 and engage team lead

---

### Runbook 2: High Latency

**Symptoms**: P95 latency > 600ms, slow response times

**Quick Checks** (5 minutes):
```bash
# Check slow queries
SELECT query, mean_exec_time, calls 
FROM pg_stat_statements 
ORDER BY mean_exec_time DESC 
LIMIT 10;

# Check application thread dumps
jstack <pid> > thread-dump.txt

# Check CPU and memory
top -b -n 1 | head -20

# Check network latency to dependencies
ping -c 5 db.prod.company.com
curl -w "@curl-format.txt" -o /dev/null -s https://api.external.com/health
```

**Common Causes**:
1. **Database Query Performance**
   - Symptom: Slow queries in pg_stat_statements
   - Check: EXPLAIN ANALYZE on slow queries
   - Fix: Add missing indexes, optimize queries
   
2. **N+1 Query Problem**
   - Symptom: Many small queries for same data
   - Check: Application logs showing repeated queries
   - Fix: Enable query batching or eager loading
   
3. **External API Latency**
   - Symptom: Slow response from third-party services
   - Check: APM traces showing external call duration
   - Fix: Implement caching or async processing

**Escalation**: If latency > 1000ms for 15 minutes, escalate to P1

---

### Runbook 3: Service Unavailable

**Symptoms**: Health check failing, 503 responses, service unreachable

**Quick Checks** (2 minutes):
```bash
# Check pod status
kubectl get pods -n prod

# Check service endpoints
kubectl get endpoints -n prod

# Check ingress/load balancer
kubectl describe ingress app-ingress -n prod

# Test direct pod access
kubectl port-forward pod/<pod-name> 8080:8080
curl http://localhost:8080/health
```

**Common Causes**:
1. **All Pods Crashed**
   - Symptom: No pods in Running state
   - Check: `kubectl describe pod` for crash reason
   - Fix: Rollback deployment or fix configuration
   
2. **Load Balancer Misconfiguration**
   - Symptom: Pods running but unreachable
   - Check: Ingress rules, service selectors
   - Fix: Correct configuration and redeploy
   
3. **Database Unavailable**
   - Symptom: App can't connect to database
   - Check: Database status, network connectivity
   - Fix: Restore database or fix network issue

**Escalation**: P0 - Immediate war room, consider rollback

---

### Runbook 4: Database Connection Pool Exhausted

**Symptoms**: "Connection timeout" errors, pool at 100% utilization

**Quick Checks** (3 minutes):
```bash
# Check active connections
SELECT count(*), state FROM pg_stat_activity GROUP BY state;

# Check long-running queries
SELECT pid, now() - query_start as duration, query 
FROM pg_stat_activity 
WHERE state = 'active' 
ORDER BY duration DESC;

# Check application pool metrics
curl http://localhost:8080/actuator/metrics/hikaricp.connections.active
```

**Immediate Actions**:
1. **Kill Long-Running Queries**
   ```sql
   SELECT pg_terminate_backend(pid) 
   FROM pg_stat_activity 
   WHERE state = 'active' 
   AND now() - query_start > interval '5 minutes';
   ```

2. **Increase Pool Size** (temporary)
   ```bash
   kubectl set env deployment/app -n prod \
     SPRING_DATASOURCE_HIKARI_MAXIMUM_POOL_SIZE=30
   ```

3. **Restart Application** (if pool corrupted)
   ```bash
   kubectl rollout restart deployment/app -n prod
   ```

**Root Cause Investigation**:
- Check for connection leaks in application code
- Review recent code changes affecting database access
- Analyze query patterns for inefficiencies

---

## Escalation Matrix

### When to Escalate

**To Team Lead**:
- P1 incident not resolved in 30 minutes
- P2 incident not resolved in 2 hours
- Multiple P2 incidents occurring simultaneously
- Uncertainty about best course of action

**To Engineering Manager**:
- P0 incident
- P1 incident not resolved in 1 hour
- Rollback decision needed
- Multiple incidents indicating systemic issue

**To Product Owner**:
- Feature needs to be disabled
- User communication required
- Business impact assessment needed

**To Executive Leadership**:
- Outage > 1 hour
- Data loss or security breach
- Regulatory compliance issue
- Major customer impact

### Escalation Contacts

| Role | Name | Phone | Slack | Email |
|------|------|-------|-------|-------|
| On-Call Engineer | Rotation | PagerDuty | @oncall | oncall@company.com |
| Team Lead | Jane Smith | +1-555-0101 | @jane | jane@company.com |
| Engineering Manager | Bob Johnson | +1-555-0102 | @bob | bob@company.com |
| Product Owner | Alice Chen | +1-555-0103 | @alice | alice@company.com |
| CTO | David Lee | +1-555-0104 | @david | david@company.com |

---

## Communication Templates

### Initial Incident Notification (P0/P1)

```
🚨 INCIDENT ALERT - [P0/P1]

Title: [Brief description]
Status: Investigating
Impact: [Number of users/transactions affected]
Started: [Timestamp]
ETA: [Estimated resolution time or "Unknown"]

Current Actions:
- [Action 1]
- [Action 2]

Next Update: [Time]

Incident Commander: [Name]
War Room: [Link if applicable]
```

### Status Update (Every 15-30 minutes)

```
📊 INCIDENT UPDATE - [P0/P1]

Title: [Brief description]
Status: [Investigating/Mitigating/Resolved]
Duration: [Time since start]

Progress:
- [What we've learned]
- [What we've tried]
- [Current theory]

Next Steps:
- [Planned action 1]
- [Planned action 2]

Next Update: [Time]
```

### Resolution Notification

```
✅ INCIDENT RESOLVED - [P0/P1]

Title: [Brief description]
Duration: [Total time]
Impact: [Final assessment]

Root Cause: [Brief explanation]

Resolution:
- [Action taken]

Preventive Actions:
- [What we'll do to prevent recurrence]

Post-Mortem: [Link to detailed analysis]
```

---

## Rollback Procedures

### When to Rollback

**Immediate Rollback** (P0):
- Complete service outage
- Data corruption detected
- Security vulnerability exploited
- Critical functionality completely broken

**Consider Rollback** (P1):
- Issue not resolved within 1 hour
- Multiple failed mitigation attempts
- Impact spreading to more users
- No clear path to resolution

**Do Not Rollback** (P2/P3):
- Minor issues with known workaround
- Issue isolated to non-critical feature
- Fix in progress and showing promise

### Rollback Execution

**Step 1: Decision** (5 minutes)
- Confirm rollback decision with Engineering Manager
- Notify all stakeholders
- Prepare rollback command

**Step 2: Execute** (10 minutes)
```bash
# Kubernetes rollback
kubectl rollout undo deployment/app -n prod

# Verify rollback
kubectl rollout status deployment/app -n prod

# Check previous version running
kubectl get pods -n prod -o wide
```

**Step 3: Validate** (15 minutes)
- Verify health checks passing
- Check error rate returned to normal
- Confirm latency acceptable
- Test critical user workflows

**Step 4: Communicate**
- Notify stakeholders of rollback completion
- Update status page
- Schedule post-mortem

---

## Post-Incident Process

### Incident Report Template

```markdown
# Incident Report: [Title]

## Summary
- **Incident ID**: INC-2024-001
- **Severity**: P1
- **Duration**: 45 minutes
- **Impact**: 15% of users experienced errors
- **Detected**: 2024-01-15 14:23 UTC
- **Resolved**: 2024-01-15 15:08 UTC

## Timeline
- 14:23 - Alert triggered: High error rate
- 14:25 - On-call engineer acknowledged
- 14:30 - Root cause identified: Database connection pool exhausted
- 14:35 - Mitigation applied: Increased pool size
- 14:45 - Metrics returning to normal
- 15:08 - Incident closed

## Root Cause
Database connection pool size (20) insufficient for traffic spike (3x normal).
Recent optimization reduced pool size without considering peak load scenarios.

## Resolution
1. Increased pool size from 20 to 30
2. Restarted application to reset connections
3. Verified metrics returned to baseline

## Impact Assessment
- 15% of requests failed (error rate 15%)
- ~500 users affected
- No data loss
- Revenue impact: ~$2,000 in failed transactions

## Preventive Actions
1. Implement auto-scaling for connection pool based on load
2. Add alerting for pool utilization > 80%
3. Update capacity planning to include peak load scenarios
4. Add connection pool metrics to hypercare dashboard

## Lessons Learned
- Pool sizing needs to account for traffic spikes, not just average load
- Need better visibility into connection pool metrics
- Optimization changes should be validated under peak load
```

### Root Cause Analysis (5 Whys)

**Problem**: Database connection pool exhausted

1. **Why?** Pool size (20) too small for traffic volume
2. **Why?** Recent optimization reduced pool size without load testing
3. **Why?** Load testing didn't include peak traffic scenarios
4. **Why?** Peak traffic patterns not documented in test plan
5. **Why?** Capacity planning didn't account for traffic variability

**Root Cause**: Inadequate capacity planning and load testing

**Preventive Action**: Update testing strategy to include peak load scenarios and document traffic patterns

---

## Incident Response Checklist

### During Incident
- [ ] Incident classified and logged
- [ ] Appropriate stakeholders notified
- [ ] Initial investigation started within SLA
- [ ] Status updates provided on schedule
- [ ] Mitigation or resolution implemented
- [ ] Resolution verified with monitoring
- [ ] Stakeholders notified of resolution

### Post-Incident
- [ ] Incident report completed within 24 hours
- [ ] Root cause analysis performed
- [ ] Preventive actions identified
- [ ] Runbooks updated with learnings
- [ ] Team retrospective scheduled
- [ ] Monitoring/alerting adjusted if needed
```

## Validation Checklist

- [ ] Severity levels clearly defined with examples
- [ ] Triage process provides step-by-step guidance
- [ ] Diagnostic runbooks cover common failure scenarios
- [ ] Escalation matrix includes specific contacts and criteria
- [ ] Communication templates ready to use
- [ ] Rollback procedures tested and validated
- [ ] Post-incident process ensures learning and improvement
- [ ] All procedures include specific commands and tools

## Guardrails

**Specific not Generic**: Include actual commands, queries, tool names

**Actionable not Theoretical**: Provide step-by-step procedures anyone can follow

**Complete Coverage**: Address all likely failure scenarios based on architecture

**Tested Procedures**: Validate rollback and diagnostic procedures before deployment