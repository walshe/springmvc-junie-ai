# Plan: Beer Order System Implementation

This plan outlines the steps to implement the Beer Order system as specified in `requirements.md`, following the established Spring Boot guidelines.

## Phase 1: Domain Model Refinement
1.  **Implement `BaseEntity`**:
    *   Create `walshe.juniemvc.juniemvc.entities.BaseEntity` as an abstract `@MappedSuperclass`.
    *   Include `id`, `version`, `createdDate`, and `updateDate` with appropriate JPA/Hibernate annotations.
    *   Set visibility to package-private.
2.  **Refactor `Beer` Entity**:
    *   Modify `Beer` to extend `BaseEntity`.
    *   Remove redundant fields (`id`, `version`, `createdDate`, `updateDate`).
    *   Add `Set<BeerOrderLine> beerOrderLines` for the bidirectional relationship.
3.  **Implement `BeerOrder` Entity**:
    *   Create `walshe.juniemvc.juniemvc.entities.BeerOrder` extending `BaseEntity`.
    *   Include `customerRef`, `paymentAmount`, `status`.
    *   Map `@OneToMany` relationship to `BeerOrderLine` with cascade and orphan removal.
    *   Add helper methods `addLine` and `removeLine`.
4.  **Implement `BeerOrderLine` Entity**:
    *   Create `walshe.juniemvc.juniemvc.entities.BeerOrderLine` extending `BaseEntity`.
    *   Include `orderQuantity`, `quantityAllocated`, `status`.
    *   Map `@ManyToOne` relationships to `BeerOrder` and `Beer`.

## Phase 2: Persistence Layer
1.  **Create Repositories**:
    *   Create `BeerOrderRepository` and `BeerOrderLineRepository` in `walshe.juniemvc.juniemvc.repositories`.
    *   Ensure they extend `JpaRepository` and are package-private.

## Phase 3: DTOs, Commands, and Mappers
1.  **Define DTOs**:
    *   Create `BeerOrderDto` and `BeerOrderLineDto` in `walshe.juniemvc.juniemvc.models`.
    *   Use Lombok `@Data` or Java `record` for consistency with `BeerDto`.
    *   Include Jakarta Validation annotations.
2.  **Implement Command Object**:
    *   Create `CreateBeerOrderCommand` for order creation input.
3.  **Create Mappers**:
    *   Create `BeerOrderMapper` in `walshe.juniemvc.juniemvc.mappers` using MapStruct.
    *   Ensure nested mapping for `BeerOrderLine` is handled.

## Phase 4: Service Layer
1.  **Implement Beer Order Service**:
    *   Create `BeerOrderService` interface.
    *   Create `BeerOrderServiceImpl` (package-private).
    *   Implement CRUD operations with proper `@Transactional` boundaries.
    *   Use constructor injection for `BeerOrderRepository`, `BeerOrderMapper`, and other dependencies.

## Phase 5: Web Layer
1.  **Implement Beer Order Controller**:
    *   Create `BeerOrderController` (package-private).
    *   Expose endpoints under `/api/v1/beerOrder`.
    *   Use `ResponseEntity` and follow REST principles.
    *   Implement basic CRUD endpoints (list, get by id, create).

## Phase 6: Infrastructure and Validation
1.  **Verify Configuration**:
    *   Ensure `spring.jpa.open-in-view=false` is set.
2.  **Global Exception Handling**:
    *   Ensure a `@RestControllerAdvice` is present to handle validation and business exceptions (Standardize error responses).

## Phase 7: Testing
1.  **Integration Testing**:
    *   Create `BeerOrderControllerTest` using `@SpringBootTest` with random port.
    *   Test the full lifecycle: create order, retrieve order, list orders.
    *   Verify cascading behavior and relationship management.
