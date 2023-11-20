package com.example.myapplication.model.entities

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
    fun getNombre(): String {
        return this.nombre
    }

    fun getNumero(): Int {
        return this.numero
    }

    fun getPuntos(): Int {
        return this.puntos
    }

    fun getCampeonatos(): Int {
        return this.campeonatos
    }

    fun getVictorias(): Int {
        return this.victorias
    }

    fun getPodios(): Int {
        return this.podios
    }

    fun getNEquipo(): String {
        return this.n_equipo
    }

    fun getGrandesPremios(): Int {
        return this.grandesPremios
    }

    fun getFoto(): String {
        return this.foto
    }

    fun setNombre(nombre: String) {
        this.nombre = nombre
    }

    fun setNumero(numero: Int) {
        this.numero = numero
    }

    fun setPuntos(puntos: Int) {
        this.puntos = puntos
    }

    fun setCampeonatos(campeonatos: Int) {
        this.campeonatos = campeonatos
    }

    fun setVictorias(victorias: Int) {
        this.victorias = victorias
    }

    fun setPodios(podios: Int) {
        this.podios = podios
    }

    fun setNEquipo(n_equipo: String) {
        this.n_equipo = n_equipo
    }

    fun setGrandesPremios(grandesPremios: Int) {
        this.grandesPremios = grandesPremios
    }

    fun setFoto(foto: String) {
        this.foto = foto
    }


}