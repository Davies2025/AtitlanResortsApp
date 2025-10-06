package com.example.atitlanresorts.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import com.example.atitlanresorts.R
import com.example.atitlanresorts.ui.navigation.AppScreen
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(idUsuario: Int, navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {


        Image(
            painter = painterResource(id = R.drawable.lago_atitlan),
            contentDescription = "Fondo Lago Atitlán",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {


            Text(
                text = "Bienvenido, Usuario #$idUsuario",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp)
            )


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {


                NavigationButton(
                    imageResId = R.drawable.ic_cerrarsesion,
                    onClick = {
                        navController.navigate(AppScreen.Login.ruta) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )


                NavigationButton(
                    imageResId = R.drawable.usuario__2_,
                    onClick = {
                        // Navega a la ruta del Perfil con el ID del usuario
                        navController.navigate(AppScreen.Perfil.crearRuta(idUsuario))
                    }
                )


                NavigationButton(
                    imageResId = R.drawable.ic_reservaciones,
                    onClick = {
                        navController.navigate(
                            AppScreen.HistorialReservaciones.crearRuta(idUsuario)
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))


            Text(
                text = "SERVICIOS DEL HOTEL",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ServiceCard(
                    imageResId = R.drawable.fondo_picnic,
                    title = "Picnic",
                    onClick = { navController.navigate(AppScreen.Picnic.ruta) }
                )
                ServiceCard(
                    imageResId = R.drawable.jardin,
                    title = "Eventos",
                    onClick = { navController.navigate(AppScreen.Eventos.ruta) }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            ServiceCard(
                imageResId = R.drawable.luna_miel,
                title = "Hospedaje",
                onClick = { navController.navigate(AppScreen.Hospedaje.ruta) }
            )
        }


        Button(
            onClick = { navController.navigate(AppScreen.Reservation.crearRuta(idUsuario)) },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF004AAD)),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .height(50.dp)
                .wrapContentWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.reserva),
                contentDescription = "Calendario",
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text("Crear Reservación", color = Color.White, fontSize = 14.sp)
        }
    }
}


@Composable
fun NavigationButton(imageResId: Int, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(48.dp)
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Navegar",
            modifier = Modifier.size(40.dp)
        )
    }
}

@Composable
fun ServiceCard(imageResId: Int, title: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = title,
            modifier = Modifier
                .size(width = 140.dp, height = 100.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF004AAD)),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.width(120.dp)
        ) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    val navController = rememberNavController()
    HomeScreen(idUsuario = 1, navController = navController)
}





