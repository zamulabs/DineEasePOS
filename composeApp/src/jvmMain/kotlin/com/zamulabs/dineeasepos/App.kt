package com.zamulabs.dineeasepos

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zamulabs.dineeasepos.components.BloomNavigationRailBar
import com.zamulabs.dineeasepos.navigation.AppNavHost
import com.zamulabs.dineeasepos.navigation.Destinations
import com.zamulabs.dineeasepos.navigation.NavRail
import com.zamulabs.dineeasepos.theme.DineEaseTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    DineEaseTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route?.substringBefore("?")
                ?: Destinations.Dashboard::class.qualifiedName.orEmpty()
            val showNavRail: Boolean =
                currentRoute in NavRail.entries.map { it.route::class.qualifiedName }

            Row {
                if (showNavRail) {
                    BloomNavigationRailBar(
                        navController = navController,
                    )
                }
                AppNavHost(
                    navController = navController,
                )
            }
        }
    }
}