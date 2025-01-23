package com.example.tugasakhir.ui.viewmodel.pasien

import PasienRepository
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.model.Pasien
import kotlinx.coroutines.launch
import toDetaiPasienlUiEvent


class UpdatePasienViewModel(
    savedStateHandle: SavedStateHandle,
    private val psn: PasienRepository
) : ViewModel() {

    val idPasien: String = checkNotNull(savedStateHandle[DestinasiUpdatePasien.ID_PASIEN])

    var uiState = mutableStateOf(InsertPasienUiState())
        private set

    init {
        ambilPasien()
    }

    private fun ambilPasien() {
        viewModelScope.launch {
            try {
                val pasien = psn.getPasienByid(idPasien)
                pasien?.let {
                    uiState.value = it.toInsertUiEventPasien()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updatePasien(idPasien: String, pasien: Pasien) {
        viewModelScope.launch {
            try {
                psn.updatePasien(idPasien, pasien)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updatePasienState(insertUiEvent: InsertUiEventPasien) {
        uiState.value = uiState.value.copy(insertUiEvent = insertUiEvent)
    }
}

fun Pasien.toInsertUiEventPasien(): InsertPasienUiState = InsertPasienUiState(
    insertUiEvent = this.toDetaiPasienlUiEvent()
)
