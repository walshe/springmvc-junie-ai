# Implementation Plan: React Frontend for Spring Boot Beer Service

This implementation plan outlines the steps required to add a modern React frontend to the existing Spring Boot project. It follows the guidelines and technical requirements defined in `prompts/requirements.md`.

## Epic 1: Project Setup & Configuration

**Goal:** Establish the foundation for the React application with a modern toolchain and styling framework.

### Story 1.1: Initialize React Project with Vite & TypeScript
*   **Task 1.1.1:** Create the React application using Vite in `src/main/frontend` using the `react-ts` template.
*   **Task 1.1.2:** Configure `tsconfig.json` for proper module resolution and path aliases (e.g., `@/*` for `src/*`).
*   **Task 1.1.3:** Set up the basic project structure: `src/app`, `src/features`, `src/components`, `src/hooks`, `src/lib`, `src/api`, and `tests/`.

### Story 1.2: Configure Vite for Development and Production
*   **Task 1.2.1:** Configure Vite dev server proxy in `vite.config.ts` to forward `/api/**` calls to the Spring Boot backend at `http://localhost:8080`.
*   **Task 1.2.2:** Set the production build output directory (`build.outDir`) to `../resources/static` to integrate with Spring Boot's static resource handling.
*   **Task 1.2.3:** Configure the base path and ensure the production build is optimized (sourcemaps, minification).

### Story 1.3: Set up Environment Variables
*   **Task 1.3.1:** Create `.env` and `.env.production` files for environment-specific configurations.
*   **Task 1.3.2:** Define `VITE_API_BASE_URL` and ensure it's accessible via `import.meta.env`.

### Story 1.4: Install and Configure UI Stack (Tailwind CSS, Radix, shadcn)
*   **Task 1.4.1:** Install and configure Tailwind CSS v4 for styling.
*   **Task 1.4.2:** Initialize shadcn UI and configure `components.json`.
*   **Task 1.4.3:** Add core shadcn components: `button`, `card`, `dialog`, `input`, `label`, `table`, `textarea`, `select`, `toast`, etc.

### Story 1.5: Establish Code Quality Standards (ESLint & Prettier)
*   **Task 1.5.1:** Install and configure ESLint with React and TypeScript plugins.
*   **Task 1.5.2:** Install and configure Prettier for consistent code formatting.
*   **Task 1.5.3:** Add `lint` and `format` scripts to `package.json` and ensure they are integrated into the development workflow.

---

## Epic 2: API Integration

**Goal:** Establish a robust, type-safe communication layer between the frontend and the backend.

### Story 2.1: Generate TypeScript Types and API Client from OpenAPI
*   **Task 2.1.1:** Install `openapi-typescript-codegen` as a development dependency.
*   **Task 2.1.2:** Add a `gen:api` script to `package.json` to generate the client and types from `openapi/openapi/openapi.yaml`.
*   **Task 2.1.3:** Run the generation script and verify the generated types in `src/api`.

### Story 2.2: Create Reusable API Service Layer (Axios)
*   **Task 2.2.1:** Create a central Axios instance in `src/lib/api.ts` with base URL and default headers.
*   **Task 2.2.2:** Implement a global response interceptor for centralized error handling (e.g., logging, toast notifications, ProblemDetails parsing).

### Story 2.3: Implement Resource Service Modules
*   **Task 2.3.1:** Create the `Beers` service module for CRUD operations on `/api/v1/beer`.
*   **Task 2.3.2:** Create the `Customers` service module for CRUD operations on `/api/v1/customers`.
*   **Task 2.3.3:** Create the `Orders` service module for CRUD operations on `/api/v1/beerOrder`.
*   **Task 2.3.4:** Create the `Shipments` service module for CRUD operations on `/api/v1/shipments`.

---

## Epic 3: Build Process Integration

**Goal:** Seamlessly integrate the frontend build into the existing Maven lifecycle.

### Story 3.1: Configure `frontend-maven-plugin` in `pom.xml`
*   **Task 3.1.1:** Add the `frontend-maven-plugin` to the `pom.xml` build section.
*   **Task 3.1.2:** Configure executions for installing Node and npm, running `npm ci`, and `npm run build` during the `generate-resources` phase.

### Story 3.2: Configure `maven-clean-plugin` for Frontend Assets
*   **Task 3.2.1:** Update the `maven-clean-plugin` to remove the generated `src/main/resources/static` directory during the `clean` phase.

---

## Epic 4: Component Development & Routing

**Goal:** Create the core UI components and application navigation structure.

### Story 4.1: Set up Application Routing (React Router)
*   **Task 4.1.1:** Define the main application routes in `src/app/routes.tsx`.
*   **Task 4.1.2:** Implement routing for Beers, Customers, Orders, and Shipments pages.

### Story 4.2: Develop Reusable UI Components
*   **Task 4.2.1:** Build custom UI components on top of shadcn primitives (e.g., generic Data Table with pagination, reusable Form components).
*   **Task 4.2.2:** Implement a consistent Modal/Dialog management strategy for CRUD operations.

### Story 4.3: Implement Main Application Layout and Navigation
*   **Task 4.3.1:** Create `AppLayout.tsx` as the main wrapper for the application.
*   **Task 4.3.2:** Develop a responsive `Nav.tsx` component for navigating between different feature areas.

---

## Epic 5: Feature Implementation: Beers

**Goal:** Implement full CRUD functionality for Beer resources.

### Story 5.1: Beer List with Filtering and Pagination
*   **Task 5.1.1:** Create `BeersPage.tsx` with a data table displaying beers.
*   **Task 5.1.2:** Implement server-side filtering (by `beerName`, `beerStyle`) and pagination (using `page` and `size` parameters).

### Story 5.2: Beer Details View
*   **Task 5.2.1:** Implement `BeerDetailPage.tsx` to display detailed information about a specific beer.
*   **Task 5.2.2:** Add navigation from the beer list to the details view.

### Story 5.3: Create, Update, and Delete Beer Operations
*   **Task 5.3.1:** Implement a form (e.g., in a Dialog) to create a new beer (POST).
*   **Task 5.3.2:** Implement edit functionality to update an existing beer (PUT).
*   **Task 5.3.3:** Implement delete functionality with a confirmation dialog (DELETE).

---

## Epic 6: Feature Implementation: Customers

**Goal:** Implement full CRUD functionality for Customer resources.

### Story 6.1: Customer List View
*   **Task 6.1.1:** Create `CustomersPage.tsx` to list all customers in a table.
*   **Task 6.1.2:** Implement basic sorting and searching if applicable.

### Story 6.2: Customer Details View
*   **Task 6.2.1:** Implement `CustomerDetailPage.tsx` for viewing individual customer data.

### Story 6.3: Create, Update, and Delete Customer Operations
*   **Task 6.3.1:** Implement creation using `CreateCustomerCommand` (POST).
*   **Task 6.3.2:** Implement update using `UpdateCustomerCommand` (PUT).
*   **Task 6.3.3:** Implement deletion of customers (DELETE).

---

## Epic 7: Feature Implementation: Beer Orders & Shipments

**Goal:** Implement order management and tracking of shipments.

### Story 7.1: Beer Order List View
*   **Task 7.1.1:** Create `OrdersPage.tsx` to display a list of all beer orders.

### Story 7.2: Beer Order Details and Management
*   **Task 7.2.1:** Implement `OrderDetailPage.tsx` to view order details, including associated customers and beer items.
*   **Task 7.2.2:** Implement partial updates using `PatchBeerOrderCommand` (PATCH) for status or detail changes.

### Story 7.3: Create and Update Beer Orders
*   **Task 7.3.1:** Implement the order creation flow using `CreateBeerOrderCommand` (POST).

### Story 7.4: Manage Order Shipments
*   **Task 7.4.1:** Create `ShipmentsPage.tsx` and detail view for shipments.
*   **Task 7.4.2:** Implement full CRUD for shipments: List, View, Create (`CreateBeerOrderShipmentCommand`), Update (`UpdateBeerOrderShipmentCommand`), and Delete.

---

## Epic 8: Testing & Validation

**Goal:** Ensure the application is reliable and functions correctly through automated testing.

### Story 8.1: Setup Testing Environment
*   **Task 8.1.1:** Configure Jest and React Testing Library for the frontend project.
*   **Task 8.1.2:** Implement `setupTests.ts` and ensure the `test` script in `package.json` is working.

### Story 8.2: Implement Unit and Integration Tests
*   **Task 8.2.1:** Write unit tests for core UI components and utility functions.
*   **Task 8.2.2:** Write integration tests for API service modules (using mocks) and core user flows (e.g., Beer creation, Order listing).
*   **Task 8.2.3:** Verify full end-to-end build via Maven: `./mvnw clean package`.
