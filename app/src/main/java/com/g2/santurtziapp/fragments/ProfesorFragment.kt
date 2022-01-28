package com.g2.santurtziapp.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.g2.santurtziapp.R
import com.g2.santurtziapp.SharedApp
import com.g2.santurtziapp.activitidades.JuegoActivity
import com.g2.santurtziapp.databinding.FragmentProfesorBinding

class ProfesorFragment : Fragment() {

    lateinit var binding: FragmentProfesorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfesorBinding.inflate(layoutInflater)

        //DIALOGO PARA SALIR DE LA APP
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle(R.string.Salir)
                setMessage(R.string.seguroSalir)
                setPositiveButton(R.string.confirmar,
                    DialogInterface.OnClickListener { dialog, id ->

                        activity?.finish()
                        dialog.dismiss()

                    })//setPositiveButton()

                setNegativeButton(R.string.menu_cerrar,
                    DialogInterface.OnClickListener { dialog, id ->

                        dialog.dismiss()

                    })//setNegativeButton()

            }//builder

            builder.create()

        }//alertDialog

        //OVERRIDE DE EL ONBACKPRESSED DE LA ACTIVITY
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                alertDialog?.show()

            }//onBackPressed()

        })//callback(viewLifecycleOwner, object : OnBackPressedCallback(true)

        binding.btnGuiado.setOnClickListener(){
            SharedApp.modolibre.modo = false
            SharedApp.puntopartida.Partida = "1"

            startActivity(Intent(requireContext(), JuegoActivity::class.java))
            activity?.finish()

        }//onClick()

        binding.btnLibre.setOnClickListener(){
            SharedApp.modolibre.modo = true
            SharedApp.puntopartida.Partida = "1"

            startActivity(Intent(requireContext(), JuegoActivity::class.java))
            activity?.finish()

        }//onClick()

        return binding.root

    }//onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View

}//ProfesorFragment()