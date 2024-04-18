package com.example.curlybananasmessenger

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.curlybananasmessenger.databinding.ActivityProfileBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var firestoreDB: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestoreDB = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        // Retrieve user information
        getUserInfo()
    }

    private fun getUserInfo() {
        val currentUserUid = auth.currentUser?.uid ?: ""
        if (currentUserUid.isNotEmpty()) {
            val currentUserDocRef = firestoreDB.collection("users").document(currentUserUid)
            currentUserDocRef.get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val user: User? = document.toObject(User::class.java)

                        if (user != null) {
                            // Display user information
                            val profileName = getString(R.string.profile_name_label) + " " + user.nickname
                            binding.tvProfileName.text = profileName
                            val profileEmail = getString(R.string.profile_email_label) + " " + user.nickname
                            binding.tvProfileEmail.text = profileEmail

                            // Convert timestamp to readable date format
                            val timestamp = user.dateOfJoin as? Timestamp
                            val formattedDate = formatDate(timestamp?.toDate())
                            val profileDate = getString(R.string.profile_date_label) + " " + formattedDate
                            binding.tvProfileDate.text = profileDate
                        } else {
                            Log.e("ProfileActivity", "User data is null")
                        }
                    } else {
                        Log.e("ProfileActivity", "User document does not exist")
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("ProfileActivity", "Failed to retrieve user data: ${e.message}", e)
                }
        } else {
            Log.e("ProfileActivity", "Current user UID is empty")
        }
    }

    private fun formatDate(date: Date?): String {
        return try {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            sdf.format(date)
        } catch (e: Exception) {
            Log.e("ProfileActivity", "Error formatting date: ${e.message}", e)
            ""
        }
    }
}