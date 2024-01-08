package com.example.spotiglu.navegacion

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.spotiglu.pantallas.PantallaBuscador
import com.example.spotiglu.pantallas.PantallaListas
import com.example.spotiglu.pantallas.PantallaPrincipal
import com.example.spotiglu.pantallas.reproductor
import com.example.spotiglu.viewModel.exoplayerViewModel

@Composable
fun GrafoNavegacion(context: Context) {
    val navController = rememberNavController()
    val exoplayerViewModel: exoplayerViewModel = viewModel()
    NavHost(navController = navController, startDestination = Navegacion.PantallaPrincipal.ruta) {
        composable(route = Navegacion.ReproductorPantalla.ruta) {
            reproductor(
                navController = navController,
                exoPlayerViewModel = exoplayerViewModel
            )
        }
        composable(route = Navegacion.PantallaPrincipal.ruta) {
            PantallaPrincipal(
                navController = navController,
                exoplayerViewModel = exoplayerViewModel
            )
        }
        composable(route = Navegacion.PantallaListas.ruta) {
            PantallaListas(
                navController = navController,
                exoPlayerViewModel = exoplayerViewModel,
                context = context
            )
        }
        composable(route = Navegacion.PantallaBuscador.ruta) {
            PantallaBuscador(
                context = context,
                navController = navController,
                exoPlayerViewModel = exoplayerViewModel)
        }
    }
}
