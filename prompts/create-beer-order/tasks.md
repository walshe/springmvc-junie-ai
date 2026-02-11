# Task List: Beer Order System Implementation

## Phase 1: Domain Model Refinement
- [x] 1.1. Create `walshe.juniemvc.juniemvc.entities.BaseEntity`
    - [x] 1.1.1. Annotate with `@MappedSuperclass`, `@Getter`, `@Setter`
    - [x] 1.1.2. Add `id` (Integer) with `@Id` and `@GeneratedValue(strategy = GenerationType.IDENTITY)`
    - [x] 1.1.3. Add `version` (Integer) with `@Version`
    - [x] 1.1.4. Add `createdDate` (LocalDateTime) with `@CreationTimestamp` and `@Column(updatable = false)`
    - [x] 1.1.5. Add `updateDate` (LocalDateTime) with `@UpdateTimestamp`
    - [x] 1.1.6. Set visibility to package-private
- [x] 1.2. Refactor `Beer` entity
    - [x] 1.2.1. Change `Beer` to extend `BaseEntity`
    - [x] 1.2.2. Remove fields: `id`, `version`, `createdDate`, `updateDate`
    - [x] 1.2.3. Add `Set<BeerOrderLine> beerOrderLines` with `@OneToMany(mappedBy = "beer")`
    - [x] 1.2.4. Initialize `beerOrderLines` to `new HashSet<>()`
- [x] 1.3. Implement `BeerOrder` entity
    - [x] 1.3.1. Create class in `walshe.juniemvc.juniemvc.entities` extending `BaseEntity`
    - [x] 1.3.2. Add fields: `customerRef` (String), `paymentAmount` (BigDecimal), `status` (String)
    - [x] 1.3.3. Map `@OneToMany` to `BeerOrderLine` with `mappedBy = "beerOrder"`, `cascade = CascadeType.ALL`, `orphanRemoval = true`, `fetch = FetchType.LAZY`
    - [x] 1.3.4. Implement `addLine(BeerOrderLine)` helper method
    - [x] 1.3.5. Implement `removeLine(BeerOrderLine)` helper method
    - [x] 1.3.6. Set visibility to package-private
- [x] 1.4. Implement `BeerOrderLine` entity
    - [x] 1.4.1. Create class in `walshe.juniemvc.juniemvc.entities` extending `BaseEntity`
    - [x] 1.4.2. Add fields: `orderQuantity` (Integer), `quantityAllocated` (Integer), `status` (String)
    - [x] 1.4.3. Map `@ManyToOne` to `BeerOrder` with `fetch = FetchType.LAZY`, `optional = false`, `@JoinColumn(name = "beer_order_id")`
    - [x] 1.4.4. Map `@ManyToOne` to `Beer` with `fetch = FetchType.LAZY`, `optional = false`, `@JoinColumn(name = "beer_id")`
    - [x] 1.4.5. Set visibility to package-private

## Phase 2: Persistence Layer
- [x] 2.1. Create `BeerOrderRepository`
    - [x] 2.1.1. Interface in `walshe.juniemvc.juniemvc.repositories` extending `JpaRepository<BeerOrder, Integer>`
    - [x] 2.1.2. Set visibility to package-private
- [x] 2.2. Create `BeerOrderLineRepository`
    - [x] 2.2.1. Interface in `walshe.juniemvc.juniemvc.repositories` extending `JpaRepository<BeerOrderLine, Integer>`
    - [x] 2.2.2. Set visibility to package-private

## Phase 3: DTOs, Commands, and Mappers
- [x] 3.1. Define `BeerOrderLineDto`
    - [x] 3.1.1. Create in `walshe.juniemvc.juniemvc.models` using `@Data`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`
    - [x] 3.1.2. Include fields: `id`, `version`, `beerId`, `orderQuantity`, `quantityAllocated`, `status`, `createdDate`, `updateDate`
- [x] 3.2. Define `BeerOrderDto`
    - [x] 3.2.1. Create in `walshe.juniemvc.juniemvc.models` using `@Data`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`
    - [x] 3.2.2. Include fields: `id`, `version`, `customerRef`, `paymentAmount`, `status`, `beerOrderLines` (List of `BeerOrderLineDto`), `createdDate`, `updateDate`
- [x] 3.3. Implement `CreateBeerOrderCommand`
    - [x] 3.3.1. Create in `walshe.juniemvc.juniemvc.models` (as a Java record)
    - [x] 3.3.2. Include fields required for order creation (e.g., `customerRef`, list of line items with `beerId` and `quantity`)
- [x] 3.4. Create `BeerOrderMapper`
    - [x] 3.4.1. Interface in `walshe.juniemvc.juniemvc.mappers` with `@Mapper(componentModel = "spring")`
    - [x] 3.4.2. Define mapping methods for `BeerOrder` <-> `BeerOrderDto`
    - [x] 3.4.3. Define mapping methods for `BeerOrderLine` <-> `BeerOrderLineDto`
    - [x] 3.4.4. Handle `beer_id` mapping in `BeerOrderLine` (may need custom logic or `@Mapping`)

## Phase 4: Service Layer
- [x] 4.1. Create `BeerOrderService` interface
    - [x] 4.1.1. Define CRUD methods: `listOrders`, `getOrderById`, `createOrder`, `updateOrder`, `deleteOrder`
- [x] 4.2. Create `BeerOrderServiceImpl`
    - [x] 4.2.1. Implement `BeerOrderService` in `walshe.juniemvc.juniemvc.services`
    - [x] 4.2.2. Annotate with `@Service` and `@RequiredArgsConstructor`
    - [x] 4.2.3. Use constructor injection for dependencies (Repository, Mapper, BeerRepository)
    - [x] 4.2.4. Apply `@Transactional(readOnly = true)` for queries
    - [x] 4.2.5. Apply `@Transactional` for data-modifying methods
    - [x] 4.2.6. Implement logic for `createOrder` (resolving Beer entities for lines)
    - [x] 4.2.7. Set visibility to package-private

## Phase 5: Web Layer
- [x] 5.1. Create `BeerOrderController`
    - [x] 5.1.1. Annotate with `@RestController`, `@RequestMapping("/api/v1/beerOrder")`, `@RequiredArgsConstructor`
    - [x] 5.1.2. Implement `GET /` (list orders)
    - [x] 5.1.3. Implement `GET /{orderId}` (get order by ID)
    - [x] 5.1.4. Implement `POST /` (create order using `CreateBeerOrderCommand`)
    - [x] 5.1.5. Set visibility to package-private

## Phase 6: Infrastructure and Validation
- [x] 6.1. Verify Configuration
    - [x] 6.1.1. Check `src/main/resources/application.properties` for `spring.jpa.open-in-view=false`
- [x] 6.2. Centralize Exception Handling (Optional/Verification)
    - [x] 6.2.1. Ensure global exception handler handles standard Spring Boot validation errors and business exceptions

## Phase 7: Testing
- [x] 7.1. Create `BeerOrderControllerTest`
    - [x] 7.1.1. Annotate with `@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)`
    - [x] 7.1.2. Test order creation flow
    - [x] 7.1.3. Test order retrieval and listing
    - [x] 7.1.4. Verify cascading behavior and relationship integrity
