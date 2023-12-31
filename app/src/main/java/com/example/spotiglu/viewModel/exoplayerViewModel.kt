package com.example.spotiglu.viewModel

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.annotation.AnyRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.example.spotiglu.Cancion
import com.example.spotiglu.albumes
import com.example.spotiglu.cancionLista
import com.example.spotiglu.playlist
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random

class exoplayerViewModel(): ViewModel() {

    private val _exoPlayer : MutableStateFlow<ExoPlayer?> = MutableStateFlow(null)
    val exoPlayer = _exoPlayer.asStateFlow()

    private val _cancion = MutableStateFlow(0)
    val cancion = _cancion.asStateFlow()

    private val _numeroAlbum = MutableStateFlow(0)
    val numeroAlbum = _numeroAlbum.asStateFlow()

    private val _album = MutableStateFlow(albumes().get(_numeroAlbum.value))
    val album = _album.asStateFlow()

    private val _lista = MutableStateFlow(_album.value.canciones)
    val lista = _lista.asStateFlow()

    private val _actual = MutableStateFlow(_lista.value.get(_cancion.value).audio)
    val actual = _actual.asStateFlow()

    private val _duracion = MutableStateFlow<Long>(0)
    val duracion = _duracion.asStateFlow()

    private val _duracionMinutos = MutableStateFlow("00:00")
    val duracionMinutos = _duracionMinutos.asStateFlow()

    private val _progreso = MutableStateFlow<Long>(0)
    val progreso = _progreso.asStateFlow()

    private val _progresoMinutos = MutableStateFlow("00:00")
    val progresoMinutos = _progresoMinutos.asStateFlow()

    private val _bucle = MutableStateFlow(false)
    val bucle = _bucle.asStateFlow()

    private val _aleatorio = MutableStateFlow(false)
    val aleatorio = _aleatorio.asStateFlow()

    private var listaHistorial = ArrayList<Int>()

    private val _listaAnteriores = MutableStateFlow(listaHistorial)
    val listaAnteriores = _listaAnteriores.asStateFlow()

    private val _carga = MutableStateFlow(false)
    val carga = _carga.asStateFlow()

    fun crearExoPlayer(context: Context) {
        _exoPlayer.value = ExoPlayer.Builder(context).build()
        _exoPlayer.value!!.prepare()
        _exoPlayer.value!!.playWhenReady = true
    }

    fun reproducirCancion(context: Context, cancionId: Int) {
        _actual.value = cancionId
        var cancion = MediaItem.fromUri(obtenerRuta(context, _actual.value))
        _exoPlayer.value!!.setMediaItem(cancion)
        _exoPlayer.value!!.playWhenReady = true
        _exoPlayer.value!!.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if(playbackState == Player.STATE_READY){
                    _duracion.value = _exoPlayer.value!!.duration
                    devolverDuracion()
                    viewModelScope.launch {
                        while (isActive) {
                            _progreso.value = _exoPlayer.value!!.currentPosition
                            devolverProgreso()
                            delay(1000)
                        }
                    }
                }
                else if(playbackState == Player.STATE_BUFFERING){

                }
                else if(playbackState == Player.STATE_ENDED){
                    if (_bucle.value) {
                        cambiarCancion(context, cancionId)
                    }else if (_aleatorio.value) {
                        cancionAleatoria()
                        cambiarCancion(context, _lista.value.get(_cancion.value).audio)
                        agregarHistorial()
                    }else if (_cancion.value >= _lista.value.size - 1) {
                        cancionMax()
                        cambiarCancion(context, _lista.value.get(_cancion.value - 4).audio)
                    }else{
                        agregarHistorial()
                        sumarCancion()
                        cambiarCancion(context, _lista.value.get(_cancion.value + 1).audio)
                    }
                }
                else if(playbackState == Player.STATE_IDLE){

                }
            }
        })
    }

    fun cambiarBucle() {
        _bucle.value = !_bucle.value
    }

    fun cambiarAleatorio() {
        _aleatorio.value = !_aleatorio.value
    }

    fun sumarCancion() {
        _cancion.value++
    }

    fun cancionMax() {
        _cancion.value = 0
    }

    fun ponerCancion(numeroCancion: Int) {
        _cancion.value = numeroCancion
    }

    fun cancionMin() {
        _cancion.value = _lista.value.size - 1
    }

    fun restarCancion() {
        _cancion.value--
    }

    fun cancionAleatoria() {
        val random = Random
        _cancion.value = random.nextInt(_lista.value.size)
    }

    fun cambiarValorCancion(id : Int) {
        _cancion.value = id
    }

    fun cambiarAlbumNumero(nuevoNumero: Int, tipo: String) {
        if (tipo == "Lista") {
            _numeroAlbum.value = nuevoNumero
            _album.value = playlist().get(_numeroAlbum.value)
        }else{
            _numeroAlbum.value = nuevoNumero
            _album.value = albumes().get(_numeroAlbum.value)
        }
    }

    override fun onCleared() {
        _exoPlayer.value!!.release()
        super.onCleared()
    }

    fun agregarHistorial() {
        _listaAnteriores.value.add(_cancion.value)
    }

    fun borrarHistorial() {
        _listaAnteriores.value.removeAt(_listaAnteriores.value.size - 1)
    }

    fun pausarCancion() {
        if (_exoPlayer.value!!.isPlaying) {
            _exoPlayer.value!!.pause()
        }else{
            _exoPlayer.value!!.play()
        }
    }

    fun cambiarCancion(context: Context, cancion: Int) {
        _exoPlayer.value!!.stop()
        _exoPlayer.value!!.clearMediaItems()
        _actual.value = cancion
        _exoPlayer.value!!.setMediaItem(MediaItem.fromUri(obtenerRuta(context, _actual.value)))
        _exoPlayer.value!!.prepare()
        _exoPlayer.value!!.playWhenReady = true
    }

    fun devolverDuracion() {
        val minutos = _duracion.value / 60000
        val segundos = (_duracion.value % 60000) / 1000
        _duracionMinutos.value = String.format("%02d:%02d", minutos, segundos)
    }

    fun devolverProgreso() {
        val minutos = _progreso.value / 60000
        val segundos = (_progreso.value % 60000) / 1000
        _progresoMinutos.value = String.format("%02d:%02d", minutos, segundos)
    }

    fun seekTo(posicion: Long) {
        _exoPlayer.value!!.seekTo(posicion)
    }

    fun cambiarLista(listaNueva: List<Cancion>) {
        _lista.value = listaNueva
    }

    fun quitarCancion() {
        _exoPlayer.value!!.stop()
    }
    fun obtenerDuracionLista(context: Context, lista: List<Cancion>): String {
        var duracionTotal = 0L
        for (cancion in lista) {
            val duracion = obtenerDuracionMP3(context = context, cancion.audio)
            duracionTotal += duracion
        }
        return devolverDuracionPedida(duracionTotal)
    }

    fun obtenerDuracionMP3(context: Context, rutaArchivoMP3: Int): Long {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(context, obtenerRuta(context, rutaArchivoMP3))
        return retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.toLong() ?: 0
    }

    fun devolverDuracionPedida(duracionNueva: Long): String {
        val horas = duracionNueva / 3600000
        val minutos = (duracionNueva % 3600000) / 60000
        val segundos = ((duracionNueva % 3600000) % 60000) / 1000
        if (horas.toInt() == 0) {
            return String.format("%02d minutos - %02d segundos", minutos, segundos)
        }else{
            return String.format("%2d horas - %02d minutos - %02d segundos", horas, minutos, segundos)
        }
    }

    fun cambiarCarga() {
        _carga.value = !_carga.value
    }
}



@Throws(Resources.NotFoundException::class)
fun obtenerRuta(context: Context, @AnyRes resId: Int): Uri {
    val res: Resources = context.resources
    return Uri.parse(
        ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + res.getResourcePackageName(resId)
                + '/' + res.getResourceTypeName(resId)
                + '/' + res.getResourceEntryName(resId)
    )
}