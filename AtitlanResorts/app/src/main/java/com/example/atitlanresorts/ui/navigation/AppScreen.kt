package com.example.atitlanresorts.ui.navigation

sealed class AppScreen(val ruta: String) {

    object Login : AppScreen("login")

    object Registro : AppScreen("registro")


    object Menu : AppScreen("menu/{idUsuario}") {
        fun crearRuta(idUsuario: Int): String = "menu/$idUsuario"
    }

    object About : AppScreen("about")

    object Picnic : AppScreen("picnic")

    object Eventos : AppScreen("eventos")

    object Hospedaje : AppScreen("hospedaje")

    object Reservation : AppScreen("reservation/{idUsuario}") {
        fun crearRuta(idUsuario: Int): String = "reservation/$idUsuario"
    }

    object Payment : AppScreen("payment/{idUsuario}/{idReserva}") {
        fun crearRuta(idUsuario: Int, idReserva: Int): String = "payment/$idUsuario/$idReserva"
    }

    object Home : AppScreen("home")

    object HistorialReservaciones : AppScreen("historial_reservaciones/{idUsuario}") {
        fun crearRuta(idUsuario: Int): String = "historial_reservaciones/$idUsuario"
    }

    object Perfil : AppScreen("perfil/{idUsuario}") {
        fun crearRuta(idUsuario: Int): String = "perfil/$idUsuario"
    }

}









/* ORIGINAL UTILIZANDO EL ID USUARIO

    object Payment : AppScreen("payment/{idUsuario}") {
        fun crearRuta(idUsuario: Int): String = "payment/$idUsuario"
    }

     */













