# Testing Strategy & Quality Assurance Plan

**Project:** Student Library Management System  
**Migration:** Monolithic Java Spring Boot → Microservices Architecture  
**Date:** 2026-05-17  
**Version:** 1.0  
**Status:** Draft — Pending Review

---

## Executive Summary

This document defines the comprehensive testing strategy for the microservices migration. It establishes test coverage targets, test pyramid structure, test data management, automation approach, and quality gates. The strategy ensures that the migrated system maintains functional correctness while achieving non-functional requirements for performance, security, and reliability.

**Key Metrics:**
- **Target Test Coverage:** ≥80% line coverage per service
- **Test Pyramid Distribution:** 70% unit, 20% integration, 10% E2E
- **Total Test Count:** 60+ tests across all layers
- **Automation Level:** 100% (all tests automated in CI/CD)
- **Test Execution Time:** <5 minutes (unit), <15 minutes (integration), <30 minutes (E2E)

**Quality Gates:**
- All tests must pass before merge
- Code coverage must not decrease
- No critical/high security vulnerabilities
- Performance benchmarks must be met

---

## Test Pyramid Structure

### Overview

```
                    /\
                   /  \
                  / E2E \          10% - End-to-End Tests
                 /______\          (6 tests)
                /        \
               /Integration\       20% - Integration Tests
              /____________\       (12 tests)
             /              \
            /   Unit Tests   \     70% - Unit Tests
           /__________________\    (42 tests)
```

### Test Distribution by Service

| Service | Unit Tests | Integration Tests | E2E Tests | Total |
|---------|-----------|-------------------|-----------|-------|
| Student Service | 12 | 3 | 1 | 16 |
| Book Service | 12 | 3 | 1 | 16 |
| Transaction Service | 15 | 4 | 2 | 21 |
| API Gateway | 3 | 2 | 2 | 7 |
| **Total** | **42** | **12** | **6** | **60** |

---

## Unit Testing Strategy

### Scope
- Individual classes and methods
- Business logic validation
- Edge case handling
- Error handling

### Technology Stack
- **Framework:** JUnit 5 (Jupiter)
- **Mocking:** Mockito
- **Assertions:** AssertJ
- **Coverage:** JaCoCo

### Coverage Targets
- **Line Coverage:** ≥80%
- **Branch Coverage:** ≥75%
- **Method Coverage:** ≥85%

### Test Structure

```java
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    
    @Mock
    private StudentRepository studentRepository;
    
    @Mock
    private CardRepository cardRepository;
    
    @InjectMocks
    private StudentService studentService;
    
    @Test
    @DisplayName("Should create student with activated card")
    void shouldCreateStudentWithActivatedCard() {
        // Given
        Student student = Student.builder()
            .emailId("test@example.com")
            .name("Test Student")
            .age(20)
            .country("USA")
            .build();
        
        Card card = Card.builder()
            .status(CardStatus.ACTIVATED)
            .build();
        
        when(cardRepository.save(any(Card.class))).thenReturn(card);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        
        // When
        Student result = studentService.createStudent(student);
        
        // Then
        assertThat(result).isNotNull();
        assertThat(result.getCard()).isNotNull();
        assertThat(result.getCard().getStatus()).isEqualTo(CardStatus.ACTIVATED);
        
        verify(cardRepository).save(any(Card.class));
        verify(studentRepository).save(any(Student.class));
    }
    
    @Test
    @DisplayName("Should throw exception when email already exists")
    void shouldThrowExceptionWhenEmailExists() {
        // Given
        Student student = Student.builder()
            .emailId("existing@example.com")
            .build();
        
        when(studentRepository.findByEmailId("existing@example.com"))
            .thenReturn(Optional.of(student));
        
        // When/Then
        assertThatThrownBy(() -> studentService.createStudent(student))
            .isInstanceOf(DuplicateEmailException.class)
            .hasMessage("Student with email existing@example.com already exists");
    }
}
```

### Unit Test Checklist (per service)

**Student Service (12 tests):**
- [x] Create student with valid data
- [x] Create student with duplicate email (exception)
- [x] Get student by ID (found)
- [x] Get student by ID (not found)
- [x] Update student with valid data
- [x] Update student with duplicate email (exception)
- [x] Delete student (success)
- [x] Delete student with active transactions (exception)
- [x] Get student card (found)
- [x] Get student card (not found)
- [x] Validate student age (valid)
- [x] Validate student age (invalid)

**Book Service (12 tests):**
- [x] Create book with valid author
- [x] Create book with invalid author (exception)
- [x] Get book by ID (found)
- [x] Get book by ID (not found)
- [x] Search books by genre
- [x] Search books by author
- [x] Search books by availability
- [x] Mark book unavailable
- [x] Mark book available
- [x] Create author with valid data
- [x] Create author with duplicate email (exception)
- [x] Get books by author

**Transaction Service (15 tests):**
- [x] Issue book - happy path
- [x] Issue book - card deactivated (exception)
- [x] Issue book - book unavailable (exception)
- [x] Issue book - max books reached (exception)
- [x] Return book - happy path
- [x] Return book - no fine
- [x] Return book - with fine
- [x] Return book - not issued (exception)
- [x] Calculate fine - on time
- [x] Calculate fine - 1 day late
- [x] Calculate fine - 10 days late
- [x] Saga orchestration - all steps succeed
- [x] Saga orchestration - step fails, compensate
- [x] Get transaction by ID
- [x] Get transactions by card ID

---

## Integration Testing Strategy

### Scope
- Service-to-service communication
- Database interactions
- External API calls
- Message broker integration

### Technology Stack
- **Framework:** Spring Boot Test
- **Database:** Testcontainers (MySQL)
- **Message Broker:** Testcontainers (Kafka/RabbitMQ)
- **HTTP Client:** RestAssured or WebTestClient

### Test Structure

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class StudentServiceIntegrationTest {
    
    @Container
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0")
        .withDatabaseName("student_db_test")
        .withUsername("test")
        .withPassword("test");
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    private StudentRepository studentRepository;
    
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysql::getJdbcUrl);
        registry.add("spring.datasource.username", mysql::getUsername);
        registry.add("spring.datasource.password", mysql::getPassword);
    }
    
    @Test
    @DisplayName("Should create student via REST API and persist to database")
    void shouldCreateStudentViaRestApi() {
        // Given
        StudentDTO request = StudentDTO.builder()
            .emailId("integration@example.com")
            .name("Integration Test")
            .age(20)
            .country("USA")
            .build();
        
        // When
        ResponseEntity<StudentDTO> response = restTemplate.postForEntity(
            "/api/v1/students",
            request,
            StudentDTO.class
        );
        
        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();
        
        // Verify database persistence
        Optional<Student> savedStudent = studentRepository
            .findByEmailId("integration@example.com");
        assertThat(savedStudent).isPresent();
        assertThat(savedStudent.get().getCard()).isNotNull();
        assertThat(savedStudent.get().getCard().getStatus())
            .isEqualTo(CardStatus.ACTIVATED);
    }
}
```

### Integration Test Checklist

**Student Service (3 tests):**
- [x] Create student via REST API → Database persistence
- [x] Get student via REST API → Database retrieval
- [x] Update student via REST API → Database update

**Book Service (3 tests):**
- [x] Create book via REST API → Database persistence
- [x] Search books via REST API → Database query
- [x] Create author via REST API → Database persistence

**Transaction Service (4 tests):**
- [x] Issue book → Saga orchestration → Multiple service calls
- [x] Return book → Saga orchestration → Multiple service calls
- [x] Issue book → Publish event to Kafka/RabbitMQ
- [x] Return book → Publish event to Kafka/RabbitMQ

**API Gateway (2 tests):**
- [x] Route request to Student Service
- [x] Route request to Book Service with authentication

---

## Contract Testing Strategy

### Purpose
Ensure service contracts remain compatible during independent deployments

### Technology Stack
- **Framework:** Spring Cloud Contract
- **Approach:** Consumer-Driven Contracts

### Contract Definition

```groovy
// Student Service Contract
Contract.make {
    description "Should return student by ID"
    request {
        method GET()
        url "/api/v1/students/1"
        headers {
            header("Authorization", "Bearer token")
        }
    }
    response {
        status 200
        headers {
            contentType(applicationJson())
        }
        body([
            id: 1,
            emailId: "test@example.com",
            name: "Test Student",
            age: 20,
            country: "USA",
            card: [
                id: 1,
                status: "ACTIVATED"
            ]
        ])
    }
}
```

### Contract Test Execution

**Provider Side (Student Service):**
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureStubRunner(
    ids = "com.library:student-service:+:stubs:8080",
    stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
class StudentServiceContractTest {
    // Auto-generated tests from contracts
}
```

**Consumer Side (Transaction Service):**
```java
@SpringBootTest
@AutoConfigureStubRunner(
    ids = "com.library:student-service:+:stubs:8080",
    stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
class TransactionServiceContractTest {
    
    @Autowired
    private StudentServiceClient studentServiceClient;
    
    @Test
    void shouldGetStudentCard() {
        CardDTO card = studentServiceClient.getStudentCard(1);
        assertThat(card).isNotNull();
        assertThat(card.getStatus()).isEqualTo("ACTIVATED");
    }
}
```

---

## End-to-End Testing Strategy

### Scope
- Complete user workflows
- Cross-service transactions
- UI to database flows

### Technology Stack
- **Framework:** Cucumber (BDD) + Selenium/RestAssured
- **Environment:** Docker Compose (all services)

### Test Scenarios

#### Scenario 1: Student Registration and Book Issue
```gherkin
Feature: Student can register and issue a book

  Scenario: New student registers and issues a physics book
    Given a new student with email "e2e@example.com"
    When the student registers with name "E2E Test" and age 20
    Then the student should have an activated card
    
    When the student searches for books in genre "PHYSICS"
    Then at least one book should be available
    
    When the student issues the first available book
    Then the transaction should be successful
    And the book should be marked as unavailable
    And the student's card should show 1 issued book
```

#### Scenario 2: Book Return with Fine Calculation
```gherkin
Feature: Student returns book with fine calculation

  Scenario: Student returns book 5 days late
    Given a student with ID 1 has issued book ID 1
    And the issue date was 5 days ago
    
    When the student returns the book
    Then the transaction should be successful
    And a fine of 25 should be calculated
    And the book should be marked as available
```

### E2E Test Implementation

```java
@SpringBootTest
@Testcontainers
class StudentBookIssueE2ETest {
    
    @Container
    static DockerComposeContainer<?> environment = new DockerComposeContainer<>(
        new File("docker-compose-test.yml")
    )
        .withExposedService("api-gateway", 8080)
        .withExposedService("student-service", 8081)
        .withExposedService("book-service", 8082)
        .withExposedService("transaction-service", 8083);
    
    private String apiGatewayUrl;
    
    @BeforeEach
    void setUp() {
        apiGatewayUrl = "http://localhost:" + 
            environment.getServicePort("api-gateway", 8080);
    }
    
    @Test
    @DisplayName("E2E: Student registers, searches books, and issues a book")
    void studentRegistersAndIssuesBook() {
        // Step 1: Register student
        StudentDTO student = given()
            .contentType(ContentType.JSON)
            .body(new StudentDTO("e2e@example.com", "E2E Test", 20, "USA"))
        .when()
            .post(apiGatewayUrl + "/api/v1/students")
        .then()
            .statusCode(201)
            .extract().as(StudentDTO.class);
        
        assertThat(student.getId()).isNotNull();
        assertThat(student.getCard().getStatus()).isEqualTo("ACTIVATED");
        
        // Step 2: Search for books
        BookDTO[] books = given()
            .queryParam("genre", "PHYSICS")
            .queryParam("available", true)
        .when()
            .get(apiGatewayUrl + "/api/v1/books")
        .then()
            .statusCode(200)
            .extract().as(BookDTO[].class);
        
        assertThat(books).isNotEmpty();
        
        // Step 3: Issue book
        TransactionDTO transaction = given()
            .contentType(ContentType.JSON)
            .body(new IssueBookRequest(student.getCard().getId(), books[0].getId()))
        .when()
            .post(apiGatewayUrl + "/api/v1/transactions/issue")
        .then()
            .statusCode(200)
            .extract().as(TransactionDTO.class);
        
        assertThat(transaction.getStatus()).isEqualTo("SUCCESSFUL");
        
        // Step 4: Verify book is unavailable
        BookDTO issuedBook = given()
        .when()
            .get(apiGatewayUrl + "/api/v1/books/" + books[0].getId())
        .then()
            .statusCode(200)
            .extract().as(BookDTO.class);
        
        assertThat(issuedBook.isAvailable()).isFalse();
    }
}
```

---

## Performance Testing Strategy

### Scope
- Response time benchmarks
- Throughput capacity
- Resource utilization
- Scalability limits

### Technology Stack
- **Framework:** JMeter or Gatling
- **Monitoring:** Prometheus + Grafana

### Performance Targets

| Metric | Target | Measurement |
|--------|--------|-------------|
| API Response Time (p95) | <200ms | 95th percentile |
| API Response Time (p99) | <500ms | 99th percentile |
| Throughput | ≥100 req/sec | Per service |
| Database Query Time | <50ms | Average |
| Saga Completion Time | <2 seconds | Issue/Return book |
| Error Rate | <0.1% | Failed requests |
| CPU Utilization | <70% | Under normal load |
| Memory Utilization | <80% | Under normal load |

### Load Test Scenarios

#### Scenario 1: Normal Load
- **Users:** 50 concurrent users
- **Duration:** 10 minutes
- **Ramp-up:** 1 minute
- **Operations:** 60% reads, 40% writes

#### Scenario 2: Peak Load
- **Users:** 200 concurrent users
- **Duration:** 5 minutes
- **Ramp-up:** 30 seconds
- **Operations:** 70% reads, 30% writes

#### Scenario 3: Stress Test
- **Users:** 500 concurrent users
- **Duration:** 3 minutes
- **Ramp-up:** 10 seconds
- **Goal:** Find breaking point

### Gatling Test Script

```scala
class StudentServiceLoadTest extends Simulation {
  
  val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("application/json")
    .authorizationHeader("Bearer ${token}")
  
  val scn = scenario("Student Service Load Test")
    .exec(http("Create Student")
      .post("/api/v1/students")
      .body(StringBody("""{"emailId":"load${userId}@example.com","name":"Load Test","age":20,"country":"USA"}"""))
      .check(status.is(201))
      .check(jsonPath("$.id").saveAs("studentId"))
    )
    .pause(1)
    .exec(http("Get Student")
      .get("/api/v1/students/${studentId}")
      .check(status.is(200))
    )
    .pause(2)
    .exec(http("Search Books")
      .get("/api/v1/books?genre=PHYSICS&available=true")
      .check(status.is(200))
      .check(jsonPath("$[0].id").saveAs("bookId"))
    )
    .pause(1)
    .exec(http("Issue Book")
      .post("/api/v1/transactions/issue")
      .body(StringBody("""{"cardId":${cardId},"bookId":${bookId}}"""))
      .check(status.is(200))
    )
  
  setUp(
    scn.inject(
      rampUsers(50) during (60 seconds),
      constantUsersPerSec(10) during (600 seconds)
    )
  ).protocols(httpProtocol)
   .assertions(
     global.responseTime.percentile3.lt(500),
     global.successfulRequests.percent.gt(99)
   )
}
```

---

## Security Testing Strategy

### Scope
- Authentication/Authorization
- Input validation
- SQL injection prevention
- XSS prevention
- Sensitive data exposure

### Technology Stack
- **SAST:** SonarQube
- **DAST:** OWASP ZAP
- **Dependency Scanning:** OWASP Dependency-Check

### Security Test Checklist

**Authentication Tests:**
- [x] Access protected endpoint without token (401)
- [x] Access protected endpoint with invalid token (401)
- [x] Access protected endpoint with expired token (401)
- [x] Access protected endpoint with valid token (200)

**Authorization Tests:**
- [x] User can access own resources (200)
- [x] User cannot access other user's resources (403)
- [x] Admin can access all resources (200)

**Input Validation Tests:**
- [x] SQL injection in email field (400)
- [x] XSS in name field (400)
- [x] Invalid email format (400)
- [x] Negative age value (400)
- [x] Oversized input (400)

**Sensitive Data Tests:**
- [x] Passwords not logged
- [x] Tokens not logged
- [x] PII encrypted in database
- [x] HTTPS enforced in production

### OWASP ZAP Scan

```bash
# Run OWASP ZAP baseline scan
docker run -t owasp/zap2docker-stable zap-baseline.py \
  -t http://localhost:8080 \
  -r zap-report.html
```

---

## Test Data Management

### Strategy
- **Approach:** Test data builders + Testcontainers
- **Isolation:** Each test gets fresh database
- **Cleanup:** Automatic via Testcontainers lifecycle

### Test Data Builders

```java
public class StudentTestDataBuilder {
    
    private String emailId = "test@example.com";
    private String name = "Test Student";
    private int age = 20;
    private String country = "USA";
    
    public StudentTestDataBuilder withEmailId(String emailId) {
        this.emailId = emailId;
        return this;
    }
    
    public StudentTestDataBuilder withName(String name) {
        this.name = name;
        return this;
    }
    
    public StudentTestDataBuilder withAge(int age) {
        this.age = age;
        return this;
    }
    
    public Student build() {
        return Student.builder()
            .emailId(emailId)
            .name(name)
            .age(age)
            .country(country)
            .build();
    }
    
    public static StudentTestDataBuilder aStudent() {
        return new StudentTestDataBuilder();
    }
}

// Usage
Student student = aStudent()
    .withEmailId("custom@example.com")
    .withAge(25)
    .build();
```

### Test Data Fixtures

```java
@Component
public class TestDataFixtures {
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private BookRepository bookRepository;
    
    public Student createTestStudent() {
        Card card = Card.builder()
            .status(CardStatus.ACTIVATED)
            .build();
        
        Student student = Student.builder()
            .emailId("fixture@example.com")
            .name("Fixture Student")
            .age(20)
            .country("USA")
            .card(card)
            .build();
        
        return studentRepository.save(student);
    }
    
    public Book createTestBook() {
        Author author = Author.builder()
            .name("Test Author")
            .email("author@example.com")
            .age(45)
            .country("USA")
            .build();
        
        Book book = Book.builder()
            .name("Test Book")
            .genre(Genre.PHYSICS)
            .available(true)
            .author(author)
            .build();
        
        return bookRepository.save(book);
    }
}
```

---

## Test Automation & CI/CD Integration

### CI/CD Pipeline

```yaml
# .github/workflows/test.yml
name: Test Pipeline

on: [push, pull_request]

jobs:
  unit-tests:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - uses: actions/setup-java@v3
        with:
          java-version: '17'
      - name: Run unit tests
        run: mvn test
      - name: Generate coverage report
        run: mvn jacoco:report
      - name: Upload coverage to Codecov
        uses: codecov/codecov-action@v3
  
  integration-tests:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - uses: actions/setup-java@v3
        with:
          java-version: '17'
      - name: Run integration tests
        run: mvn verify -P integration-tests
  
  e2e-tests:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - uses: actions/setup-java@v3
        with:
          java-version: '17'
      - name: Start services
        run: docker-compose -f docker-compose-test.yml up -d
      - name: Wait for services
        run: ./wait-for-services.sh
      - name: Run E2E tests
        run: mvn verify -P e2e-tests
      - name: Stop services
        run: docker-compose -f docker-compose-test.yml down
  
  security-scan:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Run OWASP Dependency Check
        run: mvn org.owasp:dependency-check-maven:check
      - name: Run SonarQube scan
        run: mvn sonar:sonar
```

### Quality Gates

**Pre-Merge Gates:**
- All unit tests pass
- All integration tests pass
- Code coverage ≥80%
- No critical/high security vulnerabilities
- SonarQube quality gate passes

**Pre-Deployment Gates:**
- All E2E tests pass
- Performance benchmarks met
- Security scan passes
- Manual approval (production only)

---

## Test Execution Schedule

### Development Phase
- **Unit Tests:** On every commit (local + CI)
- **Integration Tests:** On every push to branch
- **Contract Tests:** On every push to branch
- **E2E Tests:** Nightly
- **Performance Tests:** Weekly

### Pre-Production Phase
- **All Tests:** On every deployment
- **Performance Tests:** Before each release
- **Security Scan:** Before each release

### Production Phase
- **Smoke Tests:** After each deployment
- **Performance Tests:** Monthly
- **Security Scan:** Weekly

---

## Test Metrics & Reporting

### Key Metrics

| Metric | Target | Tracking |
|--------|--------|----------|
| Test Coverage | ≥80% | JaCoCo + Codecov |
| Test Pass Rate | 100% | CI/CD dashboard |
| Test Execution Time | <5 min (unit) | CI/CD logs |
| Flaky Test Rate | <1% | Test history |
| Bug Escape Rate | <5% | JIRA metrics |

### Reporting Tools

- **Coverage:** JaCoCo + Codecov
- **Test Results:** JUnit XML + Allure
- **Performance:** Gatling HTML reports
- **Security:** OWASP ZAP HTML reports
- **Dashboard:** Grafana (aggregated metrics)

---

## Validation Checklist

- [x] Test pyramid structure defined (70/20/10)
- [x] Unit test strategy with 42 tests specified
- [x] Integration test strategy with 12 tests specified
- [x] Contract testing approach defined
- [x] E2E test scenarios with 6 tests specified
- [x] Performance testing strategy with targets
- [x] Security testing checklist complete
- [x] Test data management approach defined
- [x] CI/CD integration specified
- [x] Quality gates established
- [x] Test metrics and reporting defined

---

**Document Owner:** QA Lead  
**Reviewers:** Development Team, QA Team  
**Approval Status:** Pending Review  
**Next Steps:** Review and approve, then proceed to POC Recommendations design