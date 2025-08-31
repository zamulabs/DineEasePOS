package com.zamulabs.dineeasepos.navigation

import dineeasepos.composeapp.generated.resources.Res
import dineeasepos.composeapp.generated.resources.add_filled
import dineeasepos.composeapp.generated.resources.calendar_filled
import dineeasepos.composeapp.generated.resources.calendar_outlined
import dineeasepos.composeapp.generated.resources.home_filled
import dineeasepos.composeapp.generated.resources.home_outlined
import dineeasepos.composeapp.generated.resources.settings_filled
import dineeasepos.composeapp.generated.resources.settings_outlined
import dineeasepos.composeapp.generated.resources.statistics_filled
import dineeasepos.composeapp.generated.resources.statistics_outlined
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
