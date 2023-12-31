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
import com.example.spotiglu.pantallas.PantallaListas
import com.example.spotiglu.pantallas.PantallaPrincipal
import com.example.spotiglu.pantallas.reproductor
import com.example.spotiglu.viewModel.exoplayerViewModel

@Composable
fun GrafoNavegacion(context: Context, modifier: Modifier) {
    val navController = rememberNavController()
    val exoplayerViewModel: exoplayerViewModel = viewModel()
    NavHost(navController = navController, startDestination = Navegacion.PantallaPrincipal.ruta) {
        composable(route = Navegacion.ReproductorPantalla.ruta) {
            reproductor(
                modifier = modifier,
                navController = navController,
                exoplayerViewModel1 = exoplayerViewModel
            )
        }
        composable(route = Navegacion.PantallaPrincipal.ruta) {
            PantallaPrincipal(
                modifier = modifier,
                navController = navController,
                exoplayerViewModel = exoplayerViewModel
            )
        }
        composable(route = Navegacion.PantallaListas.ruta) {
            PantallaListas(
                modifier = modifier,
                navController = navController,
                exoPlayerViewModel = exoplayerViewModel,
                context = context
            )
        }
    }
}
