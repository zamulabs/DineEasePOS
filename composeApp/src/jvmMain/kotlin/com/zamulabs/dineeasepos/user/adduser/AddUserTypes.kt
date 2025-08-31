package com.zamulabs.dineeasepos.user.adduser

import androidx.compose.runtime.Immutable

@Immutable
data class AddUserUiState(
    val name: String = "",
    val email: String = "",
    val role: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isActive: Boolean = true,
    val roles: List<String> = listOf("Select role","Admin","Manager","Waiter","Chef"),
)

sealed interface AddUserUiEvent{
    data class OnNameChanged(val value: String): AddUserUiEvent
    data class OnEmailChanged(val value: String): AddUserUiEvent
    data class OnRoleChanged(val value: String): AddUserUiEvent
    data class OnPasswordChanged(val value: String): AddUserUiEvent
    data class OnConfirmPasswordChanged(val value: String): AddUserUiEvent
    data class OnActiveChanged(val value: Boolean): AddUserUiEvent
    data object OnCancel: AddUserUiEvent
    data object OnSave: AddUserUiEvent
}
