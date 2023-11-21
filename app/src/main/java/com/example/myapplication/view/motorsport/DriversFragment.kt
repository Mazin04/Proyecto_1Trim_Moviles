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


class DriversFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    lateinit var listaPilotos : ArrayList<Piloto>
    var controlador : AplicacionController? = null
    lateinit var adaptador : AdaptadorPilotos

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_drivers, container, false)
        controlador = context?.let { AplicacionController(it) }
        recyclerView = view.findViewById(R.id.recyclerPilotos)
        recyclerView.layoutManager = LinearLayoutManager(context)
        listaPilotos = controlador!!.obtenerPilotos()
        adaptador = context?.let { AdaptadorPilotos(listaPilotos, it) }!!
        recyclerView.adapter = adaptador

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search, menu)
        val searchItem = menu.findItem(R.id.searchView)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Buscar Piloto"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                adaptador.filtrado(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adaptador.filtrado(newText)
                return false
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.searchView -> {
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}