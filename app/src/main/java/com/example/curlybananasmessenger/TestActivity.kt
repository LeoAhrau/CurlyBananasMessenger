package com.example.curlybananasmessenger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.curlybananasmessenger.databinding.ActivityLoginBinding
import com.example.curlybananasmessenger.databinding.ActivityRegisterBinding
import com.example.curlybananasmessenger.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {

    lateinit var binding: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        



    }

}