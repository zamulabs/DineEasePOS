package com.zamulabs.dineeasepos.user

import androidx.compose.runtime.Immutable

@Immutable
data class User(
    val name: String,
    val role: String,
    val active: Boolean,
)

@Immutable
data class UserManagementUiState(
    val users: List<User> = emptyList(),
)

sealed interface UserManagementUiEvent {
    data object OnClickAddUser: UserManagementUiEvent
    data class OnToggleActive(val index: Int): UserManagementUiEvent
    data class OnEdit(val index: Int): UserManagementUiEvent
}
