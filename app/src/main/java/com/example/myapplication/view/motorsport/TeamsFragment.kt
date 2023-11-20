package com.example.myapplication.view.motorsport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.controller.AplicacionController
import com.example.myapplication.model.adapters.AdaptadorEscuderias
import com.example.myapplication.model.entities.Escuderia

class TeamsFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    var controlador : AplicacionController? = null
    lateinit var listaEquipos : ArrayList<Escuderia>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_teams, container, false)
        controlador = context?.let { AplicacionController(it) }
        listaEquipos = controlador!!.obtenerEquipos()
        recyclerView = view.findViewById(R.id.recyclerEquipos)
        val layoutManager : RecyclerView.LayoutManager = GridLayoutManager(context, 3)
        recyclerView.layoutManager = layoutManager
        llenarAdaptadorEscuderia()
        return view
    }

    private fun llenarAdaptadorEscuderia() {
        var adaptador = context?.let { AdaptadorEscuderias(listaEquipos, it) }
        recyclerView.adapter = adaptador
    }
}