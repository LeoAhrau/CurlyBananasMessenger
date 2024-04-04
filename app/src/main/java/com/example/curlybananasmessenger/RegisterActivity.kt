package com.example.curlybananasmessenger

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.curlybananasmessenger.databinding.ActivityRegisterBinding
import java.util.UUID

class RegisterActivity : AppCompatActivity(){

    lateinit var binding: ActivityRegisterBinding
    val userDao = UserDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {

            registerUser()
        }



    }


    fun registerUser(){
        try {
            val nickname = binding.etNickname.text.toString()
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            val user = User(UUID.randomUUID().toString(), nickname, username, password)
            userDao.registerUser(user)

        } catch(e: Exception){
            Log.e("FAILURE", e.message.toString())
        }


    }
}