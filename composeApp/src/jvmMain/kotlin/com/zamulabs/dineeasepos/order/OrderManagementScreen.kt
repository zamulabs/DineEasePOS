package com.zamulabs.dineeasepos.order

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.seanproctor.datatable.DataColumn
import com.zamulabs.dineeasepos.components.table.AppDataTable

@Composable
fun OrderManagementScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val vm = OrderManagementViewModel()
    OrderManagementScreenContent(
        modifier = modifier,
        onEvent = {
            vm.onEvent(it)
            when (it) {
                is OrderManagementUiEvent.OnClickNewOrder -> {
                    navController.navigate(com.zamulabs.dineeasepos.navigation.Destinations.NewOrder)
                }
                is OrderManagementUiEvent.OnClickViewDetails -> {
                    navController.navigate(com.zamulabs.dineeasepos.navigation.Destinations.OrderDetails)
                }
                else -> {}
            }
        },
        onOrderClick = { /* orderId -> */
            navController.navigate(com.zamulabs.dineeasepos.navigation.Destinations.OrderDetails)
        },
        state = vm.uiState
    )
}