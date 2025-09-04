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
package com.zamulabs.dineeasepos.ui.menu

import androidx.compose.foundation.border
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zamulabs.dineeasepos.ui.theme.PrimaryLightColor
import com.zamulabs.dineeasepos.ui.theme.SecondaryLightColor

@Composable
fun MenuDetailsSidePane(
    item: MenuItem?,
    onEdit: () -> Unit,
    onToggleActive: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        Text("Menu Item Details", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))

        @Composable
        fun RowItem(label: String, value: String) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .border(0.dp, Color.Transparent)
            ) {
                Text(label, color = MaterialTheme.colorScheme.outline)
                Text(value)
            }
        }

        if (item == null) {
            Text("Select an item to see details", color = MaterialTheme.colorScheme.outline)
        } else {
            // Picture area (same style as New Order screen placeholder)
            androidx.compose.foundation.layout.Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF223A2C))
            )
            Spacer(Modifier.height(12.dp))

            RowItem("Name", item.name)
            RowItem("Category", item.category)
            RowItem("Price", item.price)
            RowItem("Status", if (item.active) "Active" else "Inactive")

            Spacer(Modifier.height(8.dp))
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = onEdit,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF38E07B)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().height(44.dp)
                ) {
                    Text("Edit", color = Color(0xFF122118))
                }
                Button(
                    onClick = onToggleActive,
                    colors = ButtonDefaults.buttonColors(containerColor = SecondaryLightColor),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().height(44.dp)
                ) {
                    Text(if (item.active) "Mark Inactive" else "Mark Active")
                }
                Button(
                    onClick = onDelete,
                    colors = ButtonDefaults.buttonColors(containerColor = SecondaryLightColor),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().height(44.dp)
                ) {
                    Text("Delete")
                }
            }
        }
    }
}
