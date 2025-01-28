package com.example.tugasakhir.repository

import com.example.tugasakhir.model.AllJenisResponse
import com.example.tugasakhir.model.JenisTerapi
import com.example.tugasakhir.service.JenisApiService
import java.io.IOException

interface JenisTerapiRepository {
    suspend fun getAllJenisTerapi(): AllJenisResponse
    suspend fun insertJenisTerapi(terapi: JenisTerapi)
    suspend fun updateJenisTerapi(id_jenis_terapi: Int, jenisterapi: JenisTerapi)
    suspend fun deleteJenisTerapi(id_jenis_terapi: Int)
    suspend fun getJenisTerapiById(id_jenis_terapi: Int): JenisTerapi
}

class NetworkJenisRepository(
    private val jenisApiService: JenisApiService
) : JenisTerapiRepository {

    override suspend fun getAllJenisTerapi(): AllJenisResponse =
        jenisApiService.getAllJenisTerapi()

    override suspend fun insertJenisTerapi(terapi: JenisTerapi) {
        jenisApiService.insertJenisTerapi(terapi)
    }

    override suspend fun updateJenisTerapi(id_jenis_terapi: Int, terapi: JenisTerapi) {
        jenisApiService.updateJenisTerapi(id_jenis_terapi, terapi)
    }

    override suspend fun deleteJenisTerapi(id_jenis_terapi: Int) {
        try {
            val response = jenisApiService.deleteJenisTerapi(id_jenis_terapi)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Terapi. HTTP Status Code: ${response.code()}")
            } else {
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getJenisTerapiById(id_jenis_terapi: Int): JenisTerapi {
        return jenisApiService.getJenisTerapiById(id_jenis_terapi).data
    }
}
