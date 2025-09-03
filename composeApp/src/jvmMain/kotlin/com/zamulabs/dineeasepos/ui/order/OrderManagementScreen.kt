/*
 * Copyright 2025 Zamulabs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zamulabs.dineeasepos.ui.order

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.zamulabs.dineeasepos.ui.navigation.Destinations

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
                    navController.navigate(Destinations.NewOrder)
                }
                is OrderManagementUiEvent.OnClickViewDetails -> {
                    navController.navigate(Destinations.OrderDetails)
                }
                else -> {}
            }
        },
        onOrderClick = { /* orderId -> */
            navController.navigate(Destinations.OrderDetails)
        },
        state = vm.uiState
    )
}
