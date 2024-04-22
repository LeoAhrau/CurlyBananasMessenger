package com.example.curlybananasmessenger

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class ContactDao {
    val KEY_CONTACT_ID = "contact_id"
    val KEY_CONTACT_NAME = "contact_name"
    val KEY_CONTACT_EMAIL = "contact_email"

    private val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
    constructor (activity: ContactActivity) {
        FirebaseFirestore
            .getInstance()
            .collection("users/$currentUserUid/contacts")
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


    fun addContact(context: Context, contact: Contact) {
        val userEmail = contact.contactEmail

        // Query the users collection to find the user with the given email
        FirebaseFirestore.getInstance().collection("users")
            .whereEqualTo("username", userEmail)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    // Email does not exist, show toast
                    Log.e("ERROR", "User with email $userEmail does not exist")
                    Toast.makeText(context, "Please enter a valid email", Toast.LENGTH_SHORT).show()
                } else {
                    // Email exists, get the ID of the user
                    val userId = documents.documents[0].id

                    // Add contact with the same ID as the user to contacts collection
                    val dataToStore = hashMapOf(
                        "contact_id" to userId,
                        "contact_name" to contact.contactName,
                        "contact_email" to contact.contactEmail
                    )

                    FirebaseFirestore.getInstance()
                        .document("users/$currentUserUid/contacts/$userId") // Use userId as document ID
                        .set(dataToStore)
                        .addOnSuccessListener {
                            Log.i("SUCCESS", "Added contact with email $userEmail to contacts")
                        }
                        .addOnFailureListener {
                            Log.e("FAILURE", "Failed to add contact with email $userEmail to contacts")
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.e("ERROR", "Error getting documents: ", exception)
            }
    }


    fun deleteContact(selectedContact : Contact){
        FirebaseFirestore
            .getInstance()
            .document("users/$currentUserUid/contacts/${selectedContact.contactId}")
            .delete()
            .addOnSuccessListener { Log.i("SUCCESS", "Contact deleted from FireSrore") }
            .addOnFailureListener { Log.e("Failure", "Failed to delete contact") }
    }


}
