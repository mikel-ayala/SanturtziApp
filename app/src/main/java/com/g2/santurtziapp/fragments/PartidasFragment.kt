package com.g2.santurtziapp.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.g2.santurtziapp.Constantes
import com.g2.santurtziapp.Constantes.guests
import com.g2.santurtziapp.DB
import com.g2.santurtziapp.R
import com.g2.santurtziapp.SharedApp
import com.g2.santurtziapp.activitidades.JuegoActivity
import com.g2.santurtziapp.databinding.FragmentPartidasBinding
import kotlinx.android.synthetic.main.partida.view.*
import kotlin.random.Random
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.g2.santurtziapp.activitidades.MainActivity
import com.google.firebase.firestore.FirebaseFirestore


class PartidasFragment : Fragment() {

    lateinit var binding: FragmentPartidasBinding
    private lateinit var db: DB
    private val fb = FirebaseFirestore.getInstance()
    private lateinit var adapter: PartidasAdapter
    var m: MainActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPartidasBinding.inflate(layoutInflater)

        m = activity as MainActivity

        //DIALOGO PARA SALIR DE LA APP
        val alertDialog: AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setTitle(R.string.Salir)
                setMessage(R.string.seguroSalir)
                setPositiveButton(R.string.confirmar,
                    DialogInterface.OnClickListener { dialog, id ->

                        m!!.finish()
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

        db = DB(requireContext(), "db", null, 1)

        configureView()

        //NUEVA PARTIDA
        binding.nuevaPButton.setOnClickListener{

            SharedApp.modolibre.modo = false

            val apodo: String = JuegoActivity().quitarEspacios(binding.apodoInput.text.toString())

            if (apodo.isNotEmpty() && apodo.length > 2) {

                if (db.cargarPartida(apodo.uppercase()) == null) {

                    fb.collection("Partida").document("${apodo.uppercase()}")
                        .get()
                        .addOnSuccessListener {

                            if (it.exists()) {

                                Toast.makeText(requireContext(), R.string.existe, Toast.LENGTH_SHORT).show()

                            }//if (it.exists())

                            else {

                                SharedApp.users.user = apodo.uppercase()
                                SharedApp.puntopartida.Partida = "0"

                                db.insertarPartida(apodo.uppercase())

                                startActivity(Intent(requireContext(), JuegoActivity::class.java))
                                m!!.finish()

                            }//if (!it.exists())

                        }//onSuccess

                        .addOnFailureListener {

                            Toast.makeText(requireContext(), R.string.failFB, Toast.LENGTH_SHORT).show()

                        }//onFailure

                }//if (db.cargarPartida(apodo.uppercase()) == null)

                else {

                    Toast.makeText(requireContext(), R.string.existe, Toast.LENGTH_SHORT).show()

                }//if (db.cargarPartida(apodo.uppercase()) != null)

            }//if (apodo.isNotEmpty() && apodo.length > 2)

            else {

                Toast.makeText(requireContext(), R.string.noApodo, Toast.LENGTH_SHORT).show()

            }//if ((!apodo.isNotEmpty()) && (!apodo.length > 2))

            binding.apodoInput.text = null

        }//onClick()

        return binding.root

    }//onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View

    override fun onDestroy() {
        db.close()
        super.onDestroy()

    }//onDestroy()

    private fun configureView() {

        setUpRecyclerView(db.cargarPartidas())

    }//configureView()

    private fun setUpRecyclerView(partidas : ArrayList<Constantes.Partida>) {

        adapter = PartidasAdapter(partidas, requireContext(), m)

        binding.partidasLayout.setHasFixedSize(true)
        binding.partidasLayout.layoutManager = LinearLayoutManager(activity)
        binding.partidasLayout.adapter = adapter

    }//setUpRecyclerView(partidas : ArrayList<Constantes.Partida>)

    //GENERAR LAS PARTIDAS LOCALES
    class PartidasAdapter(private val partidas: ArrayList<Constantes.Partida>, val context: Context, mainActivity: MainActivity?) :
        RecyclerView.Adapter<PartidasAdapter.ViewHolder>() {

        val m = mainActivity

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            fun bind(partida: Constantes.Partida) {

                itemView.apodoPartidaR.text = partida.apodo

                if (partida.punto == "9") {

                    itemView.puntoPartidaR.text = R.string.finish.toString()

                }//if (partida.punto == "9")

                else {

                    itemView.puntoPartidaR.text = partida.punto

                }//if (partida.punto != "9")

                itemView.avatarPartidaR.setImageResource(guests[Random.nextInt(0, 5)])

                if(partida.punto < "9") {

                    itemView.setOnClickListener{

                        SharedApp.users.user = partida.apodo
                        SharedApp.puntopartida.Partida = partida.punto
                        SharedApp.modolibre.modo = false

                        m?.startActivity(Intent(context, JuegoActivity::class.java))
                        m?.finish()

                    }//onClick()

                }//if(partida.punto < "9")

            }//bind(partida: Constantes.Partida)

        }//ViewHolder(view: View) : RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

            val view = LayoutInflater.from(viewGroup.context)
                .inflate((R.layout.partida), viewGroup, false)

            return ViewHolder(view)

        }//onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder

        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

            val item = partidas[position]
            viewHolder.bind(item)

        }//onBindViewHolder(viewHolder: ViewHolder, position: Int)

        override fun getItemCount() = partidas.size

    }//PartidasAdapter(private val partidas: ArrayList<Constantes.Partida>, val context: Context,val db: DB, mainActivity: MainActivity?) :RecyclerView.Adapter<PartidasAdapter.ViewHolder>()

}//PartidasFragment()