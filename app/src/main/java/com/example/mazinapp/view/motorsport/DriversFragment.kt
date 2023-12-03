package com.example.mazinapp.view.motorsport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mazinapp.controller.AplicacionController
import com.example.mazinapp.model.adapters.AdaptadorCircuitos
import com.example.mazinapp.model.adapters.AdaptadorPilotos
import com.example.mazinapp.model.entities.Circuitos
import com.example.mazinapp.model.entities.Piloto
import org.mazinapp.rubengarcia.R

/**
 * Fragmento que muestra la lista de pilotos y permite buscar pilotos mediante un [SearchView].
 *
 * @property searchView El componente de búsqueda para filtrar pilotos.
 * @property recyclerView El RecyclerView que muestra la lista de pilotos.
 * @property listaCircuitos La lista de pilotos a mostrar.
 * @property controlador El controlador de la aplicación.
 * @property adaptador El adaptador para el RecyclerView que gestiona la visualización de pilotos.
 * @constructor Crea una instancia de la clase DriversFragment.
 * @see [Piloto]
 * @see [AplicacionController]
 * @see [AdaptadorPilotos]
 * @constructor Crea una instancia de la clase DriversFragment.
 * @author [Rubén García](https://github.com/Mazin04)
 */
class DriversFragment : Fragment(), SearchView.OnQueryTextListener{
    /**
     * El componente de búsqueda para filtrar circuitos.
     */
    lateinit var searchView: SearchView
    /**
     * El RecyclerView que muestra la lista de circuitos.
     */
    lateinit var recyclerView: RecyclerView
    /**
     * La lista de pilotos a mostrar.
     */
    lateinit var listaPilotos : ArrayList<Piloto>
    /**
     * El controlador de la aplicación.
     */
    var controlador : AplicacionController? = null
    /**
     * El adaptador para el RecyclerView que gestiona la visualización de pilotos.
     */
    lateinit var adaptador : AdaptadorPilotos

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
        val view : View = inflater.inflate(R.layout.fragment_drivers, container, false)
        controlador = context?.let { AplicacionController(it) }
        configurarRecyclerView(view)
        configurarBuscador(view)

        return view
    }

    /**
     * Adquiere el recycler view y le configura con su adaptador correspondiente
     * para que se muestra la lista de circuitos.
     * @param view vista con la que se trabaja
     */
    fun configurarRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerPilotos)
        recyclerView.layoutManager = LinearLayoutManager(context)
        listaPilotos = controlador!!.obtenerPilotos()
        adaptador = context?.let { AdaptadorPilotos(listaPilotos, it, controlador!!.obtenerPilotos()) }!!
        recyclerView.adapter = adaptador
    }

    /**
     * Configura el funcionamiento del buscador.
     * @param view vista con la que se trabaja
     */
    fun configurarBuscador(view: View) {
        searchView = view.findViewById(R.id.buscador)
        searchView.setOnQueryTextListener(this)
        searchView.queryHint = "Busca el piloto"
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
