package com.g2.santurtziapp.fragments.explicaciones

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.g2.santurtziapp.R
import com.g2.santurtziapp.SharedApp
import com.g2.santurtziapp.databinding.FragmentExplicacionPremioBinding

class ExplicacionPremioFragment : Fragment() {

    lateinit var cofreanimation: AnimationDrawable
    private lateinit var vistaanimada: Animation
    lateinit var binding: FragmentExplicacionPremioBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentExplicacionPremioBinding.inflate(layoutInflater)

        return binding.root

    }//onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animacion_cofre(binding.imgcofre)

        val moneda= binding.imgmoneda

        when (SharedApp.puntopartida.Partida) {

            "1" -> moneda.setImageResource(R.drawable.monedam)

            "2" -> moneda.setImageResource(R.drawable.monedae)

            "3" -> moneda.setImageResource(R.drawable.monedak)

            "4" -> moneda.setImageResource(R.drawable.monedau)

            "5" -> moneda.setImageResource(R.drawable.monedaa)

            "6" -> moneda.setImageResource(R.drawable.monedam)

            "7" -> moneda.setImageResource(R.drawable.monedae)

        }//when (SharedApp.puntopartida.Partida)

        vistaanimada= AnimationUtils.loadAnimation(requireContext(),
            R.anim.view_animacion)

        moneda.startAnimation(vistaanimada)

    }//onViewCreated(view: View, savedInstanceState: Bundle?)

    //ANIMACIÓN DEL COFRE
    fun animacion_cofre(imagen: ImageView) {

        imagen.apply {

            setBackgroundResource(R.drawable.animation)
            cofreanimation = background as AnimationDrawable

        }//apply

        cofreanimation.start()

    }//animacion_cofre(imagen: ImageView)

}//ExplicacionPremioFinal()