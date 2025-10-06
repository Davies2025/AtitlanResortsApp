package com.example.atitlanresorts.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.atitlanresorts.R

val PrimaryButtonColor = Color(0xFF05236E)


@Composable
fun PicnicScreen(navController: NavController) {
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
                .verticalScroll(rememberScrollState())
                .padding(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PrimaryButtonColor)
                    .height(70.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logohotel),
                        contentDescription = "Logo Atitlán Resorts",
                        modifier = Modifier.size(135.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "PICNIC",
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Bold,
                        color = TitleGold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "RELÁJATE Y DISFRUTA",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryButtonColor,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "EL PICNIC es un lugar maravilloso que te invita a disfrutar de una plática con el chef en la cocina abierta y el bar, que ofrece bebidas refrescantes y buena comida. El gran jardín, la fogata, un hermoso cine al aire, nuestro lounge con juegos de mesa y una variedad de frutas.",
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Image(
                    painter = painterResource(id = R.drawable.picnic),
                    contentDescription = "Picnic",
                    modifier = Modifier
                        .weight(1f)
                        .height(160.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .padding(end = 4.dp),
                    contentScale = ContentScale.Crop
                )
                Image(
                    painter = painterResource(id = R.drawable.comida),
                    contentDescription = "Comida fresca",
                    modifier = Modifier
                        .weight(1f)
                        .height(160.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .padding(start = 4.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Image(
                painter = painterResource(id = R.drawable.chef),
                contentDescription = "Chef preparando comida",
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5488DC))
            ) {
                Text("Regresar", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PicnicScreenPreview() {
    val navController = rememberNavController()
    PicnicScreen(navController)
}
