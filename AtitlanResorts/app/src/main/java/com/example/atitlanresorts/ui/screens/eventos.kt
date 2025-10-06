package com.example.atitlanresorts.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.atitlanresorts.R

val HeaderColor = Color(0xFF05236E)
val TitleGold = Color(0xFFE5C069)
val EventBlue = Color(0xFF05236E)

@Composable
fun EventosScreen(
    navController: NavController
) {
    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.lago_atitlan),
            contentDescription = "Fondo del Hotel",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.6f
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(HeaderColor),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logohotel),
                        contentDescription = "Logo Atitlán Resorts",
                        modifier = Modifier
                            .size(100.dp)
                            .padding(end = 12.dp)
                    )
                    Text(
                        text = "EVENTOS",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        color = TitleGold
                    )
                }
            }


            Text(
                text = "Disfruta de muchos eventos entre los cuales destacan los siguientes:",
                fontSize = 18.sp,
                fontStyle = FontStyle.Italic,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))


            EventSection(
                title = "PISCINA AL AIRE LIBRE",
                imageResId = R.drawable.piscina
            )
            Spacer(modifier = Modifier.height(16.dp))

            EventSection(
                title = "PASEO EN JET SKI",
                imageResId = R.drawable.motoacuatica
            )
            Spacer(modifier = Modifier.height(16.dp))

            EventSection(
                title = "CAMINATA AL LAGO ATITLAN",
                imageResId = R.drawable.caminar
            )
            Spacer(modifier = Modifier.height(30.dp))


            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5488DC))
            ) {
                Text(
                    text = "Regresar",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun EventSection(
    title: String,
    imageResId: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title,
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            color = EventBlue,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = title,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )
    }
}



