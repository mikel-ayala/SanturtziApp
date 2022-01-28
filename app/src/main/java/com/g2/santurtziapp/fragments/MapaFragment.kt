package com.g2.santurtziapp.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.Location
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.g2.santurtziapp.Constantes.paradas
import com.g2.santurtziapp.R
import com.g2.santurtziapp.SharedApp
import com.g2.santurtziapp.activitidades.JuegoActivity
import com.g2.santurtziapp.activitidades.MainActivity
import com.g2.santurtziapp.databinding.FragmentMapaBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapaFragment : Fragment() {

    //Durante este fragment van a aparecer tres lineas marcadas como error pero que funcionan bien  (es un error de AS)
    private lateinit var fusedLocation: FusedLocationProviderClient
    lateinit var binding: FragmentMapaBinding
    lateinit var googleMap: GoogleMap
    lateinit var ubicacion:LatLng
    var marcadores:ArrayList<Marker> = arrayListOf()
    @SuppressLint("MissingPermission")

    private val callback = OnMapReadyCallback { GoogleMap ->
        googleMap=GoogleMap

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        //Solicitud de permisos

        //Configuración del mapa
        /*
        *
        Error 1
        *
         */
        if (!SharedApp.modolibre.modo){
            googleMap.isMyLocationEnabled=true
            googleMap.uiSettings.isMyLocationButtonEnabled = false
            googleMap.uiSettings.isCompassEnabled=false
        }

        //Generar marcadores y ubicar la cámara
        paradas.forEach {
            val marcador= googleMap.addMarker(MarkerOptions().position(it))
            if (marcador != null) {
                marcadores.add(marcador)
            }
        }
        if (!SharedApp.modolibre.modo||SharedApp.tipousu.tipo=="alumno"){
            cambiarMarcador(SharedApp.puntopartida.Partida.toInt())
        }

        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> {
                googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                        requireContext(),
                        R.raw.styleday
                    )
                )
            } // Night mode is not active, we're using the light theme
            Configuration.UI_MODE_NIGHT_YES -> {
                googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                        requireContext(),
                        R.raw.stylenight
                    )
                )
            } // Night mode is active, we're using dark theme
        }

        /*
        *
        Error 2
        *
         */
        if(!SharedApp.modolibre.modo){
            fusedLocation.lastLocation.addOnSuccessListener {
                if (it!=null){
                    ubicacion = LatLng(it.latitude, it.longitude)
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15f))
                    println("Ubicación actual. Latitud: "+it.latitude+". Longitud: "+it.longitude)
                }
            }
        }else{
            ubicacion = LatLng(43.3351509,-3.0331127)
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15f))
        }
        //Se activa el botón de juego al seleccionar un punto
        if(SharedApp.modolibre.modo){
            googleMap.setOnMarkerClickListener { marker ->
                //Genera un mensaje "Prueba: "+mX .Donde X es la id del marcador
                println("Prueba: "+marker.id)
                setFragmentResult("libre", bundleOf("punto" to marker.id.substring(1,2).toInt()))
                true
            }
            val handler= Handler()
            handler.postDelayed({
                JuegoActivity().slideView(requireActivity().findViewById(R.id.fragment1Juego),
                    requireActivity().findViewById<FrameLayout>(R.id.fragment1Juego).height,
                    requireActivity().findViewById<LinearLayout>(R.id.juegoLinearL).height)
            }, 500)
        }

        /*Autofocus de la cámara al cambiar la ubicación
        (ahora está comentado por una cuestión de funcionalidad)*/
        if(!SharedApp.modolibre.modo){
            googleMap.setOnMyLocationChangeListener {
                ubicacion= LatLng(it.latitude, it.longitude)
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 17f))
                val distancia=FloatArray(3)

                //Distancia con las paradas
                if (SharedApp.puntopartida.Partida == "0"){
                    Location.distanceBetween(ubicacion.latitude, ubicacion.longitude, paradas[SharedApp.puntopartida.Partida.toInt()].latitude, paradas[SharedApp.puntopartida.Partida.toInt()].longitude,distancia)
                }else if (SharedApp.puntopartida.Partida.toInt() in 1..7){
                    Location.distanceBetween(ubicacion.latitude, ubicacion.longitude, paradas[SharedApp.puntopartida.Partida.toInt()-1].latitude, paradas[SharedApp.puntopartida.Partida.toInt()-1].longitude,distancia)
                }

                //Distancia con CIFP Txurdinaga LHII
//                Location.distanceBetween(ubicacion.latitude,ubicacion.longitude,43.257686, -2.902560,distancia)

                if (distancia[0]<50){
                   setFragmentResult("mapa", bundleOf("rango" to "yes"))
                }else{
                    setFragmentResult("mapa", bundleOf("rango" to "no"))
                }
            }
        }

    }

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapaBinding.inflate(layoutInflater)

        binding.UbicacionButton.setOnClickListener {


            /*

            *
            Error 3
            *
            */
            if(!SharedApp.modolibre.modo) {
                fusedLocation.lastLocation.addOnSuccessListener {
                    ubicacion = LatLng(it.latitude, it.longitude)
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15f))
                }
            }
        }
        fusedLocation = LocationServices.getFusedLocationProviderClient(requireActivity())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapsFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapsFragment?.getMapAsync(callback)
    }

    fun cambiarMarcador(posicion:Int){
        marcadores.forEach {
            when {
                marcadores.indexOf(it)<(posicion-1) -> {
                    it.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                }
                marcadores.indexOf(it)==(posicion-1) -> {
                    it.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                }
                marcadores.indexOf(it)>(posicion-1) -> {
                    it.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                }
            }
        }
    }


    override fun onDestroy() {
        JuegoActivity().slideView(requireActivity().findViewById<FrameLayout>(R.id.fragment1Juego),
            requireActivity().findViewById<FrameLayout>(R.id.fragment1Juego).height, 0)

        super.onDestroy()
    }

    override fun onPause() {
        JuegoActivity().slideView(requireActivity().findViewById<FrameLayout>(R.id.fragment1Juego),
            requireActivity().findViewById<FrameLayout>(R.id.fragment1Juego).height, 0)
        super.onPause()
    }

    override fun onResume() {
        super.onResume()

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){

            val j: JuegoActivity? = activity as JuegoActivity?
            j!!.startActivity(Intent(requireContext(), MainActivity::class.java))

        }
    }

}