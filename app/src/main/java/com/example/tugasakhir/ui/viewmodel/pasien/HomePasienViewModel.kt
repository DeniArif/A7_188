package com.example.tugasakhir.ui.viewmodel.pasien



import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.tugasakhir.model.Pasien
import com.example.tugasakhir.repository.PasienRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomePasienUiState {
    data class Success(val pasien: List<Pasien>) : HomePasienUiState()
    object Error : HomePasienUiState()
    object Loading : HomePasienUiState()
}

class HomePasienViewModel(private val psn: PasienRepository) : ViewModel() {
    var pasienUiState: HomePasienUiState by mutableStateOf(HomePasienUiState.Loading)
        private set

    init {
        getPasien()
    }

    fun getPasien() {
        viewModelScope.launch {
            pasienUiState = HomePasienUiState.Loading
            pasienUiState = try {
                HomePasienUiState.Success(psn.getAllPasien().data)
            } catch (e: IOException) {
                HomePasienUiState.Error
            } catch (e: HttpException) {
                HomePasienUiState.Error
            }
        }
    }

    fun deletePasien(idPasien: String) {
        viewModelScope.launch {
            try {
                psn.deletePasien(idPasien)
            } catch (e: IOException) {
                HomePasienUiState.Error
            } catch (e: HttpException) {
                HomePasienUiState.Error
            }
        }
    }
}
