package com.example.myapplication.model.login

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream

class LoginDBDAO(val context : Context,
                 name: String?,
                 factory : SQLiteDatabase.CursorFactory?,
                 version : Int) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object {
        const val DATABASE_NAME = "Login"
        const val DATABASE_VERSION = 1
        const val ASSETS_PATH = "databases"
    }

    private fun installDatabaseFromAssets(){
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

    @Synchronized
    private fun installOrUpdateIfNecessary() {
        if (installedDatabaseIsOutdated()) {
            context.deleteDatabase(DATABASE_NAME)
            installDatabaseFromAssets()
            writeDatabaseVersionInPreferences()
        }
    }

    private val preferences: SharedPreferences = context.getSharedPreferences(
        "${context.packageName}.database_versions",
        Context.MODE_PRIVATE
    )
    private fun installedDatabaseIsOutdated(): Boolean {
        return preferences.getInt(DATABASE_NAME, 0) < DATABASE_VERSION
    }
    private fun writeDatabaseVersionInPreferences() {
        preferences.edit().apply {
            putInt(DATABASE_NAME, DATABASE_VERSION)
            apply()
        }
    }
    override fun getReadableDatabase(): SQLiteDatabase {
        installOrUpdateIfNecessary()
        return super.getReadableDatabase()
    }


    override fun onCreate(db: SQLiteDatabase?) {
        // Al usar base de datos propia no hace falta nada aquí
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Nada, de momento
    }
    fun comprobarExistenciaUsuario(username : String, context : Context) : Boolean{
        val db = readableDatabase
        val cursor = db?.query("Login",arrayOf("*"),"Username = ?",arrayOf(username),null,null,null)
        if (cursor?.moveToFirst() == true){
            return true
        }
        return false
    }

    fun comprobarContraseña(username: String, password: String, context: Context): Boolean {
        val db = readableDatabase
        val cursor = db?.query("Login", arrayOf("*"), "Username = ? and Password = ?", arrayOf(username, password), null, null, null)
        if (cursor?.moveToFirst() == true){
            return true
        }
        return false
    }

    fun getNombre(username: String, context: Context): String {
        val db = readableDatabase
        val cursor = db?.query("Persona", arrayOf("Nombre"), "Username = ?", arrayOf(username), null, null, null)
        if (cursor?.moveToFirst() == true){
            return cursor.getString(0)
        }
        return ""
    }

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
    }

    fun obtenerRol(username: String): String {
        val db = readableDatabase
        val cursor = db?.query("Login", arrayOf("Rol"), "Username = ?", arrayOf(username), null, null, null)
        if(cursor?.moveToFirst() == true){
            return cursor.getString(0)
        }
        return ""
    }
}
