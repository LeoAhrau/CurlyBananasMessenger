package com.example.curlybananasmessenger


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.curlybananasmessenger.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setContentView(R.layout.activity_chat)

        // Starta ChatActivity direkt
        val intent = Intent(this, ChatActivity::class.java)
        startActivity(intent)

        // Avsluta MainActivity så att den inte är aktiv i backstack
        finish()

        // ------------------- Testing---------------------
        val fragment = supportFragmentManager.beginTransaction()

        fragment.replace(binding.frameContainerTest.id, ChatInterfaceFragment())
        fragment.addToBackStack(null)
        fragment.commit()
        // ------------------- Testing---------------------


    }

}

