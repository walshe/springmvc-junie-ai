# Add DTOs for Beer API and Integrate MapStruct

## Objective
Introduce explicit Web DTOs for the Beer resource and use MapStruct to map between persistence entities and DTOs. Update the service and controller layers to operate on DTOs, keeping the persistence layer unchanged.

## Scope
- Create BeerDto in a new package dedicated to API models.
- Create a MapStruct mapper for conversions between Beer (JPA entity) and BeerDto.
- Update the service layer to accept/return BeerDto and perform conversions using the mapper.
- Update the controller to consume/produce BeerDto and return proper HTTP responses.

## Out of scope
- Database schema changes.
- Partial update (PATCH) semantics.
- Business logic changes beyond introducing DTO mapping.

## Deliverables
1. models.BeerDto
2. mappers.BeerMapper (MapStruct interface + Spring component)
3. Updated BeerService and BeerServiceImpl to work with BeerDto
4. Updated BeerController to consume/produce BeerDto and ResponseEntity

## Project Constraints and Conventions
- Follow Spring Boot guidelines:
  - Prefer constructor injection (use Lombok @RequiredArgsConstructor).
  - Keep Spring components and handler methods package‑private unless public is required by frameworks.
  - Separate Web layer DTOs from persistence entities.
- Existing dependencies must be used (Lombok and MapStruct are already configured in pom.xml with component model "spring").
- Keep the existing resource URL structure: /api/v1/beer

---

## 1) BeerDto
Package: walshe.juniemvc.juniemvc.models

Purpose: API contract for Beer resource used by the Web and Service layers.

Structure and annotations:
- Class (not record), annotated with Lombok to minimize boilerplate.
- Required Lombok annotations:
  - @Data (or @Getter/@Setter) and @Builder
  - @NoArgsConstructor and @AllArgsConstructor
- Fields should mirror the Beer entity except for JPA/Hibernate specifics. Include id for reads but it must be ignored on create/update input mapping (see mapping rules):
  - Integer id
  - Integer version
  - String beerName
  - String beerStyle
  - String upc
  - Integer quantityOnHand
  - BigDecimal price
  - LocalDateTime createdDate (read-only)
  - LocalDateTime updateDate (read-only)

Validation (Jakarta):
- beerName: @NotBlank
- beerStyle: @NotBlank
- upc: @NotBlank
- price: @NotNull and @DecimalMin("0.00")
- quantityOnHand: @NotNull and @Min(0)

Notes:
- createdDate and updateDate are output/read-only fields. They should not be populated from client input.

## 2) MapStruct mapper
Package: walshe.juniemvc.juniemvc.mappers

Interface: BeerMapper
- @Mapper(componentModel = "spring")
- Methods:
  - BeerDto toDto(Beer source)
  - Beer toEntity(BeerDto source)
  - void updateEntityFromDto(BeerDto source, @MappingTarget Beer target) — full replace semantics used by PUT

Mapping rules:
- When converting from DTO to entity for create/update inputs, ignore the following entity fields (managed by the database/ORM):
  - id
  - createdDate
  - updateDate
- version may flow from DTO to entity if provided (optimistic locking), otherwise leave as is.
- For updateEntityFromDto: copy all updatable business fields (beerName, beerStyle, upc, quantityOnHand, price, version). Do not touch id, createdDate, updateDate.

## 3) Service layer updates
Interfaces/Classes: BeerService, BeerServiceImpl

Intent: Service is the transactional boundary and exposes/accepts DTOs to the Web layer; internally it uses the repository with entities and the mapper for conversions.

API changes (signatures):
- List<BeerDto> listBeers();
- Optional<BeerDto> getBeerById(Integer id);
- BeerDto saveNewBeer(BeerDto beerDto);
- void updateBeerById(Integer beerId, BeerDto beerDto);
- void deleteById(Integer beerId);

Implementation notes (BeerServiceImpl):
- Inject BeerRepository and BeerMapper via constructor (@RequiredArgsConstructor).
- listBeers: repository.findAll() -> map each entity to DTO.
- getBeerById: repository.findById(id) -> map to DTO if present.
- saveNewBeer:
  - mapper.toEntity(beerDto) -> repository.save(entity) -> mapper.toDto(saved)
- updateBeerById:
  - repository.findById(beerId).ifPresent(existing ->
    mapper.updateEntityFromDto(beerDto, existing);
    repository.save(existing);
    )
- deleteById: delegate to repository.

Transactional considerations:
- Methods that read only may stay default; annotate with @Transactional(readOnly = true) if a @Transactional strategy is used. Mutating methods should be @Transactional.

## 4) Controller updates
Class: BeerController

Responsibilities:
- Consume/produce application/json using BeerDto as the message type.
- Return explicit HTTP statuses with ResponseEntity.

Changes:
- Inject BeerService via constructor (@RequiredArgsConstructor). Keep class and handler methods package‑private unless public is required by Spring MVC.
- Endpoints (keep path prefix /api/v1/beer):
  - GET /api/v1/beer -> List<BeerDto>
  - GET /api/v1/beer/{beerId} -> BeerDto (404 if not found)
  - POST /api/v1/beer -> create from BeerDto body; return 201 Created with Location header pointing to /api/v1/beer/{newId}; optionally return body BeerDto of created resource
  - PUT /api/v1/beer/{beerId} -> update with BeerDto body; return 204 No Content
  - DELETE /api/v1/beer/{beerId} -> 204 No Content

Validation:
- Add @Validated at the controller class level and @Valid on @RequestBody BeerDto parameters.

Error handling:
- Not required in this task, but if a GlobalExceptionHandler exists, use ProblemDetails or consistent error responses for 404 and validation errors.

Content negotiation:
- application/json only.

## 5) Packaging layout
- models: walshe.juniemvc.juniemvc.models (BeerDto)
- mappers: walshe.juniemvc.juniemvc.mappers (BeerMapper)
- entities, repositories, services, controllers remain in their existing packages.

## 6) Backward compatibility and migration
- Controller response types change from entity to DTO; this is a breaking internal change but preserves the public JSON shape if DTO mirrors entity fields. Tests referencing entity types in controller/service should be updated to use BeerDto.
- Keep URL structure and IDs unchanged.

## 7) Acceptance criteria
- BeerDto class exists with Lombok builder and validation annotations; compiles without manual getters/setters.
- BeerMapper is a Spring component generated by MapStruct; build succeeds and the mapper is injectible.
- Service layer signatures use BeerDto and all conversions are delegated to BeerMapper.
- Controller methods consume and produce BeerDto and return appropriate HTTP statuses; POST sets Location header.
- All existing tests compile and are updated (if necessary) to use DTOs; add or adjust tests for the controller/service layer to validate DTO usage and mapping behavior, as needed.
