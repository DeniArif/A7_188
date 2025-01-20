package com.example.tugasakhir.container


import NetworkMahasiswaRepository
import PasienRepository
import com.example.tugasakhir.service.PasienApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val pasienRepository: PasienRepository
}

class PasienContainer : AppContainer {

    private val baseUrl = "http://10.0.2.2:80/yourApiPath/" // Ganti dengan base URL Anda
    private val json = Json { ignoreUnknownKeys = true }

    // Menyiapkan Retrofit dengan Converter untuk JSON
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    // Menginisialisasi PasienApiService
    private val pasienApiService: PasienApiService by lazy {
        retrofit.create(PasienApiService::class.java)
    }

    // Menyediakan PasienRepository menggunakan PasienApiService
    override val pasienRepository: PasienRepository by lazy {
        NetworkMahasiswaRepository(pasienApiService)
    }
}
