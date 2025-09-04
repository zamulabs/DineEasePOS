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
package com.zamulabs.dineeasepos.ui.order.neworder

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

class NewOrderViewModel(
    private val repository: DineEaseRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(NewOrderUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = Channel<NewOrderUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    fun updateUiState(block: NewOrderUiState.() -> NewOrderUiState) {
        _uiState.update(block)
    }

    fun onEvent(event: NewOrderUiEvent) {
        when (event) {
            is NewOrderUiEvent.OnSearch -> updateUiState { copy(searchString = event.query) }
            is NewOrderUiEvent.OnTableSelected -> updateUiState { copy(selectedTable = event.table) }
            is NewOrderUiEvent.OnCategorySelected -> updateUiState { copy(selectedCategoryIndex = event.index) }
            is NewOrderUiEvent.OnNotesChanged -> updateUiState { copy(notes = event.notes) }
            is NewOrderUiEvent.OnAddToCart -> addToCart(event.title)
            is NewOrderUiEvent.OnIncQty -> changeQty(event.title, +1)
            is NewOrderUiEvent.OnDecQty -> changeQty(event.title, -1)
            is NewOrderUiEvent.OnRemoveItem -> removeItem(event.title)
            NewOrderUiEvent.OnPlaceOrder -> { /* handled by screen payment pane toggle for now */ }
        }
    }

    private fun addToCart(title: String) {
        viewModelScope.launch {
            val price = guessPrice(title)
            val existing = _uiState.value.cart.find { it.title == title }
            val newQty = (existing?.quantity ?: 0) + 1
            when (val ok = repository.checkStock(title, newQty)) {
                is NetworkResult.Error -> _uiEffect.trySend(NewOrderUiEffect.ShowSnackBar(ok.errorMessage ?: "Failed to check stock"))
                is NetworkResult.Success -> {
                    if (ok.data == true) {
                        repository.adjustStock(title, -1)
                        updateUiState {
                            val list = cart.toMutableList()
                            if (existing == null) list.add(CartItem(title, 1, price)) else list[cart.indexOf(existing)] = existing.copy(quantity = newQty)
                            copy(cart = list)
                        }
                    } else {
                        _uiEffect.trySend(NewOrderUiEffect.ShowSnackBar("Insufficient stock for $title"))
                    }
                }
            }
        }
    }

    private fun changeQty(title: String, delta: Int) {
        viewModelScope.launch {
            val current = _uiState.value.cart.find { it.title == title } ?: return@launch
            val targetQty = (current.quantity + delta).coerceAtLeast(0)
            if (targetQty == 0 && delta < 0) {
                // return stock and remove
                repository.adjustStock(title, +current.quantity)
                removeItem(title)
                return@launch
            }
            if (delta > 0) {
                when (val ok = repository.checkStock(title, current.quantity + 1)) {
                    is NetworkResult.Error -> _uiEffect.trySend(NewOrderUiEffect.ShowSnackBar(ok.errorMessage ?: "Failed to check stock"))
                    is NetworkResult.Success -> {
                        if (ok.data == true) {
                            repository.adjustStock(title, -1)
                            updateUiState {
                                val list = cart.toMutableList()
                                val idx = list.indexOfFirst { it.title == title }
                                list[idx] = list[idx].copy(quantity = list[idx].quantity + 1)
                                copy(cart = list)
                            }
                        } else _uiEffect.trySend(NewOrderUiEffect.ShowSnackBar("Insufficient stock for $title"))
                    }
                }
            } else if (delta < 0) {
                repository.adjustStock(title, +1)
                updateUiState {
                    val list = cart.toMutableList()
                    val idx = list.indexOfFirst { it.title == title }
                    val newQ = (list[idx].quantity - 1).coerceAtLeast(0)
                    if (newQ == 0) list.removeAt(idx) else list[idx] = list[idx].copy(quantity = newQ)
                    copy(cart = list)
                }
            }
        }
    }

    private fun removeItem(title: String) {
        val existing = _uiState.value.cart.find { it.title == title } ?: return
        viewModelScope.launch {
            repository.adjustStock(title, +existing.quantity)
            updateUiState { copy(cart = cart.filterNot { it.title == title }) }
        }
    }

    private fun guessPrice(title: String): Double {
        val catalog = _uiState.value.items
        val fromMenu = catalog.find { it.title == title }
        val priceFromMenu = fromMenu?.description?.let { s ->
            // We don't have price in menu DTO; fallback to a simple sample mapping
            null
        }
        return priceFromMenu ?: when {
            title.contains("pizza", true) -> 10.0
            title.contains("salad", true) -> 6.5
            title.contains("soup", true) -> 5.0
            else -> 8.0
        }
    }

    fun loadData() {
        viewModelScope.launch {
            // Load tables
            updateUiState { copy(isLoadingTables = true) }
            when (val tablesRes = repository.getTables()) {
                is NetworkResult.Error -> {
                    updateUiState {
                        copy(
                            isLoadingTables = false,
                            errorLoadingTables = tablesRes.errorMessage,
                            tables = listOf("Select Table")
                        )
                    }
                }
                is NetworkResult.Success -> {
                    val tableLabels = listOf("Select Table", "Takeaway") + (tablesRes.data?.map { it.number } ?: emptyList())
                    updateUiState { copy(isLoadingTables = false, tables = tableLabels) }
                }
            }

            // Load menu items to display
            updateUiState { copy(isLoadingMenuItems = true) }
            when (val menuRes = repository.getMenu()) {
                is NetworkResult.Error -> {
                    updateUiState {
                        copy(
                            isLoadingMenuItems = false,
                            errorLoadingMenuItems = menuRes.errorMessage,
                            items = emptyList()
                        )
                    }
                }
                is NetworkResult.Success -> {
                    val items = menuRes.data?.map {
                        MenuItem(
                            title = it.name,
                            description = it.category,
                            imageUrl = "" // no image from API
                        )
                    } ?: emptyList()
                    updateUiState { copy(isLoadingMenuItems = false, items = items) }
                }
            }
        }
    }
}
