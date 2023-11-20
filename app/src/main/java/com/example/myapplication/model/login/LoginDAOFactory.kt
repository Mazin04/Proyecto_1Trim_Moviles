package com.example.myapplication.model.login

import android.content.Context
import android.database.sqlite.SQLiteDatabase

object LoginDAOFactory {
    public val MODO_SQLITE = 1

    public fun getDao(modo : Int, context : Context, nombreDDBB : String, factory : SQLiteDatabase.CursorFactory?, version : Int) : LoginDBDAO {
        return when (modo) {
            else -> {return LoginDBDAO(context, nombreDDBB, factory, version)
            }
        }

    }
}