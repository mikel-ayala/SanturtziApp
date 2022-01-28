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
import com.g2.santurtziapp.databinding.FragmentJuego7Binding

class Juego7Fragment : Fragment() {

    lateinit var binding: FragmentJuego7Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentJuego7Binding.inflate(layoutInflater)

        binding.validarCancion.setOnClickListener {

            if (binding.spinner1.selectedItem.equals("Santurce")){

                binding.spinner1.setBackgroundColor(resources.getColor(R.color.Success))
                binding.spinner1.isEnabled = false

            } else {

                binding.spinner1.setBackgroundColor(resources.getColor(R.color.Danger))

            }

            if (binding.spinner2.selectedItem.equals("salla")){

                binding.spinner2.setBackgroundColor(resources.getColor(R.color.Success))
                binding.spinner2.isEnabled = false

            } else {

                binding.spinner2.setBackgroundColor(resources.getColor(R.color.Danger))

            }

            if (binding.spinner3.selectedItem.equals("corriendo")){

                binding.spinner3.setBackgroundColor(resources.getColor(R.color.Success))
                binding.spinner3.isEnabled = false

            } else {

                binding.spinner3.setBackgroundColor(resources.getColor(R.color.Danger))

            }

            if (binding.spinner4.selectedItem.equals("gritando")){

                binding.spinner4.setBackgroundColor(resources.getColor(R.color.Success))
                binding.spinner4.isEnabled = false

            } else {

                binding.spinner4.setBackgroundColor(resources.getColor(R.color.Danger))

            }

            if (binding.spinner5.selectedItem.equals("sardinitas")){

                binding.spinner5.setBackgroundColor(resources.getColor(R.color.Success))
                binding.spinner5.isEnabled = false

            } else {

                binding.spinner5.setBackgroundColor(resources.getColor(R.color.Danger))

            }

            if (binding.spinner6.selectedItem.equals("Santurce")){

                binding.spinner6.setBackgroundColor(resources.getColor(R.color.Success))
                binding.spinner6.isEnabled = false

            } else {

                binding.spinner6.setBackgroundColor(resources.getColor(R.color.Danger))

            }

            if (binding.spinner7.selectedItem.equals("yo")){

                binding.spinner7.setBackgroundColor(resources.getColor(R.color.Success))
                binding.spinner7.isEnabled = false

            } else {

                binding.spinner7.setBackgroundColor(resources.getColor(R.color.Danger))

            }

            if ((binding.spinner1.selectedItem.equals("Santurce")) &&
                    (binding.spinner2.selectedItem.equals("salla")) &&
                    (binding.spinner3.selectedItem.equals("corriendo")) &&
                    (binding.spinner4.selectedItem.equals("gritando")) &&
                    (binding.spinner5.selectedItem.equals("sardinitas")) &&
                    (binding.spinner6.selectedItem.equals("Santurce")) &&
                    (binding.spinner7.selectedItem.equals("yo"))) {

                        binding.validarCancion.setBackgroundColor(resources.getColor(R.color.Disabled))
                        binding.validarCancion.isClickable = false

                        MediaPlayer.create(requireContext(), R.raw.ondo).start()

                        setFragmentResult("juego7", bundleOf("terminado" to "yes"))

            }
            else {

                MediaPlayer.create(requireContext(), R.raw.txarto).start()

                Toast.makeText(requireContext(), getString(R.string.error), Toast.LENGTH_SHORT).show()

            }

        }

        return binding.root
    }

}