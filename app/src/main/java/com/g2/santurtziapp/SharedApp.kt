package com.g2.santurtziapp

import android.app.Application

class SharedApp : Application() {

    companion object {

        internal lateinit var users: Constantes.User
        internal lateinit var tipousu: Constantes.TipoUsu
        internal lateinit var puntopartida: Constantes.PuntoPartida
        internal lateinit var modolibre: Constantes.ModoLibre

    }//companion object

    override fun onCreate() {
        super.onCreate()

        users = Constantes.User(applicationContext)
        tipousu = Constantes.TipoUsu(applicationContext)
        puntopartida = Constantes.PuntoPartida(applicationContext)
        modolibre = Constantes.ModoLibre(applicationContext)

    }//onCreate()

}//SharedApp()