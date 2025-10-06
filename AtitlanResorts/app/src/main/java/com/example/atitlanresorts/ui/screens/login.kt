package com.example.atitlanresorts.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.atitlanresorts.R
import com.example.atitlanresorts.ui.navigation.AppScreen
import com.example.atitlanresorts.ui.viewmodels.UserViewModel
import kotlinx.coroutines.launch
import com.example.atitlanresorts.ui.theme.PrimaryButtonColor


@Composable
fun LoginScreen(navController: NavController, userViewModel: UserViewModel = viewModel()) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val loginResult by userViewModel.loginResult.collectAsState()


    LaunchedEffect(loginResult) {
        loginResult?.let { user ->
            if (user.id > 0) {
                navController.navigate(AppScreen.Menu.crearRuta(user.id)) {
                    popUpTo(AppScreen.Login.ruta) { inclusive = true }
                }
                userViewModel.clearLoginResult()
            }
        } ?: run {

            if (email.isNotBlank() || password.isNotBlank()) {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Correo o Contraseña incorrectos")
                }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            userViewModel.clearLoginResult()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            Image(
                painter = painterResource(id = R.drawable.lago_atitlan),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {


                Image(
                    painter = painterResource(id = R.drawable.logohotel),
                    contentDescription = "Logo de Atitlan Resorts",
                    modifier = Modifier.size(230.dp)
                )


                Text(
                    text = "¡BIENVENIDOS!",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)
                )


                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo Electrónico", color = Color.White.copy(alpha = 0.7f)) },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_email),
                            contentDescription = "Icono correo",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.LightGray,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White.copy(alpha = 0.7f)
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))


                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña", color = Color.White.copy(alpha = 0.7f)) },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_password),
                            contentDescription = "Icono contraseña",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.LightGray,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.White.copy(alpha = 0.7f)
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))


                Button(
                    onClick = {
                        if (email.isBlank() || password.isBlank()) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Por favor, introduce tu correo y contraseña")
                            }
                        } else {
                            userViewModel.login(email, password)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryButtonColor)
                ) {
                    Text("INICIAR SESIÓN", color = Color.White, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(10.dp))


                Button(
                    onClick = { navController.navigate(AppScreen.Registro.ruta) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryButtonColor)
                ) {
                    Text("REGISTRO", color = Color.White, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "ACERCA DE NOSOTROS",
                    color = Color.White,
                    modifier = Modifier.clickable {
                        navController.navigate(AppScreen.About.ruta)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    val navController = rememberNavController()
    LoginScreen(navController = navController, userViewModel = viewModel())
}

