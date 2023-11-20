package com.example.myapplication.model.entities

class Circuitos (
    private var nombre: String,
    private var pais: String,
    private var longitud: Double,
    private var GPs : Int,
    private var layout: String
) {
    // Getters

    fun getNombre(): String {
        return this.nombre
    }
    fun getPais(): String {
        return this.pais
    }
    fun getLongitud(): Double {
        return this.longitud
    }
    fun getGPs(): Int {
        return this.GPs
    }
    fun getLayout(): String {
        return this.layout
    }

    // Setters
    fun setNombre(nombre: String) {
        this.nombre = nombre
    }
    fun setPais(pais: String) {
        this.pais = pais
    }
    fun setLongitud(longitud: Double) {
        this.longitud = longitud
    }
    fun setGPs(GPs: Int) {
        this.GPs = GPs
    }
    fun setLayout(layout: String) {
        this.layout = layout
    }
}