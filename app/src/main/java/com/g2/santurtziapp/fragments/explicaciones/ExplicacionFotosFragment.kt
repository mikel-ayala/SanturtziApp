package com.g2.santurtziapp.fragments.explicaciones

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import com.g2.santurtziapp.R
import com.g2.santurtziapp.SharedApp
import com.g2.santurtziapp.activitidades.JuegoActivity
import com.g2.santurtziapp.databinding.FragmentExplicacionFotosBinding

class ExplicacionFotosFragment : Fragment() {

    lateinit var binding: FragmentExplicacionFotosBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentExplicacionFotosBinding.inflate(layoutInflater)

        val j: JuegoActivity? = activity as JuegoActivity?
        val lugar = j?.checkPuntoPartida(SharedApp.puntopartida.Partida)!!

        requireActivity().supportFragmentManager.setFragmentResultListener("foto", viewLifecycleOwner) { foto, bundle ->

            val result = bundle.getString("imagen")

            if (result != null) {
                setImage(lugar[result.toInt()])
            }
        }

        return binding!!.root
    }

    fun setImage(image: String) {

       binding.fotoExplicacion.setImageResource(image.toInt())

    }

}