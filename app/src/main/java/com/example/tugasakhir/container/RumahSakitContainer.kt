package com.example.tugasakhir.container


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugasakhir.model.Pasien
import com.example.tugasakhir.repository.JenisTerapiRepository
import com.example.tugasakhir.repository.NetworkJenisRepository
import com.example.tugasakhir.repository.NetworkPasienRepository
import com.example.tugasakhir.repository.NetworkTerapisRepository
import com.example.tugasakhir.repository.PasienRepository
import com.example.tugasakhir.repository.TerapisRepository
import com.example.tugasakhir.service.JenisApiService
import com.example.tugasakhir.service.PasienApiService
import com.example.tugasakhir.service.TerapisApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val pasienRepository: PasienRepository
    val terapisRepository: TerapisRepository
    val jenisRepository: JenisTerapiRepository
}

class RumahSakitContainer : AppContainer {

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


    //Terapis

    private val terapisApiService: TerapisApiService by lazy {
        retrofit.create(TerapisApiService::class.java)
    }

    override val terapisRepository: TerapisRepository by lazy {
        NetworkTerapisRepository(terapisApiService)
    }

    //Jenis Terapi

    private val jenisApiService: JenisApiService by lazy {
        retrofit.create(JenisApiService::class.java)
    }

    override val jenisRepository: JenisTerapiRepository by lazy {
        NetworkJenisRepository(jenisApiService)
    }


}

