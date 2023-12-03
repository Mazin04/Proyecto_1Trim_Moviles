package com.example.mazinapp.view.motorsport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mazinapp.controller.AplicacionController
import com.example.mazinapp.model.adapters.AdaptadorEscuderias
import com.example.mazinapp.model.entities.Escuderia
import org.mazinapp.rubengarcia.R

class TeamsFragment : Fragment(), SearchView.OnQueryTextListener {
    private lateinit var searchView: SearchView
    lateinit var adaptador : AdaptadorEscuderias
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
        adaptador = context?.let { AdaptadorEscuderias(listaEquipos, it, controlador!!.obtenerEquipos()) }!!
        recyclerView.adapter = adaptador
        searchView = view.findViewById(R.id.buscadorEquipos)
        searchView.setOnQueryTextListener(this)
        searchView.queryHint = "Busca el piloto"
        return view
    }
    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            adaptador.filtrado(query)
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            adaptador.filtrado(newText)
        }
        return false
    }
}