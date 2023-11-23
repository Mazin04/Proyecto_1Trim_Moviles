package com.example.myapplication.model.informacion

import android.content.ContentValues
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
        var INSTALADO = false
    }

    override public fun installDatabaseFromAssets(){
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
    }

    override fun onCreate(db: SQLiteDatabase?) {
        //
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //Nada
    }

    override fun obtenerPilotos(): ArrayList<Piloto> {
        var lista = ArrayList<Piloto>()
        val db = writableDatabase
        val cursor = db.query("pilotos", arrayOf("*"),null, null, null, null, "campeonatos DESC, podio DESC, puntos DESC")
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
        db.close()
        return lista
    }

    override fun obtenerCircuitos(): ArrayList<Circuitos> {
            var lista = ArrayList<Circuitos>()
            val db = writableDatabase
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
            db.close()
        return lista
        }

    override fun obtenerEquipos(): ArrayList<Escuderia> {
        var lista = ArrayList<Escuderia>()
        val db = writableDatabase
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
        db.close()

        return lista
    }

    override fun add(piloto: Piloto) {
        val db = writableDatabase
        val values = ContentValues()
        values.put("nombre", piloto.getNombre())
        values.put("numero", piloto.getNumero())
        values.put("puntos", piloto.getPuntos())
        values.put("campeonatos", piloto.getCampeonatos())
        values.put("victorias", piloto.getVictorias())
        values.put("podio", piloto.getPodios())
        values.put("n_equipo", piloto.getNEquipo())
        values.put("gps", piloto.getGrandesPremios())
        values.put("foto", piloto.getFoto())
        db.insert("pilotos", null, values)
        db.close()
    }

    override fun eliminarPiloto(nombre: String) {
        val db = writableDatabase
        val sql = "DELETE FROM pilotos WHERE nombre = ?"
        val statement = db.compileStatement(sql)
        statement.bindString(1, nombre)
        statement.executeUpdateDelete()
        db.close()
    }


    override fun modify(piloto: Piloto, nombreOriginal : String) {
        eliminarPiloto(nombreOriginal)
        add(piloto)
    }

    override fun obtenerNombrePilotos(): ArrayList<String> {
        var lista = ArrayList<String>()
        val db = writableDatabase
        val cursor = db?.query("pilotos", arrayOf("nombre"), null, null, null, null, "nombre")
        if (cursor != null) {
            while (cursor.moveToNext()){
                val nombre = cursor.getString(0)
                lista.add(nombre)
            }
        }
        db.close()
        return lista
    }

    override fun obtenerFoto(nombre: String): String {
        var foto : String = ""
        val db = writableDatabase
        var cursor = db.rawQuery("SELECT foto FROM pilotos WHERE nombre = ?", arrayOf(nombre))
        if (cursor.moveToFirst()){
            foto = cursor.getString(0)
        }

        return foto
    }

    override fun obtenerPiloto(nombre: String): Piloto {
        val db = writableDatabase
        val sql = "SELECT * FROM pilotos WHERE nombre = ?"
        lateinit var piloto : Piloto
        val cursor = db.rawQuery(sql, arrayOf(nombre))
        if(cursor != null) {
            while (cursor.moveToNext()){
                piloto = Piloto(
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
            }
        }
        cursor.close()
        db.close()
        return piloto
    }
}