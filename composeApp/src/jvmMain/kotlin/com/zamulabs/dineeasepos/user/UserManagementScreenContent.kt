package com.zamulabs.dineeasepos.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.seanproctor.datatable.DataColumn
import com.zamulabs.dineeasepos.components.table.AppDataTable
import com.zamulabs.dineeasepos.theme.PrimaryLightColor
import com.zamulabs.dineeasepos.theme.SecondaryLightColor

@Composable
fun UserManagementScreenContent(
    state: UserManagementUiState,
    onEvent: (UserManagementUiEvent) -> Unit,
    modifier: Modifier = Modifier,
){
    com.zamulabs.dineeasepos.components.ui.AppScaffold(
        modifier = modifier,
        topBar = { com.zamulabs.dineeasepos.components.ui.AppScreenTopBar(title = "Users") },
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
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}
