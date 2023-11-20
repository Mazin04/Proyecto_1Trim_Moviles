package com.example.myapplication.model.entities

enum class Strings(val nombre: String) {
    URL_TWT("https://twitter.com/rubengs_04"),
    URL_INSTA("https://www.instagram.com/rubengs_04/"),
    URL_GITHUB("https://github.com/Mazin04");

    override fun toString(): String {
        return nombre
    }
}