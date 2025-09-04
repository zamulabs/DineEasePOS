/*
 * Copyright 2023 Zamulabs.
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
package com.zamulabs.dineeasepos.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.zamulabs.composeapp.generated.resources.Res
import com.zamulabs.composeapp.generated.resources.compose_multiplatform
import com.zamulabs.dineeasepos.ui.navigation.Destinations
import com.zamulabs.dineeasepos.ui.navigation.NavRail
import org.jetbrains.compose.resources.painterResource
import kotlinx.coroutines.launch

@Composable
fun AppNavigationRailBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route?.substringBefore("?")
        ?: Destinations.Dashboard::class.qualifiedName.orEmpty()

    // Read current role from settings; default to Admin if unset
    val settings: com.zamulabs.dineeasepos.data.SettingsRepository = org.koin.compose.koinInject()
    val roleFlow = settings.getUserString(com.zamulabs.dineeasepos.data.PreferenceManager.USER_TYPE)
    val roleState = roleFlow.collectAsState(initial = "Admin")
    val role = roleState.value ?: "Admin"
    val devOverride = settings.superAdminDevOverride().collectAsState(initial = false).value
    val scope = androidx.compose.runtime.rememberCoroutineScope()

    fun filteredNavItems(): List<NavRail> {
        if (devOverride) return NavRail.entries
        return when (role.trim().lowercase()) {
            "admin" -> NavRail.entries
            "cashier" -> NavRail.entries.filter { it != NavRail.Users }
            "waiter" -> NavRail.entries.filter { it != NavRail.Payments && it != NavRail.Reports && it != NavRail.Users }
            else -> NavRail.entries
        }
    }

    NavigationRail(
        modifier = modifier
            .fillMaxHeight() // rail should stretch vertically
            .padding(end = 8.dp), // optional spacing before content
        containerColor = MaterialTheme.colorScheme.surface,
        header = {
            Row(
                modifier = Modifier
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Res.drawable.compose_multiplatform),
                    contentDescription = "App logo",
                    modifier = Modifier.size(32.dp)
                )
                Text(
                    text = "DineEase POS",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        },
    ) {
        // Build a full-height Column so we can anchor actions at the bottom
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            // Scrollable list of navigation items
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopStart),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredNavItems()) { navigationItem ->
                    val isSelected by remember(currentRoute) {
                        derivedStateOf { currentRoute == navigationItem.route::class.qualifiedName }
                    }

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                if (!isSelected) {
                                    navController.navigate(navigationItem.route) {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                        color = if (isSelected) MaterialTheme.colorScheme.secondaryContainer
                        else Color.Transparent,
                        tonalElevation = if (isSelected) 2.dp else 0.dp,
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(
                                    if (isSelected) navigationItem.selectedIcon else navigationItem.unselectedIcon
                                ),
                                contentDescription = navigationItem.label,
                                tint = if (isSelected) MaterialTheme.colorScheme.onSecondaryContainer
                                else MaterialTheme.colorScheme.onSurface
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = navigationItem.label,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = if (isSelected) MaterialTheme.colorScheme.onSecondaryContainer
                                    else MaterialTheme.colorScheme.onSurface
                                )
//                                navigationItem.description?.let {
//                                    Text(
//                                        text = it,
//                                        style = MaterialTheme.typography.bodySmall,
//                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
//                                    )
//                                }
                            }
                        }
                    }
                }
            }

            // Logout action anchored at the bottom
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        scope.launch {
                            // Clear all stored user/session data
                            settings.clearAll()
                            // Navigate to Login and clear back stack
                            navController.navigate(Destinations.Login) {
                                popUpTo(navController.graph.startDestinationId) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                                restoreState = false
                            }
                        }
                    }
                    .padding(horizontal = 12.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Logout",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
