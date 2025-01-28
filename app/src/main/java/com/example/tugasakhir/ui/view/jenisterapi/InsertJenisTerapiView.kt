package com.example.tugasakhir.ui.view.jenisterapi


import CostumeTopAppBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhir.ui.navigation.DestinasiNavigasi
import com.example.tugasakhir.ui.viewmodel.PenyediaViewModel
import com.example.tugasakhir.ui.viewmodel.jenisterapis.InsertJenisTerapiViewModel
import com.example.tugasakhir.ui.viewmodel.jenisterapis.InsertJnsUiEvent
import com.example.tugasakhir.ui.viewmodel.jenisterapis.InsertJnsUiState
//import com.example.tugasakhir.ui.viewmodel.jenisterapis.InsertJenisTerapiViewModel
//import com.example.tugasakhir.ui.viewmodel.jenisterapis.InsertJnsUiEvent
//import com.example.tugasakhir.ui.viewmodel.jenisterapis.InsertJnsUiState
import com.example.tugasakhir.ui.viewmodel.pasien.InsertPsnUiEvent
import com.example.tugasakhir.ui.viewmodel.pasien.InsertPsnUiState
import com.example.tugasakhir.ui.viewmodel.pasien.InsertPsnViewModel
import kotlinx.coroutines.launch


object DestinasiInsertJenis: DestinasiNavigasi {
    override val route = "item_entry"
    override val titleRes = "Entry_Pasien"

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryJnsScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertJenisTerapiViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertJenis.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBody(
            insertJnsUiState = viewModel.JnsuiState,
            onJnsValueChange = viewModel::updateInsertJnsState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertJns()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBody(
    insertJnsUiState: InsertJnsUiState,
    onJnsValueChange: (InsertJnsUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputJenis(
            insertJnsUiEvent = insertJnsUiState.insertJnsUiEvent,
            onValueChange = onJnsValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputJenis(
    insertJnsUiEvent: InsertJnsUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertJnsUiEvent) -> Unit = {},
    enabled: Boolean = true
) {


    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertJnsUiEvent.nama_jenis_terapi,
            onValueChange = { onValueChange(insertJnsUiEvent.copy(nama_jenis_terapi = it)) },
            label = { Text("Nama Jenis Terapi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertJnsUiEvent.id_jenis_terapi.toString(), // Convert id_pasien to String for display
            onValueChange = {
                // Convert the input to Int and update id_pasien
                onValueChange(insertJnsUiEvent.copy(id_jenis_terapi = it.toIntOrNull() ?: 0))
            },
            label = { Text("Id Jenis Terapi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertJnsUiEvent.deskripsi_terapi,
            onValueChange = { onValueChange(insertJnsUiEvent.copy(deskripsi_terapi = it)) },
            label = { Text("Deskripsi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )


        if (enabled) {
            Text(
                text = "Isi Semua Data Pasien!",
                modifier = Modifier.padding(12.dp)
            )
        }

        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}

