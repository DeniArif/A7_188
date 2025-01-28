package com.example.tugasakhir.ui.view.jenisterapi

import CostumeTopAppBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhir.R
import com.example.tugasakhir.model.JenisTerapi
import com.example.tugasakhir.ui.navigation.DestinasiNavigasi
import com.example.tugasakhir.ui.viewmodel.PenyediaViewModel
import com.example.tugasakhir.ui.viewmodel.jenisterapis.HomeJenisTerapiViewModel
import com.example.tugasakhir.ui.viewmodel.jenisterapis.HomeJnsUiState

object DestinasiHomeJns : DestinasiNavigasi {
    override val route = "home_pasien"
    override val titleRes = "Home Jenis Terapi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeJnsScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},  // Change to accept Int instead of String
    viewModel: HomeJenisTerapiViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeJns.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = { viewModel.getJns() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Jenis Terapi")
            }
        }
    ) { innerPadding ->
        HomeJnsStatus (
            homeJnsUiState = viewModel.jnsUiState,
            retryAction = { viewModel.getJns() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,  // Ensure onDetailClick is accepting Int
            onDeleteClick = {
                viewModel.deleteJns(it.id_jenis_terapi)
                viewModel.getJns()
            }
        )
    }
}


@Composable
fun HomeJnsStatus(
    homeJnsUiState: HomeJnsUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (JenisTerapi) -> Unit = {},
    onDetailClick: (Int) -> Unit  // Expecting Int here
) {
    when (homeJnsUiState) {
        is HomeJnsUiState.Loading -> OnLoading(modifier.fillMaxWidth())
        is HomeJnsUiState.Success ->
            if (homeJnsUiState.jenisTerapi.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Tidak ada data kontak")
                }
            } else {
                JnsLayout(
                    jenisTerapi = homeJnsUiState.jenisTerapi,
                    modifier = modifier.fillMaxWidth(),
                    onDeleteClick = { onDeleteClick(it) },
                    onDetailClick = { onDetailClick(it.id_jenis_terapi) }  // Passing id_pasien as Int
                )
            }
        is HomeJnsUiState.Error -> OnError(retryAction, modifier.fillMaxSize())
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
fun JnsLayout(
    jenisTerapi: List<JenisTerapi>,
    modifier: Modifier = Modifier,
    onDetailClick: (JenisTerapi) -> Unit,
    onDeleteClick: (JenisTerapi) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(jenisTerapi) { jenisTerapi ->
            JenisCard(
                jenisTerapi = jenisTerapi,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(jenisTerapi) },
                onDeleteClick = { onDeleteClick(jenisTerapi) }
            )
        }
    }
}
@Composable
fun JenisCard(
    jenisTerapi: JenisTerapi,
    modifier: Modifier = Modifier,
    onDeleteClick: (JenisTerapi) -> Unit = {},
    onUpdateJenisClick: (JenisTerapi) -> Unit = {}
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
                    text = jenisTerapi.id_jenis_terapi.toString(),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                )
                Spacer(Modifier.weight(1f))


                // Tombol Delete
                IconButton(onClick = { onDeleteClick(jenisTerapi) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            Text(
                text = jenisTerapi.nama_jenis_terapi,
                style = MaterialTheme.typography.displayLarge
            )
        }
    }
}