package com.example.tugasakhir.ui.view.terapis

import CostumeTopAppBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhir.R
import com.example.tugasakhir.model.Pasien

import com.example.tugasakhir.model.Terapis
import com.example.tugasakhir.ui.navigation.DestinasiNavigasi
import com.example.tugasakhir.ui.viewmodel.PenyediaViewModel
import com.example.tugasakhir.ui.viewmodel.terapis.HomeTerapisViewModel
import com.example.tugasakhir.ui.viewmodel.terapis.HomeTrpUiState


object DestinasiHomeTerapis : DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "Home Daftar Terapis"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTrpScreen(
    navigateBack: () -> Unit,
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},  // Change to accept Int instead of String
    viewModel: HomeTerapisViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeTerapis.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = { viewModel.getTerapis() },
                navigateUp = navigateBack

            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Terapis")
            }
        }
    ) { innerPadding ->
        HomeTrpStatus(
            homeTrpUiState = viewModel.terapisUiState,
            retryAction = { viewModel.getTerapis() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,  // Ensure onDetailClick is accepting Int
            onDeleteClick = {
                viewModel.deleteTerapis(it.id_terapis)
                viewModel.getTerapis()
            }
        )
    }
}


@Composable
fun HomeTrpStatus(
    homeTrpUiState: HomeTrpUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Terapis) -> Unit = {},
    onDetailClick: (Int) -> Unit  // Expecting Int here
) {
    when (homeTrpUiState) {
        is HomeTrpUiState.Loading -> OnLoading(modifier.fillMaxWidth())
        is HomeTrpUiState.Success ->
            if (homeTrpUiState.terapis.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Tidak ada data kontak")
                }
            } else {
                TrpLayout(
                    terapis = homeTrpUiState.terapis,
                    modifier = modifier.fillMaxWidth(),
                    onDeleteClick = { onDeleteClick(it) },
                    onDetailClick = { onDetailClick(it.id_terapis) }  // Passing id_terapis as Int
                )
            }
        is HomeTrpUiState.Error -> OnError(retryAction, modifier.fillMaxSize())
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
fun TrpLayout(
    terapis: List<Terapis>,
    modifier: Modifier = Modifier,
    onDetailClick: (Terapis) -> Unit,
    onDeleteClick: (Terapis) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(terapis) { terapis ->
            TerapisCard(
                terapis = terapis,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(terapis) },
                onDeleteClick = { onDeleteClick(terapis) }
            )
        }
    }
}
@Composable
fun TerapisCard(
    terapis: Terapis,
    modifier: Modifier = Modifier,
    onDeleteClick: (Terapis) -> Unit = {},
    onUpdateTerapisClick: (Terapis) -> Unit = {}
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
                    text = terapis.id_terapis.toString(),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                )
                Spacer(Modifier.weight(1f))


                // Tombol Delete
                IconButton(onClick = { onDeleteClick(terapis) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            Text(
                text = terapis.nama_terapis,
                style = MaterialTheme.typography.displayLarge
            )
        }
    }
}
