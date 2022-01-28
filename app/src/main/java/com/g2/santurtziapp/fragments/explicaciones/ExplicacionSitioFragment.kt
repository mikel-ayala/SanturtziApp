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
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentExplicacionSitioBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = DB(requireContext(), "db", null, 1)
        val j: JuegoActivity? = activity as JuegoActivity?
        val lugar = j?.checkPuntoPartida(SharedApp.puntopartida.Partida)

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
                    })
                setNegativeButton(R.string.menu_cerrar,
                    DialogInterface.OnClickListener { dialog, id ->

                        dialog.dismiss()

                    })
            }

            builder.create()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                if (binding.sitioExplicacion.text == lugar!![2]) {

                    alertDialog?.show()

                }

            }

        })

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

                }

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
                    }

                    audio(lugar[5])
                    binding.sitioExplicacion.typeWrite(this, lugar[4], 33L, j)
                    j.disableOrEnableButton(binding.sitioPrev, false)

                }

            }
            "1" -> {

                binding.sitioPrev.setOnClickListener {

                    requireActivity().supportFragmentManager.setFragmentResult("foto", bundleOf("imagen" to "1"))
                    audio(lugar[3])
                    binding.sitioExplicacion.typeWrite(this, lugar[2], 33L, j)
                    j.disableOrEnableButton(binding.sitioPrev, true)

                }

                binding.sitioNext.setOnClickListener {

                    if (binding.sitioExplicacion.text == lugar[5]){

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

                    }

                    requireActivity().supportFragmentManager.setFragmentResult("foto", bundleOf("imagen" to "4"))
                    audio(lugar[6])
                    binding.sitioExplicacion.typeWrite(this, lugar[5], 33L, j)

                    j.disableOrEnableButton(binding.sitioPrev, false)
                    j.disableOrEnableButton(binding.sitioNext, true)

                }

            }
            "2" -> {

                binding.sitioPrev.setOnClickListener {

                    requireActivity().supportFragmentManager.setFragmentResult("foto", bundleOf("imagen" to "1"))
                    j.disableOrEnableButton(binding.sitioPrev, true)
                    audio(lugar[3])
                    binding.sitioExplicacion.typeWrite(this, lugar[2], 33L, j)

                }

                binding.sitioNext.setOnClickListener {

                    if (binding.sitioExplicacion.text == lugar[5]){

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

                    }

                    requireActivity().supportFragmentManager.setFragmentResult("foto", bundleOf("imagen" to "4"))
                    audio(lugar[6])
                    binding.sitioExplicacion.typeWrite(this, lugar[5], 33L, j)

                    j.disableOrEnableButton(binding.sitioPrev, false)
                    j.disableOrEnableButton(binding.sitioNext, true)

                }
            }
            "3" -> {

                binding.sitioPrev.setOnClickListener {

                    requireActivity().supportFragmentManager.setFragmentResult("foto", bundleOf("imagen" to "1"))
                    audio(lugar[3])
                    binding.sitioExplicacion.typeWrite(this, lugar[2], 33L, j)
                    j.disableOrEnableButton(binding.sitioPrev, true)

                }

                binding.sitioNext.setOnClickListener {

                    if (binding.sitioExplicacion.text == lugar[5]){

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                            (heightDp / 2.10).toInt()
                        )

                        Navigation.findNavController(view).navigate(R.id.action_Sitio_Juego)

                    }

                    requireActivity().supportFragmentManager.setFragmentResult("foto", bundleOf("imagen" to "4"))
                    audio(lugar[6])
                    binding.sitioExplicacion.typeWrite(this, lugar[5], 33L, j)

                    j.disableOrEnableButton(binding.sitioPrev, false)
                    j.disableOrEnableButton(binding.sitioNext, true)

                }

            }
            "4" -> {

                binding.sitioNext.setOnClickListener {

                    JuegoActivity().slideView(
                        requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                        requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                        (heightDp / 2.10).toInt()
                    )

                    Navigation.findNavController(view).navigate(R.id.action_Sitio_Juego)

                }

            }
            "5" -> {

                binding.sitioPrev.setOnClickListener {

                    requireActivity().supportFragmentManager.setFragmentResult(
                        "foto",
                        bundleOf("imagen" to "1")
                    )
                    audio(lugar[3])
                    binding.sitioExplicacion.typeWrite(this, lugar[2], 33L, j)
                    j.disableOrEnableButton(binding.sitioPrev, true)

                }

                binding.sitioNext.setOnClickListener {

                    if (binding.sitioExplicacion.text == lugar[5]) {

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                            (heightDp / 2.10).toInt()
                        )

                        Navigation.findNavController(view).navigate(R.id.action_Sitio_Juego)

                    }

                    requireActivity().supportFragmentManager.setFragmentResult(
                        "foto",
                        bundleOf("imagen" to "4")
                    )
                    audio(lugar[6])
                    binding.sitioExplicacion.typeWrite(this, lugar[5], 33L, j)

                    j.disableOrEnableButton(binding.sitioPrev, false)
                    j.disableOrEnableButton(binding.sitioNext, true)

                }

            }
            "6" -> {

                binding.sitioPrev.setOnClickListener {

                    requireActivity().supportFragmentManager.setFragmentResult(
                        "foto",
                        bundleOf("imagen" to "1")
                    )
                    audio(lugar[3])
                    binding.sitioExplicacion.typeWrite(this, lugar[2], 33L, j)
                    j.disableOrEnableButton(binding.sitioPrev, true)

                }

                binding.sitioNext.setOnClickListener {

                    if (binding.sitioExplicacion.text == lugar[5]) {

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                            (heightDp / 2.10).toInt()
                        )

                        Navigation.findNavController(view).navigate(R.id.action_Sitio_Juego)

                    }

                    requireActivity().supportFragmentManager.setFragmentResult(
                        "foto",
                        bundleOf("imagen" to "4")
                    )
                    audio(lugar[6])
                    binding.sitioExplicacion.typeWrite(this, lugar[5], 33L, j)

                    j.disableOrEnableButton(binding.sitioPrev, false)
                    j.disableOrEnableButton(binding.sitioNext, true)

                }

            }

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

                    }

                    audio(lugar[3])
                    binding.sitioExplicacion.typeWrite(this, lugar[2], 33L, j)
                    j.disableOrEnableButton(binding.sitioPrev, true)

                }

                binding.sitioNext.setOnClickListener {

                    if (binding.sitioExplicacion.text == lugar[5]) {

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                            (heightDp / 2.10).toInt()
                        )

                        Navigation.findNavController(view).navigate(R.id.action_Sitio_Juego)

                    }

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

                        } else {

                            cancion = MediaPlayer.create(requireContext(), R.raw.desdesanturceabilbao)
                            mp.stop()
                            binding.imgmusica.playAnimation()
                            cancion.start()

                        }

                    }

                    j.disableOrEnableButton(binding.sitioPrev, false)
                    j.disableOrEnableButton(binding.sitioNext, true)

                }

            }
            "8" -> {

                binding.sitioNext.setOnClickListener {

                    Navigation.findNavController(view).navigate(R.id.action_Sitio_Juego)

                }

            }

        }

    }

    fun TextView.typeWrite(lifecycleOwner: LifecycleOwner, text: String, intervalMs: Long, juego: JuegoActivity) {

        this@typeWrite.text = ""

        if(::routine.isInitialized){

            routine.cancel()
        }

        lifecycleOwner.lifecycleScope.launch {

            routine = this

            repeat(text.length) {

                delay(intervalMs)
                this@typeWrite.text = text.take(it + 1)

            }

            juego.disableOrEnableButton(binding.sitioNext, false)

        }

    }

    fun audio(audio: String) {

        if (::mp.isInitialized) {

            mp.stop()

        }

        mp = MediaPlayer.create(requireContext(), audio.toInt())

        mp.start()

    }

    override fun onPause() {
        super.onPause()
        if (::mp.isInitialized) {
            mp.stop()
        }

        if (::cancion.isInitialized) {

            if (cancion.isPlaying) {
                cancion.stop()
            }
        }

        binding.textmusica.isVisible = false

    }

    override fun onDestroy() {
        super.onDestroy()
        if (::mp.isInitialized){
            mp.stop()
        }

        if (::cancion.isInitialized) {

            if (cancion.isPlaying) {
                cancion.stop()
            }
        }

        binding.imgmusica.isVisible = false
        binding.textmusica.isVisible = false

    }

}