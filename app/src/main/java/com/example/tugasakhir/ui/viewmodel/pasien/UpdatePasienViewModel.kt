package com.example.tugasakhir.ui.viewmodel.pasien

import PasienRepository
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.model.Pasien
import com.example.tugasakhir.ui.viewpasien.DestinasiUpdatePasien
import kotlinx.coroutines.launch
import toDetailUiEvent

class UpdatePasienViewModel(
    savedStateHandle: SavedStateHandle,
    private val pasienRepository: PasienRepository
) : ViewModel() {

    val idPasien: String = checkNotNull(savedStateHandle[DestinasiUpdatePasien.ID_PASIEN])

    var uiState = mutableStateOf(InsertUiStatePasien())
        private set

    init {
        ambilPasien()
    }

    private fun ambilPasien() {
        viewModelScope.launch {
            try {
                val pasien = pasienRepository.getPasienByid(idPasien)
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
                pasienRepository.updatePasien(idPasien, pasien)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updatePasienState(insertUiEvent: InsertUiEventPasien) {
        uiState.value = uiState.value.copy(insertUiEvent = insertUiEvent)
    }
}

fun Pasien.toInsertUiEventPasien(): InsertUiStatePasien = InsertUiStatePasien(
    insertUiEvent = this.toDetailUiEvent()
)
