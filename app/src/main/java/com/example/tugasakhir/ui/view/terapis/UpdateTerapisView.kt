package com.example.tugasakhir.ui.view.terapis

import CostumeTopAppBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhir.ui.navigation.DestinasiNavigasi
import com.example.tugasakhir.ui.viewmodel.PenyediaViewModel
import com.example.tugasakhir.ui.viewmodel.terapis.UpdateTrpViewModel
import com.example.tugasakhir.ui.viewmodel.terapis.toTerapis
import kotlinx.coroutines.launch


object DestinasiUpdateTrp : DestinasiNavigasi {
    override val route = "update_jenis"
    const val ID_TERAPIS = "id_terapis"
    val routesWithArg = "$route/{$ID_TERAPIS}"
    override val titleRes = "Update Terapis"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TerapisUpdateView(
    id_terapis: Int,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateTrpViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Collect the UI state from the ViewModel
    val uiState = viewModel.TrpuiState.value

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateTrp.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Pass the UI state to EntryBody to handle form input
            EntryBody(
                insertTrpUiState = uiState, // Sebelumnya salah referensi
                onValueChange = { updatedValue ->
                    viewModel.updateTrpState(updatedValue) // Update ViewModel state with new data
                },
                onSaveClick = {
                    uiState.insertTrpUiEvent?.let { insertUiEventTrp -> // Sebelumnya salah penulisan
                        coroutineScope.launch {
                            // Call ViewModel update method with updated Pasien
                            viewModel.updateTrp(
                                id_terapis = viewModel.id_terapis, // Pass the patient ID from ViewModel
                                terapis = insertUiEventTrp.toTerapis() // Convert InsertUiEvent to Pasien
                            )
                            navigateBack() // Navigate back after saving changes
                        }
                    }
                }
            )
        }
    }
}