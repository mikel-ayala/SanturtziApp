package com.g2.santurtziapp.fragments.animaciones

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.g2.santurtziapp.Constantes
import com.g2.santurtziapp.DB
import com.g2.santurtziapp.R
import com.g2.santurtziapp.activitidades.MainActivity
import com.g2.santurtziapp.fragments.RankingFragment
import com.google.firebase.firestore.FirebaseFirestore

class AnimacionCargaFragment : Fragment() {

    private lateinit var db: DB
    private val lista: ArrayList<Constantes.Partida> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val fb = FirebaseFirestore.getInstance()
        val m: MainActivity? = activity as MainActivity?
        db = DB(requireContext(), "db", null, 1)

        //RECOGER TODAS LAS PARTIDAS DE LA COLECCIÓN FB
        fb.collection("Partida")
            .get()
            .addOnSuccessListener { i ->

                db.limpiarRanking()

                for (u in i) {

                    lista.add(Constantes.Partida(u.id, u.data["Punto"].toString()))

                }

                db.actualizarRanking(lista)

                m?.replaceFragment(RankingFragment(), 1)

            }//onSuccess

            .addOnFailureListener { Exception->

                Toast.makeText(requireContext(), "$Exception", Toast.LENGTH_SHORT).show()

            }//onFailure

        return inflater.inflate(R.layout.fragment_animacion_carga, container, false)

    }//onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View?

}//AnimacionCargaFragment()