package com.zamulabs.dineeasepos.user

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.koinInject

@Composable
fun UserManagementScreen(
    onEdit: (Int) -> Unit = {},
    onAddUser: () -> Unit = {},
    viewModel: UserManagementViewModel = koinInject<UserManagementViewModel>()
){
    val state = viewModel.uiState
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
