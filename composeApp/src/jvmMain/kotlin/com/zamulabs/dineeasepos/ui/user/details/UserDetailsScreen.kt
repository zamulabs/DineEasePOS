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
package com.zamulabs.dineeasepos.ui.user.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import com.zamulabs.dineeasepos.utils.ObserverAsEvent
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun UserDetailsScreen(
    navController: NavController,
    viewModel: UserDetailsViewModel = koinInject(),
) {
    val state by viewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(viewModel) {
        viewModel.loadUserDetails()
    }

    ObserverAsEvent(viewModel.uiEffect) { effect ->
        when (effect) {
            is UserDetailsUiEffect.ShowSnackBar -> scope.launch { state.snackbarHostState.showSnackbar(effect.message) }
            is UserDetailsUiEffect.ShowToast -> { }
            UserDetailsUiEffect.NavigateBack -> navController.popBackStack()
            UserDetailsUiEffect.NavigateToEdit -> { }
        }
    }

    UserDetailsScreenContent(
        state = state,
        onEvent = viewModel::onEvent,
        onBack = { navController.popBackStack() }
    )
}
