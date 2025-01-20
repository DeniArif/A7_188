package com.example.tugasakhir.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pasien(
    @SerialName("id_pasien")
    val idPasien: Int, // Kolom id_pasien (AUTO_INCREMENT)

    @SerialName("nama")
    val nama: String, // Kolom nama

    @SerialName("alamat")
    val alamat: String, // Kolom alamat

    @SerialName("nomor_telepon")
    val nomorTelepon: String, // Kolom nomor_telepon

    @SerialName("tanggal_lahir")
    val tanggalLahir: String, // Kolom tanggal_lahir

    @SerialName("riwayat_medikal")
    val riwayatMedikal: String // Kolom rivayat_medikal
)

@Serializable
data class AllPasienResponse(
    val status: Boolean,
    val message: String,
    val data: List<Pasien> // List of Pasien objects
)

@Serializable
data class PasienDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Pasien // Single Pasien object
)