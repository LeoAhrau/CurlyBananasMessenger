package com.example.curlybananasmessenger

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class ContactDao {
    val KEY_CONTACT_ID = "contact_id"
    val KEY_CONTACT_NAME = "contact_name"
    val KEY_CONTACT_EMAIL = "contact_email"


    constructor (activity: ContactActivity) {
        FirebaseFirestore
            .getInstance()
            .collection("users/contacts")
            .addSnapshotListener(activity) { value, error ->
                if (error != null) {
                    Log.e("ERROR", "Failed to listen")
                }
                if (value != null) {
                    val contactsList = ArrayList<Contact>()
                    for (document in value) {
                        val contact_id = document.getString(KEY_CONTACT_ID)
                        val contact_name = document.getString(KEY_CONTACT_NAME)
                        val contact_email = document.getString(KEY_CONTACT_EMAIL)

                        val contact = Contact(contact_id, contact_name, contact_email)
                        contactsList.add(contact)
                    }
                    Log.i("SUCCESS", "Contact deleted from FireStore")
                    activity.showContacts(contactsList)
                }
            }
    }


    fun addContact(contact: Contact) {
        val dataToStore = HashMap<String, Any>()

        dataToStore[KEY_CONTACT_ID] = contact.contactId as Any
        dataToStore[KEY_CONTACT_NAME] = contact.contactName as Any
        dataToStore[KEY_CONTACT_EMAIL] = contact.contactEmail as Any

        FirebaseFirestore
            .getInstance()
            .document("users/contacts/${contact.contactId}")
            .set(dataToStore)
            .addOnSuccessListener {
                Log.i(
                    "SUCCESS",
                    "Added contact with name $(contact.contactID) to contacts"
                )
            }
            .addOnFailureListener { Log.i("FAILURE", "Failed to add contact to contacts") }

    }

//    fun fetchContacts(activity: ContactActivity) {
//        val contactsList = ArrayList<Contact>()
//
//        FirebaseFirestore
//            .getInstance()
//            .collection("users/contacts")
//            .get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    val contact_id = document.getString(KEY_CONTACT_ID)
//                    val contact_name = document.getString(KEY_CONTACT_NAME)
//                    val contact_email = document.getString(KEY_CONTACT_EMAIL)
//
//                    val contact = Contact(contact_id, contact_name, contact_email)
//                    contactsList.add(contact)
//                }
//                Log.i("SUCCESS", "Successfully fetched all contacts")
//                activity.showContacts(contactsList)
//            }
//    }

    fun deleteContact(selectedContact : Contact){
        FirebaseFirestore
            .getInstance()
            .document("users/contacts/${selectedContact.contactId}")
            .delete()
            .addOnSuccessListener { Log.i("SUCCESS", "Contact deleted from FireSrore") }
            .addOnFailureListener { Log.e("Failure", "Failed to delete contact") }
    }


}
