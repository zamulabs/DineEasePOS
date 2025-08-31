package com.zamulabs.dineeasepos.table.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import org.koin.compose.koinInject
import com.zamulabs.dineeasepos.navigation.Destinations

@Composable
fun TableDetailsScreen(
    navController: NavController,
){
    val vm: TableDetailsViewModel = koinInject<TableDetailsViewModel>()
    val state by vm.uiState.collectAsState()

    TableDetailsScreenContent(
        state = state,
        onEvent = { ev ->
            when(ev){
                TableDetailsUiEvent.OnClickCreateOrder -> navController.navigate(Destinations.NewOrder)
                TableDetailsUiEvent.OnClickEditTable -> navController.navigate(Destinations.AddTable)
            }
        },
        onBack = { navController.popBackStack() }
    )
}
