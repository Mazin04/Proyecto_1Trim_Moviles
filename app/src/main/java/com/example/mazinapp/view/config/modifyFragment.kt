package com.example.mazinapp.view.config

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import com.example.mazinapp.controller.AplicacionController
import com.example.mazinapp.model.entities.Escuderia
import com.example.mazinapp.model.entities.Piloto
import com.google.android.material.textfield.TextInputEditText
import org.mazinapp.rubengarcia.R

class modifyFragment : Fragment() {
    private var controlador : AplicacionController? = null
    private lateinit var elegirNombrePiloto : AutoCompleteTextView
    private lateinit var nombrePiloto : TextInputEditText
    private lateinit var numeroPiloto : TextInputEditText
    private lateinit var puntosPiloto : TextInputEditText
    private lateinit var campeonatosPiloto : TextInputEditText
    private lateinit var victoriasPiloto : TextInputEditText
    private lateinit var podiosPiloto : TextInputEditText
    private lateinit var grandesPremiosPiloto : TextInputEditText
    private lateinit var elegirEquipoPiloto : AutoCompleteTextView
    private lateinit var btnModify : Button
    private lateinit var nombreOriginal : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view : View = inflater.inflate(R.layout.fragment_modify, container, false)
        controlador = context?.let{ AplicacionController(it) }

        elegirNombrePiloto = view.findViewById(R.id.selectDriver)
        nombrePiloto = view.findViewById(R.id.txtNombrePiloto)
        numeroPiloto = view.findViewById(R.id.txtNumeroPiloto)
        puntosPiloto = view.findViewById(R.id.txtPuntosPiloto)
        campeonatosPiloto = view.findViewById(R.id.txtCampeonatosPiloto)
        victoriasPiloto = view.findViewById(R.id.txtVictoriasPiloto)
        podiosPiloto = view.findViewById(R.id.txtPodiosPiloto)
        grandesPremiosPiloto = view.findViewById(R.id.txtGPPiloto)
        elegirEquipoPiloto = view.findViewById(R.id.selectTeamDriver)
        btnModify = view.findViewById(R.id.btnModify)

        view.setOnClickListener {
            cerrarTeclado(view)
        }

        val arrayAdapterNombres = ArrayAdapter(requireContext(), R.layout.dropdown_item, controlador!!.obtenerNombrePilotos())
        elegirNombrePiloto.setAdapter(arrayAdapterNombres)
        elegirNombrePiloto.setHint("Elige un piloto")
        elegirNombrePiloto.setText("")

        val escuderias = controlador!!.obtenerEquipos()
        var nombreEscuderias = getNombresEscuderias(escuderias)
        val arrayAdapterEquipos = ArrayAdapter(requireContext(), R.layout.dropdown_item, nombreEscuderias)
        elegirEquipoPiloto.setAdapter(arrayAdapterEquipos)
        elegirEquipoPiloto.setHint("Elige una escudería")
        elegirEquipoPiloto.setText("")

        lateinit var piloto : Piloto
        elegirNombrePiloto.setOnItemClickListener { parent, view, position, id ->
            piloto = controlador!!.obtenerPiloto(elegirNombrePiloto.text.toString())
            nombrePiloto.setText(piloto.getNombre())
            nombreOriginal = nombrePiloto.text.toString()
            numeroPiloto.setText(piloto.getNumero().toString())
            puntosPiloto.setText(piloto.getPuntos().toString())
            campeonatosPiloto.setText(piloto.getCampeonatos().toString())
            victoriasPiloto.setText(piloto.getVictorias().toString())
            podiosPiloto.setText(piloto.getPodios().toString())
            grandesPremiosPiloto.setText(piloto.getGrandesPremios().toString())
        }

        btnModify.setOnClickListener { view ->
            if(nombrePiloto.text?.isNotEmpty() == true && numeroPiloto.text?.isNotEmpty() == true && puntosPiloto.text?.isNotEmpty() == true && campeonatosPiloto.text?.isNotEmpty() == true && victoriasPiloto.text?.isNotEmpty() == true && podiosPiloto.text?.isNotEmpty() == true && grandesPremiosPiloto.text?.isNotEmpty() == true && elegirEquipoPiloto.text.isNotEmpty()){
                piloto.setNombre(nombrePiloto.text.toString())
                piloto.setNumero((numeroPiloto.text.toString().toInt()))
                piloto.setPuntos((puntosPiloto.text.toString().toInt()))
                piloto.setCampeonatos(campeonatosPiloto.text.toString().toInt())
                piloto.setVictorias(victoriasPiloto.text.toString().toInt())
                piloto.setPodios(podiosPiloto.text.toString().toInt())
                piloto.setNEquipo(elegirEquipoPiloto.text.toString())
                controlador!!.modify(piloto, nombreOriginal.toString())
                Toast.makeText(context, "Se ha modificado correctamente a ${nombrePiloto.text}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Tienes que completar todos los campos", Toast.LENGTH_SHORT).show()
            }

        }

        return view
    }

    private fun getNombresEscuderias(escuderias: ArrayList<Escuderia>): List<String> {

        val nombres = mutableListOf<String>()

        escuderias.forEach { escuderia ->
            nombres.add(escuderia.getNombre())
        }
        return nombres
    }

    private fun cerrarTeclado(view: View) {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


}