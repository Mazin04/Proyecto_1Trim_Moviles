package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import com.example.myapplication.controller.AplicacionController
import android.os.Handler
import com.example.myapplication.view.login.LoginRegisterActivity


class SplashActivity : AppCompatActivity() {
    var controlador : AplicacionController? = null
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