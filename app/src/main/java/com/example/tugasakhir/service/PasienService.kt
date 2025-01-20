package com.example.tugasakhir.service

import com.example.tugasakhir.model.AllPasienResponse
import com.example.tugasakhir.model.Pasien
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface PasienApiService {

    // Mengambil daftar semua pasien
    @GET("pasien/all")
    suspend fun getAllPasien(): AllPasienResponse

    // Menambahkan data pasien
    @POST("pasien")
    suspend fun insertPasien(@Body pasien: Pasien): AllPasienResponse

    // Memperbarui data pasien berdasarkan ID
    @PUT("pasien/{id}")
    suspend fun updatePasien(@Path("id") idPasien: Int, @Body pasien: Pasien): AllPasienResponse

    // Menghapus pasien berdasarkan ID
    @DELETE("pasien/{id}")
    suspend fun deletePasien(@Path("id") idPasien: Int): AllPasienResponse

    // Mengambil data pasien berdasarkan ID
    @GET("pasien/{id}")
    suspend fun getPasienById(@Path("id") idPasien: Int): AllPasienResponse
}