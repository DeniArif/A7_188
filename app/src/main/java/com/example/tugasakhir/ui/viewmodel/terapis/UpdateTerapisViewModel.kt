package com.example.tugasakhir.ui.viewmodel.terapis



import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.model.Terapis
import com.example.tugasakhir.repository.TerapisRepository
import com.example.tugasakhir.ui.view.terapis.DestinasiUpdateTrp
import kotlinx.coroutines.launch


class UpdateTrpViewModel(
    savedStateHandle: SavedStateHandle,
    private val trs: TerapisRepository
) : ViewModel() {

    val id_terapis: Int = checkNotNull(savedStateHandle[DestinasiUpdateTrp.ID_TERAPIS])

    var TrpuiState = mutableStateOf(InsertTrpUiState())
        private set

    init {
        ambilTerapis()
    }

    private fun ambilTerapis() {
        viewModelScope.launch {
            try {
                val terapis = trs.getTerapisById(id_terapis)
                terapis?.let {

                    TrpuiState.value = it.toInsertTrpUiEvent()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun updateTrp(id_terapis: Int, terapis: Terapis) {
        viewModelScope.launch {
            try {
                trs.updateTerapis(id_terapis, terapis)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateTrpState(insertTrpUiEvent: InsertTrpUiEvent) {
        TrpuiState.value = TrpuiState.value.copy(insertTrpUiEvent = insertTrpUiEvent)
    }
}

fun Terapis.toInsertTrpUiEvent(): InsertTrpUiState = InsertTrpUiState(
    insertTrpUiEvent = this.toDetailTrpUiEvent()
)
