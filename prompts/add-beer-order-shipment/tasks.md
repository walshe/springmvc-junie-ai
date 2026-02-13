# Task List - Add BeerOrderShipment Entity

## 1. Database Migration
- [x] Create Flyway migration script `src/main/resources/db/migration/V3__add_beer_order_shipment.sql`.
- [x] Define `beer_order_shipment` table schema (id, version, beer_order_id, shipment_date, carrier, tracking_number, created_date, update_date).
- [x] Add foreign key constraint to `beer_order(id)`.
- [x] Add index on `beer_order_id`.

## 2. JPA Entities
- [x] Create `BeerOrderShipment` entity in `walshe.juniemvc.juniemvc.entities` extending `BaseEntity`.
- [x] Implement `BeerOrderShipment` fields and relationship with `BeerOrder`.
- [x] Update `BeerOrder` entity with `@OneToMany` relationship to `BeerOrderShipment`.
- [x] Add helper methods `addShipment` and `removeShipment` to `BeerOrder`.

## 3. Repositories
- [x] Create `BeerOrderShipmentRepository` interface in `walshe.juniemvc.juniemvc.repositories`.

## 4. DTOs and Mappers
- [x] Create `BeerOrderShipmentDto` in `walshe.juniemvc.juniemvc.models`.
- [x] Create `CreateBeerOrderShipmentCommand` and `UpdateBeerOrderShipmentCommand` records.
- [x] Create `BeerOrderShipmentMapper` interface in `walshe.juniemvc.juniemvc.mappers` using MapStruct.

## 5. Service Layer
- [x] Create `BeerOrderShipmentService` interface in `walshe.juniemvc.juniemvc.services`.
- [x] Create `BeerOrderShipmentServiceImpl` class with required implementation.
- [x] Ensure proper `@Transactional` boundaries and constructor injection.

## 6. Web Layer (REST Controller)
- [x] Create `BeerOrderShipmentController` in `walshe.juniemvc.juniemvc.controllers`.
- [x] Implement REST endpoints: GET (list/id), POST, PUT, DELETE.
- [x] Use package-private visibility and `ResponseEntity` for responses.

## 7. OpenAPI Documentation
- [x] Create `openapi/openapi/paths/shipments.yaml`.
- [x] Create `openapi/openapi/paths/shipments_{shipmentId}.yaml`.
- [x] Create `openapi/openapi/components/schemas/BeerOrderShipment.yaml`.
- [x] Update `openapi/openapi/openapi.yaml` with new paths and components.
- [x] Validate OpenAPI spec using `npm test` in `openapi` directory.

## 8. Testing
- [x] Create `BeerOrderShipmentMapperTest` for unit testing mapping logic.
- [x] Create `BeerOrderShipmentControllerTest` using `MockMvc` for web layer unit testing.
- [x] Create `BeerOrderShipmentRepositoryTest` for repository integration testing.
- [x] Create `BeerOrderShipmentControllerIT` for full integration testing.
- [x] Verify all tests pass.
