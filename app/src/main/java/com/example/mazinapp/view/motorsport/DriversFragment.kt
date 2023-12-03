package com.example.mazinapp.view.motorsport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mazinapp.controller.AplicacionController
import com.example.mazinapp.model.adapters.AdaptadorPilotos
import com.example.mazinapp.model.entities.Piloto
import org.mazinapp.rubengarcia.R


class DriversFragment : Fragment(), SearchView.OnQueryTextListener{
    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    lateinit var listaPilotos : ArrayList<Piloto>
    var controlador : AplicacionController? = null
    lateinit var adaptador : AdaptadorPilotos

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_drivers, container, false)
        controlador = context?.let { AplicacionController(it) }
        recyclerView = view.findViewById(R.id.recyclerPilotos)
        recyclerView.layoutManager = LinearLayoutManager(context)
        listaPilotos = controlador!!.obtenerPilotos()
        adaptador = context?.let { AdaptadorPilotos(listaPilotos, it, controlador!!.obtenerPilotos()) }!!
        recyclerView.adapter = adaptador
        searchView = view.findViewById(R.id.buscador)
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
