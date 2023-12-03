package com.example.mazinapp.model.entities

/**
 * Clase que representa un piloto en el sistema.
 *
 * @property nombre El nombre del piloto.
 * @property numero El número asignado al piloto.
 * @property puntos La cantidad de puntos acumulados por el piloto.
 * @property campeonatos El número de campeonatos ganados por el piloto.
 * @property victorias El número de victorias obtenidas por el piloto.
 * @property podios El número de podios alcanzados por el piloto.
 * @property n_equipo El nombre del equipo al que pertenece el piloto.
 * @property grandesPremios El número total de Grandes Premios en los que ha participado el piloto.
 * @property foto La referencia a la foto del piloto.
 * @constructor Crea una instancia de la clase Piloto con los datos proporcionados.
 * @author [Rubén García](https://github.com/Mazin04)
 */
class Piloto(
    private var nombre : String,
    private var numero : Int,
    private var puntos : Int,
    private var campeonatos : Int,
    private var victorias : Int,
    private var podios : Int,
    private var n_equipo : String,
    private var grandesPremios : Int,
    private var foto : String
) {
    /**
     * Obtiene el nombre del piloto.
     *
     * @return El nombre del piloto.
     */
    fun getNombre(): String {
        return this.nombre
    }
    /**
     * Obtiene el número asignado al piloto.
     *
     * @return El número del piloto.
     */
    fun getNumero(): Int {
        return this.numero
    }
    /**
     * Obtiene la cantidad de puntos acumulados por el piloto.
     *
     * @return La cantidad de puntos del piloto.
     */
    fun getPuntos(): Int {
        return this.puntos
    }
    /**
     * Obtiene el número de campeonatos mundiales ganados por el piloto.
     *
     * @return El número de campeonatos mundiales ganados.
     */
    fun getCampeonatos(): Int {
        return this.campeonatos
    }

    /**
     * Obtiene el número de victorias obtenidas por el piloto.
     *
     * @return El número de victorias del piloto.
     */
    fun getVictorias(): Int {
        return this.victorias
    }
    /**
     * Obtiene el número de podios alcanzados por el piloto.
     *
     * @return El número de podios del piloto.
     */
    fun getPodios(): Int {
        return this.podios
    }

    /**
     * Obtiene el nombre del equipo al que pertenece el piloto.
     *
     * @return El nombre del equipo.
     */
    fun getNEquipo(): String {
        return this.n_equipo
    }
    /**
     * Obtiene el número total de Grandes Premios en los que ha participado el piloto.
     *
     * @return El número de Grandes Premios del piloto.
     */
    fun getGrandesPremios(): Int {
        return this.grandesPremios
    }
    /**
     * Obtiene la referencia a la foto del piloto.
     *
     * @return La referencia a la foto del piloto.
     */
    fun getFoto(): String {
        return this.foto
    }
    /**
     * Establece el nombre del piloto.
     *
     * @param nombre El nuevo nombre del piloto.
     */
    fun setNombre(nombre: String) {
        this.nombre = nombre
    }
    /**
     * Establece el número asignado al piloto.
     *
     * @param numero El nuevo número del piloto.
     */
    fun setNumero(numero: Int) {
        this.numero = numero
    }
    /**
     * Establece la cantidad de puntos acumulados por el piloto.
     *
     * @param puntos La nueva cantidad de puntos del piloto.
     */
    fun setPuntos(puntos: Int) {
        this.puntos = puntos
    }
    /**
     * Establece el número de campeonatos ganados por el piloto.
     *
     * @param campeonatos El nuevo número de campeonatos ganados.
     */
    fun setCampeonatos(campeonatos: Int) {
        this.campeonatos = campeonatos
    }
    /**
     * Establece el número de victorias obtenidas por el piloto.
     *
     * @param victorias El nuevo número de victorias del piloto.
     */
    fun setVictorias(victorias: Int) {
        this.victorias = victorias
    }
    /**
     * Establece el número de podios alcanzados por el piloto.
     *
     * @param podios El nuevo número de podios del piloto.
     */
    fun setPodios(podios: Int) {
        this.podios = podios
    }
    /**
     * Establece el nombre del equipo al que pertenece el piloto.
     *
     * @param n_equipo El nuevo nombre del equipo.
     */
    fun setNEquipo(n_equipo: String) {
        this.n_equipo = n_equipo
    }
    /**
     * Establece el número total de Grandes Premios en los que ha participado el piloto.
     *
     * @param grandesPremios El nuevo número de Grandes Premios del piloto.
     */
    fun setGrandesPremios(grandesPremios: Int) {
        this.grandesPremios = grandesPremios
    }
    /**
     * Establece la referencia a la foto del piloto.
     *
     * @param foto La nueva referencia a la foto del piloto.
     */
    fun setFoto(foto: String) {
        this.foto = foto
    }
}