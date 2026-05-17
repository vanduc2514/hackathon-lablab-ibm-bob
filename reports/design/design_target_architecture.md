# Target Architecture Design

**Project:** Student Library Management System  
**Migration:** Monolithic Java Spring Boot → Microservices Architecture  
**Date:** 2026-05-17  
**Version:** 1.0  
**Status:** Draft — Pending Review

---

## Executive Summary

This document defines the target microservices architecture for the Student Library Management System migration. The system will be decomposed from a monolithic Java 11 Spring Boot 2.4.3 application into 3 core microservices (Student, Book, Transaction) supported by 5 infrastructure services. The architecture adopts industry-standard patterns including API Gateway, Service Discovery, Saga orchestration for distributed transactions, and per-service databases following Domain-Driven Design principles.

**Key Architectural Decisions:**
- **Pattern:** Microservices with bounded contexts aligned to business domains
- **Communication:** Synchronous REST for queries, asynchronous events for transactions
- **Data:** Per-service databases with eventual consistency
- **Deployment:** Containerized services on Kubernetes (or Docker Compose for dev)
- **Observability:** Distributed tracing, centralized logging, metrics aggregation

---

## Target Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────────────┐
│                              Client Layer                                │
│                    (Web Browser, Mobile App, CLI)                        │
└────────────────────────────────┬────────────────────────────────────────┘
                                 │
                                 │ HTTPS
                                 ▼
┌─────────────────────────────────────────────────────────────────────────┐
│                         API Gateway Layer                                │
│  ┌──────────────────────────────────────────────────────────────────┐  │
│  │           Spring Cloud Gateway                                    │  │
│  │  - Routing & Load Balancing                                       │  │
│  │  - Authentication (OAuth2/JWT via Keycloak)                       │  │
│  │  - Rate Limiting & CORS                                           │  │
│  │  - Request/Response Transformation                                │  │
│  └──────────────────────────────────────────────────────────────────┘  │
└────────────────────────────────┬────────────────────────────────────────┘
                                 │
                ┌────────────────┼────────────────┐
                │                │                │
                ▼                ▼                ▼
┌───────────────────┐  ┌───────────────────┐  ┌───────────────────┐
│  Student Service  │  │   Book Service    │  │Transaction Service│
│                   │  │                   │  │                   │
│  ┌─────────────┐ │  │  ┌─────────────┐ │  │  ┌─────────────┐ │
│  │ Controllers │ │  │  │ Controllers │ │  │  │ Controllers │ │
│  └──────┬──────┘ │  │  └──────┬──────┘ │  │  └──────┬──────┘ │
│         │        │  │         │        │  │         │        │
│  ┌──────▼──────┐ │  │  ┌──────▼──────┐ │  │  ┌──────▼──────┐ │
│  │  Services   │ │  │  │  Services   │ │  │  │Saga Orchestr│ │
│  │  - Student  │ │  │  │  - Book     │  │  │  - Issue    │ │
│  │  - Card     │ │  │  │  - Author   │  │  │  - Return   │ │
│  └──────┬──────┘ │  │  └──────┬──────┘ │  │  └──────┬──────┘ │
│         │        │  │         │        │  │         │        │
│  ┌──────▼──────┐ │  │  ┌──────▼──────┐ │  │  ┌──────▼──────┐ │
│  │Repositories │ │  │  │Repositories │ │  │  │Repositories │ │
│  └──────┬──────┘ │  │  └──────┬──────┘ │  │  └──────┬──────┘ │
│         │        │  │         │        │  │         │        │
│  ┌──────▼──────┐ │  │  ┌──────▼──────┐ │  │  ┌──────▼──────┐ │
│  │ Student DB  │ │  │  │  Book DB    │ │  │  │Transaction DB│ │
│  │  (MySQL)    │ │  │  │  (MySQL)    │ │  │  │  (MySQL)    │ │
│  └─────────────┘ │  │  └─────────────┘ │  │  └─────────────┘ │
└───────────────────┘  └───────────────────┘  └───────────────────┘
         │                      │                      │
         └──────────────────────┼──────────────────────┘
                                │
                                │ Service-to-Service Communication
                                │ (REST via Feign Client)
                                │
┌─────────────────────────────────────────────────────────────────────────┐
│                      Infrastructure Services Layer                       │
│                                                                          │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐  ┌────────────┐ │
│  │Config Server │  │Service Disc. │  │Distributed   │  │Circuit     │ │
│  │(Spring Cloud)│  │(Eureka)      │  │Tracing       │  │Breaker     │ │
│  │              │  │              │  │(Sleuth+Zipkin│  │(Resilience4j│ │
│  └──────────────┘  └──────────────┘  └──────────────┘  └────────────┘ │
│                                                                          │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐                 │
│  │Auth Service  │  │Monitoring    │  │Logging       │                 │
│  │(Keycloak)    │  │(Prometheus+  │  │(ELK Stack)   │                 │
│  │              │  │ Grafana)     │  │              │                 │
│  └──────────────┘  └──────────────┘  └──────────────┘                 │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## Component Mapping Matrix

| Current Component | Current Technology | Target Component | Target Technology | Transformation Type | Notes |
|-------------------|-------------------|------------------|-------------------|---------------------|-------|
| **Student Entity** | JPA Entity (Java 11) | Student Aggregate | JPA Entity (Java 11/17) | Replatform | Remains JPA but isolated in Student Service |
| **Card Entity** | JPA Entity (Java 11) | Part of Student Aggregate | JPA Entity (Java 11/17) | Replatform | Lifecycle managed by Student Service |
| **Book Entity** | JPA Entity (Java 11) | Book Aggregate | JPA Entity (Java 11/17) | Replatform | Isolated in Book Service |
| **Author Entity** | JPA Entity (Java 11) | Part of Book Aggregate | JPA Entity (Java 11/17) | Replatform | Lifecycle managed by Book Service |
| **Transaction Entity** | JPA Entity (Java 11) | Transaction Aggregate | JPA Entity (Java 11/17) | Refactor | Saga orchestration added |
| **StudentController** | Spring MVC | Student Service REST API | Spring MVC | Replatform | Exposed via API Gateway |
| **BookController** | Spring MVC | Book Service REST API | Spring MVC | Replatform | Exposed via API Gateway |
| **TransactionController** | Spring MVC | Transaction Service REST API | Spring MVC | Refactor | Saga endpoints added |
| **StudentService** | Spring Service | Student Service Business Logic | Spring Service | Replatform | Isolated service layer |
| **BookService** | Spring Service | Book Service Business Logic | Spring Service | Replatform | Isolated service layer |
| **TransactionService** | Spring Service | Transaction Saga Orchestrator | Spring Service + Saga | Refactor | Distributed transaction logic |
| **Repositories** | Spring Data JPA | Per-Service Repositories | Spring Data JPA | Replatform | One repo per service DB |
| **Single MySQL DB** | MySQL 8.0 | 3 MySQL Databases | MySQL 8.0 | Refactor | student_db, book_db, transaction_db |
| **application.properties** | Properties file | Spring Cloud Config | Git-backed config | Rehost | Externalized configuration |
| **N/A** | Not present | API Gateway | Spring Cloud Gateway | Repurchase | New component |
| **N/A** | Not present | Service Discovery | Netflix Eureka | Repurchase | New component |
| **N/A** | Not present | Authentication | Keycloak OAuth2/JWT | Repurchase | New component |
| **N/A** | Not present | Distributed Tracing | Sleuth + Zipkin | Repurchase | New component |
| **N/A** | Not present | Circuit Breaker | Resilience4j | Repurchase | New component |

---

## Architectural Patterns

### 1. Microservices Pattern
**Rationale:** Decompose monolith into independently deployable services aligned with business domains (Student, Book, Transaction).

**Benefits:**
- Independent scaling per service
- Technology flexibility per service
- Fault isolation
- Parallel development

**Trade-offs:**
- Increased operational complexity
- Distributed system challenges (network latency, partial failures)
- Data consistency complexity

### 2. API Gateway Pattern
**Rationale:** Single entry point for all client requests, handling cross-cutting concerns.

**Implementation:** Spring Cloud Gateway

**Responsibilities:**
- Request routing to appropriate microservice
- Authentication/Authorization (OAuth2/JWT validation)
- Rate limiting and throttling
- CORS policy enforcement
- Request/response transformation
- API versioning support

### 3. Service Discovery Pattern
**Rationale:** Dynamic service registration and discovery for resilient inter-service communication.

**Implementation:** Netflix Eureka

**Benefits:**
- No hardcoded service URLs
- Automatic failover
- Load balancing
- Health checking

### 4. Saga Pattern (Orchestration)
**Rationale:** Manage distributed transactions across Student, Book, and Transaction services.

**Implementation:** Transaction Service as Saga Orchestrator

**Use Cases:**
- **Issue Book:** Validate card (Student Service) → Check availability (Book Service) → Create transaction (Transaction Service)
- **Return Book:** Validate transaction (Transaction Service) → Update book (Book Service) → Calculate fine (Transaction Service)

**Compensating Transactions:**
- Issue failure: Release book, deactivate transaction
- Return failure: Mark book unavailable, reverse fine

### 5. Database per Service Pattern
**Rationale:** Each microservice owns its data, ensuring loose coupling and independent evolution.

**Implementation:**
- **student_db:** Student and Card tables
- **book_db:** Book and Author tables
- **transaction_db:** Transaction table (stores cardId and bookId as foreign references, not FKs)

**Data Consistency:** Eventual consistency via Saga pattern

### 6. Circuit Breaker Pattern
**Rationale:** Prevent cascading failures when services are unavailable.

**Implementation:** Resilience4j

**Configuration:**
- Failure threshold: 50% failures in 10 requests
- Open circuit duration: 30 seconds
- Fallback methods for critical operations

### 7. Strangler Fig Pattern
**Rationale:** Gradual migration with zero downtime.

**Implementation:**
- Deploy microservices alongside monolith
- API Gateway routes traffic: 10% → 50% → 100% to microservices
- Maintain monolith as fallback for 30 days
- Decommission monolith after stability proven

---

## Technology Stack

### Core Services

| Layer | Technology | Version | Rationale |
|-------|-----------|---------|-----------|
| **Language** | Java | 11 (initial), 17+ (optional upgrade) | Maintain current language, option to upgrade |
| **Framework** | Spring Boot | 2.7.x or 3.x | Mature microservices support, wide adoption |
| **Data Access** | Spring Data JPA | 2.7.x or 3.x | Consistent with current stack |
| **Database** | MySQL | 8.0 | Maintain current database technology |
| **Build Tool** | Maven | 3.8+ | Current build system |
| **API Documentation** | SpringDoc OpenAPI | 1.7.x | Generate OpenAPI 3.0 specs |

### Infrastructure Services

| Service | Technology | Version | Purpose |
|---------|-----------|---------|---------|
| **API Gateway** | Spring Cloud Gateway | 3.1.x | Request routing, auth, rate limiting |
| **Service Discovery** | Netflix Eureka | 3.1.x | Service registration and discovery |
| **Config Server** | Spring Cloud Config | 3.1.x | Centralized configuration management |
| **Authentication** | Keycloak | 21.x | OAuth2/JWT authentication |
| **Distributed Tracing** | Spring Cloud Sleuth + Zipkin | 3.1.x + 2.24.x | Request tracing across services |
| **Circuit Breaker** | Resilience4j | 2.0.x | Fault tolerance |
| **Monitoring** | Prometheus + Grafana | 2.45.x + 10.x | Metrics collection and visualization |
| **Logging** | ELK Stack | 8.x | Centralized log aggregation |
| **Containerization** | Docker | 24.x | Service packaging |
| **Orchestration** | Kubernetes or Docker Compose | 1.27.x or 2.x | Service deployment and management |

### Development & Testing

| Tool | Technology | Version | Purpose |
|------|-----------|---------|---------|
| **Testing** | JUnit 5 + Mockito | 5.9.x + 5.x | Unit and integration testing |
| **Contract Testing** | Spring Cloud Contract | 3.1.x | Consumer-driven contracts |
| **Load Testing** | JMeter or Gatling | 5.6.x or 3.9.x | Performance testing |
| **Security Testing** | OWASP ZAP | 2.14.x | Security scanning |
| **CI/CD** | GitHub Actions or Jenkins | Latest | Automated build and deployment |

---

## Design Decisions Log

### Decision 1: Java Version
**Decision:** Start with Java 11, optionally upgrade to Java 17+ later

**Alternatives Considered:**
- Immediate upgrade to Java 17 or 21
- Stay on Java 11 permanently

**Rationale:**
- Minimize risk by maintaining current Java version initially
- Java 11 is LTS and well-supported
- Upgrade to Java 17+ can be done post-migration as optimization

**Trade-offs:**
- Miss out on Java 17+ features (records, pattern matching, virtual threads)
- Additional migration effort if upgrading later

**Decision Owner:** Technical Lead  
**Date:** 2026-05-17

---

### Decision 2: Spring Boot Version
**Decision:** Use Spring Boot 2.7.x (latest 2.x) or 3.x depending on Java version

**Alternatives Considered:**
- Stay on Spring Boot 2.4.3
- Upgrade to Spring Boot 3.x immediately

**Rationale:**
- Spring Boot 2.7.x is latest 2.x with Java 11 support
- Spring Boot 3.x requires Java 17+ but offers better performance
- Upgrade path is well-documented

**Trade-offs:**
- Spring Boot 3.x requires Java 17+ (additional migration)
- Spring Boot 2.7.x reaches EOL in 2025 (need to plan upgrade)

**Decision Owner:** Technical Lead  
**Date:** 2026-05-17

---

### Decision 3: Database Strategy
**Decision:** Per-service MySQL databases with eventual consistency

**Alternatives Considered:**
- Shared database with schema separation
- Polyglot persistence (MySQL + MongoDB)
- Distributed database (CockroachDB, YugabyteDB)

**Rationale:**
- Maintains current MySQL expertise
- Clear data ownership per service
- Eventual consistency acceptable for library domain
- Simpler than polyglot or distributed databases

**Trade-offs:**
- No ACID transactions across services (Saga pattern required)
- Data duplication (cardId, bookId in transaction_db)
- Increased operational complexity (3 databases vs 1)

**Decision Owner:** Technical Lead + DBA  
**Date:** 2026-05-17

---

### Decision 4: Saga Pattern (Orchestration vs Choreography)
**Decision:** Saga Orchestration with Transaction Service as orchestrator

**Alternatives Considered:**
- Saga Choreography (event-driven)
- Two-Phase Commit (2PC)
- Accept eventual consistency without Saga

**Rationale:**
- Orchestration provides centralized control and visibility
- Easier to debug and monitor than choreography
- 2PC is complex and impacts performance
- Saga is industry-standard for microservices

**Trade-offs:**
- Transaction Service becomes critical dependency
- More complex than choreography for simple flows
- Requires careful design of compensating transactions

**Decision Owner:** Technical Lead  
**Date:** 2026-05-17

---

### Decision 5: API Gateway Technology
**Decision:** Spring Cloud Gateway

**Alternatives Considered:**
- Netflix Zuul
- Kong
- Nginx + custom routing

**Rationale:**
- Native Spring ecosystem integration
- Reactive (non-blocking) architecture
- Active development and community support
- Zuul 1.x is deprecated, Zuul 2.x less mature

**Trade-offs:**
- Tied to Spring ecosystem
- Learning curve for reactive programming
- Less feature-rich than Kong for advanced use cases

**Decision Owner:** Technical Lead  
**Date:** 2026-05-17

---

### Decision 6: Authentication Strategy
**Decision:** OAuth2/JWT with Keycloak

**Alternatives Considered:**
- Spring Security with custom JWT
- Auth0 (SaaS)
- AWS Cognito

**Rationale:**
- Keycloak is open-source and self-hosted
- Industry-standard OAuth2/OIDC support
- Role-based access control (STUDENT/ADMIN)
- No vendor lock-in

**Trade-offs:**
- Operational overhead (self-hosted)
- Learning curve for Keycloak administration
- SaaS alternatives (Auth0, Cognito) offer less control but easier ops

**Decision Owner:** Technical Lead + Security Team  
**Date:** 2026-05-17

---

### Decision 7: Service Communication
**Decision:** Synchronous REST for queries, asynchronous events for transactions

**Alternatives Considered:**
- Pure REST (synchronous only)
- Pure event-driven (asynchronous only)
- gRPC for inter-service communication

**Rationale:**
- REST is familiar to team and well-supported
- Events decouple services for transaction flows
- Hybrid approach balances simplicity and decoupling
- gRPC adds complexity without clear benefit for this scale

**Trade-offs:**
- Two communication patterns to maintain
- Event infrastructure (Kafka/RabbitMQ) adds operational complexity
- REST can be chatty for complex operations

**Decision Owner:** Technical Lead  
**Date:** 2026-05-17

---

### Decision 8: Deployment Strategy
**Decision:** Docker containers with Kubernetes (or Docker Compose for dev/staging)

**Alternatives Considered:**
- Traditional VM deployment
- Serverless (AWS Lambda, Azure Functions)
- Platform-as-a-Service (Heroku, Cloud Foundry)

**Rationale:**
- Containers provide consistency across environments
- Kubernetes is industry-standard for microservices orchestration
- Docker Compose simplifies local development
- Serverless not suitable for stateful services

**Trade-offs:**
- Kubernetes has steep learning curve
- Operational complexity (cluster management, networking)
- Docker Compose sufficient for small-scale deployments

**Decision Owner:** Technical Lead + DevOps  
**Date:** 2026-05-17

---

## Scalability Considerations

### Horizontal Scaling
- **Student Service:** Scale based on user registration load (typically low)
- **Book Service:** Scale based on catalog search traffic (read-heavy, cache-friendly)
- **Transaction Service:** Scale based on issue/return operations (write-heavy, peak during library hours)

### Caching Strategy
- **Book Service:** Cache book catalog and author data (Redis or in-memory)
- **Student Service:** Cache student profiles (short TTL)
- **Transaction Service:** No caching (transactional data)

### Database Optimization
- **Indexes:** Add indexes on frequently queried fields (email, bookId, cardId)
- **Connection Pooling:** Configure HikariCP with appropriate pool sizes
- **Read Replicas:** Consider read replicas for Book Service (read-heavy)

---

## Security Architecture

### Authentication Flow
1. Client requests token from Keycloak (username/password)
2. Keycloak validates credentials and returns JWT
3. Client includes JWT in Authorization header for API requests
4. API Gateway validates JWT signature and expiration
5. Gateway forwards request to microservice with user context
6. Microservice enforces authorization (STUDENT vs ADMIN roles)

### Authorization Model
- **STUDENT Role:**
  - Create/update own student profile
  - Search books
  - Issue/return books (own card only)
  - View own transaction history

- **ADMIN Role:**
  - All STUDENT permissions
  - Create/update/delete any student
  - Create/update/delete books and authors
  - View all transactions
  - Generate reports

### Security Best Practices
- **Secrets Management:** Use Kubernetes Secrets or HashiCorp Vault
- **HTTPS Only:** Enforce TLS for all external communication
- **Service-to-Service Auth:** Mutual TLS or JWT propagation
- **Input Validation:** Bean Validation (JSR-303) on all endpoints
- **SQL Injection Prevention:** Parameterized queries (JPA handles this)
- **CORS:** Restrict to known origins in API Gateway

---

## Performance Targets

| Metric | Target | Measurement Method |
|--------|--------|-------------------|
| **API Response Time (P95)** | <200ms | APM tools (Prometheus) |
| **Throughput** | 100 req/sec per service | Load testing (JMeter) |
| **Database Query Time (P95)** | <50ms | Database monitoring |
| **Service Startup Time** | <30 seconds | Container logs |
| **Memory per Service** | <512MB | Container metrics |
| **CPU per Service** | <1 core | Container metrics |

---

## Disaster Recovery & High Availability

### High Availability
- **Service Redundancy:** Minimum 2 replicas per service
- **Database Replication:** Master-slave replication for each database
- **Load Balancing:** Kubernetes Service or external load balancer
- **Health Checks:** Liveness and readiness probes

### Backup Strategy
- **Database Backups:** Daily full backup, hourly incremental
- **Configuration Backups:** Git repository for Config Server
- **Retention:** 30 days for databases, indefinite for config

### Disaster Recovery
- **RTO (Recovery Time Objective):** 4 hours
- **RPO (Recovery Point Objective):** 1 hour
- **Failover:** Automated failover to standby database replicas
- **Rollback:** Maintain monolith as fallback for 30 days post-cutover

---

## Migration Phases Alignment

### Phase 1: Foundation (Weeks 1-4)
- Deploy infrastructure services (Config, Eureka, Gateway, Keycloak)
- Set up monitoring and logging
- Develop test suite for monolith

### Phase 2: Read Services (Weeks 5-8)
- Migrate Student Service (read operations)
- Migrate Book Service (read operations)
- Run in parallel with monolith

### Phase 3: Write Services (Weeks 9-16)
- Migrate Transaction Service with Saga pattern
- Implement compensating transactions
- Extensive integration testing

### Phase 4: Data Migration (Weeks 17-19)
- Decompose database schema
- Implement data synchronization
- Validate data consistency

### Phase 5: Cutover (Weeks 20-22)
- Gradual traffic shift (10% → 50% → 100%)
- Monitor and rollback if needed
- Decommission monolith after 30-day stability

---

## Validation Checklist

- [x] Architecture diagram clearly shows all major components
- [x] All current components mapped to target architecture
- [x] Technology stack versions specified
- [x] Architectural patterns identified with rationale
- [x] Design decisions documented with alternatives and trade-offs
- [x] Architecture aligns with microservices best practices
- [x] Scalability and performance requirements addressed
- [x] Security requirements incorporated
- [x] Disaster recovery and high availability planned
- [x] Migration phases aligned with architecture

---

**Document Owner:** Technical Lead  
**Reviewers:** Development Team, DevOps, Security Team  
**Approval Status:** Pending Review  
**Next Steps:** Review and approve, then proceed to Component Strategies design