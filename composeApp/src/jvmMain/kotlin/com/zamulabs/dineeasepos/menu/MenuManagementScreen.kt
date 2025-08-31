package com.zamulabs.dineeasepos.menu

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.koin.compose.koinInject

@Composable
fun MenuManagementScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: MenuManagementViewModel = koinInject<MenuManagementViewModel>()
) {
    val state = viewModel.uiState
    MenuManagementScreenContent(
        state = state,
        onEvent = { ev ->
            if (ev is MenuManagementUiEvent.OnClickAddItem) {
                navController.navigate(com.zamulabs.dineeasepos.navigation.Destinations.AddMenuItem)
            }
            viewModel.onEvent(ev)
        },
        modifier = modifier
    )
}