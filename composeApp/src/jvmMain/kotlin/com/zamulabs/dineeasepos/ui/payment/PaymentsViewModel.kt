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
package com.zamulabs.dineeasepos.ui.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zamulabs.dineeasepos.data.DineEaseRepository
import com.zamulabs.dineeasepos.utils.NetworkResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PaymentsViewModel(
    private val repository: DineEaseRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(PaymentsUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = Channel<PaymentsUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    fun updateUiState(block: PaymentsUiState.() -> PaymentsUiState) {
        _uiState.update(block)
    }

    fun onEvent(event: PaymentsUiEvent) {
        when (event) {
            is PaymentsUiEvent.OnFilterChanged -> updateUiState { copy(filter = event.value) }
            PaymentsUiEvent.OnExport -> { /* TODO: export */ }
        }
    }

    fun loadPayments() {
        viewModelScope.launch {
            updateUiState { copy(isLoadingPayments = true) }
            when (val result = repository.getPayments()) {
                is NetworkResult.Error -> {
                    updateUiState {
                        copy(
                            isLoadingPayments = false,
                            errorLoadingPayments = result.errorMessage,
                            items = samplePayments(),
                        )
                    }
                }

                is NetworkResult.Success -> {
                    updateUiState {
                        copy(
                            isLoadingPayments = false,
                            items = result.data ?: samplePayments(),
                        )
                    }
                }
            }
        }
    }
}

private fun samplePayments(): List<PaymentItem> =
    listOf(
        PaymentItem("Jul 20, 2024", "ORD-20240720-001", "Credit Card", "$55.75"),
        PaymentItem("Jul 19, 2024", "ORD-20240719-002", "Cash", "$22.50"),
        PaymentItem("Jul 18, 2024", "ORD-20240718-003", "Credit Card", "$38.90"),
        PaymentItem("Jul 17, 2024", "ORD-20240717-004", "Mobile Payment", "$45.20"),
        PaymentItem("Jul 16, 2024", "ORD-20240716-005", "Cash", "$15.00"),
        PaymentItem("Jul 15, 2024", "ORD-20240715-006", "Credit Card", "$62.30"),
        PaymentItem("Jul 14, 2024", "ORD-20240714-007", "Mobile Payment", "$28.45"),
        PaymentItem("Jul 13, 2024", "ORD-20240713-008", "Cash", "$18.75"),
        PaymentItem("Jul 12, 2024", "ORD-20240712-009", "Credit Card", "$51.10"),
        PaymentItem("Jul 11, 2024", "ORD-20240711-010", "Mobile Payment", "$33.60"),
    )
