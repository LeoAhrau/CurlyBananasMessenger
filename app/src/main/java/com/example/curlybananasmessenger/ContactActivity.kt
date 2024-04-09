package com.example.curlybananasmessenger

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.curlybananasmessenger.databinding.ActivityContactBinding
import com.example.curlybananasmessenger.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.UUID

class ContactActivity : AppCompatActivity() {

    lateinit var binding: ActivityContactBinding
//    lateinit var firebaseDB: FirebaseDatabase

    var contactDao = ContactDao(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnAddContact.setOnClickListener {
            addContact()
        }

        binding.lvContacts.onItemLongClickListener =
            AdapterView.OnItemLongClickListener { parent, view, position, id ->
                val selectedContact = parent.getItemAtPosition(position) as Contact
                contactDao.deleteContact(selectedContact)
                true
            }
    }

    private fun addContact() {
        try {
//            firebaseDB = FirebaseDatabase.getInstance()

            val contactName = binding.etContactName.toString()
            val contactEmail = binding.etContactEmail.toString()

            val contact = Contact(UUID.randomUUID().toString(), contactName, contactEmail)
            contactDao.addContact(contact)

        } catch (e: Exception) {
            Log.e("ERROR", e.message.toString())
        }
    }

    fun showContacts(contactList: ArrayList<Contact>){
        val arrayAdapter = ArrayAdapter(this, R.layout.simple_list_item_1, contactList)
        binding.lvContacts.adapter = arrayAdapter
    }
}