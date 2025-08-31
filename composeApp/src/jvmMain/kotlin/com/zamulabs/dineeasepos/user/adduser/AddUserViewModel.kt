package com.zamulabs.dineeasepos.user.adduser

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AddUserViewModel: ViewModel(){
    var state by mutableStateOf(AddUserUiState())
        private set

    fun onEvent(event: AddUserUiEvent){
        when(event){
            is AddUserUiEvent.OnNameChanged -> state = state.copy(name = event.value)
            is AddUserUiEvent.OnEmailChanged -> state = state.copy(email = event.value)
            is AddUserUiEvent.OnRoleChanged -> state = state.copy(role = event.value)
            is AddUserUiEvent.OnPasswordChanged -> state = state.copy(password = event.value)
            is AddUserUiEvent.OnConfirmPasswordChanged -> state = state.copy(confirmPassword = event.value)
            is AddUserUiEvent.OnActiveChanged -> state = state.copy(isActive = event.value)
            AddUserUiEvent.OnCancel -> { /* no-op */ }
            AddUserUiEvent.OnSave -> { /* TODO persist */ }
        }
    }
}
