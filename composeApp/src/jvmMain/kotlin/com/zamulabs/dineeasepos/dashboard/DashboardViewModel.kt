package com.zamulabs.dineeasepos.dashboard

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DashboardViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    fun onEvent(event: DashboardUiEvent) {
        when(event){
            DashboardUiEvent.Refresh -> refresh()
        }
    }

    fun refresh() {
        _uiState.value = _uiState.value.copy()
    }
}
