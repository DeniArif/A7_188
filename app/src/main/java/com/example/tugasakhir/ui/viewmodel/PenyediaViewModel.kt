package com.example.tugasakhir.ui.viewmodel




import DetailPasienViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.tugasakhir.RumahSakitApplication
import com.example.tugasakhir.ui.viewmodel.pasien.HomePasienViewModel
import com.example.tugasakhir.ui.viewmodel.pasien.InsertPasienViewModel
import com.example.tugasakhir.ui.viewmodel.pasien.UpdatePasienViewModel


object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomePasienViewModel(DaftarPasien().container.pasienRepository)}
        initializer { DetailPasienViewModel(createSavedStateHandle(),RumahSakitApplication().container.pasienRepository) }
        initializer { InsertPasienViewModel(DaftarPasien().container.pasienRepository) }
        initializer { UpdatePasienViewModel(createSavedStateHandle(), RumahSakitApplication().container.pasienRepository) }

    }

    fun CreationExtras.DaftarPasien(): RumahSakitApplication =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RumahSakitApplication)
}