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
    ): View? {
        // Inflate the layout for this fragment
        if (AppCompatDelegate.MODE_NIGHT_NO==0){
            binding.imageView6.setColorFilter(Color.WHITE)
        }

        binding = FragmentNosotrosBinding.inflate(layoutInflater)

        val m = activity as MainActivity?

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                m?.replaceFragment(if (SharedApp.tipousu.tipo=="profesor"){ProfesorFragment()}else{PartidasFragment()}, 1)

            }

        })

        //llamar
        fun llamar(){
            val nTel = Uri.parse("tel:"+binding.txtllamada.text.toString())
            val phone = Intent(Intent.ACTION_DIAL, nTel)
            startActivity(phone)
        }
        binding.imgllamada.setOnClickListener{
            llamar()
        }
        binding.txtllamada.setOnClickListener{
            llamar()
        }
        //enlace a WhatsAPP
        fun WhatsApp(){
            abrirweb("https://api.whatsapp.com/send?phone=+3448980&text=Buenos%20d%C3%ADas.%20Necesito%20ayuda%20con%20la%20aplicacion.")
        }
        binding.imgwas.setOnClickListener{
            WhatsApp()
        }
        binding.txtwas.setOnClickListener{
            WhatsApp()
        }
        //Enlace a Gmail
        fun Gmail(){
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/html"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("G2_2DM3@fptxurdinaga.com"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "")
            intent.putExtra(Intent.EXTRA_TEXT, "")
            startActivity(Intent(intent))
        }
        binding.imggmail.setOnClickListener{
            Gmail()
        }
        binding.txtmail.setOnClickListener{
            Gmail()
        }
        //Enlace a instagram
        fun Instagram(){
            abrirweb("https://www.instagram.com/ago_Santurtzi/?hl=es")
        }
        binding.imginsta.setOnClickListener{
            Instagram()
        }
        binding.txtinsta.setOnClickListener{
            Instagram()
        }
        //Enlace a twiter
        fun twitter(){
            abrirweb("https://twitter.com/ago_santurtzi")
        }
        binding.imgtwiter.setOnClickListener{
            twitter()
        }
        binding.txttuit.setOnClickListener{
            twitter()
        }
        //enlace a facebook
        fun facebook(){
            abrirweb("https://es-la.facebook.com/ago_santurtzi/")
        }
        binding.imgfacebook.setOnClickListener{
            facebook()
        }
        binding.txtfacebook.setOnClickListener{
            facebook()
        }

        return binding.root
    }

    fun abrirweb(web:String){
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(web))
        startActivity(browserIntent)
    }

}