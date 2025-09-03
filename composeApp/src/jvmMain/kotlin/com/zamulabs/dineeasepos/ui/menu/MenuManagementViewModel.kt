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
package com.zamulabs.dineeasepos.ui.menu

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MenuManagementViewModel : ViewModel() {
    var uiState by mutableStateOf(sample())
        private set

    fun onEvent(event: MenuManagementUiEvent) {
        when (event) {
            MenuManagementUiEvent.OnClickAddItem -> {}
            is MenuManagementUiEvent.OnSearch -> uiState = uiState.copy(searchString = event.query)
            is MenuManagementUiEvent.OnTabSelected -> uiState = uiState.copy(selectedTab = event.tab)
            is MenuManagementUiEvent.OnToggleActive -> {
                val list = uiState.items.toMutableList()
                val item = list[event.index]
                list[event.index] = item.copy(active = !item.active)
                uiState = uiState.copy(items = list)
            }
            is MenuManagementUiEvent.OnEdit -> {}
        }
    }

    private fun sample(): MenuManagementUiState {
        val items =
            listOf(
                MenuItem("Spicy Chicken Sandwich", "Sandwiches", "$9.99", true),
                MenuItem("Classic Cheeseburger", "Burgers", "$8.49", true),
                MenuItem("Garden Salad", "Salads", "$7.99", true),
                MenuItem("Fries", "Sides", "$3.49", true),
                MenuItem("Chocolate Cake", "Desserts", "$5.99", false),
            )
        return MenuManagementUiState(items = items)
    }
}
