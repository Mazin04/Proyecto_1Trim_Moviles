package com.example.myapplication.model.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.entities.Piloto
import java.util.stream.Collectors


class AdaptadorPilotos(private val listaPilotos: ArrayList<Piloto>, contexto : Context) : RecyclerView.Adapter<AdaptadorPilotos.PilotosHolder>() {
    var context : Context = contexto
    lateinit var listaOriginal : ArrayList<Piloto>


    class PilotosHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val foto : ImageView = itemView.findViewById(R.id.fotoPiloto)
        val nombrePiloto : TextView = itemView.findViewById(R.id.nombrePiloto)
        val campeonatosPiloto : TextView = itemView.findViewById(R.id.campeonatosPiloto)
        val victoriasPiloto : TextView = itemView.findViewById(R.id.victoriasPiloto)
        val podiosPiloto : TextView = itemView.findViewById(R.id.podiosPiloto)
        val puntosPiloto : TextView = itemView.findViewById(R.id.puntosPiloto)
        val participacionesPiloto : TextView = itemView.findViewById(R.id.participacionesGP)
        val equipoPiloto : TextView = itemView.findViewById(R.id.piloto_equipo)
        val numeroPiloto : TextView = itemView.findViewById(R.id.numeroPiloto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PilotosHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.piloto_list_resultado, parent, false)
        return PilotosHolder(itemView)
    }

    fun filtrado(txtBuscar : String){
        listaOriginal.addAll(listaPilotos)
        var longitud : Int = txtBuscar.length

        if (longitud == 0){
            listaPilotos.clear()
            listaPilotos.addAll(listaOriginal)
        } else {
            var coleccion : MutableList<Piloto>? = listaPilotos.stream().filter { i -> i.getNombre().lowercase().contains(txtBuscar.lowercase())}.collect(
                Collectors.toList())
        }
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return listaPilotos.size
    }

    override fun onBindViewHolder(holder: PilotosHolder, position: Int) {
        val currentItem = listaPilotos[position]
        try {
            val drawableResource = context.resources.getIdentifier(currentItem.getFoto(), "drawable", context.packageName)
            holder.foto.setImageDrawable(context.resources.getDrawable(drawableResource))
        } catch (e: Exception){
            holder.foto.setImageResource(R.drawable.r25)
        }
        holder.nombrePiloto.text = currentItem.getNombre()
        holder.campeonatosPiloto.setText(currentItem.getCampeonatos().toString() + " campeonatos")
        holder.podiosPiloto.setText(currentItem.getPodios().toString() + " podios")
        holder.puntosPiloto.setText(currentItem.getPuntos().toString() + " puntos")
        holder.participacionesPiloto.setText(currentItem.getGrandesPremios().toString() + " GP")
        holder.equipoPiloto.text = currentItem.getNEquipo()
        holder.numeroPiloto.text = currentItem.getNumero().toString()
        holder.victoriasPiloto.text = currentItem.getVictorias().toString() + " Victorias"
        setAnimation(holder.itemView, position)
    }

    private fun setAnimation(view : View, position : Int){
        var slideIn : Animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_left_right)
        view.startAnimation(slideIn)
    }
}