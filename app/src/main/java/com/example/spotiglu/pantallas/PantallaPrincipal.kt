package com.example.spotiglu.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.spotiglu.Cancion
import com.example.spotiglu.Lista
import com.example.spotiglu.R
import com.example.spotiglu.albumes
import com.example.spotiglu.cancionLista
import com.example.spotiglu.listaTodas
import com.example.spotiglu.navegacion.GrafoNavegacion
import com.example.spotiglu.playlist
import com.example.spotiglu.viewModel.exoplayerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal(navController: NavController, exoplayerViewModel: exoplayerViewModel) {
    val lista = cancionLista()
    val albumes = albumes()
    val playlist = playlist()
    var carga = exoplayerViewModel.carga.collectAsState().value
    if (carga) {
        carga()
    }
    Scaffold(
        topBar = { TopAppBar(
            title = { Text(text = stringResource(id = R.string.app_name)) },
        )
        },
        bottomBar = {
            BottomAppBar(/*containerColor = colorResource(id = R.color.azulClaro)*/) {
                Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
                    IconButton(onClick = { navController.navigate("PantallaPrincipal") }
                    ) {
                        Icon(painter = painterResource(id = R.drawable.home), contentDescription = null)
                    }
                    IconButton(onClick = { navController.navigate("PantallaBuscador");
                        exoplayerViewModel.cambiarLista(listaTodas()) }
                    ) {
                        Icon(painter = painterResource(id = R.drawable.buscar), contentDescription = null, modifier = Modifier.size(30.dp))
                    }
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Text(text = "Canciones populares")
            Row() {
                crearLazyRow(lista = lista, navController = navController, exoplayerViewModel)
            }
            Text(text = "Albumes para ti")
            Row {
                crearLazyRowListas(lista = albumes, navController, exoplayerViewModel)
            }
            Text(text = "Playlist para ti")
            Row {
                crearLazyRowListas(lista = playlist, navController, exoplayerViewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun crearLazyRow(lista: List<Cancion>, navController: NavController, exoplayerViewModel: exoplayerViewModel) {
    LazyRow() {
        items(items = lista, itemContent = {item -> Card(onClick = {
            navController.navigate("ReproductorPantalla"); exoplayerViewModel.cambiarValorCancion(item.id); exoplayerViewModel.cambiarLista(lista)},
            modifier = Modifier
                .size(165.dp)
                .padding(10.dp)
        ) {
            Box {
                Image(painter = painterResource(id = item.imagen), contentDescription = "")
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black), startY = 300f
                        )
                    ))
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(text = "${item.titulo} - ${item.autor}", style = TextStyle(color = Color.White, fontSize = 14.sp))
                }
            }
        }
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun crearLazyRowListas(lista: ArrayList<Lista>, navController: NavController, exoplayerViewModel: exoplayerViewModel) {
    LazyRow() {
        items(items = lista, itemContent = {item -> Card(onClick = {
            exoplayerViewModel.cambiarCarga()
            navController.navigate("PantallaListas"); exoplayerViewModel.cambiarLista(
            item.canciones); exoplayerViewModel.cambiarAlbumNumero(item.id, item.tipo); exoplayerViewModel.cambiarLista(item.canciones);}, modifier = Modifier
            .size(165.dp)
            .padding(10.dp)
        ) {
            Box {
                Image(painter = painterResource(id = item.imagen), contentDescription = "")
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black), startY = 300f
                        )
                    ))
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    val titulo = if (item.tipo == "Album") "${item.titulo} - ${item.autor}" else "${item.titulo}"
                    Text(text = titulo, style = TextStyle(color = Color.White, fontSize = 14.sp))
                }
            }
        }
        })
    }
}

@Composable
fun carga() {
    Dialog(onDismissRequest = { /*TODO*/ }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Cargando")
        }
    }
}
