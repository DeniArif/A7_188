package com.example.tugasakhir.ui.viewpasien

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
import com.example.tugasakhir.ui.viewmodel.pasien.UpdatePasienViewModel
import kotlinx.coroutines.launch

object DestinasiUpdatePasien : DestinasiNavigasi {
    override val route = "update_pasien"
    const val ID_PASIEN = "id_pasien"
    val routesWithArg = "$route/{$ID_PASIEN}"
    override val titleRes = "Update Pasien"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePasienView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdatePasienViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Collect the UI state from the ViewModel
    val uiState = viewModel.uiState.value

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdatePasien.titleRes,
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
                insertUiState = uiState,
                onPasienValueChange = { updatedValue ->
                    viewModel.updatePasienState(updatedValue) // Update ViewModel state with new data
                },
                onSaveClick = {
                    uiState.insertUiEvent?.let { insertUiEvent ->
                        coroutineScope.launch {
                            // Call ViewModel update method with updated Pasien
                            viewModel.updatePasien(
                                idPasien = viewModel.idPasien, // Pass the patient ID from ViewModel
                                pasien = insertUiEvent.toPasien() // Convert InsertUiEvent to Pasien
                            )
                            navigateBack() // Navigate back after saving changes
                        }
                    }
                }
            )
        }
    }
}
