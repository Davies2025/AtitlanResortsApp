package com.example.atitlanresorts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.atitlanresorts.ui.navigation.AtitlanResortsNavHost
import com.example.atitlanresorts.ui.navigation.AppScreen
import com.example.atitlanresorts.ui.theme.AtitlanResortsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AtitlanResortsTheme {
                val navController = rememberNavController()

                AtitlanResortsNavHost(
                    navHostController = navController,
                    startDestination = AppScreen.Login.ruta
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    androidx.compose.material3.Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AtitlanResortsTheme {
        Greeting("Android")
    }
}


