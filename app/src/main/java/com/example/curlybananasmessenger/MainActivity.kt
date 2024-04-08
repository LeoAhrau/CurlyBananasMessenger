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

    }
}

//Använd suspend för enstaka asynkrona operationer som inte kräver direkt observabilitet för UI-uppdateringar, såsom att infoga eller radera poster.
//Använd LiveData för att exponera data till UI som automatiskt ska uppdateras när databasen ändras, perfekt för läsoperationer där resultaten direkt påverkar vad användaren ser.
//Överväg att använda Flow för mer avancerad asynkron datahantering och strömningskontroll, särskilt när du behöver utföra ytterligare operationer på datan innan den når UI.