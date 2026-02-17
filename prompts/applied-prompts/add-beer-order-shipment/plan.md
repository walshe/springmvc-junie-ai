# Implementation Plan - Add BeerOrderShipment Entity

This plan outlines the steps required to add the `BeerOrderShipment` entity and its supporting components, providing full CRUD operations via a RESTful API. The implementation follows the project's Spring Boot guidelines and existing patterns.

## 1. Database Migration
- Create a new Flyway migration script: `src/main/resources/db/migration/V3__add_beer_order_shipment.sql`.
- Define the `beer_order_shipment` table with fields:
    - `id` (INTEGER, PRIMARY KEY, IDENTITY)
    - `version` (INTEGER)
    - `beer_order_id` (INTEGER, NOT NULL, FK to `beer_order.id`)
    - `shipment_date` (TIMESTAMP)
    - `carrier` (VARCHAR(255))
    - `tracking_number` (VARCHAR(255))
    - `created_date` (TIMESTAMP)
    - `update_date` (TIMESTAMP)
- Add foreign key constraint from `beer_order_shipment.beer_order_id` to `beer_order.id`.
- Add an index on `beer_order_id` for performance.

## 2. JPA Entities
- Create `BeerOrderShipment` entity in `walshe.juniemvc.juniemvc.entities`:
    - Extend `BaseEntity`.
    - Annotate with `@Entity`, `@Getter`, `@Setter`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`.
    - Fields: `shipmentDate`, `carrier`, `trackingNumber`.
    - Relationship: `@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "beer_order_id") private BeerOrder beerOrder`.
- Update `BeerOrder` entity:
    - Add `private Set<BeerOrderShipment> beerOrderShipments = new HashSet<>()`.
    - Annotate with `@OneToMany(mappedBy = "beerOrder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)`.
    - Add helper methods `addShipment(BeerOrderShipment shipment)` and `removeShipment(BeerOrderShipment shipment)`.

## 3. Repositories
- Create `BeerOrderShipmentRepository` interface in `walshe.juniemvc.juniemvc.repositories`:
    - Extend `JpaRepository<BeerOrderShipment, Integer>`.

## 4. DTOs and Mappers
- Create `BeerOrderShipmentDto` in `walshe.juniemvc.juniemvc.models`.
- Create `CreateBeerOrderShipmentCommand` and `UpdateBeerOrderShipmentCommand` records.
- Create `BeerOrderShipmentMapper` interface in `walshe.juniemvc.juniemvc.mappers`:
    - Use MapStruct for mapping between entity and DTO/Commands.
    - Handle the `beerOrderId` mapping.

## 5. Service Layer
- Create `BeerOrderShipmentService` interface in `walshe.juniemvc.juniemvc.services`:
    - Define CRUD methods: `listShipments`, `getShipmentById`, `createShipment`, `updateShipment`, `deleteShipment`.
- Create `BeerOrderShipmentServiceImpl` class:
    - Annotate with `@Service`, `@RequiredArgsConstructor`.
    - Use `@Transactional` and `@Transactional(readOnly = true)`.
    - Implement methods using `BeerOrderShipmentRepository`, `BeerOrderRepository`, and `BeerOrderShipmentMapper`.

## 6. Web Layer (REST Controller)
- Create `BeerOrderShipmentController` in `walshe.juniemvc.juniemvc.controllers`:
    - Annotate with `@RestController`, `@RequestMapping("/api/v1/shipments")`, `@RequiredArgsConstructor`, `@Validated`.
    - Use package-private visibility.
    - Implement standard REST endpoints: GET (all/id), POST, PUT, DELETE.

## 7. OpenAPI Documentation
- Follow the modularized OpenAPI structure in `openapi/openapi/`:
- Create `openapi/openapi/paths/shipments.yaml`.
- Create `openapi/openapi/paths/shipments_{shipmentId}.yaml`.
- Create `openapi/openapi/components/schemas/BeerOrderShipment.yaml`.
- Update `openapi/openapi/openapi.yaml` to include new paths and components.
- Run `npm test` to validate.

## 8. Testing
- **Unit Tests:**
    - `BeerOrderShipmentMapperTest` for mapping logic.
    - `BeerOrderShipmentControllerTest` using `MockMvc` or standalone `MockMvcBuilders`.
- **Integration Tests:**
    - `BeerOrderShipmentRepositoryTest` for repository layer.
    - `BeerOrderShipmentControllerIT` for full end-to-end integration tests.
- All tests must pass.
