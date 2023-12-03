package com.example.mazinapp.model.informacion

import com.example.mazinapp.model.entities.Circuitos
import com.example.mazinapp.model.entities.Escuderia
import com.example.mazinapp.model.entities.Piloto

/**
 * Interfaz que define métodos para acceder a la base de datos de información relacionada con pilotos, circuitos y equipos.
 *
 * @see [Piloto]
 * @see [Circuitos]
 * @see [Escuderia]
 * @constructor Crea una instancia de la interfaz InfoDAO.
 * @author [Rubén García](https://github.com/Mazin04)
 */
interface InfoDAO {
    /**
     * Obtiene la lista de pilotos desde la base de datos.
     * @return La lista de pilotos.
     */
    fun obtenerPilotos(): ArrayList<Piloto>

    /**
     * Obtiene la lista de circuitos desde la base de datos.
     * @return La lista de circuitos.
     */
    fun obtenerCircuitos(): ArrayList<Circuitos>

    /**
     * Obtiene la lista de equipos desde la base de datos.
     * @return La lista de equipos.
     */
    fun obtenerEquipos(): ArrayList<Escuderia>

    /**
     * Agrega un nuevo piloto a la base de datos.
     * @param piloto El piloto a agregar.
     */
    fun add(piloto: Piloto)

    /**
     * Modifica la información de un piloto en la base de datos.
     * @param piloto El piloto con la información modificada.
     * @param nombreOriginal El nombre original del piloto.
     */
    fun modify(piloto: Piloto, nombreOriginal: String)

    /**
     * Obtiene la lista de nombres de pilotos desde la base de datos.
     * @return La lista de nombres de pilotos.
     */
    fun obtenerNombrePilotos(): ArrayList<String>

    /**
     * Elimina un piloto de la base de datos según su nombre.
     * @param nombre El nombre del piloto a eliminar.
     */
    fun eliminarPiloto(nombre: String)

    /**
     * Instala la base de datos desde los recursos de la aplicación.
     */
    fun installDatabaseFromAssets()

    /**
     * Obtiene la referencia a la foto de un piloto según su nombre.
     * @param nombre El nombre del piloto.
     * @return La referencia a la foto del piloto.
     */
    fun obtenerFoto(nombre: String): String

    /**
     * Obtiene la información de un piloto según su nombre.
     * @param nombre El nombre del piloto.
     * @return La información del piloto.
     */
    fun obtenerPiloto(nombre: String): Piloto
}