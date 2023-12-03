package com.example.mazinapp.model.entities

/**
 * Clase que representa un circuito en el sistema.
 *
 * @property nombre El nombre del circuito.
 * @property pais El país en el que se encuentra el circuito.
 * @property longitud La longitud del circuito en kilómetros.
 * @property GPs El número de Grandes Premios celebrados en el circuito.
 * @property layout El nombre del archivo de diseño asociado al circuito.
 * @constructor Crea una instancia de la clase Circuitos con los datos proporcionados.
 * @author [Rubén García](https://github.com/Mazin04)
 */
class Circuitos (
    private var nombre: String,
    private var pais: String,
    private var longitud: Double,
    private var GPs : Int,
    private var layout: String
) {
    /**
     * Obtiene el nombre del circuito.
     *
     * @return El nombre del circuito.
     */
    fun getNombre(): String {
        return this.nombre
    }
    /**
     * Obtiene el país en el que se encuentra el circuito.
     *
     * @return El país del circuito.
     */
    fun getPais(): String {
        return this.pais
    }
    /**
     * Obtiene la longitud del circuito en kilómetros.
     *
     * @return La longitud del circuito.
     */
    fun getLongitud(): Double {
        return this.longitud
    }
    /**
     * Obtiene el número de Grandes Premios celebrados en el circuito.
     *
     * @return El número de Grandes Premios.
     */
    fun getGPs(): Int {
        return this.GPs
    }
    /**
     * Obtiene el nombre del archivo de diseño asociado al circuito.
     *
     * @return El nombre del archivo de diseño.
     */
    fun getLayout(): String {
        return this.layout
    }
}