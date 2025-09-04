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
package com.zamulabs.dineeasepos

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zamulabs.dineeasepos.ui.components.AppNavigationRailBar
import com.zamulabs.dineeasepos.ui.components.SplitScreenScaffold
import com.zamulabs.dineeasepos.ui.navigation.AppNavHost
import com.zamulabs.dineeasepos.ui.navigation.Destinations
import com.zamulabs.dineeasepos.ui.navigation.NavRail
import com.zamulabs.dineeasepos.ui.theme.DineEaseTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    modifier: Modifier = Modifier
) {
    DineEaseTheme {
        Surface(
            modifier = modifier.fillMaxSize()
        ) {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route?.substringBefore("?")
                ?: Destinations.Dashboard::class.qualifiedName.orEmpty()
            val showNavRail: Boolean =
                currentRoute in NavRail.entries.map { it.route::class.qualifiedName }

            SplitScreenScaffold(
                modifier = Modifier.fillMaxSize(),
                // Customize nav rail size here
                navRail = if (showNavRail) {
                    {
                        AppNavigationRailBar(
                            navController = navController,
//                            modifier = Modifier.fillMaxWidth() // this width is controlled by SplitScreenScaffold
                        )
                    }
                } else null,
                main = {
                    AppNavHost(
                        navController = navController,
                    )
                }
                // no side/extra panes here — child screens (like OrderManagement) can add them
            )
        }
    }
}
