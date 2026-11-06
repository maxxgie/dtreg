package com.example.reg.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.reg.ui.screens.HostelInfoScreen
import com.example.reg.ui.screens.LibraryRequestScreen
import com.example.reg.ui.screens.PaymentScreen
import com.example.reg.ui.screens.RegistrationScreen
import com.example.reg.ui.screens.SummaryScreen
import com.example.reg.ui.viewmodel.RegistrationViewModel

object AppRoutes {
    const val registration = "registration"
    const val library = "library"
    const val hostel = "hostel"
    const val payment = "payment"
    const val summary = "summary"
}

@Composable
fun AppNavHost(viewModel: RegistrationViewModel = viewModel()) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppRoutes.registration) {
        composable(AppRoutes.registration) {
            RegistrationScreen(viewModel)
        }
        composable(AppRoutes.library) {
            LibraryRequestScreen(viewModel)
        }
        composable(AppRoutes.hostel) {
            HostelInfoScreen(viewModel)
        }
        composable(AppRoutes.payment) {
            PaymentScreen(viewModel)
        }
        composable(AppRoutes.summary) {
            SummaryScreen(viewModel)
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    currentRoute: String
) {
    val items = listOf(
        AppRoutes.registration to "Registration",
        AppRoutes.library to "Library",
        AppRoutes.hostel to "Hostel",
        AppRoutes.payment to "Payment",
        AppRoutes.summary to "Summary"
    )

    NavigationBar {
        items.forEach { (route, label) ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = when (route) {
                            AppRoutes.registration -> Icons.Default.Person
                            AppRoutes.library -> Icons.Default.Book
                            AppRoutes.hostel -> Icons.Default.Home
                            AppRoutes.payment -> Icons.Default.Payment
                            AppRoutes.summary -> Icons.Default.Description
                            else -> Icons.Default.Info
                        },
                        contentDescription = label
                    )
                },
                label = { Text(label) },
                selected = currentRoute == route,
                onClick = {
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}
