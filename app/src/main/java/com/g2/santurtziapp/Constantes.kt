package com.g2.santurtziapp

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.gms.maps.model.LatLng

object Constantes {

    //CLASES PARA LA SHAREDAPP
    internal class TipoUsu(context: Context) {
        val PREFS_NAME = "com.g2.santurtziapp.sharedpreferences"
        val SHARED_NAME = "Tipo"
        val name: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)
        var tipo: String
            //comprobamos el archivo de shared preferences
            get() = name.getString(SHARED_NAME, "alumno").toString()
            //modificamos el valor de shared preferences
            set(value) = name.edit().putString(SHARED_NAME, value).apply()

    }//TipoUsu(context: Context)

    internal class User(context: Context) {
        val PREFS_NAME = "com.g2.santurtziapp.sharedpreferences.User"
        val SHARED_NAME = "User"
        val User: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)
        var user: String
            //comprobamos el archivo de shared preferences
            get() = User.getString(SHARED_NAME, "").toString()
            //modificamos el valor de shared preferences
            set(value) = User.edit().putString(SHARED_NAME, value).apply()

    }//User(context: Context)

    internal class PuntoPartida(context: Context) {
        val PREFS_NAME = "com.g2.ago.santurtziapp.PuntoPartida"
        val SHARED_NAME = "Partida"
        val PuntoPartida: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)
        var Partida: String
            //comprobamos el archivo de shared preferences
            get() = PuntoPartida.getString(SHARED_NAME, "").toString()
            //modificamos el valor de shared preferences
            set(value) = PuntoPartida.edit().putString(SHARED_NAME, value).apply()

    }//PuntoPartida(context: Context)

    internal class ModoLibre(context: Context) {
        val PREFS_NAME = "com.g2.santurtziapp.sharedpreferences"
        val SHARED_NAME = "Modo"
        val name: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)
        var modo: Boolean
            //comprobamos el archivo de shared preferences
            get() = name.getBoolean(SHARED_NAME, false)
            //modificamos el valor de shared preferences
            set(value) = name.edit().putBoolean(SHARED_NAME, value).apply()

    }//ModoLibre(context: Context)

    //PIEZAS DEL PUZZLE
    class PuzzlePiece(context: Context?): AppCompatImageView(context!!) {

        var xCoord = 0
        var yCoord = 0
        var pieceWidth = 0
        var pieceHeight = 0
        var canMove = true

    }//PuzzlePiece(context: Context?): AppCompatImageView(context!!)

    //CARTA DEL MEMORYCARD
    data class MemoryCard(val identifier: Int, var isFaceUp: Boolean = false, var isMatched: Boolean = false)

    //PARTIDA
    data class Partida(var apodo: String, var punto: String)

    const val TAG = "Juego2Fragment"

    //COORDENADAS DE LOS PUNTOS DE INTERÉS
    private val parada1: LatLng = LatLng(43.330306, -3.029750)
    private val parada2: LatLng = LatLng(43.330611, -3.030861)
    private val parada3: LatLng = LatLng(43.330778, -3.031583)
    private val parada4: LatLng = LatLng(43.328917, -3.031500)
    private val parada5: LatLng = LatLng(43.328861, -3.032944)
    private val parada6: LatLng = LatLng(43.333750, -3.038722)
    private val parada7: LatLng = LatLng(43.334417, -3.039278)
    internal val paradas: List<LatLng> = listOf(parada1, parada2, parada3, parada4, parada5, parada6, parada7)

    //ICONO DEL PERFIL DEL USUARIO
    private const val guestAmarillo: Int = R.drawable.guest_amarillo
    private const val guestAzul: Int = R.drawable.guest_azul
    private const val guestMorado: Int = R.drawable.guest_morado
    private const val guestNaranja: Int = R.drawable.guest_naranja
    private const val guestRojo: Int = R.drawable.guest_rojo
    private const val guestVerde: Int = R.drawable.guest_verde
    internal val guests: List<Int> = listOf(guestAmarillo, guestAzul, guestMorado, guestNaranja, guestRojo, guestVerde)

}//Constantes