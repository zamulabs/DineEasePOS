package com.zamulabs.dineeasepos.table

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import org.koin.compose.koinInject

@Composable
fun TableManagementScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    vm: TableManagementViewModel = koinInject<TableManagementViewModel>(),
) {
    Surface(color = MaterialTheme.colorScheme.background, modifier = modifier.fillMaxSize()) {
        val state = vm.uiState
        TableManagementScreenContent(state = state, onEvent = { ev ->
            when(ev){
                is TableManagementUiEvent.OnClickAddTable -> navController.navigate(com.zamulabs.dineeasepos.navigation.Destinations.AddTable)
                is TableManagementUiEvent.OnClickViewDetails -> navController.navigate(com.zamulabs.dineeasepos.navigation.Destinations.TableDetails)
                is TableManagementUiEvent.OnSearch -> { /* optional search handling in VM */ }
            }
            vm.onEvent(ev)
        })
    }
}