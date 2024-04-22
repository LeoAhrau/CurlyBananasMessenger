package com.example.curlybananasmessenger

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.curlybananasmessenger.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonToRoom.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.buttonShowContacts.setOnClickListener {
            val intent = Intent(this, ContactActivity::class.java) //DETTA SKA VARA UTAN S
            startActivity(intent)
            finish()
        }
        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
        firebaseAuth = FirebaseAuth.getInstance()
        binding.btnLogin.setOnClickListener{
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            authenticateUser(username,password)
        }



    }

    fun authenticateUser(username: String,password: String){
        if (!Patterns.EMAIL_ADDRESS.matcher(username).matches() || password.isEmpty() ){
           Toast.makeText(this, "Both or one Field is incorrect/empty",
                                                           Toast.LENGTH_SHORT).show()
        }else{
        login(username,password)
        }

    }

    fun login(username: String, password: String){
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(username,password)
            .addOnSuccessListener {
                val intent = Intent(this, ContactActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener { error ->
                Toast.makeText(this, "Thy credentials tis not correct",
                                                   Toast.LENGTH_SHORT).show()
            }
    }

}