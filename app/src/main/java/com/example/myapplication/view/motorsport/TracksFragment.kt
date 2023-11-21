package com.example.myapplication.view.motorsport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.controller.AplicacionController
import com.example.myapplication.model.adapters.AdaptadorCircuitos
import com.example.myapplication.model.adapters.AdaptadorPilotos
import com.example.myapplication.model.entities.Circuitos

class TracksFragment : Fragment(), SearchView.OnQueryTextListener {
    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    lateinit var listaCircuitos : ArrayList<Circuitos>
    var controlador : AplicacionController? = null
    lateinit var adaptador : AdaptadorCircuitos


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view : View = inflater.inflate(R.layout.fragment_tracks, container, false)
        controlador = context?.let { AplicacionController(it) }
        listaCircuitos = controlador!!.obtenerCircuitos()
        recyclerView = view.findViewById(R.id.recyclerCircuitos)
        val layoutManager : RecyclerView.LayoutManager = GridLayoutManager(context,2)
        recyclerView.layoutManager = layoutManager
        adaptador = context?.let { AdaptadorCircuitos(listaCircuitos, it, controlador!!.obtenerCircuitos()) }!!
        recyclerView.adapter = adaptador
        searchView = view.findViewById(R.id.buscadorCircuito)
        searchView.setOnQueryTextListener(this)
        searchView.queryHint = "Busca el circuito"

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