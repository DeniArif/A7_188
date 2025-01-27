package com.example.tugasakhir.repository

import com.example.tugasakhir.model.AllTerapisResponse
import com.example.tugasakhir.model.Terapis
import com.example.tugasakhir.service.TerapisApiService
import java.io.IOException

interface TerapisRepository {
    suspend fun getAllTerapis(): AllTerapisResponse
    suspend fun insertTerapis(terapis: Terapis)
    suspend fun updateTerapis(id_terapis: Int, terapis: Terapis) // Update id_terapis to Int
    suspend fun deleteTerapis(id_terapis: Int) // Update id_terapis to Int
    suspend fun getTerapisById(id_terapis: Int): Terapis // Update id_terapis to Int
}

class NetworkTerapisRepository(
    private val terapisApiService: TerapisApiService
) : TerapisRepository {

    override suspend fun getAllTerapis(): AllTerapisResponse =
        terapisApiService.getAllTerapis()

    override suspend fun insertTerapis(terapis: Terapis) {
        terapisApiService.insertTerapis(terapis)
    }

    override suspend fun updateTerapis(id_terapis: Int, terapis: Terapis) { // Update id_terapis to Int
        terapisApiService.updateTerapis(id_terapis, terapis)
    }

    override suspend fun deleteTerapis(id_terapis: Int) { // Update id_terapis to Int
        try {
            val response = terapisApiService.deleteTerapis(id_terapis)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Terapis. HTTP Status Code: ${response.code()}")
            } else {
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getTerapisById(id_terapis: Int): Terapis {
        return terapisApiService.getTerapisById(id_terapis).data
    }
}
