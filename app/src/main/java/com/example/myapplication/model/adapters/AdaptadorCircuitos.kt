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
import com.example.myapplication.model.entities.Circuitos
import java.lang.Exception
import java.util.ArrayList

class AdaptadorCircuitos(private val listaCircuitos: ArrayList<Circuitos>, it: Context) : RecyclerView.Adapter<AdaptadorCircuitos.CircuitosHolder>() {
    var context : Context = it

    class CircuitosHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        val fotoCircuito : ImageView = itemView.findViewById(R.id.fotoCircuito)
        val nombreCircuito : TextView = itemView.findViewById(R.id.nombreCircuito)
        val paisCircuito : TextView = itemView.findViewById(R.id.paisCircuito)
        val longitudCircuito : TextView = itemView.findViewById(R.id.longitudCircuito)
        val gpCircuito : TextView = itemView.findViewById(R.id.gpsCircuito)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CircuitosHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.track_list_resultado, parent, false)
        return CircuitosHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listaCircuitos.size
    }

    override fun onBindViewHolder(holder: CircuitosHolder, position: Int) {
        val currentItem = listaCircuitos[position]
        try {
            val drawableResource = context.resources.getIdentifier(currentItem.getLayout(), "drawable", context.packageName)
            holder.fotoCircuito.setImageDrawable(context.resources.getDrawable(drawableResource))
        } catch (e : Exception) {
            holder.fotoCircuito.setImageResource(R.drawable.c_interlagos)
        }
        holder.nombreCircuito.text = currentItem.getNombre()
        holder.paisCircuito.text = currentItem.getPais()
        holder.longitudCircuito.text = currentItem.getLongitud().toString() + " km"
        holder.gpCircuito.text = currentItem.getGPs().toString()

        setAnimation(holder.itemView, position)

    }

    private fun setAnimation(view : View, position : Int){
        var slideIn : Animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_left_right)
        view.startAnimation(slideIn)
    }
}
