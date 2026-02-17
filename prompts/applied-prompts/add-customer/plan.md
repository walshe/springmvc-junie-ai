# Implementation Plan - Add Customer Entity

This plan outlines the steps required to add a new `Customer` entity and provide full CRUD operations via a RESTful API, following the project's Spring Boot guidelines and existing patterns.

## 1. Database Migration
- Create a new Flyway migration script: `src/main/resources/db/migration/V2__add_customer.sql`.
- Define the `customer` table with fields: `id`, `version`, `name`, `email`, `phone_number`, `address_line_1`, `address_line_2`, `city`, `state`, `postal_code`, `created_date`, `update_date`.
- Add a `customer_id` column to the `beer_order` table.
- Add a foreign key constraint from `beer_order.customer_id` to `customer.id`.
- (Optional) Migrate existing `customer_ref` data if necessary, or just allow it to be nullable/deprecated.

## 2. JPA Entities
- Create `Customer` entity in `walshe.juniemvc.juniemvc.entities`:
    - Extend `BaseEntity`.
    - Annotate with `@Entity`, `@Getter`, `@Setter`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`.
    - Fields: `name` (NotNull), `email`, `phoneNumber`, `addressLine1` (NotNull), `addressLine2`, `city` (NotNull), `state` (NotNull), `postalCode` (NotNull).
    - Relationship: `@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY) private Set<BeerOrder> beerOrders`.
- Update `BeerOrder` entity:
    - Add `private Customer customer`.
    - Annotate with `@ManyToOne(fetch = FetchType.LAZY)`.
    - Add `@JoinColumn(name = "customer_id")`.

## 3. Repositories
- Create `CustomerRepository` interface in `walshe.juniemvc.juniemvc.repositories`:
    - Extend `JpaRepository<Customer, Integer>`.

## 4. DTOs and Mappers
- Create `CustomerDto` record/class in `walshe.juniemvc.juniemvc.models`.
- Create `CreateCustomerCommand` record for POST operations.
- Create `UpdateCustomerCommand` record for PUT operations (or reuse DTO).
- Create `CustomerMapper` interface in `walshe.juniemvc.juniemvc.mappers`:
    - Use `@Mapper(componentModel = "spring")`.
    - Define mapping methods between `Customer` and `CustomerDto`.

## 5. Service Layer
- Create `CustomerService` interface in `walshe.juniemvc.juniemvc.services`:
    - Define CRUD methods: `listCustomers`, `getCustomerById`, `createCustomer`, `updateCustomer`, `deleteCustomer`.
- Create `CustomerServiceImpl` class:
    - Annotate with `@Service`, `@RequiredArgsConstructor`.
    - Implement methods using `CustomerRepository` and `CustomerMapper`.
    - Use `@Transactional` and `@Transactional(readOnly = true)` appropriately.

## 6. Web Layer (REST Controller)
- Create `CustomerController` in `walshe.juniemvc.juniemvc.controllers`:
    - Annotate with `@RestController`, `@RequestMapping("/api/v1/customers")`, `@RequiredArgsConstructor`, `@Validated`.
    - Use package-private visibility for the class and methods where possible.
    - Implement GET (list and by ID), POST, PUT, and DELETE endpoints.
    - Return `ResponseEntity` with appropriate status codes.

## 7. OpenAPI Documentation
- Follow the modularized OpenAPI structure in `openapi/openapi/`:
- Create `openapi/openapi/paths/customers.yaml` for collection operations.
- Create `openapi/openapi/paths/customers_{customerId}.yaml` for individual resource operations.
- Create `openapi/openapi/components/schemas/Customer.yaml` and other necessary schemas.
- Update `openapi/openapi/openapi.yaml` to include new paths and components.
- Run `npm test` in the `openapi` directory to validate the specification.

## 8. Testing
- **Unit Tests:**
    - `CustomerMapperTest` for mapping logic.
    - `CustomerControllerTest` using `MockMvc` (mocking the service).
- **Integration Tests:**
    - `CustomerRepositoryTest` using `@DataJpaTest` and Testcontainers.
    - `CustomerControllerIT` using `@SpringBootTest` with `RANDOM_PORT` and Testcontainers.
- Verify all tests pass.
