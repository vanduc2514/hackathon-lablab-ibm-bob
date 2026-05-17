# Component Strategies Design

**Project:** Student Library Management System  
**Migration:** Monolithic Java Spring Boot → Microservices Architecture  
**Date:** 2026-05-17  
**Version:** 1.0  
**Status:** Draft — Pending Review

---

## Executive Summary

This document defines the migration strategy for each component using the 7Rs framework (Rehost, Replatform, Refactor, Repurchase, Retire, Retain, Relocate). The migration follows a phased approach with clear dependencies, risk mitigation, and rollback procedures. The strategy prioritizes low-risk components first (infrastructure, read services) before tackling high-complexity components (transaction service with Saga pattern).

**Key Strategy Distribution:**
- **Rehost:** 1 component (Configuration)
- **Replatform:** 7 components (Student, Book, Card, Author, REST API, Build System)
- **Repurchase:** 5 components (Security, Discovery, Gateway, Tracing, Circuit Breaker)
- **Refactor:** 2 components (Transaction Service, Database Schema)
- **Retire:** 0 components (all migrating)
- **Retain:** 0 components (all migrating)
- **Relocate:** 0 components

---

## Component Inventory

| Component ID | Component Name | Current Technology | Purpose | Business Criticality | Technical Complexity |
|--------------|----------------|-------------------|---------|---------------------|---------------------|
| C-001 | Student Entity | JPA Entity (Java 11) | Student data model | High | Low |
| C-002 | Card Entity | JPA Entity (Java 11) | Library card data model | High | Low |
| C-003 | Book Entity | JPA Entity (Java 11) | Book catalog data model | High | Low |
| C-004 | Author Entity | JPA Entity (Java 11) | Author data model | Medium | Low |
| C-005 | Transaction Entity | JPA Entity (Java 11) | Transaction data model | High | Medium |
| C-006 | StudentController | Spring MVC | Student REST API | High | Low |
| C-007 | BookController | Spring MVC | Book REST API | High | Low |
| C-008 | TransactionController | Spring MVC | Transaction REST API | High | Medium |
| C-009 | StudentService | Spring Service | Student business logic | High | Medium |
| C-010 | CardService | Spring Service | Card business logic | High | Low |
| C-011 | BookService | Spring Service | Book business logic | High | Low |
| C-012 | AuthorService | Spring Service | Author business logic | Medium | Low |
| C-013 | TransactionService | Spring Service | Transaction orchestration | High | High |
| C-014 | StudentRepository | Spring Data JPA | Student data access | High | Low |
| C-015 | CardRepository | Spring Data JPA | Card data access | High | Low |
| C-016 | BookRepository | Spring Data JPA | Book data access | High | Low |
| C-017 | AuthorRepository | Spring Data JPA | Author data access | Medium | Low |
| C-018 | TransactionRepository | Spring Data JPA | Transaction data access | High | Low |
| C-019 | MySQL Database | MySQL 8.0 | Single shared database | High | High |
| C-020 | Configuration | application.properties | Application config | Medium | Low |
| C-021 | Build System | Maven (single module) | Build and packaging | Medium | Medium |

---

## Strategy Matrix

| Component | Current Stack | Target Stack | 7Rs Strategy | Rationale | Risk Level | Effort | Dependencies |
|-----------|--------------|--------------|--------------|-----------|------------|--------|--------------|
| **C-020: Configuration** | application.properties | Spring Cloud Config Server | **Rehost** | Externalize config with minimal logic change | Low | S | None |
| **C-001: Student Entity** | JPA Entity | JPA Entity (Student Service) | **Replatform** | Move to Student Service, maintain JPA | Low | S | C-019 |
| **C-002: Card Entity** | JPA Entity | Part of Student Aggregate | **Replatform** | Lifecycle managed by Student Service | Low | S | C-001 |
| **C-003: Book Entity** | JPA Entity | JPA Entity (Book Service) | **Replatform** | Move to Book Service, maintain JPA | Low | S | C-019 |
| **C-004: Author Entity** | JPA Entity | Part of Book Aggregate | **Replatform** | Lifecycle managed by Book Service | Low | S | C-003 |
| **C-005: Transaction Entity** | JPA Entity | JPA Entity (Transaction Service) | **Refactor** | Add Saga orchestration logic | High | L | C-001, C-003 |
| **C-006: StudentController** | Spring MVC | Student Service REST API | **Replatform** | Expose via API Gateway | Low | M | C-009, Gateway |
| **C-007: BookController** | Spring MVC | Book Service REST API | **Replatform** | Expose via API Gateway | Low | M | C-011, Gateway |
| **C-008: TransactionController** | Spring MVC | Transaction Service REST API | **Refactor** | Add Saga endpoints | Medium | M | C-013, Gateway |
| **C-009: StudentService** | Spring Service | Student Service Business Logic | **Replatform** | Isolate in Student Service | Low | M | C-001, C-002 |
| **C-010: CardService** | Spring Service | Part of Student Service | **Replatform** | Merge into Student Service | Low | S | C-009 |
| **C-011: BookService** | Spring Service | Book Service Business Logic | **Replatform** | Isolate in Book Service | Low | M | C-003, C-004 |
| **C-012: AuthorService** | Spring Service | Part of Book Service | **Replatform** | Merge into Book Service | Low | S | C-011 |
| **C-013: TransactionService** | Spring Service | Transaction Saga Orchestrator | **Refactor** | Implement Saga pattern | High | XL | C-009, C-011 |
| **C-014: StudentRepository** | Spring Data JPA | Student Service Repository | **Replatform** | Connect to student_db | Low | S | C-019 |
| **C-015: CardRepository** | Spring Data JPA | Part of Student Service | **Replatform** | Connect to student_db | Low | S | C-014 |
| **C-016: BookRepository** | Spring Data JPA | Book Service Repository | **Replatform** | Connect to book_db | Low | S | C-019 |
| **C-017: AuthorRepository** | Spring Data JPA | Part of Book Service | **Replatform** | Connect to book_db | Low | S | C-016 |
| **C-018: TransactionRepository** | Spring Data JPA | Transaction Service Repository | **Replatform** | Connect to transaction_db | Low | S | C-019 |
| **C-019: MySQL Database** | Single MySQL DB | 3 MySQL Databases | **Refactor** | Decompose into per-service DBs | High | L | None |
| **C-021: Build System** | Single Maven module | Multi-module Maven | **Replatform** | Per-service builds | Medium | M | All services |
| **NEW: Config Server** | Not present | Spring Cloud Config | **Repurchase** | Centralized configuration | Low | M | None |
| **NEW: Service Discovery** | Not present | Netflix Eureka | **Repurchase** | Service registration | Low | M | Config Server |
| **NEW: API Gateway** | Not present | Spring Cloud Gateway | **Repurchase** | Request routing | Medium | M | Eureka |
| **NEW: Authentication** | Not present | Keycloak OAuth2/JWT | **Repurchase** | Industry-standard auth | Medium | L | Gateway |
| **NEW: Distributed Tracing** | Not present | Sleuth + Zipkin | **Repurchase** | Request tracing | Low | S | Gateway |
| **NEW: Circuit Breaker** | Not present | Resilience4j | **Repurchase** | Fault tolerance | Low | S | Services |

---

## Migration Sequence

### Phase 1: Foundation (Weeks 1-4)
**Goal:** Establish infrastructure and baseline

| Seq | Component | Strategy | Effort | Parallel Group | Validation Checkpoint |
|-----|-----------|----------|--------|----------------|----------------------|
| 1.1 | C-020: Configuration | Rehost | 0.5d | A | Config retrieved successfully |
| 1.2 | Config Server | Repurchase | 1d | A | Health check passing |
| 1.3 | Service Discovery (Eureka) | Repurchase | 1.5d | B | Service registration working |
| 1.4 | API Gateway | Repurchase | 1.5d | C | Routing test requests |
| 1.5 | Authentication (Keycloak) | Repurchase | 2d | D | JWT validation working |
| 1.6 | Distributed Tracing | Repurchase | 0.5d | E | Traces visible in Zipkin |
| 1.7 | Circuit Breaker | Repurchase | 0.5d | E | Circuit breaker configured |

**Dependencies:** 1.2 → 1.3 → 1.4 → 1.5; 1.6 and 1.7 depend on 1.4

**Validation:** All infrastructure services healthy, smoke tests passing

---

### Phase 2: Student Service (Weeks 5-6)
**Goal:** Migrate Student and Card management (read-heavy, low risk)

| Seq | Component | Strategy | Effort | Parallel Group | Validation Checkpoint |
|-----|-----------|----------|--------|----------------|----------------------|
| 2.1 | C-001: Student Entity | Replatform | 0.5d | A | Entity tests passing |
| 2.2 | C-002: Card Entity | Replatform | 0.5d | A | Entity tests passing |
| 2.3 | C-014: StudentRepository | Replatform | 0.5d | B | Repository tests passing |
| 2.4 | C-015: CardRepository | Replatform | 0.5d | B | Repository tests passing |
| 2.5 | C-009: StudentService | Replatform | 1d | C | Service tests passing |
| 2.6 | C-010: CardService | Replatform | 0.5d | C | Service tests passing |
| 2.7 | C-006: StudentController | Replatform | 1d | D | API tests passing |
| 2.8 | Student Service Integration | Replatform | 1d | E | E2E tests passing |

**Dependencies:** 2.1, 2.2 → 2.3, 2.4 → 2.5, 2.6 → 2.7 → 2.8

**Validation:** Student Service registered with Eureka, CRUD operations working, Gateway routing correctly

---

### Phase 3: Book Service (Weeks 7-8)
**Goal:** Migrate Book and Author management (read-heavy, low risk)

| Seq | Component | Strategy | Effort | Parallel Group | Validation Checkpoint |
|-----|-----------|----------|--------|----------------|----------------------|
| 3.1 | C-003: Book Entity | Replatform | 0.5d | A | Entity tests passing |
| 3.2 | C-004: Author Entity | Replatform | 0.5d | A | Entity tests passing |
| 3.3 | C-016: BookRepository | Replatform | 0.5d | B | Repository tests passing |
| 3.4 | C-017: AuthorRepository | Replatform | 0.5d | B | Repository tests passing |
| 3.5 | C-011: BookService | Replatform | 1d | C | Service tests passing |
| 3.6 | C-012: AuthorService | Replatform | 0.5d | C | Service tests passing |
| 3.7 | C-007: BookController | Replatform | 1d | D | API tests passing |
| 3.8 | Book Service Integration | Replatform | 1d | E | E2E tests passing |

**Dependencies:** 3.1, 3.2 → 3.3, 3.4 → 3.5, 3.6 → 3.7 → 3.8

**Validation:** Book Service registered with Eureka, catalog operations working, search functional

---

### Phase 4: Transaction Service (Weeks 9-12)
**Goal:** Migrate transaction management with Saga pattern (high complexity)

| Seq | Component | Strategy | Effort | Parallel Group | Validation Checkpoint |
|-----|-----------|----------|--------|----------------|----------------------|
| 4.1 | C-005: Transaction Entity | Refactor | 1d | A | Entity with Saga state |
| 4.2 | C-018: TransactionRepository | Replatform | 0.5d | B | Repository tests passing |
| 4.3 | C-013: TransactionService (Saga) | Refactor | 4d | C | Saga orchestration working |
| 4.4 | Compensating Transactions | Refactor | 2d | D | Rollback scenarios tested |
| 4.5 | C-008: TransactionController | Refactor | 1d | E | API tests passing |
| 4.6 | Transaction Service Integration | Refactor | 1.5d | F | E2E Saga tests passing |

**Dependencies:** 4.1 → 4.2 → 4.3 → 4.4 → 4.5 → 4.6; Requires Student and Book services operational

**Validation:** Saga orchestration working, compensating transactions tested, distributed transaction consistency verified

---

### Phase 5: Database Decomposition (Weeks 13-14)
**Goal:** Decompose single database into per-service databases

| Seq | Component | Strategy | Effort | Parallel Group | Validation Checkpoint |
|-----|-----------|----------|--------|----------------|----------------------|
| 5.1 | C-019: Database Schema Design | Refactor | 1d | A | Schemas designed |
| 5.2 | Create student_db | Refactor | 0.5d | B | Database created |
| 5.3 | Create book_db | Refactor | 0.5d | B | Database created |
| 5.4 | Create transaction_db | Refactor | 0.5d | B | Database created |
| 5.5 | Data Migration Scripts | Refactor | 1d | C | Scripts tested |
| 5.6 | Execute Data Migration | Refactor | 1d | D | Data migrated, validated |
| 5.7 | Remove Foreign Keys | Refactor | 0.5d | E | Referential integrity in code |

**Dependencies:** 5.1 → 5.2, 5.3, 5.4 → 5.5 → 5.6 → 5.7

**Validation:** All data migrated, zero data loss, services connected to correct databases

---

### Phase 6: Build System (Week 15)
**Goal:** Restructure build system for independent service builds

| Seq | Component | Strategy | Effort | Parallel Group | Validation Checkpoint |
|-----|-----------|----------|--------|----------------|----------------------|
| 6.1 | C-021: Multi-module Maven | Replatform | 1d | A | Build structure created |
| 6.2 | Service Build Configs | Replatform | 0.5d | B | Each service builds independently |
| 6.3 | CI/CD Pipeline Updates | Replatform | 0.5d | C | Automated builds working |

**Dependencies:** 6.1 → 6.2 → 6.3

**Validation:** Each service builds and deploys independently, Docker images created

---

## Dependency Graph

```
Foundation Phase (Infrastructure)
    ├─ Config Server (1.2)
    │   └─ Service Discovery (1.3)
    │       └─ API Gateway (1.4)
    │           ├─ Authentication (1.5)
    │           ├─ Distributed Tracing (1.6)
    │           └─ Circuit Breaker (1.7)
    │
Student Service Phase
    ├─ Student Entity (2.1) ──┐
    ├─ Card Entity (2.2) ─────┤
    │                         ├─ Student Repository (2.3) ──┐
    │                         └─ Card Repository (2.4) ─────┤
    │                                                        ├─ Student Service (2.5) ──┐
    │                                                        └─ Card Service (2.6) ─────┤
    │                                                                                    ├─ Student Controller (2.7)
    │                                                                                    └─ Integration (2.8)
    │
Book Service Phase
    ├─ Book Entity (3.1) ──┐
    ├─ Author Entity (3.2) ┤
    │                      ├─ Book Repository (3.3) ──┐
    │                      └─ Author Repository (3.4) ┤
    │                                                  ├─ Book Service (3.5) ──┐
    │                                                  └─ Author Service (3.6) ┤
    │                                                                           ├─ Book Controller (3.7)
    │                                                                           └─ Integration (3.8)
    │
Transaction Service Phase (depends on Student + Book)
    ├─ Transaction Entity (4.1)
    │   └─ Transaction Repository (4.2)
    │       └─ Transaction Service/Saga (4.3)
    │           └─ Compensating Transactions (4.4)
    │               └─ Transaction Controller (4.5)
    │                   └─ Integration (4.6)
    │
Database Decomposition Phase (depends on all services)
    ├─ Schema Design (5.1)
    │   ├─ Create student_db (5.2)
    │   ├─ Create book_db (5.3)
    │   └─ Create transaction_db (5.4)
    │       └─ Migration Scripts (5.5)
    │           └─ Execute Migration (5.6)
    │               └─ Remove FKs (5.7)
    │
Build System Phase (depends on all services)
    └─ Multi-module Maven (6.1)
        └─ Service Build Configs (6.2)
            └─ CI/CD Updates (6.3)
```

---

## Rollback Procedures

### Phase 1: Foundation Rollback
**Trigger:** Infrastructure service deployment failure or critical bug

**Procedure:**
1. Stop all infrastructure services
2. Revert to monolith-only deployment
3. Remove service discovery registrations
4. Restore original application.properties
5. Verify monolith health

**Recovery Time:** 30 minutes  
**Data Impact:** None (no data migration yet)  
**Validation:** Monolith endpoints responding, all tests passing

---

### Phase 2: Student Service Rollback
**Trigger:** Student Service failure, data inconsistency, or performance degradation

**Procedure:**
1. Route 100% traffic to monolith via API Gateway
2. Stop Student Service instances
3. Restore student and card tables from backup (if data migration occurred)
4. Verify monolith handling all student operations
5. Investigate and fix issues before retry

**Recovery Time:** 1 hour  
**Data Impact:** Potential data loss if writes occurred to Student Service  
**Validation:** Monolith CRUD operations working, no data loss confirmed

---

### Phase 3: Book Service Rollback
**Trigger:** Book Service failure, catalog search issues, or performance degradation

**Procedure:**
1. Route 100% traffic to monolith via API Gateway
2. Stop Book Service instances
3. Restore book and author tables from backup (if data migration occurred)
4. Verify monolith handling all book operations
5. Investigate and fix issues before retry

**Recovery Time:** 1 hour  
**Data Impact:** Potential data loss if writes occurred to Book Service  
**Validation:** Monolith catalog operations working, search functional

---

### Phase 4: Transaction Service Rollback
**Trigger:** Saga orchestration failure, distributed transaction inconsistency, or critical bug

**Procedure:**
1. **Immediate:** Stop accepting new transactions in Transaction Service
2. Complete in-flight Saga transactions (allow compensating transactions to finish)
3. Route 100% traffic to monolith via API Gateway
4. Stop Transaction Service instances
5. Restore transaction table from backup
6. Verify monolith handling all issue/return operations
7. Audit data consistency across Student, Book, Transaction tables
8. Fix inconsistencies manually if needed

**Recovery Time:** 2 hours  
**Data Impact:** High risk - in-flight transactions may be incomplete  
**Validation:** Monolith transactions working, data consistency verified, no orphaned records

**Critical Note:** This is the highest-risk rollback due to distributed state. Extensive testing required before production deployment.

---

### Phase 5: Database Decomposition Rollback
**Trigger:** Data migration failure, data loss, or referential integrity violations

**Procedure:**
1. **Immediate:** Stop all microservices
2. Restore single MySQL database from pre-migration backup
3. Verify row counts match pre-migration state
4. Restore foreign key constraints
5. Restart monolith
6. Run data validation queries
7. Verify all monolith operations working

**Recovery Time:** 2-4 hours (depends on database size)  
**Data Impact:** All data written to microservices since migration start will be lost  
**Validation:** Database restored, row counts match, referential integrity intact, monolith functional

**Critical Note:** This is a destructive rollback. Ensure comprehensive backups before migration.

---

### Phase 6: Build System Rollback
**Trigger:** Build failures, deployment issues, or CI/CD pipeline problems

**Procedure:**
1. Revert to single-module Maven structure in version control
2. Restore original build configuration
3. Rebuild monolith JAR
4. Redeploy monolith
5. Verify build and deployment working

**Recovery Time:** 1 hour  
**Data Impact:** None  
**Validation:** Monolith builds successfully, deployment pipeline working

---

## Risk Distribution

### Low Risk Components (Can migrate in parallel)
- Configuration (Rehost)
- Infrastructure services (Repurchase)
- Student Service (Replatform)
- Book Service (Replatform)

### Medium Risk Components (Require careful sequencing)
- API Gateway (Repurchase) - critical path
- Authentication (Repurchase) - security impact
- Build System (Replatform) - affects all services

### High Risk Components (Require extensive testing)
- Transaction Service (Refactor) - distributed transactions
- Database Decomposition (Refactor) - data integrity

**Risk Mitigation Strategy:**
- Migrate low-risk components first to build confidence
- Extensive testing for high-risk components
- Maintain monolith as fallback throughout migration
- Gradual traffic shift (10% → 50% → 100%)
- 30-day stability period before decommissioning monolith

---

## Parallel Migration Opportunities

### Parallel Group A (Foundation)
- Config Server setup
- Service Discovery setup
- Can run simultaneously

### Parallel Group B (Services)
- Student Service development
- Book Service development
- Can run simultaneously (no dependencies between them)

### Parallel Group C (Database)
- student_db creation
- book_db creation
- transaction_db creation
- Can run simultaneously

**Estimated Time Savings:** 2-3 weeks by parallelizing independent work streams

---

## Validation Checkpoints

### Checkpoint 1: Foundation Complete
- [ ] All infrastructure services deployed and healthy
- [ ] Service registration working
- [ ] API Gateway routing test requests
- [ ] Authentication flow working end-to-end
- [ ] Distributed tracing showing request paths
- [ ] Circuit breaker configured and tested

### Checkpoint 2: Student Service Complete
- [ ] Student Service registered with Eureka
- [ ] CRUD operations working via API Gateway
- [ ] Card lifecycle managed correctly
- [ ] Unit tests passing (>80% coverage)
- [ ] Integration tests passing
- [ ] Performance within ±10% of baseline

### Checkpoint 3: Book Service Complete
- [ ] Book Service registered with Eureka
- [ ] Catalog operations working via API Gateway
- [ ] Author management working
- [ ] Search functionality tested
- [ ] Unit tests passing (>80% coverage)
- [ ] Integration tests passing
- [ ] Performance within ±10% of baseline

### Checkpoint 4: Transaction Service Complete
- [ ] Transaction Service registered with Eureka
- [ ] Saga orchestration working for issue/return
- [ ] Compensating transactions tested
- [ ] Distributed transaction consistency verified
- [ ] Unit tests passing (>80% coverage)
- [ ] Integration tests passing
- [ ] Saga flow tests passing
- [ ] Performance within ±10% of baseline

### Checkpoint 5: Database Decomposition Complete
- [ ] All data migrated to per-service databases
- [ ] Zero data loss confirmed
- [ ] Row counts match pre-migration state
- [ ] Referential integrity enforced in code
- [ ] Services connected to correct databases
- [ ] Data validation queries passing

### Checkpoint 6: Build System Complete
- [ ] Each service builds independently
- [ ] Docker images created for all services
- [ ] CI/CD pipeline working
- [ ] Automated tests running in pipeline
- [ ] Deployment to staging successful

---

## Success Metrics

| Metric | Target | Measurement |
|--------|--------|-------------|
| **Migration Completion Rate** | 100% of components | Component checklist |
| **Rollback Success Rate** | 100% (all rollbacks tested) | Rollback drill results |
| **Data Migration Accuracy** | 100% (zero data loss) | Row count validation |
| **Service Uptime During Migration** | >99% | Monitoring dashboards |
| **Performance Variance** | ±10% of baseline | Load testing |
| **Test Coverage** | >80% per service | Code coverage tools |
| **Incident Count** | <5 during migration | Incident tracking |

---

## Validation Checklist

- [x] All components assigned a 7Rs strategy
- [x] Rationale documented for each strategy decision
- [x] Dependencies identified and mapped
- [x] Migration sequence is logical and respects dependencies
- [x] Rollback procedure defined per phase
- [x] Risk levels assessed and distributed across phases
- [x] Parallel migration opportunities identified
- [x] Validation checkpoints defined per phase
- [x] Success metrics specified

---

**Document Owner:** Technical Lead  
**Reviewers:** Development Team, DevOps, QA  
**Approval Status:** Pending Review  
**Next Steps:** Review and approve, then proceed to Interface Contracts design