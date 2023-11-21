package com.example.myapplication.view.motorsport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.controller.AplicacionController
import com.example.myapplication.model.adapters.AdaptadorPilotos
import com.example.myapplication.model.entities.Piloto
import java.sql.Driver


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
