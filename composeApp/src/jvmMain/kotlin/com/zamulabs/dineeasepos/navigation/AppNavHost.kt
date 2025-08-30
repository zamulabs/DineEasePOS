/*
 * Copyright 2024 Joel Kanyi.
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
package com.zamulabs.dineeasepos.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zamulabs.dineeasepos.auth.login.LoginScreen
import com.zamulabs.dineeasepos.dashboard.DashboardScreen
import com.zamulabs.dineeasepos.menu.MenuManagementScreen
import com.zamulabs.dineeasepos.order.OrderManagementScreen
import com.zamulabs.dineeasepos.payment.PaymentProcessingScreen
import com.zamulabs.dineeasepos.table.TableManagementScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Destinations.Login,
    ) {
        composable<Destinations.Dashboard> {
            DashboardScreen(
                navController = navController
            )
        }

        composable<Destinations.Login> {
            LoginScreen(
                navController = navController
            )
        }

        composable<Destinations.Menu> {
            MenuManagementScreen(
                navController = navController
            )
        }

        composable<Destinations.Order> { backStackEntry ->
            OrderManagementScreen(
                navController = navController
            )
        }

        composable<Destinations.Table> { backStackEntry ->
            TableManagementScreen(
                navController = navController
            )
        }

        composable<Destinations.Payment> {
            PaymentProcessingScreen(navController = navController)
        }
    }
}
