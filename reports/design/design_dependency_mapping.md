# Dependency & Tool Substitution Mapping

**Project:** Student Library Management System  
**Migration:** Monolithic Java Spring Boot → Microservices Architecture  
**Date:** 2026-05-17  
**Version:** 1.0  
**Status:** Draft — Pending Review

---

## Executive Summary

This document maps all dependencies from the current monolithic application to the target microservices architecture. It identifies which dependencies to retain, upgrade, replace, or introduce, along with migration paths and risk assessments. The analysis covers Spring Boot framework, database drivers, testing libraries, build tools, and new microservices-specific dependencies.

**Key Findings:**
- **Current Dependencies:** 12 core dependencies (Spring Boot 2.x stack)
- **Target Dependencies:** 28 dependencies (Spring Boot 3.x + microservices stack)
- **Major Upgrades:** Spring Boot 2.7.x → 3.2.x, Java 11 → 17
- **New Additions:** 16 microservices-specific dependencies
- **Deprecated Removals:** 0 (all current dependencies have upgrade paths)
- **High-Risk Changes:** 2 (Spring Boot 3 migration, Saga orchestration)

---

## Current Dependency Analysis

### Monolith Dependencies (from pom.xml)

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.7.14</version>
</parent>

<properties>
    <java.version>11</java.version>
</properties>

<dependencies>
    <!-- Spring Boot Starters -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <!-- Database -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
    </dependency>
    
    <!-- Lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    
    <!-- Testing -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

### Dependency Inventory

| Dependency | Current Version | Purpose | Criticality |
|------------|----------------|---------|-------------|
| spring-boot-starter-parent | 2.7.14 | Parent POM | Critical |
| spring-boot-starter-data-jpa | 2.7.14 | JPA/Hibernate | Critical |
| spring-boot-starter-web | 2.7.14 | REST APIs | Critical |
| mysql-connector-java | 8.0.33 | MySQL driver | Critical |
| lombok | 1.18.28 | Boilerplate reduction | Medium |
| spring-boot-starter-test | 2.7.14 | Testing framework | High |
| Java | 11 | Runtime | Critical |
| Maven | 3.8+ | Build tool | Critical |

---

## Target Dependency Architecture

### Microservices Common Dependencies

**All services will share these base dependencies:**

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.2.5</version>
</parent>

<properties>
    <java.version>17</java.version>
    <spring-cloud.version>2023.0.1</spring-cloud.version>
</properties>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring-cloud.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>

<dependencies>
    <!-- Spring Boot Core -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    
    <!-- Service Discovery -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    
    <!-- Configuration -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-config</artifactId>
    </dependency>
    
    <!-- Distributed Tracing -->
    <dependency>
        <groupId>io.micrometer</groupId>
        <artifactId>micrometer-tracing-bridge-brave</artifactId>
    </dependency>
    
    <dependency>
        <groupId>io.zipkin.reporter2</groupId>
        <artifactId>zipkin-reporter-brave</artifactId>
    </dependency>
    
    <!-- Circuit Breaker -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>
    </dependency>
    
    <!-- Security -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
    </dependency>
    
    <!-- Utilities -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    
    <!-- Testing -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    
    <dependency>
        <groupId>org.springframework.security</groupId>
        <artifactId>spring-security-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

---

### Service-Specific Dependencies

#### Student Service & Book Service

```xml
<!-- Additional dependencies for data services -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

#### Transaction Service

```xml
<!-- All Student/Book dependencies plus: -->

<!-- Saga Orchestration -->
<dependency>
    <groupId>org.springframework.statemachine</groupId>
    <artifactId>spring-statemachine-core</artifactId>
    <version>3.2.0</version>
</dependency>

<!-- Async Messaging -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-stream-kafka</artifactId>
</dependency>

<!-- Or RabbitMQ alternative -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-stream-rabbit</artifactId>
</dependency>
```

#### API Gateway

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis-reactive</artifactId>
</dependency>
```

#### Config Server

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-server</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bootstrap</artifactId>
</dependency>
```

#### Eureka Server

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

---

## Dependency Migration Matrix

### Core Framework Dependencies

| Component | Current | Target | Strategy | Risk | Effort |
|-----------|---------|--------|----------|------|--------|
| Spring Boot | 2.7.14 | 3.2.5 | Upgrade | High | 5 days |
| Java | 11 | 17 | Upgrade | Medium | 2 days |
| Spring Data JPA | 2.7.14 | 3.2.5 | Upgrade | Medium | 2 days |
| Spring Web | 2.7.14 | 3.2.5 | Upgrade | Low | 1 day |
| MySQL Connector | 8.0.33 (mysql-connector-java) | 8.3.0 (mysql-connector-j) | Replace | Low | 0.5 days |
| Lombok | 1.18.28 | 1.18.32 | Upgrade | Low | 0.5 days |
| Maven | 3.8+ | 3.9+ | Upgrade | Low | 0.5 days |

**Total Core Migration Effort:** 11.5 days

---

### New Microservices Dependencies

| Component | Version | Purpose | Risk | Effort |
|-----------|---------|---------|------|--------|
| Spring Cloud Config | 2023.0.1 | Centralized configuration | Low | 2 days |
| Netflix Eureka | 2023.0.1 | Service discovery | Low | 2 days |
| Spring Cloud Gateway | 2023.0.1 | API gateway | Medium | 3 days |
| Spring Cloud OpenFeign | 2023.0.1 | REST client | Low | 2 days |
| Resilience4j | 2.2.0 | Circuit breaker | Medium | 2 days |
| Micrometer Tracing | 1.2.5 | Distributed tracing | Low | 2 days |
| Zipkin | 2.27.0 | Trace aggregation | Low | 1 day |
| Spring Security OAuth2 | 3.2.5 | Resource server | Medium | 3 days |
| Keycloak | 24.0.3 | Auth server | Medium | 3 days |
| Spring State Machine | 3.2.0 | Saga orchestration | High | 5 days |
| Spring Cloud Stream | 2023.0.1 | Event streaming | Medium | 3 days |
| Kafka | 3.7.0 | Message broker | Medium | 3 days |
| Redis | 7.2.4 | Gateway cache | Low | 1 day |
| Docker | 24.0+ | Containerization | Low | 2 days |
| Kubernetes | 1.29+ | Orchestration | Medium | 5 days |
| Helm | 3.14+ | K8s package manager | Low | 2 days |

**Total New Dependencies Effort:** 41 days

---

## Detailed Migration Paths

### 1. Spring Boot 2.7 → 3.2 Migration

**Breaking Changes:**
- Java 17 minimum requirement
- Jakarta EE namespace (javax.* → jakarta.*)
- Spring Security 6.x changes
- Actuator endpoint changes
- Property deprecations

**Migration Steps:**

1. **Update Java Version**
   ```xml
   <properties>
       <java.version>17</java.version>
   </properties>
   ```

2. **Update Spring Boot Version**
   ```xml
   <parent>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-parent</artifactId>
       <version>3.2.5</version>
   </parent>
   ```

3. **Replace javax.* imports**
   ```bash
   # Use OpenRewrite recipe
   mvn org.openrewrite.maven:rewrite-maven-plugin:run \
     -Drewrite.activeRecipes=org.openrewrite.java.spring.boot3.UpgradeSpringBoot_3_2
   ```

4. **Update Security Configuration**
   ```java
   // Old (Spring Security 5.x)
   @Override
   protected void configure(HttpSecurity http) throws Exception {
       http.authorizeRequests()...
   }
   
   // New (Spring Security 6.x)
   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       http.authorizeHttpRequests(auth -> auth...)
       return http.build();
   }
   ```

5. **Test Thoroughly**
   - Run all unit tests
   - Run integration tests
   - Manual smoke testing

**Estimated Effort:** 5 days  
**Risk Level:** High  
**Mitigation:** Use OpenRewrite for automated migration, extensive testing

---

### 2. MySQL Connector Migration

**Change:** `mysql-connector-java` → `mysql-connector-j`

**Reason:** Oracle renamed the artifact in MySQL Connector/J 8.0.31+

**Migration Steps:**

1. **Update Dependency**
   ```xml
   <!-- Old -->
   <dependency>
       <groupId>mysql</groupId>
       <artifactId>mysql-connector-java</artifactId>
       <scope>runtime</scope>
   </dependency>
   
   <!-- New -->
   <dependency>
       <groupId>com.mysql</groupId>
       <artifactId>mysql-connector-j</artifactId>
       <scope>runtime</scope>
   </dependency>
   ```

2. **No Code Changes Required** - API remains the same

**Estimated Effort:** 0.5 days  
**Risk Level:** Low  
**Mitigation:** Drop-in replacement, no API changes

---

### 3. Spring Cloud Dependencies Introduction

**New Dependency:** Spring Cloud 2023.0.1 (Leyton release train)

**Compatible with:** Spring Boot 3.2.x

**Migration Steps:**

1. **Add Dependency Management**
   ```xml
   <dependencyManagement>
       <dependencies>
           <dependency>
               <groupId>org.springframework.cloud</groupId>
               <artifactId>spring-cloud-dependencies</artifactId>
               <version>2023.0.1</version>
               <type>pom</type>
               <scope>import</scope>
           </dependency>
       </dependencies>
   </dependencyManagement>
   ```

2. **Add Individual Starters** (per service needs)

3. **Configure Bootstrap Properties**
   ```yaml
   # bootstrap.yml (for Config Server)
   spring:
     application:
       name: student-service
     cloud:
       config:
         uri: http://config-server:8888
   ```

**Estimated Effort:** 2 days per infrastructure service  
**Risk Level:** Low to Medium  
**Mitigation:** Follow Spring Cloud documentation, use starter templates

---

### 4. Saga Orchestration (Spring State Machine)

**New Dependency:** Spring State Machine 3.2.0

**Purpose:** Orchestrate distributed transactions (issue/return book)

**Migration Steps:**

1. **Add Dependency**
   ```xml
   <dependency>
       <groupId>org.springframework.statemachine</groupId>
       <artifactId>spring-statemachine-core</artifactId>
       <version>3.2.0</version>
   </dependency>
   ```

2. **Define State Machine Configuration**
   ```java
   @Configuration
   @EnableStateMachine
   public class SagaStateMachineConfig 
       extends StateMachineConfigurerAdapter<SagaState, SagaEvent> {
       
       @Override
       public void configure(StateMachineStateConfigurer<SagaState, SagaEvent> states) {
           states
               .withStates()
               .initial(SagaState.VALIDATE_CARD)
               .state(SagaState.CHECK_AVAILABILITY)
               .state(SagaState.CHECK_LIMIT)
               .state(SagaState.MARK_UNAVAILABLE)
               .state(SagaState.CREATE_TRANSACTION)
               .end(SagaState.COMPLETED)
               .end(SagaState.FAILED);
       }
       
       @Override
       public void configure(StateMachineTransitionConfigurer<SagaState, SagaEvent> transitions) {
           transitions
               .withExternal()
               .source(SagaState.VALIDATE_CARD).target(SagaState.CHECK_AVAILABILITY)
               .event(SagaEvent.CARD_VALID)
               // ... more transitions
       }
   }
   ```

3. **Implement Saga Actions**
   ```java
   @Component
   public class SagaActions {
       @Action
       public void validateCard(StateContext<SagaState, SagaEvent> context) {
           // Call Student Service
       }
       
       @Action
       public void compensateValidateCard(StateContext<SagaState, SagaEvent> context) {
           // Rollback if needed
       }
   }
   ```

**Estimated Effort:** 5 days  
**Risk Level:** High  
**Mitigation:** Start with POC, extensive testing, implement compensating transactions

---

### 5. OAuth2/JWT Security (Keycloak)

**New Dependencies:**
- Spring Security OAuth2 Resource Server
- Keycloak (external)

**Migration Steps:**

1. **Add Dependencies**
   ```xml
   <dependency>
       <groupId>org.springframework.boot</groupId>
       <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
   </dependency>
   ```

2. **Configure Resource Server**
   ```yaml
   spring:
     security:
       oauth2:
         resourceserver:
           jwt:
             issuer-uri: http://keycloak:8080/realms/library
             jwk-set-uri: http://keycloak:8080/realms/library/protocol/openid-connect/certs
   ```

3. **Configure Security**
   ```java
   @Configuration
   @EnableWebSecurity
   public class SecurityConfig {
       @Bean
       public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
           http
               .authorizeHttpRequests(auth -> auth
                   .requestMatchers("/actuator/**").permitAll()
                   .anyRequest().authenticated()
               )
               .oauth2ResourceServer(oauth2 -> oauth2.jwt());
           return http.build();
       }
   }
   ```

4. **Deploy Keycloak**
   ```yaml
   # docker-compose.yml
   keycloak:
     image: quay.io/keycloak/keycloak:24.0.3
     environment:
       KEYCLOAK_ADMIN: admin
       KEYCLOAK_ADMIN_PASSWORD: admin
     ports:
       - "8080:8080"
     command: start-dev
   ```

**Estimated Effort:** 3 days  
**Risk Level:** Medium  
**Mitigation:** Use Keycloak Docker image, follow Spring Security docs

---

## Dependency Risk Assessment

### High-Risk Dependencies

| Dependency | Risk Factor | Mitigation Strategy |
|------------|-------------|---------------------|
| Spring Boot 3.x | Breaking changes in Security, Actuator | Use OpenRewrite, extensive testing |
| Spring State Machine | Complex saga orchestration | POC first, comprehensive testing |
| Kafka/RabbitMQ | Message broker complexity | Start with RabbitMQ (simpler), migrate to Kafka later |

### Medium-Risk Dependencies

| Dependency | Risk Factor | Mitigation Strategy |
|------------|-------------|---------------------|
| Spring Cloud Gateway | Reactive programming model | Training, use WebFlux examples |
| Resilience4j | Circuit breaker configuration | Start with defaults, tune based on metrics |
| Kubernetes | Orchestration complexity | Use managed K8s (EKS/GKE/AKS) initially |

### Low-Risk Dependencies

| Dependency | Risk Factor | Mitigation Strategy |
|------------|-------------|---------------------|
| Spring Cloud Config | Well-documented | Follow Spring guides |
| Netflix Eureka | Mature technology | Use Spring Boot starter |
| Micrometer/Zipkin | Observability standard | Use Spring Boot auto-configuration |

---

## Build Tool Configuration

### Maven Multi-Module Structure

```
student-library-microservices/
├── pom.xml (parent)
├── common/
│   └── pom.xml
├── student-service/
│   └── pom.xml
├── book-service/
│   └── pom.xml
├── transaction-service/
│   └── pom.xml
├── api-gateway/
│   └── pom.xml
├── config-server/
│   └── pom.xml
└── eureka-server/
    └── pom.xml
```

### Parent POM

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.5</version>
    </parent>
    
    <groupId>com.library</groupId>
    <artifactId>student-library-microservices</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>pom</packaging>
    
    <modules>
        <module>common</module>
        <module>student-service</module>
        <module>book-service</module>
        <module>transaction-service</module>
        <module>api-gateway</module>
        <module>config-server</module>
        <module>eureka-server</module>
    </modules>
    
    <properties>
        <java.version>17</java.version>
        <spring-cloud.version>2023.0.1</spring-cloud.version>
        <lombok.version>1.18.32</lombok.version>
    </properties>
    
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
</project>
```

---

## Dependency Update Strategy

### Version Management

**Approach:** Centralized version management in parent POM

**Benefits:**
- Consistent versions across all services
- Easier dependency updates
- Reduced version conflicts

**Implementation:**
```xml
<properties>
    <spring-cloud.version>2023.0.1</spring-cloud.version>
    <resilience4j.version>2.2.0</resilience4j.version>
    <zipkin.version>2.27.0</zipkin.version>
</properties>
```

---

### Dependency Update Process

1. **Monitor for Updates**
   - Use Maven Versions Plugin: `mvn versions:display-dependency-updates`
   - Subscribe to Spring Boot release notes
   - Monitor CVE databases for security updates

2. **Test Updates**
   - Update in development environment first
   - Run full test suite
   - Perform integration testing

3. **Rollout**
   - Update parent POM version
   - Rebuild all services
   - Deploy to staging
   - Deploy to production (blue-green)

4. **Rollback Plan**
   - Keep previous Docker images
   - Revert parent POM version if issues
   - Redeploy previous version

---

## Validation Checklist

- [x] Current dependencies inventoried
- [x] Target dependencies defined for all services
- [x] Migration paths documented for major upgrades
- [x] Risk assessment completed
- [x] Effort estimates provided
- [x] Build tool configuration specified
- [x] Version management strategy defined
- [x] Dependency update process documented

---

**Document Owner:** Technical Lead  
**Reviewers:** Development Team, DevOps Team  
**Approval Status:** Pending Review  
**Next Steps:** Review and approve, then proceed to Testing Strategy design