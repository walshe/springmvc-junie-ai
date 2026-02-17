# Junie MVC Demo

This repository is created to demonstrate the power of **Junie** (an autonomous AI programmer part of IntelliJ IDEA) and **Prompt Engineering** in building a Spring Boot application with standard REST patterns.

The project follows the [Spring Boot Guidelines](.junie/guidelines.md) to ensure high code quality and maintainability.

## Features Demonstrated

- **Autonomous Code Generation**: Junie identifies project structures, locates relevant files, and propagates changes across the entire stack (Database, JPA Entities, DTOs, MapStruct Mappers, Controllers, and OpenAPI specs).
- **React UI Vibe Coding**: The entire React frontend (including planning, task generation, and implementation) was "vibe coded" autonomously by Junie, starting from a draft requirements document.
- **Smart Prompt Templates**: The use of templates like `smart-add-property.md` and `smart-patch-operation.md` allows for complex, multi-step tasks to be executed with minimal user input.
- **Full-Stack Integration**: 
  - The React frontend is seamlessly integrated into the Spring Boot Maven lifecycle.
  - API-First types and services are generated from the OpenAPI specification to maintain strict contract synchronization.
- **RESTful Best Practices**:
  - Partial updates using `PATCH` and MapStruct's `NullValuePropertyMappingStrategy.IGNORE`.
  - Separation of concerns using DTOs and Records.
  - API-First design with OpenAPI documentation and Redocly linting.
  - Database versioning with Flyway.
- **Integration Testing**: Automatic generation and updates of integration tests to verify functionality and prevent regressions.

## Purpose

The primary goal of this project is to showcase how a well-structured codebase, combined with precise prompt engineering, enables an AI agent to handle architectural complexity and maintain high code quality standards autonomously.
