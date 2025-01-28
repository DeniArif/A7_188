package com.example.tugasakhir.ui.viewmodel.terapis




import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.tugasakhir.model.Terapis
import com.example.tugasakhir.repository.TerapisRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeTrpUiState {
    data class Success(val terapis: List<Terapis>) : HomeTrpUiState()
    object Error : HomeTrpUiState()
    object Loading : HomeTrpUiState()
}

class HomeTerapisViewModel(private val trp: TerapisRepository) : ViewModel() {
    var terapisUiState: HomeTrpUiState by mutableStateOf(HomeTrpUiState.Loading)
        private set

    init {
        getTerapis()
    }

    fun getTerapis() {
        viewModelScope.launch {
            terapisUiState = HomeTrpUiState.Loading
            terapisUiState = try {
                HomeTrpUiState.Success(trp.getAllTerapis().data)
            } catch (e: IOException) {
                HomeTrpUiState.Error
            } catch (e: HttpException) {
                HomeTrpUiState.Error
            }
        }
    }

    fun deleteTerapis(terapis: Int) {
        viewModelScope.launch {
            try {
                trp.deleteTerapis(terapis)
            } catch (e: IOException) {
                terapisUiState = HomeTrpUiState.Error
            } catch (e: HttpException) {
                terapisUiState = HomeTrpUiState.Error
            }
        }
    }
}
