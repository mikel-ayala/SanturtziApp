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

class ExplicacionPremioFragment : Fragment() {

    lateinit var cofreanimation: AnimationDrawable
    private lateinit var vistaanimada: Animation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explicacion_premio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //creamos la animacion del cofre
        val img= view.findViewById<ImageView>(R.id.imgcofre)
        if (img != null) {
            animacion_cofre(img)
        }
        //Ejecutamos la animacion de la moneda
        val moneda= view.findViewById<ImageView>(R.id.imgmoneda)

        if (moneda != null) {
            when(SharedApp.puntopartida.Partida){
                "1" -> moneda.setImageResource(R.drawable.monedam)
                "2" -> moneda.setImageResource(R.drawable.monedae)
                "3" -> moneda.setImageResource(R.drawable.monedak)
                "4" -> moneda.setImageResource(R.drawable.monedau)
                "5" -> moneda.setImageResource(R.drawable.monedaa)
                "6" -> moneda.setImageResource(R.drawable.monedam)
                "7" -> moneda.setImageResource(R.drawable.monedae)
            }
            vistaanimada= AnimationUtils.loadAnimation(requireContext(),
                R.anim.view_animacion)
            moneda.startAnimation(vistaanimada)
        }

    }
    //funcion para animar el cofre
    fun animacion_cofre(imagen: ImageView){
        imagen.apply {
            setBackgroundResource(R.drawable.animation)
            cofreanimation = background as AnimationDrawable
        }
        cofreanimation.start()
    }

}