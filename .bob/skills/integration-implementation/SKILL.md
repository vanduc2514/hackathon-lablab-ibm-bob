---
name: integration-implementation
description: Implement integration points, API contracts, and data flows between components. Executes interface specifications from design phase.
---

# Integration Implementation

You are implementing integration points between components based on design specifications. Your goal is to establish communication patterns, implement APIs, and ensure data flows correctly.

## Objective

Execute interface contracts from design phase, implement API endpoints, configure message flows, and establish integration patterns between migrated components.

## Required Inputs

- `design_interface_contracts.md`: API specifications and integration patterns
- `design_target_architecture.md`: Component boundaries and communication protocols
- `design_component_strategies.md`: Component migration status
- API documentation or OpenAPI specifications

## Output Artifacts

Create `execution_integration_implementation.md` documenting:

**Implemented Endpoints**: List of API endpoints with status

**Integration Patterns**: Communication patterns implemented

**Data Flow Validation**: Verification of data flows between components

**Breaking Changes Handled**: How API changes were managed

**Testing Results**: Integration test results

## Implementation Process

1. **Review Contracts**: Study interface specifications from design phase
2. **Implement APIs**: Create or update API endpoints
3. **Configure Integration**: Set up message queues, event buses, etc.
4. **Handle Breaking Changes**: Implement compatibility layers if needed
5. **Test Integration**: Verify component communication
6. **Document Endpoints**: Update API documentation

## Example: REST API Implementation

```java
// Implementing interface contract from design phase

// UserController.java
@RestController
@RequestMapping("/api/v2/users")
public class UserController {
    
    private final UserService userService;
    
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(UserResponse.from(user));
    }
    
    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody CreateUserRequest request) {
        User user = userService.create(request);
        return ResponseEntity
            .created(URI.create("/api/v2/users/" + user.getId()))
            .body(UserResponse.from(user));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request) {
        User user = userService.update(id, request);
        return ResponseEntity.ok(UserResponse.from(user));
    }
}

// UserResponse.java (DTO)
public record UserResponse(
    Long id,
    String username,
    String email,
    LocalDateTime createdAt
) {
    public static UserResponse from(User user) {
        return new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getCreatedAt()
        );
    }
}
```

## Message Queue Integration

```java
// Implementing event-driven integration

// OrderEventPublisher.java
@Service
public class OrderEventPublisher {
    
    private final RabbitTemplate rabbitTemplate;
    
    public void publishOrderCreated(Order order) {
        OrderCreatedEvent event = new OrderCreatedEvent(
            order.getId(),
            order.getCustomerId(),
            order.getTotalAmount(),
            Instant.now()
        );
        
        rabbitTemplate.convertAndSend(
            "orders.exchange",
            "order.created",
            event
        );
    }
}

// OrderEventListener.java (in another service)
@Service
public class OrderEventListener {
    
    private final InventoryService inventoryService;
    
    @RabbitListener(queues = "inventory.orders.queue")
    public void handleOrderCreated(OrderCreatedEvent event) {
        inventoryService.reserveItems(event.orderId());
    }
}

// RabbitMQ Configuration
@Configuration
public class RabbitMQConfig {
    
    @Bean
    public Exchange ordersExchange() {
        return ExchangeBuilder
            .topicExchange("orders.exchange")
            .durable(true)
            .build();
    }
    
    @Bean
    public Queue inventoryOrdersQueue() {
        return QueueBuilder
            .durable("inventory.orders.queue")
            .build();
    }
    
    @Bean
    public Binding binding(Queue queue, Exchange exchange) {
        return BindingBuilder
            .bind(queue)
            .to(exchange)
            .with("order.created")
            .noargs();
    }
}
```

## gRPC Integration

```java
// Implementing gRPC service contract

// UserServiceGrpc.java (generated from proto)
@GrpcService
public class UserServiceGrpcImpl extends UserServiceGrpc.UserServiceImplBase {
    
    private final UserService userService;
    
    @Override
    public void getUser(GetUserRequest request, 
                       StreamObserver<UserResponse> responseObserver) {
        try {
            User user = userService.findById(request.getId());
            
            UserResponse response = UserResponse.newBuilder()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .build();
            
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (UserNotFoundException e) {
            responseObserver.onError(
                Status.NOT_FOUND
                    .withDescription("User not found")
                    .asRuntimeException()
            );
        }
    }
}
```

## Backward Compatibility Layer

```java
// Implementing compatibility for breaking API changes

// V1 to V2 API adapter
@RestController
@RequestMapping("/api/v1/users")
public class UserControllerV1Adapter {
    
    private final UserController v2Controller;
    
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseV1> getUser(@PathVariable Long id) {
        // Call v2 endpoint
        ResponseEntity<UserResponse> v2Response = v2Controller.getUser(id);
        
        // Transform v2 response to v1 format
        UserResponseV1 v1Response = UserResponseV1.from(v2Response.getBody());
        
        return ResponseEntity.ok(v1Response);
    }
}

// UserResponseV1.java (legacy format)
public class UserResponseV1 {
    private Long id;
    private String userName;  // Note: different field name
    private String emailAddr; // Note: different field name
    
    public static UserResponseV1 from(UserResponse v2) {
        UserResponseV1 v1 = new UserResponseV1();
        v1.setId(v2.id());
        v1.setUserName(v2.username());
        v1.setEmailAddr(v2.email());
        return v1;
    }
}
```

## Database Integration

```java
// Implementing data access layer

// UserRepository.java
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
    List<User> findByEmailContaining(String emailPart);
    
    @Query("SELECT u FROM User u WHERE u.createdAt > :since")
    List<User> findRecentUsers(@Param("since") LocalDateTime since);
}

// User.java (Entity)
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 100)
    private String username;
    
    @Column(nullable = false, unique = true, length = 255)
    private String email;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
```

## Integration Testing

```java
// Testing integration points

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    void shouldCreateAndRetrieveUser() throws Exception {
        // Create user via API
        String createRequest = """
            {
                "username": "john",
                "email": "john@example.com"
            }
            """;
        
        MvcResult createResult = mockMvc
            .perform(post("/api/v2/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createRequest))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.username").value("john"))
            .andReturn();
        
        // Extract ID from response
        String location = createResult.getResponse().getHeader("Location");
        String userId = location.substring(location.lastIndexOf('/') + 1);
        
        // Retrieve user via API
        mockMvc
            .perform(get("/api/v2/users/" + userId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("john"))
            .andExpect(jsonPath("$.email").value("john@example.com"));
    }
}
```

## Example Implementation Report

```
Integration Implementation: Quarkus → Spring Boot

Implemented Endpoints:
✅ GET /api/v2/users/{id} - Retrieve user by ID
✅ POST /api/v2/users - Create new user
✅ PUT /api/v2/users/{id} - Update user
✅ DELETE /api/v2/users/{id} - Delete user
✅ GET /api/v2/users/search - Search users

Integration Patterns:
✅ REST APIs with JSON (primary communication)
✅ RabbitMQ for async events (order processing)
✅ gRPC for internal service calls (high performance)
✅ WebSocket for real-time notifications

Data Flow Validation:
✅ User creation triggers welcome email event
✅ Order creation reserves inventory via message queue
✅ Payment processing updates order status
✅ All data flows tested end-to-end

Breaking Changes Handled:
✅ V1 API maintained via adapter pattern
✅ Field name changes mapped (userName → username)
✅ Authentication upgraded (Basic → OAuth2) with proxy
✅ Deprecation warnings added to V1 endpoints

Testing Results:
✅ Integration tests: 53/53 passed
✅ API contract tests: 28/28 passed
✅ Message flow tests: 15/15 passed
✅ Performance tests: All endpoints < 200ms P95

OpenAPI Documentation:
✅ Generated from code annotations
✅ Published to API portal
✅ Includes examples and error responses
✅ Versioned (v2.0.0)
```

## Validation Checklist

- [ ] All API endpoints from design implemented
- [ ] Integration patterns configured correctly
- [ ] Data flows validated end-to-end
- [ ] Breaking changes handled with compatibility layers
- [ ] Integration tests pass
- [ ] API documentation updated
- [ ] Error handling implemented
- [ ] Authentication/authorization working

## Guardrails

**Follow contracts**: Implement exactly what design specified

**Test integration**: Don't assume components communicate correctly

**Handle errors**: Implement proper error handling and retries

**Document deviations**: If implementation differs from design, document why

**Backward compatibility**: Maintain old APIs during transition period