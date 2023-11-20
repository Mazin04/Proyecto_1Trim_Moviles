package com.example.myapplication.model.informacion

import android.content.Context
import android.database.sqlite.SQLiteDatabase

object DAOFactory {
    public val MODO_SQLITE = 1

    public fun getDao(modo : Int, context : Context, nombreDDBB : String, factory : SQLiteDatabase.CursorFactory?, version : Int) : InfoDAO {
        return when (modo) {
            1 -> return SQLiteDAO(context, nombreDDBB, factory, version)
            else -> {return SQLiteDAO(context, nombreDDBB, factory, version)
            }
        }

    }
}