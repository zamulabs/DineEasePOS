package com.zamulabs.dineeasepos.order.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import org.koin.compose.koinInject

@Composable
fun OrderDetailsScreen(
    navController: NavController,
    viewModel: OrderDetailsViewModel = koinInject(),
){
    val state by viewModel.uiState.collectAsState()
    OrderDetailsScreenContent(state = state, onEvent = viewModel::onEvent, onBack = { navController.popBackStack() })
}
