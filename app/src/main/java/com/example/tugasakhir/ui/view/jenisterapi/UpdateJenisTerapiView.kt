//package com.example.tugasakhir.ui.view.jenisterapi
//
//
//import CostumeTopAppBar
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.input.nestedscroll.nestedScroll
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.tugasakhir.ui.navigation.DestinasiNavigasi
//import com.example.tugasakhir.ui.view.pasien.DestinasiUpdatePsn
//import com.example.tugasakhir.ui.viewmodel.PenyediaViewModel
//import com.example.tugasakhir.ui.viewmodel.pasien.UpdatePsnViewModel
//import com.example.tugasakhir.ui.viewmodel.pasien.toPasien
//import kotlinx.coroutines.launch
//
//object DestinasiUpdateTrp : DestinasiNavigasi {
//    override val route = "update"
//    const val ID_JENIS_TERAPI = "id_jenis_terapi"
//    val routesWithArg = "$route/{$ID_JENIS_TERAPI}"
//    override val titleRes = "Update Pasien"
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun PasienUpdateView(
//    id_pasien: String,
//    navigateBack: () -> Unit,
//    modifier: Modifier = Modifier,
//    viewModel: UpdatePsnViewModel = viewModel(factory = PenyediaViewModel.Factory)
//) {
//    val coroutineScope = rememberCoroutineScope()
//    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
//
//    // Collect the UI state from the ViewModel
//    val uiState = viewModel.PsnuiState.value
//
//    Scaffold(
//        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
//        topBar = {
//            CostumeTopAppBar(
//                title = DestinasiUpdatePsn.titleRes,
//                canNavigateBack = true,
//                scrollBehavior = scrollBehavior,
//                navigateUp = navigateBack
//            )
//        }
//    ) { padding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(padding)
//                .padding(16.dp)
//        ) {
//            // Pass the UI state to EntryBody to handle form input
//            com.example.tugasakhir.ui.view.pasien.EntryBody(
//                insertUiState = uiState, // Sebelumnya salah referensi
//                onPasienValueChange = { updatedValue ->
//                    viewModel.updatePsnState(updatedValue) // Update ViewModel state with new data
//                },
//                onSaveClick = {
//                    uiState.insertPsnUiEvent?.let { insertUiEventPasien -> // Sebelumnya salah penulisan
//                        coroutineScope.launch {
//                            // Call ViewModel update method with updated Pasien
//                            viewModel.updatePsn(
//                                id_pasien = viewModel.id_pasien, // Pass the patient ID from ViewModel
//                                pasien = insertUiEventPasien.toPasien() // Convert InsertUiEvent to Pasien
//                            )
//                            navigateBack() // Navigate back after saving changes
//                        }
//                    }
//                }
//            )
//        }
//    }
//}