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
package com.zamulabs.dineeasepos.ui.receipt

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun ReceiptScreen(
    navController: NavController,
    orderId: String,
    modifier: Modifier = Modifier,
) {
    val vm: ReceiptViewModel = org.koin.compose.koinInject()
    val state = vm.uiState.collectAsState().value
    androidx.compose.runtime.LaunchedEffect(orderId) {
        vm.loadReceipt(orderId)
    }
    ReceiptScreenContent(
        state = state,
        onEvent = vm::onEvent,
        onBack = { navController.popBackStack() },
    )
}
