package com.zamulabs.dineeasepos.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.seanproctor.datatable.DataColumn
import com.zamulabs.dineeasepos.components.table.AppDataTable
import com.zamulabs.dineeasepos.theme.PrimaryLightColor
import com.zamulabs.dineeasepos.theme.SecondaryColor
import com.zamulabs.dineeasepos.theme.SurfaceDark
import org.koin.compose.koinInject

@Composable
fun DashboardScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val viewModel: DashboardViewModel = koinInject()
    val uiState by viewModel.uiState.collectAsState()

    DashboardScreenContent(
        state = uiState,
        onOrderClick = { /* orderId -> */
            navController.navigate(com.zamulabs.dineeasepos.navigation.Destinations.OrderDetails)
        },
        modifier = modifier,
    )
}
