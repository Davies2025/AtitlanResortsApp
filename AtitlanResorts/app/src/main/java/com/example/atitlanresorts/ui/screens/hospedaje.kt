package com.example.atitlanresorts.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.atitlanresorts.R

@Composable
fun HospedajeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {


        Image(
            painter = painterResource(id = R.drawable.lago_atitlan),
            contentDescription = "Fondo Lago",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xAA000000))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logohotel),
                    contentDescription = "Logo",
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "HOSPEDAJE",
                        fontWeight = FontWeight.Bold,
                        fontSize = 26.sp,
                        color = Color(0xFFFFC107)
                    )
                    Text(
                        text = "Naturaleza y confort",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
            }


            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                HospedajeCard(
                    titulo = "Suite Luna de Miel",
                    descripcion = "Nuestra tienda de glamping más lujosa: sin ventanas, solo cortinas y vistas espectaculares al lago, volcanes y estrellas. Cama king, baño privado con ducha tipo lluvia y terraza amplia y 45 m² para relajarte. Precio: $200 Dólares y Capacidad Máxima de 3 Personas",
                    imagen = R.drawable.colibri
                )

                HospedajeCard(
                    titulo = "TIENDA \"TZ'UNÚN\" (Colibrí)",
                    descripcion = "La exclusiva carpa doble que también se puede usar como habitación individual, te ofrece todo lo que necesitas para unas vacaciones relajantes y placenteras en el lago de Atitlán. Precio: $100 Dólares y Capacidad Máxima de 4 Personas",
                    imagen = R.drawable.colibri_hospedaje
                )
            }


            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(top = 12.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5488DC))
            ) {
                Text("Regresar", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun HospedajeCard(titulo: String, descripcion: String, imagen: Int) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0047AB).copy(alpha = 0.8f)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            Image(
                painter = painterResource(id = imagen),
                contentDescription = titulo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = titulo,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 6.dp)
            )

            Text(
                text = descripcion,
                fontSize = 14.sp,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHospedajeScreen() {
    val navController = rememberNavController()
    HospedajeScreen(navController)
}

