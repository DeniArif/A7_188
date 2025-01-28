package com.example.tugasakhir.ui.viewmodel.terapis


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.model.Terapis
import com.example.tugasakhir.repository.TerapisRepository
import kotlinx.coroutines.launch

class InsertTrpViewModel(
    private val trs: TerapisRepository
) : ViewModel() {

    var TrpuiState by mutableStateOf(InsertTrpUiState())

    fun updateInsertTrpState(insertTrpnUiEvent: InsertTrpUiEvent) {
        TrpuiState = InsertTrpUiState(insertTrpUiEvent = insertTrpnUiEvent)
    }

    fun insertPsn() {  // Tidak perlu `suspend`, karena sudah menggunakan `viewModelScope.launch`
        viewModelScope.launch {
            try {
                trs.insertTerapis(TrpuiState.insertTrpUiEvent.toTerapis())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertTrpUiState(
    val insertTrpUiEvent: InsertTrpUiEvent = InsertTrpUiEvent()
)

data class InsertTrpUiEvent(
    val id_terapis : Int = 0,
    val nama_terapis: String = "",
    val spesialisasi: String = "",
    val nomor_izin_praktik: String = ""
)

fun InsertTrpUiEvent.toTerapis(): Terapis = Terapis(
    id_terapis = id_terapis,
    nama_terapis = nama_terapis,
    spesialisasi = spesialisasi,
    nomor_izin_praktik = nomor_izin_praktik
)


