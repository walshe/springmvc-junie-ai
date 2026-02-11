# Implementation Plan - Add DTOs and MapStruct Integration

This plan outlines the steps required to implement Web DTOs for the Beer API and integrate MapStruct for entity-DTO mapping, as specified in `prompts/add-dtos/requirements.md`.

## Phase 1: Model and Mapper Creation
1. **Create `BeerDto` class**
    - Package: `walshe.juniemvc.juniemvc.models`
    - Annotations: `@Data`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`
    - Fields: id, version, beerName, beerStyle, upc, quantityOnHand, price, createdDate, updateDate.
    - Add Jakarta Validation annotations (`@NotBlank`, `@NotNull`, `@Min`, `@DecimalMin`).

2. **Create `BeerMapper` interface**
    - Package: `walshe.juniemvc.juniemvc.mappers`
    - Annotations: `@Mapper(componentModel = "spring")`
    - Methods:
        - `BeerDto beerToBeerDto(Beer beer)`
        - `Beer beerDtoToBeer(BeerDto dto)`
        - `@Mapping(target = "id", ignore = true)`
          `@Mapping(target = "createdDate", ignore = true)`
          `@Mapping(target = "updateDate", ignore = true)`
          `void updateBeerFromDto(BeerDto dto, @MappingTarget Beer beer)`

## Phase 2: Service Layer Refactoring
1. **Update `BeerService` interface**
    - Change method signatures to use `BeerDto` instead of `Beer`.
2. **Update `BeerServiceImpl` implementation**
    - Inject `BeerMapper` via constructor.
    - Use `BeerMapper` to convert between `Beer` entities and `BeerDto` objects in all methods.
    - Ensure `updateBeerById` uses `mapper.updateBeerFromDto`.
    - Add `@Transactional` annotations where appropriate (read-only for queries).

## Phase 3: Controller Layer Refactoring
1. **Update `BeerController`**
    - Change handler methods to use `BeerDto`.
    - Ensure package-private visibility where possible.
    - Use `ResponseEntity` for all responses.
    - Implement `201 Created` with `Location` header for `handlePost`.
    - Add `@Valid` to `@RequestBody` parameters.

## Phase 4: Test Updates and Verification
1. **Fix `BeerServiceTest`**
    - Update test cases to use `BeerDto` when interacting with the service.
2. **Fix `BeerControllerTest`**
    - Update test cases to expect/provide `BeerDto`.
    - Verify `Location` header in POST test.
    - Verify JSON paths match DTO structure.
3. **Verify Build**
    - Run `./mvnw clean compile` to ensure MapStruct implementation is generated and code compiles.
    - Run all tests to ensure no regressions.

## Phase 5: Documentation and Cleanup
1. Ensure all code follows the Spring Boot guidelines provided.
2. Verify package-private visibility for Spring components.
