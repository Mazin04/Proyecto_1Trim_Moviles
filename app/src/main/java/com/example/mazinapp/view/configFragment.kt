package com.example.mazinapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mazinapp.view.config.addFragment
import com.example.mazinapp.view.config.deleteFragment
import com.example.mazinapp.view.config.modifyFragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import org.mazinapp.rubengarcia.R

/**
 * Fragmento que gestiona las opciones de configuración, permitiendo al usuario agregar, eliminar o modificar pilotos.
 *
 * @property btmNavBar El menú de navegación.
 * @constructor Crea una instancia de la clase configFragment.
 * @see [addFragment]
 * @see [deleteFragment]
 * @see [modifyFragment]
 * @constructor Crea una instancia de la clase configFragment.
 * @author [Rubén García](https://github.com/Mazin04)
 */
class configFragment : Fragment() {
    /**
     * El menú de navegación.
     */
    lateinit var btmNavBar : ChipNavigationBar

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
        var view : View = inflater.inflate(R.layout.fragment_config, container, false)

        // Configurar el menú de navegación.
        btmNavBar = view.findViewById(R.id.bottomNavigationView)
        btmNavBar.setItemSelected(R.id.insertarPiloto)
        cargarFragmento(addFragment())

        btmNavBar.setOnItemSelectedListener {
            cambiarFragmentoEnSeleccion(it)
        }
        return view
    }

    /**
     * Configura el click en la barra de navegación para cambiar los fragmentos, este método llama
     * a [cargarFragmento]
     */
    private fun cambiarFragmentoEnSeleccion(it: Int) {
        when (it) {
            R.id.insertarPiloto -> cargarFragmento(addFragment())
            R.id.eliminarPiloto -> cargarFragmento(deleteFragment())
            R.id.modificarPiloto -> cargarFragmento(modifyFragment())
        }
    }

    /**
     * Carga el fragmento especificado en el contenedor de fragmentos.
     * @param fragment El fragmento a cargar.
     */
    private fun cargarFragmento(fragment : Fragment) {
        fragmentManager?.beginTransaction()?.replace(R.id.frameLayout, fragment)!!.commit()
    }

}