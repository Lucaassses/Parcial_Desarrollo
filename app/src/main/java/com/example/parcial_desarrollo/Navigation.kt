package com.example.parcial_desarrollo

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.parcial_desarrollo.Screen.ClientesScreen
import com.example.parcial_desarrollo.Screen.MainScreen
import com.example.parcial_desarrollo.Screen.ProductosScreen
import com.example.parcial_desarrollo.Screen.VentasScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("productos") { ProductosScreen(navController) }
        composable("clientes") { ClientesScreen(navController) }
        composable("ventas") { VentasScreen(navController) }
    }
}