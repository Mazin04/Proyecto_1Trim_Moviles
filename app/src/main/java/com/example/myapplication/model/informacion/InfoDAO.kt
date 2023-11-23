package com.example.myapplication.model.informacion

import com.example.myapplication.model.entities.Circuitos
import com.example.myapplication.model.entities.Escuderia
import com.example.myapplication.model.entities.Piloto

interface InfoDAO {
    public fun obtenerPilotos() : ArrayList<Piloto>
    public fun obtenerCircuitos() : ArrayList<Circuitos>
    public fun obtenerEquipos() : ArrayList<Escuderia>
    public fun add(piloto : Piloto)
    public fun modify(piloto : Piloto, nombreOriginal : String)
    public fun obtenerNombrePilotos(): ArrayList<String>
    public fun eliminarPiloto(nombre: String)
    public fun installDatabaseFromAssets()
    abstract fun obtenerFoto(nombre: String): String
    abstract fun obtenerPiloto(nombre: String): Piloto
}