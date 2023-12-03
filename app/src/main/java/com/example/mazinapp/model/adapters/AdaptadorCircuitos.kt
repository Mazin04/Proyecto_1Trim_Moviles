package com.example.mazinapp.model.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mazinapp.model.entities.Circuitos
import org.mazinapp.rubengarcia.R

import java.lang.Exception
import java.util.ArrayList
import java.util.stream.Collectors

/**
 * Adaptador personalizado para la lista de circuitos en un RecyclerView.
 *
 * @param listaCircuitos La lista de circuitos a mostrar.
 * @param context El contexto de la aplicación.
 * @param listaOriginal La lista original de circuitos (sin filtrar).
 * @constructor Crea una instancia del adaptador con la lista de circuitos, el contexto y la lista original.
 * @author [Rubén García](https://github.com/Mazin04)
 */
class AdaptadorCircuitos(val listaCircuitos: ArrayList<Circuitos>, it: Context, listaOriginal : ArrayList<Circuitos>) : RecyclerView.Adapter<AdaptadorCircuitos.CircuitosHolder>() {
    /**
     * Contexto a usar de la vista para trabajar con ella.
     */
    var context : Context = it

    /**
     * Lista original para luego poder usarla en el buscador.
     */
    var lOriginal : ArrayList<Circuitos> = listaOriginal

    /**
     * Representa un elemento de la lista de circuitos en el RecyclerView.
     *
     * @param itemView La vista de un elemento individual.
     */
    class CircuitosHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        val fotoCircuito : ImageView = itemView.findViewById(R.id.fotoCircuito)
        val nombreCircuito : TextView = itemView.findViewById(R.id.nombreCircuito)
        val paisCircuito : TextView = itemView.findViewById(R.id.paisCircuito)
        val longitudCircuito : TextView = itemView.findViewById(R.id.longitudCircuito)
        val gpCircuito : TextView = itemView.findViewById(R.id.gpsCircuito)
    }

    /**
     * Crea y devuelve un nuevo ViewHolder basado en la vista del elemento individual.
     *
     * @param parent El ViewGroup al que se añadirá el nuevo ViewHolder.
     * @param viewType El tipo de la vista.
     * @return El nuevo ViewHolder creado, es decir, el item/tarjeta que creamos.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CircuitosHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.track_list_resultado, parent, false)
        return CircuitosHolder(itemView)
    }

    /**
     * Realiza el filtrado de la lista de circuitos según el texto de búsqueda proporcionado.
     * Es decir, literalmente un buscador por nombre.
     * @param txtBuscar El texto de búsqueda.
     */
    fun filtrado(txtBuscar : String){
        var longitud : Int = txtBuscar.length

        if (longitud == 0){
            listaCircuitos.clear()
            listaCircuitos.addAll(lOriginal)
        } else {
            listaCircuitos.clear()
            listaCircuitos.addAll(lOriginal)
            var coleccion : MutableList<Circuitos>? = listaCircuitos.stream().filter { i -> i.getNombre().lowercase().contains(txtBuscar.lowercase())}.collect(
                Collectors.toList())
            listaCircuitos.clear()
            coleccion?.let { listaCircuitos.addAll(it) }
        }
        notifyDataSetChanged()
    }

    /**
     * Devuelve el número total de elementos en la lista.
     *
     * @return El número total de elementos en la lista de circuitos.
     */
    override fun getItemCount(): Int {
        return listaCircuitos.size
    }

    /**
     * Establece las características del Circuito actual en su tarjeta correspondiente.
     *
     * @param holder El ViewHolder a actualizar.
     * @param position La posición del elemento en la lista.
     */
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

    /**
     * Aplica una animación de deslizamiento a la vista del elemento.
     *
     * @param view La vista del elemento.
     * @param position La posición del elemento en la lista.
     */
    fun setAnimation(view : View, position : Int){
        var slideIn : Animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_left_right)
        view.startAnimation(slideIn)
    }
}
