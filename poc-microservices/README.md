# Student Library Management System - POC Microservices

**Version:** 1.0.0-SNAPSHOT  
**Status:** Proof of Concept - In Development  
**Duration:** 2 weeks  
**Team:** 2 Developers

---

## Overview

This is a Proof of Concept (POC) implementation to validate the microservices migration strategy for the Student Library Management System. The POC focuses on validating the highest-risk components:

1. **Saga Pattern** - Distributed transaction orchestration
2. **Service Communication** - Feign clients with circuit breakers
3. **Distributed Tracing** - Request correlation across services
4. **Service Discovery** - Dynamic service registration
5. **Performance** - Response time and throughput validation

---

## Architecture

```
┌─────────────┐
│   Client    │
└──────┬──────┘
       │
       ▼
┌─────────────────────────────────────┐
│       API Gateway (8080)            │
│  - Routing                          │
│  - Load Balancing                   │
└──────┬──────────────────────────────┘
       │
       ├──────────┬──────────┬─────────────┐
       │          │          │             │
       ▼          ▼          ▼             ▼
┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────┐
│ Student  │ │   Book   │ │Transaction│ │  Eureka  │
│ Service  │ │ Service  │ │  Service  │ │  Server  │
│  (8081)  │ │  (8082)  │ │  (8083)   │ │  (8761)  │
└────┬─────┘ └────┬─────┘ └────┬──────┘ └──────────┘
     │            │            │
     ▼            ▼            ▼
┌─────────┐  ┌─────────┐  ┌─────────┐
│ H2 (mem)│  │ H2 (mem)│  │ H2 (mem)│
└─────────┘  └─────────┘  └─────────┘

┌──────────┐  ┌──────────┐
│  Config  │  │  Zipkin  │
│  Server  │  │  (9411)  │
│  (8888)  │  │          │
└──────────┘  └──────────┘
```

---

## Technology Stack

| Component | Technology | Version |
|-----------|-----------|---------|
| Java | OpenJDK | 17 |
| Spring Boot | Spring Boot | 3.2.5 |
| Spring Cloud | Spring Cloud | 2023.0.1 (Leyton) |
| Database | H2 (in-memory) | 2.2.224 |
| Service Discovery | Netflix Eureka | 2023.0.1 |
| API Gateway | Spring Cloud Gateway | 2023.0.1 |
| Circuit Breaker | Resilience4j | 2.2.0 |
| Tracing | Sleuth + Zipkin | 1.2.5 / 2.27.0 |
| Saga | Spring State Machine | 3.2.0 |
| Build Tool | Maven | 3.9+ |

---

## Prerequisites

- **Java 17** or higher
- **Maven 3.9+**
- **Git**
- **IDE** (IntelliJ IDEA, Eclipse, or VS Code)
- **Postman** or **curl** (for API testing)

---

## Project Structure

```
poc-microservices/
├── pom.xml                          # Parent POM
├── README.md                        # This file
├── eureka-server/                   # Service Discovery
│   ├── pom.xml
│   └── src/
├── config-server/                   # Configuration Management
│   ├── pom.xml
│   └── src/
├── api-gateway/                     # API Gateway
│   ├── pom.xml
│   └── src/
├── student-service/                 # Student & Card Management
│   ├── pom.xml
│   └── src/
├── book-service/                    # Book & Author Management
│   ├── pom.xml
│   └── src/
└── transaction-service/             # Transaction & Saga Orchestration
    ├── pom.xml
    └── src/
```

---

## Getting Started

### 1. Clone the Repository

```bash
git clone <repository-url>
cd poc-microservices
```

### 2. Build All Services

```bash
mvn clean install
```

### 3. Start Services (in order)

#### Step 1: Start Eureka Server
```bash
cd eureka-server
mvn spring-boot:run
```

Wait for Eureka to start, then verify at: http://localhost:8761

#### Step 2: Start Config Server
```bash
cd config-server
mvn spring-boot:run
```

Verify at: http://localhost:8888/actuator/health

#### Step 3: Start Zipkin (Docker)
```bash
docker run -d -p 9411:9411 openzipkin/zipkin
```

Verify at: http://localhost:9411

#### Step 4: Start API Gateway
```bash
cd api-gateway
mvn spring-boot:run
```

Verify at: http://localhost:8080/actuator/health

#### Step 5: Start Student Service
```bash
cd student-service
mvn spring-boot:run
```

Verify at: http://localhost:8081/actuator/health

#### Step 6: Start Book Service
```bash
cd book-service
mvn spring-boot:run
```

Verify at: http://localhost:8082/actuator/health

#### Step 7: Start Transaction Service
```bash
cd transaction-service
mvn spring-boot:run
```

Verify at: http://localhost:8083/actuator/health

---

## Service Endpoints

### Eureka Server
- **URL:** http://localhost:8761
- **Dashboard:** http://localhost:8761
- **Purpose:** View registered services

### Config Server
- **URL:** http://localhost:8888
- **Health:** http://localhost:8888/actuator/health
- **Purpose:** Centralized configuration

### API Gateway
- **URL:** http://localhost:8080
- **Routes:**
  - `/api/v1/students/**` → Student Service
  - `/api/v1/books/**` → Book Service
  - `/api/v1/transactions/**` → Transaction Service

### Student Service
- **Direct URL:** http://localhost:8081
- **Via Gateway:** http://localhost:8080/api/v1/students
- **Endpoints:**
  - `GET /api/v1/students/{id}` - Get student by ID
  - `GET /api/v1/students/{id}/card` - Get student card
  - `GET /api/v1/students/{id}/validate` - Validate card status
  - `GET /api/v1/students/{id}/books-count` - Get issued books count

### Book Service
- **Direct URL:** http://localhost:8082
- **Via Gateway:** http://localhost:8080/api/v1/books
- **Endpoints:**
  - `GET /api/v1/books/{id}` - Get book by ID
  - `PUT /api/v1/books/{id}/availability` - Update availability
  - `GET /api/v1/books/search?genre={genre}` - Search books

### Transaction Service
- **Direct URL:** http://localhost:8083
- **Via Gateway:** http://localhost:8080/api/v1/transactions
- **Endpoints:**
  - `POST /api/v1/transactions/issue` - Issue book (Saga)
  - `POST /api/v1/transactions/return` - Return book (Saga)
  - `GET /api/v1/transactions/{id}/status` - Get Saga status

### Zipkin
- **URL:** http://localhost:9411
- **Purpose:** View distributed traces

---

## Testing the POC

### 1. Verify Service Discovery

Check Eureka dashboard to see all services registered:
```bash
curl http://localhost:8761
```

Expected: All 6 services (gateway, student, book, transaction, config, zipkin-client) registered

---

### 2. Test Student Service

```bash
# Get student (will return 404 initially, need to seed data)
curl http://localhost:8080/api/v1/students/1

# Get student card
curl http://localhost:8080/api/v1/students/1/card

# Validate card
curl http://localhost:8080/api/v1/students/1/validate
```

---

### 3. Test Book Service

```bash
# Get book
curl http://localhost:8080/api/v1/books/1

# Search books by genre
curl "http://localhost:8080/api/v1/books/search?genre=PHYSICS"

# Update book availability
curl -X PUT http://localhost:8080/api/v1/books/1/availability \
  -H "Content-Type: application/json" \
  -d '{"available": false}'
```

---

### 4. Test Transaction Service (Saga)

```bash
# Issue book (triggers Saga orchestration)
curl -X POST http://localhost:8080/api/v1/transactions/issue \
  -H "Content-Type: application/json" \
  -d '{
    "cardId": 1,
    "bookId": 1
  }'

# Check Saga status
curl http://localhost:8080/api/v1/transactions/{transactionId}/status

# Return book
curl -X POST http://localhost:8080/api/v1/transactions/return \
  -H "Content-Type: application/json" \
  -d '{
    "cardId": 1,
    "bookId": 1
  }'
```

---

### 5. Test Circuit Breaker

```bash
# Stop Book Service
# Then try to issue a book - should get fallback response

curl -X POST http://localhost:8080/api/v1/transactions/issue \
  -H "Content-Type: application/json" \
  -d '{
    "cardId": 1,
    "bookId": 1
  }'

# Expected: Circuit breaker opens, fallback response returned
```

---

### 6. View Distributed Traces

1. Execute any API call
2. Open Zipkin UI: http://localhost:9411
3. Click "Run Query"
4. See complete trace across all services

---

## Running Tests

### Unit Tests
```bash
# Run all unit tests
mvn test

# Run tests for specific service
cd student-service
mvn test
```

### Integration Tests
```bash
# Run integration tests
mvn verify -P integration-tests
```

### E2E Tests
```bash
# Start all services first, then:
mvn verify -P e2e-tests
```

---

## POC Success Criteria

The POC must validate these 5 critical criteria:

### ✅ Criterion 1: Saga Orchestration
- **Test:** Issue book transaction with all steps succeeding
- **Target:** Saga completes in <2 seconds
- **Validation:** All steps execute in correct order, transaction created, book marked unavailable

### ✅ Criterion 2: Compensating Transactions
- **Test:** Issue book transaction with step 3 failure (max books reached)
- **Target:** Saga rolls back correctly
- **Validation:** No partial state left in system, error returned to client

### ✅ Criterion 3: Circuit Breaker
- **Test:** Book Service down, Student Service calls Book Service
- **Target:** Circuit breaker opens after 5 failures
- **Validation:** Fallback response returned, circuit breaker closes after recovery

### ✅ Criterion 4: Distributed Tracing
- **Test:** Issue book transaction across 3 services
- **Target:** Single trace ID across all services
- **Validation:** All service calls visible in Zipkin, timing information accurate

### ✅ Criterion 5: Performance
- **Test:** 10 concurrent issue book transactions
- **Target:** All transactions complete successfully, average <2 seconds
- **Validation:** No deadlocks, no race conditions, system stable

---

## Troubleshooting

### Services Not Registering with Eureka

**Problem:** Service shows as DOWN in Eureka dashboard

**Solution:**
1. Check service logs for errors
2. Verify `eureka.client.service-url.defaultZone` in application.yml
3. Ensure Eureka Server is running
4. Wait 30 seconds for registration

---

### Circuit Breaker Not Opening

**Problem:** Circuit breaker doesn't open on failures

**Solution:**
1. Check Resilience4j configuration
2. Verify failure threshold (default: 50%)
3. Check sliding window size (default: 100 calls)
4. Review logs for circuit breaker events

---

### Traces Not Appearing in Zipkin

**Problem:** No traces visible in Zipkin UI

**Solution:**
1. Verify Zipkin is running: http://localhost:9411
2. Check `spring.zipkin.base-url` in application.yml
3. Ensure `spring.sleuth.sampler.probability=1.0` (100% sampling)
4. Check service logs for tracing errors

---

### H2 Database Issues

**Problem:** Data not persisting or schema errors

**Solution:**
1. Check H2 console: http://localhost:8081/h2-console
2. Verify JDBC URL: `jdbc:h2:mem:studentdb`
3. Check entity annotations
4. Review `spring.jpa.hibernate.ddl-auto` setting

---

## Development Guidelines

### Code Style
- Follow Java naming conventions
- Use Lombok to reduce boilerplate
- Add Javadoc for public methods
- Keep methods small (<20 lines)

### Git Workflow
- Create feature branch: `feature/poc-<component>`
- Commit frequently with clear messages
- Tag milestones: `poc-<component>-complete`
- Push to remote daily

### Testing
- Write tests before implementation (TDD)
- Aim for ≥80% code coverage
- Test happy path and error scenarios
- Use meaningful test names

---

## Known Limitations (POC)

1. **No Authentication** - OAuth2/Keycloak not implemented
2. **H2 Database** - In-memory, data lost on restart
3. **No Async Messaging** - Kafka/RabbitMQ not included
4. **Basic Error Handling** - Production-grade error handling pending
5. **No Monitoring** - Prometheus/Grafana not set up
6. **Single Instance** - No HA or clustering

These limitations are acceptable for POC and will be addressed in full implementation.

---

## Next Steps After POC

### If GO Decision:
1. Migrate from H2 to MySQL
2. Add OAuth2/Keycloak authentication
3. Implement async messaging (Kafka)
4. Add comprehensive error handling
5. Set up monitoring (Prometheus/Grafana)
6. Implement CI/CD pipeline
7. Deploy to staging environment

### If NO-GO Decision:
1. Document issues discovered
2. Analyze root causes
3. Propose alternative approaches
4. Get stakeholder input
5. Consider: Event Sourcing, Modular Monolith, or Hybrid approach

---

## Resources

### Documentation
- [Spring Boot 3.2 Docs](https://docs.spring.io/spring-boot/docs/3.2.5/reference/html/)
- [Spring Cloud Docs](https://spring.io/projects/spring-cloud)
- [Resilience4j Docs](https://resilience4j.readme.io/)
- [Spring State Machine Docs](https://docs.spring.io/spring-statemachine/docs/current/reference/)

### Design Documents
- [Target Architecture](../reports/design/design_target_architecture.md)
- [Component Strategies](../reports/design/design_component_strategies.md)
- [Interface Contracts](../reports/design/design_interface_contracts.md)
- [POC Recommendations](../reports/design/design_poc_recommendations.md)

### Execution Documents
- [Execution Roadmap](../reports/execution/execution_roadmap.md)
- [Code Changes Log](../reports/execution/execution_code_changes.md)

---

## Support

**Team Lead:** [Name]  
**Developers:** [Names]  
**Slack Channel:** #migration-poc  
**JIRA Project:** MIGR

---

## License

Internal use only - Student Library Management System Migration Project

---

**Last Updated:** 2026-05-17  
**POC Duration:** 2 weeks (2026-05-19 to 2026-05-30)  
**Status:** Week 1, Day 1 - Infrastructure Setup