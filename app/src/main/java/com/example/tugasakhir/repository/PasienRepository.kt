package com.example.tugasakhir.repository

import com.example.tugasakhir.model.AllPasienResponse
import com.example.tugasakhir.model.Pasien
import com.example.tugasakhir.service.PasienApiService
import java.io.IOException


interface PasienRepository {
    // Mengambil daftar semua pasien
    suspend fun getAllPasien(): AllPasienResponse

    // Menambahkan data pasien
    suspend fun insertPasien(pasien: Pasien)

    // Memperbarui data pasien berdasarkan ID
    suspend fun updatePasien(idPasien: Int, pasien: Pasien)

    // Menghapus pasien berdasarkan ID
    suspend fun deletePasien(idPasien: Int)

    // Mengambil data pasien berdasarkan ID
    suspend fun getPasienById(idPasien: Int): Pasien
}

class NetworkPasienRepository(
    private val pasienApiService: PasienApiService
) : PasienRepository {
    override suspend fun getAllPasien(): AllPasienResponse =
        pasienApiService.getAllPasien()

    override suspend fun insertPasien(pasien: Pasien) {
        pasienApiService.insertPasien(pasien)
    }

    override suspend fun updatePasien(idPasien: Int, pasien: Pasien) {
        pasienApiService.updatePasien(idPasien, pasien)
    }

    override suspend fun deletePasien(idPasien: Int) {
        try {
            val response = pasienApiService.deletePasien(idPasien)
            if (!response.isSuccessful) {
                throw IOException(
                    "Failed to delete Pasien. HTTP Status Code: ${response.code()}"
                )
            } else {
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPasienById(idPasien: Int): Pasien {
        return pasienApiService.getPasienById(idPasien).data
    }
}