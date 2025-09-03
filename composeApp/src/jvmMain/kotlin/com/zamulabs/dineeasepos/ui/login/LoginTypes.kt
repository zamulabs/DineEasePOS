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
package com.zamulabs.dineeasepos.ui.login

import androidx.compose.runtime.Immutable

@Immutable
 data class LoginUiState(
     val email: String = "",
     val password: String = "",
     val loading: Boolean = false,
     val error: String? = null,
     val showForgotPassword: Boolean = false,
     val showResetPassword: Boolean = false,
     val resetCode: String = "",
     val newPassword: String = "",
     val confirmPassword: String = "",
     val snackbarHostState: androidx.compose.material3.SnackbarHostState = androidx.compose.material3.SnackbarHostState(),
 )

 sealed interface LoginUiEvent {
     data class OnEmailChanged(
         val value: String,
     ) : LoginUiEvent

     data class OnPasswordChanged(
         val value: String,
     ) : LoginUiEvent

     data object OnSubmit : LoginUiEvent

     data object OnForgotPassword : LoginUiEvent
     data object OnDismissForgot : LoginUiEvent
     data object OnSubmitForgot : LoginUiEvent

     data object OnShowReset : LoginUiEvent
     data object OnDismissReset : LoginUiEvent
     data object OnSubmitReset : LoginUiEvent

     data class OnResetCodeChanged(val value: String) : LoginUiEvent
     data class OnNewPasswordChanged(val value: String) : LoginUiEvent
     data class OnConfirmPasswordChanged(val value: String) : LoginUiEvent

     data object OnSignup : LoginUiEvent
 }

 sealed class LoginUiEffect {
     data class ShowSnackBar(val message: String) : LoginUiEffect()
     data class ShowToast(val message: String) : LoginUiEffect()
     data object NavigateToDashboard : LoginUiEffect()
 }
