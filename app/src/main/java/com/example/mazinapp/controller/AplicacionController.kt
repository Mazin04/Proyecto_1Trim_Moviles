package com.example.mazinapp.controller

import android.app.Application
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.view.View
import com.example.mazinapp.model.entities.Piloto
import com.example.mazinapp.model.entities.Circuitos
import com.example.mazinapp.model.entities.Escuderia
import com.example.mazinapp.model.informacion.DAOFactory
import com.example.mazinapp.model.informacion.InfoDAO
import com.example.mazinapp.model.login.LoginDAOFactory
import com.example.mazinapp.model.login.LoginDBDAO
import org.mazinapp.rubengarcia.R
import java.io.IOException
import java.util.ArrayList

/**
 * Clase controladora de la aplicación que gestiona la lógica de negocio y la interacción
 * entre la interfaz de usuario y los datos de la aplicación.
 * @param context Contexto de la aplicación.
 * @author [Rubén García](https://github.com/Mazin04)
 */
class AplicacionController(context : Context) : Application() {
    /**
     * Se encarga de gestionar el acceso a la BBDD de Motorsport.
     */
    private lateinit var daoInfo : InfoDAO

    /**
     * Se encarga de gestionar el acceso a la BBDD de Login.
     */
    private lateinit var daoUsuario : LoginDBDAO

    /**
     * Almacena el contexto actual para poder trabajar con el.
     */
    private var context : Context = context

    /**
     * Control para reproducir sonido.
     */
    var mediaPlayer: MediaPlayer = MediaPlayer()

    /**
    * Según se instancia por primera vez la clase se crean dos DAO que cada uno accede a su correspondiente base de datos.
    * @author [Rubén García](https://github.com/Mazin04)
    */
    init {
        daoInfo = DAOFactory.getDao(DAOFactory.MODO_SQLITE, context, "nombreDDBB", null, 1)
        daoUsuario = LoginDAOFactory.getDao(LoginDAOFactory.MODO_SQLITE, context, "Login", null, 1)
    }


    /**
     * Se crea para que almacene el rol de la persona que ha iniciado sesión
     * para luego poder decidir si ve o no la parte de configuración, ya que corresponde a los Admins.
     * @param rol Almacena el rol de la persona que inicia sesión.
     * @author [Rubén García](https://github.com/Mazin04)
     */
    companion object{
        var rol : String? = null
    }

    /**
     * Método para abrir enlaces o aplicaciones externas.
     * @param url URL a abrir.
     * @author [Rubén García](https://github.com/Mazin04)
     */
    public fun abrirEnlace(url : String){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        this.context.startActivity(intent)
    }

    /**
     * Método para cambiar a una nueva ventana de la aplicación.
     * @param contextAnterior Contexto de la ventana actual.
     * @param clase Clase de la nueva ventana.
     * @author [Rubén García](https://github.com/Mazin04)
     */
    public fun cambiarVentana(contextAnterior: Context, clase : Class<*>){
        val ventanaNueva = Intent(contextAnterior, clase)
        contextAnterior.startActivity(ventanaNueva)
    }

    /**
     * Método para reproducir un sonido.
     * @param view Vista asociada al sonido.
     * @author [Rubén García](https://github.com/Mazin04)
     */
    fun reproducirSonido(view: View) {
        mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer = MediaPlayer.create(context, R.raw.nano)
            try{
                mediaPlayer!!.start()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

    /**
     * Detiene la reproducción de medios.
     * @param view La vista asociada al evento de parada.
     * @author [Rubén García](https://github.com/Mazin04)
     */
    fun parar(view: View?) {
        mediaPlayer!!.stop()
    }

    /**
     * Comprueba la existencia de un usuario en la base de datos.
     *
     * @param username El nombre de usuario a verificar.
     * @return True si el usuario existe, False en caso contrario.
     * @author [Rubén García](https://github.com/Mazin04)
     */
    fun comprobarUsuario(username: String): Boolean {
        return daoUsuario.comprobarExistenciaUsuario(username, context)
    }

    /**
     * Comprueba la contraseña de un usuario en la base de datos.
     *
     * @param username El nombre de usuario.
     * @param password La contraseña a verificar.
     * @return True si la contraseña es correcta, False en caso contrario.
     * @author [Rubén García](https://github.com/Mazin04)
     */
    fun comprobarContrasena(username: String, password: String): Boolean {
        return daoUsuario.comprobarContraseña(username, password, context)
    }

    /**
     * Obtiene el nombre de un usuario en la base de datos.
     *
     * @param username El nombre de usuario.
     * @return El nombre del usuario.
     * @author [Rubén García](https://github.com/Mazin04)
     */
    fun getNombre(username: String): String {
        return daoUsuario.getNombre(username, context)
    }

    /**
     * Agrega un nuevo usuario a la base de datos.
     *
     * @param name El nombre del usuario.
     * @param username El nombre de usuario.
     * @param password La contraseña del usuario.
     * @author [Rubén García](https://github.com/Mazin04)
     */
    fun agregarUsuario(name: String, username: String, password: String) {
        daoUsuario.agregarUsuario(name, username, password, context)
    }

    /**
     * Obtiene el rol de un usuario en la base de datos.
     *
     * @param username El nombre de usuario.
     * @return El rol del usuario.
     * @author [Rubén García](https://github.com/Mazin04)
     */
    fun obtenerRol(username: String): String {
        return daoUsuario.obtenerRol(username)
    }

    /**
     * Obtiene la lista de pilotos desde la base de datos.
     *
     * @return La lista de pilotos.
     * @author [Rubén García](https://github.com/Mazin04)
     */
    fun obtenerPilotos(): ArrayList<Piloto> {
        return daoInfo.obtenerPilotos()
    }

    /**
     * Obtiene la lista de circuitos desde la base de datos.
     *
     * @return La lista de circuitos.
     * @author [Rubén García](https://github.com/Mazin04)
     */
    fun obtenerCircuitos(): ArrayList<Circuitos> {
        return daoInfo.obtenerCircuitos()
    }

    /**
     * Obtiene la lista de equipos desde la base de datos.
     *
     * @return La lista de equipos.
     * @author [Rubén García](https://github.com/Mazin04)
     */
    fun obtenerEquipos(): ArrayList<Escuderia> {
        return daoInfo.obtenerEquipos()
    }

    /**
     * Obtiene la lista de nombres de pilotos desde la base de datos.
     *
     * @return La lista de nombres de pilotos.
     * @author [Rubén García](https://github.com/Mazin04)
     */
    fun obtenerNombrePilotos(): ArrayList<String> {
        return daoInfo.obtenerNombrePilotos()
    }

    /**
     * Elimina un piloto de la base de datos.
     *
     * @param nombre El nombre del piloto a eliminar.
     * @author [Rubén García](https://github.com/Mazin04)
     */
    fun eliminarPiloto(nombre : String) {
        daoInfo.eliminarPiloto(nombre)
    }

    /**
     * Instala la base de datos desde los recursos, solo la primera vez, o en caso de error de la aplicación
     *
     * @author [Rubén García](https://github.com/Mazin04)
     */
    fun instalarDDBB() {
        daoInfo.installDatabaseFromAssets()
    }

    /**
     * Obtiene la foto de un piloto desde la base de datos.
     *
     * @param nombre El nombre del piloto.
     * @return La ruta de la foto del piloto.
     * @author [Rubén García](https://github.com/Mazin04)
     */
    fun obtenerFoto(nombre: String): String {
        return daoInfo.obtenerFoto(nombre)
    }

    /**
     * Obtiene un piloto desde la base de datos.
     *
     * @param nombre El nombre del piloto.
     * @return El objeto Piloto.
     * @author [Rubén García](https://github.com/Mazin04)
     */
    fun obtenerPiloto(nombre: String): Piloto {
        return daoInfo.obtenerPiloto(nombre)
    }

    /**
     * Modifica un piloto en la base de datos.
     *
     * @param piloto El objeto Piloto con los nuevos datos.
     * @param nombreOriginal El nombre original del piloto a modificar.
     * @author [Rubén García](https://github.com/Mazin04)
     */
    fun modify(piloto: Piloto, nombreOriginal: String) {
        daoInfo.modify(piloto, nombreOriginal)
    }

    /**
     * Agrega un nuevo piloto a la base de datos.
     *
     * @param piloto El objeto Piloto a agregar.
     * @author [Rubén García](https://github.com/Mazin04)
     */
    fun add(piloto: Piloto) {
        daoInfo.add(piloto)
    }
}

