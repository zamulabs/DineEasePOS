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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class SettingsViewModel(
    private val repository: com.zamulabs.dineeasepos.data.DineEaseRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: SettingsUiEvent) {
        when (event) {
            is SettingsUiEvent.OnTabSelected -> _uiState.update { it.copy(activeTab = event.tab) }
            SettingsUiEvent.OnAddTaxRate -> {
                _uiState.update {
                    val list = it.taxRates + TaxRate("New tax", "0%", false)
                    it.copy(taxRates = list)
                }
            }
            is SettingsUiEvent.OnPrepItemChanged -> {
                _uiState.update {
                    val rows = it.morningPrepRows.toMutableList()
                    val idx = event.index.coerceIn(0, rows.size - 1)
                    rows[idx] = rows[idx].copy(item = event.value)
                    it.copy(morningPrepRows = rows)
                }
            }
            is SettingsUiEvent.OnPrepQtyChanged -> {
                _uiState.update {
                    val rows = it.morningPrepRows.toMutableList()
                    val idx = event.index.coerceIn(0, rows.size - 1)
                    rows[idx] = rows[idx].copy(quantity = event.value.filter { ch -> ch.isDigit() || ch == '.' })
                    it.copy(morningPrepRows = rows)
                }
            }
            SettingsUiEvent.OnAddPrepRow -> {
                _uiState.update { it.copy(morningPrepRows = it.morningPrepRows + MorningPrepRow()) }
            }
            SettingsUiEvent.OnSaveMorningPrep -> {
                val rows = _uiState.value.morningPrepRows
                val valid = rows.filter { it.item.isNotBlank() && it.quantity.isNotBlank() }
                viewModelScope.launch {
                    val pairs = valid.map { it.item to (it.quantity.toDoubleOrNull() ?: 0.0) }
                    repository.recordMorningPrep(pairs)
                }
            }
        }
    }
}
