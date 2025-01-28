package com.example.tugasakhir.ui.viewmodel





import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.tugasakhir.RumahSakitApplication
import com.example.tugasakhir.ui.viewmodel.jenisterapis.DetailJenisTerapiViewModel
import com.example.tugasakhir.ui.viewmodel.jenisterapis.HomeJenisTerapiViewModel
import com.example.tugasakhir.ui.viewmodel.jenisterapis.InsertJenisTerapiViewModel
import com.example.tugasakhir.ui.viewmodel.jenisterapis.UpdateJenisTerapiViewModel
import com.example.tugasakhir.ui.viewmodel.pasien.DetailPasienViewModel
import com.example.tugasakhir.ui.viewmodel.pasien.HomePasienViewModel
import com.example.tugasakhir.ui.viewmodel.pasien.InsertPsnViewModel
import com.example.tugasakhir.ui.viewmodel.pasien.UpdatePsnViewModel
import com.example.tugasakhir.ui.viewmodel.terapis.DetailTerapisViewModel
import com.example.tugasakhir.ui.viewmodel.terapis.HomeTerapisViewModel
import com.example.tugasakhir.ui.viewmodel.terapis.InsertTrpViewModel
import com.example.tugasakhir.ui.viewmodel.terapis.UpdateTrpViewModel


object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomePasienViewModel(DaftarPasien().container.pasienRepository)}
        initializer { DetailPasienViewModel(createSavedStateHandle(),DaftarPasien().container.pasienRepository) }
        initializer { InsertPsnViewModel(DaftarPasien().container.pasienRepository) }
        initializer { UpdatePsnViewModel(createSavedStateHandle(),DaftarPasien() .container.pasienRepository) }


        initializer { HomeTerapisViewModel(DaftarPasien().container.terapisRepository) }
        initializer { DetailTerapisViewModel(createSavedStateHandle(),DaftarPasien().container.terapisRepository) }
        initializer { InsertTrpViewModel(DaftarPasien().container.terapisRepository) }
        initializer { UpdateTrpViewModel(createSavedStateHandle(),DaftarPasien() .container.terapisRepository) }

        initializer { HomeJenisTerapiViewModel(DaftarPasien().container.jenisRepository) }
        initializer { InsertJenisTerapiViewModel(DaftarPasien().container.jenisRepository) }
        initializer { UpdateJenisTerapiViewModel(createSavedStateHandle(),DaftarPasien().container.jenisRepository) }
        initializer { DetailJenisTerapiViewModel(createSavedStateHandle(),DaftarPasien().container.jenisRepository) }

    }

    fun CreationExtras.DaftarPasien(): RumahSakitApplication =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RumahSakitApplication)
}