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

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zamulabs.dineeasepos.ui.components.ui.AppButton
import com.zamulabs.dineeasepos.ui.components.ui.AppScaffold
import com.zamulabs.dineeasepos.ui.components.ui.BackBreadcrumb

@Composable
fun UserDetailsScreenContent(
    state: UserDetailsUiState,
    onEvent: (UserDetailsUiEvent) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppScaffold(
        snackbarHostState = state.snackbarHostState,
        modifier = modifier,
        topBar = {
            BackBreadcrumb(
                parentLabel = "Users",
                currentLabel = state.name,
                onBack = onBack,
            )
        },
        contentList = {
            item { GridRow("Name", state.name) }
            item { GridRow("Email", state.email) }
            item { GridRow("Role", state.role) }
            item { GridRow("Status", if (state.active) "Active" else "Inactive") }
            item {
                Row(Modifier.fillMaxWidth()) {
                    AppButton(onClick = { onEvent(UserDetailsUiEvent.Edit) }) { Text("Edit") }
                    Spacer(Modifier.width(8.dp))
                    AppButton(onClick = { onEvent(UserDetailsUiEvent.ToggleActive) }) { Text(if (state.active) "Mark Inactive" else "Mark Active") }
                    Spacer(Modifier.width(8.dp))
                    AppButton(onClick = { onEvent(UserDetailsUiEvent.ResetPassword) }) { Text("Reset Password") }
                }
            }
        }
    )
}

@Composable
private fun GridRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        androidx.compose.foundation.layout.Box(Modifier.width(180.dp)) { Text(label, color = MaterialTheme.colorScheme.outline) }
        Text(value)
    }
}
