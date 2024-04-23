package com.example.curlybananasmessenger

import android.R
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.curlybananasmessenger.databinding.ActivityContactBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.UUID

class ContactActivity : BaseActivity() {

    lateinit var binding: ActivityContactBinding
    lateinit var customAdapter: CustomContactsListAdapter
    lateinit var contactDao: ContactDao
    private lateinit var allContacts: ArrayList<Contact>
    lateinit var mainView: ConstraintLayout
    private val fragment = ChatInterfaceFragment()
    private var firebaseAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainView = binding.mainLayout

        // Initialize custom adapter for ListView
        customAdapter = CustomContactsListAdapter(this, ArrayList())
        binding.lvContacts.adapter = customAdapter

        contactDao = ContactDao(this)

        allContacts = ArrayList()

        // TextWatcher for filtering contacts as user types in search field
        binding.edSearchContact.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No implementation needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Filter contacts based on the entered text
                filterContacts(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                // No implementation needed
            }
        })

        binding.btnAddContact.setOnClickListener {
            addContact()
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()
                    mainView.visibility = View.VISIBLE
                } else {
                    finish()
                }
            }
        })


        // Item click listener to open chat activity when a contact is clicked
        binding.lvContacts.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectedContact = parent.getItemAtPosition(position) as Contact
                mainView.visibility = View.GONE

                // Retrieve the currentContactId for the selected contact
                val currentContactId = selectedContact.contactId

                // Create a new instance of ChatInterfaceFragment
                val fragment = ChatInterfaceFragment().apply {
                    // Pass the contactId as an argument to the fragment
                    arguments = Bundle().apply {
                        putString("contactId", currentContactId)
                        putString("contactName", selectedContact.contactName)
                        putString("currentUserId", firebaseAuth.currentUser?.uid)

                    }
                }

                // Begin transaction to replace the fragment
                supportFragmentManager.beginTransaction()
                    .replace(binding.chatInterfaceContainer.id, fragment)
                    .addToBackStack(null)
                    .commit()

                true
            }

        // Item long click listener to delete a contact
        binding.lvContacts.onItemLongClickListener =
            AdapterView.OnItemLongClickListener { parent, view, position, id ->
                val selectedContact = parent.getItemAtPosition(position) as Contact
                contactDao.deleteContact(selectedContact)
                true
            }
    }

    private fun addContact() {
        try {
            val contactName = binding.etContactName.text.toString()
            val contactEmail = binding.etContactEmail.text.toString()

            // Create a new contact object with UUID as contact ID
            val contact = Contact(UUID.randomUUID().toString(), contactName, contactEmail)
            // Add the contact using ContactDao
            contactDao.addContact(this, contact)

            binding.etContactName.text.clear()
            binding.etContactEmail.text.clear()

        } catch (e: Exception) {
            Log.e("ERROR", e.message.toString())
        }
    }

    // Function to filter contacts based on search query
    private fun filterContacts(query: String) {
        val filteredContacts = ArrayList<Contact>()

        for (contact in allContacts) {
            if (contact.contactName?.contains(query, ignoreCase = true) == true ||
                contact.contactEmail?.contains(query, ignoreCase = true) == true
            ) {
                filteredContacts.add(contact)
            }
        }

        // Update adapter with filtered contacts
        customAdapter.clear()
        customAdapter.addAll(filteredContacts)
        customAdapter.notifyDataSetChanged()
    }

    fun showContacts(contactList: ArrayList<Contact>) {
        // Sort contacts by name
        val sortedContacts = contactList.sortedWith(compareBy { it.contactName?.lowercase() })

        //Update allContacts list
        allContacts.clear()
        allContacts.addAll(sortedContacts)

        // Filter contacts based on current search query
        filterContacts(binding.edSearchContact.text.toString())
    }
}