package com.example.tugasakhir.ui.viewmodel.pasien

import com.example.tugasakhir.repository.PasienRepository
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.model.Pasien
import com.example.tugasakhir.ui.viewpasien.DestinasiUpdatePsn
import kotlinx.coroutines.launch




class UpdatePsnViewModel(
    savedStateHandle: SavedStateHandle,
    private val psn: PasienRepository
) : ViewModel() {

    val id_pasien: Int = checkNotNull(savedStateHandle[DestinasiUpdatePsn.ID_PASIEN])

    var PsnuiState = mutableStateOf(InsertPsnUiState())
        private set

    init {
        ambilPasien()
    }

    private fun ambilPasien() {
        viewModelScope.launch {
            try {
                val pasien = psn.getPasienById(id_pasien)
                pasien?.let {
                    // Pastikan memanggil toInsertUiEventPasien() di sini
                    PsnuiState.value = it.toInserPsntUiEvent()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun updatePsn(id_pasien: Int, pasien: Pasien) {
        viewModelScope.launch {
            try {
                psn.updatePasien(id_pasien, pasien)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updatePsnState(insertPsnUiEvent: InsertPsnUiEvent) {
        PsnuiState.value = PsnuiState.value.copy(insertPsnUiEvent = insertPsnUiEvent)
    }
}

fun Pasien.toInserPsntUiEvent(): InsertPsnUiState = InsertPsnUiState(
    insertPsnUiEvent = this.toDetailPsnUiEvent()
)
