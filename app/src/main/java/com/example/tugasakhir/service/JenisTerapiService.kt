package com.example.tugasakhir.service

import com.example.tugasakhir.model.AllJenisResponse
import com.example.tugasakhir.model.JenisDetailResponse
import com.example.tugasakhir.model.JenisTerapi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface JenisApiService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("jenis_terapi/")
    suspend fun getAllJenisTerapi(): AllJenisResponse

    @GET("jenis_terapi/{id_jenis_terapi}")
    suspend fun getJenisTerapiById(@Path("id_jenis_terapi") id_jenis_terapi: Int): JenisDetailResponse

    @POST("jenis_terapi/store")
    suspend fun insertJenisTerapi(@Body jenisTerapi: JenisTerapi): Response<JenisTerapi>

    @PUT("jenis_terapi/update_jenis/{id_jenis_terapi}")
    suspend fun updateJenisTerapi(
        @Path("id_jenis_terapi") id_jenis_terapi: Int,
        @Body jenisTerapi: JenisTerapi
    ): Response<JenisTerapi>

    @DELETE("jenis_terapi/{id_jenis_terapi}")
    suspend fun deleteJenisTerapi(@Path("id_jenis_terapi") id_jenis_terapi: Int): Response<Void>
}
