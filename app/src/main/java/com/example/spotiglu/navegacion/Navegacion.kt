package com.example.spotiglu.navegacion

sealed class Navegacion(val ruta: String) {
    object ReproductorPantalla: Navegacion("ReproductorPantalla")
    object PantallaPrincipal: Navegacion("PantallaPrincipal")
    object PantallaListas: Navegacion("PantallaListas")
    object PantallaBuscador: Navegacion("PantallaBuscador")
}