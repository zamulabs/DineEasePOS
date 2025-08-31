package com.zamulabs.dineeasepos.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class UserManagementViewModel: ViewModel() {
    var uiState by mutableStateOf(
        UserManagementUiState(
            users = listOf(
                User("Sophia Bennett", "Manager", true),
                User("Ethan Carter", "Server", true),
                User("Olivia Davis", "Chef", true),
                User("Liam Foster", "Bartender", false),
                User("Ava Green", "Hostess", true),
            )
        )
    )
        private set

    fun onEvent(event: UserManagementUiEvent){
        when(event){
            is UserManagementUiEvent.OnClickAddUser -> { /* route from Screen */ }
            is UserManagementUiEvent.OnEdit -> { /* route from Screen */ }
            is UserManagementUiEvent.OnToggleActive -> {
                val list = uiState.users.toMutableList()
                val u = list[event.index]
                list[event.index] = u.copy(active = !u.active)
                uiState = uiState.copy(users = list)
            }
        }
    }
}
