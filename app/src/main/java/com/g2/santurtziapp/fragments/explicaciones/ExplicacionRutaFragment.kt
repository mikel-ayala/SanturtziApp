package com.g2.santurtziapp.fragments.explicaciones

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.app.ActivityCompat
import androidx.navigation.Navigation
import com.g2.santurtziapp.R
import com.g2.santurtziapp.SharedApp
import com.g2.santurtziapp.activitidades.JuegoActivity
import com.g2.santurtziapp.activitidades.MainActivity
import com.g2.santurtziapp.databinding.FragmentExplicacionRutaBinding
import com.g2.santurtziapp.fragments.MapaFragment

class ExplicacionRutaFragment : Fragment() {

    lateinit var binding: FragmentExplicacionRutaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentExplicacionRutaBinding.inflate(layoutInflater)

        return binding.root

    }//onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val j: JuegoActivity? = activity as JuegoActivity?
        val heightDp = resources.displayMetrics.run { heightPixels / density }

        binding.rutaNext.isEnabled = false

        //COMPROBAR PERMISOS DE LOCALIZACIÓN
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            val j = activity as JuegoActivity

            j.startActivity(Intent(requireContext(), MainActivity::class.java))
            j.finish()

        }//if (ActivityCompat.checkSelfPermission( requireContext(), Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED)

        else {

            Handler().postDelayed(Runnable {

                if ((SharedApp.puntopartida.Partida == "0") || (SharedApp.puntopartida.Partida == "8")) {

                    JuegoActivity().slideView(
                        requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                        requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                        (heightDp / 1.10).toInt()
                    )

                    Navigation.findNavController(view).navigate(R.id.action_Ruta_Sitio)

                }//if ((SharedApp.puntopartida.Partida == "0") || (SharedApp.puntopartida.Partida == "8"))

                else {

                    j?.replaceFragment(MapaFragment(), 2)

                    val lugar = j?.checkPuntoPartida(SharedApp.puntopartida.Partida)

                    //MODO LIBRE
                    requireActivity().supportFragmentManager.setFragmentResultListener(
                        "libre",
                        viewLifecycleOwner
                    ) { juego, bundle ->

                        val result = bundle.getInt("punto")

                        SharedApp.puntopartida.Partida = (result + 1).toString()

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment1Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment1Juego).height,
                            0
                        )

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                            (heightDp / 1.5).toInt()
                        )

                        Navigation.findNavController(view).navigate(R.id.action_Ruta_Sitio)

                    }//FragmentResultListener

                    binding.rutaLugar.text = lugar!![0]

                    binding.rutaImagen.setImageResource(lugar[1].toInt())

                    //MODO GUIADO
                    requireActivity().supportFragmentManager.setFragmentResultListener(
                        "mapa",
                        viewLifecycleOwner
                    ) { juego, bundle ->

                        val result = bundle.getString("rango")

                        if (result != null && result == "yes") {

                            binding.rutaNext.isEnabled = true

                        }//if (result != null && result == "yes")

                        if (result != null && result == "no") {

                            binding.rutaNext.isEnabled = false

                        }//if (result != null && result == "no")

                    }//FragmentResultListener

                    binding.rutaNext.setOnClickListener {

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment1Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment1Juego).height,
                            0
                        )

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                            (heightDp / 1.10).toInt()
                        )

                        Navigation.findNavController(view).navigate(R.id.action_Ruta_Sitio)

                    }//onClick

                }//if ((SharedApp.puntopartida.Partida != "0") || (SharedApp.puntopartida.Partida != "8"))

            }, 500)

        }//if (ActivityCompat.checkSelfPermission( requireContext(), Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED)

    }//onViewCreated(view: View, savedInstanceState: Bundle?)

}//ExplicacionRutaFragment()