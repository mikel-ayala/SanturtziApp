package com.g2.santurtziapp.fragments.explicaciones

import android.app.AlertDialog
import android.content.DialogInterface
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.g2.santurtziapp.R
import com.g2.santurtziapp.SharedApp
import com.g2.santurtziapp.activitidades.JuegoActivity
import com.g2.santurtziapp.databinding.FragmentExplicacionJuegoBinding
import com.g2.santurtziapp.fragments.juegos.*
import kotlinx.android.synthetic.main.fragment_explicacion_juego.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ExplicacionJuegoFragment : Fragment() {

    lateinit var binding: FragmentExplicacionJuegoBinding
    var v: View? = null
    lateinit var mp: MediaPlayer
    private lateinit var routine: CoroutineScope

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentExplicacionJuegoBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val j: JuegoActivity? = activity as JuegoActivity?
        val lugar = j?.checkPuntoPartida(SharedApp.puntopartida.Partida)

        var heightDp = resources.displayMetrics.run { heightPixels / density }

        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle(R.string.Salir)
                setMessage(R.string.volver)
                setPositiveButton(R.string.confirmar,
                    DialogInterface.OnClickListener { dialog, id ->

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment1Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment1Juego).height,
                            0
                        )

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                            0
                        )

                        Navigation.findNavController(view).navigate(R.id.action_Juego_Hasiera)
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

                alertDialog?.show()

            }

        })

        binding.juegoLugar.text = lugar!![0]
        j.disableOrEnableButton(binding.juegoNext, true)

        when(SharedApp.puntopartida.Partida){

            "1" -> {

                j.replaceFragment(Juego1Fragment(), 2)

                requireActivity().supportFragmentManager.setFragmentResultListener("juego1", viewLifecycleOwner) { juego, bundle ->

                    val result = bundle.getString("terminado")

                    if (result != null && result == "yes") {

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment1Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment1Juego).height,
                            0
                        )

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                            (heightDp / 1.10).toInt()
                        )

                        Handler().postDelayed(Runnable {
                            Navigation.findNavController(view).navigate(R.id.action_Juego_Final)
                        }, 500)
                    }
                }

            }
            "2" -> {

                j.replaceFragment(Juego2Fragment(), 2)

                requireActivity().supportFragmentManager.setFragmentResultListener("juego2", viewLifecycleOwner) { juego, bundle ->

                    val result = bundle.getString("terminado")

                    if (result != null && result == "yes") {

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment1Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment1Juego).height,
                            0
                        )

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                            (heightDp / 1.10).toInt()
                        )

                        Handler().postDelayed(Runnable {
                            Navigation.findNavController(view).navigate(R.id.action_Juego_Final)
                        }, 500)
                    }
                }

            }
            "3" -> {

                j.replaceFragment(Juego3Fragment(), 2)

                j.button.hide()

                audio(lugar[8])
                juegoExplicacion.typeWrite(this, lugar[7], 33L, j)

                requireActivity().supportFragmentManager.setFragmentResultListener("juego3", viewLifecycleOwner) { juego, bundle ->

                    val result = bundle.getString("terminado")

                    if (result != null && result == "yes") {

                        j.disableOrEnableButton(binding.juegoNext, false)

                    }
                }

                binding.juegoNext.setOnClickListener {

                    j.button.show()

                    JuegoActivity().slideView(
                        requireActivity().findViewById<FrameLayout>(R.id.fragment1Juego),
                        requireActivity().findViewById<FrameLayout>(R.id.fragment1Juego).height,
                        0
                    )

                    JuegoActivity().slideView(
                        requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                        requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                        (heightDp / 1.10).toInt()
                    )

                    Handler().postDelayed(Runnable {
                        Navigation.findNavController(view).navigate(R.id.action_Juego_Final)
                    }, 500)

                }

            }
            "4" -> {

                j.replaceFragment(Juego4Fragment(), 2)

                audio(lugar[5])
                juegoExplicacion.typeWrite(this, lugar[4], 33L, j)

                requireActivity().supportFragmentManager.setFragmentResultListener("juego4", viewLifecycleOwner) { juego, bundle ->

                    val result = bundle.getString("terminado")

                    if (result != null && result == "yes") {

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment1Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment1Juego).height,
                            0
                        )

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                            (heightDp / 1.10).toInt()
                        )

                        Handler().postDelayed(Runnable {
                            Navigation.findNavController(view).navigate(R.id.action_Juego_Final)
                        }, 500)

                    }
                }

            }
            "5" -> {

                j.replaceFragment(Juego5Fragment(), 2)

                audio(lugar[6])
                juegoExplicacion.typeWrite(this, lugar[7], 33L, j)

                requireActivity().supportFragmentManager.setFragmentResultListener("juego5", viewLifecycleOwner) { juego, bundle ->

                    val result = bundle.getString("terminado")

                    if (result != null && result == "yes") {

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment1Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment1Juego).height,
                            0
                        )

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                            (heightDp / 1.10).toInt()
                        )

                        Handler().postDelayed(Runnable {
                            Navigation.findNavController(view).navigate(R.id.action_Juego_Final)
                        }, 500)
                    }
                }

            }
            "6" -> {

                j.replaceFragment(Juego6Fragment(), 2)

                audio(lugar[6])
                juegoExplicacion.typeWrite(this, lugar[7], 33L, j)

                requireActivity().supportFragmentManager.setFragmentResultListener("juego6", viewLifecycleOwner) { juego, bundle ->

                    val result = bundle.getString("terminado")

                    if (result != null && result == "yes") {

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment1Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment1Juego).height,
                            0
                        )

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                            (heightDp / 1.10).toInt()
                        )

                        Handler().postDelayed(Runnable {
                            Navigation.findNavController(view).navigate(R.id.action_Juego_Final)
                        }, 500)

                    }
                }

            }
            "7" -> {

                j.replaceFragment(Juego7Fragment(), 2)

                audio(lugar[6])
                juegoExplicacion.typeWrite(this, lugar[7], 33L, j)

                requireActivity().supportFragmentManager.setFragmentResultListener("juego7", viewLifecycleOwner) { juego, bundle ->

                    val result = bundle.getString("terminado")

                    if (result != null && result == "yes") {

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment1Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment1Juego).height,
                            0
                        )

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                            (heightDp / 1.10).toInt()
                        )

                        Handler().postDelayed(Runnable {
                            Navigation.findNavController(view).navigate(R.id.action_Juego_Final)
                        }, 500)
                    }
                }

            }
            "8" -> {

                j.replaceFragment(Juego8Fragment(), 2)

                audio(lugar[5])
                juegoExplicacion.typeWrite(this, lugar[4], 33L, j)

                requireActivity().supportFragmentManager.setFragmentResultListener("juego8", viewLifecycleOwner) { juego, bundle ->

                    val result = bundle.getString("terminado")

                    if (result != null && result == "yes") {

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment1Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment1Juego).height,
                            0
                        )

                        JuegoActivity().slideView(
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego),
                            requireActivity().findViewById<FrameLayout>(R.id.fragment2Juego).height,
                            (heightDp / 1.10).toInt()
                        )

                        Handler().postDelayed(Runnable {
                            Navigation.findNavController(view).navigate(R.id.action_Juego_Final)
                        }, 500)
                    }
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

            repeat(text.length) {

                delay(intervalMs)
                this@typeWrite.text = text.take(it + 1)

            }

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
        if (::mp.isInitialized){
            mp.stop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::mp.isInitialized){
            mp.stop()
        }
    }

}