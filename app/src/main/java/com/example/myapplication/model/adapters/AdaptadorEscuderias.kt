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
import java.lang.Exception

class AdaptadorEscuderias(private val listaEscuderias : ArrayList<Escuderia>, it : Context) : RecyclerView.Adapter<AdaptadorEscuderias.EscuderiaHolder>() {
    var context : Context = it

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