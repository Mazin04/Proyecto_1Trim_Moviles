package com.example.myapplication.model.informacion

import com.example.myapplication.model.entities.Circuitos
import com.example.myapplication.model.entities.Escuderia
import com.example.myapplication.model.entities.Piloto

interface InfoDAO {
    public fun obtenerPilotos() : ArrayList<Piloto>
    public fun obtenerCircuitos() : ArrayList<Circuitos>
    public fun obtenerEquipos() : ArrayList<Escuderia>
    public fun add(piloto : Piloto)
    public fun add(escuderia : Escuderia)
    public fun add(circuitos : Circuitos)
    public fun modify(piloto : Piloto)
    public fun modify(escuderia : Escuderia)
    public fun modify(circuitos : Circuitos)
    public fun delete(piloto : Piloto)
    public fun delete(escuderia : Escuderia)
    public fun delete(circuitos : Circuitos)
}