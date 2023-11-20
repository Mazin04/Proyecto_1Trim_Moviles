package com.example.myapplication.controller

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.view.View
import com.example.myapplication.R
import com.example.myapplication.model.entities.Piloto
import com.example.myapplication.model.entities.Circuitos
import com.example.myapplication.model.entities.Escuderia
import com.example.myapplication.model.informacion.DAOFactory
import com.example.myapplication.model.informacion.InfoDAO
import com.example.myapplication.model.login.LoginDAOFactory
import com.example.myapplication.model.login.LoginDBDAO
import java.io.IOException
import java.util.ArrayList

class AplicacionController(context : Context) : Application() {
    private lateinit var daoInfo : InfoDAO
    private lateinit var daoUsuario : LoginDBDAO
    private var context : Context = context
    var mediaPlayer: MediaPlayer = MediaPlayer()


    init {
        daoInfo = DAOFactory.getDao(DAOFactory.MODO_SQLITE, context, "nombreDDBB", null, 1)
        daoUsuario = LoginDAOFactory.getDao(LoginDAOFactory.MODO_SQLITE, context, "Login", null, 1)
    }

    companion object{
        var rol : String? = null
    }

    // Abrir enlaces o aplicaciones
    public fun abrirEnlace(url : String){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        this.context.startActivity(intent)
    }

    // Cambiar ventana
    public fun cambiarVentana(contextAnterior: Context, clase : Class<*>){
        val ventanaNueva = Intent(contextAnterior, clase)
        contextAnterior.startActivity(ventanaNueva)
    }


    // Reproducir sonidos
    @SuppressLint("SuspiciousIndentation")
    fun reproducirSonido(view: View) {
        mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer = MediaPlayer.create(context, R.raw.nano)
            try{
                mediaPlayer!!.start()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

    fun parar(view: View?) {
        mediaPlayer!!.stop()
    }

    // Acceso a la BBDD de Usuarios
    fun comprobarUsuario(username: String): Boolean {
        return daoUsuario.comprobarExistenciaUsuario(username, context)
    }

    fun comprobarContraseña(username: String, password: String): Boolean {
        return daoUsuario.comprobarContraseña(username, password, context)
    }

    fun getNombre(username: String): String {
        return daoUsuario.getNombre(username, context)
    }

    fun agregarUsuario(name: String, username: String, password: String) {
        daoUsuario.agregarUsuario(name, username, password, context)
    }

    fun obtenerRol(username: String): String {
        return daoUsuario.obtenerRol(username)
    }

    fun obtenerPilotos(): ArrayList<Piloto> {
        return daoInfo.obtenerPilotos()
    }

    fun obtenerCircuitos(): ArrayList<Circuitos> {
        return daoInfo.obtenerCircuitos()
    }

    fun obtenerEquipos(): ArrayList<Escuderia> {
        return daoInfo.obtenerEquipos()
    }
}

