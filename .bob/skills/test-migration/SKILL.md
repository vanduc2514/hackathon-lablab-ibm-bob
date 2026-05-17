---
name: test-migration
description: Migrate and update test suites for target framework. Updates test dependencies, refactors test code, and ensures test coverage is maintained or improved.
---

# Test Migration

You are migrating test suites to work with the target framework. Your goal is to update test code, dependencies, and configurations while maintaining or improving test coverage.

## Objective

Transform existing tests to use target framework's testing tools, update assertions and mocking patterns, and ensure all tests pass with equivalent or better coverage.

## Required Inputs

- `design_testing_strategy.md`: Testing approach and coverage targets
- `design_component_strategies.md`: Component migration strategies
- Existing test suites (unit, integration, e2e)
- Test coverage reports from current system

## Output Artifacts

Create `execution_test_migration.md` documenting:

**Test Framework Changes**: Updates to testing libraries and tools

**Test Code Modifications**: Patterns used to migrate test code

**Coverage Comparison**: Before/after coverage metrics

**New Tests Added**: Tests created for new functionality or gaps

**Test Execution Results**: Pass/fail status and performance

## Migration Process

1. **Analyze Current Tests**: Review existing test structure and coverage
2. **Update Test Dependencies**: Migrate to target framework's test libraries
3. **Refactor Test Code**: Update imports, annotations, assertions
4. **Update Mocking**: Migrate to target framework's mocking approach
5. **Run Tests**: Execute migrated tests and fix failures
6. **Verify Coverage**: Ensure coverage meets or exceeds targets

## Example: JUnit 4 → JUnit 5 Migration

```java
// BEFORE: JUnit 4
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    
    @Before
    public void setUp() {
        // setup code
    }
    
    @Test
    public void testFindUser() {
        User user = userService.findById(1L);
        assertNotNull(user);
        assertEquals("John", user.getName());
    }
    
    @Test(expected = UserNotFoundException.class)
    public void testUserNotFound() {
        userService.findById(999L);
    }
}

// AFTER: JUnit 5
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @BeforeEach
    void setUp() {
        // setup code
    }
    
    @Test
    void shouldFindUserById() {
        User user = userService.findById(1L);
        assertNotNull(user);
        assertEquals("John", user.getName());
    }
    
    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        assertThrows(UserNotFoundException.class, 
            () -> userService.findById(999L));
    }
}
```

## Spring Boot Test Migration

```java
// BEFORE: Spring Boot 2.7
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerIntegrationTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void testGetUser() {
        ResponseEntity<User> response = restTemplate
            .getForEntity("/api/users/1", User.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}

// AFTER: Spring Boot 3.2
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIntegrationTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    void shouldReturnUserWhenExists() {
        ResponseEntity<User> response = restTemplate
            .getForEntity("/api/users/1", User.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
```

## Mocking Pattern Updates

```java
// BEFORE: Mockito with JUnit 4
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
}

// AFTER: Mockito with JUnit 5
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
}
```

## Test Container Integration

```java
// Integration test with database
@SpringBootTest
@Testcontainers
class UserRepositoryIntegrationTest {
    
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
        .withDatabaseName("testdb")
        .withUsername("test")
        .withPassword("test");
    
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
    
    @Test
    void shouldSaveAndRetrieveUser() {
        User user = new User("John", "john@example.com");
        User saved = userRepository.save(user);
        
        Optional<User> found = userRepository.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("John", found.get().getName());
    }
}
```

## Coverage Verification

```bash
# Generate coverage report
mvn clean test jacoco:report

# Coverage thresholds
<jacoco>
    <rules>
        <rule>
            <element>BUNDLE</element>
            <limits>
                <limit>
                    <counter>LINE</counter>
                    <value>COVEREDRATIO</value>
                    <minimum>0.80</minimum>
                </limit>
            </limits>
        </rule>
    </rules>
</jacoco>
```

## Example Migration Report

```
Test Migration Execution: JUnit 4 → JUnit 5

Test Framework Updates:
✅ junit:junit 4.13.2 → org.junit.jupiter:junit-jupiter 5.10.1
✅ Added mockito-junit-jupiter 5.7.0
✅ Added testcontainers-postgresql 1.19.3

Test Code Modifications:
- Updated 127 unit tests
- Updated 45 integration tests
- Migrated 12 test utilities
- Refactored 8 test base classes

Patterns Applied:
- @Test → @Test (no change in annotation)
- @Before → @BeforeEach
- @After → @AfterEach
- @RunWith → @ExtendWith
- Assert.* → Assertions.*
- @Test(expected=...) → assertThrows(...)

Coverage Comparison:
Before: 78% line coverage, 65% branch coverage
After:  85% line coverage, 72% branch coverage
Improvement: +7% line, +7% branch

New Tests Added:
- 15 tests for edge cases discovered during migration
- 8 tests for new framework-specific behavior
- 5 integration tests using Testcontainers

Test Execution Results:
✅ Unit tests: 142/142 passed (was 127)
✅ Integration tests: 53/53 passed (was 45)
✅ Average execution time: 45s (was 52s, 13% faster)
✅ No flaky tests detected

Issues Resolved:
- Fixed 3 tests with timing dependencies
- Updated 5 tests with hardcoded assumptions
- Refactored 2 tests with framework-specific behavior
```

## Validation Checklist

- [ ] All test dependencies updated
- [ ] Test code compiles without errors
- [ ] All tests pass
- [ ] Coverage meets or exceeds targets (≥80%)
- [ ] No flaky tests
- [ ] Test execution time acceptable
- [ ] Integration tests use appropriate test containers
- [ ] Mocking patterns updated correctly

## Guardrails

**Maintain coverage**: Don't reduce test coverage during migration

**Fix, don't skip**: Fix failing tests rather than disabling them

**Update assertions**: Use target framework's assertion style consistently

**Test the tests**: Ensure tests actually validate behavior, not just pass

**Performance matters**: Monitor test execution time, optimize if needed