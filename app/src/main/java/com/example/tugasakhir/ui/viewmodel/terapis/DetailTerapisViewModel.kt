package com.example.tugasakhir.ui.viewmodel.terapis


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.model.Terapis
import com.example.tugasakhir.repository.TerapisRepository
import com.example.tugasakhir.ui.view.terapis.DestinasiDetailTrp
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

 //UI state sealed class
sealed class DetailTrpUiState {
    data class Success(val terapis: Terapis) : DetailTrpUiState()
    object Error : DetailTrpUiState()
    object Loading : DetailTrpUiState()
}

 //ViewModel untuk detail terapis
class DetailTerapisViewModel(
    savedStateHandle: SavedStateHandle,
    private val trs: TerapisRepository
) : ViewModel() {

     //Mengambil id_terapis sebagai Int
    private val _id_terapis: Int = checkNotNull(savedStateHandle[DestinasiDetailTrp.ID_TERAPIS])

     //StateFlow untuk menyimpan status UI
    private val _terapisDetailUiState = MutableStateFlow<DetailTrpUiState>(DetailTrpUiState.Loading)
    val terapisDetailUiState: StateFlow<DetailTrpUiState> = _terapisDetailUiState

    init {
        getDetailTerapis()
    }

     //Fungsi untuk mengambil detail terapis
    fun getDetailTerapis() {
        viewModelScope.launch {
            try {
                _terapisDetailUiState.value = DetailTrpUiState.Loading

                val terapis = trs.getTerapisById(_id_terapis)

                if (terapis != null) {
                     //Jika data ditemukan
                    _terapisDetailUiState.value = DetailTrpUiState.Success(terapis)
                } else {
                     //Jika data tidak ditemukan
                    _terapisDetailUiState.value = DetailTrpUiState.Error
                }
            } catch (e: Exception) {
                 //Menangani error
                _terapisDetailUiState.value = DetailTrpUiState.Error
            }
        }
    }
}

 //Memindahkan data dari entity Pasien ke UI event
fun Terapis.toDetailTrpUiEvent(): InsertTrpUiEvent {
    return InsertTrpUiEvent(
        id_terapis = 0,
        nama_terapis = nama_terapis,
        spesialisasi = spesialisasi,
        nomor_izin_praktik = nomor_izin_praktik
    )
}

