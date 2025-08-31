package com.zamulabs.dineeasepos.menu.addmenu

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.koin.compose.koinInject

@Composable
fun AddMenuItemScreen(
    navController: NavController,
    vm: AddMenuItemViewModel = koinInject<AddMenuItemViewModel>()
){
    AddMenuItemScreenContent(
        state = vm.uiState,
        onEvent = vm::onEvent,
        onSave = { navController.popBackStack() },
        onCancel = { navController.popBackStack() }
    )
}
