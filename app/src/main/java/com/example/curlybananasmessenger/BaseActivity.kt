package com.example.curlybananasmessenger

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

open class BaseActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var auth: FirebaseAuth

    @SuppressLint("InflateParams")
    override fun setContentView(view: View?) {
        auth = FirebaseAuth.getInstance()

        drawerLayout = layoutInflater.inflate(R.layout.activity_base, null) as DrawerLayout
        val container =
            drawerLayout.findViewById<FrameLayout>(R.id.frame_container) // OBS! Container
        container.addView(view)

        super.setContentView(drawerLayout)

        val toolbar = drawerLayout.findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView = drawerLayout.findViewById<NavigationView>(R.id.nv_navigation)
        navigationView.setNavigationItemSelectedListener {
            onOptionsItemSelected(it)
        }

        drawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_contacts -> {
                    finish()
                    startActivity(Intent(this, ContactActivity::class.java))
                    true
                }

                R.id.item_add_contact -> {
                    finish()
                    startActivity(Intent(this, ContactActivity::class.java))
                    true
                }

                R.id.item_logout -> {
                    // TODO Change this
                    auth.signOut()
                    println("Logged out")
                    true
                }

                R.id.item_profile -> {
                    finish()
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (drawerToggle.onOptionsItemSelected(item)) {
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}