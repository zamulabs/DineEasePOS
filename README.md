# DineEase POS

## Overview
**DineEase POS** is a Kotlin/JVM desktop application built with **JetBrains Compose for Desktop**.  
It provides a complete restaurant Point of Sale (POS) solution, covering:

- Table assignments  
- Menu/catalog management  
- Order and payment processing  
- Receipts and reports  
- User and settings management  

**Tech highlights:**  
- **UI:** Compose Material 3 with custom components and Compose Navigation  
- **Architecture:** MVVM + Repository, unidirectional data flow with StateFlow  
- **DI:** Koin  
- **Networking:** Ktor Client with pluggable `ApiService` (real or in-memory fake for demo/offline)  
- **Persistence:** Multiplatform Settings for lightweight storage; SQLDelight setup (schema pending)  
- **Packaging:** Native desktop distributions (DMG, MSI, DEB)  

---

## Architecture

- **Pattern:** MVVM with Repository  
  - **UI layer:** Screens + stateless `ScreenContent` composables  
  - **Presentation:** ViewModels (Lifecycle + StateFlow) handling `UiState` + `UiEvent`  
  - **Data layer:** Repository interfaces mapping DTOs → UI models; `ApiService` abstraction for networking (real or Fake for demo)  

- **Dependency Injection:** Koin  
  - `CommonModule`: HttpClient, PreferenceManager, Dispatchers, logging  
  - `AppModule`: ViewModels, ApiService binding, SettingsRepository, Repository implementations  

- **Navigation:** Type-safe navigation via sealed `Destinations` objects (`@Serializable`) and `AppNavHost`  

- **Theming:** Centralized `DineEaseTheme` (Material 3)  

---

## Core Libraries & Dependencies

- **UI:** JetBrains Compose (Material3, foundation, icons, split panes)  
- **State & Lifecycle:** `lifecycle-viewmodel-compose`, `lifecycle-runtime-compose`  
- **DI:** Koin (core + compose)  
- **Networking:** Ktor (CIO, Auth, Logging, Serialization, Content Negotiation)  
- **Persistence:** Multiplatform Settings (preferences) + SQLDelight (DB runtime, no schema yet)  
- **Serialization:** Kotlinx Serialization  
- **Logging:** Napier  
- **Date/Time:** kotlinx-datetime  
- **Charts:** KoalaPlot (planned for reports/dashboard)  
- **UI Components:** Data table (Material3), SplitPane, Toast4j (notifications), JNA/JFA (native interop)  
- **Coroutines:** kotlinx-coroutines-swing for desktop event loop integration  
- **Testing:** Kotlin test + JUnit  

---

## Features

Each feature follows a consistent structure:  

- `FeatureScreen` → wires DI + ViewModel  
- `FeatureScreenContent` → stateless UI  
- `FeatureViewModel` → state + logic via StateFlow  
- `FeatureUiState` / `FeatureUiEvent` → state models + intents  

### 1. Dashboard  
- Displays KPIs, recent orders, and reports (tables/charts).  

### 2. Login  
- Authenticates user → saves token in Settings → navigates to dashboard.  

### 3. Menu Management  
- List/add menu items via repository.  

### 4. Table Management  
- View/add tables; detail view per table.  

### 5. Order Management  
- Manage orders in a split-pane view (list + details).  
- Create new orders with line items.  

### 6. Payments (History)  
- View past payment records.  

### 7. Payment Processing  
- Complete order payment (cash/online), compute change, update status.  

### 8. Receipts  
- Generate and view receipts per order.  

### 9. Reports  
- Sales reports (tabular + charts).  

### 10. Settings  
- Theme, onboarding, notifications, token storage.  

### 11. User Management  
- Add users, list users, view user details.  

---

## Technical Decisions & Patterns

- **Repository pattern:** Abstracts data sources; easy swap between Fake/Real ApiService.  
- **DI via Koin:** Decoupled construction, simpler testing.  
- **StateFlow in ViewModels:** Predictable, unidirectional UI state.  
- **Result wrapping:** `NetworkResult` ensures consistent error handling.  
- **Safe API calls:** Centralized error catching and parsing.  
- **FakeApiService:** Supports offline demo/dev without backend.  
- **UI Consistency:** Custom wrappers (e.g., `AppDataTable`) for styling and layout consistency.  
- **Typed Navigation:** Avoids string-based route errors.  

---

## Data Management

- **Preferences:** Multiplatform Settings via `PreferenceManager`  
- **Database:** SQLDelight runtime configured, schema missing (future work)  
- **Caching:** Not implemented (FakeApiService = in-memory only)  
- **DTO Mapping:** Repositories map DTOs to UI models  

---

## Error Handling & Logging

- **Error Handling:**  
  - All network calls wrapped in `safeApiCall` → return `NetworkResult.Error` with parsed server error when possible  

- **Logging:**  
  - Napier for structured logs  
  - Ktor logging plugin (optional, DI toggle)  

---

## Testing

- **Frameworks:** Kotlin Test + JUnit  
- **Coverage:** Currently unit tests only (no UI/integration tests)  
- **TODO:** Add ViewModel, Repository, and UI tests with FakeApiService  

---

## Deployment

- **Build system:** Gradle Kotlin DSL + Compose Multiplatform plugin  
- **Run locally:**  
  - macOS/Linux → `./gradlew :composeApp:run`  
  - Windows → `gradlew.bat :composeApp:run`  
- **Distributions:** Native packages (DMG, MSI, DEB)  
- **Main entry:** `com.zamulabs.dineeasepos.MainKt`  
- **CI/CD:** Not present (future work)  

---

## Known Gaps / TODO

- Configure **real ApiService** (set `BASE_URL`, replace FakeApiService).  
- Add **SQLDelight schema** (`.sq` files) or remove dependency.  
- Implement **caching** (local DB or memory strategy).  
- Expand **test coverage** (ViewModel, repository, UI).  
- Add **logging configuration** per environment.  
- Add **CI/CD pipeline** for build/test/package.  
- Document **API secrets/configs** (auth, payment gateways).  
- Clarify **KoalaPlot usage** (dashboard/reports).  
- Add **packaging/signing instructions** for release builds.  
