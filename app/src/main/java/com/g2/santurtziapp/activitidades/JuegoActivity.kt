package com.g2.santurtziapp.activitidades

import android.animation.AnimatorSet
import android.os.Bundle
import com.g2.santurtziapp.databinding.ActivityJuegoBinding
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.g2.santurtziapp.R
import kotlin.collections.ArrayList


class JuegoActivity : DrawerActivity() {

    private lateinit var binding: ActivityJuegoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityJuegoBinding.inflate(layoutInflater)

        setContentView(binding.root)

    }//onCreate(savedInstanceState: Bundle?)

    //CAMBIAR EL TAMAÑO DE LOS FRAGMENTS DE MANERA ANIMADA
    fun slideView(view: View, currentHeight: Int, newHeight: Int) {

        var slideAnimator = ValueAnimator
            .ofInt(currentHeight, newHeight)
            .setDuration(500)

        slideAnimator.addUpdateListener {
            var value = it.animatedValue
            view.layoutParams.height = value as Int
            view.requestLayout();
        }//updateListener

        var animationSet = AnimatorSet()
        animationSet.interpolator = AccelerateDecelerateInterpolator()
        animationSet.play(slideAnimator)
        animationSet.start()

    }//slideView(view: View, currentHeight: Int, newHeight: Int)

    //RECOLECTAR TODOS LOS DATOS DEL LUGAR AL QUE LE TOCA IR
    fun checkPuntoPartida(punto: String): ArrayList<String>? {

        lateinit var nombre: String
        lateinit var foto1: String
        lateinit var texto1: String
        lateinit var audio1: String
        lateinit var foto2: String
        lateinit var texto2: String
        lateinit var audio2: String
        lateinit var foto3: String
        lateinit var texto3: String
        lateinit var audio3: String
        lateinit var foto4: String
        lateinit var texto4: String
        lateinit var audio4: String
        lateinit var foto5: String
        lateinit var texto5: String
        lateinit var audio5: String
        var lugar: ArrayList<String>? = null

        when(punto) {

            "0" -> {


                nombre = getString(R.string.app_name)

                foto1 = R.drawable.santurtzi.toString()
                texto1 = getString(R.string.hasiera1)
                audio1 = R.raw.hasiera1audioa.toString()

                texto2 = getString(R.string.hasiera2)
                audio2 = R.raw.hasiera2audioa.toString()

                lugar = arrayListOf(nombre, foto1, texto1, audio1, texto2, audio2)

            }//0

            "1" -> {

                nombre = getString(R.string.parada1)

                foto1 = R.drawable.portua1argazkia.toString()
                texto1 = getString(R.string.portua1)
                audio1 = R.raw.portua1audioa.toString()

                foto2 = R.drawable.portua2argazkia.toString()
                texto2 = getString(R.string.portua2)
                audio2 = R.raw.portua2audioa.toString()

                texto3 = getString(R.string.portua3)
                audio3 = R.raw.portua3audioa.toString()

                foto4 = R.drawable.portua3argazkia.toString()
                texto4 = getString(R.string.portua4)
                audio4 = R.raw.portua4audioa.toString()

                lugar = arrayListOf(nombre, foto1, texto1, audio1, foto2, texto2, audio2, texto3, audio3, foto4, texto4, audio4)

            }//1

            "2" -> {

                nombre = getString(R.string.parada2)

                foto1 = R.drawable.kofradia1argazkia.toString()
                texto1 = getString(R.string.kofradia1)
                audio1 = R.raw.kofradia1audioa.toString()

                foto3 = R.drawable.kofradia2argazkia.toString()
                texto3 = getString(R.string.kofradia2)
                audio3 = R.raw.kofradia2audioa.toString()

                texto4 = getString(R.string.kofradia3)
                audio4 = R.raw.kofradia3audioa.toString()

                lugar = arrayListOf(nombre, foto1, texto1, audio1, foto3, texto3, audio3, texto4, audio4)

            }//2

            "3" -> {

                nombre = getString(R.string.parada3)

                foto1 = R.drawable.sotera1argazkia.toString()
                texto1 = getString(R.string.sotera1)
                audio1 = R.raw.sotera1audioa.toString()

                foto2 = R.drawable.sotera2argazkia.toString()
                texto2 = getString(R.string.sotera2)
                audio2 = R.raw.sotera2audioa.toString()

                texto3 = getString(R.string.sotera3)
                audio3 = R.raw.sotera3audioa.toString()

                texto4 = getString(R.string.sotera4)
                audio4 = R.raw.sotera4audioa.toString()

                foto5 = R.drawable.sotera3izaskunetxaniz.toString()
                texto5 = getString(R.string.sotera5)
                audio5 = R.raw.sotera5audioa.toString()

                lugar = arrayListOf(nombre, foto1, texto1, audio1, foto2, texto2, audio2, texto3, audio3, texto4, audio4, foto5, texto5, audio5)

            }//3

            "4" -> {

                nombre = getString(R.string.parada4)

                foto1 = R.drawable.kioskoa1argazkia.toString()
                texto1 = getString(R.string.kioskoa1)
                audio1 = R.raw.kioskoa1audioa.toString()

                texto3 = getString(R.string.kioskoa2)
                audio3 = R.raw.kioskoa2audioa.toString()

                texto4 = getString(R.string.kioskoa3)
                audio4 = R.raw.kioskoa3audioa.toString()

                lugar = arrayListOf(nombre, foto1, texto1, audio1, texto3, audio3, texto4, audio4)

            }//4

            "5" -> {

                nombre = getString(R.string.parada5)

                foto1 = R.drawable.udaletxea1argazkia.toString()
                texto1 = getString(R.string.udaletxea1)
                audio1 = R.raw.udaletxea1audioa.toString()

                foto2 = R.drawable.udaletxea2argazkia.toString()
                texto2 = getString(R.string.udaletxea2)
                audio2 = R.raw.udaletxea2audioa.toString()

                texto3 = getString(R.string.udaletxea3)
                audio3 = R.raw.udaletxea3audioa.toString()

                texto4 = getString(R.string.udaletxea4)
                audio4 = R.raw.udaletxea4audioa.toString()

                lugar = arrayListOf(nombre, foto1, texto1, audio1, foto2, texto2, audio2, texto3, audio3, texto4, audio4)

            }//5

            "6" -> {

                nombre = getString(R.string.parada6)

                foto1 = R.drawable.auzoa1argazkia.toString()
                texto1 = getString(R.string.auzoa1)
                audio1 = R.raw.auzoa1audioa.toString()

                foto2 = R.drawable.auzoa2argazkia.toString()
                texto2 = getString(R.string.auzoa2)
                audio2 = R.raw.auzoa2audioa.toString()

                texto3 = getString(R.string.auzoa3)
                audio3 = R.raw.auzoa3audioa.toString()

                texto4 = getString(R.string.auzoa4)
                audio4 = R.raw.auzoa4audioa.toString()

                lugar = arrayListOf(nombre, foto1, texto1, audio1, foto2, texto2, audio2, texto3, audio3, texto4, audio4)

            }//6

            "7" -> {

                nombre = getString(R.string.parada7)

                foto1 = R.drawable.sardinera1argazkia.toString()
                texto1 = getString(R.string.sardinera1)
                audio1 = R.raw.sardinera1audioa.toString()

                foto2 = R.drawable.sardinera2argazkia.toString()
                texto2 = getString(R.string.sardinera2)
                audio2 = R.raw.sardinera2audioa.toString()

                texto3 = getString(R.string.sardinera3)
                audio3 = R.raw.sardinera3audioa.toString()

                texto4 = getString(R.string.sardinera4)
                audio4 = R.raw.sardinera4audioa.toString()

                lugar = arrayListOf(nombre, foto1, texto1, audio1, foto2, texto2, audio2, texto3, audio3, texto4, audio4)

            }//7

            "8" -> {

                nombre = getString(R.string.parada8)

                foto1 = R.drawable.hasierakoargazkia.toString()
                texto1 = getString(R.string.asmakizuna1)
                audio1 = R.raw.asmakizuna1audioa.toString()

                texto3 = getString(R.string.asmakizuna2)
                audio3 = R.raw.asmakizuna2audioa.toString()

                texto4 = getString(R.string.asmakizuna3)
                audio4 = R.raw.asmakizuna3audioa.toString()

                texto5 = getString(R.string.creditos)
                audio5 = R.raw.desdesanturceabilbao.toString()

                lugar = arrayListOf(nombre, foto1, texto1, audio1, texto3, audio3, texto4, audio4, texto5, audio5)

            }//8

        }//when(punto)

        return lugar

    }//checkPuntoPartida(punto: String): ArrayList<String>?

    //HABILITAR O DESHABILITAR LOS BOTONES DE NAVEGACIÓN DENTRO DEL JUEGO
    fun disableOrEnableButton(view: View, d: Boolean) {

        if (d){

            view.isEnabled = false
            view.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.Disabled))

        }//if(d)

        else{

            view.isEnabled = true
            view.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.Primary))

        }//if(!d)

    }//disableOrEnableButton(view: View, d: Boolean)

}//JuegoActivity()