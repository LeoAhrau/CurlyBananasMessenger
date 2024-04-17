package com.example.curlybananasmessenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewbinding.ViewBinding
import com.example.curlybananasmessenger.databinding.ActivityBaseBinding
import com.example.curlybananasmessenger.databinding.ActivityContactBinding
import com.google.android.material.navigation.NavigationView

 open class BaseActivity : AppCompatActivity() {

    open lateinit var drawerLayout: DrawerLayout
    open lateinit var  actionBarDrawerToggle: ActionBarDrawerToggle

     override fun setContentView(view: View?) {

         drawerLayout = layoutInflater.inflate(R.layout.activity_base, null) as DrawerLayout
         val container = drawerLayout.findViewById<FrameLayout>(R.id.frame_container) // OBS! Container
         container.addView(view)
         super.setContentView(drawerLayout)

         val toolbar = drawerLayout.findViewById<Toolbar>(R.id.toolbar)
         setSupportActionBar(toolbar)
         val navigationView = drawerLayout.findViewById<NavigationView>(R.id.nav_view)
         navigationView.setNavigationItemSelectedListener {
             onOptionsItemSelected(it)
         }

         val toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
         drawerLayout.addDrawerListener(toggle)
         toggle.syncState()




     }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_base)
//        // init drawer layout
//        drawerLayout = findViewById(R.id.drawer_layout)
//        // init action bar drawer toggle
//        actionBarDrawerToggle = ActionBarDrawerToggle(this,drawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close)
//        // add a drawer listener into  drawer layout
//        drawerLayout.addDrawerListener(actionBarDrawerToggle)
//        actionBarDrawerToggle.syncState()
//
//        // show menu icon and back icon while drawer open
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // check conndition for drawer item with menu item
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            true
        }else{
            super.onOptionsItemSelected(item)
        }

    }
}


