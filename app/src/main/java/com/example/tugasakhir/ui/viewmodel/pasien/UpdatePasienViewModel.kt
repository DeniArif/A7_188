package com.example.tugasakhir.ui.viewmodel.pasien

import android.util.Log
import androidx.compose.runtime.getValue
import com.example.tugasakhir.repository.PasienRepository
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.model.Pasien
import com.example.tugasakhir.ui.view.pasien.DestinasiUpdatePsn
import kotlinx.coroutines.launch


class UpdatePsnViewModel(
    savedStateHandle: SavedStateHandle,
    private val psn: PasienRepository
) : ViewModel() {

    val id_pasien: Int = savedStateHandle.get<String>("id_pasien")?.toIntOrNull() ?: 0
    val nama = mutableStateOf("")
    val alamat = mutableStateOf("")
    val nomor_telepon = mutableStateOf("")
    val tanggal_lahir = mutableStateOf("")
    val riwayat_medikal = mutableStateOf("")
    val isLoading = mutableStateOf(false)
    val isSuccess = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)



    fun loadPasienData() {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null

            try {
                val pasien = psn.getPasienById(id_pasien)
                if (pasien != null) {
                    nama.value = pasien.nama
                    alamat.value = pasien.alamat
                    nomor_telepon.value = pasien.nomor_telepon
                    tanggal_lahir.value = pasien.tanggal_lahir
                    riwayat_medikal.value = pasien.riwayat_medikal
                } else {
                    errorMessage.value = "Data pasien tidak ditemukan"
                }
            } catch (e: Exception) {
                errorMessage.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun updatePsn() {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null
            isSuccess.value = false

            try {
                val updatePasien = Pasien(
                    id_pasien = id_pasien,
                    nama = nama.value,
                    alamat = alamat.value,
                    nomor_telepon = nomor_telepon.value,
                    tanggal_lahir = tanggal_lahir.value,
                    riwayat_medikal = riwayat_medikal.value
                )
                try {
                    psn.updatePasien(id_pasien, updatePasien) // Ini akan menjalankan pembaruan
                    isSuccess.value = true // Jika tidak ada error, set sukses
                } catch (e: Exception) {
                    Log.e("UpdatePsnViewModel", "Error during updatePasien: ${e.message}", e) // Menambahkan log error
                    errorMessage.value = "Terjadi kesalahan: ${e.message}" // Menangani error jika ada masalah
                    isSuccess.value = false
                }

            } catch (e: Exception) {
                Log.e("UpdatePsnViewModel", "Error during update: ${e.message}", e) // Menambahkan log error
                errorMessage.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }


    var PsnuiState = mutableStateOf(InsertPsnUiState())
        private set


}


