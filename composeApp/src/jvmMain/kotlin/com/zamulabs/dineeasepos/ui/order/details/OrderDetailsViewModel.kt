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
package com.zamulabs.dineeasepos.ui.order.details

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

class OrderDetailsViewModel(
    private val repository: DineEaseRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(OrderDetailsUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = Channel<OrderDetailsUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    fun updateUiState(block: OrderDetailsUiState.() -> OrderDetailsUiState) {
        _uiState.update(block)
    }

    fun loadOrderDetails(orderId: String? = null) {
        viewModelScope.launch {
            when (val result = repository.getOrders()) {
                is NetworkResult.Error -> {
                    // Keep existing sample but show error snackbar
                    _uiEffect.trySend(OrderDetailsUiEffect.ShowSnackBar(result.errorMessage ?: "Failed to load order details"))
                }
                is NetworkResult.Success -> {
                    val orders = result.data.orEmpty()
                    val order = if (orderId != null) orders.find { it.id == orderId } else orders.firstOrNull()
                    order?.let { o ->
                        updateUiState {
                            copy(
                                orderId = o.id,
                                table = o.table,
                                placedOn = o.time,
                                total = o.total,
                                // keep other fields/sample data for now until API provides details
                            )
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: OrderDetailsUiEvent) {
        when (event) {
            OrderDetailsUiEvent.Cancel -> { /* TODO: cancel order via repository */ }
            OrderDetailsUiEvent.Complete -> { /* TODO: complete order via repository */ }
            OrderDetailsUiEvent.MarkPreparing -> {
                // In new UX, this is used as "Process Payment" entrypoint from side pane
                _uiEffect.trySend(OrderDetailsUiEffect.NavigateToPayment(orderId = _uiState.value.orderId))
            }
            OrderDetailsUiEvent.MarkReady -> { /* TODO: mark ready */ }
            OrderDetailsUiEvent.GenerateReceipt -> {
                // Minimal behavior: navigate to Receipts screen to view/print
                // A real implementation would generate and pass receipt id
                _uiEffect.trySend(OrderDetailsUiEffect.ShowSnackBar("Generating receipt..."))
                _uiEffect.trySend(OrderDetailsUiEffect.NavigateToReceipt(orderId = _uiState.value.orderId))
            }
        }
    }
}
