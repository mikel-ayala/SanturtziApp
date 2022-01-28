package com.g2.santurtziapp.fragments.explicaciones

import android.app.AlertDialog
import android.content.DialogInterface
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.g2.santurtziapp.DB
import com.g2.santurtziapp.R
import com.g2.santurtziapp.SharedApp
import com.g2.santurtziapp.activitidades.JuegoActivity
import com.g2.santurtziapp.databinding.FragmentExplicacionSitioBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ExplicacionSitioFragment : Fragment() {

    lateinit var binding: FragmentExplicacionSitioBinding
    lateinit var db: DB
    lateinit var mp: MediaPlayer
    lateinit var cancion: MediaPlayer
    private lateinit var routine: CoroutineScope

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentExplicacionSitioBinding.inflate(layoutInflater)

        return binding.root

    }//onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = DB(requireContext(), "db", null, 1)
        val j: JuegoActivity? = activity as JuegoActivity?
        val lugar = j?.checkPuntoPartida(SharedApp.puntopartida.Partida)

        //DIALOGO PARA IR AL MAPA
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle(R.string.Salir)
                setMessage(R.string.volver)
                setPositiveButton(R.string.confirmar,
                    DialogInterface.OnClickListener { dialog, id ->

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                            0
                        )

                        Navigation.findNavController(view).navigate(R.id.action_Sitio_Hasiera)
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

                if (binding.sitioExplicacion.text == lugar!![2]) {

                    alertDialog?.show()

                }//if (binding.sitioExplicacion.text == lugar!![2])

            }//onBackPressed()

        })//callback(viewLifecycleOwner, object : OnBackPressedCallback(true)

        var heightDp = resources.displayMetrics.run { heightPixels / density }

        j!!.replaceFragment(ExplicacionFotosFragment(), 2)

        binding.sitioLugar.text = lugar!![0]
        requireActivity().supportFragmentManager.setFragmentResult("foto", bundleOf("imagen" to "1"))
        audio(lugar[3])
        binding.sitioExplicacion.typeWrite(this, lugar[2], 33L, j)
        j.disableOrEnableButton(binding.sitioPrev, true)
        j.disableOrEnableButton(binding.sitioNext, true)

        when (SharedApp.puntopartida.Partida) {

            "0" -> {

                binding.sitioPrev.setOnClickListener {

                    audio(lugar[3])
                    binding.sitioExplicacion.typeWrite(this, lugar[2], 33L, j)
                    j.disableOrEnableButton(binding.sitioPrev, true)

                }//onClick()

                binding.sitioNext.setOnClickListener {

                    j.disableOrEnableButton(binding.sitioNext, true)

                    if (binding.sitioExplicacion.text == lugar[4]) {

                        SharedApp.puntopartida.Partida = "1"
                        db.actualizarPartida(SharedApp.users.user, SharedApp.puntopartida.Partida)

                        j.navigationView.getHeaderView(0)
                            .findViewById<TextView>(R.id.headerPunto).text =
                            SharedApp.puntopartida.Partida

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                            0
                        )

                        Navigation.findNavController(view).navigate(R.id.action_Sitio_Hasiera)

                    }//if (binding.sitioExplicacion.text == lugar[4])

                    audio(lugar[5])
                    binding.sitioExplicacion.typeWrite(this, lugar[4], 33L, j)
                    j.disableOrEnableButton(binding.sitioPrev, false)

                }//onClick()

            }//0

            "1" -> {

                binding.sitioPrev.setOnClickListener {

                    requireActivity().supportFragmentManager.setFragmentResult("foto", bundleOf("imagen" to "1"))
                    audio(lugar[3])
                    binding.sitioExplicacion.typeWrite(this, lugar[2], 33L, j)
                    j.disableOrEnableButton(binding.sitioPrev, true)

                }//onClick()

                binding.sitioNext.setOnClickListener {

                    if (binding.sitioExplicacion.text == lugar[5]) {

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                            0
                        )

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment1Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment1Juego).height,
                            requireActivity().findViewById<LinearLayout>(R.id.juegoLinearL).height)

                        Navigation.findNavController(view).navigate(R.id.action_Sitio_Juego)

                    }//if (binding.sitioExplicacion.text == lugar[5])

                    requireActivity().supportFragmentManager.setFragmentResult("foto", bundleOf("imagen" to "4"))
                    audio(lugar[6])
                    binding.sitioExplicacion.typeWrite(this, lugar[5], 33L, j)

                    j.disableOrEnableButton(binding.sitioPrev, false)
                    j.disableOrEnableButton(binding.sitioNext, true)

                }//onClick()

            }//1

            "2" -> {

                binding.sitioPrev.setOnClickListener {

                    requireActivity().supportFragmentManager.setFragmentResult("foto", bundleOf("imagen" to "1"))
                    j.disableOrEnableButton(binding.sitioPrev, true)
                    audio(lugar[3])
                    binding.sitioExplicacion.typeWrite(this, lugar[2], 33L, j)

                }//onClick

                binding.sitioNext.setOnClickListener {

                    if (binding.sitioExplicacion.text == lugar[5]) {

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                            0
                        )

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment1Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment1Juego).height,
                            requireActivity().findViewById<LinearLayout>(R.id.juegoLinearL).height)

                        Navigation.findNavController(view).navigate(R.id.action_Sitio_Juego)

                    }//if (binding.sitioExplicacion.text == lugar[5])

                    requireActivity().supportFragmentManager.setFragmentResult("foto", bundleOf("imagen" to "4"))
                    audio(lugar[6])
                    binding.sitioExplicacion.typeWrite(this, lugar[5], 33L, j)

                    j.disableOrEnableButton(binding.sitioPrev, false)
                    j.disableOrEnableButton(binding.sitioNext, true)

                }//onClick()

            }//2

            "3" -> {

                binding.sitioPrev.setOnClickListener {

                    requireActivity().supportFragmentManager.setFragmentResult("foto", bundleOf("imagen" to "1"))
                    audio(lugar[3])
                    binding.sitioExplicacion.typeWrite(this, lugar[2], 33L, j)
                    j.disableOrEnableButton(binding.sitioPrev, true)

                }//onClick()

                binding.sitioNext.setOnClickListener {

                    if (binding.sitioExplicacion.text == lugar[5]) {

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                            (heightDp / 2.10).toInt()
                        )

                        Navigation.findNavController(view).navigate(R.id.action_Sitio_Juego)

                    }//if (binding.sitioExplicacion.text == lugar[5])

                    requireActivity().supportFragmentManager.setFragmentResult("foto", bundleOf("imagen" to "4"))
                    audio(lugar[6])
                    binding.sitioExplicacion.typeWrite(this, lugar[5], 33L, j)

                    j.disableOrEnableButton(binding.sitioPrev, false)
                    j.disableOrEnableButton(binding.sitioNext, true)

                }//onClick

            }//3

            "4" -> {

                binding.sitioNext.setOnClickListener {

                    JuegoActivity().slideView(
                        requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                        requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                        (heightDp / 2.10).toInt()
                    )

                    Navigation.findNavController(view).navigate(R.id.action_Sitio_Juego)

                }//onClick()

            }//4

            "5" -> {

                binding.sitioPrev.setOnClickListener {

                    requireActivity().supportFragmentManager.setFragmentResult(
                        "foto",
                        bundleOf("imagen" to "1")
                    )
                    audio(lugar[3])
                    binding.sitioExplicacion.typeWrite(this, lugar[2], 33L, j)
                    j.disableOrEnableButton(binding.sitioPrev, true)

                }//onClick()

                binding.sitioNext.setOnClickListener {

                    if (binding.sitioExplicacion.text == lugar[5]) {

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                            (heightDp / 2.10).toInt()
                        )

                        Navigation.findNavController(view).navigate(R.id.action_Sitio_Juego)

                    }//if (binding.sitioExplicacion.text == lugar[5])

                    requireActivity().supportFragmentManager.setFragmentResult(
                        "foto",
                        bundleOf("imagen" to "4")
                    )
                    audio(lugar[6])
                    binding.sitioExplicacion.typeWrite(this, lugar[5], 33L, j)

                    j.disableOrEnableButton(binding.sitioPrev, false)
                    j.disableOrEnableButton(binding.sitioNext, true)

                }//onClick()

            }//5

            "6" -> {

                binding.sitioPrev.setOnClickListener {

                    requireActivity().supportFragmentManager.setFragmentResult(
                        "foto",
                        bundleOf("imagen" to "1")
                    )
                    audio(lugar[3])
                    binding.sitioExplicacion.typeWrite(this, lugar[2], 33L, j)
                    j.disableOrEnableButton(binding.sitioPrev, true)

                }//onClick()

                binding.sitioNext.setOnClickListener {

                    if (binding.sitioExplicacion.text == lugar[5]) {

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                            (heightDp / 2.10).toInt()
                        )

                        Navigation.findNavController(view).navigate(R.id.action_Sitio_Juego)

                    }//if (binding.sitioExplicacion.text == lugar[5])

                    requireActivity().supportFragmentManager.setFragmentResult(
                        "foto",
                        bundleOf("imagen" to "4")
                    )
                    audio(lugar[6])
                    binding.sitioExplicacion.typeWrite(this, lugar[5], 33L, j)

                    j.disableOrEnableButton(binding.sitioPrev, false)
                    j.disableOrEnableButton(binding.sitioNext, true)

                }//onClick()

            }//6

            "7" -> {

                cancion = MediaPlayer.create(requireContext(), R.raw.desdesanturceabilbao)


                binding.sitioPrev.setOnClickListener {

                    requireActivity().supportFragmentManager.setFragmentResult(
                        "foto",
                        bundleOf("imagen" to "1")
                    )

                    binding.imgmusica.isVisible = false
                    binding.textmusica.isVisible = false

                    if (cancion.isPlaying) {

                        cancion.stop()

                    }//if (cancion.isPlaying)

                    audio(lugar[3])
                    binding.sitioExplicacion.typeWrite(this, lugar[2], 33L, j)
                    j.disableOrEnableButton(binding.sitioPrev, true)

                }//onClick()

                binding.sitioNext.setOnClickListener {

                    if (binding.sitioExplicacion.text == lugar[5]) {

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                            (heightDp / 2.10).toInt()
                        )

                        Navigation.findNavController(view).navigate(R.id.action_Sitio_Juego)

                    }//if (binding.sitioExplicacion.text == lugar[5])

                    requireActivity().supportFragmentManager.setFragmentResult(
                        "foto",
                        bundleOf("imagen" to "4")
                    )
                    audio(lugar[6])

                    binding.sitioExplicacion.typeWrite(this, lugar[5], 33L, j)

                    binding.imgmusica.isVisible = true
                    binding.textmusica.isVisible = true

                    binding.imgmusica.setOnClickListener {

                        if (cancion.isPlaying) {

                            cancion.stop()
                            binding.imgmusica.pauseAnimation()

                        }//if (cancion.isPlaying)

                        else {

                            cancion = MediaPlayer.create(requireContext(), R.raw.desdesanturceabilbao)
                            mp.stop()
                            binding.imgmusica.playAnimation()
                            cancion.start()

                        }//if (!cancion.isPlaying)

                    }//onClick

                    j.disableOrEnableButton(binding.sitioPrev, false)
                    j.disableOrEnableButton(binding.sitioNext, true)

                }//onClick()

            }//7

            "8" -> {

                binding.sitioNext.setOnClickListener {

                    Navigation.findNavController(view).navigate(R.id.action_Sitio_Juego)

                }//onClick()

            }//8

        }//when (SharedApp.puntopartida.Partida)

    }//onViewCreated(view: View, savedInstanceState: Bundle?)

    //EXPLICACIONES ANIMADAS
    fun TextView.typeWrite(lifecycleOwner: LifecycleOwner, text: String, intervalMs: Long, juego: JuegoActivity) {

        this@typeWrite.text = ""

        if(::routine.isInitialized){

            routine.cancel()

        }//if(::routine.isInitialized)

        lifecycleOwner.lifecycleScope.launch {

            routine = this

            repeat(text.length) {

                delay(intervalMs)
                this@typeWrite.text = text.take(it + 1)

            }//repeat(text.length)

            juego.disableOrEnableButton(binding.sitioNext, false)

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

        if (::cancion.isInitialized) {

            if (cancion.isPlaying) {

                cancion.stop()

            }//if (cancion.isPlaying)

        }//if (::cancion.isInitialized)

        binding.textmusica.isVisible = false

    }//onPause()

    override fun onDestroy() {
        super.onDestroy()

        if (::mp.isInitialized) {

            mp.stop()

        }//if (::mp.isInitialized)

        if (::cancion.isInitialized) {

            if (cancion.isPlaying) {

                cancion.stop()

            }//if (cancion.isPlaying)

        }//if (::cancion.isInitialized)

        binding.imgmusica.isVisible = false
        binding.textmusica.isVisible = false

    }//onDestroy()

}//ExplicacionSitioFragment()