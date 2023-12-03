package com.example.mazinapp.view.config

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import com.example.mazinapp.controller.AplicacionController
import com.google.android.material.imageview.ShapeableImageView
import org.mazinapp.rubengarcia.R

class deleteFragment : Fragment() {
    private var controlador : AplicacionController? = null
    private lateinit var txtNombre : AutoCompleteTextView
    private lateinit var btnDelete : Button
    private lateinit var fotoEliminar : ShapeableImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view : View = inflater.inflate(R.layout.fragment_delete, container, false)
        controlador = context?.let { AplicacionController(it) }
        txtNombre = view.findViewById(R.id.autoComplete)
        btnDelete = view.findViewById(R.id.btnDelete)
        fotoEliminar = view.findViewById(R.id.fotoPilotoEliminar)
        fotoEliminar.setImageDrawable(null)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item, controlador!!.obtenerNombrePilotos())
        txtNombre.setAdapter(arrayAdapter)
        txtNombre.hint = "Elige nombre del piloto"
        txtNombre.setText("")
        txtNombre.setOnItemClickListener { parent, view, position, id ->
            val drawableResource = requireContext().resources.getIdentifier(controlador!!.obtenerFoto(txtNombre.text.toString()), "drawable", requireContext().packageName)
            fotoEliminar.setImageDrawable(requireContext().resources.getDrawable(drawableResource))
        }

        btnDelete.setOnClickListener { view ->
            var nombre : String = txtNombre.text.toString()
            controlador!!.eliminarPiloto(nombre)
            Toast.makeText(context, "Piloto eliminado correctamente", Toast.LENGTH_SHORT).show()
            actualizarAdapter()
            txtNombre.setText("")
            fotoEliminar.setImageDrawable(null)
        }
        return view
    }

    private fun actualizarAdapter() {
        txtNombre.setAdapter(ArrayAdapter(requireContext(), R.layout.dropdown_item, controlador!!.obtenerNombrePilotos()))
    }

}