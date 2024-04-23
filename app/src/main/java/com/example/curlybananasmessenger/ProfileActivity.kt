package com.example.curlybananasmessenger

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.curlybananasmessenger.databinding.ActivityProfileBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread
import java.net.URL

// Activity for managing user profile
class ProfileActivity : BaseActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var firestoreDB: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var fbStorage: FirebaseStorage
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestoreDB = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        fbStorage = FirebaseStorage.getInstance()

        // Retrieve user information
        getUserInfo()

        binding.ivProfileImage.setOnClickListener {
            showImageOptionsDialog()
        }

        // Save data changes both for name of the user and uploaded image in IV field
        binding.btnSaveProfile.setOnClickListener {
            saveProfile()
        }

        // Log out button
        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showImageOptionsDialog() {
        val options = arrayOf("Choose from Gallery", "Take a Photo")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Option")
        builder.setItems(options) { dialog, which ->
            when (which) {
                0 -> chooseFromGallery()
                1 -> takePhoto()
            }
        }
        builder.show()
    }


    /* 'startActivityForResult(Intent, Int): Unit' is deprecated. But still working in Kotlin!
        Used launcher instead

    private fun chooseFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, REQUEST_GALLERY)
    }

    private fun takePhoto() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CAMERA)
    }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_GALLERY -> {
                    // Handle image chosen from gallery
                    val imageUri = data?.data
                    binding.ivProfileImage.setImageURI(imageUri)
                    selectedImageUri = imageUri // Store selected image URI
                }
                REQUEST_CAMERA -> {
                    // Handle photo taken from camera
                    val imageBitmap = data?.extras?.get("data") as? Bitmap
                    imageBitmap?.let {
                        binding.ivProfileImage.setImageBitmap(imageBitmap)
                        // Convert bitmap to URI
                        selectedImageUri = bitmapToUriConverter(imageBitmap)
                    }
                }
            }
        }
    }
     */

    // Capture photo using device camera
    private fun takePhoto() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncher.launch(cameraIntent)
    }

    // Choose image from device gallery
    fun chooseFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        resultLauncher.launch(galleryIntent)
    }

    companion object {
        private const val REQUEST_GALLERY = 1
        private const val REQUEST_CAMERA = 2
    }

    // Activity Result Launcher for handling image capture and selection
    var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data

                when (result.resultCode) {
                    REQUEST_GALLERY -> {
                        val imageUri = data?.data
                        binding.ivProfileImage.setImageURI(imageUri)
                        selectedImageUri = imageUri
                    }

                    REQUEST_CAMERA -> {
                        val imageBitmap = data?.extras?.get("data") as? Bitmap
                        imageBitmap?.let {
                            binding.ivProfileImage.setImageBitmap(imageBitmap)
                            // Convert bitmap to URI
                            selectedImageUri = bitmapToUriConverter(imageBitmap)
                        }
                    }
                }
            }
        }

    // Convert Bitmap to URI
    private fun bitmapToUriConverter(bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            contentResolver,
            bitmap,
            "Title",
            null
        )
        return Uri.parse(path)
    }

    // Save changes in IV and Name
    private fun saveProfile() {
        // Get current user's UID
        val currentUserUid = auth.currentUser?.uid ?: ""
        if (currentUserUid.isNotEmpty()) {
            // Reference to current user's document in Firestore
            val currentUserDocRef = firestoreDB.collection("users").document(currentUserUid)
            currentUserDocRef.get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val user: User? = document.toObject(User::class.java)

                        if (user != null) {
                            // Check if user has a profile image
                            if (!user.profileImage.isNullOrEmpty()) {
                                // Load profile image from Firestore Storage
                                val storageRef = fbStorage.reference.child("profile_images")
                                    .child("$currentUserUid.jpg")
                                storageRef.downloadUrl.addOnSuccessListener { uri ->
                                    // Load image from URL into ImageView
                                    thread {
                                        try {
                                            val url = URL(uri.toString())
                                            val connection = url.openConnection()
                                            connection.doInput = true
                                            connection.connect()
                                            val input = connection.getInputStream()
                                            val bitmap = BitmapFactory.decodeStream(input)

                                            runOnUiThread {
                                                binding.ivProfileImage.setImageBitmap(bitmap)
                                            }
                                        } catch (e: Exception) {
                                            Log.e(
                                                "ProfileActivity",
                                                "Failed to load profile image: ${e.message}",
                                                e
                                            )
                                        }
                                    }
                                }.addOnFailureListener { e ->
                                    Log.e(
                                        "ProfileActivity",
                                        "Failed to load profile image: ${e.message}",
                                        e
                                    )
                                }
                            }
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

            // Update user's nickname in Firestore
            val newNickname = binding.etChangeName.text.toString()
            if (newNickname.isNotEmpty()) {
                currentUserDocRef.update("nickname", newNickname)
                    .addOnSuccessListener {
                        binding.tvProfileName.text =
                            getString(R.string.profile_name_label) + " " + newNickname
                        binding.etChangeName.text.clear()
                        Log.d("ProfileActivity", "Nickname updated successfully")
                    }
                    .addOnFailureListener { e ->
                        Log.e("ProfileActivity", "Failed to update nickname: ${e.message}", e)
                    }
            }

        } else {
            Log.e("ProfileActivity", "Current user UID is empty")
        }
    }

    // Retrieve user information from Firestore
    private fun getUserInfo() {
        // Get current user's UID
        val currentUserUid = auth.currentUser?.uid ?: ""
        if (currentUserUid.isNotEmpty()) {
            // Reference to current user's document in Firestore
            val currentUserDocRef = firestoreDB.collection("users").document(currentUserUid)
            currentUserDocRef.get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val user: User? = document.toObject(User::class.java)

                        if (user != null) {
                            // Display user information
                            val profileName =
                                getString(R.string.profile_name_label) + " " + user.nickname
                            binding.tvProfileName.text = profileName
                            val profileEmail =
                                getString(R.string.profile_email_label) + " " + user.username
                            binding.tvProfileEmail.text = profileEmail

                            // Convert timestamp to readable date format
                            val timestamp = user.dateOfJoin as? Timestamp
                            val formattedDate = formatDate(timestamp?.toDate())
                            val profileDate =
                                getString(R.string.profile_date_label) + " " + formattedDate
                            binding.tvProfileDate.text = profileDate

                            // Check if user has a profile image
                            if (!user.profileImage.isNullOrEmpty()) {
                                // Load profile image from Firestore Storage
                                val storageRef = fbStorage.reference.child("profile_images")
                                    .child("$currentUserUid.jpg")
                                storageRef.downloadUrl.addOnSuccessListener { uri ->
                                    // Load image from URL into ImageView
                                    thread {
                                        try {
                                            val url = URL(uri.toString())
                                            val connection = url.openConnection()
                                            connection.doInput = true
                                            connection.connect()
                                            val input = connection.getInputStream()
                                            val bitmap = BitmapFactory.decodeStream(input)

                                            runOnUiThread {
                                                binding.ivProfileImage.setImageBitmap(bitmap)
                                            }
                                        } catch (e: Exception) {
                                            Log.e(
                                                "ProfileActivity",
                                                "Failed to load profile image: ${e.message}",
                                                e
                                            )
                                        }
                                    }
                                }.addOnFailureListener { e ->
                                    Log.e(
                                        "ProfileActivity",
                                        "Failed to load profile image: ${e.message}",
                                        e
                                    )
                                }
                            }
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

    // Function to format date to a human readable format
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