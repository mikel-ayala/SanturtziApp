package com.g2.santurtziapp.fragments.juegos

import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.g2.santurtziapp.R
import com.g2.santurtziapp.databinding.FragmentJuego6Binding

class Juego6Fragment : Fragment() {

    lateinit var binding: FragmentJuego6Binding
    var fase: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentJuego6Binding.inflate(layoutInflater)

        binding.validar.setOnClickListener {

            when(fase) {

                1 -> {

                    if (binding.radioButton2.isChecked){

                        binding.radioButton2.setBackgroundColor(resources.getColor(R.color.Success))

                        MediaPlayer.create(requireContext(), R.raw.ondo).start()
                        Handler().postDelayed(Runnable {
                            binding.radioButton2.setBackgroundColor(Color.TRANSPARENT)
                            binding.radioGroup.clearCheck()
                            binding.pregunta.text = getString(R.string.VF2)
                        }, 500)

                        fase = 2

                    }
                    else {

                        binding.radioButton1.setBackgroundColor(resources.getColor(R.color.Danger))

                        MediaPlayer.create(requireContext(), R.raw.txarto).start()
                        Handler().postDelayed(Runnable {
                            binding.radioButton1.setBackgroundColor(Color.TRANSPARENT)
                            binding.radioGroup.clearCheck()
                        }, 500)

                        Toast.makeText(requireContext(), getString(R.string.preguntamal), Toast.LENGTH_SHORT).show()

                    }

                }
                2 -> {

                    if (binding.radioButton1.isChecked){

                        binding.radioButton1.setBackgroundColor(resources.getColor(R.color.Success))

                        MediaPlayer.create(requireContext(), R.raw.ondo).start()
                        Handler().postDelayed(Runnable {
                            binding.radioButton1.setBackgroundColor(Color.TRANSPARENT)
                            binding.radioGroup.clearCheck()
                            binding.pregunta.text = getString(R.string.VF3)
                        }, 500)

                        fase = 3

                    }
                    else {

                        binding.radioButton2.setBackgroundColor(resources.getColor(R.color.Danger))

                        MediaPlayer.create(requireContext(), R.raw.txarto).start()
                        Handler().postDelayed(Runnable {
                            binding.radioButton2.setBackgroundColor(Color.TRANSPARENT)
                            binding.radioGroup.clearCheck()
                        }, 500)

                        Toast.makeText(requireContext(), getString(R.string.preguntamal), Toast.LENGTH_SHORT).show()

                    }

                }
                3 -> {

                    if (binding.radioButton1.isChecked){

                        binding.radioButton1.setBackgroundColor(resources.getColor(R.color.Success))

                        MediaPlayer.create(requireContext(), R.raw.ondo).start()
                        Handler().postDelayed(Runnable {
                            binding.radioButton1.setBackgroundColor(Color.TRANSPARENT)
                            binding.radioGroup.clearCheck()
                            binding.pregunta.text = getString(R.string.VF4)
                        }, 500)

                        fase = 4

                    }
                    else {

                        binding.radioButton2.setBackgroundColor(resources.getColor(R.color.Danger))

                        MediaPlayer.create(requireContext(), R.raw.txarto).start()
                        Handler().postDelayed(Runnable {
                            binding.radioButton2.setBackgroundColor(Color.TRANSPARENT)
                            binding.radioGroup.clearCheck()
                        }, 500)

                        Toast.makeText(requireContext(), getString(R.string.preguntamal), Toast.LENGTH_SHORT).show()

                    }

                }
                4 -> {

                    if (binding.radioButton2.isChecked){

                        binding.radioButton2.setBackgroundColor(resources.getColor(R.color.Success))

                        MediaPlayer.create(requireContext(), R.raw.ondo).start()
                        Handler().postDelayed(Runnable {
                            binding.radioButton2.setBackgroundColor(Color.TRANSPARENT)
                            binding.radioGroup.clearCheck()
                            binding.pregunta.text = getString(R.string.VF5)
                        }, 500)

                        fase = 5

                    }
                    else {

                        binding.radioButton1.setBackgroundColor(resources.getColor(R.color.Danger))

                        MediaPlayer.create(requireContext(), R.raw.txarto).start()
                        Handler().postDelayed(Runnable {
                            binding.radioButton1.setBackgroundColor(Color.TRANSPARENT)
                            binding.radioGroup.clearCheck()
                        }, 500)

                        Toast.makeText(requireContext(), getString(R.string.preguntamal), Toast.LENGTH_SHORT).show()

                    }

                }
                5 -> {

                    if (binding.radioButton1.isChecked){

                        binding.radioButton1.setBackgroundColor(resources.getColor(R.color.Success))

                        MediaPlayer.create(requireContext(), R.raw.ondo).start()
                        binding.validar.isClickable = false
                        binding.validar.setBackgroundColor(resources.getColor(R.color.Disabled))

                        setFragmentResult("juego6", bundleOf("terminado" to "yes"))

                    }
                    else {

                        binding.radioButton2.setBackgroundColor(resources.getColor(R.color.Danger))

                        MediaPlayer.create(requireContext(), R.raw.txarto).start()
                        Handler().postDelayed(Runnable {
                            binding.radioButton2.setBackgroundColor(Color.TRANSPARENT)
                            binding.radioGroup.clearCheck()
                        }, 500)

                        Toast.makeText(requireContext(), getString(R.string.preguntamal), Toast.LENGTH_SHORT).show()

                    }

                }

            }

        }

        return binding.root
    }

}