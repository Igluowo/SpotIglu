package com.example.spotiglu

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.spotiglu.ui.theme.SpotIgluTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spotiglu.viewModel.exoplayerViewModel
import com.example.spotiglu.viewModel.viewModelCanciones
import kotlinx.coroutines.flow.forEach

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpotIgluTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = { TopAppBar(
                            title = { Text(text = stringResource(id = R.string.app_name)) },
                            navigationIcon = { IconButton(onClick = { }) {
                                Icon(painter = painterResource(id = R.drawable.atras), contentDescription = null,
                                    Modifier.size(30.dp))
                            }},
                        )},
                        bottomBar = {
                            BottomAppBar(/*containerColor = colorResource(id = R.color.azulClaro)*/) {
                                Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
                                    IconButton(onClick = { /*TODO*/ }) {
                                        Icon(painter = painterResource(id = R.drawable.home), contentDescription = null)
                                    }
                                    IconButton(onClick = { /*TODO*/ }) {
                                        Icon(painter = painterResource(id = R.drawable.buscar), contentDescription = null, modifier = Modifier.size(30.dp))
                                    }
                                }
                            }
                        }
                    ) { padding ->
                        ReproductorPantalla(modifier = Modifier.padding(padding))
                    }
                }
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun ReproductorPantalla(modifier: Modifier) {
        val viewModelCanciones : viewModelCanciones = viewModel()
        val contexto = LocalContext.current
        val exoPlayerViewModel: exoplayerViewModel = viewModel()
        val listaCanciones = cancionLista()
        val cancion = viewModelCanciones.cancion.collectAsState().value
        var progreso = exoPlayerViewModel.progreso.collectAsState().value.toFloat()
        println("numero de cancion $cancion")
        Column (
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
            Slider(value = progreso, onValueChange = { progreso = it; exoPlayerViewModel.seekTo(it.toLong()) }, modifier = Modifier.fillMaxWidth()
                , valueRange = 0f .. exoPlayerViewModel.duracion.value.toFloat())
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
                                                              }, color = viewModelCanciones.colorAleatorio.collectAsState().value)
                //Boton previous
                botonesReproductor(id = R.drawable.skip_previous, {
                    if (viewModelCanciones.bucle.value){
                        exoPlayerViewModel.cambiarCancion(contexto, listaCanciones.get(cancion).audio)
                    }else if (viewModelCanciones.aleatorio.value){
                        //Hace un historial de canciones para que te lleve a tus canciones anteriores.
                        if (viewModelCanciones.listaAnteriores.value.size < 1) {
                                Toast.makeText(contexto, "Ha llegado a la última canción anterior", Toast.LENGTH_SHORT).show()
                        }else{
                            val cancionAnterior = viewModelCanciones.listaAnteriores.value.get(viewModelCanciones.listaAnteriores.value.size - 1)
                            viewModelCanciones.ponerCancion(cancionAnterior)
                            exoPlayerViewModel.cambiarCancion(contexto, listaCanciones.get(cancionAnterior).audio)
                            viewModelCanciones.borrarHistorial()
                            viewModelCanciones.listaAnteriores.value.forEach { e -> println("anteriores: $e") }
                        }
                    }else if(cancion < 1) {
                        viewModelCanciones.agregarHistorial()
                        viewModelCanciones.cancionMin()
                        exoPlayerViewModel.cambiarCancion(contexto, listaCanciones.get(cancion + (listaCanciones.size - 1)).audio)
                    }else{
                        viewModelCanciones.agregarHistorial()
                        viewModelCanciones.restarCancion()
                        exoPlayerViewModel.cambiarCancion(contexto, listaCanciones.get(cancion - 1).audio)
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
                    if (viewModelCanciones.bucle.value){
                        exoPlayerViewModel.cambiarCancion(contexto, listaCanciones.get(cancion).audio)
                    }else if (viewModelCanciones.aleatorio.value){
                        viewModelCanciones.agregarHistorial()
                        viewModelCanciones.cancionAleatoria()
                        exoPlayerViewModel.cambiarCancion(contexto, listaCanciones.get(viewModelCanciones.cancion.value).audio)
                    }else if (cancion >= listaCanciones.size - 1) {
                        viewModelCanciones.agregarHistorial()
                        viewModelCanciones.cancionMax()
                        exoPlayerViewModel.cambiarCancion(contexto, listaCanciones.get(cancion - 4).audio)
                    }else{
                        viewModelCanciones.agregarHistorial()
                        viewModelCanciones.sumarCancion()
                        exoPlayerViewModel.cambiarCancion(contexto, listaCanciones.get(cancion + 1).audio)
                    }
                    viewModelCanciones.cambiarPlay(true)
                }, color = viewModelCanciones.colorDefault.value)
                //Boton bucle
                botonesReproductor(id = R.drawable.repetir, {
                    viewModelCanciones.cambiarColor("Bucle")
                    viewModelCanciones.cambiarBucle()
                } , viewModelCanciones.colorBucle.collectAsState().value)
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
}