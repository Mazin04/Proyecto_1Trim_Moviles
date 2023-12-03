package com.example.mazinapp.model.login

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream

/**
 * Clase que implementa la lógica de acceso a la base de datos SQLite para autenticación y gestión de usuarios.
 *
 * @property context El contexto de la aplicación.
 * @property name El nombre de la base de datos.
 * @property factory La fábrica de cursores para la base de datos (puede ser nula).
 * @property version La versión de la base de datos.
 * @constructor Crea una instancia de la clase LoginDBDAO.
 * @param context El contexto de la aplicación.
 * @param name El nombre de la base de datos.
 * @param factory La fábrica de cursores para la base de datos (puede ser nula).
 * @param version La versión de la base de datos.
 * @constructor Crea una instancia de la clase LoginDBDAO.
 * @author [Rubén García](https://github.com/Mazin04)
 */
class LoginDBDAO(val context : Context,
                 name: String?,
                 factory : SQLiteDatabase.CursorFactory?,
                 version : Int) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object {
        /**
         * El nombre de la base de datos.
         */
        const val DATABASE_NAME = "Login"
        /**
         * La versión de la base de datos.
         */
        const val DATABASE_VERSION = 1
        /**
         * La ruta de los activos que contienen la base de datos.
         */
        const val ASSETS_PATH = "databases"
    }

    /**
     * Instala la base de datos desde los archivos al arrancar por primera vez la aplicación o en caso de error.
     */
    fun installDatabaseFromAssets(){
        val inputStream = context.assets.open("$ASSETS_PATH/${DATABASE_NAME}.db")

        try {
            val outputFile = File(context.getDatabasePath(DATABASE_NAME).path)
            val outputStream = FileOutputStream(outputFile)

            inputStream.copyTo(outputStream)
            inputStream.close()

            outputStream.flush()
            outputStream.close()
        } catch (e : Throwable) {
            Toast.makeText(context, "The $DATABASE_NAME no se ha podido instalar", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Instala la base de datos desde los activos si es necesario o si la versión instalada es anterior.
     */
    @Synchronized
    fun installOrUpdateIfNecessary() {
        if (installedDatabaseIsOutdated()) {
            context.deleteDatabase(DATABASE_NAME)
            installDatabaseFromAssets()
            writeDatabaseVersionInPreferences()
        }
    }
    /**
     * Obtiene el objeto [SharedPreferences] para almacenar la versión de la base de datos instalada.
     */
    val preferences: SharedPreferences = context.getSharedPreferences(
        "${context.packageName}.database_versions",
        Context.MODE_PRIVATE
    )

    /**
     * Comprueba si la base de datos instalada es antigua o no.
     * @return 'true' si es antigua, 'false' si no lo es.
     */
    fun installedDatabaseIsOutdated(): Boolean {
        return preferences.getInt(DATABASE_NAME, 0) < DATABASE_VERSION
    }
    /**
     * Escribe la versión de la base de datos instalada en las preferencias.
     */
    fun writeDatabaseVersionInPreferences() {
        preferences.edit().apply {
            putInt(DATABASE_NAME, DATABASE_VERSION)
            apply()
        }
    }

    /**
     * Obtiene una instancia de base de datos legible, asegurándose de instalar o actualizar la base de datos si es necesario.
     * @return Una instancia de base de datos legible.
     */
    override fun getReadableDatabase(): SQLiteDatabase {
        installOrUpdateIfNecessary()
        return super.getReadableDatabase()
    }
    /**
     * Método invocado al crear la base de datos (no se requiere acción específica aquí).
     */
    override fun onCreate(db: SQLiteDatabase?) {
        // Al usar base de datos propia no hace falta nada aquí
    }
    /**
     * Método invocado al actualizar la base de datos (no se requiere acción específica aquí).
     */
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Nada, de momento
    }
    /**
     * Verifica la existencia de un usuario en la base de datos.
     *
     * @param username El nombre de usuario a verificar.
     * @param context Contexto de la aplicación
     * @return `true` si el usuario existe; de lo contrario, `false`.
     */
    fun comprobarExistenciaUsuario(username : String, context : Context) : Boolean{
        val db = readableDatabase
        val cursor = db?.query("Login",arrayOf("*"),"Username = ?",arrayOf(username),null,null,null)
        if (cursor?.moveToFirst() == true){
            return true
        }
        db.close()
        return false
    }

    /**
     * Verifica la contraseña de un usuario en la base de datos.
     *
     * @param username El nombre de usuario.
     * @param password La contraseña a verificar.
     * @return `true` si la contraseña es correcta; de lo contrario, `false`.
     */
    fun comprobarContraseña(username: String, password: String, context: Context): Boolean {
        val db = readableDatabase
        val cursor = db?.query("Login", arrayOf("*"), "Username = ? and Password = ?", arrayOf(username, password), null, null, null)
        if (cursor?.moveToFirst() == true){
            return true
        }
        db.close()
        return false
    }
    /**
     * Obtiene el nombre de un usuario en la base de datos.
     *
     * @param username El nombre de usuario.
     * @return El nombre del usuario o una cadena vacía si no se encuentra.
     */
    fun getNombre(username: String, context: Context): String {
        val db = readableDatabase
        val cursor = db?.query("Persona", arrayOf("Nombre"), "Username = ?", arrayOf(username), null, null, null)
        if (cursor?.moveToFirst() == true){
            return cursor.getString(0)
        }
        db.close()
        return ""
    }
    /**
     * Agrega un nuevo usuario a la base de datos.
     *
     * @param name El nombre del nuevo usuario.
     * @param username El nombre de usuario del nuevo usuario.
     * @param password La contraseña del nuevo usuario.
     */
    fun agregarUsuario(name: String, username: String, password: String, context: Context) {
        val db = writableDatabase
        // Insertar el usuario en la tabla Persona
        val persona = ContentValues()
        persona.put("Nombre", name)
        persona.put("Username", username)
        db.insert("Persona", null, persona)

        // Insertar el usuario en la tabla Login
        val login = ContentValues()
        login.put("Username", username)
        login.put("Password", password)
        login.put("Rol", "normal")
        db.insert("Login", null, login)
        db.close()
    }
    /**
     * Obtiene el rol de un usuario en la base de datos.
     *
     * @param username El nombre de usuario.
     * @return El rol del usuario o una cadena vacía si no se encuentra.
     */
    fun obtenerRol(username: String): String {
        val db = readableDatabase
        val cursor = db?.query("Login", arrayOf("Rol"), "Username = ?", arrayOf(username), null, null, null)
        if(cursor?.moveToFirst() == true){
            return cursor.getString(0)
        }
        db.close()
        return ""
    }
}
