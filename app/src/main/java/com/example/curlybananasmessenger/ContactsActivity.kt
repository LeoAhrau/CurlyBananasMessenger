package com.example.curlybananasmessenger

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
//
//class ContactsActivity : AppCompatActivity() {
//    private val viewModel: ContactsViewModel by viewModels { ContactsViewModelFactory(ContactsRepository(ContactsDatabase.getDatabase(this).contactsDao())) }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_contacts)
//
//        val editTextName = findViewById<EditText>(R.id.editTextName)
//        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
//        val buttonAddContact = findViewById<Button>(R.id.buttonAddContact)
//        val textViewContacts = findViewById<TextView>(R.id.textViewContacts)
//
//        // Observing the LiveData from the ViewModel to update UI
//        viewModel.allContacts.observe(this, Observer { contacts ->
//            // Display each contact in the TextView
//            textViewContacts.text = contacts.joinToString("\n") { contact ->
//                "${contact.name} - ${contact.email}"
//            }
//        })
//
//        // Handling button click event
//        buttonAddContact.setOnClickListener {
//            val name = editTextName.text.toString().trim()
//            val email = editTextEmail.text.toString().trim()
//
//            if (name.isNotEmpty() && email.isNotEmpty()) {
//                // Insert new contact into the ViewModel
//                viewModel.insert(Contacts(name = name, email = email))
//
//                // Clear the input fields after insertion
//                editTextName.text.clear()
//                editTextEmail.text.clear()
//            } else {
//                // Show an error or toast message if fields are empty
//                Toast.makeText(this, "Both fields are required", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//}

//        viewModel.allContacts.observe(this, Observer { contacts ->
//            textViewContacts.text = contacts.joinToString("\n") { contact ->
//                "${contact.name} - ${contact.email}"
//            }
//        })
//
//        buttonAddContact.setOnClickListener {
//            val name = editTextName.text.toString()
//            val email = editTextEmail.text.toString()
//            if (name.isNotBlank() && email.isNotBlank()) {
//                viewModel.insert(Contacts(name = name, email = email))
//                editTextName.text.clear()
//                editTextEmail.text.clear()
//            }
//        }
//    }
//}
//

//import android.os.Bundle
//import androidx.activity.viewModels
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.Observer
////
//class ContactsActivity : AppCompatActivity() {
//    private val viewModel: ContactsViewModel by viewModels()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_contacts)
//
//        val editTextName = findViewById<EditText>(R.id.editTextName)
//        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
//        val buttonAddContact = findViewById<Button>(R.id.buttonAddContact)
//        val textViewContacts = findViewById<TextView>(R.id.textViewContacts)
//
//        viewModel.allContacts.observe(this, Observer { contacts ->
//            textViewContacts.text = contacts.joinToString("\n") { contact ->
//                "${contact.name} - ${contact.email}"
//            }
//        })
//
//        buttonAddContact.setOnClickListener {
//            val name = editTextName.text.toString()
//            val email = editTextEmail.text.toString()
//            viewModel.insert(Contact(name = name, email = email))
//            editTextName.text.clear()
//            editTextEmail.text.clear()
//        }
//    }
//}
//

//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import android.widget.TextView
//import androidx.activity.viewModels
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.Observer

//class ContactsActivity : AppCompatActivity() {
//    private val viewModel: ContactsViewModel by viewModels()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_contacts)
//
//        val editTextName = findViewById<EditText>(R.id.editTextName)
//        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
//        val buttonAddContact = findViewById<Button>(R.id.buttonAddContact)
//        val textViewContacts = findViewById<TextView>(R.id.textViewContacts)
//
//        viewModel.allContacts.observe(this, Observer { contacts ->
//            textViewContacts.text = contacts.joinToString("\n") { contact ->
//                "${contact.name} - ${contact.email}"
//            }
//        })
//
//        buttonAddContact.setOnClickListener {
//            val name = editTextName.text.toString()
//            val email = editTextEmail.text.toString()
//            viewModel.insert(Contact(name = name, email = email))
//            editTextName.text.clear()
//            editTextEmail.text.clear()
//        }
//    }
//}
//
//


//
//import android.os.Bundle
//import androidx.activity.viewModels
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.Observer
//
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import android.widget.TextView
//import androidx.activity.viewModels
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.Observer
//
//class ContactsActivity : AppCompatActivity() {
//    private val viewModel: ContactsViewModel by viewModels()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_contacts)
//
//        val editTextName = findViewById<EditText>(R.id.editTextName)
//        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
//        val buttonAddContact = findViewById<Button>(R.id.buttonAddContact)
//        val textViewContacts = findViewById<TextView>(R.id.textViewContacts)
//
//        viewModel.allContacts.observe(this, Observer { contacts ->
//            textViewContacts.text = contacts.joinToString("\n") { contact ->
//                "${contact.name} - ${contact.email}"
//            }
//        })
//
//        buttonAddContact.setOnClickListener {
//            val name = editTextName.text.toString()
//            val email = editTextEmail.text.toString()
//            viewModel.insert(Contact(name = name, email = email))
//            editTextName.text.clear()
//            editTextEmail.text.clear()
//        }
//    }
//}
//
////
//
//class ContactsActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityContactsBinding
//    private val viewModel: ContactsViewModel by viewModels()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityContactsBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        viewModel.allContacts.observe(this, Observer { contacts ->
//            binding.textViewContacts.text = contacts.joinToString("\n") { contact ->
//                "${contact.name} - ${contact.email} - ${contact.phoneNumber} - ${contact.address}"
//            }
//        })
//
//        binding.buttonAddContact.setOnClickListener {
//            val name = binding.editTextName.text.toString()
//            val email = binding.editTextEmail.text.toString()
//
//            viewModel.insert(Contact(name = name, email = email))
//            clearFields()
//        }
//    }
//
//    private fun clearFields() {
//        binding.editTextName.text.clear()
//        binding.editTextEmail.text.clear()
//    }
//}



//class ContactsActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityContactsBinding
//    private val viewModel: ContactsViewModel by viewModels()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityContactsBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        viewModel.contacts.observe(this, Observer { contacts ->
//            displayContacts(contacts)
//        })
//
//        viewModel.fetchContactsAndStore()
//    }
//
//    private fun displayContacts(contacts: List<Contact>) {
//        val contactsText = contacts.joinToString("\n") { contact ->
//            "Name: ${contact.name}, Email: ${contact.email}, Phone: ${contact.phoneNumber}, Address: ${contact.address}"
//        }
//        binding.textViewContacts.text = contactsText
//    }
//}
