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
package com.zamulabs.dineeasepos.ui.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.seanproctor.datatable.DataColumn
import com.zamulabs.dineeasepos.ui.components.table.AppDataTable
import com.zamulabs.dineeasepos.ui.components.ui.AppScaffold
import com.zamulabs.dineeasepos.ui.components.ui.AppScreenTopBar
import com.zamulabs.dineeasepos.ui.theme.PrimaryLightColor

@Composable
fun UserManagementScreenContent(
    state: UserManagementUiState,
    onEvent: (UserManagementUiEvent) -> Unit,
    modifier: Modifier = Modifier,
){
    AppScaffold(
        snackbarHostState = state.snackbarHostState,
        modifier = modifier,
        topBar = { AppScreenTopBar(title = "Users") },
        contentHorizontalPadding = 24.dp,
        contentList = {
            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically){
                    Button(onClick = { onEvent(UserManagementUiEvent.OnClickAddUser) }, colors = ButtonDefaults.buttonColors(containerColor = PrimaryLightColor)){
                        Text("Add User")
                    }
                }
            }
            item { Spacer(Modifier.height(12.dp)) }
            item {
                AppDataTable(
                    columns = listOf(
                        DataColumn { Text("Name") },
                        DataColumn { Text("Role") },
                        DataColumn { Text("Status") },
                        DataColumn { Text("Actions") },
                    ),
                    paginated = false,
                ){
                    state.users.forEachIndexed { index, user ->
                        row {
                            cell { Text(user.name) }
                            cell { Text(user.role, color = Color(0xFFA6C7B5)) }
                            cell {
                                val bg = Color(0xFF264532)
                                Row(modifier = Modifier.clip(RoundedCornerShape(24.dp)).background(bg).padding(horizontal = 16.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically){
                                    Text(if(user.active) "Active" else "Inactive", color = Color.White)
                                }
                            }
                            cell {
                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)){
                                    TextButton(onClick = { onEvent(UserManagementUiEvent.OnEdit(index)) }){ Text("Edit", color = Color(0xFFA6C7B5), fontWeight = FontWeight.Bold) }
                                    Text("|", color = Color(0xFFA6C7B5))
                                    TextButton(onClick = { onEvent(UserManagementUiEvent.OnToggleActive(index)) }){ Text(if(user.active) "Deactivate" else "Activate", color = Color(0xFFA6C7B5), fontWeight = FontWeight.Bold) }
                                    Text("|", color = Color(0xFFA6C7B5))
                                    TextButton(onClick = { onEvent(UserManagementUiEvent.OnResetPassword(index)) }){ Text("Reset Password", color = Color(0xFFA6C7B5), fontWeight = FontWeight.Bold) }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}
