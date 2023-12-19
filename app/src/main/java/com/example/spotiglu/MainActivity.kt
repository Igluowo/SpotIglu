package com.example.spotiglu

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.spotiglu.ui.theme.SpotIgluTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spotiglu.viewModel.exoplayerViewModel

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
                                        Icon(painter = painterResource(id = R.drawable.buscar), contentDescription = null)
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
        val contexto = LocalContext.current
        val exoPlayerViewModel: exoplayerViewModel = viewModel()
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.i_brought_you_my_bullets__you_brought_me_your_love),
                contentDescription = null,
                modifier = Modifier
                    .size(255.dp)
                    .padding(0.dp, 20.dp)
            )
            Text(text = "Demolitions Lovers - My Chemical Romance")
            Slider(value = 0f, onValueChange = {}, modifier = Modifier.fillMaxWidth())
            Row() {
                LaunchedEffect(Unit) {
                    exoPlayerViewModel.crearExoPlayer(contexto)
                    exoPlayerViewModel.reproducirCancion(contexto)
                }
                botonesReproductor(id = R.drawable.aleatorio, { })
                botonesReproductor(id = R.drawable.skip_previous, { })
                botonesReproductor(id = R.drawable.play) {
                    exoPlayerViewModel.pausarCancion()
                }
                botonesReproductor(id = R.drawable.skip_next) {
                    exoPlayerViewModel.cambiarCancion(contexto)
                }
                botonesReproductor(id = R.drawable.repetir, {})
            }
        }
    }

    @Composable
    fun botonesReproductor(id : Int, accion: () -> Unit) {
        IconButton(onClick = accion) {
            Icon(
                painter = painterResource(id = id),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }
        Spacer(modifier = Modifier.width(20.dp))
    }
}