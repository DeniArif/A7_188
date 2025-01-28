package com.example.tugasakhir.ui.view.pasien
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhir.R
import com.example.tugasakhir.model.Pasien
import com.example.tugasakhir.ui.viewmodel.PenyediaViewModel
import com.example.tugasakhir.ui.viewmodel.pasien.HomePsnUiState
import com.example.tugasakhir.ui.viewmodel.pasien.HomePasienViewModel

object DestinasiHomePsn : DestinasiNavigasi {
    override val route = "home_pasien"
    override val titleRes = "Home Daftar Pasien"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePsnScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},  // Change to accept Int instead of String
    viewModel: HomePasienViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomePsn.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = { viewModel.getPsn() }
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
        }
    ) { innerPadding ->
        HomePsnStatus(
            homePasienUiState = viewModel.psnUiState,
            retryAction = { viewModel.getPsn() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,  // Ensure onDetailClick is accepting Int
            onDeleteClick = {
                viewModel.deletePsn(it.id_pasien)
                viewModel.getPsn()
            }
        )
    }
}


@Composable
fun HomePsnStatus(
    homePasienUiState: HomePsnUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pasien) -> Unit = {},
    onDetailClick: (Int) -> Unit  // Expecting Int here
) {
    when (homePasienUiState) {
        is HomePsnUiState.Loading -> OnLoading(modifier.fillMaxWidth())
        is HomePsnUiState.Success ->
            if (homePasienUiState.pasien.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Tidak ada data kontak")
                }
            } else {
                PsnLayout(
                    pasien = homePasienUiState.pasien,
                    modifier = modifier.fillMaxWidth(),
                    onDeleteClick = { onDeleteClick(it) },
                    onDetailClick = { onDetailClick(it.id_pasien) }  // Passing id_pasien as Int
                )
            }
        is HomePsnUiState.Error -> OnError(retryAction, modifier.fillMaxSize())
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
        Image(
            painter = painterResource(id = R.drawable.error),
            contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun PsnLayout(
    pasien: List<Pasien>,
    modifier: Modifier = Modifier,
    onDetailClick: (Pasien) -> Unit,
    onDeleteClick: (Pasien) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pasien) { pasien ->
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
    onDeleteClick: (Pasien) -> Unit = {},
    onUpdatePasienClick: (Pasien) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
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
                    text = pasien.id_pasien.toString(),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                )
                Spacer(Modifier.weight(1f))


                // Tombol Delete
                IconButton(onClick = { onDeleteClick(pasien) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            Text(
                text = pasien.nama,
                style = MaterialTheme.typography.displayLarge
            )
        }
    }
}
