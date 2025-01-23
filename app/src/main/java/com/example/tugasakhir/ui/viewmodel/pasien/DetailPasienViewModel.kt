import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.model.Pasien
import com.example.tugasakhir.ui.viewmodel.pasien.InsertUiEventPasien
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class PasienDetailUiState {
    data class Success(val pasien: Pasien) : PasienDetailUiState()
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

                val pasien = psn.getPasienByid(_idPasien)

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


fun Pasien.toDetaiPasienlUiEvent(): InsertUiEventPasien {
    return InsertUiEventPasien(
        idPasien = idPasien,
        nama = nama,
        alamat = alamat,
        nomorTelepon = nomorTelepon,
        tanggalLahir = tanggalLahir,
        riwayatMedikal = riwayatMedikal
    )
}
