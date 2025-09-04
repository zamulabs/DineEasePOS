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
package com.zamulabs.dineeasepos.ui.navigation

import com.zamulabs.composeapp.generated.resources.Res
import com.zamulabs.composeapp.generated.resources.add_filled
import com.zamulabs.composeapp.generated.resources.calendar_filled
import com.zamulabs.composeapp.generated.resources.calendar_outlined
import com.zamulabs.composeapp.generated.resources.home_filled
import com.zamulabs.composeapp.generated.resources.home_outlined
import com.zamulabs.composeapp.generated.resources.settings_filled
import com.zamulabs.composeapp.generated.resources.settings_outlined
import com.zamulabs.composeapp.generated.resources.statistics_filled
import com.zamulabs.composeapp.generated.resources.statistics_outlined
import org.jetbrains.compose.resources.DrawableResource

enum class NavRail(
    val label: String,
    val description: String?,
    val selectedIcon: DrawableResource,
    val unselectedIcon: DrawableResource,
    val route: Any,
) {
    Dashboard(
        label = "Dashboard",
        description = "Overview of sales and activity",
        selectedIcon = Res.drawable.home_filled,
        unselectedIcon = Res.drawable.home_outlined,
        route = Destinations.Dashboard,
    ),
    Order(
        label = "Order",
        description = "Create and manage customer orders",
        selectedIcon = Res.drawable.calendar_filled,
        unselectedIcon = Res.drawable.calendar_outlined,
        route = Destinations.Order,
    ),
    Menu(
        label = "Menu",
        description = "Add or update food and drinks",
        selectedIcon = Res.drawable.add_filled,
        unselectedIcon = Res.drawable.add_filled,
        route = Destinations.Menu,
    ),
    Table(
        label = "Table",
        description = "View and manage table assignments",
        selectedIcon = Res.drawable.statistics_filled,
        unselectedIcon = Res.drawable.statistics_outlined,
        route = Destinations.Table,
    ),
    Payments(
        label = "Payments",
        description = "Track and process customer payments",
        selectedIcon = Res.drawable.statistics_filled,
        unselectedIcon = Res.drawable.statistics_outlined,
        route = Destinations.Payment,
    ),
    Reports(
        label = "Reports",
        description = "Business insights and performance",
        selectedIcon = Res.drawable.statistics_filled,
        unselectedIcon = Res.drawable.statistics_outlined,
        route = Destinations.Reports,
    ),
    Receipt(
        label = "Receipt",
        description = "Generate and view receipts",
        selectedIcon = Res.drawable.statistics_filled,
        unselectedIcon = Res.drawable.statistics_outlined,
        route = Destinations.Receipt,
    ),
    Users(
        label = "Users",
        description = "Manage staff and user accounts",
        selectedIcon = Res.drawable.statistics_filled,
        unselectedIcon = Res.drawable.statistics_outlined,
        route = Destinations.Users,
    ),
    Settings(
        label = "Settings",
        description = "Configure app preferences",
        selectedIcon = Res.drawable.settings_filled,
        unselectedIcon = Res.drawable.settings_outlined,
        route = Destinations.Settings,
    ),
}
