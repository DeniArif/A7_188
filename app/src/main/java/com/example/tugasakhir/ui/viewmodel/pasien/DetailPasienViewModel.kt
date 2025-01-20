import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.model.Pasien
import com.example.tugasakhir.ui.viewpasien.DestinasiDetailPasien
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class PasienDetailUiState {
    data class Success(val pasien: Pasien) : PasienDetailUiState()
    object Error : PasienDetailUiState()
    object Loading : PasienDetailUiState()
}

class PasienDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val pasienRepository: PasienRepository
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

                val pasien = pasienRepository.getPasienByid(_idPasien)

                _pasienDetailUiState.value = if (pasien != null) {
                    PasienDetailUiState.Success(pasien)
                } else {
                    PasienDetailUiState.Error
                }
            } catch (e: Exception) {
                _pasienDetailUiState.value = PasienDetailUiState.Error
            }
        }
    }
}

// Memindahkan data dari entity ke UI
data class InsertUiEventPasien(
    val idPasien: String,
    val nama: String,
    val alamat: String,
    val nomorTelepon: String,
    val tanggalLahir: String,
    val riwayatMedikal: String
)

fun Pasien.toDetailUiEvent(): InsertUiEventPasien {
    return InsertUiEventPasien(
        idPasien = idPasien,
        nama = nama,
        alamat = alamat,
        nomorTelepon = nomorTelepon,
        tanggalLahir = tanggalLahir,
        riwayatMedikal = riwayatMedikal
    )
}
