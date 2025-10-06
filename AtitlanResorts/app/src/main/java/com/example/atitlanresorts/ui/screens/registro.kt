package com.example.atitlanresorts.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.atitlanresorts.R
import com.example.atitlanresorts.data.entities.User
import com.example.atitlanresorts.ui.viewmodels.UserViewModel
import com.example.atitlanresorts.ui.viewmodels.RegisterResult
import kotlinx.coroutines.launch

@Composable
fun RegistroScreen(
    userViewModel: UserViewModel = viewModel(),
    onRegistroExitoso: () -> Unit = {}
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmarPassword by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.fondo_reservacion),
                contentDescription = "Fondo Lago Atitlán",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
            ) {
                Text(
                    text = "¡HOLA!  Nuevo Usuario",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Esta información será requerida al momento de hacer una reservación.",
                    color = Color.White,
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                InputField(Icons.Filled.Email, "Nombre Completo", value = fullName, onValueChange = { fullName = it })
                InputField(Icons.Filled.Email, "Correo electrónico", keyboardType = KeyboardType.Email, value = email, onValueChange = { email = it })
                InputField(Icons.Filled.Lock, "Contraseña", isPassword = true, value = password, onValueChange = { password = it })
                InputField(Icons.Filled.Lock, "Confirmar Contraseña", isPassword = true, value = confirmarPassword, onValueChange = { confirmarPassword = it })
                InputField(Icons.Filled.Phone, "Teléfono", keyboardType = KeyboardType.Phone, value = telefono, onValueChange = { telefono = it })

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        when {



                            fullName.isBlank() || email.isBlank() || password.isBlank() || confirmarPassword.isBlank() || telefono.isBlank() -> {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Por favor, completa todos los campos")
                                }
                            }

                            password.length < 8 -> {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("La contraseña debe tener al menos 8 caracteres")
                                }
                            }

                            password != confirmarPassword -> {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Las contraseñas no coinciden")
                                }
                            }

                            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Formato de correo electrónico no válido")
                                }
                            }


                            else -> {
                                val newUser = User(
                                    fullName = fullName,
                                    gmail = email,
                                    password = password,
                                    phone = telefono
                                )


                                userViewModel.registerUser(newUser) { result ->
                                    coroutineScope.launch {
                                        when (result) {
                                            RegisterResult.SUCCESS -> {

                                                onRegistroExitoso()
                                            }
                                            RegisterResult.EMAIL_ALREADY_EXISTS -> {

                                                snackbarHostState.showSnackbar("El correo ya está registrado. Por favor, usa otro correo o inicia sesión.")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A4C9C))
                ) {
                    Text("GUARDAR", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun InputField(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "Icono $placeholder",
                tint = Color.Gray
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        textStyle = TextStyle(color = Color.Black),
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.LightGray,
            focusedBorderColor = Color.White,
            cursorColor = Color.White,
            focusedLabelColor = Color.White
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewRegistroScreen() {
    RegistroScreen()
}

















