package com.example.curlybananasmessenger

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.curlybananasmessenger.databinding.ActivityContactBinding
import java.util.UUID

class ContactActivity : BaseActivity() {

    lateinit var binding: ActivityContactBinding
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

    fun showContacts(contactList: ArrayList<Contact>){
        val arrayAdapter = ArrayAdapter(this, R.layout.simple_list_item_1, contactList)
        binding.lvContacts.adapter = arrayAdapter
    }
}