package com.zamulabs.dineeasepos.payment

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun PaymentProcessingScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val vm: PaymentProcessingViewModel = org.koin.compose.koinInject()
    val state = vm.uiState
    PaymentProcessingScreenContent(
        state = state,
        onEvent = {
            vm.onEvent(it)
            if (it is PaymentProcessingUiEvent.OnProcessPayment) {
                navController.navigate(com.zamulabs.dineeasepos.navigation.Destinations.Receipt)
            }
        },
        onBack = { navController.popBackStack() },
        modifier = modifier
    )
}