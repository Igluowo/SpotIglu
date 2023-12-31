package com.example.spotiglu.pantallas

import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.spotiglu.R
import com.example.spotiglu.cancionLista
import com.example.spotiglu.viewModel.exoplayerViewModel
import com.example.spotiglu.viewModel.viewModelCanciones

@Composable
fun reproductor(
    modifier: Modifier,
    navController: NavController,
    exoplayerViewModel1: exoplayerViewModel
) {
    val viewModelCanciones: viewModelCanciones = viewModel()
    val contexto = LocalContext.current
    val exoPlayerViewModel = exoplayerViewModel1
    val listaCanciones = exoPlayerViewModel.lista.collectAsState().value
    val cancion = exoPlayerViewModel.cancion.collectAsState().value
    var progreso = exoPlayerViewModel.progreso.collectAsState().value.toFloat()
    LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher?.addCallback(object :
        OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            exoPlayerViewModel.quitarCancion()
            navController.popBackStack()
        }
    })
    println("numero de cancion $cancion")
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        //Imagen
        Image(
            painter = painterResource(id = listaCanciones.get(cancion).imagen),
            contentDescription = null,
            modifier = Modifier
                .size(255.dp)
                .padding(0.dp, 20.dp)
        )
        //Texto de la cancion
        Text(text = listaCanciones.get(cancion).titulo + " - " + listaCanciones.get(cancion).autor)
        //Slider
        Slider(
            value = progreso,
            onValueChange = { progreso = it; exoPlayerViewModel.seekTo(it.toLong()) },
            modifier = Modifier.fillMaxWidth(),
            valueRange = 0f..exoPlayerViewModel.duracion.value.toFloat()
        )
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(text = exoPlayerViewModel.progresoMinutos.collectAsState().value)
            Text(text = exoPlayerViewModel.duracionMinutos.collectAsState().value)
        }
        Spacer(modifier = Modifier.height(45.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            LaunchedEffect(Unit) {
                exoPlayerViewModel.crearExoPlayer(contexto)
                exoPlayerViewModel.reproducirCancion(contexto, listaCanciones.get(cancion).audio)
            }
            //Boton aleatorio
            botonesReproductor(id = R.drawable.aleatorio, {
                viewModelCanciones.cambiarColor("Aleatorio")
                viewModelCanciones.cambiarAleatorio()
                exoPlayerViewModel.cambiarAleatorio()
            }, color = viewModelCanciones.colorAleatorio.collectAsState().value)
            //Boton previous
            botonesReproductor(id = R.drawable.skip_previous, {
                if (viewModelCanciones.bucle.value) {
                    exoPlayerViewModel.cambiarCancion(contexto, listaCanciones.get(cancion).audio)
                } else if (viewModelCanciones.aleatorio.value) {
                    //Hace un historial de canciones para que te lleve a tus canciones anteriores.
                    if (exoPlayerViewModel.listaAnteriores.value.size < 1) {
                        Toast.makeText(
                            contexto,
                            "Ha llegado a la última canción anterior",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val cancionAnterior =
                            exoPlayerViewModel.listaAnteriores.value.get(exoPlayerViewModel.listaAnteriores.value.size - 1)
                        exoPlayerViewModel.ponerCancion(cancionAnterior)
                        exoPlayerViewModel.cambiarCancion(
                            contexto,
                            listaCanciones.get(cancionAnterior).audio
                        )
                        exoPlayerViewModel.borrarHistorial()
                        exoPlayerViewModel.listaAnteriores.value.forEach { e -> println("anteriores: $e") }
                    }
                } else if (cancion < 1) {
                    exoPlayerViewModel.agregarHistorial()
                    exoPlayerViewModel.cancionMin()
                    exoPlayerViewModel.cambiarCancion(
                        contexto,
                        listaCanciones.get(cancion + (listaCanciones.size - 1)).audio
                    )
                } else {
                    exoPlayerViewModel.agregarHistorial()
                    exoPlayerViewModel.restarCancion()
                    exoPlayerViewModel.cambiarCancion(
                        contexto,
                        listaCanciones.get(cancion - 1).audio
                    )
                }
                viewModelCanciones.cambiarPlay(true)
            }, color = viewModelCanciones.colorDefault.value)
            //Boton play
            botonesReproductor(id = viewModelCanciones.botonPlay.collectAsState().value, {
                viewModelCanciones.cambiarPlay(false)
                exoPlayerViewModel.pausarCancion()
            }, color = viewModelCanciones.colorDefault.value)
            //Boton next
            botonesReproductor(id = R.drawable.skip_next, {
                if (viewModelCanciones.bucle.value) {
                    exoPlayerViewModel.cambiarCancion(contexto, listaCanciones.get(cancion).audio)
                } else if (viewModelCanciones.aleatorio.value) {
                    exoPlayerViewModel.agregarHistorial()
                    exoPlayerViewModel.cancionAleatoria()
                    exoPlayerViewModel.cambiarCancion(
                        contexto,
                        listaCanciones.get(exoPlayerViewModel.cancion.value).audio
                    )
                } else if (cancion >= listaCanciones.size - 1) {
                    exoPlayerViewModel.agregarHistorial()
                    exoPlayerViewModel.cancionMax()
                    exoPlayerViewModel.cambiarCancion(
                        contexto,
                        listaCanciones.get(cancion - 4).audio
                    )
                } else {
                    exoPlayerViewModel.agregarHistorial()
                    exoPlayerViewModel.sumarCancion()
                    exoPlayerViewModel.cambiarCancion(
                        contexto,
                        listaCanciones.get(cancion + 1).audio
                    )
                }
                viewModelCanciones.cambiarPlay(true)
            }, color = viewModelCanciones.colorDefault.value)
            //Boton bucle
            botonesReproductor(id = R.drawable.repetir, {
                viewModelCanciones.cambiarColor("Bucle")
                viewModelCanciones.cambiarBucle()
                exoPlayerViewModel.cambiarBucle()
            }, viewModelCanciones.colorBucle.collectAsState().value)
        }
    }
}

@Composable
fun botonesReproductor(id : Int, accion: () -> Unit, color: Int) {
    IconButton(onClick = accion) {
        Icon(
            painter = painterResource(id = id),
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            tint = colorResource(id = color)
        )
    }
}