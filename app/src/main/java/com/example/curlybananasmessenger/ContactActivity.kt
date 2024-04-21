package com.example.curlybananasmessenger

import android.R
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.curlybananasmessenger.databinding.ActivityContactBinding
import java.util.UUID

class ContactActivity : BaseActivity() {

    lateinit var binding: ActivityContactBinding
    lateinit var customAdapter: CustomContactsListAdapter
    lateinit var contactDao: ContactDao
    private lateinit var allContacts: ArrayList<Contact>
    lateinit var mainView: ConstraintLayout
    private val fragment = ChatInterfaceFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainView = binding.mainLayout

        customAdapter = CustomContactsListAdapter(this, ArrayList())
        binding.lvContacts.adapter = customAdapter

        contactDao = ContactDao(this)

        allContacts = ArrayList()

        binding.edSearchContact.addTextChangedListener(object : TextWatcher{
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


        binding.lvContacts.onItemLongClickListener =
            AdapterView.OnItemLongClickListener { parent, view, position, id ->
                val selectedContact = parent.getItemAtPosition(position) as Contact
                mainView.visibility = View.GONE
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.add(binding.chatInterfaceContainer.id, fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()

                val bundle = Bundle()
                bundle.putString("contactName", selectedContact.contactName)
                fragment.arguments = bundle
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

    private fun filterContacts(query: String) {
        val filteredContacts = ArrayList<Contact>()

        for (contact in allContacts) {
            if (contact.contactName?.contains(query, ignoreCase = true) == true ||
                contact.contactEmail?.contains(query, ignoreCase = true) == true
            ) {
                filteredContacts.add(contact)
            }
        }

        customAdapter.clear()
        customAdapter.addAll(filteredContacts)
        customAdapter.notifyDataSetChanged()
    }

    fun showContacts(contactList: ArrayList<Contact>) {
        val sortedContacts = contactList.sortedWith(compareBy { it.contactName?.lowercase() })

        allContacts.clear()
        allContacts.addAll(sortedContacts)

        filterContacts(binding.edSearchContact.text.toString())
    }
}
