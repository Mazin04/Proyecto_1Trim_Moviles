package com.example.myapplication.view

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.removeItemAt
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.controller.AplicacionController
import com.example.myapplication.view.login.LoginRegisterActivity
import com.example.myapplication.view.motorsport.DriversFragment
import com.example.myapplication.view.motorsport.TeamsFragment
import com.example.myapplication.view.motorsport.TracksFragment
import com.google.android.material.navigation.NavigationView
import java.io.File

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var controlador : AplicacionController? = null
    private lateinit var activity: Activity

    lateinit var drawerLayout : DrawerLayout
    lateinit var navigationView : NavigationView
    var toolbar : Toolbar?  = null
    var toolbarText : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        controlador = AplicacionController(this)
        activity = this

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

        if (savedInstanceState == null){
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, DriversFragment()).commit()
            navigationView.setCheckedItem(R.id.nav_piloto)
        }

        val menu = navigationView.menu
        val configItem = menu.findItem(R.id.nav_config)
        if(!AplicacionController.rol.equals("admin")){
            configItem.isVisible = false
        } else {
            configItem.isVisible = true
        }

    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("¿Seguro que desea salir?")
                .setMessage("Si sale, tendrá que volver a iniciar sesión")
                .setPositiveButton("Sí", DialogInterface.OnClickListener { dialog, which ->
                    controlador!!.cambiarVentana(this, LoginRegisterActivity::class.java)
                    finish()
                }).setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                }).show()
        }
    }

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
    private fun cargarFragment(fragmento : Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragmento).commit()
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in)
    }
}