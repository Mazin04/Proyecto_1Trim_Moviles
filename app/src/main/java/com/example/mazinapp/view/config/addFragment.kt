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
 * Fragmento para agregar un nuevo piloto.
 *
 * Este fragmento proporciona una interfaz para agregar un nuevo piloto con información como nombre, número, puntos, etc.
 * Se comunica con el controlador de la aplicación para realizar operaciones relacionadas con la base de datos y la lógica de la aplicación.
 *
 * @property controlador El controlador de la aplicación que gestiona la lógica de la base de datos y la aplicación.
 * @property nombrePiloto Campo de entrada para el nombre del piloto.
 * @property numeroPiloto Campo de entrada para el número del piloto.
 * @property puntosPiloto Campo de entrada para los puntos del piloto.
 * @property campeonatosPiloto Campo de entrada para los campeonatos del piloto.
 * @property victoriasPiloto Campo de entrada para las victorias del piloto.
 * @property podiosPiloto Campo de entrada para los podios del piloto.
 * @property grandesPremiosPiloto Campo de entrada para el número de grandes premios del piloto.
 * @property elegirEquipoPiloto Menú desplegable para seleccionar la escudería del piloto.
 * @property btnAdd Botón para agregar el piloto.
 *
 * @constructor Crea una instancia de [addFragment].
 * @author [Rubén García](https://github.com/Mazin04)
 */
class addFragment : Fragment() {
    var controlador : AplicacionController? = null
    lateinit var nombrePiloto : TextInputEditText
    lateinit var numeroPiloto : TextInputEditText
    lateinit var puntosPiloto : TextInputEditText
    lateinit var campeonatosPiloto : TextInputEditText
    lateinit var victoriasPiloto : TextInputEditText
    lateinit var podiosPiloto : TextInputEditText
    lateinit var grandesPremiosPiloto : TextInputEditText
    lateinit var elegirEquipoPiloto : AutoCompleteTextView
    lateinit var btnAdd : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view : View = inflater.inflate(R.layout.fragment_add, container, false)
        controlador = context?.let{ AplicacionController(it) }
        inicializarComponentes(view)

        view.setOnClickListener {
            cerrarTeclado(view)
        }

        configurarComponentes()

        var piloto : Piloto = Piloto("", 0, 0, 0, 0, 0, "", 0, "")
        btnAdd.setOnClickListener { view ->
            agregarPiloto(piloto, view)
        }
        return view
    }

    /**
     * Configura los componentes de la interfaz de usuario, como el autocompletado de equipos.
     */
    fun configurarComponentes() {
        val escuderias = controlador!!.obtenerEquipos()
        var nombreEscuderias = getNombresEscuderias(escuderias)
        val arrayAdapterEquipos = ArrayAdapter(requireContext(), R.layout.dropdown_item, nombreEscuderias)
        elegirEquipoPiloto.setAdapter(arrayAdapterEquipos)
        elegirEquipoPiloto.setHint("Elige una escudería")
        elegirEquipoPiloto.setText("")
    }

    /**
     * Agrega un nuevo piloto con la información ingresada en los campos.
     *
     * @param piloto El objeto Piloto que se va a agregar.
     * @param view La vista actual.
     */
    fun agregarPiloto(piloto : Piloto, view : View) {
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

    /**
     * Inicializa los componentes de la interfaz de usuario.
     *
     * @param view La vista raíz del fragmento.
     */
    fun inicializarComponentes(view: View) {
        nombrePiloto = view.findViewById(R.id.txtInsertarNombrePiloto)
        numeroPiloto = view.findViewById(R.id.txtInsertarNumeroPiloto)
        puntosPiloto = view.findViewById(R.id.txtInsertarPuntosPiloto)
        campeonatosPiloto = view.findViewById(R.id.txtInsertarCampeonatosPiloto)
        victoriasPiloto = view.findViewById(R.id.txtInsertarVictoriasPiloto)
        podiosPiloto = view.findViewById(R.id.txtInsertarPodiosPiloto)
        grandesPremiosPiloto = view.findViewById(R.id.txtInsertarGPPiloto)
        elegirEquipoPiloto = view.findViewById(R.id.selectInsertTeamDriver)
        btnAdd = view.findViewById(R.id.btnAdd)
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