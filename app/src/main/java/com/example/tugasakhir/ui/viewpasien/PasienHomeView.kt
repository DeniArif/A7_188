package com.example.tugasakhir.ui.viewpasien

import CostumeTopAppBar
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.example.tugasakhir.ui.navigation.DestinasiNavigasi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhir.R
import com.example.tugasakhir.model.Pasien
import com.example.tugasakhir.ui.viewmodel.PenyediaViewModel
import com.example.tugasakhir.ui.viewmodel.pasien.HomePasienUiState
import com.example.tugasakhir.ui.viewmodel.pasien.HomePasienViewModel


object DestinasiHome: DestinasiNavigasi {
    override val route ="home"
    override val titleRes = "Daftar Pasien"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomePasienViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHome.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getPasien()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Pasien")
            }
        },
    ) { innerPadding ->
        HomeStatus(
            homePasienUiState = viewModel.pasienUiState,
            retryAction = { viewModel.getPasien() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deletePasien(it.id_pasien)
                viewModel.getPasien()
            }
        )
    }
}


@Composable
fun HomeStatus(
    homePasienUiState: HomePasienUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pasien) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (homePasienUiState){
        is HomePasienUiState.Loading-> OnLoading(modifier = modifier.fillMaxWidth())

        is HomePasienUiState.Success ->
            if (homePasienUiState.pasien.isEmpty()){
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Tidak ada data kontak")
                }
            } else {
                PasienLayout(
                    pasienList = homePasienUiState.pasien,modifier = modifier.fillMaxWidth(),
                    onDeleteClick = {
                        onDeleteClick(it)
                    },
                    onDetailClick = {
                        onDeleteClick(it)
                    }
                )
            }
        is HomePasienUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun PasienLayout(
    pasienList: List<Pasien>,
    modifier: Modifier = Modifier,
    onDetailClick: (Pasien) -> Unit,
    onDeleteClick: (Pasien) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pasienList) { pasien ->
            PasienCard(
                pasien = pasien,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(pasien) },
                onDeleteClick = { onDeleteClick(pasien) }
            )
        }
    }
}

@Composable
fun PasienCard(
    pasien: Pasien,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pasien) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = pasien.nama,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onDeleteClick(pasien) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                    )
                }
                Text(
                    text = pasien.id_pasien,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Text(
                text = "Alamat: ${pasien.alamat}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Telepon: ${pasien.nomor_telepon}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Tanggal Lahir: ${pasien.tanggal_lahir}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Riwayat Medikal: ${pasien.riwayat_medikal}",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}
