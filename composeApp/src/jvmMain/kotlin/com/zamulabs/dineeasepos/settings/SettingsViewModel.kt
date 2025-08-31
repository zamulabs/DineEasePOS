package com.zamulabs.dineeasepos.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SettingsViewModel: ViewModel() {
    var uiState by mutableStateOf(SettingsUiState())
        private set

    fun onEvent(event: SettingsUiEvent){
        when(event){
            is SettingsUiEvent.OnTabSelected -> uiState = uiState.copy(activeTab = event.tab)
            SettingsUiEvent.OnAddTaxRate -> {
                val list = uiState.taxRates + TaxRate("New tax", "0%", false)
                uiState = uiState.copy(taxRates = list)
            }
        }
    }
}
