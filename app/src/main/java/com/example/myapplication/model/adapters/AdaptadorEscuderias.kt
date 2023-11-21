package com.example.myapplication.model.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.entities.Escuderia
import com.example.myapplication.model.entities.Piloto
import java.lang.Exception
import java.util.stream.Collectors

class AdaptadorEscuderias(private val listaEscuderias : ArrayList<Escuderia>, it : Context, listaOriginal : ArrayList<Escuderia>) : RecyclerView.Adapter<AdaptadorEscuderias.EscuderiaHolder>() {
    var context : Context = it
    var lOriginal : ArrayList<Escuderia> = listaOriginal
    class EscuderiaHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        val logoEquipo : ImageView = itemView.findViewById(R.id.fotoEquipo)
        val nombreEquipo : TextView = itemView.findViewById(R.id.nombreEquipo)
        val sedeEquipo : TextView = itemView.findViewById(R.id.sedeEquipo)
        val jefeEquipo : TextView = itemView.findViewById(R.id.jefeEquipo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EscuderiaHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.team_list_resultado, parent, false)
        return EscuderiaHolder(itemView)
    }

    fun filtrado(txtBuscar : String){
        var longitud : Int = txtBuscar.length

        if (longitud == 0){
            listaEscuderias.clear()
            listaEscuderias.addAll(lOriginal)
        } else {
            listaEscuderias.clear()
            listaEscuderias.addAll(lOriginal)
            var coleccion : MutableList<Escuderia>? = listaEscuderias.stream().filter { i -> i.getNombre().lowercase().contains(txtBuscar.lowercase())}.collect(
                Collectors.toList())
            listaEscuderias.clear()
            coleccion?.let { listaEscuderias.addAll(it) }
        }
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return listaEscuderias.size
    }

    override fun onBindViewHolder(holder: EscuderiaHolder, position: Int) {
        val currentItem = listaEscuderias[position]
        try {
            val drawableResource = context.resources.getIdentifier(currentItem.getLogoRef(), "drawable", context.packageName)
            holder.logoEquipo.setImageDrawable(context.resources.getDrawable(drawableResource))
        } catch (e : Exception) {
            holder.logoEquipo.setImageResource(R.drawable.e_redbull)
        }
        holder.nombreEquipo.text = currentItem.getNombre()
        holder.sedeEquipo.text = currentItem.getSede()
        holder.jefeEquipo.text = currentItem.getJefe()
        setAnimation(holder.itemView, position)
    }

    private fun setAnimation(view : View, position : Int){
        var slideIn : Animation = AnimationUtils.loadAnimation(context, com.airbnb.lottie.R.anim.abc_fade_in)
        view.startAnimation(slideIn)
    }
}