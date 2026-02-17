# Add New Entity Property (Smart Template)

## Instructions for the AI
When this prompt is used, you are expected to automatically infer most variables by inspecting the project structure.
**Only the following core variables are required from the user:**

* entity_name = `BeerOrder`
* property_name = `notes`
* property_type = `String`
* property_description = `Ad hoc notes about the order.`
* nullable = `true` (default: false)
* default_value = `` (optional)

---

## Task Description
Add the property `${property_name}` to `${entity_name}` and propagate it through the entire stack (DB, Entity, DTO, Mapper, OpenAPI, Tests).

### Automation Steps (AI to handle)
1. **Identify latest Flyway version**: Search `src/main/resources/db/migration` for the highest `V<N>` and use `V<N+1>`.
2. **Infer Table Name**: Check the `@Table` annotation or entity name (convert to snake_case).
3. **Locate Files**: 
   - Entity: `src/main/java/**/entities/${entity_name}.java`
   - DTO: `src/main/java/**/models/${entity_name}Dto.java`
   - Mapper: `src/main/java/**/mappers/${entity_name}Mapper.java`
   - OpenAPI: `openapi/openapi/components/schemas/${entity_name}.yaml`
4. **Determine SQL Type**: Map Java `${property_type}` to appropriate SQL type (e.g., `VARCHAR(255)`, `INTEGER`, `DECIMAL(19,2)`, `BOOLEAN`).

### Implementation Checklist
* [ ] **Migration**: Create `src/main/resources/db/migration/V{N}__add_{table}_{property}.sql`.
* [ ] **Entity**: Add field with JPA `@Column` and update Lombok `@Builder` constructor.
* [ ] **DTO**: Add field with matching type and appropriate Jakarta Validation (e.g., `@NotNull`, `@Min`).
* [ ] **Mapper**: Ensure MapStruct maps the new field (usually automatic, but check explicit mappings).
* [ ] **OpenAPI**: Add property to the schema file and validate with `npm test`.
* [ ] **Tests**:
  - Update Repository test to verify persistence.
  - Update Service/Controller tests to include the new field in JSON/DTO assertions.
* [ ] **Verification**: Run `./mvnw test` and OpenAPI linting.

---

## AI Strategy
1. **Explore**: Find the latest migration and the exact paths for Entity/DTO/Mapper.
2. **Apply DB**: Create the migration file.
3. **Apply Java**: Multi-edit Entity, DTO, and Mapper.
4. **Apply Docs**: Update OpenAPI YAML.
5. **Verify**: Run `npm test` (OpenAPI) and relevant Java tests.
