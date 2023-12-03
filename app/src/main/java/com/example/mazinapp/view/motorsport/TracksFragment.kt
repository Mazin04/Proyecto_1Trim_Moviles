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
import com.example.mazinapp.model.adapters.AdaptadorCircuitos
import com.example.mazinapp.model.entities.Circuitos
import org.mazinapp.rubengarcia.R

/**
 * Fragmento que muestra la lista de circuitos y permite buscar circuitos mediante un [SearchView].
 *
 * @property searchView El componente de búsqueda para filtrar circuitos.
 * @property recyclerView El RecyclerView que muestra la lista de circuitos.
 * @property listaCircuitos La lista de circuitos a mostrar.
 * @property controlador El controlador de la aplicación.
 * @property adaptador El adaptador para el RecyclerView que gestiona la visualización de circuitos.
 * @constructor Crea una instancia de la clase TracksFragment.
 * @see [Circuitos]
 * @see [AplicacionController]
 * @see [AdaptadorCircuitos]
 * @constructor Crea una instancia de la clase TracksFragment.
 * @author [Rubén García](https://github.com/Mazin04)
 */
class TracksFragment : Fragment(), SearchView.OnQueryTextListener {
    /**
     * El componente de búsqueda para filtrar circuitos.
     */
    private lateinit var searchView: SearchView
    /**
     * El RecyclerView que muestra la lista de circuitos.
     */
    private lateinit var recyclerView: RecyclerView

    /**
     * La lista de circuitos a mostrar.
     */
    lateinit var listaCircuitos: ArrayList<Circuitos>

    /**
     * El controlador de la aplicación.
     */
    var controlador: AplicacionController? = null

    /**
     * El adaptador para el RecyclerView que gestiona la visualización de circuitos.
     */
    lateinit var adaptador: AdaptadorCircuitos


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
    ): View? {
        // Inflar el diseño del fragmento
        var view : View = inflater.inflate(R.layout.fragment_tracks, container, false)

        // Inicializar el controlador de la aplicación y obtener la lista de circuitos
        controlador = context?.let { AplicacionController(it) }
        listaCircuitos = controlador!!.obtenerCircuitos()

        // Configurar el RecyclerView para mostrar la lista de circuitos
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
    private fun configurarRecyclerView(view : View) {
        recyclerView = view.findViewById(R.id.recyclerCircuitos)
        val layoutManager : RecyclerView.LayoutManager = GridLayoutManager(context,2)
        recyclerView.layoutManager = layoutManager
        adaptador = context?.let { AdaptadorCircuitos(listaCircuitos, it, controlador!!.obtenerCircuitos()) }!!
        recyclerView.adapter = adaptador
    }

    /**
     * Configura el funcionamiento del buscador.
     * @param view vista con la que se trabaja
     */
    private fun configurarBuscador(view : View) {
        searchView = view.findViewById(R.id.buscadorCircuito)
        searchView.setOnQueryTextListener(this)
        searchView.queryHint = "Busca el circuito"
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