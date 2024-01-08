package com.example.spotiglu.pantallas

import android.content.Context
import android.graphics.drawable.shapes.Shape
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.spotiglu.R
import com.example.spotiglu.listaTodas
import com.example.spotiglu.viewModel.exoplayerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaListas(/*modifier: Modifier,*/ navController: NavController, exoPlayerViewModel: exoplayerViewModel, context: Context) {
    val listaCanciones = exoPlayerViewModel.lista.collectAsState().value
    val album = exoPlayerViewModel.album.collectAsState().value
    LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher?.addCallback(object :
        OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            navController.popBackStack()
            exoPlayerViewModel.cambiarCarga()
        }
    })
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
                    IconButton(onClick = { navController.navigate("PantallaPrincipal") }) {
                        Icon(painter = painterResource(id = R.drawable.home), contentDescription = null)
                    }
                    IconButton(onClick = { navController.navigate("PantallaBuscador");
                        exoPlayerViewModel.cambiarLista(listaTodas()) }) {
                        Icon(painter = painterResource(id = R.drawable.buscar), contentDescription = null, modifier = Modifier.size(30.dp))
                    }
                }
            }
        }
    ) { padding -> Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = album.imagen),
                        contentDescription = "", modifier = Modifier.size(200.dp)
                    )
                    val titulo =
                        if (album.tipo == "Album") "${album.titulo} - ${album.autor}" else "${album.titulo}"
                    Text(text = titulo, modifier = Modifier.padding(5.dp))
                    Button(onClick = { navController.navigate("ReproductorPantalla"); exoPlayerViewModel.cancionAleatoria() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.aleatorio),
                            contentDescription = "",
                            modifier = Modifier.size(20.dp)
                        )
                        Text(text = "  Modo aleatorio")
                    }
                }
            }
            Box(
                Modifier
                    .size(width = 400.dp, height = 225.dp)
                    .fillMaxWidth()
            ) {
                LazyColumn(Modifier.fillMaxWidth()) {
                    items(items = listaCanciones) { item ->
                        Card(
                            onClick = {
                                navController.navigate("ReproductorPantalla"); exoPlayerViewModel.cambiarValorCancion(
                                item.id
                            )
                            },
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth(),
                            shape = RectangleShape,
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Row {
                                if (album.tipo != "Album") {
                                    Image(
                                        painter = painterResource(id = item.imagen),
                                        contentDescription = "",
                                        modifier = Modifier.size(65.dp)
                                    )
                                }
                                Column(Modifier.padding(5.dp)) {
                                    Text(text = item.titulo)
                                    Text(text = item.album)
                                }
                            }
                        }
                    }
                }
            }
            Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (album.tipo == "Album") {
                        Text(text = "${album.anyo}")
                    }
                    val duracion = exoPlayerViewModel.obtenerDuracionLista(context, album.canciones)
                    Text(text = "${album.canciones.size} Canciones - $duracion")
                }
            }
        }
    }
}