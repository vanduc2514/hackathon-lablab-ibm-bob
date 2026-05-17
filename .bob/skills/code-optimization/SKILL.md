---
name: code-optimization
description: Optimize application code to leverage target platform capabilities and improve performance. Implements caching, async patterns, efficient algorithms, and platform-specific features.
---

# Code Optimization

You are optimizing application code to improve performance while maintaining functionality. Your goal is to leverage target platform features, implement efficient patterns, and eliminate performance bottlenecks identified in analysis.

## Objective

Apply code-level optimizations that improve throughput, reduce latency, and better utilize system resources without introducing bugs or breaking changes.

## Required Inputs

- `optimization_performance_analysis.md`: Identified bottlenecks and priorities
- Design Documents: target architecture, component strategies
- Source code: access to application codebase
- Performance targets: specific improvement goals

## Output Document

Create `optimization_code_improvements.md` containing:

**Optimization Summary**: Overview of code changes made and expected impact

**Implemented Optimizations**: Detailed list of changes with before/after code snippets

**Platform Features Leveraged**: New capabilities utilized (e.g., virtual threads, reactive streams)

**Performance Impact**: Measured improvements from benchmarks

**Rollback Procedures**: How to revert each optimization if issues arise

## Optimization Categories

### 1. Algorithm & Data Structure Improvements
- Replace inefficient algorithms with better alternatives
- Use appropriate data structures (HashMap vs TreeMap, ArrayList vs LinkedList)
- Eliminate unnecessary iterations or nested loops
- Implement lazy evaluation where appropriate

### 2. Database Query Optimization
- Fix N+1 query problems with batch loading or joins
- Add strategic indexes based on query patterns
- Implement query result caching
- Use database-specific optimizations (CTEs, window functions)

### 3. Caching Strategies
- Application-level caching for expensive computations
- HTTP response caching with appropriate headers
- Distributed caching for shared data (Redis, Memcached)
- Cache invalidation strategies to maintain consistency

### 4. Asynchronous Processing
- Convert blocking I/O to non-blocking where beneficial
- Implement async patterns for external service calls
- Use message queues for background processing
- Leverage platform async capabilities (CompletableFuture, reactive streams)

### 5. Platform-Specific Features
- Java 21: Virtual threads for high-concurrency I/O
- Spring Boot 3: Native compilation, improved startup time
- Modern frameworks: Built-in connection pooling, reactive support
- Language features: Pattern matching, records, sealed classes

## Optimization Process

1. **Select Target**: Choose highest-priority bottleneck from analysis
2. **Design Solution**: Plan optimization approach with minimal risk
3. **Implement Change**: Apply optimization incrementally
4. **Measure Impact**: Run benchmarks to quantify improvement
5. **Validate Correctness**: Ensure no functional regressions
6. **Document Change**: Record what, why, and measured impact
7. **Repeat**: Move to next priority item

## Example: Java Spring Boot Optimizations

```
Optimization 1: Fix N+1 Query Problem
Priority: P0 (High Impact)

Before:
public List<OrderDTO> getOrders(Long userId) {
    List<Order> orders = orderRepository.findByUserId(userId);
    return orders.stream()
        .map(order -> {
            List<OrderItem> items = itemRepository.findByOrderId(order.getId());
            return new OrderDTO(order, items);
        })
        .collect(Collectors.toList());
}
// Result: 1 query for orders + N queries for items = N+1 queries

After:
public List<OrderDTO> getOrders(Long userId) {
    List<Order> orders = orderRepository.findByUserIdWithItems(userId);
    return orders.stream()
        .map(order -> new OrderDTO(order, order.getItems()))
        .collect(Collectors.toList());
}

@Query("SELECT o FROM Order o LEFT JOIN FETCH o.items WHERE o.userId = :userId")
List<Order> findByUserIdWithItems(@Param("userId") Long userId);
// Result: 1 query with JOIN

Impact: P95 latency reduced from 450ms to 150ms (-67%)

---

Optimization 2: Leverage Java 21 Virtual Threads
Priority: P1 (Medium Impact)

Before:
@Bean
public Executor taskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(50);
    executor.setMaxPoolSize(100);
    return executor;
}

After:
@Bean
public Executor taskExecutor() {
    return Executors.newVirtualThreadPerTaskExecutor();
}

@Async
public CompletableFuture<PaymentResult> processPayment(Order order) {
    // Now runs on virtual thread - scales to thousands of concurrent operations
    return paymentGateway.charge(order);
}

Impact: Throughput increased from 300 to 500 req/sec (+67%)

---

Optimization 3: Implement Response Caching
Priority: P2 (Low Impact)

Before:
@GetMapping("/products/{id}")
public Product getProduct(@PathVariable Long id) {
    return productService.findById(id);
}

After:
@GetMapping("/products/{id}")
@Cacheable(value = "products", key = "#id")
public Product getProduct(@PathVariable Long id) {
    return productService.findById(id);
}

@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("products");
    }
}

Impact: P50 latency reduced from 120ms to 15ms for cached responses (-87%)
```

## Best Practices

**Measure before and after**: Always benchmark to confirm improvements

**One change at a time**: Isolate optimizations to understand individual impact

**Maintain readability**: Don't sacrifice code clarity for marginal gains

**Test thoroughly**: Ensure optimizations don't introduce bugs

**Document rationale**: Explain why optimization was chosen

## Validation Checklist

- [ ] Each optimization addresses a specific bottleneck from analysis
- [ ] Before/after code snippets provided for clarity
- [ ] Performance impact measured with benchmarks
- [ ] No functional regressions introduced (tests pass)
- [ ] Code remains maintainable and readable
- [ ] Platform-specific features properly utilized
- [ ] Rollback procedure documented for each change

## Guardrails

**Functionality first**: Never break existing behavior for performance

**Incremental changes**: Apply one optimization at a time

**Evidence-based**: Only optimize based on profiling data, not hunches

**Sustainable**: Ensure optimizations are maintainable by the team