package com.example.tugasakhir.ui.viewmodel.pasien

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.model.Pasien
import com.example.tugasakhir.repository.PasienRepository
import com.example.tugasakhir.ui.viewpasien.DestinasiDetailPasien
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// UI state sealed class
sealed class DetailPsnUiState {
    data class Success(val pasien: Pasien) : DetailPsnUiState()
    object Error : DetailPsnUiState()
    object Loading : DetailPsnUiState()
}

// ViewModel untuk detail pasien
class DetailPasienViewModel(
    savedStateHandle: SavedStateHandle,
    private val psn: PasienRepository
) : ViewModel() {

    // Mengambil id_pasien sebagai Int
    private val _id_pasien: Int = checkNotNull(savedStateHandle[DestinasiDetailPasien.ID_PASIEN])

    // StateFlow untuk menyimpan status UI
    private val _pasienDetailUiState = MutableStateFlow<DetailPsnUiState>(DetailPsnUiState.Loading)
    val pasienDetailUiState: StateFlow<DetailPsnUiState> = _pasienDetailUiState

    init {
        getDetailPasien()
    }

    // Fungsi untuk mengambil detail pasien
    fun getDetailPasien() {
        viewModelScope.launch {
            try {
                _pasienDetailUiState.value = DetailPsnUiState.Loading

                val pasien = psn.getPasienById(_id_pasien)

                if (pasien != null) {
                    // Jika data ditemukan
                    _pasienDetailUiState.value = DetailPsnUiState.Success(pasien)
                } else {
                    // Jika data tidak ditemukan
                    _pasienDetailUiState.value = DetailPsnUiState.Error
                }
            } catch (e: Exception) {
                // Menangani error
                _pasienDetailUiState.value = DetailPsnUiState.Error
            }
        }
    }
}

// Memindahkan data dari entity Pasien ke UI event
fun Pasien.toDetailPsnUiEvent(): InsertPsnUiEvent {
    return InsertPsnUiEvent(
        id_pasien = 0,
        nama = nama,
        alamat = alamat,
        nomor_telepon = nomor_telepon,
        tanggal_lahir = tanggal_lahir,
        riwayat_medikal = riwayat_medikal
    )
}
