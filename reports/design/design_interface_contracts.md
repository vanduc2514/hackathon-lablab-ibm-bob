# Interface & Contract Specification

**Project:** Student Library Management System  
**Migration:** Monolithic Java Spring Boot → Microservices Architecture  
**Date:** 2026-05-17  
**Version:** 1.0  
**Status:** Draft — Pending Review

---

## Executive Summary

This document specifies all API contracts, data schemas, and integration patterns for the microservices architecture. It defines how services communicate, handles breaking changes, establishes versioning strategy, and ensures backward compatibility during migration. All APIs follow REST principles with OpenAPI 3.0 specifications, and data schemas are designed for per-service database ownership.

**Key Specifications:**
- **3 Microservice APIs:** Student, Book, Transaction
- **API Versioning:** URL-based (`/api/v1/...`)
- **Authentication:** OAuth2/JWT via API Gateway
- **Data Format:** JSON (application/json)
- **Error Handling:** RFC 7807 Problem Details
- **Breaking Changes:** 6-month deprecation period

---

## API Specifications

### Student Service API

**Base URL:** `/api/v1/students`  
**Authentication:** OAuth2 Bearer token  
**Content-Type:** application/json

#### Endpoints

##### 1. Create Student
```
POST /api/v1/students
Authorization: Bearer {jwt_token}
Content-Type: application/json

Request Body:
{
  "emailId": "student@example.com",
  "name": "John Doe",
  "age": 20,
  "country": "USA"
}

Response: 201 Created
{
  "id": 1,
  "emailId": "student@example.com",
  "name": "John Doe",
  "age": 20,
  "country": "USA",
  "card": {
    "id": 1,
    "status": "ACTIVATED",
    "createdOn": "2026-05-17T10:00:00Z",
    "updatedOn": "2026-05-17T10:00:00Z"
  },
  "createdOn": "2026-05-17T10:00:00Z",
  "updatedOn": "2026-05-17T10:00:00Z",
  "_links": {
    "self": { "href": "/api/v1/students/1" },
    "card": { "href": "/api/v1/students/1/card" }
  }
}

Error Responses:
400 Bad Request - Invalid input
401 Unauthorized - Missing or invalid token
409 Conflict - Email already exists
```

##### 2. Get Student by ID
```
GET /api/v1/students/{id}
Authorization: Bearer {jwt_token}

Response: 200 OK
{
  "id": 1,
  "emailId": "student@example.com",
  "name": "John Doe",
  "age": 20,
  "country": "USA",
  "card": {
    "id": 1,
    "status": "ACTIVATED",
    "createdOn": "2026-05-17T10:00:00Z",
    "updatedOn": "2026-05-17T10:00:00Z"
  },
  "createdOn": "2026-05-17T10:00:00Z",
  "updatedOn": "2026-05-17T10:00:00Z",
  "_links": {
    "self": { "href": "/api/v1/students/1" },
    "card": { "href": "/api/v1/students/1/card" },
    "transactions": { "href": "/api/v1/transactions?cardId=1" }
  }
}

Error Responses:
401 Unauthorized - Missing or invalid token
404 Not Found - Student not found
```

##### 3. Update Student
```
PUT /api/v1/students/{id}
Authorization: Bearer {jwt_token}
Content-Type: application/json

Request Body:
{
  "emailId": "newemail@example.com",
  "name": "John Doe Updated",
  "age": 21,
  "country": "USA"
}

Response: 200 OK
{
  "id": 1,
  "emailId": "newemail@example.com",
  "name": "John Doe Updated",
  "age": 21,
  "country": "USA",
  "card": { ... },
  "updatedOn": "2026-05-17T11:00:00Z",
  "_links": { ... }
}

Error Responses:
400 Bad Request - Invalid input
401 Unauthorized - Missing or invalid token
403 Forbidden - Not authorized to update this student
404 Not Found - Student not found
```

##### 4. Delete Student
```
DELETE /api/v1/students/{id}
Authorization: Bearer {jwt_token}

Response: 204 No Content

Error Responses:
401 Unauthorized - Missing or invalid token
403 Forbidden - Not authorized to delete this student
404 Not Found - Student not found
409 Conflict - Student has active transactions
```

##### 5. Get Student Card
```
GET /api/v1/students/{id}/card
Authorization: Bearer {jwt_token}

Response: 200 OK
{
  "id": 1,
  "studentId": 1,
  "status": "ACTIVATED",
  "createdOn": "2026-05-17T10:00:00Z",
  "updatedOn": "2026-05-17T10:00:00Z",
  "_links": {
    "self": { "href": "/api/v1/students/1/card" },
    "student": { "href": "/api/v1/students/1" }
  }
}
```

---

### Book Service API

**Base URL:** `/api/v1/books`  
**Authentication:** OAuth2 Bearer token  
**Content-Type:** application/json

#### Endpoints

##### 1. Create Book
```
POST /api/v1/books
Authorization: Bearer {jwt_token}
Content-Type: application/json

Request Body:
{
  "name": "Introduction to Physics",
  "genre": "PHYSICS",
  "authorId": 1
}

Response: 201 Created
{
  "id": 1,
  "name": "Introduction to Physics",
  "genre": "PHYSICS",
  "available": true,
  "author": {
    "id": 1,
    "name": "Dr. Smith",
    "email": "smith@example.com",
    "age": 45,
    "country": "USA"
  },
  "_links": {
    "self": { "href": "/api/v1/books/1" },
    "author": { "href": "/api/v1/authors/1" }
  }
}

Error Responses:
400 Bad Request - Invalid input
401 Unauthorized - Missing or invalid token
404 Not Found - Author not found
```

##### 2. Search Books
```
GET /api/v1/books?genre={genre}&author={author}&available={true|false}
Authorization: Bearer {jwt_token}

Query Parameters:
- genre (optional): PHYSICS, MATHEMATICS, COMPUTER_SCIENCE, etc.
- author (optional): Author name (partial match)
- available (optional): true or false

Response: 200 OK
{
  "books": [
    {
      "id": 1,
      "name": "Introduction to Physics",
      "genre": "PHYSICS",
      "available": true,
      "author": {
        "id": 1,
        "name": "Dr. Smith",
        "email": "smith@example.com"
      },
      "_links": {
        "self": { "href": "/api/v1/books/1" }
      }
    }
  ],
  "page": {
    "size": 20,
    "totalElements": 1,
    "totalPages": 1,
    "number": 0
  },
  "_links": {
    "self": { "href": "/api/v1/books?genre=PHYSICS&available=true" }
  }
}
```

##### 3. Get Book by ID
```
GET /api/v1/books/{id}
Authorization: Bearer {jwt_token}

Response: 200 OK
{
  "id": 1,
  "name": "Introduction to Physics",
  "genre": "PHYSICS",
  "available": true,
  "author": {
    "id": 1,
    "name": "Dr. Smith",
    "email": "smith@example.com",
    "age": 45,
    "country": "USA"
  },
  "_links": {
    "self": { "href": "/api/v1/books/1" },
    "author": { "href": "/api/v1/authors/1" }
  }
}
```

##### 4. Create Author
```
POST /api/v1/authors
Authorization: Bearer {jwt_token}
Content-Type: application/json

Request Body:
{
  "name": "Dr. Smith",
  "email": "smith@example.com",
  "age": 45,
  "country": "USA"
}

Response: 201 Created
{
  "id": 1,
  "name": "Dr. Smith",
  "email": "smith@example.com",
  "age": 45,
  "country": "USA",
  "_links": {
    "self": { "href": "/api/v1/authors/1" },
    "books": { "href": "/api/v1/books?author=Dr.%20Smith" }
  }
}
```

---

### Transaction Service API

**Base URL:** `/api/v1/transactions`  
**Authentication:** OAuth2 Bearer token  
**Content-Type:** application/json

#### Endpoints

##### 1. Issue Book (Saga Orchestration)
```
POST /api/v1/transactions/issue
Authorization: Bearer {jwt_token}
Content-Type: application/json

Request Body:
{
  "cardId": 1,
  "bookId": 1
}

Response: 202 Accepted (Saga initiated)
{
  "transactionId": "550e8400-e29b-41d4-a716-446655440000",
  "status": "PENDING",
  "cardId": 1,
  "bookId": 1,
  "isIssueOperation": true,
  "transactionDate": "2026-05-17T12:00:00Z",
  "_links": {
    "self": { "href": "/api/v1/transactions/550e8400-e29b-41d4-a716-446655440000" },
    "status": { "href": "/api/v1/transactions/550e8400-e29b-41d4-a716-446655440000/status" }
  }
}

Saga Steps:
1. Validate card status (Student Service)
2. Check book availability (Book Service)
3. Check max books limit (Student Service)
4. Mark book unavailable (Book Service)
5. Create transaction record (Transaction Service)

Response: 200 OK (Saga completed)
{
  "transactionId": "550e8400-e29b-41d4-a716-446655440000",
  "status": "SUCCESSFUL",
  "cardId": 1,
  "bookId": 1,
  "isIssueOperation": true,
  "transactionDate": "2026-05-17T12:00:00Z",
  "fineAmount": 0,
  "_links": {
    "self": { "href": "/api/v1/transactions/550e8400-e29b-41d4-a716-446655440000" }
  }
}

Error Responses:
400 Bad Request - Invalid input
401 Unauthorized - Missing or invalid token
404 Not Found - Card or book not found
409 Conflict - Card deactivated, book unavailable, or max books reached
500 Internal Server Error - Saga orchestration failed
```

##### 2. Return Book (Saga Orchestration)
```
POST /api/v1/transactions/return
Authorization: Bearer {jwt_token}
Content-Type: application/json

Request Body:
{
  "cardId": 1,
  "bookId": 1
}

Response: 202 Accepted (Saga initiated)
{
  "transactionId": "660e8400-e29b-41d4-a716-446655440001",
  "status": "PENDING",
  "cardId": 1,
  "bookId": 1,
  "isIssueOperation": false,
  "transactionDate": "2026-05-17T14:00:00Z",
  "_links": {
    "self": { "href": "/api/v1/transactions/660e8400-e29b-41d4-a716-446655440001" },
    "status": { "href": "/api/v1/transactions/660e8400-e29b-41d4-a716-446655440001/status" }
  }
}

Saga Steps:
1. Find latest issue transaction (Transaction Service)
2. Calculate fine based on days overdue (Transaction Service)
3. Mark book available (Book Service)
4. Create return transaction record (Transaction Service)

Response: 200 OK (Saga completed)
{
  "transactionId": "660e8400-e29b-41d4-a716-446655440001",
  "status": "SUCCESSFUL",
  "cardId": 1,
  "bookId": 1,
  "isIssueOperation": false,
  "transactionDate": "2026-05-17T14:00:00Z",
  "fineAmount": 25,
  "_links": {
    "self": { "href": "/api/v1/transactions/660e8400-e29b-41d4-a716-446655440001" }
  }
}
```

##### 3. Get Transaction Status
```
GET /api/v1/transactions/{transactionId}/status
Authorization: Bearer {jwt_token}

Response: 200 OK
{
  "transactionId": "550e8400-e29b-41d4-a716-446655440000",
  "status": "SUCCESSFUL",
  "sagaState": "COMPLETED",
  "steps": [
    {
      "step": "VALIDATE_CARD",
      "status": "COMPLETED",
      "timestamp": "2026-05-17T12:00:01Z"
    },
    {
      "step": "CHECK_AVAILABILITY",
      "status": "COMPLETED",
      "timestamp": "2026-05-17T12:00:02Z"
    },
    {
      "step": "CHECK_LIMIT",
      "status": "COMPLETED",
      "timestamp": "2026-05-17T12:00:03Z"
    },
    {
      "step": "MARK_UNAVAILABLE",
      "status": "COMPLETED",
      "timestamp": "2026-05-17T12:00:04Z"
    },
    {
      "step": "CREATE_TRANSACTION",
      "status": "COMPLETED",
      "timestamp": "2026-05-17T12:00:05Z"
    }
  ]
}
```

---

## Data Schemas

### Student Service Database (student_db)

#### Student Table
```sql
CREATE TABLE student (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email_id VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    country VARCHAR(100) NOT NULL,
    card_id INT,
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (card_id) REFERENCES card(id)
);

CREATE INDEX idx_student_email ON student(email_id);
```

#### Card Table
```sql
CREATE TABLE card (
    id INT AUTO_INCREMENT PRIMARY KEY,
    status ENUM('ACTIVATED', 'DEACTIVATED') NOT NULL DEFAULT 'ACTIVATED',
    created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

---

### Book Service Database (book_db)

#### Book Table
```sql
CREATE TABLE book (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    genre ENUM('PHYSICS', 'MATHEMATICS', 'COMPUTER_SCIENCE', 'LITERATURE', 'HISTORY') NOT NULL,
    available BOOLEAN NOT NULL DEFAULT TRUE,
    author_id INT NOT NULL,
    FOREIGN KEY (author_id) REFERENCES author(id)
);

CREATE INDEX idx_book_genre ON book(genre);
CREATE INDEX idx_book_available ON book(available);
CREATE INDEX idx_book_author ON book(author_id);
```

#### Author Table
```sql
CREATE TABLE author (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    age INT NOT NULL,
    country VARCHAR(100) NOT NULL
);

CREATE INDEX idx_author_email ON author(email);
```

---

### Transaction Service Database (transaction_db)

#### Transaction Table
```sql
CREATE TABLE transaction (
    id INT AUTO_INCREMENT PRIMARY KEY,
    transaction_id VARCHAR(36) NOT NULL UNIQUE,
    card_id INT NOT NULL,
    book_id INT NOT NULL,
    is_issue_operation BOOLEAN NOT NULL,
    transaction_status ENUM('PENDING', 'SUCCESSFUL', 'FAILED') NOT NULL,
    fine_amount INT DEFAULT 0,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    saga_state VARCHAR(50),
    INDEX idx_transaction_card (card_id),
    INDEX idx_transaction_book (book_id),
    INDEX idx_transaction_status (transaction_status),
    INDEX idx_transaction_date (transaction_date)
);

-- Note: card_id and book_id are NOT foreign keys (per-service database pattern)
-- Referential integrity enforced in application code
```

---

## Breaking Changes

### Change 1: Field Name Standardization
**Current (Monolith):**
- `emailId` (camelCase)
- `createdOn` (camelCase)

**Target (Microservices):**
- `emailId` (maintained for backward compatibility)
- `createdOn` (maintained for backward compatibility)

**Decision:** No breaking change - maintain current naming for consistency

---

### Change 2: Transaction ID Format
**Current (Monolith):**
- Integer ID (e.g., 123)

**Target (Microservices):**
- UUID string (e.g., "550e8400-e29b-41d4-a716-446655440000")

**Breaking Change:** Yes  
**Migration Path:**
1. Add `transaction_id` UUID field alongside integer `id`
2. Return both in API responses during transition period
3. Deprecate integer `id` in API responses (6 months)
4. Remove integer `id` from API responses (keep in database for internal use)

**Compatibility Layer:** API Gateway can translate UUID to integer for legacy clients

---

### Change 3: Transaction Status Values
**Current (Monolith):**
- SUCCESSFUL, PENDING, FAILED

**Target (Microservices):**
- SUCCESSFUL, PENDING, FAILED (no change)

**Decision:** No breaking change

---

### Change 4: Error Response Format
**Current (Monolith):**
```json
{
  "error": "Book not found"
}
```

**Target (Microservices - RFC 7807):**
```json
{
  "type": "https://api.library.com/errors/not-found",
  "title": "Resource Not Found",
  "status": 404,
  "detail": "Book with ID 123 not found",
  "instance": "/api/v1/books/123",
  "timestamp": "2026-05-17T12:00:00Z"
}
```

**Breaking Change:** Yes  
**Migration Path:**
1. Support both formats during transition (6 months)
2. Add `Accept` header negotiation: `application/problem+json` for new format
3. Default to new format after deprecation period

---

## Versioning Strategy

### API Versioning Approach
**Method:** URL-based versioning  
**Format:** `/api/v{version}/{resource}`  
**Example:** `/api/v1/students`, `/api/v2/students`

**Rationale:**
- Clear and explicit version in URL
- Easy to route in API Gateway
- Simple for clients to understand
- Industry standard

**Alternatives Considered:**
- Header-based versioning (less visible)
- Query parameter versioning (not RESTful)
- Content negotiation (complex for clients)

---

### Deprecation Policy

**Timeline:**
1. **Announcement:** 6 months before deprecation
2. **Warning Headers:** Add `Deprecation` and `Sunset` headers to responses
3. **Documentation:** Update API docs with migration guide
4. **Monitoring:** Track usage of deprecated endpoints
5. **Communication:** Email notifications to API consumers
6. **Sunset:** Remove deprecated version after 6 months

**Example Headers:**
```
Deprecation: true
Sunset: Wed, 17 Nov 2026 12:00:00 GMT
Link: </api/v2/students>; rel="successor-version"
```

---

## Compatibility Layers

### API Gateway Transformation

**Purpose:** Maintain backward compatibility during migration

**Transformations:**
1. **Route Translation:**
   - `/student/createStudent` → `/api/v1/students` (POST)
   - `/book/getBooks` → `/api/v1/books` (GET)

2. **Response Format:**
   - Add HAL links to responses
   - Transform error format to RFC 7807

3. **Authentication:**
   - Accept both Basic Auth (legacy) and OAuth2 (new)
   - Upgrade Basic Auth to OAuth2 using service account

**Implementation:** Spring Cloud Gateway filters

---

### Database Compatibility

**Challenge:** Monolith and microservices accessing same data during transition

**Solution:** Dual-write pattern
1. Microservices write to per-service databases
2. Synchronization service writes to monolith database
3. Monolith reads from own database
4. After cutover, disable synchronization

**Data Consistency:** Eventual consistency acceptable (library domain)

---

## Integration Patterns

### Synchronous Communication (REST)

**Use Cases:**
- Query operations (GET requests)
- Simple CRUD operations
- Real-time validation

**Technology:** Spring Cloud OpenFeign

**Example:**
```java
@FeignClient(name = "student-service")
public interface StudentServiceClient {
    @GetMapping("/api/v1/students/{id}/card")
    CardDTO getStudentCard(@PathVariable("id") int studentId);
}
```

**Circuit Breaker:** Resilience4j wraps all Feign clients

---

### Asynchronous Communication (Events)

**Use Cases:**
- Saga orchestration
- Data synchronization
- Audit logging

**Technology:** Spring Cloud Stream + Kafka (or RabbitMQ)

**Event Schema:**
```json
{
  "eventId": "uuid",
  "eventType": "BOOK_ISSUED",
  "timestamp": "2026-05-17T12:00:00Z",
  "payload": {
    "transactionId": "uuid",
    "cardId": 1,
    "bookId": 1
  },
  "metadata": {
    "source": "transaction-service",
    "correlationId": "uuid"
  }
}
```

---

### Authentication Flow

**OAuth2 Authorization Code Flow:**

```
1. Client → Keycloak: Request authorization code
   GET /auth/realms/library/protocol/openid-connect/auth
   ?client_id=library-client
   &redirect_uri=http://localhost:3000/callback
   &response_type=code
   &scope=openid profile email

2. Keycloak → Client: Authorization code

3. Client → Keycloak: Exchange code for tokens
   POST /auth/realms/library/protocol/openid-connect/token
   Content-Type: application/x-www-form-urlencoded
   
   grant_type=authorization_code
   &code={authorization_code}
   &redirect_uri=http://localhost:3000/callback
   &client_id=library-client
   &client_secret={client_secret}

4. Keycloak → Client: Access token + Refresh token
   {
     "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...",
     "refresh_token": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9...",
     "expires_in": 3600,
     "token_type": "Bearer"
   }

5. Client → API Gateway: API request with access token
   GET /api/v1/students/1
   Authorization: Bearer {access_token}

6. API Gateway → Keycloak: Validate token (cached)

7. API Gateway → Microservice: Forward request with user context
```

---

### Error Handling

**Standard Error Response (RFC 7807):**
```json
{
  "type": "https://api.library.com/errors/validation-error",
  "title": "Validation Error",
  "status": 400,
  "detail": "Email address is required",
  "instance": "/api/v1/students",
  "timestamp": "2026-05-17T12:00:00Z",
  "errors": [
    {
      "field": "emailId",
      "message": "must not be null",
      "rejectedValue": null
    }
  ]
}
```

**HTTP Status Codes:**
- 200 OK - Successful GET/PUT
- 201 Created - Successful POST
- 202 Accepted - Saga initiated
- 204 No Content - Successful DELETE
- 400 Bad Request - Validation error
- 401 Unauthorized - Missing/invalid token
- 403 Forbidden - Insufficient permissions
- 404 Not Found - Resource not found
- 409 Conflict - Business rule violation
- 500 Internal Server Error - Server error
- 503 Service Unavailable - Service down

---

## Validation Checklist

- [x] API specifications complete for all 3 services
- [x] Data schemas defined for all 3 databases
- [x] Breaking changes identified and migration paths defined
- [x] Versioning strategy established
- [x] Compatibility layers designed
- [x] Integration patterns specified (sync and async)
- [x] Authentication flow documented
- [x] Error handling standardized

---

**Document Owner:** Technical Lead  
**Reviewers:** Development Team, API Consumers  
**Approval Status:** Pending Review  
**Next Steps:** Review and approve, then proceed to Dependency Mapping design