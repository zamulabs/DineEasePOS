package com.zamulabs.dineeasepos.menu

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MenuManagementViewModel: ViewModel() {
    var uiState by mutableStateOf(sample())
        private set

    fun onEvent(event: MenuManagementUiEvent){
        when(event){
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
        val items = listOf(
            MenuItem("Spicy Chicken Sandwich","Sandwiches","$9.99", true),
            MenuItem("Classic Cheeseburger","Burgers","$8.49", true),
            MenuItem("Garden Salad","Salads","$7.99", true),
            MenuItem("Fries","Sides","$3.49", true),
            MenuItem("Chocolate Cake","Desserts","$5.99", false),
        )
        return MenuManagementUiState(items = items)
    }
}
