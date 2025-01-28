package com.example.tugasakhir.ui.view.jenisterapi


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
import com.example.tugasakhir.ui.viewmodel.jenisterapis.UpdateJenisTerapiViewModel
import com.example.tugasakhir.ui.viewmodel.pasien.UpdatePsnViewModel
import com.example.tugasakhir.ui.viewmodel.pasien.toPasien
import kotlinx.coroutines.launch

object DestinasiUpdateJns : DestinasiNavigasi {
    override val route = "update_jenis"
    const val ID_JENIS_TERAPI = "id_jenis_terapi"
    val routesWithArg = "$route/{$ID_JENIS_TERAPI}"
    override val titleRes = "Update Jenis Terapi"
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JenisUpdateView(
    id_jenis_terapi: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateJenisTerapiViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val nama_jenis_terapi by viewModel.nama_jenis_terapi
    val deskripsi_terapi by viewModel.deskripsi_terapi

    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Mengambil data pasien dari ViewModel
    val uiState = viewModel.JnsuiState.value

    // Memantine fungsi loadPasienData jika id_pasien tersedia
    if (id_jenis_terapi.isNotEmpty()) {
        viewModel.loadTrpData()  // Memastikan data pasien dimuat
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateJns.titleRes,
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
                value = nama_jenis_terapi,
                onValueChange = {viewModel.nama_jenis_terapi.value = it},
                label = { Text("Nama Paisen") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = deskripsi_terapi,
                onValueChange = {viewModel.deskripsi_terapi.value = it},
                label = { Text("Nama Alamat") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))




            // Pass the UI state to EntryBody to handle form input
            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.updateJns()
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
