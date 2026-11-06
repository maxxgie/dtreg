package com.example.reg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.reg.ui.navigation.AppNavHost
import com.example.reg.ui.navigation.AppRoutes
import com.example.reg.ui.navigation.BottomNavigationBar
import com.example.reg.ui.theme.RegTheme
import com.example.reg.ui.viewmodel.RegistrationViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegTheme {
                RegistrationApp()
            }
        }
    }
}

@Composable
fun RegistrationApp() {
    val viewModel: RegistrationViewModel = viewModel()
    val navController = rememberNavController()
    var currentRoute by remember { mutableStateOf(AppRoutes.registration) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(navController, currentRoute)
        }
    ) { innerPadding ->
        AppNavHost(viewModel)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RegTheme {
        Text("Hello Android!")
    }
}
