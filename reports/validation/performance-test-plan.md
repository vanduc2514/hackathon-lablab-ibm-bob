# Performance Test Plan

**Project:** Student Library Management System  
**Migration:** Monolithic Java Spring Boot → Microservices Architecture  
**Date:** 2026-05-17  
**Version:** 1.0  
**Status:** 📋 DEFERRED - Execute after service implementation complete

---

## Executive Summary

This document defines the performance testing strategy for the migrated microservices system. Performance testing is **deferred** until all core services are implemented and functional tests pass. This plan will be executed in Week 2 of the remediation timeline.

**Test Execution Prerequisites:**
- ✅ All 3 core services implemented (Student, Book, Transaction)
- ✅ All functional tests passing (60 tests)
- ✅ Services operational and integrated
- ✅ Test data loaded

**Performance Targets (from design specifications):**
- API Response Time (p95): <200ms
- API Response Time (p99): <500ms
- Throughput: ≥100 req/sec per service
- Saga Completion: <2 seconds
- Error Rate: <0.1%

---

## 1. Test Scope

### 1.1 Services Under Test

**Core Services:**
1. **Student Service** (Port 8081)
   - Create student
   - Get student by ID
   - Update student
   - Delete student
   - Get student card

2. **Book Service** (Port 8082)
   - Create book
   - Get book by ID
   - Search books
   - Update book availability
   - Get books by author

3. **Transaction Service** (Port 8083)
   - Issue book (Saga orchestration)
   - Return book (Saga orchestration)
   - Get transaction history
   - Get active transactions

**Infrastructure Services:**
- API Gateway (Port 8080) - All requests routed through gateway
- Eureka Server (Port 8761) - Service discovery overhead
- Config Server (Port 8888) - Configuration refresh impact

---

### 1.2 Test Types

**1. Load Testing**
- Objective: Validate system under expected load
- Duration: 10 minutes
- Users: 100 concurrent users
- Ramp-up: 30 seconds

**2. Stress Testing**
- Objective: Find breaking point
- Duration: 15 minutes
- Users: Start at 100, increase to 500
- Ramp-up: 2 minutes

**3. Spike Testing**
- Objective: Validate recovery from sudden load
- Duration: 5 minutes
- Users: 50 → 300 → 50
- Spike duration: 1 minute

**4. Endurance Testing**
- Objective: Detect memory leaks and degradation
- Duration: 2 hours
- Users: 50 concurrent users
- Steady state

**5. Saga Performance Testing**
- Objective: Validate distributed transaction performance
- Focus: Issue/Return book operations
- Target: <2 seconds end-to-end

---

## 2. Test Scenarios

### 2.1 Student Service Scenarios

#### Scenario 1: Create Student
**Endpoint:** `POST /api/v1/students`  
**Weight:** 20%  
**Expected Response Time (p95):** <150ms  
**Payload:**
```json
{
  "name": "John Doe",
  "emailId": "john.doe@example.com",
  "age": 20,
  "country": "USA"
}
```

**Success Criteria:**
- Response time p95 <150ms
- Response time p99 <300ms
- Error rate <0.1%
- HTTP 201 Created

---

#### Scenario 2: Get Student by ID
**Endpoint:** `GET /api/v1/students/{id}`  
**Weight:** 40%  
**Expected Response Time (p95):** <100ms  

**Success Criteria:**
- Response time p95 <100ms
- Response time p99 <200ms
- Error rate <0.1%
- HTTP 200 OK

---

#### Scenario 3: Update Student
**Endpoint:** `PUT /api/v1/students/{id}`  
**Weight:** 20%  
**Expected Response Time (p95):** <150ms  

**Success Criteria:**
- Response time p95 <150ms
- Response time p99 <300ms
- Error rate <0.1%
- HTTP 200 OK

---

#### Scenario 4: Get Student Card
**Endpoint:** `GET /api/v1/students/{id}/card`  
**Weight:** 20%  
**Expected Response Time (p95):** <100ms  

**Success Criteria:**
- Response time p95 <100ms
- Response time p99 <200ms
- Error rate <0.1%
- HTTP 200 OK

---

### 2.2 Book Service Scenarios

#### Scenario 5: Search Books
**Endpoint:** `GET /api/v1/books?genre={genre}`  
**Weight:** 40%  
**Expected Response Time (p95):** <150ms  

**Success Criteria:**
- Response time p95 <150ms
- Response time p99 <300ms
- Error rate <0.1%
- HTTP 200 OK

---

#### Scenario 6: Get Book by ID
**Endpoint:** `GET /api/v1/books/{id}`  
**Weight:** 30%  
**Expected Response Time (p95):** <100ms  

**Success Criteria:**
- Response time p95 <100ms
- Response time p99 <200ms
- Error rate <0.1%
- HTTP 200 OK

---

#### Scenario 7: Create Book
**Endpoint:** `POST /api/v1/books`  
**Weight:** 15%  
**Expected Response Time (p95):** <150ms  

**Success Criteria:**
- Response time p95 <150ms
- Response time p99 <300ms
- Error rate <0.1%
- HTTP 201 Created

---

#### Scenario 8: Get Books by Author
**Endpoint:** `GET /api/v1/books/author/{authorId}`  
**Weight:** 15%  
**Expected Response Time (p95):** <150ms  

**Success Criteria:**
- Response time p95 <150ms
- Response time p99 <300ms
- Error rate <0.1%
- HTTP 200 OK

---

### 2.3 Transaction Service Scenarios (Saga)

#### Scenario 9: Issue Book (Saga)
**Endpoint:** `POST /api/v1/transactions/issue`  
**Weight:** 40%  
**Expected Response Time (p95):** <1500ms  
**Payload:**
```json
{
  "studentId": 1,
  "bookId": 1
}
```

**Saga Steps:**
1. Validate student card active
2. Check book availability
3. Create transaction
4. Update book availability
5. Update student issued books count

**Success Criteria:**
- Response time p95 <1500ms
- Response time p99 <2000ms
- Error rate <0.1%
- HTTP 201 Created
- All saga steps complete or compensate

---

#### Scenario 10: Return Book (Saga)
**Endpoint:** `POST /api/v1/transactions/return`  
**Weight:** 40%  
**Expected Response Time (p95):** <1500ms  
**Payload:**
```json
{
  "transactionId": 1
}
```

**Saga Steps:**
1. Validate transaction exists
2. Calculate fine if overdue
3. Update transaction status
4. Update book availability
5. Update student issued books count

**Success Criteria:**
- Response time p95 <1500ms
- Response time p99 <2000ms
- Error rate <0.1%
- HTTP 200 OK
- All saga steps complete or compensate

---

#### Scenario 11: Get Transaction History
**Endpoint:** `GET /api/v1/transactions/student/{studentId}`  
**Weight:** 20%  
**Expected Response Time (p95):** <150ms  

**Success Criteria:**
- Response time p95 <150ms
- Response time p99 <300ms
- Error rate <0.1%
- HTTP 200 OK

---

## 3. Test Environment

### 3.1 Infrastructure

**Hardware:**
- CPU: 4 cores
- RAM: 8 GB
- Disk: SSD

**Software:**
- OS: macOS Monterey
- Java: 17
- Spring Boot: 3.2.5
- Database: H2 (in-memory for POC)

**Services:**
- Eureka Server: 1 instance
- Config Server: 1 instance
- API Gateway: 1 instance
- Student Service: 1 instance
- Book Service: 1 instance
- Transaction Service: 1 instance

---

### 3.2 Test Data

**Students:** 1,000 records
- IDs: 1-1000
- Active cards: 900
- Inactive cards: 100

**Books:** 500 records
- IDs: 1-500
- Available: 400
- Issued: 100
- Genres: 5 (FICTION, NON_FICTION, SCIENCE, HISTORY, BIOGRAPHY)

**Authors:** 100 records
- IDs: 1-100
- Books per author: 5 average

**Transactions:** 200 records
- Active: 100
- Completed: 100

---

## 4. Test Tools

### 4.1 Gatling

**Version:** 3.9.5  
**Language:** Scala  
**Reason:** Industry-standard, excellent reporting, Scala DSL

**Installation:**
```bash
# Download Gatling
wget https://repo1.maven.org/maven2/io/gatling/highcharts/gatling-charts-highcharts-bundle/3.9.5/gatling-charts-highcharts-bundle-3.9.5-bundle.zip

# Extract
unzip gatling-charts-highcharts-bundle-3.9.5-bundle.zip

# Run test
./bin/gatling.sh
```

---

### 4.2 Test Scripts

**Location:** `poc-microservices/performance-tests/`

**Scripts:**
1. `LoadTest.scala` - Load testing
2. `StressTest.scala` - Stress testing
3. `SpikeTest.scala` - Spike testing
4. `EnduranceTest.scala` - Endurance testing
5. `SagaTest.scala` - Saga performance testing

---

## 5. Metrics Collection

### 5.1 Application Metrics

**Spring Boot Actuator:**
- `/actuator/metrics/http.server.requests`
- `/actuator/metrics/jvm.memory.used`
- `/actuator/metrics/jvm.threads.live`
- `/actuator/metrics/process.cpu.usage`

**Custom Metrics:**
- Saga execution time
- Circuit breaker state changes
- Service discovery lookup time

---

### 5.2 Infrastructure Metrics

**JVM Metrics:**
- Heap memory usage
- GC pause time
- Thread count
- CPU usage

**Database Metrics:**
- Connection pool size
- Query execution time
- Transaction count

**Network Metrics:**
- Request rate
- Response time
- Error rate
- Throughput

---

## 6. Test Execution Plan

### 6.1 Pre-Test Checklist

- [ ] All services running
- [ ] Test data loaded
- [ ] Monitoring tools configured
- [ ] Baseline metrics captured
- [ ] Test scripts validated
- [ ] Environment stable

---

### 6.2 Test Sequence

**Day 1: Baseline and Load Testing**
1. Capture baseline metrics (no load)
2. Run load test (100 users, 10 minutes)
3. Analyze results
4. Tune if needed
5. Re-run load test

**Day 2: Stress and Spike Testing**
1. Run stress test (100-500 users, 15 minutes)
2. Identify breaking point
3. Run spike test (50-300-50 users, 5 minutes)
4. Validate recovery

**Day 3: Endurance and Saga Testing**
1. Run endurance test (50 users, 2 hours)
2. Monitor for memory leaks
3. Run saga-specific tests
4. Validate distributed transaction performance

---

### 6.3 Post-Test Activities

1. **Generate Reports**
   - Gatling HTML reports
   - Metrics dashboards
   - Performance summary

2. **Analyze Results**
   - Compare against targets
   - Identify bottlenecks
   - Document findings

3. **Recommendations**
   - Optimization opportunities
   - Configuration tuning
   - Architecture improvements

---

## 7. Success Criteria

### 7.1 Performance Targets

| Metric | Target | Measurement |
|--------|--------|-------------|
| API Response Time (p95) | <200ms | Gatling report |
| API Response Time (p99) | <500ms | Gatling report |
| Throughput | ≥100 req/sec | Gatling report |
| Saga Completion (p95) | <1500ms | Custom metric |
| Saga Completion (p99) | <2000ms | Custom metric |
| Error Rate | <0.1% | Gatling report |
| CPU Usage | <70% | JVM metrics |
| Memory Usage | <80% | JVM metrics |
| GC Pause Time | <100ms | JVM metrics |

---

### 7.2 Pass/Fail Criteria

**PASS:** All targets met
- ✅ All response time targets met
- ✅ Throughput target met
- ✅ Error rate below threshold
- ✅ No memory leaks detected
- ✅ System stable under load

**CONDITIONAL PASS:** Minor deviations
- ⚠️ 1-2 targets missed by <20%
- ⚠️ No critical issues
- ⚠️ Clear optimization path

**FAIL:** Significant issues
- ❌ Multiple targets missed
- ❌ System instability
- ❌ Memory leaks detected
- ❌ High error rates

---

## 8. Bottleneck Analysis

### 8.1 Potential Bottlenecks

**Database:**
- H2 in-memory limitations
- Connection pool exhaustion
- Query performance

**Network:**
- Service-to-service latency
- API Gateway overhead
- Eureka lookup time

**Application:**
- Saga orchestration complexity
- Circuit breaker overhead
- Serialization/deserialization

**Infrastructure:**
- CPU constraints
- Memory constraints
- Thread pool exhaustion

---

### 8.2 Optimization Strategies

**If Database Bottleneck:**
- Increase connection pool size
- Add database indexes
- Optimize queries
- Consider caching

**If Network Bottleneck:**
- Enable HTTP/2
- Optimize payload size
- Add caching layer
- Consider async communication

**If Application Bottleneck:**
- Optimize business logic
- Reduce object creation
- Use reactive programming
- Implement caching

**If Infrastructure Bottleneck:**
- Increase resources
- Horizontal scaling
- Load balancing
- Container optimization

---

## 9. Monitoring During Tests

### 9.1 Real-time Monitoring

**Metrics to Watch:**
- Response time trends
- Error rate spikes
- CPU usage patterns
- Memory usage growth
- Thread count changes
- GC frequency

**Tools:**
- Spring Boot Actuator
- JConsole
- VisualVM
- Gatling real-time charts

---

### 9.2 Alert Thresholds

| Metric | Warning | Critical |
|--------|---------|----------|
| Response Time (p95) | >150ms | >200ms |
| Error Rate | >0.05% | >0.1% |
| CPU Usage | >60% | >80% |
| Memory Usage | >70% | >85% |
| GC Pause Time | >50ms | >100ms |
| Thread Count | >100 | >150 |

---

## 10. Test Deliverables

### 10.1 Reports

1. **Gatling HTML Report**
   - Response time distribution
   - Throughput graphs
   - Error analysis
   - Percentile charts

2. **Performance Summary Report**
   - Executive summary
   - Target vs actual comparison
   - Bottleneck analysis
   - Recommendations

3. **Metrics Dashboard**
   - JVM metrics
   - Application metrics
   - Infrastructure metrics

---

### 10.2 Artifacts

1. **Test Scripts**
   - All Gatling scenarios
   - Test data generators
   - Configuration files

2. **Test Data**
   - Sample datasets
   - Data generation scripts

3. **Baseline Metrics**
   - Pre-test measurements
   - Environment configuration

---

## 11. Risk Mitigation

### 11.1 Test Risks

**Risk 1: Test Environment Instability**
- **Mitigation:** Validate environment before tests
- **Contingency:** Rebuild environment if needed

**Risk 2: Insufficient Test Data**
- **Mitigation:** Generate comprehensive test data
- **Contingency:** Use data generation scripts

**Risk 3: Service Failures During Test**
- **Mitigation:** Implement health checks
- **Contingency:** Restart services and re-run

**Risk 4: Resource Constraints**
- **Mitigation:** Monitor resources continuously
- **Contingency:** Reduce load or increase resources

---

## 12. Next Steps

### 12.1 Immediate Actions

1. **Wait for Service Implementation**
   - Student Service complete
   - Book Service complete
   - Transaction Service complete

2. **Prepare Test Environment**
   - Install Gatling
   - Generate test data
   - Configure monitoring

3. **Write Test Scripts**
   - Implement all scenarios
   - Validate scripts
   - Dry run tests

---

### 12.2 Execution Timeline

**Week 2, Day 6-7 (After functional tests pass):**

**Day 6:**
- Morning: Environment setup and baseline
- Afternoon: Load testing
- Evening: Analysis and tuning

**Day 7:**
- Morning: Stress and spike testing
- Afternoon: Saga performance testing
- Evening: Report generation

---

## 13. Conclusion

This performance test plan provides a comprehensive strategy for validating the migrated microservices system against performance targets. Execution is **deferred** until all services are implemented and functional tests pass.

**Key Points:**
- ✅ Comprehensive test scenarios defined
- ✅ Clear success criteria established
- ✅ Monitoring strategy in place
- ✅ Risk mitigation planned
- ⏳ Execution pending service completion

**Expected Outcome:**
- Validate all performance targets met
- Identify optimization opportunities
- Establish performance baseline
- Provide data for optimization phase

---

**Test Plan Prepared By:** QA Lead  
**Date:** 2026-05-17  
**Status:** 📋 DEFERRED  
**Execution Date:** Week 2, Days 6-7 (After service implementation)  
**Approval:** Pending stakeholder review

---

## Appendix A: Gatling Script Template

```scala
package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class LoadTest extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("application/json")
    .contentTypeHeader("application/json")

  val scn = scenario("Load Test")
    .exec(
      http("Create Student")
        .post("/api/v1/students")
        .body(StringBody("""{"name":"John Doe","emailId":"john.doe@example.com","age":20,"country":"USA"}"""))
        .check(status.is(201))
    )
    .pause(1)
    .exec(
      http("Get Student")
        .get("/api/v1/students/${studentId}")
        .check(status.is(200))
    )

  setUp(
    scn.inject(
      rampUsers(100) during (30 seconds)
    ).protocols(httpProtocol)
  )
}
```

---

## Appendix B: Test Data Generation Script

```java
// TestDataGenerator.java
public class TestDataGenerator {
    
    public static void generateStudents(int count) {
        for (int i = 1; i <= count; i++) {
            Student student = Student.builder()
                .name("Student " + i)
                .emailId("student" + i + "@example.com")
                .age(18 + (i % 10))
                .country("Country " + (i % 5))
                .build();
            studentRepository.save(student);
        }
    }
    
    public static void generateBooks(int count) {
        for (int i = 1; i <= count; i++) {
            Book book = Book.builder()
                .title("Book " + i)
                .genre(Genre.values()[i % 5])
                .available(i % 5 != 0)
                .build();
            bookRepository.save(book);
        }
    }
}