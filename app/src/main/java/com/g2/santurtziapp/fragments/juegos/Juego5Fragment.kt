package com.g2.santurtziapp.fragments.juegos

import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.g2.santurtziapp.R
import com.g2.santurtziapp.databinding.FragmentJuego5Binding

class Juego5Fragment : Fragment() {

    lateinit var binding: FragmentJuego5Binding
    var fase: Int = 1
    var checked: RadioButton? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentJuego5Binding.inflate(layoutInflater)

        binding.validar.setOnClickListener {

            checked = requireActivity().findViewById(binding.radioGroup.checkedRadioButtonId)

            if (checked != null) {

                when(fase) {

                    1 -> {

                        if (binding.radioButton4.isChecked){

                            binding.radioButton4.setBackgroundColor(resources.getColor(R.color.Success))

                            MediaPlayer.create(requireContext(), R.raw.ondo).start()
                            Handler().postDelayed(Runnable {
                                binding.radioButton4.setBackgroundColor(Color.TRANSPARENT)
                                binding.radioGroup.clearCheck()
                                binding.pregunta.text = getString(R.string.test2)
                                binding.radioButton1.text = "23"
                                binding.radioButton2.text = "53"
                                binding.radioButton3.text = "63"
                                binding.radioButton4.text = "36"
                            }, 500)

                            fase = 2

                        }
                        else {

                            checked!!.setBackgroundColor(resources.getColor(R.color.Danger))

                            MediaPlayer.create(requireContext(), R.raw.txarto).start()
                            Handler().postDelayed(Runnable {
                                checked!!.setBackgroundColor(Color.TRANSPARENT)
                                binding.radioGroup.clearCheck()

                            }, 500)
                            Toast.makeText(requireContext(), getString(R.string.preguntamal), Toast.LENGTH_SHORT).show()

                        }

                    }
                    2 -> {

                        if (binding.radioButton2.isChecked){

                            binding.radioButton2.setBackgroundColor(resources.getColor(R.color.Success))

                            MediaPlayer.create(requireContext(), R.raw.ondo).start()
                            Handler().postDelayed(Runnable {
                                binding.radioButton2.setBackgroundColor(Color.TRANSPARENT)
                                binding.radioGroup.clearCheck()
                                binding.pregunta.text = getString(R.string.test3)
                                binding.radioButton1.text = "2"
                                binding.radioButton2.text = "3"
                                binding.radioButton3.text = "1"
                                binding.radioButton4.text = "0"
                            }, 500)

                            fase = 3

                        }
                        else {

                            checked!!.setBackgroundColor(resources.getColor(R.color.Danger))

                            MediaPlayer.create(requireContext(), R.raw.txarto).start()
                            Handler().postDelayed(Runnable {
                                checked!!.setBackgroundColor(Color.TRANSPARENT)
                                binding.radioGroup.clearCheck()

                            }, 500)
                            Toast.makeText(requireContext(), getString(R.string.preguntamal), Toast.LENGTH_SHORT).show()

                        }

                    }
                    3 -> {

                        if (binding.radioButton3.isChecked){

                            binding.radioButton3.setBackgroundColor(resources.getColor(R.color.Success))

                            MediaPlayer.create(requireContext(), R.raw.ondo).start()
                            Handler().postDelayed(Runnable {
                                binding.radioButton3.setBackgroundColor(Color.TRANSPARENT)
                                binding.radioGroup.clearCheck()
                                binding.pregunta.text = getString(R.string.test4)
                                binding.radioButton1.text = "Aintzane Urkijo"
                                binding.radioButton2.text = "Mireia Etxendia"
                                binding.radioButton3.text = "Maialen Urkixo"
                                binding.radioButton4.text = "Ander Etxebarria"
                            }, 500)

                            fase = 4

                        }
                        else {

                            checked!!.setBackgroundColor(resources.getColor(R.color.Danger))

                            MediaPlayer.create(requireContext(), R.raw.txarto).start()
                            Handler().postDelayed(Runnable {
                                checked!!.setBackgroundColor(Color.TRANSPARENT)
                                binding.radioGroup.clearCheck()

                            }, 500)
                            Toast.makeText(requireContext(), getString(R.string.preguntamal), Toast.LENGTH_SHORT).show()

                        }

                    }
                    4 -> {

                        if (binding.radioButton1.isChecked){

                            binding.radioButton1.setBackgroundColor(resources.getColor(R.color.Success))

                            MediaPlayer.create(requireContext(), R.raw.ondo).start()
                            Handler().postDelayed(Runnable {
                                binding.radioButton1.setBackgroundColor(Color.TRANSPARENT)
                                binding.radioGroup.clearCheck()
                                binding.pregunta.text = getString(R.string.test5)
                                binding.radioButton1.text = getString(R.string.test5a)
                                binding.radioButton2.text = getString(R.string.test5b)
                                binding.radioButton3.text = getString(R.string.test5c)
                                binding.radioButton4.text = getString(R.string.test5d)
                            }, 500)

                            fase = 5

                        }
                        else {

                            checked!!.setBackgroundColor(resources.getColor(R.color.Danger))

                            MediaPlayer.create(requireContext(), R.raw.txarto).start()
                            Handler().postDelayed(Runnable {
                                checked!!.setBackgroundColor(Color.TRANSPARENT)
                                binding.radioGroup.clearCheck()

                            }, 500)
                            Toast.makeText(requireContext(), getString(R.string.preguntamal), Toast.LENGTH_SHORT).show()

                        }

                    }
                    5 -> {

                        if (binding.radioButton3.isChecked){

                            binding.radioButton3.setBackgroundColor(resources.getColor(R.color.Success))

                            MediaPlayer.create(requireContext(), R.raw.ondo).start()
                            binding.validar.isClickable = false
                            binding.validar.setBackgroundColor(resources.getColor(R.color.Disabled))

                            setFragmentResult("juego5", bundleOf("terminado" to "yes"))

                        }
                        else {

                            checked!!.setBackgroundColor(resources.getColor(R.color.Danger))

                            MediaPlayer.create(requireContext(), R.raw.txarto).start()
                            Handler().postDelayed(Runnable {
                                checked!!.setBackgroundColor(Color.TRANSPARENT)
                                binding.radioGroup.clearCheck()

                            }, 500)
                            Toast.makeText(requireContext(), getString(R.string.preguntamal), Toast.LENGTH_SHORT).show()

                        }

                    }

                }

            }

        }

        return binding.root
    }

}