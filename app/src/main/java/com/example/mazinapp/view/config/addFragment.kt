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


class addFragment : Fragment() {
    private var controlador : AplicacionController? = null
    private lateinit var nombrePiloto : TextInputEditText
    private lateinit var numeroPiloto : TextInputEditText
    private lateinit var puntosPiloto : TextInputEditText
    private lateinit var campeonatosPiloto : TextInputEditText
    private lateinit var victoriasPiloto : TextInputEditText
    private lateinit var podiosPiloto : TextInputEditText
    private lateinit var grandesPremiosPiloto : TextInputEditText
    private lateinit var elegirEquipoPiloto : AutoCompleteTextView
    private lateinit var btnAdd : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view : View = inflater.inflate(R.layout.fragment_add, container, false)
        controlador = context?.let{ AplicacionController(it) }

        nombrePiloto = view.findViewById(R.id.txtInsertarNombrePiloto)
        numeroPiloto = view.findViewById(R.id.txtInsertarNumeroPiloto)
        puntosPiloto = view.findViewById(R.id.txtInsertarPuntosPiloto)
        campeonatosPiloto = view.findViewById(R.id.txtInsertarCampeonatosPiloto)
        victoriasPiloto = view.findViewById(R.id.txtInsertarVictoriasPiloto)
        podiosPiloto = view.findViewById(R.id.txtInsertarPodiosPiloto)
        grandesPremiosPiloto = view.findViewById(R.id.txtInsertarGPPiloto)
        elegirEquipoPiloto = view.findViewById(R.id.selectInsertTeamDriver)
        btnAdd = view.findViewById(R.id.btnAdd)

        view.setOnClickListener {
            cerrarTeclado(view)
        }

        val escuderias = controlador!!.obtenerEquipos()
        var nombreEscuderias = getNombresEscuderias(escuderias)
        val arrayAdapterEquipos = ArrayAdapter(requireContext(), R.layout.dropdown_item, nombreEscuderias)
        elegirEquipoPiloto.setAdapter(arrayAdapterEquipos)
        elegirEquipoPiloto.setHint("Elige una escudería")
        elegirEquipoPiloto.setText("")

        var piloto : Piloto = Piloto("", 0, 0, 0, 0, 0, "", 0, "")
        btnAdd.setOnClickListener { view ->
            if(nombrePiloto.text?.isNotEmpty() == true && numeroPiloto.text?.isNotEmpty() == true && puntosPiloto.text?.isNotEmpty() == true && campeonatosPiloto.text?.isNotEmpty() == true && victoriasPiloto.text?.isNotEmpty() == true && podiosPiloto.text?.isNotEmpty() == true && grandesPremiosPiloto.text?.isNotEmpty() == true && elegirEquipoPiloto.text.isNotEmpty()){
                piloto.setNombre(nombrePiloto.text.toString())
                piloto.setNumero((numeroPiloto.text.toString().toInt()))
                piloto.setPuntos((puntosPiloto.text.toString().toInt()))
                piloto.setCampeonatos(campeonatosPiloto.text.toString().toInt())
                piloto.setVictorias(victoriasPiloto.text.toString().toInt())
                piloto.setPodios(podiosPiloto.text.toString().toInt())
                piloto.setNEquipo(elegirEquipoPiloto.text.toString())
                piloto.setGrandesPremios(grandesPremiosPiloto.text.toString().toInt())
                piloto.setFoto("d_newdriver")
                controlador!!.add(piloto)
                Toast.makeText(context, "Se ha agregado a ${nombrePiloto.text}", Toast.LENGTH_SHORT).show()
                nombrePiloto.setText("")
                numeroPiloto.setText("")
                puntosPiloto.setText("")
                campeonatosPiloto.setText("")
                podiosPiloto.setText("")
                victoriasPiloto.setText("")
                elegirEquipoPiloto.setText("")
                grandesPremiosPiloto.setText("")

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