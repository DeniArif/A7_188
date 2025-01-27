package com.example.tugasakhir.ui.view.pasien

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
import com.example.tugasakhir.ui.viewmodel.PenyediaViewModel
import com.example.tugasakhir.ui.viewmodel.pasien.UpdatePsnViewModel
import com.example.tugasakhir.ui.viewmodel.pasien.toPasien
import kotlinx.coroutines.launch

object DestinasiUpdatePsn : DestinasiNavigasi {
    override val route = "update"
    const val ID_PASIEN = "id_pasien"
    val routesWithArg = "$route/{$ID_PASIEN}"
    override val titleRes = "Update Pasien"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasienUpdateView(
    id_pasien: String,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdatePsnViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val nama by viewModel.nama
    val alamat by viewModel.alamat
    val nomor_telepon by viewModel.nomor_telepon
    val tanggal_lahir by viewModel.tanggal_lahir
    val riwayat_medikal by viewModel.riwayat_medikal

    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Mengambil data pasien dari ViewModel
    val uiState = viewModel.PsnuiState.value

    // Memanggil fungsi loadPasienData jika id_pasien tersedia
    if (id_pasien.isNotEmpty()) {
        viewModel.loadPasienData()  // Memastikan data pasien dimuat
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
                value = nama,
                onValueChange = {viewModel.nama.value = it},
                label = { Text("Nama Paisen")},
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = alamat,
                onValueChange = {viewModel.alamat.value = it},
                label = { Text("Nama Alamat")},
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = nomor_telepon,
                onValueChange = {viewModel.nomor_telepon.value = it},
                label = { Text("Nomor Telepon")},
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = tanggal_lahir,
                onValueChange = {viewModel.tanggal_lahir.value = it},
                label = { Text("Tanggal Lahir")},
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = riwayat_medikal,
                onValueChange = {viewModel.riwayat_medikal.value = it},
                label = { Text("Riwayat Medikal")},
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))


            // Pass the UI state to EntryBody to handle form input
            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.updatePsn()
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
