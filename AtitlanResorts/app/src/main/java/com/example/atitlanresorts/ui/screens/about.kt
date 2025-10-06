package com.example.atitlanresorts.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.atitlanresorts.R
import com.example.atitlanresorts.ui.navigation.AppScreen
import com.example.atitlanresorts.ui.theme.AtitlanResortsTheme
import com.example.atitlanresorts.ui.theme.PrimaryButtonColor

val gold = Color(0xFFFFD700)

@Composable
fun AboutScreen(navController: NavController, modifier: Modifier = Modifier) {


    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            Image(
                painter = painterResource(id = R.drawable.lago_atitlan),
                contentDescription = "Fondo Lago Atitlán",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(PrimaryButtonColor)
                        .height(80.dp)
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.logohotel),
                            contentDescription = "Logo Atitlán Resorts",
                            modifier = Modifier.size(100.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Atitlán Resorts",
                            style = TextStyle(
                                fontSize = 35.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))


                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(horizontal = 16.dp)
                        .background(Color.White.copy(alpha = 0.3f), shape = RoundedCornerShape(12.dp))
                        .padding(24.dp)
                        .weight(1f),
                    contentAlignment = Alignment.TopStart
                ) {
                    Text(
                        text = "Un hotel de estilo rústico ubicado junto al lago de Atitlán. " +
                                "Cuenta con amplios jardines, vistas panorámicas, piscina al aire libre, " +
                                "bañera de hidromasaje y un jardín botánico. Sus habitaciones son elegantes, " +
                                "con muebles de hierro forjado, TV por cable, vistas al lago y baño privado. " +
                                "El restaurante del hotel ofrece cocina internacional y abre de 07:00 a 22:00.",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 25.sp,
                            lineHeight = 30.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))


                Button(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp)

                        .padding(bottom = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryButtonColor)
                ) {
                    Text("Regresar", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAboutScreen() {
    AtitlanResortsTheme {
        val navController = rememberNavController()
        AboutScreen(navController)
    }
}









