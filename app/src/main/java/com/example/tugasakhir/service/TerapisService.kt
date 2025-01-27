package com.example.tugasakhir.service


import com.example.tugasakhir.model.AllTerapisResponse
import com.example.tugasakhir.model.Terapis
import com.example.tugasakhir.model.TerapisDetailResponse
import retrofit2.Response
import retrofit2.http.*

interface TerapisApiService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("terapis")
    suspend fun getAllTerapis(): AllTerapisResponse

    @GET("terapis/{id_terapis}")
    suspend fun getTerapisById(@Path("id_terapis") id_terapis: Int): TerapisDetailResponse

    @POST("terapis/store")
    suspend fun insertTerapis(@Body terapis: Terapis): Response<Terapis> // Menambahkan terapis baru

    @PUT("terapis/{id_terapis}")
    suspend fun updateTerapis(@Path("id_terapis") id_terapi: Int, @Body terapis: Terapis)

    @DELETE("terapis/{id_terapis}")
    suspend fun deleteTerapis(@Path("id_terapis") id_terapis: Int): Response<Void> // Mengubah id_terapis menjadi Int
}
