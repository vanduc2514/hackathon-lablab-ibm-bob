---
name: interface-contract-specification
description: Design API contracts, data schemas, and integration patterns for migrated components. Specifies interfaces, breaking changes, versioning strategies, and compatibility layers.
---

# Interface & Contract Specification

You are defining the contracts and interfaces for migrated components. Your goal is to ensure clear, stable boundaries between components and external systems.

## Objective

Specify all APIs, data schemas, and integration patterns that define how components communicate, including handling of breaking changes and backward compatibility.

## Required Inputs

- `design_target_architecture.md`: Target architecture and component boundaries
- `design_component_strategies.md`: Component migration strategies
- Current API documentation or code analysis
- Integration requirements from stakeholders

## Output Document

Create `design_interface_contracts.md` containing:

**API Specifications**: OpenAPI/Swagger definitions for all REST APIs, or equivalent for other protocols

**Data Schemas**: Database schemas, message formats, event structures with field definitions and constraints

**Breaking Changes**: List of incompatible changes with migration paths for consumers

**Versioning Strategy**: API versioning approach (URL, header, content negotiation) with deprecation policy

**Compatibility Layers**: Adapters or facades needed to maintain backward compatibility during transition

**Integration Patterns**: Communication protocols (REST, gRPC, messaging), authentication flows, error handling

## Design Process

1. **Inventory Interfaces**: Identify all APIs, database schemas, message formats, events
2. **Analyze Changes**: Compare current vs target to identify breaking changes
3. **Design Contracts**: Specify target interface contracts with clear semantics
4. **Plan Compatibility**: Design adapters or versioning to support gradual migration
5. **Document Patterns**: Define standard integration patterns for consistency
6. **Validate Completeness**: Ensure all integration points are addressed

## API Design Principles

- **Consistency**: Use consistent naming, error formats, and patterns across APIs
- **Versioning**: Plan for evolution with clear versioning and deprecation strategy
- **Documentation**: Provide complete, accurate specifications (OpenAPI preferred)
- **Error Handling**: Define standard error responses with actionable messages
- **Security**: Specify authentication, authorization, and data protection requirements

## Example: REST API Migration

```
API: User Management Service

Current (v1):
- Endpoint: GET /users/{id}
- Auth: Basic authentication
- Response: XML format

Target (v2):
- Endpoint: GET /api/v2/users/{id}
- Auth: OAuth2 Bearer token
- Response: JSON with HAL links
- Breaking Changes:
  * XML removed (JSON only)
  * Basic auth deprecated (OAuth2 required)
  * Field name changes: userName → username, emailAddr → email

Migration Path:
1. Deploy v2 alongside v1
2. Maintain v1 with deprecation warnings (6 months)
3. Provide migration guide for consumers
4. Monitor v1 usage, communicate with remaining clients
5. Retire v1 after grace period

Compatibility Layer:
- API Gateway translates v1 requests to v2 format
- Response transformer converts v2 JSON to v1 XML
- Authentication proxy upgrades Basic to OAuth2 using service account
```

## Data Schema Design

```
Database: User Table Migration

Current Schema:
- user_name VARCHAR(50)
- email_addr VARCHAR(100)
- created_dt TIMESTAMP

Target Schema:
- username VARCHAR(100) NOT NULL
- email VARCHAR(255) NOT NULL UNIQUE
- created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
- updated_at TIMESTAMPTZ

Migration Strategy:
1. Add new columns alongside old (dual-write phase)
2. Backfill data from old to new columns
3. Update application to read from new columns
4. Verify data consistency
5. Drop old columns after validation period
```

## Integration Pattern Examples

**Synchronous REST**: For request-response operations requiring immediate feedback

**Asynchronous Messaging**: For event-driven workflows, decoupling, and resilience

**gRPC**: For high-performance internal service communication

**GraphQL**: For flexible client-driven data fetching (if applicable)

## Validation Checklist

- [ ] All APIs documented with OpenAPI/equivalent specifications
- [ ] Data schemas defined with types, constraints, and relationships
- [ ] Breaking changes identified with migration paths
- [ ] Versioning strategy defined and consistent
- [ ] Compatibility layers designed for gradual migration
- [ ] Authentication and authorization flows specified
- [ ] Error handling patterns standardized
- [ ] Integration patterns appropriate for use cases

## Guardrails

**Specification only**: Define contracts, don't implement them

**Consumer impact**: Always consider impact on API consumers and provide migration paths

**Standards compliance**: Follow target platform's API design standards and conventions