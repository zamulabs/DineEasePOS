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
import com.zamulabs.dineeasepos.menu.addmenu.AddMenuItemScreen
import com.zamulabs.dineeasepos.order.OrderManagementScreen
import com.zamulabs.dineeasepos.payment.PaymentProcessingScreen
import com.zamulabs.dineeasepos.table.TableManagementScreen
import com.zamulabs.dineeasepos.table.addtable.AddTableScreen
import com.zamulabs.dineeasepos.order.neworder.NewOrderScreen
import com.zamulabs.dineeasepos.user.UserManagementScreen

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
            com.zamulabs.dineeasepos.payment.PaymentsScreen()
        }

        composable<Destinations.PaymentProcessing> {
            com.zamulabs.dineeasepos.payment.PaymentProcessingScreen(navController = navController)
        }

        composable<Destinations.OrderDetails> {
            com.zamulabs.dineeasepos.order.details.OrderDetailsScreen(navController = navController)
        }

        composable<Destinations.TableDetails> {
            com.zamulabs.dineeasepos.table.details.TableDetailsScreen(navController = navController)
        }

        composable<Destinations.Settings> {
            com.zamulabs.dineeasepos.settings.SettingsScreen(navController = navController)
        }

        composable<Destinations.Users> {
            UserManagementScreen(
                onAddUser = { navController.navigate(Destinations.AddUser) }
            )
        }

        composable<Destinations.AddUser> {
            com.zamulabs.dineeasepos.user.adduser.AddUserScreen(
                onBack = { navController.popBackStack() },
                onSaved = { navController.popBackStack() }
            )
        }

        composable<Destinations.Reports> {
            com.zamulabs.dineeasepos.reports.ReportsScreen()
        }
        
        composable<Destinations.Receipt> {
            com.zamulabs.dineeasepos.receipt.ReceiptScreen(navController = navController)
        }
    }
}
