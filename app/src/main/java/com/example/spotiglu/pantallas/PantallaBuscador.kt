package com.example.spotiglu.pantallas

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.spotiglu.R
import com.example.spotiglu.listaTodas
import com.example.spotiglu.viewModel.exoplayerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaBuscador(context: Context, navController: NavController, exoPlayerViewModel: exoplayerViewModel) {
    val listaCanciones = exoPlayerViewModel.lista.collectAsState().value
    var fieldTexto by remember { mutableStateOf("")}
    Scaffold(
        topBar = { TopAppBar(
            title = { IconButton(onClick = { navController.navigate("PantallaPrincipal")},
                content = { Icon(
                    painter = painterResource(id = R.drawable.atras), contentDescription = "", modifier = Modifier.size(30.dp)) })
            },
            actions = {
                      TextField(value = fieldTexto, onValueChange = {fieldTexto = it}, leadingIcon = {
                          Icon(painter = painterResource(id = R.drawable.buscar), contentDescription = "")
                      }, placeholder = { Text(text = "Buscar cancion o artista")}, shape = RoundedCornerShape(15.dp))
            },
        )
        },
        bottomBar = {
            BottomAppBar() {
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
    ) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {
            Text(text = "Seleccione una cancion")
            LazyColumn(Modifier.fillMaxWidth()) {
                items(items = listaCanciones) { item ->
                    if (item.autor.startsWith(fieldTexto) || item.titulo.startsWith(fieldTexto)) {
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
                                Image(
                                    painter = painterResource(id = item.imagen),
                                    contentDescription = "",
                                    modifier = Modifier.size(65.dp)
                                )
                                Column(Modifier.padding(5.dp)) {
                                    Text(text = item.titulo)
                                    Text(text = item.autor)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}