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
    viewModel: UserManagementViewModel = koinInject<UserManagementViewModel>()
){
    val state by viewModel.uiState.collectAsState()

    androidx.compose.runtime.LaunchedEffect(viewModel) {
        viewModel.loadUsers()
    }

    ObserverAsEvent(flow = viewModel.uiEffect) { effect ->
        when (effect) {
            is UserManagementUiEffect.ShowSnackBar -> {}
            is UserManagementUiEffect.ShowToast -> {}
            UserManagementUiEffect.NavigateBack -> {}
        }
    }

    // Observe AddUser effects to close side pane and refresh list
    val addVm = org.koin.compose.koinInject<com.zamulabs.dineeasepos.ui.user.adduser.AddUserViewModel>()
    ObserverAsEvent(flow = addVm.uiEffect) { effect ->
        when (effect) {
            is com.zamulabs.dineeasepos.ui.user.adduser.AddUserUiEffect.ShowSnackBar -> {}
            is com.zamulabs.dineeasepos.ui.user.adduser.AddUserUiEffect.ShowToast -> {}
            com.zamulabs.dineeasepos.ui.user.adduser.AddUserUiEffect.NavigateBack -> {
                // Close add pane and refresh users
                viewModel.onEvent(UserManagementUiEvent.OnEdit(index = -1))
                // Reset: set no selection and hide add
                // simpler: update state directly via loadUsers; selection will be null, showAddUser should already be false
                viewModel.loadUsers()
            }
        }
    }

    com.zamulabs.dineeasepos.ui.components.SplitScreenScaffold(
        main = {
            UserManagementScreenContent(
                state = state,
                onEvent = { ev -> viewModel.onEvent(ev) }
            )
        },
        side = {
            val showAdd = state.showAddUser
            if (showAdd) {
                val addVm = org.koin.compose.koinInject<com.zamulabs.dineeasepos.ui.user.adduser.AddUserViewModel>()
                val addState by addVm.uiState.collectAsState()
                com.zamulabs.dineeasepos.ui.user.adduser.AddUserScreenContent(
                    state = addState,
                    onEvent = { addVm.onEvent(it) }
                )
            } else {
                // Render scaffolded user details content
                run {
                    val detailsVm = org.koin.compose.koinInject<com.zamulabs.dineeasepos.ui.user.details.UserDetailsViewModel>()
                    val detailsState by detailsVm.uiState.collectAsState()
                    androidx.compose.runtime.LaunchedEffect(state.selectedUser) {
                        val sel = state.selectedUser
                        if (sel != null) {
                            detailsVm.updateUiState {
                                copy(
                                    name = sel.name,
                                    role = sel.role,
                                    active = sel.active,
                                )
                            }
                        }
                    }
                    com.zamulabs.dineeasepos.ui.user.details.UserDetailsScreenContent(
                        state = detailsState,
                        onEvent = { /* could forward to VM or management VM */ },
                        onBack = { }
                    )
                }
            }
        }
    )
}
