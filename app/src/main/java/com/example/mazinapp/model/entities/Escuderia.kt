package com.example.mazinapp.model.entities

/**
 * Clase que representa una escudería en el sistema.
 *
 * @property nombre El nombre de la escudería.
 * @property sede La sede de la escudería.
 * @property jefe El nombre del jefe de la escudería.
 * @property logo_ref La referencia al logo de la escudería.
 * @constructor Crea una instancia de la clase Escuderia con los datos proporcionados.
 * @author [Rubén García](https://github.com/Mazin04)
 */
class Escuderia (
    private var nombre: String,
    private var sede: String,
    private var jefe: String,
    private var logo_ref : String
) {
    /**
     * Obtiene el nombre de la escudería.
     *
     * @return El nombre de la escudería.
     */
    fun getNombre(): String {
        return nombre
    }
    /**
     * Obtiene la sede de la escudería.
     *
     * @return La sede de la escudería.
     */
    fun getSede(): String {
        return sede
    }
    /**
     * Obtiene el nombre del jefe de la escudería.
     *
     * @return El nombre del jefe de la escudería.
     */
    fun getJefe(): String {
        return jefe
    }
    /**
     * Obtiene la referencia al logo de la escudería.
     *
     * @return La referencia al logo de la escudería.
     */
    fun getLogoRef(): String {
        return logo_ref
    }
}
