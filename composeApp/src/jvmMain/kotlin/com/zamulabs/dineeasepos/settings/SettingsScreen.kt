package com.zamulabs.dineeasepos.settings

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.koin.compose.koinInject

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = koinInject()
){
    val state = viewModel.uiState
    SettingsScreenContent(state = state, onEvent = viewModel::onEvent)
}
