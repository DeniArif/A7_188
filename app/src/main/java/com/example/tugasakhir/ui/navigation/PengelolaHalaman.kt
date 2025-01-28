package com.example.tugasakhir.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tugasakhir.ui.view.DestinasiHomeAwal
import com.example.tugasakhir.ui.view.HomeViewTerapi
import com.example.tugasakhir.ui.view.jenisterapi.DestinasiDetailJenis
import com.example.tugasakhir.ui.view.jenisterapi.DestinasiHomeJns
import com.example.tugasakhir.ui.view.jenisterapi.DestinasiInsertJenis
import com.example.tugasakhir.ui.view.jenisterapi.DestinasiUpdateJns
import com.example.tugasakhir.ui.view.jenisterapi.DetailJenisDetailView
import com.example.tugasakhir.ui.view.jenisterapi.EntryJnsScreen
import com.example.tugasakhir.ui.view.jenisterapi.HomeJnsScreen
import com.example.tugasakhir.ui.view.jenisterapi.JenisUpdateView
import com.example.tugasakhir.ui.view.pasien.DestinasiDetailPasien

import com.example.tugasakhir.ui.view.pasien.DestinasiHomePsn
import com.example.tugasakhir.ui.view.pasien.DestinasiInsertPsn
import com.example.tugasakhir.ui.view.pasien.DestinasiUpdatePsn
//import com.example.tugasakhir.ui.viewpasien.DestinasiUpdatePasien
import com.example.tugasakhir.ui.view.pasien.EntryPasienScreen
import com.example.tugasakhir.ui.view.pasien.HomePsnScreen
import com.example.tugasakhir.ui.view.pasien.PasienDetailView
import com.example.tugasakhir.ui.view.pasien.PasienUpdateView
import com.example.tugasakhir.ui.view.terapis.DestinasiDetailTrp
import com.example.tugasakhir.ui.view.terapis.DestinasiHomeTerapis
import com.example.tugasakhir.ui.view.terapis.DestinasiInserTrp
import com.example.tugasakhir.ui.view.terapis.DestinasiUpdateTrp
import com.example.tugasakhir.ui.view.terapis.EntryTrpScreen
import com.example.tugasakhir.ui.view.terapis.HomeTrpScreen
import com.example.tugasakhir.ui.view.terapis.TerapisDetailView
import com.example.tugasakhir.ui.view.terapis.TerapisUpdateView



@Composable
fun PengelolaHalaman(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeAwal.route,
        modifier = Modifier,
    ) {
        composable(
            route = DestinasiHomeAwal.route

        ){

            HomeViewTerapi(
                onNavigateToPasien = {
                    navController.navigate(DestinasiHomePsn.route)
                },
                onNavigateToTerapis = {
                    navController.navigate(DestinasiHomeTerapis.route)
                },
                onNavigateToJenisTerapi = {
                    navController.navigate(DestinasiHomeJns.route)
                }

            )
        }

        //HomePasien
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

        //TambahPasien
        composable(DestinasiInsertPsn.route) {
            EntryPasienScreen(navigateBack = {
                navController.navigate(DestinasiHomePsn.route) {
                    popUpTo(DestinasiHomePsn.route) {
                        inclusive = true
                    }
                }
            })
        }

        //DetailPasien
        composable(
            DestinasiDetailPasien.routesWithArg, // Correct route with argument
            arguments = listOf(
                navArgument(DestinasiDetailPasien.ID_PASIEN) { type = NavType.IntType } // Ubah menjadi IntType
            )
        ) { backStackEntry ->

            val id_pasien = backStackEntry.arguments?.getInt(DestinasiDetailPasien.ID_PASIEN) // Gunakan getInt untuk mengambil Int
            id_pasien?.let {
                PasienDetailView(
                    id_pasien = it,
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

        //UpdatePasien
        composable(
            DestinasiUpdatePsn.routesWithArg, // Correct route with argument
            arguments = listOf(
                navArgument(DestinasiUpdatePsn.ID_PASIEN) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

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






        //TampilanHomeTerapis
        composable(DestinasiHomeTerapis.route) {
            HomeTrpScreen(
                navigateToItemEntry = { navController.navigate(DestinasiInserTrp.route)},
                navigateBack = {},
                onDetailClick = { id_pasien ->
                    navController.navigate("${DestinasiDetailTrp.route}/${id_pasien}") {
                        popUpTo(DestinasiHomeTerapis.route) {
                            inclusive =true
                        }
                    }
                }
            )
        }

        //Tambah Terapis
        composable(DestinasiInserTrp.route) {
            EntryTrpScreen(navigateBack = {
                navController.navigate(DestinasiHomeTerapis.route) {
                    popUpTo(DestinasiHomeTerapis.route) {
                        inclusive = true
                    }
                }
            })
        }

        //DetailTerapis
        composable(
            DestinasiDetailTrp.routesWithArg, // Correct route with argument
            arguments = listOf(
                navArgument(DestinasiDetailTrp.ID_TERAPIS) { type = NavType.IntType } // Ubah menjadi IntType
            )
        ) { backStackEntry ->
            // Mendapatkan ID Pasien dari argument route
            val id_terapis = backStackEntry.arguments?.getInt(DestinasiDetailTrp.ID_TERAPIS) // Gunakan getInt untuk mengambil Int
            id_terapis?.let {
                TerapisDetailView(
                    id_terapis = it, // Mengirimkan ID Pasien ke DetailPasienView
                    navigateBack = {
                        // Aksi ketika tombol "Kembali" ditekan
                        navController.navigate(DestinasiHomeTerapis.route) {
                            popUpTo(DestinasiHomeTerapis.route) {
                                inclusive = true // Pop sampai ke DestinasiHome
                            }
                        }
                    },
                    onEditClick = {
                        // Navigasi ke halaman update dengan ID Pasien sebagai argumen
                        navController.navigate("${DestinasiUpdateTrp.route}/$it")
                    }
                )
            }
        }

        //UpdateTerapis
        composable(
            DestinasiUpdateTrp.routesWithArg, // Correct route with argument
            arguments = listOf(
                navArgument(DestinasiUpdateTrp.ID_TERAPIS) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            // Retrieve the 'idPasien' argument from the navBackStackEntry
            val id_terapis = backStackEntry.arguments?.getString(DestinasiUpdateTrp.ID_TERAPIS)
            id_terapis?.let {
                // Pass 'idPasien' to the UpdatePasienView composable
                TerapisUpdateView(
                    id_terapis = it,
                    navigateBack = {
                        navController.popBackStack()
                    },
                    modifier = modifier
                )
            }
        }




        //JenisTerapi
        composable(DestinasiHomeJns.route) {
            HomeJnsScreen(
                navigateToItemEntry = { navController.navigate(DestinasiInsertJenis.route)},
                onDetailClick = { id_pasien ->
                    navController.navigate("${DestinasiDetailJenis.route}/${id_pasien}") {
                        popUpTo(DestinasiHomeJns.route) {
                            inclusive =true
                        }
                    }
                }
            )
        }


        composable(DestinasiInsertJenis.route) {
            EntryJnsScreen(navigateBack = {
                navController.navigate(DestinasiHomeJns.route) {
                    popUpTo(DestinasiHomeJns.route) {
                        inclusive = true
                    }
                }
            })
        }

        composable(
            DestinasiDetailJenis.routesWithArg, // Correct route with argument
            arguments = listOf(
                navArgument(DestinasiDetailJenis.ID_JENIS_TERAPI) { type = NavType.IntType } // Ubah menjadi IntType
            )
        ) { backStackEntry ->

            val id_jenis_terapi = backStackEntry.arguments?.getInt(DestinasiDetailJenis.ID_JENIS_TERAPI) // Gunakan getInt untuk mengambil Int
            id_jenis_terapi?.let {
                DetailJenisDetailView(
                    id_jenis_terapi = it,
                    navigateBack = {
                        // Aksi ketika tombol "Kembali" ditekan
                        navController.navigate(DestinasiHomeJns.route) {
                            popUpTo(DestinasiHomeJns.route) {
                                inclusive = true // Pop sampai ke DestinasiHome
                            }
                        }
                    },
                    onEditClick = {
                        // Navigasi ke halaman update dengan ID Pasien sebagai argumen
                        navController.navigate("${DestinasiUpdateJns.route}/$it")
                    }
                )
            }
        }

        composable(
            DestinasiUpdateJns.routesWithArg, // Correct route with argument
            arguments = listOf(
                navArgument(DestinasiUpdateJns.ID_JENIS_TERAPI) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            // Retrieve the 'idPasien' argument from the navBackStackEntry
            val id_jenis_terapi = backStackEntry.arguments?.getString(DestinasiUpdateJns.ID_JENIS_TERAPI)
            id_jenis_terapi?.let {
                // Pass 'idPasien' to the UpdatePasienView composable
                JenisUpdateView(
                    id_jenis_terapi = it,
                    navigateBack = {
                        navController.popBackStack()
                    },
                    modifier = modifier
                )
            }
        }


    }
}

