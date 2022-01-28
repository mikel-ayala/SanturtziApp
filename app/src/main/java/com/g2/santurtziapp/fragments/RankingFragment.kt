package com.g2.santurtziapp.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.g2.santurtziapp.Constantes
import com.g2.santurtziapp.DB
import com.g2.santurtziapp.R
import com.g2.santurtziapp.SharedApp
import com.g2.santurtziapp.activitidades.MainActivity
import com.g2.santurtziapp.databinding.FragmentRankingBinding
import kotlinx.android.synthetic.main.partida.view.*
import kotlin.random.Random

class RankingFragment : Fragment() {

    lateinit var binding: FragmentRankingBinding
    private lateinit var db: DB
    private lateinit var adapter: RankingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRankingBinding.inflate(layoutInflater)

        db = DB(requireContext(), "db", null, 1)

        configureView()

        val m = activity as MainActivity?

        //OVERRIDE DE EL ONBACKPRESSED DE LA ACTIVITY
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                m?.replaceFragment(if (SharedApp.tipousu.tipo=="profesor") {ProfesorFragment()}else{PartidasFragment()}, 1)

            }//onBackPressed()

        })//callback(viewLifecycleOwner, object : OnBackPressedCallback(true)

        return binding.root

    }//onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View


    //GENERAR EL RANKING
    private fun configureView() {

        setUpRecyclerView(db.cargarRanking())

    }//configureView()

    private fun setUpRecyclerView(partidas : ArrayList<Constantes.Partida>) {

        adapter = RankingAdapter(partidas, requireContext())

        binding.rankingLayout.setHasFixedSize(true)
        binding.rankingLayout.layoutManager = LinearLayoutManager(activity)
        binding.rankingLayout.adapter = adapter

    }//setUpRecyclerView(partidas : ArrayList<Constantes.Partida>)

    class RankingAdapter(private val partidas: ArrayList<Constantes.Partida>, val context: Context) : RecyclerView.Adapter<RankingAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            fun bind(partida: Constantes.Partida) {

                itemView.apodoPartidaR.text = partida.apodo
                itemView.puntoPartidaR.text = partida.punto
                itemView.avatarPartidaR.setImageResource(Constantes.guests[Random.nextInt(0, 5)])

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

    }//RankingAdapter(private val partidas: ArrayList<Constantes.Partida>, val context: Context, val db: DB) : RecyclerView.Adapter<RankingAdapter.ViewHolder>()

}//RankingFragment()