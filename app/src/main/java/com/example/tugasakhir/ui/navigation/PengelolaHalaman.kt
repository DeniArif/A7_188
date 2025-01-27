package com.example.tugasakhir.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tugasakhir.ui.view.pasien.DestinasiDetailPasien

import com.example.tugasakhir.ui.view.pasien.DestinasiHomePsn
import com.example.tugasakhir.ui.view.pasien.DestinasiInsertPsn
import com.example.tugasakhir.ui.view.pasien.DestinasiUpdatePsn
//import com.example.tugasakhir.ui.viewpasien.DestinasiUpdatePasien
import com.example.tugasakhir.ui.view.pasien.EntryPasienScreen
import com.example.tugasakhir.ui.view.pasien.HomePsnScreen
import com.example.tugasakhir.ui.view.pasien.PasienDetailView
import com.example.tugasakhir.ui.view.pasien.PasienUpdateView
//import com.example.tugasakhir.ui.view.terapis.DestinasiDetailTrp
//import com.example.tugasakhir.ui.view.terapis.DestinasiHomeTerapis
//import com.example.tugasakhir.ui.view.terapis.HomeTrpScreen


@Composable
fun PengelolaHalaman(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomePsn.route,
        modifier = Modifier,
    ) {
        composable(DestinasiHomePsn.route) {
            HomePsnScreen(
                navigateToItemEntry = { navController.navigate(DestinasiInsertPsn.route)},
                onDetailClick = { id_pasien ->
                    navController.navigate("${DestinasiDetailPasien.route}/${id_pasien}") {
                        popUpTo(DestinasiHomePsn.route) {
                            inclusive =true
                        }
                    }
                }
            )
        }

        composable(DestinasiInsertPsn.route) {
            EntryPasienScreen(navigateBack = {
                navController.navigate(DestinasiHomePsn.route) {
                    popUpTo(DestinasiHomePsn.route) {
                        inclusive = true
                    }
                }
            })
        }

        composable(
            DestinasiDetailPasien.routesWithArg, // Correct route with argument
            arguments = listOf(
                navArgument(DestinasiDetailPasien.ID_PASIEN) { type = NavType.IntType } // Ubah menjadi IntType
            )
        ) { backStackEntry ->
            // Mendapatkan ID Pasien dari argument route
            val id_pasien = backStackEntry.arguments?.getInt(DestinasiDetailPasien.ID_PASIEN) // Gunakan getInt untuk mengambil Int
            id_pasien?.let {
                PasienDetailView(
                    id_pasien = it, // Mengirimkan ID Pasien ke DetailPasienView
                    navigateBack = {
                        // Aksi ketika tombol "Kembali" ditekan
                        navController.navigate(DestinasiHomePsn.route) {
                            popUpTo(DestinasiHomePsn.route) {
                                inclusive = true // Pop sampai ke DestinasiHome
                            }
                        }
                    },
                    onEditClick = {
                        // Navigasi ke halaman update dengan ID Pasien sebagai argumen
                        navController.navigate("${DestinasiUpdatePsn.route}/$it")
                    }
                )
            }
        }

        composable(
            DestinasiUpdatePsn.routesWithArg, // Correct route with argument
            arguments = listOf(
                navArgument(DestinasiUpdatePsn.ID_PASIEN) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            // Retrieve the 'idPasien' argument from the navBackStackEntry
            val id_pasien = backStackEntry.arguments?.getString(DestinasiUpdatePsn.ID_PASIEN)
            id_pasien?.let {
                // Pass 'idPasien' to the UpdatePasienView composable
                PasienUpdateView(
                    id_pasien = it,
                    navigateBack = {
                        navController.popBackStack()
                    },
                    modifier = modifier
                )
            }
        }

//        //Terapis
//        composable(DestinasiHomeTerapis.route) {
//            HomeTrpScreen(
//                navigateToItemEntry = { navController.navigate(DestinasiEntry.route)},
//                onDetailClick = { id_terapis ->
//                    navController.navigate("${DestinasiDetailTrp.route}/${id_terapis}") {
//                        popUpTo(DestinasiHomeTerapis.route) {
//                            inclusive =true
//                        }
//                    }
//                }
//            )
//        }
    }
}

