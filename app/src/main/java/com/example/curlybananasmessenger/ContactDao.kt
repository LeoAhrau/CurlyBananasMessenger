package com.example.curlybananasmessenger

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// DAO (Data Access Object) for managing contacts
class ContactDao {

    val KEY_CONTACT_ID = "contact_id"
    val KEY_CONTACT_NAME = "contact_name"
    val KEY_CONTACT_EMAIL = "contact_email"

    private val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid

    // Constructor to initialize ContactDao with activity context
    constructor (activity: ContactActivity) {
        // Listen for changes in the Firestore "contacts" collection for the current user
        FirebaseFirestore
            .getInstance()
            .collection("users/$currentUserUid/contacts")
            .addSnapshotListener(activity) { value, error ->
                if (error != null) {
                    Log.e("ERROR", "Failed to listen")
                }
                if (value != null) {
                    val contactsList = ArrayList<Contact>()
                    // Iterate over documents in the snapshot
                    for (document in value) {
                        // Extract contact data from the document
                        val contact_id = document.getString(KEY_CONTACT_ID)
                        val contact_name = document.getString(KEY_CONTACT_NAME)
                        val contact_email = document.getString(KEY_CONTACT_EMAIL)
                        // Create a Contact object and add it to the list
                        val contact = Contact(contact_id, contact_name, contact_email)
                        contactsList.add(contact)
                    }
                    Log.i("SUCCESS", "Contacts retrieved from Firestore")
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
                // If entered email not found, show error toast
                if (documents.isEmpty) {
                    Log.e("ERROR", "User with email $userEmail does not exist")
                    Toast.makeText(context, "Please enter a valid email", Toast.LENGTH_SHORT).show()
                } else {
                    // Get the ID of the user with the given email
                    val userId = documents.documents[0].id
                    // Create data to store in Firestore
                    val dataToStore = hashMapOf(
                        "contact_id" to userId,
                        "contact_name" to contact.contactName,
                        "contact_email" to contact.contactEmail
                    )
                    // Add contact document to Firestore under the current user's contacts
                    FirebaseFirestore.getInstance()
                        // Use userId as document ID
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

    fun deleteContact(selectedContact: Contact) {
        FirebaseFirestore
            .getInstance()
            .document("users/$currentUserUid/contacts/${selectedContact.contactId}")
            .delete()
            .addOnSuccessListener { Log.i("SUCCESS", "Contact deleted from Firestore") }
            .addOnFailureListener { Log.e("FAILURE", "Failed to delete contact") }
    }

}