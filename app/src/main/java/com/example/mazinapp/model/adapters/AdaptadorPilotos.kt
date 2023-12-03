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
import com.example.mazinapp.model.entities.Piloto
import org.mazinapp.rubengarcia.R
import java.util.stream.Collectors

/**
 * Adaptador personalizado para la lista de pilotos en un RecyclerView.
 *
 * @param listaPilotos La lista de circuitos a mostrar.
 * @param contexto El contexto de la aplicación.
 * @param listaOriginal La lista original de pilotos (sin filtrar).
 * @constructor Crea una instancia del adaptador con la lista de pilotos, el contexto y la lista original.
 * @author [Rubén García](https://github.com/Mazin04)
 */
class AdaptadorPilotos(private val listaPilotos: ArrayList<Piloto>, contexto : Context, listaOriginal : ArrayList<Piloto>): RecyclerView.Adapter<AdaptadorPilotos.PilotosHolder>() {
    /**
     * Contexto a usar de la vista para trabajar con ella.
     */
    var context : Context = contexto
    /**
     * Lista original para luego poder usarla en el buscador.
     */
    var lOriginal : ArrayList<Piloto> = listaOriginal

    /**
     * Representa un elemento de la lista de pilotos en el RecyclerView.
     *
     * @param itemView La vista de un elemento individual.
     */
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

    /**
     * Crea y devuelve un nuevo ViewHolder basado en la vista del elemento individual.
     *
     * @param parent El ViewGroup al que se añadirá el nuevo ViewHolder.
     * @param viewType El tipo de la vista.
     * @return El nuevo ViewHolder creado, es decir, el item/tarjeta que creamos.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PilotosHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.piloto_list_resultado, parent, false)
        return PilotosHolder(itemView)
    }

    /**
     * Realiza el filtrado de la lista de pilotos según el texto de búsqueda proporcionado.
     * Es decir, literalmente un buscador por nombre.
     * @param txtBuscar El texto de búsqueda.
     */
    fun filtrado(txtBuscar : String){
        var longitud : Int = txtBuscar.length

        if (longitud == 0){
            listaPilotos.clear()
            listaPilotos.addAll(lOriginal)
        } else {
            listaPilotos.clear()
            listaPilotos.addAll(lOriginal)
            var coleccion : MutableList<Piloto>? = listaPilotos.stream().filter { i -> i.getNombre().lowercase().contains(txtBuscar.lowercase())}.collect(
                Collectors.toList())
            listaPilotos.clear()
            coleccion?.let { listaPilotos.addAll(it) }
        }
        notifyDataSetChanged()
    }

    /**
     * Devuelve el número total de elementos en la lista.
     *
     * @return El número total de elementos en la lista de circuitos.
     */
    override fun getItemCount(): Int {
        return listaPilotos.size
    }

    /**
     * Establece las características del Circuito actual en su tarjeta correspondiente.
     *
     * @param holder El ViewHolder a actualizar.
     * @param position La posición del elemento en la lista.
     */
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

    /**
     * Aplica una animación de deslizamiento a la vista del elemento.
     *
     * @param view La vista del elemento.
     * @param position La posición del elemento en la lista.
     */
    private fun setAnimation(view : View, position : Int){
        var slideIn : Animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_left_right)
        view.startAnimation(slideIn)
    }
}