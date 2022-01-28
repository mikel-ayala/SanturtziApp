package com.g2.santurtziapp.fragments

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatDelegate
import com.g2.santurtziapp.SharedApp
import com.g2.santurtziapp.activitidades.MainActivity
import com.g2.santurtziapp.databinding.FragmentNosotrosBinding

class NosotrosFragment : Fragment() {

    lateinit var binding: FragmentNosotrosBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //LOGO DE LA UPV EN BLANCO PARA EL MODO OSCURO
        if (AppCompatDelegate.MODE_NIGHT_NO == 0) {

            binding.imageView6.setColorFilter(Color.WHITE)

        }//if (AppCompatDelegate.MODE_NIGHT_NO == 0)

        binding = FragmentNosotrosBinding.inflate(layoutInflater)

        val m = activity as MainActivity?

        //OVERRIDE DE EL ONBACKPRESSED DE LA ACTIVITY
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                m?.replaceFragment(if (SharedApp.tipousu.tipo=="profesor"){ProfesorFragment()}else{PartidasFragment()}, 1)

            }//onBackPressed()

        })//callback(viewLifecycleOwner, object : OnBackPressedCallback(true)

        //LLAMADA
        binding.imgllamada.setOnClickListener{
            llamar()

        }//onClick()

        binding.txtllamada.setOnClickListener{
            llamar()

        }//onClick()

        //WHATSAPP
        binding.imgwas.setOnClickListener{
            abrirweb("https://api.whatsapp.com/send?phone=+3448980&text=Buenos%20d%C3%ADas.%20Necesito%20ayuda%20con%20la%20aplicacion.")

        }//onClick()

        binding.txtwas.setOnClickListener{
            abrirweb("https://api.whatsapp.com/send?phone=+3448980&text=Buenos%20d%C3%ADas.%20Necesito%20ayuda%20con%20la%20aplicacion.")

        }//onClick()

        //GMAIL
        binding.imggmail.setOnClickListener{
            gmail()

        }//onClick()

        binding.txtmail.setOnClickListener{
            gmail()

        }//onClick()

        //INSTAGRAM
        binding.imginsta.setOnClickListener{
            abrirweb("https://www.instagram.com/ago_Santurtzi/?hl=es")

        }//onClick()

        binding.txtinsta.setOnClickListener{
            abrirweb("https://www.instagram.com/ago_Santurtzi/?hl=es")

        }//onClick()

        //TWITTER
        binding.imgtwiter.setOnClickListener{
            abrirweb("https://twitter.com/ago_santurtzi")

        }//onClick()

        binding.txttuit.setOnClickListener{
            abrirweb("https://twitter.com/ago_santurtzi")

        }//onClick()

        //FACEBOOK
        binding.imgfacebook.setOnClickListener{
            abrirweb("https://es-la.facebook.com/ago_santurtzi/")

        }//onClick()

        binding.txtfacebook.setOnClickListener{
            abrirweb("https://es-la.facebook.com/ago_santurtzi/")

        }//onClick()

        return binding.root

    }//onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View

    //ABRIR LA APLICACIÓN DE TELÉFONO PARA QUE NOS LLAMEN
    fun llamar(){

        val nTel = Uri.parse("tel:"+binding.txtllamada.text.toString())
        val phone = Intent(Intent.ACTION_DIAL, nTel)

        startActivity(phone)

    }//llamar()

    //ABRIR EN EL NAVEGADOR LA RED SOCIAL REQUERIDA
    fun abrirweb(web:String){

        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(web))

        startActivity(browserIntent)

    }//abrirweb(web:String)

    //ABRIR EL GMAIL PARA QUE NOS ENVIEN UN CORREO
    fun gmail(){

        val intent = Intent(Intent.ACTION_SEND)

        intent.type = "text/html"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("G2_2DM3@fptxurdinaga.com"))
        intent.putExtra(Intent.EXTRA_SUBJECT, "")
        intent.putExtra(Intent.EXTRA_TEXT, "")

        startActivity(Intent(intent))

    }//gmail()

}//NosotrosFragment()