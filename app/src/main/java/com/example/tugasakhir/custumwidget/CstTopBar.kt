package com.example.tugasakhir.custumwidget

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomTopAppBar(
    judul: String,
    showBackButton: Boolean = true,
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (showBackButton) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = onBack,
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Text("Kembali", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }

        Text(
            text = judul,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
