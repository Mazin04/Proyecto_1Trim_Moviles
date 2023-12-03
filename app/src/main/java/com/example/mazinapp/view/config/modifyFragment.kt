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

/**
 * Fragmento para modificar la información de un piloto existente.
 *
 * Este fragmento proporciona una interfaz para seleccionar y modificar la información de un piloto existente en la base de datos.
 * Se comunica con el controlador de la aplicación para realizar operaciones relacionadas con la base de datos y la lógica de la aplicación.
 *
 * @property controlador El controlador de la aplicación que gestiona la lógica de la base de datos y la aplicación.
 * @property elegirNombrePiloto Campo de entrada de autocompletado para seleccionar el nombre del piloto a modificar.
 * @property nombrePiloto Campo de entrada para el nuevo nombre del piloto.
 * @property numeroPiloto Campo de entrada para el nuevo número del piloto.
 * @property puntosPiloto Campo de entrada para los nuevos puntos del piloto.
 * @property campeonatosPiloto Campo de entrada para los nuevos campeonatos del piloto.
 * @property victoriasPiloto Campo de entrada para las nuevas victorias del piloto.
 * @property podiosPiloto Campo de entrada para los nuevos podios del piloto.
 * @property grandesPremiosPiloto Campo de entrada para los nuevos Grandes Premios del piloto.
 * @property elegirEquipoPiloto Campo de entrada de autocompletado para seleccionar la nueva escudería del piloto.
 * @property btnModify Botón para confirmar y ejecutar la modificación del piloto seleccionado.
 * @property nombreOriginal Almacena el nombre original del piloto antes de cualquier modificación.
 * @author [Rubén García](https://github.com/Mazin04)
 * @constructor Crea una instancia de [ModifyFragment].
 */
class modifyFragment : Fragment() {
    var controlador : AplicacionController? = null
    lateinit var elegirNombrePiloto : AutoCompleteTextView
    lateinit var nombrePiloto : TextInputEditText
    lateinit var numeroPiloto : TextInputEditText
    lateinit var puntosPiloto : TextInputEditText
    lateinit var campeonatosPiloto : TextInputEditText
    lateinit var victoriasPiloto : TextInputEditText
    lateinit var podiosPiloto : TextInputEditText
    lateinit var grandesPremiosPiloto : TextInputEditText
    lateinit var elegirEquipoPiloto : AutoCompleteTextView
    lateinit var btnModify : Button
    lateinit var nombreOriginal : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view : View = inflater.inflate(R.layout.fragment_modify, container, false)
        controlador = context?.let{ AplicacionController(it) }
        inicializarComponentes(view)
        view.setOnClickListener {
            cerrarTeclado(view)
        }
        configurarComponentes(view)
        lateinit var piloto : Piloto
        elegirNombrePiloto.setOnItemClickListener { parent, view, position, id ->
            piloto = controlador!!.obtenerPiloto(elegirNombrePiloto.text.toString())
            ponerAtributos(piloto, view)
        }
        btnModify.setOnClickListener { view ->
            modificarPiloto(piloto, view)
        }
        return view
    }

    /**
     * Inicializa los componentes de la interfaz de usuario.
     *
     * @param view La vista raíz del fragmento.
     */
    fun inicializarComponentes(view: View) {
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
    }

    /**
     * Configura los componentes de la interfaz de usuario, como los campos de autocompletado de nombres de pilotos y escuderías.
     *
     * @param view La vista actual.
     */
    fun configurarComponentes(view: View) {
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
    }

    /**
     * Completa los campos de entrada con los atributos del piloto seleccionado.
     *
     * @param piloto El objeto Piloto seleccionado.
     * @param view La vista actual.
     */
    fun ponerAtributos(piloto: Piloto, view: View?) {
        nombrePiloto.setText(piloto.getNombre())
        nombreOriginal = nombrePiloto.text.toString()
        numeroPiloto.setText(piloto.getNumero().toString())
        puntosPiloto.setText(piloto.getPuntos().toString())
        campeonatosPiloto.setText(piloto.getCampeonatos().toString())
        victoriasPiloto.setText(piloto.getVictorias().toString())
        podiosPiloto.setText(piloto.getPodios().toString())
        grandesPremiosPiloto.setText(piloto.getGrandesPremios().toString())
    }

    /**
     * Modifica la información del piloto seleccionado y actualiza la base de datos.
     *
     * @param piloto El objeto Piloto seleccionado.
     * @param view La vista actual.
     */
    fun modificarPiloto(piloto: Piloto, view: View?) {
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

    /**
     * Con la lista de las escuderías genera una nueva lista
     * que contenga todos los nombres de las escuderías.
     *
     * @param escuderias La lista con todos los objetos [Escuderia].
     * @return nombres Lista contenedora de todos los nombres de las escuderías.
     */
    fun getNombresEscuderias(escuderias: ArrayList<Escuderia>): List<String> {
        val nombres = mutableListOf<String>()
        escuderias.forEach { escuderia ->
            nombres.add(escuderia.getNombre())
        }
        return nombres
    }

    /**
     * Cierra el teclado cuando se le llama
     *
     * @param view La vista raíz del fragmento.
     */
    fun cerrarTeclado(view: View) {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}