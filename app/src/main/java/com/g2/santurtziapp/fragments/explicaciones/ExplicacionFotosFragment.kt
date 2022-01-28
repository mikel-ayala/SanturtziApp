package com.g2.santurtziapp.fragments.explicaciones

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.g2.santurtziapp.SharedApp
import com.g2.santurtziapp.activitidades.JuegoActivity
import com.g2.santurtziapp.databinding.FragmentExplicacionFotosBinding

class ExplicacionFotosFragment : Fragment() {

    lateinit var binding: FragmentExplicacionFotosBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentExplicacionFotosBinding.inflate(layoutInflater)

        val j: JuegoActivity? = activity as JuegoActivity?
        val lugar = j?.checkPuntoPartida(SharedApp.puntopartida.Partida)!!

        requireActivity().supportFragmentManager.setFragmentResultListener("foto", viewLifecycleOwner) { foto, bundle ->

            val result = bundle.getString("imagen")

            if (result != null) {

                setImage(lugar[result.toInt()])

            }//if (result != null)

        }//FragmentResultListener

        return binding.root

    }//onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View

    //QUE IMAGEN MOSTRAR
    fun setImage(image: String) {

       binding.fotoExplicacion.setImageResource(image.toInt())

    }//setImage(image: String)

}