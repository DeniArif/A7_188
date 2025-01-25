package com.example.tugasakhir.service

import com.example.tugasakhir.model.AllPasienResponse
import com.example.tugasakhir.model.Pasien
import com.example.tugasakhir.model.PasienDetailResponse
import retrofit2.Response
import retrofit2.http.*

interface PasienApiService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("pasien")
    suspend fun getAllPasien(): AllPasienResponse

    @GET("pasien/{id_pasien}")
    suspend fun getPasienById(@Path("id_pasien") id_pasien: Int): PasienDetailResponse

    @POST("pasien/store")
    suspend fun insertPasien(@Body pasien: Pasien): Response<Pasien>

    @PUT("pasien/{id_pasien}")
    suspend fun updatePasien(@Path("id_pasien") id_pasien: Int, @Body pasien: Pasien)

    @DELETE("pasien/{id_pasien}")
    suspend fun deletePasien(@Path("id_pasien") id_pasien: Int): Response<Void>
}
