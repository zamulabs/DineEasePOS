/*
 * Copyright 2025 Zamulabs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zamulabs.dineeasepos.ui.settings

import androidx.compose.material3.SnackbarHostState

// UiState
data class SettingsUiState(
    val activeTab: SettingsTab = SettingsTab.Taxes,
    val taxRates: List<TaxRate> =
        listOf(
            TaxRate("Sales tax", "7.25%", true),
            TaxRate("City tax", "1.5%", true),
            TaxRate("County tax", "0.75%", false),
        ),
    val snackbarHostState: SnackbarHostState = SnackbarHostState(),
)

enum class SettingsTab { General, Payments, Receipts, Taxes, System }

data class TaxRate(
    val name: String,
    val rate: String,
    val active: Boolean,
)

// UiEvent
sealed interface SettingsUiEvent {
    data class OnTabSelected(
        val tab: SettingsTab,
    ) : SettingsUiEvent

    data object OnAddTaxRate : SettingsUiEvent
}
