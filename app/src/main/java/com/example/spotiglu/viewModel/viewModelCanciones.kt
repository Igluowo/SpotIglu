package com.example.spotiglu.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.spotiglu.Cancion
import com.example.spotiglu.R
import com.example.spotiglu.cancionLista
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

class viewModelCanciones : ViewModel() {
    private val _botonPlay = MutableStateFlow(R.drawable.pause)
    val botonPlay = _botonPlay.asStateFlow()

    private val _colorAleatorio = MutableStateFlow(R.color.white)
    val colorAleatorio = _colorAleatorio.asStateFlow()

    private val _colorBucle = MutableStateFlow(R.color.white)
    val colorBucle = _colorBucle.asStateFlow()

    private val _colorDefault = MutableStateFlow(R.color.white)
    val colorDefault = _colorDefault.asStateFlow()

    private val _bucle = MutableStateFlow(false)
    val bucle = _bucle.asStateFlow()

    private val _aleatorio = MutableStateFlow(false)
    val aleatorio = _aleatorio.asStateFlow()

    fun cambiarPlay(skip: Boolean) {
        _botonPlay.value = if (_botonPlay.value == R.drawable.pause) R.drawable.play else R.drawable.pause
        if (skip) {
            _botonPlay.value = R.drawable.pause
        }
    }

    fun cambiarColor(boton: String) {
        if (boton == "Aleatorio") {
            _colorAleatorio.value = if (_colorAleatorio.value == R.color.teal_700) R.color.white else R.color.teal_700
        }else{
            _colorBucle.value = if (_colorBucle.value == R.color.teal_700) R.color.white else R.color.teal_700
        }
    }

    fun cambiarBucle() {
        _bucle.value = !_bucle.value
    }

    fun cambiarAleatorio() {
        _aleatorio.value = !_aleatorio.value
    }
}