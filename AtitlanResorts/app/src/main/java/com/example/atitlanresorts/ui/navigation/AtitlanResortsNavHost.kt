package com.example.atitlanresorts.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.atitlanresorts.ui.screens.PerfilScreen
import com.example.atitlanresorts.ui.screens.*

@Composable
fun AtitlanResortsNavHost(navHostController: NavHostController, startDestination: String) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {

        composable(AppScreen.Login.ruta) {
            LoginScreen(navController = navHostController)
        }


        composable(AppScreen.Registro.ruta) {
            RegistroScreen(onRegistroExitoso = {


                navHostController.navigate(AppScreen.Login.ruta) {
                    popUpTo(AppScreen.Registro.ruta) { inclusive = true }
                }
            })
        }


        composable(AppScreen.About.ruta) {
            AboutScreen(navController = navHostController)
        }

        composable(
            route = AppScreen.Menu.ruta,
            arguments = listOf(navArgument("idUsuario") { type = NavType.IntType })
        ) { backStackEntry ->
            val idUsuario = backStackEntry.arguments?.getInt("idUsuario") ?: 0
            HomeScreen(idUsuario = idUsuario, navController = navHostController)
        }

        composable(AppScreen.Picnic.ruta) {
            PicnicScreen(navController = navHostController)
        }

        composable(AppScreen.Hospedaje.ruta) {
            HospedajeScreen(navController = navHostController)
        }

        composable(AppScreen.Eventos.ruta) {
            EventosScreen(navController = navHostController)
        }

        composable(
            route = AppScreen.Reservation.ruta,
            arguments = listOf(navArgument("idUsuario") { type = NavType.IntType })
        ) { backStackEntry ->
            val idUsuario = backStackEntry.arguments?.getInt("idUsuario") ?: 0
            CrearReservacionScreen(
                idUsuario = idUsuario,
                navController = navHostController,
                reservationViewModel = viewModel()
            )
        }



        composable(
            route = AppScreen.Payment.ruta,
            arguments = listOf(
                navArgument("idUsuario") { type = NavType.IntType },
                navArgument("idReserva") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val idUsuario = backStackEntry.arguments?.getInt("idUsuario") ?: 0
            val idReserva = backStackEntry.arguments?.getInt("idReserva") ?: 0

            PaymentScreen(
                idUsuario = idUsuario,
                idReserva = idReserva,
                navController = navHostController,
                reservationViewModel = viewModel()
            )
        }


        composable(
            route = AppScreen.HistorialReservaciones.ruta,
            arguments = listOf(navArgument("idUsuario") { type = NavType.IntType }),

        ) { backStackEntry ->
            val idUsuario = backStackEntry.arguments?.getInt("idUsuario") ?: 0
            HistorialReservacionesScreen(
                navController = navHostController,
                idUsuario = idUsuario
            )
        }


        composable(
            route = AppScreen.Perfil.ruta,
            arguments = listOf(
                navArgument("idUsuario") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val idUsuario = backStackEntry.arguments?.getInt("idUsuario") ?: 0
            PerfilScreen(
                idUsuario = idUsuario,
                navController = navHostController,
                reservationViewModel = viewModel()
            )
        }





    }
}
















