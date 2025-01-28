package com.example.tugasakhir.repository

import com.example.tugasakhir.model.AllPasienResponse
import com.example.tugasakhir.model.Pasien
import com.example.tugasakhir.service.PasienApiService
import java.io.IOException

interface PasienRepository {
    suspend fun getAllPasien(): AllPasienResponse
    suspend fun insertPasien(pasien: Pasien)
    suspend fun updatePasien(id_pasien: Int, pasien: Pasien)
    suspend fun deletePasien(id_pasien: Int)
    suspend fun getPasienById(id_pasien: Int): Pasien
}

class NetworkPasienRepository(
    private val pasienApiService: PasienApiService
) : PasienRepository {

    override suspend fun getAllPasien(): AllPasienResponse =
        pasienApiService.getAllPasien()

    override suspend fun insertPasien(pasien: Pasien) {
        pasienApiService.insertPasien(pasien)
    }

    override suspend fun updatePasien(id_pasien: Int, pasien: Pasien) {
        pasienApiService.updatePasien(id_pasien, pasien)
    }

    override suspend fun deletePasien(id_pasien: Int) {
        try {
            val response = pasienApiService.deletePasien(id_pasien)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Pasien. HTTP Status Code: ${response.code()}")
            } else {
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPasienById(id_pasien: Int): Pasien {
        return pasienApiService.getPasienById(id_pasien).data
    }
}