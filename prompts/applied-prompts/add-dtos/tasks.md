# Task List - Add DTOs and MapStruct Integration

This task list is derived from the implementation plan in `/prompts/add-dtos/plan.md`.

## Phase 1: Model and Mapper Creation
1. [x] Create `BeerDto` class in `walshe.juniemvc.juniemvc.models`
    - [x] Add Lombok annotations: `@Data`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`
    - [x] Define fields: `id`, `version`, `beerName`, `beerStyle`, `upc`, `quantityOnHand`, `price`, `createdDate`, `updateDate`
    - [x] Add Jakarta Validation annotations:
        - `beerName`: `@NotBlank`
        - `beerStyle`: `@NotBlank`
        - `upc`: `@NotBlank`
        - `price`: `@NotNull`, `@DecimalMin("0.00")`
        - `quantityOnHand`: `@NotNull`, `@Min(0)`
2. [x] Create `BeerMapper` interface in `walshe.juniemvc.juniemvc.mappers`
    - [x] Add `@Mapper(componentModel = "spring")` annotation
    - [x] Define `BeerDto beerToBeerDto(Beer beer)` method
    - [x] Define `Beer beerDtoToBeer(BeerDto dto)` method
    - [x] Define `updateBeerFromDto(BeerDto dto, @MappingTarget Beer beer)` method with `@Mapping` to ignore `id`, `createdDate`, and `updateDate`

## Phase 2: Service Layer Refactoring
1. [x] Update `BeerService` interface to use `BeerDto` in all method signatures
2. [x] Update `BeerServiceImpl` class
    - [x] Inject `BeerMapper` via constructor (use `@RequiredArgsConstructor`)
    - [x] Refactor `listBeers()` to return `List<BeerDto>` using mapper
    - [x] Refactor `getBeerById()` to return `Optional<BeerDto>` using mapper
    - [x] Refactor `saveNewBeer()` to accept and return `BeerDto` using mapper
    - [x] Refactor `updateBeerById()` to accept `BeerDto` and use `mapper.updateBeerFromDto()`
    - [x] Add `@Transactional(readOnly = true)` to read methods and `@Transactional` to mutating methods

## Phase 3: Controller Layer Refactoring
1. [x] Update `BeerController` class
    - [x] Change method signatures to use `BeerDto`
    - [x] Ensure package-private visibility for the class and its methods
    - [x] Update `listBeers()` to return `List<BeerDto>`
    - [x] Update `getBeerById()` to return `BeerDto` and handle 404 (if not handled by global advice)
    - [x] Update `handlePost()` to return `201 Created` with `Location` header
    - [x] Update `updateById()` and `deleteById()` to return `204 No Content`
    - [x] Add `@Valid` to `@RequestBody` parameters

## Phase 4: Test Updates and Verification
1. [x] Update `BeerServiceTest`
    - [x] Refactor test cases to use `BeerDto`
2. [x] Update `BeerControllerTest`
    - [x] Refactor test cases to use `BeerDto`
    - [x] Add assertion for `Location` header in POST test
3. [x] Perform Verification
    - [x] Run `./mvnw clean compile` to verify MapStruct generation and compilation
    - [x] Run all tests (`./mvnw test`) to ensure everything passes

## Phase 5: Final Review
1. [x] Verify adherence to Spring Boot guidelines (Constructor injection, package-private visibility, etc.)
2. [x] Ensure all DTO and Mapper requirements from `requirements.md` are met
