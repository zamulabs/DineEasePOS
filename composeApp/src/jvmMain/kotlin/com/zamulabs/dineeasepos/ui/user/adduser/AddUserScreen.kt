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
package com.zamulabs.dineeasepos.ui.user.adduser

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject

@Composable
fun AddUserScreen(
    navController: androidx.navigation.NavController,
    modifier: Modifier = Modifier,
    vm: AddUserViewModel = koinInject<AddUserViewModel>()
){
    val state by vm.uiState.collectAsState()
    val credentialsToShow = androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf<Pair<String,String>?>(null) }

    com.zamulabs.dineeasepos.utils.ObserverAsEvent(flow = vm.uiEffect) { effect ->
        when (effect) {
            is AddUserUiEffect.ShowSnackBar -> { /* hook up snackbar if needed */ }
            is AddUserUiEffect.ShowToast -> { /* TODO desktop toast */ }
            is AddUserUiEffect.ShowCredentials -> {
                credentialsToShow.value = effect.email to effect.tempPassword
            }
            AddUserUiEffect.NavigateBack -> { navController.popBackStack() }
        }
    }

    AddUserScreenContent(
        state = state,
        onEvent = vm::onEvent,
        modifier = modifier,
    )

    credentialsToShow.value?.let { (email, temp) ->
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { /* block dismiss; force explicit action */ },
            title = { androidx.compose.material3.Text("Credentials generated") },
            text = {
                androidx.compose.foundation.layout.Column {
                    androidx.compose.material3.Text("Provide these login details to the user. They are shown once.")
                    androidx.compose.material3.Text("Email: $email")
                    androidx.compose.material3.Text("Temporary password: $temp")
                }
            },
            confirmButton = {
                androidx.compose.material3.TextButton(onClick = {
                    credentialsToShow.value = null
                    // After acknowledging, navigate back
                    navController.popBackStack()
                }) { androidx.compose.material3.Text("Done") }
            }
        )
    }
}
