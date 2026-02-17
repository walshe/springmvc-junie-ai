# Developer Guide: Add a React Frontend to the Spring Boot Beer Service

This guide walks an experienced Java/Spring Boot developer through adding a modern React (Vite + TypeScript) frontend to this project and integrating it into the Maven build so a single Spring Boot JAR serves the UI.

The instructions are concrete and step‑by‑step, tailored to this repository’s APIs and structure.

---

## 1) Objective and Final State
- Build a React app (React + Vite + TypeScript) inside the existing Spring Boot project.
- In development: run the Vite dev server with an HTTP proxy into the Spring Boot backend at http://localhost:8080.
- In production: `mvn clean package` produces a single executable Spring Boot JAR that serves the built frontend from `src/main/resources/static`.

APIs available (from Spring MVC/OpenAPI):
- Beer: `/api/v1/beer` (list with required `page` and `size`, optional `beerName`, `beerStyle`), `/api/v1/beer/{beerId}` (GET/PUT/DELETE), POST `/api/v1/beer`.
- Customers: `/api/v1/customers` (GET, POST), `/api/v1/customers/{customerId}` (GET/PUT/DELETE).
- Orders: `/api/v1/beerOrder` (GET, POST), `/api/v1/beerOrder/{orderId}` (GET, PATCH).
- Shipments: `/api/v1/shipments` (GET, POST), `/api/v1/shipments/{shipmentId}` (GET/PUT/DELETE).

OpenAPI definition: `openapi/openapi/openapi.yaml`.

---

## 2) Recommended Project Structure
Place all frontend sources under `src/main/frontend`. Vite’s production output will be configured to `src/main/resources/static` so Spring Boot can serve the built assets directly.

Project tree (end state – relevant parts only):
```
/ (project root)
├─ pom.xml
├─ src/
│  └─ main/
│     ├─ java/...
│     ├─ resources/
│     │  └─ static/          # Vite build goes here (production)
│     └─ frontend/           # React app root (development sources)
│        ├─ index.html
│        ├─ vite.config.ts
│        ├─ tsconfig.json
│        ├─ package.json
│        ├─ .eslintrc.cjs
│        ├─ .prettierrc
│        ├─ src/
│        │  ├─ main.tsx
│        │  ├─ app/
│        │  │  ├─ routes.tsx
│        │  │  ├─ layout/
│        │  │  │  ├─ AppLayout.tsx
│        │  │  │  └─ Nav.tsx
│        │  │  └─ providers/
│        │  ├─ components/   # shadcn + custom components
│        │  ├─ features/
│        │  │  ├─ beers/
│        │  │  ├─ customers/
│        │  │  ├─ orders/
│        │  │  └─ shipments/
│        │  ├─ hooks/
│        │  ├─ lib/
│        │  │  └─ api.ts     # Axios instance
│        │  └─ api/          # (generated) OpenAPI TS client/types
│        └─ tests/
├─ openapi/
│  └─ openapi/openapi.yaml
└─ prompts/
   └─ requirements.md (this file)
```

Rationale:
- `src/main/frontend` keeps all web assets in one place, simplifies Maven plugin configuration, and cleanly separates dev sources from Spring Boot resources.
- `src/main/resources/static` is Spring Boot’s default static web root. Placing built assets there lets the JAR serve the UI without extra config.

---

## 3) Initialize the React + TypeScript App (Vite)
From project root:
```
# Create the app directly in src/main/frontend
npm create vite@latest src/main/frontend -- --template react-ts
```

Recommended runtime toolchain (matching the draft):
- Node.js v22.16.0
- npm 11.4.0
- TypeScript 5.8.x

Then install core dependencies inside the frontend folder:
```
cd src/main/frontend
npm i react@19.1.0 react-dom@19.1.0 react-router-dom@7.6.36 axios@1.10.0
npm i -D typescript@5.8.3 @types/react@19.1.0 @types/react-dom@19.1.0 vite@6.3.5 @vitejs/plugin-react@4.5.2
```

---

## 4) Install and Configure UI Stack (Tailwind, Radix, shadcn)
Install styling and UI helpers:
```
npm i -D tailwindcss@4.1.10 postcss@8.5.5 autoprefixer@10.4.20
npm i class-variance-authority@0.7.1 tailwind-merge@3.3.1 clsx@2.1.1
npm i lucide-react@0.515.0 @radix-ui/react-icons@latest
```

Initialize Tailwind CSS (Vite):
- Tailwind v4 simplifies setup. Add a top-level stylesheet and include Tailwind directives.

Create `src/main/frontend/src/index.css`:
```css
@import "tailwindcss";
/* Your app-level styles and any Tailwind layers/utilities go here */
```

Import it in `src/main.tsx`:
```ts
import './index.css'
```

Install and initialize shadcn UI (component generator for Radix/Tailwind):
```
npx shadcn@latest init
# Follow prompts; a components.json file will be added
# To add components later:
# npx shadcn@latest add button card dialog input label table textarea select toast ...
```

Note: shadcn generates source components into `src/components` (configurable). They are plain React files you can customize.

---

## 5) Vite Configuration (dev proxy, build output, env)
Create or edit `src/main/frontend/vite.config.ts`:
```ts
import { defineConfig, loadEnv } from 'vite'
import react from '@vitejs/plugin-react'
import path from 'node:path'

export default defineConfig(({ mode }) => {
  const env = loadEnv(mode, process.cwd(), '')
  // Vite will expose vars prefixed with VITE_ via import.meta.env
  const apiBase = env.VITE_API_BASE_URL || '/api/v1'

  return {
    plugins: [react()],
    root: '.',
    base: '/',
    server: {
      port: 5173,
      strictPort: true,
      proxy: {
        // Proxy API calls to Spring Boot during development
        '/api': {
          target: 'http://localhost:8080',
          changeOrigin: true,
        },
      },
    },
    resolve: {
      alias: {
        '@': path.resolve(__dirname, 'src'),
      },
    },
    build: {
      // Output into Spring Boot static resources for production
      outDir: '../resources/static',
      emptyOutDir: true,
      sourcemap: false,
    },
    define: {
      __API_BASE__: JSON.stringify(apiBase),
    },
  }
})
```

Environment variables (`.env` files, optional):
- Vite only exposes vars prefixed with `VITE_`.
- Create `src/main/frontend/.env` (dev defaults):
```
VITE_API_BASE_URL=/api/v1
```
- For production builds in CI, you can add `.env.production` with the same key if you deploy the UI separately. When served by Spring Boot, keeping `/api/v1` works because the backend serves the same origin.

---

## 6) Maven Integration (frontend-maven-plugin + clean)
Add the `frontend-maven-plugin` to `pom.xml` to install Node/npm and run the frontend build during the Maven lifecycle.

In `pom.xml` (under `<build><plugins>`), add:
```xml
<plugin>
  <groupId>com.github.eirslett</groupId>
  <artifactId>frontend-maven-plugin</artifactId>
  <version>1.15.1</version>
  <configuration>
    <workingDirectory>src/main/frontend</workingDirectory>
  </configuration>
  <executions>
    <execution>
      <id>install-node-and-npm</id>
      <goals>
        <goal>install-node-and-npm</goal>
      </goals>
      <configuration>
        <nodeVersion>v22.16.0</nodeVersion>
        <npmVersion>11.4.0</npmVersion>
      </configuration>
    </execution>
    <execution>
      <id>npm-ci</id>
      <goals>
        <goal>npm</goal>
      </goals>
      <phase>generate-resources</phase>
      <configuration>
        <arguments>ci</arguments>
      </configuration>
    </execution>
    <execution>
      <id>npm-build</id>
      <goals>
        <goal>npm</goal>
      </goals>
      <phase>generate-resources</phase>
      <configuration>
        <arguments>run build</arguments>
      </configuration>
    </execution>
  </executions>
</plugin>
```

Clean up built static assets on `mvn clean` by extending `maven-clean-plugin` (add under `<build><plugins>` if not present):
```xml
<plugin>
  <artifactId>maven-clean-plugin</artifactId>
  <version>3.3.2</version>
  <configuration>
    <filesets>
      <fileset>
        <directory>src/main/resources/static</directory>
        <followSymlinks>false</followSymlinks>
      </fileset>
    </filesets>
  </configuration>
</plugin>
```

Usage:
- Dev (two processes):
  - Backend: `./mvnw spring-boot:run`
  - Frontend: `cd src/main/frontend && npm run dev`
- Production: `./mvnw clean package` → run the JAR → UI served from `/`.

---

## 7) End-to-End Type Safety from OpenAPI
Generate TypeScript types and a client from `openapi/openapi/openapi.yaml`.

Install generator:
```
cd src/main/frontend
npm i -D openapi-typescript-codegen@0.29.0
```

Add an npm script to `src/main/frontend/package.json`:
```json
{
  "scripts": {
    "gen:api": "openapi -i ../../openapi/openapi/openapi.yaml -o src/api --client axios --useOptions"
  }
}
```
Run it:
```
npm run gen:api
```
This generates a typed client and models into `src/api`. Re-run whenever the OpenAPI spec changes.

---

## 8) API Service Layer (Axios instance + per-resource modules)
Create a reusable Axios instance at `src/main/frontend/src/lib/api.ts`:
```ts
import axios from 'axios'

const baseURL = (import.meta as any).env?.VITE_API_BASE_URL || '/api/v1'

export const api = axios.create({
  baseURL,
  headers: { 'Content-Type': 'application/json' },
  timeout: 15000,
})

// Global error logging/interception
api.interceptors.response.use(
  (resp) => resp,
  (error) => {
    // You can centralize ProblemDetails handling here if the API returns RFC 9457 format
    console.error('[API ERROR]', error?.response?.status, error?.response?.data)
    return Promise.reject(error)
  }
)
```

Option A: Use the generated OpenAPI client directly in features.

Option B: Wrap it in simple service modules, e.g. `src/features/beers/api.ts`:
```ts
import { api } from '@/lib/api'
import type { Beer } from '@/api/models/Beer' // from generated types

export type BeerQuery = {
  page: number
  size: number
  beerName?: string
  beerStyle?: string
}

export async function listBeers(q: BeerQuery) {
  const { data } = await api.get<Beer[]>(`/beer`, { params: q })
  return data
}

export async function getBeer(id: number) {
  const { data } = await api.get<Beer>(`/beer/${id}`)
  return data
}

export async function createBeer(payload: Beer) {
  const { data } = await api.post<Beer>(`/beer`, payload)
  return data
}

export async function updateBeer(id: number, payload: Beer) {
  await api.put(`/beer/${id}`, payload)
}

export async function deleteBeer(id: number) {
  await api.delete(`/beer/${id}`)
}
```
Repeat similarly for `customers`, `beerOrder`, and `shipments` using the endpoints listed in Section 1.

---

## 9) Routing, Layout, and Navigation
Install React Router (already added). Create `src/app/routes.tsx`:
```tsx
import { createBrowserRouter } from 'react-router-dom'
import AppLayout from './layout/AppLayout'
import BeersPage from '@/features/beers/BeersPage'
import BeerDetailPage from '@/features/beers/BeerDetailPage'
import CustomersPage from '@/features/customers/CustomersPage'
import CustomerDetailPage from '@/features/customers/CustomerDetailPage'
import OrdersPage from '@/features/orders/OrdersPage'
import OrderDetailPage from '@/features/orders/OrderDetailPage'
import ShipmentsPage from '@/features/shipments/ShipmentsPage'
import ShipmentDetailPage from '@/features/shipments/ShipmentDetailPage'

export const router = createBrowserRouter([
  {
    path: '/',
    element: <AppLayout />,
    children: [
      { index: true, element: <BeersPage /> },
      { path: 'beers', element: <BeersPage /> },
      { path: 'beers/:beerId', element: <BeerDetailPage /> },
      { path: 'customers', element: <CustomersPage /> },
      { path: 'customers/:customerId', element: <CustomerDetailPage /> },
      { path: 'orders', element: <OrdersPage /> },
      { path: 'orders/:orderId', element: <OrderDetailPage /> },
      { path: 'shipments', element: <ShipmentsPage /> },
      { path: 'shipments/:shipmentId', element: <ShipmentDetailPage /> },
    ],
  },
])
```

Wire it in `src/main.tsx`:
```tsx
import React from 'react'
import ReactDOM from 'react-dom/client'
import { RouterProvider } from 'react-router-dom'
import { router } from './app/routes'
import './index.css'

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
)
```

Create a basic layout using shadcn components: `src/app/layout/AppLayout.tsx` with a `Nav` and an `Outlet`.

---

## 10) Feature Pages (CRUD flows)
Create feature directories under `src/features/*` with components, hooks, and pages:

Beers
- List (`BeersPage.tsx`): table with filters (`beerName`, `beerStyle`) and required pagination (`page`, `size`).
- Detail (`BeerDetailPage.tsx`): show, edit (PUT), delete.
- Create (`BeerCreateDialog.tsx` or page): POST to `/api/v1/beer`.

Customers
- List, Detail, Create (POST using `CreateCustomerCommand`), Update (PUT with `UpdateCustomerCommand`), Delete.

Orders
- List, Detail, Create (POST `CreateBeerOrderCommand`), Patch (`PatchBeerOrderCommand`).

Shipments
- List, Detail, Create (POST `CreateBeerOrderShipmentCommand`), Update (PUT `UpdateBeerOrderShipmentCommand`), Delete.

Implementation tips
- Use shadcn table, form, dialog components for consistent look & feel.
- Move API calls into `features/*/api.ts` and state into `features/*/hooks.ts` (e.g., `useBeers`, `useCustomers`).
- Use optimistic UI where safe (e.g., delete row then refetch on error).
- Return consistent error toasts/snackbars from Axios interceptor or per-call `catch` using shadcn’s toast.

---

## 11) Hooks and Error Handling
Example hook for beers: `src/features/beers/hooks.ts`:
```ts
import { useEffect, useState } from 'react'
import { listBeers, type BeerQuery } from './api'
import type { Beer } from '@/api/models/Beer'

export function useBeers(params: BeerQuery) {
  const [data, setData] = useState<Beer[] | null>(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<unknown>(null)

  useEffect(() => {
    let cancelled = false
    setLoading(true)
    listBeers(params)
      .then((d) => {
        if (!cancelled) setData(d)
      })
      .catch((e) => !cancelled && setError(e))
      .finally(() => !cancelled && setLoading(false))
    return () => {
      cancelled = true
    }
  }, [params.page, params.size, params.beerName, params.beerStyle])

  return { data, loading, error }
}
```

Global error handling options:
- Centralize ProblemDetails parsing in the Axios interceptor and surface a uniform `{ message, details }` shape.
- Show errors using shadcn `toast` and render inline field errors for form validation failures.

---

## 12) Code Quality: ESLint and Prettier
Install tooling:
```
cd src/main/frontend
npm i -D eslint@9.14.0 @eslint/js@9.14.0 typescript-eslint@8.13.0 eslint-plugin-react@7.37.2 eslint-plugin-react-hooks@4.6.2
npm i -D prettier@3.3.3 eslint-config-prettier@9.1.0 eslint-plugin-prettier@5.2.1
```

`.eslintrc.cjs`:
```js
/* eslint-env node */
module.exports = {
  root: true,
  env: { browser: true, es2023: true, node: true, jest: true },
  extends: [
    'plugin:react/recommended',
    'plugin:react-hooks/recommended',
    'plugin:@typescript-eslint/recommended',
    'prettier',
  ],
  parser: '@typescript-eslint/parser',
  plugins: ['react', 'react-hooks', '@typescript-eslint', 'prettier'],
  settings: { react: { version: 'detect' } },
}
```

`.prettierrc`:
```json
{
  "singleQuote": true,
  "semi": false,
  "printWidth": 100
}
```

Add scripts to `package.json`:
```json
{
  "scripts": {
    "lint": "eslint \"src/**/*.{ts,tsx}\"",
    "format": "prettier --write ."
  }
}
```

---

## 13) Testing: Jest + React Testing Library
Install testing libs:
```
cd src/main/frontend
npm i -D jest@30.0.0 @types/jest@29.5.14 ts-node@10.9.2 ts-jest@29.2.5
npm i -D @testing-library/react@16.3.0 @testing-library/jest-dom@6.6.3 whatwg-url@14.1.0
```

`jest.config.ts`:
```ts
import type { Config } from 'jest'

const config: Config = {
  testEnvironment: 'jsdom',
  roots: ['<rootDir>/src', '<rootDir>/tests'],
  moduleFileExtensions: ['ts', 'tsx', 'js', 'jsx'],
  transform: {
    '^.+\\.(ts|tsx)$': ['ts-jest', { tsconfig: '<rootDir>/tsconfig.json' }],
  },
  setupFilesAfterEnv: ['<rootDir>/tests/setupTests.ts'],
}
export default config
```

`tests/setupTests.ts`:
```ts
import '@testing-library/jest-dom'
```

Example test `tests/BeersPage.test.tsx`:
```tsx
import { render, screen } from '@testing-library/react'
import BeersPage from '@/features/beers/BeersPage'

test('renders beers page heading', () => {
  render(<BeersPage />)
  expect(screen.getByRole('heading', { name: /beers/i })).toBeInTheDocument()
})
```

Add script:
```json
{
  "scripts": {
    "test": "jest --passWithNoTests"
  }
}
```

Note: Vite projects often use Vitest. If you prefer, replace Jest with Vitest for tighter Vite integration. This guide follows the specified Jest stack.

---

## 14) Development and Production Workflows
Development
- Start backend: `./mvnw spring-boot:run` → http://localhost:8080
- Start frontend: `cd src/main/frontend && npm run dev` → http://localhost:5173
- All `/api/**` requests from the frontend are proxied to the backend; no CORS issues.

Production
- `./mvnw clean package`
- Run the JAR: `java -jar target/juniemvc-0.0.1-SNAPSHOT.jar`
- Open http://localhost:8080/ to see the React app. Static assets are served from `src/main/resources/static`.

---

## 15) Troubleshooting
- Blank page in production: ensure `vite.config.ts` has `base: '/'` and `build.outDir: '../resources/static'`.
- 404 on direct refresh of a route (e.g., `/beers`): serve SPA fallback via Spring. Add a controller to map unknown paths to `index.html` if necessary, or configure frontend routes to be hash-based. In many deployments, relying on client-side routing and linking from `/` is sufficient.
- CORS errors in dev: confirm Vite `server.proxy['/api']` points to `http://localhost:8080` and that you are calling paths under `/api`.
- API shape mismatches: regenerate client with `npm run gen:api` after backend spec updates.

---

## 16) Appendix: Text to Add to .junie/guidelines.md
Add the following new subsection to document the frontend structure and commands.

```
### Frontend (React + Vite) Integration

- Location: `src/main/frontend` (development sources)
- Production output: `src/main/resources/static` (served by Spring Boot)
- Dev server: Vite on http://localhost:5173 with proxy to http://localhost:8080 for `/api/**`.

Common commands:
- Backend (dev): `./mvnw spring-boot:run`
- Frontend (dev): `cd src/main/frontend && npm run dev`
- Generate API client/types: `cd src/main/frontend && npm run gen:api`
- Lint/format: `npm run lint && npm run format`
- Tests: `npm test`
- Full build: `./mvnw clean package` (runs Node install and `npm run build`, then packages Spring Boot JAR)
```

---

## 17) Summary Checklist
- [ ] Create app under `src/main/frontend` (Vite + React + TS), install dependencies.
- [ ] Configure Tailwind + shadcn, establish base layout and navigation.
- [ ] Set `vite.config.ts` (proxy `/api`, `outDir: ../resources/static`).
- [ ] Add `frontend-maven-plugin` and `maven-clean-plugin` to `pom.xml`.
- [ ] Generate TS client from `openapi/openapi/openapi.yaml` and wire into services.
- [ ] Implement Beers, Customers, Orders, Shipments pages with CRUD.
- [ ] Add Axios instance and error handling; extract hooks per feature.
- [ ] Add ESLint + Prettier and format/lint scripts.
- [ ] Add Jest + RTL and initial tests.
- [ ] Verify dev (two processes) and prod (`mvn clean package`) workflows.
