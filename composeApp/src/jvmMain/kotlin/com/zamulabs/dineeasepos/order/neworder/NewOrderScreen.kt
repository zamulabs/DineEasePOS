package com.zamulabs.dineeasepos.order.neworder

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import org.koin.compose.koinInject

@Composable
fun NewOrderScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: NewOrderViewModel = koinInject<NewOrderViewModel>(),
){
    val state = viewModel.uiState
    NewOrderScreenContent(
        state = state,
        onEvent = viewModel::onEvent,
        onPlaceOrder = {
            navController.navigate(com.zamulabs.dineeasepos.navigation.Destinations.PaymentProcessing)
        },
        onBack = { navController.popBackStack() },
        modifier = modifier
    )
}
