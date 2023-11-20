package com.example.myapplication.view.motorsport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.controller.AplicacionController
import com.example.myapplication.model.adapters.AdaptadorPilotos
import com.example.myapplication.model.entities.Piloto


class DriversFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    lateinit var listaPilotos : ArrayList<Piloto>
    var controlador : AplicacionController? = null

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
        llenarAdaptadorPilotos()


        return view
    }

    private fun llenarAdaptadorPilotos() {
        var adaptador = context?.let { AdaptadorPilotos(listaPilotos, it) }
        recyclerView.adapter = adaptador
    }
}