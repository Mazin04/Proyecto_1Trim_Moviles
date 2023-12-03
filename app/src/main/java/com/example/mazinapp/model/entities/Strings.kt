package com.example.mazinapp.model.entities

/**
 * Enumeración que contiene diferentes URL que se usan en la aplicación.
 *
 * @property nombre La cadena de texto asociada al elemento de la enumeración.
 * @constructor Crea una instancia del elemento de la enumeración con la cadena de texto proporcionada.
 * @param nombre La cadena de texto asociada al elemento de la enumeración.
 * @see [Strings.URL_TWT](https://twitter.com/rubengs_04)
 * @see [Strings.URL_INSTA](https://www.instagram.com/rubengs_04/)
 * @see [Strings.URL_GITHUB](https://github.com/Mazin04)
 * @author [Rubén García](https://github.com/Mazin04)
 */
enum class Strings(val nombre: String) {
    /**
     * URL del perfil de Twitter.
     */
    URL_TWT("https://twitter.com/rubengs_04"),

    /**
     * URL del perfil de Instagram.
     */
    URL_INSTA("https://www.instagram.com/rubengs_04/"),

    /**
     * URL del perfil de GitHub.
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