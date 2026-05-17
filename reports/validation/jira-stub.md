# JIRA Issue Tracking Stub

**Project:** Student Library Management System Migration  
**Epic:** SLMS-MIGRATION  
**Date:** 2026-05-17  
**Status:** 📋 READY FOR IMPORT

---

## Overview

This document contains JIRA story stubs for all issues identified during the Migration Validation phase. These issues must be resolved before proceeding to the Optimization phase.

**Total Issues:** 8  
**Blocking Issues:** 5 (P0)  
**Non-Blocking Issues:** 3 (P1)  
**Total Effort:** 16.5 days

---

## Blocking Issues (P0) - Must Complete Before Optimization

### SLMS-VAL-001: Implement Student Service Controller

**Type:** Story  
**Priority:** P0 - Blocker  
**Severity:** High  
**Component:** Student Service  
**Sprint:** Validation Remediation - Week 1  
**Effort:** 1 day  
**Assignee:** Dev Team

**Description:**
Implement the REST controller for Student Service to expose all student management endpoints. The service layer, repositories, and models are complete, but the controller layer is missing.

**Acceptance Criteria:**
- [ ] StudentController.java created with all endpoints
- [ ] POST /api/v1/students - Create student
- [ ] GET /api/v1/students/{id} - Get student by ID
- [ ] GET /api/v1/students/email/{email} - Get student by email
- [ ] PUT /api/v1/students/{id} - Update student
- [ ] DELETE /api/v1/students/{id} - Delete student
- [ ] GET /api/v1/students/{id}/card - Get student card
- [ ] All endpoints use @Valid for request validation
- [ ] Proper HTTP status codes returned
- [ ] Exception handling with @RestControllerAdvice
- [ ] API documentation with Swagger annotations

**Technical Details:**
```java
@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Slf4j
public class StudentController {
    private final StudentService studentService;
    
    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        // Implementation
    }
    
    // Other endpoints...
}
```

**Dependencies:**
- None (service layer complete)

**Related Files:**
- `poc-microservices/student-service/src/main/java/com/library/student/controller/StudentController.java` (new)
- `poc-microservices/student-service/src/main/java/com/library/student/exception/GlobalExceptionHandler.java` (new)

**Testing:**
- Unit tests: 5 tests
- Integration tests: 2 tests

**Definition of Done:**
- [ ] Code implemented and reviewed
- [ ] Unit tests written and passing
- [ ] Integration tests written and passing
- [ ] API documentation generated
- [ ] Postman collection created
- [ ] Code merged to main branch

---

### SLMS-VAL-002: Create Student Service Configuration

**Type:** Task  
**Priority:** P0 - Blocker  
**Severity:** High  
**Component:** Student Service  
**Sprint:** Validation Remediation - Week 1  
**Effort:** 0.5 days  
**Assignee:** Dev Team

**Description:**
Create application.yml configuration file for Student Service with all required settings including port, database, Eureka client, and Zipkin tracing.

**Acceptance Criteria:**
- [ ] application.yml created in src/main/resources
- [ ] Server port configured (8081)
- [ ] H2 database configured (student_db)
- [ ] Eureka client configured
- [ ] Zipkin tracing configured
- [ ] Actuator endpoints configured
- [ ] Logging configuration added
- [ ] Profile-specific configurations (dev, prod)

**Technical Details:**
```yaml
server:
  port: 8081

spring:
  application:
    name: student-service
  datasource:
    url: jdbc:h2:mem:student_db
    driver-class-name: org.h2.Driver
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
  tracing:
    sampling:
      probability: 1.0
```

**Dependencies:**
- None

**Related Files:**
- `poc-microservices/student-service/src/main/resources/application.yml` (new)
- `poc-microservices/student-service/src/main/resources/application-dev.yml` (new)
- `poc-microservices/student-service/src/main/resources/application-prod.yml` (new)

**Testing:**
- Service starts successfully
- Registers with Eureka
- Database connection works
- Actuator endpoints accessible

**Definition of Done:**
- [ ] Configuration file created
- [ ] Service starts without errors
- [ ] Eureka registration verified
- [ ] Database connectivity verified
- [ ] Configuration reviewed and approved

---

### SLMS-VAL-003: Implement Complete Book Service

**Type:** Story  
**Priority:** P0 - Blocker  
**Severity:** Critical  
**Component:** Book Service  
**Sprint:** Validation Remediation - Week 1  
**Effort:** 2 days  
**Assignee:** Dev Team

**Description:**
Implement the complete Book Service including models, repositories, services, controllers, and configuration. This service manages books and authors with search functionality.

**Acceptance Criteria:**
- [ ] Models: Book.java, Author.java, Genre.java (enum)
- [ ] Repositories: BookRepository.java, AuthorRepository.java
- [ ] Services: BookService.java, AuthorService.java
- [ ] DTOs: BookDTO.java, AuthorDTO.java
- [ ] Controllers: BookController.java, AuthorController.java
- [ ] Configuration: application.yml
- [ ] Exception handling: Custom exceptions and global handler
- [ ] Search functionality: By genre, author, title
- [ ] Validation: Jakarta Validation annotations
- [ ] Lombok: Used for boilerplate reduction

**API Endpoints:**

**Books:**
- POST /api/v1/books - Create book
- GET /api/v1/books/{id} - Get book by ID
- GET /api/v1/books - Search books (query params: genre, title, authorId)
- PUT /api/v1/books/{id} - Update book
- DELETE /api/v1/books/{id} - Delete book
- PATCH /api/v1/books/{id}/availability - Update availability

**Authors:**
- POST /api/v1/authors - Create author
- GET /api/v1/authors/{id} - Get author by ID
- GET /api/v1/authors - List all authors
- PUT /api/v1/authors/{id} - Update author
- DELETE /api/v1/authors/{id} - Delete author
- GET /api/v1/authors/{id}/books - Get books by author

**Technical Details:**
- Port: 8082
- Database: book_db (H2)
- Bidirectional relationships removed (microservices pattern)
- Feign client for Student Service (if needed)

**Dependencies:**
- Student Service (for validation)

**Related Files:**
- `poc-microservices/book-service/` (entire service)

**Testing:**
- Unit tests: 12 tests
- Integration tests: 3 tests

**Definition of Done:**
- [ ] All components implemented
- [ ] Service starts successfully
- [ ] All endpoints functional
- [ ] Unit tests passing
- [ ] Integration tests passing
- [ ] Code coverage ≥80%
- [ ] Code reviewed and merged

---

### SLMS-VAL-004: Implement Complete Transaction Service with Saga

**Type:** Epic  
**Priority:** P0 - Blocker  
**Severity:** Critical  
**Component:** Transaction Service  
**Sprint:** Validation Remediation - Week 1  
**Effort:** 3 days  
**Assignee:** Dev Team

**Description:**
Implement the complete Transaction Service with Saga orchestration for distributed transactions. This is the most complex service, handling book issue/return operations across multiple services.

**Acceptance Criteria:**
- [ ] Models: Transaction.java, TransactionStatus.java (enum)
- [ ] Repositories: TransactionRepository.java
- [ ] Services: TransactionService.java, SagaOrchestrator.java
- [ ] Saga State Machine: Spring State Machine configuration
- [ ] Feign Clients: StudentServiceClient.java, BookServiceClient.java
- [ ] Circuit Breakers: Resilience4j for all Feign clients
- [ ] Controllers: TransactionController.java
- [ ] Configuration: application.yml with circuit breaker settings
- [ ] DTOs: TransactionDTO.java, IssueBookRequest.java, ReturnBookRequest.java
- [ ] Exception handling: Saga compensation logic

**API Endpoints:**
- POST /api/v1/transactions/issue - Issue book (Saga)
- POST /api/v1/transactions/return - Return book (Saga)
- GET /api/v1/transactions/{id} - Get transaction by ID
- GET /api/v1/transactions/student/{studentId} - Get student transactions
- GET /api/v1/transactions/book/{bookId} - Get book transactions
- GET /api/v1/transactions/active - Get active transactions

**Saga Flow - Issue Book:**
1. START → Validate student card active (Student Service)
2. Check book availability (Book Service)
3. Create transaction (Transaction Service)
4. Update book availability (Book Service)
5. Update student issued books count (Student Service)
6. COMPLETE

**Compensation Flow:**
- If any step fails, rollback previous steps
- Restore book availability
- Delete transaction
- Log failure

**Technical Details:**
- Port: 8083
- Database: transaction_db (H2)
- Spring State Machine for Saga orchestration
- Feign clients with circuit breakers
- Retry logic: 3 retries with exponential backoff
- Timeout: 5 seconds per service call

**Dependencies:**
- Student Service (Feign client)
- Book Service (Feign client)

**Related Files:**
- `poc-microservices/transaction-service/` (entire service)

**Testing:**
- Unit tests: 15 tests
- Integration tests: 4 tests
- Saga tests: 3 tests (success, partial failure, complete failure)

**Definition of Done:**
- [ ] All components implemented
- [ ] Saga orchestration working
- [ ] Compensation logic working
- [ ] Circuit breakers functional
- [ ] All endpoints functional
- [ ] Unit tests passing
- [ ] Integration tests passing
- [ ] Saga tests passing
- [ ] Code coverage ≥80%
- [ ] Performance <2 seconds
- [ ] Code reviewed and merged

---

### SLMS-VAL-005: Implement Comprehensive Test Suite

**Type:** Epic  
**Priority:** P0 - Blocker  
**Severity:** Critical  
**Component:** All Services  
**Sprint:** Validation Remediation - Week 2  
**Effort:** 5 days  
**Assignee:** QA + Dev Team

**Description:**
Implement comprehensive test suite covering all services with unit tests, integration tests, and E2E tests. Target code coverage ≥80%.

**Acceptance Criteria:**
- [ ] Unit tests: 42 tests total
  - Student Service: 12 tests
  - Book Service: 12 tests
  - Transaction Service: 15 tests
  - Infrastructure: 3 tests
- [ ] Integration tests: 12 tests total
  - Student Service: 3 tests
  - Book Service: 3 tests
  - Transaction Service: 4 tests
  - API Gateway: 2 tests
- [ ] E2E tests: 6 tests total
  - Complete user flows
  - Saga orchestration
  - Error scenarios
- [ ] Code coverage ≥80% for all services
- [ ] All tests passing
- [ ] Test reports generated

**Test Framework:**
- JUnit 5
- Mockito for mocking
- Testcontainers for integration tests
- RestAssured for API testing
- WireMock for service mocking

**Test Categories:**

**Unit Tests:**
- Service layer logic
- Repository queries
- DTO validation
- Exception handling
- Saga state transitions

**Integration Tests:**
- Database operations
- Service-to-service communication
- Circuit breaker behavior
- Feign client integration
- Eureka registration

**E2E Tests:**
- Complete issue book flow
- Complete return book flow
- Student CRUD operations
- Book search functionality
- Error handling scenarios
- Saga compensation

**Dependencies:**
- All services implemented (SLMS-VAL-001 to SLMS-VAL-004)

**Related Files:**
- `poc-microservices/*/src/test/java/**/*Test.java`

**Testing:**
- All tests must pass
- Coverage reports generated
- No flaky tests

**Definition of Done:**
- [ ] All 60 tests implemented
- [ ] All tests passing
- [ ] Code coverage ≥80%
- [ ] Coverage reports generated
- [ ] Test documentation created
- [ ] CI/CD pipeline configured
- [ ] Tests reviewed and approved

---

## Non-Blocking Issues (P1) - Defer to Optimization Phase

### SLMS-VAL-006: Implement OAuth2/Keycloak Authentication

**Type:** Story  
**Priority:** P1 - High  
**Severity:** Medium  
**Component:** Security  
**Sprint:** Optimization Phase  
**Effort:** 2 days  
**Assignee:** Security Team

**Description:**
Implement OAuth2/JWT authentication using Keycloak for all microservices. This provides secure authentication and authorization for the system.

**Acceptance Criteria:**
- [ ] Keycloak server set up (Docker)
- [ ] Realm and clients configured
- [ ] API Gateway integration with Keycloak
- [ ] JWT token validation
- [ ] Role-based access control (RBAC)
- [ ] User management endpoints
- [ ] Token refresh mechanism
- [ ] Logout functionality

**Technical Details:**
- Keycloak version: 23.0.0
- Port: 8180
- Realm: library-realm
- Clients: student-service, book-service, transaction-service
- Roles: USER, ADMIN, LIBRARIAN

**Dependencies:**
- All services operational

**Related Files:**
- `poc-microservices/keycloak/` (new)
- `poc-microservices/api-gateway/src/main/java/com/library/gateway/config/SecurityConfig.java` (new)

**Testing:**
- Authentication tests: 5 tests
- Authorization tests: 5 tests
- Token validation tests: 3 tests

**Definition of Done:**
- [ ] Keycloak configured
- [ ] All services secured
- [ ] Tests passing
- [ ] Documentation updated

---

### SLMS-VAL-007: Run Security Scanning

**Type:** Task  
**Priority:** P1 - High  
**Severity:** Medium  
**Component:** Security  
**Sprint:** Optimization Phase  
**Effort:** 1 day  
**Assignee:** Security Team

**Description:**
Run comprehensive security scanning using OWASP tools and SonarQube to identify and fix security vulnerabilities.

**Acceptance Criteria:**
- [ ] OWASP Dependency Check run
- [ ] OWASP ZAP DAST scan run
- [ ] SonarQube SAST scan run
- [ ] All critical vulnerabilities fixed
- [ ] All high vulnerabilities fixed
- [ ] Security report generated

**Tools:**
- OWASP Dependency Check 9.0.0
- OWASP ZAP 2.14.0
- SonarQube 10.3.0

**Scan Types:**
- Dependency vulnerabilities
- Code vulnerabilities (SQL injection, XSS, etc.)
- Configuration issues
- Secrets detection

**Dependencies:**
- All services implemented

**Related Files:**
- `reports/security/dependency-check-report.html`
- `reports/security/zap-report.html`
- `reports/security/sonarqube-report.pdf`

**Testing:**
- Verify no false positives
- Validate fixes

**Definition of Done:**
- [ ] All scans completed
- [ ] Critical/high vulnerabilities fixed
- [ ] Reports generated
- [ ] Findings documented

---

### SLMS-VAL-008: Set Up Monitoring and Observability

**Type:** Story  
**Priority:** P1 - High  
**Severity:** Medium  
**Component:** Operations  
**Sprint:** Optimization Phase  
**Effort:** 2 days  
**Assignee:** DevOps Team

**Description:**
Set up comprehensive monitoring and observability stack with Prometheus, Grafana, and ELK for production readiness.

**Acceptance Criteria:**
- [ ] Prometheus server configured
- [ ] Grafana dashboards created
- [ ] ELK Stack configured (Elasticsearch, Logstash, Kibana)
- [ ] Application metrics exported
- [ ] Log aggregation working
- [ ] Alerting rules configured
- [ ] Dashboards for all services

**Components:**
- Prometheus 2.48.0
- Grafana 10.2.0
- Elasticsearch 8.11.0
- Logstash 8.11.0
- Kibana 8.11.0

**Metrics to Monitor:**
- Request rate
- Response time (p50, p95, p99)
- Error rate
- CPU usage
- Memory usage
- JVM metrics
- Database connections
- Circuit breaker state

**Dashboards:**
- System overview
- Service health
- API Gateway metrics
- Saga performance
- Error tracking
- Resource utilization

**Dependencies:**
- All services operational

**Related Files:**
- `poc-microservices/monitoring/prometheus/prometheus.yml`
- `poc-microservices/monitoring/grafana/dashboards/`
- `poc-microservices/monitoring/elk/logstash.conf`

**Testing:**
- Metrics collection verified
- Dashboards functional
- Alerts triggering correctly

**Definition of Done:**
- [ ] All components deployed
- [ ] Metrics flowing
- [ ] Dashboards created
- [ ] Alerts configured
- [ ] Documentation created
- [ ] Team trained

---

## Issue Summary

### By Priority

| Priority | Count | Effort |
|----------|-------|--------|
| P0 (Blocker) | 5 | 11.5 days |
| P1 (High) | 3 | 5 days |
| **Total** | **8** | **16.5 days** |

---

### By Component

| Component | Count | Effort |
|-----------|-------|--------|
| Student Service | 2 | 1.5 days |
| Book Service | 1 | 2 days |
| Transaction Service | 1 | 3 days |
| Testing | 1 | 5 days |
| Security | 2 | 3 days |
| Operations | 1 | 2 days |
| **Total** | **8** | **16.5 days** |

---

### By Sprint

| Sprint | Issues | Effort |
|--------|--------|--------|
| Validation Remediation - Week 1 | 4 | 6.5 days |
| Validation Remediation - Week 2 | 1 | 5 days |
| Optimization Phase | 3 | 5 days |
| **Total** | **8** | **16.5 days** |

---

## Import Instructions

### JIRA Import Format

This stub can be imported into JIRA using CSV format:

```csv
Issue Type,Priority,Summary,Description,Component,Sprint,Estimate,Assignee
Story,Blocker,Implement Student Service Controller,[Full description],Student Service,Week 1,1d,Dev Team
Task,Blocker,Create Student Service Configuration,[Full description],Student Service,Week 1,0.5d,Dev Team
Story,Blocker,Implement Complete Book Service,[Full description],Book Service,Week 1,2d,Dev Team
Epic,Blocker,Implement Complete Transaction Service with Saga,[Full description],Transaction Service,Week 1,3d,Dev Team
Epic,Blocker,Implement Comprehensive Test Suite,[Full description],All Services,Week 2,5d,QA + Dev
Story,High,Implement OAuth2/Keycloak Authentication,[Full description],Security,Optimization,2d,Security Team
Task,High,Run Security Scanning,[Full description],Security,Optimization,1d,Security Team
Story,High,Set Up Monitoring and Observability,[Full description],Operations,Optimization,2d,DevOps Team
```

---

## Tracking Dashboard

### Week 1 Progress

| Issue | Status | Progress | Blocker |
|-------|--------|----------|---------|
| SLMS-VAL-001 | 🔴 Not Started | 0% | Yes |
| SLMS-VAL-002 | 🔴 Not Started | 0% | Yes |
| SLMS-VAL-003 | 🔴 Not Started | 0% | Yes |
| SLMS-VAL-004 | 🔴 Not Started | 0% | Yes |

### Week 2 Progress

| Issue | Status | Progress | Blocker |
|-------|--------|----------|---------|
| SLMS-VAL-005 | 🔴 Not Started | 0% | Yes |

### Optimization Phase

| Issue | Status | Progress | Blocker |
|-------|--------|----------|---------|
| SLMS-VAL-006 | 🔴 Not Started | 0% | No |
| SLMS-VAL-007 | 🔴 Not Started | 0% | No |
| SLMS-VAL-008 | 🔴 Not Started | 0% | No |

---

**Status Legend:**
- 🔴 Not Started
- 🟡 In Progress
- 🟢 Complete
- ⚫ Blocked

---

**Document Prepared By:** QA Lead  
**Date:** 2026-05-17  
**Status:** 📋 READY FOR IMPORT  
**Next Review:** Weekly during remediation sprints