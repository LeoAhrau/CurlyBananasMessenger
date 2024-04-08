package com.example.curlybananasmessenger

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.curlybananasmessenger.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.emptyFlow
import java.util.UUID

class RegisterActivity : AppCompatActivity(){

    lateinit var binding: ActivityRegisterBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseDB: FirebaseDatabase
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
                firebaseAuth = FirebaseAuth.getInstance()
                firebaseDB = FirebaseDatabase.getInstance()
            val nickname = binding.etNickname.text.toString()
            val username = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()



            if (nickname.isEmpty()){
                Toast.makeText(this, "Enter thy title", Toast.LENGTH_SHORT).show()
            }else if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()){
                Toast.makeText(this, "Enter thy name ", Toast.LENGTH_SHORT).show()
            }else if(password.isEmpty()){
                Toast.makeText(this, "Enter thee secret word", Toast.LENGTH_SHORT).show()
            }else{


                val user = User(UUID.randomUUID().toString(), nickname, username, password)
                userDao.registerUser(user)
                firebaseAuth.createUserWithEmailAndPassword(username,password)
                Toast.makeText(this,"Successfully registered", Toast.LENGTH_SHORT).show()
            }




        } catch(e: Exception){
            Log.e("FAILURE", e.message.toString())
        }


    }


}