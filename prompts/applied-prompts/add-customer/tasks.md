# Task List - Add BeerOrderShipment Entity

## 1. Database Migration
- [ ] Create Flyway migration script `src/main/resources/db/migration/V3__add_beer_order_shipment.sql`.
- [ ] Define `beer_order_shipment` table schema (id, version, beer_order_id, shipment_date, carrier, tracking_number, created_date, update_date).
- [ ] Add foreign key constraint to `beer_order(id)`.
- [ ] Add index on `beer_order_id`.

## 2. JPA Entities
- [ ] Create `BeerOrderShipment` entity in `walshe.juniemvc.juniemvc.entities` extending `BaseEntity`.
- [ ] Implement `BeerOrderShipment` fields and relationship with `BeerOrder`.
- [ ] Update `BeerOrder` entity with `@OneToMany` relationship to `BeerOrderShipment`.
- [ ] Add helper methods `addShipment` and `removeShipment` to `BeerOrder`.

## 3. Repositories
- [ ] Create `BeerOrderShipmentRepository` interface in `walshe.juniemvc.juniemvc.repositories`.

## 4. DTOs and Mappers
- [ ] Create `BeerOrderShipmentDto` in `walshe.juniemvc.juniemvc.models`.
- [ ] Create `CreateBeerOrderShipmentCommand` and `UpdateBeerOrderShipmentCommand` records.
- [ ] Create `BeerOrderShipmentMapper` interface in `walshe.juniemvc.juniemvc.mappers` using MapStruct.

## 5. Service Layer
- [ ] Create `BeerOrderShipmentService` interface in `walshe.juniemvc.juniemvc.services`.
- [ ] Create `BeerOrderShipmentServiceImpl` class with required implementation.
- [ ] Ensure proper `@Transactional` boundaries and constructor injection.

## 6. Web Layer (REST Controller)
- [ ] Create `BeerOrderShipmentController` in `walshe.juniemvc.juniemvc.controllers`.
- [ ] Implement REST endpoints: GET (list/id), POST, PUT, DELETE.
- [ ] Use package-private visibility and `ResponseEntity` for responses.

## 7. OpenAPI Documentation
- [ ] Create `openapi/openapi/paths/shipments.yaml`.
- [ ] Create `openapi/openapi/paths/shipments_{shipmentId}.yaml`.
- [ ] Create `openapi/openapi/components/schemas/BeerOrderShipment.yaml`.
- [ ] Update `openapi/openapi/openapi.yaml` with new paths and components.
- [ ] Validate OpenAPI spec using `npm test` in `openapi` directory.

## 8. Testing
- [ ] Create `BeerOrderShipmentMapperTest` for unit testing mapping logic.
- [ ] Create `BeerOrderShipmentControllerTest` using `MockMvc` for web layer unit testing.
- [ ] Create `BeerOrderShipmentRepositoryTest` for repository integration testing.
- [ ] Create `BeerOrderShipmentControllerIT` for full integration testing.
- [ ] Verify all tests pass.
