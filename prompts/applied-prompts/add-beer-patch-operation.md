# Add Patch Operation (Smart Template)

## Instructions for the AI
When this prompt is used, you are expected to automatically infer most variables by inspecting the project structure.
**Only the following core variables are required from the user:**

* entity_name = `BeerOrder`
* description = `Patch operation to update beer order details.`

---

## Task Description
Implement a PATCH endpoint for `${entity_name}` that allows partial updates. This involves creating a specialized Patch DTO, updating the Mapper with null-safe property mapping, and adding the endpoint through the Service and Controller layers.

### Automation Steps (AI to handle)
1. **Locate Files**: 
   - Entity: `src/main/java/**/entities/${entity_name}.java`
   - Existing DTO: `src/main/java/**/models/${entity_name}Dto.java`
   - Mapper: `src/main/java/**/mappers/${entity_name}Mapper.java`
   - Controller: `src/main/java/**/controllers/${entity_name}Controller.java`
   - Service Interface: `src/main/java/**/services/${entity_name}Service.java`
   - Service Impl: `src/main/java/**/services/${entity_name}ServiceImpl.java`
   - OpenAPI Paths: `openapi/openapi/paths/${entity_path}.yaml`
   - OpenAPI Schemas: `openapi/openapi/components/schemas/`

2. **Infer Base Path**: Check the `@RequestMapping` in the Controller (e.g., `/api/v1/beerOrder`).

### Implementation Checklist
* [ ] **Patch DTO**: Create `src/main/java/**/models/Patch${entity_name}Command.java` (as a Java Record).
  - Include all updatable fields from the Entity.
  - **Crucial**: Do NOT include `@NotNull`, `@NotBlank`, or other mandatory constraints that would prevent partial updates.
* [ ] **Mapper**: Add a patch update method to `${entity_name}Mapper.java`.
  - Use `@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)`.
  - Method signature should look like: `void update${entity_name}FromPatch(Patch${entity_name}Command command, @MappingTarget ${entity_name} entity);`.
* [ ] **Service**: 
  - Add `void patch${entity_name}(Integer id, Patch${entity_name}Command command);` to the Service interface.
  - Implement it in the ServiceImpl using `@Transactional`. The implementation should find the entity, call the mapper, and save.
* [ ] **Controller**: Add the `@PatchMapping("/{id}")` endpoint.
  - Return `204 No Content` on success.
* [ ] **OpenAPI**: 
  - Create `openapi/openapi/components/schemas/Patch${entity_name}Command.yaml`.
  - Update the relevant path file in `openapi/openapi/paths/` to include the `patch` operation.
  - Validate with `npm test`.
* [ ] **Tests**:
  - Create/Update Integration Test (e.g., `${entity_name}ControllerTest.java`) to verify the PATCH operation.
  - Test case: Update one field while leaving others null and verify only that field changed in the DB.
* [ ] **Verification**: Run `./mvnw test` and OpenAPI linting.

---

## AI Strategy
1. **Explore**: Locate all relevant files and identify updatable fields in the Entity.
2. **Apply Java**: 
   - Create the `Patch${entity_name}Command` record.
   - Update Mapper, Service, and Controller.
3. **Apply Docs**: Update OpenAPI schemas and paths.
4. **Verify**: Run `npm test` (OpenAPI) and integration tests.
