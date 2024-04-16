package com.example.curlybananasmessenger

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.curlybananasmessenger.databinding.ActivityContactBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.UUID

class ContactActivity : AppCompatActivity() {

    lateinit var binding: ActivityContactBinding
    lateinit var customAdapter: CustomContactsListAdapter
    lateinit var contactDao: ContactDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        customAdapter = CustomContactsListAdapter(this, ArrayList())
        binding.lvContacts.adapter = customAdapter

        contactDao = ContactDao(this)

        binding.btnAddContact.setOnClickListener {
            addContact()
        }

        binding.lvContacts.onItemLongClickListener =
            AdapterView.OnItemLongClickListener { parent, view, position, id ->
                val selectedContact = parent.getItemAtPosition(position) as Contact
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("contactName", selectedContact)
                startActivity(intent)
                finish()
                true
            }

        //binding.lvContacts.onItemLongClickListener =
        //            AdapterView.OnItemLongClickListener { parent, view, position, id ->
        //                val selectedContact = parent.getItemAtPosition(position) as Contact
        //                contactDao.deleteContact(selectedContact)
        //                true
        //            }
    }

    private fun addContact() {
        try {
            val contactName = binding.etContactName.text.toString()
            val contactEmail = binding.etContactEmail.text.toString()

            val contact = Contact(UUID.randomUUID().toString(), contactName, contactEmail)
            contactDao.addContact(contact)

            binding.etContactName.text.clear()
            binding.etContactEmail.text.clear()

        } catch (e: Exception) {
            Log.e("ERROR", e.message.toString())
        }
    }

    fun showContacts(contactList: ArrayList<Contact>) {
        customAdapter.clear()
        customAdapter.addAll(contactList)
        customAdapter.notifyDataSetChanged()
    }
}
