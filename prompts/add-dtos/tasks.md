# Task List - Add DTOs and MapStruct Integration

This task list is derived from the implementation plan in `/prompts/add-dtos/plan.md`.

## Phase 1: Model and Mapper Creation
1. [] Create `BeerDto` class in `walshe.juniemvc.juniemvc.models`
    - [] Add Lombok annotations: `@Data`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`
    - [] Define fields: `id`, `version`, `beerName`, `beerStyle`, `upc`, `quantityOnHand`, `price`, `createdDate`, `updateDate`
    - [] Add Jakarta Validation annotations:
        - `beerName`: `@NotBlank`
        - `beerStyle`: `@NotBlank`
        - `upc`: `@NotBlank`
        - `price`: `@NotNull`, `@DecimalMin("0.00")`
        - `quantityOnHand`: `@NotNull`, `@Min(0)`
2. [] Create `BeerMapper` interface in `walshe.juniemvc.juniemvc.mappers`
    - [] Add `@Mapper(componentModel = "spring")` annotation
    - [] Define `BeerDto beerToBeerDto(Beer beer)` method
    - [] Define `Beer beerDtoToBeer(BeerDto dto)` method
    - [] Define `updateBeerFromDto(BeerDto dto, @MappingTarget Beer beer)` method with `@Mapping` to ignore `id`, `createdDate`, and `updateDate`

## Phase 2: Service Layer Refactoring
1. [] Update `BeerService` interface to use `BeerDto` in all method signatures
2. [] Update `BeerServiceImpl` class
    - [] Inject `BeerMapper` via constructor (use `@RequiredArgsConstructor`)
    - [] Refactor `listBeers()` to return `List<BeerDto>` using mapper
    - [] Refactor `getBeerById()` to return `Optional<BeerDto>` using mapper
    - [] Refactor `saveNewBeer()` to accept and return `BeerDto` using mapper
    - [] Refactor `updateBeerById()` to accept `BeerDto` and use `mapper.updateBeerFromDto()`
    - [] Add `@Transactional(readOnly = true)` to read methods and `@Transactional` to mutating methods

## Phase 3: Controller Layer Refactoring
1. [] Update `BeerController` class
    - [] Change method signatures to use `BeerDto`
    - [] Ensure package-private visibility for the class and its methods
    - [] Update `listBeers()` to return `List<BeerDto>`
    - [] Update `getBeerById()` to return `BeerDto` and handle 404 (if not handled by global advice)
    - [] Update `handlePost()` to return `201 Created` with `Location` header
    - [] Update `updateById()` and `deleteById()` to return `204 No Content`
    - [] Add `@Valid` to `@RequestBody` parameters

## Phase 4: Test Updates and Verification
1. [] Update `BeerServiceTest`
    - [] Refactor test cases to use `BeerDto`
2. [] Update `BeerControllerTest`
    - [] Refactor test cases to use `BeerDto`
    - [] Add assertion for `Location` header in POST test
3. [] Perform Verification
    - [] Run `./mvnw clean compile` to verify MapStruct generation and compilation
    - [] Run all tests (`./mvnw test`) to ensure everything passes

## Phase 5: Final Review
1. [] Verify adherence to Spring Boot guidelines (Constructor injection, package-private visibility, etc.)
2. [] Ensure all DTO and Mapper requirements from `requirements.md` are met
