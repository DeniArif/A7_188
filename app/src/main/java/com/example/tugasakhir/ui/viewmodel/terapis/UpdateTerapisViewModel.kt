package com.example.tugasakhir.ui.viewmodel.terapis



import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.model.Terapis
import com.example.tugasakhir.repository.TerapisRepository
import kotlinx.coroutines.launch


class UpdateTrpViewModel(
    savedStateHandle: SavedStateHandle,
    private val Trp: TerapisRepository
) : ViewModel() {

    val id_terapis: Int = savedStateHandle.get<String>("id_terapis")?.toIntOrNull() ?: 0
    val nama_terapis = mutableStateOf("")
    val spesialisasi = mutableStateOf("")
    val nomor_izin_praktik = mutableStateOf("")
    val isLoading = mutableStateOf(false)
    val isSuccess = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)



    fun loadTrpData() {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null

            try {
                val terapis = Trp.getTerapisById(id_terapis)
                if (terapis != null) {
                    nama_terapis.value = terapis.nama_terapis
                    spesialisasi.value = terapis.spesialisasi
                    nomor_izin_praktik.value = terapis.nomor_izin_praktik
                } else {
                    errorMessage.value = "Data terapis tidak ditemukan"
                }
            } catch (e: Exception) {
                errorMessage.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun updateTrp() {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null
            isSuccess.value = false

            try {
                val updateTrp = Terapis(
                    id_terapis = id_terapis,
                    nama_terapis = nama_terapis.value,
                    spesialisasi = spesialisasi.value,
                    nomor_izin_praktik = nomor_izin_praktik.value,
                )
                try {
                    Trp.updateTerapis(id_terapis, updateTrp) // Ini akan menjalankan pembaruan
                    isSuccess.value = true // Jika tidak ada error, set sukses
                } catch (e: Exception) {
                    Log.e("UpdatePsnViewModel", "Error during updateTerapis: ${e.message}", e) // Menambahkan log error
                    errorMessage.value = "Terjadi kesalahan: ${e.message}" // Menangani error jika ada masalah
                    isSuccess.value = false
                }

            } catch (e: Exception) {
                Log.e("UpdateTrpViewModel", "Error during update: ${e.message}", e) // Menambahkan log error
                errorMessage.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }


    var TrpuiState = mutableStateOf(InsertTrpUiState())
        private set


}