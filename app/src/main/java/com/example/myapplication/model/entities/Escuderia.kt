package com.example.myapplication.model.entities

class Escuderia (
    private var nombre: String,
    private var sede: String,
    private var jefe: String,
    private var logo_ref : String
) {
    // Getters
    fun getNombre(): String {
        return nombre
    }
    fun getSede(): String {
        return sede
    }
    fun getJefe(): String {
        return jefe
    }
    fun getLogoRef(): String {
        return logo_ref
    }

    // Setters
    fun setNombre(nombre: String) {
        this.nombre = nombre
    }
    fun setSede(sede: String) {
        this.sede = sede
    }
    fun setJefe(jefe: String) {
        this.jefe = jefe
    }
    fun setLogoRef(logoRef: String) {
        this.logo_ref = logoRef
    }
}
