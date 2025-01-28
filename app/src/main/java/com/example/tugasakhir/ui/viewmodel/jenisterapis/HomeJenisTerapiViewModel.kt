package com.example.tugasakhir.ui.viewmodel.jenisterapis


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.model.JenisTerapi
import com.example.tugasakhir.repository.JenisTerapiRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeJnsUiState {
    data class Success(val jenisTerapi: List<JenisTerapi>) : HomeJnsUiState()
    object Error : HomeJnsUiState()
    object Loading : HomeJnsUiState()
}

class HomeJenisTerapiViewModel(private val jenisterapiRepository: JenisTerapiRepository) : ViewModel() {
    var jnsUiState: HomeJnsUiState by mutableStateOf(HomeJnsUiState.Loading)
        private set

    init {
        getJns()
    }

    fun getJns() {
        viewModelScope.launch {
            jnsUiState = HomeJnsUiState.Loading
            try {
                val response = jenisterapiRepository.getAllJenisTerapi()
                jnsUiState = HomeJnsUiState.Success(response.data)
            } catch (e: IOException) {
                jnsUiState = HomeJnsUiState.Error
                e.printStackTrace() // Log exception to help debugging
            }
        }
    }

    fun deleteJns(id_jenis_terapi: Int) {
        viewModelScope.launch {
            try {
                jenisterapiRepository.deleteJenisTerapi(id_jenis_terapi)
            } catch (e: IOException) {
                jnsUiState = HomeJnsUiState.Error
                e.printStackTrace() // Log exception to help debugging
            }
        }
    }
}

