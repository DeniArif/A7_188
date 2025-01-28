package com.example.tugasakhir.ui.viewmodel.jenisterapis

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.model.JenisTerapi
import com.example.tugasakhir.repository.JenisTerapiRepository
import com.example.tugasakhir.ui.view.jenisterapi.DestinasiDetailJenis
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// UI state sealed class
sealed class DetailJnsUiState {
    data class Success(val jenisterapi: JenisTerapi) : DetailJnsUiState()
    object Error : DetailJnsUiState()
    object Loading : DetailJnsUiState()
}

// ViewModel untuk detail jenisterapi
class DetailJenisTerapiViewModel(
    savedStateHandle: SavedStateHandle,
    private val jns: JenisTerapiRepository
) : ViewModel() {

    // Mengambil id_jenis_terapis sebagai Int
    private val _id_jenis_terapi: Int = checkNotNull(savedStateHandle[DestinasiDetailJenis.ID_JENIS_TERAPI])

    // StateFlow untuk menyimpan status UI
    private val _jenisDetailUiState = MutableStateFlow<DetailJnsUiState>(DetailJnsUiState.Loading)
    val jenisDetailUiState: StateFlow<DetailJnsUiState> = _jenisDetailUiState

    init {
        getDetailTerapi()
    }

    // Fungsi untuk mengambil detail jenisterapi
    fun getDetailTerapi() {
        viewModelScope.launch {
            try {
                _jenisDetailUiState.value = DetailJnsUiState.Loading

                val jenis = jns.getJenisTerapiById(_id_jenis_terapi)

                if (jenis != null) {
                    // Jika data ditemukan
                    _jenisDetailUiState.value = DetailJnsUiState.Success(jenis)
                } else {
                    // Jika data tidak ditemukan
                    _jenisDetailUiState.value = DetailJnsUiState.Error
                }
            } catch (e: Exception) {
                // Menangani error
                _jenisDetailUiState.value = DetailJnsUiState.Error
            }
        }
    }
}

// Memindahkan data dari entity Pasien ke UI event
fun JenisTerapi.toDetailJnsUiEvent(): InsertJnsUiEvent {
    return InsertJnsUiEvent(
        id_jenis_terapi =  id_jenis_terapi,
        nama_jenis_terapi = nama_jenis_terapi,
        deskripsi_terapi = deskripsi_terapi,
    )
}
