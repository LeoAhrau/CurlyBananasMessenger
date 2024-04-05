package com.example.curlybananasmessenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.curlybananasmessenger.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Testing
        val fragment = supportFragmentManager.beginTransaction()

        fragment.replace(binding.frameContainerTest.id, ChatInterfaceFragment())
        fragment.addToBackStack(null)
        fragment.commit()


    }
}