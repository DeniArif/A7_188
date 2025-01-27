package com.example.tugasakhir


import android.app.Application
import com.example.tugasakhir.container.AppContainer
import com.example.tugasakhir.container.RumahSakitContainer

class RumahSakitApplication : Application() {
    // Deklarasi AppContainer agar bisa diakses di seluruh aplikasi
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        // Inisialisasi container dengan PasienContainer
        container = RumahSakitContainer()
    }
}

