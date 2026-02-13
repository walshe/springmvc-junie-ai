# Task List - Add Customer Entity

## 1. Database Migration
- [x] Create Flyway migration script `src/main/resources/db/migration/V2__add_customer.sql`.
- [x] Define `customer` table schema (id, version, name, email, phone_number, address fields, audit dates).
- [x] Add `customer_id` column and foreign key to `beer_order` table.

## 2. JPA Entities
- [x] Create `Customer` entity in `walshe.juniemvc.juniemvc.entities` extending `BaseEntity`.
- [x] Implement `Customer` fields with appropriate JPA annotations and Jakarta Validation.
- [x] Define `@OneToMany` relationship with `BeerOrder` in `Customer`.
- [x] Update `BeerOrder` entity to include `@ManyToOne` relationship with `Customer`.

## 3. Repositories
- [x] Create `CustomerRepository` interface in `walshe.juniemvc.juniemvc.repositories`.

## 4. DTOs and Mappers
- [x] Create `CustomerDto` record/class in `walshe.juniemvc.juniemvc.models`.
- [x] Create `CreateCustomerCommand` and `UpdateCustomerCommand` records.
- [x] Create `CustomerMapper` interface in `walshe.juniemvc.juniemvc.mappers` using MapStruct.

## 5. Service Layer
- [x] Create `CustomerService` interface in `walshe.juniemvc.juniemvc.services`.
- [x] Create `CustomerServiceImpl` class with required implementation.
- [x] Ensure proper `@Transactional` boundaries and constructor injection.

## 6. Web Layer (REST Controller)
- [x] Create `CustomerController` in `walshe.juniemvc.juniemvc.controllers`.
- [x] Implement REST endpoints: GET (list/id), POST, PUT, DELETE.
- [x] Use package-private visibility and `ResponseEntity` for responses.

## 7. OpenAPI Documentation
- [x] Create `openapi/openapi/paths/customers.yaml`.
- [x] Create `openapi/openapi/paths/customers_{customerId}.yaml`.
- [x] Create `openapi/openapi/components/schemas/Customer.yaml`.
- [x] Update `openapi/openapi/openapi.yaml` with new paths and components.
- [x] Validate OpenAPI spec using `npm test` in `openapi` directory.

## 8. Testing
- [x] Create `CustomerMapperTest` for unit testing mapping logic.
- [x] Create `CustomerControllerTest` using `MockMvc` for web layer unit testing.
- [x] Create `CustomerRepositoryTest` for repository integration testing with Testcontainers.
- [x] Create `CustomerControllerIT` for full integration testing with Testcontainers and `RANDOM_PORT`.
- [x] Verify all tests pass.
