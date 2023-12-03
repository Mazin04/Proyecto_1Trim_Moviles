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

/**
 * Fragmento para eliminar un piloto existente.
 *
 * Este fragmento proporciona una interfaz para seleccionar y eliminar un piloto existente de la base de datos.
 * Se comunica con el controlador de la aplicación para realizar operaciones relacionadas con la base de datos y la lógica de la aplicación.
 *
 * @property controlador El controlador de la aplicación que gestiona la lógica de la base de datos y la aplicación.
 * @property txtNombre Campo de entrada de autocompletado para seleccionar el nombre del piloto a eliminar.
 * @property btnDelete Botón para confirmar y ejecutar la eliminación del piloto seleccionado.
 * @property fotoEliminar Imagen del piloto que se mostrará antes de confirmar la eliminación.
 *
 * @constructor Crea una instancia de [deleteFragment].
 * @author [Rubén García](https://github.com/Mazin04)
 *
 */
class deleteFragment : Fragment() {
    var controlador : AplicacionController? = null
    lateinit var txtNombre : AutoCompleteTextView
    lateinit var btnDelete : Button
    lateinit var fotoEliminar : ShapeableImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view : View = inflater.inflate(R.layout.fragment_delete, container, false)
        controlador = context?.let { AplicacionController(it) }
        inicializarComponentes(view)
        configurarComponentes(view)
        return view
    }

    /**
     * Elimina el piloto seleccionado de la base de datos y actualiza la interfaz de usuario.
     *
     * @param view La vista actual.
     */
    fun eliminarPiloto(view: View?) {
        var nombre : String = txtNombre.text.toString()
        controlador!!.eliminarPiloto(nombre)
        Toast.makeText(context, "Piloto eliminado correctamente", Toast.LENGTH_SHORT).show()
        actualizarAdapter()
        txtNombre.setText("")
        fotoEliminar.setImageDrawable(null)
    }

    /**
     * Configura los componentes de la interfaz de usuario, como el autocompletado de nombres de pilotos.
     *
     * @param view La vista actual.
     */
    fun configurarComponentes(view: View) {
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item, controlador!!.obtenerNombrePilotos())
        txtNombre.setAdapter(arrayAdapter)
        txtNombre.hint = "Elige nombre del piloto"
        txtNombre.setText("")
        txtNombre.setOnItemClickListener { parent, view, position, id ->
            cargarFoto()
        }
        btnDelete.setOnClickListener { view ->
            eliminarPiloto(view)
        }
    }

    /**
     * Carga la foto del piloto seleccionado y la muestra en la interfaz de usuario.
     */
    fun cargarFoto() {
        val drawableResource = requireContext().resources.getIdentifier(controlador!!.obtenerFoto(txtNombre.text.toString()), "drawable", requireContext().packageName)
        fotoEliminar.setImageDrawable(requireContext().resources.getDrawable(drawableResource))
    }

    /**
     * Inicializa los componentes de la interfaz de usuario.
     *
     * @param view La vista raíz del fragmento.
     */
    fun inicializarComponentes(view: View) {
        txtNombre = view.findViewById(R.id.autoComplete)
        btnDelete = view.findViewById(R.id.btnDelete)
        fotoEliminar = view.findViewById(R.id.fotoPilotoEliminar)
        fotoEliminar.setImageDrawable(null)
    }

    /**
     * Actualiza el adaptador del campo de autocompletado con la lista de nombres de pilotos.
     */
    fun actualizarAdapter() {
        txtNombre.setAdapter(ArrayAdapter(requireContext(), R.layout.dropdown_item, controlador!!.obtenerNombrePilotos()))
    }

}