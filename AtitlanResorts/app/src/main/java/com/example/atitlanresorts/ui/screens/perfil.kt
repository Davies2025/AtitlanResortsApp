package com.example.atitlanresorts.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.atitlanresorts.R
import com.example.atitlanresorts.data.entities.User
import com.example.atitlanresorts.ui.navigation.AppScreen
import com.example.atitlanresorts.ui.viewmodels.ReservationViewModel


@Composable
fun PerfilScreen(
    idUsuario: Int,
    navController: NavController,
    reservationViewModel: ReservationViewModel? = null
) {
    val vm = reservationViewModel ?: viewModel<ReservationViewModel>()

    // OBTENIENDO DATOS DEL VIEWMODEL
    val userProfile by vm.getUserProfile(idUsuario).collectAsState(initial = null)
    val reservationCount by vm.countReservationsByUserId(idUsuario).collectAsState(initial = 0)


    val colorHeader = Color(0xFF5488DC)
    val colorInfoBlock = colorHeader.copy(alpha = 0.5f)

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
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.height(60.dp))


            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(colorHeader, shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "DATOS DEL USUARIO",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            }


            if (userProfile == null) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(200.dp)
                        .background(colorInfoBlock, shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            } else {

                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .background(colorInfoBlock, shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp))
                        .padding(20.dp),
                    horizontalAlignment = Alignment.Start
                ) {

                    DataField(label = "Nombre del usuario:", value = userProfile!!.fullName, labelColor = Color.White, valueColor = Color.White)


                    DataField(label = "Correo electrónico:", value = userProfile!!.gmail, labelColor = Color.White, valueColor = Color.White)


                    DataField(label = "Teléfono:", value = userProfile!!.phone, labelColor = Color.White, valueColor = Color.White)

                    // Cantidad de reservaciones
                    DataField(label = "Cantidad de reservaciones:", value = reservationCount.toString(), labelColor = Color.White, valueColor = Color.White)

                    // La contraseña no se muestra por seguridad
                }
            }

            Spacer(modifier = Modifier.height(40.dp))


            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = colorHeader),
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text("Regresar", color = Color.White, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}


@Composable
fun DataField(label: String, value: String, labelColor: Color, valueColor: Color) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = label,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            color = labelColor
        )
        Text(
            text = value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            color = valueColor,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}