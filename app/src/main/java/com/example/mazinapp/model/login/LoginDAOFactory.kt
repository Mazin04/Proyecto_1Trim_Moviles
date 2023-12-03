package com.example.mazinapp.model.login

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.mazinapp.model.informacion.DAOFactory.MODO_SQLITE
import com.example.mazinapp.model.informacion.InfoDAO
import com.example.mazinapp.model.informacion.SQLiteDAO

/**
 * Objeto que actúa como una fábrica para obtener instancias de DAO (Data Access Object).
 *
 * @property MODO_SQLITE Constante que representa el modo SQLite.
 * @see [LoginDBDAO]
 * @constructor Crea una instancia del objeto DAOFactory.
 * @author [Rubén García](https://github.com/Mazin04)
 */
object LoginDAOFactory {
    /**
     * Constante que representa el modo SQLite.
     * Tendría sentido si fuese una aplicación más complicada donde habría más modelos de acceso o incluso un modelo de prueba
     */
    public val MODO_SQLITE = 1

    /**
     * Obtiene una instancia de DAO según el modo especificado.
     *
     * @param modo El modo de operación del DAO.
     * @param context El contexto de la aplicación.
     * @param nombreDDBB El nombre de la base de datos.
     * @param factory La fábrica de cursores para la base de datos (puede ser nula).
     * @param version La versión de la base de datos.
     * @return Una instancia de [LoginDBDAO]
     * @see [LoginDBDAO]
     */
    public fun getDao(modo : Int, context : Context, nombreDDBB : String, factory : SQLiteDatabase.CursorFactory?, version : Int) : LoginDBDAO {
        return when (modo) {
            1 -> return LoginDBDAO(context, nombreDDBB, factory, version)
            else -> {return LoginDBDAO(context, nombreDDBB, factory, version)
            }
        }

    }
}