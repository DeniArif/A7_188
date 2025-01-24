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
    @GET(".")
    suspend fun getAllPasien(): AllPasienResponse

    @GET("{id_pasien}")
    suspend fun getPasienById(@Path("id_pasien") id_pasien: String): PasienDetailResponse

    @POST("insertpasien.php")
    suspend fun insertPasien(@Body pasien: Pasien)

    @PUT("editpasien.php/{id_pasien")
    suspend fun updatePasien(@Path("id_pasien") id_pasien: String, @Body pasien: Pasien)

    @DELETE("deletepasien.php/{id_pasien}")
    suspend fun deletePasien(@Path("id_pasien") id_pasien: String): retrofit2.Response<Void>
}
