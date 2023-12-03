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
import com.example.mazinapp.model.entities.Escuderia
import org.mazinapp.rubengarcia.R
import java.lang.Exception
import java.util.stream.Collectors


/**
 * Adaptador personalizado para la lista de escuderias en un RecyclerView.
 *
 * @param listaEscuderias La lista de escuderías a mostrar.
 * @param it El contexto de la aplicación.
 * @param listaOriginal La lista original de escuderías (sin filtrar).
 * @constructor Crea una instancia del adaptador con la lista de escuderías, el contexto y la lista original.
 * @author [Rubén García](https://github.com/Mazin04)
 */
class AdaptadorEscuderias(private val listaEscuderias : ArrayList<Escuderia>, it : Context, listaOriginal : ArrayList<Escuderia>) : RecyclerView.Adapter<AdaptadorEscuderias.EscuderiaHolder>() {
    /**
     * Contexto a usar de la vista para trabajar con ella.
     */
    var context : Context = it
    /**
     * Lista original para luego poder usarla en el buscador.
     */
    var lOriginal : ArrayList<Escuderia> = listaOriginal

    /**
     * Representa un elemento de la lista de circuitos en el RecyclerView.
     *
     * @param itemView La vista de un elemento individual.
     */
    class EscuderiaHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        val logoEquipo : ImageView = itemView.findViewById(R.id.fotoEquipo)
        val nombreEquipo : TextView = itemView.findViewById(R.id.nombreEquipo)
        val sedeEquipo : TextView = itemView.findViewById(R.id.sedeEquipo)
        val jefeEquipo : TextView = itemView.findViewById(R.id.jefeEquipo)
    }

    /**
     * Crea y devuelve un nuevo ViewHolder basado en la vista del elemento individual.
     *
     * @param parent El ViewGroup al que se añadirá el nuevo ViewHolder.
     * @param viewType El tipo de la vista.
     * @return El nuevo ViewHolder creado, es decir, el item/tarjeta que creamos.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EscuderiaHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.team_list_resultado, parent, false)
        return EscuderiaHolder(itemView)
    }

    /**
     * Realiza el filtrado de la lista de escuderías según el texto de búsqueda proporcionado.
     * Es decir, literalmente un buscador por nombre.
     * @param txtBuscar El texto de búsqueda.
     */
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

    /**
     * Devuelve el número total de elementos en la lista.
     *
     * @return El número total de elementos en la lista de circuitos.
     */
    override fun getItemCount(): Int {
        return listaEscuderias.size
    }

    /**
     * Establece las características del escudería actual en su tarjeta correspondiente.
     *
     * @param holder El ViewHolder a actualizar.
     * @param position La posición del elemento en la lista.
     */
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

    /**
     * Aplica una animación de deslizamiento a la vista del elemento.
     *
     * @param view La vista del elemento.
     * @param position La posición del elemento en la lista.
     */
    private fun setAnimation(view : View, position : Int){
        var slideIn : Animation = AnimationUtils.loadAnimation(context, com.airbnb.lottie.R.anim.abc_fade_in)
        view.startAnimation(slideIn)
    }
}