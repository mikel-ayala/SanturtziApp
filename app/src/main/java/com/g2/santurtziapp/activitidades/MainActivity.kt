package com.g2.santurtziapp.activitidades

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.g2.santurtziapp.R
import com.g2.santurtziapp.SharedApp
import com.g2.santurtziapp.databinding.ActivityMainBinding
import com.g2.santurtziapp.fragments.*
import com.g2.santurtziapp.fragments.animaciones.AnimacionCargaFragment

class MainActivity : DrawerActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //COMPROBAR PERMISOS DE LOCALIZACIÓN
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1)

            Handler().postDelayed(Runnable {

                Toast.makeText(this, R.string.ubicacion, Toast.LENGTH_SHORT).show()

            }, 2000)

        }//if !PERMISSION_GRANTED

        var fr = intent.extras

        SharedApp.modolibre.modo = false

        //MOSTRAR UN FRAGMENT DEPENDIENDO DE LA VARIABLE QUE LE PASAMOS DESDE EL MENÚ
        if (fr != null) {

            when(fr.get("fragment")){

                "LoginFragment()" -> replaceFragment(LoginFragment(), 1)
                "NosotrosFragment()" -> replaceFragment(NosotrosFragment(), 1)
                "AnimacionCargaFragment()" -> replaceFragment(AnimacionCargaFragment(), 1)
                else -> {

                    if (SharedApp.tipousu.tipo == "profesor") {

                        replaceFragment(ProfesorFragment(), 1)

                    }//fr == null && is profesor

                    else {

                        replaceFragment(PartidasFragment(), 1)

                    }//fr == null && is alumno

                }//else

            }//when(fr.get("fragment"))

        }//if fr != null

        else if (SharedApp.tipousu.tipo == "profesor") {

            replaceFragment(ProfesorFragment(), 1)

        }//fr == null && is profesor

        else {

            replaceFragment(PartidasFragment(), 1)

        }//fr == null && is alumno

    }//onCreate(savedInstanceState: Bundle?)

}//MainActivity()