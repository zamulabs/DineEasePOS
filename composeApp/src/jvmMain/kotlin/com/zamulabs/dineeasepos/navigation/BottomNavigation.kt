package com.zamulabs.dineeasepos.navigation

import dineeasepos.composeapp.generated.resources.Res
import dineeasepos.composeapp.generated.resources.add_filled
import dineeasepos.composeapp.generated.resources.calendar_filled
import dineeasepos.composeapp.generated.resources.calendar_outlined
import dineeasepos.composeapp.generated.resources.home_filled
import dineeasepos.composeapp.generated.resources.home_outlined
import dineeasepos.composeapp.generated.resources.statistics_filled
import dineeasepos.composeapp.generated.resources.statistics_outlined
import org.jetbrains.compose.resources.DrawableResource

enum class NavRail(
    val label: String,
    val selectedIcon: DrawableResource,
    val unselectedIcon: DrawableResource,
    val route: Any,
) {
    Dashboard(
        label = "Dashboard",
        selectedIcon = Res.drawable.home_filled,
        unselectedIcon = Res.drawable.home_outlined,
        route = Destinations.Dashboard,
    ),
    Order(
        label = "Order",
        selectedIcon = Res.drawable.calendar_filled,
        unselectedIcon = Res.drawable.calendar_outlined,
        route = Destinations.Order,
    ),
    Menu(
        label = "Menu",
        selectedIcon = Res.drawable.add_filled,
        unselectedIcon = Res.drawable.add_filled,
        route = Destinations.Menu,
    ),
    Table(
        label = "Table",
        selectedIcon = Res.drawable.statistics_filled,
        unselectedIcon = Res.drawable.statistics_outlined,
        route = Destinations.Table,
    ),
}
