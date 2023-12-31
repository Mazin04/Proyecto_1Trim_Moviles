package com.example.mazinapp.view.login

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.mazinapp.controller.AplicacionController
import com.example.mazinapp.model.entities.Strings
import com.example.mazinapp.view.MainActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.mazinapp.rubengarcia.R
import java.io.File

/**
 * Actividad principal para el inicio de sesión y registro de usuarios.
 *
 * Esta actividad permite a los usuarios iniciar sesión o registrarse en la aplicación.
 * Proporciona funcionalidades para cambiar entre los modos de inicio de sesión y registro,
 * además de validar y procesar la información proporcionada por los usuarios.
 *
 * @property controlador El controlador de la aplicación que gestiona la lógica de la base de datos y la aplicación.
 * @property activity La referencia a la actividad actual.
 * @property imgTwt ImageView para abrir el enlace de Twitter.
 * @property imgGit ImageView para abrir el enlace de GitHub.
 * @property imgInsta ImageView para abrir el enlace de Instagram.
 * @property txtUser TextInputEditText para el nombre de usuario.
 * @property txtPassword TextInputEditText para la contraseña del usuario.
 * @property txtNombre TextInputEditText para el nombre del usuario al registrarse.
 * @property btnLoginRegister Botón para realizar la acción de inicio de sesión o registro.
 * @property msgLoginRegister TextView para cambiar entre los modos de inicio de sesión y registro.
 * @property llRegister LinearLayout que sirve como Easter Egg para reproducir sonidos.
 * @property view La vista raíz de la actividad.
 * @property reproduciendo Indica si se está reproduciendo un sonido como parte del Easter Egg.
 * @property isLogin Indica si el usuario está en modo de inicio de sesión.
 * @property valid Indica si la contraseña proporcionada durante el registro cumple con los requisitos.
 *
 * @constructor Crea una instancia de [LoginRegisterActivity].
 * @author [Rubén García](https://github.com/Mazin04)
 */
class LoginRegisterActivity : AppCompatActivity() {
    // Agrega el controlador y controla elementos del telefono
    var controlador : AplicacionController? = null
    lateinit var activity: Activity

    // Elementos
    lateinit var imgTwt : ImageView
    lateinit var imgGit : ImageView
    lateinit var imgInsta : ImageView
    lateinit var txtUser : TextInputEditText
    lateinit var txtPassword : TextInputEditText
    lateinit var txtNombre : TextInputEditText
    lateinit var btnLoginRegister : Button
    lateinit var msgLoginRegister : TextView
    lateinit var llRegister : LinearLayout
    lateinit var view : View

    //Variables propias
    var reproduciendo : Boolean = false
    var isLogin : Boolean = false
    var valid : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_register_activity)
        controlador = AplicacionController(this)
        activity = this

        var existe = existeDDBB(this, "Motorsport")
        if(!existe){
            controlador!!.instalarDDBB()
        }

        view = findViewById(R.id.LoginRegister)
        view.setOnClickListener {
            cerrarTeclado(view)
        }
        // Botones pantalla
        imgGit = findViewById(R.id.imgGit)
        imgGit.setOnClickListener { view ->
            controlador!!.abrirEnlace(Strings.URL_GITHUB.toString())
        }

        imgTwt = findViewById(R.id.imgTwt)
        imgTwt.setOnClickListener { view ->
            controlador!!.abrirEnlace(Strings.URL_TWT.toString())
        }

        imgInsta = findViewById(R.id.imgInsta)
        imgInsta.setOnClickListener { view ->
            controlador!!.abrirEnlace(Strings.URL_INSTA.toString())
        }

        //Easter EGG
        llRegister = findViewById(R.id.llRegister)
        llRegister.setOnClickListener { view ->
            if (!reproduciendo){
                controlador!!.reproducirSonido(view)
                reproduciendo = true
            } else {
                controlador!!.parar(view)
                reproduciendo = false
            }
        }

        //Cambio de modo de registrar o de inicio de sesión
        msgLoginRegister = findViewById(R.id.txtNew)
        msgLoginRegister.setOnClickListener { view ->
            if(isLogin){
                cambiarRegistrar()
                isLogin = false
            } else {
                cambiarInicioSesión()
                isLogin = true
            }
        }

        //Campos de texto y funciones
        txtUser = findViewById(R.id.txtUsuario)
        txtNombre = findViewById(R.id.txtNombre)
        txtPassword = findViewById(R.id.txtContrasenia)
        txtPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(!isLogin){
                    val regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9])\\S{10,}\$"
                    valid = Regex(regex).matches(txtPassword.text.toString())
                    if(!valid) {
                        txtPassword.setTextColor(Color.RED)
                    } else {
                        txtPassword.setTextColor(Color.BLACK)
                    }
                } else {
                    txtPassword.setTextColor(Color.BLACK)
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        // Inicio de sesión o Registro
        btnLoginRegister = findViewById(R.id.btnLoginRegistrar)
        btnLoginRegister.setOnClickListener { view ->
            if(registrarIniciarSesion(view)){
                controlador!!.cambiarVentana(this, MainActivity::class.java)
                overridePendingTransition(com.airbnb.lottie.R.anim.abc_slide_in_bottom, com.airbnb.lottie.R.anim.abc_slide_in_top)
                finish()
            }
        }

    }

    /**
     * Inicia sesión o registra al usuario según el modo actual.
     *
     * @param view La vista actual.
     * @return `true` si la acción fue exitosa, `false` de lo contrario.
     */
    fun registrarIniciarSesion(view : View): Boolean {
        if(isLogin){
            return iniciarSesion(view)
        } else {
            return registrar(view)
        }
    }

    /**
     * Inicio sesión del usuario.
     *
     * @param view La vista actual.
     * @return `true` si el inicio de sesión fue exitoso, `false` de lo contrario.
     */
    fun iniciarSesion(view: View) : Boolean {
        // Variables
        var username: String = txtUser.text.toString()
        var password: String = txtPassword.text.toString()

        if (username.isNotEmpty() && password.isNotEmpty()) {
            // Cerrar el teclado después de todo
            cerrarTeclado(view)

            val existe: Boolean = controlador!!.comprobarUsuario(username)
            val correcto: Boolean = controlador!!.comprobarContrasena(username, password)

            if (!existe || !correcto) {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show()
            } else {
                val nombreUsuario = controlador!!.getNombre(username)
                val bienvenido: String = "Bienvenido $nombreUsuario"
                txtUser.setText("")
                txtPassword.setText("")
                AplicacionController.rol = controlador!!.obtenerRol(username)
                Toast.makeText(this, bienvenido, Toast.LENGTH_SHORT).show()
                return true
            }
        } else {
            // Cerrar el teclado después de todo
            cerrarTeclado(view)
            Toast.makeText(this, "No se han completado todos los campos", Toast.LENGTH_LONG).show()
        }
        return false
    }

    /**
     * Registra a un nuevo usuario.
     *
     * @param view La vista actual.
     * @return `true` si el registro fue exitoso, `false` de lo contrario.
     */
    fun registrar(view : View) : Boolean {
        var name : String = txtNombre.text.toString()
        var username : String = txtUser.text.toString()
        var password : String = txtPassword.text.toString()

        if (name.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()) {
            cerrarTeclado(view)

            val existeUsuario : Boolean = controlador!!.comprobarUsuario(username)
            if (existeUsuario){
                txtUser.setTextColor(Color.RED)
                Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_SHORT).show()
            } else {
                txtUser.setTextColor(Color.BLACK)
                if (!valid){
                    val dialog = AlertDialog.Builder(this)
                        .setTitle("Requisitos contraseña")
                        .setMessage("Su contraseña no cumple todos los requisitos \n\nSu contraseña debe contener:\n - Al menos 10 caracteres \n - Al menos 1 dígito \n - Al menos un letra \n - Al menos una letra mayúscula \n - Al menos un símbolo especial")
                        .setPositiveButton("Aceptar", null)
                        .show()
                } else {
                    Toast.makeText(this, "Registrado exitosamente!", Toast.LENGTH_SHORT).show()
                    controlador!!.agregarUsuario(name, username, password)
                    AplicacionController.rol = controlador!!.obtenerRol(username)
                    return true
                }
            }
        } else {
            cerrarTeclado(view)
            Toast.makeText(this, "No se han completado todos los campos", Toast.LENGTH_LONG).show()
        }
        return false
    }

    /**
     * Cierra el teclado cuando se le llama
     *
     * @param view La vista raíz del fragmento.
     */
    fun cerrarTeclado(view: View) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
    /**
     * Cambia la interfaz para el modo de inicio de sesión.
     */
    fun cambiarInicioSesión() {
        findViewById<TextInputLayout>(R.id.txtLayoutNombre).visibility = TextInputLayout.GONE
        findViewById<TextInputLayout>(R.id.txtLayoutNombre).startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.fade_out).apply {
                duration = 1000
            }
        )
        findViewById<TextView>(R.id.txtNombre).text = ""
        findViewById<Button>(R.id.btnLoginRegistrar).text = "Iniciar Sesión"
        findViewById<TextView>(R.id.txtNew).text = "No tienes cuenta? Registrate"
        findViewById<TextView>(R.id.txtRegister).text = "Iniciar Sesión"
    }

    /**
     * Cambia la interfaz para el modo de registro.
     */
    fun cambiarRegistrar() {
        findViewById<TextInputLayout>(R.id.txtLayoutNombre).visibility = TextInputLayout.VISIBLE
        findViewById<TextInputLayout>(R.id.txtLayoutNombre).startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.fade_in).apply {
                duration = 1000
            }
        )
        findViewById<TextView>(R.id.txtNombre).text = ""
        findViewById<Button>(R.id.btnLoginRegistrar).text = "Registrarse"
        findViewById<TextView>(R.id.txtNew).text = "Ya tienes cuenta? Inicia Sesión"
        findViewById<TextView>(R.id.txtRegister).text = "Regístrate"
    }

    /**
     * Maneja el evento de retroceso y muestra un diálogo de confirmación para cerrar la aplicación.
     */    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Seguro que quiere cerrar la aplicación?").setMessage("No lo haga, le echaremos de menos").setPositiveButton("Sí", DialogInterface.OnClickListener { dialog, which ->
            finish()
        }).setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        }).show()
    }

    /**
     * Verifica si la base de datos con el nombre proporcionado existe en la aplicación.
     *
     * @param context El contexto de la aplicación.
     * @param nombre El nombre de la base de datos.
     * @return `true` si la base de datos existe, `false` de lo contrario.
     */
    fun existeDDBB(context : Context, nombre : String) : Boolean{
        var fileDDBB : File = context.getDatabasePath(nombre)
        return fileDDBB.exists()
    }

}