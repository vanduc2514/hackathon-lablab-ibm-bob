---
name: performance-analysis
description: Analyze system performance metrics, identify bottlenecks, and establish optimization priorities. Creates baseline measurements and profiling reports for post-migration optimization.
---

# Performance Analysis

You are analyzing the performance of a migrated system to identify optimization opportunities. Your goal is to establish baselines, identify bottlenecks, and prioritize improvements based on impact.

## Objective

Systematically profile the migrated application to understand performance characteristics, identify inefficiencies, and create a prioritized optimization roadmap.

## Required Inputs

- Validation Report: functional correctness confirmation, initial performance baselines
- Design Documents: target architecture, component strategies
- Access to: production/staging metrics, profiling tools, monitoring dashboards
- Performance targets: SLAs, throughput requirements, latency goals

## Output Document

Create `optimization_performance_analysis.md` containing:

**Performance Baseline**: Current metrics (throughput, latency percentiles, resource utilization, error rates)

**Bottleneck Analysis**: Top performance issues ranked by impact with supporting data

**Profiling Results**: CPU hotspots, memory allocation patterns, I/O wait times, database query performance

**Comparison to Targets**: Gap analysis between current performance and SLA requirements

**Optimization Priorities**: Ranked list of improvements by expected impact vs. effort

## Analysis Process

1. **Collect Baseline Metrics**: Gather current performance data from monitoring systems
2. **Profile Under Load**: Run performance tests simulating realistic usage patterns
3. **Identify Hotspots**: Use profiling tools to find CPU, memory, I/O bottlenecks
4. **Analyze Dependencies**: Examine external service calls, database queries, network latency
5. **Prioritize Issues**: Rank bottlenecks by impact on user experience and business goals
6. **Document Findings**: Create comprehensive analysis with actionable recommendations

## Key Metrics to Analyze

**Throughput**: Requests per second, transactions per minute, message processing rate

**Latency**: P50, P95, P99 response times for critical operations

**Resource Utilization**: CPU usage, memory consumption, disk I/O, network bandwidth

**Error Rates**: HTTP 5xx errors, exceptions, timeouts, circuit breaker trips

**Database Performance**: Query execution time, connection pool utilization, slow query log

**External Dependencies**: Third-party API latency, message queue lag, cache hit rates

## Example: Spring Boot Application Analysis

```
Performance Baseline (Production - 7 days):
- Throughput: 150 req/sec average, 300 req/sec peak
- Latency: P50=120ms, P95=450ms, P99=1200ms
- CPU: 65% average, 85% peak
- Memory: 2.1GB heap used (70% of 3GB allocated)
- Error Rate: 0.3% (mostly timeouts)

Bottleneck Analysis:
1. Database Query Performance (HIGH IMPACT)
   - N+1 query pattern in order retrieval: 15 queries per request
   - Missing index on user_orders.created_at: 800ms avg query time
   - Impact: Adds 400ms to P95 latency
   - Priority: P0 - Fix immediately

2. Synchronous External API Calls (MEDIUM IMPACT)
   - Payment gateway: 200ms average, blocks request thread
   - Inventory service: 150ms average, no timeout configured
   - Impact: Limits throughput, increases latency under load
   - Priority: P1 - Optimize in next sprint

3. Inefficient JSON Serialization (LOW IMPACT)
   - Jackson default settings, no custom serializers
   - Large response payloads (avg 50KB)
   - Impact: 30ms added to response time
   - Priority: P2 - Nice to have

Optimization Priorities:
1. Fix N+1 queries and add database indexes (Expected: -300ms P95)
2. Implement async processing for external calls (Expected: +50% throughput)
3. Add connection pooling tuning (Expected: -50ms P95)
4. Optimize JSON serialization (Expected: -20ms P50)
```

## Profiling Tools by Platform

**Java/JVM**: JProfiler, YourKit, VisualVM, async-profiler, JFR (Java Flight Recorder)

**Node.js**: Chrome DevTools, clinic.js, 0x, node --prof

**Python**: cProfile, py-spy, memory_profiler, line_profiler

**.NET**: dotTrace, PerfView, Visual Studio Profiler

**Database**: EXPLAIN ANALYZE, slow query log, pg_stat_statements, query execution plans

## Validation Checklist

- [ ] Baseline metrics collected from production/staging environment
- [ ] Performance profiling completed under realistic load
- [ ] Top 5-10 bottlenecks identified with supporting data
- [ ] Each bottleneck quantified (impact on latency, throughput, resources)
- [ ] Optimization priorities ranked by impact vs. effort
- [ ] Comparison to SLA targets documented
- [ ] Actionable recommendations provided for each issue

## Guardrails

**Realistic load testing**: Profile under production-like conditions, not synthetic benchmarks

**Data-driven decisions**: Use actual profiling data, not assumptions or guesses

**Holistic view**: Consider entire request path including external dependencies

**Quantify impact**: Estimate improvement potential for each optimization