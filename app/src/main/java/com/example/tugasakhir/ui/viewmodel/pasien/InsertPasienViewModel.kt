package com.example.tugasakhir.ui.viewmodel.pasien

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.model.Pasien
import com.example.tugasakhir.repository.PasienRepository
import kotlinx.coroutines.launch

class InsertPsnViewModel(
    private val psn: PasienRepository
) : ViewModel() {

    var PsnuiState by mutableStateOf(InsertPsnUiState())

    fun updateInsertPsnState(insertPsnUiEvent: InsertPsnUiEvent) {
        PsnuiState = InsertPsnUiState(insertPsnUiEvent = insertPsnUiEvent)
    }

    fun insertPsn() {  // Tidak perlu `suspend`, karena sudah menggunakan `viewModelScope.launch`
        viewModelScope.launch {
            try {
                psn.insertPasien(PsnuiState.insertPsnUiEvent.toPasien())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertPsnUiState(
    val insertPsnUiEvent: InsertPsnUiEvent = InsertPsnUiEvent()
)

data class InsertPsnUiEvent(
    val id_pasien: Int = 0,
    val nama: String = "",
    val alamat: String = "",
    val nomor_telepon: String = "",
    val tanggal_lahir: String = "",
    val riwayat_medikal: String = ""
)

fun InsertPsnUiEvent.toPasien(): Pasien = Pasien(
    id_pasien = id_pasien,
    nama = nama,
    alamat = alamat,
    nomor_telepon = nomor_telepon,
    tanggal_lahir = tanggal_lahir,
    riwayat_medikal = riwayat_medikal
)
