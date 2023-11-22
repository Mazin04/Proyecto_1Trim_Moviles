package com.example.myapplication.view.config

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.R.layout.dropdown_item
import com.example.myapplication.controller.AplicacionController
import com.example.myapplication.model.entities.Piloto

class deleteFragment : Fragment() {
    private var controlador : AplicacionController? = null
    private lateinit var txtNombre : AutoCompleteTextView
    private lateinit var btnDelete : Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view : View = inflater.inflate(R.layout.fragment_delete, container, false)
        controlador = context?.let { AplicacionController(it) }
        txtNombre = view.findViewById(R.id.autoComplete)
        btnDelete = view.findViewById(R.id.btnDelete)
        val arrayAdapter = ArrayAdapter(requireContext(), dropdown_item, controlador!!.obtenerNombrePilotos())
        txtNombre.setAdapter(arrayAdapter)
        txtNombre.hint = "Elige nombre del piloto"
        txtNombre.setText("")
        btnDelete.setOnClickListener { view ->
            var nombre : String = txtNombre.text.toString()
            controlador!!.eliminarPiloto(nombre)
            Toast.makeText(context, "Piloto eliminado correctamente", Toast.LENGTH_SHORT).show()
            actualizarAdapter()
            txtNombre.setText("")
        }
        return view
    }

    private fun actualizarAdapter() {
        txtNombre.setAdapter(ArrayAdapter(requireContext(), dropdown_item, controlador!!.obtenerNombrePilotos()))
    }

}