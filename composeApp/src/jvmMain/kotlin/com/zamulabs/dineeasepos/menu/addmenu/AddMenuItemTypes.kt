package com.zamulabs.dineeasepos.menu.addmenu

import androidx.compose.runtime.Immutable

@Immutable
data class AddMenuItemUiState(
    val name: String = "",
    val description: String = "",
    val price: String = "",
    val category: String = "",
    val status: String = "",
    val prepTimeMinutes: String = "",
    val ingredients: String = "",
    val categories: List<String> = listOf("Select Category", "Starters", "Mains", "Desserts", "Drinks"),
    val statuses: List<String> = listOf("Select Status", "Active", "Inactive"),
)

sealed interface AddMenuItemUiEvent {
    data class OnNameChanged(val value: String): AddMenuItemUiEvent
    data class OnDescriptionChanged(val value: String): AddMenuItemUiEvent
    data class OnPriceChanged(val value: String): AddMenuItemUiEvent
    data class OnCategoryChanged(val value: String): AddMenuItemUiEvent
    data class OnStatusChanged(val value: String): AddMenuItemUiEvent
    data class OnPrepTimeChanged(val value: String): AddMenuItemUiEvent
    data class OnIngredientsChanged(val value: String): AddMenuItemUiEvent
    data object OnCancel: AddMenuItemUiEvent
    data object OnSave: AddMenuItemUiEvent
}
