package com.example.atitlanresorts.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.atitlanresorts.R
import com.example.atitlanresorts.ui.navigation.AppScreen
import com.example.atitlanresorts.ui.viewmodels.ReservationViewModel
import java.util.Calendar


fun isValidExpiryDate(dateString: String): Boolean {
    if (dateString.length != 5 || dateString[2] != '/') return false

    val parts = dateString.split("/")
    if (parts.size != 2) return false

    val month = parts[0].toIntOrNull() ?: return false
    val year = parts[1].toIntOrNull() ?: return false

    if (month < 1 || month > 12) return false

    val currentYear = Calendar.getInstance().get(Calendar.YEAR) % 100
    val currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1

    if (year < currentYear) return false
    if (year == currentYear && month < currentMonth) return false

    return true
}

@Composable
fun PaymentScreen(
    navController: NavController,
    idUsuario: Int,
    idReserva: Int,
    reservationViewModel: ReservationViewModel? = null
) {
    val vm = reservationViewModel ?: viewModel<ReservationViewModel>()
    val context = LocalContext.current

    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }


    val colorTextoInput = Color.Black
    val colorsTextField = OutlinedTextFieldDefaults.colors(
        focusedTextColor = colorTextoInput,
        unfocusedTextColor = colorTextoInput,
        focusedLabelColor = colorTextoInput.copy(alpha = 0.7f),
        unfocusedLabelColor = colorTextoInput.copy(alpha = 0.5f)
    )

    val reservation by vm.getReservationById(idReserva).collectAsState(initial = null)

    if (reservation == null) {
        if (idReserva <= 0) {
            LaunchedEffect(Unit) {
                Toast.makeText(context, "ID de reservación no válido o no encontrado.", Toast.LENGTH_LONG).show()
                navController.popBackStack()
            }
        }
        return Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    val res = reservation!!

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.lago_atitlan),
            contentDescription = "Fondo lago Atitlán",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.logohotel),
                    contentDescription = "Logo Hotel",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = "Método de Pago",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF002B5B)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.8f), shape = RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {

                OutlinedTextField(
                    value = cardNumber,
                    onValueChange = { newValue ->
                        val filtered = newValue.filter { it.isDigit() }
                        if (filtered.length <= 16) {
                            cardNumber = filtered
                        }
                    },
                    label = { Text("Número de Tarjeta") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = colorsTextField
                )
                Spacer(modifier = Modifier.height(12.dp))


                OutlinedTextField(
                    value = expiryDate,
                    onValueChange = { newValue ->
                        val filtered = newValue.filter { it.isDigit() }

                        if (filtered.length <= 4) {
                            expiryDate = when (filtered.length) {
                                1 -> if (filtered.toInt() > 1) "0$filtered/" else filtered
                                2 -> "$filtered/"
                                3 -> "${filtered.substring(0, 2)}/${filtered.substring(2, 3)}"
                                4 -> "${filtered.substring(0, 2)}/${filtered.substring(2, 4)}"
                                else -> ""
                            }
                        }
                    },
                    label = { Text("Fecha de Vencimiento (MM/YY)") },
                    placeholder = { Text("MM/YY") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = colorsTextField
                )
                Spacer(modifier = Modifier.height(12.dp))


                OutlinedTextField(
                    value = cvv,
                    onValueChange = { newValue ->
                        val filtered = newValue.filter { it.isDigit() }
                        if (filtered.length <= 3) {
                            cvv = filtered
                        }
                    },
                    label = { Text("Código de Seguridad (CVV)") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    colors = colorsTextField
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF002B5B), shape = RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Text(
                    "Total a Cancelar: \$${res.totalPagar}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Cantidad de huéspedes: ${res.cantidadHuespedes}",
                    color = Color.White
                )
                Text(
                    "${res.cantidadHabitaciones} Habitación(es) ${res.tipoHabitacion}",
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        navController.navigate(AppScreen.Menu.crearRuta(idUsuario)) {
                            popUpTo(AppScreen.Menu.ruta) { inclusive = true }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A6CF7))
                ) {
                    Text("Regresar a Menú", color = Color.White)
                }

                /*Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A6CF7))
                ) {
                    Text("Regresar", color = Color.White)
                }

                 */

                Button(
                    onClick = {

                        if (cardNumber.length != 16) {
                            Toast.makeText(context, "El número de tarjeta debe tener 16 dígitos", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        if (!isValidExpiryDate(expiryDate)) {
                            Toast.makeText(context, "Fecha de vencimiento inválida o pasada (MM/YY)", Toast.LENGTH_LONG).show()
                            return@Button
                        }
                        if (cvv.length != 3) {
                            Toast.makeText(context, "El CVV debe tener 3 dígitos", Toast.LENGTH_SHORT).show()
                            return@Button
                        }


                        vm.confirmarPago(idReserva, cardNumber) {
                            Toast.makeText(context, "Pago realizado con éxito", Toast.LENGTH_SHORT).show()

                            navController.navigate(AppScreen.HistorialReservaciones.crearRuta(idUsuario)) {
                                popUpTo(AppScreen.Reservation.ruta) { inclusive = true }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                ) {
                    Text("Aceptar", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}




