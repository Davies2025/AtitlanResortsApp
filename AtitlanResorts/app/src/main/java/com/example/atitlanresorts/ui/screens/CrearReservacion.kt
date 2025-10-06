package com.example.atitlanresorts.ui.screens

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.atitlanresorts.data.entities.Habitacion
import com.example.atitlanresorts.data.entities.Reservation
import com.example.atitlanresorts.ui.navigation.AppScreen
import com.example.atitlanresorts.ui.viewmodels.ReservationViewModel
import java.util.concurrent.TimeUnit
import java.util.*
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearReservacionScreen(
    idUsuario: Int,
    navController: NavController,
    reservationViewModel: ReservationViewModel? = null
) {
    val context = LocalContext.current
    val vm = reservationViewModel ?: viewModel<ReservationViewModel>()


    val colorFondoAzul = Color(0xFF05236E)
    val colorLetraFormulario = Color(0xFFE5C069)
    val colorBotonRegresar = Color(0xFF5488DC)
    val colorLetraCampos = Color.White


    var nombre by rememberSaveable { mutableStateOf("") }
    var tipoHabitacion by rememberSaveable { mutableStateOf("") }
    var cantidadHuespedes by rememberSaveable { mutableStateOf("") }
    var cantidadHabitacionesText by rememberSaveable { mutableStateOf("1") }
    var fechaLlegadaText by rememberSaveable { mutableStateOf("") }
    var fechaSalidaText by rememberSaveable { mutableStateOf("") }
    var fechaLlegadaDate by remember { mutableStateOf<Date?>(null) }
    var fechaSalidaDate by remember { mutableStateOf<Date?>(null) }


    val habitacionesState by vm.habitaciones.collectAsState()
    val habitaciones = if (habitacionesState.isEmpty()) {
        listOf(
            Habitacion(tipo = "Colibrí", cantidadDisponible = 20, capacidad = 4),
            Habitacion(tipo = "Luna de Miel", cantidadDisponible = 10, capacidad = 3)
        )
    } else habitacionesState

    val calendar = Calendar.getInstance()

    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    val todayMillis = calendar.timeInMillis

    val dayMillis = TimeUnit.DAYS.toMillis(1)


    val datePickerLlegada = DatePickerDialog(
        context,
        { _, year, month, day ->
            val c = Calendar.getInstance()
            c.set(year, month, day, 0, 0, 0)
            val newLlegadaDate = c.time

            if (fechaSalidaDate != null && newLlegadaDate.time >= fechaSalidaDate!!.time) {
                fechaSalidaDate = null
                fechaSalidaText = ""
                Toast.makeText(context, "La salida debe ser posterior a la llegada y se ha restablecido.", Toast.LENGTH_LONG).show()
            }

            fechaLlegadaDate = newLlegadaDate
            fechaLlegadaText = "$day/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    datePickerLlegada.datePicker.minDate = todayMillis


    val datePickerSalida = DatePickerDialog(
        context,
        { _, year, month, day ->
            val c = Calendar.getInstance()
            c.set(year, month, day, 0, 0, 0)
            fechaSalidaDate = c.time
            fechaSalidaText = "$day/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    fun showDatePickerSalida() {
        if (fechaLlegadaDate == null) {
            Toast.makeText(context, "Primero selecciona la Fecha de Llegada.", Toast.LENGTH_SHORT).show()
            return
        }

        val minSalidaMillis = fechaLlegadaDate!!.time + dayMillis
        datePickerSalida.datePicker.minDate = minSalidaMillis
        datePickerSalida.show()
    }


    val topBoxHeight = 80.dp

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.lagos),
            contentDescription = "Fondo Lago",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(topBoxHeight)
                .background(colorFondoAzul)
                .align(Alignment.TopCenter)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    painter = painterResource(id = R.drawable.logohotel),
                    contentDescription = "Logo Atitlan Resorts",
                    modifier = Modifier.size(90.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))


                Text(
                    text = "Formulario",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorLetraFormulario,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = topBoxHeight + 16.dp)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del Encargado", color = colorLetraCampos) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = colorLetraCampos,
                    unfocusedLabelColor = colorLetraCampos,
                    focusedTextColor = colorLetraCampos,
                    unfocusedTextColor = colorLetraCampos
                )
            )
            Spacer(modifier = Modifier.height(8.dp))


            var expanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = tipoHabitacion,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipo de Habitación", color = colorLetraCampos) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedLabelColor = colorLetraCampos,
                        unfocusedLabelColor = colorLetraCampos,
                        focusedTextColor = colorLetraCampos,
                        unfocusedTextColor = colorLetraCampos
                    )
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(Color.White)
                ) {
                    habitaciones.forEach { h ->
                        DropdownMenuItem(
                            text = { Text(h.tipo, color = Color.Black) },
                            onClick = {
                                tipoHabitacion = h.tipo
                                expanded = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))


            OutlinedTextField(
                value = cantidadHuespedes,
                onValueChange = { cantidadHuespedes = it.filter { it.isDigit() } },
                label = { Text("Cantidad de Huéspedes", color = colorLetraCampos) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = colorLetraCampos,
                    unfocusedLabelColor = colorLetraCampos,
                    focusedTextColor = colorLetraCampos,
                    unfocusedTextColor = colorLetraCampos
                )
            )
            Spacer(modifier = Modifier.height(8.dp))


            OutlinedTextField(
                value = cantidadHabitacionesText,
                onValueChange = { cantidadHabitacionesText = it.filter { it.isDigit() } },
                label = { Text("Cantidad de Habitaciones", color = colorLetraCampos) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = colorLetraCampos,
                    unfocusedLabelColor = colorLetraCampos,
                    focusedTextColor = colorLetraCampos,
                    unfocusedTextColor = colorLetraCampos
                )
            )
            Spacer(modifier = Modifier.height(8.dp))


            val colibri = habitaciones.firstOrNull { it.tipo == "Colibrí" }
            val lunaDeMiel = habitaciones.firstOrNull { it.tipo == "Luna de Miel" }

            if (colibri != null || lunaDeMiel != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "Habitaciones Disponibles:",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 18.sp
                    )
                    if (colibri != null) {
                        Text(
                            text = "${colibri.cantidadDisponible} Colibrí",
                            color = Color.White,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                    if (lunaDeMiel != null) {
                        Text(
                            text = "${lunaDeMiel.cantidadDisponible} Luna de Miel",
                            color = Color.White,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))


            OutlinedTextField(
                value = fechaLlegadaText,
                onValueChange = {},
                readOnly = true,
                label = { Text("Fecha de Llegada", color = colorLetraCampos) },
                trailingIcon = {
                    IconButton(onClick = { datePickerLlegada.show() }) {
                        Image(
                            painter = painterResource(R.drawable.calendario),
                            contentDescription = "Seleccionar fecha",
                            modifier = Modifier.size(32.dp)

                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = colorLetraCampos,
                    unfocusedLabelColor = colorLetraCampos,
                    focusedTextColor = colorLetraCampos,
                    unfocusedTextColor = colorLetraCampos
                )
            )
            Spacer(modifier = Modifier.height(8.dp))


            OutlinedTextField(
                value = fechaSalidaText,
                onValueChange = {},
                readOnly = true,
                label = { Text("Fecha de Salida", color = colorLetraCampos) },
                trailingIcon = {
                    IconButton(onClick = {
                        showDatePickerSalida()
                    }) {
                        Image(
                            painter = painterResource(R.drawable.calendario),
                            contentDescription = "Seleccionar fecha",
                            modifier = Modifier.size(32.dp)

                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = colorLetraCampos,
                    unfocusedLabelColor = colorLetraCampos,
                    focusedTextColor = colorLetraCampos,
                    unfocusedTextColor = colorLetraCampos
                )
            )
            Spacer(modifier = Modifier.height(16.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {


                Button(
                    onClick = {


                        val cantidadHuespedesInt = cantidadHuespedes.toIntOrNull()
                        val cantidadHabitacionesInt = cantidadHabitacionesText.toIntOrNull()

                        if (nombre.isBlank() || tipoHabitacion.isBlank() ||
                            cantidadHuespedes.isBlank() || cantidadHabitacionesText.isBlank() ||
                            fechaLlegadaDate == null || fechaSalidaDate == null
                        ) {
                            Toast.makeText(context, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        if (fechaLlegadaDate!!.time >= fechaSalidaDate!!.time) {
                            Toast.makeText(context, "La fecha de salida debe ser posterior a la de llegada.", Toast.LENGTH_LONG).show()
                            return@Button
                        }

                        if (cantidadHuespedesInt == null || cantidadHuespedesInt <= 0 ||
                            cantidadHabitacionesInt == null || cantidadHabitacionesInt <= 0
                        ) {
                            Toast.makeText(context, "Valores inválidos para huéspedes o habitaciones", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        val habitacionSeleccionada = habitaciones.firstOrNull { it.tipo == tipoHabitacion }

                        if (habitacionSeleccionada == null || cantidadHabitacionesInt > habitacionSeleccionada.cantidadDisponible) {
                            Toast.makeText(context, "No hay suficiente disponibilidad de la habitación ${tipoHabitacion}.", Toast.LENGTH_SHORT).show()
                            return@Button
                        }


                        val capacidadMaximaPorHabitacion = when (tipoHabitacion) {
                            "Colibrí" -> 4
                            "Luna de Miel" -> 3
                            else -> 999
                        }

                        val capacidadMaximaTotal = cantidadHabitacionesInt * capacidadMaximaPorHabitacion

                        if (cantidadHuespedesInt > capacidadMaximaTotal) {
                            Toast.makeText(
                                context,
                                "Máximo ${capacidadMaximaTotal} huéspedes para ${cantidadHabitacionesInt} habitación(es) ${tipoHabitacion}",
                                Toast.LENGTH_LONG
                            ).show()
                            return@Button
                        }


                        val total = vm.calcularTotal(tipoHabitacion, cantidadHabitacionesInt)
                        val reservation = Reservation(
                            id = 0,
                            idUsuario = idUsuario,
                            nombreEncargado = nombre,
                            tipoHabitacion = tipoHabitacion,
                            cantidadHuespedes = cantidadHuespedesInt,
                            cantidadHabitaciones = cantidadHabitacionesInt,
                            fechaLlegada = fechaLlegadaDate!!,
                            fechaSalida = fechaSalidaDate!!,
                            totalPagar = total,
                            tarjetaPago = null
                        )

                        vm.addReservation(reservation) { newReservationId ->
                            Toast.makeText(context, "Reserva creada, yendo a pago...", Toast.LENGTH_SHORT).show()
                            navController.navigate(AppScreen.Payment.crearRuta(idUsuario, newReservationId)) {
                                launchSingleTop = true
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50),
                        contentColor = Color.White
                    )
                ) {
                    Text("Método de Pago")
                }




                Button(
                    onClick = {
                        navController.navigate(AppScreen.Menu.crearRuta(idUsuario)) {
                            launchSingleTop = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorBotonRegresar,
                        contentColor = Color.White
                    )
                ) {
                    Text("Regresar")
                }
            }
        }
    }
}






