---
name: configuration-migration
description: Migrate configuration files, properties, and environment settings to target framework format. Updates application configs, build settings, and deployment descriptors.
---

# Configuration Migration

You are migrating configuration files to target framework format. Your goal is to transform configs while preserving functionality and following target platform conventions.

## Objective

Convert configuration files from source to target format, update property names and structures, migrate environment-specific settings, and ensure all configurations are valid.

## Required Inputs

- `design_target_architecture.md`: Target framework and conventions
- `design_component_strategies.md`: Component-specific config requirements
- Current configuration files (properties, YAML, XML, JSON)
- Environment-specific configurations

## Output Artifacts

Create `execution_configuration_changes.md` documenting:

**Configuration Mappings**: Source to target property mappings

**File Transformations**: Changes to config file structure and format

**Environment Updates**: Changes to environment-specific configs

**Validation Results**: Configuration validation and testing

**Migration Notes**: Special considerations or manual steps required

## Migration Process

1. **Inventory Configs**: Identify all configuration files and properties
2. **Map Properties**: Create mapping from source to target format
3. **Transform Files**: Convert configs to target framework format
4. **Update Environments**: Migrate dev, test, prod configurations
5. **Validate Syntax**: Ensure configs are syntactically correct
6. **Test Loading**: Verify application loads configs successfully

## Example: Quarkus → Spring Boot Configuration

```properties
# BEFORE: application.properties (Quarkus)
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=dbuser
quarkus.datasource.password=dbpass
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/mydb
quarkus.hibernate-orm.database.generation=update
quarkus.http.port=8080
quarkus.http.cors=true
quarkus.log.level=INFO
quarkus.log.category."com.example".level=DEBUG
```

```yaml
# AFTER: application.yml (Spring Boot)
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/mydb
    username: dbuser
    password: dbpass
    driver-class-name: org.postgresql.Driver
  
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
  
server:
  port: 8080

logging:
  level:
    root: INFO
    com.example: DEBUG

# CORS configuration moved to WebMvcConfigurer
```

## Spring Boot 2.7 → 3.2 Property Updates

```yaml
# BEFORE: Spring Boot 2.7
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: https://auth.example.com
  
  jpa:
    properties:
      hibernate:
        jdbc:
          lob:
            non_contextual_creation: true

# AFTER: Spring Boot 3.2
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: https://auth.example.com
  
  jpa:
    properties:
      hibernate:
        # Property removed, no longer needed in Hibernate 6
```

## Build Configuration Migration

```xml
<!-- BEFORE: Maven pom.xml (Spring Boot 2.7) -->
<properties>
    <java.version>11</java.version>
    <spring-boot.version>2.7.18</spring-boot.version>
</properties>

<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>

<!-- AFTER: Maven pom.xml (Spring Boot 3.2) -->
<properties>
    <java.version>21</java.version>
    <spring-boot.version>3.2.0</spring-boot.version>
</properties>

<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <image>
                    <builder>paketobuildpacks/builder-jammy-base:latest</builder>
                </image>
            </configuration>
        </plugin>
        <plugin>
            <groupId>org.graalvm.buildtools</groupId>
            <artifactId>native-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

## Environment-Specific Configurations

```yaml
# application-dev.yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/mydb_dev
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: create-drop

logging:
  level:
    root: DEBUG

# application-prod.yml
spring:
  datasource:
    url: ${DATABASE_URL}
    username: ${DATABASE_USERNAME}
    password: ${DATABASE_PASSWORD}
  jpa:
    show-sql: false
    hibernate:
      ddl-auto: validate

logging:
  level:
    root: WARN
    com.example: INFO
```

## Security Configuration Migration

```java
// BEFORE: Spring Security 5 (Spring Boot 2.7)
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/public/**").permitAll()
                .anyRequest().authenticated()
            .and()
            .oauth2ResourceServer()
                .jwt();
    }
}

// AFTER: Spring Security 6 (Spring Boot 3.2)
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/public/**").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(Customizer.withDefaults())
            );
        return http.build();
    }
}
```

## Docker Configuration Updates

```dockerfile
# BEFORE: Dockerfile (Java 11)
FROM openjdk:11-jre-slim
COPY target/app.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

# AFTER: Dockerfile (Java 21)
FROM eclipse-temurin:21-jre-alpine
COPY target/app.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

# OR: Multi-stage build with native compilation
FROM ghcr.io/graalvm/native-image:21 AS builder
WORKDIR /build
COPY . .
RUN ./mvnw -Pnative native:compile

FROM ubuntu:22.04
COPY --from=builder /build/target/app /app
ENTRYPOINT ["/app"]
```

## Example Migration Report

```
Configuration Migration: Quarkus → Spring Boot

File Transformations:
✅ application.properties → application.yml (converted format)
✅ Created application-dev.yml (dev environment)
✅ Created application-test.yml (test environment)
✅ Created application-prod.yml (prod environment)
✅ Updated pom.xml build configuration
✅ Migrated SecurityConfig.java to new pattern

Property Mappings Applied:
- quarkus.datasource.* → spring.datasource.*
- quarkus.hibernate-orm.* → spring.jpa.hibernate.*
- quarkus.http.port → server.port
- quarkus.log.* → logging.*
- CORS config → WebMvcConfigurer bean

Environment Updates:
- Dev: Local PostgreSQL, debug logging, create-drop schema
- Test: H2 in-memory, test logging, create-drop schema
- Prod: Environment variables, warn logging, validate schema

Validation Results:
✅ YAML syntax valid
✅ All required properties present
✅ No deprecated properties used
✅ Application starts successfully in all environments
✅ Configuration loaded correctly (verified via actuator)

Migration Notes:
- CORS configuration moved from properties to Java config
- Security configuration requires new SecurityFilterChain pattern
- Hibernate 6 removed some properties (non_contextual_creation)
- Native compilation config added for GraalVM support
```

## Validation Checklist

- [ ] All configuration files identified and migrated
- [ ] Property mappings complete and accurate
- [ ] Environment-specific configs updated
- [ ] Configuration syntax validated
- [ ] Application loads configs successfully
- [ ] No deprecated properties used
- [ ] Security configurations updated
- [ ] Build configurations functional

## Guardrails

**Preserve functionality**: Ensure migrated configs maintain same behavior

**Follow conventions**: Use target framework's naming and structure conventions

**Environment parity**: Keep environment configs consistent in structure

**Validate early**: Test config loading before running full application

**Document changes**: Note any manual steps or special considerations