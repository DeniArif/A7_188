package com.example.tugasakhir.repository

import com.example.tugasakhir.model.Pasien
import com.example.tugasakhir.service.PasienApiService

class NetworkPasienRepository(
    private val pasienApiService: PasienApiService
) : PasienRepository {

    override suspend fun getAllPasien(): List<Pasien> {
        // Memastikan pengembalian data sesuai dengan format yang diinginkan
        return pasienApiService.getAllPasien().data // Asumsi API mengembalikan data dalam field "data"
    }

    override suspend fun insertPasien(pasien: Pasien) {
        pasienApiService.insertPasien(pasien)
    }

    override suspend fun updatePasien(idPasien: Int, pasien: Pasien) {
        pasienApiService.updatePasien(idPasien, pasien)
    }

    override suspend fun deletePasien(idPasien: Int) {
        pasienApiService.deletePasien(idPasien)
    }

    override suspend fun getPasienById(idPasien: Int): Pasien {
        return pasienApiService.getPasienById(idPasien).data // Asumsi API mengembalikan data dalam field "data"
    }
}