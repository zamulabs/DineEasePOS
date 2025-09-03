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
package com.zamulabs.dineeasepos.ui.user

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.zamulabs.dineeasepos.utils.ObserverAsEvent
import org.koin.compose.koinInject

@Composable
fun UserManagementScreen(
    onEdit: (Int) -> Unit = {},
    onAddUser: () -> Unit = {},
    viewModel: UserManagementViewModel = koinInject<UserManagementViewModel>()
){
    val state by viewModel.uiState.collectAsState()

    ObserverAsEvent(flow = viewModel.uiEffect) { effect ->
        when (effect) {
            is UserManagementUiEffect.ShowSnackBar -> {}
            is UserManagementUiEffect.ShowToast -> {}
            UserManagementUiEffect.NavigateBack -> {}
        }
    }

    UserManagementScreenContent(
        state = state,
        onEvent = {
            when(it){
                is UserManagementUiEvent.OnEdit -> onEdit(it.index)
                is UserManagementUiEvent.OnClickAddUser -> onAddUser()
                else -> viewModel.onEvent(it)
            }
        }
    )
}
