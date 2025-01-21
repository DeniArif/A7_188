package com.example.tugasakhir.ui.viewmodel.pasien

import PasienRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.model.Pasien
import kotlinx.coroutines.launch

class InsertPasienViewModel(private val pasienRepository: PasienRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertPasienUiState())

    fun updateInsertPasienState(insertUiEvent: InsertUiEventPasien) {
        uiState = InsertPasienUiState(insertUiEvent = insertUiEvent)
    }

    fun insertPasien() {
        viewModelScope.launch {
            try {
                pasienRepository.insertPasien(uiState.insertUiEvent.toPasien())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertPasienUiState(
    val insertUiEvent: InsertUiEventPasien = InsertUiEventPasien()
)

data class InsertUiEventPasien(
    val idPasien: String = "",
    val nama: String = "",
    val alamat: String = "",
    val nomorTelepon: String = "",
    val tanggalLahir: String = "",
    val riwayatMedikal: String = ""
)

fun InsertUiEventPasien.toPasien(): Pasien = Pasien(
    idPasien = idPasien,
    nama = nama,
    alamat = alamat,
    nomorTelepon = nomorTelepon,
    tanggalLahir = tanggalLahir,
    riwayatMedikal = riwayatMedikal
)

fun Pasien.toInsertUiEvent(): InsertUiEventPasien = InsertUiEventPasien(
    idPasien = idPasien,
    nama = nama,
    alamat = alamat,
    nomorTelepon = nomorTelepon,
    tanggalLahir = tanggalLahir,
    riwayatMedikal = riwayatMedikal
)
