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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.zamulabs.dineeasepos.ui.navigation.Destinations
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val vm: LoginViewModel = org.koin.compose.koinInject()
    val state = vm.uiState.collectAsState().value
    val scope = rememberCoroutineScope()

    com.zamulabs.dineeasepos.utils.ObserverAsEvent(flow = vm.uiEffect) { effect ->
        when (effect) {
            is LoginUiEffect.ShowSnackBar -> {
                scope.launch {
                    state.snackbarHostState.showSnackbar(effect.message)
                }
            }
            is LoginUiEffect.ShowToast -> { /* TODO: desktop toast */ }
            LoginUiEffect.NavigateToDashboard -> navController.navigate(Destinations.Dashboard)
        }
    }

    LoginScreenContent(
        state = state,
        onEvent = vm::onEvent,
        onLoginSuccess = { /* navigation handled via effect */ },
        modifier = modifier
    )
}
