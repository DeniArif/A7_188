package com.example.tugasakhir.ui.viewmodel.pasien


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.model.Pasien
import com.example.tugasakhir.model.PasienDetailResponse
import com.example.tugasakhir.repository.PasienRepository
import com.example.tugasakhir.ui.viewpasien.DestinasiDetailPasien
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class PasienDetailUiState {
    data class Success(val pasien: PasienDetailResponse) : PasienDetailUiState()
    object Error : PasienDetailUiState()
    object Loading : PasienDetailUiState()
}

class DetailPasienViewModel(
    savedStateHandle: SavedStateHandle,
    private val psn: PasienRepository
) : ViewModel() {

    private val _idPasien: String = checkNotNull(savedStateHandle[DestinasiDetailPasien.ID_PASIEN])

    // StateFlow untuk menyimpan status UI
    private val _pasienDetailUiState = MutableStateFlow<PasienDetailUiState>(PasienDetailUiState.Loading)
    val pasienDetailUiState: StateFlow<PasienDetailUiState> = _pasienDetailUiState

    init {
        getDetailPasien()
    }

    fun getDetailPasien() {
        viewModelScope.launch {
            try {
                _pasienDetailUiState.value = PasienDetailUiState.Loading

                val pasien = psn.getPasienById(_idPasien)

               if (pasien != null){
                   //jika data ditemukan "sukses"
                   _pasienDetailUiState.value = PasienDetailUiState.Success(pasien)
                } else {
                    //jika data tidak ditemukan "error"
                    PasienDetailUiState.Error
                }
            } catch (e: Exception) {
                _pasienDetailUiState.value = PasienDetailUiState.Error
            }
        }
    }
}

//Memindahkan data dari entity ke ui
fun Pasien.toDetailPasienUiEvent() : InsertUiEventPasien {
    return InsertUiEventPasien(
        id_pasien = id_pasien,
        nama = nama,
        alamat = alamat,
        nomor_telepon = nomor_telepon,
        tanggal_lahir = tanggal_lahir,
        riwayat_medikal = riwayat_medikal
    )
}
