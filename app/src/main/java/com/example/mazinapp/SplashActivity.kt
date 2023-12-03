package com.example.mazinapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import com.example.mazinapp.controller.AplicacionController
import android.os.Handler
import com.example.mazinapp.view.login.LoginRegisterActivity
import org.mazinapp.rubengarcia.R

/**
 * Video que aparece según se hace click en el icono de la aplicación para abrirla.
 *
 * @property controlador El controlador de la aplicación.
 * @constructor Crea una instancia de la clase SplashActivity.
 * @see [AplicacionController]
 * @see [LoginRegisterActivity]
 * @constructor Crea una instancia de la clase SplashActivity.
 * @author [Rubén García](https://github.com/Mazin04)
 */
class SplashActivity : AppCompatActivity() {
    /**
     * Controlador de la aplicación.
     */
    var controlador : AplicacionController? = null

    /**
     * Captura el arranque de la aplicación para realizar una SplashScreen antes de inicializar la aplicación.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        controlador = AplicacionController(this)

        Handler(Looper.getMainLooper()).postDelayed({
            controlador!!.cambiarVentana(this, LoginRegisterActivity::class.java)
            overridePendingTransition(com.airbnb.lottie.R.anim.abc_popup_enter, com.airbnb.lottie.R.anim.abc_popup_exit)
            finish()
        },2000)
    }
}