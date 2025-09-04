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
package com.zamulabs.dineeasepos.ui.menu.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zamulabs.dineeasepos.ui.components.ui.AppButton
import com.zamulabs.dineeasepos.ui.components.ui.AppScaffold
import com.zamulabs.dineeasepos.ui.components.ui.BackBreadcrumb

@Composable
fun MenuDetailsScreenContent(
    state: MenuDetailsUiState,
    onEvent: (MenuDetailsUiEvent) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppScaffold(
        snackbarHostState = state.snackbarHostState,
        modifier = modifier,
        topBar = {
            BackBreadcrumb(
                parentLabel = "Menu",
                currentLabel = state.name,
                onBack = onBack,
            )
        },
        contentList = {
            item { Spacer(Modifier.height(8.dp)) }
            item {
                Row(
                    Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(Modifier.weight(1f)) {
                        Text(state.name, style = MaterialTheme.typography.headlineMedium)
                        Text("${'$'}{state.price}", color = MaterialTheme.colorScheme.outline)
                    }
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .background(Color(0xFF223A2C), RoundedCornerShape(12.dp))
                )
            }
            item { SectionTitle("Details") }
            item { GridRow("Category", state.category) }
            item { GridRow("Status", if (state.active) "Active" else "Inactive") }
            if (state.description.isNotBlank()) {
                item { GridRow("Description", state.description) }
            }
            item {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    AppButton(onClick = { onEvent(MenuDetailsUiEvent.Edit) }) { Text("Edit") }
                    Spacer(Modifier.width(8.dp))
                    AppButton(onClick = { onEvent(MenuDetailsUiEvent.ToggleActive) }) { Text(if (state.active) "Mark Inactive" else "Mark Active") }
                    Spacer(Modifier.width(8.dp))
                    AppButton(onClick = { onEvent(MenuDetailsUiEvent.Delete) }) { Text("Delete") }
                }
            }
        }
    )
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)
    )
}

@Composable
private fun GridRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        androidx.compose.foundation.layout.Box(Modifier.width(180.dp)) { Text(label, color = MaterialTheme.colorScheme.outline) }
        Text(value)
    }
}
