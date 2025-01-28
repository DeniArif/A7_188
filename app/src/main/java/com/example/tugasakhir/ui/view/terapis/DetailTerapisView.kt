package com.example.tugasakhir.ui.view.terapis

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
import com.example.tugasakhir.model.Terapis
import com.example.tugasakhir.ui.navigation.DestinasiNavigasi
import com.example.tugasakhir.ui.viewmodel.PenyediaViewModel
import com.example.tugasakhir.ui.view.pasien.OnError
import com.example.tugasakhir.ui.view.pasien.OnLoading
import com.example.tugasakhir.ui.viewmodel.terapis.DetailTerapisViewModel
import com.example.tugasakhir.ui.viewmodel.terapis.DetailTrpUiState

object DestinasiDetailTrp : DestinasiNavigasi {
    override val route = "detail"
    const val ID_TERAPIS = "id_terapis"
    val routesWithArg = "$route/{$ID_TERAPIS}"
    override val titleRes = "Detail Terapis"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TerapisDetailView(
    id_terapis: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailTerapisViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onEditClick: (Int) -> Unit = {},
    navigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailTrp.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = { viewModel.getDetailTerapis() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(id_terapis) },  // Menggunakan ID pasien yang diberikan
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Pasien"
                )
            }
        }
    ) { innerPadding ->
        val terapisDetailUiState by viewModel.terapisDetailUiState.collectAsState()

        BodyDetailTerapis(
            modifier = Modifier.padding(innerPadding),
            detailTrpUiState = terapisDetailUiState,
            retryAction = { viewModel.getDetailTerapis() }  // Pastikan ID pasien diteruskan saat mencoba ulang
        )
    }
}


@Composable
fun BodyDetailTerapis(
    modifier: Modifier = Modifier,
    detailTrpUiState: DetailTrpUiState,
    retryAction: () -> Unit = {}
) {
    when (detailTrpUiState) {
        is DetailTrpUiState.Loading -> {
            OnLoading(modifier = modifier.fillMaxSize())
        }
        is DetailTrpUiState.Success -> {
            Column(modifier = modifier.fillMaxWidth().padding(16.dp)) {
                ItemDetailTerapis(terapis = detailTrpUiState.terapis)
            }
        }
        is DetailTrpUiState.Error -> {
            OnError(retryAction = retryAction, modifier = modifier.fillMaxSize())
        }
        else -> {
            Text("Unexpected state encountered")
        }
    }
}


@Composable
fun ItemDetailTerapis(terapis: Terapis) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            ComponentDetailPasien(judul = "ID Terapis", isinya = terapis.id_terapis.toString()) // id_terapis diubah menjadi String
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPasien(judul = "Nama Terapis", isinya = terapis.nama_terapis)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPasien(judul = "Spesialisasi", isinya = terapis.spesialisasi)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailPasien(judul = "Nomor Izin Praktik", isinya = terapis.nomor_izin_praktik)
        }
    }
}


@Composable
fun ComponentDetailPasien(
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