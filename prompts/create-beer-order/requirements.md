# Requirements: Beer Order System Implementation

## Overview
Implement the `BeerOrder` and `BeerOrderLine` entities and their associated layers (Repository, Service, Mapper, Controller) based on the project ERD and Spring Boot guidelines.

## 1. Domain Model Enhancements

### 1.1. Base Entity
Create a package-private `@MappedSuperclass` named `BaseEntity` in `walshe.juniemvc.juniemvc.entities` to centralize common auditing and identity fields.
- `id`: `Integer`, `@Id`, `@GeneratedValue(strategy = GenerationType.IDENTITY)`
- `version`: `Integer`, `@Version`
- `createdDate`: `LocalDateTime`, `@CreationTimestamp`, `@Column(updatable = false)`
- `updateDate`: `LocalDateTime`, `@UpdateTimestamp`

### 1.2. Beer Entity (Existing)
Refactor the existing `Beer` entity to extend `BaseEntity`.
- Remove fields now present in `BaseEntity`.
- Maintain existing fields: `beerName`, `beerStyle`, `upc`, `quantityOnHand`, `price`.
- Add a bidirectional relationship to `BeerOrderLine`.
  - `@OneToMany(mappedBy = "beer")`
  - Use `Set<BeerOrderLine>` initialized to `HashSet`.

### 1.3. BeerOrder Entity
Create a package-private `BeerOrder` entity extending `BaseEntity`.
- Fields:
  - `customerRef`: `String`
  - `paymentAmount`: `BigDecimal`
  - `status`: `String` (Consider using an Enum later, but keep as String for now per ERD)
- Relationships:
  - `@OneToMany` with `BeerOrderLine`.
  - `cascade = CascadeType.ALL`, `orphanRemoval = true`, `fetch = FetchType.LAZY`.
  - Provide helper methods `addLine(BeerOrderLine)` and `removeLine(BeerOrderLine)` to sync both sides of the relationship.

### 1.4. BeerOrderLine Entity
Create a package-private `BeerOrderLine` entity extending `BaseEntity`.
- Fields:
  - `orderQuantity`: `Integer`
  - `quantityAllocated`: `Integer`
  - `status`: `String`
- Relationships:
  - `@ManyToOne(fetch = FetchType.LAZY, optional = false)` to `BeerOrder`.
  - `@ManyToOne(fetch = FetchType.LAZY, optional = false)` to `Beer`.

## 2. Data Transfer Objects (DTOs) & Commands

### 2.1. BeerOrderDto & BeerOrderLineDto
Define public DTOs in `walshe.juniemvc.juniemvc.models`.
- Use Java `record` for DTOs if possible, or Lombok `@Data` classes consistent with `BeerDto`.
- Ensure `BeerOrderDto` includes a collection of `BeerOrderLineDto`.
- Apply Jakarta Validation constraints (e.g., `@NotNull`, `@NotBlank`, `@Positive`).

### 2.2. Command Objects
Implement `CreateBeerOrderCommand` to wrap input data for creating orders, following Guideline #8.

## 3. Mappers
Extend `walshe.juniemvc.juniemvc.mappers` with `BeerOrderMapper`.
- Use MapStruct for conversions between Entities and DTOs.
- Handle the mapping of nested collections (`BeerOrderLine`).

## 4. Persistence Layer
Create package-private Spring Data JPA repositories in `walshe.juniemvc.juniemvc.repositories`.
- `BeerOrderRepository`
- `BeerOrderLineRepository`

## 5. Service Layer
Implement a service to handle Beer Order business logic.
- Interface: `BeerOrderService`
- Implementation: `BeerOrderServiceImpl` (package-private)
- **Transaction Boundaries**:
  - Annotate the class or methods with `@Transactional`.
  - Use `readOnly = true` for query methods.
- **Dependency Injection**: Use constructor injection for all dependencies (Guideline #1).

## 6. Web Layer
Implement `BeerOrderController` in `walshe.juniemvc.juniemvc.controllers`.
- Follow REST API design principles (Guideline #7).
- Endpoint: `/api/v1/beerOrder`
- Use `ResponseEntity<T>` for explicit HTTP status codes.
- Do not expose entities directly; use DTOs (Guideline #6).

## 7. Configuration & Infrastructure
- **Open Session In View**: Ensure `spring.jpa.open-in-view=false` is set in `application.properties` (Guideline #5).
- **Validation**: Ensure global exception handling is in place for validation errors (Guideline #9).

## 8. Testing
- Implement integration tests for the Beer Order lifecycle.
- Use `@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)` (Guideline #13).
- Use Testcontainers for database integration testing if applicable (Guideline #12).
