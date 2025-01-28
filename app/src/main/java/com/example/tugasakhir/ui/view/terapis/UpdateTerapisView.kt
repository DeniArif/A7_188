package com.example.tugasakhir.ui.view.terapis

import CostumeTopAppBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhir.ui.navigation.DestinasiNavigasi
import com.example.tugasakhir.ui.view.pasien.DestinasiUpdatePsn
import com.example.tugasakhir.ui.viewmodel.PenyediaViewModel
import com.example.tugasakhir.ui.viewmodel.terapis.UpdateTrpViewModel
import kotlinx.coroutines.launch


object DestinasiUpdateTrp : DestinasiNavigasi {
    override val route = "update"
    const val ID_TERAPIS = "id_terapis"
    val routesWithArg = "$route/{$ID_TERAPIS}"
    override val titleRes = "Update Terapis"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TerapisUpdateView(
    id_terapis: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateTrpViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val nama_terapis by viewModel.nama_terapis
    val spesialisasi by viewModel.spesialisasi
    val nomor_izin_praktik by viewModel.nomor_izin_praktik


    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()


    val uiState = viewModel.TrpuiState.value


    if (id_terapis.isNotEmpty()) {
        viewModel.loadTrpData()  // Memastikan data pasien dimuat
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdatePsn.titleRes,
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
            OutlinedTextField(
                value = nama_terapis,
                onValueChange = {viewModel.nama_terapis.value = it},
                label = { Text("Nama Terapis") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = spesialisasi,
                onValueChange = {viewModel.spesialisasi.value = it},
                label = { Text("Spesialisasi") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = nomor_izin_praktik,
                onValueChange = {viewModel.nomor_izin_praktik.value = it},
                label = { Text("Nomor Telepon") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))


            // Pass the UI state to EntryBody to handle form input
            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.updateTrp()
                        navigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simpan Perubahan")
            }

        }
    }
}