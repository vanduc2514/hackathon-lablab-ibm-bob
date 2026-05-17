---
name: configuration-tuning
description: Optimize application and infrastructure configurations for target platform. Tunes JVM parameters, connection pools, caching, timeouts, and framework settings.
---

# Configuration Tuning

You are tuning application and infrastructure configurations to optimize performance and resource utilization. Your goal is to adjust settings that maximize efficiency without requiring code changes.

## Objective

Optimize configuration parameters across the application stack—JVM, framework, database, caching, networking—to achieve better performance, stability, and resource efficiency.

## Required Inputs

- `optimization_performance_analysis.md`: Performance bottlenecks and resource patterns
- `optimization_code_improvements.md`: Code-level changes already implemented
- Current configurations: application.properties, JVM args, database settings
- Platform documentation: recommended settings and best practices

## Output Document

Create `optimization_configuration_changes.md` containing:

**Configuration Changes**: Detailed list of parameter adjustments with rationale

**Before/After Settings**: Side-by-side comparison of old and new values

**Expected Impact**: Performance improvements anticipated from each change

**Tuning Methodology**: How optimal values were determined (testing, calculation, best practices)

**Rollback Instructions**: How to revert each configuration change

## Configuration Categories

### 1. JVM Tuning (Java Applications)
- Heap size and garbage collection settings
- Thread pool configurations
- JIT compiler options
- Memory management parameters

### 2. Application Framework Settings
- Connection pool sizes and timeouts
- Thread pool configurations
- Request/response buffer sizes
- Session management settings

### 3. Database Configuration
- Connection pool parameters
- Query cache settings
- Buffer pool sizes
- Timeout configurations

### 4. Caching Configuration
- Cache sizes and eviction policies
- TTL (time-to-live) settings
- Distributed cache settings
- Cache warming strategies

### 5. Network & Timeout Settings
- HTTP client timeouts
- Keep-alive settings
- Circuit breaker thresholds
- Retry policies

## Tuning Process

1. **Identify Parameters**: List configuration options that affect performance
2. **Research Best Practices**: Review platform documentation and recommendations
3. **Calculate Optimal Values**: Use formulas or load testing to determine settings
4. **Apply Changes**: Update configurations incrementally
5. **Measure Impact**: Benchmark before and after each change
6. **Fine-Tune**: Adjust based on observed behavior
7. **Document Settings**: Record final values and rationale

## Example: Spring Boot Application Tuning

```
JVM Configuration Tuning:

Before:
java -Xms1g -Xmx2g -jar application.jar

After:
java -Xms2g -Xmx2g \
  -XX:+UseG1GC \
  -XX:MaxGCPauseMillis=200 \
  -XX:+UseStringDeduplication \
  -XX:+ParallelRefProcEnabled \
  -jar application.jar

Rationale:
- Set Xms = Xmx to avoid heap resizing overhead
- G1GC better for large heaps and low-latency requirements
- MaxGCPauseMillis=200 targets sub-200ms pause times
- String deduplication reduces memory for duplicate strings

Expected Impact: -15% GC pause time, -10% memory usage

---

Database Connection Pool Tuning:

Before (application.yml):
spring:
  datasource:
    hikari:
      maximum-pool-size: 10
      minimum-idle: 5
      connection-timeout: 30000

After:
spring:
  datasource:
    hikari:
      maximum-pool-size: 20
      minimum-idle: 10
      connection-timeout: 20000
      idle-timeout: 300000
      max-lifetime: 1800000
      leak-detection-threshold: 60000

Rationale:
- Increased pool size based on concurrent request analysis (avg 15 concurrent)
- Reduced connection-timeout to fail fast
- Added leak detection to identify connection leaks
- Set max-lifetime to handle database connection recycling

Expected Impact: -30% connection wait time, better connection management

---

Caching Configuration:

Before:
@EnableCaching
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("products");
    }
}

After:
@EnableCaching
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("products", "users");
        cacheManager.setCaffeine(Caffeine.newBuilder()
            .maximumSize(10000)
            .expireAfterWrite(Duration.ofMinutes(30))
            .recordStats());
        return cacheManager;
    }
}

Rationale:
- Caffeine provides better performance than ConcurrentHashMap
- Size limit prevents unbounded memory growth
- TTL ensures stale data is refreshed
- Stats recording enables cache monitoring

Expected Impact: +40% cache hit rate, controlled memory usage

---

Thread Pool Configuration:

Before:
server:
  tomcat:
    threads:
      max: 200
      min-spare: 10

After:
server:
  tomcat:
    threads:
      max: 400
      min-spare: 50
    accept-count: 100
    max-connections: 10000
    connection-timeout: 20000

Rationale:
- Increased max threads to handle higher concurrency (virtual threads not yet adopted)
- Higher min-spare reduces thread creation overhead
- Accept-count queue prevents connection rejection during spikes
- Connection timeout prevents hung connections

Expected Impact: +50% throughput under load, better spike handling
```

## Platform-Specific Tuning

**Java/Spring Boot**: JVM GC, Hikari connection pool, Tomcat/Undertow settings

**Node.js**: Event loop tuning, cluster mode, V8 heap settings

**Python**: WSGI workers, connection pools, async settings

**.NET**: Thread pool, GC settings, Kestrel configuration

**Databases**: PostgreSQL shared_buffers, MySQL innodb_buffer_pool_size, connection limits

## Calculation Formulas

**Connection Pool Size**: 
```
pool_size = (core_count * 2) + effective_spindle_count
For cloud/SSD: pool_size = core_count * 4
```

**Thread Pool Size**:
```
For CPU-bound: threads = core_count + 1
For I/O-bound: threads = core_count * (1 + wait_time/service_time)
```

**Heap Size (Java)**:
```
Xmx = (available_memory * 0.75) - (non_heap_memory)
Typical: 50-75% of container memory
```

## Validation Checklist

- [ ] Each configuration change documented with rationale
- [ ] Before/after values clearly specified
- [ ] Expected performance impact estimated
- [ ] Changes tested in non-production environment first
- [ ] Monitoring alerts updated for new thresholds
- [ ] Rollback procedure documented
- [ ] Configuration changes version controlled

## Guardrails

**Test first**: Always validate in staging before production

**Incremental changes**: Tune one parameter at a time to isolate impact

**Monitor closely**: Watch metrics after each configuration change

**Document everything**: Record why each setting was chosen

**Follow platform guidance**: Start with vendor recommendations, then fine-tune