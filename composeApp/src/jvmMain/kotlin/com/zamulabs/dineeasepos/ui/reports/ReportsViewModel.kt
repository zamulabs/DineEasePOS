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
package com.zamulabs.dineeasepos.ui.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReportsViewModel(
    private val repository: com.zamulabs.dineeasepos.data.DineEaseRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        ReportsUiState(
            rows = emptyList(),
        )
    )
    val uiState: StateFlow<ReportsUiState> = _uiState

    private val _uiEffect = Channel<ReportsUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    private fun updateUiState(block: ReportsUiState.() -> ReportsUiState) {
        _uiState.update(block)
    }

    fun onEvent(event: ReportsUiEvent) {
        when (event) {
            is ReportsUiEvent.OnTabSelected -> updateUiState { copy(selectedTab = event.tab) }
            is ReportsUiEvent.OnPeriodChanged -> {
                updateUiState { copy(period = event.period) }
                getSalesReports()
            }
            ReportsUiEvent.OnExport -> { /* no-op for now */ }
        }
    }

    fun getSalesReports() {
        viewModelScope.launch {
            val period = _uiState.value.period
            when (val result = repository.getSalesReports(period)) {
                is com.zamulabs.dineeasepos.utils.NetworkResult.Error -> {
                    updateUiState { /* keep previous rows */ this }
                }
                is com.zamulabs.dineeasepos.utils.NetworkResult.Success -> {
                    updateUiState { copy(rows = result.data.orEmpty()) }
                }
            }
        }
    }
}
