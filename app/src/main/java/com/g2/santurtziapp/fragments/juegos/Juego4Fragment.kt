package com.g2.santurtziapp.fragments.juegos

import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.g2.santurtziapp.R
import com.g2.santurtziapp.activitidades.JuegoActivity
import com.g2.santurtziapp.databinding.FragmentJuego4Binding

class Juego4Fragment : Fragment() {

    lateinit var binding: FragmentJuego4Binding
    var correcto: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentJuego4Binding.inflate(layoutInflater)

        binding.validarPreguntas.setOnClickListener {

            var p1: String? = JuegoActivity().quitarEspacios(binding.pregunta1.text.toString())
            var p2: String? = JuegoActivity().quitarEspacios(binding.pregunta2.text.toString())

            if ((p1 != null) && (p1 == "3")) {

                binding.pregunta1Label.boxBackgroundColor = resources.getColor(R.color.Success)
                binding.pregunta1.isEnabled = false

            } else {

                binding.pregunta1Label.boxBackgroundColor = resources.getColor(R.color.Danger)
                binding.pregunta1.text = null
                correcto = false

            }
            if ((p2 != null) && (p2 == "3")) {

                binding.pregunta2Label.boxBackgroundColor = resources.getColor(R.color.Success)
                binding.pregunta2.isEnabled = false

            } else {

                binding.pregunta2Label.boxBackgroundColor = resources.getColor(R.color.Danger)
                binding.pregunta2.text = null
                correcto = false

            }

            if ((p1 != null) && (p2 != null) && (p1 == "3") && (p2 == "3")){

                correcto = true
                binding.validarPreguntas.isClickable = false
                binding.validarPreguntas.setBackgroundColor(resources.getColor(R.color.Disabled))

            }

            if (correcto) {

                MediaPlayer.create(requireContext(), R.raw.ondo).start()
                setFragmentResult("juego4", bundleOf("terminado" to "yes"))

            }
            else {

                MediaPlayer.create(requireContext(), R.raw.txarto).start()
                Toast.makeText(requireContext(), getString(R.string.preguntamal), Toast.LENGTH_SHORT).show()

            }

        }

        return binding.root
    }

}