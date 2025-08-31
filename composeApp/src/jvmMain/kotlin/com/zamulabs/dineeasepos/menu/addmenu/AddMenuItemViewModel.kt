package com.zamulabs.dineeasepos.menu.addmenu

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AddMenuItemViewModel: ViewModel() {
    var uiState by mutableStateOf(AddMenuItemUiState())
        private set

    fun onEvent(event: AddMenuItemUiEvent){
        when(event){
            is AddMenuItemUiEvent.OnNameChanged -> uiState = uiState.copy(name = event.value)
            is AddMenuItemUiEvent.OnDescriptionChanged -> uiState = uiState.copy(description = event.value)
            is AddMenuItemUiEvent.OnPriceChanged -> uiState = uiState.copy(price = event.value.filter { it.isDigit() || it=='.' }.take(10))
            is AddMenuItemUiEvent.OnCategoryChanged -> uiState = uiState.copy(category = event.value)
            is AddMenuItemUiEvent.OnStatusChanged -> uiState = uiState.copy(status = event.value)
            is AddMenuItemUiEvent.OnPrepTimeChanged -> uiState = uiState.copy(prepTimeMinutes = event.value.filter { it.isDigit() }.take(3))
            is AddMenuItemUiEvent.OnIngredientsChanged -> uiState = uiState.copy(ingredients = event.value)
            AddMenuItemUiEvent.OnCancel -> {}
            AddMenuItemUiEvent.OnSave -> {}
        }
    }
}
