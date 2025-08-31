package com.zamulabs.dineeasepos.receipt

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun ReceiptScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val vm: ReceiptViewModel = org.koin.compose.koinInject()
    val state = vm.uiState
    ReceiptScreenContent(
        state = state,
        onEvent = vm::onEvent,
        onBack = { navController.popBackStack() },
    )
}
