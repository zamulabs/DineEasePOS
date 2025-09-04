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
import androidx.compose.runtime.collectAsState
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
    // Read role from settings
    val settings: com.zamulabs.dineeasepos.data.SettingsRepository = org.koin.compose.koinInject()
    val role = settings.getUserString(com.zamulabs.dineeasepos.data.PreferenceManager.USER_TYPE).collectAsState(initial = "Admin").value?.trim()?.lowercase()

    val devOverride = settings.superAdminDevOverride().collectAsState(initial = false).value

    fun allowed(vararg roles: String): Boolean {
        if (devOverride) return true
        val r = role ?: "admin"
        return roles.any { it.trim().lowercase() == r }
    }

    @Composable
    fun AccessDenied() {
        androidx.compose.material3.Text("Unauthorized")
    }

    val token = settings.getBearerToken().collectAsState(initial = null).value
    val resetRequired = settings.passwordResetRequired().collectAsState(initial = false).value
    val startDest =Destinations.Dashboard
//        when {
//        token.isNullOrBlank() -> Destinations.Login
//        resetRequired -> Destinations.Login
//        else -> Destinations.Dashboard
//    }


    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDest,
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
            if (allowed("admin")) {
                AddTableScreen(navController = navController)
            } else {
                AccessDenied()
            }
        }

        composable<Destinations.AddMenuItem> {
            AddMenuItemScreen(navController = navController)
        }

        composable<Destinations.NewOrder> {
            NewOrderScreen(navController = navController)
        }

        composable<Destinations.Payment> {
            if (allowed("admin", "cashier")) {
                PaymentsScreen()
            } else {
                AccessDenied()
            }
        }

        composable<Destinations.PaymentProcessing> {
            if (allowed("admin", "cashier")) {
                PaymentProcessingScreen(navController = navController)
            } else {
                AccessDenied()
            }
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
            if (allowed("admin")) {
                UserManagementScreen()
            } else {
                AccessDenied()
            }
        }

        composable<Destinations.AddUser> {
            if (allowed("admin")) {
                AddUserScreen(navController = navController)
            } else {
                AccessDenied()
            }
        }

        composable<Destinations.Reports> {
            if (allowed("admin", "cashier")) {
                ReportsScreen()
            } else {
                AccessDenied()
            }
        }

        composable<Destinations.Receipt> {
            if (allowed("admin", "cashier")) {
                ReceiptScreen(navController = navController)
            } else {
                AccessDenied()
            }
        }
    }
}
