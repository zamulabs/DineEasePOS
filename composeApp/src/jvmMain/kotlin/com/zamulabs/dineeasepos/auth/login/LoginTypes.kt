package com.zamulabs.dineeasepos.auth.login

import androidx.compose.runtime.Immutable

@Immutable
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val error: String? = null,
)

sealed interface LoginUiEvent {
    data class OnEmailChanged(val value: String): LoginUiEvent
    data class OnPasswordChanged(val value: String): LoginUiEvent
    data object OnSubmit: LoginUiEvent
    data object OnForgotPassword: LoginUiEvent
    data object OnSignup: LoginUiEvent
}
