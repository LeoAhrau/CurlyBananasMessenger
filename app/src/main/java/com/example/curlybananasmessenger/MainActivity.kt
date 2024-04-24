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
//        setContentView(R.layout.activity_chat)
//
//      //   Starta ChatActivity direkt
//        val intent = Intent(this, ChatActivity::class.java)
//                startActivity(intent)
//        //  Avsluta MainActivity så att den inte är aktiv i backstack
//        finish()


//        // ------------------- Testing---------------------
//        val contactName = intent.getStringExtra("contactName")
//
//        val fragment = ChatInterfaceFragment()
//        val bundle = Bundle()
//        bundle.putString("contactName", contactName)
//        fragment.arguments = bundle
//
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        fragmentTransaction.replace(binding.frameContainerTest.id, fragment)
//        fragmentTransaction.addToBackStack(null)
//        fragmentTransaction.commit()
//        // ------------------- Testing---------------------


    }

}

