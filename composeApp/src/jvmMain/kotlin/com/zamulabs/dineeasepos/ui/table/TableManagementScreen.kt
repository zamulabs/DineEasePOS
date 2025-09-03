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
package com.zamulabs.dineeasepos.ui.table

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.zamulabs.dineeasepos.ui.navigation.Destinations
import org.koin.compose.koinInject

@Composable
fun TableManagementScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    vm: TableManagementViewModel = koinInject<TableManagementViewModel>(),
) {
    Surface(color = MaterialTheme.colorScheme.background, modifier = modifier.fillMaxSize()) {
        val state = vm.uiState
        TableManagementScreenContent(state = state, onEvent = { ev ->
            when(ev){
                is TableManagementUiEvent.OnClickAddTable -> navController.navigate(Destinations.AddTable)
                is TableManagementUiEvent.OnClickViewDetails -> navController.navigate(Destinations.TableDetails)
                is TableManagementUiEvent.OnSearch -> { /* optional search handling in VM */ }
            }
            vm.onEvent(ev)
        })
    }
}
