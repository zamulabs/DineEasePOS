package com.zamulabs.dineeasepos.reports

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun ReportsScreen(
){
    val vm = ReportsViewModel()
    val state by vm.uiState.collectAsState()
    ReportsScreenContent(state = state, onEvent = vm::onEvent)
}
