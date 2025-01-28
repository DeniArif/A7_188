package com.example.tugasakhir.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tugasakhir.R
import com.example.tugasakhir.custumwidget.CustomTopAppBar
import com.example.tugasakhir.ui.navigation.DestinasiNavigasi

object DestinasiHomeAwal : DestinasiNavigasi{
    override val route = "home awal"
    override val titleRes = "Home Halaman"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeViewTerapi(
    onNavigateToPasien: () -> Unit = {},
    onNavigateToTerapis: () -> Unit = {},
    onNavigateToJenisTerapi: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                judul = "Beranda",
                showBackButton = false,
                onBack = {},
                modifier = modifier


            )
        }
    ) { innerPadding ->
        HomeContent(
            onNavigateToPasien = onNavigateToPasien,
            onNavigateToTerapis = onNavigateToTerapis,
            onNavigateToJenisTerapi = onNavigateToJenisTerapi,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun HomeContent(
    onNavigateToPasien: () -> Unit = {},
    onNavigateToTerapis: () -> Unit = {},
    onNavigateToJenisTerapi: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.rmhskt),
                contentDescription = "Logo Aplikasi",
                modifier = Modifier.size(120.dp)
            )

            Text(
                text = "Selamat Datang di Aplikasi Manajemen Terapi",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            ElevatedButton(
                onClick = onNavigateToPasien,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Icon(Icons.Default.Person, contentDescription = "Kelola Pasien", tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Kelola Pasien", color = Color.White)
            }

            ElevatedButton(
                onClick = onNavigateToTerapis,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Icon(Icons.Default.Settings, contentDescription = "Kelola Terapis", tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Kelola Terapis", color = Color.White)
            }

            ElevatedButton(
                onClick = onNavigateToJenisTerapi,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                ),
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Icon(Icons.Default.Info, contentDescription = "Kelola Jenis Terapi", tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Kelola Jenis Terapi", color = Color.White)
            }
        }
    }
}
