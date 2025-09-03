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
package com.zamulabs.dineeasepos.ui.receipt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReceiptViewModel(
    private val repository: com.zamulabs.dineeasepos.data.DineEaseRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ReceiptUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = Channel<ReceiptUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    private fun updateUiState(block: ReceiptUiState.() -> ReceiptUiState) {
        _uiState.update(block)
    }

    fun onEvent(event: ReceiptUiEvent) {
        when (event) {
            is ReceiptUiEvent.OnSearchChanged -> updateUiState { copy(search = event.value) }
            ReceiptUiEvent.OnExport -> { /* TODO: export receipts */ }
        }
    }

    fun loadReceipts() {
        viewModelScope.launch {
            updateUiState { copy(isLoading = true) }
            when (val result = repository.getPayments()) {
                is com.zamulabs.dineeasepos.utils.NetworkResult.Error -> {
                    updateUiState {
                        copy(
                            isLoading = false,
                            error = result.errorMessage,
                            items = sampleReceipts(),
                        )
                    }
                }
                is com.zamulabs.dineeasepos.utils.NetworkResult.Success -> {
                    val payments = result.data.orEmpty()
                    val mapped = payments.map { p ->
                        ReceiptListItem(
                            receiptNo = "R-${'$'}{p.orderId}",
                            orderId = p.orderId,
                            date = p.date,
                            method = p.method,
                            amount = p.amount,
                        )
                    }
                    updateUiState { copy(isLoading = false, items = if (mapped.isEmpty()) sampleReceipts() else mapped) }
                }
            }
        }
    }
}

private fun sampleReceipts(): List<ReceiptListItem> = listOf(
    ReceiptListItem("R-ORD-20240720-001", "ORD-20240720-001", "Jul 20, 2024", "Credit Card", "$55.75"),
    ReceiptListItem("R-ORD-20240719-002", "ORD-20240719-002", "Jul 19, 2024", "Cash", "$22.50"),
)

sealed class ReceiptUiEffect {
    data class ShowSnackBar(val message: String) : ReceiptUiEffect()
}
