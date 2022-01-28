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
import com.g2.santurtziapp.databinding.FragmentJuego8Binding

class Juego8Fragment : Fragment() {

    lateinit var binding: FragmentJuego8Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentJuego8Binding.inflate(layoutInflater)

        binding.validarPalabra.setOnClickListener {

            var palabra: String = JuegoActivity().quitarEspacios(binding.palabra.text.toString().lowercase())

            if ((palabra.isNotBlank()) && (palabra == "emakume")) {

                binding.palabraLabel.boxBackgroundColor = resources.getColor(R.color.Success)

                MediaPlayer.create(requireContext(), R.raw.ondo).start()

                binding.palabra.isEnabled = false
                setFragmentResult("juego8", bundleOf("terminado" to "yes"))

            } else {

                binding.palabraLabel.boxBackgroundColor = resources.getColor(R.color.Danger)

                MediaPlayer.create(requireContext(), R.raw.txarto).start()

                Toast.makeText(requireContext(), getString(R.string.error), Toast.LENGTH_SHORT).show()

            }

        }

        return binding.root
    }

}