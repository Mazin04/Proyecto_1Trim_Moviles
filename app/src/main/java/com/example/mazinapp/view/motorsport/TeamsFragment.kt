package com.example.mazinapp.view.motorsport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mazinapp.controller.AplicacionController
import com.example.mazinapp.model.adapters.AdaptadorEscuderias
import com.example.mazinapp.model.entities.Escuderia
import org.mazinapp.rubengarcia.R

/**
 * Fragmento que muestra la lista de equipos y permite buscar equipos mediante un [SearchView].
 *
 * @property searchView El componente de búsqueda para filtrar equipos.
 * @property recyclerView El RecyclerView que muestra la lista de equipos.
 * @property listaEquipos La lista de equipos a mostrar.
 * @property controlador El controlador de la aplicación.
 * @property adaptador El adaptador para el RecyclerView que gestiona la visualización de equipos.
 * @constructor Crea una instancia de la clase TeamsFragment.
 * @see [Escuderia]
 * @see [AplicacionController]
 * @see [AdaptadorEscuderias]
 * @constructor Crea una instancia de la clase TeamsFragment.
 * @author [Rubén García](https://github.com/Mazin04)
 */
class TeamsFragment : Fragment(), SearchView.OnQueryTextListener {
    /**
     * El componente de búsqueda para filtrar equipos.
     */
    lateinit var searchView: SearchView

    /**
     * El RecyclerView que muestra la lista de equipos.
     */
    lateinit var recyclerView: RecyclerView

    /**
     * La lista de equipos a mostrar.
     */
    lateinit var listaEquipos: ArrayList<Escuderia>

    /**
     * El controlador de la aplicación.
     */
    var controlador: AplicacionController? = null

    /**
     * El adaptador para el RecyclerView que gestiona la visualización de equipos.
     */
    lateinit var adaptador: AdaptadorEscuderias

    /**
     * Método invocado al crear la vista del fragmento.
     *
     * @param inflater El inflador de diseño.
     * @param container El contenedor de la vista.
     * @param savedInstanceState El estado de la instancia guardada.
     * @return La vista creada.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflar el diseño del fragmento
        var view = inflater.inflate(R.layout.fragment_teams, container, false)

        // Inicializar el controlador de la aplicación y obtener la lista de equipos
        controlador = context?.let { AplicacionController(it) }
        listaEquipos = controlador!!.obtenerEquipos()

        // Configurar el RecyclerView para mostrar la lista de equipos
        configurarRecyclerView(view)

        // Configurar el componente de búsqueda
        configurarBuscador(view)
        return view
    }

    /**
     * Adquiere el recycler view y le configura con su adaptador correspondiente
     * para que se muestra la lista de circuitos.
     * @param view vista con la que se trabaja
     */
    fun configurarRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerEquipos)
        val layoutManager : RecyclerView.LayoutManager = GridLayoutManager(context, 3)
        recyclerView.layoutManager = layoutManager
        adaptador = context?.let { AdaptadorEscuderias(listaEquipos, it, controlador!!.obtenerEquipos()) }!!
        recyclerView.adapter = adaptador
    }

    /**
     * Configura el funcionamiento del buscador.
     * @param view vista con la que se trabaja
     */
    fun configurarBuscador(view: View) {
        searchView = view.findViewById(R.id.buscadorEquipos)
        searchView.setOnQueryTextListener(this)
        searchView.queryHint = "Busca la escudería"
    }

    /**
     * Método invocado cuando se envía un formulario de búsqueda.
     *
     * @param query El texto de búsqueda.
     * @return `true` si la búsqueda se ha manejado; de lo contrario, `false`.
     */
    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            adaptador.filtrado(query)
        }
        return false
    }

    /**
     * Método invocado cuando cambia el texto de búsqueda.
     *
     * @param newText El nuevo texto de búsqueda.
     * @return `true` si el cambio de texto se ha manejado; de lo contrario, `false`.
     */
    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            adaptador.filtrado(newText)
        }
        return false
    }
}