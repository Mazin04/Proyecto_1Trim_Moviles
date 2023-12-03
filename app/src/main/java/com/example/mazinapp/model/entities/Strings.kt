package com.example.mazinapp.model.entities

/**
 * Enumeración que contiene diferentes URL que se usan en la aplicación.
 *
 * @property nombre La cadena de texto asociada al elemento de la enumeración.
 * @constructor Crea una instancia del elemento de la enumeración con la cadena de texto proporcionada.
 * @param nombre La cadena de texto asociada al elemento de la enumeración.
 * @author [Rubén García](https://github.com/Mazin04)
 */
enum class Strings(val nombre: String) {
    /**
     * URL del perfil de Twitter.
     * @see [URL Twitter](https://twitter.com/rubengs_04)
     */
    URL_TWT("https://twitter.com/rubengs_04"),

    /**
     * URL del perfil de Instagram.
     * @see [URL Instagram](https://www.instagram.com/rubengs_04)
     */
    URL_INSTA("https://www.instagram.com/rubengs_04/"),

    /**
     * URL del perfil de GitHub.
     * @see [URL GitHub](https://github.com/Mazin04)
     */
    URL_GITHUB("https://github.com/Mazin04");

    /**
     * Devuelve la cadena de texto asociada al elemento de la enumeración.
     * @return La cadena de texto asociada al elemento.
     */
    override fun toString(): String {
        return nombre
    }
}