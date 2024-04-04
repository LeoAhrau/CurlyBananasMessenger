package com.example.curlybananasmessenger

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class UserDao {

    val KEY_ID = "id"
    val KEY_NICKNAME = "nickname"
    val KEY_USERNAME = "username"
    val KEY_PASSWORD = "password"

    fun registerUser(user: User){
        val userDetails = HashMap<String, Any>()

        userDetails[KEY_ID] = user.id as Any
        userDetails[KEY_NICKNAME] = user.nickname as Any
        userDetails[KEY_USERNAME] = user.username as Any
        userDetails[KEY_PASSWORD] = user.password as Any

        FirebaseFirestore
            .getInstance()
            .document("users/${user.id}")
            .set(userDetails)
            .addOnSuccessListener { Log.i("SUCCESS", "Successfully registered user") }
            .addOnFailureListener { Log.e("FAILURE", "Failed to register user") }

    }


}