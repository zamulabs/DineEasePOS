package com.zamulabs.dineeasepos.table.addtable

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.koin.compose.koinInject

@Composable
fun AddTableScreen(
    navController: NavController,
    vm: AddTableViewModel = koinInject<AddTableViewModel>()
){
    val state = vm.uiState
    AddTableScreenContent(
        state = state,
        onEvent = vm::onEvent,
        onSave = { navController.popBackStack() },
        onCancel = { navController.popBackStack() }
    )
}
