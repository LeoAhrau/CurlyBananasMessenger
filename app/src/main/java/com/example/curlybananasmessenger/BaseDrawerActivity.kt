package com.example.curlybananasmessenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.curlybananasmessenger.databinding.ActivityBaseDrawerBinding
import com.google.android.material.navigation.NavigationView

class BaseDrawerActivity : AppCompatActivity() {

    lateinit var binding: ActivityBaseDrawerBinding
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

     fun instantiateDrawer() {
        drawerLayout = findViewById(binding.drawerMenuLayout.id)
        val navView: NavigationView = findViewById(binding.nvNavigation.id)

        navView.setNavigationItemSelectedListener { menuItem ->
            // Identifiera vilket menyobjekt som har blivit klickat
            when (menuItem.itemId) {
                R.id.item_profile -> {
                    closeDrawerMenu()
                }
                R.id.item_conversations -> {
                    closeDrawerMenu()
                }
                R.id.item_contacts -> {
                    finish()
                    val intent = Intent(this, ContactActivity::class.java)
                    startActivity(intent)
                }
                R.id.item_add_contact -> {
                    finish()
                    val intent = Intent(this, ContactActivity::class.java)
                    startActivity(intent)
                }
                R.id.item_logout -> {
                    closeDrawerMenu()

                }
                // Lägg till fler fall för andra menyalternativ om det behövs
            }
            // Stäng Navigation Drawer efter klick
            drawerLayout.closeDrawer(GravityCompat.START)
            // Indikera att klickhändelsen har hanterats korrekt
            true
        }
    }

    fun openDrawerMenu() {
        drawerLayout.openDrawer(GravityCompat.START)
    }

    fun closeDrawerMenu() {
        drawerLayout.closeDrawer(GravityCompat.START)
    }
}