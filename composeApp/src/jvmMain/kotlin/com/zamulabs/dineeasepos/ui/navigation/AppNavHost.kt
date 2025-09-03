/*
 * Copyright 2024 Zamulabs.
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
package com.zamulabs.dineeasepos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.toRoute
import androidx.navigation.compose.composable
import com.zamulabs.dineeasepos.ui.dashboard.DashboardScreen
import com.zamulabs.dineeasepos.ui.login.LoginScreen
import com.zamulabs.dineeasepos.ui.menu.MenuManagementScreen
import com.zamulabs.dineeasepos.ui.menu.addmenu.AddMenuItemScreen
import com.zamulabs.dineeasepos.ui.order.OrderManagementScreen
import com.zamulabs.dineeasepos.ui.order.details.OrderDetailsScreen
import com.zamulabs.dineeasepos.ui.order.neworder.NewOrderScreen
import com.zamulabs.dineeasepos.ui.payment.paymentprocessing.PaymentProcessingScreen
import com.zamulabs.dineeasepos.ui.payment.PaymentsScreen
import com.zamulabs.dineeasepos.ui.receipt.ReceiptScreen
import com.zamulabs.dineeasepos.ui.reports.ReportsScreen
import com.zamulabs.dineeasepos.ui.settings.SettingsScreen
import com.zamulabs.dineeasepos.ui.table.TableManagementScreen
import com.zamulabs.dineeasepos.ui.table.addtable.AddTableScreen
import com.zamulabs.dineeasepos.ui.table.details.TableDetailsScreen
import com.zamulabs.dineeasepos.ui.user.UserManagementScreen
import com.zamulabs.dineeasepos.ui.user.adduser.AddUserScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Destinations.Dashboard,
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

        composable<Destinations.AddTable> {
            AddTableScreen(navController = navController)
        }

        composable<Destinations.AddMenuItem> {
            AddMenuItemScreen(navController = navController)
        }

        composable<Destinations.NewOrder> {
            NewOrderScreen(navController = navController)
        }

        composable<Destinations.Payment> {
            PaymentsScreen()
        }

        composable<Destinations.PaymentProcessing> {
            PaymentProcessingScreen(navController = navController)
        }

        composable<Destinations.OrderDetails> {
            OrderDetailsScreen(navController = navController)
        }

        composable<Destinations.TableDetails> {
            TableDetailsScreen(navController = navController)
        }

        composable<Destinations.Settings> {
            SettingsScreen(navController = navController)
        }

        composable<Destinations.Users> {
            UserManagementScreen(
                onAddUser = { navController.navigate(Destinations.AddUser) }
            )
        }

        composable<Destinations.AddUser> {
            AddUserScreen(navController = navController)
        }

        composable<Destinations.Reports> {
            ReportsScreen()
        }

        composable<Destinations.Receipt> {
            ReceiptScreen(navController = navController)
        }
    }
}
