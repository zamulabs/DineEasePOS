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
package com.zamulabs.dineeasepos.ui.order

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

class OrderManagementViewModel(
    private val repository: DineEaseRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(OrderManagementUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = Channel<OrderManagementUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    fun updateUiState(block: OrderManagementUiState.() -> OrderManagementUiState) {
        _uiState.update(block)
    }

    fun onEvent(event: OrderManagementUiEvent) {
        when (event) {
            OrderManagementUiEvent.OnClickNewOrder -> {
                // Navigation handled by Screen via event
            }

            is OrderManagementUiEvent.OnSearch -> {
                updateUiState { copy(searchString = event.query) }
            }

            is OrderManagementUiEvent.OnTabSelected -> {
                updateUiState { copy(selectedTab = event.tab) }
            }

            is OrderManagementUiEvent.OnClickViewDetails -> {
                // Navigation handled by Screen
            }
        }
    }

    fun loadOrders() {
        viewModelScope.launch {
            updateUiState { copy(isLoadingOrders = true) }
            when (val result = repository.getOrders()) {
                is NetworkResult.Error -> {
                    updateUiState {
                        copy(
                            isLoadingOrders = false,
                            errorLoadingOrders = result.errorMessage,
                        )
                    }
                }

                is NetworkResult.Success -> {
                    updateUiState {
                        copy(
                            isLoadingOrders = false,
                            orders = result.data.orEmpty(),
                        )
                    }
                }
            }
        }
    }

}
