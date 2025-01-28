package com.example.tugasakhir.ui.viewmodel.jenisterapis

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.model.JenisTerapi
import com.example.tugasakhir.repository.JenisTerapiRepository
import kotlinx.coroutines.launch

class InsertJenisTerapiViewModel(
    private val psn: JenisTerapiRepository
) : ViewModel() {

    var JnsuiState by mutableStateOf(InsertJnsUiState())

    fun updateInsertJnsState(insertJnsUiEvent: InsertJnsUiEvent) {
        JnsuiState = InsertJnsUiState(insertJnsUiEvent = insertJnsUiEvent)
    }

    fun insertJns() {
        viewModelScope.launch {
            try {
                psn.insertJenisTerapi(JnsuiState.insertJnsUiEvent.toJenis())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertJnsUiState(
    val insertJnsUiEvent: InsertJnsUiEvent = InsertJnsUiEvent()
)

data class InsertJnsUiEvent(
    val id_jenis_terapi: Int = 0,
    val nama_jenis_terapi: String = "",
    val deskripsi_terapi: String = "",
)

fun InsertJnsUiEvent.toJenis(): JenisTerapi = JenisTerapi(
    id_jenis_terapi =  id_jenis_terapi,
    nama_jenis_terapi = nama_jenis_terapi,
    deskripsi_terapi = deskripsi_terapi,
)
