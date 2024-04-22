package com.example.curlybananasmessenger

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {
    private val repo: UserRepository
    private val allUser: LiveData<List<User>>

    private val firestoreDB = FirebaseFirestore.getInstance()
    private val usersCollection = firestoreDB.collection("users")

    private var firebaseAuth: FirebaseAuth

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repo = UserRepository(userDao)
        allUser = repo.allUsers
        firebaseAuth = FirebaseAuth.getInstance()
    }
    fun registerUser(user: User) {
        val userDetails = HashMap<String, Any>()
        userDetails["id"] = user.id ?: ""
        userDetails["nickname"] = user.nickname ?: ""
        userDetails["username"] = user.username ?: ""
        userDetails["password"] = user.password ?: ""
        userDetails["dateOfJoin"] = Timestamp.now()
        userDetails["profileImage"] = user.profileImage ?: ""


        usersCollection.document(user.id ?: "").set(userDetails)
            .addOnSuccessListener { Log.i("SUCCESS", "Successfully registered user") }
            .addOnFailureListener { e ->
                Log.e("FAILURE", "Failed to register user: ${e.message}")
            }
        firebaseAuth.createUserWithEmailAndPassword(user.username.toString(), user.password.toString())
            .addOnSuccessListener { authResult ->

                if (user.username != null && user.password != null) {
                    Toast.makeText(getApplication(), "User registered", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(getApplication(), "User registration failed", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .addOnFailureListener { e ->
                Log.e("FAILURE", "User registration failed: ${e.message}")
                Toast.makeText(getApplication(), "User registration failed", Toast.LENGTH_SHORT).show()
            }

        addUserToRoom(user)
    }
    fun addUserToRoom(user: User) {
        viewModelScope.launch {
            repo.addUser(user)
        }
    }

    fun getAllUsers(): LiveData<List<User>> {
        return repo.allUsers
    }

    fun deleteUser(user: User) = viewModelScope.launch {
        repo.deleteUser(user)
    }

    fun updateUser(user: User) = viewModelScope.launch {
        repo.update(user)
    }

}


