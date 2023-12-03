package com.example.mazinapp.view

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.mazinapp.controller.AplicacionController
import com.example.mazinapp.view.login.LoginRegisterActivity
import com.example.mazinapp.view.motorsport.DriversFragment
import com.example.mazinapp.view.motorsport.TeamsFragment
import com.example.mazinapp.view.motorsport.TracksFragment
import com.google.android.material.navigation.NavigationView
import org.mazinapp.rubengarcia.R

/**
 * Actividad principal que contiene la barra lateral para gestionar el frameLayout contenedor del resto de
 * Fragments de la aplicación.
 *
 * @property controlador El controlador de la aplicación.
 * @property activity La instancia de la actividad.
 * @property drawerLayout El diseño del cajón de navegación.
 * @property navigationView La vista de navegación lateral.
 * @property toolbar La barra de herramientas de la actividad.
 * @property toolbarText El TextView dentro de la barra de herramientas para mostrar el título.
 * @constructor Crea una instancia de la clase MainActivity.
 * @see [AplicacionController]
 * @see [DriversFragment]
 * @see [TeamsFragment]
 * @see [TracksFragment]
 * @see [configFragment]
 * @constructor Crea una instancia de la clase MainActivity.
 * @author [Rubén García](https://github.com/Mazin04)
 */
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    /**
     * El controlador de la aplicación.
     */
    var controlador: AplicacionController? = null

    /**
     * La instancia de la actividad.
     */
    private lateinit var activity: Activity

    /**
     * El diseño del cajón de navegación.
     */
    lateinit var drawerLayout: DrawerLayout

    /**
     * La vista de navegación lateral.
     */
    lateinit var navigationView: NavigationView

    /**
     * La toolbar, es decir, la barra de arriba que se muestra todo el rato tras iniciar sesión.
     */
    var toolbar: Toolbar? = null

    /**
     * El TextView dentro de la barra de herramientas para mostrar el título.
     */
    var toolbarText: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar el controlador de la aplicación

        controlador = AplicacionController(this)
        activity = this

        // Configuración de la barra de herramientas y la vista de navegación lateral
        toolbar = findViewById(R.id.toolbar)
        toolbarText = findViewById(R.id.toolbarText)
        setSupportActionBar(toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.bringToFront()
        var toggle : ActionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Cargar el fragmento inicial al iniciar la actividad
        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, DriversFragment()).commit()
            navigationView.setCheckedItem(R.id.nav_piloto)
        }

        // Ocultar la opción de configuración si el rol no es "admin"
        val menu = navigationView.menu
        val configItem = menu.findItem(R.id.nav_config)
        configItem.isVisible = AplicacionController.rol.equals("admin")

    }

    /**
     * Método invocado al presionar el botón de atrás, o realizar el gesto desde el borde
     * izquierdo de la pantalla.
     * Este método muestra un aviso preguntando si realmente se quiere cerrar sesión.
     * En caso de decir 'si', se cierra, sino, no.
     */
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            // Mostrar un diálogo de confirmación al intentar salir
            val builder = AlertDialog.Builder(this)
            builder.setTitle("¿Seguro que desea salir?")
                .setMessage("Si sale, tendrá que volver a iniciar sesión")
                .setPositiveButton("Sí") { dialog, which ->
                    controlador!!.cambiarVentana(this, LoginRegisterActivity::class.java)
                    finish()
                }.setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                }.show()
        }
    }

    /**
     * Método invocado al seleccionar un elemento en la vista de navegación lateral.
     *
     * @param menuItem El elemento de menú seleccionado.
     * @return `true` si se maneja la selección del elemento; de lo contrario, `false`.
     */
    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            R.id.nav_piloto -> {
                toolbarText!!.setText("Pilotos")
                cargarFragment(DriversFragment())
            }
            R.id.nav_team -> {
                toolbarText!!.setText("Escuderías")
                cargarFragment(TeamsFragment())
            }
            R.id.nav_track -> {
                toolbarText!!.setText("Circuitos")
                cargarFragment(TracksFragment())
            }
            R.id.nav_config -> {
                toolbarText!!.setText("Configuración")
                cargarFragment(configFragment())
            }
            R.id.cerrar_sesion -> {
                // Mostrar un diálogo de confirmación al intentar cerrar sesión
                val builder = AlertDialog.Builder(this)
                builder.setTitle("¿Seguro que desea cerrar sesión?")
                    .setPositiveButton("Sí", DialogInterface.OnClickListener { dialog, which ->
                        controlador!!.cambiarVentana(this, LoginRegisterActivity::class.java)
                        this.finish()
                    }).setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                        dialog.dismiss()
                        navigationView.setCheckedItem(R.id.nav_piloto)
                    }).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START, true)
        return true
    }
    /**
     * Carga el fragmento especificado en el contenedor de fragmentos.
     *
     * @param fragmento El fragmento a cargar.
     */
    private fun cargarFragment(fragmento : Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragmento).commit()
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in)
    }
}