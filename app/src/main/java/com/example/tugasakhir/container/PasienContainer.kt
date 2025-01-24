package com.example.tugasakhir.container




import com.example.tugasakhir.repository.NetworkPasienRepository
import com.example.tugasakhir.repository.PasienRepository
import com.example.tugasakhir.service.PasienApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val pasienRepository: PasienRepository
}

class PasienContainer : AppContainer {

    private val baseUrl = "http://10.0.2.2:2000/api/"
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
        NetworkPasienRepository(pasienApiService)
    }
}
