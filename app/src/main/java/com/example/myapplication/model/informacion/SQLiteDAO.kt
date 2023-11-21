package com.example.myapplication.model.informacion

import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.myapplication.model.entities.Circuitos
import com.example.myapplication.model.entities.Escuderia
import com.example.myapplication.model.entities.Piloto
import java.io.File
import java.io.FileOutputStream

class SQLiteDAO(val context : Context, name: String?, factory : SQLiteDatabase.CursorFactory?, version : Int) : InfoDAO, SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "Motorsport"
        const val DATABASE_VERSION = 1
        const val ASSETS_PATH = "databases"
    }

    private fun installDatabaseFromAssets(){
        val inputStream = context.assets.open("${SQLiteDAO.ASSETS_PATH}/${SQLiteDAO.DATABASE_NAME}.db")

        try {
            val outputFile = File(context.getDatabasePath(DATABASE_NAME).path)
            val outputStream = FileOutputStream(outputFile)

            inputStream.copyTo(outputStream)
            inputStream.close()

            outputStream.flush()
            outputStream.close()
        } catch (e : Throwable) {
            Toast.makeText(context, "La BBDD $DATABASE_NAME no se ha podido instalar", Toast.LENGTH_SHORT).show()
        }
    }

    @Synchronized
    private fun installOrUpdateIfNecessary() {
        context.deleteDatabase(DATABASE_NAME)
        installDatabaseFromAssets()
        writeDatabaseVersionInPreferences()
    }

    private val preferences: SharedPreferences = context.getSharedPreferences(
        "${context.packageName}.database_versions",
        Context.MODE_PRIVATE
    )

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
        //Nada ya que usamos base de datos propia
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //Nada
    }

    override fun obtenerPilotos(): ArrayList<Piloto> {
        installOrUpdateIfNecessary()
        var lista = ArrayList<Piloto>()
        val db = readableDatabase
        val cursor = db.query("Pilotos", arrayOf("*"),null, null, null, null, "campeonatos DESC, podio DESC, puntos DESC")
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val piloto = Piloto(
                    cursor.getString(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getString(6),
                    cursor.getInt(7),
                    cursor.getString(8)
                )
                lista.add(piloto)
            }
        }
        cursor.close()
        return lista
    }

    override fun obtenerCircuitos(): ArrayList<Circuitos> {
            installOrUpdateIfNecessary()
            var lista = ArrayList<Circuitos>()
            val db = readableDatabase
            val cursor = db.query("circuitos", arrayOf("*"),null, null, null, null, null)
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val circuito = Circuitos(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getDouble(2),
                        cursor.getInt(3),
                        cursor.getString(4)
                    )
                    lista.add(circuito)
                }
            }
            cursor.close()
            return lista
        }

    override fun obtenerEquipos(): ArrayList<Escuderia> {
        installOrUpdateIfNecessary()
        var lista = ArrayList<Escuderia>()
        val db = readableDatabase
        val cursor = db.query("equipos", arrayOf("*"), null, null, null, null, "n_equipo")
        if(cursor != null) {
            while (cursor.moveToNext()){
                val equipo = Escuderia(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
                )
                lista.add(equipo)
            }
        }
        cursor.close()
        return lista
    }

    override fun add(piloto: Piloto) {
        TODO("Not yet implemented")
    }

    override fun add(escuderia: Escuderia) {
        TODO("Not yet implemented")
    }

    override fun add(circuitos: Circuitos) {
        TODO("Not yet implemented")
    }

    override fun modify(piloto: Piloto) {
        TODO("Not yet implemented")
    }

    override fun modify(escuderia: Escuderia) {
        TODO("Not yet implemented")
    }

    override fun modify(circuitos: Circuitos) {
        TODO("Not yet implemented")
    }

    override fun delete(piloto: Piloto) {
        TODO("Not yet implemented")
    }

    override fun delete(escuderia: Escuderia) {
        TODO("Not yet implemented")
    }

    override fun delete(circuitos: Circuitos) {
        TODO("Not yet implemented")
    }
}
