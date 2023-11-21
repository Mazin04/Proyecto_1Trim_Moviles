package com.example.myapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.FragmentConfigBinding
import com.example.myapplication.view.config.addFragment
import com.example.myapplication.view.config.deleteFragment
import com.example.myapplication.view.config.modifyFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class configFragment : Fragment() {
    lateinit var btmNavBar : ChipNavigationBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view : View = inflater.inflate(R.layout.fragment_config, container, false)
        btmNavBar = view.findViewById(R.id.bottomNavigationView)
        btmNavBar.setItemSelected(R.id.insertarPiloto)
        cargarFragmento(addFragment())
        btmNavBar.setOnItemSelectedListener {
            when (it) {
                R.id.insertarPiloto -> cargarFragmento(addFragment())
                R.id.eliminarPiloto -> cargarFragmento(deleteFragment())
                R.id.modificarPiloto -> cargarFragmento(modifyFragment())
            }
        }
        return view
    }

    private fun cargarFragmento(fragment : Fragment) {
        fragmentManager?.beginTransaction()?.replace(R.id.frameLayout, fragment)!!.commit()
    }

}