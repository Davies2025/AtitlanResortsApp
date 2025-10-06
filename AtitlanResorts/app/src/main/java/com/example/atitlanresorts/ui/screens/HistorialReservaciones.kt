package com.example.atitlanresorts.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.atitlanresorts.R
import com.example.atitlanresorts.data.entities.Reservation
import com.example.atitlanresorts.ui.navigation.AppScreen
import com.example.atitlanresorts.ui.viewmodels.ReservationViewModel
import java.text.SimpleDateFormat
import java.util.*


val CustomDarkBlue = Color(0xFF002B5B)
val CustomPurpleHeader = Color(0xFF5E499E)

@Composable
fun HistorialReservacionesScreen(
    navController: NavController,
    idUsuario: Int,
    reservationViewModel: ReservationViewModel = viewModel()
) {
    val reservations by reservationViewModel.reservations.collectAsState()

    LaunchedEffect(Unit) {
        reservationViewModel.getAllReservations()
    }

    val userReservations = reservations.filter { it.idUsuario == idUsuario }

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.fondo_reservacion),
            contentDescription = "Fondo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(CustomPurpleHeader.copy(alpha = 0.9f)),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Image(
                            painter = painterResource(id = R.drawable.atras),
                            contentDescription = "Regresar",
                            //tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Text(
                        text = "Reservaciones y Notificaciones",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }


            if (userReservations.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(userReservations) { reservation ->
                        ReservationCard(
                            reservation = reservation,
                            navController = navController,
                            idUsuario = idUsuario
                        )
                    }
                }
            } else {
                Text(
                    text = "No tienes reservaciones recientes.",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}


@Composable
fun ReservationCard(
    reservation: Reservation,
    navController: NavController,
    idUsuario: Int
) {
    val pagoPendiente = reservation.tarjetaPago.isNullOrBlank()
    val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),

        colors = CardDefaults.cardColors(containerColor = CustomDarkBlue.copy(alpha = 0.7f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text("Datos del Encargado", fontWeight = FontWeight.ExtraBold, color = Color.White)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Nombre: ${reservation.nombreEncargado}", color = Color.White)
            Text("ID de Reservación: ${reservation.id}", color = Color.White)

            Spacer(modifier = Modifier.height(12.dp))

            Text("Datos de la Reserva", fontWeight = FontWeight.ExtraBold, color = Color.White)
            Spacer(modifier = Modifier.height(4.dp))
            Text("Tipo de Habitación: ${reservation.tipoHabitacion}", color = Color.White)
            Text("Huéspedes: ${reservation.cantidadHuespedes}", color = Color.White)
            Text("Habitaciones: ${reservation.cantidadHabitaciones}", color = Color.White)

            Text("Llegada: ${sdf.format(reservation.fechaLlegada)}", color = Color.White)
            Text("Salida: ${sdf.format(reservation.fechaSalida)}", color = Color.White)

            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = Color.White.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(8.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "Total a Pagar: $${reservation.totalPagar}",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    if (pagoPendiente) {
                        Text(
                            text = "Pago pendiente",
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                    } else {

                        Text(
                            text = "Pago Confirmado",
                            color = Color(0xFF8BC34A), // Verde claro
                            fontWeight = FontWeight.Bold
                        )
                    }
                }


                if (pagoPendiente) {
                    Button(
                        onClick = {
                            // Navega al método de pago, pasando el ID del usuario y el ID de esta reserva
                            navController.navigate(AppScreen.Payment.crearRuta(idUsuario, reservation.id))
                        },

                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                    ) {
                        Text("Pagar Ahora", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun HistorialReservacionesPreview() {
    val navController = rememberNavController()
    HistorialReservacionesScreen(navController = navController, idUsuario = 0)
}
