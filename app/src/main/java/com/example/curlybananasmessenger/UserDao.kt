package com.example.curlybananasmessenger


import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class UserDao {

    private val firestoreDB = FirebaseFirestore.getInstance()
    private val usersCollection = firestoreDB.collection("users")

    fun registerUser(user: User) {
        val userDetails = HashMap<String, Any>()
        userDetails["id"] = user.id ?: ""
        userDetails["nickname"] = user.nickname ?: ""
        userDetails["username"] = user.username ?: ""
        userDetails["password"] = user.password ?: ""

        usersCollection.document(user.id ?: "").set(userDetails)
            .addOnSuccessListener { Log.i("SUCCESS", "Successfully registered user") }
            .addOnFailureListener { e ->
                Log.e("FAILURE", "Failed to register user: ${e.message}")
            }
    }

}


