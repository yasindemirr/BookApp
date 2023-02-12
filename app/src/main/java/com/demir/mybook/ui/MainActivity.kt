package com.demir.mybook.ui

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.demir.mybook.R
import com.demir.mybook.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MyBook)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView=findViewById<NavigationView>(R.id.navDraw)
        navView.itemIconTintList=null
        binding.imgMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        val nav= Navigation.findNavController(this,R.id.navHostFragment)
        NavigationUI.setupWithNavController(navView,nav)


        nav.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.homeFragment ->binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED)
                else -> binding.drawerLayout.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
            }
            when(destination.id){
                R.id.homeFragment->binding.imgMenu.visibility=View.VISIBLE
                else ->binding.imgMenu.visibility=View.GONE
            }
        }
        navView.menu.findItem(R.id.exitFrom).setOnMenuItemClickListener {
            AlertDialog.Builder(this@MainActivity).setTitle("Alert Dialog")
                .setMessage("Dou you want to exit?")
                .setPositiveButton("Yes",object :DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        finish()
                    }
                }).setNegativeButton("No",object :DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        Toast.makeText(this@MainActivity,"da",Toast.LENGTH_SHORT) .show()
                    }
                }).create().show()
             true
        }

        /*
        exit.setOnClickListener {

        }

         */



    }

}