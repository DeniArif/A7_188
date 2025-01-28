package com.example.tugasakhir.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pasien(
    val id_pasien: Int,
    val nama: String,
    val alamat: String,
    val nomor_telepon: String,
    val tanggal_lahir: String,
    val riwayat_medikal: String
)

@Serializable
data class AllPasienResponse( // Perbaikan nama
    val status: Boolean,
    val message: String,
    val data: List<Pasien>
)

@Serializable
data class PasienDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Pasien
)
