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
            ReceiptUiEvent.OnEmail -> { /* no-op for now */ }
            ReceiptUiEvent.OnPrint -> { /* no-op for now */ }
            ReceiptUiEvent.OnSavePdf -> { /* no-op for now */ }
        }
    }

    fun loadReceipt(orderId: String?) {
        if (orderId.isNullOrBlank()) return // keep default demo state
        viewModelScope.launch {
            when (val result = repository.getReceipt(orderId)) {
                is com.zamulabs.dineeasepos.utils.NetworkResult.Error -> {
                    // Stick to current demo state on error
                }
                is com.zamulabs.dineeasepos.utils.NetworkResult.Success -> {
                    result.data?.let { data -> _uiState.update { data } }
                }
            }
        }
    }
}

sealed class ReceiptUiEffect {
    data class ShowSnackBar(val message: String) : ReceiptUiEffect()
}
