package com.example.tugasakhir.ui.navigation

interface DestinasiNavigasi{
    val route : String
    val titleRes : String
}

object HomeDestinasi : DestinasiNavigasi {
    override val route: String = "home"
    override val titleRes: String = "Home"
}

object DetailDestinasi : DestinasiNavigasi {
    override val route: String = "detail/{id}"
    override val titleRes: String = "Detail"
}