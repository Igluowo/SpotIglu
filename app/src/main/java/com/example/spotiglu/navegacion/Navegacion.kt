package com.example.spotiglu.navegacion

sealed class Navegacion(val ruta: String) {
    object ReproductorPantalla: Navegacion("R")
}