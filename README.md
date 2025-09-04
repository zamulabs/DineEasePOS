# DineEasePOS

## Overview
- Type: Kotlin/JVM desktop application built with JetBrains Compose for Desktop.
- Purpose: Restaurant Point of Sale (POS) workflow management: table assignments, menu/catalog, orders, payments, receipts, reports, and basic user/settings.
- UI: Compose Material 3 with custom components; navigation via Compose Navigation.
- DI: Koin.
- Networking: Ktor client (CIO engine) configured; ApiService has endpoints defined. Default runtime uses a FakeApiService (in-memory) for demo/offline behavior.
- Persistence/Config: Multiplatform Settings for lightweight key-value storage (tokens, flags). SQLDelight plugin configured (no schema present in repo).
- Packaging: Compose Desktop native distributions (DMG/MSI/DEB).

## Libraries & Dependencies
From composeApp/build.gradle.kts and gradle/libs.versions.toml:

- JetBrains Compose Multiplatform (runtime, foundation, material3, materialIconsExtended, UI tooling preview)
  - Role: Desktop UI framework and components.
- androidx.lifecycle lifecycle-viewmodel-compose, lifecycle-runtime-compose
  - Role: ViewModel integration with Compose; lifecycle-aware state.
- Koin (core, compose)
  - Role: Dependency Injection for ViewModels and services.
- Kotlinx Serialization (core)
  - Role: JSON serialization/deserialization.
- dev.chrisbanes.material3: material3-window-size-class-multiplatform
  - Role: Window/adaptive size classes.
- app.cash.sqldelight: runtime, sqlite-driver, coroutines-extensions, primitive-adapters
  - Role: SQL database runtime and drivers (configured; not used in code paths visible; no .sq schema files present).
- com.russhwolf: multiplatform-settings(-no-arg, -coroutines)
  - Role: Key-value preferences and observable flows (PreferenceManager/SettingsRepositoryImpl).
- Napier (io.github.aakira:napier)
  - Role: Multiplatform logging (used in HTTP logging and error handling).
- kotlinx-datetime
  - Role: Date/time utilities (used in FakeApiService timestamps).
- KoalaPlot (io.github.koalaplot:koalaplot-core)
  - Role: Charts (presumably used in dashboard/reports; present as dependency).
- org.jetbrains.androidx.navigation:navigation-compose
  - Role: Type-safe Compose Navigation on Desktop.
- Ktor Client (core, serialization, logging, content-negotiation, CIO, auth)
  - Role: HTTP client for ApiService; auth bearer tokens; logging and retries via plugins.
- com.seanproctor:data-table-material3
  - Role: Data table component (wrapped by AppDataTable).
- org.jetbrains.compose.components:components-splitpane-desktop
  - Role: Split pane layout for Desktop (used in screen layouts; dependency present).
- de.mobanisto:toast4j
  - Role: Windows notifications.
- net.java.dev.jna:jna and de.jangassen:jfa
  - Role: Native access/interop (Windows/Linux platform specifics for notifications or integrations).
- org.jetbrains.kotlinx:kotlinx-coroutines-swing
  - Role: Coroutines integration with Swing event loop for Desktop.
- org.jetbrains.kotlin:kotlin-stdlib
  - Role: Kotlin standard library.
- Testing: org.jetbrains.kotlin:kotlin-test, junit:junit
  - Role: Unit testing.
- Build Plugins: org.jetbrains.kotlin.multiplatform, org.jetbrains.compose, org.jetbrains.kotlin.plugin.compose, org.jetbrains.compose.hot-reload, org.jetbrains.kotlin.plugin.serialization, app.cash.sqldelight, com.diffplug.spotless
  - Role: KMP/Compose build, hot reload, serialization, SQLDelight codegen, formatting/linting.

## Architecture
- Pattern: MVVM with Repository.
  - UI Layer: Compose screens and stateless ScreenContent composables per feature.
  - Presentation Layer: ViewModels (androidx.lifecycle.ViewModel) exposing StateFlow UiState and handling UiEvent.
  - Data Layer: Repository (DineEaseRepository/Impl) transforming DTOs to UI/domain models and returning NetworkResult. ApiService abstracts HTTP endpoints. FakeApiService provides in-memory data for demo and offline dev.
- DI: Koin modules
  - CommonModule: HttpClient (Ktor + plugins), PreferenceManager, coroutine Dispatchers, logging setup.
  - AppModule (viewModelModule): Binds all feature ViewModels as singletons; binds ApiService -> FakeApiService; binds SettingsRepository and DineEaseRepository.
- Navigation: Compose Navigation with typed Destinations sealed objects (Serializable) and AppNavHost wiring all routes.
- Theming: Central DineEaseTheme for M3 styling.

## Design Patterns & Technical Decisions
- Repository pattern (DineEaseRepository): isolates data access and mapping from UI.
  - Decision: Enables swapping ApiService implementation (Fake vs real) without touching UI.
- Dependency Injection (Koin): decouples construction, simplifies testing and feature wiring.
  - Decision: Centralized modules (CommonModule, viewModelModule) for clarity; easy replacement of ApiService.
- MVVM with StateFlow: ViewModels manage state and side effects; UI collects state via collectAsState.
  - Decision: Unidirectional data flow with immutable UiState copies; predictable UI.
- Result Wrapping (NetworkResult): encapsulates success/error with optional cached data.
  - Trade-off: Requires explicit when-branches but centralizes error paths.
- Error Handling helper (safeApiCall): standardizes catching Ktor exceptions and parsing server errors.
  - Decision: Provides consistent error messages and logging via Napier.
- FakeApiService for development: in-memory data set and mutations.
  - Trade-off: Real backend not required to run; must ensure parity with real API later.
- AppDataTable wrapper: thin layer over data-table-material3 to standardize paddings/colors and optional pagination.
  - Decision: Keeps table styling consistent across screens.
- Navigation with typed routes (Serializable sealed objects): reduces stringly-typed errors.

## Features Documentation (End-to-End)
Each feature follows the convention: <Feature>Screen (wires DI, obtains ViewModel), <Feature>ScreenContent (stateless UI), <Feature>ViewModel (state + logic), <Feature>UiState/UiEvent (data and intents). Navigation routes defined in Destinations.

1) Dashboard
- Purpose: Overview metrics and quick lists (uses AppDataTable and charts dependency present).
- Workflow: UI loads -> DashboardViewModel loads menu/orders/tables/payments via repository -> UiState updates -> AppDataTable displays rows.
- Flow: Compose UI -> DashboardViewModel -> DineEaseRepository -> ApiService (Fake) -> DTOs mapped -> UiState.
- Dependencies: Compose, Koin, Repository, AppDataTable, KoalaPlot (if used), Napier for logs.

2) Login
- Purpose: Authenticate and store token.
- Workflow: User enters credentials -> LoginViewModel calls repository.login -> on success, SettingsRepository saves bearer token -> navigation to Dashboard.
- Flow: UI -> ViewModel -> Repository -> ApiService.login (Fake returns token) -> SettingsRepository -> Nav.
- Dependencies: Ktor Auth (client), Multiplatform Settings, Koin, Napier.

3) Menu Management
- Purpose: View/add menu items.
- Workflow: Screen loads -> ViewModel calls repository.getMenu -> display in AppDataTable. Add flow uses AddMenuItemScreen -> AddMenuItemViewModel -> repository.addMenuItem -> updates list.
- Flow: UI -> ViewModel -> Repository -> ApiService.fetch/create menu -> DTO->UI mapping.
- Dependencies: AppDataTable, Repository, Ktor, Koin.

4) Table Management / Add Table / Table Details
- Purpose: View and manage dining tables; add new.
- Workflow: TableManagementViewModel loads tables. AddTableScreen posts CreateTable via repository; details screen shows a selected table’s info.
- Flow: UI -> ViewModel -> Repository -> ApiService.fetch/create table.
- Dependencies: AppDataTable, Repository, Koin.

5) Order Management / New Order / Order Details
- Purpose: Create/manage orders and view details; open split-pane flows.
- Workflow: OrderManagementViewModel handles tabs, search, detail pane, and opening payment pane; repository.getOrders loads orders. NewOrderViewModel handles selection and submission of order lines (implementation-specific in repo; UI scaffolding present).
- Flow: UI -> ViewModel -> Repository -> ApiService.fetchOrders.
- Dependencies: AppDataTable, SplitPane component, Repository, Koin.

6) Payments (History)
- Purpose: View payment records.
- Workflow: PaymentsScreen loads list via PaymentsViewModel -> repository.getPayments -> AppDataTable display.
- Flow: UI -> ViewModel -> Repository -> ApiService.fetchPayments.
- Dependencies: AppDataTable, Repository, Koin.

7) Payment Processing
- Purpose: Complete payment for an order (cash/online, compute change, select gateway).
- Workflow: User selects method and enters amount -> PaymentProcessingViewModel computes changeDue locally -> on Complete Payment -> repository.processPayment -> ApiService.createPayment -> updates status.
- Flow: UI (PaymentProcessingScreen/Content) -> ViewModel -> Repository -> ApiService.createPayment -> response mapped to status in UiState.
- Dependencies: Compose UI components (AppScaffold, AppFilterChip, AppTextField, AppDropdown, AppButton), Repository, Koin.

8) Receipt
- Purpose: Display a receipt for an order.
- Workflow: ReceiptScreen asks Repository.getReceipt(orderId) -> ApiService.fetchReceipt -> displays columns via AppDataTable.
- Flow: UI -> ViewModel -> Repository -> ApiService.fetchReceipt.
- Dependencies: AppDataTable, Repository, Koin.

9) Reports
- Purpose: Sales reports over a period.
- Workflow: ReportsViewModel calls repository.getSalesReports(period) -> displays rows and (optionally) charts.
- Flow: UI -> ViewModel -> Repository -> ApiService.fetchSalesReports.
- Dependencies: AppDataTable, KoalaPlot (if used), Repository, Koin.

10) Settings
- Purpose: App preferences (theme, onboarding flag, notifications) and token storage.
- Workflow: SettingsViewModel reads/writes via SettingsRepository -> PreferenceManager (Multiplatform Settings).
- Dependencies: Multiplatform Settings, Koin.

11) Users
- Purpose: Manage users; add user and view list/details.
- Workflow: UserManagementViewModel loads users via repository.getUsers; AddUserViewModel calls repository.addUser.
- Flow: UI -> ViewModel -> Repository -> ApiService.fetch/create users.
- Dependencies: AppDataTable, Repository, Koin.

## Implementation Details
- Key Classes/Interfaces
  - UI: Screens under ui/* with Screen/ScreenContent pairs; AppNavigationRailBar; AppScaffold; AppDataTable wrapper.
  - ViewModels: One per feature; expose StateFlow UiState and handle UiEvent; use viewModelScope coroutines.
  - Data Layer: DineEaseRepository (interface) and DineEaseRepositoryImpl; ApiService (Ktor); FakeApiService (in-memory); DTOs under data/dto/* with mappers to UI models.
  - DI: KoinInit to start Koin; CommonModule for HttpClient and PreferenceManager; viewModelModule for all singletons and service bindings.
  - Navigation: Destinations sealed class with @Serializable; AppNavHost wiring routes to screens.
  - Utilities: NetworkResult, SafeApiCall, PreferenceManager, SettingsRepositoryImpl.
- Configuration/Constants
  - ApiService.BASE_URL = "" (empty). TODO: Set production/staging base URL.
  - Compose Desktop packaging set to DMG/MSI/DEB with packageName com.zamulabs.dineeasepos and version 1.0.0.
  - SQLDelight database configured: name "DineEaseDatabase", package com.zamulabs.dineeasepos.database. TODO: Add .sq schema and usages if persistence is required.

## Data Management
- Storage
  - Preferences: Multiplatform Settings via PreferenceManager for bearer token, theme, onboarding, and user fields (keys defined in PreferenceManager companion object).
  - Database: SQLDelight runtime included; no schema or DAO usages present. TODO: Implement schema and repository bindings if local persistence is needed.
- Retrieval and Mapping
  - Repository methods call ApiService (or FakeApiService via DI) and map DTOs to UI models using mapper functions in data/dto.
- Caching/Sync
  - No explicit caching layer present beyond FakeApiService in-memory lists. TODO: Define caching strategy if required.

## Error Handling & Logging
- Error Strategy
  - Network calls should use safeApiCall to convert exceptions into NetworkResult.Error with parsed ErrorResponse where possible.
  - ViewModels branch on NetworkResult to update UiState error fields.
- Logging
  - Napier used for logging; Ktor Logging plugin installed when enableNetworkLogs = true in CommonModule; logs tagged (e.g., "Http Client").
  - Errors logged in SafeApiCall parsing and exception branches.

## Testing
- Frameworks: kotlin.test and JUnit 4 (ComposeAppDesktopTest shows example).
- Types: Unit tests (no integration/UI tests found). TODO: Add ViewModel/repository tests and UI tests.
- Example Flow
  - Test class ComposeAppDesktopTest uses kotlin.test assertions.
  - Run via Gradle: ./gradlew :composeApp:test.

## Deployment & Distribution
- Build System: Gradle Kotlin DSL with Compose Multiplatform plugin.
- Run (Desktop):
  - macOS/Linux: ./gradlew :composeApp:run
  - Windows: gradlew.bat :composeApp:run
- Packaging: Compose Desktop nativeDistributions targets DMG (macOS), MSI (Windows), DEB (Linux). mainClass = com.zamulabs.dineeasepos.MainKt.
- CI/CD: None present in repository. TODO: Add CI workflows for build/test/package.

## TODO / Gaps
- Configure ApiService.BASE_URL and real ApiService binding (replace FakeApiService) for production.
- Add SQLDelight schema (.sq files) and actual DAO usage or remove dependency if not needed.
- Define and implement caching strategy (if required): local DB or in-memory with invalidation.
- Expand test coverage: ViewModel tests with fake repository, repository tests with FakeApiService, and UI tests.
- Logging configuration per environment (toggle enableNetworkLogs via DI/module parameterization).
- Document environment variables/secrets if needed for real API (auth, gateways).
- Verify and document usage of KoalaPlot in dashboard/reports (currently only dependency present).
- Add detailed build/package instructions for signing (Windows/MSI, macOS/DMG) if required for distribution.