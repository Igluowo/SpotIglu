package com.example.spotiglu.viewModel

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import androidx.annotation.AnyRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.example.spotiglu.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class exoplayerViewModel: ViewModel() {
    private val _exoPlayer : MutableStateFlow<ExoPlayer?> = MutableStateFlow(null)
    val exoPlayer = _exoPlayer.asStateFlow()

    private val _actual = MutableStateFlow(R.raw.mychemicalromance_demolitionlovers)
    val actual = _actual.asStateFlow()

    private val _duracion = MutableStateFlow<Long>(0)
    val duracion = _duracion.asStateFlow()

    private val _duracionMinutos = MutableStateFlow("00:00")
    val duracionMinutos = _duracionMinutos.asStateFlow()

    private val _progreso = MutableStateFlow<Long>(0)
    val progreso = _progreso.asStateFlow()

    private val _progresoMinutos = MutableStateFlow("00:00")
    val progresoMinutos = _progresoMinutos.asStateFlow()

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
                    cambiarCancion(context, cancionId)
                }
                else if(playbackState == Player.STATE_IDLE){

                }
            }
        })
    }

    override fun onCleared() {
        _exoPlayer.value!!.release()
        super.onCleared()
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