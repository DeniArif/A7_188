package com.example.tugasakhir.ui.viewpasien

import CostumeTopAppBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugasakhir.R
import com.example.tugasakhir.model.Pasien
import com.example.tugasakhir.ui.navigation.DestinasiNavigasi
import com.example.tugasakhir.ui.viewmodel.PenyediaViewModel
import com.example.tugasakhir.ui.viewmodel.pasien.HomePasienUiState
import com.example.tugasakhir.ui.viewmodel.pasien.HomePasienViewModel

object DestinasiHomePasien : DestinasiNavigasi {
    override val route = "home_pasien"
    override val titleRes = "Manajemen Pasien"
}

@Composable
fun HomePasienScreen(
    navigateToAddPasien: () -> Unit,
    navigateToDetailPasien: (String) -> Unit,
    viewModel: HomePasienViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomePasien.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = { viewModel.getPasien() }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToAddPasien,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Pasien")
            }
        }
    ) { innerPadding ->
        HomePasienStatus(
            pasienUiState = viewModel.pasienUiState,
            retryAction = { viewModel.getPasien() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = navigateToDetailPasien,
            onDeleteClick = { pasien -> viewModel.deletePasien(pasien.idPasien) }
        )
    }
}

@Composable
fun HomePasienStatus(
    pasienUiState: HomePasienUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pasien) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (pasienUiState) {
        is HomePasienUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomePasienUiState.Success ->
            if (pasienUiState.pasien.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data pasien")
                }
            } else {
                PasienLayout(
                    pasien = pasienUiState.pasien,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.idPasien) },
                    onDeleteClick = { onDeleteClick(it) }
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
        Image(painter = painterResource(id = R.drawable.error), contentDescription = "")
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

