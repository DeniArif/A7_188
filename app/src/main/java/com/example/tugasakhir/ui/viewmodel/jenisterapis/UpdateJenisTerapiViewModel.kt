package com.example.tugasakhir.ui.viewmodel.jenisterapis



import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.model.JenisTerapi
import com.example.tugasakhir.repository.JenisTerapiRepository
import kotlinx.coroutines.launch

class UpdateJenisTerapiViewModel(
    savedStateHandle: SavedStateHandle,
    private val jns: JenisTerapiRepository
) : ViewModel() {
    val id_jenis_terapi = savedStateHandle.get<String>("id_jenis_terapi")?.toIntOrNull() ?: 0
    val nama_jenis_terapi = mutableStateOf("")
    val deskripsi_terapi = mutableStateOf("")

    val isLoading = mutableStateOf(false)
    val isSuccess = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)



    fun loadTrpData() {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null

            try {
                val terapi = jns.getJenisTerapiById(id_jenis_terapi)
                if (terapi != null) {
                    nama_jenis_terapi.value = terapi.nama_jenis_terapi
                    deskripsi_terapi.value = terapi.deskripsi_terapi
                } else {
                    errorMessage.value = "Data terapi tidak ditemukan"
                }
            } catch (e: Exception) {
                errorMessage.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    fun updateJns() {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null
            isSuccess.value = false

            try {
                val updateJns = JenisTerapi(
                    id_jenis_terapi = id_jenis_terapi,
                    nama_jenis_terapi = nama_jenis_terapi.value,
                    deskripsi_terapi = deskripsi_terapi.value
                )
                try {
                    jns.updateJenisTerapi(id_jenis_terapi, updateJns) // Ini akan menjalankan pembaruan
                    isSuccess.value = true // Jika tidak ada error, set sukses
                } catch (e: Exception) {
                    Log.e("UpdateJensiTerapiViewModel", "Error during updateJenisTerapi: ${e.message}", e) // Menambahkan log error
                    errorMessage.value = "Terjadi kesalahan: ${e.message}" // Menangani error jika ada masalah
                    isSuccess.value = false
                }

            } catch (e: Exception) {
                Log.e("UpdateJenisTerapiViewModel", "Error during update: ${e.message}", e) // Menambahkan log error
                errorMessage.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }


    var JnsuiState = mutableStateOf(InsertJnsUiState())
        private set


}