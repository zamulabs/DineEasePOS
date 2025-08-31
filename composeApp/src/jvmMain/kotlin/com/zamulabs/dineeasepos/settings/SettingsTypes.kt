package com.zamulabs.dineeasepos.settings

// UiState
data class SettingsUiState(
    val activeTab: SettingsTab = SettingsTab.Taxes,
    val taxRates: List<TaxRate> = listOf(
        TaxRate("Sales tax", "7.25%", true),
        TaxRate("City tax", "1.5%", true),
        TaxRate("County tax", "0.75%", false),
    ),
)

enum class SettingsTab { General, Payments, Receipts, Taxes, System }

data class TaxRate(val name: String, val rate: String, val active: Boolean)

// UiEvent
sealed interface SettingsUiEvent {
    data class OnTabSelected(val tab: SettingsTab): SettingsUiEvent
    data object OnAddTaxRate: SettingsUiEvent
}
