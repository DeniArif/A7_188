package com.example.tugasakhir.ui.viewmodel.pasien


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.model.Pasien
import com.example.tugasakhir.repository.PasienRepository
import kotlinx.coroutines.launch

class InsertPasienViewModel(private val psn: PasienRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertPasienUiState())

    fun updateInsertPasienState(insertUiEvent: InsertUiEventPasien) {
        uiState = InsertPasienUiState(insertUiEventPasien = insertUiEvent)
    }

    suspend fun insertPasien() {
        viewModelScope.launch {
            try {
                psn.insertPasien(uiState.insertUiEventPasien.toPasien())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertPasienUiState(
    val insertUiEventPasien: InsertUiEventPasien = InsertUiEventPasien()
)

data class InsertUiEventPasien(
    val id_pasien: String = "",
    val nama: String = "",
    val alamat: String = "",
    val nomor_telepon: String = "",
    val tanggal_lahir: String = "",
    val riwayat_medikal: String = ""
)

fun InsertUiEventPasien.toPasien(): Pasien = Pasien(
    id_pasien = id_pasien,
    nama = nama,
    alamat = alamat,
    nomor_telepon = nomor_telepon,
    tanggal_lahir = tanggal_lahir,
    riwayat_medikal = riwayat_medikal
)


