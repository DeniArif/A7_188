package com.example.tugasakhir.ui.viewmodel.pasien

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.model.Pasien
import com.example.tugasakhir.repository.PasienRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomePsnUiState {
    data class Success(val pasien: List<Pasien>) : HomePsnUiState()
    object Error : HomePsnUiState()
    object Loading : HomePsnUiState()
}

class HomePasienViewModel(private val pasienRepository: PasienRepository) : ViewModel() {
    var psnUiState: HomePsnUiState by mutableStateOf(HomePsnUiState.Loading)
        private set

    init {
        getPsn()
    }

    fun getPsn() {
        viewModelScope.launch {
            psnUiState = HomePsnUiState.Loading
            try {
                val response = pasienRepository.getAllPasien()
                psnUiState = HomePsnUiState.Success(response.data)
            } catch (e: IOException) {
                psnUiState = HomePsnUiState.Error
                e.printStackTrace() // Log exception to help debugging
            }
        }
    }

    fun deletePsn(id_pasien: Int) {
        viewModelScope.launch {
            try {
                pasienRepository.deletePasien(id_pasien)
            } catch (e: IOException) {
                psnUiState = HomePsnUiState.Error
                e.printStackTrace() // Log exception to help debugging
            }
        }
    }
}

