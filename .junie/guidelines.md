# Project Guidelines

## Project Overview
DineEasePOS is a Compose Multiplatform desktop application (Kotlin/JVM) for managing a restaurant Point of Sale workflow. It provides modules for menu management, table management, order handling, payments, and a dashboard. The UI is built with JetBrains Compose for Desktop, and dependency management is handled via Gradle Kotlin DSL. Koin is used for dependency injection.

Key goals of the app:
- Streamline table assignments and status tracking.
- Manage menu items and categories.
- Create and manage orders and payments.
- Provide a clean, responsive desktop UI.

## Tech Stack
- Language: Kotlin (JVM)
- UI: JetBrains Compose for Desktop
- DI: Koin
- Build: Gradle (Kotlin DSL)

## Project Structure
- composeApp/
  - src/jvmMain/kotlin/com/zamulabs/dineeasepos/
    - auth, dashboard, menu, order, payment, table, navigation, theme
    - App.kt, main.kt, AppModule.kt, KoinInit.kt
  - src/jvmTest/ — desktop tests
  - composeResources/ — images, fonts, icons
- designs/ — mockups and HTML code samples
- .junie/guidelines.md — this file

## How to Build and Run
- Build from IDE: Open the project in IntelliJ IDEA or Android Studio (with KMP/Compose support). Use Gradle tasks or run the desktop application run configuration.
- CLI: Use Gradle wrapper from project root.
  - macOS/Linux: ./gradlew :composeApp:run
  - Windows: gradlew.bat :composeApp:run

## Tests
- Location: composeApp/src/jvmTest/kotlin
- Run tests:
  - IDE: Run test classes directly.
  - CLI: ./gradlew :composeApp:test

## Guidelines for Junie (AI Assistant)
- Minimal changes: prefer the smallest, targeted edits to satisfy issues.
- Use repository structure as described above; do not create files outside project root.
- When adding content to this file, keep sections succinct and accurate.
- When modifying Kotlin sources, keep code style consistent with Kotlin conventions and existing formatting.
- Prefer running specific tests related to modified areas instead of full builds, unless the issue requires a full build.
- If a build or tests are necessary for verification, use Gradle wrapper commands shown above.

## Code Style
- Kotlin style: follow standard Kotlin coding conventions.
- Compose: keep composables small and preview-friendly where applicable; use theming from theme/ package.
- DI: register modules in AppModule.kt and initialize via KoinInit.kt.

## Contribution Notes
- Keep commit scope small and messages descriptive.
- Update this guidelines file if you add new modules or change run/test commands.

## Screen Implementation Conventions
- Use AppDataTable: When implementing list/data views in any screen, use com.zamulabs.dineeasepos.components.table.AppDataTable as the primary table component.
- Required screen structure (per feature <Name>):
  - <Name>Screen: Entry composable that wires DI (Koin), obtains the ViewModel, collects UiState, and passes callbacks to content.
  - <Name>ScreenContent: Stateless UI that receives UiState and lambda handlers for UiEvent; contains layout and calls to AppDataTable.
  - <Name>ViewModel: Holds state and business logic, exposes StateFlow/<UiState>, and functions to handle <UiEvent>.
  - <Name>UiState: Immutable state holder (data class), e.g., orders, searchString, loading, error.
  - <Name>UiEvent: Sealed interface/class for user intents, e.g., OnClickNewOrder, OnSearchChanged.
- Naming examples:
  - OrderManagementScreen, OrderManagementScreenContent, OrderManagementViewModel, OrderManagementUiState, OrderManagementUiEvent.
- Design fidelity: Match designs exactly using the provided assets:
  - designs/screen.png (visual reference)
  - designs/code.html (HTML reference for spacing, colors, and layout behavior)
- Implementation checklist:
  - Wire ViewModel via Koin module in AppModule.kt and initialize with KoinInit.kt.
  - In Screen, collect state with collectAsState and pass to ScreenContent.
  - Use AppDataTable for tabular data; ensure columns, sorting, selection, and paddings mirror the design.
  - Provide UiEvent handlers for all interactive elements; ensure Everything should work as per the design.
  - Reuse theme values (colors, typography, shapes) from theme/ package to ensure consistent look and feel.
