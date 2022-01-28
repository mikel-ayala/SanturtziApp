package com.g2.santurtziapp.activitidades

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.g2.santurtziapp.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import android.app.ActivityManager
import android.app.AlertDialog
import android.content.DialogInterface
import android.widget.TextView
import com.g2.santurtziapp.fragments.*
import com.g2.santurtziapp.fragments.animaciones.AnimacionCargaFragment
import kotlin.system.exitProcess

open class DrawerActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var menu: Menu
    lateinit var button: FloatingActionButton
    lateinit var fragment: Fragment
    private lateinit var activityName: String
    lateinit var alertDialog: AlertDialog

    //TODAS LAS ACTIVIDADES QUE EXTIENDAN DE ESTA TENDRÁN TODAS LAS FUNCIONALIDADES
    override fun setContentView(view: View?) {

        drawerLayout = layoutInflater.inflate(R.layout.activity_drawer, null) as DrawerLayout
        val container: FrameLayout = drawerLayout.findViewById(R.id.drawerFragmentPrincipal)
        container.addView(view)
        super.setContentView(drawerLayout)

        navigationView = drawerLayout.findViewById(R.id.nav_view)
        button = drawerLayout.findViewById(R.id.MenuButton)
        menu = navigationView.menu

        alertDialog = this.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle(R.string.Salir)
                setMessage(R.string.seguroSalir)
                setPositiveButton(R.string.confirmar,
                    DialogInterface.OnClickListener { dialog, id ->

                        finishAffinity()
                        exitProcess(0)
                    })
                setNegativeButton(R.string.menu_cerrar,
                    DialogInterface.OnClickListener { dialog, id ->

                        dialog.dismiss()

                    })
            }

            builder.create()
        }

        if (SharedApp.users.user == ""){

            menu.findItem(R.id.nav_cerrar_sesion).isVisible = false
            navigationView.getHeaderView(0).findViewById<TextView>(R.id.headerApodo).text = getString(R.string.invitado)
            navigationView.getHeaderView(0).findViewById<TextView>(R.id.headerPunto).text = ""

        }//is not logged
        else if (SharedApp.users.user.length != 1){

            navigationView.getHeaderView(0).findViewById<TextView>(R.id.headerApodo).text = SharedApp.users.user
            navigationView.getHeaderView(0).findViewById<TextView>(R.id.headerPunto).text = SharedApp.puntopartida.Partida

        }//is logged

        checkUserMode()

        button.setOnClickListener(){
            drawerLayout.openDrawer(GravityCompat.START);
        }//onClick

        navigationView.bringToFront()

        navigationView.setNavigationItemSelectedListener(this)

        val am = this.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val taskInfo = am.getRunningTasks(1)
        activityName = taskInfo[0].topActivity!!.className

    }//setContentView(view: View?)

    //AL PULSAR UNA OPCIÓN DEL MENÚ
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(activityName){

            "com.g2.santurtziapp.activitidades.MainActivity" -> {
                when(item.itemId){
                    R.id.nav_inicio -> {
                        if (SharedApp.tipousu.tipo == "profesor"){

                            fragment = ProfesorFragment()
                            replaceFragment(fragment, 1)

                        }//is profesor
                        else{

                            fragment = PartidasFragment()
                            replaceFragment(fragment, 1)

                        }//is alumno
                    }//inicio
                    R.id.nav_ranking -> {
                        fragment = AnimacionCargaFragment()
                        replaceFragment(fragment, 1)
                    }//ranking
                    R.id.nav_quienes -> {
                        fragment = NosotrosFragment()
                        replaceFragment(fragment, 1)
                    }//quienes
                    R.id.nav_profe -> {
                        fragment = LoginFragment()
                        replaceFragment(fragment, 1)
                    }//profe
                    R.id.nav_cerrar_sesion -> {
                        SharedApp.users.user = ""
                        SharedApp.puntopartida.Partida = ""
                        SharedApp.tipousu.tipo = "alumno"
                        checkUserMode()
                        navigationView.getHeaderView(0).findViewById<TextView>(R.id.headerApodo).text = getString(R.string.invitado)
                        navigationView.getHeaderView(0).findViewById<TextView>(R.id.headerPunto).text = ""
                        fragment = PartidasFragment()
                        replaceFragment(fragment, 1)
                    }//cerrar_sesion
                    R.id.nav_salir -> {
                        alertDialog.show()
                    }//salir

                }//when(item.itemId)
            }//MainActivity
            "com.g2.santurtziapp.activitidades.JuegoActivity" -> {
                val intent = Intent(this, MainActivity::class.java)
                when(item.itemId){

                    R.id.nav_inicio -> {
                        startActivity(intent)
                        finish()
                    }//inicio
                    R.id.nav_ranking -> {
                       intent.putExtra("fragment", "AnimacionCargaFragment()")
                        startActivity(intent)
                        finish()
                    }//ranking
                    R.id.nav_quienes -> {
                        intent.putExtra("fragment", "NosotrosFragment()")
                        startActivity(intent)
                        finish()
                    }//quienes
                    R.id.nav_profe -> {
                        intent.putExtra("fragment", "LoginFragment()")
                        startActivity(intent)
                        finish()
                    }//profe
                    R.id.nav_cerrar_sesion -> {
                        SharedApp.users.user = ""
                        SharedApp.puntopartida.Partida = ""
                        SharedApp.tipousu.tipo = "alumno"
                        startActivity(intent)
                        finish()
                    }//cerrar_sesion
                    R.id.nav_salir -> {
                        alertDialog.show()
                    }//salir

                }//when(item.itemId)
            }//JuegoActivity

        }//when(activityName)

        drawerLayout.closeDrawer(GravityCompat.START)
        return true

    }//onNavigationItemSelected(item: MenuItem): Boolean

    //AL PULSAR HACIA ATRÁS
    override fun onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }//drawerLayout is open
        else{
            super.onBackPressed()
        }//drawerLayout is closed

    }//onBackPressed()

    //CAMBIAR FRAGMENTS
    internal fun replaceFragment(fragment: Fragment, f: Int){

        when(f){

            1 -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragmentMain, fragment).commit()
            }//1
            2 -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment1Juego, fragment).commit()
            }//2
            3 -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment2Juego, fragment).commit()
            }//3

        }//when(f)

    }//replaceFragment(fragment: Fragment, f: Int)

    //COMPROBAR SI ES PROFESOR O ALUMNO
    internal fun checkUserMode(){

        if (SharedApp.tipousu.tipo == "profesor"){

            menu.findItem(R.id.nav_profe).isVisible = false
            menu.findItem(R.id.nav_ranking).isVisible = true
            menu.findItem(R.id.nav_cerrar_sesion).isVisible = true

        }//is profesor
        else if (SharedApp.tipousu.tipo == "alumno"){

            menu.findItem(R.id.nav_profe).isVisible = true
            menu.findItem(R.id.nav_ranking).isVisible = false
            menu.findItem(R.id.nav_cerrar_sesion).isVisible = false


        }//is alumno

    }//checkUserMode()

    //QUITAR ESPACIOS, TABULACIONES Y ENTER DE LOS STRINGS
    fun quitarEspacios(texto: String): String {

        var t = texto.replace(" ", "")
        t = t.replace("\n", "")
        t = t.replace("\t", "")

        return t

    }//quitarEspacios(texto: String): String

}//DrawerActivity()