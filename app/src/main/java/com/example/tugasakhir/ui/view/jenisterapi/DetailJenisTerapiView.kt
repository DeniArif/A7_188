package com.example.tugasakhir.ui.view.jenisterapi

import CostumeTopAppBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhir.model.JenisTerapi
import com.example.tugasakhir.ui.navigation.DestinasiNavigasi
import com.example.tugasakhir.ui.view.pasien.OnError
import com.example.tugasakhir.ui.view.pasien.OnLoading
import com.example.tugasakhir.ui.viewmodel.PenyediaViewModel
import com.example.tugasakhir.ui.viewmodel.jenisterapis.DetailJenisTerapiViewModel
import com.example.tugasakhir.ui.viewmodel.jenisterapis.DetailJnsUiState

object DestinasiDetailJenis : DestinasiNavigasi {
    override val route = "detail_jenis_terapi"
    const val ID_JENIS_TERAPI = "id_jenis_terapi"
    val routesWithArg = "$route/{$ID_JENIS_TERAPI}"
    override val titleRes = "Detail_Jenis_Terapi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailJenisDetailView(
    id_jenis_terapi: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailJenisTerapiViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: (Int) -> Unit = {},
    navigateBack: () -> Unit

) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailJenis.titleRes,
                canNavigateBack = false,
                navigateUp = {},
                onRefresh = { viewModel.getDetailTerapi() }  // Pastikan ID Pasien diberikan
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(id_jenis_terapi) },  // Menggunakan ID pasien yang diberikan
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Jenis Terapi"
                )
            }
        }
    ) { innerPadding ->
        val detailJenisUiState by viewModel.jenisDetailUiState.collectAsState()

        BodyDetailJenis(
            modifier = Modifier.padding(innerPadding),
            detailJnsUiState = detailJenisUiState,
            retryAction = { viewModel.getDetailTerapi() }  // Pastikan ID pasien diteruskan saat mencoba ulang
        )
    }
}


@Composable
fun BodyDetailJenis(
    modifier: Modifier = Modifier,
    detailJnsUiState: DetailJnsUiState,
    retryAction: () -> Unit = {}
) {
    when (detailJnsUiState) {
        is DetailJnsUiState.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is DetailJnsUiState.Success -> {
            Column(modifier = modifier.fillMaxWidth().padding(16.dp)) {
                ItemDetailJenis(jenisTerapi = detailJnsUiState.jenisterapi)
            }
        }
        is DetailJnsUiState.Error -> {
            OnError(retryAction = retryAction, modifier = modifier.fillMaxSize())
        }
        else -> {
            Text("Unexpected state encountered")
        }
    }
}


@Composable
fun ItemDetailJenis(jenisTerapi: JenisTerapi) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetailJenis(judul = "ID Pasien", isinya = jenisTerapi.id_jenis_terapi.toString())
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailJenis(judul = "Nama", isinya = jenisTerapi.nama_jenis_terapi)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailJenis(judul = "Alamat", isinya = jenisTerapi.deskripsi_terapi)
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}


@Composable
fun ComponentDetailJenis(
    judul: String,
    isinya: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul :",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}