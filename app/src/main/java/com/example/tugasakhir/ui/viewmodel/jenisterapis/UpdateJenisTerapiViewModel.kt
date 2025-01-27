//package com.example.tugasakhir.ui.viewmodel.jenisterapis
//
//
//import androidx.compose.runtime.mutableStateOf
//import androidx.lifecycle.SavedStateHandle
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.tugasakhir.model.JenisTerapi
//import com.example.tugasakhir.repository.JenisTerapiRepository
//import kotlinx.coroutines.launch
//
//
//
//
//class UpdateJnsViewModel(
//    savedStateHandle: SavedStateHandle,
//    private val jns: JenisTerapiRepository
//) : ViewModel() {
//
//    val id_jenis_terapi: Int = checkNotNull(savedStateHandle[DestinasiUpdateJns.ID_JENIS_TERAPI])
//
//    var JnsuiState = mutableStateOf(InsertJnsUiState())
//        private set
//
//    init {
//        ambilJenis()
//    }
//
//    private fun ambilJenis() {
//        viewModelScope.launch {
//            try {
//                val JenisTerapi = jns.getJenisTerapiById(id_jenis_terapi)
//                JenisTerapi?.let {
//                    // Pastikan memanggil toInsertUiEventPasien() di sini
//                    JnsuiState.value = it.toInserJnstUiEvent()
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }
//
//
//    fun updateJns(id_jenis_terapi: Int, JenisTerapi: JenisTerapi) {
//        viewModelScope.launch {
//            try {
//                jns.updateJenisTerapi(id_jenis_terapi, JenisTerapi)
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }
//
//    fun updateJnsState(insertJnsUiEvent: InsertJnsUiEvent) {
//        JnsuiState.value = JnsuiState.value.copy(insertJnsUiEvent = insertJnsUiEvent)
//    }
//}
//
//fun JenisTerapi.toInserJnstUiEvent(): InsertJnsUiState = InsertJnsUiState(
//    insertJnsUiEvent = this.toDetailJnsUiEvent()
//)
