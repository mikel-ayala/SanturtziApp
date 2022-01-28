package com.g2.santurtziapp.fragments.explicaciones

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.g2.santurtziapp.DB
import com.g2.santurtziapp.R
import com.g2.santurtziapp.SharedApp
import com.g2.santurtziapp.activitidades.JuegoActivity
import com.g2.santurtziapp.activitidades.MainActivity
import com.g2.santurtziapp.databinding.FragmentExplicacionFinalBinding
import com.g2.santurtziapp.fragments.animaciones.AnimacionCreditosFragment
import com.g2.santurtziapp.fragments.animaciones.AnimacionFinalFragment
import kotlinx.android.synthetic.main.header.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ExplicacionFinalFragment : Fragment() {

    lateinit var binding: FragmentExplicacionFinalBinding
    lateinit var db: DB
    lateinit var mp: MediaPlayer
    private lateinit var routine: CoroutineScope

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentExplicacionFinalBinding.inflate(layoutInflater)

        return binding.root

    }//onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = DB(requireContext(), "db", null, 1)

        val j: JuegoActivity? = activity as JuegoActivity?
        val lugar = j?.checkPuntoPartida(SharedApp.puntopartida.Partida)

        //OVERRIDE DE EL ONBACKPRESSED DE LA ACTIVITY
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

            }//onBackPressed()

        })//callback(viewLifecycleOwner, object : OnBackPressedCallback(true)

        binding.finalLugar.text = lugar!![0]
        j.disableOrEnableButton(binding.finalNext, true)

        when (SharedApp.puntopartida.Partida) {

            "1" -> {

                j.replaceFragment(ExplicacionPremioFragment(), 2)

                audio(lugar[8])
                binding.finalExplicacion.typeWrite(this, lugar[7], 33L, j)

                binding.finalNext.setOnClickListener {

                    j.disableOrEnableButton(binding.finalNext, true)

                    if (binding.finalExplicacion.text == lugar[10]) {

                        if (SharedApp.modolibre.modo) {

                            SharedApp.puntopartida.Partida = "1"

                            JuegoActivity().slideView(
                                requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                                requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                                0
                            )

                            Navigation.findNavController(view).navigate(R.id.action_Final_Ruta)

                        }//if (SharedApp.modolibre.modo)

                        else {

                            terminado("2", view, j)

                        }//if (!SharedApp.modolibre.modo)

                    }//if (binding.finalExplicacion.text == lugar[10])

                    j.replaceFragment(ExplicacionFotosFragment(), 2)
                    audio(lugar[11])
                    requireActivity().supportFragmentManager.setFragmentResult("foto", bundleOf("imagen" to "9"))
                    binding.finalExplicacion.typeWrite(this, lugar[10], 33L, j)

                }//onClick()

            }//1

            "2" -> {

                j.replaceFragment(ExplicacionPremioFragment(), 2)

                audio(lugar[8])
                binding.finalExplicacion.typeWrite(this, lugar[7], 33L, j)

                binding.finalNext.setOnClickListener {

                    if (SharedApp.modolibre.modo) {

                        SharedApp.puntopartida.Partida = "1"

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                            0
                        )

                        Navigation.findNavController(view).navigate(R.id.action_Final_Ruta)

                    }//if (SharedApp.modolibre.modo)

                    else {

                        terminado("3", view, j)

                    }//if (!SharedApp.modolibre.modo)

                }//onClick

            }//2

            "3" -> {

                j.replaceFragment(ExplicacionPremioFragment(), 2)

                audio(lugar[10])
                binding.finalExplicacion.typeWrite(this, lugar[9], 33L, j)

                binding.finalNext.setOnClickListener {

                    if (binding.finalExplicacion.text == lugar[12]) {

                        if (SharedApp.modolibre.modo) {

                            SharedApp.puntopartida.Partida = "1"

                            JuegoActivity().slideView(
                                requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                                requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                                0
                            )

                            Navigation.findNavController(view).navigate(R.id.action_Final_Ruta)

                        }//if (SharedApp.modolibre.modo)

                        else {

                            terminado("4", view, j)

                        }//if (!SharedApp.modolibre.modo)

                    }//if (binding.finalExplicacion.text == lugar[12])

                    j.replaceFragment(ExplicacionFotosFragment(), 2)
                    requireActivity().supportFragmentManager.setFragmentResult("foto", bundleOf("imagen" to "11"))
                    audio(lugar[13])
                    binding.finalExplicacion.typeWrite(this, lugar[12], 33L, j)

                    j.disableOrEnableButton(binding.finalNext, true)

                }//onClick()

            }//3

            "4" -> {

                j.replaceFragment(ExplicacionPremioFragment(), 2)

                audio(lugar[7])
                binding.finalExplicacion.typeWrite(this, lugar[6], 33L, j)

                binding.finalNext.setOnClickListener {

                    if (binding.finalExplicacion.text == lugar[6]) {

                        if (SharedApp.modolibre.modo) {

                            SharedApp.puntopartida.Partida = "1"

                            JuegoActivity().slideView(
                                requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                                requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                                0
                            )

                            Navigation.findNavController(view).navigate(R.id.action_Final_Ruta)

                        }//if (SharedApp.modolibre.modo)

                        else {

                            terminado("5", view, j)

                        }//if (!SharedApp.modolibre.modo)

                    }//if (binding.finalExplicacion.text == lugar[6])

                }//onClick()

            }//4

            "5" -> {

                j.replaceFragment(ExplicacionPremioFragment(), 2)

                audio(lugar[10])
                binding.finalExplicacion.typeWrite(this, lugar[9], 33L, j)

                binding.finalNext.setOnClickListener {

                    if (binding.finalExplicacion.text == lugar[9]) {

                        if (SharedApp.modolibre.modo) {

                            SharedApp.puntopartida.Partida = "1"

                            JuegoActivity().slideView(
                                requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                                requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                                0
                            )

                            Navigation.findNavController(view).navigate(R.id.action_Final_Ruta)

                        }//if (SharedApp.modolibre.modo)

                        else {

                            terminado("6", view, j)

                        }//if (!SharedApp.modolibre.modo)

                    }//if (binding.finalExplicacion.text == lugar[9])

                }//onClick()

            }//5

            "6" -> {

                j.replaceFragment(ExplicacionPremioFragment(), 2)

                audio(lugar[10])
                binding.finalExplicacion.typeWrite(this, lugar[9], 33L, j)

                binding.finalNext.setOnClickListener {

                    if (binding.finalExplicacion.text == lugar[9]) {

                        if (SharedApp.modolibre.modo) {

                            SharedApp.puntopartida.Partida = "1"

                            JuegoActivity().slideView(
                                requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                                requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                                0
                            )

                            Navigation.findNavController(view).navigate(R.id.action_Final_Ruta)

                        }//if (SharedApp.modolibre.modo)

                        else {

                            terminado("7", view, j)

                        }//if (!SharedApp.modolibre.modo)

                    }//if (binding.finalExplicacion.text == lugar[9])

                }//onClick()

            }//6

            "7" -> {

                j.replaceFragment(ExplicacionPremioFragment(), 2)

                audio(lugar[10])
                binding.finalExplicacion.typeWrite(this, lugar[9], 33L, j)

                binding.finalNext.setOnClickListener {

                    if (binding.finalExplicacion.text == lugar[9]) {

                        if (SharedApp.modolibre.modo) {

                            SharedApp.puntopartida.Partida = "1"

                            JuegoActivity().slideView(
                                requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                                requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                                0
                            )

                            Navigation.findNavController(view).navigate(R.id.action_Final_Ruta)

                        }//if (SharedApp.modolibre.modo)

                        else {

                            terminado("8", view, j)

                        }//if (!SharedApp.modolibre.modo)

                    }//if (binding.finalExplicacion.text == lugar[9])

                }//onClick()

            }//7

            "8" -> {

                j.replaceFragment(AnimacionFinalFragment(), 2)

                audio(lugar[7])

                binding.finalExplicacion.typeWrite(this, lugar[6], 33L, j)

                binding.finalNext.setOnClickListener {

                    if ((binding.finalExplicacion.text == lugar[6]) || (binding.finalExplicacion.text == lugar[8])) {

                        if (binding.finalExplicacion.text == lugar[6]) {

                            j.replaceFragment(AnimacionCreditosFragment(), 2)

                            binding.finalExplicacion.typeWrite(this, lugar[8], 33L, j)
                            audio(lugar[9])

                        }//if (binding.finalExplicacion.text == lugar[6])

                        else if (binding.finalExplicacion.text == lugar[8]) {

                            terminado("9", view, j)

                        }//else if (binding.finalExplicacion.text == lugar[8])

                    }//if ((binding.finalExplicacion.text == lugar[6]) || (binding.finalExplicacion.text == lugar[8]))

                }//onClick()

            }//8

        }//when (SharedApp.puntopartida.Partida)

    }//onViewCreated(view: View, savedInstanceState: Bundle?)

    //GUARDAR LA PARTIDA DESPUES DE RECOGER EL PREMIO
    fun terminado(punto: String, view: View, j: JuegoActivity) {

        SharedApp.puntopartida.Partida = punto
        db.actualizarPartida(SharedApp.users.user, punto)

        if (punto == "9") {

            SharedApp.puntopartida.Partida = ""
            SharedApp.users.user = ""
            SharedApp.tipousu.tipo = ""

            j.startActivity(Intent(requireContext(), MainActivity::class.java))
            j.finish()

        }//if (punto == "9")

        else {

            j.navigationView.getHeaderView(0)
                .findViewById<TextView>(R.id.headerPunto).text = punto

            JuegoActivity().slideView(
                requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                0
            )

            Navigation.findNavController(view).navigate(R.id.action_Final_Ruta)

        }///if (punto != "9")

    }//terminado(punto: String, view: View, j: JuegoActivity)

    //EXPLICACIONES ANIMADAS
    fun TextView.typeWrite(lifecycleOwner: LifecycleOwner, text: String, intervalMs: Long, juego: JuegoActivity) {

        this@typeWrite.text = ""

        if(::routine.isInitialized) {

            routine.cancel()

        }//if(::routine.isInitialized)

        lifecycleOwner.lifecycleScope.launch {

            repeat(text.length) {

                delay(intervalMs)
                this@typeWrite.text = text.take(it + 1)

            }//repeat(text.length)

            juego.disableOrEnableButton(binding.finalNext, false)

        }//launch

    }//TextView.typeWrite(lifecycleOwner: LifecycleOwner, text: String, intervalMs: Long, juego: JuegoActivity)

    //REPRODUCIR AUDIOS DE LAS EXPLICACIONES
    fun audio(audio: String) {

        if (::mp.isInitialized) {

            mp.stop()

        }//if (::mp.isInitialized)

        mp = MediaPlayer.create(requireContext(), audio.toInt())

        mp.start()

    }//audio(audio: String)

    override fun onPause() {
        super.onPause()

        if (::mp.isInitialized) {

            mp.stop()

        }//if (::mp.isInitialized)

    }//onPause()

    override fun onDestroy() {
        super.onDestroy()

        if (::mp.isInitialized) {

            mp.stop()

        }//if (::mp.isInitialized)

    }//onDestroy()

}//ExplicacionFinalFragment()